package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SalesInvoiceLinesDummyCriteriaTest {

    @Test
    void newSalesInvoiceLinesDummyCriteriaHasAllFiltersNullTest() {
        var salesInvoiceLinesDummyCriteria = new SalesInvoiceLinesDummyCriteria();
        assertThat(salesInvoiceLinesDummyCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void salesInvoiceLinesDummyCriteriaFluentMethodsCreatesFiltersTest() {
        var salesInvoiceLinesDummyCriteria = new SalesInvoiceLinesDummyCriteria();

        setAllFilters(salesInvoiceLinesDummyCriteria);

        assertThat(salesInvoiceLinesDummyCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void salesInvoiceLinesDummyCriteriaCopyCreatesNullFilterTest() {
        var salesInvoiceLinesDummyCriteria = new SalesInvoiceLinesDummyCriteria();
        var copy = salesInvoiceLinesDummyCriteria.copy();

        assertThat(salesInvoiceLinesDummyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceLinesDummyCriteria)
        );
    }

    @Test
    void salesInvoiceLinesDummyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var salesInvoiceLinesDummyCriteria = new SalesInvoiceLinesDummyCriteria();
        setAllFilters(salesInvoiceLinesDummyCriteria);

        var copy = salesInvoiceLinesDummyCriteria.copy();

        assertThat(salesInvoiceLinesDummyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceLinesDummyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var salesInvoiceLinesDummyCriteria = new SalesInvoiceLinesDummyCriteria();

        assertThat(salesInvoiceLinesDummyCriteria).hasToString("SalesInvoiceLinesDummyCriteria{}");
    }

    private static void setAllFilters(SalesInvoiceLinesDummyCriteria salesInvoiceLinesDummyCriteria) {
        salesInvoiceLinesDummyCriteria.id();
        salesInvoiceLinesDummyCriteria.invoiceId();
        salesInvoiceLinesDummyCriteria.lineId();
        salesInvoiceLinesDummyCriteria.itemId();
        salesInvoiceLinesDummyCriteria.itemCode();
        salesInvoiceLinesDummyCriteria.itemName();
        salesInvoiceLinesDummyCriteria.description();
        salesInvoiceLinesDummyCriteria.unitOfMeasurement();
        salesInvoiceLinesDummyCriteria.quantity();
        salesInvoiceLinesDummyCriteria.itemCost();
        salesInvoiceLinesDummyCriteria.itemPrice();
        salesInvoiceLinesDummyCriteria.discount();
        salesInvoiceLinesDummyCriteria.tax();
        salesInvoiceLinesDummyCriteria.sellingPrice();
        salesInvoiceLinesDummyCriteria.lineTotal();
        salesInvoiceLinesDummyCriteria.lmu();
        salesInvoiceLinesDummyCriteria.lmd();
        salesInvoiceLinesDummyCriteria.nbt();
        salesInvoiceLinesDummyCriteria.vat();
        salesInvoiceLinesDummyCriteria.distinct();
    }

    private static Condition<SalesInvoiceLinesDummyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInvoiceId()) &&
                condition.apply(criteria.getLineId()) &&
                condition.apply(criteria.getItemId()) &&
                condition.apply(criteria.getItemCode()) &&
                condition.apply(criteria.getItemName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getUnitOfMeasurement()) &&
                condition.apply(criteria.getQuantity()) &&
                condition.apply(criteria.getItemCost()) &&
                condition.apply(criteria.getItemPrice()) &&
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getTax()) &&
                condition.apply(criteria.getSellingPrice()) &&
                condition.apply(criteria.getLineTotal()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getNbt()) &&
                condition.apply(criteria.getVat()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SalesInvoiceLinesDummyCriteria> copyFiltersAre(
        SalesInvoiceLinesDummyCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInvoiceId(), copy.getInvoiceId()) &&
                condition.apply(criteria.getLineId(), copy.getLineId()) &&
                condition.apply(criteria.getItemId(), copy.getItemId()) &&
                condition.apply(criteria.getItemCode(), copy.getItemCode()) &&
                condition.apply(criteria.getItemName(), copy.getItemName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getUnitOfMeasurement(), copy.getUnitOfMeasurement()) &&
                condition.apply(criteria.getQuantity(), copy.getQuantity()) &&
                condition.apply(criteria.getItemCost(), copy.getItemCost()) &&
                condition.apply(criteria.getItemPrice(), copy.getItemPrice()) &&
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getTax(), copy.getTax()) &&
                condition.apply(criteria.getSellingPrice(), copy.getSellingPrice()) &&
                condition.apply(criteria.getLineTotal(), copy.getLineTotal()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getNbt(), copy.getNbt()) &&
                condition.apply(criteria.getVat(), copy.getVat()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
