package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.WorkshopVehicleWorkList;
import com.heavenscode.rac.domain.WorkshopVehicleWorkListId;
import com.heavenscode.rac.repository.WorkshopVehicleWorkListRepository;
import com.heavenscode.rac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.WorkshopVehicleWorkList}.
 */
@RestController
@RequestMapping("/api/workshop-vehicle-work-lists")
@Transactional
public class WorkshopVehicleWorkListResource {

    private static final Logger LOG = LoggerFactory.getLogger(WorkshopVehicleWorkListResource.class);

    private static final String ENTITY_NAME = "workshopVehicleWorkList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkshopVehicleWorkListRepository workshopVehicleWorkListRepository;

    public WorkshopVehicleWorkListResource(WorkshopVehicleWorkListRepository workshopVehicleWorkListRepository) {
        this.workshopVehicleWorkListRepository = workshopVehicleWorkListRepository;
    }

    /**
     * {@code POST  /workshop-vehicle-work-lists} : Create a new workshopVehicleWorkList.
     *
     * @param workshopVehicleWorkList the workshopVehicleWorkList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workshopVehicleWorkList, or with status {@code 400 (Bad Request)} if the workshopVehicleWorkList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WorkshopVehicleWorkList> createWorkshopVehicleWorkList(
        @RequestBody WorkshopVehicleWorkList workshopVehicleWorkList
    ) throws URISyntaxException {
        LOG.debug("REST request to save WorkshopVehicleWorkList : {}", workshopVehicleWorkList);
        WorkshopVehicleWorkListId workshopVehicleWorkListId = toCompositeId(workshopVehicleWorkList);
        if (workshopVehicleWorkListId.getVehicleworkid() == null || workshopVehicleWorkListId.getLineid() == null) {
            throw new BadRequestAlertException("A new workshopVehicleWorkList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        workshopVehicleWorkList = workshopVehicleWorkListRepository.save(workshopVehicleWorkList);
        return ResponseEntity.created(new URI("/api/workshop-vehicle-work-lists/" + workshopVehicleWorkList.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, workshopVehicleWorkList.getId()))
            .body(workshopVehicleWorkList);
    }

    /**
     * {@code PUT  /workshop-vehicle-work-lists/:id} : Updates an existing workshopVehicleWorkList.
     *
     * @param id the id of the workshopVehicleWorkList to save.
     * @param workshopVehicleWorkList the workshopVehicleWorkList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workshopVehicleWorkList,
     * or with status {@code 400 (Bad Request)} if the workshopVehicleWorkList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workshopVehicleWorkList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkshopVehicleWorkList> updateWorkshopVehicleWorkList(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody WorkshopVehicleWorkList workshopVehicleWorkList
    ) throws URISyntaxException {
        LOG.debug("REST request to update WorkshopVehicleWorkList : {}, {}", id, workshopVehicleWorkList);
        WorkshopVehicleWorkListId workshopVehicleWorkListId = toCompositeId(workshopVehicleWorkList);
        if (workshopVehicleWorkListId.getVehicleworkid() == null || workshopVehicleWorkListId.getLineid() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workshopVehicleWorkList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workshopVehicleWorkListRepository.existsById(workshopVehicleWorkListId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        workshopVehicleWorkList = workshopVehicleWorkListRepository.save(workshopVehicleWorkList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workshopVehicleWorkList.getId()))
            .body(workshopVehicleWorkList);
    }

    /**
     * {@code PATCH  /workshop-vehicle-work-lists/:id} : Partial updates given fields of an existing workshopVehicleWorkList, field will ignore if it is null
     *
     * @param id the id of the workshopVehicleWorkList to save.
     * @param workshopVehicleWorkList the workshopVehicleWorkList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workshopVehicleWorkList,
     * or with status {@code 400 (Bad Request)} if the workshopVehicleWorkList is not valid,
     * or with status {@code 404 (Not Found)} if the workshopVehicleWorkList is not found,
     * or with status {@code 500 (Internal Server Error)} if the workshopVehicleWorkList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkshopVehicleWorkList> partialUpdateWorkshopVehicleWorkList(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody WorkshopVehicleWorkList workshopVehicleWorkList
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update WorkshopVehicleWorkList partially : {}, {}", id, workshopVehicleWorkList);
        WorkshopVehicleWorkListId workshopVehicleWorkListId = toCompositeId(workshopVehicleWorkList);
        if (workshopVehicleWorkListId.getVehicleworkid() == null || workshopVehicleWorkListId.getLineid() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workshopVehicleWorkList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workshopVehicleWorkListRepository.existsById(workshopVehicleWorkListId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkshopVehicleWorkList> result = workshopVehicleWorkListRepository
            .findById(workshopVehicleWorkListId)
            .map(existingWorkshopVehicleWorkList -> {
                if (workshopVehicleWorkList.getVehicleworkid() != null) {
                    existingWorkshopVehicleWorkList.setVehicleworkid(workshopVehicleWorkList.getVehicleworkid());
                }
                if (workshopVehicleWorkList.getLineid() != null) {
                    existingWorkshopVehicleWorkList.setLineid(workshopVehicleWorkList.getLineid());
                }
                if (workshopVehicleWorkList.getWorkid() != null) {
                    existingWorkshopVehicleWorkList.setWorkid(workshopVehicleWorkList.getWorkid());
                }
                if (workshopVehicleWorkList.getWorkshopwork() != null) {
                    existingWorkshopVehicleWorkList.setWorkshopwork(workshopVehicleWorkList.getWorkshopwork());
                }
                if (workshopVehicleWorkList.getIsjobdone() != null) {
                    existingWorkshopVehicleWorkList.setIsjobdone(workshopVehicleWorkList.getIsjobdone());
                }
                if (workshopVehicleWorkList.getJobdonedate() != null) {
                    existingWorkshopVehicleWorkList.setJobdonedate(workshopVehicleWorkList.getJobdonedate());
                }
                if (workshopVehicleWorkList.getJobnumber() != null) {
                    existingWorkshopVehicleWorkList.setJobnumber(workshopVehicleWorkList.getJobnumber());
                }
                if (workshopVehicleWorkList.getJobvalue() != null) {
                    existingWorkshopVehicleWorkList.setJobvalue(workshopVehicleWorkList.getJobvalue());
                }
                if (workshopVehicleWorkList.getEstimatevalue() != null) {
                    existingWorkshopVehicleWorkList.setEstimatevalue(workshopVehicleWorkList.getEstimatevalue());
                }

                return existingWorkshopVehicleWorkList;
            })
            .map(workshopVehicleWorkListRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workshopVehicleWorkList.getId())
        );
    }

    /**
     * {@code GET  /workshop-vehicle-work-lists} : get all the workshopVehicleWorkLists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workshopVehicleWorkLists in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WorkshopVehicleWorkList>> getAllWorkshopVehicleWorkLists(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of WorkshopVehicleWorkLists");
        Page<WorkshopVehicleWorkList> page = workshopVehicleWorkListRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/vehicleworks")
    public ResponseEntity<List<WorkshopVehicleWorkList>> getWorkshopVehicleWorkListsByVehicleworkids(@RequestParam("ids") String ids) {
        LOG.debug("REST request to get WorkshopVehicleWorkLists by vehiclework ids : {}", ids);
        List<Integer> vehicleworkids = Arrays.stream(ids.split(","))
            .map(String::trim)
            .filter(value -> !value.isEmpty())
            .map(Integer::valueOf)
            .collect(Collectors.toList());

        if (vehicleworkids.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(workshopVehicleWorkListRepository.findByVehicleworkidIn(vehicleworkids));
    }

    /**
     * {@code GET  /workshop-vehicle-work-lists/:id} : get the "id" workshopVehicleWorkList.
     *
     * @param id the id of the workshopVehicleWorkList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workshopVehicleWorkList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkshopVehicleWorkList> getWorkshopVehicleWorkList(@PathVariable("id") String id) {
        LOG.debug("REST request to get WorkshopVehicleWorkList : {}", id);
        Optional<WorkshopVehicleWorkList> workshopVehicleWorkList = workshopVehicleWorkListRepository.findById(parseCompositeId(id));
        return ResponseUtil.wrapOrNotFound(workshopVehicleWorkList);
    }

    /**
     * {@code DELETE  /workshop-vehicle-work-lists/:id} : delete the "id" workshopVehicleWorkList.
     *
     * @param id the id of the workshopVehicleWorkList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshopVehicleWorkList(@PathVariable("id") String id) {
        LOG.debug("REST request to delete WorkshopVehicleWorkList : {}", id);
        workshopVehicleWorkListRepository.deleteById(parseCompositeId(id));
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    private WorkshopVehicleWorkListId toCompositeId(WorkshopVehicleWorkList workshopVehicleWorkList) {
        return new WorkshopVehicleWorkListId(workshopVehicleWorkList.getVehicleworkid(), workshopVehicleWorkList.getLineid());
    }

    private WorkshopVehicleWorkListId parseCompositeId(String id) {
        if (id == null || !id.contains("-")) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        String[] idParts = id.split("-", 2);
        return new WorkshopVehicleWorkListId(Integer.valueOf(idParts[0]), Integer.valueOf(idParts[1]));
    }
}
