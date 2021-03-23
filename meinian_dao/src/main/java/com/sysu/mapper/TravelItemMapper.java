package com.sysu.mapper;

import com.sysu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemMapper {
    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    Long selectTravelGroup_TravelItemByTravelItemId(Integer id);

    void delete(Integer id);

    TravelItem queryOneItemById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
    List<TravelItem> findTravelItemListByTravelGroupId(Integer id);
}
