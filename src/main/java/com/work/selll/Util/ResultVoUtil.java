package com.work.selll.Util;

import com.work.selll.VO.ResultVo;

public class ResultVoUtil {
   public final static ResultVo success(Object o)
    {
        ResultVo resultVo =new ResultVo();
        resultVo.setData(o);
        resultVo.setCode(0);
        resultVo.setMessage("成功");
        return  resultVo;
    }
    public final static ResultVo success()
    {

        return  success(null);
    }
    public final static ResultVo success(Integer code,String message)
    {
        ResultVo resultVo =new ResultVo();
        resultVo.setData(null);
        resultVo.setCode(code);
        resultVo.setMessage(message);
        return  resultVo;
    }
    public final static ResultVo fail(Integer code,String msg)
    {
        ResultVo resultVo =new ResultVo();
        resultVo.setCode(code);
        resultVo.setMessage("msg");
        return  resultVo;
    }
}
