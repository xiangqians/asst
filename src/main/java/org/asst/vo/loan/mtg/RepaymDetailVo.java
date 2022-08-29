package org.asst.vo.loan.mtg;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.asst.jackson.BigDecimalJsonSerializer;
import org.asst.o.Vo;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每月还款详情
 *
 * @author xiangqian
 * @date 21:45 2022/08/29
 */
@Data
@ApiModel(description = "还款详情信息")
public class RepaymDetailVo implements Vo {

    @ApiModelProperty("期次")
    private int period;

    @ApiModelProperty("还款日期")
    private LocalDate time;

    @ApiModelProperty("还款金额")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal repaymAmount;

    @ApiModelProperty("偿还本金")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal repayPrincipal;

    @ApiModelProperty("偿还利息")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal repaymInterest;

    @ApiModelProperty("剩余本金")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal remainingPrincipal;

    @ApiModelProperty("剩余利息")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal residualInterest;

}
