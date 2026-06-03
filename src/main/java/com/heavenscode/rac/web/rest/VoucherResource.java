package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Voucher;
import com.heavenscode.rac.repository.VoucherRepository;
import com.heavenscode.rac.service.VoucherQueryService;
import com.heavenscode.rac.service.VoucherService;
import com.heavenscode.rac.service.criteria.VoucherCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Voucher}.
 */
@RestController
@RequestMapping("/api/vouchers")
public class VoucherResource {

    private static final Logger LOG = LoggerFactory.getLogger(VoucherResource.class);

    private static final String ENTITY_NAME = "voucher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoucherService voucherService;

    private final VoucherRepository voucherRepository;

    private final VoucherQueryService voucherQueryService;

    public VoucherResource(VoucherService voucherService, VoucherRepository voucherRepository, VoucherQueryService voucherQueryService) {
        this.voucherService = voucherService;
        this.voucherRepository = voucherRepository;
        this.voucherQueryService = voucherQueryService;
    }

    /**
     * {@code POST  /vouchers} : Create a new voucher.
     *
     * @param voucher the voucher to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voucher, or with status {@code 400 (Bad Request)} if the voucher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Voucher> createVoucher(@RequestBody Voucher voucher) throws URISyntaxException {
        LOG.debug("REST request to save Voucher : {}", voucher);
        if (voucher.getId() != null) {
            throw new BadRequestAlertException("A new voucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        voucher = voucherService.save(voucher);
        return ResponseEntity.created(new URI("/api/vouchers/" + voucher.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, voucher.getId().toString()))
            .body(voucher);
    }

    /**
     * {@code PUT  /vouchers/:id} : Updates an existing voucher.
     *
     * @param id the id of the voucher to save.
     * @param voucher the voucher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucher,
     * or with status {@code 400 (Bad Request)} if the voucher is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voucher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Voucher> updateVoucher(@PathVariable(value = "id", required = false) final Long id, @RequestBody Voucher voucher)
        throws URISyntaxException {
        LOG.debug("REST request to update Voucher : {}, {}", id, voucher);
        if (voucher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucher.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        voucher = voucherService.update(voucher);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucher.getId().toString()))
            .body(voucher);
    }

    /**
     * {@code PATCH  /vouchers/:id} : Partial updates given fields of an existing voucher, field will ignore if it is null
     *
     * @param id the id of the voucher to save.
     * @param voucher the voucher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucher,
     * or with status {@code 400 (Bad Request)} if the voucher is not valid,
     * or with status {@code 404 (Not Found)} if the voucher is not found,
     * or with status {@code 500 (Internal Server Error)} if the voucher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Voucher> partialUpdateVoucher(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Voucher voucher
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Voucher partially : {}, {}", id, voucher);
        if (voucher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voucher.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voucherRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Voucher> result = voucherService.partialUpdate(voucher);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, voucher.getId().toString())
        );
    }

    /**
     * {@code GET  /vouchers} : get all the vouchers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vouchers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Voucher>> getAllVouchers(
        VoucherCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Vouchers by criteria: {}", criteria);

        Page<Voucher> page = voucherQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vouchers/count} : count all the vouchers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countVouchers(VoucherCriteria criteria) {
        LOG.debug("REST request to count Vouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(voucherQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vouchers/:id} : get the "id" voucher.
     *
     * @param id the id of the voucher to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voucher, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Voucher : {}", id);
        Optional<Voucher> voucher = voucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucher);
    }

    /**
     * {@code DELETE  /vouchers/:id} : delete the "id" voucher.
     *
     * @param id the id of the voucher to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Voucher : {}", id);
        voucherService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
