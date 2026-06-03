package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.SalesInvoiceLines;
import com.heavenscode.rac.repository.SalesInvoiceLinesRepository;
import com.heavenscode.rac.service.criteria.SalesInvoiceLinesCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalesInvoiceLines} entities in the database.
 * The main input is a {@link SalesInvoiceLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SalesInvoiceLines} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesInvoiceLinesQueryService extends QueryService<SalesInvoiceLines> {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLinesQueryService.class);

    private final SalesInvoiceLinesRepository salesInvoiceLinesRepository;

    public SalesInvoiceLinesQueryService(SalesInvoiceLinesRepository salesInvoiceLinesRepository) {
        this.salesInvoiceLinesRepository = salesInvoiceLinesRepository;
    }

    /**
     * Return a {@link Page} of {@link SalesInvoiceLines} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesInvoiceLines> findByCriteria(SalesInvoiceLinesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalesInvoiceLines> specification = createSpecification(criteria);
        return salesInvoiceLinesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesInvoiceLinesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SalesInvoiceLines> specification = createSpecification(criteria);
        return salesInvoiceLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesInvoiceLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalesInvoiceLines> createSpecification(SalesInvoiceLinesCriteria criteria) {
        Specification<SalesInvoiceLines> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalesInvoiceLines_.id));
            }
            if (criteria.getInvoiceid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceid(), SalesInvoiceLines_.invoiceid));
            }
            if (criteria.getLineid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineid(), SalesInvoiceLines_.lineid));
            }
            if (criteria.getItemid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemid(), SalesInvoiceLines_.itemid));
            }
            if (criteria.getItemcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemcode(), SalesInvoiceLines_.itemcode));
            }
            if (criteria.getItemname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemname(), SalesInvoiceLines_.itemname));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SalesInvoiceLines_.description));
            }
            if (criteria.getUnitofmeasurement() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getUnitofmeasurement(), SalesInvoiceLines_.unitofmeasurement)
                );
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), SalesInvoiceLines_.quantity));
            }
            if (criteria.getItemcost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemcost(), SalesInvoiceLines_.itemcost));
            }
            if (criteria.getItemprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemprice(), SalesInvoiceLines_.itemprice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), SalesInvoiceLines_.discount));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTax(), SalesInvoiceLines_.tax));
            }
            if (criteria.getSellingprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellingprice(), SalesInvoiceLines_.sellingprice));
            }
            if (criteria.getLinetotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLinetotal(), SalesInvoiceLines_.linetotal));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), SalesInvoiceLines_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), SalesInvoiceLines_.lmd));
            }
            if (criteria.getNbt() != null) {
                specification = specification.and(buildSpecification(criteria.getNbt(), SalesInvoiceLines_.nbt));
            }
            if (criteria.getVat() != null) {
                specification = specification.and(buildSpecification(criteria.getVat(), SalesInvoiceLines_.vat));
            }
        }
        return specification;
    }
}
