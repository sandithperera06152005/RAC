package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.VoucherLines;
import com.heavenscode.rac.repository.VoucherLinesRepository;
import com.heavenscode.rac.service.VoucherLinesQueryService;
import com.heavenscode.rac.service.VoucherLinesService;
import com.heavenscode.rac.service.criteria.VoucherLinesCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.VoucherLines}.
 */
@RestController
@RequestMapping("/api/voucher-lines")
public class VoucherLinesResource {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherLinesResource.class);

    private static final String ENTITY_NAME = "voucherLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoucherLinesService voucherLinesService;

    private final VoucherLinesRepository voucherLinesRepository;

    private final VoucherLinesQueryService voucherLinesQueryService;

    public VoucherLinesResource(
        VoucherLinesService voucherLinesService,
        VoucherLinesRepository voucherLinesRepository,
        VoucherLinesQueryService voucherLinesQueryService
    ) {
        this.voucherLinesService = voucherLinesService;
        this.voucherLinesRepository = voucherLinesRepository;
        this.voucherLinesQueryService = voucherLinesQueryService;
    }

    /**
     * {@code POST  /voucher-lines} : Create a new voucherLines.
     *
     * @param voucherLines the voucherLines to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voucherLines, or with status {@code 400 (Bad Request)} if the voucherLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VoucherLines> createVoucherLines(@RequestBody VoucherLines voucherLines) throws URISyntaxException {
        LOG.debug("REST request to save VoucherLines : {}", voucherLines);
        if (voucherLines.getId() != null) {
            throw new BadRequestAlertException("A new voucherLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        voucherLines = voucherLinesService.save(voucherLines);
        return ResponseEntity.created(new URI("/api/voucher-lines/" + voucherLines.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, voucherLines.getId().toString()))
            .body(voucherLines);
    }

    /**
     * {@code PUT  /voucher-lines/:id} : Updates an existing voucherLines.
     *
     * @param id the id of the voucherLines to save.
     * @param voucherLines the voucherLines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherLines,
     * or with status {@code 400 (Bad Request)} if the voucherLines is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voucherLines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VoucherLines> updateVoucherLines(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoucherLines voucherLines
    ) throws URISyntaxException {
        LOG.debug("REST request to update VoucherLines : {}, {}", id, voucherLines);
        if (voucherLines.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherLines.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherLinesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        voucherLines = voucherLinesService.update(voucherLines);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherLines.getId().toString()))
            .body(voucherLines);
    }

    /**
     * {@code PATCH  /voucher-lines/:id} : Partial updates given fields of an existing voucherLines, field will ignore if it is null
     *
     * @param id the id of the voucherLines to save.
     * @param voucherLines the voucherLines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucherLines,
     * or with status {@code 400 (Bad Request)} if the voucherLines is not valid,
     * or with status {@code 404 (Not Found)} if the voucherLines is not found,
     * or with status {@code 500 (Internal Server Error)} if the voucherLines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VoucherLines> partialUpdateVoucherLines(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VoucherLines voucherLines
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update VoucherLines partially : {}, {}", id, voucherLines);
        if (voucherLines.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucherLines.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherLinesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoucherLines> result = voucherLinesService.partialUpdate(voucherLines);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucherLines.getId().toString())
        );
    }

    /**
     * {@code GET  /voucher-lines} : get all the voucherLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voucherLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VoucherLines>> getAllVoucherLines(
        VoucherLinesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get VoucherLines by criteria: {}", criteria);

        Page<VoucherLines> page = voucherLinesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /voucher-lines/count} : count all the voucherLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countVoucherLines(VoucherLinesCriteria criteria) {
        LOG.debug("REST request to count VoucherLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(voucherLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /voucher-lines/:id} : get the "id" voucherLines.
     *
     * @param id the id of the voucherLines to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voucherLines, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VoucherLines> getVoucherLines(@PathVariable("id") Long id) {
        LOG.debug("REST request to get VoucherLines : {}", id);
        Optional<VoucherLines> voucherLines = voucherLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucherLines);
    }

    /**
     * {@code DELETE  /voucher-lines/:id} : delete the "id" voucherLines.
     *
     * @param id the id of the voucherLines to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucherLines(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete VoucherLines : {}", id);
        voucherLinesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
