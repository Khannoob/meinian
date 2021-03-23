package com.sysu.mapper;

import com.github.pagehelper.Page;
import com.sysu.pojo.TravelGroup;

import java.util.List;
import java.util.Map;

public interface TravelGroupMapper {

    void add(TravelGroup travelGroup);

    void setTravelGroupAndTravelItem(Map map);


    Page<TravelGroup> findPage(String queryString);

    Long queryTravelGroupAndTravelItem(Integer id);

    void delete(Integer id);

    TravelGroup findByTravelGroupId(Integer id);

    List<Integer> findTravelItemIdByTravelGroupId(Integer id);

    void update(TravelGroup travelGroup);

    void deleteTravelGroupAndTravelItemIds(Integer id);

    List<TravelGroup> findAll();
    List<TravelGroup> findTravelGroupListBySetMealId(Integer id);
}
