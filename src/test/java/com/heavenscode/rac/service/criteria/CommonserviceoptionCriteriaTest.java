package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CommonserviceoptionCriteriaTest {

    @Test
    void newCommonserviceoptionCriteriaHasAllFiltersNullTest() {
        var commonserviceoptionCriteria = new CommonserviceoptionCriteria();
        assertThat(commonserviceoptionCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void commonserviceoptionCriteriaFluentMethodsCreatesFiltersTest() {
        var commonserviceoptionCriteria = new CommonserviceoptionCriteria();

        setAllFilters(commonserviceoptionCriteria);

        assertThat(commonserviceoptionCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void commonserviceoptionCriteriaCopyCreatesNullFilterTest() {
        var commonserviceoptionCriteria = new CommonserviceoptionCriteria();
        var copy = commonserviceoptionCriteria.copy();

        assertThat(commonserviceoptionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(commonserviceoptionCriteria)
        );
    }

    @Test
    void commonserviceoptionCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var commonserviceoptionCriteria = new CommonserviceoptionCriteria();
        setAllFilters(commonserviceoptionCriteria);

        var copy = commonserviceoptionCriteria.copy();

        assertThat(commonserviceoptionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(commonserviceoptionCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var commonserviceoptionCriteria = new CommonserviceoptionCriteria();

        assertThat(commonserviceoptionCriteria).hasToString("CommonserviceoptionCriteria{}");
    }

    private static void setAllFilters(CommonserviceoptionCriteria commonserviceoptionCriteria) {
        commonserviceoptionCriteria.id();
        commonserviceoptionCriteria.mainid();
        commonserviceoptionCriteria.code();
        commonserviceoptionCriteria.name();
        commonserviceoptionCriteria.description();
        commonserviceoptionCriteria.value();
        commonserviceoptionCriteria.isactive();
        commonserviceoptionCriteria.lmd();
        commonserviceoptionCriteria.lmu();
        commonserviceoptionCriteria.distinct();
    }

    private static Condition<CommonserviceoptionCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getMainid()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getValue()) &&
                condition.apply(criteria.getIsactive()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CommonserviceoptionCriteria> copyFiltersAre(
        CommonserviceoptionCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getMainid(), copy.getMainid()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getValue(), copy.getValue()) &&
                condition.apply(criteria.getIsactive(), copy.getIsactive()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
