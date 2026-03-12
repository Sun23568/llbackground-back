package com.llback.dal.setting.repository;

import com.llback.core.setting.repository.SysSettingRepository;
import com.llback.dal.setting.dao.SysSettingDao;
import com.llback.dal.setting.po.SysSettingPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 全局系统配置仓储实现
 */
@Component
public class SysSettingRepositoryImpl implements SysSettingRepository {

    @Autowired
    private SysSettingDao sysSettingDao;

    @Override
    public Map<String, String> findAll() {
        List<SysSettingPo> list = sysSettingDao.queryAll();
        Map<String, String> result = new HashMap<>();
        if (list != null) {
            for (SysSettingPo po : list) {
                result.put(po.getCfgKey(), po.getCfgValue());
            }
        }
        return result;
    }

    @Override
    public void saveOrUpdate(String key, String value) {
        String pkId = UUID.randomUUID().toString().replace("-", "");
        sysSettingDao.saveOrUpdate(pkId, key, value);
    }
}
