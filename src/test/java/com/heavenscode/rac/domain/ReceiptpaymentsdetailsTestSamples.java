package com.heavenscode.rac.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiptpaymentsdetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Receiptpaymentsdetails getReceiptpaymentsdetailsSample1() {
        return new Receiptpaymentsdetails()
            .id(1)
            .lineid(1)
            .checkqueno("checkqueno1")
            .bankname("bankname1")
            .bankid(1)
            .bankbranchname("bankbranchname1")
            .bankbranchid(1)
            .creditcardno("creditcardno1")
            .reference("reference1")
            .otherdetails("otherdetails1")
            .lmu("lmu1")
            .termid(1)
            .termname("termname1")
            .accountno("accountno1")
            .accountnumber("accountnumber1")
            .chequestatusid(1)
            .accountid(1)
            .accountcode("accountcode1")
            .bankdepositbankname("bankdepositbankname1")
            .bankdepositbankid(1)
            .bankdepositbankbranchname("bankdepositbankbranchname1")
            .bankdepositbankbranchid(1)
            .companybankid(1);
    }

    public static Receiptpaymentsdetails getReceiptpaymentsdetailsSample2() {
        return new Receiptpaymentsdetails()
            .id(2)
            .lineid(2)
            .checkqueno("checkqueno2")
            .bankname("bankname2")
            .bankid(2)
            .bankbranchname("bankbranchname2")
            .bankbranchid(2)
            .creditcardno("creditcardno2")
            .reference("reference2")
            .otherdetails("otherdetails2")
            .lmu("lmu2")
            .termid(2)
            .termname("termname2")
            .accountno("accountno2")
            .accountnumber("accountnumber2")
            .chequestatusid(2)
            .accountid(2)
            .accountcode("accountcode2")
            .bankdepositbankname("bankdepositbankname2")
            .bankdepositbankid(2)
            .bankdepositbankbranchname("bankdepositbankbranchname2")
            .bankdepositbankbranchid(2)
            .companybankid(2);
    }

    public static Receiptpaymentsdetails getReceiptpaymentsdetailsRandomSampleGenerator() {
        return new Receiptpaymentsdetails()
            .id(intCount.incrementAndGet())
            .lineid(intCount.incrementAndGet())
            .checkqueno(UUID.randomUUID().toString())
            .bankname(UUID.randomUUID().toString())
            .bankid(intCount.incrementAndGet())
            .bankbranchname(UUID.randomUUID().toString())
            .bankbranchid(intCount.incrementAndGet())
            .creditcardno(UUID.randomUUID().toString())
            .reference(UUID.randomUUID().toString())
            .otherdetails(UUID.randomUUID().toString())
            .lmu(UUID.randomUUID().toString())
            .termid(intCount.incrementAndGet())
            .termname(UUID.randomUUID().toString())
            .accountno(UUID.randomUUID().toString())
            .accountnumber(UUID.randomUUID().toString())
            .chequestatusid(intCount.incrementAndGet())
            .accountid(intCount.incrementAndGet())
            .accountcode(UUID.randomUUID().toString())
            .bankdepositbankname(UUID.randomUUID().toString())
            .bankdepositbankid(intCount.incrementAndGet())
            .bankdepositbankbranchname(UUID.randomUUID().toString())
            .bankdepositbankbranchid(intCount.incrementAndGet())
            .companybankid(intCount.incrementAndGet());
    }
}
