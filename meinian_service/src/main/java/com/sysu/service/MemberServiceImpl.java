package com.sysu.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sysu.mapper.MemberMapper;
import com.sysu.pojo.Member;
import com.sysu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public Member checkMember(String telephone) {
        return memberMapper.findMemberByTelephone(telephone);
    }

    @Override
    public void addMember(Member member) {
        memberMapper.addMember(member);
    }


}
