package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Accounts;
import com.heavenscode.rac.repository.AccountsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Accounts}.
 */
@Service
@Transactional
public class AccountsService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountsService.class);

    private final AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    /**
     * Save a accounts.
     *
     * @param accounts the entity to save.
     * @return the persisted entity.
     */
    public Accounts save(Accounts accounts) {
        LOG.debug("Request to save Accounts : {}", accounts);
        return accountsRepository.save(accounts);
    }

    /**
     * Update a accounts.
     *
     * @param accounts the entity to save.
     * @return the persisted entity.
     */
    public Accounts update(Accounts accounts) {
        LOG.debug("Request to update Accounts : {}", accounts);
        return accountsRepository.save(accounts);
    }

    /**
     * Partially update a accounts.
     *
     * @param accounts the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Accounts> partialUpdate(Accounts accounts) {
        LOG.debug("Request to partially update Accounts : {}", accounts);

        return accountsRepository
            .findById(accounts.getId())
            .map(existingAccounts -> {
                if (accounts.getCode() != null) {
                    existingAccounts.setCode(accounts.getCode());
                }
                if (accounts.getDate() != null) {
                    existingAccounts.setDate(accounts.getDate());
                }
                if (accounts.getName() != null) {
                    existingAccounts.setName(accounts.getName());
                }
                if (accounts.getDescription() != null) {
                    existingAccounts.setDescription(accounts.getDescription());
                }
                if (accounts.getType() != null) {
                    existingAccounts.setType(accounts.getType());
                }
                if (accounts.getParent() != null) {
                    existingAccounts.setParent(accounts.getParent());
                }
                if (accounts.getBalance() != null) {
                    existingAccounts.setBalance(accounts.getBalance());
                }
                if (accounts.getLmu() != null) {
                    existingAccounts.setLmu(accounts.getLmu());
                }
                if (accounts.getLmd() != null) {
                    existingAccounts.setLmd(accounts.getLmd());
                }
                if (accounts.getHasbatches() != null) {
                    existingAccounts.setHasbatches(accounts.getHasbatches());
                }
                if (accounts.getAccountvalue() != null) {
                    existingAccounts.setAccountvalue(accounts.getAccountvalue());
                }
                if (accounts.getAccountlevel() != null) {
                    existingAccounts.setAccountlevel(accounts.getAccountlevel());
                }
                if (accounts.getAccountsnumberingsystem() != null) {
                    existingAccounts.setAccountsnumberingsystem(accounts.getAccountsnumberingsystem());
                }
                if (accounts.getSubparentid() != null) {
                    existingAccounts.setSubparentid(accounts.getSubparentid());
                }
                if (accounts.getCanedit() != null) {
                    existingAccounts.setCanedit(accounts.getCanedit());
                }
                if (accounts.getAmount() != null) {
                    existingAccounts.setAmount(accounts.getAmount());
                }
                if (accounts.getCreditamount() != null) {
                    existingAccounts.setCreditamount(accounts.getCreditamount());
                }
                if (accounts.getDebitamount() != null) {
                    existingAccounts.setDebitamount(accounts.getDebitamount());
                }
                if (accounts.getDebitorcredit() != null) {
                    existingAccounts.setDebitorcredit(accounts.getDebitorcredit());
                }
                if (accounts.getReporttype() != null) {
                    existingAccounts.setReporttype(accounts.getReporttype());
                }

                return existingAccounts;
            })
            .map(accountsRepository::save);
    }

    /**
     * Get one accounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Accounts> findOne(Long id) {
        LOG.debug("Request to get Accounts : {}", id);
        return accountsRepository.findById(id);
    }

    /**
     * Update the balance of an account.
     *
     * @param id the id of the account.
     * @param balance the new balance.
     */
    public void updateBalance(Long id, Float balance) {
        LOG.debug("Request to update balance of Accounts : {}, {}", id, balance);
        accountsRepository.updateBalance(id, balance);
    }

    /**
     * Delete the accounts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Accounts : {}", id);
        accountsRepository.deleteById(id);
    }
}
