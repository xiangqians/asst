package org.asst.vo.gantt.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.po.param.GanttPoParam;
import org.asst.validation.groups.Modify;

import javax.validation.constraints.NotBlank;

/**
 * @author xiangqian
 * @date 20:39 2022/08/27
 */
@Data
@ApiModel(description = "修改甘特图信息")
public class GanttModifyVoParam extends GanttAddVoParam {

    @NotBlank(message = "甘特图id不能为空", groups = {Modify.class})
    @ApiModelProperty(value = "甘特图id", required = true)
    private String id;

    @ApiModelProperty("甘特图内容")
    private String content;

    @ApiModelProperty("甘特图名称")
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void post() {
        id = StringUtils.trimToNull(id);
        content = StringUtils.trimToNull(content);
        super.post();
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == GanttPoParam.class) {
            GanttPoParam poParam = super.convertToPoParam(GanttPoParam.class);
            poParam.setId(getId());
            poParam.setContent(getContent());
            return (T) poParam;
        }

        return super.convertToPoParam(type);
    }
}
