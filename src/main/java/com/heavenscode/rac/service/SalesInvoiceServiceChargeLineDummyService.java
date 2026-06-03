package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummy;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineDummyRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummy}.
 */
@Service
@Transactional
public class SalesInvoiceServiceChargeLineDummyService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceServiceChargeLineDummyService.class);

    private final SalesInvoiceServiceChargeLineDummyRepository salesInvoiceServiceChargeLineDummyRepository;

    public SalesInvoiceServiceChargeLineDummyService(
        SalesInvoiceServiceChargeLineDummyRepository salesInvoiceServiceChargeLineDummyRepository
    ) {
        this.salesInvoiceServiceChargeLineDummyRepository = salesInvoiceServiceChargeLineDummyRepository;
    }

    /**
     * Save a salesInvoiceServiceChargeLineDummy.
     *
     * @param salesInvoiceServiceChargeLineDummy the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceServiceChargeLineDummy save(SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy) {
        LOG.debug("Request to save SalesInvoiceServiceChargeLineDummy : {}", salesInvoiceServiceChargeLineDummy);
        return salesInvoiceServiceChargeLineDummyRepository.save(salesInvoiceServiceChargeLineDummy);
    }

    /**
     * Update a salesInvoiceServiceChargeLineDummy.
     *
     * @param salesInvoiceServiceChargeLineDummy the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceServiceChargeLineDummy update(SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy) {
        LOG.debug("Request to update SalesInvoiceServiceChargeLineDummy : {}", salesInvoiceServiceChargeLineDummy);
        return salesInvoiceServiceChargeLineDummyRepository.save(salesInvoiceServiceChargeLineDummy);
    }

    /**
     * Partially update a salesInvoiceServiceChargeLineDummy.
     *
     * @param salesInvoiceServiceChargeLineDummy the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesInvoiceServiceChargeLineDummy> partialUpdate(
        SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy
    ) {
        LOG.debug("Request to partially update SalesInvoiceServiceChargeLineDummy : {}", salesInvoiceServiceChargeLineDummy);

        return salesInvoiceServiceChargeLineDummyRepository
            .findById(salesInvoiceServiceChargeLineDummy.getId())
            .map(existingSalesInvoiceServiceChargeLineDummy -> {
                if (salesInvoiceServiceChargeLineDummy.getInvoiceId() != null) {
                    existingSalesInvoiceServiceChargeLineDummy.setInvoiceId(salesInvoiceServiceChargeLineDummy.getInvoiceId());
                }
                if (salesInvoiceServiceChargeLineDummy.getLineId() != null) {
                    existingSalesInvoiceServiceChargeLineDummy.setLineId(salesInvoiceServiceChargeLineDummy.getLineId());
                }
                if (salesInvoiceServiceChargeLineDummy.getOptionId() != null) {
                    existingSalesInvoiceServiceChargeLineDummy.setOptionId(salesInvoiceServiceChargeLineDummy.getOptionId());
                }
                if (salesInvoiceServiceChargeLineDummy.getServiceName() != null) {
                    existingSalesInvoiceServiceChargeLineDummy.setServiceName(salesInvoiceServiceChargeLineDummy.getServiceName());
                }
                if (salesInvoiceServiceChargeLineDummy.getServiceDescription() != null) {
                    existingSalesInvoiceServiceChargeLineDummy.setServiceDescription(
                        salesInvoiceServiceChargeLineDummy.getServiceDescription()
                    );
                }
                if (salesInvoiceServiceChargeLineDummy.getValue() != null) {
                    existingSalesInvoiceServiceChargeLineDummy.setValue(salesInvoiceServiceChargeLineDummy.getValue());
                }
                if (salesInvoiceServiceChargeLineDummy.getIsCustomerService() != null) {
                    existingSalesInvoiceServiceChargeLineDummy.setIsCustomerService(
                        salesInvoiceServiceChargeLineDummy.getIsCustomerService()
                    );
                }

                return existingSalesInvoiceServiceChargeLineDummy;
            })
            .map(salesInvoiceServiceChargeLineDummyRepository::save);
    }

    /**
     * Get one salesInvoiceServiceChargeLineDummy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesInvoiceServiceChargeLineDummy> findOne(Long id) {
        LOG.debug("Request to get SalesInvoiceServiceChargeLineDummy : {}", id);
        return salesInvoiceServiceChargeLineDummyRepository.findById(id);
    }

    /**
     * Delete the salesInvoiceServiceChargeLineDummy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SalesInvoiceServiceChargeLineDummy : {}", id);
        salesInvoiceServiceChargeLineDummyRepository.deleteById(id);
    }
}
