package com.llback.spring.filter;

import com.llback.common.exception.BizException;
import com.llback.frame.rest.RestResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * css @2024
 *
 * @author L0912a
 * @date 2020/12/10
 */
public interface RestApiExceptionHandler {

    /**
     * handleMethodArgException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    RestResult<Object> handleMethodArgException(MethodArgumentNotValidException e,
                                                HttpServletRequest request,
                                                HttpServletResponse response);


    /**
     * handleIllegaArgException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    RestResult<Object> handleIllegaArgException(IllegalArgumentException e,
                                                HttpServletRequest request,
                                                HttpServletResponse response);

    /**
     * handleBizException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    RestResult<Object> handleBizException(BizException e, HttpServletRequest request, HttpServletResponse response);

    /**
     * handleRuntimeException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    RestResult<Object> handleValidationException(RuntimeException e, HttpServletRequest request, HttpServletResponse response);


    /**
     * handleRuntimeException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    RestResult<Object> handleRuntimeException(RuntimeException e, HttpServletRequest request, HttpServletResponse response);

    /**
     * handleException
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    RestResult<Object> handleException(Exception e, HttpServletRequest request, HttpServletResponse response);

    /**
     * handleError
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    RestResult<Object> handleError(Error e, HttpServletRequest request, HttpServletResponse response);

    /**
     * handleThrowable
     *
     * @param e        e
     * @param request  request
     * @param response response
     * @return RestResult<Object>
     */
    RestResult<Object> handleThrowable(Throwable e, HttpServletRequest request, HttpServletResponse response);
}
