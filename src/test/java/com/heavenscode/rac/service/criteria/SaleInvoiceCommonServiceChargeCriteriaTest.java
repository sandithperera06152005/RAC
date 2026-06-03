package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SaleInvoiceCommonServiceChargeCriteriaTest {

    @Test
    void newSaleInvoiceCommonServiceChargeCriteriaHasAllFiltersNullTest() {
        var saleInvoiceCommonServiceChargeCriteria = new SaleInvoiceCommonServiceChargeCriteria();
        assertThat(saleInvoiceCommonServiceChargeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void saleInvoiceCommonServiceChargeCriteriaFluentMethodsCreatesFiltersTest() {
        var saleInvoiceCommonServiceChargeCriteria = new SaleInvoiceCommonServiceChargeCriteria();

        setAllFilters(saleInvoiceCommonServiceChargeCriteria);

        assertThat(saleInvoiceCommonServiceChargeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void saleInvoiceCommonServiceChargeCriteriaCopyCreatesNullFilterTest() {
        var saleInvoiceCommonServiceChargeCriteria = new SaleInvoiceCommonServiceChargeCriteria();
        var copy = saleInvoiceCommonServiceChargeCriteria.copy();

        assertThat(saleInvoiceCommonServiceChargeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(saleInvoiceCommonServiceChargeCriteria)
        );
    }

    @Test
    void saleInvoiceCommonServiceChargeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var saleInvoiceCommonServiceChargeCriteria = new SaleInvoiceCommonServiceChargeCriteria();
        setAllFilters(saleInvoiceCommonServiceChargeCriteria);

        var copy = saleInvoiceCommonServiceChargeCriteria.copy();

        assertThat(saleInvoiceCommonServiceChargeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(saleInvoiceCommonServiceChargeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var saleInvoiceCommonServiceChargeCriteria = new SaleInvoiceCommonServiceChargeCriteria();

        assertThat(saleInvoiceCommonServiceChargeCriteria).hasToString("SaleInvoiceCommonServiceChargeCriteria{}");
    }

    private static void setAllFilters(SaleInvoiceCommonServiceChargeCriteria saleInvoiceCommonServiceChargeCriteria) {
        saleInvoiceCommonServiceChargeCriteria.id();
        saleInvoiceCommonServiceChargeCriteria.invoiceId();
        saleInvoiceCommonServiceChargeCriteria.lineId();
        saleInvoiceCommonServiceChargeCriteria.optionId();
        saleInvoiceCommonServiceChargeCriteria.mainId();
        saleInvoiceCommonServiceChargeCriteria.code();
        saleInvoiceCommonServiceChargeCriteria.name();
        saleInvoiceCommonServiceChargeCriteria.description();
        saleInvoiceCommonServiceChargeCriteria.value();
        saleInvoiceCommonServiceChargeCriteria.discount();
        saleInvoiceCommonServiceChargeCriteria.servicePrice();
        saleInvoiceCommonServiceChargeCriteria.distinct();
    }

    private static Condition<SaleInvoiceCommonServiceChargeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
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
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getServicePrice()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SaleInvoiceCommonServiceChargeCriteria> copyFiltersAre(
        SaleInvoiceCommonServiceChargeCriteria copy,
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
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getServicePrice(), copy.getServicePrice()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
