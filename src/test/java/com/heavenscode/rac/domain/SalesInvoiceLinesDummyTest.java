package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SalesInvoiceLinesDummyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesInvoiceLinesDummyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesInvoiceLinesDummy.class);
        SalesInvoiceLinesDummy salesInvoiceLinesDummy1 = getSalesInvoiceLinesDummySample1();
        SalesInvoiceLinesDummy salesInvoiceLinesDummy2 = new SalesInvoiceLinesDummy();
        assertThat(salesInvoiceLinesDummy1).isNotEqualTo(salesInvoiceLinesDummy2);

        salesInvoiceLinesDummy2.setId(salesInvoiceLinesDummy1.getId());
        assertThat(salesInvoiceLinesDummy1).isEqualTo(salesInvoiceLinesDummy2);

        salesInvoiceLinesDummy2 = getSalesInvoiceLinesDummySample2();
        assertThat(salesInvoiceLinesDummy1).isNotEqualTo(salesInvoiceLinesDummy2);
    }
}
