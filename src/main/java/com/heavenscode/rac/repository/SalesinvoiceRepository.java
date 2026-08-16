package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Salesinvoice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Salesinvoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalesinvoiceRepository extends JpaRepository<Salesinvoice, Long>, JpaSpecificationExecutor<Salesinvoice> {
    @Query(
        value = """
            select count(1)
            from [dbo].[SalesInvoice]
            where [AutoCareJobId] = :autocarejobid
              and ltrim(rtrim([VehicleNo])) = ltrim(rtrim(:vehicleno))
              and abs([NetTotal] - :nettotal) < 0.01
              and [CreatedDate] >= cast(cast(getdate() as date) as datetime)
              and [CreatedDate] < dateadd(day, 1, cast(cast(getdate() as date) as datetime))
              and [IsActive] = 1
        """,
        nativeQuery = true
    )
    long countActiveInvoiceForSameJobVehicleTotalToday(
        @Param("autocarejobid") Integer autocarejobid,
        @Param("vehicleno") String vehicleno,
        @Param("nettotal") Float nettotal
    );
}
