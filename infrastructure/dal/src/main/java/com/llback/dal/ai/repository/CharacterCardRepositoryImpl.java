package com.llback.dal.ai.repository;

import com.llback.common.types.StringId;
import com.llback.common.types.UserId;
import com.llback.core.ai.eo.CharacterCardEo;
import com.llback.core.ai.repository.CharacterCardRepository;
import com.llback.dal.ai.dao.CharacterCardDao;
import com.llback.dal.ai.po.CharacterCardPo;
import com.llback.rt.common.util.PoAssembleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色卡仓储实现类
 *
 * @author HaleyAI
 * @date 2026/1/6
 */
@Component
public class CharacterCardRepositoryImpl implements CharacterCardRepository {
    @Autowired
    private CharacterCardDao characterCardDao;

    @Override
    public void save(CharacterCardEo characterCardEo) {
        CharacterCardPo po = PoAssembleUtil.eo2Po(characterCardEo, CharacterCardPo.class);
        characterCardDao.insert(po);
    }

    @Override
    public CharacterCardEo findById(StringId id) {
        CharacterCardPo po = characterCardDao.selectById(id.toString());
        return po == null ? null : PoAssembleUtil.po2Eo(po, CharacterCardEo.class);
    }

    @Override
    public List<CharacterCardEo> findByUserId(UserId userId) {
        List<CharacterCardPo> poList = characterCardDao.selectByUserId(userId.getValue());
        return PoAssembleUtil.poList2EoList(poList, CharacterCardEo.class);
    }

    @Override
    public List<CharacterCardEo> findByUserIdWithNsfw(UserId userId, Boolean showNsfw) {
        List<CharacterCardPo> poList = characterCardDao.selectByUserIdWithNsfw(userId.getValue(), showNsfw);
        return PoAssembleUtil.poList2EoList(poList, CharacterCardEo.class);
    }

    @Override
    public void update(CharacterCardEo characterCardEo) {
        CharacterCardPo po = PoAssembleUtil.eo2Po(characterCardEo, CharacterCardPo.class);
        characterCardDao.update(po);
    }

    @Override
    public void deleteById(StringId id) {
        characterCardDao.deleteById(id.getValue());
    }
}
