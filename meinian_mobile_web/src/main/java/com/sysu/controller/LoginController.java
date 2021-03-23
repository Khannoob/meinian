package com.sysu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sysu.constant.MessageConstant;
import com.sysu.constant.RedisMessageConstant;
import com.sysu.entity.Result;
import com.sysu.pojo.Member;
import com.sysu.service.MemberService;
import com.sysu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController //作用 : 包含两个注解 @ResponseBody  @Controller
@RequestMapping("login")
public class LoginController {
    @Reference
    MemberService memberService;
    @Autowired
    JedisPool jedisPool;

    @PostMapping("check")
    //使用map要加requestBody
    public Result check(@RequestBody Map map, HttpServletResponse response){

        String telephone = map.get("telephone").toString();
        String validateCode = map.get("validateCode").toString();
        String RedisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        //        1、校验用户输入的短信验证码是否正确，如果验证码错误则登录失败
        if (RedisCode!=null&&RedisCode.equals(validateCode)){
            //        2、如果验证码正确，则判断当前用户是否为会员，如果不是会员则自动完成会员注册
            Member member = memberService.checkMember(telephone);
            if (member==null){
                member = new Member();
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberService.addMember(member);
            }
            //        3、向客户端写入Cookie，内容为用户手机号
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            //设置有效的路径
            cookie.setPath("/");
            //设置存活时间
            cookie.setMaxAge(60*60*24*30);
            //添加cookie到客户端
            response.addCookie(cookie);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }
}
