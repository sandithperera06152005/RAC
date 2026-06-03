package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineRepository;
import com.heavenscode.rac.service.criteria.SalesInvoiceServiceChargeLineCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalesInvoiceServiceChargeLine} entities in the database.
 * The main input is a {@link SalesInvoiceServiceChargeLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SalesInvoiceServiceChargeLine} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesInvoiceServiceChargeLineQueryService extends QueryService<SalesInvoiceServiceChargeLine> {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceServiceChargeLineQueryService.class);

    private final SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository;

    public SalesInvoiceServiceChargeLineQueryService(SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository) {
        this.salesInvoiceServiceChargeLineRepository = salesInvoiceServiceChargeLineRepository;
    }

    /**
     * Return a {@link Page} of {@link SalesInvoiceServiceChargeLine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesInvoiceServiceChargeLine> findByCriteria(SalesInvoiceServiceChargeLineCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalesInvoiceServiceChargeLine> specification = createSpecification(criteria);
        return salesInvoiceServiceChargeLineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesInvoiceServiceChargeLineCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SalesInvoiceServiceChargeLine> specification = createSpecification(criteria);
        return salesInvoiceServiceChargeLineRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesInvoiceServiceChargeLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalesInvoiceServiceChargeLine> createSpecification(SalesInvoiceServiceChargeLineCriteria criteria) {
        Specification<SalesInvoiceServiceChargeLine> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalesInvoiceServiceChargeLine_.id));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getInvoiceId(), SalesInvoiceServiceChargeLine_.invoiceId)
                );
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineId(), SalesInvoiceServiceChargeLine_.lineId));
            }
            if (criteria.getOptionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOptionId(), SalesInvoiceServiceChargeLine_.optionId));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getServiceName(), SalesInvoiceServiceChargeLine_.serviceName)
                );
            }
            if (criteria.getServiceDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getServiceDescription(), SalesInvoiceServiceChargeLine_.serviceDescription)
                );
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), SalesInvoiceServiceChargeLine_.value));
            }
            if (criteria.getIsCustomerService() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getIsCustomerService(), SalesInvoiceServiceChargeLine_.isCustomerService)
                );
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), SalesInvoiceServiceChargeLine_.discount));
            }
            if (criteria.getServicePrice() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getServicePrice(), SalesInvoiceServiceChargeLine_.servicePrice)
                );
            }
        }
        return specification;
    }
}
