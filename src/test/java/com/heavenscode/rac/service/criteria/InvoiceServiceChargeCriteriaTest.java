package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InvoiceServiceChargeCriteriaTest {

    @Test
    void newInvoiceServiceChargeCriteriaHasAllFiltersNullTest() {
        var invoiceServiceChargeCriteria = new InvoiceServiceChargeCriteria();
        assertThat(invoiceServiceChargeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void invoiceServiceChargeCriteriaFluentMethodsCreatesFiltersTest() {
        var invoiceServiceChargeCriteria = new InvoiceServiceChargeCriteria();

        setAllFilters(invoiceServiceChargeCriteria);

        assertThat(invoiceServiceChargeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void invoiceServiceChargeCriteriaCopyCreatesNullFilterTest() {
        var invoiceServiceChargeCriteria = new InvoiceServiceChargeCriteria();
        var copy = invoiceServiceChargeCriteria.copy();

        assertThat(invoiceServiceChargeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(invoiceServiceChargeCriteria)
        );
    }

    @Test
    void invoiceServiceChargeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var invoiceServiceChargeCriteria = new InvoiceServiceChargeCriteria();
        setAllFilters(invoiceServiceChargeCriteria);

        var copy = invoiceServiceChargeCriteria.copy();

        assertThat(invoiceServiceChargeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(invoiceServiceChargeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var invoiceServiceChargeCriteria = new InvoiceServiceChargeCriteria();

        assertThat(invoiceServiceChargeCriteria).hasToString("InvoiceServiceChargeCriteria{}");
    }

    private static void setAllFilters(InvoiceServiceChargeCriteria invoiceServiceChargeCriteria) {
        invoiceServiceChargeCriteria.id();
        invoiceServiceChargeCriteria.invoiceId();
        invoiceServiceChargeCriteria.lineId();
        invoiceServiceChargeCriteria.optionId();
        invoiceServiceChargeCriteria.serviceName();
        invoiceServiceChargeCriteria.serviceDiscription();
        invoiceServiceChargeCriteria.value();
        invoiceServiceChargeCriteria.addedById();
        invoiceServiceChargeCriteria.isCustomerSrvice();
        invoiceServiceChargeCriteria.discount();
        invoiceServiceChargeCriteria.servicePrice();
        invoiceServiceChargeCriteria.distinct();
    }

    private static Condition<InvoiceServiceChargeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInvoiceId()) &&
                condition.apply(criteria.getLineId()) &&
                condition.apply(criteria.getOptionId()) &&
                condition.apply(criteria.getServiceName()) &&
                condition.apply(criteria.getServiceDiscription()) &&
                condition.apply(criteria.getValue()) &&
                condition.apply(criteria.getAddedById()) &&
                condition.apply(criteria.getIsCustomerSrvice()) &&
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getServicePrice()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InvoiceServiceChargeCriteria> copyFiltersAre(
        InvoiceServiceChargeCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInvoiceId(), copy.getInvoiceId()) &&
                condition.apply(criteria.getLineId(), copy.getLineId()) &&
                condition.apply(criteria.getOptionId(), copy.getOptionId()) &&
                condition.apply(criteria.getServiceName(), copy.getServiceName()) &&
                condition.apply(criteria.getServiceDiscription(), copy.getServiceDiscription()) &&
                condition.apply(criteria.getValue(), copy.getValue()) &&
                condition.apply(criteria.getAddedById(), copy.getAddedById()) &&
                condition.apply(criteria.getIsCustomerSrvice(), copy.getIsCustomerSrvice()) &&
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getServicePrice(), copy.getServicePrice()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
