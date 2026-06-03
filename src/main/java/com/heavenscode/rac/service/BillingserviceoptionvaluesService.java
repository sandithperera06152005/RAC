package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Billingserviceoptionvalues;
import com.heavenscode.rac.repository.BillingserviceoptionvaluesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Billingserviceoptionvalues}.
 */
@Service
@Transactional
public class BillingserviceoptionvaluesService {

    private static final Logger LOG = LoggerFactory.getLogger(BillingserviceoptionvaluesService.class);

    private final BillingserviceoptionvaluesRepository billingserviceoptionvaluesRepository;

    public BillingserviceoptionvaluesService(BillingserviceoptionvaluesRepository billingserviceoptionvaluesRepository) {
        this.billingserviceoptionvaluesRepository = billingserviceoptionvaluesRepository;
    }

    /**
     * Save a billingserviceoptionvalues.
     *
     * @param billingserviceoptionvalues the entity to save.
     * @return the persisted entity.
     */
    public Billingserviceoptionvalues save(Billingserviceoptionvalues billingserviceoptionvalues) {
        LOG.debug("Request to save Billingserviceoptionvalues : {}", billingserviceoptionvalues);
        return billingserviceoptionvaluesRepository.save(billingserviceoptionvalues);
    }

    /**
     * Update a billingserviceoptionvalues.
     *
     * @param billingserviceoptionvalues the entity to save.
     * @return the persisted entity.
     */
    public Billingserviceoptionvalues update(Billingserviceoptionvalues billingserviceoptionvalues) {
        LOG.debug("Request to update Billingserviceoptionvalues : {}", billingserviceoptionvalues);
        return billingserviceoptionvaluesRepository.save(billingserviceoptionvalues);
    }

    /**
     * Partially update a billingserviceoptionvalues.
     *
     * @param billingserviceoptionvalues the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Billingserviceoptionvalues> partialUpdate(Billingserviceoptionvalues billingserviceoptionvalues) {
        LOG.debug("Request to partially update Billingserviceoptionvalues : {}", billingserviceoptionvalues);

        return billingserviceoptionvaluesRepository
            .findById(billingserviceoptionvalues.getId())
            .map(existingBillingserviceoptionvalues -> {
                if (billingserviceoptionvalues.getVehicletypeid() != null) {
                    existingBillingserviceoptionvalues.setVehicletypeid(billingserviceoptionvalues.getVehicletypeid());
                }
                if (billingserviceoptionvalues.getBillingserviceoptionid() != null) {
                    existingBillingserviceoptionvalues.setBillingserviceoptionid(billingserviceoptionvalues.getBillingserviceoptionid());
                }
                if (billingserviceoptionvalues.getValue() != null) {
                    existingBillingserviceoptionvalues.setValue(billingserviceoptionvalues.getValue());
                }
                if (billingserviceoptionvalues.getLmd() != null) {
                    existingBillingserviceoptionvalues.setLmd(billingserviceoptionvalues.getLmd());
                }
                if (billingserviceoptionvalues.getLmu() != null) {
                    existingBillingserviceoptionvalues.setLmu(billingserviceoptionvalues.getLmu());
                }

                return existingBillingserviceoptionvalues;
            })
            .map(billingserviceoptionvaluesRepository::save);
    }

    /**
     * Get one billingserviceoptionvalues by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Billingserviceoptionvalues> findOne(Long id) {
        LOG.debug("Request to get Billingserviceoptionvalues : {}", id);
        return billingserviceoptionvaluesRepository.findById(id);
    }

    /**
     * Delete the billingserviceoptionvalues by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Billingserviceoptionvalues : {}", id);
        billingserviceoptionvaluesRepository.deleteById(id);
    }
}
