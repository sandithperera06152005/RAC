package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummy;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeDummyRepository;
import com.heavenscode.rac.service.criteria.SaleInvoiceCommonServiceChargeDummyCriteria;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SaleInvoiceCommonServiceChargeDummy} entities in the database.
 * The main input is a {@link SaleInvoiceCommonServiceChargeDummyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SaleInvoiceCommonServiceChargeDummy} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SaleInvoiceCommonServiceChargeDummyQueryService extends QueryService<SaleInvoiceCommonServiceChargeDummy> {

    private static final Logger LOG = LoggerFactory.getLogger(SaleInvoiceCommonServiceChargeDummyQueryService.class);

    private final SaleInvoiceCommonServiceChargeDummyRepository saleInvoiceCommonServiceChargeDummyRepository;

    public SaleInvoiceCommonServiceChargeDummyQueryService(
        SaleInvoiceCommonServiceChargeDummyRepository saleInvoiceCommonServiceChargeDummyRepository
    ) {
        this.saleInvoiceCommonServiceChargeDummyRepository = saleInvoiceCommonServiceChargeDummyRepository;
    }

    /**
     * Return a {@link List} of {@link SaleInvoiceCommonServiceChargeDummy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SaleInvoiceCommonServiceChargeDummy> findByCriteria(SaleInvoiceCommonServiceChargeDummyCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<SaleInvoiceCommonServiceChargeDummy> specification = createSpecification(criteria);
        return saleInvoiceCommonServiceChargeDummyRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SaleInvoiceCommonServiceChargeDummyCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SaleInvoiceCommonServiceChargeDummy> specification = createSpecification(criteria);
        return saleInvoiceCommonServiceChargeDummyRepository.count(specification);
    }

    /**
     * Function to convert {@link SaleInvoiceCommonServiceChargeDummyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SaleInvoiceCommonServiceChargeDummy> createSpecification(SaleInvoiceCommonServiceChargeDummyCriteria criteria) {
        Specification<SaleInvoiceCommonServiceChargeDummy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SaleInvoiceCommonServiceChargeDummy_.id));
            }
            if (criteria.getInvoiceid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getInvoiceid(), SaleInvoiceCommonServiceChargeDummy_.invoiceid)
                );
            }
            if (criteria.getLineid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLineid(), SaleInvoiceCommonServiceChargeDummy_.lineid)
                );
            }
            if (criteria.getOptionid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getOptionid(), SaleInvoiceCommonServiceChargeDummy_.optionid)
                );
            }
            if (criteria.getMainid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getMainid(), SaleInvoiceCommonServiceChargeDummy_.mainid)
                );
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SaleInvoiceCommonServiceChargeDummy_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SaleInvoiceCommonServiceChargeDummy_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getDescription(), SaleInvoiceCommonServiceChargeDummy_.description)
                );
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), SaleInvoiceCommonServiceChargeDummy_.value));
            }
        }
        return specification;
    }
}
