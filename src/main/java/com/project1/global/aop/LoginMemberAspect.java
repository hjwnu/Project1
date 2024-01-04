package com.project1.global.aop;

import com.project1.domain.member.service.Layer2.MemberVerifyService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
@Deprecated
@Aspect
public class LoginMemberAspect {
    private final MemberVerifyService memberVerifyService;

    public LoginMemberAspect(MemberVerifyService memberVerifyService) {
        this.memberVerifyService = memberVerifyService;
    }
    @Pointcut("execution(* com.mainproject.be28.domain..*creat*(..))")
    private void member(){}

    @Before("member()")
    public void verifySameMember(JoinPoint joinPoint) throws  Throwable{
//        long tokenMemberId = memberVerifyService.findTokenMember();
    }

}
