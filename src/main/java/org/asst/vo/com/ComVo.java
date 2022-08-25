package org.asst.vo.com;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.asst.o.Vo;

import java.time.LocalDateTime;

/**
 * @author xiangqian
 * @date 22:20 2022/08/16
 */
@Data
public class ComVo implements Vo {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
