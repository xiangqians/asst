package org.asst.vo.gantt.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.po.param.GanttPoParam;

import javax.validation.constraints.NotBlank;

/**
 * @author xiangqian
 * @date 20:39 2022/08/27
 */
@Data
@ApiModel(description = "修改甘特图信息")
public class GanttModifyVoParam extends GanttAddVoParam {


    @NotBlank(message = "甘特图id不能为空")
    @ApiModelProperty(value = "甘特图id", required = true)
    private String id;

    @Override
    public void post() {
        id = StringUtils.trimToNull(id);
        super.post();
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == GanttPoParam.class) {
            GanttPoParam poParam = super.convertToPoParam(GanttPoParam.class);
            poParam.setId(getId());
            return (T) poParam;
        }

        return super.convertToPoParam(type);
    }
}
