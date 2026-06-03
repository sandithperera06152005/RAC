package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.ReceiptpaymentsdetailsAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Receiptpaymentsdetails;
import com.heavenscode.rac.repository.ReceiptpaymentsdetailsRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReceiptpaymentsdetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReceiptpaymentsdetailsResourceIT {

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;

    private static final Float DEFAULT_PAYMENTAMOUNT = 1F;
    private static final Float UPDATED_PAYMENTAMOUNT = 2F;

    private static final Float DEFAULT_TOTALRECEIPTAMOUNT = 1F;
    private static final Float UPDATED_TOTALRECEIPTAMOUNT = 2F;

    private static final Float DEFAULT_CHECKQUEAMOUNT = 1F;
    private static final Float UPDATED_CHECKQUEAMOUNT = 2F;

    private static final String DEFAULT_CHECKQUENO = "AAAAAAAAAA";
    private static final String UPDATED_CHECKQUENO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CHECKQUEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECKQUEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CHECKQUEEXPIREDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECKQUEEXPIREDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BANKNAME = "AAAAAAAAAA";
    private static final String UPDATED_BANKNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BANKID = 1;
    private static final Integer UPDATED_BANKID = 2;

    private static final String DEFAULT_BANKBRANCHNAME = "AAAAAAAAAA";
    private static final String UPDATED_BANKBRANCHNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BANKBRANCHID = 1;
    private static final Integer UPDATED_BANKBRANCHID = 2;

    private static final String DEFAULT_CREDITCARDNO = "AAAAAAAAAA";
    private static final String UPDATED_CREDITCARDNO = "BBBBBBBBBB";

    private static final Float DEFAULT_CREDITCARDAMOUNT = 1F;
    private static final Float UPDATED_CREDITCARDAMOUNT = 2F;

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_OTHERDETAILS = "AAAAAAAAAA";
    private static final String UPDATED_OTHERDETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_LMU = "AAAAAAAAAA";
    private static final String UPDATED_LMU = "BBBBBBBBBB";

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TERMID = 1;
    private static final Integer UPDATED_TERMID = 2;

    private static final String DEFAULT_TERMNAME = "AAAAAAAAAA";
    private static final String UPDATED_TERMNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNTNO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTNO = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNTNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTNUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CHEQUERETURNDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHEQUERETURNDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ISDEPOSIT = false;
    private static final Boolean UPDATED_ISDEPOSIT = true;

    private static final Instant DEFAULT_DEPOSITEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPOSITEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CHEQUESTATUSCHANGEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHEQUESTATUSCHANGEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RETURNCHEQUESTTLEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RETURNCHEQUESTTLEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CHEQUESTATUSID = 1;
    private static final Integer UPDATED_CHEQUESTATUSID = 2;

    private static final Boolean DEFAULT_IS_PD_CHEQUE = false;
    private static final Boolean UPDATED_IS_PD_CHEQUE = true;

    private static final Instant DEFAULT_DEPOSITDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPOSITDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ACCOUNTID = 1;
    private static final Integer UPDATED_ACCOUNTID = 2;

    private static final String DEFAULT_ACCOUNTCODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANKDEPOSITBANKNAME = "AAAAAAAAAA";
    private static final String UPDATED_BANKDEPOSITBANKNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BANKDEPOSITBANKID = 1;
    private static final Integer UPDATED_BANKDEPOSITBANKID = 2;

    private static final String DEFAULT_BANKDEPOSITBANKBRANCHNAME = "AAAAAAAAAA";
    private static final String UPDATED_BANKDEPOSITBANKBRANCHNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BANKDEPOSITBANKBRANCHID = 1;
    private static final Integer UPDATED_BANKDEPOSITBANKBRANCHID = 2;

    private static final Float DEFAULT_RETURNCHEQUEFINE = 1F;
    private static final Float UPDATED_RETURNCHEQUEFINE = 2F;

    private static final Integer DEFAULT_COMPANYBANKID = 1;
    private static final Integer UPDATED_COMPANYBANKID = 2;

    private static final Boolean DEFAULT_ISBANKRECONCILIATION = false;
    private static final Boolean UPDATED_ISBANKRECONCILIATION = true;

    private static final String ENTITY_API_URL = "/api/receiptpaymentsdetails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReceiptpaymentsdetailsRepository receiptpaymentsdetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceiptpaymentsdetailsMockMvc;

    private Receiptpaymentsdetails receiptpaymentsdetails;

    private Receiptpaymentsdetails insertedReceiptpaymentsdetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receiptpaymentsdetails createEntity() {
        return new Receiptpaymentsdetails()
            .lineid(DEFAULT_LINEID)
            .paymentamount(DEFAULT_PAYMENTAMOUNT)
            .totalreceiptamount(DEFAULT_TOTALRECEIPTAMOUNT)
            .checkqueamount(DEFAULT_CHECKQUEAMOUNT)
            .checkqueno(DEFAULT_CHECKQUENO)
            .checkquedate(DEFAULT_CHECKQUEDATE)
            .checkqueexpiredate(DEFAULT_CHECKQUEEXPIREDATE)
            .bankname(DEFAULT_BANKNAME)
            .bankid(DEFAULT_BANKID)
            .bankbranchname(DEFAULT_BANKBRANCHNAME)
            .bankbranchid(DEFAULT_BANKBRANCHID)
            .creditcardno(DEFAULT_CREDITCARDNO)
            .creditcardamount(DEFAULT_CREDITCARDAMOUNT)
            .reference(DEFAULT_REFERENCE)
            .otherdetails(DEFAULT_OTHERDETAILS)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .termid(DEFAULT_TERMID)
            .termname(DEFAULT_TERMNAME)
            .accountno(DEFAULT_ACCOUNTNO)
            .accountnumber(DEFAULT_ACCOUNTNUMBER)
            .chequereturndate(DEFAULT_CHEQUERETURNDATE)
            .isdeposit(DEFAULT_ISDEPOSIT)
            .depositeddate(DEFAULT_DEPOSITEDDATE)
            .chequestatuschangeddate(DEFAULT_CHEQUESTATUSCHANGEDDATE)
            .returnchequesttledate(DEFAULT_RETURNCHEQUESTTLEDATE)
            .chequestatusid(DEFAULT_CHEQUESTATUSID)
            .isPdCheque(DEFAULT_IS_PD_CHEQUE)
            .depositdate(DEFAULT_DEPOSITDATE)
            .accountid(DEFAULT_ACCOUNTID)
            .accountcode(DEFAULT_ACCOUNTCODE)
            .bankdepositbankname(DEFAULT_BANKDEPOSITBANKNAME)
            .bankdepositbankid(DEFAULT_BANKDEPOSITBANKID)
            .bankdepositbankbranchname(DEFAULT_BANKDEPOSITBANKBRANCHNAME)
            .bankdepositbankbranchid(DEFAULT_BANKDEPOSITBANKBRANCHID)
            .returnchequefine(DEFAULT_RETURNCHEQUEFINE)
            .companybankid(DEFAULT_COMPANYBANKID)
            .isbankreconciliation(DEFAULT_ISBANKRECONCILIATION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Receiptpaymentsdetails createUpdatedEntity() {
        return new Receiptpaymentsdetails()
            .lineid(UPDATED_LINEID)
            .paymentamount(UPDATED_PAYMENTAMOUNT)
            .totalreceiptamount(UPDATED_TOTALRECEIPTAMOUNT)
            .checkqueamount(UPDATED_CHECKQUEAMOUNT)
            .checkqueno(UPDATED_CHECKQUENO)
            .checkquedate(UPDATED_CHECKQUEDATE)
            .checkqueexpiredate(UPDATED_CHECKQUEEXPIREDATE)
            .bankname(UPDATED_BANKNAME)
            .bankid(UPDATED_BANKID)
            .bankbranchname(UPDATED_BANKBRANCHNAME)
            .bankbranchid(UPDATED_BANKBRANCHID)
            .creditcardno(UPDATED_CREDITCARDNO)
            .creditcardamount(UPDATED_CREDITCARDAMOUNT)
            .reference(UPDATED_REFERENCE)
            .otherdetails(UPDATED_OTHERDETAILS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termid(UPDATED_TERMID)
            .termname(UPDATED_TERMNAME)
            .accountno(UPDATED_ACCOUNTNO)
            .accountnumber(UPDATED_ACCOUNTNUMBER)
            .chequereturndate(UPDATED_CHEQUERETURNDATE)
            .isdeposit(UPDATED_ISDEPOSIT)
            .depositeddate(UPDATED_DEPOSITEDDATE)
            .chequestatuschangeddate(UPDATED_CHEQUESTATUSCHANGEDDATE)
            .returnchequesttledate(UPDATED_RETURNCHEQUESTTLEDATE)
            .chequestatusid(UPDATED_CHEQUESTATUSID)
            .isPdCheque(UPDATED_IS_PD_CHEQUE)
            .depositdate(UPDATED_DEPOSITDATE)
            .accountid(UPDATED_ACCOUNTID)
            .accountcode(UPDATED_ACCOUNTCODE)
            .bankdepositbankname(UPDATED_BANKDEPOSITBANKNAME)
            .bankdepositbankid(UPDATED_BANKDEPOSITBANKID)
            .bankdepositbankbranchname(UPDATED_BANKDEPOSITBANKBRANCHNAME)
            .bankdepositbankbranchid(UPDATED_BANKDEPOSITBANKBRANCHID)
            .returnchequefine(UPDATED_RETURNCHEQUEFINE)
            .companybankid(UPDATED_COMPANYBANKID)
            .isbankreconciliation(UPDATED_ISBANKRECONCILIATION);
    }

    @BeforeEach
    public void initTest() {
        receiptpaymentsdetails = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedReceiptpaymentsdetails != null) {
            receiptpaymentsdetailsRepository.delete(insertedReceiptpaymentsdetails);
            insertedReceiptpaymentsdetails = null;
        }
    }

    @Test
    @Transactional
    void createReceiptpaymentsdetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Receiptpaymentsdetails
        var returnedReceiptpaymentsdetails = om.readValue(
            restReceiptpaymentsdetailsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(receiptpaymentsdetails)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Receiptpaymentsdetails.class
        );

        // Validate the Receiptpaymentsdetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertReceiptpaymentsdetailsUpdatableFieldsEquals(
            returnedReceiptpaymentsdetails,
            getPersistedReceiptpaymentsdetails(returnedReceiptpaymentsdetails)
        );

        insertedReceiptpaymentsdetails = returnedReceiptpaymentsdetails;
    }

    @Test
    @Transactional
    void createReceiptpaymentsdetailsWithExistingId() throws Exception {
        // Create the Receiptpaymentsdetails with an existing ID
        receiptpaymentsdetails.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptpaymentsdetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(receiptpaymentsdetails)))
            .andExpect(status().isBadRequest());

        // Validate the Receiptpaymentsdetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReceiptpaymentsdetails() throws Exception {
        // Initialize the database
        insertedReceiptpaymentsdetails = receiptpaymentsdetailsRepository.saveAndFlush(receiptpaymentsdetails);

        // Get all the receiptpaymentsdetailsList
        restReceiptpaymentsdetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptpaymentsdetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].paymentamount").value(hasItem(DEFAULT_PAYMENTAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalreceiptamount").value(hasItem(DEFAULT_TOTALRECEIPTAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].checkqueamount").value(hasItem(DEFAULT_CHECKQUEAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].checkqueno").value(hasItem(DEFAULT_CHECKQUENO)))
            .andExpect(jsonPath("$.[*].checkquedate").value(hasItem(DEFAULT_CHECKQUEDATE.toString())))
            .andExpect(jsonPath("$.[*].checkqueexpiredate").value(hasItem(DEFAULT_CHECKQUEEXPIREDATE.toString())))
            .andExpect(jsonPath("$.[*].bankname").value(hasItem(DEFAULT_BANKNAME)))
            .andExpect(jsonPath("$.[*].bankid").value(hasItem(DEFAULT_BANKID)))
            .andExpect(jsonPath("$.[*].bankbranchname").value(hasItem(DEFAULT_BANKBRANCHNAME)))
            .andExpect(jsonPath("$.[*].bankbranchid").value(hasItem(DEFAULT_BANKBRANCHID)))
            .andExpect(jsonPath("$.[*].creditcardno").value(hasItem(DEFAULT_CREDITCARDNO)))
            .andExpect(jsonPath("$.[*].creditcardamount").value(hasItem(DEFAULT_CREDITCARDAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].otherdetails").value(hasItem(DEFAULT_OTHERDETAILS)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].termid").value(hasItem(DEFAULT_TERMID)))
            .andExpect(jsonPath("$.[*].termname").value(hasItem(DEFAULT_TERMNAME)))
            .andExpect(jsonPath("$.[*].accountno").value(hasItem(DEFAULT_ACCOUNTNO)))
            .andExpect(jsonPath("$.[*].accountnumber").value(hasItem(DEFAULT_ACCOUNTNUMBER)))
            .andExpect(jsonPath("$.[*].chequereturndate").value(hasItem(DEFAULT_CHEQUERETURNDATE.toString())))
            .andExpect(jsonPath("$.[*].isdeposit").value(hasItem(DEFAULT_ISDEPOSIT.booleanValue())))
            .andExpect(jsonPath("$.[*].depositeddate").value(hasItem(DEFAULT_DEPOSITEDDATE.toString())))
            .andExpect(jsonPath("$.[*].chequestatuschangeddate").value(hasItem(DEFAULT_CHEQUESTATUSCHANGEDDATE.toString())))
            .andExpect(jsonPath("$.[*].returnchequesttledate").value(hasItem(DEFAULT_RETURNCHEQUESTTLEDATE.toString())))
            .andExpect(jsonPath("$.[*].chequestatusid").value(hasItem(DEFAULT_CHEQUESTATUSID)))
            .andExpect(jsonPath("$.[*].isPdCheque").value(hasItem(DEFAULT_IS_PD_CHEQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].depositdate").value(hasItem(DEFAULT_DEPOSITDATE.toString())))
            .andExpect(jsonPath("$.[*].accountid").value(hasItem(DEFAULT_ACCOUNTID)))
            .andExpect(jsonPath("$.[*].accountcode").value(hasItem(DEFAULT_ACCOUNTCODE)))
            .andExpect(jsonPath("$.[*].bankdepositbankname").value(hasItem(DEFAULT_BANKDEPOSITBANKNAME)))
            .andExpect(jsonPath("$.[*].bankdepositbankid").value(hasItem(DEFAULT_BANKDEPOSITBANKID)))
            .andExpect(jsonPath("$.[*].bankdepositbankbranchname").value(hasItem(DEFAULT_BANKDEPOSITBANKBRANCHNAME)))
            .andExpect(jsonPath("$.[*].bankdepositbankbranchid").value(hasItem(DEFAULT_BANKDEPOSITBANKBRANCHID)))
            .andExpect(jsonPath("$.[*].returnchequefine").value(hasItem(DEFAULT_RETURNCHEQUEFINE.doubleValue())))
            .andExpect(jsonPath("$.[*].companybankid").value(hasItem(DEFAULT_COMPANYBANKID)))
            .andExpect(jsonPath("$.[*].isbankreconciliation").value(hasItem(DEFAULT_ISBANKRECONCILIATION.booleanValue())));
    }

    @Test
    @Transactional
    void getReceiptpaymentsdetails() throws Exception {
        // Initialize the database
        insertedReceiptpaymentsdetails = receiptpaymentsdetailsRepository.saveAndFlush(receiptpaymentsdetails);

        // Get the receiptpaymentsdetails
        restReceiptpaymentsdetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, receiptpaymentsdetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receiptpaymentsdetails.getId().intValue()))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.paymentamount").value(DEFAULT_PAYMENTAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalreceiptamount").value(DEFAULT_TOTALRECEIPTAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.checkqueamount").value(DEFAULT_CHECKQUEAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.checkqueno").value(DEFAULT_CHECKQUENO))
            .andExpect(jsonPath("$.checkquedate").value(DEFAULT_CHECKQUEDATE.toString()))
            .andExpect(jsonPath("$.checkqueexpiredate").value(DEFAULT_CHECKQUEEXPIREDATE.toString()))
            .andExpect(jsonPath("$.bankname").value(DEFAULT_BANKNAME))
            .andExpect(jsonPath("$.bankid").value(DEFAULT_BANKID))
            .andExpect(jsonPath("$.bankbranchname").value(DEFAULT_BANKBRANCHNAME))
            .andExpect(jsonPath("$.bankbranchid").value(DEFAULT_BANKBRANCHID))
            .andExpect(jsonPath("$.creditcardno").value(DEFAULT_CREDITCARDNO))
            .andExpect(jsonPath("$.creditcardamount").value(DEFAULT_CREDITCARDAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.otherdetails").value(DEFAULT_OTHERDETAILS))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.termid").value(DEFAULT_TERMID))
            .andExpect(jsonPath("$.termname").value(DEFAULT_TERMNAME))
            .andExpect(jsonPath("$.accountno").value(DEFAULT_ACCOUNTNO))
            .andExpect(jsonPath("$.accountnumber").value(DEFAULT_ACCOUNTNUMBER))
            .andExpect(jsonPath("$.chequereturndate").value(DEFAULT_CHEQUERETURNDATE.toString()))
            .andExpect(jsonPath("$.isdeposit").value(DEFAULT_ISDEPOSIT.booleanValue()))
            .andExpect(jsonPath("$.depositeddate").value(DEFAULT_DEPOSITEDDATE.toString()))
            .andExpect(jsonPath("$.chequestatuschangeddate").value(DEFAULT_CHEQUESTATUSCHANGEDDATE.toString()))
            .andExpect(jsonPath("$.returnchequesttledate").value(DEFAULT_RETURNCHEQUESTTLEDATE.toString()))
            .andExpect(jsonPath("$.chequestatusid").value(DEFAULT_CHEQUESTATUSID))
            .andExpect(jsonPath("$.isPdCheque").value(DEFAULT_IS_PD_CHEQUE.booleanValue()))
            .andExpect(jsonPath("$.depositdate").value(DEFAULT_DEPOSITDATE.toString()))
            .andExpect(jsonPath("$.accountid").value(DEFAULT_ACCOUNTID))
            .andExpect(jsonPath("$.accountcode").value(DEFAULT_ACCOUNTCODE))
            .andExpect(jsonPath("$.bankdepositbankname").value(DEFAULT_BANKDEPOSITBANKNAME))
            .andExpect(jsonPath("$.bankdepositbankid").value(DEFAULT_BANKDEPOSITBANKID))
            .andExpect(jsonPath("$.bankdepositbankbranchname").value(DEFAULT_BANKDEPOSITBANKBRANCHNAME))
            .andExpect(jsonPath("$.bankdepositbankbranchid").value(DEFAULT_BANKDEPOSITBANKBRANCHID))
            .andExpect(jsonPath("$.returnchequefine").value(DEFAULT_RETURNCHEQUEFINE.doubleValue()))
            .andExpect(jsonPath("$.companybankid").value(DEFAULT_COMPANYBANKID))
            .andExpect(jsonPath("$.isbankreconciliation").value(DEFAULT_ISBANKRECONCILIATION.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingReceiptpaymentsdetails() throws Exception {
        // Get the receiptpaymentsdetails
        restReceiptpaymentsdetailsMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReceiptpaymentsdetails() throws Exception {
        // Initialize the database
        insertedReceiptpaymentsdetails = receiptpaymentsdetailsRepository.saveAndFlush(receiptpaymentsdetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the receiptpaymentsdetails
        Receiptpaymentsdetails updatedReceiptpaymentsdetails = receiptpaymentsdetailsRepository
            .findById(receiptpaymentsdetails.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedReceiptpaymentsdetails are not directly saved in db
        em.detach(updatedReceiptpaymentsdetails);
        updatedReceiptpaymentsdetails
            .lineid(UPDATED_LINEID)
            .paymentamount(UPDATED_PAYMENTAMOUNT)
            .totalreceiptamount(UPDATED_TOTALRECEIPTAMOUNT)
            .checkqueamount(UPDATED_CHECKQUEAMOUNT)
            .checkqueno(UPDATED_CHECKQUENO)
            .checkquedate(UPDATED_CHECKQUEDATE)
            .checkqueexpiredate(UPDATED_CHECKQUEEXPIREDATE)
            .bankname(UPDATED_BANKNAME)
            .bankid(UPDATED_BANKID)
            .bankbranchname(UPDATED_BANKBRANCHNAME)
            .bankbranchid(UPDATED_BANKBRANCHID)
            .creditcardno(UPDATED_CREDITCARDNO)
            .creditcardamount(UPDATED_CREDITCARDAMOUNT)
            .reference(UPDATED_REFERENCE)
            .otherdetails(UPDATED_OTHERDETAILS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termid(UPDATED_TERMID)
            .termname(UPDATED_TERMNAME)
            .accountno(UPDATED_ACCOUNTNO)
            .accountnumber(UPDATED_ACCOUNTNUMBER)
            .chequereturndate(UPDATED_CHEQUERETURNDATE)
            .isdeposit(UPDATED_ISDEPOSIT)
            .depositeddate(UPDATED_DEPOSITEDDATE)
            .chequestatuschangeddate(UPDATED_CHEQUESTATUSCHANGEDDATE)
            .returnchequesttledate(UPDATED_RETURNCHEQUESTTLEDATE)
            .chequestatusid(UPDATED_CHEQUESTATUSID)
            .isPdCheque(UPDATED_IS_PD_CHEQUE)
            .depositdate(UPDATED_DEPOSITDATE)
            .accountid(UPDATED_ACCOUNTID)
            .accountcode(UPDATED_ACCOUNTCODE)
            .bankdepositbankname(UPDATED_BANKDEPOSITBANKNAME)
            .bankdepositbankid(UPDATED_BANKDEPOSITBANKID)
            .bankdepositbankbranchname(UPDATED_BANKDEPOSITBANKBRANCHNAME)
            .bankdepositbankbranchid(UPDATED_BANKDEPOSITBANKBRANCHID)
            .returnchequefine(UPDATED_RETURNCHEQUEFINE)
            .companybankid(UPDATED_COMPANYBANKID)
            .isbankreconciliation(UPDATED_ISBANKRECONCILIATION);

        restReceiptpaymentsdetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReceiptpaymentsdetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedReceiptpaymentsdetails))
            )
            .andExpect(status().isOk());

        // Validate the Receiptpaymentsdetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReceiptpaymentsdetailsToMatchAllProperties(updatedReceiptpaymentsdetails);
    }

    @Test
    @Transactional
    void putNonExistingReceiptpaymentsdetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptpaymentsdetails.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptpaymentsdetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, receiptpaymentsdetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(receiptpaymentsdetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receiptpaymentsdetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReceiptpaymentsdetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptpaymentsdetails.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiptpaymentsdetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(receiptpaymentsdetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receiptpaymentsdetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReceiptpaymentsdetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptpaymentsdetails.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiptpaymentsdetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(receiptpaymentsdetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Receiptpaymentsdetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReceiptpaymentsdetailsWithPatch() throws Exception {
        // Initialize the database
        insertedReceiptpaymentsdetails = receiptpaymentsdetailsRepository.saveAndFlush(receiptpaymentsdetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the receiptpaymentsdetails using partial update
        Receiptpaymentsdetails partialUpdatedReceiptpaymentsdetails = new Receiptpaymentsdetails();
        partialUpdatedReceiptpaymentsdetails.setId(receiptpaymentsdetails.getId());

        partialUpdatedReceiptpaymentsdetails
            .totalreceiptamount(UPDATED_TOTALRECEIPTAMOUNT)
            .checkqueamount(UPDATED_CHECKQUEAMOUNT)
            .bankname(UPDATED_BANKNAME)
            .bankbranchid(UPDATED_BANKBRANCHID)
            .creditcardamount(UPDATED_CREDITCARDAMOUNT)
            .termid(UPDATED_TERMID)
            .termname(UPDATED_TERMNAME)
            .accountnumber(UPDATED_ACCOUNTNUMBER)
            .chequereturndate(UPDATED_CHEQUERETURNDATE)
            .isdeposit(UPDATED_ISDEPOSIT)
            .chequestatuschangeddate(UPDATED_CHEQUESTATUSCHANGEDDATE)
            .returnchequesttledate(UPDATED_RETURNCHEQUESTTLEDATE)
            .chequestatusid(UPDATED_CHEQUESTATUSID)
            .accountid(UPDATED_ACCOUNTID)
            .accountcode(UPDATED_ACCOUNTCODE)
            .bankdepositbankbranchname(UPDATED_BANKDEPOSITBANKBRANCHNAME)
            .bankdepositbankbranchid(UPDATED_BANKDEPOSITBANKBRANCHID)
            .returnchequefine(UPDATED_RETURNCHEQUEFINE)
            .companybankid(UPDATED_COMPANYBANKID);

        restReceiptpaymentsdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceiptpaymentsdetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReceiptpaymentsdetails))
            )
            .andExpect(status().isOk());

        // Validate the Receiptpaymentsdetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReceiptpaymentsdetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedReceiptpaymentsdetails, receiptpaymentsdetails),
            getPersistedReceiptpaymentsdetails(receiptpaymentsdetails)
        );
    }

    @Test
    @Transactional
    void fullUpdateReceiptpaymentsdetailsWithPatch() throws Exception {
        // Initialize the database
        insertedReceiptpaymentsdetails = receiptpaymentsdetailsRepository.saveAndFlush(receiptpaymentsdetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the receiptpaymentsdetails using partial update
        Receiptpaymentsdetails partialUpdatedReceiptpaymentsdetails = new Receiptpaymentsdetails();
        partialUpdatedReceiptpaymentsdetails.setId(receiptpaymentsdetails.getId());

        partialUpdatedReceiptpaymentsdetails
            .lineid(UPDATED_LINEID)
            .paymentamount(UPDATED_PAYMENTAMOUNT)
            .totalreceiptamount(UPDATED_TOTALRECEIPTAMOUNT)
            .checkqueamount(UPDATED_CHECKQUEAMOUNT)
            .checkqueno(UPDATED_CHECKQUENO)
            .checkquedate(UPDATED_CHECKQUEDATE)
            .checkqueexpiredate(UPDATED_CHECKQUEEXPIREDATE)
            .bankname(UPDATED_BANKNAME)
            .bankid(UPDATED_BANKID)
            .bankbranchname(UPDATED_BANKBRANCHNAME)
            .bankbranchid(UPDATED_BANKBRANCHID)
            .creditcardno(UPDATED_CREDITCARDNO)
            .creditcardamount(UPDATED_CREDITCARDAMOUNT)
            .reference(UPDATED_REFERENCE)
            .otherdetails(UPDATED_OTHERDETAILS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termid(UPDATED_TERMID)
            .termname(UPDATED_TERMNAME)
            .accountno(UPDATED_ACCOUNTNO)
            .accountnumber(UPDATED_ACCOUNTNUMBER)
            .chequereturndate(UPDATED_CHEQUERETURNDATE)
            .isdeposit(UPDATED_ISDEPOSIT)
            .depositeddate(UPDATED_DEPOSITEDDATE)
            .chequestatuschangeddate(UPDATED_CHEQUESTATUSCHANGEDDATE)
            .returnchequesttledate(UPDATED_RETURNCHEQUESTTLEDATE)
            .chequestatusid(UPDATED_CHEQUESTATUSID)
            .isPdCheque(UPDATED_IS_PD_CHEQUE)
            .depositdate(UPDATED_DEPOSITDATE)
            .accountid(UPDATED_ACCOUNTID)
            .accountcode(UPDATED_ACCOUNTCODE)
            .bankdepositbankname(UPDATED_BANKDEPOSITBANKNAME)
            .bankdepositbankid(UPDATED_BANKDEPOSITBANKID)
            .bankdepositbankbranchname(UPDATED_BANKDEPOSITBANKBRANCHNAME)
            .bankdepositbankbranchid(UPDATED_BANKDEPOSITBANKBRANCHID)
            .returnchequefine(UPDATED_RETURNCHEQUEFINE)
            .companybankid(UPDATED_COMPANYBANKID)
            .isbankreconciliation(UPDATED_ISBANKRECONCILIATION);

        restReceiptpaymentsdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceiptpaymentsdetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReceiptpaymentsdetails))
            )
            .andExpect(status().isOk());

        // Validate the Receiptpaymentsdetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReceiptpaymentsdetailsUpdatableFieldsEquals(
            partialUpdatedReceiptpaymentsdetails,
            getPersistedReceiptpaymentsdetails(partialUpdatedReceiptpaymentsdetails)
        );
    }

    @Test
    @Transactional
    void patchNonExistingReceiptpaymentsdetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptpaymentsdetails.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptpaymentsdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, receiptpaymentsdetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(receiptpaymentsdetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receiptpaymentsdetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReceiptpaymentsdetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptpaymentsdetails.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiptpaymentsdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(receiptpaymentsdetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the Receiptpaymentsdetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReceiptpaymentsdetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptpaymentsdetails.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiptpaymentsdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(receiptpaymentsdetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Receiptpaymentsdetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReceiptpaymentsdetails() throws Exception {
        // Initialize the database
        insertedReceiptpaymentsdetails = receiptpaymentsdetailsRepository.saveAndFlush(receiptpaymentsdetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the receiptpaymentsdetails
        restReceiptpaymentsdetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, receiptpaymentsdetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return receiptpaymentsdetailsRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Receiptpaymentsdetails getPersistedReceiptpaymentsdetails(Receiptpaymentsdetails receiptpaymentsdetails) {
        return receiptpaymentsdetailsRepository.findById(receiptpaymentsdetails.getId()).orElseThrow();
    }

    protected void assertPersistedReceiptpaymentsdetailsToMatchAllProperties(Receiptpaymentsdetails expectedReceiptpaymentsdetails) {
        assertReceiptpaymentsdetailsAllPropertiesEquals(
            expectedReceiptpaymentsdetails,
            getPersistedReceiptpaymentsdetails(expectedReceiptpaymentsdetails)
        );
    }

    protected void assertPersistedReceiptpaymentsdetailsToMatchUpdatableProperties(Receiptpaymentsdetails expectedReceiptpaymentsdetails) {
        assertReceiptpaymentsdetailsAllUpdatablePropertiesEquals(
            expectedReceiptpaymentsdetails,
            getPersistedReceiptpaymentsdetails(expectedReceiptpaymentsdetails)
        );
    }
}
