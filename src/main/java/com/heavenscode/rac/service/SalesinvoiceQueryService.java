package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Salesinvoice;
import com.heavenscode.rac.repository.SalesinvoiceRepository;
import com.heavenscode.rac.service.criteria.SalesinvoiceCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Salesinvoice} entities in the database.
 * The main input is a {@link SalesinvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Salesinvoice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesinvoiceQueryService extends QueryService<Salesinvoice> {

    private static final Logger LOG = LoggerFactory.getLogger(SalesinvoiceQueryService.class);

    private final SalesinvoiceRepository salesinvoiceRepository;

    public SalesinvoiceQueryService(SalesinvoiceRepository salesinvoiceRepository) {
        this.salesinvoiceRepository = salesinvoiceRepository;
    }

    /**
     * Return a {@link Page} of {@link Salesinvoice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Salesinvoice> findByCriteria(SalesinvoiceCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Salesinvoice> specification = createSpecification(criteria);
        return salesinvoiceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesinvoiceCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Salesinvoice> specification = createSpecification(criteria);
        return salesinvoiceRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesinvoiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Salesinvoice> createSpecification(SalesinvoiceCriteria criteria) {
        Specification<Salesinvoice> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Salesinvoice_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Salesinvoice_.code));
            }
            if (criteria.getInvoicedate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoicedate(), Salesinvoice_.invoicedate));
            }
            if (criteria.getCreateddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateddate(), Salesinvoice_.createddate));
            }
            if (criteria.getQuoteid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteid(), Salesinvoice_.quoteid));
            }
            if (criteria.getOrderid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderid(), Salesinvoice_.orderid));
            }
            if (criteria.getDelieverydate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelieverydate(), Salesinvoice_.delieverydate));
            }
            if (criteria.getSalesrepid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalesrepid(), Salesinvoice_.salesrepid));
            }
            if (criteria.getSalesrepname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalesrepname(), Salesinvoice_.salesrepname));
            }
            if (criteria.getDelieverfrom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDelieverfrom(), Salesinvoice_.delieverfrom));
            }
            if (criteria.getCustomerid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerid(), Salesinvoice_.customerid));
            }
            if (criteria.getCustomername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomername(), Salesinvoice_.customername));
            }
            if (criteria.getCustomeraddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomeraddress(), Salesinvoice_.customeraddress));
            }
            if (criteria.getDeliveryaddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryaddress(), Salesinvoice_.deliveryaddress));
            }
            if (criteria.getSubtotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubtotal(), Salesinvoice_.subtotal));
            }
            if (criteria.getTotaltax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotaltax(), Salesinvoice_.totaltax));
            }
            if (criteria.getTotaldiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotaldiscount(), Salesinvoice_.totaldiscount));
            }
            if (criteria.getNettotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNettotal(), Salesinvoice_.nettotal));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), Salesinvoice_.message));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Salesinvoice_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Salesinvoice_.lmd));
            }
            if (criteria.getPaidamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidamount(), Salesinvoice_.paidamount));
            }
            if (criteria.getAmountowing() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountowing(), Salesinvoice_.amountowing));
            }
            if (criteria.getIsactive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsactive(), Salesinvoice_.isactive));
            }
            if (criteria.getLocationid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLocationid(), Salesinvoice_.locationid));
            }
            if (criteria.getLocationcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocationcode(), Salesinvoice_.locationcode));
            }
            if (criteria.getReferencecode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferencecode(), Salesinvoice_.referencecode));
            }
            if (criteria.getCreatedbyid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedbyid(), Salesinvoice_.createdbyid));
            }
            if (criteria.getCreatedbyname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedbyname(), Salesinvoice_.createdbyname));
            }
            if (criteria.getAutocarecharges() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAutocarecharges(), Salesinvoice_.autocarecharges));
            }
            if (criteria.getAutocarejobid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAutocarejobid(), Salesinvoice_.autocarejobid));
            }
            if (criteria.getVehicleno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVehicleno(), Salesinvoice_.vehicleno));
            }
            if (criteria.getNextmeter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNextmeter(), Salesinvoice_.nextmeter));
            }
            if (criteria.getCurrentmeter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentmeter(), Salesinvoice_.currentmeter));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Salesinvoice_.remarks));
            }
            if (criteria.getHasdummybill() != null) {
                specification = specification.and(buildSpecification(criteria.getHasdummybill(), Salesinvoice_.hasdummybill));
            }
            if (criteria.getDummybillid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummybillid(), Salesinvoice_.dummybillid));
            }
            if (criteria.getDummybillamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummybillamount(), Salesinvoice_.dummybillamount));
            }
            if (criteria.getDummycommision() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummycommision(), Salesinvoice_.dummycommision));
            }
            if (criteria.getIsserviceinvoice() != null) {
                specification = specification.and(buildSpecification(criteria.getIsserviceinvoice(), Salesinvoice_.isserviceinvoice));
            }
            if (criteria.getNbtamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbtamount(), Salesinvoice_.nbtamount));
            }
            if (criteria.getVatamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVatamount(), Salesinvoice_.vatamount));
            }
            if (criteria.getAutocarecompanyid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAutocarecompanyid(), Salesinvoice_.autocarecompanyid)
                );
            }
            if (criteria.getIscompanyinvoice() != null) {
                specification = specification.and(buildSpecification(criteria.getIscompanyinvoice(), Salesinvoice_.iscompanyinvoice));
            }
            if (criteria.getInvcanceldate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvcanceldate(), Salesinvoice_.invcanceldate));
            }
            if (criteria.getInvcancelby() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvcancelby(), Salesinvoice_.invcancelby));
            }
            if (criteria.getIsvatinvoice() != null) {
                specification = specification.and(buildSpecification(criteria.getIsvatinvoice(), Salesinvoice_.isvatinvoice));
            }
            if (criteria.getPaymenttype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymenttype(), Salesinvoice_.paymenttype));
            }
            if (criteria.getPendingamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPendingamount(), Salesinvoice_.pendingamount));
            }
            if (criteria.getAdvancepayment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdvancepayment(), Salesinvoice_.advancepayment));
            }
            if (criteria.getDiscountcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscountcode(), Salesinvoice_.discountcode));
            }
        }
        return specification;
    }
}
