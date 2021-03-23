package com.sysu.mapper;

import com.sysu.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper {


    Order findOrderByDateMemberIdAndSetMealId(@Param("orderDate") Date orderDate, @Param("memberId") Integer memberId, @Param("setMealId") int setMealId);

    void addOrder(Order order);

    Map findById(Integer id);

    int getTodayOrderNumber(@Param("today") String today);

    int getTodayVisitsNumber(@Param("today") String today);

    int getThisWeekAndMonthOrderNumber(Map<String, Object> param);

    int getThisWeekAndMonthVisitsNumber(Map<String, Object> paramWeekVisit);

    List<Map<String, Object>> findHotSetmeal();
}
