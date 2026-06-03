package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SalesInvoiceLinesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SalesInvoiceLines getSalesInvoiceLinesSample1() {
        return new SalesInvoiceLines()
            .id(1L)
            .invoiceid(1)
            .lineid(1)
            .itemid(1)
            .itemcode("itemcode1")
            .itemname("itemname1")
            .description("description1")
            .unitofmeasurement("unitofmeasurement1")
            .lmu(1);
    }

    public static SalesInvoiceLines getSalesInvoiceLinesSample2() {
        return new SalesInvoiceLines()
            .id(2L)
            .invoiceid(2)
            .lineid(2)
            .itemid(2)
            .itemcode("itemcode2")
            .itemname("itemname2")
            .description("description2")
            .unitofmeasurement("unitofmeasurement2")
            .lmu(2);
    }

    public static SalesInvoiceLines getSalesInvoiceLinesRandomSampleGenerator() {
        return new SalesInvoiceLines()
            .id(longCount.incrementAndGet())
            .invoiceid(intCount.incrementAndGet())
            .lineid(intCount.incrementAndGet())
            .itemid(intCount.incrementAndGet())
            .itemcode(UUID.randomUUID().toString())
            .itemname(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .unitofmeasurement(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet());
    }
}
