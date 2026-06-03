package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.InvoiceServiceChargeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceServiceChargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceServiceCharge.class);
        InvoiceServiceCharge invoiceServiceCharge1 = getInvoiceServiceChargeSample1();
        InvoiceServiceCharge invoiceServiceCharge2 = new InvoiceServiceCharge();
        assertThat(invoiceServiceCharge1).isNotEqualTo(invoiceServiceCharge2);

        invoiceServiceCharge2.setId(invoiceServiceCharge1.getId());
        assertThat(invoiceServiceCharge1).isEqualTo(invoiceServiceCharge2);

        invoiceServiceCharge2 = getInvoiceServiceChargeSample2();
        assertThat(invoiceServiceCharge1).isNotEqualTo(invoiceServiceCharge2);
    }
}
