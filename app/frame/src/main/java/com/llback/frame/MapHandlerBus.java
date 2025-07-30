package com.llback.frame;

import com.llback.common.util.AssertUtil;
import com.llback.frame.context.ReqContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class MapHandlerBus implements HandlerBus {

    /**
     * 处理器Map
     */
    private final ConcurrentMap<Class<?>, HandlerInfo> handlerMap = new ConcurrentHashMap<>();


    @Override
    public void add(Handler<?, ?> handler) {
        Type[] genericInterfaces = handler.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (!(genericInterface instanceof ParameterizedType)) {
                continue;
            }
            ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
            Type rawType = parameterizedType.getRawType();

            if (Handler.class.equals(rawType) || Handler.class.isAssignableFrom((Class<?>) rawType)) {
                Class<?> genericParamType = getGenericParamType(genericInterface, handler.getClass(), 1);
                handlerMap.put(genericParamType, HandlerInfo.of(genericParamType, handler));
                return;
            }
        }

        throw new IllegalArgumentException("not found generic interface: from " + handler);
    }


    /**
     * 从泛型中获取指定位置的参数类型
     *
     * @param genType 泛型类型
     * @param clazz   目标Class，仅用于异常信息显示
     * @param index   index
     * @return Class
     */
    private static Class<?> getGenericParamType(Type genType, final Class<?> clazz, int index) {
        AssertUtil.assertTrue(genType instanceof ParameterizedType, String.format("Warn: %s's superclass not " + "ParameterizedType", clazz));

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        AssertUtil.assertTrue(index < params.length && index >= 0, String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index, clazz, params.length));

        AssertUtil.assertTrue(params[index] instanceof Class, String.format("Warn: %s not set the actual class on superclass" + " generic parameter", clazz));

        return (Class<?>) params[index];
    }

    @Override
    public void add(Class<?> reqType, Handler<?, ?> handler) {

    }

    @Override
    public <R, Q> R execute(Q req) {
        return executeHandler(req, this.handlerMap.get(req.getClass()));
    }

    /**
     * 执行
     *
     * @param req
     * @param <Q>
     * @param <R>
     * @return
     */
    private <R, Q> R executeHandler(Q req, HandlerInfo handlerInfo) {
        try (ReqContext reqContext = ReqContext.getCurrent()) {
            Handler<R, Q> handler = (Handler<R, Q>) handlerInfo.getHandler();
            HandlerPermUtil.checkPermission(handlerInfo, reqContext);
            return handler.execute(req);
        }
    }
}
