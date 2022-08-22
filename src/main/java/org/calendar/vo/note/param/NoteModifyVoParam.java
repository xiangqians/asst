package org.calendar.vo.note.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.calendar.o.PoParam;
import org.calendar.po.param.NotePoParam;
import org.calendar.validation.groups.Modify;

import javax.validation.constraints.NotBlank;

/**
 * @author xiangqian
 * @date 22:23 2022/08/16
 */
@Data
@ApiModel(description = "修改笔记信息")
public class NoteModifyVoParam extends NoteAddVoParam {

    @NotBlank(message = "笔记id不能为空", groups = {Modify.class})
    @ApiModelProperty(value = "笔记id", required = true)
    private String id;

    @Override
    public void post() {
        id = StringUtils.trimToNull(id);
        super.post();
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == NotePoParam.class) {
            NotePoParam poParam = super.convertToPoParam(NotePoParam.class);
            poParam.setId(getId());
            return (T) poParam;
        }

        return super.convertToPoParam(type);
    }

}
