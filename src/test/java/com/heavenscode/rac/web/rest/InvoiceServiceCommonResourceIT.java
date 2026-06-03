package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.InvoiceServiceCommonAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.InvoiceServiceCommon;
import com.heavenscode.rac.repository.InvoiceServiceCommonRepository;
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
 * Integration tests for the {@link InvoiceServiceCommonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InvoiceServiceCommonResourceIT {

    private static final Long DEFAULT_INVOICE_ID = 1L;
    private static final Long UPDATED_INVOICE_ID = 2L;
    private static final Long SMALLER_INVOICE_ID = 1L - 1L;

    private static final Long DEFAULT_LINE_ID = 1L;
    private static final Long UPDATED_LINE_ID = 2L;
    private static final Long SMALLER_LINE_ID = 1L - 1L;

    private static final Long DEFAULT_OPTION_ID = 1L;
    private static final Long UPDATED_OPTION_ID = 2L;
    private static final Long SMALLER_OPTION_ID = 1L - 1L;

    private static final Long DEFAULT_MAIN_ID = 1L;
    private static final Long UPDATED_MAIN_ID = 2L;
    private static final Long SMALLER_MAIN_ID = 1L - 1L;

    private static final Double DEFAULT_CODE = 1D;
    private static final Double UPDATED_CODE = 2D;
    private static final Double SMALLER_CODE = 1D - 1D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;
    private static final Double SMALLER_VALUE = 1D - 1D;

    private static final Long DEFAULT_ADDED_BY_ID = 1L;
    private static final Long UPDATED_ADDED_BY_ID = 2L;
    private static final Long SMALLER_ADDED_BY_ID = 1L - 1L;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;
    private static final Double SMALLER_DISCOUNT = 1D - 1D;

    private static final Double DEFAULT_SERVICE_PRICE = 1D;
    private static final Double UPDATED_SERVICE_PRICE = 2D;
    private static final Double SMALLER_SERVICE_PRICE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/invoice-service-commons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InvoiceServiceCommonRepository invoiceServiceCommonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceServiceCommonMockMvc;

    private InvoiceServiceCommon invoiceServiceCommon;

    private InvoiceServiceCommon insertedInvoiceServiceCommon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceServiceCommon createEntity() {
        return new InvoiceServiceCommon()
            .invoiceId(DEFAULT_INVOICE_ID)
            .lineId(DEFAULT_LINE_ID)
            .optionId(DEFAULT_OPTION_ID)
            .mainId(DEFAULT_MAIN_ID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .addedById(DEFAULT_ADDED_BY_ID)
            .discount(DEFAULT_DISCOUNT)
            .servicePrice(DEFAULT_SERVICE_PRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceServiceCommon createUpdatedEntity() {
        return new InvoiceServiceCommon()
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .mainId(UPDATED_MAIN_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedById(UPDATED_ADDED_BY_ID)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);
    }

    @BeforeEach
    public void initTest() {
        invoiceServiceCommon = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedInvoiceServiceCommon != null) {
            invoiceServiceCommonRepository.delete(insertedInvoiceServiceCommon);
            insertedInvoiceServiceCommon = null;
        }
    }

    @Test
    @Transactional
    void createInvoiceServiceCommon() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InvoiceServiceCommon
        var returnedInvoiceServiceCommon = om.readValue(
            restInvoiceServiceCommonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceServiceCommon)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InvoiceServiceCommon.class
        );

        // Validate the InvoiceServiceCommon in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInvoiceServiceCommonUpdatableFieldsEquals(
            returnedInvoiceServiceCommon,
            getPersistedInvoiceServiceCommon(returnedInvoiceServiceCommon)
        );

        insertedInvoiceServiceCommon = returnedInvoiceServiceCommon;
    }

    @Test
    @Transactional
    void createInvoiceServiceCommonWithExistingId() throws Exception {
        // Create the InvoiceServiceCommon with an existing ID
        invoiceServiceCommon.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceServiceCommonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceServiceCommon)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCommon in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommons() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList
        restInvoiceServiceCommonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceServiceCommon.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].mainId").value(hasItem(DEFAULT_MAIN_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedById").value(hasItem(DEFAULT_ADDED_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getInvoiceServiceCommon() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get the invoiceServiceCommon
        restInvoiceServiceCommonMockMvc
            .perform(get(ENTITY_API_URL_ID, invoiceServiceCommon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceServiceCommon.getId().intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID.intValue()))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID.intValue()))
            .andExpect(jsonPath("$.optionId").value(DEFAULT_OPTION_ID.intValue()))
            .andExpect(jsonPath("$.mainId").value(DEFAULT_MAIN_ID.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.addedById").value(DEFAULT_ADDED_BY_ID.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.servicePrice").value(DEFAULT_SERVICE_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getInvoiceServiceCommonsByIdFiltering() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        Long id = invoiceServiceCommon.getId();

        defaultInvoiceServiceCommonFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultInvoiceServiceCommonFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultInvoiceServiceCommonFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByInvoiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where invoiceId equals to
        defaultInvoiceServiceCommonFiltering("invoiceId.equals=" + DEFAULT_INVOICE_ID, "invoiceId.equals=" + UPDATED_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByInvoiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where invoiceId in
        defaultInvoiceServiceCommonFiltering(
            "invoiceId.in=" + DEFAULT_INVOICE_ID + "," + UPDATED_INVOICE_ID,
            "invoiceId.in=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByInvoiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where invoiceId is not null
        defaultInvoiceServiceCommonFiltering("invoiceId.specified=true", "invoiceId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByInvoiceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where invoiceId is greater than or equal to
        defaultInvoiceServiceCommonFiltering(
            "invoiceId.greaterThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.greaterThanOrEqual=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByInvoiceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where invoiceId is less than or equal to
        defaultInvoiceServiceCommonFiltering(
            "invoiceId.lessThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.lessThanOrEqual=" + SMALLER_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByInvoiceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where invoiceId is less than
        defaultInvoiceServiceCommonFiltering("invoiceId.lessThan=" + UPDATED_INVOICE_ID, "invoiceId.lessThan=" + DEFAULT_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByInvoiceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where invoiceId is greater than
        defaultInvoiceServiceCommonFiltering("invoiceId.greaterThan=" + SMALLER_INVOICE_ID, "invoiceId.greaterThan=" + DEFAULT_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where lineId equals to
        defaultInvoiceServiceCommonFiltering("lineId.equals=" + DEFAULT_LINE_ID, "lineId.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where lineId in
        defaultInvoiceServiceCommonFiltering("lineId.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID, "lineId.in=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where lineId is not null
        defaultInvoiceServiceCommonFiltering("lineId.specified=true", "lineId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where lineId is greater than or equal to
        defaultInvoiceServiceCommonFiltering(
            "lineId.greaterThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.greaterThanOrEqual=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where lineId is less than or equal to
        defaultInvoiceServiceCommonFiltering("lineId.lessThanOrEqual=" + DEFAULT_LINE_ID, "lineId.lessThanOrEqual=" + SMALLER_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where lineId is less than
        defaultInvoiceServiceCommonFiltering("lineId.lessThan=" + UPDATED_LINE_ID, "lineId.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where lineId is greater than
        defaultInvoiceServiceCommonFiltering("lineId.greaterThan=" + SMALLER_LINE_ID, "lineId.greaterThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByOptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where optionId equals to
        defaultInvoiceServiceCommonFiltering("optionId.equals=" + DEFAULT_OPTION_ID, "optionId.equals=" + UPDATED_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByOptionIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where optionId in
        defaultInvoiceServiceCommonFiltering(
            "optionId.in=" + DEFAULT_OPTION_ID + "," + UPDATED_OPTION_ID,
            "optionId.in=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByOptionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where optionId is not null
        defaultInvoiceServiceCommonFiltering("optionId.specified=true", "optionId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByOptionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where optionId is greater than or equal to
        defaultInvoiceServiceCommonFiltering(
            "optionId.greaterThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.greaterThanOrEqual=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByOptionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where optionId is less than or equal to
        defaultInvoiceServiceCommonFiltering(
            "optionId.lessThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.lessThanOrEqual=" + SMALLER_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByOptionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where optionId is less than
        defaultInvoiceServiceCommonFiltering("optionId.lessThan=" + UPDATED_OPTION_ID, "optionId.lessThan=" + DEFAULT_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByOptionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where optionId is greater than
        defaultInvoiceServiceCommonFiltering("optionId.greaterThan=" + SMALLER_OPTION_ID, "optionId.greaterThan=" + DEFAULT_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByMainIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where mainId equals to
        defaultInvoiceServiceCommonFiltering("mainId.equals=" + DEFAULT_MAIN_ID, "mainId.equals=" + UPDATED_MAIN_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByMainIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where mainId in
        defaultInvoiceServiceCommonFiltering("mainId.in=" + DEFAULT_MAIN_ID + "," + UPDATED_MAIN_ID, "mainId.in=" + UPDATED_MAIN_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByMainIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where mainId is not null
        defaultInvoiceServiceCommonFiltering("mainId.specified=true", "mainId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByMainIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where mainId is greater than or equal to
        defaultInvoiceServiceCommonFiltering(
            "mainId.greaterThanOrEqual=" + DEFAULT_MAIN_ID,
            "mainId.greaterThanOrEqual=" + UPDATED_MAIN_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByMainIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where mainId is less than or equal to
        defaultInvoiceServiceCommonFiltering("mainId.lessThanOrEqual=" + DEFAULT_MAIN_ID, "mainId.lessThanOrEqual=" + SMALLER_MAIN_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByMainIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where mainId is less than
        defaultInvoiceServiceCommonFiltering("mainId.lessThan=" + UPDATED_MAIN_ID, "mainId.lessThan=" + DEFAULT_MAIN_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByMainIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where mainId is greater than
        defaultInvoiceServiceCommonFiltering("mainId.greaterThan=" + SMALLER_MAIN_ID, "mainId.greaterThan=" + DEFAULT_MAIN_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where code equals to
        defaultInvoiceServiceCommonFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where code in
        defaultInvoiceServiceCommonFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where code is not null
        defaultInvoiceServiceCommonFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where code is greater than or equal to
        defaultInvoiceServiceCommonFiltering("code.greaterThanOrEqual=" + DEFAULT_CODE, "code.greaterThanOrEqual=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where code is less than or equal to
        defaultInvoiceServiceCommonFiltering("code.lessThanOrEqual=" + DEFAULT_CODE, "code.lessThanOrEqual=" + SMALLER_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where code is less than
        defaultInvoiceServiceCommonFiltering("code.lessThan=" + UPDATED_CODE, "code.lessThan=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where code is greater than
        defaultInvoiceServiceCommonFiltering("code.greaterThan=" + SMALLER_CODE, "code.greaterThan=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where name equals to
        defaultInvoiceServiceCommonFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where name in
        defaultInvoiceServiceCommonFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where name is not null
        defaultInvoiceServiceCommonFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where name contains
        defaultInvoiceServiceCommonFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where name does not contain
        defaultInvoiceServiceCommonFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where description equals to
        defaultInvoiceServiceCommonFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where description in
        defaultInvoiceServiceCommonFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where description is not null
        defaultInvoiceServiceCommonFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where description contains
        defaultInvoiceServiceCommonFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where description does not contain
        defaultInvoiceServiceCommonFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where value equals to
        defaultInvoiceServiceCommonFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where value in
        defaultInvoiceServiceCommonFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where value is not null
        defaultInvoiceServiceCommonFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where value is greater than or equal to
        defaultInvoiceServiceCommonFiltering("value.greaterThanOrEqual=" + DEFAULT_VALUE, "value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where value is less than or equal to
        defaultInvoiceServiceCommonFiltering("value.lessThanOrEqual=" + DEFAULT_VALUE, "value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where value is less than
        defaultInvoiceServiceCommonFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where value is greater than
        defaultInvoiceServiceCommonFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByAddedByIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where addedById equals to
        defaultInvoiceServiceCommonFiltering("addedById.equals=" + DEFAULT_ADDED_BY_ID, "addedById.equals=" + UPDATED_ADDED_BY_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByAddedByIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where addedById in
        defaultInvoiceServiceCommonFiltering(
            "addedById.in=" + DEFAULT_ADDED_BY_ID + "," + UPDATED_ADDED_BY_ID,
            "addedById.in=" + UPDATED_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByAddedByIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where addedById is not null
        defaultInvoiceServiceCommonFiltering("addedById.specified=true", "addedById.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByAddedByIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where addedById is greater than or equal to
        defaultInvoiceServiceCommonFiltering(
            "addedById.greaterThanOrEqual=" + DEFAULT_ADDED_BY_ID,
            "addedById.greaterThanOrEqual=" + UPDATED_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByAddedByIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where addedById is less than or equal to
        defaultInvoiceServiceCommonFiltering(
            "addedById.lessThanOrEqual=" + DEFAULT_ADDED_BY_ID,
            "addedById.lessThanOrEqual=" + SMALLER_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByAddedByIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where addedById is less than
        defaultInvoiceServiceCommonFiltering("addedById.lessThan=" + UPDATED_ADDED_BY_ID, "addedById.lessThan=" + DEFAULT_ADDED_BY_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByAddedByIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where addedById is greater than
        defaultInvoiceServiceCommonFiltering(
            "addedById.greaterThan=" + SMALLER_ADDED_BY_ID,
            "addedById.greaterThan=" + DEFAULT_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where discount equals to
        defaultInvoiceServiceCommonFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where discount in
        defaultInvoiceServiceCommonFiltering("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT, "discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where discount is not null
        defaultInvoiceServiceCommonFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where discount is greater than or equal to
        defaultInvoiceServiceCommonFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where discount is less than or equal to
        defaultInvoiceServiceCommonFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where discount is less than
        defaultInvoiceServiceCommonFiltering("discount.lessThan=" + UPDATED_DISCOUNT, "discount.lessThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where discount is greater than
        defaultInvoiceServiceCommonFiltering("discount.greaterThan=" + SMALLER_DISCOUNT, "discount.greaterThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByServicePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where servicePrice equals to
        defaultInvoiceServiceCommonFiltering(
            "servicePrice.equals=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.equals=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByServicePriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where servicePrice in
        defaultInvoiceServiceCommonFiltering(
            "servicePrice.in=" + DEFAULT_SERVICE_PRICE + "," + UPDATED_SERVICE_PRICE,
            "servicePrice.in=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByServicePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where servicePrice is not null
        defaultInvoiceServiceCommonFiltering("servicePrice.specified=true", "servicePrice.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByServicePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where servicePrice is greater than or equal to
        defaultInvoiceServiceCommonFiltering(
            "servicePrice.greaterThanOrEqual=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.greaterThanOrEqual=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByServicePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where servicePrice is less than or equal to
        defaultInvoiceServiceCommonFiltering(
            "servicePrice.lessThanOrEqual=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.lessThanOrEqual=" + SMALLER_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByServicePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where servicePrice is less than
        defaultInvoiceServiceCommonFiltering(
            "servicePrice.lessThan=" + UPDATED_SERVICE_PRICE,
            "servicePrice.lessThan=" + DEFAULT_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCommonsByServicePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        // Get all the invoiceServiceCommonList where servicePrice is greater than
        defaultInvoiceServiceCommonFiltering(
            "servicePrice.greaterThan=" + SMALLER_SERVICE_PRICE,
            "servicePrice.greaterThan=" + DEFAULT_SERVICE_PRICE
        );
    }

    private void defaultInvoiceServiceCommonFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultInvoiceServiceCommonShouldBeFound(shouldBeFound);
        defaultInvoiceServiceCommonShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceServiceCommonShouldBeFound(String filter) throws Exception {
        restInvoiceServiceCommonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceServiceCommon.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].mainId").value(hasItem(DEFAULT_MAIN_ID.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedById").value(hasItem(DEFAULT_ADDED_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restInvoiceServiceCommonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceServiceCommonShouldNotBeFound(String filter) throws Exception {
        restInvoiceServiceCommonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceServiceCommonMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInvoiceServiceCommon() throws Exception {
        // Get the invoiceServiceCommon
        restInvoiceServiceCommonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInvoiceServiceCommon() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoiceServiceCommon
        InvoiceServiceCommon updatedInvoiceServiceCommon = invoiceServiceCommonRepository
            .findById(invoiceServiceCommon.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInvoiceServiceCommon are not directly saved in db
        em.detach(updatedInvoiceServiceCommon);
        updatedInvoiceServiceCommon
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .mainId(UPDATED_MAIN_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedById(UPDATED_ADDED_BY_ID)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restInvoiceServiceCommonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInvoiceServiceCommon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInvoiceServiceCommon))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceServiceCommon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInvoiceServiceCommonToMatchAllProperties(updatedInvoiceServiceCommon);
    }

    @Test
    @Transactional
    void putNonExistingInvoiceServiceCommon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCommon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceServiceCommonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceServiceCommon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(invoiceServiceCommon))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCommon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInvoiceServiceCommon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCommon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceServiceCommonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(invoiceServiceCommon))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCommon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInvoiceServiceCommon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCommon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceServiceCommonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceServiceCommon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceServiceCommon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInvoiceServiceCommonWithPatch() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoiceServiceCommon using partial update
        InvoiceServiceCommon partialUpdatedInvoiceServiceCommon = new InvoiceServiceCommon();
        partialUpdatedInvoiceServiceCommon.setId(invoiceServiceCommon.getId());

        partialUpdatedInvoiceServiceCommon
            .invoiceId(UPDATED_INVOICE_ID)
            .optionId(UPDATED_OPTION_ID)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedById(UPDATED_ADDED_BY_ID)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restInvoiceServiceCommonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceServiceCommon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInvoiceServiceCommon))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceServiceCommon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInvoiceServiceCommonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInvoiceServiceCommon, invoiceServiceCommon),
            getPersistedInvoiceServiceCommon(invoiceServiceCommon)
        );
    }

    @Test
    @Transactional
    void fullUpdateInvoiceServiceCommonWithPatch() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoiceServiceCommon using partial update
        InvoiceServiceCommon partialUpdatedInvoiceServiceCommon = new InvoiceServiceCommon();
        partialUpdatedInvoiceServiceCommon.setId(invoiceServiceCommon.getId());

        partialUpdatedInvoiceServiceCommon
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .mainId(UPDATED_MAIN_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .addedById(UPDATED_ADDED_BY_ID)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restInvoiceServiceCommonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceServiceCommon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInvoiceServiceCommon))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceServiceCommon in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInvoiceServiceCommonUpdatableFieldsEquals(
            partialUpdatedInvoiceServiceCommon,
            getPersistedInvoiceServiceCommon(partialUpdatedInvoiceServiceCommon)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInvoiceServiceCommon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCommon.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceServiceCommonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoiceServiceCommon.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(invoiceServiceCommon))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCommon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInvoiceServiceCommon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCommon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceServiceCommonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(invoiceServiceCommon))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCommon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInvoiceServiceCommon() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCommon.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceServiceCommonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(invoiceServiceCommon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceServiceCommon in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInvoiceServiceCommon() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCommon = invoiceServiceCommonRepository.saveAndFlush(invoiceServiceCommon);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the invoiceServiceCommon
        restInvoiceServiceCommonMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoiceServiceCommon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return invoiceServiceCommonRepository.count();
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

    protected InvoiceServiceCommon getPersistedInvoiceServiceCommon(InvoiceServiceCommon invoiceServiceCommon) {
        return invoiceServiceCommonRepository.findById(invoiceServiceCommon.getId()).orElseThrow();
    }

    protected void assertPersistedInvoiceServiceCommonToMatchAllProperties(InvoiceServiceCommon expectedInvoiceServiceCommon) {
        assertInvoiceServiceCommonAllPropertiesEquals(
            expectedInvoiceServiceCommon,
            getPersistedInvoiceServiceCommon(expectedInvoiceServiceCommon)
        );
    }

    protected void assertPersistedInvoiceServiceCommonToMatchUpdatableProperties(InvoiceServiceCommon expectedInvoiceServiceCommon) {
        assertInvoiceServiceCommonAllUpdatablePropertiesEquals(
            expectedInvoiceServiceCommon,
            getPersistedInvoiceServiceCommon(expectedInvoiceServiceCommon)
        );
    }
}
