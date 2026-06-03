package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.repository.AutojobsalesinvoiceservicechargelineRepository;
import com.heavenscode.rac.service.criteria.AutojobsalesinvoiceservicechargelineCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Autojobsalesinvoiceservicechargeline} entities in the database.
 * The main input is a {@link AutojobsalesinvoiceservicechargelineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Autojobsalesinvoiceservicechargeline} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutojobsalesinvoiceservicechargelineQueryService extends QueryService<Autojobsalesinvoiceservicechargeline> {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsalesinvoiceservicechargelineQueryService.class);

    private final AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository;

    public AutojobsalesinvoiceservicechargelineQueryService(
        AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository
    ) {
        this.autojobsalesinvoiceservicechargelineRepository = autojobsalesinvoiceservicechargelineRepository;
    }

    /**
     * Return a {@link Page} of {@link Autojobsalesinvoiceservicechargeline} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Autojobsalesinvoiceservicechargeline> findByCriteria(AutojobsalesinvoiceservicechargelineCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Autojobsalesinvoiceservicechargeline> specification = createSpecification(criteria);
        return autojobsalesinvoiceservicechargelineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutojobsalesinvoiceservicechargelineCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Autojobsalesinvoiceservicechargeline> specification = createSpecification(criteria);
        return autojobsalesinvoiceservicechargelineRepository.count(specification);
    }

    /**
     * Function to convert {@link AutojobsalesinvoiceservicechargelineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Autojobsalesinvoiceservicechargeline> createSpecification(
        AutojobsalesinvoiceservicechargelineCriteria criteria
    ) {
        Specification<Autojobsalesinvoiceservicechargeline> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Autojobsalesinvoiceservicechargeline_.id));
            }
            if (criteria.getInvoiceid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getInvoiceid(), Autojobsalesinvoiceservicechargeline_.invoiceid)
                );
            }
            if (criteria.getLineid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLineid(), Autojobsalesinvoiceservicechargeline_.lineid)
                );
            }
            if (criteria.getOptionid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getOptionid(), Autojobsalesinvoiceservicechargeline_.optionid)
                );
            }
            if (criteria.getServicename() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getServicename(), Autojobsalesinvoiceservicechargeline_.servicename)
                );
            }
            if (criteria.getServicediscription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getServicediscription(), Autojobsalesinvoiceservicechargeline_.servicediscription)
                );
            }
            if (criteria.getValue() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getValue(), Autojobsalesinvoiceservicechargeline_.value)
                );
            }
            if (criteria.getAddedbyid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAddedbyid(), Autojobsalesinvoiceservicechargeline_.addedbyid)
                );
            }
            if (criteria.getIscustomersrvice() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getIscustomersrvice(), Autojobsalesinvoiceservicechargeline_.iscustomersrvice)
                );
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDiscount(), Autojobsalesinvoiceservicechargeline_.discount)
                );
            }
            if (criteria.getServiceprice() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getServiceprice(), Autojobsalesinvoiceservicechargeline_.serviceprice)
                );
            }
        }
        return specification;
    }
}
