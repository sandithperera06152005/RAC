package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.BinCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BinCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BinCardRepository extends JpaRepository<BinCard, Long> {}
