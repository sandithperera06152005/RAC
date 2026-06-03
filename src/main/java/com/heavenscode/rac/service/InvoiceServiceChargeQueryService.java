package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.InvoiceServiceCharge;
import com.heavenscode.rac.repository.InvoiceServiceChargeRepository;
import com.heavenscode.rac.service.criteria.InvoiceServiceChargeCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link InvoiceServiceCharge} entities in the database.
 * The main input is a {@link InvoiceServiceChargeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link InvoiceServiceCharge} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceServiceChargeQueryService extends QueryService<InvoiceServiceCharge> {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceChargeQueryService.class);

    private final InvoiceServiceChargeRepository invoiceServiceChargeRepository;

    public InvoiceServiceChargeQueryService(InvoiceServiceChargeRepository invoiceServiceChargeRepository) {
        this.invoiceServiceChargeRepository = invoiceServiceChargeRepository;
    }

    /**
     * Return a {@link Page} of {@link InvoiceServiceCharge} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceServiceCharge> findByCriteria(InvoiceServiceChargeCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvoiceServiceCharge> specification = createSpecification(criteria);
        return invoiceServiceChargeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceServiceChargeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<InvoiceServiceCharge> specification = createSpecification(criteria);
        return invoiceServiceChargeRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceServiceChargeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InvoiceServiceCharge> createSpecification(InvoiceServiceChargeCriteria criteria) {
        Specification<InvoiceServiceCharge> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InvoiceServiceCharge_.id));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceId(), InvoiceServiceCharge_.invoiceId));
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineId(), InvoiceServiceCharge_.lineId));
            }
            if (criteria.getOptionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOptionId(), InvoiceServiceCharge_.optionId));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceName(), InvoiceServiceCharge_.serviceName));
            }
            if (criteria.getServiceDiscription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getServiceDiscription(), InvoiceServiceCharge_.serviceDiscription)
                );
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), InvoiceServiceCharge_.value));
            }
            if (criteria.getAddedById() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddedById(), InvoiceServiceCharge_.addedById));
            }
            if (criteria.getIsCustomerSrvice() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getIsCustomerSrvice(), InvoiceServiceCharge_.isCustomerSrvice)
                );
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), InvoiceServiceCharge_.discount));
            }
            if (criteria.getServicePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getServicePrice(), InvoiceServiceCharge_.servicePrice));
            }
        }
        return specification;
    }
}
