package com.project1.global.aop;

import com.project1.domain.member.service.Layer2.MemberVerificationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AdminAspect {
    private final MemberVerificationService memberVerificationService;

    public AdminAspect(MemberVerificationService memberVerificationService) {
        this.memberVerificationService = memberVerificationService;
    }

    @Pointcut("execution(* com.project1.domain.admin.service.*.*(..))")
    private void admin(){}

    @Before("admin()")
    public void verifyAdmin(){
        memberVerificationService.verifyAdmin();
    }
}
