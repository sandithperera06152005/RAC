package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class VoucherPaymentsDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static VoucherPaymentsDetails getVoucherPaymentsDetailsSample1() {
        return new VoucherPaymentsDetails()
            .id(1L)
            .lineID(1)
            .checkqueNo("checkqueNo1")
            .bankName("bankName1")
            .bankID(1)
            .creditCardNo("creditCardNo1")
            .reference("reference1")
            .otherDetails("otherDetails1")
            .lmu("lmu1")
            .termID(1)
            .termName("termName1")
            .accountNo(1)
            .accountNumber(1L)
            .accountId(1)
            .accountCode("accountCode1")
            .chequeStatusId(1)
            .companyBankId(1);
    }

    public static VoucherPaymentsDetails getVoucherPaymentsDetailsSample2() {
        return new VoucherPaymentsDetails()
            .id(2L)
            .lineID(2)
            .checkqueNo("checkqueNo2")
            .bankName("bankName2")
            .bankID(2)
            .creditCardNo("creditCardNo2")
            .reference("reference2")
            .otherDetails("otherDetails2")
            .lmu("lmu2")
            .termID(2)
            .termName("termName2")
            .accountNo(2)
            .accountNumber(2L)
            .accountId(2)
            .accountCode("accountCode2")
            .chequeStatusId(2)
            .companyBankId(2);
    }

    public static VoucherPaymentsDetails getVoucherPaymentsDetailsRandomSampleGenerator() {
        return new VoucherPaymentsDetails()
            .id(longCount.incrementAndGet())
            .lineID(intCount.incrementAndGet())
            .checkqueNo(UUID.randomUUID().toString())
            .bankName(UUID.randomUUID().toString())
            .bankID(intCount.incrementAndGet())
            .creditCardNo(UUID.randomUUID().toString())
            .reference(UUID.randomUUID().toString())
            .otherDetails(UUID.randomUUID().toString())
            .lmu(UUID.randomUUID().toString())
            .termID(intCount.incrementAndGet())
            .termName(UUID.randomUUID().toString())
            .accountNo(intCount.incrementAndGet())
            .accountNumber(longCount.incrementAndGet())
            .accountId(intCount.incrementAndGet())
            .accountCode(UUID.randomUUID().toString())
            .chequeStatusId(intCount.incrementAndGet())
            .companyBankId(intCount.incrementAndGet());
    }
}
