package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.AutojobsaleinvoicecommonservicechargeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutojobsaleinvoicecommonservicechargeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autojobsaleinvoicecommonservicecharge.class);
        Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge1 = getAutojobsaleinvoicecommonservicechargeSample1();
        Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge2 = new Autojobsaleinvoicecommonservicecharge();
        assertThat(autojobsaleinvoicecommonservicecharge1).isNotEqualTo(autojobsaleinvoicecommonservicecharge2);

        autojobsaleinvoicecommonservicecharge2.setId(autojobsaleinvoicecommonservicecharge1.getId());
        assertThat(autojobsaleinvoicecommonservicecharge1).isEqualTo(autojobsaleinvoicecommonservicecharge2);

        autojobsaleinvoicecommonservicecharge2 = getAutojobsaleinvoicecommonservicechargeSample2();
        assertThat(autojobsaleinvoicecommonservicecharge1).isNotEqualTo(autojobsaleinvoicecommonservicecharge2);
    }
}
