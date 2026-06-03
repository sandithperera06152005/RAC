package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.VoucherLines;
import com.heavenscode.rac.repository.VoucherLinesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.VoucherLines}.
 */
@Service
@Transactional
public class VoucherLinesService {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherLinesService.class);

    private final VoucherLinesRepository voucherLinesRepository;

    public VoucherLinesService(VoucherLinesRepository voucherLinesRepository) {
        this.voucherLinesRepository = voucherLinesRepository;
    }

    /**
     * Save a voucherLines.
     *
     * @param voucherLines the entity to save.
     * @return the persisted entity.
     */
    public VoucherLines save(VoucherLines voucherLines) {
        LOG.debug("Request to save VoucherLines : {}", voucherLines);
        return voucherLinesRepository.save(voucherLines);
    }

    /**
     * Update a voucherLines.
     *
     * @param voucherLines the entity to save.
     * @return the persisted entity.
     */
    public VoucherLines update(VoucherLines voucherLines) {
        LOG.debug("Request to update VoucherLines : {}", voucherLines);
        return voucherLinesRepository.save(voucherLines);
    }

    /**
     * Partially update a voucherLines.
     *
     * @param voucherLines the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VoucherLines> partialUpdate(VoucherLines voucherLines) {
        LOG.debug("Request to partially update VoucherLines : {}", voucherLines);

        return voucherLinesRepository
            .findById(voucherLines.getId())
            .map(existingVoucherLines -> {
                if (voucherLines.getLineID() != null) {
                    existingVoucherLines.setLineID(voucherLines.getLineID());
                }
                if (voucherLines.getGrnCode() != null) {
                    existingVoucherLines.setGrnCode(voucherLines.getGrnCode());
                }
                if (voucherLines.getGrnType() != null) {
                    existingVoucherLines.setGrnType(voucherLines.getGrnType());
                }
                if (voucherLines.getOriginalAmount() != null) {
                    existingVoucherLines.setOriginalAmount(voucherLines.getOriginalAmount());
                }
                if (voucherLines.getAmountOwing() != null) {
                    existingVoucherLines.setAmountOwing(voucherLines.getAmountOwing());
                }
                if (voucherLines.getDiscountAvailable() != null) {
                    existingVoucherLines.setDiscountAvailable(voucherLines.getDiscountAvailable());
                }
                if (voucherLines.getDiscountTaken() != null) {
                    existingVoucherLines.setDiscountTaken(voucherLines.getDiscountTaken());
                }
                if (voucherLines.getAmountReceived() != null) {
                    existingVoucherLines.setAmountReceived(voucherLines.getAmountReceived());
                }
                if (voucherLines.getLmu() != null) {
                    existingVoucherLines.setLmu(voucherLines.getLmu());
                }
                if (voucherLines.getLmd() != null) {
                    existingVoucherLines.setLmd(voucherLines.getLmd());
                }
                if (voucherLines.getAccountId() != null) {
                    existingVoucherLines.setAccountId(voucherLines.getAccountId());
                }

                return existingVoucherLines;
            })
            .map(voucherLinesRepository::save);
    }

    /**
     * Get one voucherLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VoucherLines> findOne(Long id) {
        LOG.debug("Request to get VoucherLines : {}", id);
        return voucherLinesRepository.findById(id);
    }

    /**
     * Delete the voucherLines by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete VoucherLines : {}", id);
        voucherLinesRepository.deleteById(id);
    }
}
