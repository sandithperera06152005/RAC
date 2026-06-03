package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Accounts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Accounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long>, JpaSpecificationExecutor<Accounts> {
    @Modifying
    @Query("update Accounts a set a.balance = :balance where a.id = :id")
    void updateBalance(
        @org.springframework.data.repository.query.Param("id") Long id,
        @org.springframework.data.repository.query.Param("balance") Float balance
    );
}
