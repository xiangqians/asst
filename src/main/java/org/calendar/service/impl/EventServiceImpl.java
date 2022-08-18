package org.calendar.service.impl;

import org.calendar.dao.EventDao;
import org.calendar.pagination.Page;
import org.calendar.po.EventPo;
import org.calendar.po.param.EventPoParam;
import org.calendar.service.EventService;
import org.calendar.vo.event.EventVo;
import org.calendar.vo.event.param.EventAddVoParam;
import org.calendar.vo.event.param.EventModifyVoParam;
import org.calendar.vo.event.param.EventPageVoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    private Function<EventPo, EventVo> poToVoFunction = po -> po.convertToVo(EventVo.class);

    @Transactional(timeout = 10, readOnly = true)
    @Override
    public Page<EventVo> queryForPage(EventPageVoParam voParam) {
        return eventDao.queryForPage(voParam, voParam.convertToPoParam(EventPoParam.class)).convert(poToVoFunction);
    }

    @Transactional(timeout = 10, readOnly = true)
    @Override
    public EventVo queryById(String id) {
        EventPo po = checkEventId(id);
        return poToVoFunction.apply(po);
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean updateById(EventModifyVoParam voParam) {
        checkEventId(voParam.getId());
        return eventDao.updateById(voParam.convertToPoParam(EventPoParam.class));
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean deleteById(String id) {
        checkEventId(id);
        return eventDao.deleteById(id);
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean save(EventAddVoParam voParam) {
        return eventDao.save(voParam.convertToPoParam(EventPoParam.class));
    }

    private EventPo checkEventId(String eventId) {
        Assert.notNull(eventId, "事件id不能为空");
        EventPo po = null;
        Assert.isTrue(Objects.nonNull(po = eventDao.queryById(eventId)), String.format("事件信息（id=%s）不存在", eventId));
        return po;
    }

}
