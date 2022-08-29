package org.asst.service;

import lombok.Data;
import org.asst.service.impl.MtgLoanServiceImpl;
import org.asst.vo.loan.mtg.EqualLoanPaymentVo;
import org.asst.vo.loan.mtg.EqualPrincipalPaymentVo;
import org.asst.vo.loan.mtg.param.MtgLoanVoParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 按揭贷款
 * Mortgage Loan
 *
 * @author xiangqian
 * @date 21:10 2022/08/29
 */
public interface MtgLoanService {

    /**
     * 等额本息
     *
     * @param voParam
     * @return
     */
    EqualLoanPaymentVo equalLoanPayment(MtgLoanVoParam voParam);

    /**
     * 等额本金
     *
     * @param voParam
     * @return
     */
    EqualPrincipalPaymentVo equalPrincipalPayment(MtgLoanVoParam voParam);

    // 预算
    BudgetResult budget(MtgLoanVoParam voParam);

    @Data
    class BudgetResult {

        // 薪资（元）
        private BigDecimal salary;

        // 贷款金额（W）
        private BigDecimal loanAmount;

        // 贷款期限（年）
        private int loanTerm;

        // 年利率（%）
        private BigDecimal apr;

        // 贷款时间
        private LocalDate loanTime;

        private List<BudgetInfo> eiopaiRepaymentMethodBudgetInfos;
        private List<BudgetInfo> eiopRepaymentMethodBudgetInfos;

    }

    @Data
    class BudgetInfo {

        // 贷款期限（年）
        private int loanTerm;

        // 期次
        private int period;

        // 还款日期
        private LocalDate time;

        // 偿还本金
        private BigDecimal repayPrincipal;

        // 剩余本金
        private BigDecimal remainingPrincipal;

        // 偿还利息
        private BigDecimal repaymentInterest;

        // 剩余利息
        private BigDecimal residualInterest;

        public BudgetInfo(int loanTerm) {
            this.loanTerm = loanTerm;
        }

    }

}
