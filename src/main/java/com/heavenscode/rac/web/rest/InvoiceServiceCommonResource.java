package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.InvoiceServiceCommon;
import com.heavenscode.rac.repository.InvoiceServiceCommonRepository;
import com.heavenscode.rac.service.InvoiceServiceCommonQueryService;
import com.heavenscode.rac.service.InvoiceServiceCommonService;
import com.heavenscode.rac.service.criteria.InvoiceServiceCommonCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.InvoiceServiceCommon}.
 */
@RestController
@RequestMapping("/api/invoice-service-commons")
public class InvoiceServiceCommonResource {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceCommonResource.class);

    private static final String ENTITY_NAME = "invoiceServiceCommon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceServiceCommonService invoiceServiceCommonService;

    private final InvoiceServiceCommonRepository invoiceServiceCommonRepository;

    private final InvoiceServiceCommonQueryService invoiceServiceCommonQueryService;

    public InvoiceServiceCommonResource(
        InvoiceServiceCommonService invoiceServiceCommonService,
        InvoiceServiceCommonRepository invoiceServiceCommonRepository,
        InvoiceServiceCommonQueryService invoiceServiceCommonQueryService
    ) {
        this.invoiceServiceCommonService = invoiceServiceCommonService;
        this.invoiceServiceCommonRepository = invoiceServiceCommonRepository;
        this.invoiceServiceCommonQueryService = invoiceServiceCommonQueryService;
    }

    /**
     * {@code POST  /invoice-service-commons} : Create a new invoiceServiceCommon.
     *
     * @param invoiceServiceCommon the invoiceServiceCommon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceServiceCommon, or with status {@code 400 (Bad Request)} if the invoiceServiceCommon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InvoiceServiceCommon> createInvoiceServiceCommon(@RequestBody InvoiceServiceCommon invoiceServiceCommon)
        throws URISyntaxException {
        LOG.debug("REST request to save InvoiceServiceCommon : {}", invoiceServiceCommon);
        if (invoiceServiceCommon.getId() != null) {
            throw new BadRequestAlertException("A new invoiceServiceCommon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        invoiceServiceCommon = invoiceServiceCommonService.save(invoiceServiceCommon);
        return ResponseEntity.created(new URI("/api/invoice-service-commons/" + invoiceServiceCommon.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, invoiceServiceCommon.getId().toString()))
            .body(invoiceServiceCommon);
    }

    /**
     * {@code PUT  /invoice-service-commons/:id} : Updates an existing invoiceServiceCommon.
     *
     * @param id the id of the invoiceServiceCommon to save.
     * @param invoiceServiceCommon the invoiceServiceCommon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceServiceCommon,
     * or with status {@code 400 (Bad Request)} if the invoiceServiceCommon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceServiceCommon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceServiceCommon> updateInvoiceServiceCommon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceServiceCommon invoiceServiceCommon
    ) throws URISyntaxException {
        LOG.debug("REST request to update InvoiceServiceCommon : {}, {}", id, invoiceServiceCommon);
        if (invoiceServiceCommon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceServiceCommon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceServiceCommonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        invoiceServiceCommon = invoiceServiceCommonService.update(invoiceServiceCommon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, invoiceServiceCommon.getId().toString()))
            .body(invoiceServiceCommon);
    }

    /**
     * {@code PATCH  /invoice-service-commons/:id} : Partial updates given fields of an existing invoiceServiceCommon, field will ignore if it is null
     *
     * @param id the id of the invoiceServiceCommon to save.
     * @param invoiceServiceCommon the invoiceServiceCommon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceServiceCommon,
     * or with status {@code 400 (Bad Request)} if the invoiceServiceCommon is not valid,
     * or with status {@code 404 (Not Found)} if the invoiceServiceCommon is not found,
     * or with status {@code 500 (Internal Server Error)} if the invoiceServiceCommon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InvoiceServiceCommon> partialUpdateInvoiceServiceCommon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceServiceCommon invoiceServiceCommon
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update InvoiceServiceCommon partially : {}, {}", id, invoiceServiceCommon);
        if (invoiceServiceCommon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceServiceCommon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceServiceCommonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InvoiceServiceCommon> result = invoiceServiceCommonService.partialUpdate(invoiceServiceCommon);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, invoiceServiceCommon.getId().toString())
        );
    }

    /**
     * {@code GET  /invoice-service-commons} : get all the invoiceServiceCommons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceServiceCommons in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InvoiceServiceCommon>> getAllInvoiceServiceCommons(
        InvoiceServiceCommonCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get InvoiceServiceCommons by criteria: {}", criteria);

        Page<InvoiceServiceCommon> page = invoiceServiceCommonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoice-service-commons/count} : count all the invoiceServiceCommons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInvoiceServiceCommons(InvoiceServiceCommonCriteria criteria) {
        LOG.debug("REST request to count InvoiceServiceCommons by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoiceServiceCommonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoice-service-commons/:id} : get the "id" invoiceServiceCommon.
     *
     * @param id the id of the invoiceServiceCommon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceServiceCommon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceServiceCommon> getInvoiceServiceCommon(@PathVariable("id") Long id) {
        LOG.debug("REST request to get InvoiceServiceCommon : {}", id);
        Optional<InvoiceServiceCommon> invoiceServiceCommon = invoiceServiceCommonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceServiceCommon);
    }

    /**
     * {@code DELETE  /invoice-service-commons/:id} : delete the "id" invoiceServiceCommon.
     *
     * @param id the id of the invoiceServiceCommon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceServiceCommon(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete InvoiceServiceCommon : {}", id);
        invoiceServiceCommonService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
