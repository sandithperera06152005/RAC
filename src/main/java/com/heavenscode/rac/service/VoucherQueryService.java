package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Voucher;
import com.heavenscode.rac.repository.VoucherRepository;
import com.heavenscode.rac.service.criteria.VoucherCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Voucher} entities in the database.
 * The main input is a {@link VoucherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Voucher} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VoucherQueryService extends QueryService<Voucher> {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherQueryService.class);

    private final VoucherRepository voucherRepository;

    public VoucherQueryService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /**
     * Return a {@link Page} of {@link Voucher} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Voucher> findByCriteria(VoucherCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Voucher> specification = createSpecification(criteria);
        return voucherRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VoucherCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Voucher> specification = createSpecification(criteria);
        return voucherRepository.count(specification);
    }

    /**
     * Function to convert {@link VoucherCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Voucher> createSpecification(VoucherCriteria criteria) {
        Specification<Voucher> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Voucher_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Voucher_.code));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), Voucher_.voucherDate));
            }
            if (criteria.getSupplierName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierName(), Voucher_.supplierName));
            }
            if (criteria.getSupplierAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierAddress(), Voucher_.supplierAddress));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), Voucher_.totalAmount));
            }
            if (criteria.getTotalAmountInWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalAmountInWord(), Voucher_.totalAmountInWord));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Voucher_.comments));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Voucher_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Voucher_.lmd));
            }
            if (criteria.getTermId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTermId(), Voucher_.termId));
            }
            if (criteria.getTerm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTerm(), Voucher_.term));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Voucher_.date));
            }
            if (criteria.getAmountPaid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountPaid(), Voucher_.amountPaid));
            }
            if (criteria.getSupplierID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSupplierID(), Voucher_.supplierID));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Voucher_.isActive));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), Voucher_.createdBy));
            }
        }
        return specification;
    }
}
