package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.SalesInvoiceDummy;
import com.heavenscode.rac.repository.SalesInvoiceDummyRepository;
import com.heavenscode.rac.service.criteria.SalesInvoiceDummyCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalesInvoiceDummy} entities in the database.
 * The main input is a {@link SalesInvoiceDummyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SalesInvoiceDummy} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesInvoiceDummyQueryService extends QueryService<SalesInvoiceDummy> {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceDummyQueryService.class);

    private final SalesInvoiceDummyRepository salesInvoiceDummyRepository;

    public SalesInvoiceDummyQueryService(SalesInvoiceDummyRepository salesInvoiceDummyRepository) {
        this.salesInvoiceDummyRepository = salesInvoiceDummyRepository;
    }

    /**
     * Return a {@link Page} of {@link SalesInvoiceDummy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesInvoiceDummy> findByCriteria(SalesInvoiceDummyCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalesInvoiceDummy> specification = createSpecification(criteria);
        return salesInvoiceDummyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesInvoiceDummyCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SalesInvoiceDummy> specification = createSpecification(criteria);
        return salesInvoiceDummyRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesInvoiceDummyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalesInvoiceDummy> createSpecification(SalesInvoiceDummyCriteria criteria) {
        Specification<SalesInvoiceDummy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalesInvoiceDummy_.id));
            }
            if (criteria.getOriginalInvoiceId() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getOriginalInvoiceId(), SalesInvoiceDummy_.originalInvoiceId)
                );
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SalesInvoiceDummy_.code));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), SalesInvoiceDummy_.invoiceDate));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SalesInvoiceDummy_.createdDate));
            }
            if (criteria.getQuoteID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteID(), SalesInvoiceDummy_.quoteID));
            }
            if (criteria.getOrderID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderID(), SalesInvoiceDummy_.orderID));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), SalesInvoiceDummy_.deliveryDate));
            }
            if (criteria.getSalesRepID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalesRepID(), SalesInvoiceDummy_.salesRepID));
            }
            if (criteria.getSalesRepName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalesRepName(), SalesInvoiceDummy_.salesRepName));
            }
            if (criteria.getDeliverFrom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliverFrom(), SalesInvoiceDummy_.deliverFrom));
            }
            if (criteria.getCustomerID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerID(), SalesInvoiceDummy_.customerID));
            }
            if (criteria.getCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerName(), SalesInvoiceDummy_.customerName));
            }
            if (criteria.getCustomerAddress() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getCustomerAddress(), SalesInvoiceDummy_.customerAddress)
                );
            }
            if (criteria.getDeliveryAddress() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getDeliveryAddress(), SalesInvoiceDummy_.deliveryAddress)
                );
            }
            if (criteria.getSubTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubTotal(), SalesInvoiceDummy_.subTotal));
            }
            if (criteria.getTotalTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalTax(), SalesInvoiceDummy_.totalTax));
            }
            if (criteria.getTotalDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDiscount(), SalesInvoiceDummy_.totalDiscount));
            }
            if (criteria.getNetTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetTotal(), SalesInvoiceDummy_.netTotal));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), SalesInvoiceDummy_.message));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), SalesInvoiceDummy_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), SalesInvoiceDummy_.lmd));
            }
            if (criteria.getPaidAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidAmount(), SalesInvoiceDummy_.paidAmount));
            }
            if (criteria.getAmountOwing() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountOwing(), SalesInvoiceDummy_.amountOwing));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), SalesInvoiceDummy_.isActive));
            }
            if (criteria.getLocationID() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLocationID(), SalesInvoiceDummy_.locationID));
            }
            if (criteria.getLocationCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocationCode(), SalesInvoiceDummy_.locationCode));
            }
            if (criteria.getReferenceCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceCode(), SalesInvoiceDummy_.referenceCode));
            }
            if (criteria.getCreatedById() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedById(), SalesInvoiceDummy_.createdById));
            }
            if (criteria.getCreatedByName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedByName(), SalesInvoiceDummy_.createdByName));
            }
            if (criteria.getAutoCareCharges() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAutoCareCharges(), SalesInvoiceDummy_.autoCareCharges)
                );
            }
            if (criteria.getAutoCareJobId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAutoCareJobId(), SalesInvoiceDummy_.autoCareJobId));
            }
            if (criteria.getVehicleNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVehicleNo(), SalesInvoiceDummy_.vehicleNo));
            }
            if (criteria.getNbtAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbtAmount(), SalesInvoiceDummy_.nbtAmount));
            }
            if (criteria.getVatAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVatAmount(), SalesInvoiceDummy_.vatAmount));
            }
            if (criteria.getDummyCommission() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDummyCommission(), SalesInvoiceDummy_.dummyCommission)
                );
            }
            if (criteria.getCommissionPaidDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getCommissionPaidDate(), SalesInvoiceDummy_.commissionPaidDate)
                );
            }
            if (criteria.getPaidCommission() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidCommission(), SalesInvoiceDummy_.paidCommission));
            }
            if (criteria.getPaidBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidBy(), SalesInvoiceDummy_.paidBy));
            }
            if (criteria.getOriginalInvoiceCode() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.getOriginalInvoiceCode(), SalesInvoiceDummy_.originalInvoiceCode)
                );
            }
        }
        return specification;
    }
}
