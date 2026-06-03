package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.Receiptpaymentsdetails;
import com.heavenscode.rac.domain.ReceiptpaymentsdetailsId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Receiptpaymentsdetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiptpaymentsdetailsRepository extends JpaRepository<Receiptpaymentsdetails, ReceiptpaymentsdetailsId> {}
