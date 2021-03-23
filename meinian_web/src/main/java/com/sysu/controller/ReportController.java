package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.entity.Result;
import com.sysu.service.MemberService;
import com.sysu.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("report")
public class ReportController {
    @Reference
    ReportService reportService;

    @GetMapping("getMemberReport")
    public Result getMemberReport() {
//        结果用一个Map封装 Map里面是两个list
//        list1 x轴是从现在开始之前的12个月份 months
//        list2 y轴通过月份查询到当月31日之前的总会员数 memberCount
        //根据当前时间，获取前12个月的日历(当前日历2021-03，12个月前，日历时间2020-04)
        LocalDate today = LocalDate.now();
        List<String> monthList = new ArrayList<>();
        for (long i = 0L; i <= 11L; i++) {
            LocalDate localDate = today.minusMonths(i);
//            String format = new SimpleDateFormat("yyyy-MM").format(localDate);
            String month = localDate.toString().substring(0, 7);
            monthList.add(month);
        }
        //反转集合
        Collections.reverse(monthList);
        // 把过去12个月的日期存储到map里面
        Map<String, Object> map = new HashMap<>();
        map.put("months", monthList);
        // 查询所有的会员
        List<Integer> memberList = new ArrayList<>();
        for (String month : monthList) {
            Integer count = reportService.queryMemberByMonth(month);
            memberList.add(count);
        }
        map.put("memberCount", memberList);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    @GetMapping("getSetmealReport")
    public Result getSetmealReport() {
        Map<String, Object> map = new HashMap<>();
//        List<String> setmealNames = new ArrayList<>();
//        List<Long> setmealCount = new ArrayList<>();
        List<Map> mapList = reportService.querySetmealNamesAndCount();
//        for (Map map1 : mapList) {
////            Long value = (Long) map1.get("value");
////            setmealCount.add(value);
//            String name = map1.get("name").toString();
//            setmealNames.add(name);
//        }

//        map.put("setmealNames", setmealNames);
        map.put("setmealCount", mapList);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
    }

    @GetMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> map = new HashMap<>();
        try {
            map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //getBusinessReportData获取运营数据
        Map<String, Object> result = reportService.getBusinessReportData();
        //取出数据
        String reportDate = (String) result.get("reportDate");
        Integer todayNewMember = (Integer) result.get("todayNewMember");
        Integer totalMember = (Integer) result.get("totalMember");
        Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
        Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
        Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
        Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
        Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
        Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
        //下载template模板
        String templateRealPath = request.getSession().getServletContext().getRealPath("template") +
                File.pathSeparator + "report_template.xlsx";
        //创建workbook对象XSSF
        XSSFWorkbook workBook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
        XSSFSheet sheet = workBook.getSheetAt(0);
        XSSFRow row = sheet.getRow(2);
        row.getCell(5).setCellValue(reportDate);//日期

        row = sheet.getRow(4);
        row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
        row.getCell(7).setCellValue(totalMember);//总会员数

        row = sheet.getRow(5);
        row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
        row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

        row = sheet.getRow(7);
        row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
        row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

        row = sheet.getRow(8);
        row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
        row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

        row = sheet.getRow(9);
        row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
        row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

        int rowNum = 12;
        for(Map map : hotSetmeal){//热门套餐
            String name = (String) map.get("name");
            Long setmeal_count = (Long) map.get("setmeal_count");
            BigDecimal proportion = (BigDecimal) map.get("proportion");
            row = sheet.getRow(rowNum ++);
            row.getCell(4).setCellValue(name);//套餐名称
            row.getCell(5).setCellValue(setmeal_count);//预约数量
            row.getCell(6).setCellValue(proportion.doubleValue());//占比
        }
        //创建文件的输出流
        ServletOutputStream out = response.getOutputStream();
        // 下载的数据类型（excel类型）
        response.setContentType("application/vnd.ms-excel");
        // 设置下载形式(通过附件的形式下载)
        response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
        workBook.write(out);
        out.flush();
        out.close();
        workBook.close();
        return null;
    }
}
