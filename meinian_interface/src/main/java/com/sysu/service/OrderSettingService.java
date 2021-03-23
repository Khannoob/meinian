package com.sysu.service;

import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.pojo.OrderSetting;
import com.sysu.pojo.SetMeal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> orderSettingList);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);


//    void add(MultipartFile excelFile) throws Exception;
}
