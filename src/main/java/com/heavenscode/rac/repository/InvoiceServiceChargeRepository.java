package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.InvoiceServiceCharge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InvoiceServiceCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceServiceChargeRepository
    extends JpaRepository<InvoiceServiceCharge, Long>, JpaSpecificationExecutor<InvoiceServiceCharge> {}
