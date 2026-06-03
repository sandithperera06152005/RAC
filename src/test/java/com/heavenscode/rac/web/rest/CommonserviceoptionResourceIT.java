package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.CommonserviceoptionAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Commonserviceoption;
import com.heavenscode.rac.repository.CommonserviceoptionRepository;
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
 * Integration tests for the {@link CommonserviceoptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommonserviceoptionResourceIT {

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

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final String ENTITY_API_URL = "/api/commonserviceoptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommonserviceoptionRepository commonserviceoptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommonserviceoptionMockMvc;

    private Commonserviceoption commonserviceoption;

    private Commonserviceoption insertedCommonserviceoption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commonserviceoption createEntity() {
        return new Commonserviceoption()
            .mainid(DEFAULT_MAINID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .isactive(DEFAULT_ISACTIVE)
            .lmd(DEFAULT_LMD)
            .lmu(DEFAULT_LMU);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commonserviceoption createUpdatedEntity() {
        return new Commonserviceoption()
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isactive(UPDATED_ISACTIVE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);
    }

    @BeforeEach
    public void initTest() {
        commonserviceoption = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCommonserviceoption != null) {
            commonserviceoptionRepository.delete(insertedCommonserviceoption);
            insertedCommonserviceoption = null;
        }
    }

    @Test
    @Transactional
    void createCommonserviceoption() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Commonserviceoption
        var returnedCommonserviceoption = om.readValue(
            restCommonserviceoptionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonserviceoption)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Commonserviceoption.class
        );

        // Validate the Commonserviceoption in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCommonserviceoptionUpdatableFieldsEquals(
            returnedCommonserviceoption,
            getPersistedCommonserviceoption(returnedCommonserviceoption)
        );

        insertedCommonserviceoption = returnedCommonserviceoption;
    }

    @Test
    @Transactional
    void createCommonserviceoptionWithExistingId() throws Exception {
        // Create the Commonserviceoption with an existing ID
        commonserviceoption.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommonserviceoptionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonserviceoption)))
            .andExpect(status().isBadRequest());

        // Validate the Commonserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptions() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList
        restCommonserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonserviceoption.getId().intValue())))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)));
    }

    @Test
    @Transactional
    void getCommonserviceoption() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get the commonserviceoption
        restCommonserviceoptionMockMvc
            .perform(get(ENTITY_API_URL_ID, commonserviceoption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commonserviceoption.getId().intValue()))
            .andExpect(jsonPath("$.mainid").value(DEFAULT_MAINID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU));
    }

    @Test
    @Transactional
    void getCommonserviceoptionsByIdFiltering() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        Long id = commonserviceoption.getId();

        defaultCommonserviceoptionFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCommonserviceoptionFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCommonserviceoptionFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByMainidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where mainid equals to
        defaultCommonserviceoptionFiltering("mainid.equals=" + DEFAULT_MAINID, "mainid.equals=" + UPDATED_MAINID);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByMainidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where mainid in
        defaultCommonserviceoptionFiltering("mainid.in=" + DEFAULT_MAINID + "," + UPDATED_MAINID, "mainid.in=" + UPDATED_MAINID);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByMainidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where mainid is not null
        defaultCommonserviceoptionFiltering("mainid.specified=true", "mainid.specified=false");
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByMainidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where mainid is greater than or equal to
        defaultCommonserviceoptionFiltering("mainid.greaterThanOrEqual=" + DEFAULT_MAINID, "mainid.greaterThanOrEqual=" + UPDATED_MAINID);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByMainidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where mainid is less than or equal to
        defaultCommonserviceoptionFiltering("mainid.lessThanOrEqual=" + DEFAULT_MAINID, "mainid.lessThanOrEqual=" + SMALLER_MAINID);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByMainidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where mainid is less than
        defaultCommonserviceoptionFiltering("mainid.lessThan=" + UPDATED_MAINID, "mainid.lessThan=" + DEFAULT_MAINID);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByMainidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where mainid is greater than
        defaultCommonserviceoptionFiltering("mainid.greaterThan=" + SMALLER_MAINID, "mainid.greaterThan=" + DEFAULT_MAINID);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where code equals to
        defaultCommonserviceoptionFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where code in
        defaultCommonserviceoptionFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where code is not null
        defaultCommonserviceoptionFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where code contains
        defaultCommonserviceoptionFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where code does not contain
        defaultCommonserviceoptionFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where name equals to
        defaultCommonserviceoptionFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where name in
        defaultCommonserviceoptionFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where name is not null
        defaultCommonserviceoptionFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where name contains
        defaultCommonserviceoptionFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where name does not contain
        defaultCommonserviceoptionFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where description equals to
        defaultCommonserviceoptionFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where description in
        defaultCommonserviceoptionFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where description is not null
        defaultCommonserviceoptionFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where description contains
        defaultCommonserviceoptionFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where description does not contain
        defaultCommonserviceoptionFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where value equals to
        defaultCommonserviceoptionFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where value in
        defaultCommonserviceoptionFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where value is not null
        defaultCommonserviceoptionFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where value is greater than or equal to
        defaultCommonserviceoptionFiltering("value.greaterThanOrEqual=" + DEFAULT_VALUE, "value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where value is less than or equal to
        defaultCommonserviceoptionFiltering("value.lessThanOrEqual=" + DEFAULT_VALUE, "value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where value is less than
        defaultCommonserviceoptionFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where value is greater than
        defaultCommonserviceoptionFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where isactive equals to
        defaultCommonserviceoptionFiltering("isactive.equals=" + DEFAULT_ISACTIVE, "isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where isactive in
        defaultCommonserviceoptionFiltering("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE, "isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where isactive is not null
        defaultCommonserviceoptionFiltering("isactive.specified=true", "isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmd equals to
        defaultCommonserviceoptionFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmd in
        defaultCommonserviceoptionFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmd is not null
        defaultCommonserviceoptionFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmu equals to
        defaultCommonserviceoptionFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmu in
        defaultCommonserviceoptionFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmu is not null
        defaultCommonserviceoptionFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmu is greater than or equal to
        defaultCommonserviceoptionFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmu is less than or equal to
        defaultCommonserviceoptionFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmu is less than
        defaultCommonserviceoptionFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllCommonserviceoptionsByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        // Get all the commonserviceoptionList where lmu is greater than
        defaultCommonserviceoptionFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    private void defaultCommonserviceoptionFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCommonserviceoptionShouldBeFound(shouldBeFound);
        defaultCommonserviceoptionShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommonserviceoptionShouldBeFound(String filter) throws Exception {
        restCommonserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonserviceoption.getId().intValue())))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)));

        // Check, that the count call also returns 1
        restCommonserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommonserviceoptionShouldNotBeFound(String filter) throws Exception {
        restCommonserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommonserviceoptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCommonserviceoption() throws Exception {
        // Get the commonserviceoption
        restCommonserviceoptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommonserviceoption() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commonserviceoption
        Commonserviceoption updatedCommonserviceoption = commonserviceoptionRepository.findById(commonserviceoption.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCommonserviceoption are not directly saved in db
        em.detach(updatedCommonserviceoption);
        updatedCommonserviceoption
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isactive(UPDATED_ISACTIVE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);

        restCommonserviceoptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommonserviceoption.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCommonserviceoption))
            )
            .andExpect(status().isOk());

        // Validate the Commonserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCommonserviceoptionToMatchAllProperties(updatedCommonserviceoption);
    }

    @Test
    @Transactional
    void putNonExistingCommonserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonserviceoption.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonserviceoptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commonserviceoption.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commonserviceoption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commonserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommonserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonserviceoption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommonserviceoptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commonserviceoption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commonserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommonserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonserviceoption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommonserviceoptionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonserviceoption)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commonserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommonserviceoptionWithPatch() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commonserviceoption using partial update
        Commonserviceoption partialUpdatedCommonserviceoption = new Commonserviceoption();
        partialUpdatedCommonserviceoption.setId(commonserviceoption.getId());

        partialUpdatedCommonserviceoption
            .mainid(UPDATED_MAINID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);

        restCommonserviceoptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommonserviceoption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommonserviceoption))
            )
            .andExpect(status().isOk());

        // Validate the Commonserviceoption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommonserviceoptionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCommonserviceoption, commonserviceoption),
            getPersistedCommonserviceoption(commonserviceoption)
        );
    }

    @Test
    @Transactional
    void fullUpdateCommonserviceoptionWithPatch() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commonserviceoption using partial update
        Commonserviceoption partialUpdatedCommonserviceoption = new Commonserviceoption();
        partialUpdatedCommonserviceoption.setId(commonserviceoption.getId());

        partialUpdatedCommonserviceoption
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isactive(UPDATED_ISACTIVE)
            .lmd(UPDATED_LMD)
            .lmu(UPDATED_LMU);

        restCommonserviceoptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommonserviceoption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommonserviceoption))
            )
            .andExpect(status().isOk());

        // Validate the Commonserviceoption in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommonserviceoptionUpdatableFieldsEquals(
            partialUpdatedCommonserviceoption,
            getPersistedCommonserviceoption(partialUpdatedCommonserviceoption)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCommonserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonserviceoption.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonserviceoptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commonserviceoption.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commonserviceoption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commonserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommonserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonserviceoption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommonserviceoptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commonserviceoption))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commonserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommonserviceoption() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonserviceoption.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommonserviceoptionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(commonserviceoption)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commonserviceoption in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommonserviceoption() throws Exception {
        // Initialize the database
        insertedCommonserviceoption = commonserviceoptionRepository.saveAndFlush(commonserviceoption);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the commonserviceoption
        restCommonserviceoptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, commonserviceoption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return commonserviceoptionRepository.count();
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

    protected Commonserviceoption getPersistedCommonserviceoption(Commonserviceoption commonserviceoption) {
        return commonserviceoptionRepository.findById(commonserviceoption.getId()).orElseThrow();
    }

    protected void assertPersistedCommonserviceoptionToMatchAllProperties(Commonserviceoption expectedCommonserviceoption) {
        assertCommonserviceoptionAllPropertiesEquals(
            expectedCommonserviceoption,
            getPersistedCommonserviceoption(expectedCommonserviceoption)
        );
    }

    protected void assertPersistedCommonserviceoptionToMatchUpdatableProperties(Commonserviceoption expectedCommonserviceoption) {
        assertCommonserviceoptionAllUpdatablePropertiesEquals(
            expectedCommonserviceoption,
            getPersistedCommonserviceoption(expectedCommonserviceoption)
        );
    }
}
