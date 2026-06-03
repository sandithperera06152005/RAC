package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class VoucherLinesCriteriaTest {

    @Test
    void newVoucherLinesCriteriaHasAllFiltersNullTest() {
        var voucherLinesCriteria = new VoucherLinesCriteria();
        assertThat(voucherLinesCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void voucherLinesCriteriaFluentMethodsCreatesFiltersTest() {
        var voucherLinesCriteria = new VoucherLinesCriteria();

        setAllFilters(voucherLinesCriteria);

        assertThat(voucherLinesCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void voucherLinesCriteriaCopyCreatesNullFilterTest() {
        var voucherLinesCriteria = new VoucherLinesCriteria();
        var copy = voucherLinesCriteria.copy();

        assertThat(voucherLinesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(voucherLinesCriteria)
        );
    }

    @Test
    void voucherLinesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var voucherLinesCriteria = new VoucherLinesCriteria();
        setAllFilters(voucherLinesCriteria);

        var copy = voucherLinesCriteria.copy();

        assertThat(voucherLinesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(voucherLinesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var voucherLinesCriteria = new VoucherLinesCriteria();

        assertThat(voucherLinesCriteria).hasToString("VoucherLinesCriteria{}");
    }

    private static void setAllFilters(VoucherLinesCriteria voucherLinesCriteria) {
        voucherLinesCriteria.id();
        voucherLinesCriteria.lineID();
        voucherLinesCriteria.grnCode();
        voucherLinesCriteria.grnType();
        voucherLinesCriteria.originalAmount();
        voucherLinesCriteria.amountOwing();
        voucherLinesCriteria.discountAvailable();
        voucherLinesCriteria.discountTaken();
        voucherLinesCriteria.amountReceived();
        voucherLinesCriteria.lmu();
        voucherLinesCriteria.lmd();
        voucherLinesCriteria.accountId();
        voucherLinesCriteria.distinct();
    }

    private static Condition<VoucherLinesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLineID()) &&
                condition.apply(criteria.getGrnCode()) &&
                condition.apply(criteria.getGrnType()) &&
                condition.apply(criteria.getOriginalAmount()) &&
                condition.apply(criteria.getAmountOwing()) &&
                condition.apply(criteria.getDiscountAvailable()) &&
                condition.apply(criteria.getDiscountTaken()) &&
                condition.apply(criteria.getAmountReceived()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getAccountId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<VoucherLinesCriteria> copyFiltersAre(
        VoucherLinesCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLineID(), copy.getLineID()) &&
                condition.apply(criteria.getGrnCode(), copy.getGrnCode()) &&
                condition.apply(criteria.getGrnType(), copy.getGrnType()) &&
                condition.apply(criteria.getOriginalAmount(), copy.getOriginalAmount()) &&
                condition.apply(criteria.getAmountOwing(), copy.getAmountOwing()) &&
                condition.apply(criteria.getDiscountAvailable(), copy.getDiscountAvailable()) &&
                condition.apply(criteria.getDiscountTaken(), copy.getDiscountTaken()) &&
                condition.apply(criteria.getAmountReceived(), copy.getAmountReceived()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getAccountId(), copy.getAccountId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
