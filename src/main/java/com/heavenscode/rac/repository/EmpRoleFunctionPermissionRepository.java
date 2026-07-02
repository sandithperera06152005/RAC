package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.EmpRoleFunctionPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link EmpRoleFunctionPermission} entity.
 */
@Repository
public interface EmpRoleFunctionPermissionRepository extends JpaRepository<EmpRoleFunctionPermission, Integer> {
    @Query(
        value = """
        select count(1)
        from dbo.[Emp_RoleFunctionPermission]
        where [RoleId] = :roleId and [FunctionId] = :functionId
        """,
        nativeQuery = true
    )
    long countByRoleIdAndFunctionId(@Param("roleId") Integer roleId, @Param("functionId") Integer functionId);

    default boolean existsByRoleIdAndFunctionId(Integer roleId, Integer functionId) {
        return countByRoleIdAndFunctionId(roleId, functionId) > 0;
    }
}
