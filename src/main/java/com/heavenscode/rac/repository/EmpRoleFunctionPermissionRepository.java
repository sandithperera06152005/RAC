package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.EmpRoleFunctionPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link EmpRoleFunctionPermission} entity.
 */
@Repository
public interface EmpRoleFunctionPermissionRepository extends JpaRepository<EmpRoleFunctionPermission, Integer> {
    boolean existsByRoleIdAndFunctionId(Integer roleId, Integer functionId);
}
