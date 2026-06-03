package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BillingserviceoptionvaluesCriteriaTest {

    @Test
    void newBillingserviceoptionvaluesCriteriaHasAllFiltersNullTest() {
        var billingserviceoptionvaluesCriteria = new BillingserviceoptionvaluesCriteria();
        assertThat(billingserviceoptionvaluesCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void billingserviceoptionvaluesCriteriaFluentMethodsCreatesFiltersTest() {
        var billingserviceoptionvaluesCriteria = new BillingserviceoptionvaluesCriteria();

        setAllFilters(billingserviceoptionvaluesCriteria);

        assertThat(billingserviceoptionvaluesCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void billingserviceoptionvaluesCriteriaCopyCreatesNullFilterTest() {
        var billingserviceoptionvaluesCriteria = new BillingserviceoptionvaluesCriteria();
        var copy = billingserviceoptionvaluesCriteria.copy();

        assertThat(billingserviceoptionvaluesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(billingserviceoptionvaluesCriteria)
        );
    }

    @Test
    void billingserviceoptionvaluesCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var billingserviceoptionvaluesCriteria = new BillingserviceoptionvaluesCriteria();
        setAllFilters(billingserviceoptionvaluesCriteria);

        var copy = billingserviceoptionvaluesCriteria.copy();

        assertThat(billingserviceoptionvaluesCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(billingserviceoptionvaluesCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var billingserviceoptionvaluesCriteria = new BillingserviceoptionvaluesCriteria();

        assertThat(billingserviceoptionvaluesCriteria).hasToString("BillingserviceoptionvaluesCriteria{}");
    }

    private static void setAllFilters(BillingserviceoptionvaluesCriteria billingserviceoptionvaluesCriteria) {
        billingserviceoptionvaluesCriteria.id();
        billingserviceoptionvaluesCriteria.vehicletypeid();
        billingserviceoptionvaluesCriteria.billingserviceoptionid();
        billingserviceoptionvaluesCriteria.value();
        billingserviceoptionvaluesCriteria.lmd();
        billingserviceoptionvaluesCriteria.lmu();
        billingserviceoptionvaluesCriteria.distinct();
    }

    private static Condition<BillingserviceoptionvaluesCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getVehicletypeid()) &&
                condition.apply(criteria.getBillingserviceoptionid()) &&
                condition.apply(criteria.getValue()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BillingserviceoptionvaluesCriteria> copyFiltersAre(
        BillingserviceoptionvaluesCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getVehicletypeid(), copy.getVehicletypeid()) &&
                condition.apply(criteria.getBillingserviceoptionid(), copy.getBillingserviceoptionid()) &&
                condition.apply(criteria.getValue(), copy.getValue()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
