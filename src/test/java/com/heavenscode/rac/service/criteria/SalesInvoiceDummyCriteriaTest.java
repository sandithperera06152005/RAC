package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SalesInvoiceDummyCriteriaTest {

    @Test
    void newSalesInvoiceDummyCriteriaHasAllFiltersNullTest() {
        var salesInvoiceDummyCriteria = new SalesInvoiceDummyCriteria();
        assertThat(salesInvoiceDummyCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void salesInvoiceDummyCriteriaFluentMethodsCreatesFiltersTest() {
        var salesInvoiceDummyCriteria = new SalesInvoiceDummyCriteria();

        setAllFilters(salesInvoiceDummyCriteria);

        assertThat(salesInvoiceDummyCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void salesInvoiceDummyCriteriaCopyCreatesNullFilterTest() {
        var salesInvoiceDummyCriteria = new SalesInvoiceDummyCriteria();
        var copy = salesInvoiceDummyCriteria.copy();

        assertThat(salesInvoiceDummyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceDummyCriteria)
        );
    }

    @Test
    void salesInvoiceDummyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var salesInvoiceDummyCriteria = new SalesInvoiceDummyCriteria();
        setAllFilters(salesInvoiceDummyCriteria);

        var copy = salesInvoiceDummyCriteria.copy();

        assertThat(salesInvoiceDummyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(salesInvoiceDummyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var salesInvoiceDummyCriteria = new SalesInvoiceDummyCriteria();

        assertThat(salesInvoiceDummyCriteria).hasToString("SalesInvoiceDummyCriteria{}");
    }

    private static void setAllFilters(SalesInvoiceDummyCriteria salesInvoiceDummyCriteria) {
        salesInvoiceDummyCriteria.id();
        salesInvoiceDummyCriteria.originalInvoiceId();
        salesInvoiceDummyCriteria.code();
        salesInvoiceDummyCriteria.invoiceDate();
        salesInvoiceDummyCriteria.createdDate();
        salesInvoiceDummyCriteria.quoteID();
        salesInvoiceDummyCriteria.orderID();
        salesInvoiceDummyCriteria.deliveryDate();
        salesInvoiceDummyCriteria.salesRepID();
        salesInvoiceDummyCriteria.salesRepName();
        salesInvoiceDummyCriteria.deliverFrom();
        salesInvoiceDummyCriteria.customerID();
        salesInvoiceDummyCriteria.customerName();
        salesInvoiceDummyCriteria.customerAddress();
        salesInvoiceDummyCriteria.deliveryAddress();
        salesInvoiceDummyCriteria.subTotal();
        salesInvoiceDummyCriteria.totalTax();
        salesInvoiceDummyCriteria.totalDiscount();
        salesInvoiceDummyCriteria.netTotal();
        salesInvoiceDummyCriteria.message();
        salesInvoiceDummyCriteria.lmu();
        salesInvoiceDummyCriteria.lmd();
        salesInvoiceDummyCriteria.paidAmount();
        salesInvoiceDummyCriteria.amountOwing();
        salesInvoiceDummyCriteria.isActive();
        salesInvoiceDummyCriteria.locationID();
        salesInvoiceDummyCriteria.locationCode();
        salesInvoiceDummyCriteria.referenceCode();
        salesInvoiceDummyCriteria.createdById();
        salesInvoiceDummyCriteria.createdByName();
        salesInvoiceDummyCriteria.autoCareCharges();
        salesInvoiceDummyCriteria.autoCareJobId();
        salesInvoiceDummyCriteria.vehicleNo();
        salesInvoiceDummyCriteria.nbtAmount();
        salesInvoiceDummyCriteria.vatAmount();
        salesInvoiceDummyCriteria.dummyCommission();
        salesInvoiceDummyCriteria.commissionPaidDate();
        salesInvoiceDummyCriteria.paidCommission();
        salesInvoiceDummyCriteria.paidBy();
        salesInvoiceDummyCriteria.originalInvoiceCode();
        salesInvoiceDummyCriteria.distinct();
    }

    private static Condition<SalesInvoiceDummyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getOriginalInvoiceId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getInvoiceDate()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getQuoteID()) &&
                condition.apply(criteria.getOrderID()) &&
                condition.apply(criteria.getDeliveryDate()) &&
                condition.apply(criteria.getSalesRepID()) &&
                condition.apply(criteria.getSalesRepName()) &&
                condition.apply(criteria.getDeliverFrom()) &&
                condition.apply(criteria.getCustomerID()) &&
                condition.apply(criteria.getCustomerName()) &&
                condition.apply(criteria.getCustomerAddress()) &&
                condition.apply(criteria.getDeliveryAddress()) &&
                condition.apply(criteria.getSubTotal()) &&
                condition.apply(criteria.getTotalTax()) &&
                condition.apply(criteria.getTotalDiscount()) &&
                condition.apply(criteria.getNetTotal()) &&
                condition.apply(criteria.getMessage()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getPaidAmount()) &&
                condition.apply(criteria.getAmountOwing()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getLocationID()) &&
                condition.apply(criteria.getLocationCode()) &&
                condition.apply(criteria.getReferenceCode()) &&
                condition.apply(criteria.getCreatedById()) &&
                condition.apply(criteria.getCreatedByName()) &&
                condition.apply(criteria.getAutoCareCharges()) &&
                condition.apply(criteria.getAutoCareJobId()) &&
                condition.apply(criteria.getVehicleNo()) &&
                condition.apply(criteria.getNbtAmount()) &&
                condition.apply(criteria.getVatAmount()) &&
                condition.apply(criteria.getDummyCommission()) &&
                condition.apply(criteria.getCommissionPaidDate()) &&
                condition.apply(criteria.getPaidCommission()) &&
                condition.apply(criteria.getPaidBy()) &&
                condition.apply(criteria.getOriginalInvoiceCode()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SalesInvoiceDummyCriteria> copyFiltersAre(
        SalesInvoiceDummyCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getOriginalInvoiceId(), copy.getOriginalInvoiceId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getInvoiceDate(), copy.getInvoiceDate()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getQuoteID(), copy.getQuoteID()) &&
                condition.apply(criteria.getOrderID(), copy.getOrderID()) &&
                condition.apply(criteria.getDeliveryDate(), copy.getDeliveryDate()) &&
                condition.apply(criteria.getSalesRepID(), copy.getSalesRepID()) &&
                condition.apply(criteria.getSalesRepName(), copy.getSalesRepName()) &&
                condition.apply(criteria.getDeliverFrom(), copy.getDeliverFrom()) &&
                condition.apply(criteria.getCustomerID(), copy.getCustomerID()) &&
                condition.apply(criteria.getCustomerName(), copy.getCustomerName()) &&
                condition.apply(criteria.getCustomerAddress(), copy.getCustomerAddress()) &&
                condition.apply(criteria.getDeliveryAddress(), copy.getDeliveryAddress()) &&
                condition.apply(criteria.getSubTotal(), copy.getSubTotal()) &&
                condition.apply(criteria.getTotalTax(), copy.getTotalTax()) &&
                condition.apply(criteria.getTotalDiscount(), copy.getTotalDiscount()) &&
                condition.apply(criteria.getNetTotal(), copy.getNetTotal()) &&
                condition.apply(criteria.getMessage(), copy.getMessage()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getPaidAmount(), copy.getPaidAmount()) &&
                condition.apply(criteria.getAmountOwing(), copy.getAmountOwing()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getLocationID(), copy.getLocationID()) &&
                condition.apply(criteria.getLocationCode(), copy.getLocationCode()) &&
                condition.apply(criteria.getReferenceCode(), copy.getReferenceCode()) &&
                condition.apply(criteria.getCreatedById(), copy.getCreatedById()) &&
                condition.apply(criteria.getCreatedByName(), copy.getCreatedByName()) &&
                condition.apply(criteria.getAutoCareCharges(), copy.getAutoCareCharges()) &&
                condition.apply(criteria.getAutoCareJobId(), copy.getAutoCareJobId()) &&
                condition.apply(criteria.getVehicleNo(), copy.getVehicleNo()) &&
                condition.apply(criteria.getNbtAmount(), copy.getNbtAmount()) &&
                condition.apply(criteria.getVatAmount(), copy.getVatAmount()) &&
                condition.apply(criteria.getDummyCommission(), copy.getDummyCommission()) &&
                condition.apply(criteria.getCommissionPaidDate(), copy.getCommissionPaidDate()) &&
                condition.apply(criteria.getPaidCommission(), copy.getPaidCommission()) &&
                condition.apply(criteria.getPaidBy(), copy.getPaidBy()) &&
                condition.apply(criteria.getOriginalInvoiceCode(), copy.getOriginalInvoiceCode()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
