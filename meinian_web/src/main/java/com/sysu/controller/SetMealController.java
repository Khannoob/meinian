package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.constant.RedisConstant;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.entity.Result;
import com.sysu.pojo.SetMeal;
import com.sysu.pojo.TravelGroup;
import com.sysu.service.SetMealService;
import com.sysu.service.TravelGroupService;
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
@RequestMapping("setMeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;
    @Autowired
    JedisPool jedisPool;

    @PostMapping("add")
    public Result add(Integer[] travelgroupIds,@RequestBody SetMeal setMeal){
        try {
            setMealService.add(travelgroupIds,setMeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
            return setMealService.findPage(queryPageBean);
    }
    @PostMapping("upload")
    public Result upload(MultipartFile imgFile) throws IOException {
        try {
            //获取本来文件名
            String originalFilename = imgFile.getOriginalFilename();
            assert originalFilename != null;
            //截取文件后缀
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            //生成随机新文件name
            String fileName = UUID.randomUUID().toString()+suffixName;
            //上传到七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //保存到Redis中
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            jedis.close();
            //把文件名返回到前端页面
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }
}
