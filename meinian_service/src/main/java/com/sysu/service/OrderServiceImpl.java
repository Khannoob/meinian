package com.sysu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sysu.constant.MessageConstant;
import com.sysu.mapper.MemberMapper;
import com.sysu.mapper.OrderMapper;
import com.sysu.mapper.OrderSettingMapper;
import com.sysu.entity.Result;
import com.sysu.pojo.Member;
import com.sysu.pojo.Order;
import com.sysu.pojo.OrderSetting;
import com.sysu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    OrderSettingMapper orderSettingMapper;

    @Override
    public Result submit(Map map) throws Exception {
        Date orderDate = DateUtils.parseString2Date(map.get("orderDate").toString());
        OrderSetting orderSetting = orderSettingMapper.findOrderSettingByDate(orderDate);

        String telephone = (String) map.get("telephone");
        Member member = memberMapper.findMemberByTelephone(telephone);
        int setMealId = Integer.parseInt(map.get("setmealId").toString());


//        1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else {
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
//        2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
            if (number <= reservations) {
                return new Result(false, MessageConstant.ORDER_FULL);
            } else {
//        3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
                if (member!=null){
                    Order order = orderMapper.findOrderByDateMemberIdAndSetMealId(orderDate,member.getId(),setMealId);
                    if (order!=null){
                        return new Result(false, MessageConstant.HAS_ORDERED);
                    }
                } else {
//        4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
                    member = new Member();
                    member.setName(map.get("name").toString());
                    member.setPhoneNumber(telephone);
                    member.setIdCard(map.get("idCard").toString());
                    member.setRegTime(new Date());
                    member.setSex(map.get("sex").toString());
                    memberMapper.addMember(member);
                }
//        5、预约成功，更新当日的已预约人数
                Order order = new Order();
                order.setMemberId(member.getId());
                order.setOrderDate(orderDate);
                order.setOrderType(Order.ORDERTYPE_WEIXIN);
                order.setSetmealId(setMealId);
                order.setOrderStatus(Order.ORDERSTATUS_NO);
                orderMapper.addOrder(order);
                orderSetting.setReservations(orderSetting.getReservations()+1);
                orderSettingMapper.editOrderSetting(orderSetting);
                return new Result(true, MessageConstant.ORDER_SUCCESS,order);

            }
        }
    }

    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderMapper.findById(id);
//        Date orderDate = DateUtils.parseString2Date(map.get("orderDate").toString());
        Date orderDate = (Date) map.get("orderDate");
        map.put("orderDate", DateUtils.parseDate2String(orderDate));
        return map;
    }
}
