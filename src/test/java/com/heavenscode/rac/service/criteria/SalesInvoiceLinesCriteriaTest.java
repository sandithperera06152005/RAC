package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SalesInvoiceLinesCriteriaTest {

    @Test
    void newSalesInvoiceLinesCriteriaHasAllFiltersNullTest() {
        var salesInvoiceLinesCriteria = new SalesInvoiceLinesCriteria();
        assertThat(salesInvoiceLinesCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void salesInvoiceLinesCriteriaFluentMethodsCreatesFiltersTest() {
        var salesInvoiceLinesCriteria = new SalesInvoiceLinesCriteria();

        setAllFilters(salesInvoiceLinesCriteria);

        assertThat(salesInvoiceLinesCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void salesInvoiceLinesCriteriaCopyCreatesNullFilterTest() {
        var salesInvoiceLinesCriteria = new SalesInvoiceLinesCriteria();
        var copy = salesInvoiceLinesCriteria.copy();

        assertThat(salesInvoiceLinesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceLinesCriteria)
        );
    }

    @Test
    void salesInvoiceLinesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var salesInvoiceLinesCriteria = new SalesInvoiceLinesCriteria();
        setAllFilters(salesInvoiceLinesCriteria);

        var copy = salesInvoiceLinesCriteria.copy();

        assertThat(salesInvoiceLinesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceLinesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var salesInvoiceLinesCriteria = new SalesInvoiceLinesCriteria();

        assertThat(salesInvoiceLinesCriteria).hasToString("SalesInvoiceLinesCriteria{}");
    }

    private static void setAllFilters(SalesInvoiceLinesCriteria salesInvoiceLinesCriteria) {
        salesInvoiceLinesCriteria.id();
        salesInvoiceLinesCriteria.invoiceid();
        salesInvoiceLinesCriteria.lineid();
        salesInvoiceLinesCriteria.itemid();
        salesInvoiceLinesCriteria.itemcode();
        salesInvoiceLinesCriteria.itemname();
        salesInvoiceLinesCriteria.description();
        salesInvoiceLinesCriteria.unitofmeasurement();
        salesInvoiceLinesCriteria.quantity();
        salesInvoiceLinesCriteria.itemcost();
        salesInvoiceLinesCriteria.itemprice();
        salesInvoiceLinesCriteria.discount();
        salesInvoiceLinesCriteria.tax();
        salesInvoiceLinesCriteria.sellingprice();
        salesInvoiceLinesCriteria.linetotal();
        salesInvoiceLinesCriteria.lmu();
        salesInvoiceLinesCriteria.lmd();
        salesInvoiceLinesCriteria.nbt();
        salesInvoiceLinesCriteria.vat();
        salesInvoiceLinesCriteria.distinct();
    }

    private static Condition<SalesInvoiceLinesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInvoiceid()) &&
                condition.apply(criteria.getLineid()) &&
                condition.apply(criteria.getItemid()) &&
                condition.apply(criteria.getItemcode()) &&
                condition.apply(criteria.getItemname()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getUnitofmeasurement()) &&
                condition.apply(criteria.getQuantity()) &&
                condition.apply(criteria.getItemcost()) &&
                condition.apply(criteria.getItemprice()) &&
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getSellingprice()) &&
                condition.apply(criteria.getLinetotal()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getNbt()) &&
                condition.apply(criteria.getVat()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SalesInvoiceLinesCriteria> copyFiltersAre(
        SalesInvoiceLinesCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInvoiceid(), copy.getInvoiceid()) &&
                condition.apply(criteria.getLineid(), copy.getLineid()) &&
                condition.apply(criteria.getItemid(), copy.getItemid()) &&
                condition.apply(criteria.getItemcode(), copy.getItemcode()) &&
                condition.apply(criteria.getItemname(), copy.getItemname()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getUnitofmeasurement(), copy.getUnitofmeasurement()) &&
                condition.apply(criteria.getQuantity(), copy.getQuantity()) &&
                condition.apply(criteria.getItemcost(), copy.getItemcost()) &&
                condition.apply(criteria.getItemprice(), copy.getItemprice()) &&
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getSellingprice(), copy.getSellingprice()) &&
                condition.apply(criteria.getLinetotal(), copy.getLinetotal()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getNbt(), copy.getNbt()) &&
                condition.apply(criteria.getVat(), copy.getVat()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
