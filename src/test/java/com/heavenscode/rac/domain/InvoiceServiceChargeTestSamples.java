package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InvoiceServiceChargeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static InvoiceServiceCharge getInvoiceServiceChargeSample1() {
        return new InvoiceServiceCharge()
            .id(1L)
            .invoiceId(1L)
            .lineId(1L)
            .optionId(1L)
            .serviceName("serviceName1")
            .serviceDiscription("serviceDiscription1")
            .addedById(1L);
    }

    public static InvoiceServiceCharge getInvoiceServiceChargeSample2() {
        return new InvoiceServiceCharge()
            .id(2L)
            .invoiceId(2L)
            .lineId(2L)
            .optionId(2L)
            .serviceName("serviceName2")
            .serviceDiscription("serviceDiscription2")
            .addedById(2L);
    }

    public static InvoiceServiceCharge getInvoiceServiceChargeRandomSampleGenerator() {
        return new InvoiceServiceCharge()
            .id(longCount.incrementAndGet())
            .invoiceId(longCount.incrementAndGet())
            .lineId(longCount.incrementAndGet())
            .optionId(longCount.incrementAndGet())
            .serviceName(UUID.randomUUID().toString())
            .serviceDiscription(UUID.randomUUID().toString())
            .addedById(longCount.incrementAndGet());
    }
}
