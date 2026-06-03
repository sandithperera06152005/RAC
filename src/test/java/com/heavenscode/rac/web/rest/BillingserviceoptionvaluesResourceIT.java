package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.BillingserviceoptionvaluesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Billingserviceoptionvalues;
import com.heavenscode.rac.repository.BillingserviceoptionvaluesRepository;
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
 * Integration tests for the {@link BillingserviceoptionvaluesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BillingserviceoptionvaluesResourceIT {

    private static final Integer DEFAULT_VEHICLETYPEID = 1;
    private static final Integer UPDATED_VEHICLETYPEID = 2;
    private static final Integer SMALLER_VEHICLETYPEID = 1 - 1;

    private static final Integer DEFAULT_BILLINGSERVICEOPTIONID = 1;
    private static final Integer UPDATED_BILLINGSERVICEOPTIONID = 2;
    private static final Integer SMALLER_BILLINGSERVICEOPTIONID = 1 - 1;

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;
    private static final Float SMALLER_VALUE = 1F - 1F;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final String ENTITY_API_URL = "/api/billingserviceoptionvalues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BillingserviceoptionvaluesRepository billingserviceoptionvaluesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillingserviceoptionvaluesMockMvc;

    private Billingserviceoptionvalues billingserviceoptionvalues;

    private Billingserviceoptionvalues insertedBillingserviceoptionvalues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Billingserviceoptionvalues createEntity() {
        return new Billingserviceoptionvalues()
            .vehicletypeid(DEFAULT_VEHICLETYPEID)
            .billingserviceoptionid(DEFAULT_BILLINGSERVICEOPTIONID)
            .value(DEFAULT_VALUE)
            .lmd(DEFAULT_LMD)
            .lmu(DEFAULT_LMU);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Billingserviceoptionvalues createUpdatedEntity() {
        return new Billingserviceoptionvalues()
            .vehicletypeid(UPDATED_VEHICLETYPEID)
            .billingserviceoptionid(UPDATED_BILLINGSERVICEOPTIONID)
            .value(UPDATED_VALUE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);
    }

    @BeforeEach
    public void initTest() {
        billingserviceoptionvalues = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBillingserviceoptionvalues != null) {
            billingserviceoptionvaluesRepository.delete(insertedBillingserviceoptionvalues);
            insertedBillingserviceoptionvalues = null;
        }
    }

    @Test
    @Transactional
    void createBillingserviceoptionvalues() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Billingserviceoptionvalues
        var returnedBillingserviceoptionvalues = om.readValue(
            restBillingserviceoptionvaluesMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(billingserviceoptionvalues))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Billingserviceoptionvalues.class
        );

        // Validate the Billingserviceoptionvalues in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBillingserviceoptionvaluesUpdatableFieldsEquals(
            returnedBillingserviceoptionvalues,
            getPersistedBillingserviceoptionvalues(returnedBillingserviceoptionvalues)
        );

        insertedBillingserviceoptionvalues = returnedBillingserviceoptionvalues;
    }

    @Test
    @Transactional
    void createBillingserviceoptionvaluesWithExistingId() throws Exception {
        // Create the Billingserviceoptionvalues with an existing ID
        billingserviceoptionvalues.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingserviceoptionvaluesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(billingserviceoptionvalues)))
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoptionvalues in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvalues() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList
        restBillingserviceoptionvaluesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingserviceoptionvalues.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicletypeid").value(hasItem(DEFAULT_VEHICLETYPEID)))
            .andExpect(jsonPath("$.[*].billingserviceoptionid").value(hasItem(DEFAULT_BILLINGSERVICEOPTIONID)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)));
    }

    @Test
    @Transactional
    void getBillingserviceoptionvalues() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get the billingserviceoptionvalues
        restBillingserviceoptionvaluesMockMvc
            .perform(get(ENTITY_API_URL_ID, billingserviceoptionvalues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billingserviceoptionvalues.getId().intValue()))
            .andExpect(jsonPath("$.vehicletypeid").value(DEFAULT_VEHICLETYPEID))
            .andExpect(jsonPath("$.billingserviceoptionid").value(DEFAULT_BILLINGSERVICEOPTIONID))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU));
    }

    @Test
    @Transactional
    void getBillingserviceoptionvaluesByIdFiltering() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        Long id = billingserviceoptionvalues.getId();

        defaultBillingserviceoptionvaluesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBillingserviceoptionvaluesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBillingserviceoptionvaluesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByVehicletypeidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where vehicletypeid equals to
        defaultBillingserviceoptionvaluesFiltering(
            "vehicletypeid.equals=" + DEFAULT_VEHICLETYPEID,
            "vehicletypeid.equals=" + UPDATED_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByVehicletypeidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where vehicletypeid in
        defaultBillingserviceoptionvaluesFiltering(
            "vehicletypeid.in=" + DEFAULT_VEHICLETYPEID + "," + UPDATED_VEHICLETYPEID,
            "vehicletypeid.in=" + UPDATED_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByVehicletypeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where vehicletypeid is not null
        defaultBillingserviceoptionvaluesFiltering("vehicletypeid.specified=true", "vehicletypeid.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByVehicletypeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where vehicletypeid is greater than or equal to
        defaultBillingserviceoptionvaluesFiltering(
            "vehicletypeid.greaterThanOrEqual=" + DEFAULT_VEHICLETYPEID,
            "vehicletypeid.greaterThanOrEqual=" + UPDATED_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByVehicletypeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where vehicletypeid is less than or equal to
        defaultBillingserviceoptionvaluesFiltering(
            "vehicletypeid.lessThanOrEqual=" + DEFAULT_VEHICLETYPEID,
            "vehicletypeid.lessThanOrEqual=" + SMALLER_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByVehicletypeidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where vehicletypeid is less than
        defaultBillingserviceoptionvaluesFiltering(
            "vehicletypeid.lessThan=" + UPDATED_VEHICLETYPEID,
            "vehicletypeid.lessThan=" + DEFAULT_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByVehicletypeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where vehicletypeid is greater than
        defaultBillingserviceoptionvaluesFiltering(
            "vehicletypeid.greaterThan=" + SMALLER_VEHICLETYPEID,
            "vehicletypeid.greaterThan=" + DEFAULT_VEHICLETYPEID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByBillingserviceoptionidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where billingserviceoptionid equals to
        defaultBillingserviceoptionvaluesFiltering(
            "billingserviceoptionid.equals=" + DEFAULT_BILLINGSERVICEOPTIONID,
            "billingserviceoptionid.equals=" + UPDATED_BILLINGSERVICEOPTIONID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByBillingserviceoptionidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where billingserviceoptionid in
        defaultBillingserviceoptionvaluesFiltering(
            "billingserviceoptionid.in=" + DEFAULT_BILLINGSERVICEOPTIONID + "," + UPDATED_BILLINGSERVICEOPTIONID,
            "billingserviceoptionid.in=" + UPDATED_BILLINGSERVICEOPTIONID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByBillingserviceoptionidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where billingserviceoptionid is not null
        defaultBillingserviceoptionvaluesFiltering("billingserviceoptionid.specified=true", "billingserviceoptionid.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByBillingserviceoptionidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where billingserviceoptionid is greater than or equal to
        defaultBillingserviceoptionvaluesFiltering(
            "billingserviceoptionid.greaterThanOrEqual=" + DEFAULT_BILLINGSERVICEOPTIONID,
            "billingserviceoptionid.greaterThanOrEqual=" + UPDATED_BILLINGSERVICEOPTIONID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByBillingserviceoptionidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where billingserviceoptionid is less than or equal to
        defaultBillingserviceoptionvaluesFiltering(
            "billingserviceoptionid.lessThanOrEqual=" + DEFAULT_BILLINGSERVICEOPTIONID,
            "billingserviceoptionid.lessThanOrEqual=" + SMALLER_BILLINGSERVICEOPTIONID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByBillingserviceoptionidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where billingserviceoptionid is less than
        defaultBillingserviceoptionvaluesFiltering(
            "billingserviceoptionid.lessThan=" + UPDATED_BILLINGSERVICEOPTIONID,
            "billingserviceoptionid.lessThan=" + DEFAULT_BILLINGSERVICEOPTIONID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByBillingserviceoptionidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where billingserviceoptionid is greater than
        defaultBillingserviceoptionvaluesFiltering(
            "billingserviceoptionid.greaterThan=" + SMALLER_BILLINGSERVICEOPTIONID,
            "billingserviceoptionid.greaterThan=" + DEFAULT_BILLINGSERVICEOPTIONID
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where value equals to
        defaultBillingserviceoptionvaluesFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where value in
        defaultBillingserviceoptionvaluesFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where value is not null
        defaultBillingserviceoptionvaluesFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where value is greater than or equal to
        defaultBillingserviceoptionvaluesFiltering(
            "value.greaterThanOrEqual=" + DEFAULT_VALUE,
            "value.greaterThanOrEqual=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where value is less than or equal to
        defaultBillingserviceoptionvaluesFiltering("value.lessThanOrEqual=" + DEFAULT_VALUE, "value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where value is less than
        defaultBillingserviceoptionvaluesFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where value is greater than
        defaultBillingserviceoptionvaluesFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmd equals to
        defaultBillingserviceoptionvaluesFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmd in
        defaultBillingserviceoptionvaluesFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmd is not null
        defaultBillingserviceoptionvaluesFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmu equals to
        defaultBillingserviceoptionvaluesFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmu in
        defaultBillingserviceoptionvaluesFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmu is not null
        defaultBillingserviceoptionvaluesFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmu is greater than or equal to
        defaultBillingserviceoptionvaluesFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmu is less than or equal to
        defaultBillingserviceoptionvaluesFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmu is less than
        defaultBillingserviceoptionvaluesFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllBillingserviceoptionvaluesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        // Get all the billingserviceoptionvaluesList where lmu is greater than
        defaultBillingserviceoptionvaluesFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    private void defaultBillingserviceoptionvaluesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBillingserviceoptionvaluesShouldBeFound(shouldBeFound);
        defaultBillingserviceoptionvaluesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBillingserviceoptionvaluesShouldBeFound(String filter) throws Exception {
        restBillingserviceoptionvaluesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingserviceoptionvalues.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicletypeid").value(hasItem(DEFAULT_VEHICLETYPEID)))
            .andExpect(jsonPath("$.[*].billingserviceoptionid").value(hasItem(DEFAULT_BILLINGSERVICEOPTIONID)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)));

        // Check, that the count call also returns 1
        restBillingserviceoptionvaluesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBillingserviceoptionvaluesShouldNotBeFound(String filter) throws Exception {
        restBillingserviceoptionvaluesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBillingserviceoptionvaluesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBillingserviceoptionvalues() throws Exception {
        // Get the billingserviceoptionvalues
        restBillingserviceoptionvaluesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBillingserviceoptionvalues() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the billingserviceoptionvalues
        Billingserviceoptionvalues updatedBillingserviceoptionvalues = billingserviceoptionvaluesRepository
            .findById(billingserviceoptionvalues.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedBillingserviceoptionvalues are not directly saved in db
        em.detach(updatedBillingserviceoptionvalues);
        updatedBillingserviceoptionvalues
            .vehicletypeid(UPDATED_VEHICLETYPEID)
            .billingserviceoptionid(UPDATED_BILLINGSERVICEOPTIONID)
            .value(UPDATED_VALUE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);

        restBillingserviceoptionvaluesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBillingserviceoptionvalues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBillingserviceoptionvalues))
            )
            .andExpect(status().isOk());

        // Validate the Billingserviceoptionvalues in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBillingserviceoptionvaluesToMatchAllProperties(updatedBillingserviceoptionvalues);
    }

    @Test
    @Transactional
    void putNonExistingBillingserviceoptionvalues() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoptionvalues.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingserviceoptionvaluesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billingserviceoptionvalues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(billingserviceoptionvalues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoptionvalues in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBillingserviceoptionvalues() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoptionvalues.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingserviceoptionvaluesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(billingserviceoptionvalues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoptionvalues in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBillingserviceoptionvalues() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoptionvalues.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingserviceoptionvaluesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(billingserviceoptionvalues)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Billingserviceoptionvalues in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBillingserviceoptionvaluesWithPatch() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the billingserviceoptionvalues using partial update
        Billingserviceoptionvalues partialUpdatedBillingserviceoptionvalues = new Billingserviceoptionvalues();
        partialUpdatedBillingserviceoptionvalues.setId(billingserviceoptionvalues.getId());

        partialUpdatedBillingserviceoptionvalues
            .vehicletypeid(UPDATED_VEHICLETYPEID)
            .billingserviceoptionid(UPDATED_BILLINGSERVICEOPTIONID)
            .value(UPDATED_VALUE)
            .lmd(UPDATED_LMD);

        restBillingserviceoptionvaluesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillingserviceoptionvalues.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBillingserviceoptionvalues))
            )
            .andExpect(status().isOk());

        // Validate the Billingserviceoptionvalues in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBillingserviceoptionvaluesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBillingserviceoptionvalues, billingserviceoptionvalues),
            getPersistedBillingserviceoptionvalues(billingserviceoptionvalues)
        );
    }

    @Test
    @Transactional
    void fullUpdateBillingserviceoptionvaluesWithPatch() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the billingserviceoptionvalues using partial update
        Billingserviceoptionvalues partialUpdatedBillingserviceoptionvalues = new Billingserviceoptionvalues();
        partialUpdatedBillingserviceoptionvalues.setId(billingserviceoptionvalues.getId());

        partialUpdatedBillingserviceoptionvalues
            .vehicletypeid(UPDATED_VEHICLETYPEID)
            .billingserviceoptionid(UPDATED_BILLINGSERVICEOPTIONID)
            .value(UPDATED_VALUE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);

        restBillingserviceoptionvaluesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillingserviceoptionvalues.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBillingserviceoptionvalues))
            )
            .andExpect(status().isOk());

        // Validate the Billingserviceoptionvalues in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBillingserviceoptionvaluesUpdatableFieldsEquals(
            partialUpdatedBillingserviceoptionvalues,
            getPersistedBillingserviceoptionvalues(partialUpdatedBillingserviceoptionvalues)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBillingserviceoptionvalues() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoptionvalues.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingserviceoptionvaluesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, billingserviceoptionvalues.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(billingserviceoptionvalues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoptionvalues in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBillingserviceoptionvalues() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoptionvalues.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingserviceoptionvaluesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(billingserviceoptionvalues))
            )
            .andExpect(status().isBadRequest());

        // Validate the Billingserviceoptionvalues in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBillingserviceoptionvalues() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        billingserviceoptionvalues.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingserviceoptionvaluesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(billingserviceoptionvalues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Billingserviceoptionvalues in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBillingserviceoptionvalues() throws Exception {
        // Initialize the database
        insertedBillingserviceoptionvalues = billingserviceoptionvaluesRepository.saveAndFlush(billingserviceoptionvalues);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the billingserviceoptionvalues
        restBillingserviceoptionvaluesMockMvc
            .perform(delete(ENTITY_API_URL_ID, billingserviceoptionvalues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return billingserviceoptionvaluesRepository.count();
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

    protected Billingserviceoptionvalues getPersistedBillingserviceoptionvalues(Billingserviceoptionvalues billingserviceoptionvalues) {
        return billingserviceoptionvaluesRepository.findById(billingserviceoptionvalues.getId()).orElseThrow();
    }

    protected void assertPersistedBillingserviceoptionvaluesToMatchAllProperties(
        Billingserviceoptionvalues expectedBillingserviceoptionvalues
    ) {
        assertBillingserviceoptionvaluesAllPropertiesEquals(
            expectedBillingserviceoptionvalues,
            getPersistedBillingserviceoptionvalues(expectedBillingserviceoptionvalues)
        );
    }

    protected void assertPersistedBillingserviceoptionvaluesToMatchUpdatableProperties(
        Billingserviceoptionvalues expectedBillingserviceoptionvalues
    ) {
        assertBillingserviceoptionvaluesAllUpdatablePropertiesEquals(
            expectedBillingserviceoptionvalues,
            getPersistedBillingserviceoptionvalues(expectedBillingserviceoptionvalues)
        );
    }
}
