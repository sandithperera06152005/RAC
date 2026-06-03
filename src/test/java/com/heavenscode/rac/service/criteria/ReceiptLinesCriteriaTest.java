package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ReceiptLinesCriteriaTest {

    @Test
    void newReceiptLinesCriteriaHasAllFiltersNullTest() {
        var receiptLinesCriteria = new ReceiptLinesCriteria();
        assertThat(receiptLinesCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void receiptLinesCriteriaFluentMethodsCreatesFiltersTest() {
        var receiptLinesCriteria = new ReceiptLinesCriteria();

        setAllFilters(receiptLinesCriteria);

        assertThat(receiptLinesCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void receiptLinesCriteriaCopyCreatesNullFilterTest() {
        var receiptLinesCriteria = new ReceiptLinesCriteria();
        var copy = receiptLinesCriteria.copy();

        assertThat(receiptLinesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(receiptLinesCriteria)
        );
    }

    @Test
    void receiptLinesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var receiptLinesCriteria = new ReceiptLinesCriteria();
        setAllFilters(receiptLinesCriteria);

        var copy = receiptLinesCriteria.copy();

        assertThat(receiptLinesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(receiptLinesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var receiptLinesCriteria = new ReceiptLinesCriteria();

        assertThat(receiptLinesCriteria).hasToString("ReceiptLinesCriteria{}");
    }

    private static void setAllFilters(ReceiptLinesCriteria receiptLinesCriteria) {
        receiptLinesCriteria.id();
        receiptLinesCriteria.lineid();
        receiptLinesCriteria.invoicecode();
        receiptLinesCriteria.invoicetype();
        receiptLinesCriteria.originalamount();
        receiptLinesCriteria.amountowing();
        receiptLinesCriteria.discountavailable();
        receiptLinesCriteria.discounttaken();
        receiptLinesCriteria.amountreceived();
        receiptLinesCriteria.lmu();
        receiptLinesCriteria.lmd();
        receiptLinesCriteria.accountid();
        receiptLinesCriteria.distinct();
    }

    private static Condition<ReceiptLinesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLineid()) &&
                condition.apply(criteria.getInvoicecode()) &&
                condition.apply(criteria.getInvoicetype()) &&
                condition.apply(criteria.getOriginalamount()) &&
                condition.apply(criteria.getAmountowing()) &&
                condition.apply(criteria.getDiscountavailable()) &&
                condition.apply(criteria.getDiscounttaken()) &&
                condition.apply(criteria.getAmountreceived()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getAccountid()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ReceiptLinesCriteria> copyFiltersAre(
        ReceiptLinesCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLineid(), copy.getLineid()) &&
                condition.apply(criteria.getInvoicecode(), copy.getInvoicecode()) &&
                condition.apply(criteria.getInvoicetype(), copy.getInvoicetype()) &&
                condition.apply(criteria.getOriginalamount(), copy.getOriginalamount()) &&
                condition.apply(criteria.getAmountowing(), copy.getAmountowing()) &&
                condition.apply(criteria.getDiscountavailable(), copy.getDiscountavailable()) &&
                condition.apply(criteria.getDiscounttaken(), copy.getDiscounttaken()) &&
                condition.apply(criteria.getAmountreceived(), copy.getAmountreceived()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getAccountid(), copy.getAccountid()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
