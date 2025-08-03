package com.llback.dal.sa.repository;

import com.llback.common.util.AssertUtil;
import com.llback.core.sa.repository.SaRepository;
import com.llback.dal.sa.po.UserPo;
import com.llback.dal.user.assembler.UserAssembler;
import com.llback.dal.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaRepositoryImpl implements SaRepository {

    /**
     * 用户Dao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserEo findUser(String userId) {
        AssertUtil.notEmpty(userId, "用户ID不能为空");
        UserPo userInfo = userDao.getUserInfo(userId);
        return UserAssembler.poToEo(userInfo);
    }
}
