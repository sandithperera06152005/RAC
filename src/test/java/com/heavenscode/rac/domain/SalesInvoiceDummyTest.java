package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SalesInvoiceDummyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalesInvoiceDummyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesInvoiceDummy.class);
        SalesInvoiceDummy salesInvoiceDummy1 = getSalesInvoiceDummySample1();
        SalesInvoiceDummy salesInvoiceDummy2 = new SalesInvoiceDummy();
        assertThat(salesInvoiceDummy1).isNotEqualTo(salesInvoiceDummy2);

        salesInvoiceDummy2.setId(salesInvoiceDummy1.getId());
        assertThat(salesInvoiceDummy1).isEqualTo(salesInvoiceDummy2);

        salesInvoiceDummy2 = getSalesInvoiceDummySample2();
        assertThat(salesInvoiceDummy1).isNotEqualTo(salesInvoiceDummy2);
    }
}
