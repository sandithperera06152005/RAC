package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AutojobsinvoiceCriteriaTest {

    @Test
    void newAutojobsinvoiceCriteriaHasAllFiltersNullTest() {
        var autojobsinvoiceCriteria = new AutojobsinvoiceCriteria();
        assertThat(autojobsinvoiceCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void autojobsinvoiceCriteriaFluentMethodsCreatesFiltersTest() {
        var autojobsinvoiceCriteria = new AutojobsinvoiceCriteria();

        setAllFilters(autojobsinvoiceCriteria);

        assertThat(autojobsinvoiceCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void autojobsinvoiceCriteriaCopyCreatesNullFilterTest() {
        var autojobsinvoiceCriteria = new AutojobsinvoiceCriteria();
        var copy = autojobsinvoiceCriteria.copy();

        assertThat(autojobsinvoiceCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsinvoiceCriteria)
        );
    }

    @Test
    void autojobsinvoiceCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var autojobsinvoiceCriteria = new AutojobsinvoiceCriteria();
        setAllFilters(autojobsinvoiceCriteria);

        var copy = autojobsinvoiceCriteria.copy();

        assertThat(autojobsinvoiceCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(autojobsinvoiceCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var autojobsinvoiceCriteria = new AutojobsinvoiceCriteria();

        assertThat(autojobsinvoiceCriteria).hasToString("AutojobsinvoiceCriteria{}");
    }

    private static void setAllFilters(AutojobsinvoiceCriteria autojobsinvoiceCriteria) {
        autojobsinvoiceCriteria.id();
        autojobsinvoiceCriteria.code();
        autojobsinvoiceCriteria.invoicedate();
        autojobsinvoiceCriteria.createddate();
        autojobsinvoiceCriteria.jobid();
        autojobsinvoiceCriteria.quoteid();
        autojobsinvoiceCriteria.orderid();
        autojobsinvoiceCriteria.delieverydate();
        autojobsinvoiceCriteria.autojobsrepid();
        autojobsinvoiceCriteria.autojobsrepname();
        autojobsinvoiceCriteria.delieverfrom();
        autojobsinvoiceCriteria.customerid();
        autojobsinvoiceCriteria.customername();
        autojobsinvoiceCriteria.customeraddress();
        autojobsinvoiceCriteria.deliveryaddress();
        autojobsinvoiceCriteria.subtotal();
        autojobsinvoiceCriteria.totaltax();
        autojobsinvoiceCriteria.totaldiscount();
        autojobsinvoiceCriteria.nettotal();
        autojobsinvoiceCriteria.message();
        autojobsinvoiceCriteria.lmu();
        autojobsinvoiceCriteria.lmd();
        autojobsinvoiceCriteria.paidamount();
        autojobsinvoiceCriteria.amountowing();
        autojobsinvoiceCriteria.isactive();
        autojobsinvoiceCriteria.locationid();
        autojobsinvoiceCriteria.locationcode();
        autojobsinvoiceCriteria.referencecode();
        autojobsinvoiceCriteria.createdbyid();
        autojobsinvoiceCriteria.createdbyname();
        autojobsinvoiceCriteria.autocarecompanyid();
        autojobsinvoiceCriteria.distinct();
    }

    private static Condition<AutojobsinvoiceCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getInvoicedate()) &&
                condition.apply(criteria.getCreateddate()) &&
                condition.apply(criteria.getJobid()) &&
                condition.apply(criteria.getQuoteid()) &&
                condition.apply(criteria.getOrderid()) &&
                condition.apply(criteria.getDelieverydate()) &&
                condition.apply(criteria.getAutojobsrepid()) &&
                condition.apply(criteria.getAutojobsrepname()) &&
                condition.apply(criteria.getDelieverfrom()) &&
                condition.apply(criteria.getCustomerid()) &&
                condition.apply(criteria.getCustomername()) &&
                condition.apply(criteria.getCustomeraddress()) &&
                condition.apply(criteria.getDeliveryaddress()) &&
                condition.apply(criteria.getSubtotal()) &&
                condition.apply(criteria.getTotaltax()) &&
                condition.apply(criteria.getTotaldiscount()) &&
                condition.apply(criteria.getNettotal()) &&
                condition.apply(criteria.getMessage()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getPaidamount()) &&
                condition.apply(criteria.getAmountowing()) &&
                condition.apply(criteria.getIsactive()) &&
                condition.apply(criteria.getLocationid()) &&
                condition.apply(criteria.getLocationcode()) &&
                condition.apply(criteria.getReferencecode()) &&
                condition.apply(criteria.getCreatedbyid()) &&
                condition.apply(criteria.getCreatedbyname()) &&
                condition.apply(criteria.getAutocarecompanyid()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AutojobsinvoiceCriteria> copyFiltersAre(
        AutojobsinvoiceCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getInvoicedate(), copy.getInvoicedate()) &&
                condition.apply(criteria.getCreateddate(), copy.getCreateddate()) &&
                condition.apply(criteria.getJobid(), copy.getJobid()) &&
                condition.apply(criteria.getQuoteid(), copy.getQuoteid()) &&
                condition.apply(criteria.getOrderid(), copy.getOrderid()) &&
                condition.apply(criteria.getDelieverydate(), copy.getDelieverydate()) &&
                condition.apply(criteria.getAutojobsrepid(), copy.getAutojobsrepid()) &&
                condition.apply(criteria.getAutojobsrepname(), copy.getAutojobsrepname()) &&
                condition.apply(criteria.getDelieverfrom(), copy.getDelieverfrom()) &&
                condition.apply(criteria.getCustomerid(), copy.getCustomerid()) &&
                condition.apply(criteria.getCustomername(), copy.getCustomername()) &&
                condition.apply(criteria.getCustomeraddress(), copy.getCustomeraddress()) &&
                condition.apply(criteria.getDeliveryaddress(), copy.getDeliveryaddress()) &&
                condition.apply(criteria.getSubtotal(), copy.getSubtotal()) &&
                condition.apply(criteria.getTotaltax(), copy.getTotaltax()) &&
                condition.apply(criteria.getTotaldiscount(), copy.getTotaldiscount()) &&
                condition.apply(criteria.getNettotal(), copy.getNettotal()) &&
                condition.apply(criteria.getMessage(), copy.getMessage()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getPaidamount(), copy.getPaidamount()) &&
                condition.apply(criteria.getAmountowing(), copy.getAmountowing()) &&
                condition.apply(criteria.getIsactive(), copy.getIsactive()) &&
                condition.apply(criteria.getLocationid(), copy.getLocationid()) &&
                condition.apply(criteria.getLocationcode(), copy.getLocationcode()) &&
                condition.apply(criteria.getReferencecode(), copy.getReferencecode()) &&
                condition.apply(criteria.getCreatedbyid(), copy.getCreatedbyid()) &&
                condition.apply(criteria.getCreatedbyname(), copy.getCreatedbyname()) &&
                condition.apply(criteria.getAutocarecompanyid(), copy.getAutocarecompanyid()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
