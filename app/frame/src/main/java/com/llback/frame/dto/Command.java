package com.llback.frame.dto;

public interface Command {

    /**
     * 获取命令id
     *
     * @return command id
     */
    default String cmdId() {
        return this.getClass().getName();
    }

}
