package com.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.blog.controller.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+
                joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}"+requestLog);


//        if(uri.matches("[/][b][l][o][g][/][0-9]*")){
//           updateBlogView(uri,ip);
//        }
    }
    private void updateBlogView(String uri,String ip){
        String reg = "\\D+(\\d+)$";
        Matcher matcher =  Pattern.compile(reg).matcher(uri);
        long historyHighestLevel = 1;
        if(matcher.find()){
            historyHighestLevel = Long.parseLong(matcher.group(1));
        }

    }

    @After("log()")
    public void doAfter(){
//        logger.info("-------doAfter---------");
    }

    @AfterReturning(returning = "result" ,pointcut = "log()")
    public void doAfterReturning(Object result){
        logger.info("Result : {}"+result);
    }

    @ToString
    @Data
    @AllArgsConstructor
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
















}
