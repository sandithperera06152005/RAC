package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BillingserviceoptionCriteriaTest {

    @Test
    void newBillingserviceoptionCriteriaHasAllFiltersNullTest() {
        var billingserviceoptionCriteria = new BillingserviceoptionCriteria();
        assertThat(billingserviceoptionCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void billingserviceoptionCriteriaFluentMethodsCreatesFiltersTest() {
        var billingserviceoptionCriteria = new BillingserviceoptionCriteria();

        setAllFilters(billingserviceoptionCriteria);

        assertThat(billingserviceoptionCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void billingserviceoptionCriteriaCopyCreatesNullFilterTest() {
        var billingserviceoptionCriteria = new BillingserviceoptionCriteria();
        var copy = billingserviceoptionCriteria.copy();

        assertThat(billingserviceoptionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(billingserviceoptionCriteria)
        );
    }

    @Test
    void billingserviceoptionCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var billingserviceoptionCriteria = new BillingserviceoptionCriteria();
        setAllFilters(billingserviceoptionCriteria);

        var copy = billingserviceoptionCriteria.copy();

        assertThat(billingserviceoptionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(billingserviceoptionCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var billingserviceoptionCriteria = new BillingserviceoptionCriteria();

        assertThat(billingserviceoptionCriteria).hasToString("BillingserviceoptionCriteria{}");
    }

    private static void setAllFilters(BillingserviceoptionCriteria billingserviceoptionCriteria) {
        billingserviceoptionCriteria.id();
        billingserviceoptionCriteria.servicename();
        billingserviceoptionCriteria.servicediscription();
        billingserviceoptionCriteria.isactive();
        billingserviceoptionCriteria.lmd();
        billingserviceoptionCriteria.lmu();
        billingserviceoptionCriteria.orderby();
        billingserviceoptionCriteria.billtocustomer();
        billingserviceoptionCriteria.distinct();
    }

    private static Condition<BillingserviceoptionCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getServicename()) &&
                condition.apply(criteria.getServicediscription()) &&
                condition.apply(criteria.getIsactive()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getOrderby()) &&
                condition.apply(criteria.getBilltocustomer()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BillingserviceoptionCriteria> copyFiltersAre(
        BillingserviceoptionCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getServicename(), copy.getServicename()) &&
                condition.apply(criteria.getServicediscription(), copy.getServicediscription()) &&
                condition.apply(criteria.getIsactive(), copy.getIsactive()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getOrderby(), copy.getOrderby()) &&
                condition.apply(criteria.getBilltocustomer(), copy.getBilltocustomer()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
