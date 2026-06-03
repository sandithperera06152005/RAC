package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeRepository;
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
 * Integration tests for the {@link SaleInvoiceCommonServiceChargeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleInvoiceCommonServiceChargeResourceIT {

    private static final Integer DEFAULT_INVOICE_ID = 1;
    private static final Integer UPDATED_INVOICE_ID = 2;
    private static final Integer SMALLER_INVOICE_ID = 1 - 1;

    private static final Integer DEFAULT_LINE_ID = 1;
    private static final Integer UPDATED_LINE_ID = 2;
    private static final Integer SMALLER_LINE_ID = 1 - 1;

    private static final Integer DEFAULT_OPTION_ID = 1;
    private static final Integer UPDATED_OPTION_ID = 2;
    private static final Integer SMALLER_OPTION_ID = 1 - 1;

    private static final Integer DEFAULT_MAIN_ID = 1;
    private static final Integer UPDATED_MAIN_ID = 2;
    private static final Integer SMALLER_MAIN_ID = 1 - 1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;
    private static final Float SMALLER_VALUE = 1F - 1F;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;
    private static final Float SMALLER_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_SERVICE_PRICE = 1F;
    private static final Float UPDATED_SERVICE_PRICE = 2F;
    private static final Float SMALLER_SERVICE_PRICE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/sale-invoice-common-service-charges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaleInvoiceCommonServiceChargeRepository saleInvoiceCommonServiceChargeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleInvoiceCommonServiceChargeMockMvc;

    private SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge;

    private SaleInvoiceCommonServiceCharge insertedSaleInvoiceCommonServiceCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleInvoiceCommonServiceCharge createEntity() {
        return new SaleInvoiceCommonServiceCharge()
            .invoiceId(DEFAULT_INVOICE_ID)
            .lineId(DEFAULT_LINE_ID)
            .optionId(DEFAULT_OPTION_ID)
            .mainId(DEFAULT_MAIN_ID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .discount(DEFAULT_DISCOUNT)
            .servicePrice(DEFAULT_SERVICE_PRICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleInvoiceCommonServiceCharge createUpdatedEntity() {
        return new SaleInvoiceCommonServiceCharge()
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .mainId(UPDATED_MAIN_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);
    }

    @BeforeEach
    public void initTest() {
        saleInvoiceCommonServiceCharge = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSaleInvoiceCommonServiceCharge != null) {
            saleInvoiceCommonServiceChargeRepository.delete(insertedSaleInvoiceCommonServiceCharge);
            insertedSaleInvoiceCommonServiceCharge = null;
        }
    }

    @Test
    @Transactional
    void createSaleInvoiceCommonServiceCharge() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaleInvoiceCommonServiceCharge
        var returnedSaleInvoiceCommonServiceCharge = om.readValue(
            restSaleInvoiceCommonServiceChargeMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saleInvoiceCommonServiceCharge))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaleInvoiceCommonServiceCharge.class
        );

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSaleInvoiceCommonServiceChargeUpdatableFieldsEquals(
            returnedSaleInvoiceCommonServiceCharge,
            getPersistedSaleInvoiceCommonServiceCharge(returnedSaleInvoiceCommonServiceCharge)
        );

        insertedSaleInvoiceCommonServiceCharge = returnedSaleInvoiceCommonServiceCharge;
    }

    @Test
    @Transactional
    void createSaleInvoiceCommonServiceChargeWithExistingId() throws Exception {
        // Create the SaleInvoiceCommonServiceCharge with an existing ID
        saleInvoiceCommonServiceCharge.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceCharges() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleInvoiceCommonServiceCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID)))
            .andExpect(jsonPath("$.[*].mainId").value(hasItem(DEFAULT_MAIN_ID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getSaleInvoiceCommonServiceCharge() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get the saleInvoiceCommonServiceCharge
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(get(ENTITY_API_URL_ID, saleInvoiceCommonServiceCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saleInvoiceCommonServiceCharge.getId().intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID))
            .andExpect(jsonPath("$.optionId").value(DEFAULT_OPTION_ID))
            .andExpect(jsonPath("$.mainId").value(DEFAULT_MAIN_ID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.servicePrice").value(DEFAULT_SERVICE_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getSaleInvoiceCommonServiceChargesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        Long id = saleInvoiceCommonServiceCharge.getId();

        defaultSaleInvoiceCommonServiceChargeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSaleInvoiceCommonServiceChargeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSaleInvoiceCommonServiceChargeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByInvoiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where invoiceId equals to
        defaultSaleInvoiceCommonServiceChargeFiltering("invoiceId.equals=" + DEFAULT_INVOICE_ID, "invoiceId.equals=" + UPDATED_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByInvoiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where invoiceId in
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "invoiceId.in=" + DEFAULT_INVOICE_ID + "," + UPDATED_INVOICE_ID,
            "invoiceId.in=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByInvoiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where invoiceId is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("invoiceId.specified=true", "invoiceId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByInvoiceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where invoiceId is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "invoiceId.greaterThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.greaterThanOrEqual=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByInvoiceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where invoiceId is less than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "invoiceId.lessThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.lessThanOrEqual=" + SMALLER_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByInvoiceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where invoiceId is less than
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "invoiceId.lessThan=" + UPDATED_INVOICE_ID,
            "invoiceId.lessThan=" + DEFAULT_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByInvoiceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where invoiceId is greater than
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "invoiceId.greaterThan=" + SMALLER_INVOICE_ID,
            "invoiceId.greaterThan=" + DEFAULT_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where lineId equals to
        defaultSaleInvoiceCommonServiceChargeFiltering("lineId.equals=" + DEFAULT_LINE_ID, "lineId.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where lineId in
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "lineId.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID,
            "lineId.in=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where lineId is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("lineId.specified=true", "lineId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where lineId is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "lineId.greaterThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.greaterThanOrEqual=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where lineId is less than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "lineId.lessThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.lessThanOrEqual=" + SMALLER_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where lineId is less than
        defaultSaleInvoiceCommonServiceChargeFiltering("lineId.lessThan=" + UPDATED_LINE_ID, "lineId.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where lineId is greater than
        defaultSaleInvoiceCommonServiceChargeFiltering("lineId.greaterThan=" + SMALLER_LINE_ID, "lineId.greaterThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByOptionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where optionId equals to
        defaultSaleInvoiceCommonServiceChargeFiltering("optionId.equals=" + DEFAULT_OPTION_ID, "optionId.equals=" + UPDATED_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByOptionIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where optionId in
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "optionId.in=" + DEFAULT_OPTION_ID + "," + UPDATED_OPTION_ID,
            "optionId.in=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByOptionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where optionId is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("optionId.specified=true", "optionId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByOptionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where optionId is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "optionId.greaterThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.greaterThanOrEqual=" + UPDATED_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByOptionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where optionId is less than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "optionId.lessThanOrEqual=" + DEFAULT_OPTION_ID,
            "optionId.lessThanOrEqual=" + SMALLER_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByOptionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where optionId is less than
        defaultSaleInvoiceCommonServiceChargeFiltering("optionId.lessThan=" + UPDATED_OPTION_ID, "optionId.lessThan=" + DEFAULT_OPTION_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByOptionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where optionId is greater than
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "optionId.greaterThan=" + SMALLER_OPTION_ID,
            "optionId.greaterThan=" + DEFAULT_OPTION_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByMainIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where mainId equals to
        defaultSaleInvoiceCommonServiceChargeFiltering("mainId.equals=" + DEFAULT_MAIN_ID, "mainId.equals=" + UPDATED_MAIN_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByMainIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where mainId in
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "mainId.in=" + DEFAULT_MAIN_ID + "," + UPDATED_MAIN_ID,
            "mainId.in=" + UPDATED_MAIN_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByMainIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where mainId is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("mainId.specified=true", "mainId.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByMainIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where mainId is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "mainId.greaterThanOrEqual=" + DEFAULT_MAIN_ID,
            "mainId.greaterThanOrEqual=" + UPDATED_MAIN_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByMainIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where mainId is less than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "mainId.lessThanOrEqual=" + DEFAULT_MAIN_ID,
            "mainId.lessThanOrEqual=" + SMALLER_MAIN_ID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByMainIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where mainId is less than
        defaultSaleInvoiceCommonServiceChargeFiltering("mainId.lessThan=" + UPDATED_MAIN_ID, "mainId.lessThan=" + DEFAULT_MAIN_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByMainIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where mainId is greater than
        defaultSaleInvoiceCommonServiceChargeFiltering("mainId.greaterThan=" + SMALLER_MAIN_ID, "mainId.greaterThan=" + DEFAULT_MAIN_ID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where code equals to
        defaultSaleInvoiceCommonServiceChargeFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where code in
        defaultSaleInvoiceCommonServiceChargeFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where code is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where code contains
        defaultSaleInvoiceCommonServiceChargeFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where code does not contain
        defaultSaleInvoiceCommonServiceChargeFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where name equals to
        defaultSaleInvoiceCommonServiceChargeFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where name in
        defaultSaleInvoiceCommonServiceChargeFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where name is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where name contains
        defaultSaleInvoiceCommonServiceChargeFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where name does not contain
        defaultSaleInvoiceCommonServiceChargeFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where description equals to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "description.equals=" + DEFAULT_DESCRIPTION,
            "description.equals=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where description in
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where description is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where description contains
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "description.contains=" + DEFAULT_DESCRIPTION,
            "description.contains=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where description does not contain
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where value equals to
        defaultSaleInvoiceCommonServiceChargeFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where value in
        defaultSaleInvoiceCommonServiceChargeFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where value is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where value is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "value.greaterThanOrEqual=" + DEFAULT_VALUE,
            "value.greaterThanOrEqual=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where value is less than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering("value.lessThanOrEqual=" + DEFAULT_VALUE, "value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where value is less than
        defaultSaleInvoiceCommonServiceChargeFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where value is greater than
        defaultSaleInvoiceCommonServiceChargeFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where discount equals to
        defaultSaleInvoiceCommonServiceChargeFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where discount in
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT,
            "discount.in=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where discount is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where discount is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where discount is less than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where discount is less than
        defaultSaleInvoiceCommonServiceChargeFiltering("discount.lessThan=" + UPDATED_DISCOUNT, "discount.lessThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where discount is greater than
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "discount.greaterThan=" + SMALLER_DISCOUNT,
            "discount.greaterThan=" + DEFAULT_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByServicePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where servicePrice equals to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "servicePrice.equals=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.equals=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByServicePriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where servicePrice in
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "servicePrice.in=" + DEFAULT_SERVICE_PRICE + "," + UPDATED_SERVICE_PRICE,
            "servicePrice.in=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByServicePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where servicePrice is not null
        defaultSaleInvoiceCommonServiceChargeFiltering("servicePrice.specified=true", "servicePrice.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByServicePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where servicePrice is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "servicePrice.greaterThanOrEqual=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.greaterThanOrEqual=" + UPDATED_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByServicePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where servicePrice is less than or equal to
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "servicePrice.lessThanOrEqual=" + DEFAULT_SERVICE_PRICE,
            "servicePrice.lessThanOrEqual=" + SMALLER_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByServicePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where servicePrice is less than
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "servicePrice.lessThan=" + UPDATED_SERVICE_PRICE,
            "servicePrice.lessThan=" + DEFAULT_SERVICE_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargesByServicePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        // Get all the saleInvoiceCommonServiceChargeList where servicePrice is greater than
        defaultSaleInvoiceCommonServiceChargeFiltering(
            "servicePrice.greaterThan=" + SMALLER_SERVICE_PRICE,
            "servicePrice.greaterThan=" + DEFAULT_SERVICE_PRICE
        );
    }

    private void defaultSaleInvoiceCommonServiceChargeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSaleInvoiceCommonServiceChargeShouldBeFound(shouldBeFound);
        defaultSaleInvoiceCommonServiceChargeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSaleInvoiceCommonServiceChargeShouldBeFound(String filter) throws Exception {
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleInvoiceCommonServiceCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].optionId").value(hasItem(DEFAULT_OPTION_ID)))
            .andExpect(jsonPath("$.[*].mainId").value(hasItem(DEFAULT_MAIN_ID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePrice").value(hasItem(DEFAULT_SERVICE_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSaleInvoiceCommonServiceChargeShouldNotBeFound(String filter) throws Exception {
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSaleInvoiceCommonServiceCharge() throws Exception {
        // Get the saleInvoiceCommonServiceCharge
        restSaleInvoiceCommonServiceChargeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSaleInvoiceCommonServiceCharge() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceCharge
        SaleInvoiceCommonServiceCharge updatedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository
            .findById(saleInvoiceCommonServiceCharge.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSaleInvoiceCommonServiceCharge are not directly saved in db
        em.detach(updatedSaleInvoiceCommonServiceCharge);
        updatedSaleInvoiceCommonServiceCharge
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .mainId(UPDATED_MAIN_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSaleInvoiceCommonServiceCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSaleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaleInvoiceCommonServiceChargeToMatchAllProperties(updatedSaleInvoiceCommonServiceCharge);
    }

    @Test
    @Transactional
    void putNonExistingSaleInvoiceCommonServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceCharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleInvoiceCommonServiceCharge.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaleInvoiceCommonServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaleInvoiceCommonServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(saleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleInvoiceCommonServiceChargeWithPatch() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceCharge using partial update
        SaleInvoiceCommonServiceCharge partialUpdatedSaleInvoiceCommonServiceCharge = new SaleInvoiceCommonServiceCharge();
        partialUpdatedSaleInvoiceCommonServiceCharge.setId(saleInvoiceCommonServiceCharge.getId());

        partialUpdatedSaleInvoiceCommonServiceCharge
            .lineId(UPDATED_LINE_ID)
            .mainId(UPDATED_MAIN_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleInvoiceCommonServiceCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceCharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaleInvoiceCommonServiceChargeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaleInvoiceCommonServiceCharge, saleInvoiceCommonServiceCharge),
            getPersistedSaleInvoiceCommonServiceCharge(saleInvoiceCommonServiceCharge)
        );
    }

    @Test
    @Transactional
    void fullUpdateSaleInvoiceCommonServiceChargeWithPatch() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceCharge using partial update
        SaleInvoiceCommonServiceCharge partialUpdatedSaleInvoiceCommonServiceCharge = new SaleInvoiceCommonServiceCharge();
        partialUpdatedSaleInvoiceCommonServiceCharge.setId(saleInvoiceCommonServiceCharge.getId());

        partialUpdatedSaleInvoiceCommonServiceCharge
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .optionId(UPDATED_OPTION_ID)
            .mainId(UPDATED_MAIN_ID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .discount(UPDATED_DISCOUNT)
            .servicePrice(UPDATED_SERVICE_PRICE);

        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleInvoiceCommonServiceCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceCharge in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaleInvoiceCommonServiceChargeUpdatableFieldsEquals(
            partialUpdatedSaleInvoiceCommonServiceCharge,
            getPersistedSaleInvoiceCommonServiceCharge(partialUpdatedSaleInvoiceCommonServiceCharge)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSaleInvoiceCommonServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceCharge.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saleInvoiceCommonServiceCharge.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaleInvoiceCommonServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaleInvoiceCommonServiceCharge() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceCharge.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceCharge))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleInvoiceCommonServiceCharge in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaleInvoiceCommonServiceCharge() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceCharge = saleInvoiceCommonServiceChargeRepository.saveAndFlush(saleInvoiceCommonServiceCharge);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saleInvoiceCommonServiceCharge
        restSaleInvoiceCommonServiceChargeMockMvc
            .perform(delete(ENTITY_API_URL_ID, saleInvoiceCommonServiceCharge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saleInvoiceCommonServiceChargeRepository.count();
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

    protected SaleInvoiceCommonServiceCharge getPersistedSaleInvoiceCommonServiceCharge(
        SaleInvoiceCommonServiceCharge saleInvoiceCommonServiceCharge
    ) {
        return saleInvoiceCommonServiceChargeRepository.findById(saleInvoiceCommonServiceCharge.getId()).orElseThrow();
    }

    protected void assertPersistedSaleInvoiceCommonServiceChargeToMatchAllProperties(
        SaleInvoiceCommonServiceCharge expectedSaleInvoiceCommonServiceCharge
    ) {
        assertSaleInvoiceCommonServiceChargeAllPropertiesEquals(
            expectedSaleInvoiceCommonServiceCharge,
            getPersistedSaleInvoiceCommonServiceCharge(expectedSaleInvoiceCommonServiceCharge)
        );
    }

    protected void assertPersistedSaleInvoiceCommonServiceChargeToMatchUpdatableProperties(
        SaleInvoiceCommonServiceCharge expectedSaleInvoiceCommonServiceCharge
    ) {
        assertSaleInvoiceCommonServiceChargeAllUpdatablePropertiesEquals(
            expectedSaleInvoiceCommonServiceCharge,
            getPersistedSaleInvoiceCommonServiceCharge(expectedSaleInvoiceCommonServiceCharge)
        );
    }
}
