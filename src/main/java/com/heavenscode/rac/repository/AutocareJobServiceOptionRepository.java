package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.AutocareJobServiceOption;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AutocareJobServiceOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutocareJobServiceOptionRepository extends JpaRepository<AutocareJobServiceOption, Long> {
    List<AutocareJobServiceOption> findByJobid(Integer jobid);

    void deleteByJobid(Integer jobid);
}
