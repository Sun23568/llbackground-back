package com.llback.frame.context;

import com.llback.common.exception.NotLoginException;
import com.llback.common.util.AssertUtil;
import com.llback.core.user.vo.UserCacheItemVo;
import com.llback.core.util.CacheUtils;
import org.springframework.stereotype.Component;

/**
 * css-backend @2024
 *
 * @author hex
 * @date 2024/3/2 00:27
 */
@Component
public class SessionMgrImpl implements SessionMgr {
    @Override
    public UserSession getUserSession(SessionMap sessionMap) {
        UserCacheItemVo userCacheItemVo = CacheUtils.getUser(sessionMap.getUserId());
        AssertUtil.notNull(userCacheItemVo, "系统未找到用户信息!");
        // 如果token重新创建，则重新加载用户信息
        if (sessionMap.getTokenCrtTimestamp() != 0 &&
                userCacheItemVo.getCrtTimestamp() < sessionMap.getTokenCrtTimestamp()) {
            // 更新缓存
            userCacheItemVo = CacheUtils.reloadUser(sessionMap.getUserId());
        }
        return new UserSessionImpl(sessionMap, userCacheItemVo);
    }

    @Override
    public void updateToken(SessionMap sessionMap) {

    }

    @Override
    public void removeToken(SessionMap sessionMap) {

    }
//
//    /**
//     * 用户缓存
//     */
//    @Autowired
//    protected UserCache userCache;
//
//    /**
//     * 安全配置服务
//     */
//    @Autowired
//    protected SaConfigService saConfigService;
//
//    @Override
//    public UserSession getUserSession(SessionMap sessionMap) {
//        UserCacheItemVo userCacheItemVo = this.userCache.get(sessionMap.getUserId().toString());
//        AssertUtil.notNull(userCacheItemVo, "系统未找到用户信息!");
//        // 如果token重新创建，则重新加载用户信息
//        if (sessionMap.getTokenCrtTimestamp() != 0 &&
//                userCacheItemVo.getCrtTimestamp() < sessionMap.getTokenCrtTimestamp()) {
//            // 更新缓存
//            userCacheItemVo = userCache.reload(sessionMap.getUserId().toString());
//        }
//        return new UserSessionImpl(sessionMap, userCacheItemVo, saConfigService.getUserActivationCfg());
//    }
}
