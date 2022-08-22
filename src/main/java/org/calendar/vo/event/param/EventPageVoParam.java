package org.calendar.vo.event.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.calendar.o.PoParam;
import org.calendar.pagination.PageRequest;
import org.calendar.po.param.EventPoParam;

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

    @Override
    public void post() {
        title = StringUtils.trimToNull(title);
        url = StringUtils.trimToNull(url);
        content = StringUtils.trimToNull(content);
        super.post();
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == EventPoParam.class) {
            EventPoParam poParam = new EventPoParam();
            poParam.setTitle(getTitle());
            poParam.setUrl(getUrl());
            poParam.setContent(getContent());
            return (T) poParam;
        }

        return super.convertToPoParam(type);
    }

}
