package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SalesInvoiceDummy;
import com.heavenscode.rac.repository.SalesInvoiceDummyRepository;
import com.heavenscode.rac.service.SalesInvoiceDummyQueryService;
import com.heavenscode.rac.service.SalesInvoiceDummyService;
import com.heavenscode.rac.service.criteria.SalesInvoiceDummyCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.SalesInvoiceDummy}.
 */
@RestController
@RequestMapping("/api/sales-invoice-dummies")
public class SalesInvoiceDummyResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceDummyResource.class);

    private static final String ENTITY_NAME = "salesInvoiceDummy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesInvoiceDummyService salesInvoiceDummyService;

    private final SalesInvoiceDummyRepository salesInvoiceDummyRepository;

    private final SalesInvoiceDummyQueryService salesInvoiceDummyQueryService;

    public SalesInvoiceDummyResource(
        SalesInvoiceDummyService salesInvoiceDummyService,
        SalesInvoiceDummyRepository salesInvoiceDummyRepository,
        SalesInvoiceDummyQueryService salesInvoiceDummyQueryService
    ) {
        this.salesInvoiceDummyService = salesInvoiceDummyService;
        this.salesInvoiceDummyRepository = salesInvoiceDummyRepository;
        this.salesInvoiceDummyQueryService = salesInvoiceDummyQueryService;
    }

    /**
     * {@code POST  /sales-invoice-dummies} : Create a new salesInvoiceDummy.
     *
     * @param salesInvoiceDummy the salesInvoiceDummy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesInvoiceDummy, or with status {@code 400 (Bad Request)} if the salesInvoiceDummy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SalesInvoiceDummy> createSalesInvoiceDummy(@RequestBody SalesInvoiceDummy salesInvoiceDummy)
        throws URISyntaxException {
        LOG.debug("REST request to save SalesInvoiceDummy : {}", salesInvoiceDummy);
        if (salesInvoiceDummy.getId() != null) {
            throw new BadRequestAlertException("A new salesInvoiceDummy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        salesInvoiceDummy = salesInvoiceDummyService.save(salesInvoiceDummy);
        return ResponseEntity.created(new URI("/api/sales-invoice-dummies/" + salesInvoiceDummy.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, salesInvoiceDummy.getId().toString()))
            .body(salesInvoiceDummy);
    }

    /**
     * {@code PUT  /sales-invoice-dummies/:id} : Updates an existing salesInvoiceDummy.
     *
     * @param id the id of the salesInvoiceDummy to save.
     * @param salesInvoiceDummy the salesInvoiceDummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceDummy,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceDummy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceDummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalesInvoiceDummy> updateSalesInvoiceDummy(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody SalesInvoiceDummy salesInvoiceDummy
    ) throws URISyntaxException {
        LOG.debug("REST request to update SalesInvoiceDummy : {}, {}", id, salesInvoiceDummy);
        if (salesInvoiceDummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceDummy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceDummyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salesInvoiceDummy = salesInvoiceDummyService.update(salesInvoiceDummy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceDummy.getId().toString()))
            .body(salesInvoiceDummy);
    }

    /**
     * {@code PATCH  /sales-invoice-dummies/:id} : Partial updates given fields of an existing salesInvoiceDummy, field will ignore if it is null
     *
     * @param id the id of the salesInvoiceDummy to save.
     * @param salesInvoiceDummy the salesInvoiceDummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceDummy,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceDummy is not valid,
     * or with status {@code 404 (Not Found)} if the salesInvoiceDummy is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceDummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesInvoiceDummy> partialUpdateSalesInvoiceDummy(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody SalesInvoiceDummy salesInvoiceDummy
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SalesInvoiceDummy partially : {}, {}", id, salesInvoiceDummy);
        if (salesInvoiceDummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceDummy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceDummyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesInvoiceDummy> result = salesInvoiceDummyService.partialUpdate(salesInvoiceDummy);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceDummy.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-invoice-dummies} : get all the salesInvoiceDummies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesInvoiceDummies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SalesInvoiceDummy>> getAllSalesInvoiceDummies(
        SalesInvoiceDummyCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SalesInvoiceDummies by criteria: {}", criteria);

        Page<SalesInvoiceDummy> page = salesInvoiceDummyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales-invoice-dummies/count} : count all the salesInvoiceDummies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSalesInvoiceDummies(SalesInvoiceDummyCriteria criteria) {
        LOG.debug("REST request to count SalesInvoiceDummies by criteria: {}", criteria);
        return ResponseEntity.ok().body(salesInvoiceDummyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sales-invoice-dummies/:id} : get the "id" salesInvoiceDummy.
     *
     * @param id the id of the salesInvoiceDummy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesInvoiceDummy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalesInvoiceDummy> getSalesInvoiceDummy(@PathVariable("id") Integer id) {
        LOG.debug("REST request to get SalesInvoiceDummy : {}", id);
        Optional<SalesInvoiceDummy> salesInvoiceDummy = salesInvoiceDummyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesInvoiceDummy);
    }

    /**
     * {@code DELETE  /sales-invoice-dummies/:id} : delete the "id" salesInvoiceDummy.
     *
     * @param id the id of the salesInvoiceDummy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesInvoiceDummy(@PathVariable("id") Integer id) {
        LOG.debug("REST request to delete SalesInvoiceDummy : {}", id);
        salesInvoiceDummyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
