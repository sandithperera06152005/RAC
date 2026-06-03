package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Billingserviceoption;
import com.heavenscode.rac.repository.BillingserviceoptionRepository;
import com.heavenscode.rac.service.criteria.BillingserviceoptionCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Billingserviceoption} entities in the database.
 * The main input is a {@link BillingserviceoptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Billingserviceoption} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BillingserviceoptionQueryService extends QueryService<Billingserviceoption> {

    private static final Logger LOG = LoggerFactory.getLogger(BillingserviceoptionQueryService.class);

    private final BillingserviceoptionRepository billingserviceoptionRepository;

    public BillingserviceoptionQueryService(BillingserviceoptionRepository billingserviceoptionRepository) {
        this.billingserviceoptionRepository = billingserviceoptionRepository;
    }

    /**
     * Return a {@link Page} of {@link Billingserviceoption} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Billingserviceoption> findByCriteria(BillingserviceoptionCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Billingserviceoption> specification = createSpecification(criteria);
        return billingserviceoptionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BillingserviceoptionCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Billingserviceoption> specification = createSpecification(criteria);
        return billingserviceoptionRepository.count(specification);
    }

    /**
     * Function to convert {@link BillingserviceoptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Billingserviceoption> createSpecification(BillingserviceoptionCriteria criteria) {
        Specification<Billingserviceoption> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Billingserviceoption_.id));
            }
            if (criteria.getServicename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServicename(), Billingserviceoption_.servicename));
            }
            if (criteria.getServicediscription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getServicediscription(), Billingserviceoption_.servicediscription)
                );
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Billingserviceoption_.isactive));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Billingserviceoption_.lmd));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Billingserviceoption_.lmu));
            }
            if (criteria.getOrderby() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderby(), Billingserviceoption_.orderby));
            }
            if (criteria.getBilltocustomer() != null) {
                specification = specification.and(buildSpecification(criteria.getBilltocustomer(), Billingserviceoption_.billtocustomer));
            }
        }
        return specification;
    }
}
