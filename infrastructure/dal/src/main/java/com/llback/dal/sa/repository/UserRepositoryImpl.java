package com.llback.dal.sa.repository;

import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import com.llback.core.user.repository.UserRepository;
import com.llback.core.user.eo.UserEo;
import com.llback.dal.sa.po.UserPo;
import com.llback.dal.user.assembler.UserAssembler;
import com.llback.dal.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

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
    public UserEo findUser(UserId userId) {
        AssertUtil.notEmpty(userId, "用户ID不能为空");
        UserPo userInfo = userDao.getUserInfo(userId.toString());
        return UserAssembler.poToEo(userInfo);
    }
}
