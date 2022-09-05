package org.asst.vo.gantt.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.o.VoParam;
import org.asst.po.param.GanttPoParam;
import org.asst.validation.groups.Add;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @author xiangqian
 * @date 20:39 2022/08/27
 */
@Data
@ApiModel(description = "新增甘特图信息")
public class GanttAddVoParam implements VoParam {

    @NotBlank(message = "甘特图名称不能为空", groups = {Add.class})
    @ApiModelProperty(value = "甘特图名称", required = true)
    private String name;

    @ApiModelProperty("甘特图描述")
    private String desc;

    @ApiModelProperty("甘特图权重，会优先根据权重排序")
    private Long weight;

    @Override
    public void post() {
        name = StringUtils.trimToNull(name);
        desc = StringUtils.trim(desc);
        if (Objects.nonNull(weight)) {
            Assert.isTrue(weight >= 0, "笔记权重必须大于0");
        }
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == GanttPoParam.class) {
            GanttPoParam poParam = new GanttPoParam();
            poParam.setName(getName());
            poParam.setDesc(getDesc());
            poParam.setWeight(getWeight());
            return (T) poParam;
        }

        return VoParam.super.convertToPoParam(type);
    }

}
