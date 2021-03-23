package com.sysu.mapper;

import com.github.pagehelper.Page;
import com.sysu.pojo.SetMeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SetMealMapper {

    void add(SetMeal setMeal);

    void setTravelGroupAndSetMeal(HashMap<String, Integer> map);

    Page<SetMeal> findPage(String queryString);

    List<SetMeal> getSetmeal();

    SetMeal findById(Integer id);

    SetMeal getSetMealById(Integer id);

    List<Map> querySetmealNamesAndCount();
}
