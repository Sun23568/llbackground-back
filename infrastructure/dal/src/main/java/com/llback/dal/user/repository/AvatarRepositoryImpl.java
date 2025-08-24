package com.llback.dal.user.repository;

import com.llback.common.types.StringId;
import com.llback.common.util.AssertUtil;
import com.llback.common.util.RandomIdUtil;
import com.llback.core.user.repository.AvatarRepository;
import com.llback.dal.user.dao.AvatarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 头像仓储实现
 */
@Component
public class AvatarRepositoryImpl implements AvatarRepository {
    /**
     * 头像数据访问接口
     */
    @Autowired
    private AvatarDao avatarDao;

    /**
     * 添加头像
     */
    @Override
    public StringId addAvatar(String avatar) {
        String avatarId = RandomIdUtil.uuid();
        AssertUtil.assertTrue(avatarDao.addAvatar(avatarId, avatar) == 1, "添加头像失败");
        return StringId.of(avatarId);
    }

    /**
     * 获取头像
     */
    @Override
    public String getAvatar(StringId avatarId) {
        return avatarDao.getAvatar(avatarId.toString());
    }
}
