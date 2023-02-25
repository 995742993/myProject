package com.xwz.exception;

import com.xwz.common.BaseResponse;
import com.xwz.common.ErrorCode;
import com.xwz.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 对全局异常进行捕获并处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse RuntimeExceptionHandler(RuntimeException e){
        log.info("RuntimeException",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }

    @ExceptionHandler(BusinessException.class)
    public BaseResponse BusinessExceptionHandler(BusinessException e){
        log.info("BusinessException:"+e.getDescription(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }
}
