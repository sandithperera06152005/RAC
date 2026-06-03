package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.SalesInvoiceLinesDummy;
import com.heavenscode.rac.repository.SalesInvoiceLinesDummyRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.SalesInvoiceLinesDummy}.
 */
@Service
@Transactional
public class SalesInvoiceLinesDummyService {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLinesDummyService.class);

    private final SalesInvoiceLinesDummyRepository salesInvoiceLinesDummyRepository;

    public SalesInvoiceLinesDummyService(SalesInvoiceLinesDummyRepository salesInvoiceLinesDummyRepository) {
        this.salesInvoiceLinesDummyRepository = salesInvoiceLinesDummyRepository;
    }

    /**
     * Save a salesInvoiceLinesDummy.
     *
     * @param salesInvoiceLinesDummy the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceLinesDummy save(SalesInvoiceLinesDummy salesInvoiceLinesDummy) {
        LOG.debug("Request to save SalesInvoiceLinesDummy : {}", salesInvoiceLinesDummy);
        return salesInvoiceLinesDummyRepository.save(salesInvoiceLinesDummy);
    }

    /**
     * Update a salesInvoiceLinesDummy.
     *
     * @param salesInvoiceLinesDummy the entity to save.
     * @return the persisted entity.
     */
    public SalesInvoiceLinesDummy update(SalesInvoiceLinesDummy salesInvoiceLinesDummy) {
        LOG.debug("Request to update SalesInvoiceLinesDummy : {}", salesInvoiceLinesDummy);
        return salesInvoiceLinesDummyRepository.save(salesInvoiceLinesDummy);
    }

    /**
     * Partially update a salesInvoiceLinesDummy.
     *
     * @param salesInvoiceLinesDummy the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalesInvoiceLinesDummy> partialUpdate(SalesInvoiceLinesDummy salesInvoiceLinesDummy) {
        LOG.debug("Request to partially update SalesInvoiceLinesDummy : {}", salesInvoiceLinesDummy);

        return salesInvoiceLinesDummyRepository
            .findById(salesInvoiceLinesDummy.getId())
            .map(existingSalesInvoiceLinesDummy -> {
                if (salesInvoiceLinesDummy.getInvoiceId() != null) {
                    existingSalesInvoiceLinesDummy.setInvoiceId(salesInvoiceLinesDummy.getInvoiceId());
                }
                if (salesInvoiceLinesDummy.getLineId() != null) {
                    existingSalesInvoiceLinesDummy.setLineId(salesInvoiceLinesDummy.getLineId());
                }
                if (salesInvoiceLinesDummy.getItemId() != null) {
                    existingSalesInvoiceLinesDummy.setItemId(salesInvoiceLinesDummy.getItemId());
                }
                if (salesInvoiceLinesDummy.getItemCode() != null) {
                    existingSalesInvoiceLinesDummy.setItemCode(salesInvoiceLinesDummy.getItemCode());
                }
                if (salesInvoiceLinesDummy.getItemName() != null) {
                    existingSalesInvoiceLinesDummy.setItemName(salesInvoiceLinesDummy.getItemName());
                }
                if (salesInvoiceLinesDummy.getDescription() != null) {
                    existingSalesInvoiceLinesDummy.setDescription(salesInvoiceLinesDummy.getDescription());
                }
                if (salesInvoiceLinesDummy.getUnitOfMeasurement() != null) {
                    existingSalesInvoiceLinesDummy.setUnitOfMeasurement(salesInvoiceLinesDummy.getUnitOfMeasurement());
                }
                if (salesInvoiceLinesDummy.getQuantity() != null) {
                    existingSalesInvoiceLinesDummy.setQuantity(salesInvoiceLinesDummy.getQuantity());
                }
                if (salesInvoiceLinesDummy.getItemCost() != null) {
                    existingSalesInvoiceLinesDummy.setItemCost(salesInvoiceLinesDummy.getItemCost());
                }
                if (salesInvoiceLinesDummy.getItemPrice() != null) {
                    existingSalesInvoiceLinesDummy.setItemPrice(salesInvoiceLinesDummy.getItemPrice());
                }
                if (salesInvoiceLinesDummy.getDiscount() != null) {
                    existingSalesInvoiceLinesDummy.setDiscount(salesInvoiceLinesDummy.getDiscount());
                }
                if (salesInvoiceLinesDummy.getTax() != null) {
                    existingSalesInvoiceLinesDummy.setTax(salesInvoiceLinesDummy.getTax());
                }
                if (salesInvoiceLinesDummy.getSellingPrice() != null) {
                    existingSalesInvoiceLinesDummy.setSellingPrice(salesInvoiceLinesDummy.getSellingPrice());
                }
                if (salesInvoiceLinesDummy.getLineTotal() != null) {
                    existingSalesInvoiceLinesDummy.setLineTotal(salesInvoiceLinesDummy.getLineTotal());
                }
                if (salesInvoiceLinesDummy.getLmu() != null) {
                    existingSalesInvoiceLinesDummy.setLmu(salesInvoiceLinesDummy.getLmu());
                }
                if (salesInvoiceLinesDummy.getLmd() != null) {
                    existingSalesInvoiceLinesDummy.setLmd(salesInvoiceLinesDummy.getLmd());
                }
                if (salesInvoiceLinesDummy.getNbt() != null) {
                    existingSalesInvoiceLinesDummy.setNbt(salesInvoiceLinesDummy.getNbt());
                }
                if (salesInvoiceLinesDummy.getVat() != null) {
                    existingSalesInvoiceLinesDummy.setVat(salesInvoiceLinesDummy.getVat());
                }

                return existingSalesInvoiceLinesDummy;
            })
            .map(salesInvoiceLinesDummyRepository::save);
    }

    /**
     * Get one salesInvoiceLinesDummy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalesInvoiceLinesDummy> findOne(Long id) {
        LOG.debug("Request to get SalesInvoiceLinesDummy : {}", id);
        return salesInvoiceLinesDummyRepository.findById(id);
    }

    /**
     * Delete the salesInvoiceLinesDummy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SalesInvoiceLinesDummy : {}", id);
        salesInvoiceLinesDummyRepository.deleteById(id);
    }
}
