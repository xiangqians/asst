package org.asst.vo.note.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.asst.o.PoParam;
import org.asst.pagination.PageRequest;
import org.asst.po.param.NotePoParam;

import java.util.Map;

/**
 * @author xiangqian
 * @date 22:23 2022/08/16
 */
@Data
@ApiModel(description = "笔记分页参数信息")
public class NotePageVoParam extends PageRequest {

    @ApiModelProperty("笔记标题")
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("createTime-根据创建时间排序，updateTime-根据更新时间排序；" + SORT_FIELDS_DOCUMENT_DESCRIPTION)
    @Override
    public String getSortFields() {
        return super.getSortFields();
    }

    @Override
    protected Map<String, String> sortableFieldMap() {
        return SORTABLE_FIELD_MAP;
    }

    @Override
    public void post() {
        title = StringUtils.trimToNull(title);
        content = StringUtils.trimToNull(content);
        super.post();
    }

    @Override
    public <T extends PoParam> T convertToPoParam(Class<T> type) {

        if (type == NotePoParam.class) {
            NotePoParam poParam = new NotePoParam();
            poParam.setTitle(getTitle());
            poParam.setContent(getContent());
            return (T) poParam;
        }

        return super.convertToPoParam(type);
    }

}
