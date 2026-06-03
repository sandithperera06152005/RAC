package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Servicesubcategory;
import com.heavenscode.rac.repository.ServicesubcategoryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Servicesubcategory}.
 */
@Service
@Transactional
public class ServicesubcategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(ServicesubcategoryService.class);

    private final ServicesubcategoryRepository servicesubcategoryRepository;

    public ServicesubcategoryService(ServicesubcategoryRepository servicesubcategoryRepository) {
        this.servicesubcategoryRepository = servicesubcategoryRepository;
    }

    /**
     * Save a servicesubcategory.
     *
     * @param servicesubcategory the entity to save.
     * @return the persisted entity.
     */
    public Servicesubcategory save(Servicesubcategory servicesubcategory) {
        LOG.debug("Request to save Servicesubcategory : {}", servicesubcategory);
        return servicesubcategoryRepository.save(servicesubcategory);
    }

    /**
     * Update a servicesubcategory.
     *
     * @param servicesubcategory the entity to save.
     * @return the persisted entity.
     */
    public Servicesubcategory update(Servicesubcategory servicesubcategory) {
        LOG.debug("Request to update Servicesubcategory : {}", servicesubcategory);
        return servicesubcategoryRepository.save(servicesubcategory);
    }

    /**
     * Partially update a servicesubcategory.
     *
     * @param servicesubcategory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Servicesubcategory> partialUpdate(Servicesubcategory servicesubcategory) {
        LOG.debug("Request to partially update Servicesubcategory : {}", servicesubcategory);

        return servicesubcategoryRepository
            .findById(servicesubcategory.getId())
            .map(existingServicesubcategory -> {
                if (servicesubcategory.getName() != null) {
                    existingServicesubcategory.setName(servicesubcategory.getName());
                }
                if (servicesubcategory.getDescription() != null) {
                    existingServicesubcategory.setDescription(servicesubcategory.getDescription());
                }
                if (servicesubcategory.getMainid() != null) {
                    existingServicesubcategory.setMainid(servicesubcategory.getMainid());
                }
                if (servicesubcategory.getMainname() != null) {
                    existingServicesubcategory.setMainname(servicesubcategory.getMainname());
                }
                if (servicesubcategory.getLmu() != null) {
                    existingServicesubcategory.setLmu(servicesubcategory.getLmu());
                }
                if (servicesubcategory.getLmd() != null) {
                    existingServicesubcategory.setLmd(servicesubcategory.getLmd());
                }
                if (servicesubcategory.getIsactive() != null) {
                    existingServicesubcategory.setIsactive(servicesubcategory.getIsactive());
                }

                return existingServicesubcategory;
            })
            .map(servicesubcategoryRepository::save);
    }

    /**
     * Get one servicesubcategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Servicesubcategory> findOne(Long id) {
        LOG.debug("Request to get Servicesubcategory : {}", id);
        return servicesubcategoryRepository.findById(id);
    }

    /**
     * Delete the servicesubcategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Servicesubcategory : {}", id);
        servicesubcategoryRepository.deleteById(id);
    }
}
