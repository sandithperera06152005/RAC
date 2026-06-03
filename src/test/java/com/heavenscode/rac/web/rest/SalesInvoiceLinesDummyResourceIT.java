package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SalesInvoiceLinesDummyAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SalesInvoiceLinesDummy;
import com.heavenscode.rac.repository.SalesInvoiceLinesDummyRepository;
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
 * Integration tests for the {@link SalesInvoiceLinesDummyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesInvoiceLinesDummyResourceIT {

    private static final Integer DEFAULT_INVOICE_ID = 1;
    private static final Integer UPDATED_INVOICE_ID = 2;
    private static final Integer SMALLER_INVOICE_ID = 1 - 1;

    private static final Integer DEFAULT_LINE_ID = 1;
    private static final Integer UPDATED_LINE_ID = 2;
    private static final Integer SMALLER_LINE_ID = 1 - 1;

    private static final Integer DEFAULT_ITEM_ID = 1;
    private static final Integer UPDATED_ITEM_ID = 2;
    private static final Integer SMALLER_ITEM_ID = 1 - 1;

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_OF_MEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_OF_MEASUREMENT = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;
    private static final Float SMALLER_QUANTITY = 1F - 1F;

    private static final Float DEFAULT_ITEM_COST = 1F;
    private static final Float UPDATED_ITEM_COST = 2F;
    private static final Float SMALLER_ITEM_COST = 1F - 1F;

    private static final Float DEFAULT_ITEM_PRICE = 1F;
    private static final Float UPDATED_ITEM_PRICE = 2F;
    private static final Float SMALLER_ITEM_PRICE = 1F - 1F;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;
    private static final Float SMALLER_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;
    private static final Float SMALLER_TAX = 1F - 1F;

    private static final Float DEFAULT_SELLING_PRICE = 1F;
    private static final Float UPDATED_SELLING_PRICE = 2F;
    private static final Float SMALLER_SELLING_PRICE = 1F - 1F;

    private static final Float DEFAULT_LINE_TOTAL = 1F;
    private static final Float UPDATED_LINE_TOTAL = 2F;
    private static final Float SMALLER_LINE_TOTAL = 1F - 1F;

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_NBT = false;
    private static final Boolean UPDATED_NBT = true;

    private static final Boolean DEFAULT_VAT = false;
    private static final Boolean UPDATED_VAT = true;

    private static final String ENTITY_API_URL = "/api/sales-invoice-lines-dummies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SalesInvoiceLinesDummyRepository salesInvoiceLinesDummyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesInvoiceLinesDummyMockMvc;

    private SalesInvoiceLinesDummy salesInvoiceLinesDummy;

    private SalesInvoiceLinesDummy insertedSalesInvoiceLinesDummy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceLinesDummy createEntity() {
        return new SalesInvoiceLinesDummy()
            .invoiceId(DEFAULT_INVOICE_ID)
            .lineId(DEFAULT_LINE_ID)
            .itemId(DEFAULT_ITEM_ID)
            .itemCode(DEFAULT_ITEM_CODE)
            .itemName(DEFAULT_ITEM_NAME)
            .description(DEFAULT_DESCRIPTION)
            .unitOfMeasurement(DEFAULT_UNIT_OF_MEASUREMENT)
            .quantity(DEFAULT_QUANTITY)
            .itemCost(DEFAULT_ITEM_COST)
            .itemPrice(DEFAULT_ITEM_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .tax(DEFAULT_TAX)
            .sellingPrice(DEFAULT_SELLING_PRICE)
            .lineTotal(DEFAULT_LINE_TOTAL)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .nbt(DEFAULT_NBT)
            .vat(DEFAULT_VAT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceLinesDummy createUpdatedEntity() {
        return new SalesInvoiceLinesDummy()
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .itemId(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .itemName(UPDATED_ITEM_NAME)
            .description(UPDATED_DESCRIPTION)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemCost(UPDATED_ITEM_COST)
            .itemPrice(UPDATED_ITEM_PRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .lineTotal(UPDATED_LINE_TOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);
    }

    @BeforeEach
    public void initTest() {
        salesInvoiceLinesDummy = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSalesInvoiceLinesDummy != null) {
            salesInvoiceLinesDummyRepository.delete(insertedSalesInvoiceLinesDummy);
            insertedSalesInvoiceLinesDummy = null;
        }
    }

    @Test
    @Transactional
    void createSalesInvoiceLinesDummy() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SalesInvoiceLinesDummy
        var returnedSalesInvoiceLinesDummy = om.readValue(
            restSalesInvoiceLinesDummyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLinesDummy)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SalesInvoiceLinesDummy.class
        );

        // Validate the SalesInvoiceLinesDummy in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSalesInvoiceLinesDummyUpdatableFieldsEquals(
            returnedSalesInvoiceLinesDummy,
            getPersistedSalesInvoiceLinesDummy(returnedSalesInvoiceLinesDummy)
        );

        insertedSalesInvoiceLinesDummy = returnedSalesInvoiceLinesDummy;
    }

    @Test
    @Transactional
    void createSalesInvoiceLinesDummyWithExistingId() throws Exception {
        // Create the SalesInvoiceLinesDummy with an existing ID
        salesInvoiceLinesDummy.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesInvoiceLinesDummyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLinesDummy)))
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLinesDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummies() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList
        restSalesInvoiceLinesDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceLinesDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID)))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].unitOfMeasurement").value(hasItem(DEFAULT_UNIT_OF_MEASUREMENT)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].itemCost").value(hasItem(DEFAULT_ITEM_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].itemPrice").value(hasItem(DEFAULT_ITEM_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].lineTotal").value(hasItem(DEFAULT_LINE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())));
    }

    @Test
    @Transactional
    void getSalesInvoiceLinesDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get the salesInvoiceLinesDummy
        restSalesInvoiceLinesDummyMockMvc
            .perform(get(ENTITY_API_URL_ID, salesInvoiceLinesDummy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesInvoiceLinesDummy.getId().intValue()))
            .andExpect(jsonPath("$.invoiceId").value(DEFAULT_INVOICE_ID))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.unitOfMeasurement").value(DEFAULT_UNIT_OF_MEASUREMENT))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.itemCost").value(DEFAULT_ITEM_COST.doubleValue()))
            .andExpect(jsonPath("$.itemPrice").value(DEFAULT_ITEM_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.sellingPrice").value(DEFAULT_SELLING_PRICE.doubleValue()))
            .andExpect(jsonPath("$.lineTotal").value(DEFAULT_LINE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.nbt").value(DEFAULT_NBT.booleanValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.booleanValue()));
    }

    @Test
    @Transactional
    void getSalesInvoiceLinesDummiesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        Long id = salesInvoiceLinesDummy.getId();

        defaultSalesInvoiceLinesDummyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSalesInvoiceLinesDummyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSalesInvoiceLinesDummyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByInvoiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where invoiceId equals to
        defaultSalesInvoiceLinesDummyFiltering("invoiceId.equals=" + DEFAULT_INVOICE_ID, "invoiceId.equals=" + UPDATED_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByInvoiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where invoiceId in
        defaultSalesInvoiceLinesDummyFiltering(
            "invoiceId.in=" + DEFAULT_INVOICE_ID + "," + UPDATED_INVOICE_ID,
            "invoiceId.in=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByInvoiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where invoiceId is not null
        defaultSalesInvoiceLinesDummyFiltering("invoiceId.specified=true", "invoiceId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByInvoiceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where invoiceId is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "invoiceId.greaterThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.greaterThanOrEqual=" + UPDATED_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByInvoiceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where invoiceId is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "invoiceId.lessThanOrEqual=" + DEFAULT_INVOICE_ID,
            "invoiceId.lessThanOrEqual=" + SMALLER_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByInvoiceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where invoiceId is less than
        defaultSalesInvoiceLinesDummyFiltering("invoiceId.lessThan=" + UPDATED_INVOICE_ID, "invoiceId.lessThan=" + DEFAULT_INVOICE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByInvoiceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where invoiceId is greater than
        defaultSalesInvoiceLinesDummyFiltering(
            "invoiceId.greaterThan=" + SMALLER_INVOICE_ID,
            "invoiceId.greaterThan=" + DEFAULT_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineId equals to
        defaultSalesInvoiceLinesDummyFiltering("lineId.equals=" + DEFAULT_LINE_ID, "lineId.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineId in
        defaultSalesInvoiceLinesDummyFiltering("lineId.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID, "lineId.in=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineId is not null
        defaultSalesInvoiceLinesDummyFiltering("lineId.specified=true", "lineId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineId is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "lineId.greaterThanOrEqual=" + DEFAULT_LINE_ID,
            "lineId.greaterThanOrEqual=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineId is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering("lineId.lessThanOrEqual=" + DEFAULT_LINE_ID, "lineId.lessThanOrEqual=" + SMALLER_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineId is less than
        defaultSalesInvoiceLinesDummyFiltering("lineId.lessThan=" + UPDATED_LINE_ID, "lineId.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineId is greater than
        defaultSalesInvoiceLinesDummyFiltering("lineId.greaterThan=" + SMALLER_LINE_ID, "lineId.greaterThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemId equals to
        defaultSalesInvoiceLinesDummyFiltering("itemId.equals=" + DEFAULT_ITEM_ID, "itemId.equals=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemId in
        defaultSalesInvoiceLinesDummyFiltering("itemId.in=" + DEFAULT_ITEM_ID + "," + UPDATED_ITEM_ID, "itemId.in=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemId is not null
        defaultSalesInvoiceLinesDummyFiltering("itemId.specified=true", "itemId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemId is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "itemId.greaterThanOrEqual=" + DEFAULT_ITEM_ID,
            "itemId.greaterThanOrEqual=" + UPDATED_ITEM_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemId is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering("itemId.lessThanOrEqual=" + DEFAULT_ITEM_ID, "itemId.lessThanOrEqual=" + SMALLER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemId is less than
        defaultSalesInvoiceLinesDummyFiltering("itemId.lessThan=" + UPDATED_ITEM_ID, "itemId.lessThan=" + DEFAULT_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemId is greater than
        defaultSalesInvoiceLinesDummyFiltering("itemId.greaterThan=" + SMALLER_ITEM_ID, "itemId.greaterThan=" + DEFAULT_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCode equals to
        defaultSalesInvoiceLinesDummyFiltering("itemCode.equals=" + DEFAULT_ITEM_CODE, "itemCode.equals=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCode in
        defaultSalesInvoiceLinesDummyFiltering(
            "itemCode.in=" + DEFAULT_ITEM_CODE + "," + UPDATED_ITEM_CODE,
            "itemCode.in=" + UPDATED_ITEM_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCode is not null
        defaultSalesInvoiceLinesDummyFiltering("itemCode.specified=true", "itemCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCode contains
        defaultSalesInvoiceLinesDummyFiltering("itemCode.contains=" + DEFAULT_ITEM_CODE, "itemCode.contains=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCode does not contain
        defaultSalesInvoiceLinesDummyFiltering(
            "itemCode.doesNotContain=" + UPDATED_ITEM_CODE,
            "itemCode.doesNotContain=" + DEFAULT_ITEM_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemName equals to
        defaultSalesInvoiceLinesDummyFiltering("itemName.equals=" + DEFAULT_ITEM_NAME, "itemName.equals=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemName in
        defaultSalesInvoiceLinesDummyFiltering(
            "itemName.in=" + DEFAULT_ITEM_NAME + "," + UPDATED_ITEM_NAME,
            "itemName.in=" + UPDATED_ITEM_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemName is not null
        defaultSalesInvoiceLinesDummyFiltering("itemName.specified=true", "itemName.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemName contains
        defaultSalesInvoiceLinesDummyFiltering("itemName.contains=" + DEFAULT_ITEM_NAME, "itemName.contains=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemName does not contain
        defaultSalesInvoiceLinesDummyFiltering(
            "itemName.doesNotContain=" + UPDATED_ITEM_NAME,
            "itemName.doesNotContain=" + DEFAULT_ITEM_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where description equals to
        defaultSalesInvoiceLinesDummyFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where description in
        defaultSalesInvoiceLinesDummyFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where description is not null
        defaultSalesInvoiceLinesDummyFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where description contains
        defaultSalesInvoiceLinesDummyFiltering(
            "description.contains=" + DEFAULT_DESCRIPTION,
            "description.contains=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where description does not contain
        defaultSalesInvoiceLinesDummyFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByUnitOfMeasurementIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where unitOfMeasurement equals to
        defaultSalesInvoiceLinesDummyFiltering(
            "unitOfMeasurement.equals=" + DEFAULT_UNIT_OF_MEASUREMENT,
            "unitOfMeasurement.equals=" + UPDATED_UNIT_OF_MEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByUnitOfMeasurementIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where unitOfMeasurement in
        defaultSalesInvoiceLinesDummyFiltering(
            "unitOfMeasurement.in=" + DEFAULT_UNIT_OF_MEASUREMENT + "," + UPDATED_UNIT_OF_MEASUREMENT,
            "unitOfMeasurement.in=" + UPDATED_UNIT_OF_MEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByUnitOfMeasurementIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where unitOfMeasurement is not null
        defaultSalesInvoiceLinesDummyFiltering("unitOfMeasurement.specified=true", "unitOfMeasurement.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByUnitOfMeasurementContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where unitOfMeasurement contains
        defaultSalesInvoiceLinesDummyFiltering(
            "unitOfMeasurement.contains=" + DEFAULT_UNIT_OF_MEASUREMENT,
            "unitOfMeasurement.contains=" + UPDATED_UNIT_OF_MEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByUnitOfMeasurementNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where unitOfMeasurement does not contain
        defaultSalesInvoiceLinesDummyFiltering(
            "unitOfMeasurement.doesNotContain=" + UPDATED_UNIT_OF_MEASUREMENT,
            "unitOfMeasurement.doesNotContain=" + DEFAULT_UNIT_OF_MEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where quantity equals to
        defaultSalesInvoiceLinesDummyFiltering("quantity.equals=" + DEFAULT_QUANTITY, "quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where quantity in
        defaultSalesInvoiceLinesDummyFiltering(
            "quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY,
            "quantity.in=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where quantity is not null
        defaultSalesInvoiceLinesDummyFiltering("quantity.specified=true", "quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where quantity is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.greaterThanOrEqual=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where quantity is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "quantity.lessThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.lessThanOrEqual=" + SMALLER_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where quantity is less than
        defaultSalesInvoiceLinesDummyFiltering("quantity.lessThan=" + UPDATED_QUANTITY, "quantity.lessThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where quantity is greater than
        defaultSalesInvoiceLinesDummyFiltering("quantity.greaterThan=" + SMALLER_QUANTITY, "quantity.greaterThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCost equals to
        defaultSalesInvoiceLinesDummyFiltering("itemCost.equals=" + DEFAULT_ITEM_COST, "itemCost.equals=" + UPDATED_ITEM_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCost in
        defaultSalesInvoiceLinesDummyFiltering(
            "itemCost.in=" + DEFAULT_ITEM_COST + "," + UPDATED_ITEM_COST,
            "itemCost.in=" + UPDATED_ITEM_COST
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCost is not null
        defaultSalesInvoiceLinesDummyFiltering("itemCost.specified=true", "itemCost.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCost is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "itemCost.greaterThanOrEqual=" + DEFAULT_ITEM_COST,
            "itemCost.greaterThanOrEqual=" + UPDATED_ITEM_COST
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCost is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "itemCost.lessThanOrEqual=" + DEFAULT_ITEM_COST,
            "itemCost.lessThanOrEqual=" + SMALLER_ITEM_COST
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCost is less than
        defaultSalesInvoiceLinesDummyFiltering("itemCost.lessThan=" + UPDATED_ITEM_COST, "itemCost.lessThan=" + DEFAULT_ITEM_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemCost is greater than
        defaultSalesInvoiceLinesDummyFiltering("itemCost.greaterThan=" + SMALLER_ITEM_COST, "itemCost.greaterThan=" + DEFAULT_ITEM_COST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemPrice equals to
        defaultSalesInvoiceLinesDummyFiltering("itemPrice.equals=" + DEFAULT_ITEM_PRICE, "itemPrice.equals=" + UPDATED_ITEM_PRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemPrice in
        defaultSalesInvoiceLinesDummyFiltering(
            "itemPrice.in=" + DEFAULT_ITEM_PRICE + "," + UPDATED_ITEM_PRICE,
            "itemPrice.in=" + UPDATED_ITEM_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemPrice is not null
        defaultSalesInvoiceLinesDummyFiltering("itemPrice.specified=true", "itemPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemPrice is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "itemPrice.greaterThanOrEqual=" + DEFAULT_ITEM_PRICE,
            "itemPrice.greaterThanOrEqual=" + UPDATED_ITEM_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemPrice is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "itemPrice.lessThanOrEqual=" + DEFAULT_ITEM_PRICE,
            "itemPrice.lessThanOrEqual=" + SMALLER_ITEM_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemPrice is less than
        defaultSalesInvoiceLinesDummyFiltering("itemPrice.lessThan=" + UPDATED_ITEM_PRICE, "itemPrice.lessThan=" + DEFAULT_ITEM_PRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByItemPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where itemPrice is greater than
        defaultSalesInvoiceLinesDummyFiltering(
            "itemPrice.greaterThan=" + SMALLER_ITEM_PRICE,
            "itemPrice.greaterThan=" + DEFAULT_ITEM_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where discount equals to
        defaultSalesInvoiceLinesDummyFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where discount in
        defaultSalesInvoiceLinesDummyFiltering(
            "discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT,
            "discount.in=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where discount is not null
        defaultSalesInvoiceLinesDummyFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where discount is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where discount is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where discount is less than
        defaultSalesInvoiceLinesDummyFiltering("discount.lessThan=" + UPDATED_DISCOUNT, "discount.lessThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where discount is greater than
        defaultSalesInvoiceLinesDummyFiltering("discount.greaterThan=" + SMALLER_DISCOUNT, "discount.greaterThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where tax equals to
        defaultSalesInvoiceLinesDummyFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where tax in
        defaultSalesInvoiceLinesDummyFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where tax is not null
        defaultSalesInvoiceLinesDummyFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where tax is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where tax is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where tax is less than
        defaultSalesInvoiceLinesDummyFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where tax is greater than
        defaultSalesInvoiceLinesDummyFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesBySellingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where sellingPrice equals to
        defaultSalesInvoiceLinesDummyFiltering(
            "sellingPrice.equals=" + DEFAULT_SELLING_PRICE,
            "sellingPrice.equals=" + UPDATED_SELLING_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesBySellingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where sellingPrice in
        defaultSalesInvoiceLinesDummyFiltering(
            "sellingPrice.in=" + DEFAULT_SELLING_PRICE + "," + UPDATED_SELLING_PRICE,
            "sellingPrice.in=" + UPDATED_SELLING_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesBySellingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where sellingPrice is not null
        defaultSalesInvoiceLinesDummyFiltering("sellingPrice.specified=true", "sellingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesBySellingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where sellingPrice is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "sellingPrice.greaterThanOrEqual=" + DEFAULT_SELLING_PRICE,
            "sellingPrice.greaterThanOrEqual=" + UPDATED_SELLING_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesBySellingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where sellingPrice is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "sellingPrice.lessThanOrEqual=" + DEFAULT_SELLING_PRICE,
            "sellingPrice.lessThanOrEqual=" + SMALLER_SELLING_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesBySellingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where sellingPrice is less than
        defaultSalesInvoiceLinesDummyFiltering(
            "sellingPrice.lessThan=" + UPDATED_SELLING_PRICE,
            "sellingPrice.lessThan=" + DEFAULT_SELLING_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesBySellingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where sellingPrice is greater than
        defaultSalesInvoiceLinesDummyFiltering(
            "sellingPrice.greaterThan=" + SMALLER_SELLING_PRICE,
            "sellingPrice.greaterThan=" + DEFAULT_SELLING_PRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineTotal equals to
        defaultSalesInvoiceLinesDummyFiltering("lineTotal.equals=" + DEFAULT_LINE_TOTAL, "lineTotal.equals=" + UPDATED_LINE_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineTotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineTotal in
        defaultSalesInvoiceLinesDummyFiltering(
            "lineTotal.in=" + DEFAULT_LINE_TOTAL + "," + UPDATED_LINE_TOTAL,
            "lineTotal.in=" + UPDATED_LINE_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineTotal is not null
        defaultSalesInvoiceLinesDummyFiltering("lineTotal.specified=true", "lineTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineTotal is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "lineTotal.greaterThanOrEqual=" + DEFAULT_LINE_TOTAL,
            "lineTotal.greaterThanOrEqual=" + UPDATED_LINE_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineTotal is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering(
            "lineTotal.lessThanOrEqual=" + DEFAULT_LINE_TOTAL,
            "lineTotal.lessThanOrEqual=" + SMALLER_LINE_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineTotal is less than
        defaultSalesInvoiceLinesDummyFiltering("lineTotal.lessThan=" + UPDATED_LINE_TOTAL, "lineTotal.lessThan=" + DEFAULT_LINE_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLineTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lineTotal is greater than
        defaultSalesInvoiceLinesDummyFiltering(
            "lineTotal.greaterThan=" + SMALLER_LINE_TOTAL,
            "lineTotal.greaterThan=" + DEFAULT_LINE_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmu equals to
        defaultSalesInvoiceLinesDummyFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmu in
        defaultSalesInvoiceLinesDummyFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmu is not null
        defaultSalesInvoiceLinesDummyFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmu is greater than or equal to
        defaultSalesInvoiceLinesDummyFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmu is less than or equal to
        defaultSalesInvoiceLinesDummyFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmu is less than
        defaultSalesInvoiceLinesDummyFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmu is greater than
        defaultSalesInvoiceLinesDummyFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmd equals to
        defaultSalesInvoiceLinesDummyFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmd in
        defaultSalesInvoiceLinesDummyFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where lmd is not null
        defaultSalesInvoiceLinesDummyFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByNbtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where nbt equals to
        defaultSalesInvoiceLinesDummyFiltering("nbt.equals=" + DEFAULT_NBT, "nbt.equals=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByNbtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where nbt in
        defaultSalesInvoiceLinesDummyFiltering("nbt.in=" + DEFAULT_NBT + "," + UPDATED_NBT, "nbt.in=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByNbtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where nbt is not null
        defaultSalesInvoiceLinesDummyFiltering("nbt.specified=true", "nbt.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByVatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where vat equals to
        defaultSalesInvoiceLinesDummyFiltering("vat.equals=" + DEFAULT_VAT, "vat.equals=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByVatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where vat in
        defaultSalesInvoiceLinesDummyFiltering("vat.in=" + DEFAULT_VAT + "," + UPDATED_VAT, "vat.in=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesDummiesByVatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        // Get all the salesInvoiceLinesDummyList where vat is not null
        defaultSalesInvoiceLinesDummyFiltering("vat.specified=true", "vat.specified=false");
    }

    private void defaultSalesInvoiceLinesDummyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSalesInvoiceLinesDummyShouldBeFound(shouldBeFound);
        defaultSalesInvoiceLinesDummyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalesInvoiceLinesDummyShouldBeFound(String filter) throws Exception {
        restSalesInvoiceLinesDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceLinesDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceId").value(hasItem(DEFAULT_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID)))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].unitOfMeasurement").value(hasItem(DEFAULT_UNIT_OF_MEASUREMENT)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].itemCost").value(hasItem(DEFAULT_ITEM_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].itemPrice").value(hasItem(DEFAULT_ITEM_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].lineTotal").value(hasItem(DEFAULT_LINE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())));

        // Check, that the count call also returns 1
        restSalesInvoiceLinesDummyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalesInvoiceLinesDummyShouldNotBeFound(String filter) throws Exception {
        restSalesInvoiceLinesDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalesInvoiceLinesDummyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalesInvoiceLinesDummy() throws Exception {
        // Get the salesInvoiceLinesDummy
        restSalesInvoiceLinesDummyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesInvoiceLinesDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLinesDummy
        SalesInvoiceLinesDummy updatedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository
            .findById(salesInvoiceLinesDummy.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSalesInvoiceLinesDummy are not directly saved in db
        em.detach(updatedSalesInvoiceLinesDummy);
        updatedSalesInvoiceLinesDummy
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .itemId(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .itemName(UPDATED_ITEM_NAME)
            .description(UPDATED_DESCRIPTION)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemCost(UPDATED_ITEM_COST)
            .itemPrice(UPDATED_ITEM_PRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .lineTotal(UPDATED_LINE_TOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);

        restSalesInvoiceLinesDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesInvoiceLinesDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSalesInvoiceLinesDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLinesDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSalesInvoiceLinesDummyToMatchAllProperties(updatedSalesInvoiceLinesDummy);
    }

    @Test
    @Transactional
    void putNonExistingSalesInvoiceLinesDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLinesDummy.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesInvoiceLinesDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceLinesDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLinesDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesInvoiceLinesDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLinesDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceLinesDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLinesDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesInvoiceLinesDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLinesDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesDummyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLinesDummy)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceLinesDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesInvoiceLinesDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLinesDummy using partial update
        SalesInvoiceLinesDummy partialUpdatedSalesInvoiceLinesDummy = new SalesInvoiceLinesDummy();
        partialUpdatedSalesInvoiceLinesDummy.setId(salesInvoiceLinesDummy.getId());

        partialUpdatedSalesInvoiceLinesDummy
            .lineId(UPDATED_LINE_ID)
            .itemId(UPDATED_ITEM_ID)
            .description(UPDATED_DESCRIPTION)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .itemCost(UPDATED_ITEM_COST)
            .tax(UPDATED_TAX)
            .lmu(UPDATED_LMU)
            .nbt(UPDATED_NBT);

        restSalesInvoiceLinesDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceLinesDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceLinesDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLinesDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceLinesDummyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSalesInvoiceLinesDummy, salesInvoiceLinesDummy),
            getPersistedSalesInvoiceLinesDummy(salesInvoiceLinesDummy)
        );
    }

    @Test
    @Transactional
    void fullUpdateSalesInvoiceLinesDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLinesDummy using partial update
        SalesInvoiceLinesDummy partialUpdatedSalesInvoiceLinesDummy = new SalesInvoiceLinesDummy();
        partialUpdatedSalesInvoiceLinesDummy.setId(salesInvoiceLinesDummy.getId());

        partialUpdatedSalesInvoiceLinesDummy
            .invoiceId(UPDATED_INVOICE_ID)
            .lineId(UPDATED_LINE_ID)
            .itemId(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .itemName(UPDATED_ITEM_NAME)
            .description(UPDATED_DESCRIPTION)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemCost(UPDATED_ITEM_COST)
            .itemPrice(UPDATED_ITEM_PRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .lineTotal(UPDATED_LINE_TOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);

        restSalesInvoiceLinesDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceLinesDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceLinesDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLinesDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceLinesDummyUpdatableFieldsEquals(
            partialUpdatedSalesInvoiceLinesDummy,
            getPersistedSalesInvoiceLinesDummy(partialUpdatedSalesInvoiceLinesDummy)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSalesInvoiceLinesDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLinesDummy.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesInvoiceLinesDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceLinesDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLinesDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesInvoiceLinesDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLinesDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceLinesDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLinesDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesInvoiceLinesDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLinesDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesDummyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(salesInvoiceLinesDummy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceLinesDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesInvoiceLinesDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLinesDummy = salesInvoiceLinesDummyRepository.saveAndFlush(salesInvoiceLinesDummy);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the salesInvoiceLinesDummy
        restSalesInvoiceLinesDummyMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesInvoiceLinesDummy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return salesInvoiceLinesDummyRepository.count();
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

    protected SalesInvoiceLinesDummy getPersistedSalesInvoiceLinesDummy(SalesInvoiceLinesDummy salesInvoiceLinesDummy) {
        return salesInvoiceLinesDummyRepository.findById(salesInvoiceLinesDummy.getId()).orElseThrow();
    }

    protected void assertPersistedSalesInvoiceLinesDummyToMatchAllProperties(SalesInvoiceLinesDummy expectedSalesInvoiceLinesDummy) {
        assertSalesInvoiceLinesDummyAllPropertiesEquals(
            expectedSalesInvoiceLinesDummy,
            getPersistedSalesInvoiceLinesDummy(expectedSalesInvoiceLinesDummy)
        );
    }

    protected void assertPersistedSalesInvoiceLinesDummyToMatchUpdatableProperties(SalesInvoiceLinesDummy expectedSalesInvoiceLinesDummy) {
        assertSalesInvoiceLinesDummyAllUpdatablePropertiesEquals(
            expectedSalesInvoiceLinesDummy,
            getPersistedSalesInvoiceLinesDummy(expectedSalesInvoiceLinesDummy)
        );
    }
}
