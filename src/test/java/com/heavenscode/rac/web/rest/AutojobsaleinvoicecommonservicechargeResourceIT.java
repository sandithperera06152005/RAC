package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutojobsaleinvoicecommonservicechargeAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge;
import com.heavenscode.rac.repository.AutojobsaleinvoicecommonservicechargeRepository;
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
 * Integration tests for the {@link AutojobsaleinvoicecommonservicechargeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutojobsaleinvoicecommonservicechargeResourceIT {

    private static final Integer DEFAULT_INVOICEID = 1;
    private static final Integer UPDATED_INVOICEID = 2;
    private static final Integer SMALLER_INVOICEID = 1 - 1;

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;
    private static final Integer SMALLER_LINEID = 1 - 1;

    private static final Integer DEFAULT_OPTIONID = 1;
    private static final Integer UPDATED_OPTIONID = 2;
    private static final Integer SMALLER_OPTIONID = 1 - 1;

    private static final Integer DEFAULT_MAINID = 1;
    private static final Integer UPDATED_MAINID = 2;
    private static final Integer SMALLER_MAINID = 1 - 1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;
    private static final Float SMALLER_VALUE = 1F - 1F;

    private static final Integer DEFAULT_ADDEDBYID = 1;
    private static final Integer UPDATED_ADDEDBYID = 2;
    private static final Integer SMALLER_ADDEDBYID = 1 - 1;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;
    private static final Float SMALLER_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_SERVICEPRICE = 1F;
    private static final Float UPDATED_SERVICEPRICE = 2F;
    private static final Float SMALLER_SERVICEPRICE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/autojobsaleinvoicecommonservicecharges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutojobsaleinvoicecommonservicechargeRepository autojobsaleinvoicecommonservicechargeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutojobsaleinvoicecommonservicechargeMockMvc;

    private Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge;

    private Autojobsaleinvoicecommonservicecharge insertedAutojobsaleinvoicecommonservicecharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsaleinvoicecommonservicecharge createEntity() {
        return new Autojobsaleinvoicecommonservicecharge()
            .invoiceid(DEFAULT_INVOICEID)
            .lineid(DEFAULT_LINEID)
            .optionid(DEFAULT_OPTIONID)
            .mainid(DEFAULT_MAINID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .addedbyid(DEFAULT_ADDEDBYID)
            .discount(DEFAULT_DISCOUNT)
            .serviceprice(DEFAULT_SERVICEPRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsaleinvoicecommonservicecharge createUpdatedEntity() {
        return new Autojobsaleinvoicecommonservicecharge()
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);
    }

    @BeforeEach
    public void initTest() {
        autojobsaleinvoicecommonservicecharge = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutojobsaleinvoicecommonservicecharge != null) {
            autojobsaleinvoicecommonservicechargeRepository.delete(insertedAutojobsaleinvoicecommonservicecharge);
            insertedAutojobsaleinvoicecommonservicecharge = null;
        }
    }

    @Test
    @Transactional
    void createAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autojobsaleinvoicecommonservicecharge
        var returnedAutojobsaleinvoicecommonservicecharge = om.readValue(
            restAutojobsaleinvoicecommonservicechargeMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autojobsaleinvoicecommonservicecharge.class
        );

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutojobsaleinvoicecommonservicechargeUpdatableFieldsEquals(
            returnedAutojobsaleinvoicecommonservicecharge,
            getPersistedAutojobsaleinvoicecommonservicecharge(returnedAutojobsaleinvoicecommonservicecharge)
        );

        insertedAutojobsaleinvoicecommonservicecharge = returnedAutojobsaleinvoicecommonservicecharge;
    }

    @Test
    @Transactional
    void createAutojobsaleinvoicecommonservicechargeWithExistingId() throws Exception {
        // Create the Autojobsaleinvoicecommonservicecharge with an existing ID
        autojobsaleinvoicecommonservicecharge.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicecharges() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsaleinvoicecommonservicecharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedbyid").value(hasItem(DEFAULT_ADDEDBYID)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceprice").value(hasItem(DEFAULT_SERVICEPRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getAutojobsaleinvoicecommonservicecharge() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get the autojobsaleinvoicecommonservicecharge
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(get(ENTITY_API_URL_ID, autojobsaleinvoicecommonservicecharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autojobsaleinvoicecommonservicecharge.getId().intValue()))
            .andExpect(jsonPath("$.invoiceid").value(DEFAULT_INVOICEID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.optionid").value(DEFAULT_OPTIONID))
            .andExpect(jsonPath("$.mainid").value(DEFAULT_MAINID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.addedbyid").value(DEFAULT_ADDEDBYID))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.serviceprice").value(DEFAULT_SERVICEPRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getAutojobsaleinvoicecommonservicechargesByIdFiltering() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        Long id = autojobsaleinvoicecommonservicecharge.getId();

        defaultAutojobsaleinvoicecommonservicechargeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAutojobsaleinvoicecommonservicechargeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAutojobsaleinvoicecommonservicechargeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByInvoiceidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where invoiceid equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "invoiceid.equals=" + DEFAULT_INVOICEID,
            "invoiceid.equals=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByInvoiceidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where invoiceid in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "invoiceid.in=" + DEFAULT_INVOICEID + "," + UPDATED_INVOICEID,
            "invoiceid.in=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByInvoiceidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where invoiceid is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("invoiceid.specified=true", "invoiceid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByInvoiceidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where invoiceid is greater than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "invoiceid.greaterThanOrEqual=" + DEFAULT_INVOICEID,
            "invoiceid.greaterThanOrEqual=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByInvoiceidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where invoiceid is less than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "invoiceid.lessThanOrEqual=" + DEFAULT_INVOICEID,
            "invoiceid.lessThanOrEqual=" + SMALLER_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByInvoiceidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where invoiceid is less than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "invoiceid.lessThan=" + UPDATED_INVOICEID,
            "invoiceid.lessThan=" + DEFAULT_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByInvoiceidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where invoiceid is greater than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "invoiceid.greaterThan=" + SMALLER_INVOICEID,
            "invoiceid.greaterThan=" + DEFAULT_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByLineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where lineid equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering("lineid.equals=" + DEFAULT_LINEID, "lineid.equals=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByLineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where lineid in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "lineid.in=" + DEFAULT_LINEID + "," + UPDATED_LINEID,
            "lineid.in=" + UPDATED_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByLineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where lineid is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("lineid.specified=true", "lineid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByLineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where lineid is greater than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "lineid.greaterThanOrEqual=" + DEFAULT_LINEID,
            "lineid.greaterThanOrEqual=" + UPDATED_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByLineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where lineid is less than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "lineid.lessThanOrEqual=" + DEFAULT_LINEID,
            "lineid.lessThanOrEqual=" + SMALLER_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByLineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where lineid is less than
        defaultAutojobsaleinvoicecommonservicechargeFiltering("lineid.lessThan=" + UPDATED_LINEID, "lineid.lessThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByLineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where lineid is greater than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "lineid.greaterThan=" + SMALLER_LINEID,
            "lineid.greaterThan=" + DEFAULT_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByOptionidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where optionid equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering("optionid.equals=" + DEFAULT_OPTIONID, "optionid.equals=" + UPDATED_OPTIONID);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByOptionidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where optionid in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "optionid.in=" + DEFAULT_OPTIONID + "," + UPDATED_OPTIONID,
            "optionid.in=" + UPDATED_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByOptionidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where optionid is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("optionid.specified=true", "optionid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByOptionidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where optionid is greater than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "optionid.greaterThanOrEqual=" + DEFAULT_OPTIONID,
            "optionid.greaterThanOrEqual=" + UPDATED_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByOptionidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where optionid is less than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "optionid.lessThanOrEqual=" + DEFAULT_OPTIONID,
            "optionid.lessThanOrEqual=" + SMALLER_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByOptionidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where optionid is less than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "optionid.lessThan=" + UPDATED_OPTIONID,
            "optionid.lessThan=" + DEFAULT_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByOptionidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where optionid is greater than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "optionid.greaterThan=" + SMALLER_OPTIONID,
            "optionid.greaterThan=" + DEFAULT_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByMainidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where mainid equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering("mainid.equals=" + DEFAULT_MAINID, "mainid.equals=" + UPDATED_MAINID);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByMainidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where mainid in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "mainid.in=" + DEFAULT_MAINID + "," + UPDATED_MAINID,
            "mainid.in=" + UPDATED_MAINID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByMainidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where mainid is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("mainid.specified=true", "mainid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByMainidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where mainid is greater than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "mainid.greaterThanOrEqual=" + DEFAULT_MAINID,
            "mainid.greaterThanOrEqual=" + UPDATED_MAINID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByMainidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where mainid is less than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "mainid.lessThanOrEqual=" + DEFAULT_MAINID,
            "mainid.lessThanOrEqual=" + SMALLER_MAINID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByMainidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where mainid is less than
        defaultAutojobsaleinvoicecommonservicechargeFiltering("mainid.lessThan=" + UPDATED_MAINID, "mainid.lessThan=" + DEFAULT_MAINID);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByMainidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where mainid is greater than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "mainid.greaterThan=" + SMALLER_MAINID,
            "mainid.greaterThan=" + DEFAULT_MAINID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where code equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where code in
        defaultAutojobsaleinvoicecommonservicechargeFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where code is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where code contains
        defaultAutojobsaleinvoicecommonservicechargeFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where code does not contain
        defaultAutojobsaleinvoicecommonservicechargeFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where name equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where name in
        defaultAutojobsaleinvoicecommonservicechargeFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where name is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where name contains
        defaultAutojobsaleinvoicecommonservicechargeFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where name does not contain
        defaultAutojobsaleinvoicecommonservicechargeFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where description equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "description.equals=" + DEFAULT_DESCRIPTION,
            "description.equals=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where description in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where description is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where description contains
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "description.contains=" + DEFAULT_DESCRIPTION,
            "description.contains=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where description does not contain
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where value equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where value in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE,
            "value.in=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where value is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where value is greater than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "value.greaterThanOrEqual=" + DEFAULT_VALUE,
            "value.greaterThanOrEqual=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where value is less than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "value.lessThanOrEqual=" + DEFAULT_VALUE,
            "value.lessThanOrEqual=" + SMALLER_VALUE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where value is less than
        defaultAutojobsaleinvoicecommonservicechargeFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where value is greater than
        defaultAutojobsaleinvoicecommonservicechargeFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByAddedbyidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where addedbyid equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "addedbyid.equals=" + DEFAULT_ADDEDBYID,
            "addedbyid.equals=" + UPDATED_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByAddedbyidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where addedbyid in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "addedbyid.in=" + DEFAULT_ADDEDBYID + "," + UPDATED_ADDEDBYID,
            "addedbyid.in=" + UPDATED_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByAddedbyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where addedbyid is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("addedbyid.specified=true", "addedbyid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByAddedbyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where addedbyid is greater than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "addedbyid.greaterThanOrEqual=" + DEFAULT_ADDEDBYID,
            "addedbyid.greaterThanOrEqual=" + UPDATED_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByAddedbyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where addedbyid is less than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "addedbyid.lessThanOrEqual=" + DEFAULT_ADDEDBYID,
            "addedbyid.lessThanOrEqual=" + SMALLER_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByAddedbyidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where addedbyid is less than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "addedbyid.lessThan=" + UPDATED_ADDEDBYID,
            "addedbyid.lessThan=" + DEFAULT_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByAddedbyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where addedbyid is greater than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "addedbyid.greaterThan=" + SMALLER_ADDEDBYID,
            "addedbyid.greaterThan=" + DEFAULT_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where discount equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where discount in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT,
            "discount.in=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where discount is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where discount is greater than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where discount is less than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where discount is less than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "discount.lessThan=" + UPDATED_DISCOUNT,
            "discount.lessThan=" + DEFAULT_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where discount is greater than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "discount.greaterThan=" + SMALLER_DISCOUNT,
            "discount.greaterThan=" + DEFAULT_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByServicepriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where serviceprice equals to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "serviceprice.equals=" + DEFAULT_SERVICEPRICE,
            "serviceprice.equals=" + UPDATED_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByServicepriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where serviceprice in
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "serviceprice.in=" + DEFAULT_SERVICEPRICE + "," + UPDATED_SERVICEPRICE,
            "serviceprice.in=" + UPDATED_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByServicepriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where serviceprice is not null
        defaultAutojobsaleinvoicecommonservicechargeFiltering("serviceprice.specified=true", "serviceprice.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByServicepriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where serviceprice is greater than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "serviceprice.greaterThanOrEqual=" + DEFAULT_SERVICEPRICE,
            "serviceprice.greaterThanOrEqual=" + UPDATED_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByServicepriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where serviceprice is less than or equal to
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "serviceprice.lessThanOrEqual=" + DEFAULT_SERVICEPRICE,
            "serviceprice.lessThanOrEqual=" + SMALLER_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByServicepriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where serviceprice is less than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "serviceprice.lessThan=" + UPDATED_SERVICEPRICE,
            "serviceprice.lessThan=" + DEFAULT_SERVICEPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsaleinvoicecommonservicechargesByServicepriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        // Get all the autojobsaleinvoicecommonservicechargeList where serviceprice is greater than
        defaultAutojobsaleinvoicecommonservicechargeFiltering(
            "serviceprice.greaterThan=" + SMALLER_SERVICEPRICE,
            "serviceprice.greaterThan=" + DEFAULT_SERVICEPRICE
        );
    }

    private void defaultAutojobsaleinvoicecommonservicechargeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAutojobsaleinvoicecommonservicechargeShouldBeFound(shouldBeFound);
        defaultAutojobsaleinvoicecommonservicechargeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutojobsaleinvoicecommonservicechargeShouldBeFound(String filter) throws Exception {
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsaleinvoicecommonservicecharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedbyid").value(hasItem(DEFAULT_ADDEDBYID)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceprice").value(hasItem(DEFAULT_SERVICEPRICE.doubleValue())));

        // Check, that the count call also returns 1
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutojobsaleinvoicecommonservicechargeShouldNotBeFound(String filter) throws Exception {
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutojobsaleinvoicecommonservicecharge() throws Exception {
        // Get the autojobsaleinvoicecommonservicecharge
        restAutojobsaleinvoicecommonservicechargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutojobsaleinvoicecommonservicecharge() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsaleinvoicecommonservicecharge
        Autojobsaleinvoicecommonservicecharge updatedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository
            .findById(autojobsaleinvoicecommonservicecharge.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAutojobsaleinvoicecommonservicecharge are not directly saved in db
        em.detach(updatedAutojobsaleinvoicecommonservicecharge);
        updatedAutojobsaleinvoicecommonservicecharge
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutojobsaleinvoicecommonservicecharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutojobsaleinvoicecommonservicechargeToMatchAllProperties(updatedAutojobsaleinvoicecommonservicecharge);
    }

    @Test
    @Transactional
    void putNonExistingAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autojobsaleinvoicecommonservicecharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutojobsaleinvoicecommonservicechargeWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsaleinvoicecommonservicecharge using partial update
        Autojobsaleinvoicecommonservicecharge partialUpdatedAutojobsaleinvoicecommonservicecharge =
            new Autojobsaleinvoicecommonservicecharge();
        partialUpdatedAutojobsaleinvoicecommonservicecharge.setId(autojobsaleinvoicecommonservicecharge.getId());

        partialUpdatedAutojobsaleinvoicecommonservicecharge
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsaleinvoicecommonservicecharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsaleinvoicecommonservicechargeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutojobsaleinvoicecommonservicecharge, autojobsaleinvoicecommonservicecharge),
            getPersistedAutojobsaleinvoicecommonservicecharge(autojobsaleinvoicecommonservicecharge)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutojobsaleinvoicecommonservicechargeWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsaleinvoicecommonservicecharge using partial update
        Autojobsaleinvoicecommonservicecharge partialUpdatedAutojobsaleinvoicecommonservicecharge =
            new Autojobsaleinvoicecommonservicecharge();
        partialUpdatedAutojobsaleinvoicecommonservicecharge.setId(autojobsaleinvoicecommonservicecharge.getId());

        partialUpdatedAutojobsaleinvoicecommonservicecharge
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedbyid(UPDATED_ADDEDBYID)
            .discount(UPDATED_DISCOUNT)
            .serviceprice(UPDATED_SERVICEPRICE);

        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsaleinvoicecommonservicecharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsaleinvoicecommonservicechargeUpdatableFieldsEquals(
            partialUpdatedAutojobsaleinvoicecommonservicecharge,
            getPersistedAutojobsaleinvoicecommonservicecharge(partialUpdatedAutojobsaleinvoicecommonservicecharge)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autojobsaleinvoicecommonservicecharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutojobsaleinvoicecommonservicecharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsaleinvoicecommonservicecharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsaleinvoicecommonservicecharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsaleinvoicecommonservicecharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutojobsaleinvoicecommonservicecharge() throws Exception {
        // Initialize the database
        insertedAutojobsaleinvoicecommonservicecharge = autojobsaleinvoicecommonservicechargeRepository.saveAndFlush(
            autojobsaleinvoicecommonservicecharge
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autojobsaleinvoicecommonservicecharge
        restAutojobsaleinvoicecommonservicechargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, autojobsaleinvoicecommonservicecharge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autojobsaleinvoicecommonservicechargeRepository.count();
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

    protected Autojobsaleinvoicecommonservicecharge getPersistedAutojobsaleinvoicecommonservicecharge(
        Autojobsaleinvoicecommonservicecharge autojobsaleinvoicecommonservicecharge
    ) {
        return autojobsaleinvoicecommonservicechargeRepository.findById(autojobsaleinvoicecommonservicecharge.getId()).orElseThrow();
    }

    protected void assertPersistedAutojobsaleinvoicecommonservicechargeToMatchAllProperties(
        Autojobsaleinvoicecommonservicecharge expectedAutojobsaleinvoicecommonservicecharge
    ) {
        assertAutojobsaleinvoicecommonservicechargeAllPropertiesEquals(
            expectedAutojobsaleinvoicecommonservicecharge,
            getPersistedAutojobsaleinvoicecommonservicecharge(expectedAutojobsaleinvoicecommonservicecharge)
        );
    }

    protected void assertPersistedAutojobsaleinvoicecommonservicechargeToMatchUpdatableProperties(
        Autojobsaleinvoicecommonservicecharge expectedAutojobsaleinvoicecommonservicecharge
    ) {
        assertAutojobsaleinvoicecommonservicechargeAllUpdatablePropertiesEquals(
            expectedAutojobsaleinvoicecommonservicecharge,
            getPersistedAutojobsaleinvoicecommonservicecharge(expectedAutojobsaleinvoicecommonservicecharge)
        );
    }
}
