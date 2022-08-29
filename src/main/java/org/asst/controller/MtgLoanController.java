package org.asst.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.asst.resp.Response;
import org.asst.resp.StatusCodeImpl;
import org.asst.service.MtgLoanService;
import org.asst.vo.loan.mtg.EqualLoanPaymentVo;
import org.asst.vo.loan.mtg.EqualPrincipalPaymentVo;
import org.asst.vo.loan.mtg.param.MtgLoanVoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author xiangqian
 * @date 22:33 2022/08/29
 */
@RestController
@RequestMapping("/loan/mtg")
@Api(value = "loan_mtg", tags = "按揭贷款信息管理")
public class MtgLoanController {

    @Autowired
    private MtgLoanService mtgLoanService;

    @GetMapping("/equalLoanPayment")
    @ApiOperation("等额本息")
    public Response<EqualLoanPaymentVo> equalLoanPayment(@Valid MtgLoanVoParam voParam) throws Exception {
        return Response.<EqualLoanPaymentVo>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(mtgLoanService.equalLoanPayment(voParam))
                .build();
    }

    @GetMapping("/equalPrincipalPayment")
    @ApiOperation("等额本金")
    public Response<EqualPrincipalPaymentVo> equalPrincipalPayment(@Valid MtgLoanVoParam voParam) throws Exception {
        return Response.<EqualPrincipalPaymentVo>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(mtgLoanService.equalPrincipalPayment(voParam))
                .build();
    }


    @GetMapping("/budget")
    @ApiOperation("预算")
    public Response<MtgLoanService.BudgetResult> budget(@Valid MtgLoanVoParam voParam) throws Exception {
        return Response.<MtgLoanService.BudgetResult>builder()
                .statusCode(StatusCodeImpl.OK)
                .body(mtgLoanService.budget(voParam))
                .build();
    }

}
