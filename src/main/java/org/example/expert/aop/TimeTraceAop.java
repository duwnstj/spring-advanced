package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Slf4j
@Aspect
@Component
public class TimeTraceAop {

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*deleteComment(..))")
    private void commentAdmin() {
    }

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*changeUserRole(..))")
    private void userAdmin() {
    }

    @Around("commentAdmin()")
    public Object timeTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Long userId = (Long) req.getAttribute("userId");
        String url = req.getRequestURI();

        try {

            return joinPoint.proceed();
        } finally {

            log.info("::: 요청한 사용자의 ID : {} :::", userId);
            log.info("::: 요청한 시간 : {}Ms:::", startTime);
            log.info("::: API 요청 URL : {}:::", url);

        }
    }
}
