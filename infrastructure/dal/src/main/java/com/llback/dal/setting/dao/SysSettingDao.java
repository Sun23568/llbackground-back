package com.llback.dal.setting.dao;

import com.llback.dal.setting.po.SysSettingPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 全局系统配置 DAO
 */
@Mapper
public interface SysSettingDao {

    /**
     * 查询所有配置
     */
    List<SysSettingPo> queryAll();

    /**
     * 按 key 查询单条
     */
    SysSettingPo queryByKey(@Param("cfgKey") String cfgKey);

    /**
     * 插入或更新
     */
    void saveOrUpdate(@Param("pkId") String pkId,
            @Param("cfgKey") String cfgKey,
            @Param("cfgValue") String cfgValue);
}
