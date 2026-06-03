package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.BillingserviceoptionAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Billingserviceoption;
import com.heavenscode.rac.repository.BillingserviceoptionRepository;
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
 * Integration tests for the {@link BillingserviceoptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BillingserviceoptionResourceIT {

    private static final String DEFAULT_SERVICENAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICEDISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICEDISCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Integer DEFAULT_ORDERBY = 1;
    private static final Integer UPDATED_ORDERBY = 2;
    private static final Integer SMALLER_ORDERBY = 1 - 1;

    private static final Boolean DEFAULT_BILLTOCUSTOMER = false;
    private static final Boolean UPDATED_BILLTOCUSTOMER = true;

    private static final String ENTITY_API_URL = "/api/billingserviceoptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BillingserviceoptionRepository billingserviceoptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillingserviceoptionMockMvc;

    private Billingserviceoption billingserviceoption;

    private Billingserviceoption insertedBillingserviceoption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Billingserviceoption createEntity() {
        return new Billingserviceoption()
            .servicename(DEFAULT_SERVICENAME)
            .servicediscription(DEFAULT_SERVICEDISCRIPTION)
            .isactive(DEFAULT_ISACTIVE)
            .lmd(DEFAULT_LMD)
            .lmu(DEFAULT_LMU)
            .orderby(DEFAULT_ORDERBY)
            .billtocustomer(DEFAULT_BILLTOCUSTOMER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Billingserviceoption createUpdatedEntity() {
        return new Billingserviceoption()
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .isactive(UPDATED_ISACTIVE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU)
            .orderby(UPDATED_ORDERBY)
            .billtocustomer(UPDATED_BILLTOCUSTOMER);
    }

    @BeforeEach
    public void initTest() {
        billingserviceoption = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBillingserviceoption != null) {
            billingserviceoptionRepository.delete(insertedBillingserviceoption);
            insertedBillingserviceoption = null;
        }
    }

    @Test
    @Transactional
    void createBillingserviceoption() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Billingserviceoption
        var returnedBillingserviceoption = om.readValue(
            restBillingserviceoptionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(billingserviceoption)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Billingserviceoption.class
        );

        // Validate the Billingserviceoption in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBillingserviceoptionUpdatableFieldsEquals(
            returnedBillingserviceoption,
            getPersistedBillingserviceoption(returnedBillingserviceoption)
        );

        insertedBillingserviceoption = returnedBillingserviceoption;
    }

    @Test
    @Transactional
    void createBillingserviceoptionWithExistingId() throws Exception {
        // Create the Billingserviceoption with an existing ID
        billingserviceoption.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingserviceoptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(billingserviceoption)))
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptions() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList
        restBillingserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingserviceoption.getId().intValue())))
            .andExpect(jsonPath("$.[*].servicename").value(hasItem(DEFAULT_SERVICENAME)))
            .andExpect(jsonPath("$.[*].servicediscription").value(hasItem(DEFAULT_SERVICEDISCRIPTION)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].orderby").value(hasItem(DEFAULT_ORDERBY)))
            .andExpect(jsonPath("$.[*].billtocustomer").value(hasItem(DEFAULT_BILLTOCUSTOMER.booleanValue())));
    }

    @Test
    @Transactional
    void getBillingserviceoption() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get the billingserviceoption
        restBillingserviceoptionMockMvc
            .perform(get(ENTITY_API_URL_ID, billingserviceoption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billingserviceoption.getId().intValue()))
            .andExpect(jsonPath("$.servicename").value(DEFAULT_SERVICENAME))
            .andExpect(jsonPath("$.servicediscription").value(DEFAULT_SERVICEDISCRIPTION))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.orderby").value(DEFAULT_ORDERBY))
            .andExpect(jsonPath("$.billtocustomer").value(DEFAULT_BILLTOCUSTOMER.booleanValue()));
    }

    @Test
    @Transactional
    void getBillingserviceoptionsByIdFiltering() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        Long id = billingserviceoption.getId();

        defaultBillingserviceoptionFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBillingserviceoptionFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBillingserviceoptionFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicenameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicename equals to
        defaultBillingserviceoptionFiltering("servicename.equals=" + DEFAULT_SERVICENAME, "servicename.equals=" + UPDATED_SERVICENAME);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicenameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicename in
        defaultBillingserviceoptionFiltering(
            "servicename.in=" + DEFAULT_SERVICENAME + "," + UPDATED_SERVICENAME,
            "servicename.in=" + UPDATED_SERVICENAME
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicename is not null
        defaultBillingserviceoptionFiltering("servicename.specified=true", "servicename.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicenameContainsSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicename contains
        defaultBillingserviceoptionFiltering("servicename.contains=" + DEFAULT_SERVICENAME, "servicename.contains=" + UPDATED_SERVICENAME);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicenameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicename does not contain
        defaultBillingserviceoptionFiltering(
            "servicename.doesNotContain=" + UPDATED_SERVICENAME,
            "servicename.doesNotContain=" + DEFAULT_SERVICENAME
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicediscriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicediscription equals to
        defaultBillingserviceoptionFiltering(
            "servicediscription.equals=" + DEFAULT_SERVICEDISCRIPTION,
            "servicediscription.equals=" + UPDATED_SERVICEDISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicediscriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicediscription in
        defaultBillingserviceoptionFiltering(
            "servicediscription.in=" + DEFAULT_SERVICEDISCRIPTION + "," + UPDATED_SERVICEDISCRIPTION,
            "servicediscription.in=" + UPDATED_SERVICEDISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicediscriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicediscription is not null
        defaultBillingserviceoptionFiltering("servicediscription.specified=true", "servicediscription.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicediscriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicediscription contains
        defaultBillingserviceoptionFiltering(
            "servicediscription.contains=" + DEFAULT_SERVICEDISCRIPTION,
            "servicediscription.contains=" + UPDATED_SERVICEDISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByServicediscriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where servicediscription does not contain
        defaultBillingserviceoptionFiltering(
            "servicediscription.doesNotContain=" + UPDATED_SERVICEDISCRIPTION,
            "servicediscription.doesNotContain=" + DEFAULT_SERVICEDISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where isactive equals to
        defaultBillingserviceoptionFiltering("isactive.equals=" + DEFAULT_ISACTIVE, "isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where isactive in
        defaultBillingserviceoptionFiltering("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE, "isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where isactive is not null
        defaultBillingserviceoptionFiltering("isactive.specified=true", "isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmd equals to
        defaultBillingserviceoptionFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmd in
        defaultBillingserviceoptionFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmd is not null
        defaultBillingserviceoptionFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmu equals to
        defaultBillingserviceoptionFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmu in
        defaultBillingserviceoptionFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmu is not null
        defaultBillingserviceoptionFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmu is greater than or equal to
        defaultBillingserviceoptionFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmu is less than or equal to
        defaultBillingserviceoptionFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmu is less than
        defaultBillingserviceoptionFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where lmu is greater than
        defaultBillingserviceoptionFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByOrderbyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where orderby equals to
        defaultBillingserviceoptionFiltering("orderby.equals=" + DEFAULT_ORDERBY, "orderby.equals=" + UPDATED_ORDERBY);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByOrderbyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where orderby in
        defaultBillingserviceoptionFiltering("orderby.in=" + DEFAULT_ORDERBY + "," + UPDATED_ORDERBY, "orderby.in=" + UPDATED_ORDERBY);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByOrderbyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where orderby is not null
        defaultBillingserviceoptionFiltering("orderby.specified=true", "orderby.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByOrderbyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where orderby is greater than or equal to
        defaultBillingserviceoptionFiltering(
            "orderby.greaterThanOrEqual=" + DEFAULT_ORDERBY,
            "orderby.greaterThanOrEqual=" + UPDATED_ORDERBY
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByOrderbyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where orderby is less than or equal to
        defaultBillingserviceoptionFiltering("orderby.lessThanOrEqual=" + DEFAULT_ORDERBY, "orderby.lessThanOrEqual=" + SMALLER_ORDERBY);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByOrderbyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where orderby is less than
        defaultBillingserviceoptionFiltering("orderby.lessThan=" + UPDATED_ORDERBY, "orderby.lessThan=" + DEFAULT_ORDERBY);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByOrderbyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where orderby is greater than
        defaultBillingserviceoptionFiltering("orderby.greaterThan=" + SMALLER_ORDERBY, "orderby.greaterThan=" + DEFAULT_ORDERBY);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByBilltocustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where billtocustomer equals to
        defaultBillingserviceoptionFiltering(
            "billtocustomer.equals=" + DEFAULT_BILLTOCUSTOMER,
            "billtocustomer.equals=" + UPDATED_BILLTOCUSTOMER
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByBilltocustomerIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where billtocustomer in
        defaultBillingserviceoptionFiltering(
            "billtocustomer.in=" + DEFAULT_BILLTOCUSTOMER + "," + UPDATED_BILLTOCUSTOMER,
            "billtocustomer.in=" + UPDATED_BILLTOCUSTOMER
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionsByBilltocustomerIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        // Get all the billingserviceoptionList where billtocustomer is not null
        defaultBillingserviceoptionFiltering("billtocustomer.specified=true", "billtocustomer.specified=false");
    }

    private void defaultBillingserviceoptionFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBillingserviceoptionShouldBeFound(shouldBeFound);
        defaultBillingserviceoptionShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBillingserviceoptionShouldBeFound(String filter) throws Exception {
        restBillingserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingserviceoption.getId().intValue())))
            .andExpect(jsonPath("$.[*].servicename").value(hasItem(DEFAULT_SERVICENAME)))
            .andExpect(jsonPath("$.[*].servicediscription").value(hasItem(DEFAULT_SERVICEDISCRIPTION)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].orderby").value(hasItem(DEFAULT_ORDERBY)))
            .andExpect(jsonPath("$.[*].billtocustomer").value(hasItem(DEFAULT_BILLTOCUSTOMER.booleanValue())));

        // Check, that the count call also returns 1
        restBillingserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBillingserviceoptionShouldNotBeFound(String filter) throws Exception {
        restBillingserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBillingserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBillingserviceoption() throws Exception {
        // Get the billingserviceoption
        restBillingserviceoptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBillingserviceoption() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the billingserviceoption
        Billingserviceoption updatedBillingserviceoption = billingserviceoptionRepository
            .findById(billingserviceoption.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedBillingserviceoption are not directly saved in db
        em.detach(updatedBillingserviceoption);
        updatedBillingserviceoption
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .isactive(UPDATED_ISACTIVE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU)
            .orderby(UPDATED_ORDERBY)
            .billtocustomer(UPDATED_BILLTOCUSTOMER);

        restBillingserviceoptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBillingserviceoption.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBillingserviceoption))
            )
            .andExpect(status().isOk());

        // Validate the Billingserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBillingserviceoptionToMatchAllProperties(updatedBillingserviceoption);
    }

    @Test
    @Transactional
    void putNonExistingBillingserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoption.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingserviceoptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billingserviceoption.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(billingserviceoption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBillingserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingserviceoptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(billingserviceoption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBillingserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingserviceoptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(billingserviceoption)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Billingserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBillingserviceoptionWithPatch() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the billingserviceoption using partial update
        Billingserviceoption partialUpdatedBillingserviceoption = new Billingserviceoption();
        partialUpdatedBillingserviceoption.setId(billingserviceoption.getId());

        partialUpdatedBillingserviceoption
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .billtocustomer(UPDATED_BILLTOCUSTOMER);

        restBillingserviceoptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillingserviceoption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBillingserviceoption))
            )
            .andExpect(status().isOk());

        // Validate the Billingserviceoption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBillingserviceoptionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBillingserviceoption, billingserviceoption),
            getPersistedBillingserviceoption(billingserviceoption)
        );
    }

    @Test
    @Transactional
    void fullUpdateBillingserviceoptionWithPatch() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the billingserviceoption using partial update
        Billingserviceoption partialUpdatedBillingserviceoption = new Billingserviceoption();
        partialUpdatedBillingserviceoption.setId(billingserviceoption.getId());

        partialUpdatedBillingserviceoption
            .servicename(UPDATED_SERVICENAME)
            .servicediscription(UPDATED_SERVICEDISCRIPTION)
            .isactive(UPDATED_ISACTIVE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU)
            .orderby(UPDATED_ORDERBY)
            .billtocustomer(UPDATED_BILLTOCUSTOMER);

        restBillingserviceoptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillingserviceoption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBillingserviceoption))
            )
            .andExpect(status().isOk());

        // Validate the Billingserviceoption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBillingserviceoptionUpdatableFieldsEquals(
            partialUpdatedBillingserviceoption,
            getPersistedBillingserviceoption(partialUpdatedBillingserviceoption)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBillingserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoption.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingserviceoptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, billingserviceoption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(billingserviceoption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBillingserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingserviceoptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(billingserviceoption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBillingserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingserviceoptionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(billingserviceoption)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Billingserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBillingserviceoption() throws Exception {
        // Initialize the database
        insertedBillingserviceoption = billingserviceoptionRepository.saveAndFlush(billingserviceoption);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the billingserviceoption
        restBillingserviceoptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, billingserviceoption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return billingserviceoptionRepository.count();
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

    protected Billingserviceoption getPersistedBillingserviceoption(Billingserviceoption billingserviceoption) {
        return billingserviceoptionRepository.findById(billingserviceoption.getId()).orElseThrow();
    }

    protected void assertPersistedBillingserviceoptionToMatchAllProperties(Billingserviceoption expectedBillingserviceoption) {
        assertBillingserviceoptionAllPropertiesEquals(
            expectedBillingserviceoption,
            getPersistedBillingserviceoption(expectedBillingserviceoption)
        );
    }

    protected void assertPersistedBillingserviceoptionToMatchUpdatableProperties(Billingserviceoption expectedBillingserviceoption) {
        assertBillingserviceoptionAllUpdatablePropertiesEquals(
            expectedBillingserviceoption,
            getPersistedBillingserviceoption(expectedBillingserviceoption)
        );
    }
}
