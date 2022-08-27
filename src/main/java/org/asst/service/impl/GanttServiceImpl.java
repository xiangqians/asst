package org.asst.service.impl;

import org.asst.dao.GanttDao;
import org.asst.pagination.Page;
import org.asst.po.GanttPo;
import org.asst.po.param.GanttPoParam;
import org.asst.service.GanttService;
import org.asst.vo.event.EventVo;
import org.asst.vo.gantt.GanttVo;
import org.asst.vo.gantt.param.GanttAddVoParam;
import org.asst.vo.gantt.param.GanttModifyVoParam;
import org.asst.vo.gantt.param.GanttPageVoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author xiangqian
 * @date 15:26 2022/08/27
 */
@Service
public class GanttServiceImpl implements GanttService {

    @Autowired
    private GanttDao ganttDao;

    private Function<GanttPo, GanttVo> poToVoFunction = po -> po.convertToVo(GanttVo.class);

    @Transactional(timeout = 10, readOnly = true)
    @Override
    public Page<GanttVo> queryForPage(GanttPageVoParam voParam) {
        return ganttDao.queryForPage(voParam, voParam.convertToPoParam(GanttPoParam.class)).convert(po -> {
            GanttVo vo = po.convertToVo(GanttVo.class);
            if (Objects.nonNull(po.getContent()) && po.getContent().length() > 20) {
                vo.setContent(po.getContent().substring(0, 20) + " [更多]");
            }
            return vo;
        });
    }

    @Transactional(timeout = 10, readOnly = true)
    @Override
    public GanttVo queryById(String id) {
        GanttPo po = checkGanttId(id);
        return poToVoFunction.apply(po);
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean updateById(GanttModifyVoParam voParam) {
        checkGanttId(voParam.getId());
        return ganttDao.updateById(voParam.convertToPoParam(GanttPoParam.class));
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean deleteById(String id) {
        checkGanttId(id);
        return ganttDao.deleteById(id);
    }

    @Transactional(timeout = 10)
    @Override
    public Boolean save(GanttAddVoParam voParam) {
        return ganttDao.save(voParam.convertToPoParam(GanttPoParam.class));
    }

    private GanttPo checkGanttId(String ganttId) {
        Assert.notNull(ganttId, "甘特图id不能为空");
        GanttPo po = null;
        Assert.isTrue(Objects.nonNull(po = ganttDao.queryById(ganttId)), String.format("甘特图信息（id=%s）不存在", ganttId));
        return po;
    }

}
