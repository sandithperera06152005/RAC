package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Commonserviceoption;
import com.heavenscode.rac.repository.CommonserviceoptionRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Commonserviceoption}.
 */
@Service
@Transactional
public class CommonserviceoptionService {

    private static final Logger LOG = LoggerFactory.getLogger(CommonserviceoptionService.class);

    private final CommonserviceoptionRepository commonserviceoptionRepository;

    public CommonserviceoptionService(CommonserviceoptionRepository commonserviceoptionRepository) {
        this.commonserviceoptionRepository = commonserviceoptionRepository;
    }

    /**
     * Save a commonserviceoption.
     *
     * @param commonserviceoption the entity to save.
     * @return the persisted entity.
     */
    public Commonserviceoption save(Commonserviceoption commonserviceoption) {
        LOG.debug("Request to save Commonserviceoption : {}", commonserviceoption);
        return commonserviceoptionRepository.save(commonserviceoption);
    }

    /**
     * Update a commonserviceoption.
     *
     * @param commonserviceoption the entity to save.
     * @return the persisted entity.
     */
    public Commonserviceoption update(Commonserviceoption commonserviceoption) {
        LOG.debug("Request to update Commonserviceoption : {}", commonserviceoption);
        return commonserviceoptionRepository.save(commonserviceoption);
    }

    /**
     * Partially update a commonserviceoption.
     *
     * @param commonserviceoption the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Commonserviceoption> partialUpdate(Commonserviceoption commonserviceoption) {
        LOG.debug("Request to partially update Commonserviceoption : {}", commonserviceoption);

        return commonserviceoptionRepository
            .findById(commonserviceoption.getId())
            .map(existingCommonserviceoption -> {
                if (commonserviceoption.getMainid() != null) {
                    existingCommonserviceoption.setMainid(commonserviceoption.getMainid());
                }
                if (commonserviceoption.getCode() != null) {
                    existingCommonserviceoption.setCode(commonserviceoption.getCode());
                }
                if (commonserviceoption.getName() != null) {
                    existingCommonserviceoption.setName(commonserviceoption.getName());
                }
                if (commonserviceoption.getDescription() != null) {
                    existingCommonserviceoption.setDescription(commonserviceoption.getDescription());
                }
                if (commonserviceoption.getValue() != null) {
                    existingCommonserviceoption.setValue(commonserviceoption.getValue());
                }
                if (commonserviceoption.getIsactive() != null) {
                    existingCommonserviceoption.setIsactive(commonserviceoption.getIsactive());
                }
                if (commonserviceoption.getLmd() != null) {
                    existingCommonserviceoption.setLmd(commonserviceoption.getLmd());
                }
                if (commonserviceoption.getLmu() != null) {
                    existingCommonserviceoption.setLmu(commonserviceoption.getLmu());
                }

                return existingCommonserviceoption;
            })
            .map(commonserviceoptionRepository::save);
    }

    /**
     * Get one commonserviceoption by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Commonserviceoption> findOne(Long id) {
        LOG.debug("Request to get Commonserviceoption : {}", id);
        return commonserviceoptionRepository.findById(id);
    }

    /**
     * Delete the commonserviceoption by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Commonserviceoption : {}", id);
        commonserviceoptionRepository.deleteById(id);
    }
}
