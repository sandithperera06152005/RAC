package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SalesInvoiceDummyTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SalesInvoiceDummy getSalesInvoiceDummySample1() {
        return new SalesInvoiceDummy()
            .id(1)
            .originalInvoiceId(1)
            .code("code1")
            .quoteID(1)
            .orderID(1)
            .salesRepID(1)
            .salesRepName("salesRepName1")
            .deliverFrom("deliverFrom1")
            .customerID(1)
            .customerName("customerName1")
            .customerAddress("customerAddress1")
            .deliveryAddress("deliveryAddress1")
            .message("message1")
            .lmu(1)
            .locationID(1)
            .locationCode("locationCode1")
            .referenceCode("referenceCode1")
            .createdById(1)
            .createdByName("createdByName1")
            .autoCareJobId(1)
            .vehicleNo("vehicleNo1")
            .paidBy(1)
            .originalInvoiceCode("originalInvoiceCode1");
    }

    public static SalesInvoiceDummy getSalesInvoiceDummySample2() {
        return new SalesInvoiceDummy()
            .id(2)
            .originalInvoiceId(2)
            .code("code2")
            .quoteID(2)
            .orderID(2)
            .salesRepID(2)
            .salesRepName("salesRepName2")
            .deliverFrom("deliverFrom2")
            .customerID(2)
            .customerName("customerName2")
            .customerAddress("customerAddress2")
            .deliveryAddress("deliveryAddress2")
            .message("message2")
            .lmu(2)
            .locationID(2)
            .locationCode("locationCode2")
            .referenceCode("referenceCode2")
            .createdById(2)
            .createdByName("createdByName2")
            .autoCareJobId(2)
            .vehicleNo("vehicleNo2")
            .paidBy(2)
            .originalInvoiceCode("originalInvoiceCode2");
    }

    public static SalesInvoiceDummy getSalesInvoiceDummyRandomSampleGenerator() {
        return new SalesInvoiceDummy()
            .id(intCount.incrementAndGet())
            .originalInvoiceId(intCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .quoteID(intCount.incrementAndGet())
            .orderID(intCount.incrementAndGet())
            .salesRepID(intCount.incrementAndGet())
            .salesRepName(UUID.randomUUID().toString())
            .deliverFrom(UUID.randomUUID().toString())
            .customerID(intCount.incrementAndGet())
            .customerName(UUID.randomUUID().toString())
            .customerAddress(UUID.randomUUID().toString())
            .deliveryAddress(UUID.randomUUID().toString())
            .message(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet())
            .locationID(intCount.incrementAndGet())
            .locationCode(UUID.randomUUID().toString())
            .referenceCode(UUID.randomUUID().toString())
            .createdById(intCount.incrementAndGet())
            .createdByName(UUID.randomUUID().toString())
            .autoCareJobId(intCount.incrementAndGet())
            .vehicleNo(UUID.randomUUID().toString())
            .paidBy(intCount.incrementAndGet())
            .originalInvoiceCode(UUID.randomUUID().toString());
    }
}
