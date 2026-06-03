package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AutojobsinvoicelinesCriteriaTest {

    @Test
    void newAutojobsinvoicelinesCriteriaHasAllFiltersNullTest() {
        var autojobsinvoicelinesCriteria = new AutojobsinvoicelinesCriteria();
        assertThat(autojobsinvoicelinesCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void autojobsinvoicelinesCriteriaFluentMethodsCreatesFiltersTest() {
        var autojobsinvoicelinesCriteria = new AutojobsinvoicelinesCriteria();

        setAllFilters(autojobsinvoicelinesCriteria);

        assertThat(autojobsinvoicelinesCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void autojobsinvoicelinesCriteriaCopyCreatesNullFilterTest() {
        var autojobsinvoicelinesCriteria = new AutojobsinvoicelinesCriteria();
        var copy = autojobsinvoicelinesCriteria.copy();

        assertThat(autojobsinvoicelinesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsinvoicelinesCriteria)
        );
    }

    @Test
    void autojobsinvoicelinesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var autojobsinvoicelinesCriteria = new AutojobsinvoicelinesCriteria();
        setAllFilters(autojobsinvoicelinesCriteria);

        var copy = autojobsinvoicelinesCriteria.copy();

        assertThat(autojobsinvoicelinesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsinvoicelinesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var autojobsinvoicelinesCriteria = new AutojobsinvoicelinesCriteria();

        assertThat(autojobsinvoicelinesCriteria).hasToString("AutojobsinvoicelinesCriteria{}");
    }

    private static void setAllFilters(AutojobsinvoicelinesCriteria autojobsinvoicelinesCriteria) {
        autojobsinvoicelinesCriteria.id();
        autojobsinvoicelinesCriteria.invocieid();
        autojobsinvoicelinesCriteria.lineid();
        autojobsinvoicelinesCriteria.itemid();
        autojobsinvoicelinesCriteria.itemcode();
        autojobsinvoicelinesCriteria.itemname();
        autojobsinvoicelinesCriteria.description();
        autojobsinvoicelinesCriteria.unitofmeasurement();
        autojobsinvoicelinesCriteria.quantity();
        autojobsinvoicelinesCriteria.itemcost();
        autojobsinvoicelinesCriteria.itemprice();
        autojobsinvoicelinesCriteria.discount();
        autojobsinvoicelinesCriteria.tax();
        autojobsinvoicelinesCriteria.sellingprice();
        autojobsinvoicelinesCriteria.linetotal();
        autojobsinvoicelinesCriteria.lmu();
        autojobsinvoicelinesCriteria.lmd();
        autojobsinvoicelinesCriteria.nbt();
        autojobsinvoicelinesCriteria.vat();
        autojobsinvoicelinesCriteria.distinct();
    }

    private static Condition<AutojobsinvoicelinesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInvocieid()) &&
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

    private static Condition<AutojobsinvoicelinesCriteria> copyFiltersAre(
        AutojobsinvoicelinesCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInvocieid(), copy.getInvocieid()) &&
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
