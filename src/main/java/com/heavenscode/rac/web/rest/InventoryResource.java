package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Inventory;
import com.heavenscode.rac.repository.InventoryRepository;
import com.heavenscode.rac.service.InventoryQueryService;
import com.heavenscode.rac.service.InventoryService;
import com.heavenscode.rac.service.criteria.InventoryCriteria;
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
 * REST controller for managing {@link com.heavenscode.rac.domain.Inventory}.
 */
@RestController
@RequestMapping("/api/inventories")
public class InventoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryResource.class);

    private static final String ENTITY_NAME = "inventory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryService inventoryService;

    private final InventoryRepository inventoryRepository;

    private final InventoryQueryService inventoryQueryService;

    public InventoryResource(
        InventoryService inventoryService,
        InventoryRepository inventoryRepository,
        InventoryQueryService inventoryQueryService
    ) {
        this.inventoryService = inventoryService;
        this.inventoryRepository = inventoryRepository;
        this.inventoryQueryService = inventoryQueryService;
    }

    /**
     * {@code POST  /inventories} : Create a new inventory.
     *
     * @param inventory the inventory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventory, or with status {@code 400 (Bad Request)} if the inventory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) throws URISyntaxException {
        LOG.debug("REST request to save Inventory : {}", inventory);
        if (inventory.getId() != null) {
            throw new BadRequestAlertException("A new inventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inventory = inventoryService.save(inventory);
        return ResponseEntity.created(new URI("/api/inventories/" + inventory.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, inventory.getId().toString()))
            .body(inventory);
    }

    /**
     * {@code PUT  /inventories/:id} : Updates an existing inventory.
     *
     * @param id the id of the inventory to save.
     * @param inventory the inventory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventory,
     * or with status {@code 400 (Bad Request)} if the inventory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inventory inventory
    ) throws URISyntaxException {
        LOG.debug("REST request to update Inventory : {}, {}", id, inventory);
        if (inventory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inventory = inventoryService.update(inventory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inventory.getId().toString()))
            .body(inventory);
    }

    /**
     * {@code PATCH  /inventories/:id} : Partial updates given fields of an existing inventory, field will ignore if it is null
     *
     * @param id the id of the inventory to save.
     * @param inventory the inventory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventory,
     * or with status {@code 400 (Bad Request)} if the inventory is not valid,
     * or with status {@code 404 (Not Found)} if the inventory is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Inventory> partialUpdateInventory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Inventory inventory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Inventory partially : {}, {}", id, inventory);
        if (inventory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Inventory> result = inventoryService.partialUpdate(inventory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inventory.getId().toString())
        );
    }

    /**
     * {@code GET  /inventories} : get all the inventories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Inventory>> getAllInventories(
        InventoryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Inventories by criteria: {}", criteria);

        Page<Inventory> page = inventoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventories/count} : count all the inventories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInventories(InventoryCriteria criteria) {
        LOG.debug("REST request to count Inventories by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventories/:id} : get the "id" inventory.
     *
     * @param id the id of the inventory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Inventory : {}", id);
        Optional<Inventory> inventory = inventoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventory);
    }

    /**
     * {@code DELETE  /inventories/:id} : delete the "id" inventory.
     *
     * @param id the id of the inventory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Inventory : {}", id);
        inventoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
