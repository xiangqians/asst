package org.calendar.service;

import org.calendar.pagination.Page;
import org.calendar.vo.event.EventVo;
import org.calendar.vo.event.param.EventAddVoParam;
import org.calendar.vo.event.param.EventModifyVoParam;
import org.calendar.vo.event.param.EventPageVoParam;

/**
 * 事件信息服务类
 *
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
public interface EventService {

    Page<EventVo> queryForPage(EventPageVoParam voParam);

    EventVo queryById(String id);

    Boolean updateById(EventModifyVoParam voParam);

    Boolean deleteById(String id);

    Boolean save(EventAddVoParam voParam);

}
