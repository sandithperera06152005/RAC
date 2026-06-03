package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaleInvoiceCommonServiceChargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleInvoiceCommonServiceCharge.class);
        SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge1 = getSaleInvoiceCommonServiceChargeSample1();
        SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge2 = new SaleInvoiceCommonServiceCharge();
        assertThat(saleInvoiceCommonServiceCharge1).isNotEqualTo(saleInvoiceCommonServiceCharge2);

        saleInvoiceCommonServiceCharge2.setId(saleInvoiceCommonServiceCharge1.getId());
        assertThat(saleInvoiceCommonServiceCharge1).isEqualTo(saleInvoiceCommonServiceCharge2);

        saleInvoiceCommonServiceCharge2 = getSaleInvoiceCommonServiceChargeSample2();
        assertThat(saleInvoiceCommonServiceCharge1).isNotEqualTo(saleInvoiceCommonServiceCharge2);
    }
}
