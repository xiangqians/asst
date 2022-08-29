package org.asst.service.impl;

import org.asst.service.MtgLoanService;
import org.asst.util.DateUtils;
import org.asst.vo.loan.mtg.EqualLoanPaymentVo;
import org.asst.vo.loan.mtg.EqualPrincipalPaymentVo;
import org.asst.vo.loan.mtg.RepaymDetailVo;
import org.asst.vo.loan.mtg.param.MtgLoanVoParam;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * LPR，贷款市场报价利率（Loan Prime Rate）是由具有代表性的报价行，根据本行对最优质客户的贷款利率，以公开市场操作利率加点形成的报价。
 * 并由中国人民银行授权全国银行间同业拆借中心计算并公布的基础性的贷款参考利率，各金融机构应主要参考LPR进行贷款定价。
 * 现在的民间借贷新规，即是按最高4倍LPR来限制法律保护的利率范围。
 * <p>
 * APR，年度利率（Annual Percentage Rate）是指一年期限内利息额与存款本金或贷款本金的比率，就是我们通常说的“年化综合成本”。
 * 网贷机构常用的利率计算方式即年度利率，公式：年利率 =（还款金额 - 本金）/ 本金。
 * APR算法，则是按照放款金额进行计息，即当期利息的计息基数是期初放款金额；
 * <p>
 * IRR，内部收益率（Internal Rate of Return），是资金流入现值总额与资金流出现值总额相等、净现值等于零时的折现率。
 * IRR算法，是按照贷款余额进行计息的，即当期利息的计息基数是剩余贷款本金；
 * <p>
 * 年度利率，简称APR，英文为Annual Percentage Rate，是利息计算年利率名词。简单的说，利息年利率就是APR。
 * 中文名年度利率外文名Annual Percentage Rate
 * 最近有些朋友在问按揭贷款年利率是多少，按揭贷款利息怎么算，今天我爱卡小编就来和大家一起聊一聊这些事情吧。
 * 按照规定，各个银行的按揭贷款年利率是根据央行的贷款基准利率确定的，各银行在央行的基准利率的基础上有所浮动。
 * 现在为止央行公布的贷款年利率如下：
 * 6个月以下(含)：4.35% ;
 * 6月-1年(含)：4.35%;
 * 1-3年(含)：4.75%;
 * 3-5年(含)：4.75%;
 * 5-30年(含)：4.90% 。
 * 具体的每家银行、各个地方的利率都会有所差异，大家需要咨询当地银以保证信息的准确定和有效性。
 * 那么按揭贷款利息怎么算呢?
 * 按揭贷款分为两种还款方式：等额本息法、等额本金法。
 * 等额本息法：
 * 月还款额=本金*月利率*[(1+月利率)^n/[(1+月利率)^n-1];
 * 月利率=年利率/12;
 * 总利息=月还款额*贷款月数-本金;
 * (n为贷款月数)
 * <p>
 * 等额本金法：
 * 月还款额=本金/n+剩余本金*月利率;
 * 总利息=本金*月利率*(贷款月数/2+0.5)
 * <p>
 * <p>
 * ps：代码实现源于网上，并做了部分修改
 *
 * @author xiangqian
 * @date 21:34 2022/08/29
 */
@Service
public class MtgLoanServiceImpl implements MtgLoanService {

    private int scale;
    private RoundingMode roundingMode;

    public MtgLoanServiceImpl() {
        this.scale = 20;
        this.roundingMode = RoundingMode.DOWN;
    }

    @Override
    public EqualLoanPaymentVo equalLoanPayment(MtgLoanVoParam voParam) {
        EqualLoanPaymentVo vo = new EqualLoanPaymentVo();

        // 月利率
        BigDecimal apr = new BigDecimal(String.valueOf(voParam.getApr()));
        BigDecimal monthlyRate = apr
                .divide(new BigDecimal("100"), scale, roundingMode)
                .divide(new BigDecimal("12"), scale, roundingMode);

        // 还款金额（本金+利息）
        Integer loanTerm = voParam.getLoanTerm();
        BigDecimal loanAmount = new BigDecimal(String.valueOf(voParam.getLoanAmount()));
        double pow = Math.pow(1 + monthlyRate.doubleValue(), loanTerm);
        BigDecimal totalMoney = new BigDecimal(String.valueOf(loanTerm))
                .multiply(loanAmount)
                .multiply(monthlyRate)
                .multiply(new BigDecimal(String.valueOf(pow)))
                .divide(new BigDecimal(String.valueOf(pow)).subtract(new BigDecimal("1")), scale, roundingMode);

        // floor函数 保留两位小数
        totalMoney = new BigDecimal(String.valueOf(Math.floor(totalMoney.multiply(new BigDecimal("100")).add(new BigDecimal("0.5")).doubleValue())))
                .divide(new BigDecimal("100"), scale, roundingMode);

        // 利息总额
        BigDecimal totalInterests = totalMoney.subtract(loanAmount);
        totalInterests = new BigDecimal(String.valueOf(Math.floor(totalInterests.multiply(new BigDecimal("100")).add(new BigDecimal("0.5")).doubleValue())))
                .divide(new BigDecimal("100"), scale, roundingMode);

        vo.setTotalRepaym(totalMoney);
        vo.setTotalInterest(totalInterests);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateUtils.dateToTimestamp(voParam.getLoanTime()));

        List<RepaymDetailVo> repaymentDetailsList = new ArrayList<>();
        BigDecimal remains = loanAmount;
        for (int i = 0; i < loanTerm; i++) {
            if (i == loanTerm - 1) {
                // 偿还本金
                BigDecimal repayPrincipal = new BigDecimal(String.valueOf(Math.floor(remains.multiply(new BigDecimal("100"))
                        .add(new BigDecimal("0.5")).doubleValue()))).divide(new BigDecimal("100"), scale, roundingMode);

                // 偿还利息
                BigDecimal repaymentInterest = new BigDecimal(String.valueOf(Math.floor(remains
                        .multiply(monthlyRate)
                        .multiply(new BigDecimal("100"))
                        .add(new BigDecimal("0.5"))
                        .doubleValue())
                )).divide(new BigDecimal("100"), scale, roundingMode);

                // 还款金额
                BigDecimal repaymentAmount = new BigDecimal(String.valueOf(Math.floor(repayPrincipal.add(repaymentInterest)
                        .multiply(new BigDecimal("100"))
                        .add(new BigDecimal("0.5"))
                        .doubleValue())
                )).divide(new BigDecimal("100"), scale, roundingMode);

                RepaymDetailVo repaymDetail = new RepaymDetailVo();
                repaymDetail.setPeriod(i + 1);
                calendar.add(Calendar.MONTH, 1); // 月，+1
                repaymDetail.setTime(DateUtils.timestampToLocalDateTime(calendar.getTimeInMillis()).toLocalDate());
                repaymDetail.setRepaymAmount(repaymentAmount);
                repaymDetail.setRepayPrincipal(repayPrincipal);
                repaymDetail.setRepaymInterest(repaymentInterest);
                repaymDetail.setRemainingPrincipal(new BigDecimal("0"));
                repaymDetail.setResidualInterest(new BigDecimal("0"));
                repaymentDetailsList.add(repaymDetail);
                break;
            }
            // 由于精度问题 最后一个月实际的本金会有差别 需要单独计算

            // 偿还利息
            BigDecimal repaymentInterest = new BigDecimal(String.valueOf(Math.floor(remains.multiply(monthlyRate)
                    .multiply(new BigDecimal("100"))
                    .add(new BigDecimal("0.5")).doubleValue())
            )).divide(new BigDecimal("100"), scale, roundingMode);

            // 还款金额
            BigDecimal repaymentAmount = new BigDecimal(String.valueOf(Math.floor(totalMoney.divide(new BigDecimal(String.valueOf(loanTerm)), scale, roundingMode)
                    .multiply(new BigDecimal("100"))
                    .add(new BigDecimal("0.5")).doubleValue()))).divide(new BigDecimal("100"), scale, roundingMode);

            // 偿还本金
            BigDecimal repayPrincipal = new BigDecimal(String.valueOf(Math.floor(repaymentAmount
                    .subtract(repaymentInterest)
                    .multiply(new BigDecimal("100"))
                    .add(new BigDecimal("0.5")).doubleValue()))).divide(new BigDecimal("100"), scale, roundingMode);

            remains = remains.subtract(repayPrincipal);

            RepaymDetailVo repaymDetail = new RepaymDetailVo();
            repaymDetail.setPeriod(i + 1);
            calendar.add(Calendar.MONTH, 1); // 月，+1
            repaymDetail.setTime(DateUtils.timestampToLocalDateTime(calendar.getTimeInMillis()).toLocalDate());
            repaymDetail.setRepaymAmount(repaymentAmount);
            repaymDetail.setRepayPrincipal(repayPrincipal);
            repaymDetail.setRepaymInterest(repaymentInterest);
            repaymDetail.setRemainingPrincipal(new BigDecimal("0"));
            repaymDetail.setResidualInterest(new BigDecimal("0"));
            repaymentDetailsList.add(repaymDetail);
        }

        BigDecimal remainingPrincipal = loanAmount; // 剩余本金
        BigDecimal residualInterest = totalInterests;  // 剩余利息
        for (RepaymDetailVo repaymentDetails : repaymentDetailsList) {
            // 剩余本金
            remainingPrincipal = remainingPrincipal.subtract(repaymentDetails.getRepayPrincipal());
            repaymentDetails.setRemainingPrincipal(remainingPrincipal);

            // 剩余利息
            residualInterest = residualInterest.subtract(repaymentDetails.getRepaymInterest());
            repaymentDetails.setResidualInterest(residualInterest);
        }
        vo.setRepaymDetails(repaymentDetailsList);

        //
        vo.setRepaymAmountPerInstal(repaymentDetailsList.isEmpty() ? new BigDecimal("0") : repaymentDetailsList.get(0).getRepaymAmount());

        return vo;
    }

    @Override
    public EqualPrincipalPaymentVo equalPrincipalPayment(MtgLoanVoParam voParam) {
        EqualPrincipalPaymentVo vo = new EqualPrincipalPaymentVo();

        //每月还款详情
        List<RepaymDetailVo> repaymentDetailsList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateUtils.dateToTimestamp(voParam.getLoanTime()));

        BigDecimal apr = new BigDecimal(String.valueOf(voParam.getApr()));
        BigDecimal mIns = apr.divide(new BigDecimal("100"), scale, roundingMode)
                .divide(new BigDecimal("12"), scale, roundingMode); //月利率
        BigDecimal loanAmount = new BigDecimal(String.valueOf(voParam.getLoanAmount()));
        BigDecimal remains = loanAmount;
        BigDecimal sum = new BigDecimal("0"); // 总计还款金额

        int loanTerm = voParam.getLoanTerm();
        for (int i = 0; i < loanTerm; i++) {
            // 每月还款本金
            BigDecimal repayPrincipal = new BigDecimal(String.valueOf(Math.floor(
                    loanAmount.divide(new BigDecimal(String.valueOf(loanTerm)), scale, roundingMode)
                            .multiply(new BigDecimal("100"))
                            .add(new BigDecimal("0.5"))
                            .doubleValue()
            ))).divide(new BigDecimal("100"), scale, roundingMode);

            // 每月还款利息
            BigDecimal repaymentInterest = new BigDecimal(String.valueOf(Math.floor(
                    remains.multiply(mIns)
                            .multiply(new BigDecimal("100"))
                            .add(new BigDecimal("0.5"))
                            .doubleValue()
            ))).divide(new BigDecimal("100"), scale, roundingMode);

            // 每月还款总额
            BigDecimal repaymentAmount = new BigDecimal(String.valueOf(Math.floor(
                    repayPrincipal.add(repaymentInterest)
                            .multiply(new BigDecimal("100"))
                            .add(new BigDecimal("0.5"))
                            .doubleValue()
            ))).divide(new BigDecimal("100"), scale, roundingMode);

            RepaymDetailVo repaymDetail = new RepaymDetailVo();
            repaymDetail.setPeriod(i + 1);
            calendar.add(Calendar.MONTH, 1); // 月，+1
            repaymDetail.setTime(DateUtils.timestampToLocalDateTime(calendar.getTimeInMillis()).toLocalDate());
            repaymDetail.setRepaymAmount(repaymentAmount);
            repaymDetail.setRepayPrincipal(repayPrincipal);
            repaymDetail.setRepaymInterest(repaymentInterest);
            repaymDetail.setRemainingPrincipal(new BigDecimal("0"));
            repaymDetail.setResidualInterest(new BigDecimal("0"));
            repaymentDetailsList.add(repaymDetail);

            sum = sum.add(repaymentAmount);
            remains = remains.subtract(repayPrincipal);
        }

//        totalMoney = Math.floor(sum * 100 + 0.5) / 100;
        BigDecimal totalRepayment = new BigDecimal(String.valueOf(Math.floor(
                sum.multiply(new BigDecimal("100"))
                        .add(new BigDecimal("0.5"))
                        .doubleValue()
        ))).divide(new BigDecimal("100"), scale, roundingMode);
        vo.setTotalRepaym(totalRepayment);

//        totalInterests = totalMoney - loanAmount;
//        totalInterests = Math.floor(totalInterests * 100 + 0.5) / 100;
        BigDecimal totalInterest = new BigDecimal(String.valueOf(Math.floor(
                totalRepayment.subtract(loanAmount)
                        .multiply(new BigDecimal("100"))
                        .add(new BigDecimal("0.5"))
                        .doubleValue()
        ))).divide(new BigDecimal("100"));
        vo.setTotalInterest(totalInterest);

        BigDecimal remainingPrincipal = loanAmount; // 剩余本金
        BigDecimal residualInterest = totalInterest;  // 剩余利息
        for (RepaymDetailVo repaymDetail : repaymentDetailsList) {
            // 剩余本金
            remainingPrincipal = remainingPrincipal.subtract(repaymDetail.getRepayPrincipal());
            repaymDetail.setRemainingPrincipal(remainingPrincipal);

            // 剩余利息
            residualInterest = residualInterest.subtract(repaymDetail.getRepaymInterest());
            repaymDetail.setResidualInterest(residualInterest);
        }
        vo.setRepaymDetails(repaymentDetailsList);

        // 首期还款金额
        vo.setFirstRepaymAmount(repaymentDetailsList.isEmpty() ? new BigDecimal("0") : repaymentDetailsList.get(0).getRepaymAmount());
        // 每期递减金额
        vo.setDcrgAmountEachPeriod(repaymentDetailsList.size() > 2 ?
                repaymentDetailsList.get(0).getRepaymAmount().subtract(repaymentDetailsList.get(1).getRepaymAmount()) : new BigDecimal("0"));

        return vo;
    }


    //////////////////////////////////


    @Override
    public BudgetResult budget(MtgLoanVoParam voParam) {
        BudgetResult budgetResult = new BudgetResult();
        BigDecimal salary = new BigDecimal(String.valueOf(voParam.getSalary()));
        budgetResult.setSalary(salary);
        BigDecimal loanAmount = new BigDecimal(String.valueOf(voParam.getLoanAmount()));
        budgetResult.setLoanAmount(loanAmount);
        budgetResult.setApr(new BigDecimal(String.valueOf(voParam.getApr())));
        budgetResult.setLoanTime(voParam.getLoanTime());

        int loanTerms = 30; // 年

        // EIOPAI
        int loanTermTmp = 0;
        List<BudgetInfo> eiopaiRepaymentMethodBudgetInfos = new ArrayList<>();
        while (++loanTermTmp <= loanTerms) {
            voParam.setLoanTerm(loanTermTmp * 12);
            EqualLoanPaymentVo eiopaiRepaymentMethod = equalLoanPayment(voParam);
            BigDecimal eiopaiResidualSalary = new BigDecimal("0");
            List<RepaymDetailVo> eiopaiRepaymentDetailsList = eiopaiRepaymentMethod.getRepaymDetails();
            for (RepaymDetailVo eiopaiRepaymentDetails : eiopaiRepaymentDetailsList) {
                eiopaiResidualSalary = eiopaiResidualSalary.add(salary.subtract(eiopaiRepaymentDetails.getRepaymAmount()));
                if (eiopaiResidualSalary.compareTo(eiopaiRepaymentDetails.getRemainingPrincipal()) >= 0) {
                    BudgetInfo eiopaiRepaymentMethodBudgetInfo = new BudgetInfo(loanTermTmp);
                    eiopaiRepaymentMethodBudgetInfo.setPeriod(eiopaiRepaymentDetails.getPeriod());
                    eiopaiRepaymentMethodBudgetInfo.setTime(eiopaiRepaymentDetails.getTime());
                    eiopaiRepaymentMethodBudgetInfo.setRepayPrincipal(loanAmount.subtract(eiopaiRepaymentDetails.getRemainingPrincipal()));
                    eiopaiRepaymentMethodBudgetInfo.setRemainingPrincipal(eiopaiRepaymentDetails.getRemainingPrincipal());
                    eiopaiRepaymentMethodBudgetInfo.setRepaymentInterest(eiopaiRepaymentMethod.getTotalInterest().subtract(eiopaiRepaymentDetails.getResidualInterest()));
                    eiopaiRepaymentMethodBudgetInfo.setResidualInterest(eiopaiRepaymentDetails.getResidualInterest());
                    eiopaiRepaymentMethodBudgetInfos.add(eiopaiRepaymentMethodBudgetInfo);
                    break;
                }
            }
        }
        budgetResult.setEiopaiRepaymentMethodBudgetInfos(eiopaiRepaymentMethodBudgetInfos);

        // EIOP
        List<BudgetInfo> eiopRepaymentMethodBudgetInfos = new ArrayList<>();
        loanTermTmp = 0;
        while (++loanTermTmp <= loanTerms) {
            voParam.setLoanTerm(loanTermTmp * 12);
            EqualPrincipalPaymentVo eiopRepaymentMethod = equalPrincipalPayment(voParam);
            BigDecimal eiopResidualSalary = new BigDecimal("0");
            List<RepaymDetailVo> eiopRepaymentDetailsList = eiopRepaymentMethod.getRepaymDetails();
            for (RepaymDetailVo eiopRepaymentDetails : eiopRepaymentDetailsList) {
                eiopResidualSalary = eiopResidualSalary.add(salary.subtract(eiopRepaymentDetails.getRepaymAmount()));
                if (eiopResidualSalary.compareTo(eiopRepaymentDetails.getRemainingPrincipal()) == 1) {
                    BudgetInfo eiopRepaymentMethodBudgetInfo = new BudgetInfo(loanTermTmp);
                    eiopRepaymentMethodBudgetInfo.setPeriod(eiopRepaymentDetails.getPeriod());
                    eiopRepaymentMethodBudgetInfo.setTime(eiopRepaymentDetails.getTime());
                    eiopRepaymentMethodBudgetInfo.setRepayPrincipal(loanAmount.subtract(eiopRepaymentDetails.getRemainingPrincipal()));
                    eiopRepaymentMethodBudgetInfo.setRemainingPrincipal(eiopRepaymentDetails.getRemainingPrincipal());
                    eiopRepaymentMethodBudgetInfo.setRepaymentInterest(eiopRepaymentMethod.getTotalInterest().subtract(eiopRepaymentDetails.getResidualInterest()));
                    eiopRepaymentMethodBudgetInfo.setResidualInterest(eiopRepaymentDetails.getResidualInterest());
                    eiopRepaymentMethodBudgetInfos.add(eiopRepaymentMethodBudgetInfo);
                    break;
                }
            }
        }
        budgetResult.setEiopRepaymentMethodBudgetInfos(eiopRepaymentMethodBudgetInfos);

        return budgetResult;
    }

}
