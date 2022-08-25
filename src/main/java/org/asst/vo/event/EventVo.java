package org.asst.vo.event;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.asst.vo.com.ComVo;

import java.time.LocalDateTime;

/**
 * @author xiangqian
 * @date 00:24 2022/08/17
 */
@Data
@ApiModel(description = "事件信息")
public class EventVo extends ComVo {

    @ApiModelProperty("事件标题")
    private String title;

    @ApiModelProperty("事件链接地址")
    private String url;

    @ApiModelProperty("事件内容")
    private String content;

    @ApiModelProperty("事件开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("事件结束时间")
    private LocalDateTime endTime;

}
