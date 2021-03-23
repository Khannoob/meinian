package com.sysu.service;

import com.sysu.entity.Result;

import java.util.Map;

public interface OrderService {
    Result submit(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
