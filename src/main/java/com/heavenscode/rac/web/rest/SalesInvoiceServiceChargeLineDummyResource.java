package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummy;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineDummyRepository;
import com.heavenscode.rac.service.SalesInvoiceServiceChargeLineDummyQueryService;
import com.heavenscode.rac.service.SalesInvoiceServiceChargeLineDummyService;
import com.heavenscode.rac.service.criteria.SalesInvoiceServiceChargeLineDummyCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummy}.
 */
@RestController
@RequestMapping("/api/sales-invoice-service-charge-line-dummies")
public class SalesInvoiceServiceChargeLineDummyResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalesInvoiceServiceChargeLineDummyResource.class);

    private static final String ENTITY_NAME = "salesInvoiceServiceChargeLineDummy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesInvoiceServiceChargeLineDummyService salesInvoiceServiceChargeLineDummyService;

    private final SalesInvoiceServiceChargeLineDummyRepository salesInvoiceServiceChargeLineDummyRepository;

    private final SalesInvoiceServiceChargeLineDummyQueryService salesInvoiceServiceChargeLineDummyQueryService;

    public SalesInvoiceServiceChargeLineDummyResource(
        SalesInvoiceServiceChargeLineDummyService salesInvoiceServiceChargeLineDummyService,
        SalesInvoiceServiceChargeLineDummyRepository salesInvoiceServiceChargeLineDummyRepository,
        SalesInvoiceServiceChargeLineDummyQueryService salesInvoiceServiceChargeLineDummyQueryService
    ) {
        this.salesInvoiceServiceChargeLineDummyService = salesInvoiceServiceChargeLineDummyService;
        this.salesInvoiceServiceChargeLineDummyRepository = salesInvoiceServiceChargeLineDummyRepository;
        this.salesInvoiceServiceChargeLineDummyQueryService = salesInvoiceServiceChargeLineDummyQueryService;
    }

    /**
     * {@code POST  /sales-invoice-service-charge-line-dummies} : Create a new salesInvoiceServiceChargeLineDummy.
     *
     * @param salesInvoiceServiceChargeLineDummy the salesInvoiceServiceChargeLineDummy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salesInvoiceServiceChargeLineDummy, or with status {@code 400 (Bad Request)} if the salesInvoiceServiceChargeLineDummy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SalesInvoiceServiceChargeLineDummy> createSalesInvoiceServiceChargeLineDummy(
        @RequestBody SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy
    ) throws URISyntaxException {
        LOG.debug("REST request to save SalesInvoiceServiceChargeLineDummy : {}", salesInvoiceServiceChargeLineDummy);
        if (salesInvoiceServiceChargeLineDummy.getId() != null) {
            throw new BadRequestAlertException(
                "A new salesInvoiceServiceChargeLineDummy cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        salesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyService.save(salesInvoiceServiceChargeLineDummy);
        return ResponseEntity.created(
            new URI("/api/sales-invoice-service-charge-line-dummies/" + salesInvoiceServiceChargeLineDummy.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    salesInvoiceServiceChargeLineDummy.getId().toString()
                )
            )
            .body(salesInvoiceServiceChargeLineDummy);
    }

    /**
     * {@code PUT  /sales-invoice-service-charge-line-dummies/:id} : Updates an existing salesInvoiceServiceChargeLineDummy.
     *
     * @param id the id of the salesInvoiceServiceChargeLineDummy to save.
     * @param salesInvoiceServiceChargeLineDummy the salesInvoiceServiceChargeLineDummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceServiceChargeLineDummy,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceServiceChargeLineDummy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceServiceChargeLineDummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalesInvoiceServiceChargeLineDummy> updateSalesInvoiceServiceChargeLineDummy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy
    ) throws URISyntaxException {
        LOG.debug("REST request to update SalesInvoiceServiceChargeLineDummy : {}, {}", id, salesInvoiceServiceChargeLineDummy);
        if (salesInvoiceServiceChargeLineDummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceServiceChargeLineDummy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceServiceChargeLineDummyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyService.update(salesInvoiceServiceChargeLineDummy);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    salesInvoiceServiceChargeLineDummy.getId().toString()
                )
            )
            .body(salesInvoiceServiceChargeLineDummy);
    }

    /**
     * {@code PATCH  /sales-invoice-service-charge-line-dummies/:id} : Partial updates given fields of an existing salesInvoiceServiceChargeLineDummy, field will ignore if it is null
     *
     * @param id the id of the salesInvoiceServiceChargeLineDummy to save.
     * @param salesInvoiceServiceChargeLineDummy the salesInvoiceServiceChargeLineDummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salesInvoiceServiceChargeLineDummy,
     * or with status {@code 400 (Bad Request)} if the salesInvoiceServiceChargeLineDummy is not valid,
     * or with status {@code 404 (Not Found)} if the salesInvoiceServiceChargeLineDummy is not found,
     * or with status {@code 500 (Internal Server Error)} if the salesInvoiceServiceChargeLineDummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalesInvoiceServiceChargeLineDummy> partialUpdateSalesInvoiceServiceChargeLineDummy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update SalesInvoiceServiceChargeLineDummy partially : {}, {}",
            id,
            salesInvoiceServiceChargeLineDummy
        );
        if (salesInvoiceServiceChargeLineDummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salesInvoiceServiceChargeLineDummy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salesInvoiceServiceChargeLineDummyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalesInvoiceServiceChargeLineDummy> result = salesInvoiceServiceChargeLineDummyService.partialUpdate(
            salesInvoiceServiceChargeLineDummy
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, salesInvoiceServiceChargeLineDummy.getId().toString())
        );
    }

    /**
     * {@code GET  /sales-invoice-service-charge-line-dummies} : get all the salesInvoiceServiceChargeLineDummies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salesInvoiceServiceChargeLineDummies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SalesInvoiceServiceChargeLineDummy>> getAllSalesInvoiceServiceChargeLineDummies(
        SalesInvoiceServiceChargeLineDummyCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SalesInvoiceServiceChargeLineDummies by criteria: {}", criteria);

        Page<SalesInvoiceServiceChargeLineDummy> page = salesInvoiceServiceChargeLineDummyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sales-invoice-service-charge-line-dummies/count} : count all the salesInvoiceServiceChargeLineDummies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSalesInvoiceServiceChargeLineDummies(SalesInvoiceServiceChargeLineDummyCriteria criteria) {
        LOG.debug("REST request to count SalesInvoiceServiceChargeLineDummies by criteria: {}", criteria);
        return ResponseEntity.ok().body(salesInvoiceServiceChargeLineDummyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sales-invoice-service-charge-line-dummies/:id} : get the "id" salesInvoiceServiceChargeLineDummy.
     *
     * @param id the id of the salesInvoiceServiceChargeLineDummy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salesInvoiceServiceChargeLineDummy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalesInvoiceServiceChargeLineDummy> getSalesInvoiceServiceChargeLineDummy(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SalesInvoiceServiceChargeLineDummy : {}", id);
        Optional<SalesInvoiceServiceChargeLineDummy> salesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(salesInvoiceServiceChargeLineDummy);
    }

    /**
     * {@code DELETE  /sales-invoice-service-charge-line-dummies/:id} : delete the "id" salesInvoiceServiceChargeLineDummy.
     *
     * @param id the id of the salesInvoiceServiceChargeLineDummy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesInvoiceServiceChargeLineDummy(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SalesInvoiceServiceChargeLineDummy : {}", id);
        salesInvoiceServiceChargeLineDummyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
