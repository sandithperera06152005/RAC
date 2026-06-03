package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SaleInvoiceCommonServiceChargeDummyCriteriaTest {

    @Test
    void newSaleInvoiceCommonServiceChargeDummyCriteriaHasAllFiltersNullTest() {
        var saleInvoiceCommonServiceChargeDummyCriteria = new SaleInvoiceCommonServiceChargeDummyCriteria();
        assertThat(saleInvoiceCommonServiceChargeDummyCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void saleInvoiceCommonServiceChargeDummyCriteriaFluentMethodsCreatesFiltersTest() {
        var saleInvoiceCommonServiceChargeDummyCriteria = new SaleInvoiceCommonServiceChargeDummyCriteria();

        setAllFilters(saleInvoiceCommonServiceChargeDummyCriteria);

        assertThat(saleInvoiceCommonServiceChargeDummyCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void saleInvoiceCommonServiceChargeDummyCriteriaCopyCreatesNullFilterTest() {
        var saleInvoiceCommonServiceChargeDummyCriteria = new SaleInvoiceCommonServiceChargeDummyCriteria();
        var copy = saleInvoiceCommonServiceChargeDummyCriteria.copy();

        assertThat(saleInvoiceCommonServiceChargeDummyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(saleInvoiceCommonServiceChargeDummyCriteria)
        );
    }

    @Test
    void saleInvoiceCommonServiceChargeDummyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var saleInvoiceCommonServiceChargeDummyCriteria = new SaleInvoiceCommonServiceChargeDummyCriteria();
        setAllFilters(saleInvoiceCommonServiceChargeDummyCriteria);

        var copy = saleInvoiceCommonServiceChargeDummyCriteria.copy();

        assertThat(saleInvoiceCommonServiceChargeDummyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(saleInvoiceCommonServiceChargeDummyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var saleInvoiceCommonServiceChargeDummyCriteria = new SaleInvoiceCommonServiceChargeDummyCriteria();

        assertThat(saleInvoiceCommonServiceChargeDummyCriteria).hasToString("SaleInvoiceCommonServiceChargeDummyCriteria{}");
    }

    private static void setAllFilters(SaleInvoiceCommonServiceChargeDummyCriteria saleInvoiceCommonServiceChargeDummyCriteria) {
        saleInvoiceCommonServiceChargeDummyCriteria.id();
        saleInvoiceCommonServiceChargeDummyCriteria.invoiceid();
        saleInvoiceCommonServiceChargeDummyCriteria.lineid();
        saleInvoiceCommonServiceChargeDummyCriteria.optionid();
        saleInvoiceCommonServiceChargeDummyCriteria.mainid();
        saleInvoiceCommonServiceChargeDummyCriteria.code();
        saleInvoiceCommonServiceChargeDummyCriteria.name();
        saleInvoiceCommonServiceChargeDummyCriteria.description();
        saleInvoiceCommonServiceChargeDummyCriteria.value();
        saleInvoiceCommonServiceChargeDummyCriteria.distinct();
    }

    private static Condition<SaleInvoiceCommonServiceChargeDummyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInvoiceid()) &&
                condition.apply(criteria.getLineid()) &&
                condition.apply(criteria.getOptionid()) &&
                condition.apply(criteria.getMainid()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getValue()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SaleInvoiceCommonServiceChargeDummyCriteria> copyFiltersAre(
        SaleInvoiceCommonServiceChargeDummyCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInvoiceid(), copy.getInvoiceid()) &&
                condition.apply(criteria.getLineid(), copy.getLineid()) &&
                condition.apply(criteria.getOptionid(), copy.getOptionid()) &&
                condition.apply(criteria.getMainid(), copy.getMainid()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getValue(), copy.getValue()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
