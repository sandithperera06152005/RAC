package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class VoucherCriteriaTest {

    @Test
    void newVoucherCriteriaHasAllFiltersNullTest() {
        var voucherCriteria = new VoucherCriteria();
        assertThat(voucherCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void voucherCriteriaFluentMethodsCreatesFiltersTest() {
        var voucherCriteria = new VoucherCriteria();

        setAllFilters(voucherCriteria);

        assertThat(voucherCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void voucherCriteriaCopyCreatesNullFilterTest() {
        var voucherCriteria = new VoucherCriteria();
        var copy = voucherCriteria.copy();

        assertThat(voucherCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(voucherCriteria)
        );
    }

    @Test
    void voucherCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var voucherCriteria = new VoucherCriteria();
        setAllFilters(voucherCriteria);

        var copy = voucherCriteria.copy();

        assertThat(voucherCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(voucherCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var voucherCriteria = new VoucherCriteria();

        assertThat(voucherCriteria).hasToString("VoucherCriteria{}");
    }

    private static void setAllFilters(VoucherCriteria voucherCriteria) {
        voucherCriteria.id();
        voucherCriteria.code();
        voucherCriteria.voucherDate();
        voucherCriteria.supplierName();
        voucherCriteria.supplierAddress();
        voucherCriteria.totalAmount();
        voucherCriteria.totalAmountInWord();
        voucherCriteria.comments();
        voucherCriteria.lmu();
        voucherCriteria.lmd();
        voucherCriteria.termId();
        voucherCriteria.term();
        voucherCriteria.date();
        voucherCriteria.amountPaid();
        voucherCriteria.supplierID();
        voucherCriteria.isActive();
        voucherCriteria.createdBy();
        voucherCriteria.distinct();
    }

    private static Condition<VoucherCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getVoucherDate()) &&
                condition.apply(criteria.getSupplierName()) &&
                condition.apply(criteria.getSupplierAddress()) &&
                condition.apply(criteria.getTotalAmount()) &&
                condition.apply(criteria.getTotalAmountInWord()) &&
                condition.apply(criteria.getComments()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getTermId()) &&
                condition.apply(criteria.getTerm()) &&
                condition.apply(criteria.getDate()) &&
                condition.apply(criteria.getAmountPaid()) &&
                condition.apply(criteria.getSupplierID()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getCreatedBy()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<VoucherCriteria> copyFiltersAre(VoucherCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getVoucherDate(), copy.getVoucherDate()) &&
                condition.apply(criteria.getSupplierName(), copy.getSupplierName()) &&
                condition.apply(criteria.getSupplierAddress(), copy.getSupplierAddress()) &&
                condition.apply(criteria.getTotalAmount(), copy.getTotalAmount()) &&
                condition.apply(criteria.getTotalAmountInWord(), copy.getTotalAmountInWord()) &&
                condition.apply(criteria.getComments(), copy.getComments()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getTermId(), copy.getTermId()) &&
                condition.apply(criteria.getTerm(), copy.getTerm()) &&
                condition.apply(criteria.getDate(), copy.getDate()) &&
                condition.apply(criteria.getAmountPaid(), copy.getAmountPaid()) &&
                condition.apply(criteria.getSupplierID(), copy.getSupplierID()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getCreatedBy(), copy.getCreatedBy()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
