package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsinvoicelinebatches;
import com.heavenscode.rac.repository.AutojobsinvoicelinebatchesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autojobsinvoicelinebatches}.
 */
@Service
@Transactional
public class AutojobsinvoicelinebatchesService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoicelinebatchesService.class);

    private final AutojobsinvoicelinebatchesRepository autojobsinvoicelinebatchesRepository;

    public AutojobsinvoicelinebatchesService(AutojobsinvoicelinebatchesRepository autojobsinvoicelinebatchesRepository) {
        this.autojobsinvoicelinebatchesRepository = autojobsinvoicelinebatchesRepository;
    }

    /**
     * Save a autojobsinvoicelinebatches.
     *
     * @param autojobsinvoicelinebatches the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoicelinebatches save(Autojobsinvoicelinebatches autojobsinvoicelinebatches) {
        LOG.debug("Request to save Autojobsinvoicelinebatches : {}", autojobsinvoicelinebatches);
        return autojobsinvoicelinebatchesRepository.save(autojobsinvoicelinebatches);
    }

    /**
     * Update a autojobsinvoicelinebatches.
     *
     * @param autojobsinvoicelinebatches the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoicelinebatches update(Autojobsinvoicelinebatches autojobsinvoicelinebatches) {
        LOG.debug("Request to update Autojobsinvoicelinebatches : {}", autojobsinvoicelinebatches);
        return autojobsinvoicelinebatchesRepository.save(autojobsinvoicelinebatches);
    }

    /**
     * Partially update a autojobsinvoicelinebatches.
     *
     * @param autojobsinvoicelinebatches the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autojobsinvoicelinebatches> partialUpdate(Autojobsinvoicelinebatches autojobsinvoicelinebatches) {
        LOG.debug("Request to partially update Autojobsinvoicelinebatches : {}", autojobsinvoicelinebatches);

        return autojobsinvoicelinebatchesRepository
            .findById(autojobsinvoicelinebatches.getId())
            .map(existingAutojobsinvoicelinebatches -> {
                if (autojobsinvoicelinebatches.getLineid() != null) {
                    existingAutojobsinvoicelinebatches.setLineid(autojobsinvoicelinebatches.getLineid());
                }
                if (autojobsinvoicelinebatches.getBatchlineid() != null) {
                    existingAutojobsinvoicelinebatches.setBatchlineid(autojobsinvoicelinebatches.getBatchlineid());
                }
                if (autojobsinvoicelinebatches.getItemid() != null) {
                    existingAutojobsinvoicelinebatches.setItemid(autojobsinvoicelinebatches.getItemid());
                }
                if (autojobsinvoicelinebatches.getCode() != null) {
                    existingAutojobsinvoicelinebatches.setCode(autojobsinvoicelinebatches.getCode());
                }
                if (autojobsinvoicelinebatches.getBatchid() != null) {
                    existingAutojobsinvoicelinebatches.setBatchid(autojobsinvoicelinebatches.getBatchid());
                }
                if (autojobsinvoicelinebatches.getBatchcode() != null) {
                    existingAutojobsinvoicelinebatches.setBatchcode(autojobsinvoicelinebatches.getBatchcode());
                }
                if (autojobsinvoicelinebatches.getTxdate() != null) {
                    existingAutojobsinvoicelinebatches.setTxdate(autojobsinvoicelinebatches.getTxdate());
                }
                if (autojobsinvoicelinebatches.getManufacturedate() != null) {
                    existingAutojobsinvoicelinebatches.setManufacturedate(autojobsinvoicelinebatches.getManufacturedate());
                }
                if (autojobsinvoicelinebatches.getExpireddate() != null) {
                    existingAutojobsinvoicelinebatches.setExpireddate(autojobsinvoicelinebatches.getExpireddate());
                }
                if (autojobsinvoicelinebatches.getQty() != null) {
                    existingAutojobsinvoicelinebatches.setQty(autojobsinvoicelinebatches.getQty());
                }
                if (autojobsinvoicelinebatches.getCost() != null) {
                    existingAutojobsinvoicelinebatches.setCost(autojobsinvoicelinebatches.getCost());
                }
                if (autojobsinvoicelinebatches.getPrice() != null) {
                    existingAutojobsinvoicelinebatches.setPrice(autojobsinvoicelinebatches.getPrice());
                }
                if (autojobsinvoicelinebatches.getNotes() != null) {
                    existingAutojobsinvoicelinebatches.setNotes(autojobsinvoicelinebatches.getNotes());
                }
                if (autojobsinvoicelinebatches.getLmu() != null) {
                    existingAutojobsinvoicelinebatches.setLmu(autojobsinvoicelinebatches.getLmu());
                }
                if (autojobsinvoicelinebatches.getLmd() != null) {
                    existingAutojobsinvoicelinebatches.setLmd(autojobsinvoicelinebatches.getLmd());
                }
                if (autojobsinvoicelinebatches.getNbt() != null) {
                    existingAutojobsinvoicelinebatches.setNbt(autojobsinvoicelinebatches.getNbt());
                }
                if (autojobsinvoicelinebatches.getVat() != null) {
                    existingAutojobsinvoicelinebatches.setVat(autojobsinvoicelinebatches.getVat());
                }
                if (autojobsinvoicelinebatches.getDiscount() != null) {
                    existingAutojobsinvoicelinebatches.setDiscount(autojobsinvoicelinebatches.getDiscount());
                }
                if (autojobsinvoicelinebatches.getTotal() != null) {
                    existingAutojobsinvoicelinebatches.setTotal(autojobsinvoicelinebatches.getTotal());
                }
                if (autojobsinvoicelinebatches.getIssued() != null) {
                    existingAutojobsinvoicelinebatches.setIssued(autojobsinvoicelinebatches.getIssued());
                }
                if (autojobsinvoicelinebatches.getIssuedby() != null) {
                    existingAutojobsinvoicelinebatches.setIssuedby(autojobsinvoicelinebatches.getIssuedby());
                }
                if (autojobsinvoicelinebatches.getIssueddatetime() != null) {
                    existingAutojobsinvoicelinebatches.setIssueddatetime(autojobsinvoicelinebatches.getIssueddatetime());
                }
                if (autojobsinvoicelinebatches.getAddedbyid() != null) {
                    existingAutojobsinvoicelinebatches.setAddedbyid(autojobsinvoicelinebatches.getAddedbyid());
                }
                if (autojobsinvoicelinebatches.getCanceloptid() != null) {
                    existingAutojobsinvoicelinebatches.setCanceloptid(autojobsinvoicelinebatches.getCanceloptid());
                }
                if (autojobsinvoicelinebatches.getCancelopt() != null) {
                    existingAutojobsinvoicelinebatches.setCancelopt(autojobsinvoicelinebatches.getCancelopt());
                }
                if (autojobsinvoicelinebatches.getCancelby() != null) {
                    existingAutojobsinvoicelinebatches.setCancelby(autojobsinvoicelinebatches.getCancelby());
                }

                return existingAutojobsinvoicelinebatches;
            })
            .map(autojobsinvoicelinebatchesRepository::save);
    }

    /**
     * Get one autojobsinvoicelinebatches by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autojobsinvoicelinebatches> findOne(Integer id) {
        LOG.debug("Request to get Autojobsinvoicelinebatches : {}", id);
        return autojobsinvoicelinebatchesRepository.findById(id);
    }

    /**
     * Delete the autojobsinvoicelinebatches by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Integer id) {
        LOG.debug("Request to delete Autojobsinvoicelinebatches : {}", id);
        autojobsinvoicelinebatchesRepository.deleteById(id);
    }
}
