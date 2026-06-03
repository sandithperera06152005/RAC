package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Billingserviceoption;
import com.heavenscode.rac.repository.BillingserviceoptionRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Billingserviceoption}.
 */
@Service
@Transactional
public class BillingserviceoptionService {

    private static final Logger LOG = LoggerFactory.getLogger(BillingserviceoptionService.class);

    private final BillingserviceoptionRepository billingserviceoptionRepository;

    public BillingserviceoptionService(BillingserviceoptionRepository billingserviceoptionRepository) {
        this.billingserviceoptionRepository = billingserviceoptionRepository;
    }

    /**
     * Save a billingserviceoption.
     *
     * @param billingserviceoption the entity to save.
     * @return the persisted entity.
     */
    public Billingserviceoption save(Billingserviceoption billingserviceoption) {
        LOG.debug("Request to save Billingserviceoption : {}", billingserviceoption);
        return billingserviceoptionRepository.save(billingserviceoption);
    }

    /**
     * Update a billingserviceoption.
     *
     * @param billingserviceoption the entity to save.
     * @return the persisted entity.
     */
    public Billingserviceoption update(Billingserviceoption billingserviceoption) {
        LOG.debug("Request to update Billingserviceoption : {}", billingserviceoption);
        return billingserviceoptionRepository.save(billingserviceoption);
    }

    /**
     * Partially update a billingserviceoption.
     *
     * @param billingserviceoption the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Billingserviceoption> partialUpdate(Billingserviceoption billingserviceoption) {
        LOG.debug("Request to partially update Billingserviceoption : {}", billingserviceoption);

        return billingserviceoptionRepository
            .findById(billingserviceoption.getId())
            .map(existingBillingserviceoption -> {
                if (billingserviceoption.getServicename() != null) {
                    existingBillingserviceoption.setServicename(billingserviceoption.getServicename());
                }
                if (billingserviceoption.getServicediscription() != null) {
                    existingBillingserviceoption.setServicediscription(billingserviceoption.getServicediscription());
                }
                if (billingserviceoption.getIsactive() != null) {
                    existingBillingserviceoption.setIsactive(billingserviceoption.getIsactive());
                }
                if (billingserviceoption.getLmd() != null) {
                    existingBillingserviceoption.setLmd(billingserviceoption.getLmd());
                }
                if (billingserviceoption.getLmu() != null) {
                    existingBillingserviceoption.setLmu(billingserviceoption.getLmu());
                }
                if (billingserviceoption.getOrderby() != null) {
                    existingBillingserviceoption.setOrderby(billingserviceoption.getOrderby());
                }
                if (billingserviceoption.getBilltocustomer() != null) {
                    existingBillingserviceoption.setBilltocustomer(billingserviceoption.getBilltocustomer());
                }

                return existingBillingserviceoption;
            })
            .map(billingserviceoptionRepository::save);
    }

    /**
     * Get one billingserviceoption by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Billingserviceoption> findOne(Long id) {
        LOG.debug("Request to get Billingserviceoption : {}", id);
        return billingserviceoptionRepository.findById(id);
    }

    /**
     * Delete the billingserviceoption by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Billingserviceoption : {}", id);
        billingserviceoptionRepository.deleteById(id);
    }
}
