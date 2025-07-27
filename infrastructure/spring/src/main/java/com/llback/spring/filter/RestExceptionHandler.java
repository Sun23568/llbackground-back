package com.llback.spring.filter;

import com.llback.common.exception.BizException;
import com.llback.frame.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.llback.common.exception.ErrorCode.GENERIC_BIZ_WARN;
import static com.llback.common.exception.ErrorCode.LOGIN_USER_VERIFY_FAILED;

/**
 * css @2024
 * 异常处理类
 *
 * @author 作者hex
 * @date 2020/7/1 10:59 上午
 */
@Slf4j
@RestControllerAdvice
final class RestExceptionHandler {
    /**
     * handleBizException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    @ExceptionHandler({BizException.class})
    public RestResult<Object> handleBizException(BizException e, HttpServletRequest request, HttpServletResponse response) {
        return RestResult.failed(GENERIC_BIZ_WARN, "业务异常");
    }

    /**
     * handleBizException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    @ExceptionHandler({Exception.class})
    public RestResult<Object> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        return RestResult.failed(GENERIC_BIZ_WARN, "业务异常");
    }
}
