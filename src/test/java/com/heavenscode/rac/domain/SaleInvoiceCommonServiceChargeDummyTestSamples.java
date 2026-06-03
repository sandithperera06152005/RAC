package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SaleInvoiceCommonServiceChargeDummyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SaleInvoiceCommonServiceChargeDummy getSaleInvoiceCommonServiceChargeDummySample1() {
        return new SaleInvoiceCommonServiceChargeDummy()
            .id(1L)
            .invoiceid(1)
            .lineid(1)
            .optionid(1)
            .mainid(1)
            .code("code1")
            .name("name1")
            .description("description1");
    }

    public static SaleInvoiceCommonServiceChargeDummy getSaleInvoiceCommonServiceChargeDummySample2() {
        return new SaleInvoiceCommonServiceChargeDummy()
            .id(2L)
            .invoiceid(2)
            .lineid(2)
            .optionid(2)
            .mainid(2)
            .code("code2")
            .name("name2")
            .description("description2");
    }

    public static SaleInvoiceCommonServiceChargeDummy getSaleInvoiceCommonServiceChargeDummyRandomSampleGenerator() {
        return new SaleInvoiceCommonServiceChargeDummy()
            .id(longCount.incrementAndGet())
            .invoiceid(intCount.incrementAndGet())
            .lineid(intCount.incrementAndGet())
            .optionid(intCount.incrementAndGet())
            .mainid(intCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
