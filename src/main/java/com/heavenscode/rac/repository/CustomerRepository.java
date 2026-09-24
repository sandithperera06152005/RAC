package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Customer;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    interface CustomerTypeNameOnly {
        Long getId();
        String getCustomerTypeName();
    }

    @Query(
        value = """
        select
            cast([ID] as bigint) as [id],
            [CustomerTypeName] as [customerTypeName]
        from [RACTestDB].[dbo].[CustomerTypes]
        where [ID] in (:ids)
        """,
        nativeQuery = true
    )
    List<CustomerTypeNameOnly> findCustomerTypeNamesByIdIn(@Param("ids") Collection<Integer> ids);
}
