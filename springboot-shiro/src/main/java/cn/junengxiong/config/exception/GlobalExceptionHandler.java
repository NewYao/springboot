package cn.junengxiong.config.exception;


import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.junengxiong.bean.ReturnMap;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ReturnMap handleException(Exception e){
        LOGGER.error("系统处理失败！", e);
        LOGGER.error(e.getMessage(), e);
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
        LOGGER.error("自定义处理失败！", e);
        LOGGER.error(e.getMessage(), e);
        return new ReturnMap().fail().message(e.getMessage());
    }

  
    
    // 捕捉shiro的异常
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public ReturnMap handle401() {
        return new ReturnMap().invalid().message("您没有权限访问！");
    }
    
    

}
