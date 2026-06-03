package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Transactions;
import com.heavenscode.rac.repository.TransactionsRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Transactions}.
 */
@Service
@Transactional
public class TransactionsService {

    private final Logger log = LoggerFactory.getLogger(TransactionsService.class);

    private final TransactionsRepository transactionsRepository;

    public TransactionsService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    /**
     * Save a transactions.
     *
     * @param transactions the entity to save.
     * @return the persisted entity.
     */
    public Transactions save(Transactions transactions) {
        log.debug("Request to save Transactions : {}", transactions);
        return transactionsRepository.save(transactions);
    }

    /**
     * Update a transactions.
     *
     * @param transactions the entity to save.
     * @return the persisted entity.
     */
    public Transactions update(Transactions transactions) {
        log.debug("Request to update Transactions : {}", transactions);
        return transactionsRepository.save(transactions);
    }

    /**
     * Partially update a transactions.
     *
     * @param transactions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Transactions> partialUpdate(Transactions transactions) {
        log.debug("Request to partially update Transactions : {}", transactions);

        return transactionsRepository
            .findById(transactions.getId())
            .map(existingTransactions -> {
                if (transactions.getAccountId() != null) {
                    existingTransactions.setAccountId(transactions.getAccountId());
                }
                if (transactions.getAccountCode() != null) {
                    existingTransactions.setAccountCode(transactions.getAccountCode());
                }
                if (transactions.getDebit() != null) {
                    existingTransactions.setDebit(transactions.getDebit());
                }
                if (transactions.getCredit() != null) {
                    existingTransactions.setCredit(transactions.getCredit());
                }
                if (transactions.getDate() != null) {
                    existingTransactions.setDate(transactions.getDate());
                }
                if (transactions.getRefDoc() != null) {
                    existingTransactions.setRefDoc(transactions.getRefDoc());
                }
                if (transactions.getRefId() != null) {
                    existingTransactions.setRefId(transactions.getRefId());
                }
                if (transactions.getSubId() != null) {
                    existingTransactions.setSubId(transactions.getSubId());
                }
                if (transactions.getSource() != null) {
                    existingTransactions.setSource(transactions.getSource());
                }
                if (transactions.getPaymentTermId() != null) {
                    existingTransactions.setPaymentTermId(transactions.getPaymentTermId());
                }
                if (transactions.getPaymentTermName() != null) {
                    existingTransactions.setPaymentTermName(transactions.getPaymentTermName());
                }
                if (transactions.getLmu() != null) {
                    existingTransactions.setLmu(transactions.getLmu());
                }
                if (transactions.getLmd() != null) {
                    existingTransactions.setLmd(transactions.getLmd());
                }

                return existingTransactions;
            })
            .map(transactionsRepository::save);
    }

    /**
     * Get all the transactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Transactions> findAll() {
        log.debug("Request to get all Transactions");
        return transactionsRepository.findAll();
    }

    /**
     * Get one transactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Transactions> findOne(Long id) {
        log.debug("Request to get Transactions : {}", id);
        return transactionsRepository.findById(id);
    }

    /**
     * Delete the transactions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transactions : {}", id);
        transactionsRepository.deleteById(id);
    }
}
