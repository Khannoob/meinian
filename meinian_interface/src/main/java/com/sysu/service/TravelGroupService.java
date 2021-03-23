package com.sysu.service;

import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.pojo.TravelGroup;
import com.sysu.pojo.TravelItem;

import java.util.List;

public interface TravelGroupService {


    void add(Integer[] travelItemIds, TravelGroup travelGroup);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    TravelGroup findByTravelGroupId(Integer id);

    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    void update(Integer[] travelItemIds, TravelGroup travelGroup);

    List<TravelGroup> findAll();
}
