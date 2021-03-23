package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.constant.RedisConstant;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.entity.Result;
import com.sysu.pojo.OrderSetting;
import com.sysu.pojo.SetMeal;
import com.sysu.service.OrderSettingService;
import com.sysu.service.SetMealService;
import com.sysu.utils.POIUtils;
import com.sysu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.*;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("ordersetting")
public class OrderSettingController {
    @Reference
    OrderSettingService orderSettingService;
    @RequestMapping("upload")
    public Result upload(MultipartFile excelFile) throws Exception {

        List<String[]> strings = POIUtils.readExcel(excelFile);
        List<OrderSetting> orderSettingList = new ArrayList<>();
        for (String[] string : strings) {
            OrderSetting orderSetting = new OrderSetting();
            orderSetting.setOrderDate(new Date(string[0]));
            orderSetting.setNumber(Integer.parseInt(string[1]));
            orderSettingList.add(orderSetting);
        }
        try {
            orderSettingService.add(orderSettingList);
//            orderSettingService.add(excelFile);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.UPLOAD_SUCCESS);
    }
    @GetMapping("getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> orderSettingList = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderSettingList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
    @PostMapping("editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
        } catch (Exception e) {
            e.printStackTrace();
        return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS);
    }

}
