package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.VoucherPaymentsDetails;
import com.heavenscode.rac.repository.VoucherPaymentsDetailsRepository;
import com.heavenscode.rac.service.VoucherPaymentsDetailsQueryService;
import com.heavenscode.rac.service.VoucherPaymentsDetailsService;
import com.heavenscode.rac.service.criteria.VoucherPaymentsDetailsCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.VoucherPaymentsDetails}.
 */
@RestController
@RequestMapping("/api/voucher-payments-details")
public class VoucherPaymentsDetailsResource {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherPaymentsDetailsResource.class);

    private static final String ENTITY_NAME = "voucherPaymentsDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoucherPaymentsDetailsService voucherPaymentsDetailsService;

    private final VoucherPaymentsDetailsRepository voucherPaymentsDetailsRepository;

    private final VoucherPaymentsDetailsQueryService voucherPaymentsDetailsQueryService;

    public VoucherPaymentsDetailsResource(
        VoucherPaymentsDetailsService voucherPaymentsDetailsService,
        VoucherPaymentsDetailsRepository voucherPaymentsDetailsRepository,
        VoucherPaymentsDetailsQueryService voucherPaymentsDetailsQueryService
    ) {
        this.voucherPaymentsDetailsService = voucherPaymentsDetailsService;
        this.voucherPaymentsDetailsRepository = voucherPaymentsDetailsRepository;
        this.voucherPaymentsDetailsQueryService = voucherPaymentsDetailsQueryService;
    }

    /**
     * {@code POST  /voucher-payments-details} : Create a new voucherPaymentsDetails.
     *
     * @param voucherPaymentsDetails the voucherPaymentsDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voucherPaymentsDetails, or with status {@code 400 (Bad Request)} if the voucherPaymentsDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VoucherPaymentsDetails> createVoucherPaymentsDetails(@RequestBody VoucherPaymentsDetails voucherPaymentsDetails)
        throws URISyntaxException {
        LOG.debug("REST request to save VoucherPaymentsDetails : {}", voucherPaymentsDetails);
        if (voucherPaymentsDetails.getId() != null) {
            throw new BadRequestAlertException("A new voucherPaymentsDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        voucherPaymentsDetails = voucherPaymentsDetailsService.save(voucherPaymentsDetails);
        return ResponseEntity.created(new URI("/api/voucher-payments-details/" + voucherPaymentsDetails.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, voucherPaymentsDetails.getId().toString()))
            .body(voucherPaymentsDetails);
    }

    /**
     * {@code PUT  /voucher-payments-details/:id} : Updates an existing voucherPaymentsDetails.
     *
     * @param id the id of the voucherPaymentsDetails to save.
     * @param voucherPaymentsDetails the voucherPaymentsDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherPaymentsDetails,
     * or with status {@code 400 (Bad Request)} if the voucherPaymentsDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voucherPaymentsDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VoucherPaymentsDetails> updateVoucherPaymentsDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoucherPaymentsDetails voucherPaymentsDetails
    ) throws URISyntaxException {
        LOG.debug("REST request to update VoucherPaymentsDetails : {}, {}", id, voucherPaymentsDetails);
        if (voucherPaymentsDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherPaymentsDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherPaymentsDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        voucherPaymentsDetails = voucherPaymentsDetailsService.update(voucherPaymentsDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherPaymentsDetails.getId().toString()))
            .body(voucherPaymentsDetails);
    }

    /**
     * {@code PATCH  /voucher-payments-details/:id} : Partial updates given fields of an existing voucherPaymentsDetails, field will ignore if it is null
     *
     * @param id the id of the voucherPaymentsDetails to save.
     * @param voucherPaymentsDetails the voucherPaymentsDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherPaymentsDetails,
     * or with status {@code 400 (Bad Request)} if the voucherPaymentsDetails is not valid,
     * or with status {@code 404 (Not Found)} if the voucherPaymentsDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the voucherPaymentsDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VoucherPaymentsDetails> partialUpdateVoucherPaymentsDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoucherPaymentsDetails voucherPaymentsDetails
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update VoucherPaymentsDetails partially : {}, {}", id, voucherPaymentsDetails);
        if (voucherPaymentsDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherPaymentsDetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherPaymentsDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoucherPaymentsDetails> result = voucherPaymentsDetailsService.partialUpdate(voucherPaymentsDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherPaymentsDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /voucher-payments-details} : get all the voucherPaymentsDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voucherPaymentsDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VoucherPaymentsDetails>> getAllVoucherPaymentsDetails(
        VoucherPaymentsDetailsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get VoucherPaymentsDetails by criteria: {}", criteria);

        Page<VoucherPaymentsDetails> page = voucherPaymentsDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /voucher-payments-details/count} : count all the voucherPaymentsDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countVoucherPaymentsDetails(VoucherPaymentsDetailsCriteria criteria) {
        LOG.debug("REST request to count VoucherPaymentsDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(voucherPaymentsDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /voucher-payments-details/:id} : get the "id" voucherPaymentsDetails.
     *
     * @param id the id of the voucherPaymentsDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voucherPaymentsDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VoucherPaymentsDetails> getVoucherPaymentsDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to get VoucherPaymentsDetails : {}", id);
        Optional<VoucherPaymentsDetails> voucherPaymentsDetails = voucherPaymentsDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucherPaymentsDetails);
    }

    /**
     * {@code DELETE  /voucher-payments-details/:id} : delete the "id" voucherPaymentsDetails.
     *
     * @param id the id of the voucherPaymentsDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucherPaymentsDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete VoucherPaymentsDetails : {}", id);
        voucherPaymentsDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
