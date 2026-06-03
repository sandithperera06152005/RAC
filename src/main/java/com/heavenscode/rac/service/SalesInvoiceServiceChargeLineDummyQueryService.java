package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummy;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineDummyRepository;
import com.heavenscode.rac.service.criteria.SalesInvoiceServiceChargeLineDummyCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalesInvoiceServiceChargeLineDummy} entities in the database.
 * The main input is a {@link SalesInvoiceServiceChargeLineDummyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SalesInvoiceServiceChargeLineDummy} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesInvoiceServiceChargeLineDummyQueryService extends QueryService<SalesInvoiceServiceChargeLineDummy> {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceServiceChargeLineDummyQueryService.class);

    private final SalesInvoiceServiceChargeLineDummyRepository salesInvoiceServiceChargeLineDummyRepository;

    public SalesInvoiceServiceChargeLineDummyQueryService(
        SalesInvoiceServiceChargeLineDummyRepository salesInvoiceServiceChargeLineDummyRepository
    ) {
        this.salesInvoiceServiceChargeLineDummyRepository = salesInvoiceServiceChargeLineDummyRepository;
    }

    /**
     * Return a {@link Page} of {@link SalesInvoiceServiceChargeLineDummy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesInvoiceServiceChargeLineDummy> findByCriteria(SalesInvoiceServiceChargeLineDummyCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalesInvoiceServiceChargeLineDummy> specification = createSpecification(criteria);
        return salesInvoiceServiceChargeLineDummyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesInvoiceServiceChargeLineDummyCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SalesInvoiceServiceChargeLineDummy> specification = createSpecification(criteria);
        return salesInvoiceServiceChargeLineDummyRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesInvoiceServiceChargeLineDummyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalesInvoiceServiceChargeLineDummy> createSpecification(SalesInvoiceServiceChargeLineDummyCriteria criteria) {
        Specification<SalesInvoiceServiceChargeLineDummy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalesInvoiceServiceChargeLineDummy_.id));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getInvoiceId(), SalesInvoiceServiceChargeLineDummy_.invoiceId)
                );
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLineId(), SalesInvoiceServiceChargeLineDummy_.lineId)
                );
            }
            if (criteria.getOptionId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getOptionId(), SalesInvoiceServiceChargeLineDummy_.optionId)
                );
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getServiceName(), SalesInvoiceServiceChargeLineDummy_.serviceName)
                );
            }
            if (criteria.getServiceDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getServiceDescription(), SalesInvoiceServiceChargeLineDummy_.serviceDescription)
                );
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), SalesInvoiceServiceChargeLineDummy_.value));
            }
            if (criteria.getIsCustomerService() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getIsCustomerService(), SalesInvoiceServiceChargeLineDummy_.isCustomerService)
                );
            }
        }
        return specification;
    }
}
