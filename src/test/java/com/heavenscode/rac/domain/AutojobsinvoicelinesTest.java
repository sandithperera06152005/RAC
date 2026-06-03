package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.AutojobsinvoicelinesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutojobsinvoicelinesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autojobsinvoicelines.class);
        Autojobsinvoicelines autojobsinvoicelines1 = getAutojobsinvoicelinesSample1();
        Autojobsinvoicelines autojobsinvoicelines2 = new Autojobsinvoicelines();
        assertThat(autojobsinvoicelines1).isNotEqualTo(autojobsinvoicelines2);

        autojobsinvoicelines2.setId(autojobsinvoicelines1.getId());
        assertThat(autojobsinvoicelines1).isEqualTo(autojobsinvoicelines2);

        autojobsinvoicelines2 = getAutojobsinvoicelinesSample2();
        assertThat(autojobsinvoicelines1).isNotEqualTo(autojobsinvoicelines2);
    }
}
