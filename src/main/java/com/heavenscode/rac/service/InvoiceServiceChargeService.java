package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.InvoiceServiceCharge;
import com.heavenscode.rac.repository.InvoiceServiceChargeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.InvoiceServiceCharge}.
 */
@Service
@Transactional
public class InvoiceServiceChargeService {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceChargeService.class);

    private final InvoiceServiceChargeRepository invoiceServiceChargeRepository;

    public InvoiceServiceChargeService(InvoiceServiceChargeRepository invoiceServiceChargeRepository) {
        this.invoiceServiceChargeRepository = invoiceServiceChargeRepository;
    }

    /**
     * Save a invoiceServiceCharge.
     *
     * @param invoiceServiceCharge the entity to save.
     * @return the persisted entity.
     */
    public InvoiceServiceCharge save(InvoiceServiceCharge invoiceServiceCharge) {
        LOG.debug("Request to save InvoiceServiceCharge : {}", invoiceServiceCharge);
        return invoiceServiceChargeRepository.save(invoiceServiceCharge);
    }

    /**
     * Update a invoiceServiceCharge.
     *
     * @param invoiceServiceCharge the entity to save.
     * @return the persisted entity.
     */
    public InvoiceServiceCharge update(InvoiceServiceCharge invoiceServiceCharge) {
        LOG.debug("Request to update InvoiceServiceCharge : {}", invoiceServiceCharge);
        return invoiceServiceChargeRepository.save(invoiceServiceCharge);
    }

    /**
     * Partially update a invoiceServiceCharge.
     *
     * @param invoiceServiceCharge the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InvoiceServiceCharge> partialUpdate(InvoiceServiceCharge invoiceServiceCharge) {
        LOG.debug("Request to partially update InvoiceServiceCharge : {}", invoiceServiceCharge);

        return invoiceServiceChargeRepository
            .findById(invoiceServiceCharge.getId())
            .map(existingInvoiceServiceCharge -> {
                if (invoiceServiceCharge.getInvoiceId() != null) {
                    existingInvoiceServiceCharge.setInvoiceId(invoiceServiceCharge.getInvoiceId());
                }
                if (invoiceServiceCharge.getLineId() != null) {
                    existingInvoiceServiceCharge.setLineId(invoiceServiceCharge.getLineId());
                }
                if (invoiceServiceCharge.getOptionId() != null) {
                    existingInvoiceServiceCharge.setOptionId(invoiceServiceCharge.getOptionId());
                }
                if (invoiceServiceCharge.getServiceName() != null) {
                    existingInvoiceServiceCharge.setServiceName(invoiceServiceCharge.getServiceName());
                }
                if (invoiceServiceCharge.getServiceDiscription() != null) {
                    existingInvoiceServiceCharge.setServiceDiscription(invoiceServiceCharge.getServiceDiscription());
                }
                if (invoiceServiceCharge.getValue() != null) {
                    existingInvoiceServiceCharge.setValue(invoiceServiceCharge.getValue());
                }
                if (invoiceServiceCharge.getAddedById() != null) {
                    existingInvoiceServiceCharge.setAddedById(invoiceServiceCharge.getAddedById());
                }
                if (invoiceServiceCharge.getIsCustomerSrvice() != null) {
                    existingInvoiceServiceCharge.setIsCustomerSrvice(invoiceServiceCharge.getIsCustomerSrvice());
                }
                if (invoiceServiceCharge.getDiscount() != null) {
                    existingInvoiceServiceCharge.setDiscount(invoiceServiceCharge.getDiscount());
                }
                if (invoiceServiceCharge.getServicePrice() != null) {
                    existingInvoiceServiceCharge.setServicePrice(invoiceServiceCharge.getServicePrice());
                }

                return existingInvoiceServiceCharge;
            })
            .map(invoiceServiceChargeRepository::save);
    }

    /**
     * Get one invoiceServiceCharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceServiceCharge> findOne(Long id) {
        LOG.debug("Request to get InvoiceServiceCharge : {}", id);
        return invoiceServiceChargeRepository.findById(id);
    }

    /**
     * Delete the invoiceServiceCharge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete InvoiceServiceCharge : {}", id);
        invoiceServiceChargeRepository.deleteById(id);
    }
}
