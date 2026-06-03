package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.VoucherLines;
import com.heavenscode.rac.repository.VoucherLinesRepository;
import com.heavenscode.rac.service.criteria.VoucherLinesCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link VoucherLines} entities in the database.
 * The main input is a {@link VoucherLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link VoucherLines} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VoucherLinesQueryService extends QueryService<VoucherLines> {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherLinesQueryService.class);

    private final VoucherLinesRepository voucherLinesRepository;

    public VoucherLinesQueryService(VoucherLinesRepository voucherLinesRepository) {
        this.voucherLinesRepository = voucherLinesRepository;
    }

    /**
     * Return a {@link Page} of {@link VoucherLines} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VoucherLines> findByCriteria(VoucherLinesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VoucherLines> specification = createSpecification(criteria);
        return voucherLinesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VoucherLinesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<VoucherLines> specification = createSpecification(criteria);
        return voucherLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link VoucherLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VoucherLines> createSpecification(VoucherLinesCriteria criteria) {
        Specification<VoucherLines> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VoucherLines_.id));
            }
            if (criteria.getLineID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineID(), VoucherLines_.lineID));
            }
            if (criteria.getGrnCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrnCode(), VoucherLines_.grnCode));
            }
            if (criteria.getGrnType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrnType(), VoucherLines_.grnType));
            }
            if (criteria.getOriginalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOriginalAmount(), VoucherLines_.originalAmount));
            }
            if (criteria.getAmountOwing() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountOwing(), VoucherLines_.amountOwing));
            }
            if (criteria.getDiscountAvailable() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDiscountAvailable(), VoucherLines_.discountAvailable)
                );
            }
            if (criteria.getDiscountTaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountTaken(), VoucherLines_.discountTaken));
            }
            if (criteria.getAmountReceived() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountReceived(), VoucherLines_.amountReceived));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), VoucherLines_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), VoucherLines_.lmd));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountId(), VoucherLines_.accountId));
            }
        }
        return specification;
    }
}
