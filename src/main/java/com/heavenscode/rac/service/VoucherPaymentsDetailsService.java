package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.VoucherPaymentsDetails;
import com.heavenscode.rac.repository.VoucherPaymentsDetailsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.VoucherPaymentsDetails}.
 */
@Service
@Transactional
public class VoucherPaymentsDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherPaymentsDetailsService.class);

    private final VoucherPaymentsDetailsRepository voucherPaymentsDetailsRepository;

    public VoucherPaymentsDetailsService(VoucherPaymentsDetailsRepository voucherPaymentsDetailsRepository) {
        this.voucherPaymentsDetailsRepository = voucherPaymentsDetailsRepository;
    }

    /**
     * Save a voucherPaymentsDetails.
     *
     * @param voucherPaymentsDetails the entity to save.
     * @return the persisted entity.
     */
    public VoucherPaymentsDetails save(VoucherPaymentsDetails voucherPaymentsDetails) {
        LOG.debug("Request to save VoucherPaymentsDetails : {}", voucherPaymentsDetails);
        return voucherPaymentsDetailsRepository.save(voucherPaymentsDetails);
    }

    /**
     * Update a voucherPaymentsDetails.
     *
     * @param voucherPaymentsDetails the entity to save.
     * @return the persisted entity.
     */
    public VoucherPaymentsDetails update(VoucherPaymentsDetails voucherPaymentsDetails) {
        LOG.debug("Request to update VoucherPaymentsDetails : {}", voucherPaymentsDetails);
        return voucherPaymentsDetailsRepository.save(voucherPaymentsDetails);
    }

    /**
     * Partially update a voucherPaymentsDetails.
     *
     * @param voucherPaymentsDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VoucherPaymentsDetails> partialUpdate(VoucherPaymentsDetails voucherPaymentsDetails) {
        LOG.debug("Request to partially update VoucherPaymentsDetails : {}", voucherPaymentsDetails);

        return voucherPaymentsDetailsRepository
            .findById(voucherPaymentsDetails.getId())
            .map(existingVoucherPaymentsDetails -> {
                if (voucherPaymentsDetails.getLineID() != null) {
                    existingVoucherPaymentsDetails.setLineID(voucherPaymentsDetails.getLineID());
                }
                if (voucherPaymentsDetails.getPaymentAmount() != null) {
                    existingVoucherPaymentsDetails.setPaymentAmount(voucherPaymentsDetails.getPaymentAmount());
                }
                if (voucherPaymentsDetails.getTotalVoucherAmount() != null) {
                    existingVoucherPaymentsDetails.setTotalVoucherAmount(voucherPaymentsDetails.getTotalVoucherAmount());
                }
                if (voucherPaymentsDetails.getCheckqueAmount() != null) {
                    existingVoucherPaymentsDetails.setCheckqueAmount(voucherPaymentsDetails.getCheckqueAmount());
                }
                if (voucherPaymentsDetails.getCheckqueNo() != null) {
                    existingVoucherPaymentsDetails.setCheckqueNo(voucherPaymentsDetails.getCheckqueNo());
                }
                if (voucherPaymentsDetails.getCheckqueDate() != null) {
                    existingVoucherPaymentsDetails.setCheckqueDate(voucherPaymentsDetails.getCheckqueDate());
                }
                if (voucherPaymentsDetails.getCheckqueExpireDate() != null) {
                    existingVoucherPaymentsDetails.setCheckqueExpireDate(voucherPaymentsDetails.getCheckqueExpireDate());
                }
                if (voucherPaymentsDetails.getBankName() != null) {
                    existingVoucherPaymentsDetails.setBankName(voucherPaymentsDetails.getBankName());
                }
                if (voucherPaymentsDetails.getBankID() != null) {
                    existingVoucherPaymentsDetails.setBankID(voucherPaymentsDetails.getBankID());
                }
                if (voucherPaymentsDetails.getCreditCardNo() != null) {
                    existingVoucherPaymentsDetails.setCreditCardNo(voucherPaymentsDetails.getCreditCardNo());
                }
                if (voucherPaymentsDetails.getCreditCardAmount() != null) {
                    existingVoucherPaymentsDetails.setCreditCardAmount(voucherPaymentsDetails.getCreditCardAmount());
                }
                if (voucherPaymentsDetails.getReference() != null) {
                    existingVoucherPaymentsDetails.setReference(voucherPaymentsDetails.getReference());
                }
                if (voucherPaymentsDetails.getOtherDetails() != null) {
                    existingVoucherPaymentsDetails.setOtherDetails(voucherPaymentsDetails.getOtherDetails());
                }
                if (voucherPaymentsDetails.getLmu() != null) {
                    existingVoucherPaymentsDetails.setLmu(voucherPaymentsDetails.getLmu());
                }
                if (voucherPaymentsDetails.getLmd() != null) {
                    existingVoucherPaymentsDetails.setLmd(voucherPaymentsDetails.getLmd());
                }
                if (voucherPaymentsDetails.getTermID() != null) {
                    existingVoucherPaymentsDetails.setTermID(voucherPaymentsDetails.getTermID());
                }
                if (voucherPaymentsDetails.getTermName() != null) {
                    existingVoucherPaymentsDetails.setTermName(voucherPaymentsDetails.getTermName());
                }
                if (voucherPaymentsDetails.getAccountNo() != null) {
                    existingVoucherPaymentsDetails.setAccountNo(voucherPaymentsDetails.getAccountNo());
                }
                if (voucherPaymentsDetails.getAccountNumber() != null) {
                    existingVoucherPaymentsDetails.setAccountNumber(voucherPaymentsDetails.getAccountNumber());
                }
                if (voucherPaymentsDetails.getAccountId() != null) {
                    existingVoucherPaymentsDetails.setAccountId(voucherPaymentsDetails.getAccountId());
                }
                if (voucherPaymentsDetails.getAccountCode() != null) {
                    existingVoucherPaymentsDetails.setAccountCode(voucherPaymentsDetails.getAccountCode());
                }
                if (voucherPaymentsDetails.getChequeStatusId() != null) {
                    existingVoucherPaymentsDetails.setChequeStatusId(voucherPaymentsDetails.getChequeStatusId());
                }
                if (voucherPaymentsDetails.getIsDeposit() != null) {
                    existingVoucherPaymentsDetails.setIsDeposit(voucherPaymentsDetails.getIsDeposit());
                }
                if (voucherPaymentsDetails.getDepositedDate() != null) {
                    existingVoucherPaymentsDetails.setDepositedDate(voucherPaymentsDetails.getDepositedDate());
                }
                if (voucherPaymentsDetails.getCompanyBankId() != null) {
                    existingVoucherPaymentsDetails.setCompanyBankId(voucherPaymentsDetails.getCompanyBankId());
                }
                if (voucherPaymentsDetails.getIsBankReconciliation() != null) {
                    existingVoucherPaymentsDetails.setIsBankReconciliation(voucherPaymentsDetails.getIsBankReconciliation());
                }

                return existingVoucherPaymentsDetails;
            })
            .map(voucherPaymentsDetailsRepository::save);
    }

    /**
     * Get one voucherPaymentsDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VoucherPaymentsDetails> findOne(Long id) {
        LOG.debug("Request to get VoucherPaymentsDetails : {}", id);
        return voucherPaymentsDetailsRepository.findById(id);
    }

    /**
     * Delete the voucherPaymentsDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete VoucherPaymentsDetails : {}", id);
        voucherPaymentsDetailsRepository.deleteById(id);
    }
}
