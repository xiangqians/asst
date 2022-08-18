package org.calendar.vo.event.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.calendar.o.PoParam;
import org.calendar.o.VoParam;
import org.calendar.po.param.EventPoParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xiangqian
 * @date 00:28 2022/08/17
 */
@Data
@ApiModel(description = "新增事件信息")
public class EventAddVoParam implements VoParam {

    @NotBlank(message = "事件标题不能为空")
    @ApiModelProperty(value = "事件标题", required = true)
    private String title;

    @ApiModelProperty("事件链接地址")
    private String url;

    @ApiModelProperty("事件内容")
    private String content;

    @NotNull(message = "事件开始时间（时间戳）不能为空")
    @ApiModelProperty(value = "事件开始时间（时间戳）", required = true)
    private Long startTime;

    @NotNull(message = "事件结束时间（时间戳）不能为空")
    @ApiModelProperty(value = "事件结束时间（时间戳）", required = true)
    private Long endTime;

    @Override
    public void post() {
        title = StringUtils.trimToNull(title);
        url = StringUtils.trim(url);
        content = StringUtils.trim(content);
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == EventPoParam.class) {
            EventPoParam poParam = new EventPoParam();
            poParam.setTitle(getTitle());
            poParam.setUrl(getUrl());
            poParam.setContent(getContent());
            poParam.setStartTime(getStartTime());
            poParam.setEndTime(getEndTime());
            return (T) poParam;
        }

        return VoParam.super.convertToPoParam(type);
    }

}
