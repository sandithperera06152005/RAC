package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Autojobsinvoicelines;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autojobsinvoicelines entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutojobsinvoicelinesRepository
    extends JpaRepository<Autojobsinvoicelines, Long>, JpaSpecificationExecutor<Autojobsinvoicelines> {}
