package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InvoiceServiceCommonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static InvoiceServiceCommon getInvoiceServiceCommonSample1() {
        return new InvoiceServiceCommon()
            .id(1L)
            .invoiceId(1L)
            .lineId(1L)
            .optionId(1L)
            .mainId(1L)
            .name("name1")
            .description("description1")
            .addedById(1L);
    }

    public static InvoiceServiceCommon getInvoiceServiceCommonSample2() {
        return new InvoiceServiceCommon()
            .id(2L)
            .invoiceId(2L)
            .lineId(2L)
            .optionId(2L)
            .mainId(2L)
            .name("name2")
            .description("description2")
            .addedById(2L);
    }

    public static InvoiceServiceCommon getInvoiceServiceCommonRandomSampleGenerator() {
        return new InvoiceServiceCommon()
            .id(longCount.incrementAndGet())
            .invoiceId(longCount.incrementAndGet())
            .lineId(longCount.incrementAndGet())
            .optionId(longCount.incrementAndGet())
            .mainId(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .addedById(longCount.incrementAndGet());
    }
}
