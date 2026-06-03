package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.ServicecategoryAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Servicecategory;
import com.heavenscode.rac.repository.ServicecategoryRepository;
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
 * Integration tests for the {@link ServicecategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServicecategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_SHOWSECURITY = false;
    private static final Boolean UPDATED_SHOWSECURITY = true;

    private static final Integer DEFAULT_SORTORDER = 1;
    private static final Integer UPDATED_SORTORDER = 2;
    private static final Integer SMALLER_SORTORDER = 1 - 1;

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final String ENTITY_API_URL = "/api/servicecategories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicecategoryRepository servicecategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicecategoryMockMvc;

    private Servicecategory servicecategory;

    private Servicecategory insertedServicecategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicecategory createEntity() {
        return new Servicecategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .showsecurity(DEFAULT_SHOWSECURITY)
            .sortorder(DEFAULT_SORTORDER)
            .isactive(DEFAULT_ISACTIVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicecategory createUpdatedEntity() {
        return new Servicecategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .showsecurity(UPDATED_SHOWSECURITY)
            .sortorder(UPDATED_SORTORDER)
            .isactive(UPDATED_ISACTIVE);
    }

    @BeforeEach
    public void initTest() {
        servicecategory = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicecategory != null) {
            servicecategoryRepository.delete(insertedServicecategory);
            insertedServicecategory = null;
        }
    }

    @Test
    @Transactional
    void createServicecategory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Servicecategory
        var returnedServicecategory = om.readValue(
            restServicecategoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicecategory)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Servicecategory.class
        );

        // Validate the Servicecategory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServicecategoryUpdatableFieldsEquals(returnedServicecategory, getPersistedServicecategory(returnedServicecategory));

        insertedServicecategory = returnedServicecategory;
    }

    @Test
    @Transactional
    void createServicecategoryWithExistingId() throws Exception {
        // Create the Servicecategory with an existing ID
        servicecategory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicecategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicecategory)))
            .andExpect(status().isBadRequest());

        // Validate the Servicecategory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicecategories() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList
        restServicecategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicecategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].showsecurity").value(hasItem(DEFAULT_SHOWSECURITY.booleanValue())))
            .andExpect(jsonPath("$.[*].sortorder").value(hasItem(DEFAULT_SORTORDER)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getServicecategory() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get the servicecategory
        restServicecategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, servicecategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicecategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.showsecurity").value(DEFAULT_SHOWSECURITY.booleanValue()))
            .andExpect(jsonPath("$.sortorder").value(DEFAULT_SORTORDER))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getServicecategoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        Long id = servicecategory.getId();

        defaultServicecategoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultServicecategoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultServicecategoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where name equals to
        defaultServicecategoryFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where name in
        defaultServicecategoryFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where name is not null
        defaultServicecategoryFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllServicecategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where name contains
        defaultServicecategoryFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where name does not contain
        defaultServicecategoryFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where description equals to
        defaultServicecategoryFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where description in
        defaultServicecategoryFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllServicecategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where description is not null
        defaultServicecategoryFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllServicecategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where description contains
        defaultServicecategoryFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where description does not contain
        defaultServicecategoryFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmu equals to
        defaultServicecategoryFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmu in
        defaultServicecategoryFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmu is not null
        defaultServicecategoryFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmu is greater than or equal to
        defaultServicecategoryFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmu is less than or equal to
        defaultServicecategoryFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmu is less than
        defaultServicecategoryFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmu is greater than
        defaultServicecategoryFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmd equals to
        defaultServicecategoryFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmd in
        defaultServicecategoryFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where lmd is not null
        defaultServicecategoryFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllServicecategoriesByShowsecurityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where showsecurity equals to
        defaultServicecategoryFiltering("showsecurity.equals=" + DEFAULT_SHOWSECURITY, "showsecurity.equals=" + UPDATED_SHOWSECURITY);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByShowsecurityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where showsecurity in
        defaultServicecategoryFiltering(
            "showsecurity.in=" + DEFAULT_SHOWSECURITY + "," + UPDATED_SHOWSECURITY,
            "showsecurity.in=" + UPDATED_SHOWSECURITY
        );
    }

    @Test
    @Transactional
    void getAllServicecategoriesByShowsecurityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where showsecurity is not null
        defaultServicecategoryFiltering("showsecurity.specified=true", "showsecurity.specified=false");
    }

    @Test
    @Transactional
    void getAllServicecategoriesBySortorderIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where sortorder equals to
        defaultServicecategoryFiltering("sortorder.equals=" + DEFAULT_SORTORDER, "sortorder.equals=" + UPDATED_SORTORDER);
    }

    @Test
    @Transactional
    void getAllServicecategoriesBySortorderIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where sortorder in
        defaultServicecategoryFiltering("sortorder.in=" + DEFAULT_SORTORDER + "," + UPDATED_SORTORDER, "sortorder.in=" + UPDATED_SORTORDER);
    }

    @Test
    @Transactional
    void getAllServicecategoriesBySortorderIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where sortorder is not null
        defaultServicecategoryFiltering("sortorder.specified=true", "sortorder.specified=false");
    }

    @Test
    @Transactional
    void getAllServicecategoriesBySortorderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where sortorder is greater than or equal to
        defaultServicecategoryFiltering(
            "sortorder.greaterThanOrEqual=" + DEFAULT_SORTORDER,
            "sortorder.greaterThanOrEqual=" + UPDATED_SORTORDER
        );
    }

    @Test
    @Transactional
    void getAllServicecategoriesBySortorderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where sortorder is less than or equal to
        defaultServicecategoryFiltering("sortorder.lessThanOrEqual=" + DEFAULT_SORTORDER, "sortorder.lessThanOrEqual=" + SMALLER_SORTORDER);
    }

    @Test
    @Transactional
    void getAllServicecategoriesBySortorderIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where sortorder is less than
        defaultServicecategoryFiltering("sortorder.lessThan=" + UPDATED_SORTORDER, "sortorder.lessThan=" + DEFAULT_SORTORDER);
    }

    @Test
    @Transactional
    void getAllServicecategoriesBySortorderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where sortorder is greater than
        defaultServicecategoryFiltering("sortorder.greaterThan=" + SMALLER_SORTORDER, "sortorder.greaterThan=" + DEFAULT_SORTORDER);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where isactive equals to
        defaultServicecategoryFiltering("isactive.equals=" + DEFAULT_ISACTIVE, "isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where isactive in
        defaultServicecategoryFiltering("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE, "isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllServicecategoriesByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        // Get all the servicecategoryList where isactive is not null
        defaultServicecategoryFiltering("isactive.specified=true", "isactive.specified=false");
    }

    private void defaultServicecategoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultServicecategoryShouldBeFound(shouldBeFound);
        defaultServicecategoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServicecategoryShouldBeFound(String filter) throws Exception {
        restServicecategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicecategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].showsecurity").value(hasItem(DEFAULT_SHOWSECURITY.booleanValue())))
            .andExpect(jsonPath("$.[*].sortorder").value(hasItem(DEFAULT_SORTORDER)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restServicecategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServicecategoryShouldNotBeFound(String filter) throws Exception {
        restServicecategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServicecategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingServicecategory() throws Exception {
        // Get the servicecategory
        restServicecategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicecategory() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicecategory
        Servicecategory updatedServicecategory = servicecategoryRepository.findById(servicecategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServicecategory are not directly saved in db
        em.detach(updatedServicecategory);
        updatedServicecategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .showsecurity(UPDATED_SHOWSECURITY)
            .sortorder(UPDATED_SORTORDER)
            .isactive(UPDATED_ISACTIVE);

        restServicecategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicecategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServicecategory))
            )
            .andExpect(status().isOk());

        // Validate the Servicecategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicecategoryToMatchAllProperties(updatedServicecategory);
    }

    @Test
    @Transactional
    void putNonExistingServicecategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicecategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicecategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicecategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicecategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicecategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicecategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicecategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicecategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicecategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicecategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicecategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicecategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicecategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicecategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicecategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicecategoryWithPatch() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicecategory using partial update
        Servicecategory partialUpdatedServicecategory = new Servicecategory();
        partialUpdatedServicecategory.setId(servicecategory.getId());

        partialUpdatedServicecategory.description(UPDATED_DESCRIPTION).lmu(UPDATED_LMU).isactive(UPDATED_ISACTIVE);

        restServicecategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicecategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicecategory))
            )
            .andExpect(status().isOk());

        // Validate the Servicecategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicecategoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicecategory, servicecategory),
            getPersistedServicecategory(servicecategory)
        );
    }

    @Test
    @Transactional
    void fullUpdateServicecategoryWithPatch() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicecategory using partial update
        Servicecategory partialUpdatedServicecategory = new Servicecategory();
        partialUpdatedServicecategory.setId(servicecategory.getId());

        partialUpdatedServicecategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .showsecurity(UPDATED_SHOWSECURITY)
            .sortorder(UPDATED_SORTORDER)
            .isactive(UPDATED_ISACTIVE);

        restServicecategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicecategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicecategory))
            )
            .andExpect(status().isOk());

        // Validate the Servicecategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicecategoryUpdatableFieldsEquals(
            partialUpdatedServicecategory,
            getPersistedServicecategory(partialUpdatedServicecategory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServicecategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicecategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicecategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicecategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicecategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicecategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicecategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicecategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicecategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicecategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicecategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicecategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicecategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicecategoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(servicecategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicecategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicecategory() throws Exception {
        // Initialize the database
        insertedServicecategory = servicecategoryRepository.saveAndFlush(servicecategory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicecategory
        restServicecategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicecategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicecategoryRepository.count();
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

    protected Servicecategory getPersistedServicecategory(Servicecategory servicecategory) {
        return servicecategoryRepository.findById(servicecategory.getId()).orElseThrow();
    }

    protected void assertPersistedServicecategoryToMatchAllProperties(Servicecategory expectedServicecategory) {
        assertServicecategoryAllPropertiesEquals(expectedServicecategory, getPersistedServicecategory(expectedServicecategory));
    }

    protected void assertPersistedServicecategoryToMatchUpdatableProperties(Servicecategory expectedServicecategory) {
        assertServicecategoryAllUpdatablePropertiesEquals(expectedServicecategory, getPersistedServicecategory(expectedServicecategory));
    }
}
