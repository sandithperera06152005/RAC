package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AutojobsalesinvoiceservicechargelineTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Autojobsalesinvoiceservicechargeline getAutojobsalesinvoiceservicechargelineSample1() {
        return new Autojobsalesinvoiceservicechargeline()
            .id(1L)
            .invoiceid(1)
            .lineid(1)
            .optionid(1)
            .servicename("servicename1")
            .servicediscription("servicediscription1")
            .addedbyid(1);
    }

    public static Autojobsalesinvoiceservicechargeline getAutojobsalesinvoiceservicechargelineSample2() {
        return new Autojobsalesinvoiceservicechargeline()
            .id(2L)
            .invoiceid(2)
            .lineid(2)
            .optionid(2)
            .servicename("servicename2")
            .servicediscription("servicediscription2")
            .addedbyid(2);
    }

    public static Autojobsalesinvoiceservicechargeline getAutojobsalesinvoiceservicechargelineRandomSampleGenerator() {
        return new Autojobsalesinvoiceservicechargeline()
            .id(longCount.incrementAndGet())
            .invoiceid(intCount.incrementAndGet())
            .lineid(intCount.incrementAndGet())
            .optionid(intCount.incrementAndGet())
            .servicename(UUID.randomUUID().toString())
            .servicediscription(UUID.randomUUID().toString())
            .addedbyid(intCount.incrementAndGet());
    }
}
