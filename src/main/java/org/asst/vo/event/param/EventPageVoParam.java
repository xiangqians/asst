package org.asst.vo.event.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.pagination.PageRequest;
import org.asst.po.param.EventPoParam;
import org.asst.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author xiangqian
 * @date 00:28 2022/08/17
 */
@Data
@ApiModel(description = "事件分页参数信息")
public class EventPageVoParam extends PageRequest {

    @ApiModelProperty("事件标题")
    private String title;

    @ApiModelProperty("事件url")
    private String url;

    @ApiModelProperty("事件内容")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("事件开始时间，时间格式：yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("事件结束时间，时间格式：yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Override
    public void post() {
        title = StringUtils.trimToNull(title);
        url = StringUtils.trimToNull(url);
        content = StringUtils.trimToNull(content);

        if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
            Assert.isTrue(startTime.isEqual(endTime) || startTime.isBefore(endTime), "事件开始时间不能大于结束时间!");
        }

        super.post();
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == EventPoParam.class) {
            EventPoParam poParam = new EventPoParam();
            poParam.setTitle(getTitle());
            poParam.setUrl(getUrl());
            poParam.setContent(getContent());
            if (Objects.nonNull(startTime)) {
                poParam.setStartTime(DateUtils.dateToTimestamp(startTime));
            }
            if (Objects.nonNull(endTime)) {
                poParam.setEndTime(DateUtils.dateToTimestamp(endTime));
            }
            return (T) poParam;
        }

        return super.convertToPoParam(type);
    }

}
