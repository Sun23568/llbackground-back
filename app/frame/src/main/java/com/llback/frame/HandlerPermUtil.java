package com.llback.frame;

import com.llback.common.exception.ForbidException;
import com.llback.common.exception.NotLoginException;
import com.llback.common.util.AssertUtil;
import com.llback.frame.context.ReqContext;
import com.llback.frame.context.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * HandlerPermUtil
 *
 * @author feifei.guo
 * @date 2024/2/29 22:33
 * @since 1.00
 */
@Slf4j
public final class HandlerPermUtil {

    private HandlerPermUtil() {
    }

    /**
     * 检查权限
     *
     * @param handlerInfo 处理器信息
     * @param reqContext  请求上下文
     */
    public static void checkPermission(HandlerInfo handlerInfo, ReqContext reqContext) {
        if (handlerInfo.isPubAcl()) {
            return;
        }

        //检查会话是否有效, 并且自动续期
        reqContext.getRestContext().checkLogin(true);

        UserSession userSession = reqContext.getUserSession();

        AssertUtil.notNull(userSession, NotLoginException::new);

        // 激活权限校验

        String needPerm = handlerInfo.getPerm();
        if (StringUtils.isEmpty(needPerm)) {
            return;
        }

        boolean checkResult = userSession.hasPerm(needPerm);

        AssertUtil.assertTrue(checkResult, () -> new ForbidException(needPerm));
    }
}
