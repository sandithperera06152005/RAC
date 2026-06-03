package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.repository.AutojobsalesinvoiceservicechargelineRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline}.
 */
@Service
@Transactional
public class AutojobsalesinvoiceservicechargelineService {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsalesinvoiceservicechargelineService.class);

    private final AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository;

    public AutojobsalesinvoiceservicechargelineService(
        AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository
    ) {
        this.autojobsalesinvoiceservicechargelineRepository = autojobsalesinvoiceservicechargelineRepository;
    }

    /**
     * Save a autojobsalesinvoiceservicechargeline.
     *
     * @param autojobsalesinvoiceservicechargeline the entity to save.
     * @return the persisted entity.
     */
    public Autojobsalesinvoiceservicechargeline save(Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline) {
        LOG.debug("Request to save Autojobsalesinvoiceservicechargeline : {}", autojobsalesinvoiceservicechargeline);
        return autojobsalesinvoiceservicechargelineRepository.save(autojobsalesinvoiceservicechargeline);
    }

    /**
     * Update a autojobsalesinvoiceservicechargeline.
     *
     * @param autojobsalesinvoiceservicechargeline the entity to save.
     * @return the persisted entity.
     */
    public Autojobsalesinvoiceservicechargeline update(Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline) {
        LOG.debug("Request to update Autojobsalesinvoiceservicechargeline : {}", autojobsalesinvoiceservicechargeline);
        return autojobsalesinvoiceservicechargelineRepository.save(autojobsalesinvoiceservicechargeline);
    }

    /**
     * Partially update a autojobsalesinvoiceservicechargeline.
     *
     * @param autojobsalesinvoiceservicechargeline the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autojobsalesinvoiceservicechargeline> partialUpdate(
        Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) {
        LOG.debug("Request to partially update Autojobsalesinvoiceservicechargeline : {}", autojobsalesinvoiceservicechargeline);

        return autojobsalesinvoiceservicechargelineRepository
            .findById(autojobsalesinvoiceservicechargeline.getId())
            .map(existingAutojobsalesinvoiceservicechargeline -> {
                if (autojobsalesinvoiceservicechargeline.getInvoiceid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setInvoiceid(autojobsalesinvoiceservicechargeline.getInvoiceid());
                }
                if (autojobsalesinvoiceservicechargeline.getLineid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setLineid(autojobsalesinvoiceservicechargeline.getLineid());
                }
                if (autojobsalesinvoiceservicechargeline.getOptionid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setOptionid(autojobsalesinvoiceservicechargeline.getOptionid());
                }
                if (autojobsalesinvoiceservicechargeline.getServicename() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServicename(autojobsalesinvoiceservicechargeline.getServicename());
                }
                if (autojobsalesinvoiceservicechargeline.getServicediscription() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServicediscription(
                        autojobsalesinvoiceservicechargeline.getServicediscription()
                    );
                }
                if (autojobsalesinvoiceservicechargeline.getValue() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setValue(autojobsalesinvoiceservicechargeline.getValue());
                }
                if (autojobsalesinvoiceservicechargeline.getAddedbyid() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setAddedbyid(autojobsalesinvoiceservicechargeline.getAddedbyid());
                }
                if (autojobsalesinvoiceservicechargeline.getIscustomersrvice() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setIscustomersrvice(
                        autojobsalesinvoiceservicechargeline.getIscustomersrvice()
                    );
                }
                if (autojobsalesinvoiceservicechargeline.getDiscount() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setDiscount(autojobsalesinvoiceservicechargeline.getDiscount());
                }
                if (autojobsalesinvoiceservicechargeline.getServiceprice() != null) {
                    existingAutojobsalesinvoiceservicechargeline.setServiceprice(autojobsalesinvoiceservicechargeline.getServiceprice());
                }

                return existingAutojobsalesinvoiceservicechargeline;
            })
            .map(autojobsalesinvoiceservicechargelineRepository::save);
    }

    /**
     * Get one autojobsalesinvoiceservicechargeline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autojobsalesinvoiceservicechargeline> findOne(Long id) {
        LOG.debug("Request to get Autojobsalesinvoiceservicechargeline : {}", id);
        return autojobsalesinvoiceservicechargelineRepository.findById(id);
    }

    /**
     * Delete the autojobsalesinvoiceservicechargeline by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Autojobsalesinvoiceservicechargeline : {}", id);
        autojobsalesinvoiceservicechargelineRepository.deleteById(id);
    }
}
