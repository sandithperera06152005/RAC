package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SalesInvoiceDummy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesInvoiceDummy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesInvoiceDummyRepository
    extends JpaRepository<SalesInvoiceDummy, Integer>, JpaSpecificationExecutor<SalesInvoiceDummy> {}
