package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.VoucherPaymentsDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherPaymentsDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherPaymentsDetails.class);
        VoucherPaymentsDetails voucherPaymentsDetails1 = getVoucherPaymentsDetailsSample1();
        VoucherPaymentsDetails voucherPaymentsDetails2 = new VoucherPaymentsDetails();
        assertThat(voucherPaymentsDetails1).isNotEqualTo(voucherPaymentsDetails2);

        voucherPaymentsDetails2.setId(voucherPaymentsDetails1.getId());
        assertThat(voucherPaymentsDetails1).isEqualTo(voucherPaymentsDetails2);

        voucherPaymentsDetails2 = getVoucherPaymentsDetailsSample2();
        assertThat(voucherPaymentsDetails1).isNotEqualTo(voucherPaymentsDetails2);
    }
}
