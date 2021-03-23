package com.sysu.service;

import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.entity.Result;
import com.sysu.pojo.Address;
import com.sysu.pojo.Member;

import java.util.List;

public interface AddressService {


    List<Address> findAllMaps();

    PageResult findPage(QueryPageBean queryPageBean);

    void addAddress(Address address);

    void deleteById(Integer id);
}
