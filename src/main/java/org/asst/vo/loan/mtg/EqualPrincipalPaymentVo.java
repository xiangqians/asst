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
 * @date 21:24 2022/08/29
 */
@Data
@ApiModel(description = "等额本金按揭贷款信息")
public class EqualPrincipalPaymentVo implements Vo {

    @ApiModelProperty("首期还款金额")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal firstRepaymAmount;

    @ApiModelProperty("每期递减金额")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal dcrgAmountEachPeriod;

    @ApiModelProperty("利息总额")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal totalInterest;

    @ApiModelProperty("还款总额")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal totalRepaym;

    @ApiModelProperty("还款详情")
    private List<RepaymDetailVo> repaymDetails;

}
