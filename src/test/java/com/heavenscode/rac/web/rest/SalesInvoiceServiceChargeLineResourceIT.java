package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SalesInvoiceServiceChargeLine;
import com.heavenscode.rac.repository.SalesInvoiceServiceChargeLineRepository;
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
 * Integration tests for the {@link SalesInvoiceServiceChargeLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesInvoiceServiceChargeLineResourceIT {

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

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;
    private static final Float SMALLER_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_SERVICE_PRICE = 1F;
    private static final Float UPDATED_SERVICE_PRICE = 2F;
    private static final Float SMALLER_SERVICE_PRICE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/sales-invoice-service-charge-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SalesInvoiceServiceChargeLineRepository salesInvoiceServiceChargeLineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesInvoiceServiceChargeLineMockMvc;

    private SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine;

    private SalesInvoiceServiceChargeLine insertedSalesInvoiceServiceChargeLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceServiceChargeLine createEntity() {
        return new SalesInvoiceServiceChargeLine()
            .invoiceId(DEFAULT_INVOICE_ID)
            .lineId(DEFAULT_LINE_ID)
            .optionId(DEFAULT_OPTION_ID)
            .serviceName(DEFAULT_SERVICE_NAME)
            .serviceDescription(DEFAULT_SERVICE_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .isCustomerService(DEFAULT_IS_CUSTOMER_SERVICE)
            .discount(DEFAULT_DISCOUNT)
            .servicePrice(DEFAULT_SERVICE_PRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceServiceChargeLine createUpdatedEntity() {
        return new SalesInvoiceServiceChargeLine()
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isCustomerService(UPDATED_IS_CUSTOMER_SERVICE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);
    }

    @BeforeEach
    public void initTest() {
        salesInvoiceServiceChargeLine = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSalesInvoiceServiceChargeLine != null) {
            salesInvoiceServiceChargeLineRepository.delete(insertedSalesInvoiceServiceChargeLine);
            insertedSalesInvoiceServiceChargeLine = null;
        }
    }

    @Test
    @Transactional
    void createSalesInvoiceServiceChargeLine() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SalesInvoiceServiceChargeLine
        var returnedSalesInvoiceServiceChargeLine = om.readValue(
            restSalesInvoiceServiceChargeLineMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(salesInvoiceServiceChargeLine))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SalesInvoiceServiceChargeLine.class
        );

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSalesInvoiceServiceChargeLineUpdatableFieldsEquals(
            returnedSalesInvoiceServiceChargeLine,
            getPersistedSalesInvoiceServiceChargeLine(returnedSalesInvoiceServiceChargeLine)
        );

        insertedSalesInvoiceServiceChargeLine = returnedSalesInvoiceServiceChargeLine;
    }

    @Test
    @Transactional
    void createSalesInvoiceServiceChargeLineWithExistingId() throws Exception {
        // Create the SalesInvoiceServiceChargeLine with an existing ID
        salesInvoiceServiceChargeLine.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceServiceChargeLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLines() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceServiceChargeLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isCustomerService").value(hasItem(DEFAULT_IS_CUSTOMER_SERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getSalesInvoiceServiceChargeLine() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get the salesInvoiceServiceChargeLine
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(get(ENTITY_API_URL_ID, salesInvoiceServiceChargeLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesInvoiceServiceChargeLine.getId().intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID))
            .andExpect(jsonPath("$.optionId").value(DEFAULT_OPTION_ID))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.serviceDescription").value(DEFAULT_SERVICE_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.isCustomerService").value(DEFAULT_IS_CUSTOMER_SERVICE.booleanValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.servicePrice").value(DEFAULT_SERVICE_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getSalesInvoiceServiceChargeLinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        Long id = salesInvoiceServiceChargeLine.getId();

        defaultSalesInvoiceServiceChargeLineFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSalesInvoiceServiceChargeLineFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSalesInvoiceServiceChargeLineFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByInvoiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where invoiceId equals to
        defaultSalesInvoiceServiceChargeLineFiltering("invoiceId.equals=" + DEFAULT_INVOICE_ID, "invoiceId.equals=" + UPDATED_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByInvoiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where invoiceId in
        defaultSalesInvoiceServiceChargeLineFiltering(
            "invoiceId.in=" + DEFAULT_INVOICE_ID + "," + UPDATED_INVOICE_ID,
            "invoiceId.in=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByInvoiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where invoiceId is not null
        defaultSalesInvoiceServiceChargeLineFiltering("invoiceId.specified=true", "invoiceId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByInvoiceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where invoiceId is greater than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "invoiceId.greaterThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.greaterThanOrEqual=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByInvoiceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where invoiceId is less than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "invoiceId.lessThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.lessThanOrEqual=" + SMALLER_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByInvoiceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where invoiceId is less than
        defaultSalesInvoiceServiceChargeLineFiltering(
            "invoiceId.lessThan=" + UPDATED_INVOICE_ID,
            "invoiceId.lessThan=" + DEFAULT_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByInvoiceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where invoiceId is greater than
        defaultSalesInvoiceServiceChargeLineFiltering(
            "invoiceId.greaterThan=" + SMALLER_INVOICE_ID,
            "invoiceId.greaterThan=" + DEFAULT_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where lineId equals to
        defaultSalesInvoiceServiceChargeLineFiltering("lineId.equals=" + DEFAULT_LINE_ID, "lineId.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where lineId in
        defaultSalesInvoiceServiceChargeLineFiltering(
            "lineId.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID,
            "lineId.in=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where lineId is not null
        defaultSalesInvoiceServiceChargeLineFiltering("lineId.specified=true", "lineId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where lineId is greater than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "lineId.greaterThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.greaterThanOrEqual=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where lineId is less than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "lineId.lessThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.lessThanOrEqual=" + SMALLER_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where lineId is less than
        defaultSalesInvoiceServiceChargeLineFiltering("lineId.lessThan=" + UPDATED_LINE_ID, "lineId.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where lineId is greater than
        defaultSalesInvoiceServiceChargeLineFiltering("lineId.greaterThan=" + SMALLER_LINE_ID, "lineId.greaterThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByOptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where optionId equals to
        defaultSalesInvoiceServiceChargeLineFiltering("optionId.equals=" + DEFAULT_OPTION_ID, "optionId.equals=" + UPDATED_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByOptionIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where optionId in
        defaultSalesInvoiceServiceChargeLineFiltering(
            "optionId.in=" + DEFAULT_OPTION_ID + "," + UPDATED_OPTION_ID,
            "optionId.in=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByOptionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where optionId is not null
        defaultSalesInvoiceServiceChargeLineFiltering("optionId.specified=true", "optionId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByOptionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where optionId is greater than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "optionId.greaterThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.greaterThanOrEqual=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByOptionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where optionId is less than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "optionId.lessThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.lessThanOrEqual=" + SMALLER_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByOptionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where optionId is less than
        defaultSalesInvoiceServiceChargeLineFiltering("optionId.lessThan=" + UPDATED_OPTION_ID, "optionId.lessThan=" + DEFAULT_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByOptionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where optionId is greater than
        defaultSalesInvoiceServiceChargeLineFiltering(
            "optionId.greaterThan=" + SMALLER_OPTION_ID,
            "optionId.greaterThan=" + DEFAULT_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceName equals to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "serviceName.equals=" + DEFAULT_SERVICE_NAME,
            "serviceName.equals=" + UPDATED_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceName in
        defaultSalesInvoiceServiceChargeLineFiltering(
            "serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME,
            "serviceName.in=" + UPDATED_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceName is not null
        defaultSalesInvoiceServiceChargeLineFiltering("serviceName.specified=true", "serviceName.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceName contains
        defaultSalesInvoiceServiceChargeLineFiltering(
            "serviceName.contains=" + DEFAULT_SERVICE_NAME,
            "serviceName.contains=" + UPDATED_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceName does not contain
        defaultSalesInvoiceServiceChargeLineFiltering(
            "serviceName.doesNotContain=" + UPDATED_SERVICE_NAME,
            "serviceName.doesNotContain=" + DEFAULT_SERVICE_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceDescription equals to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "serviceDescription.equals=" + DEFAULT_SERVICE_DESCRIPTION,
            "serviceDescription.equals=" + UPDATED_SERVICE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceDescription in
        defaultSalesInvoiceServiceChargeLineFiltering(
            "serviceDescription.in=" + DEFAULT_SERVICE_DESCRIPTION + "," + UPDATED_SERVICE_DESCRIPTION,
            "serviceDescription.in=" + UPDATED_SERVICE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceDescription is not null
        defaultSalesInvoiceServiceChargeLineFiltering("serviceDescription.specified=true", "serviceDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceDescription contains
        defaultSalesInvoiceServiceChargeLineFiltering(
            "serviceDescription.contains=" + DEFAULT_SERVICE_DESCRIPTION,
            "serviceDescription.contains=" + UPDATED_SERVICE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServiceDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where serviceDescription does not contain
        defaultSalesInvoiceServiceChargeLineFiltering(
            "serviceDescription.doesNotContain=" + UPDATED_SERVICE_DESCRIPTION,
            "serviceDescription.doesNotContain=" + DEFAULT_SERVICE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where value equals to
        defaultSalesInvoiceServiceChargeLineFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where value in
        defaultSalesInvoiceServiceChargeLineFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where value is not null
        defaultSalesInvoiceServiceChargeLineFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where value is greater than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "value.greaterThanOrEqual=" + DEFAULT_VALUE,
            "value.greaterThanOrEqual=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where value is less than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering("value.lessThanOrEqual=" + DEFAULT_VALUE, "value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where value is less than
        defaultSalesInvoiceServiceChargeLineFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where value is greater than
        defaultSalesInvoiceServiceChargeLineFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByIsCustomerServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where isCustomerService equals to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "isCustomerService.equals=" + DEFAULT_IS_CUSTOMER_SERVICE,
            "isCustomerService.equals=" + UPDATED_IS_CUSTOMER_SERVICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByIsCustomerServiceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where isCustomerService in
        defaultSalesInvoiceServiceChargeLineFiltering(
            "isCustomerService.in=" + DEFAULT_IS_CUSTOMER_SERVICE + "," + UPDATED_IS_CUSTOMER_SERVICE,
            "isCustomerService.in=" + UPDATED_IS_CUSTOMER_SERVICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByIsCustomerServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where isCustomerService is not null
        defaultSalesInvoiceServiceChargeLineFiltering("isCustomerService.specified=true", "isCustomerService.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where discount equals to
        defaultSalesInvoiceServiceChargeLineFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where discount in
        defaultSalesInvoiceServiceChargeLineFiltering(
            "discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT,
            "discount.in=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where discount is not null
        defaultSalesInvoiceServiceChargeLineFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where discount is greater than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where discount is less than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where discount is less than
        defaultSalesInvoiceServiceChargeLineFiltering("discount.lessThan=" + UPDATED_DISCOUNT, "discount.lessThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where discount is greater than
        defaultSalesInvoiceServiceChargeLineFiltering(
            "discount.greaterThan=" + SMALLER_DISCOUNT,
            "discount.greaterThan=" + DEFAULT_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServicePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where servicePrice equals to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "servicePrice.equals=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.equals=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServicePriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where servicePrice in
        defaultSalesInvoiceServiceChargeLineFiltering(
            "servicePrice.in=" + DEFAULT_SERVICE_PRICE + "," + UPDATED_SERVICE_PRICE,
            "servicePrice.in=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServicePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where servicePrice is not null
        defaultSalesInvoiceServiceChargeLineFiltering("servicePrice.specified=true", "servicePrice.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServicePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where servicePrice is greater than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "servicePrice.greaterThanOrEqual=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.greaterThanOrEqual=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServicePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where servicePrice is less than or equal to
        defaultSalesInvoiceServiceChargeLineFiltering(
            "servicePrice.lessThanOrEqual=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.lessThanOrEqual=" + SMALLER_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServicePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where servicePrice is less than
        defaultSalesInvoiceServiceChargeLineFiltering(
            "servicePrice.lessThan=" + UPDATED_SERVICE_PRICE,
            "servicePrice.lessThan=" + DEFAULT_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceServiceChargeLinesByServicePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        // Get all the salesInvoiceServiceChargeLineList where servicePrice is greater than
        defaultSalesInvoiceServiceChargeLineFiltering(
            "servicePrice.greaterThan=" + SMALLER_SERVICE_PRICE,
            "servicePrice.greaterThan=" + DEFAULT_SERVICE_PRICE
        );
    }

    private void defaultSalesInvoiceServiceChargeLineFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSalesInvoiceServiceChargeLineShouldBeFound(shouldBeFound);
        defaultSalesInvoiceServiceChargeLineShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalesInvoiceServiceChargeLineShouldBeFound(String filter) throws Exception {
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceServiceChargeLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].serviceDescription").value(hasItem(DEFAULT_SERVICE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isCustomerService").value(hasItem(DEFAULT_IS_CUSTOMER_SERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalesInvoiceServiceChargeLineShouldNotBeFound(String filter) throws Exception {
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalesInvoiceServiceChargeLine() throws Exception {
        // Get the salesInvoiceServiceChargeLine
        restSalesInvoiceServiceChargeLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesInvoiceServiceChargeLine() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceServiceChargeLine
        SalesInvoiceServiceChargeLine updatedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository
            .findById(salesInvoiceServiceChargeLine.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSalesInvoiceServiceChargeLine are not directly saved in db
        em.detach(updatedSalesInvoiceServiceChargeLine);
        updatedSalesInvoiceServiceChargeLine
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isCustomerService(UPDATED_IS_CUSTOMER_SERVICE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesInvoiceServiceChargeLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSalesInvoiceServiceChargeLine))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSalesInvoiceServiceChargeLineToMatchAllProperties(updatedSalesInvoiceServiceChargeLine);
    }

    @Test
    @Transactional
    void putNonExistingSalesInvoiceServiceChargeLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLine.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesInvoiceServiceChargeLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesInvoiceServiceChargeLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLine.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesInvoiceServiceChargeLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLine.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceServiceChargeLine))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesInvoiceServiceChargeLineWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceServiceChargeLine using partial update
        SalesInvoiceServiceChargeLine partialUpdatedSalesInvoiceServiceChargeLine = new SalesInvoiceServiceChargeLine();
        partialUpdatedSalesInvoiceServiceChargeLine.setId(salesInvoiceServiceChargeLine.getId());

        partialUpdatedSalesInvoiceServiceChargeLine
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION);

        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceServiceChargeLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceServiceChargeLine))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceServiceChargeLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceServiceChargeLineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSalesInvoiceServiceChargeLine, salesInvoiceServiceChargeLine),
            getPersistedSalesInvoiceServiceChargeLine(salesInvoiceServiceChargeLine)
        );
    }

    @Test
    @Transactional
    void fullUpdateSalesInvoiceServiceChargeLineWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceServiceChargeLine using partial update
        SalesInvoiceServiceChargeLine partialUpdatedSalesInvoiceServiceChargeLine = new SalesInvoiceServiceChargeLine();
        partialUpdatedSalesInvoiceServiceChargeLine.setId(salesInvoiceServiceChargeLine.getId());

        partialUpdatedSalesInvoiceServiceChargeLine
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceDescription(UPDATED_SERVICE_DESCRIPTION)
            .value(UPDATED_VALUE)
            .isCustomerService(UPDATED_IS_CUSTOMER_SERVICE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceServiceChargeLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceServiceChargeLine))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceServiceChargeLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceServiceChargeLineUpdatableFieldsEquals(
            partialUpdatedSalesInvoiceServiceChargeLine,
            getPersistedSalesInvoiceServiceChargeLine(partialUpdatedSalesInvoiceServiceChargeLine)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSalesInvoiceServiceChargeLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLine.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesInvoiceServiceChargeLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesInvoiceServiceChargeLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLine.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesInvoiceServiceChargeLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceServiceChargeLine.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceServiceChargeLine))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceServiceChargeLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesInvoiceServiceChargeLine() throws Exception {
        // Initialize the database
        insertedSalesInvoiceServiceChargeLine = salesInvoiceServiceChargeLineRepository.saveAndFlush(salesInvoiceServiceChargeLine);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the salesInvoiceServiceChargeLine
        restSalesInvoiceServiceChargeLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesInvoiceServiceChargeLine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return salesInvoiceServiceChargeLineRepository.count();
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

    protected SalesInvoiceServiceChargeLine getPersistedSalesInvoiceServiceChargeLine(
        SalesInvoiceServiceChargeLine salesInvoiceServiceChargeLine
    ) {
        return salesInvoiceServiceChargeLineRepository.findById(salesInvoiceServiceChargeLine.getId()).orElseThrow();
    }

    protected void assertPersistedSalesInvoiceServiceChargeLineToMatchAllProperties(
        SalesInvoiceServiceChargeLine expectedSalesInvoiceServiceChargeLine
    ) {
        assertSalesInvoiceServiceChargeLineAllPropertiesEquals(
            expectedSalesInvoiceServiceChargeLine,
            getPersistedSalesInvoiceServiceChargeLine(expectedSalesInvoiceServiceChargeLine)
        );
    }

    protected void assertPersistedSalesInvoiceServiceChargeLineToMatchUpdatableProperties(
        SalesInvoiceServiceChargeLine expectedSalesInvoiceServiceChargeLine
    ) {
        assertSalesInvoiceServiceChargeLineAllUpdatablePropertiesEquals(
            expectedSalesInvoiceServiceChargeLine,
            getPersistedSalesInvoiceServiceChargeLine(expectedSalesInvoiceServiceChargeLine)
        );
    }
}
