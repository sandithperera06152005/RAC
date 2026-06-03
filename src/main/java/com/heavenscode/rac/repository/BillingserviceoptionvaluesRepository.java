package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Billingserviceoptionvalues;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Billingserviceoptionvalues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingserviceoptionvaluesRepository
    extends JpaRepository<Billingserviceoptionvalues, Long>, JpaSpecificationExecutor<Billingserviceoptionvalues> {
    List<Billingserviceoptionvalues> findByVehicletypeid(Integer vehicletypeid);
}
