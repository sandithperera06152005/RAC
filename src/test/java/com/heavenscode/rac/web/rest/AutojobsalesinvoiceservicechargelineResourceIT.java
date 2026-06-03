package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutojobsalesinvoiceservicechargelineAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline;
import com.heavenscode.rac.repository.AutojobsalesinvoiceservicechargelineRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link AutojobsalesinvoiceservicechargelineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutojobsalesinvoiceservicechargelineResourceIT {

    private static final Integer DEFAULT_INVOICEID = 1;
    private static final Integer UPDATED_INVOICEID = 2;
    private static final Integer SMALLER_INVOICEID = 1 - 1;

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;
    private static final Integer SMALLER_LINEID = 1 - 1;

    private static final Integer DEFAULT_OPTIONID = 1;
    private static final Integer UPDATED_OPTIONID = 2;
    private static final Integer SMALLER_OPTIONID = 1 - 1;

    private static final String DEFAULT_SERVICENAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICEDISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICEDISCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;
    private static final Float SMALLER_VALUE = 1F - 1F;

    private static final Integer DEFAULT_ADDEDBYID = 1;
    private static final Integer UPDATED_ADDEDBYID = 2;
    private static final Integer SMALLER_ADDEDBYID = 1 - 1;

    private static final Boolean DEFAULT_ISCUSTOMERSRVICE = false;
    private static final Boolean UPDATED_ISCUSTOMERSRVICE = true;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;
    private static final Float SMALLER_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_SERVICEPRICE = 1F;
    private static final Float UPDATED_SERVICEPRICE = 2F;
    private static final Float SMALLER_SERVICEPRICE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/autojobsalesinvoiceservicechargelines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutojobsalesinvoiceservicechargelineRepository autojobsalesinvoiceservicechargelineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutojobsalesinvoiceservicechargelineMockMvc;

    private Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline;

    private Autojobsalesinvoiceservicechargeline insertedAutojobsalesinvoiceservicechargeline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsalesinvoiceservicechargeline createEntity() {
        return new Autojobsalesinvoiceservicechargeline()
            .invoiceid(DEFAULT_INVOICEID)
            .lineid(DEFAULT_LINEID)
            .optionid(DEFAULT_OPTIONID)
            .servicename(DEFAULT_SERVICENAME)
            .servicediscription(DEFAULT_SERVICEDISCRIPTION)
            .value(DEFAULT_VALUE)
            .addedbyid(DEFAULT_ADDEDBYID)
            .iscustomersrvice(DEFAULT_ISCUSTOMERSRVICE)
            .discount(DEFAULT_DISCOUNT)
            .serviceprice(DEFAULT_SERVICEPRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsalesinvoiceservicechargeline createUpdatedEntity() {
        return new Autojobsalesinvoiceservicechargeline()
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .iscustomersrvice(UPDATED_ISCUSTOMERSRVICE)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);
    }

    @BeforeEach
    public void initTest() {
        autojobsalesinvoiceservicechargeline = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutojobsalesinvoiceservicechargeline != null) {
            autojobsalesinvoiceservicechargelineRepository.delete(insertedAutojobsalesinvoiceservicechargeline);
            insertedAutojobsalesinvoiceservicechargeline = null;
        }
    }

    @Test
    @Transactional
    void createAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autojobsalesinvoiceservicechargeline
        var returnedAutojobsalesinvoiceservicechargeline = om.readValue(
            restAutojobsalesinvoiceservicechargelineMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autojobsalesinvoiceservicechargeline.class
        );

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutojobsalesinvoiceservicechargelineUpdatableFieldsEquals(
            returnedAutojobsalesinvoiceservicechargeline,
            getPersistedAutojobsalesinvoiceservicechargeline(returnedAutojobsalesinvoiceservicechargeline)
        );

        insertedAutojobsalesinvoiceservicechargeline = returnedAutojobsalesinvoiceservicechargeline;
    }

    @Test
    @Transactional
    void createAutojobsalesinvoiceservicechargelineWithExistingId() throws Exception {
        // Create the Autojobsalesinvoiceservicechargeline with an existing ID
        autojobsalesinvoiceservicechargeline.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelines() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsalesinvoiceservicechargeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].servicename").value(hasItem(DEFAULT_SERVICENAME)))
            .andExpect(jsonPath("$.[*].servicediscription").value(hasItem(DEFAULT_SERVICEDISCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedbyid").value(hasItem(DEFAULT_ADDEDBYID)))
            .andExpect(jsonPath("$.[*].iscustomersrvice").value(hasItem(DEFAULT_ISCUSTOMERSRVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceprice").value(hasItem(DEFAULT_SERVICEPRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getAutojobsalesinvoiceservicechargeline() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get the autojobsalesinvoiceservicechargeline
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(get(ENTITY_API_URL_ID, autojobsalesinvoiceservicechargeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autojobsalesinvoiceservicechargeline.getId().intValue()))
            .andExpect(jsonPath("$.invoiceid").value(DEFAULT_INVOICEID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.optionid").value(DEFAULT_OPTIONID))
            .andExpect(jsonPath("$.servicename").value(DEFAULT_SERVICENAME))
            .andExpect(jsonPath("$.servicediscription").value(DEFAULT_SERVICEDISCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.addedbyid").value(DEFAULT_ADDEDBYID))
            .andExpect(jsonPath("$.iscustomersrvice").value(DEFAULT_ISCUSTOMERSRVICE.booleanValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.serviceprice").value(DEFAULT_SERVICEPRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getAutojobsalesinvoiceservicechargelinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        Long id = autojobsalesinvoiceservicechargeline.getId();

        defaultAutojobsalesinvoiceservicechargelineFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAutojobsalesinvoiceservicechargelineFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAutojobsalesinvoiceservicechargelineFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByInvoiceidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where invoiceid equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "invoiceid.equals=" + DEFAULT_INVOICEID,
            "invoiceid.equals=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByInvoiceidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where invoiceid in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "invoiceid.in=" + DEFAULT_INVOICEID + "," + UPDATED_INVOICEID,
            "invoiceid.in=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByInvoiceidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where invoiceid is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("invoiceid.specified=true", "invoiceid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByInvoiceidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where invoiceid is greater than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "invoiceid.greaterThanOrEqual=" + DEFAULT_INVOICEID,
            "invoiceid.greaterThanOrEqual=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByInvoiceidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where invoiceid is less than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "invoiceid.lessThanOrEqual=" + DEFAULT_INVOICEID,
            "invoiceid.lessThanOrEqual=" + SMALLER_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByInvoiceidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where invoiceid is less than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "invoiceid.lessThan=" + UPDATED_INVOICEID,
            "invoiceid.lessThan=" + DEFAULT_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByInvoiceidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where invoiceid is greater than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "invoiceid.greaterThan=" + SMALLER_INVOICEID,
            "invoiceid.greaterThan=" + DEFAULT_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByLineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where lineid equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering("lineid.equals=" + DEFAULT_LINEID, "lineid.equals=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByLineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where lineid in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "lineid.in=" + DEFAULT_LINEID + "," + UPDATED_LINEID,
            "lineid.in=" + UPDATED_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByLineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where lineid is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("lineid.specified=true", "lineid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByLineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where lineid is greater than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "lineid.greaterThanOrEqual=" + DEFAULT_LINEID,
            "lineid.greaterThanOrEqual=" + UPDATED_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByLineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where lineid is less than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "lineid.lessThanOrEqual=" + DEFAULT_LINEID,
            "lineid.lessThanOrEqual=" + SMALLER_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByLineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where lineid is less than
        defaultAutojobsalesinvoiceservicechargelineFiltering("lineid.lessThan=" + UPDATED_LINEID, "lineid.lessThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByLineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where lineid is greater than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "lineid.greaterThan=" + SMALLER_LINEID,
            "lineid.greaterThan=" + DEFAULT_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByOptionidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where optionid equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering("optionid.equals=" + DEFAULT_OPTIONID, "optionid.equals=" + UPDATED_OPTIONID);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByOptionidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where optionid in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "optionid.in=" + DEFAULT_OPTIONID + "," + UPDATED_OPTIONID,
            "optionid.in=" + UPDATED_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByOptionidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where optionid is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("optionid.specified=true", "optionid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByOptionidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where optionid is greater than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "optionid.greaterThanOrEqual=" + DEFAULT_OPTIONID,
            "optionid.greaterThanOrEqual=" + UPDATED_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByOptionidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where optionid is less than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "optionid.lessThanOrEqual=" + DEFAULT_OPTIONID,
            "optionid.lessThanOrEqual=" + SMALLER_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByOptionidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where optionid is less than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "optionid.lessThan=" + UPDATED_OPTIONID,
            "optionid.lessThan=" + DEFAULT_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByOptionidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where optionid is greater than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "optionid.greaterThan=" + SMALLER_OPTIONID,
            "optionid.greaterThan=" + DEFAULT_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicenameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicename equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "servicename.equals=" + DEFAULT_SERVICENAME,
            "servicename.equals=" + UPDATED_SERVICENAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicenameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicename in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "servicename.in=" + DEFAULT_SERVICENAME + "," + UPDATED_SERVICENAME,
            "servicename.in=" + UPDATED_SERVICENAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicename is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("servicename.specified=true", "servicename.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicenameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicename contains
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "servicename.contains=" + DEFAULT_SERVICENAME,
            "servicename.contains=" + UPDATED_SERVICENAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicenameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicename does not contain
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "servicename.doesNotContain=" + UPDATED_SERVICENAME,
            "servicename.doesNotContain=" + DEFAULT_SERVICENAME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicediscriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicediscription equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "servicediscription.equals=" + DEFAULT_SERVICEDISCRIPTION,
            "servicediscription.equals=" + UPDATED_SERVICEDISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicediscriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicediscription in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "servicediscription.in=" + DEFAULT_SERVICEDISCRIPTION + "," + UPDATED_SERVICEDISCRIPTION,
            "servicediscription.in=" + UPDATED_SERVICEDISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicediscriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicediscription is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("servicediscription.specified=true", "servicediscription.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicediscriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicediscription contains
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "servicediscription.contains=" + DEFAULT_SERVICEDISCRIPTION,
            "servicediscription.contains=" + UPDATED_SERVICEDISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicediscriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where servicediscription does not contain
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "servicediscription.doesNotContain=" + UPDATED_SERVICEDISCRIPTION,
            "servicediscription.doesNotContain=" + DEFAULT_SERVICEDISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where value equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where value in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE,
            "value.in=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where value is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where value is greater than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "value.greaterThanOrEqual=" + DEFAULT_VALUE,
            "value.greaterThanOrEqual=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where value is less than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "value.lessThanOrEqual=" + DEFAULT_VALUE,
            "value.lessThanOrEqual=" + SMALLER_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where value is less than
        defaultAutojobsalesinvoiceservicechargelineFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where value is greater than
        defaultAutojobsalesinvoiceservicechargelineFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByAddedbyidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where addedbyid equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "addedbyid.equals=" + DEFAULT_ADDEDBYID,
            "addedbyid.equals=" + UPDATED_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByAddedbyidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where addedbyid in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "addedbyid.in=" + DEFAULT_ADDEDBYID + "," + UPDATED_ADDEDBYID,
            "addedbyid.in=" + UPDATED_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByAddedbyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where addedbyid is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("addedbyid.specified=true", "addedbyid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByAddedbyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where addedbyid is greater than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "addedbyid.greaterThanOrEqual=" + DEFAULT_ADDEDBYID,
            "addedbyid.greaterThanOrEqual=" + UPDATED_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByAddedbyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where addedbyid is less than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "addedbyid.lessThanOrEqual=" + DEFAULT_ADDEDBYID,
            "addedbyid.lessThanOrEqual=" + SMALLER_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByAddedbyidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where addedbyid is less than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "addedbyid.lessThan=" + UPDATED_ADDEDBYID,
            "addedbyid.lessThan=" + DEFAULT_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByAddedbyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where addedbyid is greater than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "addedbyid.greaterThan=" + SMALLER_ADDEDBYID,
            "addedbyid.greaterThan=" + DEFAULT_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByIscustomersrviceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where iscustomersrvice equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "iscustomersrvice.equals=" + DEFAULT_ISCUSTOMERSRVICE,
            "iscustomersrvice.equals=" + UPDATED_ISCUSTOMERSRVICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByIscustomersrviceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where iscustomersrvice in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "iscustomersrvice.in=" + DEFAULT_ISCUSTOMERSRVICE + "," + UPDATED_ISCUSTOMERSRVICE,
            "iscustomersrvice.in=" + UPDATED_ISCUSTOMERSRVICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByIscustomersrviceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where iscustomersrvice is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("iscustomersrvice.specified=true", "iscustomersrvice.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where discount equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where discount in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT,
            "discount.in=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where discount is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where discount is greater than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where discount is less than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where discount is less than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "discount.lessThan=" + UPDATED_DISCOUNT,
            "discount.lessThan=" + DEFAULT_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where discount is greater than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "discount.greaterThan=" + SMALLER_DISCOUNT,
            "discount.greaterThan=" + DEFAULT_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicepriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where serviceprice equals to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "serviceprice.equals=" + DEFAULT_SERVICEPRICE,
            "serviceprice.equals=" + UPDATED_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicepriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where serviceprice in
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "serviceprice.in=" + DEFAULT_SERVICEPRICE + "," + UPDATED_SERVICEPRICE,
            "serviceprice.in=" + UPDATED_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicepriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where serviceprice is not null
        defaultAutojobsalesinvoiceservicechargelineFiltering("serviceprice.specified=true", "serviceprice.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicepriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where serviceprice is greater than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "serviceprice.greaterThanOrEqual=" + DEFAULT_SERVICEPRICE,
            "serviceprice.greaterThanOrEqual=" + UPDATED_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicepriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where serviceprice is less than or equal to
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "serviceprice.lessThanOrEqual=" + DEFAULT_SERVICEPRICE,
            "serviceprice.lessThanOrEqual=" + SMALLER_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicepriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where serviceprice is less than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "serviceprice.lessThan=" + UPDATED_SERVICEPRICE,
            "serviceprice.lessThan=" + DEFAULT_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsalesinvoiceservicechargelinesByServicepriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        // Get all the autojobsalesinvoiceservicechargelineList where serviceprice is greater than
        defaultAutojobsalesinvoiceservicechargelineFiltering(
            "serviceprice.greaterThan=" + SMALLER_SERVICEPRICE,
            "serviceprice.greaterThan=" + DEFAULT_SERVICEPRICE
        );
    }

    private void defaultAutojobsalesinvoiceservicechargelineFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAutojobsalesinvoiceservicechargelineShouldBeFound(shouldBeFound);
        defaultAutojobsalesinvoiceservicechargelineShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutojobsalesinvoiceservicechargelineShouldBeFound(String filter) throws Exception {
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsalesinvoiceservicechargeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].servicename").value(hasItem(DEFAULT_SERVICENAME)))
            .andExpect(jsonPath("$.[*].servicediscription").value(hasItem(DEFAULT_SERVICEDISCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedbyid").value(hasItem(DEFAULT_ADDEDBYID)))
            .andExpect(jsonPath("$.[*].iscustomersrvice").value(hasItem(DEFAULT_ISCUSTOMERSRVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceprice").value(hasItem(DEFAULT_SERVICEPRICE.doubleValue())));

        // Check, that the count call also returns 1
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutojobsalesinvoiceservicechargelineShouldNotBeFound(String filter) throws Exception {
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutojobsalesinvoiceservicechargeline() throws Exception {
        // Get the autojobsalesinvoiceservicechargeline
        restAutojobsalesinvoiceservicechargelineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutojobsalesinvoiceservicechargeline() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsalesinvoiceservicechargeline
        Autojobsalesinvoiceservicechargeline updatedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository
            .findById(autojobsalesinvoiceservicechargeline.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAutojobsalesinvoiceservicechargeline are not directly saved in db
        em.detach(updatedAutojobsalesinvoiceservicechargeline);
        updatedAutojobsalesinvoiceservicechargeline
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .iscustomersrvice(UPDATED_ISCUSTOMERSRVICE)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutojobsalesinvoiceservicechargeline.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutojobsalesinvoiceservicechargelineToMatchAllProperties(updatedAutojobsalesinvoiceservicechargeline);
    }

    @Test
    @Transactional
    void putNonExistingAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autojobsalesinvoiceservicechargeline.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutojobsalesinvoiceservicechargelineWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsalesinvoiceservicechargeline using partial update
        Autojobsalesinvoiceservicechargeline partialUpdatedAutojobsalesinvoiceservicechargeline =
            new Autojobsalesinvoiceservicechargeline();
        partialUpdatedAutojobsalesinvoiceservicechargeline.setId(autojobsalesinvoiceservicechargeline.getId());

        partialUpdatedAutojobsalesinvoiceservicechargeline
            .invoiceid(UPDATED_INVOICEID)
            .optionid(UPDATED_OPTIONID)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .iscustomersrvice(UPDATED_ISCUSTOMERSRVICE)
            .discount(UPDATED_DISCOUNT);

        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsalesinvoiceservicechargeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsalesinvoiceservicechargeline in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsalesinvoiceservicechargelineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutojobsalesinvoiceservicechargeline, autojobsalesinvoiceservicechargeline),
            getPersistedAutojobsalesinvoiceservicechargeline(autojobsalesinvoiceservicechargeline)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutojobsalesinvoiceservicechargelineWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsalesinvoiceservicechargeline using partial update
        Autojobsalesinvoiceservicechargeline partialUpdatedAutojobsalesinvoiceservicechargeline =
            new Autojobsalesinvoiceservicechargeline();
        partialUpdatedAutojobsalesinvoiceservicechargeline.setId(autojobsalesinvoiceservicechargeline.getId());

        partialUpdatedAutojobsalesinvoiceservicechargeline
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .iscustomersrvice(UPDATED_ISCUSTOMERSRVICE)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsalesinvoiceservicechargeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsalesinvoiceservicechargeline in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsalesinvoiceservicechargelineUpdatableFieldsEquals(
            partialUpdatedAutojobsalesinvoiceservicechargeline,
            getPersistedAutojobsalesinvoiceservicechargeline(partialUpdatedAutojobsalesinvoiceservicechargeline)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autojobsalesinvoiceservicechargeline.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutojobsalesinvoiceservicechargeline() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsalesinvoiceservicechargeline.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsalesinvoiceservicechargeline))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsalesinvoiceservicechargeline in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutojobsalesinvoiceservicechargeline() throws Exception {
        // Initialize the database
        insertedAutojobsalesinvoiceservicechargeline = autojobsalesinvoiceservicechargelineRepository.saveAndFlush(
            autojobsalesinvoiceservicechargeline
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autojobsalesinvoiceservicechargeline
        restAutojobsalesinvoiceservicechargelineMockMvc
            .perform(delete(ENTITY_API_URL_ID, autojobsalesinvoiceservicechargeline.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autojobsalesinvoiceservicechargelineRepository.count();
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

    protected Autojobsalesinvoiceservicechargeline getPersistedAutojobsalesinvoiceservicechargeline(
        Autojobsalesinvoiceservicechargeline autojobsalesinvoiceservicechargeline
    ) {
        return autojobsalesinvoiceservicechargelineRepository.findById(autojobsalesinvoiceservicechargeline.getId()).orElseThrow();
    }

    protected void assertPersistedAutojobsalesinvoiceservicechargelineToMatchAllProperties(
        Autojobsalesinvoiceservicechargeline expectedAutojobsalesinvoiceservicechargeline
    ) {
        assertAutojobsalesinvoiceservicechargelineAllPropertiesEquals(
            expectedAutojobsalesinvoiceservicechargeline,
            getPersistedAutojobsalesinvoiceservicechargeline(expectedAutojobsalesinvoiceservicechargeline)
        );
    }

    protected void assertPersistedAutojobsalesinvoiceservicechargelineToMatchUpdatableProperties(
        Autojobsalesinvoiceservicechargeline expectedAutojobsalesinvoiceservicechargeline
    ) {
        assertAutojobsalesinvoiceservicechargelineAllUpdatablePropertiesEquals(
            expectedAutojobsalesinvoiceservicechargeline,
            getPersistedAutojobsalesinvoiceservicechargeline(expectedAutojobsalesinvoiceservicechargeline)
        );
    }
}
