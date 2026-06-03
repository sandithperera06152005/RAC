package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeRepository;
import com.heavenscode.rac.service.criteria.SaleInvoiceCommonServiceChargeCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SaleInvoiceCommonServiceCharge} entities in the database.
 * The main input is a {@link SaleInvoiceCommonServiceChargeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SaleInvoiceCommonServiceCharge} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SaleInvoiceCommonServiceChargeQueryService extends QueryService<SaleInvoiceCommonServiceCharge> {

    private static final Logger LOG = LoggerFactory.getLogger(SaleInvoiceCommonServiceChargeQueryService.class);

    private final SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository;

    public SaleInvoiceCommonServiceChargeQueryService(SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository) {
        this.saleInvoiceCommonServiceChargeRepository = saleInvoiceCommonServiceChargeRepository;
    }

    /**
     * Return a {@link Page} of {@link SaleInvoiceCommonServiceCharge} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SaleInvoiceCommonServiceCharge> findByCriteria(SaleInvoiceCommonServiceChargeCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SaleInvoiceCommonServiceCharge> specification = createSpecification(criteria);
        return saleInvoiceCommonServiceChargeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SaleInvoiceCommonServiceChargeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SaleInvoiceCommonServiceCharge> specification = createSpecification(criteria);
        return saleInvoiceCommonServiceChargeRepository.count(specification);
    }

    /**
     * Function to convert {@link SaleInvoiceCommonServiceChargeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SaleInvoiceCommonServiceCharge> createSpecification(SaleInvoiceCommonServiceChargeCriteria criteria) {
        Specification<SaleInvoiceCommonServiceCharge> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SaleInvoiceCommonServiceCharge_.id));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getInvoiceId(), SaleInvoiceCommonServiceCharge_.invoiceId)
                );
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineId(), SaleInvoiceCommonServiceCharge_.lineId));
            }
            if (criteria.getOptionId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getOptionId(), SaleInvoiceCommonServiceCharge_.optionId)
                );
            }
            if (criteria.getMainId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMainId(), SaleInvoiceCommonServiceCharge_.mainId));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SaleInvoiceCommonServiceCharge_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SaleInvoiceCommonServiceCharge_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getDescription(), SaleInvoiceCommonServiceCharge_.description)
                );
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), SaleInvoiceCommonServiceCharge_.value));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDiscount(), SaleInvoiceCommonServiceCharge_.discount)
                );
            }
            if (criteria.getServicePrice() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getServicePrice(), SaleInvoiceCommonServiceCharge_.servicePrice)
                );
            }
        }
        return specification;
    }
}
