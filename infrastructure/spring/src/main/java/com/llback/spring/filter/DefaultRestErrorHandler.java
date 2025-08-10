package com.llback.spring.filter;

import com.llback.common.exception.BizError;
import com.llback.common.exception.BizException;
import com.llback.common.exception.ErrorCode;
import com.llback.frame.context.ReqContext;
import com.llback.frame.rest.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * css @2024
 * 异常处理类
 *
 * @author 作者hex
 * @date 2020/12/10 20:45:00
 */
public class DefaultRestErrorHandler implements RestApiExceptionHandler {

    /**
     * logger
     */
    static Logger log = LoggerFactory.getLogger(DefaultRestErrorHandler.class);

    /**
     * handleArgException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    @Override
    public RestResult<Object> handleMethodArgException(MethodArgumentNotValidException e, HttpServletRequest request,
                                                       HttpServletResponse response) {
        log.warn("{}", request.getRequestURL(), e);
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(item -> item.getField() + " is required")
                .collect(Collectors.joining(", "));
        response.setStatus(ErrorCode.INVALID_PARAM.getHttpCode());
        return RestResult.failed(ErrorCode.INVALID_PARAM, message);
    }


    /**
     * handleIllegaArgException
     *
     * @param e       e
     * @param request request
     * @return RestResult<Object>
     */
    @Override
    public RestResult<Object> handleIllegaArgException(IllegalArgumentException e, HttpServletRequest request,
                                                       HttpServletResponse response) {
        log.warn("{}", request.getRequestURL(), e);
        response.setStatus(ErrorCode.INVALID_PARAM.getHttpCode());
        return RestResult.failed(ErrorCode.INVALID_PARAM, e.getMessage());
    }

    /**
     * handleWithErrorCode
     *
     * @param errorCode errorCode
     * @param e         e
     * @param request   request
     * @param response  response
     * @return RestResult<Object>
     */
    protected RestResult<Object> handleWithErrorCode(ErrorCode errorCode, Exception e,
                                                     HttpServletRequest request, HttpServletResponse response) {

        if (errorCode.isSysError()) {
            log.error("{}", request.getRequestURL(), e);
        } else {
            log.warn("{},[{}]-{}", request.getRequestURL(), errorCode.getCode(), e.getMessage());
        }
        response.setStatus(errorCode.getHttpCode());
        Object data = null;

        if (e instanceof BizError) {
            return RestResult.failed((BizError) e, data);
        } else {
            return RestResult.failed(errorCode, e.getMessage(), data);
        }
    }

    @Override
    public RestResult<Object> handleBizException(BizException e, HttpServletRequest request, HttpServletResponse response) {
        // 判断是否是未登录异常, 如果是则清空session
        if (e.getError() == ErrorCode.NOT_LOGIN) {
            try {
                ReqContext.clearSession();
            } catch (Exception ex) {
                log.warn("清除会话错误", ex);
            }
        }
        return this.handleWithErrorCode(e.getError(), e, request, response);
    }

    /**
     * handleValidationException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    @Override
    public RestResult<Object> handleValidationException(RuntimeException e,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        log.warn("{},{}", request.getRequestURL(), e.getMessage());
        return RestResult.failed(ErrorCode.INVALID_PARAM, e.getMessage());
    }


    /**
     * handleRuntimeException
     *
     * @param e       e
     * @param request request
     * @return RestResult<Object>
     */
    @Override
    public RestResult<Object> handleRuntimeException(RuntimeException e, HttpServletRequest request,
                                                     HttpServletResponse response) {
        log.error("{}", request.getRequestURL(), e);
        response.setStatus(ErrorCode.SERVICE_INNER_ERROR.getHttpCode());
        return RestResult.failed(ErrorCode.SERVICE_INNER_ERROR);
    }

    /**
     * handleException
     *
     * @param e       e
     * @param request request
     * @return RestResult<Object>
     */
    @Override
    public RestResult<Object> handleException(Exception e, HttpServletRequest request,
                                              HttpServletResponse response) {
        log.error("{}", request.getRequestURL(), e);
        response.setStatus(ErrorCode.SERVICE_INNER_ERROR.getHttpCode());
        return RestResult.failed(ErrorCode.SERVICE_INNER_ERROR);
    }

    /**
     * handleError
     *
     * @param e       e
     * @param request request
     * @return RestResult<Object>
     */
    @Override
    public RestResult<Object> handleError(Error e, HttpServletRequest request,
                                          HttpServletResponse response) {
        log.error("{}", request.getRequestURL(), e);
        response.setStatus(ErrorCode.SERVICE_INNER_ERROR.getHttpCode());
        return RestResult.failed(ErrorCode.SERVICE_INNER_ERROR);
    }

    /**
     * handleThrowable
     *
     * @param e       e
     * @param request request
     * @return RestResult<Object>
     */
    @Override
    public RestResult<Object> handleThrowable(Throwable e, HttpServletRequest request,
                                              HttpServletResponse response) {
        log.error("{}", request.getRequestURL(), e);
        response.setStatus(ErrorCode.SERVICE_INNER_ERROR.getHttpCode());
        return RestResult.failed(ErrorCode.SERVICE_INNER_ERROR);
    }
}
