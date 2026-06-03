package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.SalesInvoiceLines;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SalesInvoiceLines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesInvoiceLinesRepository extends JpaRepository<SalesInvoiceLines, Long>, JpaSpecificationExecutor<SalesInvoiceLines> {}
