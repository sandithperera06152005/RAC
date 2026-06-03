package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.InvoiceServiceCharge;
import com.heavenscode.rac.repository.InvoiceServiceChargeRepository;
import com.heavenscode.rac.service.InvoiceServiceChargeQueryService;
import com.heavenscode.rac.service.InvoiceServiceChargeService;
import com.heavenscode.rac.service.criteria.InvoiceServiceChargeCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.InvoiceServiceCharge}.
 */
@RestController
@RequestMapping("/api/invoice-service-charges")
public class InvoiceServiceChargeResource {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceChargeResource.class);

    private static final String ENTITY_NAME = "invoiceServiceCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceServiceChargeService invoiceServiceChargeService;

    private final InvoiceServiceChargeRepository invoiceServiceChargeRepository;

    private final InvoiceServiceChargeQueryService invoiceServiceChargeQueryService;

    public InvoiceServiceChargeResource(
        InvoiceServiceChargeService invoiceServiceChargeService,
        InvoiceServiceChargeRepository invoiceServiceChargeRepository,
        InvoiceServiceChargeQueryService invoiceServiceChargeQueryService
    ) {
        this.invoiceServiceChargeService = invoiceServiceChargeService;
        this.invoiceServiceChargeRepository = invoiceServiceChargeRepository;
        this.invoiceServiceChargeQueryService = invoiceServiceChargeQueryService;
    }

    /**
     * {@code POST  /invoice-service-charges} : Create a new invoiceServiceCharge.
     *
     * @param invoiceServiceCharge the invoiceServiceCharge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceServiceCharge, or with status {@code 400 (Bad Request)} if the invoiceServiceCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InvoiceServiceCharge> createInvoiceServiceCharge(@RequestBody InvoiceServiceCharge invoiceServiceCharge)
        throws URISyntaxException {
        LOG.debug("REST request to save InvoiceServiceCharge : {}", invoiceServiceCharge);
        if (invoiceServiceCharge.getId() != null) {
            throw new BadRequestAlertException("A new invoiceServiceCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        invoiceServiceCharge = invoiceServiceChargeService.save(invoiceServiceCharge);
        return ResponseEntity.created(new URI("/api/invoice-service-charges/" + invoiceServiceCharge.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, invoiceServiceCharge.getId().toString()))
            .body(invoiceServiceCharge);
    }

    /**
     * {@code PUT  /invoice-service-charges/:id} : Updates an existing invoiceServiceCharge.
     *
     * @param id the id of the invoiceServiceCharge to save.
     * @param invoiceServiceCharge the invoiceServiceCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceServiceCharge,
     * or with status {@code 400 (Bad Request)} if the invoiceServiceCharge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceServiceCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceServiceCharge> updateInvoiceServiceCharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceServiceCharge invoiceServiceCharge
    ) throws URISyntaxException {
        LOG.debug("REST request to update InvoiceServiceCharge : {}, {}", id, invoiceServiceCharge);
        if (invoiceServiceCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceServiceCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceServiceChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        invoiceServiceCharge = invoiceServiceChargeService.update(invoiceServiceCharge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, invoiceServiceCharge.getId().toString()))
            .body(invoiceServiceCharge);
    }

    /**
     * {@code PATCH  /invoice-service-charges/:id} : Partial updates given fields of an existing invoiceServiceCharge, field will ignore if it is null
     *
     * @param id the id of the invoiceServiceCharge to save.
     * @param invoiceServiceCharge the invoiceServiceCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceServiceCharge,
     * or with status {@code 400 (Bad Request)} if the invoiceServiceCharge is not valid,
     * or with status {@code 404 (Not Found)} if the invoiceServiceCharge is not found,
     * or with status {@code 500 (Internal Server Error)} if the invoiceServiceCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InvoiceServiceCharge> partialUpdateInvoiceServiceCharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceServiceCharge invoiceServiceCharge
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InvoiceServiceCharge partially : {}, {}", id, invoiceServiceCharge);
        if (invoiceServiceCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceServiceCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceServiceChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InvoiceServiceCharge> result = invoiceServiceChargeService.partialUpdate(invoiceServiceCharge);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, invoiceServiceCharge.getId().toString())
        );
    }

    /**
     * {@code GET  /invoice-service-charges} : get all the invoiceServiceCharges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceServiceCharges in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InvoiceServiceCharge>> getAllInvoiceServiceCharges(
        InvoiceServiceChargeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get InvoiceServiceCharges by criteria: {}", criteria);

        Page<InvoiceServiceCharge> page = invoiceServiceChargeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoice-service-charges/count} : count all the invoiceServiceCharges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInvoiceServiceCharges(InvoiceServiceChargeCriteria criteria) {
        LOG.debug("REST request to count InvoiceServiceCharges by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoiceServiceChargeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoice-service-charges/:id} : get the "id" invoiceServiceCharge.
     *
     * @param id the id of the invoiceServiceCharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceServiceCharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceServiceCharge> getInvoiceServiceCharge(@PathVariable("id") Long id) {
        LOG.debug("REST request to get InvoiceServiceCharge : {}", id);
        Optional<InvoiceServiceCharge> invoiceServiceCharge = invoiceServiceChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceServiceCharge);
    }

    /**
     * {@code DELETE  /invoice-service-charges/:id} : delete the "id" invoiceServiceCharge.
     *
     * @param id the id of the invoiceServiceCharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceServiceCharge(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete InvoiceServiceCharge : {}", id);
        invoiceServiceChargeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
