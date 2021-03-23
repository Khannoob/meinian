package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.constant.RedisConstant;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.entity.Result;
import com.sysu.pojo.SetMeal;
import com.sysu.service.SetMealService;
import com.sysu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("setmeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;
    @Autowired
    JedisPool jedisPool;

    @PostMapping("getSetmeal")
    public Result getSetmeal(){
        List<SetMeal> setMealList = null;
        try {
            setMealList = setMealService.getSetmeal();
        } catch (Exception e) {
            e.printStackTrace();
        return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,setMealList);
    }
    @PostMapping("findById")
    public Result findById(Integer id){
        try {
            SetMeal setMeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setMeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @GetMapping("getSetMealById")
    public Result getSetMealById(Integer id){
        SetMeal setMeal = setMealService.getSetMealById(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setMeal);
    }

}
