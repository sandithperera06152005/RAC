package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AutojobsaleinvoicecommonservicechargeCriteriaTest {

    @Test
    void newAutojobsaleinvoicecommonservicechargeCriteriaHasAllFiltersNullTest() {
        var autojobsaleinvoicecommonservicechargeCriteria = new AutojobsaleinvoicecommonservicechargeCriteria();
        assertThat(autojobsaleinvoicecommonservicechargeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void autojobsaleinvoicecommonservicechargeCriteriaFluentMethodsCreatesFiltersTest() {
        var autojobsaleinvoicecommonservicechargeCriteria = new AutojobsaleinvoicecommonservicechargeCriteria();

        setAllFilters(autojobsaleinvoicecommonservicechargeCriteria);

        assertThat(autojobsaleinvoicecommonservicechargeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void autojobsaleinvoicecommonservicechargeCriteriaCopyCreatesNullFilterTest() {
        var autojobsaleinvoicecommonservicechargeCriteria = new AutojobsaleinvoicecommonservicechargeCriteria();
        var copy = autojobsaleinvoicecommonservicechargeCriteria.copy();

        assertThat(autojobsaleinvoicecommonservicechargeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsaleinvoicecommonservicechargeCriteria)
        );
    }

    @Test
    void autojobsaleinvoicecommonservicechargeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var autojobsaleinvoicecommonservicechargeCriteria = new AutojobsaleinvoicecommonservicechargeCriteria();
        setAllFilters(autojobsaleinvoicecommonservicechargeCriteria);

        var copy = autojobsaleinvoicecommonservicechargeCriteria.copy();

        assertThat(autojobsaleinvoicecommonservicechargeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsaleinvoicecommonservicechargeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var autojobsaleinvoicecommonservicechargeCriteria = new AutojobsaleinvoicecommonservicechargeCriteria();

        assertThat(autojobsaleinvoicecommonservicechargeCriteria).hasToString("AutojobsaleinvoicecommonservicechargeCriteria{}");
    }

    private static void setAllFilters(AutojobsaleinvoicecommonservicechargeCriteria autojobsaleinvoicecommonservicechargeCriteria) {
        autojobsaleinvoicecommonservicechargeCriteria.id();
        autojobsaleinvoicecommonservicechargeCriteria.invoiceid();
        autojobsaleinvoicecommonservicechargeCriteria.lineid();
        autojobsaleinvoicecommonservicechargeCriteria.optionid();
        autojobsaleinvoicecommonservicechargeCriteria.mainid();
        autojobsaleinvoicecommonservicechargeCriteria.code();
        autojobsaleinvoicecommonservicechargeCriteria.name();
        autojobsaleinvoicecommonservicechargeCriteria.description();
        autojobsaleinvoicecommonservicechargeCriteria.value();
        autojobsaleinvoicecommonservicechargeCriteria.addedbyid();
        autojobsaleinvoicecommonservicechargeCriteria.discount();
        autojobsaleinvoicecommonservicechargeCriteria.serviceprice();
        autojobsaleinvoicecommonservicechargeCriteria.distinct();
    }

    private static Condition<AutojobsaleinvoicecommonservicechargeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
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
                condition.apply(criteria.getAddedbyid()) &&
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getServiceprice()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AutojobsaleinvoicecommonservicechargeCriteria> copyFiltersAre(
        AutojobsaleinvoicecommonservicechargeCriteria copy,
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
                condition.apply(criteria.getAddedbyid(), copy.getAddedbyid()) &&
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getServiceprice(), copy.getServiceprice()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
