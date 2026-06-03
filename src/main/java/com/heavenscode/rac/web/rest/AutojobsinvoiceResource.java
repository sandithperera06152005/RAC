package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsinvoice;
import com.heavenscode.rac.repository.AutojobsinvoiceRepository;
import com.heavenscode.rac.service.AutojobsinvoiceQueryService;
import com.heavenscode.rac.service.AutojobsinvoiceService;
import com.heavenscode.rac.service.criteria.AutojobsinvoiceCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autojobsinvoice}.
 */
@RestController
@RequestMapping("/api/autojobsinvoices")
public class AutojobsinvoiceResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsinvoiceResource.class);

    private static final String ENTITY_NAME = "autojobsinvoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsinvoiceService autojobsinvoiceService;

    private final AutojobsinvoiceRepository autojobsinvoiceRepository;

    private final AutojobsinvoiceQueryService autojobsinvoiceQueryService;

    public AutojobsinvoiceResource(
        AutojobsinvoiceService autojobsinvoiceService,
        AutojobsinvoiceRepository autojobsinvoiceRepository,
        AutojobsinvoiceQueryService autojobsinvoiceQueryService
    ) {
        this.autojobsinvoiceService = autojobsinvoiceService;
        this.autojobsinvoiceRepository = autojobsinvoiceRepository;
        this.autojobsinvoiceQueryService = autojobsinvoiceQueryService;
    }

    /**
     * {@code POST  /autojobsinvoices} : Create a new autojobsinvoice.
     *
     * @param autojobsinvoice the autojobsinvoice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autojobsinvoice, or with status {@code 400 (Bad Request)} if the autojobsinvoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autojobsinvoice> createAutojobsinvoice(@RequestBody Autojobsinvoice autojobsinvoice) throws URISyntaxException {
        LOG.debug("REST request to save Autojobsinvoice : {}", autojobsinvoice);
        if (autojobsinvoice.getId() != null) {
            throw new BadRequestAlertException("A new autojobsinvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        autojobsinvoice = autojobsinvoiceService.save(autojobsinvoice);
        return ResponseEntity.created(new URI("/api/autojobsinvoices/" + autojobsinvoice.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, autojobsinvoice.getId().toString()))
            .body(autojobsinvoice);
    }

    /**
     * {@code PUT  /autojobsinvoices/:id} : Updates an existing autojobsinvoice.
     *
     * @param id the id of the autojobsinvoice to save.
     * @param autojobsinvoice the autojobsinvoice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsinvoice,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autojobsinvoice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Autojobsinvoice> updateAutojobsinvoice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsinvoice autojobsinvoice
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autojobsinvoice : {}, {}", id, autojobsinvoice);
        if (autojobsinvoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsinvoice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsinvoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsinvoice = autojobsinvoiceService.update(autojobsinvoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsinvoice.getId().toString()))
            .body(autojobsinvoice);
    }

    /**
     * {@code PATCH  /autojobsinvoices/:id} : Partial updates given fields of an existing autojobsinvoice, field will ignore if it is null
     *
     * @param id the id of the autojobsinvoice to save.
     * @param autojobsinvoice the autojobsinvoice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsinvoice,
     * or with status {@code 400 (Bad Request)} if the autojobsinvoice is not valid,
     * or with status {@code 404 (Not Found)} if the autojobsinvoice is not found,
     * or with status {@code 500 (Internal Server Error)} if the autojobsinvoice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsinvoice> partialUpdateAutojobsinvoice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsinvoice autojobsinvoice
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Autojobsinvoice partially : {}, {}", id, autojobsinvoice);
        if (autojobsinvoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsinvoice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsinvoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsinvoice> result = autojobsinvoiceService.partialUpdate(autojobsinvoice);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsinvoice.getId().toString())
        );
    }

    /**
     * {@code GET  /autojobsinvoices} : get all the autojobsinvoices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsinvoices in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsinvoice>> getAllAutojobsinvoices(
        AutojobsinvoiceCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Autojobsinvoices by criteria: {}", criteria);

        Page<Autojobsinvoice> page = autojobsinvoiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autojobsinvoices/count} : count all the autojobsinvoices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAutojobsinvoices(AutojobsinvoiceCriteria criteria) {
        LOG.debug("REST request to count Autojobsinvoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(autojobsinvoiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /autojobsinvoices/:id} : get the "id" autojobsinvoice.
     *
     * @param id the id of the autojobsinvoice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsinvoice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autojobsinvoice> getAutojobsinvoice(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Autojobsinvoice : {}", id);
        Optional<Autojobsinvoice> autojobsinvoice = autojobsinvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autojobsinvoice);
    }

    /**
     * {@code DELETE  /autojobsinvoices/:id} : delete the "id" autojobsinvoice.
     *
     * @param id the id of the autojobsinvoice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsinvoice(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autojobsinvoice : {}", id);
        autojobsinvoiceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
