package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class WorkshopVehicleWorkListTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static WorkshopVehicleWorkList getWorkshopVehicleWorkListSample1() {
        return new WorkshopVehicleWorkList()
            .id(1L)
            .vehicleworkid(1)
            .lineid(1)
            .workid(1)
            .workshopwork("workshopwork1")
            .jobnumber("jobnumber1");
    }

    public static WorkshopVehicleWorkList getWorkshopVehicleWorkListSample2() {
        return new WorkshopVehicleWorkList()
            .id(2L)
            .vehicleworkid(2)
            .lineid(2)
            .workid(2)
            .workshopwork("workshopwork2")
            .jobnumber("jobnumber2");
    }

    public static WorkshopVehicleWorkList getWorkshopVehicleWorkListRandomSampleGenerator() {
        return new WorkshopVehicleWorkList()
            .id(longCount.incrementAndGet())
            .vehicleworkid(intCount.incrementAndGet())
            .lineid(intCount.incrementAndGet())
            .workid(intCount.incrementAndGet())
            .workshopwork(UUID.randomUUID().toString())
            .jobnumber(UUID.randomUUID().toString());
    }
}
