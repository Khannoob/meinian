package com.sysu.job;

import com.sysu.constant.RedisConstant;
import com.sysu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

public class ClearImgJob {
        @Autowired
        JedisPool jedisPool;
    public void clearImg(){
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String deleteName = iterator.next();
            System.out.println("删除的图片id:"+deleteName);
            QiniuUtils.deleteFileFromQiniu(deleteName);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, deleteName);
        }
    }
}
