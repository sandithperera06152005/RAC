package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SaleInvoiceCommonServiceChargeDummy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleInvoiceCommonServiceChargeDummyRepository
    extends JpaRepository<SaleInvoiceCommonServiceChargeDummy, Long>, JpaSpecificationExecutor<SaleInvoiceCommonServiceChargeDummy> {}
