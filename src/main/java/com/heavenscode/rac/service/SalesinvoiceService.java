package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Salesinvoice;
import com.heavenscode.rac.repository.SalesinvoiceRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Salesinvoice}.
 */
@Service
@Transactional
public class SalesinvoiceService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesinvoiceService.class);

    private final SalesinvoiceRepository salesinvoiceRepository;

    public SalesinvoiceService(SalesinvoiceRepository salesinvoiceRepository) {
        this.salesinvoiceRepository = salesinvoiceRepository;
    }

    /**
     * Save a salesinvoice.
     *
     * @param salesinvoice the entity to save.
     * @return the persisted entity.
     */
    public Salesinvoice save(Salesinvoice salesinvoice) {
        LOG.debug("Request to save Salesinvoice : {}", salesinvoice);
        return salesinvoiceRepository.save(salesinvoice);
    }

    /**
     * Update a salesinvoice.
     *
     * @param salesinvoice the entity to save.
     * @return the persisted entity.
     */
    public Salesinvoice update(Salesinvoice salesinvoice) {
        LOG.debug("Request to update Salesinvoice : {}", salesinvoice);
        return salesinvoiceRepository.save(salesinvoice);
    }

    /**
     * Partially update a salesinvoice.
     *
     * @param salesinvoice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Salesinvoice> partialUpdate(Salesinvoice salesinvoice) {
        LOG.debug("Request to partially update Salesinvoice : {}", salesinvoice);

        return salesinvoiceRepository
            .findById(salesinvoice.getId())
            .map(existingSalesinvoice -> {
                if (salesinvoice.getCode() != null) {
                    existingSalesinvoice.setCode(salesinvoice.getCode());
                }
                if (salesinvoice.getInvoicedate() != null) {
                    existingSalesinvoice.setInvoicedate(salesinvoice.getInvoicedate());
                }
                if (salesinvoice.getCreateddate() != null) {
                    existingSalesinvoice.setCreateddate(salesinvoice.getCreateddate());
                }
                if (salesinvoice.getQuoteid() != null) {
                    existingSalesinvoice.setQuoteid(salesinvoice.getQuoteid());
                }
                if (salesinvoice.getOrderid() != null) {
                    existingSalesinvoice.setOrderid(salesinvoice.getOrderid());
                }
                if (salesinvoice.getDelieverydate() != null) {
                    existingSalesinvoice.setDelieverydate(salesinvoice.getDelieverydate());
                }
                if (salesinvoice.getSalesrepid() != null) {
                    existingSalesinvoice.setSalesrepid(salesinvoice.getSalesrepid());
                }
                if (salesinvoice.getSalesrepname() != null) {
                    existingSalesinvoice.setSalesrepname(salesinvoice.getSalesrepname());
                }
                if (salesinvoice.getDelieverfrom() != null) {
                    existingSalesinvoice.setDelieverfrom(salesinvoice.getDelieverfrom());
                }
                if (salesinvoice.getCustomerid() != null) {
                    existingSalesinvoice.setCustomerid(salesinvoice.getCustomerid());
                }
                if (salesinvoice.getCustomername() != null) {
                    existingSalesinvoice.setCustomername(salesinvoice.getCustomername());
                }
                if (salesinvoice.getCustomeraddress() != null) {
                    existingSalesinvoice.setCustomeraddress(salesinvoice.getCustomeraddress());
                }
                if (salesinvoice.getDeliveryaddress() != null) {
                    existingSalesinvoice.setDeliveryaddress(salesinvoice.getDeliveryaddress());
                }
                if (salesinvoice.getSubtotal() != null) {
                    existingSalesinvoice.setSubtotal(salesinvoice.getSubtotal());
                }
                if (salesinvoice.getTotaltax() != null) {
                    existingSalesinvoice.setTotaltax(salesinvoice.getTotaltax());
                }
                if (salesinvoice.getTotaldiscount() != null) {
                    existingSalesinvoice.setTotaldiscount(salesinvoice.getTotaldiscount());
                }
                if (salesinvoice.getNettotal() != null) {
                    existingSalesinvoice.setNettotal(salesinvoice.getNettotal());
                }
                if (salesinvoice.getMessage() != null) {
                    existingSalesinvoice.setMessage(salesinvoice.getMessage());
                }
                if (salesinvoice.getLmu() != null) {
                    existingSalesinvoice.setLmu(salesinvoice.getLmu());
                }
                if (salesinvoice.getLmd() != null) {
                    existingSalesinvoice.setLmd(salesinvoice.getLmd());
                }
                if (salesinvoice.getPaidamount() != null) {
                    existingSalesinvoice.setPaidamount(salesinvoice.getPaidamount());
                }
                if (salesinvoice.getAmountowing() != null) {
                    existingSalesinvoice.setAmountowing(salesinvoice.getAmountowing());
                }
                if (salesinvoice.getIsactive() != null) {
                    existingSalesinvoice.setIsactive(salesinvoice.getIsactive());
                }
                if (salesinvoice.getLocationid() != null) {
                    existingSalesinvoice.setLocationid(salesinvoice.getLocationid());
                }
                if (salesinvoice.getLocationcode() != null) {
                    existingSalesinvoice.setLocationcode(salesinvoice.getLocationcode());
                }
                if (salesinvoice.getReferencecode() != null) {
                    existingSalesinvoice.setReferencecode(salesinvoice.getReferencecode());
                }
                if (salesinvoice.getCreatedbyid() != null) {
                    existingSalesinvoice.setCreatedbyid(salesinvoice.getCreatedbyid());
                }
                if (salesinvoice.getCreatedbyname() != null) {
                    existingSalesinvoice.setCreatedbyname(salesinvoice.getCreatedbyname());
                }
                if (salesinvoice.getAutocarecharges() != null) {
                    existingSalesinvoice.setAutocarecharges(salesinvoice.getAutocarecharges());
                }
                if (salesinvoice.getAutocarejobid() != null) {
                    existingSalesinvoice.setAutocarejobid(salesinvoice.getAutocarejobid());
                }
                if (salesinvoice.getVehicleno() != null) {
                    existingSalesinvoice.setVehicleno(salesinvoice.getVehicleno());
                }
                if (salesinvoice.getNextmeter() != null) {
                    existingSalesinvoice.setNextmeter(salesinvoice.getNextmeter());
                }
                if (salesinvoice.getCurrentmeter() != null) {
                    existingSalesinvoice.setCurrentmeter(salesinvoice.getCurrentmeter());
                }
                if (salesinvoice.getRemarks() != null) {
                    existingSalesinvoice.setRemarks(salesinvoice.getRemarks());
                }
                if (salesinvoice.getHasdummybill() != null) {
                    existingSalesinvoice.setHasdummybill(salesinvoice.getHasdummybill());
                }
                if (salesinvoice.getDummybillid() != null) {
                    existingSalesinvoice.setDummybillid(salesinvoice.getDummybillid());
                }
                if (salesinvoice.getDummybillamount() != null) {
                    existingSalesinvoice.setDummybillamount(salesinvoice.getDummybillamount());
                }
                if (salesinvoice.getDummycommision() != null) {
                    existingSalesinvoice.setDummycommision(salesinvoice.getDummycommision());
                }
                if (salesinvoice.getIsserviceinvoice() != null) {
                    existingSalesinvoice.setIsserviceinvoice(salesinvoice.getIsserviceinvoice());
                }
                if (salesinvoice.getNbtamount() != null) {
                    existingSalesinvoice.setNbtamount(salesinvoice.getNbtamount());
                }
                if (salesinvoice.getVatamount() != null) {
                    existingSalesinvoice.setVatamount(salesinvoice.getVatamount());
                }
                if (salesinvoice.getAutocarecompanyid() != null) {
                    existingSalesinvoice.setAutocarecompanyid(salesinvoice.getAutocarecompanyid());
                }
                if (salesinvoice.getIscompanyinvoice() != null) {
                    existingSalesinvoice.setIscompanyinvoice(salesinvoice.getIscompanyinvoice());
                }
                if (salesinvoice.getInvcanceldate() != null) {
                    existingSalesinvoice.setInvcanceldate(salesinvoice.getInvcanceldate());
                }
                if (salesinvoice.getInvcancelby() != null) {
                    existingSalesinvoice.setInvcancelby(salesinvoice.getInvcancelby());
                }
                if (salesinvoice.getIsvatinvoice() != null) {
                    existingSalesinvoice.setIsvatinvoice(salesinvoice.getIsvatinvoice());
                }
                if (salesinvoice.getPaymenttype() != null) {
                    existingSalesinvoice.setPaymenttype(salesinvoice.getPaymenttype());
                }
                if (salesinvoice.getPendingamount() != null) {
                    existingSalesinvoice.setPendingamount(salesinvoice.getPendingamount());
                }
                if (salesinvoice.getAdvancepayment() != null) {
                    existingSalesinvoice.setAdvancepayment(salesinvoice.getAdvancepayment());
                }
                if (salesinvoice.getDiscountcode() != null) {
                    existingSalesinvoice.setDiscountcode(salesinvoice.getDiscountcode());
                }

                return existingSalesinvoice;
            })
            .map(salesinvoiceRepository::save);
    }

    /**
     * Get one salesinvoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Salesinvoice> findOne(Long id) {
        LOG.debug("Request to get Salesinvoice : {}", id);
        return salesinvoiceRepository.findById(id);
    }

    /**
     * Delete the salesinvoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Salesinvoice : {}", id);
        salesinvoiceRepository.deleteById(id);
    }
}
