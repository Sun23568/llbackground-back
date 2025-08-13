package com.llback.api.app.access.handler;

import com.llback.api.app.access.dto.UserAccessDto;
import com.llback.api.app.access.dto.req.AccessListReq;
import com.llback.api.app.access.dto.resp.AccessListResp;
import com.llback.api.app.access.fetch.AccessFetch;
import com.llback.frame.Handler;
import com.llback.frame.HandlerAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 查询权限列表
 */
@Component
@HandlerAcl("access:list")
public class QueryAccessListHandler implements Handler<AccessListResp, AccessListReq> {
    /**
     * 权限查询
     */
    @Autowired
    private AccessFetch accessFetch;

    /**
     * 查询权限列表
     */
    @Override
    public AccessListResp execute(AccessListReq req) {
        List<UserAccessDto> userAccessPermList = accessFetch.queryAllUserPerms();
        List<UserAccessDto> userAccessMenuList = accessFetch.queryAllUserMenus();
        // 组装到一起
        List<UserAccessDto> resp = new ArrayList<>();
        Map<String, UserAccessDto> permGroupByUserId = userAccessPermList.stream().collect(Collectors.toMap(UserAccessDto::getUserId, userAccessDto -> userAccessDto));
        Map<String, UserAccessDto> menuGroupByUserId = userAccessMenuList.stream().collect(Collectors.toMap(UserAccessDto::getUserId, userAccessDto -> userAccessDto));
        for (Map.Entry<String, UserAccessDto> entry : permGroupByUserId.entrySet()) {
            String userId = entry.getKey();
            UserAccessDto userAccessDto = entry.getValue();
            if (menuGroupByUserId.containsKey(userId)){
                userAccessDto.setMenus(menuGroupByUserId.get(userId).getMenus());
                menuGroupByUserId.remove(userId);
            }
            resp.add(userAccessDto);
        }
        resp.addAll(menuGroupByUserId.values());
        AccessListResp accessListResp = new AccessListResp();
        accessListResp.setUserAccessRespList(resp);
        return accessListResp;
    }
}
