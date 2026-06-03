package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.VoucherLinesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherLinesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherLines.class);
        VoucherLines voucherLines1 = getVoucherLinesSample1();
        VoucherLines voucherLines2 = new VoucherLines();
        assertThat(voucherLines1).isNotEqualTo(voucherLines2);

        voucherLines2.setId(voucherLines1.getId());
        assertThat(voucherLines1).isEqualTo(voucherLines2);

        voucherLines2 = getVoucherLinesSample2();
        assertThat(voucherLines1).isNotEqualTo(voucherLines2);
    }
}
