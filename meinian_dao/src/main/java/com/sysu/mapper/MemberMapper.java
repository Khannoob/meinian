package com.sysu.mapper;

import com.sysu.pojo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {

    Member findMemberByTelephone(@Param("telephone") String telephone);

    void addMember(@Param("member") Member member);

    Integer queryMemberByMonth(@Param("lastDayOfMonth") String lastDayOfMonth);

    int getTodayNewMember(@Param("today") String today);

    int getTotalMember();

    int getThisWeekAndMonthNewMember(@Param("firstDay") String firstDay);
}
