package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.SalesInvoiceLinesDummy;
import com.heavenscode.rac.repository.SalesInvoiceLinesDummyRepository;
import com.heavenscode.rac.service.criteria.SalesInvoiceLinesDummyCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalesInvoiceLinesDummy} entities in the database.
 * The main input is a {@link SalesInvoiceLinesDummyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SalesInvoiceLinesDummy} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesInvoiceLinesDummyQueryService extends QueryService<SalesInvoiceLinesDummy> {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLinesDummyQueryService.class);

    private final SalesInvoiceLinesDummyRepository salesInvoiceLinesDummyRepository;

    public SalesInvoiceLinesDummyQueryService(SalesInvoiceLinesDummyRepository salesInvoiceLinesDummyRepository) {
        this.salesInvoiceLinesDummyRepository = salesInvoiceLinesDummyRepository;
    }

    /**
     * Return a {@link Page} of {@link SalesInvoiceLinesDummy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesInvoiceLinesDummy> findByCriteria(SalesInvoiceLinesDummyCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalesInvoiceLinesDummy> specification = createSpecification(criteria);
        return salesInvoiceLinesDummyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesInvoiceLinesDummyCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SalesInvoiceLinesDummy> specification = createSpecification(criteria);
        return salesInvoiceLinesDummyRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesInvoiceLinesDummyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalesInvoiceLinesDummy> createSpecification(SalesInvoiceLinesDummyCriteria criteria) {
        Specification<SalesInvoiceLinesDummy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalesInvoiceLinesDummy_.id));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceId(), SalesInvoiceLinesDummy_.invoiceId));
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineId(), SalesInvoiceLinesDummy_.lineId));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemId(), SalesInvoiceLinesDummy_.itemId));
            }
            if (criteria.getItemCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemCode(), SalesInvoiceLinesDummy_.itemCode));
            }
            if (criteria.getItemName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemName(), SalesInvoiceLinesDummy_.itemName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SalesInvoiceLinesDummy_.description));
            }
            if (criteria.getUnitOfMeasurement() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getUnitOfMeasurement(), SalesInvoiceLinesDummy_.unitOfMeasurement)
                );
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), SalesInvoiceLinesDummy_.quantity));
            }
            if (criteria.getItemCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemCost(), SalesInvoiceLinesDummy_.itemCost));
            }
            if (criteria.getItemPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemPrice(), SalesInvoiceLinesDummy_.itemPrice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), SalesInvoiceLinesDummy_.discount));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTax(), SalesInvoiceLinesDummy_.tax));
            }
            if (criteria.getSellingPrice() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getSellingPrice(), SalesInvoiceLinesDummy_.sellingPrice)
                );
            }
            if (criteria.getLineTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineTotal(), SalesInvoiceLinesDummy_.lineTotal));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), SalesInvoiceLinesDummy_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), SalesInvoiceLinesDummy_.lmd));
            }
            if (criteria.getNbt() != null) {
                specification = specification.and(buildSpecification(criteria.getNbt(), SalesInvoiceLinesDummy_.nbt));
            }
            if (criteria.getVat() != null) {
                specification = specification.and(buildSpecification(criteria.getVat(), SalesInvoiceLinesDummy_.vat));
            }
        }
        return specification;
    }
}
