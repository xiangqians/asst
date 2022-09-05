package org.asst.vo.gantt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.asst.vo.com.ComVo;

/**
 * @author xiangqian
 * @date 15:52 2022/08/27
 */
@Data
@ApiModel(description = "甘特图信息")
public class GanttVo extends ComVo {

    @ApiModelProperty("甘特图名称")
    private String name;

    @ApiModelProperty("甘特图描述")
    private String desc;

    @ApiModelProperty("甘特图内容")
    private String content;

    @ApiModelProperty("甘特图权重，会优先根据权重排序")
    private Long weight;

}
