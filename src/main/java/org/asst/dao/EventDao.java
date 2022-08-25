package org.asst.dao;

import org.asst.pagination.Page;
import org.asst.pagination.PageRequest;
import org.asst.po.EventPo;
import org.asst.po.param.EventPoParam;

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
