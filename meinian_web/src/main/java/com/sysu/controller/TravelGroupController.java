package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.entity.Result;
import com.sysu.pojo.TravelGroup;
import com.sysu.service.TravelGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("travelGroup")
public class TravelGroupController {
    @Reference
    TravelGroupService travelGroupService;

    @PostMapping("add")
    public Result add(Integer[] travelItemIds, @RequestBody TravelGroup travelGroup){
        try {
            //controller只处理请求不做业务
            travelGroupService.add(travelItemIds,travelGroup);
            return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelGroupService.findPage(queryPageBean);
        return pageResult;
    }
    @GetMapping("delete")
    public Result delete(Integer id){
        try {
            travelGroupService.delete(id);
            return new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }
    }
    @GetMapping("findByTravelGroupId")
    public Result findByTravelGroupId(Integer id){
        TravelGroup travelGroup = null;
        try {
            travelGroup = travelGroupService.findByTravelGroupId(id);
            return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }

    }
    @GetMapping("findTravelItemIdByTravelGroupId")
    public Result findTravelItemIdByTravelGroupId(Integer id){
        try {
            List<Integer> Ids = travelGroupService.findTravelItemIdByTravelGroupId(id);
            return  new Result(true, "",Ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "");
        }
    }
    @PostMapping("update")
    public Result update(Integer[] travelItemIds,@RequestBody TravelGroup travelGroup){
        try {
            travelGroupService.update(travelItemIds,travelGroup);
            return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }

    }
    @GetMapping("findAll")
    public Result findAll(){
        try {
            List<TravelGroup> travelGroupList = travelGroupService.findAll();
            return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }
}
