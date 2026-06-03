package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Servicecategory;
import com.heavenscode.rac.repository.ServicecategoryRepository;
import com.heavenscode.rac.service.criteria.ServicecategoryCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Servicecategory} entities in the database.
 * The main input is a {@link ServicecategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Servicecategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServicecategoryQueryService extends QueryService<Servicecategory> {

    private static final Logger LOG = LoggerFactory.getLogger(ServicecategoryQueryService.class);

    private final ServicecategoryRepository servicecategoryRepository;

    public ServicecategoryQueryService(ServicecategoryRepository servicecategoryRepository) {
        this.servicecategoryRepository = servicecategoryRepository;
    }

    /**
     * Return a {@link Page} of {@link Servicecategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Servicecategory> findByCriteria(ServicecategoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Servicecategory> specification = createSpecification(criteria);
        return servicecategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServicecategoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Servicecategory> specification = createSpecification(criteria);
        return servicecategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ServicecategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Servicecategory> createSpecification(ServicecategoryCriteria criteria) {
        Specification<Servicecategory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Servicecategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Servicecategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Servicecategory_.description));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Servicecategory_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Servicecategory_.lmd));
            }
            if (criteria.getShowsecurity() != null) {
                specification = specification.and(buildSpecification(criteria.getShowsecurity(), Servicecategory_.showsecurity));
            }
            if (criteria.getSortorder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortorder(), Servicecategory_.sortorder));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Servicecategory_.isactive));
            }
        }
        return specification;
    }
}
