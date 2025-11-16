package com.llback.api.app.ai.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 模型聊天参数
 */
@Data
public class ModelChat implements Query {
    /**
     * 聊天内容
     */
    private String message;

    /**
     * 上下文
     */
    private List<String> context;

    /**
     * 模型
     */
    private String model;

    /**
     * 响应
     */
    private HttpServletResponse response;

    public ModelChat setServlet(HttpServletResponse response) {
        this.response = response;
        return this;
    }
}
