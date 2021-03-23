package com.sysu.service;

import java.util.List;
import java.util.Map;

public interface ReportService {
    Integer queryMemberByMonth(String month);

    List<Map> querySetmealNamesAndCount();

    Map<String, Object> getBusinessReportData();
}
