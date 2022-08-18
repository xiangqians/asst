package org.calendar.mapper;

import org.calendar.po.EventPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 *
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
@Mapper
public interface EventMapper extends BaseMapper<EventPo> {

}
