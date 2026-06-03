package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.repository.AutojobsalesinvoiceservicechargelineRepository;
import com.heavenscode.rac.service.AutojobsChildInsertService;
import com.heavenscode.rac.service.AutojobsalesinvoiceservicechargelineQueryService;
import com.heavenscode.rac.service.AutojobsalesinvoiceservicechargelineService;
import com.heavenscode.rac.service.LegacyInvoiceChildrenReadService;
import com.heavenscode.rac.service.criteria.AutojobsalesinvoiceservicechargelineCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline}.
 */
@RestController
@RequestMapping("/api/autojobsalesinvoiceservicechargelines")
public class AutojobsalesinvoiceservicechargelineResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutojobsalesinvoiceservicechargelineResource.class);

    private static final String ENTITY_NAME = "autojobsalesinvoiceservicechargeline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutojobsalesinvoiceservicechargelineService autojobsalesinvoiceservicechargelineService;

    private final AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository;

    private final AutojobsalesinvoiceservicechargelineQueryService autojobsalesinvoiceservicechargelineQueryService;
    private final AutojobsChildInsertService autojobsChildInsertService;
    private final LegacyInvoiceChildrenReadService legacyInvoiceChildrenReadService;

    public AutojobsalesinvoiceservicechargelineResource(
        AutojobsalesinvoiceservicechargelineService autojobsalesinvoiceservicechargelineService,
        AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository,
        AutojobsalesinvoiceservicechargelineQueryService autojobsalesinvoiceservicechargelineQueryService,
        AutojobsChildInsertService autojobsChildInsertService,
        LegacyInvoiceChildrenReadService legacyInvoiceChildrenReadService
    ) {
        this.autojobsalesinvoiceservicechargelineService = autojobsalesinvoiceservicechargelineService;
        this.autojobsalesinvoiceservicechargelineRepository = autojobsalesinvoiceservicechargelineRepository;
        this.autojobsalesinvoiceservicechargelineQueryService = autojobsalesinvoiceservicechargelineQueryService;
        this.autojobsChildInsertService = autojobsChildInsertService;
        this.legacyInvoiceChildrenReadService = legacyInvoiceChildrenReadService;
    }

    /**
     * {@code POST  /autojobsalesinvoiceservicechargelines} : Create a new autojobsalesinvoiceservicechargeline.
     *
     * @param autojobsalesinvoiceservicechargeline the autojobsalesinvoiceservicechargeline to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autojobsalesinvoiceservicechargeline, or with status {@code 400 (Bad Request)} if the autojobsalesinvoiceservicechargeline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> createAutojobsalesinvoiceservicechargeline(
        @RequestBody Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) throws URISyntaxException {
        LOG.debug("REST request to save Autojobsalesinvoiceservicechargeline : {}", autojobsalesinvoiceservicechargeline);
        if (autojobsalesinvoiceservicechargeline.getId() != null) {
            throw new BadRequestAlertException(
                "A new autojobsalesinvoiceservicechargeline cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        autojobsalesinvoiceservicechargeline = autojobsChildInsertService.insertServiceChargeLine(autojobsalesinvoiceservicechargeline);
        String identifier = creationIdentifier(autojobsalesinvoiceservicechargeline);
        return ResponseEntity.created(new URI("/api/autojobsalesinvoiceservicechargelines/" + identifier))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, identifier))
            .body(autojobsalesinvoiceservicechargeline);
    }

    /**
     * {@code PUT  /autojobsalesinvoiceservicechargelines/:id} : Updates an existing autojobsalesinvoiceservicechargeline.
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to save.
     * @param autojobsalesinvoiceservicechargeline the autojobsalesinvoiceservicechargeline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsalesinvoiceservicechargeline,
     * or with status {@code 400 (Bad Request)} if the autojobsalesinvoiceservicechargeline is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autojobsalesinvoiceservicechargeline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> updateAutojobsalesinvoiceservicechargeline(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autojobsalesinvoiceservicechargeline : {}, {}", id, autojobsalesinvoiceservicechargeline);
        if (autojobsalesinvoiceservicechargeline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsalesinvoiceservicechargeline.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsalesinvoiceservicechargelineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineService.update(autojobsalesinvoiceservicechargeline);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    autojobsalesinvoiceservicechargeline.getId().toString()
                )
            )
            .body(autojobsalesinvoiceservicechargeline);
    }

    /**
     * {@code PATCH  /autojobsalesinvoiceservicechargelines/:id} : Partial updates given fields of an existing autojobsalesinvoiceservicechargeline, field will ignore if it is null
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to save.
     * @param autojobsalesinvoiceservicechargeline the autojobsalesinvoiceservicechargeline to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autojobsalesinvoiceservicechargeline,
     * or with status {@code 400 (Bad Request)} if the autojobsalesinvoiceservicechargeline is not valid,
     * or with status {@code 404 (Not Found)} if the autojobsalesinvoiceservicechargeline is not found,
     * or with status {@code 500 (Internal Server Error)} if the autojobsalesinvoiceservicechargeline couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> partialUpdateAutojobsalesinvoiceservicechargeline(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) throws URISyntaxException {
        LOG.debug(
            "REST request to partial update Autojobsalesinvoiceservicechargeline partially : {}, {}",
            id,
            autojobsalesinvoiceservicechargeline
        );
        if (autojobsalesinvoiceservicechargeline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autojobsalesinvoiceservicechargeline.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autojobsalesinvoiceservicechargelineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autojobsalesinvoiceservicechargeline> result = autojobsalesinvoiceservicechargelineService.partialUpdate(
            autojobsalesinvoiceservicechargeline
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autojobsalesinvoiceservicechargeline.getId().toString())
        );
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines} : get all the autojobsalesinvoiceservicechargelines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autojobsalesinvoiceservicechargelines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autojobsalesinvoiceservicechargeline>> getAllAutojobsalesinvoiceservicechargelines(
        AutojobsalesinvoiceservicechargelineCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Autojobsalesinvoiceservicechargelines by criteria: {}", criteria);

        Page<Autojobsalesinvoiceservicechargeline> page = autojobsalesinvoiceservicechargelineQueryService.findByCriteria(
            criteria,
            pageable
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<Map<String, Object>>> getServiceLinesByInvoiceId(@PathVariable("invoiceId") Integer invoiceId) {
        LOG.debug("REST request to get Autojobsalesinvoiceservicechargeline by invoiceId : {}", invoiceId);
        LinkedHashMap<String, String> columns = new LinkedHashMap<>();
        columns.put("id", "id");
        columns.put("lineid", "lineid");
        columns.put("optionid", "optionid");
        columns.put("servicename", "servicename");
        columns.put("servicediscription", "servicediscription");
        columns.put("value", "value");
        columns.put("discount", "discount");
        columns.put("serviceprice", "serviceprice");
        return ResponseEntity.ok(
            legacyInvoiceChildrenReadService.findByInvoiceId("autojobsalesinvoiceservicechargeline", "invoiceid", columns, invoiceId)
        );
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines/count} : count all the autojobsalesinvoiceservicechargelines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAutojobsalesinvoiceservicechargelines(AutojobsalesinvoiceservicechargelineCriteria criteria) {
        LOG.debug("REST request to count Autojobsalesinvoiceservicechargelines by criteria: {}", criteria);
        return ResponseEntity.ok().body(autojobsalesinvoiceservicechargelineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /autojobsalesinvoiceservicechargelines/:id} : get the "id" autojobsalesinvoiceservicechargeline.
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autojobsalesinvoiceservicechargeline, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autojobsalesinvoiceservicechargeline> getAutojobsalesinvoiceservicechargeline(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Autojobsalesinvoiceservicechargeline : {}", id);
        Optional<Autojobsalesinvoiceservicechargeline> autojobsalesinvoiceservicechargeline =
            autojobsalesinvoiceservicechargelineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autojobsalesinvoiceservicechargeline);
    }

    /**
     * {@code DELETE  /autojobsalesinvoiceservicechargelines/:id} : delete the "id" autojobsalesinvoiceservicechargeline.
     *
     * @param id the id of the autojobsalesinvoiceservicechargeline to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutojobsalesinvoiceservicechargeline(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autojobsalesinvoiceservicechargeline : {}", id);
        autojobsalesinvoiceservicechargelineService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    private String creationIdentifier(Autojobsalesinvoiceservicechargeline entity) {
        if (entity.getId() != null) {
            return entity.getId().toString();
        }
        if (entity.getInvoiceid() != null && entity.getLineid() != null) {
            return entity.getInvoiceid() + "-" + entity.getLineid();
        }
        if (entity.getLineid() != null) {
            return entity.getLineid().toString();
        }
        return "created";
    }
}
