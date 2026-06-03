package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.VoucherPaymentsDetails;
import com.heavenscode.rac.repository.VoucherPaymentsDetailsRepository;
import com.heavenscode.rac.service.criteria.VoucherPaymentsDetailsCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link VoucherPaymentsDetails} entities in the database.
 * The main input is a {@link VoucherPaymentsDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link VoucherPaymentsDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VoucherPaymentsDetailsQueryService extends QueryService<VoucherPaymentsDetails> {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherPaymentsDetailsQueryService.class);

    private final VoucherPaymentsDetailsRepository voucherPaymentsDetailsRepository;

    public VoucherPaymentsDetailsQueryService(VoucherPaymentsDetailsRepository voucherPaymentsDetailsRepository) {
        this.voucherPaymentsDetailsRepository = voucherPaymentsDetailsRepository;
    }

    /**
     * Return a {@link Page} of {@link VoucherPaymentsDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VoucherPaymentsDetails> findByCriteria(VoucherPaymentsDetailsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VoucherPaymentsDetails> specification = createSpecification(criteria);
        return voucherPaymentsDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VoucherPaymentsDetailsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<VoucherPaymentsDetails> specification = createSpecification(criteria);
        return voucherPaymentsDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link VoucherPaymentsDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VoucherPaymentsDetails> createSpecification(VoucherPaymentsDetailsCriteria criteria) {
        Specification<VoucherPaymentsDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VoucherPaymentsDetails_.id));
            }
            if (criteria.getLineID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineID(), VoucherPaymentsDetails_.lineID));
            }
            if (criteria.getPaymentAmount() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getPaymentAmount(), VoucherPaymentsDetails_.paymentAmount)
                );
            }
            if (criteria.getTotalVoucherAmount() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getTotalVoucherAmount(), VoucherPaymentsDetails_.totalVoucherAmount)
                );
            }
            if (criteria.getCheckqueAmount() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCheckqueAmount(), VoucherPaymentsDetails_.checkqueAmount)
                );
            }
            if (criteria.getCheckqueNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckqueNo(), VoucherPaymentsDetails_.checkqueNo));
            }
            if (criteria.getCheckqueDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCheckqueDate(), VoucherPaymentsDetails_.checkqueDate)
                );
            }
            if (criteria.getCheckqueExpireDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCheckqueExpireDate(), VoucherPaymentsDetails_.checkqueExpireDate)
                );
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), VoucherPaymentsDetails_.bankName));
            }
            if (criteria.getBankID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBankID(), VoucherPaymentsDetails_.bankID));
            }
            if (criteria.getCreditCardNo() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCreditCardNo(), VoucherPaymentsDetails_.creditCardNo)
                );
            }
            if (criteria.getCreditCardAmount() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCreditCardAmount(), VoucherPaymentsDetails_.creditCardAmount)
                );
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), VoucherPaymentsDetails_.reference));
            }
            if (criteria.getOtherDetails() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getOtherDetails(), VoucherPaymentsDetails_.otherDetails)
                );
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLmu(), VoucherPaymentsDetails_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), VoucherPaymentsDetails_.lmd));
            }
            if (criteria.getTermID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTermID(), VoucherPaymentsDetails_.termID));
            }
            if (criteria.getTermName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermName(), VoucherPaymentsDetails_.termName));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountNo(), VoucherPaymentsDetails_.accountNo));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAccountNumber(), VoucherPaymentsDetails_.accountNumber)
                );
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountId(), VoucherPaymentsDetails_.accountId));
            }
            if (criteria.getAccountCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountCode(), VoucherPaymentsDetails_.accountCode));
            }
            if (criteria.getChequeStatusId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getChequeStatusId(), VoucherPaymentsDetails_.chequeStatusId)
                );
            }
            if (criteria.getIsDeposit() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeposit(), VoucherPaymentsDetails_.isDeposit));
            }
            if (criteria.getDepositedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDepositedDate(), VoucherPaymentsDetails_.depositedDate)
                );
            }
            if (criteria.getCompanyBankId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCompanyBankId(), VoucherPaymentsDetails_.companyBankId)
                );
            }
            if (criteria.getIsBankReconciliation() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getIsBankReconciliation(), VoucherPaymentsDetails_.isBankReconciliation)
                );
            }
        }
        return specification;
    }
}
