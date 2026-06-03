package com.heavenscode.rac.web.rest;

import com.heavenscode.rac.domain.Receiptpaymentsdetails;
import com.heavenscode.rac.domain.ReceiptpaymentsdetailsId;
import com.heavenscode.rac.repository.ReceiptpaymentsdetailsRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.heavenscode.rac.domain.Receiptpaymentsdetails}.
 */
@RestController
@RequestMapping("/api/receiptpaymentsdetails")
@Transactional
public class ReceiptpaymentsdetailsResource {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiptpaymentsdetailsResource.class);

    private static final String ENTITY_NAME = "receiptpaymentsdetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReceiptpaymentsdetailsRepository receiptpaymentsdetailsRepository;

    public ReceiptpaymentsdetailsResource(ReceiptpaymentsdetailsRepository receiptpaymentsdetailsRepository) {
        this.receiptpaymentsdetailsRepository = receiptpaymentsdetailsRepository;
    }

    /**
     * {@code POST  /receiptpaymentsdetails} : Create a new receiptpaymentsdetails.
     *
     * @param receiptpaymentsdetails the receiptpaymentsdetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new receiptpaymentsdetails, or with status {@code 400 (Bad Request)} if the receiptpaymentsdetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Receiptpaymentsdetails> createReceiptpaymentsdetails(@RequestBody Receiptpaymentsdetails receiptpaymentsdetails)
        throws URISyntaxException {
        LOG.info("REST request to save Receiptpaymentsdetails : {}", receiptpaymentsdetails);
        receiptpaymentsdetails = receiptpaymentsdetailsRepository.save(receiptpaymentsdetails);
        return ResponseEntity.created(new URI("/api/receiptpaymentsdetails/" + receiptpaymentsdetails.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, receiptpaymentsdetails.getId().toString()))
            .body(receiptpaymentsdetails);
    }

    /**
     * {@code PUT  /receiptpaymentsdetails/:id} : Updates an existing receiptpaymentsdetails.
     *
     * @param id the id of the receiptpaymentsdetails to save.
     * @param receiptpaymentsdetails the receiptpaymentsdetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiptpaymentsdetails,
     * or with status {@code 400 (Bad Request)} if the receiptpaymentsdetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the receiptpaymentsdetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Receiptpaymentsdetails> updateReceiptpaymentsdetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Receiptpaymentsdetails receiptpaymentsdetails
    ) throws URISyntaxException {
        LOG.debug("REST request to update Receiptpaymentsdetails : {}, {}", id, receiptpaymentsdetails);
        if (receiptpaymentsdetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receiptpaymentsdetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receiptpaymentsdetailsRepository.existsById(new ReceiptpaymentsdetailsId(id, receiptpaymentsdetails.getLineid()))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        receiptpaymentsdetails = receiptpaymentsdetailsRepository.save(receiptpaymentsdetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, receiptpaymentsdetails.getId().toString()))
            .body(receiptpaymentsdetails);
    }

    /**
     * {@code PATCH  /receiptpaymentsdetails/:id} : Partial updates given fields of an existing receiptpaymentsdetails, field will ignore if it is null
     *
     * @param id the id of the receiptpaymentsdetails to save.
     * @param receiptpaymentsdetails the receiptpaymentsdetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated receiptpaymentsdetails,
     * or with status {@code 400 (Bad Request)} if the receiptpaymentsdetails is not valid,
     * or with status {@code 404 (Not Found)} if the receiptpaymentsdetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the receiptpaymentsdetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Receiptpaymentsdetails> partialUpdateReceiptpaymentsdetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Receiptpaymentsdetails receiptpaymentsdetails
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Receiptpaymentsdetails partially : {}, {}", id, receiptpaymentsdetails);
        if (receiptpaymentsdetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, receiptpaymentsdetails.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!receiptpaymentsdetailsRepository.existsById(new ReceiptpaymentsdetailsId(id, receiptpaymentsdetails.getLineid()))) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Receiptpaymentsdetails> result = receiptpaymentsdetailsRepository
            .findById(new ReceiptpaymentsdetailsId(receiptpaymentsdetails.getId(), receiptpaymentsdetails.getLineid()))
            .map(existingReceiptpaymentsdetails -> {
                if (receiptpaymentsdetails.getLineid() != null) {
                    existingReceiptpaymentsdetails.setLineid(receiptpaymentsdetails.getLineid());
                }
                if (receiptpaymentsdetails.getPaymentamount() != null) {
                    existingReceiptpaymentsdetails.setPaymentamount(receiptpaymentsdetails.getPaymentamount());
                }
                if (receiptpaymentsdetails.getTotalreceiptamount() != null) {
                    existingReceiptpaymentsdetails.setTotalreceiptamount(receiptpaymentsdetails.getTotalreceiptamount());
                }
                if (receiptpaymentsdetails.getCheckqueamount() != null) {
                    existingReceiptpaymentsdetails.setCheckqueamount(receiptpaymentsdetails.getCheckqueamount());
                }
                if (receiptpaymentsdetails.getCheckqueno() != null) {
                    existingReceiptpaymentsdetails.setCheckqueno(receiptpaymentsdetails.getCheckqueno());
                }
                if (receiptpaymentsdetails.getCheckquedate() != null) {
                    existingReceiptpaymentsdetails.setCheckquedate(receiptpaymentsdetails.getCheckquedate());
                }
                if (receiptpaymentsdetails.getCheckqueexpiredate() != null) {
                    existingReceiptpaymentsdetails.setCheckqueexpiredate(receiptpaymentsdetails.getCheckqueexpiredate());
                }
                if (receiptpaymentsdetails.getBankname() != null) {
                    existingReceiptpaymentsdetails.setBankname(receiptpaymentsdetails.getBankname());
                }
                if (receiptpaymentsdetails.getBankid() != null) {
                    existingReceiptpaymentsdetails.setBankid(receiptpaymentsdetails.getBankid());
                }
                if (receiptpaymentsdetails.getBankbranchname() != null) {
                    existingReceiptpaymentsdetails.setBankbranchname(receiptpaymentsdetails.getBankbranchname());
                }
                if (receiptpaymentsdetails.getBankbranchid() != null) {
                    existingReceiptpaymentsdetails.setBankbranchid(receiptpaymentsdetails.getBankbranchid());
                }
                if (receiptpaymentsdetails.getCreditcardno() != null) {
                    existingReceiptpaymentsdetails.setCreditcardno(receiptpaymentsdetails.getCreditcardno());
                }
                if (receiptpaymentsdetails.getCreditcardamount() != null) {
                    existingReceiptpaymentsdetails.setCreditcardamount(receiptpaymentsdetails.getCreditcardamount());
                }
                if (receiptpaymentsdetails.getReference() != null) {
                    existingReceiptpaymentsdetails.setReference(receiptpaymentsdetails.getReference());
                }
                if (receiptpaymentsdetails.getOtherdetails() != null) {
                    existingReceiptpaymentsdetails.setOtherdetails(receiptpaymentsdetails.getOtherdetails());
                }
                if (receiptpaymentsdetails.getLmu() != null) {
                    existingReceiptpaymentsdetails.setLmu(receiptpaymentsdetails.getLmu());
                }
                if (receiptpaymentsdetails.getLmd() != null) {
                    existingReceiptpaymentsdetails.setLmd(receiptpaymentsdetails.getLmd());
                }
                if (receiptpaymentsdetails.getTermid() != null) {
                    existingReceiptpaymentsdetails.setTermid(receiptpaymentsdetails.getTermid());
                }
                if (receiptpaymentsdetails.getTermname() != null) {
                    existingReceiptpaymentsdetails.setTermname(receiptpaymentsdetails.getTermname());
                }
                if (receiptpaymentsdetails.getAccountno() != null) {
                    existingReceiptpaymentsdetails.setAccountno(receiptpaymentsdetails.getAccountno());
                }
                if (receiptpaymentsdetails.getAccountnumber() != null) {
                    existingReceiptpaymentsdetails.setAccountnumber(receiptpaymentsdetails.getAccountnumber());
                }
                if (receiptpaymentsdetails.getChequereturndate() != null) {
                    existingReceiptpaymentsdetails.setChequereturndate(receiptpaymentsdetails.getChequereturndate());
                }
                if (receiptpaymentsdetails.getIsdeposit() != null) {
                    existingReceiptpaymentsdetails.setIsdeposit(receiptpaymentsdetails.getIsdeposit());
                }
                if (receiptpaymentsdetails.getDepositeddate() != null) {
                    existingReceiptpaymentsdetails.setDepositeddate(receiptpaymentsdetails.getDepositeddate());
                }
                if (receiptpaymentsdetails.getChequestatuschangeddate() != null) {
                    existingReceiptpaymentsdetails.setChequestatuschangeddate(receiptpaymentsdetails.getChequestatuschangeddate());
                }
                if (receiptpaymentsdetails.getReturnchequesttledate() != null) {
                    existingReceiptpaymentsdetails.setReturnchequesttledate(receiptpaymentsdetails.getReturnchequesttledate());
                }
                if (receiptpaymentsdetails.getChequestatusid() != null) {
                    existingReceiptpaymentsdetails.setChequestatusid(receiptpaymentsdetails.getChequestatusid());
                }
                if (receiptpaymentsdetails.getIsPdCheque() != null) {
                    existingReceiptpaymentsdetails.setIsPdCheque(receiptpaymentsdetails.getIsPdCheque());
                }
                if (receiptpaymentsdetails.getDepositdate() != null) {
                    existingReceiptpaymentsdetails.setDepositdate(receiptpaymentsdetails.getDepositdate());
                }
                if (receiptpaymentsdetails.getAccountid() != null) {
                    existingReceiptpaymentsdetails.setAccountid(receiptpaymentsdetails.getAccountid());
                }
                if (receiptpaymentsdetails.getAccountcode() != null) {
                    existingReceiptpaymentsdetails.setAccountcode(receiptpaymentsdetails.getAccountcode());
                }
                if (receiptpaymentsdetails.getBankdepositbankname() != null) {
                    existingReceiptpaymentsdetails.setBankdepositbankname(receiptpaymentsdetails.getBankdepositbankname());
                }
                if (receiptpaymentsdetails.getBankdepositbankid() != null) {
                    existingReceiptpaymentsdetails.setBankdepositbankid(receiptpaymentsdetails.getBankdepositbankid());
                }
                if (receiptpaymentsdetails.getBankdepositbankbranchname() != null) {
                    existingReceiptpaymentsdetails.setBankdepositbankbranchname(receiptpaymentsdetails.getBankdepositbankbranchname());
                }
                if (receiptpaymentsdetails.getBankdepositbankbranchid() != null) {
                    existingReceiptpaymentsdetails.setBankdepositbankbranchid(receiptpaymentsdetails.getBankdepositbankbranchid());
                }
                if (receiptpaymentsdetails.getReturnchequefine() != null) {
                    existingReceiptpaymentsdetails.setReturnchequefine(receiptpaymentsdetails.getReturnchequefine());
                }
                if (receiptpaymentsdetails.getCompanybankid() != null) {
                    existingReceiptpaymentsdetails.setCompanybankid(receiptpaymentsdetails.getCompanybankid());
                }
                if (receiptpaymentsdetails.getIsbankreconciliation() != null) {
                    existingReceiptpaymentsdetails.setIsbankreconciliation(receiptpaymentsdetails.getIsbankreconciliation());
                }

                return existingReceiptpaymentsdetails;
            })
            .map(receiptpaymentsdetailsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, receiptpaymentsdetails.getId().toString())
        );
    }

    /**
     * {@code GET  /receiptpaymentsdetails} : get all the receiptpaymentsdetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of receiptpaymentsdetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Receiptpaymentsdetails>> getAllReceiptpaymentsdetails(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of Receiptpaymentsdetails");
        Page<Receiptpaymentsdetails> page = receiptpaymentsdetailsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /receiptpaymentsdetails/:id} : get the "id" receiptpaymentsdetails.
     *
     * @param id the id of the receiptpaymentsdetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the receiptpaymentsdetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}/{lineid}")
    public ResponseEntity<Receiptpaymentsdetails> getReceiptpaymentsdetails(
        @PathVariable("id") Long id,
        @PathVariable("lineid") Long lineid
    ) {
        LOG.debug("REST request to get Receiptpaymentsdetails : {}, {}", id, lineid);
        Optional<Receiptpaymentsdetails> receiptpaymentsdetails = receiptpaymentsdetailsRepository.findById(
            new ReceiptpaymentsdetailsId(id, lineid)
        );
        return ResponseUtil.wrapOrNotFound(receiptpaymentsdetails);
    }

    /**
     * {@code DELETE  /receiptpaymentsdetails/:id} : delete the "id" receiptpaymentsdetails.
     *
     * @param id the id of the receiptpaymentsdetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}/{lineid}")
    public ResponseEntity<Void> deleteReceiptpaymentsdetails(@PathVariable("id") Long id, @PathVariable("lineid") Long lineid) {
        LOG.debug("REST request to delete Receiptpaymentsdetails : {}, {}", id, lineid);
        receiptpaymentsdetailsRepository.deleteById(new ReceiptpaymentsdetailsId(id, lineid));
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString() + "-" + lineid.toString()))
            .build();
    }
}
