package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AutojobsalesinvoiceservicechargelineCriteriaTest {

    @Test
    void newAutojobsalesinvoiceservicechargelineCriteriaHasAllFiltersNullTest() {
        var autojobsalesinvoiceservicechargelineCriteria = new AutojobsalesinvoiceservicechargelineCriteria();
        assertThat(autojobsalesinvoiceservicechargelineCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void autojobsalesinvoiceservicechargelineCriteriaFluentMethodsCreatesFiltersTest() {
        var autojobsalesinvoiceservicechargelineCriteria = new AutojobsalesinvoiceservicechargelineCriteria();

        setAllFilters(autojobsalesinvoiceservicechargelineCriteria);

        assertThat(autojobsalesinvoiceservicechargelineCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void autojobsalesinvoiceservicechargelineCriteriaCopyCreatesNullFilterTest() {
        var autojobsalesinvoiceservicechargelineCriteria = new AutojobsalesinvoiceservicechargelineCriteria();
        var copy = autojobsalesinvoiceservicechargelineCriteria.copy();

        assertThat(autojobsalesinvoiceservicechargelineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsalesinvoiceservicechargelineCriteria)
        );
    }

    @Test
    void autojobsalesinvoiceservicechargelineCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var autojobsalesinvoiceservicechargelineCriteria = new AutojobsalesinvoiceservicechargelineCriteria();
        setAllFilters(autojobsalesinvoiceservicechargelineCriteria);

        var copy = autojobsalesinvoiceservicechargelineCriteria.copy();

        assertThat(autojobsalesinvoiceservicechargelineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsalesinvoiceservicechargelineCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var autojobsalesinvoiceservicechargelineCriteria = new AutojobsalesinvoiceservicechargelineCriteria();

        assertThat(autojobsalesinvoiceservicechargelineCriteria).hasToString("AutojobsalesinvoiceservicechargelineCriteria{}");
    }

    private static void setAllFilters(AutojobsalesinvoiceservicechargelineCriteria autojobsalesinvoiceservicechargelineCriteria) {
        autojobsalesinvoiceservicechargelineCriteria.id();
        autojobsalesinvoiceservicechargelineCriteria.invoiceid();
        autojobsalesinvoiceservicechargelineCriteria.lineid();
        autojobsalesinvoiceservicechargelineCriteria.optionid();
        autojobsalesinvoiceservicechargelineCriteria.servicename();
        autojobsalesinvoiceservicechargelineCriteria.servicediscription();
        autojobsalesinvoiceservicechargelineCriteria.value();
        autojobsalesinvoiceservicechargelineCriteria.addedbyid();
        autojobsalesinvoiceservicechargelineCriteria.iscustomersrvice();
        autojobsalesinvoiceservicechargelineCriteria.discount();
        autojobsalesinvoiceservicechargelineCriteria.serviceprice();
        autojobsalesinvoiceservicechargelineCriteria.distinct();
    }

    private static Condition<AutojobsalesinvoiceservicechargelineCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInvoiceid()) &&
                condition.apply(criteria.getLineid()) &&
                condition.apply(criteria.getOptionid()) &&
                condition.apply(criteria.getServicename()) &&
                condition.apply(criteria.getServicediscription()) &&
                condition.apply(criteria.getValue()) &&
                condition.apply(criteria.getAddedbyid()) &&
                condition.apply(criteria.getIscustomersrvice()) &&
                condition.apply(criteria.getDiscount()) &&
                condition.apply(criteria.getServiceprice()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AutojobsalesinvoiceservicechargelineCriteria> copyFiltersAre(
        AutojobsalesinvoiceservicechargelineCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInvoiceid(), copy.getInvoiceid()) &&
                condition.apply(criteria.getLineid(), copy.getLineid()) &&
                condition.apply(criteria.getOptionid(), copy.getOptionid()) &&
                condition.apply(criteria.getServicename(), copy.getServicename()) &&
                condition.apply(criteria.getServicediscription(), copy.getServicediscription()) &&
                condition.apply(criteria.getValue(), copy.getValue()) &&
                condition.apply(criteria.getAddedbyid(), copy.getAddedbyid()) &&
                condition.apply(criteria.getIscustomersrvice(), copy.getIscustomersrvice()) &&
                condition.apply(criteria.getDiscount(), copy.getDiscount()) &&
                condition.apply(criteria.getServiceprice(), copy.getServiceprice()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
