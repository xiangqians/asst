package org.asst.service;

import org.asst.pagination.Page;
import org.asst.vo.gantt.GanttVo;
import org.asst.vo.gantt.param.GanttAddVoParam;
import org.asst.vo.gantt.param.GanttModifyVoParam;
import org.asst.vo.gantt.param.GanttPageVoParam;

/**
 * 甘特图服务类
 *
 * @author xiangqian
 * @date 15:26 2022/08/27
 */
public interface GanttService {

    Page<GanttVo> queryForPage(GanttPageVoParam voParam);

    GanttVo queryById(String id);

    Boolean updateById(GanttModifyVoParam voParam);

    Boolean deleteById(String id);

    Boolean save(GanttAddVoParam voParam);
}
