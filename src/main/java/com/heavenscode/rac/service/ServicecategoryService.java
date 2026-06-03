package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Servicecategory;
import com.heavenscode.rac.repository.ServicecategoryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Servicecategory}.
 */
@Service
@Transactional
public class ServicecategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ServicecategoryService.class);

    private final ServicecategoryRepository servicecategoryRepository;

    public ServicecategoryService(ServicecategoryRepository servicecategoryRepository) {
        this.servicecategoryRepository = servicecategoryRepository;
    }

    /**
     * Save a servicecategory.
     *
     * @param servicecategory the entity to save.
     * @return the persisted entity.
     */
    public Servicecategory save(Servicecategory servicecategory) {
        LOG.debug("Request to save Servicecategory : {}", servicecategory);
        return servicecategoryRepository.save(servicecategory);
    }

    /**
     * Update a servicecategory.
     *
     * @param servicecategory the entity to save.
     * @return the persisted entity.
     */
    public Servicecategory update(Servicecategory servicecategory) {
        LOG.debug("Request to update Servicecategory : {}", servicecategory);
        return servicecategoryRepository.save(servicecategory);
    }

    /**
     * Partially update a servicecategory.
     *
     * @param servicecategory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Servicecategory> partialUpdate(Servicecategory servicecategory) {
        LOG.debug("Request to partially update Servicecategory : {}", servicecategory);

        return servicecategoryRepository
            .findById(servicecategory.getId())
            .map(existingServicecategory -> {
                if (servicecategory.getName() != null) {
                    existingServicecategory.setName(servicecategory.getName());
                }
                if (servicecategory.getDescription() != null) {
                    existingServicecategory.setDescription(servicecategory.getDescription());
                }
                if (servicecategory.getLmu() != null) {
                    existingServicecategory.setLmu(servicecategory.getLmu());
                }
                if (servicecategory.getLmd() != null) {
                    existingServicecategory.setLmd(servicecategory.getLmd());
                }
                if (servicecategory.getShowsecurity() != null) {
                    existingServicecategory.setShowsecurity(servicecategory.getShowsecurity());
                }
                if (servicecategory.getSortorder() != null) {
                    existingServicecategory.setSortorder(servicecategory.getSortorder());
                }
                if (servicecategory.getIsactive() != null) {
                    existingServicecategory.setIsactive(servicecategory.getIsactive());
                }

                return existingServicecategory;
            })
            .map(servicecategoryRepository::save);
    }

    /**
     * Get one servicecategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Servicecategory> findOne(Long id) {
        LOG.debug("Request to get Servicecategory : {}", id);
        return servicecategoryRepository.findById(id);
    }

    /**
     * Delete the servicecategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Servicecategory : {}", id);
        servicecategoryRepository.deleteById(id);
    }
}
