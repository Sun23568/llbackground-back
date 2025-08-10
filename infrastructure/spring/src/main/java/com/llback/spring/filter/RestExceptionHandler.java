package com.llback.spring.filter;

import com.llback.common.exception.BizException;
import com.llback.common.exception.NotLoginException;
import com.llback.frame.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static com.llback.common.exception.ErrorCode.*;

/**
 * css @2024
 * 异常处理类
 *
 * @author 作者hex
 * @date 2020/7/1 10:59 上午
 */
@Slf4j
@RestControllerAdvice
final class RestExceptionHandler implements ApplicationContextAware{

    /**
     * webErrorHandler
     */
    private RestApiExceptionHandler webErrorHandler;

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
        return this.webErrorHandler.handleBizException(e, request, response);
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RestApiExceptionHandler> beansOfType =
                applicationContext.getBeansOfType(RestApiExceptionHandler.class);
        if (beansOfType.isEmpty()) {
            this.webErrorHandler = new DefaultRestErrorHandler();
        } else {
            this.webErrorHandler = beansOfType.values().iterator().next();
        }
    }
}
