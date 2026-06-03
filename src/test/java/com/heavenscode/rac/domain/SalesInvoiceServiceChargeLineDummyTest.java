package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesInvoiceServiceChargeLineDummyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesInvoiceServiceChargeLineDummy.class);
        SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy1 = getSalesInvoiceServiceChargeLineDummySample1();
        SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy2 = new SalesInvoiceServiceChargeLineDummy();
        assertThat(salesInvoiceServiceChargeLineDummy1).isNotEqualTo(salesInvoiceServiceChargeLineDummy2);

        salesInvoiceServiceChargeLineDummy2.setId(salesInvoiceServiceChargeLineDummy1.getId());
        assertThat(salesInvoiceServiceChargeLineDummy1).isEqualTo(salesInvoiceServiceChargeLineDummy2);

        salesInvoiceServiceChargeLineDummy2 = getSalesInvoiceServiceChargeLineDummySample2();
        assertThat(salesInvoiceServiceChargeLineDummy1).isNotEqualTo(salesInvoiceServiceChargeLineDummy2);
    }
}
