package com.sysu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sysu.mapper.OrderSettingMapper;
import com.sysu.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingMapper orderSettingMapper;


    @Override
//    public void add(MultipartFile excelFile) throws Exception {
    public void add(List<OrderSetting> orderSettingList) {
//        List<String[]> strings = POIUtils.readExcel(excelFile);
//        List<OrderSetting> orderSettingList = new ArrayList<>();
//        for (String[] string : strings) {
//            OrderSetting orderSetting = new OrderSetting();
//            orderSetting.setOrderDate(new Date(string[0]));
//            orderSetting.setNumber(Integer.parseInt(string[1]));
//            orderSettingList.add(orderSetting);
//        }
        for (OrderSetting orderSetting : orderSettingList) {
            Long count = orderSettingMapper.queryOrderSettingByDate(orderSetting.getOrderDate());
            if (count > 0) {
                orderSettingMapper.editOrderSetting(orderSetting);
            } else {
                orderSettingMapper.addOrderSetting(orderSetting);
            }
        }

    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {

        List<OrderSetting> orderSettingByMonth = orderSettingMapper.getOrderSettingByMonth(date);
        ArrayList<Map> orderSettingList = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingByMonth) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", orderSetting.getOrderDate().getDate());
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            orderSettingList.add(map);
        }
        return orderSettingList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Long count = orderSettingMapper.queryOrderSettingByDate(orderSetting.getOrderDate());
        if (count > 0) {
            Integer registeredNumber = orderSettingMapper.queryNumberByDate(orderSetting.getOrderDate());
            //修改人数为0就删除活动
            if (orderSetting.getNumber() == 0 && registeredNumber == 0) {

                orderSettingMapper.deleteOrderSetting(orderSetting.getOrderDate());
            } else {
                //已报名人数大于想修改的人数就不能修改
                if (registeredNumber > orderSetting.getNumber()) {
                    throw new RuntimeException("已注册人数大于想要修改的值");
                } else {
                    orderSettingMapper.editOrderSetting(orderSetting);
                }
            }

        } else {
            orderSettingMapper.addOrderSetting(orderSetting);
        }
    }
}
