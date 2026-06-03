package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InvoiceServiceCommonCriteriaTest {

    @Test
    void newInvoiceServiceCommonCriteriaHasAllFiltersNullTest() {
        var invoiceServiceCommonCriteria = new InvoiceServiceCommonCriteria();
        assertThat(invoiceServiceCommonCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void invoiceServiceCommonCriteriaFluentMethodsCreatesFiltersTest() {
        var invoiceServiceCommonCriteria = new InvoiceServiceCommonCriteria();

        setAllFilters(invoiceServiceCommonCriteria);

        assertThat(invoiceServiceCommonCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void invoiceServiceCommonCriteriaCopyCreatesNullFilterTest() {
        var invoiceServiceCommonCriteria = new InvoiceServiceCommonCriteria();
        var copy = invoiceServiceCommonCriteria.copy();

        assertThat(invoiceServiceCommonCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(invoiceServiceCommonCriteria)
        );
    }

    @Test
    void invoiceServiceCommonCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var invoiceServiceCommonCriteria = new InvoiceServiceCommonCriteria();
        setAllFilters(invoiceServiceCommonCriteria);

        var copy = invoiceServiceCommonCriteria.copy();

        assertThat(invoiceServiceCommonCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(invoiceServiceCommonCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var invoiceServiceCommonCriteria = new InvoiceServiceCommonCriteria();

        assertThat(invoiceServiceCommonCriteria).hasToString("InvoiceServiceCommonCriteria{}");
    }

    private static void setAllFilters(InvoiceServiceCommonCriteria invoiceServiceCommonCriteria) {
        invoiceServiceCommonCriteria.id();
        invoiceServiceCommonCriteria.invoiceId();
        invoiceServiceCommonCriteria.lineId();
        invoiceServiceCommonCriteria.optionId();
        invoiceServiceCommonCriteria.mainId();
        invoiceServiceCommonCriteria.code();
        invoiceServiceCommonCriteria.name();
        invoiceServiceCommonCriteria.description();
        invoiceServiceCommonCriteria.value();
        invoiceServiceCommonCriteria.addedById();
        invoiceServiceCommonCriteria.discount();
        invoiceServiceCommonCriteria.servicePrice();
        invoiceServiceCommonCriteria.distinct();
    }

    private static Condition<InvoiceServiceCommonCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInvoiceId()) &&
                condition.apply(criteria.getLineId()) &&
                condition.apply(criteria.getOptionId()) &&
                condition.apply(criteria.getMainId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getValue()) &&
                condition.apply(criteria.getAddedById()) &&
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getServicePrice()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InvoiceServiceCommonCriteria> copyFiltersAre(
        InvoiceServiceCommonCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInvoiceId(), copy.getInvoiceId()) &&
                condition.apply(criteria.getLineId(), copy.getLineId()) &&
                condition.apply(criteria.getOptionId(), copy.getOptionId()) &&
                condition.apply(criteria.getMainId(), copy.getMainId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getValue(), copy.getValue()) &&
                condition.apply(criteria.getAddedById(), copy.getAddedById()) &&
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getServicePrice(), copy.getServicePrice()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
