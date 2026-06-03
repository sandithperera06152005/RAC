package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.InvoiceServiceCommon;
import com.heavenscode.rac.repository.InvoiceServiceCommonRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.InvoiceServiceCommon}.
 */
@Service
@Transactional
public class InvoiceServiceCommonService {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceCommonService.class);

    private final InvoiceServiceCommonRepository invoiceServiceCommonRepository;

    public InvoiceServiceCommonService(InvoiceServiceCommonRepository invoiceServiceCommonRepository) {
        this.invoiceServiceCommonRepository = invoiceServiceCommonRepository;
    }

    /**
     * Save a invoiceServiceCommon.
     *
     * @param invoiceServiceCommon the entity to save.
     * @return the persisted entity.
     */
    public InvoiceServiceCommon save(InvoiceServiceCommon invoiceServiceCommon) {
        LOG.debug("Request to save InvoiceServiceCommon : {}", invoiceServiceCommon);
        return invoiceServiceCommonRepository.save(invoiceServiceCommon);
    }

    /**
     * Update a invoiceServiceCommon.
     *
     * @param invoiceServiceCommon the entity to save.
     * @return the persisted entity.
     */
    public InvoiceServiceCommon update(InvoiceServiceCommon invoiceServiceCommon) {
        LOG.debug("Request to update InvoiceServiceCommon : {}", invoiceServiceCommon);
        return invoiceServiceCommonRepository.save(invoiceServiceCommon);
    }

    /**
     * Partially update a invoiceServiceCommon.
     *
     * @param invoiceServiceCommon the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InvoiceServiceCommon> partialUpdate(InvoiceServiceCommon invoiceServiceCommon) {
        LOG.debug("Request to partially update InvoiceServiceCommon : {}", invoiceServiceCommon);

        return invoiceServiceCommonRepository
            .findById(invoiceServiceCommon.getId())
            .map(existingInvoiceServiceCommon -> {
                if (invoiceServiceCommon.getInvoiceId() != null) {
                    existingInvoiceServiceCommon.setInvoiceId(invoiceServiceCommon.getInvoiceId());
                }
                if (invoiceServiceCommon.getLineId() != null) {
                    existingInvoiceServiceCommon.setLineId(invoiceServiceCommon.getLineId());
                }
                if (invoiceServiceCommon.getOptionId() != null) {
                    existingInvoiceServiceCommon.setOptionId(invoiceServiceCommon.getOptionId());
                }
                if (invoiceServiceCommon.getMainId() != null) {
                    existingInvoiceServiceCommon.setMainId(invoiceServiceCommon.getMainId());
                }
                if (invoiceServiceCommon.getCode() != null) {
                    existingInvoiceServiceCommon.setCode(invoiceServiceCommon.getCode());
                }
                if (invoiceServiceCommon.getName() != null) {
                    existingInvoiceServiceCommon.setName(invoiceServiceCommon.getName());
                }
                if (invoiceServiceCommon.getDescription() != null) {
                    existingInvoiceServiceCommon.setDescription(invoiceServiceCommon.getDescription());
                }
                if (invoiceServiceCommon.getValue() != null) {
                    existingInvoiceServiceCommon.setValue(invoiceServiceCommon.getValue());
                }
                if (invoiceServiceCommon.getAddedById() != null) {
                    existingInvoiceServiceCommon.setAddedById(invoiceServiceCommon.getAddedById());
                }
                if (invoiceServiceCommon.getDiscount() != null) {
                    existingInvoiceServiceCommon.setDiscount(invoiceServiceCommon.getDiscount());
                }
                if (invoiceServiceCommon.getServicePrice() != null) {
                    existingInvoiceServiceCommon.setServicePrice(invoiceServiceCommon.getServicePrice());
                }

                return existingInvoiceServiceCommon;
            })
            .map(invoiceServiceCommonRepository::save);
    }

    /**
     * Get one invoiceServiceCommon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceServiceCommon> findOne(Long id) {
        LOG.debug("Request to get InvoiceServiceCommon : {}", id);
        return invoiceServiceCommonRepository.findById(id);
    }

    /**
     * Delete the invoiceServiceCommon by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete InvoiceServiceCommon : {}", id);
        invoiceServiceCommonRepository.deleteById(id);
    }
}
