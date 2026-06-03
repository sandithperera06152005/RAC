package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SalesInvoiceLinesDummy;
import com.heavenscode.rac.repository.SalesInvoiceLinesDummyRepository;
import com.heavenscode.rac.service.SalesInvoiceLinesDummyQueryService;
import com.heavenscode.rac.service.SalesInvoiceLinesDummyService;
import com.heavenscode.rac.service.criteria.SalesInvoiceLinesDummyCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.SalesInvoiceLinesDummy}.
 */
@RestController
@RequestMapping("/api/sales-invoice-lines-dummies")
public class SalesInvoiceLinesDummyResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceLinesDummyResource.class);

    private static final String ENTITY_NAME = "salesInvoiceLinesDummy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesInvoiceLinesDummyService salesInvoiceLinesDummyService;

    private final SalesInvoiceLinesDummyRepository salesInvoiceLinesDummyRepository;

    private final SalesInvoiceLinesDummyQueryService salesInvoiceLinesDummyQueryService;

    public SalesInvoiceLinesDummyResource(
        SalesInvoiceLinesDummyService salesInvoiceLinesDummyService,
        SalesInvoiceLinesDummyRepository salesInvoiceLinesDummyRepository,
        SalesInvoiceLinesDummyQueryService salesInvoiceLinesDummyQueryService
    ) {
        this.salesInvoiceLinesDummyService = salesInvoiceLinesDummyService;
        this.salesInvoiceLinesDummyRepository = salesInvoiceLinesDummyRepository;
        this.salesInvoiceLinesDummyQueryService = salesInvoiceLinesDummyQueryService;
    }

    /**
     * {@code POST  /sales-invoice-lines-dummies} : Create a new salesInvoiceLinesDummy.
     *
     * @param salesInvoiceLinesDummy the salesInvoiceLinesDummy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesInvoiceLinesDummy, or with status {@code 400 (Bad Request)} if the salesInvoiceLinesDummy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SalesInvoiceLinesDummy> createSalesInvoiceLinesDummy(@RequestBody SalesInvoiceLinesDummy salesInvoiceLinesDummy)
        throws URISyntaxException {
        LOG.debug("REST request to save SalesInvoiceLinesDummy : {}", salesInvoiceLinesDummy);
        if (salesInvoiceLinesDummy.getId() != null) {
            throw new BadRequestAlertException("A new salesInvoiceLinesDummy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        salesInvoiceLinesDummy = salesInvoiceLinesDummyService.save(salesInvoiceLinesDummy);
        return ResponseEntity.created(new URI("/api/sales-invoice-lines-dummies/" + salesInvoiceLinesDummy.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, salesInvoiceLinesDummy.getId().toString()))
            .body(salesInvoiceLinesDummy);
    }

    /**
     * {@code PUT  /sales-invoice-lines-dummies/:id} : Updates an existing salesInvoiceLinesDummy.
     *
     * @param id the id of the salesInvoiceLinesDummy to save.
     * @param salesInvoiceLinesDummy the salesInvoiceLinesDummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceLinesDummy,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceLinesDummy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceLinesDummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalesInvoiceLinesDummy> updateSalesInvoiceLinesDummy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesInvoiceLinesDummy salesInvoiceLinesDummy
    ) throws URISyntaxException {
        LOG.debug("REST request to update SalesInvoiceLinesDummy : {}, {}", id, salesInvoiceLinesDummy);
        if (salesInvoiceLinesDummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceLinesDummy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceLinesDummyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salesInvoiceLinesDummy = salesInvoiceLinesDummyService.update(salesInvoiceLinesDummy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceLinesDummy.getId().toString()))
            .body(salesInvoiceLinesDummy);
    }

    /**
     * {@code PATCH  /sales-invoice-lines-dummies/:id} : Partial updates given fields of an existing salesInvoiceLinesDummy, field will ignore if it is null
     *
     * @param id the id of the salesInvoiceLinesDummy to save.
     * @param salesInvoiceLinesDummy the salesInvoiceLinesDummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceLinesDummy,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceLinesDummy is not valid,
     * or with status {@code 404 (Not Found)} if the salesInvoiceLinesDummy is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceLinesDummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesInvoiceLinesDummy> partialUpdateSalesInvoiceLinesDummy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesInvoiceLinesDummy salesInvoiceLinesDummy
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SalesInvoiceLinesDummy partially : {}, {}", id, salesInvoiceLinesDummy);
        if (salesInvoiceLinesDummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceLinesDummy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceLinesDummyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesInvoiceLinesDummy> result = salesInvoiceLinesDummyService.partialUpdate(salesInvoiceLinesDummy);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceLinesDummy.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-invoice-lines-dummies} : get all the salesInvoiceLinesDummies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesInvoiceLinesDummies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SalesInvoiceLinesDummy>> getAllSalesInvoiceLinesDummies(
        SalesInvoiceLinesDummyCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SalesInvoiceLinesDummies by criteria: {}", criteria);

        Page<SalesInvoiceLinesDummy> page = salesInvoiceLinesDummyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales-invoice-lines-dummies/count} : count all the salesInvoiceLinesDummies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSalesInvoiceLinesDummies(SalesInvoiceLinesDummyCriteria criteria) {
        LOG.debug("REST request to count SalesInvoiceLinesDummies by criteria: {}", criteria);
        return ResponseEntity.ok().body(salesInvoiceLinesDummyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sales-invoice-lines-dummies/:id} : get the "id" salesInvoiceLinesDummy.
     *
     * @param id the id of the salesInvoiceLinesDummy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesInvoiceLinesDummy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalesInvoiceLinesDummy> getSalesInvoiceLinesDummy(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SalesInvoiceLinesDummy : {}", id);
        Optional<SalesInvoiceLinesDummy> salesInvoiceLinesDummy = salesInvoiceLinesDummyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesInvoiceLinesDummy);
    }

    /**
     * {@code DELETE  /sales-invoice-lines-dummies/:id} : delete the "id" salesInvoiceLinesDummy.
     *
     * @param id the id of the salesInvoiceLinesDummy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesInvoiceLinesDummy(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SalesInvoiceLinesDummy : {}", id);
        salesInvoiceLinesDummyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
