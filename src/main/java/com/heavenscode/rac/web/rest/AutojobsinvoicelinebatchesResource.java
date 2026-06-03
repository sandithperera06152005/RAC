package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsinvoicelinebatches;
import com.heavenscode.rac.repository.AutojobsinvoicelinebatchesRepository;
import com.heavenscode.rac.service.AutojobsChildInsertService;
import com.heavenscode.rac.service.AutojobsinvoicelinebatchesQueryService;
import com.heavenscode.rac.service.AutojobsinvoicelinebatchesReadService;
import com.heavenscode.rac.service.AutojobsinvoicelinebatchesService;
import com.heavenscode.rac.service.criteria.AutojobsinvoicelinebatchesCriteria;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.Autojobsinvoicelinebatches}.
 */
@RestController
@RequestMapping("/api/autojobsinvoicelinebatches")
public class AutojobsinvoicelinebatchesResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoicelinebatchesResource.class);

    private static final String ENTITY_NAME = "autojobsinvoicelinebatches";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsinvoicelinebatchesService autojobsinvoicelinebatchesService;

    private final AutojobsinvoicelinebatchesRepository autojobsinvoicelinebatchesRepository;

    private final AutojobsinvoicelinebatchesQueryService autojobsinvoicelinebatchesQueryService;
    private final AutojobsinvoicelinebatchesReadService autojobsinvoicelinebatchesReadService;
    private final AutojobsChildInsertService autojobsChildInsertService;

    public AutojobsinvoicelinebatchesResource(
        AutojobsinvoicelinebatchesService autojobsinvoicelinebatchesService,
        AutojobsinvoicelinebatchesRepository autojobsinvoicelinebatchesRepository,
        AutojobsinvoicelinebatchesQueryService autojobsinvoicelinebatchesQueryService,
        AutojobsinvoicelinebatchesReadService autojobsinvoicelinebatchesReadService,
        AutojobsChildInsertService autojobsChildInsertService
    ) {
        this.autojobsinvoicelinebatchesService = autojobsinvoicelinebatchesService;
        this.autojobsinvoicelinebatchesRepository = autojobsinvoicelinebatchesRepository;
        this.autojobsinvoicelinebatchesQueryService = autojobsinvoicelinebatchesQueryService;
        this.autojobsinvoicelinebatchesReadService = autojobsinvoicelinebatchesReadService;
        this.autojobsChildInsertService = autojobsChildInsertService;
    }

    /**
     * {@code POST  /autojobsinvoicelinebatches} : Create a new autojobsinvoicelinebatches.
     *
     * @param autojobsinvoicelinebatches the autojobsinvoicelinebatches to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autojobsinvoicelinebatches, or with status {@code 400 (Bad Request)} if the autojobsinvoicelinebatches has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autojobsinvoicelinebatches> createAutojobsinvoicelinebatches(
        @RequestBody Autojobsinvoicelinebatches autojobsinvoicelinebatches
    ) throws URISyntaxException {
        LOG.debug("REST request to save Autojobsinvoicelinebatches : {}", autojobsinvoicelinebatches);
        autojobsinvoicelinebatches = autojobsChildInsertService.insertInvoiceLineBatch(autojobsinvoicelinebatches);
        String identifier = creationIdentifier(autojobsinvoicelinebatches);
        return ResponseEntity.created(new URI("/api/autojobsinvoicelinebatches/" + identifier))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, identifier))
            .body(autojobsinvoicelinebatches);
    }

    /**
     * {@code PUT  /autojobsinvoicelinebatches/:id} : Updates an existing autojobsinvoicelinebatches.
     *
     * @param id the id of the autojobsinvoicelinebatches to save.
     * @param autojobsinvoicelinebatches the autojobsinvoicelinebatches to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsinvoicelinebatches,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoicelinebatches is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autojobsinvoicelinebatches couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Autojobsinvoicelinebatches> updateAutojobsinvoicelinebatches(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody Autojobsinvoicelinebatches autojobsinvoicelinebatches
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autojobsinvoicelinebatches : {}, {}", id, autojobsinvoicelinebatches);
        if (autojobsinvoicelinebatches.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsinvoicelinebatches.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsinvoicelinebatchesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsinvoicelinebatches = autojobsinvoicelinebatchesService.update(autojobsinvoicelinebatches);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsinvoicelinebatches.getId().toString()))
            .body(autojobsinvoicelinebatches);
    }

    /**
     * {@code PATCH  /autojobsinvoicelinebatches/:id} : Partial updates given fields of an existing autojobsinvoicelinebatches, field will ignore if it is null
     *
     * @param id the id of the autojobsinvoicelinebatches to save.
     * @param autojobsinvoicelinebatches the autojobsinvoicelinebatches to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsinvoicelinebatches,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoicelinebatches is not valid,
     * or with status {@code 404 (Not Found)} if the autojobsinvoicelinebatches is not found,
     * or with status {@code 500 (Internal Server Error)} if the autojobsinvoicelinebatches couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsinvoicelinebatches> partialUpdateAutojobsinvoicelinebatches(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody Autojobsinvoicelinebatches autojobsinvoicelinebatches
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Autojobsinvoicelinebatches partially : {}, {}", id, autojobsinvoicelinebatches);
        if (autojobsinvoicelinebatches.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsinvoicelinebatches.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsinvoicelinebatchesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsinvoicelinebatches> result = autojobsinvoicelinebatchesService.partialUpdate(autojobsinvoicelinebatches);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsinvoicelinebatches.getId().toString())
        );
    }

    /**
     * {@code GET  /autojobsinvoicelinebatches} : get all the autojobsinvoicelinebatches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsinvoicelinebatches in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsinvoicelinebatches>> getAllAutojobsinvoicelinebatches(
        AutojobsinvoicelinebatchesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Autojobsinvoicelinebatches by criteria: {}", criteria);

        Page<Autojobsinvoicelinebatches> page = autojobsinvoicelinebatchesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/parent-lines")
    public ResponseEntity<List<Autojobsinvoicelinebatches>> getBatchesByParentLineIds(@RequestParam("ids") List<Integer> ids) {
        LOG.debug("REST request to get Autojobsinvoicelinebatches by parent invoice line IDs : {}", ids);
        return ResponseEntity.ok(autojobsinvoicelinebatchesReadService.findByParentInvoiceLineIds(ids));
    }

    /**
     * {@code GET  /autojobsinvoicelinebatches/count} : count all the autojobsinvoicelinebatches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAutojobsinvoicelinebatches(AutojobsinvoicelinebatchesCriteria criteria) {
        LOG.debug("REST request to count Autojobsinvoicelinebatches by criteria: {}", criteria);
        return ResponseEntity.ok().body(autojobsinvoicelinebatchesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /autojobsinvoicelinebatches/:id} : get the "id" autojobsinvoicelinebatches.
     *
     * @param id the id of the autojobsinvoicelinebatches to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsinvoicelinebatches, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autojobsinvoicelinebatches> getAutojobsinvoicelinebatches(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get Autojobsinvoicelinebatches : {}", id);
        Optional<Autojobsinvoicelinebatches> autojobsinvoicelinebatches = autojobsinvoicelinebatchesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autojobsinvoicelinebatches);
    }

    /**
     * {@code DELETE  /autojobsinvoicelinebatches/:id} : delete the "id" autojobsinvoicelinebatches.
     *
     * @param id the id of the autojobsinvoicelinebatches to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsinvoicelinebatches(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete Autojobsinvoicelinebatches : {}", id);
        autojobsinvoicelinebatchesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    private String creationIdentifier(Autojobsinvoicelinebatches entity) {
        if (entity.getId() != null) {
            return entity.getId().toString();
        }
        if (entity.getLineid() != null && entity.getBatchlineid() != null) {
            return entity.getLineid() + "-" + entity.getBatchlineid();
        }
        if (entity.getBatchlineid() != null) {
            return entity.getBatchlineid().toString();
        }
        return "created";
    }
}
