package org.asst.vo.gantt.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.pagination.PageRequest;
import org.asst.po.param.GanttPoParam;

/**
 * @author xiangqian
 * @date 20:39 2022/08/27
 */
@Data
@ApiModel(description = "甘特图分页参数信息")
public class GanttPageVoParam extends PageRequest {

    @ApiModelProperty("甘特图名称")
    private String name;

    @ApiModelProperty("甘特图描述")
    private String desc;

    @Override
    public void post() {
        name = StringUtils.trimToNull(name);
        desc = StringUtils.trimToNull(desc);
        super.post();
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == GanttPoParam.class) {
            GanttPoParam poParam = new GanttPoParam();
            poParam.setName(getName());
            poParam.setDesc(getDesc());
            return (T) poParam;
        }

        return super.convertToPoParam(type);
    }

}
