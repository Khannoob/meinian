package com.sysu.mapper;

import com.github.pagehelper.Page;
import com.sysu.pojo.Address;
import com.sysu.pojo.Member;
import com.sysu.pojo.TravelGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {

    List<Address> findAllMaps();

    Page<Address> findPage(String queryString);

    void addAddress(Address address);

    void deleteById(Integer id);
}
