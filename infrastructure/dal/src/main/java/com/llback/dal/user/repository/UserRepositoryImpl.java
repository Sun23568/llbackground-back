package com.llback.dal.user.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.llback.common.types.UserId;
import com.llback.common.util.AssertUtil;
import com.llback.core.user.eo.UserEo;
import com.llback.core.user.repository.UserRepository;
import com.llback.dal.user.assembler.UserAssembler;
import com.llback.dal.user.dao.UserDao;
import com.llback.dal.user.po.UserPo;
import com.llback.rt.common.cache.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /**
     * 查询用户列表
     */
    @Override
    public PageInfo<UserEo> findUsers(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        PageInfo<UserPo> poPageInfo = new PageInfo<>(userDao.listUser());
        return PoAssembleUtil.poPage2EoPage(poPageInfo, UserEo.class);
    }

    /**
     * 添加用户
     */
    @Override
    public int addUser(UserEo userEo) {
        return userDao.addUser(PoAssembleUtil.eo2Po(userEo, UserPo.class));
    }

    /**
     * 修改用户
     */
    @Override
    public int updateUser(UserEo userEo) {
        return userDao.updateUser(PoAssembleUtil.eo2Po(userEo, UserPo.class));
    }

    /**
     * 删除用户
     */
    @Override
    public boolean removeUsers(List<String> userIds) {
        int count = userDao.removeUsers(userIds);
        return userIds.size() == count;
    }
}
