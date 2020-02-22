package com.work.selll.handle;

import com.work.selll.Util.ResultVoUtil;
import com.work.selll.VO.ResultVo;
import com.work.selll.exception.SellerAuthorizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class SellExceptionHandler {
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ResultVo handlerSellException()
    {
        return ResultVoUtil.fail(103,"没有权限请登入");
    }

}
