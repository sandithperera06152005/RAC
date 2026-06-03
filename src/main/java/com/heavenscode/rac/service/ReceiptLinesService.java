package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.ReceiptLines;
import com.heavenscode.rac.domain.ReceiptLinesId;
import com.heavenscode.rac.repository.ReceiptLinesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.ReceiptLines}.
 */
@Service
@Transactional
public class ReceiptLinesService {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiptLinesService.class);

    private final ReceiptLinesRepository receiptLinesRepository;

    public ReceiptLinesService(ReceiptLinesRepository receiptLinesRepository) {
        this.receiptLinesRepository = receiptLinesRepository;
    }

    /**
     * Save a receiptLines.
     *
     * @param receiptLines the entity to save.
     * @return the persisted entity.
     */
    public ReceiptLines save(ReceiptLines receiptLines) {
        LOG.debug("Request to save ReceiptLines : {}", receiptLines);
        return receiptLinesRepository.save(receiptLines);
    }

    /**
     * Update a receiptLines.
     *
     * @param receiptLines the entity to save.
     * @return the persisted entity.
     */
    public ReceiptLines update(ReceiptLines receiptLines) {
        LOG.debug("Request to update ReceiptLines : {}", receiptLines);
        return receiptLinesRepository.save(receiptLines);
    }

    /**
     * Partially update a receiptLines.
     *
     * @param receiptLines the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ReceiptLines> partialUpdate(ReceiptLines receiptLines) {
        LOG.debug("Request to partially update ReceiptLines : {}", receiptLines);

        return receiptLinesRepository
            .findById(new ReceiptLinesId(receiptLines.getId(), receiptLines.getLineid()))
            .map(existingReceiptLines -> {
                if (receiptLines.getLineid() != null) {
                    existingReceiptLines.setLineid(receiptLines.getLineid());
                }
                if (receiptLines.getInvoicecode() != null) {
                    existingReceiptLines.setInvoicecode(receiptLines.getInvoicecode());
                }
                if (receiptLines.getInvoicetype() != null) {
                    existingReceiptLines.setInvoicetype(receiptLines.getInvoicetype());
                }
                if (receiptLines.getOriginalamount() != null) {
                    existingReceiptLines.setOriginalamount(receiptLines.getOriginalamount());
                }
                if (receiptLines.getAmountowing() != null) {
                    existingReceiptLines.setAmountowing(receiptLines.getAmountowing());
                }
                if (receiptLines.getDiscountavailable() != null) {
                    existingReceiptLines.setDiscountavailable(receiptLines.getDiscountavailable());
                }
                if (receiptLines.getDiscounttaken() != null) {
                    existingReceiptLines.setDiscounttaken(receiptLines.getDiscounttaken());
                }
                if (receiptLines.getAmountreceived() != null) {
                    existingReceiptLines.setAmountreceived(receiptLines.getAmountreceived());
                }
                if (receiptLines.getLmu() != null) {
                    existingReceiptLines.setLmu(receiptLines.getLmu());
                }
                if (receiptLines.getLmd() != null) {
                    existingReceiptLines.setLmd(receiptLines.getLmd());
                }
                if (receiptLines.getAccountid() != null) {
                    existingReceiptLines.setAccountid(receiptLines.getAccountid());
                }

                return existingReceiptLines;
            })
            .map(receiptLinesRepository::save);
    }

    /**
     * Get one receiptLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReceiptLines> findOne(Long id, Long lineid) {
        LOG.debug("Request to get ReceiptLines : {}, {}", id, lineid);
        return receiptLinesRepository.findById(new ReceiptLinesId(id, lineid));
    }

    /**
     * Delete the receiptLines by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id, Long lineid) {
        LOG.debug("Request to delete ReceiptLines : {}, {}", id, lineid);
        receiptLinesRepository.deleteById(new ReceiptLinesId(id, lineid));
    }
}
