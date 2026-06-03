package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesInvoiceServiceChargeLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesInvoiceServiceChargeLine.class);
        SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine1 = getSalesInvoiceServiceChargeLineSample1();
        SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine2 = new SalesInvoiceServiceChargeLine();
        assertThat(salesInvoiceServiceChargeLine1).isNotEqualTo(salesInvoiceServiceChargeLine2);

        salesInvoiceServiceChargeLine2.setId(salesInvoiceServiceChargeLine1.getId());
        assertThat(salesInvoiceServiceChargeLine1).isEqualTo(salesInvoiceServiceChargeLine2);

        salesInvoiceServiceChargeLine2 = getSalesInvoiceServiceChargeLineSample2();
        assertThat(salesInvoiceServiceChargeLine1).isNotEqualTo(salesInvoiceServiceChargeLine2);
    }
}
