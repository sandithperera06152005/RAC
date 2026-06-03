package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Transactions;
import com.heavenscode.rac.service.TransactionsService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.Transactions}.
 */
@RestController
@RequestMapping("/api")
public class TransactionsResource {

    private final Logger log = LoggerFactory.getLogger(TransactionsResource.class);

    private static final String ENTITY_NAME = "transactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionsService transactionsService;

    public TransactionsResource(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    /**
     * {@code POST  /transactions} : Create a new transactions.
     *
     * @param transactions the transactions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactions, or with status {@code 400 (Bad Request)} if the transactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transactions")
    public ResponseEntity<Transactions> createTransactions(@RequestBody Transactions transactions) throws URISyntaxException {
        log.debug("REST request to save Transactions : {}", transactions);
        if (transactions.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(
                    HeaderUtil.createFailureAlert(
                        applicationName,
                        false,
                        ENTITY_NAME,
                        "idexists",
                        "A new transactions cannot already have an ID"
                    )
                )
                .body(null);
        }
        Transactions result = transactionsService.save(transactions);
        return ResponseEntity.created(new URI("/api/transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transactions/:id} : Updates an existing transactions.
     *
     * @param id the id of the transactions to save.
     * @param transactions the transactions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactions,
     * or with status {@code 400 (Bad Request)} if the transactions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transactions> updateTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Transactions transactions
    ) throws URISyntaxException {
        log.debug("REST request to update Transactions : {}, {}", id, transactions);
        if (transactions.getId() == null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(applicationName, false, ENTITY_NAME, "idnull", "Invalid id"))
                .body(null);
        }
        if (!id.equals(transactions.getId())) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(applicationName, false, ENTITY_NAME, "idinvalid", "Invalid id"))
                .body(null);
        }

        Transactions result = transactionsService.update(transactions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/transactions")
    public List<Transactions> getAllTransactions() {
        log.debug("REST request to get all Transactions");
        return transactionsService.findAll();
    }

    /**
     * {@code GET  /transactions/:id} : get the "id" transactions.
     *
     * @param id the id of the transactions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transactions> getTransactions(@PathVariable Long id) {
        log.debug("REST request to get Transactions : {}", id);
        Optional<Transactions> transactions = transactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactions);
    }

    /**
     * {@code DELETE  /transactions/:id} : delete the "id" transactions.
     *
     * @param id the id of the transactions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransactions(@PathVariable Long id) {
        log.debug("REST request to delete Transactions : {}", id);
        transactionsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
