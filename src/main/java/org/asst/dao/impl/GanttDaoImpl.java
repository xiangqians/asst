package org.asst.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.asst.dao.DaoHelper;
import org.asst.dao.GanttDao;
import org.asst.mapper.GanttMapper;
import org.asst.pagination.Page;
import org.asst.pagination.PageRequest;
import org.asst.po.GanttPo;
import org.asst.po.param.GanttPoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xiangqian
 * @date 15:47 2022/08/27
 */
@Component
public class GanttDaoImpl implements GanttDao {

    @Autowired
    private GanttMapper ganttMapper;

    @Override
    public Page<GanttPo> queryForPage(PageRequest pageRequest, GanttPoParam poParam) {
        LambdaQueryWrapper<GanttPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(poParam.getName())) {
            lambdaQueryWrapper.like(GanttPo::getName, poParam.getName());
        }
        if (Objects.nonNull(poParam.getDesc())) {
            lambdaQueryWrapper.like(GanttPo::getDesc, poParam.getDesc());
        }
        if (Objects.nonNull(poParam.getContent())) {
            lambdaQueryWrapper.like(GanttPo::getContent, poParam.getContent());
        }
        lambdaQueryWrapper.orderByDesc(GanttPo::getCreateTime);
        return DaoHelper.queryForPage(ganttMapper, pageRequest, lambdaQueryWrapper);
    }

    @Override
    public GanttPo queryById(String id) {
        return ganttMapper.selectById(id);
    }

    @Override
    public Boolean updateById(GanttPoParam poParam) {
        return DaoHelper.updateById(ganttMapper, poParam);
    }

    @Override
    public Boolean deleteById(String id) {
        return ganttMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean save(GanttPoParam poParam) {
        return DaoHelper.save(ganttMapper, poParam);
    }

}
