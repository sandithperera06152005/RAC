package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Servicesubcategory;
import com.heavenscode.rac.repository.ServicesubcategoryRepository;
import com.heavenscode.rac.service.criteria.ServicesubcategoryCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Servicesubcategory} entities in the database.
 * The main input is a {@link ServicesubcategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Servicesubcategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServicesubcategoryQueryService extends QueryService<Servicesubcategory> {

    private static final Logger LOG = LoggerFactory.getLogger(ServicesubcategoryQueryService.class);

    private final ServicesubcategoryRepository servicesubcategoryRepository;

    public ServicesubcategoryQueryService(ServicesubcategoryRepository servicesubcategoryRepository) {
        this.servicesubcategoryRepository = servicesubcategoryRepository;
    }

    /**
     * Return a {@link Page} of {@link Servicesubcategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Servicesubcategory> findByCriteria(ServicesubcategoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Servicesubcategory> specification = createSpecification(criteria);
        return servicesubcategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServicesubcategoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Servicesubcategory> specification = createSpecification(criteria);
        return servicesubcategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ServicesubcategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Servicesubcategory> createSpecification(ServicesubcategoryCriteria criteria) {
        Specification<Servicesubcategory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Servicesubcategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Servicesubcategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Servicesubcategory_.description));
            }
            if (criteria.getMainid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMainid(), Servicesubcategory_.mainid));
            }
            if (criteria.getMainname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMainname(), Servicesubcategory_.mainname));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Servicesubcategory_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Servicesubcategory_.lmd));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Servicesubcategory_.isactive));
            }
        }
        return specification;
    }
}
