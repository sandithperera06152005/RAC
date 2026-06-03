package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine}.
 */
@Service
@Transactional
public class SalesInvoiceServiceChargeLineService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceServiceChargeLineService.class);

    private final SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository;

    public SalesInvoiceServiceChargeLineService(SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository) {
        this.salesInvoiceServiceChargeLineRepository = salesInvoiceServiceChargeLineRepository;
    }

    /**
     * Save a salesInvoiceServiceChargeLine.
     *
     * @param salesInvoiceServiceChargeLine the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceServiceChargeLine save(SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine) {
        LOG.debug("Request to save SalesInvoiceServiceChargeLine : {}", salesInvoiceServiceChargeLine);
        return salesInvoiceServiceChargeLineRepository.save(salesInvoiceServiceChargeLine);
    }

    /**
     * Update a salesInvoiceServiceChargeLine.
     *
     * @param salesInvoiceServiceChargeLine the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceServiceChargeLine update(SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine) {
        LOG.debug("Request to update SalesInvoiceServiceChargeLine : {}", salesInvoiceServiceChargeLine);
        return salesInvoiceServiceChargeLineRepository.save(salesInvoiceServiceChargeLine);
    }

    /**
     * Partially update a salesInvoiceServiceChargeLine.
     *
     * @param salesInvoiceServiceChargeLine the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesInvoiceServiceChargeLine> partialUpdate(SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine) {
        LOG.debug("Request to partially update SalesInvoiceServiceChargeLine : {}", salesInvoiceServiceChargeLine);

        return salesInvoiceServiceChargeLineRepository
            .findById(salesInvoiceServiceChargeLine.getId())
            .map(existingSalesInvoiceServiceChargeLine -> {
                if (salesInvoiceServiceChargeLine.getInvoiceId() != null) {
                    existingSalesInvoiceServiceChargeLine.setInvoiceId(salesInvoiceServiceChargeLine.getInvoiceId());
                }
                if (salesInvoiceServiceChargeLine.getLineId() != null) {
                    existingSalesInvoiceServiceChargeLine.setLineId(salesInvoiceServiceChargeLine.getLineId());
                }
                if (salesInvoiceServiceChargeLine.getOptionId() != null) {
                    existingSalesInvoiceServiceChargeLine.setOptionId(salesInvoiceServiceChargeLine.getOptionId());
                }
                if (salesInvoiceServiceChargeLine.getServiceName() != null) {
                    existingSalesInvoiceServiceChargeLine.setServiceName(salesInvoiceServiceChargeLine.getServiceName());
                }
                if (salesInvoiceServiceChargeLine.getServiceDescription() != null) {
                    existingSalesInvoiceServiceChargeLine.setServiceDescription(salesInvoiceServiceChargeLine.getServiceDescription());
                }
                if (salesInvoiceServiceChargeLine.getValue() != null) {
                    existingSalesInvoiceServiceChargeLine.setValue(salesInvoiceServiceChargeLine.getValue());
                }
                if (salesInvoiceServiceChargeLine.getIsCustomerService() != null) {
                    existingSalesInvoiceServiceChargeLine.setIsCustomerService(salesInvoiceServiceChargeLine.getIsCustomerService());
                }
                if (salesInvoiceServiceChargeLine.getDiscount() != null) {
                    existingSalesInvoiceServiceChargeLine.setDiscount(salesInvoiceServiceChargeLine.getDiscount());
                }
                if (salesInvoiceServiceChargeLine.getServicePrice() != null) {
                    existingSalesInvoiceServiceChargeLine.setServicePrice(salesInvoiceServiceChargeLine.getServicePrice());
                }

                return existingSalesInvoiceServiceChargeLine;
            })
            .map(salesInvoiceServiceChargeLineRepository::save);
    }

    /**
     * Get one salesInvoiceServiceChargeLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesInvoiceServiceChargeLine> findOne(Long id) {
        LOG.debug("Request to get SalesInvoiceServiceChargeLine : {}", id);
        return salesInvoiceServiceChargeLineRepository.findById(id);
    }

    /**
     * Delete the salesInvoiceServiceChargeLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SalesInvoiceServiceChargeLine : {}", id);
        salesInvoiceServiceChargeLineRepository.deleteById(id);
    }
}
