package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge}.
 */
@Service
@Transactional
public class SaleInvoiceCommonServiceChargeService {

    private static final Logger LOG = LoggerFactory.getLogger(SaleInvoiceCommonServiceChargeService.class);

    private final SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository;

    public SaleInvoiceCommonServiceChargeService(SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository) {
        this.saleInvoiceCommonServiceChargeRepository = saleInvoiceCommonServiceChargeRepository;
    }

    /**
     * Save a saleInvoiceCommonServiceCharge.
     *
     * @param saleInvoiceCommonServiceCharge the entity to save.
     * @return the persisted entity.
     */
    public SaleInvoiceCommonServiceCharge save(SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge) {
        LOG.debug("Request to save SaleInvoiceCommonServiceCharge : {}", saleInvoiceCommonServiceCharge);
        return saleInvoiceCommonServiceChargeRepository.save(saleInvoiceCommonServiceCharge);
    }

    /**
     * Update a saleInvoiceCommonServiceCharge.
     *
     * @param saleInvoiceCommonServiceCharge the entity to save.
     * @return the persisted entity.
     */
    public SaleInvoiceCommonServiceCharge update(SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge) {
        LOG.debug("Request to update SaleInvoiceCommonServiceCharge : {}", saleInvoiceCommonServiceCharge);
        return saleInvoiceCommonServiceChargeRepository.save(saleInvoiceCommonServiceCharge);
    }

    /**
     * Partially update a saleInvoiceCommonServiceCharge.
     *
     * @param saleInvoiceCommonServiceCharge the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SaleInvoiceCommonServiceCharge> partialUpdate(SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge) {
        LOG.debug("Request to partially update SaleInvoiceCommonServiceCharge : {}", saleInvoiceCommonServiceCharge);

        return saleInvoiceCommonServiceChargeRepository
            .findById(saleInvoiceCommonServiceCharge.getId())
            .map(existingSaleInvoiceCommonServiceCharge -> {
                if (saleInvoiceCommonServiceCharge.getInvoiceId() != null) {
                    existingSaleInvoiceCommonServiceCharge.setInvoiceId(saleInvoiceCommonServiceCharge.getInvoiceId());
                }
                if (saleInvoiceCommonServiceCharge.getLineId() != null) {
                    existingSaleInvoiceCommonServiceCharge.setLineId(saleInvoiceCommonServiceCharge.getLineId());
                }
                if (saleInvoiceCommonServiceCharge.getOptionId() != null) {
                    existingSaleInvoiceCommonServiceCharge.setOptionId(saleInvoiceCommonServiceCharge.getOptionId());
                }
                if (saleInvoiceCommonServiceCharge.getMainId() != null) {
                    existingSaleInvoiceCommonServiceCharge.setMainId(saleInvoiceCommonServiceCharge.getMainId());
                }
                if (saleInvoiceCommonServiceCharge.getCode() != null) {
                    existingSaleInvoiceCommonServiceCharge.setCode(saleInvoiceCommonServiceCharge.getCode());
                }
                if (saleInvoiceCommonServiceCharge.getName() != null) {
                    existingSaleInvoiceCommonServiceCharge.setName(saleInvoiceCommonServiceCharge.getName());
                }
                if (saleInvoiceCommonServiceCharge.getDescription() != null) {
                    existingSaleInvoiceCommonServiceCharge.setDescription(saleInvoiceCommonServiceCharge.getDescription());
                }
                if (saleInvoiceCommonServiceCharge.getValue() != null) {
                    existingSaleInvoiceCommonServiceCharge.setValue(saleInvoiceCommonServiceCharge.getValue());
                }
                if (saleInvoiceCommonServiceCharge.getDiscount() != null) {
                    existingSaleInvoiceCommonServiceCharge.setDiscount(saleInvoiceCommonServiceCharge.getDiscount());
                }
                if (saleInvoiceCommonServiceCharge.getServicePrice() != null) {
                    existingSaleInvoiceCommonServiceCharge.setServicePrice(saleInvoiceCommonServiceCharge.getServicePrice());
                }

                return existingSaleInvoiceCommonServiceCharge;
            })
            .map(saleInvoiceCommonServiceChargeRepository::save);
    }

    /**
     * Get one saleInvoiceCommonServiceCharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SaleInvoiceCommonServiceCharge> findOne(Long id) {
        LOG.debug("Request to get SaleInvoiceCommonServiceCharge : {}", id);
        return saleInvoiceCommonServiceChargeRepository.findById(id);
    }

    /**
     * Delete the saleInvoiceCommonServiceCharge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SaleInvoiceCommonServiceCharge : {}", id);
        saleInvoiceCommonServiceChargeRepository.deleteById(id);
    }
}
