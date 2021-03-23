package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.entity.Result;
import com.sysu.pojo.TravelItem;
import com.sysu.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("travelItem")
public class TravelItemController {
    @Reference
    TravelItemService travelItemService;

    @PostMapping("add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")
    public Result add(@RequestBody TravelItem travelItem){
        try {
            travelItemService.add(travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return travelItemService.findPage(queryPageBean);
    }

    @GetMapping("delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE')")
    public Result delete(Integer id){
        try {
            travelItemService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }
    @GetMapping("queryOneItemById")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    public Result queryOneItemById(Integer id){
        TravelItem travelItem = null;
        try {
            travelItem = travelItemService.queryOneItemById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
    }
    @PostMapping("edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")
    public Result edit(@RequestBody TravelItem travelItem){
        try {
            travelItemService.edit(travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }
    @GetMapping("findAll")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    public Result findAll(){
        try {
            List<TravelItem> items = travelItemService.findAll();
            return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS,items);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }
}
