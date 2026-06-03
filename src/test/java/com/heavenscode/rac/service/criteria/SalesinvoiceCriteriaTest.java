package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SalesinvoiceCriteriaTest {

    @Test
    void newSalesinvoiceCriteriaHasAllFiltersNullTest() {
        var salesinvoiceCriteria = new SalesinvoiceCriteria();
        assertThat(salesinvoiceCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void salesinvoiceCriteriaFluentMethodsCreatesFiltersTest() {
        var salesinvoiceCriteria = new SalesinvoiceCriteria();

        setAllFilters(salesinvoiceCriteria);

        assertThat(salesinvoiceCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void salesinvoiceCriteriaCopyCreatesNullFilterTest() {
        var salesinvoiceCriteria = new SalesinvoiceCriteria();
        var copy = salesinvoiceCriteria.copy();

        assertThat(salesinvoiceCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(salesinvoiceCriteria)
        );
    }

    @Test
    void salesinvoiceCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var salesinvoiceCriteria = new SalesinvoiceCriteria();
        setAllFilters(salesinvoiceCriteria);

        var copy = salesinvoiceCriteria.copy();

        assertThat(salesinvoiceCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(salesinvoiceCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var salesinvoiceCriteria = new SalesinvoiceCriteria();

        assertThat(salesinvoiceCriteria).hasToString("SalesinvoiceCriteria{}");
    }

    private static void setAllFilters(SalesinvoiceCriteria salesinvoiceCriteria) {
        salesinvoiceCriteria.id();
        salesinvoiceCriteria.code();
        salesinvoiceCriteria.invoicedate();
        salesinvoiceCriteria.createddate();
        salesinvoiceCriteria.quoteid();
        salesinvoiceCriteria.orderid();
        salesinvoiceCriteria.delieverydate();
        salesinvoiceCriteria.salesrepid();
        salesinvoiceCriteria.salesrepname();
        salesinvoiceCriteria.delieverfrom();
        salesinvoiceCriteria.customerid();
        salesinvoiceCriteria.customername();
        salesinvoiceCriteria.customeraddress();
        salesinvoiceCriteria.deliveryaddress();
        salesinvoiceCriteria.subtotal();
        salesinvoiceCriteria.totaltax();
        salesinvoiceCriteria.totaldiscount();
        salesinvoiceCriteria.nettotal();
        salesinvoiceCriteria.message();
        salesinvoiceCriteria.lmu();
        salesinvoiceCriteria.lmd();
        salesinvoiceCriteria.paidamount();
        salesinvoiceCriteria.amountowing();
        salesinvoiceCriteria.isactive();
        salesinvoiceCriteria.locationid();
        salesinvoiceCriteria.locationcode();
        salesinvoiceCriteria.referencecode();
        salesinvoiceCriteria.createdbyid();
        salesinvoiceCriteria.createdbyname();
        salesinvoiceCriteria.autocarecharges();
        salesinvoiceCriteria.autocarejobid();
        salesinvoiceCriteria.vehicleno();
        salesinvoiceCriteria.nextmeter();
        salesinvoiceCriteria.currentmeter();
        salesinvoiceCriteria.remarks();
        salesinvoiceCriteria.hasdummybill();
        salesinvoiceCriteria.dummybillid();
        salesinvoiceCriteria.dummybillamount();
        salesinvoiceCriteria.dummycommision();
        salesinvoiceCriteria.isserviceinvoice();
        salesinvoiceCriteria.nbtamount();
        salesinvoiceCriteria.vatamount();
        salesinvoiceCriteria.autocarecompanyid();
        salesinvoiceCriteria.iscompanyinvoice();
        salesinvoiceCriteria.invcanceldate();
        salesinvoiceCriteria.invcancelby();
        salesinvoiceCriteria.isvatinvoice();
        salesinvoiceCriteria.paymenttype();
        salesinvoiceCriteria.pendingamount();
        salesinvoiceCriteria.advancepayment();
        salesinvoiceCriteria.discountcode();
        salesinvoiceCriteria.distinct();
    }

    private static Condition<SalesinvoiceCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getInvoicedate()) &&
                condition.apply(criteria.getCreateddate()) &&
                condition.apply(criteria.getQuoteid()) &&
                condition.apply(criteria.getOrderid()) &&
                condition.apply(criteria.getDelieverydate()) &&
                condition.apply(criteria.getSalesrepid()) &&
                condition.apply(criteria.getSalesrepname()) &&
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
                condition.apply(criteria.getAutocarecharges()) &&
                condition.apply(criteria.getAutocarejobid()) &&
                condition.apply(criteria.getVehicleno()) &&
                condition.apply(criteria.getNextmeter()) &&
                condition.apply(criteria.getCurrentmeter()) &&
                condition.apply(criteria.getRemarks()) &&
                condition.apply(criteria.getHasdummybill()) &&
                condition.apply(criteria.getDummybillid()) &&
                condition.apply(criteria.getDummybillamount()) &&
                condition.apply(criteria.getDummycommision()) &&
                condition.apply(criteria.getIsserviceinvoice()) &&
                condition.apply(criteria.getNbtamount()) &&
                condition.apply(criteria.getVatamount()) &&
                condition.apply(criteria.getAutocarecompanyid()) &&
                condition.apply(criteria.getIscompanyinvoice()) &&
                condition.apply(criteria.getInvcanceldate()) &&
                condition.apply(criteria.getInvcancelby()) &&
                condition.apply(criteria.getIsvatinvoice()) &&
                condition.apply(criteria.getPaymenttype()) &&
                condition.apply(criteria.getPendingamount()) &&
                condition.apply(criteria.getAdvancepayment()) &&
                condition.apply(criteria.getDiscountcode()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SalesinvoiceCriteria> copyFiltersAre(
        SalesinvoiceCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getInvoicedate(), copy.getInvoicedate()) &&
                condition.apply(criteria.getCreateddate(), copy.getCreateddate()) &&
                condition.apply(criteria.getQuoteid(), copy.getQuoteid()) &&
                condition.apply(criteria.getOrderid(), copy.getOrderid()) &&
                condition.apply(criteria.getDelieverydate(), copy.getDelieverydate()) &&
                condition.apply(criteria.getSalesrepid(), copy.getSalesrepid()) &&
                condition.apply(criteria.getSalesrepname(), copy.getSalesrepname()) &&
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
                condition.apply(criteria.getAutocarecharges(), copy.getAutocarecharges()) &&
                condition.apply(criteria.getAutocarejobid(), copy.getAutocarejobid()) &&
                condition.apply(criteria.getVehicleno(), copy.getVehicleno()) &&
                condition.apply(criteria.getNextmeter(), copy.getNextmeter()) &&
                condition.apply(criteria.getCurrentmeter(), copy.getCurrentmeter()) &&
                condition.apply(criteria.getRemarks(), copy.getRemarks()) &&
                condition.apply(criteria.getHasdummybill(), copy.getHasdummybill()) &&
                condition.apply(criteria.getDummybillid(), copy.getDummybillid()) &&
                condition.apply(criteria.getDummybillamount(), copy.getDummybillamount()) &&
                condition.apply(criteria.getDummycommision(), copy.getDummycommision()) &&
                condition.apply(criteria.getIsserviceinvoice(), copy.getIsserviceinvoice()) &&
                condition.apply(criteria.getNbtamount(), copy.getNbtamount()) &&
                condition.apply(criteria.getVatamount(), copy.getVatamount()) &&
                condition.apply(criteria.getAutocarecompanyid(), copy.getAutocarecompanyid()) &&
                condition.apply(criteria.getIscompanyinvoice(), copy.getIscompanyinvoice()) &&
                condition.apply(criteria.getInvcanceldate(), copy.getInvcanceldate()) &&
                condition.apply(criteria.getInvcancelby(), copy.getInvcancelby()) &&
                condition.apply(criteria.getIsvatinvoice(), copy.getIsvatinvoice()) &&
                condition.apply(criteria.getPaymenttype(), copy.getPaymenttype()) &&
                condition.apply(criteria.getPendingamount(), copy.getPendingamount()) &&
                condition.apply(criteria.getAdvancepayment(), copy.getAdvancepayment()) &&
                condition.apply(criteria.getDiscountcode(), copy.getDiscountcode()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
