package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsinvoice;
import com.heavenscode.rac.repository.AutojobsinvoiceRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autojobsinvoice}.
 */
@Service
@Transactional
public class AutojobsinvoiceService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoiceService.class);
    private static final String INVOICE_CODE_PREFIX = "SI";

    private final AutojobsinvoiceRepository autojobsinvoiceRepository;
    private final JdbcTemplate jdbcTemplate;

    public AutojobsinvoiceService(AutojobsinvoiceRepository autojobsinvoiceRepository, JdbcTemplate jdbcTemplate) {
        this.autojobsinvoiceRepository = autojobsinvoiceRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a autojobsinvoice.
     *
     * @param autojobsinvoice the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoice save(Autojobsinvoice autojobsinvoice) {
        LOG.debug("Request to save Autojobsinvoice : {}", autojobsinvoice);
        assignInvoiceCodeIfMissing(autojobsinvoice);
        return autojobsinvoiceRepository.save(autojobsinvoice);
    }

    /**
     * Update a autojobsinvoice.
     *
     * @param autojobsinvoice the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoice update(Autojobsinvoice autojobsinvoice) {
        LOG.debug("Request to update Autojobsinvoice : {}", autojobsinvoice);
        return autojobsinvoiceRepository.save(autojobsinvoice);
    }

    /**
     * Partially update a autojobsinvoice.
     *
     * @param autojobsinvoice the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autojobsinvoice> partialUpdate(Autojobsinvoice autojobsinvoice) {
        LOG.debug("Request to partially update Autojobsinvoice : {}", autojobsinvoice);

        return autojobsinvoiceRepository
            .findById(autojobsinvoice.getId())
            .map(existingAutojobsinvoice -> {
                if (autojobsinvoice.getCode() != null) {
                    existingAutojobsinvoice.setCode(autojobsinvoice.getCode());
                }
                if (autojobsinvoice.getInvoicedate() != null) {
                    existingAutojobsinvoice.setInvoicedate(autojobsinvoice.getInvoicedate());
                }
                if (autojobsinvoice.getCreateddate() != null) {
                    existingAutojobsinvoice.setCreateddate(autojobsinvoice.getCreateddate());
                }
                if (autojobsinvoice.getJobid() != null) {
                    existingAutojobsinvoice.setJobid(autojobsinvoice.getJobid());
                }
                if (autojobsinvoice.getQuoteid() != null) {
                    existingAutojobsinvoice.setQuoteid(autojobsinvoice.getQuoteid());
                }
                if (autojobsinvoice.getOrderid() != null) {
                    existingAutojobsinvoice.setOrderid(autojobsinvoice.getOrderid());
                }
                if (autojobsinvoice.getDelieverydate() != null) {
                    existingAutojobsinvoice.setDelieverydate(autojobsinvoice.getDelieverydate());
                }
                if (autojobsinvoice.getAutojobsrepid() != null) {
                    existingAutojobsinvoice.setAutojobsrepid(autojobsinvoice.getAutojobsrepid());
                }
                if (autojobsinvoice.getAutojobsrepname() != null) {
                    existingAutojobsinvoice.setAutojobsrepname(autojobsinvoice.getAutojobsrepname());
                }
                if (autojobsinvoice.getDelieverfrom() != null) {
                    existingAutojobsinvoice.setDelieverfrom(autojobsinvoice.getDelieverfrom());
                }
                if (autojobsinvoice.getCustomerid() != null) {
                    existingAutojobsinvoice.setCustomerid(autojobsinvoice.getCustomerid());
                }
                if (autojobsinvoice.getCustomername() != null) {
                    existingAutojobsinvoice.setCustomername(autojobsinvoice.getCustomername());
                }
                if (autojobsinvoice.getCustomeraddress() != null) {
                    existingAutojobsinvoice.setCustomeraddress(autojobsinvoice.getCustomeraddress());
                }
                if (autojobsinvoice.getDeliveryaddress() != null) {
                    existingAutojobsinvoice.setDeliveryaddress(autojobsinvoice.getDeliveryaddress());
                }
                if (autojobsinvoice.getSubtotal() != null) {
                    existingAutojobsinvoice.setSubtotal(autojobsinvoice.getSubtotal());
                }
                if (autojobsinvoice.getTotaltax() != null) {
                    existingAutojobsinvoice.setTotaltax(autojobsinvoice.getTotaltax());
                }
                if (autojobsinvoice.getTotaldiscount() != null) {
                    existingAutojobsinvoice.setTotaldiscount(autojobsinvoice.getTotaldiscount());
                }
                if (autojobsinvoice.getNettotal() != null) {
                    existingAutojobsinvoice.setNettotal(autojobsinvoice.getNettotal());
                }
                if (autojobsinvoice.getMessage() != null) {
                    existingAutojobsinvoice.setMessage(autojobsinvoice.getMessage());
                }
                if (autojobsinvoice.getLmu() != null) {
                    existingAutojobsinvoice.setLmu(autojobsinvoice.getLmu());
                }
                if (autojobsinvoice.getLmd() != null) {
                    existingAutojobsinvoice.setLmd(autojobsinvoice.getLmd());
                }
                if (autojobsinvoice.getPaidamount() != null) {
                    existingAutojobsinvoice.setPaidamount(autojobsinvoice.getPaidamount());
                }
                if (autojobsinvoice.getAmountowing() != null) {
                    existingAutojobsinvoice.setAmountowing(autojobsinvoice.getAmountowing());
                }
                if (autojobsinvoice.getIsactive() != null) {
                    existingAutojobsinvoice.setIsactive(autojobsinvoice.getIsactive());
                }
                if (autojobsinvoice.getLocationid() != null) {
                    existingAutojobsinvoice.setLocationid(autojobsinvoice.getLocationid());
                }
                if (autojobsinvoice.getLocationcode() != null) {
                    existingAutojobsinvoice.setLocationcode(autojobsinvoice.getLocationcode());
                }
                if (autojobsinvoice.getReferencecode() != null) {
                    existingAutojobsinvoice.setReferencecode(autojobsinvoice.getReferencecode());
                }
                if (autojobsinvoice.getCreatedbyid() != null) {
                    existingAutojobsinvoice.setCreatedbyid(autojobsinvoice.getCreatedbyid());
                }
                if (autojobsinvoice.getCreatedbyname() != null) {
                    existingAutojobsinvoice.setCreatedbyname(autojobsinvoice.getCreatedbyname());
                }
                if (autojobsinvoice.getAutocarecompanyid() != null) {
                    existingAutojobsinvoice.setAutocarecompanyid(autojobsinvoice.getAutocarecompanyid());
                }

                return existingAutojobsinvoice;
            })
            .map(autojobsinvoiceRepository::save);
    }

    /**
     * Get one autojobsinvoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autojobsinvoice> findOne(Long id) {
        LOG.debug("Request to get Autojobsinvoice : {}", id);
        return autojobsinvoiceRepository.findById(id);
    }

    /**
     * Delete the autojobsinvoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Autojobsinvoice : {}", id);
        autojobsinvoiceRepository.deleteById(id);
    }

    private void assignInvoiceCodeIfMissing(Autojobsinvoice autojobsinvoice) {
        if (autojobsinvoice.getCode() != null && !autojobsinvoice.getCode().trim().isEmpty()) {
            return;
        }

        String qualifiedTableName = resolveQualifiedTableName("autojobsinvoice");
        String sql =
            "SELECT COALESCE(MAX(TRY_CAST(SUBSTRING([code], 3, LEN([code])) AS INT)), 0) " +
            "FROM " +
            qualifiedTableName +
            " WHERE [code] LIKE ?";

        Integer maxNumber = jdbcTemplate.queryForObject(sql, Integer.class, INVOICE_CODE_PREFIX + "%");
        autojobsinvoice.setCode(INVOICE_CODE_PREFIX + (maxNumber == null ? 1 : maxNumber + 1));
    }

    private String resolveQualifiedTableName(String tableName) {
        List<String> tableNames = jdbcTemplate.queryForList(
            "SELECT TOP 1 QUOTENAME(TABLE_SCHEMA) + '.' + QUOTENAME(TABLE_NAME) FROM INFORMATION_SCHEMA.TABLES WHERE LOWER(TABLE_NAME) = ?",
            String.class,
            tableName.toLowerCase()
        );

        if (tableNames.isEmpty()) {
            throw new IllegalStateException("Table " + tableName + " was not found");
        }

        return tableNames.get(0);
    }
}
