package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.entity.PageResult;
import com.sysu.entity.QueryPageBean;
import com.sysu.entity.Result;
import com.sysu.pojo.Address;
import com.sysu.service.AddressService;
import com.sysu.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("address")
public class AddressController {
    @Reference
    AddressService addressService;
    @RequestMapping("findAllMaps")
    public Result findAllMaps() {
        List<Address> addressList = null;
        try {
            addressList = addressService.findAllMaps();
            return new Result(true, "查询分店地址成功", addressList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询分店地址失败");
        }
    }
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return addressService.findPage(queryPageBean);
    }
    @RequestMapping("addAddress")
    public Result addAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return new Result(true, "新增地址成功");
    }
    @RequestMapping("deleteById")
    public Result deleteById(Integer id){
        addressService.deleteById(id);
        return new Result(true, "删除成功");
    }
}
