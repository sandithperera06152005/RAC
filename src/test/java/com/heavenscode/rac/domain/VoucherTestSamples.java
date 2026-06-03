package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class VoucherTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Voucher getVoucherSample1() {
        return new Voucher()
            .id(1L)
            .code("code1")
            .supplierName("supplierName1")
            .supplierAddress("supplierAddress1")
            .totalAmountInWord("totalAmountInWord1")
            .comments("comments1")
            .lmu(1)
            .termId(1)
            .term("term1")
            .supplierID(1)
            .createdBy(1);
    }

    public static Voucher getVoucherSample2() {
        return new Voucher()
            .id(2L)
            .code("code2")
            .supplierName("supplierName2")
            .supplierAddress("supplierAddress2")
            .totalAmountInWord("totalAmountInWord2")
            .comments("comments2")
            .lmu(2)
            .termId(2)
            .term("term2")
            .supplierID(2)
            .createdBy(2);
    }

    public static Voucher getVoucherRandomSampleGenerator() {
        return new Voucher()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .supplierName(UUID.randomUUID().toString())
            .supplierAddress(UUID.randomUUID().toString())
            .totalAmountInWord(UUID.randomUUID().toString())
            .comments(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet())
            .termId(intCount.incrementAndGet())
            .term(UUID.randomUUID().toString())
            .supplierID(intCount.incrementAndGet())
            .createdBy(intCount.incrementAndGet());
    }
}
