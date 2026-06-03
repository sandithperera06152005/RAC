package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesInvoiceServiceChargeLineDummy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesInvoiceServiceChargeLineDummyRepository
    extends JpaRepository<SalesInvoiceServiceChargeLineDummy, Long>, JpaSpecificationExecutor<SalesInvoiceServiceChargeLineDummy> {}
