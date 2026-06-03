package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummy;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeDummyRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummy}.
 */
@Service
@Transactional
public class SaleInvoiceCommonServiceChargeDummyService {

    private static final Logger LOG = LoggerFactory.getLogger(SaleInvoiceCommonServiceChargeDummyService.class);

    private final SaleInvoiceCommonServiceChargeDummyRepository saleInvoiceCommonServiceChargeDummyRepository;

    public SaleInvoiceCommonServiceChargeDummyService(
        SaleInvoiceCommonServiceChargeDummyRepository saleInvoiceCommonServiceChargeDummyRepository
    ) {
        this.saleInvoiceCommonServiceChargeDummyRepository = saleInvoiceCommonServiceChargeDummyRepository;
    }

    /**
     * Save a saleInvoiceCommonServiceChargeDummy.
     *
     * @param saleInvoiceCommonServiceChargeDummy the entity to save.
     * @return the persisted entity.
     */
    public SaleInvoiceCommonServiceChargeDummy save(SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy) {
        LOG.debug("Request to save SaleInvoiceCommonServiceChargeDummy : {}", saleInvoiceCommonServiceChargeDummy);
        return saleInvoiceCommonServiceChargeDummyRepository.save(saleInvoiceCommonServiceChargeDummy);
    }

    /**
     * Update a saleInvoiceCommonServiceChargeDummy.
     *
     * @param saleInvoiceCommonServiceChargeDummy the entity to save.
     * @return the persisted entity.
     */
    public SaleInvoiceCommonServiceChargeDummy update(SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy) {
        LOG.debug("Request to update SaleInvoiceCommonServiceChargeDummy : {}", saleInvoiceCommonServiceChargeDummy);
        return saleInvoiceCommonServiceChargeDummyRepository.save(saleInvoiceCommonServiceChargeDummy);
    }

    /**
     * Partially update a saleInvoiceCommonServiceChargeDummy.
     *
     * @param saleInvoiceCommonServiceChargeDummy the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaleInvoiceCommonServiceChargeDummy> partialUpdate(
        SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy
    ) {
        LOG.debug("Request to partially update SaleInvoiceCommonServiceChargeDummy : {}", saleInvoiceCommonServiceChargeDummy);

        return saleInvoiceCommonServiceChargeDummyRepository
            .findById(saleInvoiceCommonServiceChargeDummy.getId())
            .map(existingSaleInvoiceCommonServiceChargeDummy -> {
                if (saleInvoiceCommonServiceChargeDummy.getInvoiceid() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setInvoiceid(saleInvoiceCommonServiceChargeDummy.getInvoiceid());
                }
                if (saleInvoiceCommonServiceChargeDummy.getLineid() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setLineid(saleInvoiceCommonServiceChargeDummy.getLineid());
                }
                if (saleInvoiceCommonServiceChargeDummy.getOptionid() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setOptionid(saleInvoiceCommonServiceChargeDummy.getOptionid());
                }
                if (saleInvoiceCommonServiceChargeDummy.getMainid() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setMainid(saleInvoiceCommonServiceChargeDummy.getMainid());
                }
                if (saleInvoiceCommonServiceChargeDummy.getCode() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setCode(saleInvoiceCommonServiceChargeDummy.getCode());
                }
                if (saleInvoiceCommonServiceChargeDummy.getName() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setName(saleInvoiceCommonServiceChargeDummy.getName());
                }
                if (saleInvoiceCommonServiceChargeDummy.getDescription() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setDescription(saleInvoiceCommonServiceChargeDummy.getDescription());
                }
                if (saleInvoiceCommonServiceChargeDummy.getValue() != null) {
                    existingSaleInvoiceCommonServiceChargeDummy.setValue(saleInvoiceCommonServiceChargeDummy.getValue());
                }

                return existingSaleInvoiceCommonServiceChargeDummy;
            })
            .map(saleInvoiceCommonServiceChargeDummyRepository::save);
    }

    /**
     * Get one saleInvoiceCommonServiceChargeDummy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaleInvoiceCommonServiceChargeDummy> findOne(Long id) {
        LOG.debug("Request to get SaleInvoiceCommonServiceChargeDummy : {}", id);
        return saleInvoiceCommonServiceChargeDummyRepository.findById(id);
    }

    /**
     * Delete the saleInvoiceCommonServiceChargeDummy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SaleInvoiceCommonServiceChargeDummy : {}", id);
        saleInvoiceCommonServiceChargeDummyRepository.deleteById(id);
    }
}
