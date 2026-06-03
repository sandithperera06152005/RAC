package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Accounts;
import com.heavenscode.rac.repository.AccountsRepository;
import com.heavenscode.rac.service.criteria.AccountsCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Accounts} entities in the database.
 * The main input is a {@link AccountsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Accounts} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountsQueryService extends QueryService<Accounts> {

    private static final Logger LOG = LoggerFactory.getLogger(AccountsQueryService.class);

    private final AccountsRepository accountsRepository;

    public AccountsQueryService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    /**
     * Return a {@link Page} of {@link Accounts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Accounts> findByCriteria(AccountsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Accounts> specification = createSpecification(criteria);
        return accountsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountsCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Accounts> specification = createSpecification(criteria);
        return accountsRepository.count(specification);
    }

    /**
     * Function to convert {@link AccountsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Accounts> createSpecification(AccountsCriteria criteria) {
        Specification<Accounts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Accounts_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Accounts_.code));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Accounts_.date));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Accounts_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Accounts_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getType(), Accounts_.type));
            }
            if (criteria.getParent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParent(), Accounts_.parent));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalance(), Accounts_.balance));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Accounts_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Accounts_.lmd));
            }
            if (criteria.getHasbatches() != null) {
                specification = specification.and(buildSpecification(criteria.getHasbatches(), Accounts_.hasbatches));
            }
            if (criteria.getAccountvalue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountvalue(), Accounts_.accountvalue));
            }
            if (criteria.getAccountlevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountlevel(), Accounts_.accountlevel));
            }
            if (criteria.getAccountsnumberingsystem() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getAccountsnumberingsystem(), Accounts_.accountsnumberingsystem)
                );
            }
            if (criteria.getSubparentid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubparentid(), Accounts_.subparentid));
            }
            if (criteria.getCanedit() != null) {
                specification = specification.and(buildSpecification(criteria.getCanedit(), Accounts_.canedit));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Accounts_.amount));
            }
            if (criteria.getCreditamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreditamount(), Accounts_.creditamount));
            }
            if (criteria.getDebitamount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDebitamount(), Accounts_.debitamount));
            }
            if (criteria.getDebitorcredit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDebitorcredit(), Accounts_.debitorcredit));
            }
            if (criteria.getReporttype() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReporttype(), Accounts_.reporttype));
            }
        }
        return specification;
    }
}
