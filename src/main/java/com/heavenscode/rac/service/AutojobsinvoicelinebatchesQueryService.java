package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Autojobsinvoicelinebatches;
import com.heavenscode.rac.repository.AutojobsinvoicelinebatchesRepository;
import com.heavenscode.rac.service.criteria.AutojobsinvoicelinebatchesCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Autojobsinvoicelinebatches} entities in the database.
 * The main input is a {@link AutojobsinvoicelinebatchesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Autojobsinvoicelinebatches} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutojobsinvoicelinebatchesQueryService extends QueryService<Autojobsinvoicelinebatches> {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoicelinebatchesQueryService.class);

    private final AutojobsinvoicelinebatchesRepository autojobsinvoicelinebatchesRepository;

    public AutojobsinvoicelinebatchesQueryService(AutojobsinvoicelinebatchesRepository autojobsinvoicelinebatchesRepository) {
        this.autojobsinvoicelinebatchesRepository = autojobsinvoicelinebatchesRepository;
    }

    /**
     * Return a {@link Page} of {@link Autojobsinvoicelinebatches} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Autojobsinvoicelinebatches> findByCriteria(AutojobsinvoicelinebatchesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Autojobsinvoicelinebatches> specification = createSpecification(criteria);
        return autojobsinvoicelinebatchesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutojobsinvoicelinebatchesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Autojobsinvoicelinebatches> specification = createSpecification(criteria);
        return autojobsinvoicelinebatchesRepository.count(specification);
    }

    /**
     * Function to convert {@link AutojobsinvoicelinebatchesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Autojobsinvoicelinebatches> createSpecification(AutojobsinvoicelinebatchesCriteria criteria) {
        Specification<Autojobsinvoicelinebatches> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Autojobsinvoicelinebatches_.id));
            }
            if (criteria.getLineid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineid(), Autojobsinvoicelinebatches_.lineid));
            }
            if (criteria.getBatchlineid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getBatchlineid(), Autojobsinvoicelinebatches_.batchlineid)
                );
            }
            if (criteria.getItemid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemid(), Autojobsinvoicelinebatches_.itemid));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Autojobsinvoicelinebatches_.code));
            }
            if (criteria.getBatchid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBatchid(), Autojobsinvoicelinebatches_.batchid));
            }
            if (criteria.getBatchcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchcode(), Autojobsinvoicelinebatches_.batchcode));
            }
            if (criteria.getTxdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTxdate(), Autojobsinvoicelinebatches_.txdate));
            }
            if (criteria.getManufacturedate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getManufacturedate(), Autojobsinvoicelinebatches_.manufacturedate)
                );
            }
            if (criteria.getExpireddate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getExpireddate(), Autojobsinvoicelinebatches_.expireddate)
                );
            }
            if (criteria.getQty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQty(), Autojobsinvoicelinebatches_.qty));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), Autojobsinvoicelinebatches_.cost));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Autojobsinvoicelinebatches_.price));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Autojobsinvoicelinebatches_.notes));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Autojobsinvoicelinebatches_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Autojobsinvoicelinebatches_.lmd));
            }
            if (criteria.getNbt() != null) {
                specification = specification.and(buildSpecification(criteria.getNbt(), Autojobsinvoicelinebatches_.nbt));
            }
            if (criteria.getVat() != null) {
                specification = specification.and(buildSpecification(criteria.getVat(), Autojobsinvoicelinebatches_.vat));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), Autojobsinvoicelinebatches_.discount));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), Autojobsinvoicelinebatches_.total));
            }
            if (criteria.getIssued() != null) {
                specification = specification.and(buildSpecification(criteria.getIssued(), Autojobsinvoicelinebatches_.issued));
            }
            if (criteria.getIssuedby() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedby(), Autojobsinvoicelinebatches_.issuedby));
            }
            if (criteria.getIssueddatetime() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getIssueddatetime(), Autojobsinvoicelinebatches_.issueddatetime)
                );
            }
            if (criteria.getAddedbyid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddedbyid(), Autojobsinvoicelinebatches_.addedbyid));
            }
            if (criteria.getCanceloptid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCanceloptid(), Autojobsinvoicelinebatches_.canceloptid)
                );
            }
            if (criteria.getCancelopt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCancelopt(), Autojobsinvoicelinebatches_.cancelopt));
            }
            if (criteria.getCancelby() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCancelby(), Autojobsinvoicelinebatches_.cancelby));
            }
        }
        return specification;
    }
}
