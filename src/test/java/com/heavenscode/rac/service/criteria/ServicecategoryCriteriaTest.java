package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ServicecategoryCriteriaTest {

    @Test
    void newServicecategoryCriteriaHasAllFiltersNullTest() {
        var servicecategoryCriteria = new ServicecategoryCriteria();
        assertThat(servicecategoryCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void servicecategoryCriteriaFluentMethodsCreatesFiltersTest() {
        var servicecategoryCriteria = new ServicecategoryCriteria();

        setAllFilters(servicecategoryCriteria);

        assertThat(servicecategoryCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void servicecategoryCriteriaCopyCreatesNullFilterTest() {
        var servicecategoryCriteria = new ServicecategoryCriteria();
        var copy = servicecategoryCriteria.copy();

        assertThat(servicecategoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(servicecategoryCriteria)
        );
    }

    @Test
    void servicecategoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var servicecategoryCriteria = new ServicecategoryCriteria();
        setAllFilters(servicecategoryCriteria);

        var copy = servicecategoryCriteria.copy();

        assertThat(servicecategoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(servicecategoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var servicecategoryCriteria = new ServicecategoryCriteria();

        assertThat(servicecategoryCriteria).hasToString("ServicecategoryCriteria{}");
    }

    private static void setAllFilters(ServicecategoryCriteria servicecategoryCriteria) {
        servicecategoryCriteria.id();
        servicecategoryCriteria.name();
        servicecategoryCriteria.description();
        servicecategoryCriteria.lmu();
        servicecategoryCriteria.lmd();
        servicecategoryCriteria.showsecurity();
        servicecategoryCriteria.sortorder();
        servicecategoryCriteria.isactive();
        servicecategoryCriteria.distinct();
    }

    private static Condition<ServicecategoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getShowsecurity()) &&
                condition.apply(criteria.getSortorder()) &&
                condition.apply(criteria.getIsactive()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ServicecategoryCriteria> copyFiltersAre(
        ServicecategoryCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getShowsecurity(), copy.getShowsecurity()) &&
                condition.apply(criteria.getSortorder(), copy.getSortorder()) &&
                condition.apply(criteria.getIsactive(), copy.getIsactive()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
