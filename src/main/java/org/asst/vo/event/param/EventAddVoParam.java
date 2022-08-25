package org.asst.vo.event.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.o.VoParam;
import org.asst.po.param.EventPoParam;
import org.asst.util.DateUtils;
import org.asst.validation.groups.Add;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author xiangqian
 * @date 00:28 2022/08/17
 */
@Data
@ApiModel(description = "新增事件信息")
public class EventAddVoParam implements VoParam {

    @NotBlank(message = "事件标题不能为空", groups = {Add.class})
    @ApiModelProperty(value = "事件标题", required = true)
    private String title;

    @ApiModelProperty("事件链接地址")
    private String url;

    @ApiModelProperty("事件内容")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "事件开始时间不能为空", groups = {Add.class})
    @ApiModelProperty(value = "事件开始时间", required = true)
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "事件结束时间不能为空", groups = {Add.class})
    @ApiModelProperty(value = "事件结束时间", required = true)
    private LocalDateTime endTime;

    @Override
    public void post() {
        title = StringUtils.trimToNull(title);
        url = StringUtils.trim(url);
        content = StringUtils.trimToEmpty(content);
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == EventPoParam.class) {
            EventPoParam poParam = new EventPoParam();
            poParam.setTitle(getTitle());
            poParam.setUrl(getUrl());
            poParam.setContent(getContent());
            poParam.setStartTime(DateUtils.dateToTimestamp(getStartTime()));
            poParam.setEndTime(DateUtils.dateToTimestamp(getEndTime()));
            return (T) poParam;
        }

        return VoParam.super.convertToPoParam(type);
    }

}
