package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Autojobsinvoicelinebatches;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autojobsinvoicelinebatches entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutojobsinvoicelinebatchesRepository
    extends JpaRepository<Autojobsinvoicelinebatches, Integer>, JpaSpecificationExecutor<Autojobsinvoicelinebatches> {}
