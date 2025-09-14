package com.llback.api.app.user.handler;

import com.github.pagehelper.PageInfo;
import com.llback.api.app.user.dto.UserDto;
import com.llback.api.app.user.dto.req.QueryUserListReq;
import com.llback.api.util.DtoEoAssemblerUtil;
import com.llback.base.user.eo.UserEo;
import com.llback.base.user.repository.UserRepository;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import com.llback.frame.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询用户列表
 */
@Component
@HandlerAcl("user:queryUserList")
public class QueryUserListHandler implements Handler<PageResult<UserDto>, QueryUserListReq> {
    /**
     * 用户查询仓库
     */
    @Autowired
    private UserRepository userRepository;

    @Override
    public PageResult<UserDto> execute(QueryUserListReq req) {
        PageInfo<UserEo> users = userRepository.findUsers(req.getPageIndex(), req.getPageSize());
        return DtoEoAssemblerUtil.eoPageToDtoResult(users, UserDto.class);
    }
}
