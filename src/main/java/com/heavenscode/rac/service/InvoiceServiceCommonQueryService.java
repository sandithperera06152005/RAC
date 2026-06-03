package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.InvoiceServiceCommon;
import com.heavenscode.rac.repository.InvoiceServiceCommonRepository;
import com.heavenscode.rac.service.criteria.InvoiceServiceCommonCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link InvoiceServiceCommon} entities in the database.
 * The main input is a {@link InvoiceServiceCommonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link InvoiceServiceCommon} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceServiceCommonQueryService extends QueryService<InvoiceServiceCommon> {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceCommonQueryService.class);

    private final InvoiceServiceCommonRepository invoiceServiceCommonRepository;

    public InvoiceServiceCommonQueryService(InvoiceServiceCommonRepository invoiceServiceCommonRepository) {
        this.invoiceServiceCommonRepository = invoiceServiceCommonRepository;
    }

    /**
     * Return a {@link Page} of {@link InvoiceServiceCommon} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceServiceCommon> findByCriteria(InvoiceServiceCommonCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvoiceServiceCommon> specification = createSpecification(criteria);
        return invoiceServiceCommonRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceServiceCommonCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<InvoiceServiceCommon> specification = createSpecification(criteria);
        return invoiceServiceCommonRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceServiceCommonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InvoiceServiceCommon> createSpecification(InvoiceServiceCommonCriteria criteria) {
        Specification<InvoiceServiceCommon> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InvoiceServiceCommon_.id));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceId(), InvoiceServiceCommon_.invoiceId));
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineId(), InvoiceServiceCommon_.lineId));
            }
            if (criteria.getOptionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOptionId(), InvoiceServiceCommon_.optionId));
            }
            if (criteria.getMainId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMainId(), InvoiceServiceCommon_.mainId));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCode(), InvoiceServiceCommon_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), InvoiceServiceCommon_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), InvoiceServiceCommon_.description));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), InvoiceServiceCommon_.value));
            }
            if (criteria.getAddedById() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddedById(), InvoiceServiceCommon_.addedById));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), InvoiceServiceCommon_.discount));
            }
            if (criteria.getServicePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getServicePrice(), InvoiceServiceCommon_.servicePrice));
            }
        }
        return specification;
    }
}
