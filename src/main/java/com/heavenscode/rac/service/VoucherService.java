package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Voucher;
import com.heavenscode.rac.repository.VoucherRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Voucher}.
 */
@Service
@Transactional
public class VoucherService {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /**
     * Save a voucher.
     *
     * @param voucher the entity to save.
     * @return the persisted entity.
     */
    public Voucher save(Voucher voucher) {
        LOG.debug("Request to save Voucher : {}", voucher);
        return voucherRepository.save(voucher);
    }

    /**
     * Update a voucher.
     *
     * @param voucher the entity to save.
     * @return the persisted entity.
     */
    public Voucher update(Voucher voucher) {
        LOG.debug("Request to update Voucher : {}", voucher);
        return voucherRepository.save(voucher);
    }

    /**
     * Partially update a voucher.
     *
     * @param voucher the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Voucher> partialUpdate(Voucher voucher) {
        LOG.debug("Request to partially update Voucher : {}", voucher);

        return voucherRepository
            .findById(voucher.getId())
            .map(existingVoucher -> {
                if (voucher.getCode() != null) {
                    existingVoucher.setCode(voucher.getCode());
                }
                if (voucher.getVoucherDate() != null) {
                    existingVoucher.setVoucherDate(voucher.getVoucherDate());
                }
                if (voucher.getSupplierName() != null) {
                    existingVoucher.setSupplierName(voucher.getSupplierName());
                }
                if (voucher.getSupplierAddress() != null) {
                    existingVoucher.setSupplierAddress(voucher.getSupplierAddress());
                }
                if (voucher.getTotalAmount() != null) {
                    existingVoucher.setTotalAmount(voucher.getTotalAmount());
                }
                if (voucher.getTotalAmountInWord() != null) {
                    existingVoucher.setTotalAmountInWord(voucher.getTotalAmountInWord());
                }
                if (voucher.getComments() != null) {
                    existingVoucher.setComments(voucher.getComments());
                }
                if (voucher.getLmu() != null) {
                    existingVoucher.setLmu(voucher.getLmu());
                }
                if (voucher.getLmd() != null) {
                    existingVoucher.setLmd(voucher.getLmd());
                }
                if (voucher.getTermId() != null) {
                    existingVoucher.setTermId(voucher.getTermId());
                }
                if (voucher.getTerm() != null) {
                    existingVoucher.setTerm(voucher.getTerm());
                }
                if (voucher.getDate() != null) {
                    existingVoucher.setDate(voucher.getDate());
                }
                if (voucher.getAmountPaid() != null) {
                    existingVoucher.setAmountPaid(voucher.getAmountPaid());
                }
                if (voucher.getSupplierID() != null) {
                    existingVoucher.setSupplierID(voucher.getSupplierID());
                }
                if (voucher.getIsActive() != null) {
                    existingVoucher.setIsActive(voucher.getIsActive());
                }
                if (voucher.getCreatedBy() != null) {
                    existingVoucher.setCreatedBy(voucher.getCreatedBy());
                }

                return existingVoucher;
            })
            .map(voucherRepository::save);
    }

    /**
     * Get one voucher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Voucher> findOne(Long id) {
        LOG.debug("Request to get Voucher : {}", id);
        return voucherRepository.findById(id);
    }

    /**
     * Delete the voucher by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Voucher : {}", id);
        voucherRepository.deleteById(id);
    }
}
