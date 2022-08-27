package org.asst.vo.gantt.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.o.VoParam;
import org.asst.po.addl.gantt.GanttValue;
import org.asst.po.param.GanttPoParam;
import org.asst.util.JacksonUtils;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author xiangqian
 * @date 20:39 2022/08/27
 */
@Data
@ApiModel(description = "新增甘特图信息")
public class GanttAddVoParam implements VoParam {

    @NotBlank(message = "甘特图名称不能为空")
    @ApiModelProperty(value = "甘特图名称", required = true)
    private String name;

    @ApiModelProperty("甘特图描述")
    private String desc;

    @Valid
    @NotNull(message = "甘特图值不能为空")
    @ApiModelProperty(value = "甘特图值", required = true)
    private GanttValue value;

    @ApiModelProperty("甘特图内容")
    private String content;

    @ApiModelProperty("甘特图权重，会优先根据权重排序")
    private Long weight;

    @Override
    public void post() {
        name = StringUtils.trimToNull(name);
        desc = StringUtils.trim(desc);
        value.post();
        content = StringUtils.trim(content);
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
            try {
                poParam.setValues(JacksonUtils.toJson(List.of(value)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            poParam.setContent(getContent());
            poParam.setWeight(getWeight());
            return (T) poParam;
        }

        return VoParam.super.convertToPoParam(type);
    }

}
