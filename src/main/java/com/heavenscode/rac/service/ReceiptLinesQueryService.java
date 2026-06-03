package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.ReceiptLines;
import com.heavenscode.rac.repository.ReceiptLinesRepository;
import com.heavenscode.rac.service.criteria.ReceiptLinesCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ReceiptLines} entities in the database.
 * The main input is a {@link ReceiptLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ReceiptLines} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReceiptLinesQueryService extends QueryService<ReceiptLines> {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiptLinesQueryService.class);

    private final ReceiptLinesRepository receiptLinesRepository;

    public ReceiptLinesQueryService(ReceiptLinesRepository receiptLinesRepository) {
        this.receiptLinesRepository = receiptLinesRepository;
    }

    /**
     * Return a {@link Page} of {@link ReceiptLines} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReceiptLines> findByCriteria(ReceiptLinesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReceiptLines> specification = createSpecification(criteria);
        return receiptLinesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReceiptLinesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ReceiptLines> specification = createSpecification(criteria);
        return receiptLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link ReceiptLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ReceiptLines> createSpecification(ReceiptLinesCriteria criteria) {
        Specification<ReceiptLines> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ReceiptLines_.id));
            }
            if (criteria.getLineid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineid(), ReceiptLines_.lineid));
            }
            if (criteria.getInvoicecode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoicecode(), ReceiptLines_.invoicecode));
            }
            if (criteria.getInvoicetype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoicetype(), ReceiptLines_.invoicetype));
            }
            if (criteria.getOriginalamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOriginalamount(), ReceiptLines_.originalamount));
            }
            if (criteria.getAmountowing() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountowing(), ReceiptLines_.amountowing));
            }
            if (criteria.getDiscountavailable() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDiscountavailable(), ReceiptLines_.discountavailable)
                );
            }
            if (criteria.getDiscounttaken() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscounttaken(), ReceiptLines_.discounttaken));
            }
            if (criteria.getAmountreceived() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountreceived(), ReceiptLines_.amountreceived));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), ReceiptLines_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), ReceiptLines_.lmd));
            }
            if (criteria.getAccountid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountid(), ReceiptLines_.accountid));
            }
        }
        return specification;
    }
}
