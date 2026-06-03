package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.AutojobsalesinvoiceservicechargelineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutojobsalesinvoiceservicechargelineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autojobsalesinvoiceservicechargeline.class);
        Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline1 = getAutojobsalesinvoiceservicechargelineSample1();
        Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline2 = new Autojobsalesinvoiceservicechargeline();
        assertThat(autojobsalesinvoiceservicechargeline1).isNotEqualTo(autojobsalesinvoiceservicechargeline2);

        autojobsalesinvoiceservicechargeline2.setId(autojobsalesinvoiceservicechargeline1.getId());
        assertThat(autojobsalesinvoiceservicechargeline1).isEqualTo(autojobsalesinvoiceservicechargeline2);

        autojobsalesinvoiceservicechargeline2 = getAutojobsalesinvoiceservicechargelineSample2();
        assertThat(autojobsalesinvoiceservicechargeline1).isNotEqualTo(autojobsalesinvoiceservicechargeline2);
    }
}
