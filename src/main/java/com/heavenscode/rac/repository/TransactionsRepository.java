package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Transactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>, JpaSpecificationExecutor<Transactions> {}
