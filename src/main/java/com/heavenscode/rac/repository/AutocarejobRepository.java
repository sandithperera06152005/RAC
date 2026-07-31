package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Autocarejob;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autocarejob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutocarejobRepository extends JpaRepository<Autocarejob, Long> {
    Optional<Autocarejob> findTopByJobdateBetweenOrderByJobnumberDesc(Instant start, Instant end);

    List<Autocarejob> findByVehiclenumberIgnoreCaseOrderByJobdateDesc(String vehiclenumber);
}
