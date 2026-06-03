package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Autojobsinvoice;
import com.heavenscode.rac.repository.AutojobsinvoiceRepository;
import com.heavenscode.rac.service.criteria.AutojobsinvoiceCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Autojobsinvoice} entities in the database.
 * The main input is a {@link AutojobsinvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Autojobsinvoice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutojobsinvoiceQueryService extends QueryService<Autojobsinvoice> {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoiceQueryService.class);

    private final AutojobsinvoiceRepository autojobsinvoiceRepository;

    public AutojobsinvoiceQueryService(AutojobsinvoiceRepository autojobsinvoiceRepository) {
        this.autojobsinvoiceRepository = autojobsinvoiceRepository;
    }

    /**
     * Return a {@link Page} of {@link Autojobsinvoice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Autojobsinvoice> findByCriteria(AutojobsinvoiceCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Autojobsinvoice> specification = createSpecification(criteria);
        return autojobsinvoiceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutojobsinvoiceCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Autojobsinvoice> specification = createSpecification(criteria);
        return autojobsinvoiceRepository.count(specification);
    }

    /**
     * Function to convert {@link AutojobsinvoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Autojobsinvoice> createSpecification(AutojobsinvoiceCriteria criteria) {
        Specification<Autojobsinvoice> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Autojobsinvoice_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Autojobsinvoice_.code));
            }
            if (criteria.getInvoicedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoicedate(), Autojobsinvoice_.invoicedate));
            }
            if (criteria.getCreateddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateddate(), Autojobsinvoice_.createddate));
            }
            if (criteria.getJobid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJobid(), Autojobsinvoice_.jobid));
            }
            if (criteria.getQuoteid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteid(), Autojobsinvoice_.quoteid));
            }
            if (criteria.getOrderid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderid(), Autojobsinvoice_.orderid));
            }
            if (criteria.getDelieverydate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelieverydate(), Autojobsinvoice_.delieverydate));
            }
            if (criteria.getAutojobsrepid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAutojobsrepid(), Autojobsinvoice_.autojobsrepid));
            }
            if (criteria.getAutojobsrepname() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getAutojobsrepname(), Autojobsinvoice_.autojobsrepname)
                );
            }
            if (criteria.getDelieverfrom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDelieverfrom(), Autojobsinvoice_.delieverfrom));
            }
            if (criteria.getCustomerid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerid(), Autojobsinvoice_.customerid));
            }
            if (criteria.getCustomername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomername(), Autojobsinvoice_.customername));
            }
            if (criteria.getCustomeraddress() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCustomeraddress(), Autojobsinvoice_.customeraddress)
                );
            }
            if (criteria.getDeliveryaddress() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getDeliveryaddress(), Autojobsinvoice_.deliveryaddress)
                );
            }
            if (criteria.getSubtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubtotal(), Autojobsinvoice_.subtotal));
            }
            if (criteria.getTotaltax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotaltax(), Autojobsinvoice_.totaltax));
            }
            if (criteria.getTotaldiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotaldiscount(), Autojobsinvoice_.totaldiscount));
            }
            if (criteria.getNettotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNettotal(), Autojobsinvoice_.nettotal));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), Autojobsinvoice_.message));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Autojobsinvoice_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Autojobsinvoice_.lmd));
            }
            if (criteria.getPaidamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidamount(), Autojobsinvoice_.paidamount));
            }
            if (criteria.getAmountowing() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountowing(), Autojobsinvoice_.amountowing));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Autojobsinvoice_.isactive));
            }
            if (criteria.getLocationid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLocationid(), Autojobsinvoice_.locationid));
            }
            if (criteria.getLocationcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocationcode(), Autojobsinvoice_.locationcode));
            }
            if (criteria.getReferencecode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferencecode(), Autojobsinvoice_.referencecode));
            }
            if (criteria.getCreatedbyid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedbyid(), Autojobsinvoice_.createdbyid));
            }
            if (criteria.getCreatedbyname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedbyname(), Autojobsinvoice_.createdbyname));
            }
            if (criteria.getAutocarecompanyid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAutocarecompanyid(), Autojobsinvoice_.autocarecompanyid)
                );
            }
        }
        return specification;
    }
}
