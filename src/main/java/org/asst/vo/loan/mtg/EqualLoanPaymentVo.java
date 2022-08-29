package org.asst.vo.loan.mtg;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.asst.jackson.BigDecimalJsonSerializer;
import org.asst.o.Vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiangqian
 * @date 21:23 2022/08/29
 */
@Data
@ApiModel(description = "等额本息按揭贷款信息")
public class EqualLoanPaymentVo implements Vo {

    @ApiModelProperty("每期还款金额")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal repaymAmountPerInstal;

    @ApiModelProperty("利息总额")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal totalInterest;

    @ApiModelProperty("还款总额")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal totalRepaym;

    @ApiModelProperty("还款详情")
    private List<RepaymDetailVo> repaymDetails;

}
