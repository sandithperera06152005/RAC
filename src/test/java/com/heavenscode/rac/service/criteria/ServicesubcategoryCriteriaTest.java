package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ServicesubcategoryCriteriaTest {

    @Test
    void newServicesubcategoryCriteriaHasAllFiltersNullTest() {
        var servicesubcategoryCriteria = new ServicesubcategoryCriteria();
        assertThat(servicesubcategoryCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void servicesubcategoryCriteriaFluentMethodsCreatesFiltersTest() {
        var servicesubcategoryCriteria = new ServicesubcategoryCriteria();

        setAllFilters(servicesubcategoryCriteria);

        assertThat(servicesubcategoryCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void servicesubcategoryCriteriaCopyCreatesNullFilterTest() {
        var servicesubcategoryCriteria = new ServicesubcategoryCriteria();
        var copy = servicesubcategoryCriteria.copy();

        assertThat(servicesubcategoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(servicesubcategoryCriteria)
        );
    }

    @Test
    void servicesubcategoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var servicesubcategoryCriteria = new ServicesubcategoryCriteria();
        setAllFilters(servicesubcategoryCriteria);

        var copy = servicesubcategoryCriteria.copy();

        assertThat(servicesubcategoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(servicesubcategoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var servicesubcategoryCriteria = new ServicesubcategoryCriteria();

        assertThat(servicesubcategoryCriteria).hasToString("ServicesubcategoryCriteria{}");
    }

    private static void setAllFilters(ServicesubcategoryCriteria servicesubcategoryCriteria) {
        servicesubcategoryCriteria.id();
        servicesubcategoryCriteria.name();
        servicesubcategoryCriteria.description();
        servicesubcategoryCriteria.mainid();
        servicesubcategoryCriteria.mainname();
        servicesubcategoryCriteria.lmu();
        servicesubcategoryCriteria.lmd();
        servicesubcategoryCriteria.isactive();
        servicesubcategoryCriteria.distinct();
    }

    private static Condition<ServicesubcategoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getMainid()) &&
                condition.apply(criteria.getMainname()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getIsactive()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ServicesubcategoryCriteria> copyFiltersAre(
        ServicesubcategoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getMainid(), copy.getMainid()) &&
                condition.apply(criteria.getMainname(), copy.getMainname()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getIsactive(), copy.getIsactive()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
