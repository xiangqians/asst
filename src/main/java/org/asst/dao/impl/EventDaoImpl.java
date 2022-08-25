package org.asst.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.asst.dao.DaoHelper;
import org.asst.dao.EventDao;
import org.asst.mapper.EventMapper;
import org.asst.pagination.Page;
import org.asst.pagination.PageRequest;
import org.asst.po.EventPo;
import org.asst.po.param.EventPoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xiangqian
 * @date 00:37 2022/08/17
 */
@Component
public class EventDaoImpl implements EventDao {

    private final String CACHE_NAME = "CACHE_EVENT_DAO";

    @Autowired
    private EventMapper eventMapper;

    @Override
    public Page<EventPo> queryForPage(PageRequest pageRequest, EventPoParam poParam) {
        LambdaQueryWrapper<EventPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(poParam.getTitle())) {
            lambdaQueryWrapper.like(EventPo::getTitle, poParam.getTitle());
        }
        if (Objects.nonNull(poParam.getUrl())) {
            lambdaQueryWrapper.like(EventPo::getUrl, poParam.getUrl());
        }
        if (Objects.nonNull(poParam.getContent())) {
            lambdaQueryWrapper.like(EventPo::getContent, poParam.getContent());
        }
        if (Objects.nonNull(poParam.getStartTime())) {
            lambdaQueryWrapper.ge(EventPo::getStartTime, poParam.getStartTime());
        }
        if (Objects.nonNull(poParam.getEndTime())) {
            lambdaQueryWrapper.le(EventPo::getStartTime, poParam.getEndTime());
        }
        lambdaQueryWrapper.orderByDesc(EventPo::getCreateTime);
        return DaoHelper.queryForPage(eventMapper, pageRequest, lambdaQueryWrapper);
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "'id_'+#id", unless = "#result == null")
    @Override
    public EventPo queryById(String id) {
        return eventMapper.selectById(id);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "'id_'+#poParam.id")
    @Override
    public Boolean updateById(EventPoParam poParam) {
        return DaoHelper.updateById(eventMapper, poParam);
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "'id_'+#id")
    @Override
    public Boolean deleteById(String id) {
        return eventMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean save(EventPoParam poParam) {
        return DaoHelper.save(eventMapper, poParam);
    }

}
