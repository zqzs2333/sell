package com.work.selll.Util;

import com.work.selll.config.RedisConfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void set(HttpServletResponse response,String name,String value,Integer max)
    {
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(max);
        response.addCookie(cookie);
    }
    public static Cookie get(HttpServletRequest request,
                           String name)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies.length>0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name))
                {
                    return cookie;
                }
            }
        }
       return null;
    }

}
