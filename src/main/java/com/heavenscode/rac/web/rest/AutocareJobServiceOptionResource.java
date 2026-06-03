package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.AutocareJobServiceOption;
import com.heavenscode.rac.repository.AutocareJobServiceOptionRepository;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.AutocareJobServiceOption}.
 */
@RestController
@RequestMapping("/api/autocare-job-service-options")
@Transactional
public class AutocareJobServiceOptionResource {

    private final Logger log = LoggerFactory.getLogger(AutocareJobServiceOptionResource.class);

    private static final String ENTITY_NAME = "autocareJobServiceOption";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutocareJobServiceOptionRepository autocareJobServiceOptionRepository;

    public AutocareJobServiceOptionResource(AutocareJobServiceOptionRepository autocareJobServiceOptionRepository) {
        this.autocareJobServiceOptionRepository = autocareJobServiceOptionRepository;
    }

    /**
     * {@code POST  /autocare-job-service-options} : Create a new autocareJobServiceOption.
     *
     * @param autocareJobServiceOption the autocareJobServiceOption to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autocareJobServiceOption, or with status {@code 400 (Bad Request)} if the autocareJobServiceOption has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AutocareJobServiceOption> createAutocareJobServiceOption(
        @RequestBody AutocareJobServiceOption autocareJobServiceOption
    ) throws URISyntaxException {
        log.debug("REST request to save AutocareJobServiceOption : {}", autocareJobServiceOption);
        if (autocareJobServiceOption.getId() != null) {
            throw new BadRequestAlertException("A new autocareJobServiceOption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        autocareJobServiceOption = autocareJobServiceOptionRepository.save(autocareJobServiceOption);
        return ResponseEntity.created(new URI("/api/autocare-job-service-options/" + autocareJobServiceOption.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, autocareJobServiceOption.getId().toString()))
            .body(autocareJobServiceOption);
    }

    /**
     * {@code PUT  /autocare-job-service-options/:id} : Updates an existing autocareJobServiceOption.
     *
     * @param id the id of the autocareJobServiceOption to save.
     * @param autocareJobServiceOption the autocareJobServiceOption to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autocareJobServiceOption,
     * or with status {@code 400 (Bad Request)} if the autocareJobServiceOption is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autocareJobServiceOption couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AutocareJobServiceOption> updateAutocareJobServiceOption(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AutocareJobServiceOption autocareJobServiceOption
    ) throws URISyntaxException {
        log.debug("REST request to update AutocareJobServiceOption : {}, {}", id, autocareJobServiceOption);
        if (autocareJobServiceOption.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autocareJobServiceOption.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autocareJobServiceOptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autocareJobServiceOption = autocareJobServiceOptionRepository.save(autocareJobServiceOption);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autocareJobServiceOption.getId().toString()))
            .body(autocareJobServiceOption);
    }

    /**
     * {@code GET  /autocare-job-service-options} : get all the autocareJobServiceOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autocareJobServiceOptions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AutocareJobServiceOption>> getAllAutocareJobServiceOptions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AutocareJobServiceOptions");
        Page<AutocareJobServiceOption> page = autocareJobServiceOptionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autocare-job-service-options/by-job/:jobId} : get all autocareJobServiceOptions by jobId.
     *
     * @param jobId the job ID to filter by.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autocareJobServiceOptions in body.
     */
    @GetMapping("/by-job/{jobId}")
    public ResponseEntity<List<AutocareJobServiceOption>> getByJobId(@PathVariable("jobId") Integer jobId) {
        log.debug("REST request to get AutocareJobServiceOptions by jobId : {}", jobId);
        List<AutocareJobServiceOption> result = autocareJobServiceOptionRepository.findByJobid(jobId);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /autocare-job-service-options/:id} : get the "id" autocareJobServiceOption.
     *
     * @param id the id of the autocareJobServiceOption to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autocareJobServiceOption, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AutocareJobServiceOption> getAutocareJobServiceOption(@PathVariable("id") Long id) {
        log.debug("REST request to get AutocareJobServiceOption : {}", id);
        Optional<AutocareJobServiceOption> autocareJobServiceOption = autocareJobServiceOptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autocareJobServiceOption);
    }

    /**
     * {@code DELETE  /autocare-job-service-options/:id} : delete the "id" autocareJobServiceOption.
     *
     * @param id the id of the autocareJobServiceOption to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutocareJobServiceOption(@PathVariable("id") Long id) {
        log.debug("REST request to delete AutocareJobServiceOption : {}", id);
        autocareJobServiceOptionRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code DELETE  /autocare-job-service-options/by-job/:jobId} : delete all autocareJobServiceOptions by jobId.
     *
     * @param jobId the job ID whose service options should be deleted.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/by-job/{jobId}")
    public ResponseEntity<Void> deleteByJobId(@PathVariable("jobId") Integer jobId) {
        log.debug("REST request to delete AutocareJobServiceOptions by jobId : {}", jobId);
        autocareJobServiceOptionRepository.deleteByJobid(jobId);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, jobId.toString()))
            .build();
    }
}
