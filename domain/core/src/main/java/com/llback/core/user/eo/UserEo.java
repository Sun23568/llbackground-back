package com.llback.core.user.eo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.llback.common.exception.BizException;
import com.llback.common.types.*;
import com.llback.common.util.AssertUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * 用户实体
 */
@Getter
@Builder
@ToString
public class UserEo implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(UserEo.class);
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
     * 手机号
     */
    private MobileNo mobileNo;
    /**
     * 登录次数
     */
    private int loginCount;

    /**
     * 密码校验规则
     */
    @Value("${sa.pattern}")
    protected boolean pattern;

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
     *
     * @param newPwd              新密码
     * @param encryptedConfirmPwd 确认密码
     * @param encryptedPassword   加密的旧密码
     * @param encryptedNewPwd     加密的新密码
     * @param needPasswordControl 是否需要校验密码复杂度
     */
    public void changePassword(PlainPassword newPwd, EncryptedPassword encryptedConfirmPwd, EncryptedPassword encryptedPassword
            , EncryptedPassword encryptedNewPwd, boolean needPasswordControl) {
        AssertUtil.assertEquals(encryptedPassword.toString(), this.password.toString(), () -> new BizException("原密码错误"));
        AssertUtil.assertTrue(encryptedNewPwd.toString().equals(encryptedConfirmPwd.toString())
                , () -> new BizException("两次输入密码不一致"));
        AssertUtil.assertFalse(encryptedNewPwd.equals(encryptedPassword), () -> new BizException("新密码不能与旧密码相同"));
        if (pattern){
            AssertUtil.assertTrue(needPasswordControl || newPwd.verityComplexity()
                    , () -> new BizException("新密码必须6-16位，包含字母数字特殊符号三种类型"));
        } else {
            StringBuilder reversed = new StringBuilder(userName);
            // 判断密码是否为用户名的正序
            AssertUtil.assertTrue(needPasswordControl || !newPwd.equals(this.userName.toString())
                    , () -> new BizException("密码不能为用户名的正序"));
            // 判断密码是否为用户名的倒序
            AssertUtil.assertTrue(needPasswordControl || !newPwd.equals(reversed.reverse().toString())
                    , () -> new BizException("密码不能为用户名的倒序"));
        }
        this.password = encryptedNewPwd;
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
}
