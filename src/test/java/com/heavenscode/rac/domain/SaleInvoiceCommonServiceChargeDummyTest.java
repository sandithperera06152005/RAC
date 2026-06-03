package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaleInvoiceCommonServiceChargeDummyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleInvoiceCommonServiceChargeDummy.class);
        SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy1 = getSaleInvoiceCommonServiceChargeDummySample1();
        SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy2 = new SaleInvoiceCommonServiceChargeDummy();
        assertThat(saleInvoiceCommonServiceChargeDummy1).isNotEqualTo(saleInvoiceCommonServiceChargeDummy2);

        saleInvoiceCommonServiceChargeDummy2.setId(saleInvoiceCommonServiceChargeDummy1.getId());
        assertThat(saleInvoiceCommonServiceChargeDummy1).isEqualTo(saleInvoiceCommonServiceChargeDummy2);

        saleInvoiceCommonServiceChargeDummy2 = getSaleInvoiceCommonServiceChargeDummySample2();
        assertThat(saleInvoiceCommonServiceChargeDummy1).isNotEqualTo(saleInvoiceCommonServiceChargeDummy2);
    }
}
