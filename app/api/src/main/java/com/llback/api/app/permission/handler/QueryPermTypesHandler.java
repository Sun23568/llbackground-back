package com.llback.api.app.permission.handler;

import com.llback.api.app.permission.dto.req.QueryPermTypesReq;
import com.llback.core.perm.eo.FuncPermEo;
import com.llback.core.perm.repository.PermRepository;
import com.llback.frame.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询权限类型列表处理器
 */
@Component
public class QueryPermTypesHandler implements Handler<List<String>, QueryPermTypesReq> {
    /**
     * 权限仓储
     */
    @Autowired
    private PermRepository permRepository;

    @Override
    public List<String> execute(QueryPermTypesReq req) {
        List<FuncPermEo> allPerms = permRepository.getAllPermission();
        return allPerms.stream()
                .map(FuncPermEo::getType)
                .filter(type -> type != null && !type.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
