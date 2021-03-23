package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.constant.RedisMessageConstant;
import com.sysu.entity.Result;
import com.sysu.service.OrderService;
import com.sysu.utils.SMSUtils;
import com.sysu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("validateCode")
public class validateCodeController {
    @Reference
    OrderService orderService;
    @Autowired
    JedisPool jedisPool;
    @GetMapping("send4Order")
    public Result send4Order(String telephone) {
        try {
            String validateCode = ValidateCodeUtils.generateValidateCode(6).toString();
            SMSUtils.sendShortMessage(telephone,validateCode);
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER, 60*10, validateCode);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
    @PostMapping("send4Login")
    public Result send4Login(String telephone){
        try {
            String validateCode = ValidateCodeUtils.generateValidateCode(6).toString();
            SMSUtils.sendShortMessage(telephone, validateCode);
            jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN, 60*10, validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
