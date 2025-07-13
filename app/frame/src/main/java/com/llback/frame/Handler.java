package com.llback.frame;

public interface Handler<R, Q> {
    /**
     * 访问
     *
     * @param req
     * @return
     */
    R execute(Q req);
}
