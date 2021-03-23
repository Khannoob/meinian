package com.sysu.service;

import com.sysu.entity.Result;
import com.sysu.pojo.Member;

import java.util.Map;

public interface MemberService {

    Member checkMember(String telephone);

    void addMember(Member member);

}
