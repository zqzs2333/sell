package com.work.selll.aspec;

import com.work.selll.Util.CookieUtil;
import com.work.selll.config.RedisConfig;
import com.work.selll.exception.SellerAuthorizeException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SellerAuthorizeAspec {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Pointcut("execution(public * com.work.selll.Controller.Seller*.*(..))" +
    "&& !execution(public * com.work.selll.Controller.SellerUserController.*(..))")
    public void veriry(){}

    @Before("veriry()")
    public void doVerify(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie cookie = CookieUtil.get(request, "token");
        if (cookie == null) {
            throw new SellerAuthorizeException();
        }
        String s = redisTemplate.opsForValue().get(String.format(RedisConfig.token, cookie.getValue()));
        if (StringUtils.isEmpty(s)) {
            throw new  SellerAuthorizeException();
        }

    }

}
