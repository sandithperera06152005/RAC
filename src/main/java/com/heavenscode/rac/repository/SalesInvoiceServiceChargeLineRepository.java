package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesInvoiceServiceChargeLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesInvoiceServiceChargeLineRepository
    extends JpaRepository<SalesInvoiceServiceChargeLine, Long>, JpaSpecificationExecutor<SalesInvoiceServiceChargeLine> {}
