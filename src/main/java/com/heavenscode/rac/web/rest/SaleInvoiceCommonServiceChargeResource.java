package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeRepository;
import com.heavenscode.rac.service.LegacyInvoiceChildrenReadService;
import com.heavenscode.rac.service.SaleInvoiceCommonServiceChargeQueryService;
import com.heavenscode.rac.service.SaleInvoiceCommonServiceChargeService;
import com.heavenscode.rac.service.SalesInvoiceChildInsertService;
import com.heavenscode.rac.service.criteria.SaleInvoiceCommonServiceChargeCriteria;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge}.
 */
@RestController
@RequestMapping("/api/sale-invoice-common-service-charges")
public class SaleInvoiceCommonServiceChargeResource {

    private static final Logger LOG = LoggerFactory.getLogger(SaleInvoiceCommonServiceChargeResource.class);

    private static final String ENTITY_NAME = "saleInvoiceCommonServiceCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaleInvoiceCommonServiceChargeService saleInvoiceCommonServiceChargeService;

    private final SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository;

    private final SaleInvoiceCommonServiceChargeQueryService saleInvoiceCommonServiceChargeQueryService;
    private final LegacyInvoiceChildrenReadService legacyInvoiceChildrenReadService;
    private final SalesInvoiceChildInsertService salesInvoiceChildInsertService;

    public SaleInvoiceCommonServiceChargeResource(
        SaleInvoiceCommonServiceChargeService saleInvoiceCommonServiceChargeService,
        SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository,
        SaleInvoiceCommonServiceChargeQueryService saleInvoiceCommonServiceChargeQueryService,
        LegacyInvoiceChildrenReadService legacyInvoiceChildrenReadService,
        SalesInvoiceChildInsertService salesInvoiceChildInsertService
    ) {
        this.saleInvoiceCommonServiceChargeService = saleInvoiceCommonServiceChargeService;
        this.saleInvoiceCommonServiceChargeRepository = saleInvoiceCommonServiceChargeRepository;
        this.saleInvoiceCommonServiceChargeQueryService = saleInvoiceCommonServiceChargeQueryService;
        this.legacyInvoiceChildrenReadService = legacyInvoiceChildrenReadService;
        this.salesInvoiceChildInsertService = salesInvoiceChildInsertService;
    }

