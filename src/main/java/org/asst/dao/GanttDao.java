package org.asst.dao;

import org.asst.pagination.Page;
import org.asst.pagination.PageRequest;
import org.asst.po.GanttPo;
import org.asst.po.param.GanttPoParam;

/**
 * @author xiangqian
 * @date 15:47 2022/08/27
 */
public interface GanttDao {

    Page<GanttPo> queryForPage(PageRequest pageRequest, GanttPoParam poParam);

    GanttPo queryById(String id);

    Boolean updateById(GanttPoParam poParam);

    Boolean deleteById(String id);

    Boolean save(GanttPoParam poParam);

}
