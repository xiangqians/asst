package org.calendar.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.calendar.dao.DaoHelper;
import org.calendar.dao.NoteDao;
import org.calendar.mapper.NoteMapper;
import org.calendar.pagination.Page;
import org.calendar.pagination.PageRequest;
import org.calendar.po.NotePo;
import org.calendar.po.param.NotePoParam;
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
