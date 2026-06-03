package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AutojobsinvoicelinesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Autojobsinvoicelines getAutojobsinvoicelinesSample1() {
        return new Autojobsinvoicelines()
            .id(1L)
            .invocieid(1)
            .lineid(1)
            .itemid(1)
            .itemcode("itemcode1")
            .itemname("itemname1")
            .description("description1")
            .unitofmeasurement("unitofmeasurement1")
            .lmu(1);
    }

    public static Autojobsinvoicelines getAutojobsinvoicelinesSample2() {
        return new Autojobsinvoicelines()
            .id(2L)
            .invocieid(2)
            .lineid(2)
            .itemid(2)
            .itemcode("itemcode2")
            .itemname("itemname2")
            .description("description2")
            .unitofmeasurement("unitofmeasurement2")
            .lmu(2);
    }

    public static Autojobsinvoicelines getAutojobsinvoicelinesRandomSampleGenerator() {
        return new Autojobsinvoicelines()
            .id(longCount.incrementAndGet())
            .invocieid(intCount.incrementAndGet())
            .lineid(intCount.incrementAndGet())
            .itemid(intCount.incrementAndGet())
            .itemcode(UUID.randomUUID().toString())
            .itemname(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .unitofmeasurement(UUID.randomUUID().toString())
            .lmu(intCount.incrementAndGet());
    }
}
