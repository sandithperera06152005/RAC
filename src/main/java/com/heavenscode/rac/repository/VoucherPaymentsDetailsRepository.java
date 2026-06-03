package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.VoucherPaymentsDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VoucherPaymentsDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherPaymentsDetailsRepository
    extends JpaRepository<VoucherPaymentsDetails, Long>, JpaSpecificationExecutor<VoucherPaymentsDetails> {}
