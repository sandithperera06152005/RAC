package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class VoucherLinesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static VoucherLines getVoucherLinesSample1() {
        return new VoucherLines().id(1L).lineID(1).grnCode("grnCode1").grnType("grnType1").lmu(1).accountId(1);
    }

    public static VoucherLines getVoucherLinesSample2() {
        return new VoucherLines().id(2L).lineID(2).grnCode("grnCode2").grnType("grnType2").lmu(2).accountId(2);
    }

    public static VoucherLines getVoucherLinesRandomSampleGenerator() {
        return new VoucherLines()
            .id(longCount.incrementAndGet())
            .lineID(intCount.incrementAndGet())
            .grnCode(UUID.randomUUID().toString())
            .grnType(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet())
            .accountId(intCount.incrementAndGet());
    }
}
