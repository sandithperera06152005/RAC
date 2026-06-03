package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge;
import com.heavenscode.rac.repository.AutojobsaleinvoicecommonservicechargeRepository;
import com.heavenscode.rac.service.criteria.AutojobsaleinvoicecommonservicechargeCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Autojobsaleinvoicecommonservicecharge} entities in the database.
 * The main input is a {@link AutojobsaleinvoicecommonservicechargeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Autojobsaleinvoicecommonservicecharge} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutojobsaleinvoicecommonservicechargeQueryService extends QueryService<Autojobsaleinvoicecommonservicecharge> {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsaleinvoicecommonservicechargeQueryService.class);

    private final AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository;

    public AutojobsaleinvoicecommonservicechargeQueryService(
        AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository
    ) {
        this.autojobsaleinvoicecommonservicechargeRepository = autojobsaleinvoicecommonservicechargeRepository;
    }

    /**
     * Return a {@link Page} of {@link Autojobsaleinvoicecommonservicecharge} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Autojobsaleinvoicecommonservicecharge> findByCriteria(
        AutojobsaleinvoicecommonservicechargeCriteria criteria,
        Pageable page
    ) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Autojobsaleinvoicecommonservicecharge> specification = createSpecification(criteria);
        return autojobsaleinvoicecommonservicechargeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutojobsaleinvoicecommonservicechargeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Autojobsaleinvoicecommonservicecharge> specification = createSpecification(criteria);
        return autojobsaleinvoicecommonservicechargeRepository.count(specification);
    }

    /**
     * Function to convert {@link AutojobsaleinvoicecommonservicechargeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Autojobsaleinvoicecommonservicecharge> createSpecification(
        AutojobsaleinvoicecommonservicechargeCriteria criteria
    ) {
        Specification<Autojobsaleinvoicecommonservicecharge> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Autojobsaleinvoicecommonservicecharge_.id));
            }
            if (criteria.getInvoiceid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getInvoiceid(), Autojobsaleinvoicecommonservicecharge_.invoiceid)
                );
            }
            if (criteria.getLineid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLineid(), Autojobsaleinvoicecommonservicecharge_.lineid)
                );
            }
            if (criteria.getOptionid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getOptionid(), Autojobsaleinvoicecommonservicecharge_.optionid)
                );
            }
            if (criteria.getMainid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMainid(), Autojobsaleinvoicecommonservicecharge_.mainid)
                );
            }
            if (criteria.getCode() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCode(), Autojobsaleinvoicecommonservicecharge_.code)
                );
            }
            if (criteria.getName() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getName(), Autojobsaleinvoicecommonservicecharge_.name)
                );
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getDescription(), Autojobsaleinvoicecommonservicecharge_.description)
                );
            }
            if (criteria.getValue() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValue(), Autojobsaleinvoicecommonservicecharge_.value)
                );
            }
            if (criteria.getAddedbyid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAddedbyid(), Autojobsaleinvoicecommonservicecharge_.addedbyid)
                );
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDiscount(), Autojobsaleinvoicecommonservicecharge_.discount)
                );
            }
            if (criteria.getServiceprice() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getServiceprice(), Autojobsaleinvoicecommonservicecharge_.serviceprice)
                );
            }
        }
        return specification;
    }
}
