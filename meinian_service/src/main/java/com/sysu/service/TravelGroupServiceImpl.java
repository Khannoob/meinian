package com.sysu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sysu.mapper.TravelGroupMapper;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.pojo.TravelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    TravelGroupMapper travelGroupMapper;

    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        //1.先给组团游表插入
        travelGroupMapper.add(travelGroup);
        //2.给联合表插入 如果有外键联系的话
        if (travelItemIds != null && travelItemIds.length != 0) {
            setGroupAndItem(travelItemIds, travelGroup.getId());
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<TravelGroup> page = travelGroupMapper.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        Long count = travelGroupMapper.queryTravelGroupAndTravelItem(id);
        if (count>0){
            throw new RuntimeException("绑定了自由行无法删除!");
        }else {
            travelGroupMapper.delete(id);
        }
    }

    @Override
    public TravelGroup findByTravelGroupId(Integer id) {
        return travelGroupMapper.findByTravelGroupId(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelGroupId(Integer id) {
        return travelGroupMapper.findTravelItemIdByTravelGroupId(id);
    }

    @Override
    public void update(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupMapper.update(travelGroup);
        //先删再加
        travelGroupMapper.deleteTravelGroupAndTravelItemIds(travelGroup.getId());
        setGroupAndItem(travelItemIds, travelGroup.getId());
    }

    @Override
    public List<TravelGroup> findAll() {
        return travelGroupMapper.findAll();
    }

    private void setGroupAndItem(Integer[] travelItemIds, Integer travelGroupId) {
        //使用map进行插入 key是列名
        for (Integer travelItemId : travelItemIds) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("travelGroupId", travelGroupId);
            map.put("travelItemId", travelItemId);
            travelGroupMapper.setTravelGroupAndTravelItem(map);
        }
    }
}
