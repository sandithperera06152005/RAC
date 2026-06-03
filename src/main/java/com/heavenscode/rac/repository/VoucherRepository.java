package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Voucher;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Voucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long>, JpaSpecificationExecutor<Voucher> {}
