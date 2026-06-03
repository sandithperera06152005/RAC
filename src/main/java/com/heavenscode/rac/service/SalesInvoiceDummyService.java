package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SalesInvoiceDummy;
import com.heavenscode.rac.repository.SalesInvoiceDummyRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SalesInvoiceDummy}.
 */
@Service
@Transactional
public class SalesInvoiceDummyService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceDummyService.class);

    private final SalesInvoiceDummyRepository salesInvoiceDummyRepository;

    public SalesInvoiceDummyService(SalesInvoiceDummyRepository salesInvoiceDummyRepository) {
        this.salesInvoiceDummyRepository = salesInvoiceDummyRepository;
    }

    /**
     * Save a salesInvoiceDummy.
     *
     * @param salesInvoiceDummy the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceDummy save(SalesInvoiceDummy salesInvoiceDummy) {
        LOG.debug("Request to save SalesInvoiceDummy : {}", salesInvoiceDummy);
        return salesInvoiceDummyRepository.save(salesInvoiceDummy);
    }

    /**
     * Update a salesInvoiceDummy.
     *
     * @param salesInvoiceDummy the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceDummy update(SalesInvoiceDummy salesInvoiceDummy) {
        LOG.debug("Request to update SalesInvoiceDummy : {}", salesInvoiceDummy);
        return salesInvoiceDummyRepository.save(salesInvoiceDummy);
    }

    /**
     * Partially update a salesInvoiceDummy.
     *
     * @param salesInvoiceDummy the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesInvoiceDummy> partialUpdate(SalesInvoiceDummy salesInvoiceDummy) {
        LOG.debug("Request to partially update SalesInvoiceDummy : {}", salesInvoiceDummy);

        return salesInvoiceDummyRepository
            .findById(salesInvoiceDummy.getId())
            .map(existingSalesInvoiceDummy -> {
                if (salesInvoiceDummy.getOriginalInvoiceId() != null) {
                    existingSalesInvoiceDummy.setOriginalInvoiceId(salesInvoiceDummy.getOriginalInvoiceId());
                }
                if (salesInvoiceDummy.getCode() != null) {
                    existingSalesInvoiceDummy.setCode(salesInvoiceDummy.getCode());
                }
                if (salesInvoiceDummy.getInvoiceDate() != null) {
                    existingSalesInvoiceDummy.setInvoiceDate(salesInvoiceDummy.getInvoiceDate());
                }
                if (salesInvoiceDummy.getCreatedDate() != null) {
                    existingSalesInvoiceDummy.setCreatedDate(salesInvoiceDummy.getCreatedDate());
                }
                if (salesInvoiceDummy.getQuoteID() != null) {
                    existingSalesInvoiceDummy.setQuoteID(salesInvoiceDummy.getQuoteID());
                }
                if (salesInvoiceDummy.getOrderID() != null) {
                    existingSalesInvoiceDummy.setOrderID(salesInvoiceDummy.getOrderID());
                }
                if (salesInvoiceDummy.getDeliveryDate() != null) {
                    existingSalesInvoiceDummy.setDeliveryDate(salesInvoiceDummy.getDeliveryDate());
                }
                if (salesInvoiceDummy.getSalesRepID() != null) {
                    existingSalesInvoiceDummy.setSalesRepID(salesInvoiceDummy.getSalesRepID());
                }
                if (salesInvoiceDummy.getSalesRepName() != null) {
                    existingSalesInvoiceDummy.setSalesRepName(salesInvoiceDummy.getSalesRepName());
                }
                if (salesInvoiceDummy.getDeliverFrom() != null) {
                    existingSalesInvoiceDummy.setDeliverFrom(salesInvoiceDummy.getDeliverFrom());
                }
                if (salesInvoiceDummy.getCustomerID() != null) {
                    existingSalesInvoiceDummy.setCustomerID(salesInvoiceDummy.getCustomerID());
                }
                if (salesInvoiceDummy.getCustomerName() != null) {
                    existingSalesInvoiceDummy.setCustomerName(salesInvoiceDummy.getCustomerName());
                }
                if (salesInvoiceDummy.getCustomerAddress() != null) {
                    existingSalesInvoiceDummy.setCustomerAddress(salesInvoiceDummy.getCustomerAddress());
                }
                if (salesInvoiceDummy.getDeliveryAddress() != null) {
                    existingSalesInvoiceDummy.setDeliveryAddress(salesInvoiceDummy.getDeliveryAddress());
                }
                if (salesInvoiceDummy.getSubTotal() != null) {
                    existingSalesInvoiceDummy.setSubTotal(salesInvoiceDummy.getSubTotal());
                }
                if (salesInvoiceDummy.getTotalTax() != null) {
                    existingSalesInvoiceDummy.setTotalTax(salesInvoiceDummy.getTotalTax());
                }
                if (salesInvoiceDummy.getTotalDiscount() != null) {
                    existingSalesInvoiceDummy.setTotalDiscount(salesInvoiceDummy.getTotalDiscount());
                }
                if (salesInvoiceDummy.getNetTotal() != null) {
                    existingSalesInvoiceDummy.setNetTotal(salesInvoiceDummy.getNetTotal());
                }
                if (salesInvoiceDummy.getMessage() != null) {
                    existingSalesInvoiceDummy.setMessage(salesInvoiceDummy.getMessage());
                }
                if (salesInvoiceDummy.getLmu() != null) {
                    existingSalesInvoiceDummy.setLmu(salesInvoiceDummy.getLmu());
                }
                if (salesInvoiceDummy.getLmd() != null) {
                    existingSalesInvoiceDummy.setLmd(salesInvoiceDummy.getLmd());
                }
                if (salesInvoiceDummy.getPaidAmount() != null) {
                    existingSalesInvoiceDummy.setPaidAmount(salesInvoiceDummy.getPaidAmount());
                }
                if (salesInvoiceDummy.getAmountOwing() != null) {
                    existingSalesInvoiceDummy.setAmountOwing(salesInvoiceDummy.getAmountOwing());
                }
                if (salesInvoiceDummy.getIsActive() != null) {
                    existingSalesInvoiceDummy.setIsActive(salesInvoiceDummy.getIsActive());
                }
                if (salesInvoiceDummy.getLocationID() != null) {
                    existingSalesInvoiceDummy.setLocationID(salesInvoiceDummy.getLocationID());
                }
                if (salesInvoiceDummy.getLocationCode() != null) {
                    existingSalesInvoiceDummy.setLocationCode(salesInvoiceDummy.getLocationCode());
                }
                if (salesInvoiceDummy.getReferenceCode() != null) {
                    existingSalesInvoiceDummy.setReferenceCode(salesInvoiceDummy.getReferenceCode());
                }
                if (salesInvoiceDummy.getCreatedById() != null) {
                    existingSalesInvoiceDummy.setCreatedById(salesInvoiceDummy.getCreatedById());
                }
                if (salesInvoiceDummy.getCreatedByName() != null) {
                    existingSalesInvoiceDummy.setCreatedByName(salesInvoiceDummy.getCreatedByName());
                }
                if (salesInvoiceDummy.getAutoCareCharges() != null) {
                    existingSalesInvoiceDummy.setAutoCareCharges(salesInvoiceDummy.getAutoCareCharges());
                }
                if (salesInvoiceDummy.getAutoCareJobId() != null) {
                    existingSalesInvoiceDummy.setAutoCareJobId(salesInvoiceDummy.getAutoCareJobId());
                }
                if (salesInvoiceDummy.getVehicleNo() != null) {
                    existingSalesInvoiceDummy.setVehicleNo(salesInvoiceDummy.getVehicleNo());
                }
                if (salesInvoiceDummy.getNbtAmount() != null) {
                    existingSalesInvoiceDummy.setNbtAmount(salesInvoiceDummy.getNbtAmount());
                }
                if (salesInvoiceDummy.getVatAmount() != null) {
                    existingSalesInvoiceDummy.setVatAmount(salesInvoiceDummy.getVatAmount());
                }
                if (salesInvoiceDummy.getDummyCommission() != null) {
                    existingSalesInvoiceDummy.setDummyCommission(salesInvoiceDummy.getDummyCommission());
                }
                if (salesInvoiceDummy.getCommissionPaidDate() != null) {
                    existingSalesInvoiceDummy.setCommissionPaidDate(salesInvoiceDummy.getCommissionPaidDate());
                }
                if (salesInvoiceDummy.getPaidCommission() != null) {
                    existingSalesInvoiceDummy.setPaidCommission(salesInvoiceDummy.getPaidCommission());
                }
                if (salesInvoiceDummy.getPaidBy() != null) {
                    existingSalesInvoiceDummy.setPaidBy(salesInvoiceDummy.getPaidBy());
                }
                if (salesInvoiceDummy.getOriginalInvoiceCode() != null) {
                    existingSalesInvoiceDummy.setOriginalInvoiceCode(salesInvoiceDummy.getOriginalInvoiceCode());
                }

                return existingSalesInvoiceDummy;
            })
            .map(salesInvoiceDummyRepository::save);
    }

    /**
     * Get one salesInvoiceDummy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesInvoiceDummy> findOne(Integer id) {
        LOG.debug("Request to get SalesInvoiceDummy : {}", id);
        return salesInvoiceDummyRepository.findById(id);
    }

    /**
     * Delete the salesInvoiceDummy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete SalesInvoiceDummy : {}", id);
        salesInvoiceDummyRepository.deleteById(id);
    }
}
