package com.sysu.mapper;

import com.sysu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {

    Long queryOrderSettingByDate(Date orderDate);

    void editOrderSetting(OrderSetting orderSetting);

    void addOrderSetting(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(String date);

    Integer queryNumberByDate(Date orderDate);

    void deleteOrderSetting(Date orderDate);

    OrderSetting findOrderSettingByDate(Date orderDate);
}
