package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.ServicesubcategoryAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Servicesubcategory;
import com.heavenscode.rac.repository.ServicesubcategoryRepository;
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
 * Integration tests for the {@link ServicesubcategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServicesubcategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAINID = 1;
    private static final Integer UPDATED_MAINID = 2;
    private static final Integer SMALLER_MAINID = 1 - 1;

    private static final String DEFAULT_MAINNAME = "AAAAAAAAAA";
    private static final String UPDATED_MAINNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final String ENTITY_API_URL = "/api/servicesubcategories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicesubcategoryRepository servicesubcategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicesubcategoryMockMvc;

    private Servicesubcategory servicesubcategory;

    private Servicesubcategory insertedServicesubcategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicesubcategory createEntity() {
        return new Servicesubcategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .mainid(DEFAULT_MAINID)
            .mainname(DEFAULT_MAINNAME)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .isactive(DEFAULT_ISACTIVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicesubcategory createUpdatedEntity() {
        return new Servicesubcategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mainid(UPDATED_MAINID)
            .mainname(UPDATED_MAINNAME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .isactive(UPDATED_ISACTIVE);
    }

    @BeforeEach
    public void initTest() {
        servicesubcategory = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicesubcategory != null) {
            servicesubcategoryRepository.delete(insertedServicesubcategory);
            insertedServicesubcategory = null;
        }
    }

    @Test
    @Transactional
    void createServicesubcategory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Servicesubcategory
        var returnedServicesubcategory = om.readValue(
            restServicesubcategoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicesubcategory)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Servicesubcategory.class
        );

        // Validate the Servicesubcategory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServicesubcategoryUpdatableFieldsEquals(
            returnedServicesubcategory,
            getPersistedServicesubcategory(returnedServicesubcategory)
        );

        insertedServicesubcategory = returnedServicesubcategory;
    }

    @Test
    @Transactional
    void createServicesubcategoryWithExistingId() throws Exception {
        // Create the Servicesubcategory with an existing ID
        servicesubcategory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicesubcategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicesubcategory)))
            .andExpect(status().isBadRequest());

        // Validate the Servicesubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicesubcategories() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList
        restServicesubcategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicesubcategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].mainname").value(hasItem(DEFAULT_MAINNAME)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getServicesubcategory() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get the servicesubcategory
        restServicesubcategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, servicesubcategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicesubcategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.mainid").value(DEFAULT_MAINID))
            .andExpect(jsonPath("$.mainname").value(DEFAULT_MAINNAME))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getServicesubcategoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        Long id = servicesubcategory.getId();

        defaultServicesubcategoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultServicesubcategoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultServicesubcategoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where name equals to
        defaultServicesubcategoryFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where name in
        defaultServicesubcategoryFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where name is not null
        defaultServicesubcategoryFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where name contains
        defaultServicesubcategoryFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where name does not contain
        defaultServicesubcategoryFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where description equals to
        defaultServicesubcategoryFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where description in
        defaultServicesubcategoryFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where description is not null
        defaultServicesubcategoryFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where description contains
        defaultServicesubcategoryFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where description does not contain
        defaultServicesubcategoryFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainid equals to
        defaultServicesubcategoryFiltering("mainid.equals=" + DEFAULT_MAINID, "mainid.equals=" + UPDATED_MAINID);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainid in
        defaultServicesubcategoryFiltering("mainid.in=" + DEFAULT_MAINID + "," + UPDATED_MAINID, "mainid.in=" + UPDATED_MAINID);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainid is not null
        defaultServicesubcategoryFiltering("mainid.specified=true", "mainid.specified=false");
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainid is greater than or equal to
        defaultServicesubcategoryFiltering("mainid.greaterThanOrEqual=" + DEFAULT_MAINID, "mainid.greaterThanOrEqual=" + UPDATED_MAINID);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainid is less than or equal to
        defaultServicesubcategoryFiltering("mainid.lessThanOrEqual=" + DEFAULT_MAINID, "mainid.lessThanOrEqual=" + SMALLER_MAINID);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainid is less than
        defaultServicesubcategoryFiltering("mainid.lessThan=" + UPDATED_MAINID, "mainid.lessThan=" + DEFAULT_MAINID);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainid is greater than
        defaultServicesubcategoryFiltering("mainid.greaterThan=" + SMALLER_MAINID, "mainid.greaterThan=" + DEFAULT_MAINID);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainnameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainname equals to
        defaultServicesubcategoryFiltering("mainname.equals=" + DEFAULT_MAINNAME, "mainname.equals=" + UPDATED_MAINNAME);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainnameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainname in
        defaultServicesubcategoryFiltering("mainname.in=" + DEFAULT_MAINNAME + "," + UPDATED_MAINNAME, "mainname.in=" + UPDATED_MAINNAME);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainname is not null
        defaultServicesubcategoryFiltering("mainname.specified=true", "mainname.specified=false");
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainnameContainsSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainname contains
        defaultServicesubcategoryFiltering("mainname.contains=" + DEFAULT_MAINNAME, "mainname.contains=" + UPDATED_MAINNAME);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByMainnameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where mainname does not contain
        defaultServicesubcategoryFiltering("mainname.doesNotContain=" + UPDATED_MAINNAME, "mainname.doesNotContain=" + DEFAULT_MAINNAME);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmu equals to
        defaultServicesubcategoryFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmu in
        defaultServicesubcategoryFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmu is not null
        defaultServicesubcategoryFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmu is greater than or equal to
        defaultServicesubcategoryFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmu is less than or equal to
        defaultServicesubcategoryFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmu is less than
        defaultServicesubcategoryFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmu is greater than
        defaultServicesubcategoryFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmd equals to
        defaultServicesubcategoryFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmd in
        defaultServicesubcategoryFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where lmd is not null
        defaultServicesubcategoryFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where isactive equals to
        defaultServicesubcategoryFiltering("isactive.equals=" + DEFAULT_ISACTIVE, "isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where isactive in
        defaultServicesubcategoryFiltering("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE, "isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllServicesubcategoriesByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        // Get all the servicesubcategoryList where isactive is not null
        defaultServicesubcategoryFiltering("isactive.specified=true", "isactive.specified=false");
    }

    private void defaultServicesubcategoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultServicesubcategoryShouldBeFound(shouldBeFound);
        defaultServicesubcategoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServicesubcategoryShouldBeFound(String filter) throws Exception {
        restServicesubcategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicesubcategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].mainname").value(hasItem(DEFAULT_MAINNAME)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restServicesubcategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServicesubcategoryShouldNotBeFound(String filter) throws Exception {
        restServicesubcategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServicesubcategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServicesubcategory() throws Exception {
        // Get the servicesubcategory
        restServicesubcategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicesubcategory() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicesubcategory
        Servicesubcategory updatedServicesubcategory = servicesubcategoryRepository.findById(servicesubcategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServicesubcategory are not directly saved in db
        em.detach(updatedServicesubcategory);
        updatedServicesubcategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mainid(UPDATED_MAINID)
            .mainname(UPDATED_MAINNAME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .isactive(UPDATED_ISACTIVE);

        restServicesubcategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicesubcategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServicesubcategory))
            )
            .andExpect(status().isOk());

        // Validate the Servicesubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicesubcategoryToMatchAllProperties(updatedServicesubcategory);
    }

    @Test
    @Transactional
    void putNonExistingServicesubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicesubcategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesubcategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicesubcategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicesubcategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicesubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicesubcategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesubcategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicesubcategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicesubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicesubcategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesubcategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicesubcategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicesubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicesubcategoryWithPatch() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicesubcategory using partial update
        Servicesubcategory partialUpdatedServicesubcategory = new Servicesubcategory();
        partialUpdatedServicesubcategory.setId(servicesubcategory.getId());

        partialUpdatedServicesubcategory
            .name(UPDATED_NAME)
            .mainid(UPDATED_MAINID)
            .mainname(UPDATED_MAINNAME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .isactive(UPDATED_ISACTIVE);

        restServicesubcategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicesubcategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicesubcategory))
            )
            .andExpect(status().isOk());

        // Validate the Servicesubcategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicesubcategoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicesubcategory, servicesubcategory),
            getPersistedServicesubcategory(servicesubcategory)
        );
    }

    @Test
    @Transactional
    void fullUpdateServicesubcategoryWithPatch() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicesubcategory using partial update
        Servicesubcategory partialUpdatedServicesubcategory = new Servicesubcategory();
        partialUpdatedServicesubcategory.setId(servicesubcategory.getId());

        partialUpdatedServicesubcategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .mainid(UPDATED_MAINID)
            .mainname(UPDATED_MAINNAME)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .isactive(UPDATED_ISACTIVE);

        restServicesubcategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicesubcategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicesubcategory))
            )
            .andExpect(status().isOk());

        // Validate the Servicesubcategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicesubcategoryUpdatableFieldsEquals(
            partialUpdatedServicesubcategory,
            getPersistedServicesubcategory(partialUpdatedServicesubcategory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServicesubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicesubcategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesubcategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicesubcategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicesubcategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicesubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicesubcategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesubcategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicesubcategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicesubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicesubcategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesubcategoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(servicesubcategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicesubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicesubcategory() throws Exception {
        // Initialize the database
        insertedServicesubcategory = servicesubcategoryRepository.saveAndFlush(servicesubcategory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicesubcategory
        restServicesubcategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicesubcategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicesubcategoryRepository.count();
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

    protected Servicesubcategory getPersistedServicesubcategory(Servicesubcategory servicesubcategory) {
        return servicesubcategoryRepository.findById(servicesubcategory.getId()).orElseThrow();
    }

    protected void assertPersistedServicesubcategoryToMatchAllProperties(Servicesubcategory expectedServicesubcategory) {
        assertServicesubcategoryAllPropertiesEquals(expectedServicesubcategory, getPersistedServicesubcategory(expectedServicesubcategory));
    }

    protected void assertPersistedServicesubcategoryToMatchUpdatableProperties(Servicesubcategory expectedServicesubcategory) {
        assertServicesubcategoryAllUpdatablePropertiesEquals(
            expectedServicesubcategory,
            getPersistedServicesubcategory(expectedServicesubcategory)
        );
    }
}
