package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.InvoiceServiceCommon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InvoiceServiceCommon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceServiceCommonRepository
    extends JpaRepository<InvoiceServiceCommon, Long>, JpaSpecificationExecutor<InvoiceServiceCommon> {}
