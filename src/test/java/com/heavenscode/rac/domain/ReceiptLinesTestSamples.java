package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReceiptLinesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ReceiptLines getReceiptLinesSample1() {
        return new ReceiptLines().id(1L).lineid(1L).invoicecode("invoicecode1").invoicetype("invoicetype1").lmu(1L).accountid(1L);
    }

    public static ReceiptLines getReceiptLinesSample2() {
        return new ReceiptLines().id(2L).lineid(2L).invoicecode("invoicecode2").invoicetype("invoicetype2").lmu(2L).accountid(2L);
    }

    public static ReceiptLines getReceiptLinesRandomSampleGenerator() {
        return new ReceiptLines()
            .id(longCount.incrementAndGet())
            .lineid(longCount.incrementAndGet())
            .invoicecode(UUID.randomUUID().toString())
            .invoicetype(UUID.randomUUID().toString())
            .lmu(longCount.incrementAndGet())
            .accountid(longCount.incrementAndGet());
    }
}
