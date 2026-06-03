package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.ReceiptLinesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReceiptLinesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptLines.class);
        ReceiptLines receiptLines1 = getReceiptLinesSample1();
        ReceiptLines receiptLines2 = new ReceiptLines();
        assertThat(receiptLines1).isNotEqualTo(receiptLines2);

        receiptLines2.setId(receiptLines1.getId());
        assertThat(receiptLines1).isEqualTo(receiptLines2);

        receiptLines2 = getReceiptLinesSample2();
        assertThat(receiptLines1).isNotEqualTo(receiptLines2);
    }
}
