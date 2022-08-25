package org.asst.service;

import org.asst.pagination.Page;
import org.asst.vo.event.EventVo;
import org.asst.vo.event.param.EventAddVoParam;
import org.asst.vo.event.param.EventModifyVoParam;
import org.asst.vo.event.param.EventPageVoParam;

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
