package org.calendar.dao;

import org.calendar.pagination.Page;
import org.calendar.pagination.PageRequest;
import org.calendar.po.EventPo;
import org.calendar.po.param.EventPoParam;

/**
 * @author xiangqian
 * @date 00:36 2022/08/17
 */
public interface EventDao {

    Page<EventPo> queryForPage(PageRequest pageRequest, EventPoParam poParam);

    EventPo queryById(String id);

    Boolean updateById(EventPoParam poParam);

    Boolean deleteById(String id);

    Boolean save(EventPoParam poParam);

}
