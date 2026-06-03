package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.ReceiptLines;
import com.heavenscode.rac.domain.ReceiptLinesId;
import com.heavenscode.rac.repository.ReceiptLinesRepository;
import com.heavenscode.rac.service.ReceiptLinesQueryService;
import com.heavenscode.rac.service.ReceiptLinesService;
import com.heavenscode.rac.service.criteria.ReceiptLinesCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.ReceiptLines}.
 */
@RestController
@RequestMapping("/api/receipt-lines")
public class ReceiptLinesResource {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiptLinesResource.class);

    private static final String ENTITY_NAME = "receiptLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceiptLinesService receiptLinesService;

    private final ReceiptLinesRepository receiptLinesRepository;

    private final ReceiptLinesQueryService receiptLinesQueryService;

    public ReceiptLinesResource(
        ReceiptLinesService receiptLinesService,
        ReceiptLinesRepository receiptLinesRepository,
        ReceiptLinesQueryService receiptLinesQueryService
    ) {
        this.receiptLinesService = receiptLinesService;
        this.receiptLinesRepository = receiptLinesRepository;
        this.receiptLinesQueryService = receiptLinesQueryService;
    }

    /**
     * {@code POST  /receipt-lines} : Create a new receiptLines.
     *
     * @param receiptLines the receiptLines to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receiptLines, or with status {@code 400 (Bad Request)} if the receiptLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ReceiptLines> createReceiptLines(@RequestBody ReceiptLines receiptLines) throws URISyntaxException {
        LOG.info("REST request to save ReceiptLines : {}", receiptLines);
        // id + lineid form a composite key; id comes from the parent receipt, not auto-generated
        receiptLines = receiptLinesService.save(receiptLines);
        return ResponseEntity.created(new URI("/api/receipt-lines/" + receiptLines.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, receiptLines.getId().toString()))
            .body(receiptLines);
    }

    /**
     * {@code PUT  /receipt-lines/:id} : Updates an existing receiptLines.
     *
     * @param id the id of the receiptLines to save.
     * @param receiptLines the receiptLines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiptLines,
     * or with status {@code 400 (Bad Request)} if the receiptLines is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receiptLines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReceiptLines> updateReceiptLines(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReceiptLines receiptLines
    ) throws URISyntaxException {
        LOG.debug("REST request to update ReceiptLines : {}, {}", id, receiptLines);
        if (receiptLines.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receiptLines.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receiptLinesRepository.existsById(new ReceiptLinesId(id, receiptLines.getLineid()))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        receiptLines = receiptLinesService.update(receiptLines);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, receiptLines.getId().toString()))
            .body(receiptLines);
    }

    /**
     * {@code PATCH  /receipt-lines/:id} : Partial updates given fields of an existing receiptLines, field will ignore if it is null
     *
     * @param id the id of the receiptLines to save.
     * @param receiptLines the receiptLines to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiptLines,
     * or with status {@code 400 (Bad Request)} if the receiptLines is not valid,
     * or with status {@code 404 (Not Found)} if the receiptLines is not found,
     * or with status {@code 500 (Internal Server Error)} if the receiptLines couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReceiptLines> partialUpdateReceiptLines(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReceiptLines receiptLines
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ReceiptLines partially : {}, {}", id, receiptLines);
        if (receiptLines.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receiptLines.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receiptLinesRepository.existsById(new ReceiptLinesId(id, receiptLines.getLineid()))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReceiptLines> result = receiptLinesService.partialUpdate(receiptLines);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, receiptLines.getId().toString())
        );
    }

    /**
     * {@code GET  /receipt-lines} : get all the receiptLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receiptLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ReceiptLines>> getAllReceiptLines(
        ReceiptLinesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ReceiptLines by criteria: {}", criteria);

        Page<ReceiptLines> page = receiptLinesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /receipt-lines/count} : count all the receiptLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countReceiptLines(ReceiptLinesCriteria criteria) {
        LOG.debug("REST request to count ReceiptLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(receiptLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /receipt-lines/:id} : get the "id" receiptLines.
     *
     * @param id the id of the receiptLines to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receiptLines, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}/{lineid}")
    public ResponseEntity<ReceiptLines> getReceiptLines(@PathVariable("id") Long id, @PathVariable("lineid") Long lineid) {
        LOG.debug("REST request to get ReceiptLines : {}, {}", id, lineid);
        Optional<ReceiptLines> receiptLines = receiptLinesService.findOne(id, lineid);
        return ResponseUtil.wrapOrNotFound(receiptLines);
    }

    /**
     * {@code DELETE  /receipt-lines/:id} : delete the "id" receiptLines.
     *
     * @param id the id of the receiptLines to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}/{lineid}")
    public ResponseEntity<Void> deleteReceiptLines(@PathVariable("id") Long id, @PathVariable("lineid") Long lineid) {
        LOG.debug("REST request to delete ReceiptLines : {}, {}", id, lineid);
        receiptLinesService.delete(id, lineid);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString() + "-" + lineid.toString()))
            .build();
    }
}
