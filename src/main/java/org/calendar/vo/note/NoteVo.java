package org.calendar.vo.note;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.calendar.vo.com.ComVo;

/**
 * @author xiangqian
 * @date 22:20 2022/08/16
 */
@Data
@ApiModel(description = "笔记信息")
public class NoteVo extends ComVo {

    @ApiModelProperty("笔记标题")
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("笔记权重，会优先根据权重排序")
    private Long weight;

}
