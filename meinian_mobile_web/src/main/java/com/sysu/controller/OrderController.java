package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.constant.RedisMessageConstant;
import com.sysu.entity.Result;
import com.sysu.pojo.SetMeal;
import com.sysu.service.OrderService;
import com.sysu.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("order")
public class OrderController {
    @Reference
    OrderService orderService;
    @Autowired
    JedisPool jedisPool;

    @PostMapping("submit")
    public Result submit(@RequestBody Map map) throws Exception {
      String telephone = (String) map.get("telephone");
      String validateCode = (String) map.get("validateCode");
      String redisCode = jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
      if (validateCode.equals(redisCode)){
          Result result = orderService.submit(map);
          return result;
      }else {
          return new Result(false, MessageConstant.VALIDATECODE_ERROR);
      }
    }
    @PostMapping("findById")
    public Result findById(Integer id) throws Exception {
        Map map = orderService.findById(id);
        return new Result(true,  MessageConstant.GET_ORDERSETTING_SUCCESS,map);
    }
}
