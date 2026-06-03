package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.ReceiptpaymentsdetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReceiptpaymentsdetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receiptpaymentsdetails.class);
        Receiptpaymentsdetails receiptpaymentsdetails1 = getReceiptpaymentsdetailsSample1();
        Receiptpaymentsdetails receiptpaymentsdetails2 = new Receiptpaymentsdetails();
        assertThat(receiptpaymentsdetails1).isNotEqualTo(receiptpaymentsdetails2);

        receiptpaymentsdetails2.setId(receiptpaymentsdetails1.getId());
        assertThat(receiptpaymentsdetails1).isEqualTo(receiptpaymentsdetails2);

        receiptpaymentsdetails2 = getReceiptpaymentsdetailsSample2();
        assertThat(receiptpaymentsdetails1).isNotEqualTo(receiptpaymentsdetails2);
    }
}
