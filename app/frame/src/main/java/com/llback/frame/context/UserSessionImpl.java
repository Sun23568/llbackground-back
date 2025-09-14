package com.llback.frame.context;

import com.llback.base.user.vo.UserCacheItemVo;
import com.llback.common.types.OperationId;
import com.llback.common.util.AssertUtil;
import lombok.Getter;

import java.util.Set;

/**
 * userSession实现类
 */
@Getter
public class UserSessionImpl implements UserSession {
    /**
     * sessionMap
     */
    private final SessionMap sessionMap;

    /**
     * 操作ID
     */
    private final OperationId operationId;

    /**
     * 用户相关对象缓存
     */
    private final UserCacheItemVo userCacheItemVo;

    /**
     * 构造函数
     *
     * @param sessionMap
     * @param userCacheItemVo
     */
    public UserSessionImpl(SessionMap sessionMap, UserCacheItemVo userCacheItemVo) {
        AssertUtil.notNull(userCacheItemVo, "系统未找到用户信息!");
        this.sessionMap = sessionMap;
        this.userCacheItemVo = userCacheItemVo;
        this.operationId = OperationId.of(sessionMap.getUserId(), userCacheItemVo.getUser().getUserName());
    }

    @Override
    public Set<String> getPerms() {
        return this.userCacheItemVo.getPerms();
    }
}
