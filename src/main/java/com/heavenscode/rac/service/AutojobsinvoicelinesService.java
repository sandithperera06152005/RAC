package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsinvoicelines;
import com.heavenscode.rac.repository.AutojobsinvoicelinesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autojobsinvoicelines}.
 */
@Service
@Transactional
public class AutojobsinvoicelinesService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoicelinesService.class);

    private final AutojobsinvoicelinesRepository autojobsinvoicelinesRepository;

    public AutojobsinvoicelinesService(AutojobsinvoicelinesRepository autojobsinvoicelinesRepository) {
        this.autojobsinvoicelinesRepository = autojobsinvoicelinesRepository;
    }

    /**
     * Save a autojobsinvoicelines.
     *
     * @param autojobsinvoicelines the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoicelines save(Autojobsinvoicelines autojobsinvoicelines) {
        LOG.debug("Request to save Autojobsinvoicelines : {}", autojobsinvoicelines);
        return autojobsinvoicelinesRepository.save(autojobsinvoicelines);
    }

    /**
     * Update a autojobsinvoicelines.
     *
     * @param autojobsinvoicelines the entity to save.
     * @return the persisted entity.
     */
    public Autojobsinvoicelines update(Autojobsinvoicelines autojobsinvoicelines) {
        LOG.debug("Request to update Autojobsinvoicelines : {}", autojobsinvoicelines);
        return autojobsinvoicelinesRepository.save(autojobsinvoicelines);
    }

    /**
     * Partially update a autojobsinvoicelines.
     *
     * @param autojobsinvoicelines the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autojobsinvoicelines> partialUpdate(Autojobsinvoicelines autojobsinvoicelines) {
        LOG.debug("Request to partially update Autojobsinvoicelines : {}", autojobsinvoicelines);

        return autojobsinvoicelinesRepository
            .findById(autojobsinvoicelines.getId())
            .map(existingAutojobsinvoicelines -> {
                if (autojobsinvoicelines.getInvocieid() != null) {
                    existingAutojobsinvoicelines.setInvocieid(autojobsinvoicelines.getInvocieid());
                }
                if (autojobsinvoicelines.getLineid() != null) {
                    existingAutojobsinvoicelines.setLineid(autojobsinvoicelines.getLineid());
                }
                if (autojobsinvoicelines.getItemid() != null) {
                    existingAutojobsinvoicelines.setItemid(autojobsinvoicelines.getItemid());
                }
                if (autojobsinvoicelines.getItemcode() != null) {
                    existingAutojobsinvoicelines.setItemcode(autojobsinvoicelines.getItemcode());
                }
                if (autojobsinvoicelines.getItemname() != null) {
                    existingAutojobsinvoicelines.setItemname(autojobsinvoicelines.getItemname());
                }
                if (autojobsinvoicelines.getDescription() != null) {
                    existingAutojobsinvoicelines.setDescription(autojobsinvoicelines.getDescription());
                }
                if (autojobsinvoicelines.getUnitofmeasurement() != null) {
                    existingAutojobsinvoicelines.setUnitofmeasurement(autojobsinvoicelines.getUnitofmeasurement());
                }
                if (autojobsinvoicelines.getQuantity() != null) {
                    existingAutojobsinvoicelines.setQuantity(autojobsinvoicelines.getQuantity());
                }
                if (autojobsinvoicelines.getItemcost() != null) {
                    existingAutojobsinvoicelines.setItemcost(autojobsinvoicelines.getItemcost());
                }
                if (autojobsinvoicelines.getItemprice() != null) {
                    existingAutojobsinvoicelines.setItemprice(autojobsinvoicelines.getItemprice());
                }
                if (autojobsinvoicelines.getDiscount() != null) {
                    existingAutojobsinvoicelines.setDiscount(autojobsinvoicelines.getDiscount());
                }
                if (autojobsinvoicelines.getTax() != null) {
                    existingAutojobsinvoicelines.setTax(autojobsinvoicelines.getTax());
                }
                if (autojobsinvoicelines.getSellingprice() != null) {
                    existingAutojobsinvoicelines.setSellingprice(autojobsinvoicelines.getSellingprice());
                }
                if (autojobsinvoicelines.getLinetotal() != null) {
                    existingAutojobsinvoicelines.setLinetotal(autojobsinvoicelines.getLinetotal());
                }
                if (autojobsinvoicelines.getLmu() != null) {
                    existingAutojobsinvoicelines.setLmu(autojobsinvoicelines.getLmu());
                }
                if (autojobsinvoicelines.getLmd() != null) {
                    existingAutojobsinvoicelines.setLmd(autojobsinvoicelines.getLmd());
                }
                if (autojobsinvoicelines.getNbt() != null) {
                    existingAutojobsinvoicelines.setNbt(autojobsinvoicelines.getNbt());
                }
                if (autojobsinvoicelines.getVat() != null) {
                    existingAutojobsinvoicelines.setVat(autojobsinvoicelines.getVat());
                }

                return existingAutojobsinvoicelines;
            })
            .map(autojobsinvoicelinesRepository::save);
    }

    /**
     * Get one autojobsinvoicelines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autojobsinvoicelines> findOne(Long id) {
        LOG.debug("Request to get Autojobsinvoicelines : {}", id);
        return autojobsinvoicelinesRepository.findById(id);
    }

    /**
     * Delete the autojobsinvoicelines by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Autojobsinvoicelines : {}", id);
        autojobsinvoicelinesRepository.deleteById(id);
    }
}
