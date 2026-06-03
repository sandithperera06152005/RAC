package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Bankbranch;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bankbranch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankbranchRepository extends JpaRepository<Bankbranch, Long> {
    List<Bankbranch> findByBankcode(String bankcode);
}
