package com.llback.api.app.user.handler;

import com.llback.api.app.sa.dto.req.GetAvatarReq;
import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.core.user.repository.AvatarRepository;
import com.llback.frame.Handler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取头像处理器
 */
@Component
public class GetAvatarHandler implements Handler<String, GetAvatarReq> {
    /**
     * 头像仓库
     */
    @Autowired
    private AvatarRepository avatarRepository;

    @Override
    public String execute(GetAvatarReq req) {
        AssertUtil.notNull(req, "请求参数不能为空");

        String avatarId = req.getAvatarId();
        if (StringUtils.isEmpty(avatarId)) {
            avatarId = "default";
        }
        String avatar = avatarRepository.getAvatar(StringId.of(avatarId));
        if (StringUtils.isEmpty(avatar)) {
            avatar = avatarRepository.getAvatar(StringId.of("default"));
        }
        return avatar;
    }
}
