package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SalesInvoiceServiceChargeLineDummyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SalesInvoiceServiceChargeLineDummy getSalesInvoiceServiceChargeLineDummySample1() {
        return new SalesInvoiceServiceChargeLineDummy()
            .id(1L)
            .invoiceId(1)
            .lineId(1)
            .optionId(1)
            .serviceName("serviceName1")
            .serviceDescription("serviceDescription1");
    }

    public static SalesInvoiceServiceChargeLineDummy getSalesInvoiceServiceChargeLineDummySample2() {
        return new SalesInvoiceServiceChargeLineDummy()
            .id(2L)
            .invoiceId(2)
            .lineId(2)
            .optionId(2)
            .serviceName("serviceName2")
            .serviceDescription("serviceDescription2");
    }

    public static SalesInvoiceServiceChargeLineDummy getSalesInvoiceServiceChargeLineDummyRandomSampleGenerator() {
        return new SalesInvoiceServiceChargeLineDummy()
            .id(longCount.incrementAndGet())
            .invoiceId(intCount.incrementAndGet())
            .lineId(intCount.incrementAndGet())
            .optionId(intCount.incrementAndGet())
            .serviceName(UUID.randomUUID().toString())
            .serviceDescription(UUID.randomUUID().toString());
    }
}
