package org.asst.vo.event.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.po.param.EventPoParam;

import javax.validation.constraints.NotBlank;

/**
 * @author xiangqian
 * @date 00:28 2022/08/17
 */
@Data
@ApiModel(description = "修改事件信息")
public class EventModifyVoParam extends EventAddVoParam {

    @NotBlank(message = "事件id不能为空")
    @ApiModelProperty(value = "事件id", required = true)
    private String id;

    @Override
    public void post() {
        id = StringUtils.trimToNull(id);
        super.post();
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == EventPoParam.class) {
            EventPoParam poParam = super.convertToPoParam(EventPoParam.class);
            poParam.setId(getId());
            return (T) poParam;
        }

        return super.convertToPoParam(type);
    }

}
