package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SalesInvoiceLinesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesInvoiceLinesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesInvoiceLines.class);
        SalesInvoiceLines salesInvoiceLines1 = getSalesInvoiceLinesSample1();
        SalesInvoiceLines salesInvoiceLines2 = new SalesInvoiceLines();
        assertThat(salesInvoiceLines1).isNotEqualTo(salesInvoiceLines2);

        salesInvoiceLines2.setId(salesInvoiceLines1.getId());
        assertThat(salesInvoiceLines1).isEqualTo(salesInvoiceLines2);

        salesInvoiceLines2 = getSalesInvoiceLinesSample2();
        assertThat(salesInvoiceLines1).isNotEqualTo(salesInvoiceLines2);
    }
}
