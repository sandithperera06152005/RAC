package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SalesInvoiceLinesDummyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SalesInvoiceLinesDummy getSalesInvoiceLinesDummySample1() {
        return new SalesInvoiceLinesDummy()
            .id(1L)
            .invoiceId(1)
            .lineId(1)
            .itemId(1)
            .itemCode("itemCode1")
            .itemName("itemName1")
            .description("description1")
            .unitOfMeasurement("unitOfMeasurement1")
            .lmu(1);
    }

    public static SalesInvoiceLinesDummy getSalesInvoiceLinesDummySample2() {
        return new SalesInvoiceLinesDummy()
            .id(2L)
            .invoiceId(2)
            .lineId(2)
            .itemId(2)
            .itemCode("itemCode2")
            .itemName("itemName2")
            .description("description2")
            .unitOfMeasurement("unitOfMeasurement2")
            .lmu(2);
    }

    public static SalesInvoiceLinesDummy getSalesInvoiceLinesDummyRandomSampleGenerator() {
        return new SalesInvoiceLinesDummy()
            .id(longCount.incrementAndGet())
            .invoiceId(intCount.incrementAndGet())
            .lineId(intCount.incrementAndGet())
            .itemId(intCount.incrementAndGet())
            .itemCode(UUID.randomUUID().toString())
            .itemName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .unitOfMeasurement(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet());
    }
}
