package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AutojobsinvoicelinebatchesCriteriaTest {

    @Test
    void newAutojobsinvoicelinebatchesCriteriaHasAllFiltersNullTest() {
        var autojobsinvoicelinebatchesCriteria = new AutojobsinvoicelinebatchesCriteria();
        assertThat(autojobsinvoicelinebatchesCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void autojobsinvoicelinebatchesCriteriaFluentMethodsCreatesFiltersTest() {
        var autojobsinvoicelinebatchesCriteria = new AutojobsinvoicelinebatchesCriteria();

        setAllFilters(autojobsinvoicelinebatchesCriteria);

        assertThat(autojobsinvoicelinebatchesCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void autojobsinvoicelinebatchesCriteriaCopyCreatesNullFilterTest() {
        var autojobsinvoicelinebatchesCriteria = new AutojobsinvoicelinebatchesCriteria();
        var copy = autojobsinvoicelinebatchesCriteria.copy();

        assertThat(autojobsinvoicelinebatchesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsinvoicelinebatchesCriteria)
        );
    }

    @Test
    void autojobsinvoicelinebatchesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var autojobsinvoicelinebatchesCriteria = new AutojobsinvoicelinebatchesCriteria();
        setAllFilters(autojobsinvoicelinebatchesCriteria);

        var copy = autojobsinvoicelinebatchesCriteria.copy();

        assertThat(autojobsinvoicelinebatchesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsinvoicelinebatchesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var autojobsinvoicelinebatchesCriteria = new AutojobsinvoicelinebatchesCriteria();

        assertThat(autojobsinvoicelinebatchesCriteria).hasToString("AutojobsinvoicelinebatchesCriteria{}");
    }

    private static void setAllFilters(AutojobsinvoicelinebatchesCriteria autojobsinvoicelinebatchesCriteria) {
        autojobsinvoicelinebatchesCriteria.id();
        autojobsinvoicelinebatchesCriteria.lineid();
        autojobsinvoicelinebatchesCriteria.batchlineid();
        autojobsinvoicelinebatchesCriteria.itemid();
        autojobsinvoicelinebatchesCriteria.code();
        autojobsinvoicelinebatchesCriteria.batchid();
        autojobsinvoicelinebatchesCriteria.batchcode();
        autojobsinvoicelinebatchesCriteria.txdate();
        autojobsinvoicelinebatchesCriteria.manufacturedate();
        autojobsinvoicelinebatchesCriteria.expireddate();
        autojobsinvoicelinebatchesCriteria.qty();
        autojobsinvoicelinebatchesCriteria.cost();
        autojobsinvoicelinebatchesCriteria.price();
        autojobsinvoicelinebatchesCriteria.notes();
        autojobsinvoicelinebatchesCriteria.lmu();
        autojobsinvoicelinebatchesCriteria.lmd();
        autojobsinvoicelinebatchesCriteria.nbt();
        autojobsinvoicelinebatchesCriteria.vat();
        autojobsinvoicelinebatchesCriteria.discount();
        autojobsinvoicelinebatchesCriteria.total();
        autojobsinvoicelinebatchesCriteria.issued();
        autojobsinvoicelinebatchesCriteria.issuedby();
        autojobsinvoicelinebatchesCriteria.issueddatetime();
        autojobsinvoicelinebatchesCriteria.addedbyid();
        autojobsinvoicelinebatchesCriteria.canceloptid();
        autojobsinvoicelinebatchesCriteria.cancelopt();
        autojobsinvoicelinebatchesCriteria.cancelby();
        autojobsinvoicelinebatchesCriteria.distinct();
    }

    private static Condition<AutojobsinvoicelinebatchesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLineid()) &&
                condition.apply(criteria.getBatchlineid()) &&
                condition.apply(criteria.getItemid()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getBatchid()) &&
                condition.apply(criteria.getBatchcode()) &&
                condition.apply(criteria.getTxdate()) &&
                condition.apply(criteria.getManufacturedate()) &&
                condition.apply(criteria.getExpireddate()) &&
                condition.apply(criteria.getQty()) &&
                condition.apply(criteria.getCost()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getNotes()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getNbt()) &&
                condition.apply(criteria.getVat()) &&
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getTotal()) &&
                condition.apply(criteria.getIssued()) &&
                condition.apply(criteria.getIssuedby()) &&
                condition.apply(criteria.getIssueddatetime()) &&
                condition.apply(criteria.getAddedbyid()) &&
                condition.apply(criteria.getCanceloptid()) &&
                condition.apply(criteria.getCancelopt()) &&
                condition.apply(criteria.getCancelby()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AutojobsinvoicelinebatchesCriteria> copyFiltersAre(
        AutojobsinvoicelinebatchesCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLineid(), copy.getLineid()) &&
                condition.apply(criteria.getBatchlineid(), copy.getBatchlineid()) &&
                condition.apply(criteria.getItemid(), copy.getItemid()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getBatchid(), copy.getBatchid()) &&
                condition.apply(criteria.getBatchcode(), copy.getBatchcode()) &&
                condition.apply(criteria.getTxdate(), copy.getTxdate()) &&
                condition.apply(criteria.getManufacturedate(), copy.getManufacturedate()) &&
                condition.apply(criteria.getExpireddate(), copy.getExpireddate()) &&
                condition.apply(criteria.getQty(), copy.getQty()) &&
                condition.apply(criteria.getCost(), copy.getCost()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getNotes(), copy.getNotes()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getNbt(), copy.getNbt()) &&
                condition.apply(criteria.getVat(), copy.getVat()) &&
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getTotal(), copy.getTotal()) &&
                condition.apply(criteria.getIssued(), copy.getIssued()) &&
                condition.apply(criteria.getIssuedby(), copy.getIssuedby()) &&
                condition.apply(criteria.getIssueddatetime(), copy.getIssueddatetime()) &&
                condition.apply(criteria.getAddedbyid(), copy.getAddedbyid()) &&
                condition.apply(criteria.getCanceloptid(), copy.getCanceloptid()) &&
                condition.apply(criteria.getCancelopt(), copy.getCancelopt()) &&
                condition.apply(criteria.getCancelby(), copy.getCancelby()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
