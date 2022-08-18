package org.calendar.vo.note.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.calendar.o.PoParam;
import org.calendar.o.VoParam;
import org.calendar.po.param.NotePoParam;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @author xiangqian
 * @date 22:22 2022/08/16
 */
@Data
@ApiModel(description = "新增笔记信息")
public class NoteAddVoParam implements VoParam {

    @NotBlank(message = "笔记标题不能为空")
    @ApiModelProperty(value = "笔记标题", required = true)
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("笔记权重，会优先根据权重排序")
    private Long weight;

    @Override
    public void post() {
        title = StringUtils.trimToNull(title);
        content = StringUtils.trim(content);
        if (Objects.nonNull(weight)) {
            Assert.isTrue(weight >= 0, "笔记权重必须大于0");
        }
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == NotePoParam.class) {
            NotePoParam poParam = new NotePoParam();
            poParam.setTitle(getTitle());
            poParam.setContent(getContent());
            poParam.setWeight(getWeight());
            return (T) poParam;
        }

        return VoParam.super.convertToPoParam(type);
    }

}
