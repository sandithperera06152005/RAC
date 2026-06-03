package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class VoucherPaymentsDetailsCriteriaTest {

    @Test
    void newVoucherPaymentsDetailsCriteriaHasAllFiltersNullTest() {
        var voucherPaymentsDetailsCriteria = new VoucherPaymentsDetailsCriteria();
        assertThat(voucherPaymentsDetailsCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void voucherPaymentsDetailsCriteriaFluentMethodsCreatesFiltersTest() {
        var voucherPaymentsDetailsCriteria = new VoucherPaymentsDetailsCriteria();

        setAllFilters(voucherPaymentsDetailsCriteria);

        assertThat(voucherPaymentsDetailsCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void voucherPaymentsDetailsCriteriaCopyCreatesNullFilterTest() {
        var voucherPaymentsDetailsCriteria = new VoucherPaymentsDetailsCriteria();
        var copy = voucherPaymentsDetailsCriteria.copy();

        assertThat(voucherPaymentsDetailsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(voucherPaymentsDetailsCriteria)
        );
    }

    @Test
    void voucherPaymentsDetailsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var voucherPaymentsDetailsCriteria = new VoucherPaymentsDetailsCriteria();
        setAllFilters(voucherPaymentsDetailsCriteria);

        var copy = voucherPaymentsDetailsCriteria.copy();

        assertThat(voucherPaymentsDetailsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(voucherPaymentsDetailsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var voucherPaymentsDetailsCriteria = new VoucherPaymentsDetailsCriteria();

        assertThat(voucherPaymentsDetailsCriteria).hasToString("VoucherPaymentsDetailsCriteria{}");
    }

    private static void setAllFilters(VoucherPaymentsDetailsCriteria voucherPaymentsDetailsCriteria) {
        voucherPaymentsDetailsCriteria.id();
        voucherPaymentsDetailsCriteria.lineID();
        voucherPaymentsDetailsCriteria.paymentAmount();
        voucherPaymentsDetailsCriteria.totalVoucherAmount();
        voucherPaymentsDetailsCriteria.checkqueAmount();
        voucherPaymentsDetailsCriteria.checkqueNo();
        voucherPaymentsDetailsCriteria.checkqueDate();
        voucherPaymentsDetailsCriteria.checkqueExpireDate();
        voucherPaymentsDetailsCriteria.bankName();
        voucherPaymentsDetailsCriteria.bankID();
        voucherPaymentsDetailsCriteria.creditCardNo();
        voucherPaymentsDetailsCriteria.creditCardAmount();
        voucherPaymentsDetailsCriteria.reference();
        voucherPaymentsDetailsCriteria.otherDetails();
        voucherPaymentsDetailsCriteria.lmu();
        voucherPaymentsDetailsCriteria.lmd();
        voucherPaymentsDetailsCriteria.termID();
        voucherPaymentsDetailsCriteria.termName();
        voucherPaymentsDetailsCriteria.accountNo();
        voucherPaymentsDetailsCriteria.accountNumber();
        voucherPaymentsDetailsCriteria.accountId();
        voucherPaymentsDetailsCriteria.accountCode();
        voucherPaymentsDetailsCriteria.chequeStatusId();
        voucherPaymentsDetailsCriteria.isDeposit();
        voucherPaymentsDetailsCriteria.depositedDate();
        voucherPaymentsDetailsCriteria.companyBankId();
        voucherPaymentsDetailsCriteria.isBankReconciliation();
        voucherPaymentsDetailsCriteria.distinct();
    }

    private static Condition<VoucherPaymentsDetailsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLineID()) &&
                condition.apply(criteria.getPaymentAmount()) &&
                condition.apply(criteria.getTotalVoucherAmount()) &&
                condition.apply(criteria.getCheckqueAmount()) &&
                condition.apply(criteria.getCheckqueNo()) &&
                condition.apply(criteria.getCheckqueDate()) &&
                condition.apply(criteria.getCheckqueExpireDate()) &&
                condition.apply(criteria.getBankName()) &&
                condition.apply(criteria.getBankID()) &&
                condition.apply(criteria.getCreditCardNo()) &&
                condition.apply(criteria.getCreditCardAmount()) &&
                condition.apply(criteria.getReference()) &&
                condition.apply(criteria.getOtherDetails()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getTermID()) &&
                condition.apply(criteria.getTermName()) &&
                condition.apply(criteria.getAccountNo()) &&
                condition.apply(criteria.getAccountNumber()) &&
                condition.apply(criteria.getAccountId()) &&
                condition.apply(criteria.getAccountCode()) &&
                condition.apply(criteria.getChequeStatusId()) &&
                condition.apply(criteria.getIsDeposit()) &&
                condition.apply(criteria.getDepositedDate()) &&
                condition.apply(criteria.getCompanyBankId()) &&
                condition.apply(criteria.getIsBankReconciliation()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<VoucherPaymentsDetailsCriteria> copyFiltersAre(
        VoucherPaymentsDetailsCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLineID(), copy.getLineID()) &&
                condition.apply(criteria.getPaymentAmount(), copy.getPaymentAmount()) &&
                condition.apply(criteria.getTotalVoucherAmount(), copy.getTotalVoucherAmount()) &&
                condition.apply(criteria.getCheckqueAmount(), copy.getCheckqueAmount()) &&
                condition.apply(criteria.getCheckqueNo(), copy.getCheckqueNo()) &&
                condition.apply(criteria.getCheckqueDate(), copy.getCheckqueDate()) &&
                condition.apply(criteria.getCheckqueExpireDate(), copy.getCheckqueExpireDate()) &&
                condition.apply(criteria.getBankName(), copy.getBankName()) &&
                condition.apply(criteria.getBankID(), copy.getBankID()) &&
                condition.apply(criteria.getCreditCardNo(), copy.getCreditCardNo()) &&
                condition.apply(criteria.getCreditCardAmount(), copy.getCreditCardAmount()) &&
                condition.apply(criteria.getReference(), copy.getReference()) &&
                condition.apply(criteria.getOtherDetails(), copy.getOtherDetails()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getTermID(), copy.getTermID()) &&
                condition.apply(criteria.getTermName(), copy.getTermName()) &&
                condition.apply(criteria.getAccountNo(), copy.getAccountNo()) &&
                condition.apply(criteria.getAccountNumber(), copy.getAccountNumber()) &&
                condition.apply(criteria.getAccountId(), copy.getAccountId()) &&
                condition.apply(criteria.getAccountCode(), copy.getAccountCode()) &&
                condition.apply(criteria.getChequeStatusId(), copy.getChequeStatusId()) &&
                condition.apply(criteria.getIsDeposit(), copy.getIsDeposit()) &&
                condition.apply(criteria.getDepositedDate(), copy.getDepositedDate()) &&
                condition.apply(criteria.getCompanyBankId(), copy.getCompanyBankId()) &&
                condition.apply(criteria.getIsBankReconciliation(), copy.getIsBankReconciliation()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
