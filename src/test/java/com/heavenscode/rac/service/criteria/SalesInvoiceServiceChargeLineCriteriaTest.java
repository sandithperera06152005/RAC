package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SalesInvoiceServiceChargeLineCriteriaTest {

    @Test
    void newSalesInvoiceServiceChargeLineCriteriaHasAllFiltersNullTest() {
        var salesInvoiceServiceChargeLineCriteria = new SalesInvoiceServiceChargeLineCriteria();
        assertThat(salesInvoiceServiceChargeLineCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void salesInvoiceServiceChargeLineCriteriaFluentMethodsCreatesFiltersTest() {
        var salesInvoiceServiceChargeLineCriteria = new SalesInvoiceServiceChargeLineCriteria();

        setAllFilters(salesInvoiceServiceChargeLineCriteria);

        assertThat(salesInvoiceServiceChargeLineCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void salesInvoiceServiceChargeLineCriteriaCopyCreatesNullFilterTest() {
        var salesInvoiceServiceChargeLineCriteria = new SalesInvoiceServiceChargeLineCriteria();
        var copy = salesInvoiceServiceChargeLineCriteria.copy();

        assertThat(salesInvoiceServiceChargeLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceServiceChargeLineCriteria)
        );
    }

    @Test
    void salesInvoiceServiceChargeLineCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var salesInvoiceServiceChargeLineCriteria = new SalesInvoiceServiceChargeLineCriteria();
        setAllFilters(salesInvoiceServiceChargeLineCriteria);

        var copy = salesInvoiceServiceChargeLineCriteria.copy();

        assertThat(salesInvoiceServiceChargeLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceServiceChargeLineCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var salesInvoiceServiceChargeLineCriteria = new SalesInvoiceServiceChargeLineCriteria();

        assertThat(salesInvoiceServiceChargeLineCriteria).hasToString("SalesInvoiceServiceChargeLineCriteria{}");
    }

    private static void setAllFilters(SalesInvoiceServiceChargeLineCriteria salesInvoiceServiceChargeLineCriteria) {
        salesInvoiceServiceChargeLineCriteria.id();
        salesInvoiceServiceChargeLineCriteria.invoiceId();
        salesInvoiceServiceChargeLineCriteria.lineId();
        salesInvoiceServiceChargeLineCriteria.optionId();
        salesInvoiceServiceChargeLineCriteria.serviceName();
        salesInvoiceServiceChargeLineCriteria.serviceDescription();
        salesInvoiceServiceChargeLineCriteria.value();
        salesInvoiceServiceChargeLineCriteria.isCustomerService();
        salesInvoiceServiceChargeLineCriteria.discount();
        salesInvoiceServiceChargeLineCriteria.servicePrice();
        salesInvoiceServiceChargeLineCriteria.distinct();
    }

    private static Condition<SalesInvoiceServiceChargeLineCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInvoiceId()) &&
                condition.apply(criteria.getLineId()) &&
                condition.apply(criteria.getOptionId()) &&
                condition.apply(criteria.getServiceName()) &&
                condition.apply(criteria.getServiceDescription()) &&
                condition.apply(criteria.getValue()) &&
                condition.apply(criteria.getIsCustomerService()) &&
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getServicePrice()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SalesInvoiceServiceChargeLineCriteria> copyFiltersAre(
        SalesInvoiceServiceChargeLineCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInvoiceId(), copy.getInvoiceId()) &&
                condition.apply(criteria.getLineId(), copy.getLineId()) &&
                condition.apply(criteria.getOptionId(), copy.getOptionId()) &&
                condition.apply(criteria.getServiceName(), copy.getServiceName()) &&
                condition.apply(criteria.getServiceDescription(), copy.getServiceDescription()) &&
                condition.apply(criteria.getValue(), copy.getValue()) &&
                condition.apply(criteria.getIsCustomerService(), copy.getIsCustomerService()) &&
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getServicePrice(), copy.getServicePrice()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
