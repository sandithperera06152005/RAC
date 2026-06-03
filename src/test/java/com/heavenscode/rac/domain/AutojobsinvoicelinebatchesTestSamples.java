package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class AutojobsinvoicelinebatchesTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Autojobsinvoicelinebatches getAutojobsinvoicelinebatchesSample1() {
        return new Autojobsinvoicelinebatches()
            .id(1)
            .lineid(1)
            .batchlineid(1)
            .itemid(1)
            .code("code1")
            .batchid(1)
            .batchcode("batchcode1")
            .notes("notes1")
            .lmu(1)
            .issuedby(1)
            .addedbyid(1)
            .canceloptid(1)
            .cancelopt("cancelopt1")
            .cancelby(1);
    }

    public static Autojobsinvoicelinebatches getAutojobsinvoicelinebatchesSample2() {
        return new Autojobsinvoicelinebatches()
            .id(2)
            .lineid(2)
            .batchlineid(2)
            .itemid(2)
            .code("code2")
            .batchid(2)
            .batchcode("batchcode2")
            .notes("notes2")
            .lmu(2)
            .issuedby(2)
            .addedbyid(2)
            .canceloptid(2)
            .cancelopt("cancelopt2")
            .cancelby(2);
    }

    public static Autojobsinvoicelinebatches getAutojobsinvoicelinebatchesRandomSampleGenerator() {
        return new Autojobsinvoicelinebatches()
            .id(intCount.incrementAndGet())
            .lineid(intCount.incrementAndGet())
            .batchlineid(intCount.incrementAndGet())
            .itemid(intCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .batchid(intCount.incrementAndGet())
            .batchcode(UUID.randomUUID().toString())
            .notes(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet())
            .issuedby(intCount.incrementAndGet())
            .addedbyid(intCount.incrementAndGet())
            .canceloptid(intCount.incrementAndGet())
            .cancelopt(UUID.randomUUID().toString())
            .cancelby(intCount.incrementAndGet());
    }
}
