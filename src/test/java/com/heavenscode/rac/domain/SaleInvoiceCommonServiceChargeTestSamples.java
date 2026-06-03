package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SaleInvoiceCommonServiceChargeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SaleInvoiceCommonServiceCharge getSaleInvoiceCommonServiceChargeSample1() {
        return new SaleInvoiceCommonServiceCharge()
            .id(1L)
            .invoiceId(1)
            .lineId(1)
            .optionId(1)
            .mainId(1)
            .code("code1")
            .name("name1")
            .description("description1");
    }

    public static SaleInvoiceCommonServiceCharge getSaleInvoiceCommonServiceChargeSample2() {
        return new SaleInvoiceCommonServiceCharge()
            .id(2L)
            .invoiceId(2)
            .lineId(2)
            .optionId(2)
            .mainId(2)
            .code("code2")
            .name("name2")
            .description("description2");
    }

    public static SaleInvoiceCommonServiceCharge getSaleInvoiceCommonServiceChargeRandomSampleGenerator() {
        return new SaleInvoiceCommonServiceCharge()
            .id(longCount.incrementAndGet())
            .invoiceId(intCount.incrementAndGet())
            .lineId(intCount.incrementAndGet())
            .optionId(intCount.incrementAndGet())
            .mainId(intCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
