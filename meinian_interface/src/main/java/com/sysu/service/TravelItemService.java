package com.sysu.service;

import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.pojo.TravelItem;

import java.util.List;

public interface TravelItemService {
    public void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    TravelItem queryOneItemById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