    /**
     * {@code POST  /sale-invoice-common-service-charges} : Create a new saleInvoiceCommonServiceCharge.
     *
     * @param saleInvoiceCommonServiceCharge the saleInvoiceCommonServiceCharge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saleInvoiceCommonServiceCharge, or with status {@code 400 (Bad Request)} if the saleInvoiceCommonServiceCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SaleInvoiceCommonServiceCharge> createSaleInvoiceCommonServiceCharge(
        @RequestBody SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge
    ) throws URISyntaxException {
        LOG.debug("REST request to save SaleInvoiceCommonServiceCharge : {}", saleInvoiceCommonServiceCharge);
        if (saleInvoiceCommonServiceCharge.getId() != null) {
            throw new BadRequestAlertException("A new saleInvoiceCommonServiceCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        saleInvoiceCommonServiceCharge = salesInvoiceChildInsertService.insertCommonServiceCharge(saleInvoiceCommonServiceCharge);
        return ResponseEntity.created(new URI("/api/sale-invoice-common-service-charges")).body(saleInvoiceCommonServiceCharge);
    }

    /**
     * {@code PUT  /sale-invoice-common-service-charges/:id} : Updates an existing saleInvoiceCommonServiceCharge.
     *
     * @param id the id of the saleInvoiceCommonServiceCharge to save.
     * @param saleInvoiceCommonServiceCharge the saleInvoiceCommonServiceCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleInvoiceCommonServiceCharge,
     * or with status {@code 400 (Bad Request)} if the saleInvoiceCommonServiceCharge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saleInvoiceCommonServiceCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SaleInvoiceCommonServiceCharge> updateSaleInvoiceCommonServiceCharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge
    ) throws URISyntaxException {
        LOG.debug("REST request to update SaleInvoiceCommonServiceCharge : {}, {}", id, saleInvoiceCommonServiceCharge);
        if (saleInvoiceCommonServiceCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleInvoiceCommonServiceCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleInvoiceCommonServiceChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        saleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeService.update(saleInvoiceCommonServiceCharge);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleInvoiceCommonServiceCharge.getId().toString())
            )
            .body(saleInvoiceCommonServiceCharge);
    }

    /**
     * {@code PATCH  /sale-invoice-common-service-charges/:id} : Partial updates given fields of an existing saleInvoiceCommonServiceCharge, field will ignore if it is null
     *
     * @param id the id of the saleInvoiceCommonServiceCharge to save.
     * @param saleInvoiceCommonServiceCharge the saleInvoiceCommonServiceCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saleInvoiceCommonServiceCharge,
     * or with status {@code 400 (Bad Request)} if the saleInvoiceCommonServiceCharge is not valid,
     * or with status {@code 404 (Not Found)} if the saleInvoiceCommonServiceCharge is not found,
     * or with status {@code 500 (Internal Server Error)} if the saleInvoiceCommonServiceCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaleInvoiceCommonServiceCharge> partialUpdateSaleInvoiceCommonServiceCharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SaleInvoiceCommonServiceCharge partially : {}, {}", id, saleInvoiceCommonServiceCharge);
        if (saleInvoiceCommonServiceCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saleInvoiceCommonServiceCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saleInvoiceCommonServiceChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaleInvoiceCommonServiceCharge> result = saleInvoiceCommonServiceChargeService.partialUpdate(
            saleInvoiceCommonServiceCharge
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, saleInvoiceCommonServiceCharge.getId().toString())
        );
    }

    /**
     * {@code GET  /sale-invoice-common-service-charges} : get all the saleInvoiceCommonServiceCharges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saleInvoiceCommonServiceCharges in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SaleInvoiceCommonServiceCharge>> getAllSaleInvoiceCommonServiceCharges(
        SaleInvoiceCommonServiceChargeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SaleInvoiceCommonServiceCharges by criteria: {}", criteria);

        Page<SaleInvoiceCommonServiceCharge> page = saleInvoiceCommonServiceChargeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<Map<String, Object>>> getCommonChargesByInvoiceId(@PathVariable("invoiceId") Integer invoiceId) {
        LOG.debug("REST request to get SaleInvoiceCommonServiceCharge by invoiceId : {}", invoiceId);
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("id", "id");
        columns.put("name", "name");
        columns.put("description", "description");
        columns.put("value", "value");
        columns.put("optionid", "optionid");
        columns.put("mainid", "mainid");
        columns.put("code", "code");
        columns.put("discount", "discount");
        columns.put("servicePrice", "serviceprice");

        return ResponseEntity.ok(
            legacyInvoiceChildrenReadService.findByInvoiceId("saleinvoicecommonservicecharge", "invoiceid", columns, invoiceId)
        );
    }

    /**
     * {@code GET  /sale-invoice-common-service-charges/count} : count all the saleInvoiceCommonServiceCharges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSaleInvoiceCommonServiceCharges(SaleInvoiceCommonServiceChargeCriteria criteria) {
        LOG.debug("REST request to count SaleInvoiceCommonServiceCharges by criteria: {}", criteria);
        return ResponseEntity.ok().body(saleInvoiceCommonServiceChargeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sale-invoice-common-service-charges/:id} : get the "id" saleInvoiceCommonServiceCharge.
     *
     * @param id the id of the saleInvoiceCommonServiceCharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saleInvoiceCommonServiceCharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SaleInvoiceCommonServiceCharge> getSaleInvoiceCommonServiceCharge(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SaleInvoiceCommonServiceCharge : {}", id);
        Optional<SaleInvoiceCommonServiceCharge> saleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saleInvoiceCommonServiceCharge);
    }

    /**
     * {@code DELETE  /sale-invoice-common-service-charges/:id} : delete the "id" saleInvoiceCommonServiceCharge.
     *
     * @param id the id of the saleInvoiceCommonServiceCharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleInvoiceCommonServiceCharge(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SaleInvoiceCommonServiceCharge : {}", id);
        saleInvoiceCommonServiceChargeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
