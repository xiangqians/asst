package org.calendar.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.calendar.o.Vo;
import org.calendar.vo.event.EventVo;

/**
 * 事件信息表
 *
 * @author xiangqian
 * @date 22:14 2022/08/16
 */
@Data
@TableName("event")
public class EventPo extends ComPo {

    private static final long serialVersionUID = 1L;

    /**
     * 事件标题
     */
    private String title;

    /**
     * 事件链接地址
     */
    private String url;

    /**
     * 事件内容
     */
    private String content;

    /**
     * 事件开始时间（时间戳）
     */
    private Long startTime;

    /**
     * 事件结束时间（时间戳）
     */
    private Long endTime;

    @Override
    public <T extends Vo> T convertToVo(Class<T> type) {

        if (type == EventVo.class) {
            EventVo vo = super.convertToVo(EventVo.class);
            vo.setTitle(getTitle());
            vo.setUrl(getUrl());
            vo.setContent(getContent());
            vo.setStartTime(getStartTime());
            vo.setEndTime(getEndTime());
            return (T) vo;
        }

        return super.convertToVo(type);
    }


}
