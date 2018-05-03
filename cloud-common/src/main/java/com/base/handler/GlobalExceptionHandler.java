package com.base.handler;

import com.base.bean.ResultBean;
import com.base.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanzhenxing
 * @create 2018/4/29 9:59 AM
 */
@ControllerAdvice("com")
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResultBean<String> baseExceptionHandler(HttpServletResponse response, BaseException ex){
        log.error(ex.getMessage(),ex);
        response.setStatus(500);
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setData(ex.getMessage());
        resultBean.setMsg(ex.getMessage());
        resultBean.setCode(ResultBean.FAIL);
        return resultBean;
    }

    @ExceptionHandler(Exception.class)
    public ResultBean<String> otherExceptionHandler(HttpServletResponse response,Exception ex){
        response.setStatus(500);
        log.error(ex.getMessage(),ex);
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setCode(ResultBean.FAIL);
        resultBean.setMsg(ex.getMessage());
        resultBean.setData(ex.getMessage());
        return resultBean;
    }

}
