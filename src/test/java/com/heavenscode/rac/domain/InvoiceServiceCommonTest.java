package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.InvoiceServiceCommonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceServiceCommonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceServiceCommon.class);
        InvoiceServiceCommon invoiceServiceCommon1 = getInvoiceServiceCommonSample1();
        InvoiceServiceCommon invoiceServiceCommon2 = new InvoiceServiceCommon();
        assertThat(invoiceServiceCommon1).isNotEqualTo(invoiceServiceCommon2);

        invoiceServiceCommon2.setId(invoiceServiceCommon1.getId());
        assertThat(invoiceServiceCommon1).isEqualTo(invoiceServiceCommon2);

        invoiceServiceCommon2 = getInvoiceServiceCommonSample2();
        assertThat(invoiceServiceCommon1).isNotEqualTo(invoiceServiceCommon2);
    }
}
