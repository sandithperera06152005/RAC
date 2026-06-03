package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SalesInvoiceServiceChargeLineDummyCriteriaTest {

    @Test
    void newSalesInvoiceServiceChargeLineDummyCriteriaHasAllFiltersNullTest() {
        var salesInvoiceServiceChargeLineDummyCriteria = new SalesInvoiceServiceChargeLineDummyCriteria();
        assertThat(salesInvoiceServiceChargeLineDummyCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void salesInvoiceServiceChargeLineDummyCriteriaFluentMethodsCreatesFiltersTest() {
        var salesInvoiceServiceChargeLineDummyCriteria = new SalesInvoiceServiceChargeLineDummyCriteria();

        setAllFilters(salesInvoiceServiceChargeLineDummyCriteria);

        assertThat(salesInvoiceServiceChargeLineDummyCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void salesInvoiceServiceChargeLineDummyCriteriaCopyCreatesNullFilterTest() {
        var salesInvoiceServiceChargeLineDummyCriteria = new SalesInvoiceServiceChargeLineDummyCriteria();
        var copy = salesInvoiceServiceChargeLineDummyCriteria.copy();

        assertThat(salesInvoiceServiceChargeLineDummyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceServiceChargeLineDummyCriteria)
        );
    }

    @Test
    void salesInvoiceServiceChargeLineDummyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var salesInvoiceServiceChargeLineDummyCriteria = new SalesInvoiceServiceChargeLineDummyCriteria();
        setAllFilters(salesInvoiceServiceChargeLineDummyCriteria);

        var copy = salesInvoiceServiceChargeLineDummyCriteria.copy();

        assertThat(salesInvoiceServiceChargeLineDummyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceServiceChargeLineDummyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var salesInvoiceServiceChargeLineDummyCriteria = new SalesInvoiceServiceChargeLineDummyCriteria();

        assertThat(salesInvoiceServiceChargeLineDummyCriteria).hasToString("SalesInvoiceServiceChargeLineDummyCriteria{}");
    }

    private static void setAllFilters(SalesInvoiceServiceChargeLineDummyCriteria salesInvoiceServiceChargeLineDummyCriteria) {
        salesInvoiceServiceChargeLineDummyCriteria.id();
        salesInvoiceServiceChargeLineDummyCriteria.invoiceId();
        salesInvoiceServiceChargeLineDummyCriteria.lineId();
        salesInvoiceServiceChargeLineDummyCriteria.optionId();
        salesInvoiceServiceChargeLineDummyCriteria.serviceName();
        salesInvoiceServiceChargeLineDummyCriteria.serviceDescription();
        salesInvoiceServiceChargeLineDummyCriteria.value();
        salesInvoiceServiceChargeLineDummyCriteria.isCustomerService();
        salesInvoiceServiceChargeLineDummyCriteria.distinct();
    }

    private static Condition<SalesInvoiceServiceChargeLineDummyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
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
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SalesInvoiceServiceChargeLineDummyCriteria> copyFiltersAre(
        SalesInvoiceServiceChargeLineDummyCriteria copy,
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
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
