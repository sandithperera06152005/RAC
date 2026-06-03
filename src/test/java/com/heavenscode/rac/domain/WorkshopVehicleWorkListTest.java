package com.heavenscode.rac.domain;

import static com.heavenscode.rac.domain.WorkshopVehicleWorkListTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.heavenscode.rac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkshopVehicleWorkListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkshopVehicleWorkList.class);
        WorkshopVehicleWorkList workshopVehicleWorkList1 = getWorkshopVehicleWorkListSample1();
        WorkshopVehicleWorkList workshopVehicleWorkList2 = new WorkshopVehicleWorkList();
        assertThat(workshopVehicleWorkList1).isNotEqualTo(workshopVehicleWorkList2);

        workshopVehicleWorkList2.setId(workshopVehicleWorkList1.getId());
        assertThat(workshopVehicleWorkList1).isEqualTo(workshopVehicleWorkList2);

        workshopVehicleWorkList2 = getWorkshopVehicleWorkListSample2();
        assertThat(workshopVehicleWorkList1).isNotEqualTo(workshopVehicleWorkList2);
    }
}
