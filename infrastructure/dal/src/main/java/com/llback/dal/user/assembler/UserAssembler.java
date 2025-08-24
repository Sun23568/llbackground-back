package com.llback.dal.user.assembler;

import com.llback.common.types.EncryptedPassword;
import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import com.llback.common.types.UserName;
import com.llback.core.user.eo.UserEo;
import com.llback.dal.user.po.UserPo;

/**
 * 用户实例转换器
 */
public class UserAssembler {
    public static UserEo poToEo(UserPo po) {
        if (po == null) {
            return null;
        }
        return UserEo.builder()
                .userId(UserId.of(po.getUserId()))
                .userName(UserName.of(po.getUserName()))
                .password(EncryptedPassword.of(po.getPassword()))
                .avatar(StringId.of(po.getAvatar()))
                .build();
    }
}
