package cn.junengxiong.config.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.junengxiong.bean.ReturnMap;



@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ReturnMap handleException(Exception e){
        return new ReturnMap().error().message("系统错误，请稍后重试！");
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    ReturnMap handleBusinessException(CustomException e){
        return new ReturnMap().fail().message(e.getMessage());
    }

  
    

}
