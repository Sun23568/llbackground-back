package com.llback.api.app.permission.handler;

import com.github.pagehelper.PageInfo;
import com.llback.api.app.permission.dto.PermDto;
import com.llback.api.app.permission.dto.req.QueryPermissionReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.core.perm.eo.FuncPermEo;
import com.llback.core.perm.repository.PermRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import com.llback.frame.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 权限分页查询处理器
 */
@Component
@HandlerAcl("perm:query")
public class QueryPermissionHandler implements Handler<PageResult<PermDto>, QueryPermissionReq> {
    /**
     * 权限仓储
     */
    @Autowired
    private PermRepository permRepository;

    @Override
    public PageResult<PermDto> execute(QueryPermissionReq req) {
        PageInfo<FuncPermEo> permEoPageInfo = permRepository.queryPermPage(req.getPageIndex(), req.getPageSize(), req.getPermType());
        return DtoEoAssemblerUtil.eoPageToDtoResult(permEoPageInfo, PermDto.class);
    }
}
