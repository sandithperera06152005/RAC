package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.VoucherLines;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VoucherLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherLinesRepository extends JpaRepository<VoucherLines, Long>, JpaSpecificationExecutor<VoucherLines> {}
