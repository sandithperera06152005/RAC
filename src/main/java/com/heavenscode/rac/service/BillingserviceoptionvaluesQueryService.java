package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Billingserviceoptionvalues;
import com.heavenscode.rac.repository.BillingserviceoptionvaluesRepository;
// import com.heavenscode.rac.domain.Billingserviceoptionvalues_; // Removed as it cannot be resolved
import com.heavenscode.rac.service.criteria.BillingserviceoptionvaluesCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Billingserviceoptionvalues} entities in the database.
 * The main input is a {@link BillingserviceoptionvaluesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Billingserviceoptionvalues} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BillingserviceoptionvaluesQueryService extends QueryService<Billingserviceoptionvalues> {

    private static final Logger LOG = LoggerFactory.getLogger(BillingserviceoptionvaluesQueryService.class);

    private final BillingserviceoptionvaluesRepository billingserviceoptionvaluesRepository;

    public BillingserviceoptionvaluesQueryService(BillingserviceoptionvaluesRepository billingserviceoptionvaluesRepository) {
        this.billingserviceoptionvaluesRepository = billingserviceoptionvaluesRepository;
    }

    /**
     * Return a {@link Page} of {@link Billingserviceoptionvalues} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Billingserviceoptionvalues> findByCriteria(BillingserviceoptionvaluesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Billingserviceoptionvalues> specification = createSpecification(criteria);
        return billingserviceoptionvaluesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BillingserviceoptionvaluesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Billingserviceoptionvalues> specification = createSpecification(criteria);
        return billingserviceoptionvaluesRepository.count(specification);
    }

    /**
     * Function to convert {@link BillingserviceoptionvaluesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Billingserviceoptionvalues> createSpecification(BillingserviceoptionvaluesCriteria criteria) {
        Specification<Billingserviceoptionvalues> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Billingserviceoptionvalues_.id));
            }
            if (criteria.getVehicletypeid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getVehicletypeid(), Billingserviceoptionvalues_.vehicletypeid)
                );
            }
            if (criteria.getBillingserviceoptionid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getBillingserviceoptionid(), Billingserviceoptionvalues_.billingserviceoptionid)
                );
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), Billingserviceoptionvalues_.value));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Billingserviceoptionvalues_.lmd));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Billingserviceoptionvalues_.lmu));
            }
        }
        return specification;
    }
}
