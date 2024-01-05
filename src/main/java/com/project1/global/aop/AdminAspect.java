package com.project1.global.aop;

import com.project1.domain.member.service.Layer2.MemberVerifyService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AdminAspect {
    private final MemberVerifyService memberVerifyService;

    public AdminAspect(MemberVerifyService memberVerifyService) {
        this.memberVerifyService = memberVerifyService;
    }

    @Pointcut("execution(* com.project1.domain.admin.service.*.*(..))")
    private void admin(){}

    @Before("admin()")
    public void verifyAdmin(JoinPoint joinPoint) throws  Throwable{
        memberVerifyService.verifyAdmin();
    }
}