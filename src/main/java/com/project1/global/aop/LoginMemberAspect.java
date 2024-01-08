package com.project1.global.aop;

import com.project1.domain.member.service.Layer2.MemberVerificationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
@Deprecated
@Aspect
public class LoginMemberAspect {
    private final MemberVerificationService memberVerificationService;

    public LoginMemberAspect(MemberVerificationService memberVerificationService) {
        this.memberVerificationService = memberVerificationService;
    }
    @Pointcut("execution(* com.project1.domain..*creat*(..))")
    private void member(){}

    @Before("member()")
    public void verifySameMember(JoinPoint joinPoint) throws  Throwable{
//        long tokenMemberId = memberVerifyService.findTokenMember();
    }

}
