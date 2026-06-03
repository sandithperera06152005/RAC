package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autojobsaleinvoicecommonservicecharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutojobsaleinvoicecommonservicechargeRepository
    extends JpaRepository<Autojobsaleinvoicecommonservicecharge, Long>, JpaSpecificationExecutor<Autojobsaleinvoicecommonservicecharge> {}
