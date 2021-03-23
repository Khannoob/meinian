package com.sysu.service;

import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.pojo.SetMeal;
import com.sysu.pojo.TravelGroup;

import java.util.List;

public interface SetMealService {

    void add(Integer[] travelGroupIds, SetMeal setMeal);

    PageResult findPage(QueryPageBean queryPageBean);

    List<SetMeal> getSetmeal();

    SetMeal findById(Integer id);

    SetMeal getSetMealById(Integer id);
}
