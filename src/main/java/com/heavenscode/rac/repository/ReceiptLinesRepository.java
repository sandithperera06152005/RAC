package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.ReceiptLines;
import com.heavenscode.rac.domain.ReceiptLinesId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReceiptLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiptLinesRepository extends JpaRepository<ReceiptLines, ReceiptLinesId>, JpaSpecificationExecutor<ReceiptLines> {}
