package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autojobsalesinvoiceservicechargeline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutojobsalesinvoiceservicechargelineRepository
    extends JpaRepository<Autojobsalesinvoiceservicechargeline, Long>, JpaSpecificationExecutor<Autojobsalesinvoiceservicechargeline> {}
