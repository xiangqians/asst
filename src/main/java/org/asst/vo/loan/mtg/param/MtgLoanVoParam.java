package org.asst.vo.loan.mtg.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.asst.o.VoParam;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author xiangqian
 * @date 21:25 2022/08/29
 */
@Data
@ApiModel(description = "按揭贷款信息")
public class MtgLoanVoParam implements VoParam {

    @Min(value = 1, message = "贷款金额必须大于0")
    @NotNull(message = "贷款金额不能为空")
    @ApiModelProperty(value = "贷款金额（元）", required = true)
    private Integer loanAmount;

    @Min(value = 1, message = "贷款期限必须大于0")
    @NotNull(message = "贷款期限不能为空")
    @ApiModelProperty(value = "贷款期限（月）", required = true)
    private Integer loanTerm;

    @Min(value = 1, message = "年利率必须大于0")
    @NotNull(message = "年利率不能为空")
    @ApiModelProperty(value = "年利率（%）", required = true)
    private Double apr;

    @NotNull(message = "贷款时间不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "贷款时间", required = true)
    private LocalDate loanTime;

    private Double salary;

}
