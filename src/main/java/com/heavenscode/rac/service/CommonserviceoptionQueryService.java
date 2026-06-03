package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Commonserviceoption;
import com.heavenscode.rac.repository.CommonserviceoptionRepository;
import com.heavenscode.rac.service.criteria.CommonserviceoptionCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Commonserviceoption} entities in the database.
 * The main input is a {@link CommonserviceoptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Commonserviceoption} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommonserviceoptionQueryService extends QueryService<Commonserviceoption> {

    private static final Logger LOG = LoggerFactory.getLogger(CommonserviceoptionQueryService.class);

    private final CommonserviceoptionRepository commonserviceoptionRepository;

    public CommonserviceoptionQueryService(CommonserviceoptionRepository commonserviceoptionRepository) {
        this.commonserviceoptionRepository = commonserviceoptionRepository;
    }

    /**
     * Return a {@link Page} of {@link Commonserviceoption} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Commonserviceoption> findByCriteria(CommonserviceoptionCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Commonserviceoption> specification = createSpecification(criteria);
        return commonserviceoptionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommonserviceoptionCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Commonserviceoption> specification = createSpecification(criteria);
        return commonserviceoptionRepository.count(specification);
    }

    /**
     * Function to convert {@link CommonserviceoptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Commonserviceoption> createSpecification(CommonserviceoptionCriteria criteria) {
        Specification<Commonserviceoption> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Commonserviceoption_.id));
            }
            if (criteria.getMainid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMainid(), Commonserviceoption_.mainid));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Commonserviceoption_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Commonserviceoption_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Commonserviceoption_.description));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), Commonserviceoption_.value));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Commonserviceoption_.isactive));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Commonserviceoption_.lmd));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Commonserviceoption_.lmu));
            }
        }
        return specification;
    }
}
