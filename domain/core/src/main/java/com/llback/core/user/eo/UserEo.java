package com.llback.core.user.eo;

import com.llback.common.exception.BizException;
import com.llback.common.types.*;
import com.llback.common.util.AssertUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Getter
@Builder
@ToString
public class UserEo implements Serializable {
    /**
     * 主键ID
     */
    private StringId pkId;

    /**
     * 用户ID
     */
    private final UserId userId;
    /**
     * 用户名
     */
    private UserName userName;
    /**
     * 密码
     */
    private transient EncryptedPassword password;

    /**
     * 密码校验规则
     */
    @Value("${sa.pattern}")
    protected boolean pattern;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 头像
     */
    private StringId avatar;

    /**
     * 创建用户
     *
     * @param userId   用户ID
     * @param userName 用户名
     * @return 用户实体
     */
    public static UserEo of(String userId, String userName) {
        return UserEo.builder().userId(UserId.of(userId))
                .userName(UserName.of(userName))
                .build();
    }

    /**
     * 更改密码
     */
    public void changePassword(EncryptedPassword oldPassword, EncryptedPassword newPassword, EncryptedPassword confirmPassword) {
        AssertUtil.assertEquals(oldPassword.toString(), this.password.toString(), () -> new BizException("原密码错误"));
        AssertUtil.assertTrue(newPassword.toString().equals(confirmPassword.toString())
                , () -> new BizException("两次输入密码不一致"));
        AssertUtil.assertFalse(oldPassword.equals(newPassword), () -> new BizException("新密码不能与旧密码相同"));
        this.password = newPassword;
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    public boolean checkPassword(String password) {
        return this.password.toString().equals(password);
    }

    /**
     * 清空密码
     */
    public void clearPassword() {
        this.password = null;
    }

    /**
     * 修改用户名
     */
    public void changeUserName(UserName userName) {
        AssertUtil.notNull(userName, "用户名不能为空");
        this.userName = userName;
    }

    /**
     * 修改头像
     */
    public void changeAvatar(StringId avatar) {
        this.avatar = avatar;
    }
}
