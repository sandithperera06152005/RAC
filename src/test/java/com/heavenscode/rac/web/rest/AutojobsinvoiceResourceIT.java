package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutojobsinvoiceAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autojobsinvoice;
import com.heavenscode.rac.repository.AutojobsinvoiceRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link AutojobsinvoiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutojobsinvoiceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_INVOICEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_JOBID = 1;
    private static final Integer UPDATED_JOBID = 2;
    private static final Integer SMALLER_JOBID = 1 - 1;

    private static final Integer DEFAULT_QUOTEID = 1;
    private static final Integer UPDATED_QUOTEID = 2;
    private static final Integer SMALLER_QUOTEID = 1 - 1;

    private static final Integer DEFAULT_ORDERID = 1;
    private static final Integer UPDATED_ORDERID = 2;
    private static final Integer SMALLER_ORDERID = 1 - 1;

    private static final Instant DEFAULT_DELIEVERYDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIEVERYDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_AUTOJOBSREPID = 1;
    private static final Integer UPDATED_AUTOJOBSREPID = 2;
    private static final Integer SMALLER_AUTOJOBSREPID = 1 - 1;

    private static final String DEFAULT_AUTOJOBSREPNAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTOJOBSREPNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DELIEVERFROM = "AAAAAAAAAA";
    private static final String UPDATED_DELIEVERFROM = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMERID = 1;
    private static final Integer UPDATED_CUSTOMERID = 2;
    private static final Integer SMALLER_CUSTOMERID = 1 - 1;

    private static final String DEFAULT_CUSTOMERNAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMERADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERYADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERYADDRESS = "BBBBBBBBBB";

    private static final Float DEFAULT_SUBTOTAL = 1F;
    private static final Float UPDATED_SUBTOTAL = 2F;
    private static final Float SMALLER_SUBTOTAL = 1F - 1F;

    private static final Float DEFAULT_TOTALTAX = 1F;
    private static final Float UPDATED_TOTALTAX = 2F;
    private static final Float SMALLER_TOTALTAX = 1F - 1F;

    private static final Float DEFAULT_TOTALDISCOUNT = 1F;
    private static final Float UPDATED_TOTALDISCOUNT = 2F;
    private static final Float SMALLER_TOTALDISCOUNT = 1F - 1F;

    private static final Float DEFAULT_NETTOTAL = 1F;
    private static final Float UPDATED_NETTOTAL = 2F;
    private static final Float SMALLER_NETTOTAL = 1F - 1F;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_PAIDAMOUNT = 1F;
    private static final Float UPDATED_PAIDAMOUNT = 2F;
    private static final Float SMALLER_PAIDAMOUNT = 1F - 1F;

    private static final Float DEFAULT_AMOUNTOWING = 1F;
    private static final Float UPDATED_AMOUNTOWING = 2F;
    private static final Float SMALLER_AMOUNTOWING = 1F - 1F;

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Integer DEFAULT_LOCATIONID = 1;
    private static final Integer UPDATED_LOCATIONID = 2;
    private static final Integer SMALLER_LOCATIONID = 1 - 1;

    private static final String DEFAULT_LOCATIONCODE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIONCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCECODE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCECODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATEDBYID = 1;
    private static final Integer UPDATED_CREATEDBYID = 2;
    private static final Integer SMALLER_CREATEDBYID = 1 - 1;

    private static final String DEFAULT_CREATEDBYNAME = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBYNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AUTOCARECOMPANYID = 1;
    private static final Integer UPDATED_AUTOCARECOMPANYID = 2;
    private static final Integer SMALLER_AUTOCARECOMPANYID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/autojobsinvoices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutojobsinvoiceRepository autojobsinvoiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutojobsinvoiceMockMvc;

    private Autojobsinvoice autojobsinvoice;

    private Autojobsinvoice insertedAutojobsinvoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsinvoice createEntity() {
        return new Autojobsinvoice()
            .code(DEFAULT_CODE)
            .invoicedate(DEFAULT_INVOICEDATE)
            .createddate(DEFAULT_CREATEDDATE)
            .jobid(DEFAULT_JOBID)
            .quoteid(DEFAULT_QUOTEID)
            .orderid(DEFAULT_ORDERID)
            .delieverydate(DEFAULT_DELIEVERYDATE)
            .autojobsrepid(DEFAULT_AUTOJOBSREPID)
            .autojobsrepname(DEFAULT_AUTOJOBSREPNAME)
            .delieverfrom(DEFAULT_DELIEVERFROM)
            .customerid(DEFAULT_CUSTOMERID)
            .customername(DEFAULT_CUSTOMERNAME)
            .customeraddress(DEFAULT_CUSTOMERADDRESS)
            .deliveryaddress(DEFAULT_DELIVERYADDRESS)
            .subtotal(DEFAULT_SUBTOTAL)
            .totaltax(DEFAULT_TOTALTAX)
            .totaldiscount(DEFAULT_TOTALDISCOUNT)
            .nettotal(DEFAULT_NETTOTAL)
            .message(DEFAULT_MESSAGE)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .paidamount(DEFAULT_PAIDAMOUNT)
            .amountowing(DEFAULT_AMOUNTOWING)
            .isactive(DEFAULT_ISACTIVE)
            .locationid(DEFAULT_LOCATIONID)
            .locationcode(DEFAULT_LOCATIONCODE)
            .referencecode(DEFAULT_REFERENCECODE)
            .createdbyid(DEFAULT_CREATEDBYID)
            .createdbyname(DEFAULT_CREATEDBYNAME)
            .autocarecompanyid(DEFAULT_AUTOCARECOMPANYID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsinvoice createUpdatedEntity() {
        return new Autojobsinvoice()
            .code(UPDATED_CODE)
            .invoicedate(UPDATED_INVOICEDATE)
            .createddate(UPDATED_CREATEDDATE)
            .jobid(UPDATED_JOBID)
            .quoteid(UPDATED_QUOTEID)
            .orderid(UPDATED_ORDERID)
            .delieverydate(UPDATED_DELIEVERYDATE)
            .autojobsrepid(UPDATED_AUTOJOBSREPID)
            .autojobsrepname(UPDATED_AUTOJOBSREPNAME)
            .delieverfrom(UPDATED_DELIEVERFROM)
            .customerid(UPDATED_CUSTOMERID)
            .customername(UPDATED_CUSTOMERNAME)
            .customeraddress(UPDATED_CUSTOMERADDRESS)
            .deliveryaddress(UPDATED_DELIVERYADDRESS)
            .subtotal(UPDATED_SUBTOTAL)
            .totaltax(UPDATED_TOTALTAX)
            .totaldiscount(UPDATED_TOTALDISCOUNT)
            .nettotal(UPDATED_NETTOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidamount(UPDATED_PAIDAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .isactive(UPDATED_ISACTIVE)
            .locationid(UPDATED_LOCATIONID)
            .locationcode(UPDATED_LOCATIONCODE)
            .referencecode(UPDATED_REFERENCECODE)
            .createdbyid(UPDATED_CREATEDBYID)
            .createdbyname(UPDATED_CREATEDBYNAME)
            .autocarecompanyid(UPDATED_AUTOCARECOMPANYID);
    }

    @BeforeEach
    public void initTest() {
        autojobsinvoice = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutojobsinvoice != null) {
            autojobsinvoiceRepository.delete(insertedAutojobsinvoice);
            insertedAutojobsinvoice = null;
        }
    }

    @Test
    @Transactional
    void createAutojobsinvoice() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autojobsinvoice
        var returnedAutojobsinvoice = om.readValue(
            restAutojobsinvoiceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoice)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autojobsinvoice.class
        );

        // Validate the Autojobsinvoice in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutojobsinvoiceUpdatableFieldsEquals(returnedAutojobsinvoice, getPersistedAutojobsinvoice(returnedAutojobsinvoice));

        insertedAutojobsinvoice = returnedAutojobsinvoice;
    }

    @Test
    @Transactional
    void createAutojobsinvoiceWithExistingId() throws Exception {
        // Create the Autojobsinvoice with an existing ID
        autojobsinvoice.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutojobsinvoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoice)))
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoices() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList
        restAutojobsinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsinvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].invoicedate").value(hasItem(DEFAULT_INVOICEDATE.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].jobid").value(hasItem(DEFAULT_JOBID)))
            .andExpect(jsonPath("$.[*].quoteid").value(hasItem(DEFAULT_QUOTEID)))
            .andExpect(jsonPath("$.[*].orderid").value(hasItem(DEFAULT_ORDERID)))
            .andExpect(jsonPath("$.[*].delieverydate").value(hasItem(DEFAULT_DELIEVERYDATE.toString())))
            .andExpect(jsonPath("$.[*].autojobsrepid").value(hasItem(DEFAULT_AUTOJOBSREPID)))
            .andExpect(jsonPath("$.[*].autojobsrepname").value(hasItem(DEFAULT_AUTOJOBSREPNAME)))
            .andExpect(jsonPath("$.[*].delieverfrom").value(hasItem(DEFAULT_DELIEVERFROM)))
            .andExpect(jsonPath("$.[*].customerid").value(hasItem(DEFAULT_CUSTOMERID)))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME)))
            .andExpect(jsonPath("$.[*].customeraddress").value(hasItem(DEFAULT_CUSTOMERADDRESS)))
            .andExpect(jsonPath("$.[*].deliveryaddress").value(hasItem(DEFAULT_DELIVERYADDRESS)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].totaltax").value(hasItem(DEFAULT_TOTALTAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totaldiscount").value(hasItem(DEFAULT_TOTALDISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].nettotal").value(hasItem(DEFAULT_NETTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].paidamount").value(hasItem(DEFAULT_PAIDAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountowing").value(hasItem(DEFAULT_AMOUNTOWING.doubleValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].locationid").value(hasItem(DEFAULT_LOCATIONID)))
            .andExpect(jsonPath("$.[*].locationcode").value(hasItem(DEFAULT_LOCATIONCODE)))
            .andExpect(jsonPath("$.[*].referencecode").value(hasItem(DEFAULT_REFERENCECODE)))
            .andExpect(jsonPath("$.[*].createdbyid").value(hasItem(DEFAULT_CREATEDBYID)))
            .andExpect(jsonPath("$.[*].createdbyname").value(hasItem(DEFAULT_CREATEDBYNAME)))
            .andExpect(jsonPath("$.[*].autocarecompanyid").value(hasItem(DEFAULT_AUTOCARECOMPANYID)));
    }

    @Test
    @Transactional
    void getAutojobsinvoice() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get the autojobsinvoice
        restAutojobsinvoiceMockMvc
            .perform(get(ENTITY_API_URL_ID, autojobsinvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autojobsinvoice.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.invoicedate").value(DEFAULT_INVOICEDATE.toString()))
            .andExpect(jsonPath("$.createddate").value(DEFAULT_CREATEDDATE.toString()))
            .andExpect(jsonPath("$.jobid").value(DEFAULT_JOBID))
            .andExpect(jsonPath("$.quoteid").value(DEFAULT_QUOTEID))
            .andExpect(jsonPath("$.orderid").value(DEFAULT_ORDERID))
            .andExpect(jsonPath("$.delieverydate").value(DEFAULT_DELIEVERYDATE.toString()))
            .andExpect(jsonPath("$.autojobsrepid").value(DEFAULT_AUTOJOBSREPID))
            .andExpect(jsonPath("$.autojobsrepname").value(DEFAULT_AUTOJOBSREPNAME))
            .andExpect(jsonPath("$.delieverfrom").value(DEFAULT_DELIEVERFROM))
            .andExpect(jsonPath("$.customerid").value(DEFAULT_CUSTOMERID))
            .andExpect(jsonPath("$.customername").value(DEFAULT_CUSTOMERNAME))
            .andExpect(jsonPath("$.customeraddress").value(DEFAULT_CUSTOMERADDRESS))
            .andExpect(jsonPath("$.deliveryaddress").value(DEFAULT_DELIVERYADDRESS))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.doubleValue()))
            .andExpect(jsonPath("$.totaltax").value(DEFAULT_TOTALTAX.doubleValue()))
            .andExpect(jsonPath("$.totaldiscount").value(DEFAULT_TOTALDISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.nettotal").value(DEFAULT_NETTOTAL.doubleValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.paidamount").value(DEFAULT_PAIDAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountowing").value(DEFAULT_AMOUNTOWING.doubleValue()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.locationid").value(DEFAULT_LOCATIONID))
            .andExpect(jsonPath("$.locationcode").value(DEFAULT_LOCATIONCODE))
            .andExpect(jsonPath("$.referencecode").value(DEFAULT_REFERENCECODE))
            .andExpect(jsonPath("$.createdbyid").value(DEFAULT_CREATEDBYID))
            .andExpect(jsonPath("$.createdbyname").value(DEFAULT_CREATEDBYNAME))
            .andExpect(jsonPath("$.autocarecompanyid").value(DEFAULT_AUTOCARECOMPANYID));
    }

    @Test
    @Transactional
    void getAutojobsinvoicesByIdFiltering() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        Long id = autojobsinvoice.getId();

        defaultAutojobsinvoiceFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAutojobsinvoiceFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAutojobsinvoiceFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where code equals to
        defaultAutojobsinvoiceFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where code in
        defaultAutojobsinvoiceFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where code is not null
        defaultAutojobsinvoiceFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where code contains
        defaultAutojobsinvoiceFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where code does not contain
        defaultAutojobsinvoiceFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByInvoicedateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where invoicedate equals to
        defaultAutojobsinvoiceFiltering("invoicedate.equals=" + DEFAULT_INVOICEDATE, "invoicedate.equals=" + UPDATED_INVOICEDATE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByInvoicedateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where invoicedate in
        defaultAutojobsinvoiceFiltering(
            "invoicedate.in=" + DEFAULT_INVOICEDATE + "," + UPDATED_INVOICEDATE,
            "invoicedate.in=" + UPDATED_INVOICEDATE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByInvoicedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where invoicedate is not null
        defaultAutojobsinvoiceFiltering("invoicedate.specified=true", "invoicedate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreateddateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createddate equals to
        defaultAutojobsinvoiceFiltering("createddate.equals=" + DEFAULT_CREATEDDATE, "createddate.equals=" + UPDATED_CREATEDDATE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreateddateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createddate in
        defaultAutojobsinvoiceFiltering(
            "createddate.in=" + DEFAULT_CREATEDDATE + "," + UPDATED_CREATEDDATE,
            "createddate.in=" + UPDATED_CREATEDDATE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreateddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createddate is not null
        defaultAutojobsinvoiceFiltering("createddate.specified=true", "createddate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByJobidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where jobid equals to
        defaultAutojobsinvoiceFiltering("jobid.equals=" + DEFAULT_JOBID, "jobid.equals=" + UPDATED_JOBID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByJobidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where jobid in
        defaultAutojobsinvoiceFiltering("jobid.in=" + DEFAULT_JOBID + "," + UPDATED_JOBID, "jobid.in=" + UPDATED_JOBID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByJobidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where jobid is not null
        defaultAutojobsinvoiceFiltering("jobid.specified=true", "jobid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByJobidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where jobid is greater than or equal to
        defaultAutojobsinvoiceFiltering("jobid.greaterThanOrEqual=" + DEFAULT_JOBID, "jobid.greaterThanOrEqual=" + UPDATED_JOBID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByJobidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where jobid is less than or equal to
        defaultAutojobsinvoiceFiltering("jobid.lessThanOrEqual=" + DEFAULT_JOBID, "jobid.lessThanOrEqual=" + SMALLER_JOBID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByJobidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where jobid is less than
        defaultAutojobsinvoiceFiltering("jobid.lessThan=" + UPDATED_JOBID, "jobid.lessThan=" + DEFAULT_JOBID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByJobidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where jobid is greater than
        defaultAutojobsinvoiceFiltering("jobid.greaterThan=" + SMALLER_JOBID, "jobid.greaterThan=" + DEFAULT_JOBID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByQuoteidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where quoteid equals to
        defaultAutojobsinvoiceFiltering("quoteid.equals=" + DEFAULT_QUOTEID, "quoteid.equals=" + UPDATED_QUOTEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByQuoteidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where quoteid in
        defaultAutojobsinvoiceFiltering("quoteid.in=" + DEFAULT_QUOTEID + "," + UPDATED_QUOTEID, "quoteid.in=" + UPDATED_QUOTEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByQuoteidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where quoteid is not null
        defaultAutojobsinvoiceFiltering("quoteid.specified=true", "quoteid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByQuoteidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where quoteid is greater than or equal to
        defaultAutojobsinvoiceFiltering("quoteid.greaterThanOrEqual=" + DEFAULT_QUOTEID, "quoteid.greaterThanOrEqual=" + UPDATED_QUOTEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByQuoteidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where quoteid is less than or equal to
        defaultAutojobsinvoiceFiltering("quoteid.lessThanOrEqual=" + DEFAULT_QUOTEID, "quoteid.lessThanOrEqual=" + SMALLER_QUOTEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByQuoteidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where quoteid is less than
        defaultAutojobsinvoiceFiltering("quoteid.lessThan=" + UPDATED_QUOTEID, "quoteid.lessThan=" + DEFAULT_QUOTEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByQuoteidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where quoteid is greater than
        defaultAutojobsinvoiceFiltering("quoteid.greaterThan=" + SMALLER_QUOTEID, "quoteid.greaterThan=" + DEFAULT_QUOTEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByOrderidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where orderid equals to
        defaultAutojobsinvoiceFiltering("orderid.equals=" + DEFAULT_ORDERID, "orderid.equals=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByOrderidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where orderid in
        defaultAutojobsinvoiceFiltering("orderid.in=" + DEFAULT_ORDERID + "," + UPDATED_ORDERID, "orderid.in=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByOrderidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where orderid is not null
        defaultAutojobsinvoiceFiltering("orderid.specified=true", "orderid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByOrderidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where orderid is greater than or equal to
        defaultAutojobsinvoiceFiltering("orderid.greaterThanOrEqual=" + DEFAULT_ORDERID, "orderid.greaterThanOrEqual=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByOrderidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where orderid is less than or equal to
        defaultAutojobsinvoiceFiltering("orderid.lessThanOrEqual=" + DEFAULT_ORDERID, "orderid.lessThanOrEqual=" + SMALLER_ORDERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByOrderidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where orderid is less than
        defaultAutojobsinvoiceFiltering("orderid.lessThan=" + UPDATED_ORDERID, "orderid.lessThan=" + DEFAULT_ORDERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByOrderidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where orderid is greater than
        defaultAutojobsinvoiceFiltering("orderid.greaterThan=" + SMALLER_ORDERID, "orderid.greaterThan=" + DEFAULT_ORDERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDelieverydateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where delieverydate equals to
        defaultAutojobsinvoiceFiltering("delieverydate.equals=" + DEFAULT_DELIEVERYDATE, "delieverydate.equals=" + UPDATED_DELIEVERYDATE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDelieverydateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where delieverydate in
        defaultAutojobsinvoiceFiltering(
            "delieverydate.in=" + DEFAULT_DELIEVERYDATE + "," + UPDATED_DELIEVERYDATE,
            "delieverydate.in=" + UPDATED_DELIEVERYDATE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDelieverydateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where delieverydate is not null
        defaultAutojobsinvoiceFiltering("delieverydate.specified=true", "delieverydate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepid equals to
        defaultAutojobsinvoiceFiltering("autojobsrepid.equals=" + DEFAULT_AUTOJOBSREPID, "autojobsrepid.equals=" + UPDATED_AUTOJOBSREPID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepid in
        defaultAutojobsinvoiceFiltering(
            "autojobsrepid.in=" + DEFAULT_AUTOJOBSREPID + "," + UPDATED_AUTOJOBSREPID,
            "autojobsrepid.in=" + UPDATED_AUTOJOBSREPID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepid is not null
        defaultAutojobsinvoiceFiltering("autojobsrepid.specified=true", "autojobsrepid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepid is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "autojobsrepid.greaterThanOrEqual=" + DEFAULT_AUTOJOBSREPID,
            "autojobsrepid.greaterThanOrEqual=" + UPDATED_AUTOJOBSREPID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepid is less than or equal to
        defaultAutojobsinvoiceFiltering(
            "autojobsrepid.lessThanOrEqual=" + DEFAULT_AUTOJOBSREPID,
            "autojobsrepid.lessThanOrEqual=" + SMALLER_AUTOJOBSREPID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepid is less than
        defaultAutojobsinvoiceFiltering(
            "autojobsrepid.lessThan=" + UPDATED_AUTOJOBSREPID,
            "autojobsrepid.lessThan=" + DEFAULT_AUTOJOBSREPID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepid is greater than
        defaultAutojobsinvoiceFiltering(
            "autojobsrepid.greaterThan=" + SMALLER_AUTOJOBSREPID,
            "autojobsrepid.greaterThan=" + DEFAULT_AUTOJOBSREPID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepnameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepname equals to
        defaultAutojobsinvoiceFiltering(
            "autojobsrepname.equals=" + DEFAULT_AUTOJOBSREPNAME,
            "autojobsrepname.equals=" + UPDATED_AUTOJOBSREPNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepnameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepname in
        defaultAutojobsinvoiceFiltering(
            "autojobsrepname.in=" + DEFAULT_AUTOJOBSREPNAME + "," + UPDATED_AUTOJOBSREPNAME,
            "autojobsrepname.in=" + UPDATED_AUTOJOBSREPNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepname is not null
        defaultAutojobsinvoiceFiltering("autojobsrepname.specified=true", "autojobsrepname.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepnameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepname contains
        defaultAutojobsinvoiceFiltering(
            "autojobsrepname.contains=" + DEFAULT_AUTOJOBSREPNAME,
            "autojobsrepname.contains=" + UPDATED_AUTOJOBSREPNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutojobsrepnameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autojobsrepname does not contain
        defaultAutojobsinvoiceFiltering(
            "autojobsrepname.doesNotContain=" + UPDATED_AUTOJOBSREPNAME,
            "autojobsrepname.doesNotContain=" + DEFAULT_AUTOJOBSREPNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDelieverfromIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where delieverfrom equals to
        defaultAutojobsinvoiceFiltering("delieverfrom.equals=" + DEFAULT_DELIEVERFROM, "delieverfrom.equals=" + UPDATED_DELIEVERFROM);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDelieverfromIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where delieverfrom in
        defaultAutojobsinvoiceFiltering(
            "delieverfrom.in=" + DEFAULT_DELIEVERFROM + "," + UPDATED_DELIEVERFROM,
            "delieverfrom.in=" + UPDATED_DELIEVERFROM
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDelieverfromIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where delieverfrom is not null
        defaultAutojobsinvoiceFiltering("delieverfrom.specified=true", "delieverfrom.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDelieverfromContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where delieverfrom contains
        defaultAutojobsinvoiceFiltering("delieverfrom.contains=" + DEFAULT_DELIEVERFROM, "delieverfrom.contains=" + UPDATED_DELIEVERFROM);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDelieverfromNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where delieverfrom does not contain
        defaultAutojobsinvoiceFiltering(
            "delieverfrom.doesNotContain=" + UPDATED_DELIEVERFROM,
            "delieverfrom.doesNotContain=" + DEFAULT_DELIEVERFROM
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeridIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customerid equals to
        defaultAutojobsinvoiceFiltering("customerid.equals=" + DEFAULT_CUSTOMERID, "customerid.equals=" + UPDATED_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeridIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customerid in
        defaultAutojobsinvoiceFiltering(
            "customerid.in=" + DEFAULT_CUSTOMERID + "," + UPDATED_CUSTOMERID,
            "customerid.in=" + UPDATED_CUSTOMERID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeridIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customerid is not null
        defaultAutojobsinvoiceFiltering("customerid.specified=true", "customerid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeridIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customerid is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "customerid.greaterThanOrEqual=" + DEFAULT_CUSTOMERID,
            "customerid.greaterThanOrEqual=" + UPDATED_CUSTOMERID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeridIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customerid is less than or equal to
        defaultAutojobsinvoiceFiltering(
            "customerid.lessThanOrEqual=" + DEFAULT_CUSTOMERID,
            "customerid.lessThanOrEqual=" + SMALLER_CUSTOMERID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeridIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customerid is less than
        defaultAutojobsinvoiceFiltering("customerid.lessThan=" + UPDATED_CUSTOMERID, "customerid.lessThan=" + DEFAULT_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeridIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customerid is greater than
        defaultAutojobsinvoiceFiltering("customerid.greaterThan=" + SMALLER_CUSTOMERID, "customerid.greaterThan=" + DEFAULT_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomernameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customername equals to
        defaultAutojobsinvoiceFiltering("customername.equals=" + DEFAULT_CUSTOMERNAME, "customername.equals=" + UPDATED_CUSTOMERNAME);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomernameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customername in
        defaultAutojobsinvoiceFiltering(
            "customername.in=" + DEFAULT_CUSTOMERNAME + "," + UPDATED_CUSTOMERNAME,
            "customername.in=" + UPDATED_CUSTOMERNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customername is not null
        defaultAutojobsinvoiceFiltering("customername.specified=true", "customername.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomernameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customername contains
        defaultAutojobsinvoiceFiltering("customername.contains=" + DEFAULT_CUSTOMERNAME, "customername.contains=" + UPDATED_CUSTOMERNAME);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomernameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customername does not contain
        defaultAutojobsinvoiceFiltering(
            "customername.doesNotContain=" + UPDATED_CUSTOMERNAME,
            "customername.doesNotContain=" + DEFAULT_CUSTOMERNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeraddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customeraddress equals to
        defaultAutojobsinvoiceFiltering(
            "customeraddress.equals=" + DEFAULT_CUSTOMERADDRESS,
            "customeraddress.equals=" + UPDATED_CUSTOMERADDRESS
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeraddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customeraddress in
        defaultAutojobsinvoiceFiltering(
            "customeraddress.in=" + DEFAULT_CUSTOMERADDRESS + "," + UPDATED_CUSTOMERADDRESS,
            "customeraddress.in=" + UPDATED_CUSTOMERADDRESS
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeraddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customeraddress is not null
        defaultAutojobsinvoiceFiltering("customeraddress.specified=true", "customeraddress.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeraddressContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customeraddress contains
        defaultAutojobsinvoiceFiltering(
            "customeraddress.contains=" + DEFAULT_CUSTOMERADDRESS,
            "customeraddress.contains=" + UPDATED_CUSTOMERADDRESS
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCustomeraddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where customeraddress does not contain
        defaultAutojobsinvoiceFiltering(
            "customeraddress.doesNotContain=" + UPDATED_CUSTOMERADDRESS,
            "customeraddress.doesNotContain=" + DEFAULT_CUSTOMERADDRESS
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDeliveryaddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where deliveryaddress equals to
        defaultAutojobsinvoiceFiltering(
            "deliveryaddress.equals=" + DEFAULT_DELIVERYADDRESS,
            "deliveryaddress.equals=" + UPDATED_DELIVERYADDRESS
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDeliveryaddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where deliveryaddress in
        defaultAutojobsinvoiceFiltering(
            "deliveryaddress.in=" + DEFAULT_DELIVERYADDRESS + "," + UPDATED_DELIVERYADDRESS,
            "deliveryaddress.in=" + UPDATED_DELIVERYADDRESS
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDeliveryaddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where deliveryaddress is not null
        defaultAutojobsinvoiceFiltering("deliveryaddress.specified=true", "deliveryaddress.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDeliveryaddressContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where deliveryaddress contains
        defaultAutojobsinvoiceFiltering(
            "deliveryaddress.contains=" + DEFAULT_DELIVERYADDRESS,
            "deliveryaddress.contains=" + UPDATED_DELIVERYADDRESS
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByDeliveryaddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where deliveryaddress does not contain
        defaultAutojobsinvoiceFiltering(
            "deliveryaddress.doesNotContain=" + UPDATED_DELIVERYADDRESS,
            "deliveryaddress.doesNotContain=" + DEFAULT_DELIVERYADDRESS
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesBySubtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where subtotal equals to
        defaultAutojobsinvoiceFiltering("subtotal.equals=" + DEFAULT_SUBTOTAL, "subtotal.equals=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesBySubtotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where subtotal in
        defaultAutojobsinvoiceFiltering("subtotal.in=" + DEFAULT_SUBTOTAL + "," + UPDATED_SUBTOTAL, "subtotal.in=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesBySubtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where subtotal is not null
        defaultAutojobsinvoiceFiltering("subtotal.specified=true", "subtotal.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesBySubtotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where subtotal is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "subtotal.greaterThanOrEqual=" + DEFAULT_SUBTOTAL,
            "subtotal.greaterThanOrEqual=" + UPDATED_SUBTOTAL
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesBySubtotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where subtotal is less than or equal to
        defaultAutojobsinvoiceFiltering("subtotal.lessThanOrEqual=" + DEFAULT_SUBTOTAL, "subtotal.lessThanOrEqual=" + SMALLER_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesBySubtotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where subtotal is less than
        defaultAutojobsinvoiceFiltering("subtotal.lessThan=" + UPDATED_SUBTOTAL, "subtotal.lessThan=" + DEFAULT_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesBySubtotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where subtotal is greater than
        defaultAutojobsinvoiceFiltering("subtotal.greaterThan=" + SMALLER_SUBTOTAL, "subtotal.greaterThan=" + DEFAULT_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaltaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaltax equals to
        defaultAutojobsinvoiceFiltering("totaltax.equals=" + DEFAULT_TOTALTAX, "totaltax.equals=" + UPDATED_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaltaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaltax in
        defaultAutojobsinvoiceFiltering("totaltax.in=" + DEFAULT_TOTALTAX + "," + UPDATED_TOTALTAX, "totaltax.in=" + UPDATED_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaltaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaltax is not null
        defaultAutojobsinvoiceFiltering("totaltax.specified=true", "totaltax.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaltaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaltax is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "totaltax.greaterThanOrEqual=" + DEFAULT_TOTALTAX,
            "totaltax.greaterThanOrEqual=" + UPDATED_TOTALTAX
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaltaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaltax is less than or equal to
        defaultAutojobsinvoiceFiltering("totaltax.lessThanOrEqual=" + DEFAULT_TOTALTAX, "totaltax.lessThanOrEqual=" + SMALLER_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaltaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaltax is less than
        defaultAutojobsinvoiceFiltering("totaltax.lessThan=" + UPDATED_TOTALTAX, "totaltax.lessThan=" + DEFAULT_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaltaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaltax is greater than
        defaultAutojobsinvoiceFiltering("totaltax.greaterThan=" + SMALLER_TOTALTAX, "totaltax.greaterThan=" + DEFAULT_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaldiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaldiscount equals to
        defaultAutojobsinvoiceFiltering("totaldiscount.equals=" + DEFAULT_TOTALDISCOUNT, "totaldiscount.equals=" + UPDATED_TOTALDISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaldiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaldiscount in
        defaultAutojobsinvoiceFiltering(
            "totaldiscount.in=" + DEFAULT_TOTALDISCOUNT + "," + UPDATED_TOTALDISCOUNT,
            "totaldiscount.in=" + UPDATED_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaldiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaldiscount is not null
        defaultAutojobsinvoiceFiltering("totaldiscount.specified=true", "totaldiscount.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaldiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaldiscount is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "totaldiscount.greaterThanOrEqual=" + DEFAULT_TOTALDISCOUNT,
            "totaldiscount.greaterThanOrEqual=" + UPDATED_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaldiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaldiscount is less than or equal to
        defaultAutojobsinvoiceFiltering(
            "totaldiscount.lessThanOrEqual=" + DEFAULT_TOTALDISCOUNT,
            "totaldiscount.lessThanOrEqual=" + SMALLER_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaldiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaldiscount is less than
        defaultAutojobsinvoiceFiltering(
            "totaldiscount.lessThan=" + UPDATED_TOTALDISCOUNT,
            "totaldiscount.lessThan=" + DEFAULT_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByTotaldiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where totaldiscount is greater than
        defaultAutojobsinvoiceFiltering(
            "totaldiscount.greaterThan=" + SMALLER_TOTALDISCOUNT,
            "totaldiscount.greaterThan=" + DEFAULT_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByNettotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where nettotal equals to
        defaultAutojobsinvoiceFiltering("nettotal.equals=" + DEFAULT_NETTOTAL, "nettotal.equals=" + UPDATED_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByNettotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where nettotal in
        defaultAutojobsinvoiceFiltering("nettotal.in=" + DEFAULT_NETTOTAL + "," + UPDATED_NETTOTAL, "nettotal.in=" + UPDATED_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByNettotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where nettotal is not null
        defaultAutojobsinvoiceFiltering("nettotal.specified=true", "nettotal.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByNettotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where nettotal is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "nettotal.greaterThanOrEqual=" + DEFAULT_NETTOTAL,
            "nettotal.greaterThanOrEqual=" + UPDATED_NETTOTAL
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByNettotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where nettotal is less than or equal to
        defaultAutojobsinvoiceFiltering("nettotal.lessThanOrEqual=" + DEFAULT_NETTOTAL, "nettotal.lessThanOrEqual=" + SMALLER_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByNettotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where nettotal is less than
        defaultAutojobsinvoiceFiltering("nettotal.lessThan=" + UPDATED_NETTOTAL, "nettotal.lessThan=" + DEFAULT_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByNettotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where nettotal is greater than
        defaultAutojobsinvoiceFiltering("nettotal.greaterThan=" + SMALLER_NETTOTAL, "nettotal.greaterThan=" + DEFAULT_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where message equals to
        defaultAutojobsinvoiceFiltering("message.equals=" + DEFAULT_MESSAGE, "message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where message in
        defaultAutojobsinvoiceFiltering("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE, "message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where message is not null
        defaultAutojobsinvoiceFiltering("message.specified=true", "message.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByMessageContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where message contains
        defaultAutojobsinvoiceFiltering("message.contains=" + DEFAULT_MESSAGE, "message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where message does not contain
        defaultAutojobsinvoiceFiltering("message.doesNotContain=" + UPDATED_MESSAGE, "message.doesNotContain=" + DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmu equals to
        defaultAutojobsinvoiceFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmu in
        defaultAutojobsinvoiceFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmu is not null
        defaultAutojobsinvoiceFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmu is greater than or equal to
        defaultAutojobsinvoiceFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmu is less than or equal to
        defaultAutojobsinvoiceFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmu is less than
        defaultAutojobsinvoiceFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmu is greater than
        defaultAutojobsinvoiceFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmd equals to
        defaultAutojobsinvoiceFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmd in
        defaultAutojobsinvoiceFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where lmd is not null
        defaultAutojobsinvoiceFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByPaidamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where paidamount equals to
        defaultAutojobsinvoiceFiltering("paidamount.equals=" + DEFAULT_PAIDAMOUNT, "paidamount.equals=" + UPDATED_PAIDAMOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByPaidamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where paidamount in
        defaultAutojobsinvoiceFiltering(
            "paidamount.in=" + DEFAULT_PAIDAMOUNT + "," + UPDATED_PAIDAMOUNT,
            "paidamount.in=" + UPDATED_PAIDAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByPaidamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where paidamount is not null
        defaultAutojobsinvoiceFiltering("paidamount.specified=true", "paidamount.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByPaidamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where paidamount is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "paidamount.greaterThanOrEqual=" + DEFAULT_PAIDAMOUNT,
            "paidamount.greaterThanOrEqual=" + UPDATED_PAIDAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByPaidamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where paidamount is less than or equal to
        defaultAutojobsinvoiceFiltering(
            "paidamount.lessThanOrEqual=" + DEFAULT_PAIDAMOUNT,
            "paidamount.lessThanOrEqual=" + SMALLER_PAIDAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByPaidamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where paidamount is less than
        defaultAutojobsinvoiceFiltering("paidamount.lessThan=" + UPDATED_PAIDAMOUNT, "paidamount.lessThan=" + DEFAULT_PAIDAMOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByPaidamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where paidamount is greater than
        defaultAutojobsinvoiceFiltering("paidamount.greaterThan=" + SMALLER_PAIDAMOUNT, "paidamount.greaterThan=" + DEFAULT_PAIDAMOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAmountowingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where amountowing equals to
        defaultAutojobsinvoiceFiltering("amountowing.equals=" + DEFAULT_AMOUNTOWING, "amountowing.equals=" + UPDATED_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAmountowingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where amountowing in
        defaultAutojobsinvoiceFiltering(
            "amountowing.in=" + DEFAULT_AMOUNTOWING + "," + UPDATED_AMOUNTOWING,
            "amountowing.in=" + UPDATED_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAmountowingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where amountowing is not null
        defaultAutojobsinvoiceFiltering("amountowing.specified=true", "amountowing.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAmountowingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where amountowing is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "amountowing.greaterThanOrEqual=" + DEFAULT_AMOUNTOWING,
            "amountowing.greaterThanOrEqual=" + UPDATED_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAmountowingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where amountowing is less than or equal to
        defaultAutojobsinvoiceFiltering(
            "amountowing.lessThanOrEqual=" + DEFAULT_AMOUNTOWING,
            "amountowing.lessThanOrEqual=" + SMALLER_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAmountowingIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where amountowing is less than
        defaultAutojobsinvoiceFiltering("amountowing.lessThan=" + UPDATED_AMOUNTOWING, "amountowing.lessThan=" + DEFAULT_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAmountowingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where amountowing is greater than
        defaultAutojobsinvoiceFiltering("amountowing.greaterThan=" + SMALLER_AMOUNTOWING, "amountowing.greaterThan=" + DEFAULT_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where isactive equals to
        defaultAutojobsinvoiceFiltering("isactive.equals=" + DEFAULT_ISACTIVE, "isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where isactive in
        defaultAutojobsinvoiceFiltering("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE, "isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where isactive is not null
        defaultAutojobsinvoiceFiltering("isactive.specified=true", "isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationid equals to
        defaultAutojobsinvoiceFiltering("locationid.equals=" + DEFAULT_LOCATIONID, "locationid.equals=" + UPDATED_LOCATIONID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationid in
        defaultAutojobsinvoiceFiltering(
            "locationid.in=" + DEFAULT_LOCATIONID + "," + UPDATED_LOCATIONID,
            "locationid.in=" + UPDATED_LOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationid is not null
        defaultAutojobsinvoiceFiltering("locationid.specified=true", "locationid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationid is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "locationid.greaterThanOrEqual=" + DEFAULT_LOCATIONID,
            "locationid.greaterThanOrEqual=" + UPDATED_LOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationid is less than or equal to
        defaultAutojobsinvoiceFiltering(
            "locationid.lessThanOrEqual=" + DEFAULT_LOCATIONID,
            "locationid.lessThanOrEqual=" + SMALLER_LOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationid is less than
        defaultAutojobsinvoiceFiltering("locationid.lessThan=" + UPDATED_LOCATIONID, "locationid.lessThan=" + DEFAULT_LOCATIONID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationid is greater than
        defaultAutojobsinvoiceFiltering("locationid.greaterThan=" + SMALLER_LOCATIONID, "locationid.greaterThan=" + DEFAULT_LOCATIONID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationcode equals to
        defaultAutojobsinvoiceFiltering("locationcode.equals=" + DEFAULT_LOCATIONCODE, "locationcode.equals=" + UPDATED_LOCATIONCODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationcodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationcode in
        defaultAutojobsinvoiceFiltering(
            "locationcode.in=" + DEFAULT_LOCATIONCODE + "," + UPDATED_LOCATIONCODE,
            "locationcode.in=" + UPDATED_LOCATIONCODE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationcode is not null
        defaultAutojobsinvoiceFiltering("locationcode.specified=true", "locationcode.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationcodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationcode contains
        defaultAutojobsinvoiceFiltering("locationcode.contains=" + DEFAULT_LOCATIONCODE, "locationcode.contains=" + UPDATED_LOCATIONCODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByLocationcodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where locationcode does not contain
        defaultAutojobsinvoiceFiltering(
            "locationcode.doesNotContain=" + UPDATED_LOCATIONCODE,
            "locationcode.doesNotContain=" + DEFAULT_LOCATIONCODE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByReferencecodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where referencecode equals to
        defaultAutojobsinvoiceFiltering("referencecode.equals=" + DEFAULT_REFERENCECODE, "referencecode.equals=" + UPDATED_REFERENCECODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByReferencecodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where referencecode in
        defaultAutojobsinvoiceFiltering(
            "referencecode.in=" + DEFAULT_REFERENCECODE + "," + UPDATED_REFERENCECODE,
            "referencecode.in=" + UPDATED_REFERENCECODE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByReferencecodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where referencecode is not null
        defaultAutojobsinvoiceFiltering("referencecode.specified=true", "referencecode.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByReferencecodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where referencecode contains
        defaultAutojobsinvoiceFiltering(
            "referencecode.contains=" + DEFAULT_REFERENCECODE,
            "referencecode.contains=" + UPDATED_REFERENCECODE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByReferencecodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where referencecode does not contain
        defaultAutojobsinvoiceFiltering(
            "referencecode.doesNotContain=" + UPDATED_REFERENCECODE,
            "referencecode.doesNotContain=" + DEFAULT_REFERENCECODE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbyidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyid equals to
        defaultAutojobsinvoiceFiltering("createdbyid.equals=" + DEFAULT_CREATEDBYID, "createdbyid.equals=" + UPDATED_CREATEDBYID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbyidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyid in
        defaultAutojobsinvoiceFiltering(
            "createdbyid.in=" + DEFAULT_CREATEDBYID + "," + UPDATED_CREATEDBYID,
            "createdbyid.in=" + UPDATED_CREATEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyid is not null
        defaultAutojobsinvoiceFiltering("createdbyid.specified=true", "createdbyid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyid is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "createdbyid.greaterThanOrEqual=" + DEFAULT_CREATEDBYID,
            "createdbyid.greaterThanOrEqual=" + UPDATED_CREATEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyid is less than or equal to
        defaultAutojobsinvoiceFiltering(
            "createdbyid.lessThanOrEqual=" + DEFAULT_CREATEDBYID,
            "createdbyid.lessThanOrEqual=" + SMALLER_CREATEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbyidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyid is less than
        defaultAutojobsinvoiceFiltering("createdbyid.lessThan=" + UPDATED_CREATEDBYID, "createdbyid.lessThan=" + DEFAULT_CREATEDBYID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyid is greater than
        defaultAutojobsinvoiceFiltering("createdbyid.greaterThan=" + SMALLER_CREATEDBYID, "createdbyid.greaterThan=" + DEFAULT_CREATEDBYID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbynameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyname equals to
        defaultAutojobsinvoiceFiltering("createdbyname.equals=" + DEFAULT_CREATEDBYNAME, "createdbyname.equals=" + UPDATED_CREATEDBYNAME);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbynameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyname in
        defaultAutojobsinvoiceFiltering(
            "createdbyname.in=" + DEFAULT_CREATEDBYNAME + "," + UPDATED_CREATEDBYNAME,
            "createdbyname.in=" + UPDATED_CREATEDBYNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbynameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyname is not null
        defaultAutojobsinvoiceFiltering("createdbyname.specified=true", "createdbyname.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbynameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyname contains
        defaultAutojobsinvoiceFiltering(
            "createdbyname.contains=" + DEFAULT_CREATEDBYNAME,
            "createdbyname.contains=" + UPDATED_CREATEDBYNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByCreatedbynameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where createdbyname does not contain
        defaultAutojobsinvoiceFiltering(
            "createdbyname.doesNotContain=" + UPDATED_CREATEDBYNAME,
            "createdbyname.doesNotContain=" + DEFAULT_CREATEDBYNAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutocarecompanyidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autocarecompanyid equals to
        defaultAutojobsinvoiceFiltering(
            "autocarecompanyid.equals=" + DEFAULT_AUTOCARECOMPANYID,
            "autocarecompanyid.equals=" + UPDATED_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutocarecompanyidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autocarecompanyid in
        defaultAutojobsinvoiceFiltering(
            "autocarecompanyid.in=" + DEFAULT_AUTOCARECOMPANYID + "," + UPDATED_AUTOCARECOMPANYID,
            "autocarecompanyid.in=" + UPDATED_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutocarecompanyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autocarecompanyid is not null
        defaultAutojobsinvoiceFiltering("autocarecompanyid.specified=true", "autocarecompanyid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutocarecompanyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autocarecompanyid is greater than or equal to
        defaultAutojobsinvoiceFiltering(
            "autocarecompanyid.greaterThanOrEqual=" + DEFAULT_AUTOCARECOMPANYID,
            "autocarecompanyid.greaterThanOrEqual=" + UPDATED_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutocarecompanyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autocarecompanyid is less than or equal to
        defaultAutojobsinvoiceFiltering(
            "autocarecompanyid.lessThanOrEqual=" + DEFAULT_AUTOCARECOMPANYID,
            "autocarecompanyid.lessThanOrEqual=" + SMALLER_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutocarecompanyidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autocarecompanyid is less than
        defaultAutojobsinvoiceFiltering(
            "autocarecompanyid.lessThan=" + UPDATED_AUTOCARECOMPANYID,
            "autocarecompanyid.lessThan=" + DEFAULT_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicesByAutocarecompanyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        // Get all the autojobsinvoiceList where autocarecompanyid is greater than
        defaultAutojobsinvoiceFiltering(
            "autocarecompanyid.greaterThan=" + SMALLER_AUTOCARECOMPANYID,
            "autocarecompanyid.greaterThan=" + DEFAULT_AUTOCARECOMPANYID
        );
    }

    private void defaultAutojobsinvoiceFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAutojobsinvoiceShouldBeFound(shouldBeFound);
        defaultAutojobsinvoiceShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutojobsinvoiceShouldBeFound(String filter) throws Exception {
        restAutojobsinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsinvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].invoicedate").value(hasItem(DEFAULT_INVOICEDATE.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].jobid").value(hasItem(DEFAULT_JOBID)))
            .andExpect(jsonPath("$.[*].quoteid").value(hasItem(DEFAULT_QUOTEID)))
            .andExpect(jsonPath("$.[*].orderid").value(hasItem(DEFAULT_ORDERID)))
            .andExpect(jsonPath("$.[*].delieverydate").value(hasItem(DEFAULT_DELIEVERYDATE.toString())))
            .andExpect(jsonPath("$.[*].autojobsrepid").value(hasItem(DEFAULT_AUTOJOBSREPID)))
            .andExpect(jsonPath("$.[*].autojobsrepname").value(hasItem(DEFAULT_AUTOJOBSREPNAME)))
            .andExpect(jsonPath("$.[*].delieverfrom").value(hasItem(DEFAULT_DELIEVERFROM)))
            .andExpect(jsonPath("$.[*].customerid").value(hasItem(DEFAULT_CUSTOMERID)))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME)))
            .andExpect(jsonPath("$.[*].customeraddress").value(hasItem(DEFAULT_CUSTOMERADDRESS)))
            .andExpect(jsonPath("$.[*].deliveryaddress").value(hasItem(DEFAULT_DELIVERYADDRESS)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].totaltax").value(hasItem(DEFAULT_TOTALTAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totaldiscount").value(hasItem(DEFAULT_TOTALDISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].nettotal").value(hasItem(DEFAULT_NETTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].paidamount").value(hasItem(DEFAULT_PAIDAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountowing").value(hasItem(DEFAULT_AMOUNTOWING.doubleValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].locationid").value(hasItem(DEFAULT_LOCATIONID)))
            .andExpect(jsonPath("$.[*].locationcode").value(hasItem(DEFAULT_LOCATIONCODE)))
            .andExpect(jsonPath("$.[*].referencecode").value(hasItem(DEFAULT_REFERENCECODE)))
            .andExpect(jsonPath("$.[*].createdbyid").value(hasItem(DEFAULT_CREATEDBYID)))
            .andExpect(jsonPath("$.[*].createdbyname").value(hasItem(DEFAULT_CREATEDBYNAME)))
            .andExpect(jsonPath("$.[*].autocarecompanyid").value(hasItem(DEFAULT_AUTOCARECOMPANYID)));

        // Check, that the count call also returns 1
        restAutojobsinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutojobsinvoiceShouldNotBeFound(String filter) throws Exception {
        restAutojobsinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutojobsinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutojobsinvoice() throws Exception {
        // Get the autojobsinvoice
        restAutojobsinvoiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutojobsinvoice() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoice
        Autojobsinvoice updatedAutojobsinvoice = autojobsinvoiceRepository.findById(autojobsinvoice.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAutojobsinvoice are not directly saved in db
        em.detach(updatedAutojobsinvoice);
        updatedAutojobsinvoice
            .code(UPDATED_CODE)
            .invoicedate(UPDATED_INVOICEDATE)
            .createddate(UPDATED_CREATEDDATE)
            .jobid(UPDATED_JOBID)
            .quoteid(UPDATED_QUOTEID)
            .orderid(UPDATED_ORDERID)
            .delieverydate(UPDATED_DELIEVERYDATE)
            .autojobsrepid(UPDATED_AUTOJOBSREPID)
            .autojobsrepname(UPDATED_AUTOJOBSREPNAME)
            .delieverfrom(UPDATED_DELIEVERFROM)
            .customerid(UPDATED_CUSTOMERID)
            .customername(UPDATED_CUSTOMERNAME)
            .customeraddress(UPDATED_CUSTOMERADDRESS)
            .deliveryaddress(UPDATED_DELIVERYADDRESS)
            .subtotal(UPDATED_SUBTOTAL)
            .totaltax(UPDATED_TOTALTAX)
            .totaldiscount(UPDATED_TOTALDISCOUNT)
            .nettotal(UPDATED_NETTOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidamount(UPDATED_PAIDAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .isactive(UPDATED_ISACTIVE)
            .locationid(UPDATED_LOCATIONID)
            .locationcode(UPDATED_LOCATIONCODE)
            .referencecode(UPDATED_REFERENCECODE)
            .createdbyid(UPDATED_CREATEDBYID)
            .createdbyname(UPDATED_CREATEDBYNAME)
            .autocarecompanyid(UPDATED_AUTOCARECOMPANYID);

        restAutojobsinvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutojobsinvoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutojobsinvoice))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutojobsinvoiceToMatchAllProperties(updatedAutojobsinvoice);
    }

    @Test
    @Transactional
    void putNonExistingAutojobsinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoice.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsinvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autojobsinvoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsinvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutojobsinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoice.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsinvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutojobsinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoice.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutojobsinvoiceWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoice using partial update
        Autojobsinvoice partialUpdatedAutojobsinvoice = new Autojobsinvoice();
        partialUpdatedAutojobsinvoice.setId(autojobsinvoice.getId());

        partialUpdatedAutojobsinvoice
            .invoicedate(UPDATED_INVOICEDATE)
            .createddate(UPDATED_CREATEDDATE)
            .jobid(UPDATED_JOBID)
            .delieverfrom(UPDATED_DELIEVERFROM)
            .deliveryaddress(UPDATED_DELIVERYADDRESS)
            .totaltax(UPDATED_TOTALTAX)
            .nettotal(UPDATED_NETTOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .locationcode(UPDATED_LOCATIONCODE)
            .createdbyid(UPDATED_CREATEDBYID)
            .autocarecompanyid(UPDATED_AUTOCARECOMPANYID);

        restAutojobsinvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsinvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsinvoice))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsinvoiceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutojobsinvoice, autojobsinvoice),
            getPersistedAutojobsinvoice(autojobsinvoice)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutojobsinvoiceWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoice using partial update
        Autojobsinvoice partialUpdatedAutojobsinvoice = new Autojobsinvoice();
        partialUpdatedAutojobsinvoice.setId(autojobsinvoice.getId());

        partialUpdatedAutojobsinvoice
            .code(UPDATED_CODE)
            .invoicedate(UPDATED_INVOICEDATE)
            .createddate(UPDATED_CREATEDDATE)
            .jobid(UPDATED_JOBID)
            .quoteid(UPDATED_QUOTEID)
            .orderid(UPDATED_ORDERID)
            .delieverydate(UPDATED_DELIEVERYDATE)
            .autojobsrepid(UPDATED_AUTOJOBSREPID)
            .autojobsrepname(UPDATED_AUTOJOBSREPNAME)
            .delieverfrom(UPDATED_DELIEVERFROM)
            .customerid(UPDATED_CUSTOMERID)
            .customername(UPDATED_CUSTOMERNAME)
            .customeraddress(UPDATED_CUSTOMERADDRESS)
            .deliveryaddress(UPDATED_DELIVERYADDRESS)
            .subtotal(UPDATED_SUBTOTAL)
            .totaltax(UPDATED_TOTALTAX)
            .totaldiscount(UPDATED_TOTALDISCOUNT)
            .nettotal(UPDATED_NETTOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidamount(UPDATED_PAIDAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .isactive(UPDATED_ISACTIVE)
            .locationid(UPDATED_LOCATIONID)
            .locationcode(UPDATED_LOCATIONCODE)
            .referencecode(UPDATED_REFERENCECODE)
            .createdbyid(UPDATED_CREATEDBYID)
            .createdbyname(UPDATED_CREATEDBYNAME)
            .autocarecompanyid(UPDATED_AUTOCARECOMPANYID);

        restAutojobsinvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsinvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsinvoice))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsinvoiceUpdatableFieldsEquals(
            partialUpdatedAutojobsinvoice,
            getPersistedAutojobsinvoice(partialUpdatedAutojobsinvoice)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutojobsinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoice.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsinvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autojobsinvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsinvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutojobsinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoice.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsinvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutojobsinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoice.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoiceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(autojobsinvoice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutojobsinvoice() throws Exception {
        // Initialize the database
        insertedAutojobsinvoice = autojobsinvoiceRepository.saveAndFlush(autojobsinvoice);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autojobsinvoice
        restAutojobsinvoiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, autojobsinvoice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autojobsinvoiceRepository.count();
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

    protected Autojobsinvoice getPersistedAutojobsinvoice(Autojobsinvoice autojobsinvoice) {
        return autojobsinvoiceRepository.findById(autojobsinvoice.getId()).orElseThrow();
    }

    protected void assertPersistedAutojobsinvoiceToMatchAllProperties(Autojobsinvoice expectedAutojobsinvoice) {
        assertAutojobsinvoiceAllPropertiesEquals(expectedAutojobsinvoice, getPersistedAutojobsinvoice(expectedAutojobsinvoice));
    }

    protected void assertPersistedAutojobsinvoiceToMatchUpdatableProperties(Autojobsinvoice expectedAutojobsinvoice) {
        assertAutojobsinvoiceAllUpdatablePropertiesEquals(expectedAutojobsinvoice, getPersistedAutojobsinvoice(expectedAutojobsinvoice));
    }
}
