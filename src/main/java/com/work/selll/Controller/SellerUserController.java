package com.work.selll.Controller;

import com.work.selll.Util.CookieUtil;
import com.work.selll.Util.ResultVoUtil;
import com.work.selll.VO.ResultVo;
import com.work.selll.bean.SellerInfo;
import com.work.selll.config.RedisConfig;
import com.work.selll.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/login")
    public ResultVo login(@RequestParam(value = "openid") String openid,
                          HttpServletResponse response)
    {

        //1:openid是否与数据库中的一致
        SellerInfo sellerInfo = sellerService.findByOpenid(openid);
        if (sellerInfo == null)
        {
            return ResultVoUtil.fail(101,"无法登入，数据库中没有该用户的openid");
        }
        else {

            //3:redis
            String token= UUID.randomUUID().toString();
            Integer max= RedisConfig.max;
            stringRedisTemplate.opsForValue().set(String.format(RedisConfig.token,token),openid,max, TimeUnit.SECONDS);
            //2:cookie
            CookieUtil.set(response,"token",token,RedisConfig.max);
//            Cookie cookie = new Cookie("token",token);
//            cookie.setPath("/");
//            cookie.setMaxAge(RedisConfig.max);
//            response.addCookie(cookie);
        }

  return ResultVoUtil.success();
    }
    @GetMapping("/logout")
    public ResultVo logout(HttpServletResponse response,
                         HttpServletRequest request)
    {
        Cookie cookie = CookieUtil.get(request, "token");
        if (cookie != null)
        {
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConfig.token,cookie.getValue()));
            CookieUtil.set(response,"token",null,0);
        }


        return ResultVoUtil.success(0,"登出成功");
    }
}
