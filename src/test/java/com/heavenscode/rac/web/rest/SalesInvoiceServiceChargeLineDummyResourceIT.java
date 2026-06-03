package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummyAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummy;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineDummyRepository;
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
 * Integration tests for the {@link SalesInvoiceServiceChargeLineDummyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesInvoiceServiceChargeLineDummyResourceIT {

    private static final Integer DEFAULT_INVOICE_ID = 1;
    private static final Integer UPDATED_INVOICE_ID = 2;
    private static final Integer SMALLER_INVOICE_ID = 1 - 1;

    private static final Integer DEFAULT_LINE_ID = 1;
    private static final Integer UPDATED_LINE_ID = 2;
    private static final Integer SMALLER_LINE_ID = 1 - 1;

    private static final Integer DEFAULT_OPTION_ID = 1;
    private static final Integer UPDATED_OPTION_ID = 2;
    private static final Integer SMALLER_OPTION_ID = 1 - 1;

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;
    private static final Float SMALLER_VALUE = 1F - 1F;

    private static final Boolean DEFAULT_IS_CUSTOMER_SERVICE = false;
    private static final Boolean UPDATED_IS_CUSTOMER_SERVICE = true;

    private static final String ENTITY_API_URL = "/api/sales-invoice-service-charge-line-dummies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SalesInvoiceServiceChargeLineDummyRepository salesInvoiceServiceChargeLineDummyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesInvoiceServiceChargeLineDummyMockMvc;

    private SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy;

    private SalesInvoiceServiceChargeLineDummy insertedSalesInvoiceServiceChargeLineDummy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceServiceChargeLineDummy createEntity() {
        return new SalesInvoiceServiceChargeLineDummy()
            .invoiceId(DEFAULT_INVOICE_ID)
            .lineId(DEFAULT_LINE_ID)
            .optionId(DEFAULT_OPTION_ID)
            .serviceName(DEFAULT_SERVICE_NAME)
            .serviceDescription(DEFAULT_SERVICE_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .isCustomerService(DEFAULT_IS_CUSTOMER_SERVICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceServiceChargeLineDummy createUpdatedEntity() {
        return new SalesInvoiceServiceChargeLineDummy()
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isCustomerService(UPDATED_IS_CUSTOMER_SERVICE);
    }

    @BeforeEach
    public void initTest() {
        salesInvoiceServiceChargeLineDummy = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSalesInvoiceServiceChargeLineDummy != null) {
            salesInvoiceServiceChargeLineDummyRepository.delete(insertedSalesInvoiceServiceChargeLineDummy);
            insertedSalesInvoiceServiceChargeLineDummy = null;
        }
    }

    @Test
    @Transactional
    void createSalesInvoiceServiceChargeLineDummy() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SalesInvoiceServiceChargeLineDummy
        var returnedSalesInvoiceServiceChargeLineDummy = om.readValue(
            restSalesInvoiceServiceChargeLineDummyMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(salesInvoiceServiceChargeLineDummy))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SalesInvoiceServiceChargeLineDummy.class
        );

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSalesInvoiceServiceChargeLineDummyUpdatableFieldsEquals(
            returnedSalesInvoiceServiceChargeLineDummy,
            getPersistedSalesInvoiceServiceChargeLineDummy(returnedSalesInvoiceServiceChargeLineDummy)
        );

        insertedSalesInvoiceServiceChargeLineDummy = returnedSalesInvoiceServiceChargeLineDummy;
    }

    @Test
    @Transactional
    void createSalesInvoiceServiceChargeLineDummyWithExistingId() throws Exception {
        // Create the SalesInvoiceServiceChargeLineDummy with an existing ID
        salesInvoiceServiceChargeLineDummy.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummies() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceServiceChargeLineDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isCustomerService").value(hasItem(DEFAULT_IS_CUSTOMER_SERVICE.booleanValue())));
    }

    @Test
    @Transactional
    void getSalesInvoiceServiceChargeLineDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get the salesInvoiceServiceChargeLineDummy
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(get(ENTITY_API_URL_ID, salesInvoiceServiceChargeLineDummy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesInvoiceServiceChargeLineDummy.getId().intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID))
            .andExpect(jsonPath("$.optionId").value(DEFAULT_OPTION_ID))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.serviceDescription").value(DEFAULT_SERVICE_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.isCustomerService").value(DEFAULT_IS_CUSTOMER_SERVICE.booleanValue()));
    }

    @Test
    @Transactional
    void getSalesInvoiceServiceChargeLineDummiesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        Long id = salesInvoiceServiceChargeLineDummy.getId();

        defaultSalesInvoiceServiceChargeLineDummyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSalesInvoiceServiceChargeLineDummyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSalesInvoiceServiceChargeLineDummyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByInvoiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where invoiceId equals to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "invoiceId.equals=" + DEFAULT_INVOICE_ID,
            "invoiceId.equals=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByInvoiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where invoiceId in
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "invoiceId.in=" + DEFAULT_INVOICE_ID + "," + UPDATED_INVOICE_ID,
            "invoiceId.in=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByInvoiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where invoiceId is not null
        defaultSalesInvoiceServiceChargeLineDummyFiltering("invoiceId.specified=true", "invoiceId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByInvoiceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where invoiceId is greater than or equal to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "invoiceId.greaterThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.greaterThanOrEqual=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByInvoiceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where invoiceId is less than or equal to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "invoiceId.lessThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.lessThanOrEqual=" + SMALLER_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByInvoiceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where invoiceId is less than
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "invoiceId.lessThan=" + UPDATED_INVOICE_ID,
            "invoiceId.lessThan=" + DEFAULT_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByInvoiceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where invoiceId is greater than
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "invoiceId.greaterThan=" + SMALLER_INVOICE_ID,
            "invoiceId.greaterThan=" + DEFAULT_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where lineId equals to
        defaultSalesInvoiceServiceChargeLineDummyFiltering("lineId.equals=" + DEFAULT_LINE_ID, "lineId.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where lineId in
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "lineId.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID,
            "lineId.in=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where lineId is not null
        defaultSalesInvoiceServiceChargeLineDummyFiltering("lineId.specified=true", "lineId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where lineId is greater than or equal to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "lineId.greaterThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.greaterThanOrEqual=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where lineId is less than or equal to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "lineId.lessThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.lessThanOrEqual=" + SMALLER_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where lineId is less than
        defaultSalesInvoiceServiceChargeLineDummyFiltering("lineId.lessThan=" + UPDATED_LINE_ID, "lineId.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where lineId is greater than
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "lineId.greaterThan=" + SMALLER_LINE_ID,
            "lineId.greaterThan=" + DEFAULT_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByOptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where optionId equals to
        defaultSalesInvoiceServiceChargeLineDummyFiltering("optionId.equals=" + DEFAULT_OPTION_ID, "optionId.equals=" + UPDATED_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByOptionIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where optionId in
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "optionId.in=" + DEFAULT_OPTION_ID + "," + UPDATED_OPTION_ID,
            "optionId.in=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByOptionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where optionId is not null
        defaultSalesInvoiceServiceChargeLineDummyFiltering("optionId.specified=true", "optionId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByOptionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where optionId is greater than or equal to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "optionId.greaterThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.greaterThanOrEqual=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByOptionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where optionId is less than or equal to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "optionId.lessThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.lessThanOrEqual=" + SMALLER_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByOptionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where optionId is less than
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "optionId.lessThan=" + UPDATED_OPTION_ID,
            "optionId.lessThan=" + DEFAULT_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByOptionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where optionId is greater than
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "optionId.greaterThan=" + SMALLER_OPTION_ID,
            "optionId.greaterThan=" + DEFAULT_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceName equals to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "serviceName.equals=" + DEFAULT_SERVICE_NAME,
            "serviceName.equals=" + UPDATED_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceName in
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME,
            "serviceName.in=" + UPDATED_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceName is not null
        defaultSalesInvoiceServiceChargeLineDummyFiltering("serviceName.specified=true", "serviceName.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceName contains
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "serviceName.contains=" + DEFAULT_SERVICE_NAME,
            "serviceName.contains=" + UPDATED_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceName does not contain
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "serviceName.doesNotContain=" + UPDATED_SERVICE_NAME,
            "serviceName.doesNotContain=" + DEFAULT_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceDescription equals to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "serviceDescription.equals=" + DEFAULT_SERVICE_DESCRIPTION,
            "serviceDescription.equals=" + UPDATED_SERVICE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceDescription in
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "serviceDescription.in=" + DEFAULT_SERVICE_DESCRIPTION + "," + UPDATED_SERVICE_DESCRIPTION,
            "serviceDescription.in=" + UPDATED_SERVICE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceDescription is not null
        defaultSalesInvoiceServiceChargeLineDummyFiltering("serviceDescription.specified=true", "serviceDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceDescription contains
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "serviceDescription.contains=" + DEFAULT_SERVICE_DESCRIPTION,
            "serviceDescription.contains=" + UPDATED_SERVICE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByServiceDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where serviceDescription does not contain
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "serviceDescription.doesNotContain=" + UPDATED_SERVICE_DESCRIPTION,
            "serviceDescription.doesNotContain=" + DEFAULT_SERVICE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where value equals to
        defaultSalesInvoiceServiceChargeLineDummyFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where value in
        defaultSalesInvoiceServiceChargeLineDummyFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where value is not null
        defaultSalesInvoiceServiceChargeLineDummyFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where value is greater than or equal to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "value.greaterThanOrEqual=" + DEFAULT_VALUE,
            "value.greaterThanOrEqual=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where value is less than or equal to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "value.lessThanOrEqual=" + DEFAULT_VALUE,
            "value.lessThanOrEqual=" + SMALLER_VALUE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where value is less than
        defaultSalesInvoiceServiceChargeLineDummyFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where value is greater than
        defaultSalesInvoiceServiceChargeLineDummyFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByIsCustomerServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where isCustomerService equals to
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "isCustomerService.equals=" + DEFAULT_IS_CUSTOMER_SERVICE,
            "isCustomerService.equals=" + UPDATED_IS_CUSTOMER_SERVICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByIsCustomerServiceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where isCustomerService in
        defaultSalesInvoiceServiceChargeLineDummyFiltering(
            "isCustomerService.in=" + DEFAULT_IS_CUSTOMER_SERVICE + "," + UPDATED_IS_CUSTOMER_SERVICE,
            "isCustomerService.in=" + UPDATED_IS_CUSTOMER_SERVICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLineDummiesByIsCustomerServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        // Get all the salesInvoiceServiceChargeLineDummyList where isCustomerService is not null
        defaultSalesInvoiceServiceChargeLineDummyFiltering("isCustomerService.specified=true", "isCustomerService.specified=false");
    }

    private void defaultSalesInvoiceServiceChargeLineDummyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSalesInvoiceServiceChargeLineDummyShouldBeFound(shouldBeFound);
        defaultSalesInvoiceServiceChargeLineDummyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalesInvoiceServiceChargeLineDummyShouldBeFound(String filter) throws Exception {
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceServiceChargeLineDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isCustomerService").value(hasItem(DEFAULT_IS_CUSTOMER_SERVICE.booleanValue())));

        // Check, that the count call also returns 1
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalesInvoiceServiceChargeLineDummyShouldNotBeFound(String filter) throws Exception {
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalesInvoiceServiceChargeLineDummy() throws Exception {
        // Get the salesInvoiceServiceChargeLineDummy
        restSalesInvoiceServiceChargeLineDummyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesInvoiceServiceChargeLineDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceServiceChargeLineDummy
        SalesInvoiceServiceChargeLineDummy updatedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository
            .findById(salesInvoiceServiceChargeLineDummy.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSalesInvoiceServiceChargeLineDummy are not directly saved in db
        em.detach(updatedSalesInvoiceServiceChargeLineDummy);
        updatedSalesInvoiceServiceChargeLineDummy
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isCustomerService(UPDATED_IS_CUSTOMER_SERVICE);

        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesInvoiceServiceChargeLineDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSalesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSalesInvoiceServiceChargeLineDummyToMatchAllProperties(updatedSalesInvoiceServiceChargeLineDummy);
    }

    @Test
    @Transactional
    void putNonExistingSalesInvoiceServiceChargeLineDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLineDummy.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesInvoiceServiceChargeLineDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesInvoiceServiceChargeLineDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLineDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesInvoiceServiceChargeLineDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLineDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesInvoiceServiceChargeLineDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceServiceChargeLineDummy using partial update
        SalesInvoiceServiceChargeLineDummy partialUpdatedSalesInvoiceServiceChargeLineDummy = new SalesInvoiceServiceChargeLineDummy();
        partialUpdatedSalesInvoiceServiceChargeLineDummy.setId(salesInvoiceServiceChargeLineDummy.getId());

        partialUpdatedSalesInvoiceServiceChargeLineDummy
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .isCustomerService(UPDATED_IS_CUSTOMER_SERVICE);

        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceServiceChargeLineDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceServiceChargeLineDummyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSalesInvoiceServiceChargeLineDummy, salesInvoiceServiceChargeLineDummy),
            getPersistedSalesInvoiceServiceChargeLineDummy(salesInvoiceServiceChargeLineDummy)
        );
    }

    @Test
    @Transactional
    void fullUpdateSalesInvoiceServiceChargeLineDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceServiceChargeLineDummy using partial update
        SalesInvoiceServiceChargeLineDummy partialUpdatedSalesInvoiceServiceChargeLineDummy = new SalesInvoiceServiceChargeLineDummy();
        partialUpdatedSalesInvoiceServiceChargeLineDummy.setId(salesInvoiceServiceChargeLineDummy.getId());

        partialUpdatedSalesInvoiceServiceChargeLineDummy
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isCustomerService(UPDATED_IS_CUSTOMER_SERVICE);

        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceServiceChargeLineDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceServiceChargeLineDummyUpdatableFieldsEquals(
            partialUpdatedSalesInvoiceServiceChargeLineDummy,
            getPersistedSalesInvoiceServiceChargeLineDummy(partialUpdatedSalesInvoiceServiceChargeLineDummy)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSalesInvoiceServiceChargeLineDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLineDummy.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesInvoiceServiceChargeLineDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesInvoiceServiceChargeLineDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLineDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesInvoiceServiceChargeLineDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLineDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLineDummy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceServiceChargeLineDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesInvoiceServiceChargeLineDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLineDummy = salesInvoiceServiceChargeLineDummyRepository.saveAndFlush(
            salesInvoiceServiceChargeLineDummy
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the salesInvoiceServiceChargeLineDummy
        restSalesInvoiceServiceChargeLineDummyMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesInvoiceServiceChargeLineDummy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return salesInvoiceServiceChargeLineDummyRepository.count();
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

    protected SalesInvoiceServiceChargeLineDummy getPersistedSalesInvoiceServiceChargeLineDummy(
        SalesInvoiceServiceChargeLineDummy salesInvoiceServiceChargeLineDummy
    ) {
        return salesInvoiceServiceChargeLineDummyRepository.findById(salesInvoiceServiceChargeLineDummy.getId()).orElseThrow();
    }

    protected void assertPersistedSalesInvoiceServiceChargeLineDummyToMatchAllProperties(
        SalesInvoiceServiceChargeLineDummy expectedSalesInvoiceServiceChargeLineDummy
    ) {
        assertSalesInvoiceServiceChargeLineDummyAllPropertiesEquals(
            expectedSalesInvoiceServiceChargeLineDummy,
            getPersistedSalesInvoiceServiceChargeLineDummy(expectedSalesInvoiceServiceChargeLineDummy)
        );
    }

    protected void assertPersistedSalesInvoiceServiceChargeLineDummyToMatchUpdatableProperties(
        SalesInvoiceServiceChargeLineDummy expectedSalesInvoiceServiceChargeLineDummy
    ) {
        assertSalesInvoiceServiceChargeLineDummyAllUpdatablePropertiesEquals(
            expectedSalesInvoiceServiceChargeLineDummy,
            getPersistedSalesInvoiceServiceChargeLineDummy(expectedSalesInvoiceServiceChargeLineDummy)
        );
    }
}
