package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.InvoiceServiceChargeAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.InvoiceServiceCharge;
import com.heavenscode.rac.repository.InvoiceServiceChargeRepository;
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
 * Integration tests for the {@link InvoiceServiceChargeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InvoiceServiceChargeResourceIT {

    private static final Long DEFAULT_INVOICE_ID = 1L;
    private static final Long UPDATED_INVOICE_ID = 2L;
    private static final Long SMALLER_INVOICE_ID = 1L - 1L;

    private static final Long DEFAULT_LINE_ID = 1L;
    private static final Long UPDATED_LINE_ID = 2L;
    private static final Long SMALLER_LINE_ID = 1L - 1L;

    private static final Long DEFAULT_OPTION_ID = 1L;
    private static final Long UPDATED_OPTION_ID = 2L;
    private static final Long SMALLER_OPTION_ID = 1L - 1L;

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DISCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;
    private static final Double SMALLER_VALUE = 1D - 1D;

    private static final Long DEFAULT_ADDED_BY_ID = 1L;
    private static final Long UPDATED_ADDED_BY_ID = 2L;
    private static final Long SMALLER_ADDED_BY_ID = 1L - 1L;

    private static final Boolean DEFAULT_IS_CUSTOMER_SRVICE = false;
    private static final Boolean UPDATED_IS_CUSTOMER_SRVICE = true;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;
    private static final Double SMALLER_DISCOUNT = 1D - 1D;

    private static final Double DEFAULT_SERVICE_PRICE = 1D;
    private static final Double UPDATED_SERVICE_PRICE = 2D;
    private static final Double SMALLER_SERVICE_PRICE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/invoice-service-charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InvoiceServiceChargeRepository invoiceServiceChargeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceServiceChargeMockMvc;

    private InvoiceServiceCharge invoiceServiceCharge;

    private InvoiceServiceCharge insertedInvoiceServiceCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceServiceCharge createEntity() {
        return new InvoiceServiceCharge()
            .invoiceId(DEFAULT_INVOICE_ID)
            .lineId(DEFAULT_LINE_ID)
            .optionId(DEFAULT_OPTION_ID)
            .serviceName(DEFAULT_SERVICE_NAME)
            .serviceDiscription(DEFAULT_SERVICE_DISCRIPTION)
            .value(DEFAULT_VALUE)
            .addedById(DEFAULT_ADDED_BY_ID)
            .isCustomerSrvice(DEFAULT_IS_CUSTOMER_SRVICE)
            .discount(DEFAULT_DISCOUNT)
            .servicePrice(DEFAULT_SERVICE_PRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceServiceCharge createUpdatedEntity() {
        return new InvoiceServiceCharge()
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDiscription(UPDATED_SERVICE_DISCRIPTION)
            .value(UPDATED_VALUE)
            .addedById(UPDATED_ADDED_BY_ID)
            .isCustomerSrvice(UPDATED_IS_CUSTOMER_SRVICE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);
    }

    @BeforeEach
    public void initTest() {
        invoiceServiceCharge = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedInvoiceServiceCharge != null) {
            invoiceServiceChargeRepository.delete(insertedInvoiceServiceCharge);
            insertedInvoiceServiceCharge = null;
        }
    }

    @Test
    @Transactional
    void createInvoiceServiceCharge() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the InvoiceServiceCharge
        var returnedInvoiceServiceCharge = om.readValue(
            restInvoiceServiceChargeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceServiceCharge)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InvoiceServiceCharge.class
        );

        // Validate the InvoiceServiceCharge in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInvoiceServiceChargeUpdatableFieldsEquals(
            returnedInvoiceServiceCharge,
            getPersistedInvoiceServiceCharge(returnedInvoiceServiceCharge)
        );

        insertedInvoiceServiceCharge = returnedInvoiceServiceCharge;
    }

    @Test
    @Transactional
    void createInvoiceServiceChargeWithExistingId() throws Exception {
        // Create the InvoiceServiceCharge with an existing ID
        invoiceServiceCharge.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceServiceChargeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceServiceCharge)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceCharges() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList
        restInvoiceServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceServiceCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDiscription").value(hasItem(DEFAULT_SERVICE_DISCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedById").value(hasItem(DEFAULT_ADDED_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].isCustomerSrvice").value(hasItem(DEFAULT_IS_CUSTOMER_SRVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getInvoiceServiceCharge() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get the invoiceServiceCharge
        restInvoiceServiceChargeMockMvc
            .perform(get(ENTITY_API_URL_ID, invoiceServiceCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceServiceCharge.getId().intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID.intValue()))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID.intValue()))
            .andExpect(jsonPath("$.optionId").value(DEFAULT_OPTION_ID.intValue()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.serviceDiscription").value(DEFAULT_SERVICE_DISCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.addedById").value(DEFAULT_ADDED_BY_ID.intValue()))
            .andExpect(jsonPath("$.isCustomerSrvice").value(DEFAULT_IS_CUSTOMER_SRVICE.booleanValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.servicePrice").value(DEFAULT_SERVICE_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getInvoiceServiceChargesByIdFiltering() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        Long id = invoiceServiceCharge.getId();

        defaultInvoiceServiceChargeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultInvoiceServiceChargeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultInvoiceServiceChargeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByInvoiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where invoiceId equals to
        defaultInvoiceServiceChargeFiltering("invoiceId.equals=" + DEFAULT_INVOICE_ID, "invoiceId.equals=" + UPDATED_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByInvoiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where invoiceId in
        defaultInvoiceServiceChargeFiltering(
            "invoiceId.in=" + DEFAULT_INVOICE_ID + "," + UPDATED_INVOICE_ID,
            "invoiceId.in=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByInvoiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where invoiceId is not null
        defaultInvoiceServiceChargeFiltering("invoiceId.specified=true", "invoiceId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByInvoiceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where invoiceId is greater than or equal to
        defaultInvoiceServiceChargeFiltering(
            "invoiceId.greaterThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.greaterThanOrEqual=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByInvoiceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where invoiceId is less than or equal to
        defaultInvoiceServiceChargeFiltering(
            "invoiceId.lessThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.lessThanOrEqual=" + SMALLER_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByInvoiceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where invoiceId is less than
        defaultInvoiceServiceChargeFiltering("invoiceId.lessThan=" + UPDATED_INVOICE_ID, "invoiceId.lessThan=" + DEFAULT_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByInvoiceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where invoiceId is greater than
        defaultInvoiceServiceChargeFiltering("invoiceId.greaterThan=" + SMALLER_INVOICE_ID, "invoiceId.greaterThan=" + DEFAULT_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where lineId equals to
        defaultInvoiceServiceChargeFiltering("lineId.equals=" + DEFAULT_LINE_ID, "lineId.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where lineId in
        defaultInvoiceServiceChargeFiltering("lineId.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID, "lineId.in=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where lineId is not null
        defaultInvoiceServiceChargeFiltering("lineId.specified=true", "lineId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where lineId is greater than or equal to
        defaultInvoiceServiceChargeFiltering(
            "lineId.greaterThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.greaterThanOrEqual=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where lineId is less than or equal to
        defaultInvoiceServiceChargeFiltering("lineId.lessThanOrEqual=" + DEFAULT_LINE_ID, "lineId.lessThanOrEqual=" + SMALLER_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where lineId is less than
        defaultInvoiceServiceChargeFiltering("lineId.lessThan=" + UPDATED_LINE_ID, "lineId.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where lineId is greater than
        defaultInvoiceServiceChargeFiltering("lineId.greaterThan=" + SMALLER_LINE_ID, "lineId.greaterThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByOptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where optionId equals to
        defaultInvoiceServiceChargeFiltering("optionId.equals=" + DEFAULT_OPTION_ID, "optionId.equals=" + UPDATED_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByOptionIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where optionId in
        defaultInvoiceServiceChargeFiltering(
            "optionId.in=" + DEFAULT_OPTION_ID + "," + UPDATED_OPTION_ID,
            "optionId.in=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByOptionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where optionId is not null
        defaultInvoiceServiceChargeFiltering("optionId.specified=true", "optionId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByOptionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where optionId is greater than or equal to
        defaultInvoiceServiceChargeFiltering(
            "optionId.greaterThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.greaterThanOrEqual=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByOptionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where optionId is less than or equal to
        defaultInvoiceServiceChargeFiltering(
            "optionId.lessThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.lessThanOrEqual=" + SMALLER_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByOptionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where optionId is less than
        defaultInvoiceServiceChargeFiltering("optionId.lessThan=" + UPDATED_OPTION_ID, "optionId.lessThan=" + DEFAULT_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByOptionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where optionId is greater than
        defaultInvoiceServiceChargeFiltering("optionId.greaterThan=" + SMALLER_OPTION_ID, "optionId.greaterThan=" + DEFAULT_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceName equals to
        defaultInvoiceServiceChargeFiltering("serviceName.equals=" + DEFAULT_SERVICE_NAME, "serviceName.equals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceName in
        defaultInvoiceServiceChargeFiltering(
            "serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME,
            "serviceName.in=" + UPDATED_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceName is not null
        defaultInvoiceServiceChargeFiltering("serviceName.specified=true", "serviceName.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceNameContainsSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceName contains
        defaultInvoiceServiceChargeFiltering(
            "serviceName.contains=" + DEFAULT_SERVICE_NAME,
            "serviceName.contains=" + UPDATED_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceName does not contain
        defaultInvoiceServiceChargeFiltering(
            "serviceName.doesNotContain=" + UPDATED_SERVICE_NAME,
            "serviceName.doesNotContain=" + DEFAULT_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceDiscriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceDiscription equals to
        defaultInvoiceServiceChargeFiltering(
            "serviceDiscription.equals=" + DEFAULT_SERVICE_DISCRIPTION,
            "serviceDiscription.equals=" + UPDATED_SERVICE_DISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceDiscriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceDiscription in
        defaultInvoiceServiceChargeFiltering(
            "serviceDiscription.in=" + DEFAULT_SERVICE_DISCRIPTION + "," + UPDATED_SERVICE_DISCRIPTION,
            "serviceDiscription.in=" + UPDATED_SERVICE_DISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceDiscriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceDiscription is not null
        defaultInvoiceServiceChargeFiltering("serviceDiscription.specified=true", "serviceDiscription.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceDiscriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceDiscription contains
        defaultInvoiceServiceChargeFiltering(
            "serviceDiscription.contains=" + DEFAULT_SERVICE_DISCRIPTION,
            "serviceDiscription.contains=" + UPDATED_SERVICE_DISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServiceDiscriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where serviceDiscription does not contain
        defaultInvoiceServiceChargeFiltering(
            "serviceDiscription.doesNotContain=" + UPDATED_SERVICE_DISCRIPTION,
            "serviceDiscription.doesNotContain=" + DEFAULT_SERVICE_DISCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where value equals to
        defaultInvoiceServiceChargeFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where value in
        defaultInvoiceServiceChargeFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where value is not null
        defaultInvoiceServiceChargeFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where value is greater than or equal to
        defaultInvoiceServiceChargeFiltering("value.greaterThanOrEqual=" + DEFAULT_VALUE, "value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where value is less than or equal to
        defaultInvoiceServiceChargeFiltering("value.lessThanOrEqual=" + DEFAULT_VALUE, "value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where value is less than
        defaultInvoiceServiceChargeFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where value is greater than
        defaultInvoiceServiceChargeFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByAddedByIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where addedById equals to
        defaultInvoiceServiceChargeFiltering("addedById.equals=" + DEFAULT_ADDED_BY_ID, "addedById.equals=" + UPDATED_ADDED_BY_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByAddedByIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where addedById in
        defaultInvoiceServiceChargeFiltering(
            "addedById.in=" + DEFAULT_ADDED_BY_ID + "," + UPDATED_ADDED_BY_ID,
            "addedById.in=" + UPDATED_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByAddedByIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where addedById is not null
        defaultInvoiceServiceChargeFiltering("addedById.specified=true", "addedById.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByAddedByIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where addedById is greater than or equal to
        defaultInvoiceServiceChargeFiltering(
            "addedById.greaterThanOrEqual=" + DEFAULT_ADDED_BY_ID,
            "addedById.greaterThanOrEqual=" + UPDATED_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByAddedByIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where addedById is less than or equal to
        defaultInvoiceServiceChargeFiltering(
            "addedById.lessThanOrEqual=" + DEFAULT_ADDED_BY_ID,
            "addedById.lessThanOrEqual=" + SMALLER_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByAddedByIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where addedById is less than
        defaultInvoiceServiceChargeFiltering("addedById.lessThan=" + UPDATED_ADDED_BY_ID, "addedById.lessThan=" + DEFAULT_ADDED_BY_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByAddedByIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where addedById is greater than
        defaultInvoiceServiceChargeFiltering(
            "addedById.greaterThan=" + SMALLER_ADDED_BY_ID,
            "addedById.greaterThan=" + DEFAULT_ADDED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByIsCustomerSrviceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where isCustomerSrvice equals to
        defaultInvoiceServiceChargeFiltering(
            "isCustomerSrvice.equals=" + DEFAULT_IS_CUSTOMER_SRVICE,
            "isCustomerSrvice.equals=" + UPDATED_IS_CUSTOMER_SRVICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByIsCustomerSrviceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where isCustomerSrvice in
        defaultInvoiceServiceChargeFiltering(
            "isCustomerSrvice.in=" + DEFAULT_IS_CUSTOMER_SRVICE + "," + UPDATED_IS_CUSTOMER_SRVICE,
            "isCustomerSrvice.in=" + UPDATED_IS_CUSTOMER_SRVICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByIsCustomerSrviceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where isCustomerSrvice is not null
        defaultInvoiceServiceChargeFiltering("isCustomerSrvice.specified=true", "isCustomerSrvice.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where discount equals to
        defaultInvoiceServiceChargeFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where discount in
        defaultInvoiceServiceChargeFiltering("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT, "discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where discount is not null
        defaultInvoiceServiceChargeFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where discount is greater than or equal to
        defaultInvoiceServiceChargeFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where discount is less than or equal to
        defaultInvoiceServiceChargeFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where discount is less than
        defaultInvoiceServiceChargeFiltering("discount.lessThan=" + UPDATED_DISCOUNT, "discount.lessThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where discount is greater than
        defaultInvoiceServiceChargeFiltering("discount.greaterThan=" + SMALLER_DISCOUNT, "discount.greaterThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServicePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where servicePrice equals to
        defaultInvoiceServiceChargeFiltering(
            "servicePrice.equals=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.equals=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServicePriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where servicePrice in
        defaultInvoiceServiceChargeFiltering(
            "servicePrice.in=" + DEFAULT_SERVICE_PRICE + "," + UPDATED_SERVICE_PRICE,
            "servicePrice.in=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServicePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where servicePrice is not null
        defaultInvoiceServiceChargeFiltering("servicePrice.specified=true", "servicePrice.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServicePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where servicePrice is greater than or equal to
        defaultInvoiceServiceChargeFiltering(
            "servicePrice.greaterThanOrEqual=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.greaterThanOrEqual=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServicePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where servicePrice is less than or equal to
        defaultInvoiceServiceChargeFiltering(
            "servicePrice.lessThanOrEqual=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.lessThanOrEqual=" + SMALLER_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServicePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where servicePrice is less than
        defaultInvoiceServiceChargeFiltering(
            "servicePrice.lessThan=" + UPDATED_SERVICE_PRICE,
            "servicePrice.lessThan=" + DEFAULT_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllInvoiceServiceChargesByServicePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        // Get all the invoiceServiceChargeList where servicePrice is greater than
        defaultInvoiceServiceChargeFiltering(
            "servicePrice.greaterThan=" + SMALLER_SERVICE_PRICE,
            "servicePrice.greaterThan=" + DEFAULT_SERVICE_PRICE
        );
    }

    private void defaultInvoiceServiceChargeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultInvoiceServiceChargeShouldBeFound(shouldBeFound);
        defaultInvoiceServiceChargeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceServiceChargeShouldBeFound(String filter) throws Exception {
        restInvoiceServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceServiceCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID.intValue())))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDiscription").value(hasItem(DEFAULT_SERVICE_DISCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].addedById").value(hasItem(DEFAULT_ADDED_BY_ID.intValue())))
            .andExpect(jsonPath("$.[*].isCustomerSrvice").value(hasItem(DEFAULT_IS_CUSTOMER_SRVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restInvoiceServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceServiceChargeShouldNotBeFound(String filter) throws Exception {
        restInvoiceServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInvoiceServiceCharge() throws Exception {
        // Get the invoiceServiceCharge
        restInvoiceServiceChargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInvoiceServiceCharge() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoiceServiceCharge
        InvoiceServiceCharge updatedInvoiceServiceCharge = invoiceServiceChargeRepository
            .findById(invoiceServiceCharge.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedInvoiceServiceCharge are not directly saved in db
        em.detach(updatedInvoiceServiceCharge);
        updatedInvoiceServiceCharge
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDiscription(UPDATED_SERVICE_DISCRIPTION)
            .value(UPDATED_VALUE)
            .addedById(UPDATED_ADDED_BY_ID)
            .isCustomerSrvice(UPDATED_IS_CUSTOMER_SRVICE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restInvoiceServiceChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInvoiceServiceCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInvoiceServiceCharge))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInvoiceServiceChargeToMatchAllProperties(updatedInvoiceServiceCharge);
    }

    @Test
    @Transactional
    void putNonExistingInvoiceServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceServiceChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceServiceCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(invoiceServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInvoiceServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceServiceChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(invoiceServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInvoiceServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceServiceChargeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(invoiceServiceCharge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInvoiceServiceChargeWithPatch() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoiceServiceCharge using partial update
        InvoiceServiceCharge partialUpdatedInvoiceServiceCharge = new InvoiceServiceCharge();
        partialUpdatedInvoiceServiceCharge.setId(invoiceServiceCharge.getId());

        partialUpdatedInvoiceServiceCharge
            .invoiceId(UPDATED_INVOICE_ID)
            .isCustomerSrvice(UPDATED_IS_CUSTOMER_SRVICE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restInvoiceServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceServiceCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInvoiceServiceCharge))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceServiceCharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInvoiceServiceChargeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInvoiceServiceCharge, invoiceServiceCharge),
            getPersistedInvoiceServiceCharge(invoiceServiceCharge)
        );
    }

    @Test
    @Transactional
    void fullUpdateInvoiceServiceChargeWithPatch() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the invoiceServiceCharge using partial update
        InvoiceServiceCharge partialUpdatedInvoiceServiceCharge = new InvoiceServiceCharge();
        partialUpdatedInvoiceServiceCharge.setId(invoiceServiceCharge.getId());

        partialUpdatedInvoiceServiceCharge
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDiscription(UPDATED_SERVICE_DISCRIPTION)
            .value(UPDATED_VALUE)
            .addedById(UPDATED_ADDED_BY_ID)
            .isCustomerSrvice(UPDATED_IS_CUSTOMER_SRVICE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restInvoiceServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceServiceCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInvoiceServiceCharge))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceServiceCharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInvoiceServiceChargeUpdatableFieldsEquals(
            partialUpdatedInvoiceServiceCharge,
            getPersistedInvoiceServiceCharge(partialUpdatedInvoiceServiceCharge)
        );
    }

    @Test
    @Transactional
    void patchNonExistingInvoiceServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoiceServiceCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(invoiceServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInvoiceServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(invoiceServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInvoiceServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        invoiceServiceCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceServiceChargeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(invoiceServiceCharge)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInvoiceServiceCharge() throws Exception {
        // Initialize the database
        insertedInvoiceServiceCharge = invoiceServiceChargeRepository.saveAndFlush(invoiceServiceCharge);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the invoiceServiceCharge
        restInvoiceServiceChargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoiceServiceCharge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return invoiceServiceChargeRepository.count();
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

    protected InvoiceServiceCharge getPersistedInvoiceServiceCharge(InvoiceServiceCharge invoiceServiceCharge) {
        return invoiceServiceChargeRepository.findById(invoiceServiceCharge.getId()).orElseThrow();
    }

    protected void assertPersistedInvoiceServiceChargeToMatchAllProperties(InvoiceServiceCharge expectedInvoiceServiceCharge) {
        assertInvoiceServiceChargeAllPropertiesEquals(
            expectedInvoiceServiceCharge,
            getPersistedInvoiceServiceCharge(expectedInvoiceServiceCharge)
        );
    }

    protected void assertPersistedInvoiceServiceChargeToMatchUpdatableProperties(InvoiceServiceCharge expectedInvoiceServiceCharge) {
        assertInvoiceServiceChargeAllUpdatablePropertiesEquals(
            expectedInvoiceServiceCharge,
            getPersistedInvoiceServiceCharge(expectedInvoiceServiceCharge)
        );
    }
}
