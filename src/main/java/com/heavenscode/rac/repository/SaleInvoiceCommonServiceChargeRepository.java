package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SaleInvoiceCommonServiceCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleInvoiceCommonServiceChargeRepository
    extends JpaRepository<SaleInvoiceCommonServiceCharge, Long>, JpaSpecificationExecutor<SaleInvoiceCommonServiceCharge> {}
