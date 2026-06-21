package com.llback.dal.game.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.llback.common.types.StringId;
import com.llback.core.game.eo.GameLevelEo;
import com.llback.core.game.repository.GameLevelRepository;
import com.llback.dal.game.dao.GameLevelDao;
import com.llback.dal.game.po.GameLevelPo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 游戏关卡仓储实现。
 */
@Component
public class GameLevelRepositoryImpl implements GameLevelRepository {
    @Autowired
    private GameLevelDao gameLevelDao;

    @Override
    public void save(GameLevelEo levelEo) {
        GameLevelPo po = PoAssembleUtil.eo2Po(levelEo, GameLevelPo.class);
        gameLevelDao.insert(po);
    }

    @Override
    public void update(GameLevelEo levelEo) {
        GameLevelPo po = PoAssembleUtil.eo2Po(levelEo, GameLevelPo.class);
        gameLevelDao.update(po);
    }

    @Override
    public void deleteById(StringId pkId) {
        gameLevelDao.deleteById(pkId.getValue());
    }

    @Override
    public GameLevelEo findById(StringId pkId) {
        GameLevelPo po = gameLevelDao.selectById(pkId.getValue());
        return po == null ? null : PoAssembleUtil.po2Eo(po, GameLevelEo.class);
    }

    @Override
    public PageInfo<GameLevelEo> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<GameLevelPo> poPageInfo = new PageInfo<>(gameLevelDao.selectAll());
        return PoAssembleUtil.poPage2EoPage(poPageInfo, GameLevelEo.class);
    }

    @Override
    public List<GameLevelEo> findEnabled() {
        return PoAssembleUtil.poList2EoList(gameLevelDao.selectEnabled(), GameLevelEo.class);
    }
}
