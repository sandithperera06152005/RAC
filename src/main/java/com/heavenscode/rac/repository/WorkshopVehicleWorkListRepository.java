package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.WorkshopVehicleWorkList;
import com.heavenscode.rac.domain.WorkshopVehicleWorkListId;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WorkshopVehicleWorkList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkshopVehicleWorkListRepository extends JpaRepository<WorkshopVehicleWorkList, WorkshopVehicleWorkListId> {
    List<WorkshopVehicleWorkList> findByVehicleworkidIn(Collection<Integer> vehicleworkids);
}
