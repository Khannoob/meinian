package com.sysu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sysu.constant.RedisConstant;
import com.sysu.mapper.SetMealMapper;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.pojo.SetMeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    SetMealMapper SetMealMapper;
    @Autowired
    JedisPool jedisPool;
    @Override
    public void add(Integer[] travelGroupIds, SetMeal setMeal) {
        SetMealMapper.add(setMeal);
        if (travelGroupIds!=null && travelGroupIds.length>0){
            setTravelGroupAndSetMeal(travelGroupIds,setMeal.getId());
        }
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setMeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<SetMeal> page = SetMealMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public SetMeal findById(Integer id) {
        return SetMealMapper.findById(id);
    }

    @Override
    public SetMeal getSetMealById(Integer id) {
        return SetMealMapper.getSetMealById(id);
    }

    @Override
    public List<SetMeal> getSetmeal() {
        return SetMealMapper.getSetmeal();
    }

    private void setTravelGroupAndSetMeal(Integer[] travelGroupIds, Integer setMealId) {
        //使用map进行插入 key要和values后面的字段相同
        for (Integer travelGroupId : travelGroupIds) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("travelGroupId", travelGroupId);
            map.put("setMealId", setMealId);
            SetMealMapper.setTravelGroupAndSetMeal(map);
        }
    }
}
