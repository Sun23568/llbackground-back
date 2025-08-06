package com.llback.dal.sa.repository;

import com.llback.common.types.UserId;
import com.llback.core.user.eo.FuncPermEo;
import com.llback.core.user.repository.PermRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 权限仓库实现
 */
@Component
public class PermRepositoryImpl implements PermRepository {
    @Override
    public List<FuncPermEo> queryUserPerms(UserId userId) {
        return Collections.emptyList();
    }
}
