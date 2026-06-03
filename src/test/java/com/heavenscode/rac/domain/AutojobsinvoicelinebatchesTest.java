package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.AutojobsinvoicelinebatchesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutojobsinvoicelinebatchesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autojobsinvoicelinebatches.class);
        Autojobsinvoicelinebatches autojobsinvoicelinebatches1 = getAutojobsinvoicelinebatchesSample1();
        Autojobsinvoicelinebatches autojobsinvoicelinebatches2 = new Autojobsinvoicelinebatches();
        assertThat(autojobsinvoicelinebatches1).isNotEqualTo(autojobsinvoicelinebatches2);

        autojobsinvoicelinebatches2.setId(autojobsinvoicelinebatches1.getId());
        assertThat(autojobsinvoicelinebatches1).isEqualTo(autojobsinvoicelinebatches2);

        autojobsinvoicelinebatches2 = getAutojobsinvoicelinebatchesSample2();
        assertThat(autojobsinvoicelinebatches1).isNotEqualTo(autojobsinvoicelinebatches2);
    }
}
