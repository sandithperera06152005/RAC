package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Salesinvoice;
import com.heavenscode.rac.repository.SalesinvoiceRepository;
import com.heavenscode.rac.service.SalesinvoiceQueryService;
import com.heavenscode.rac.service.SalesinvoiceService;
import com.heavenscode.rac.service.criteria.SalesinvoiceCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Salesinvoice}.
 */
@RestController
@RequestMapping("/api/salesinvoices")
public class SalesinvoiceResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalesinvoiceResource.class);

    private static final String ENTITY_NAME = "salesinvoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesinvoiceService salesinvoiceService;

    private final SalesinvoiceRepository salesinvoiceRepository;

    private final SalesinvoiceQueryService salesinvoiceQueryService;

    public SalesinvoiceResource(
        SalesinvoiceService salesinvoiceService,
        SalesinvoiceRepository salesinvoiceRepository,
        SalesinvoiceQueryService salesinvoiceQueryService
    ) {
        this.salesinvoiceService = salesinvoiceService;
        this.salesinvoiceRepository = salesinvoiceRepository;
        this.salesinvoiceQueryService = salesinvoiceQueryService;
    }

    /**
     * {@code POST  /salesinvoices} : Create a new salesinvoice.
     *
     * @param salesinvoice the salesinvoice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesinvoice, or with status {@code 400 (Bad Request)} if the salesinvoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Salesinvoice> createSalesinvoice(@RequestBody Salesinvoice salesinvoice) throws URISyntaxException {
        LOG.debug("REST request to save Salesinvoice : {}", salesinvoice);
        if (salesinvoice.getId() != null) {
            throw new BadRequestAlertException("A new salesinvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        salesinvoice = salesinvoiceService.save(salesinvoice);
        return ResponseEntity.created(new URI("/api/salesinvoices/" + salesinvoice.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, salesinvoice.getId().toString()))
            .body(salesinvoice);
    }

    /**
     * {@code PUT  /salesinvoices/:id} : Updates an existing salesinvoice.
     *
     * @param id the id of the salesinvoice to save.
     * @param salesinvoice the salesinvoice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesinvoice,
     * or with status {@code 400 (Bad Request)} if the salesinvoice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesinvoice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Salesinvoice> updateSalesinvoice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Salesinvoice salesinvoice
    ) throws URISyntaxException {
        LOG.debug("REST request to update Salesinvoice : {}, {}", id, salesinvoice);
        if (salesinvoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesinvoice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesinvoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salesinvoice = salesinvoiceService.update(salesinvoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesinvoice.getId().toString()))
            .body(salesinvoice);
    }

    /**
     * {@code PATCH  /salesinvoices/:id} : Partial updates given fields of an existing salesinvoice, field will ignore if it is null
     *
     * @param id the id of the salesinvoice to save.
     * @param salesinvoice the salesinvoice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesinvoice,
     * or with status {@code 400 (Bad Request)} if the salesinvoice is not valid,
     * or with status {@code 404 (Not Found)} if the salesinvoice is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesinvoice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Salesinvoice> partialUpdateSalesinvoice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Salesinvoice salesinvoice
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Salesinvoice partially : {}, {}", id, salesinvoice);
        if (salesinvoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesinvoice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesinvoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Salesinvoice> result = salesinvoiceService.partialUpdate(salesinvoice);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesinvoice.getId().toString())
        );
    }

    /**
     * {@code GET  /salesinvoices} : get all the salesinvoices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesinvoices in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Salesinvoice>> getAllSalesinvoices(
        SalesinvoiceCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Salesinvoices by criteria: {}", criteria);

        Page<Salesinvoice> page = salesinvoiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /salesinvoices/count} : count all the salesinvoices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSalesinvoices(SalesinvoiceCriteria criteria) {
        LOG.debug("REST request to count Salesinvoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(salesinvoiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /salesinvoices/:id} : get the "id" salesinvoice.
     *
     * @param id the id of the salesinvoice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesinvoice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Salesinvoice> getSalesinvoice(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Salesinvoice : {}", id);
        Optional<Salesinvoice> salesinvoice = salesinvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesinvoice);
    }

    /**
     * {@code DELETE  /salesinvoices/:id} : delete the "id" salesinvoice.
     *
     * @param id the id of the salesinvoice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesinvoice(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Salesinvoice : {}", id);
        salesinvoiceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
