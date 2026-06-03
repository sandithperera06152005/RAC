package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Autojobsinvoicelines;
import com.heavenscode.rac.repository.AutojobsinvoicelinesRepository;
import com.heavenscode.rac.service.criteria.AutojobsinvoicelinesCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Autojobsinvoicelines} entities in the database.
 * The main input is a {@link AutojobsinvoicelinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Autojobsinvoicelines} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutojobsinvoicelinesQueryService extends QueryService<Autojobsinvoicelines> {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoicelinesQueryService.class);

    private final AutojobsinvoicelinesRepository autojobsinvoicelinesRepository;

    public AutojobsinvoicelinesQueryService(AutojobsinvoicelinesRepository autojobsinvoicelinesRepository) {
        this.autojobsinvoicelinesRepository = autojobsinvoicelinesRepository;
    }

    /**
     * Return a {@link Page} of {@link Autojobsinvoicelines} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Autojobsinvoicelines> findByCriteria(AutojobsinvoicelinesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Autojobsinvoicelines> specification = createSpecification(criteria);
        return autojobsinvoicelinesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutojobsinvoicelinesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Autojobsinvoicelines> specification = createSpecification(criteria);
        return autojobsinvoicelinesRepository.count(specification);
    }

    /**
     * Function to convert {@link AutojobsinvoicelinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Autojobsinvoicelines> createSpecification(AutojobsinvoicelinesCriteria criteria) {
        Specification<Autojobsinvoicelines> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Autojobsinvoicelines_.id));
            }
            if (criteria.getInvocieid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvocieid(), Autojobsinvoicelines_.invocieid));
            }
            if (criteria.getLineid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineid(), Autojobsinvoicelines_.lineid));
            }
            if (criteria.getItemid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemid(), Autojobsinvoicelines_.itemid));
            }
            if (criteria.getItemcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemcode(), Autojobsinvoicelines_.itemcode));
            }
            if (criteria.getItemname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemname(), Autojobsinvoicelines_.itemname));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Autojobsinvoicelines_.description));
            }
            if (criteria.getUnitofmeasurement() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getUnitofmeasurement(), Autojobsinvoicelines_.unitofmeasurement)
                );
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Autojobsinvoicelines_.quantity));
            }
            if (criteria.getItemcost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemcost(), Autojobsinvoicelines_.itemcost));
            }
            if (criteria.getItemprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemprice(), Autojobsinvoicelines_.itemprice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), Autojobsinvoicelines_.discount));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTax(), Autojobsinvoicelines_.tax));
            }
            if (criteria.getSellingprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellingprice(), Autojobsinvoicelines_.sellingprice));
            }
            if (criteria.getLinetotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLinetotal(), Autojobsinvoicelines_.linetotal));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Autojobsinvoicelines_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Autojobsinvoicelines_.lmd));
            }
            if (criteria.getNbt() != null) {
                specification = specification.and(buildSpecification(criteria.getNbt(), Autojobsinvoicelines_.nbt));
            }
            if (criteria.getVat() != null) {
                specification = specification.and(buildSpecification(criteria.getVat(), Autojobsinvoicelines_.vat));
            }
        }
        return specification;
    }
}
