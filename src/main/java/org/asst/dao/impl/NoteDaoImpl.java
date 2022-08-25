package org.asst.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.asst.dao.DaoHelper;
import org.asst.dao.NoteDao;
import org.asst.mapper.NoteMapper;
import org.asst.pagination.Page;
import org.asst.pagination.PageRequest;
import org.asst.po.NotePo;
import org.asst.po.param.NotePoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xiangqian
 * @date 22:36 2022/08/16
 */
@Component
public class NoteDaoImpl implements NoteDao {

    private final String CACHE_NAME = "CACHE_NOTE_DAO";

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public Page<NotePo> queryForPage(PageRequest pageRequest, NotePoParam poParam) {
        LambdaQueryWrapper<NotePo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(poParam.getTitle())) {
            lambdaQueryWrapper.like(NotePo::getTitle, poParam.getTitle());
        }
        if (Objects.nonNull(poParam.getContent())) {
            lambdaQueryWrapper.like(NotePo::getContent, poParam.getContent());
        }
        lambdaQueryWrapper.orderByDesc(NotePo::getCreateTime);
        return DaoHelper.queryForPage(noteMapper, pageRequest, lambdaQueryWrapper);
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "'id_'+#id", unless = "#result == null")
    @Override
    public NotePo queryById(String id) {
        return noteMapper.selectById(id);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "'id_'+#poParam.id")
    @Override
    public Boolean updateById(NotePoParam poParam) {
        return DaoHelper.updateById(noteMapper, poParam);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "'id_'+#id")
    @Override
    public Boolean deleteById(String id) {
        return noteMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean save(NotePoParam poParam) {
        return DaoHelper.save(noteMapper, poParam);
    }

}
