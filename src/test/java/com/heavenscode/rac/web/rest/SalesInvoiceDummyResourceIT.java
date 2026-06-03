package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SalesInvoiceDummyAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SalesInvoiceDummy;
import com.heavenscode.rac.repository.SalesInvoiceDummyRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
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
 * Integration tests for the {@link SalesInvoiceDummyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesInvoiceDummyResourceIT {

    private static final Integer DEFAULT_ORIGINAL_INVOICE_ID = 1;
    private static final Integer UPDATED_ORIGINAL_INVOICE_ID = 2;
    private static final Integer SMALLER_ORIGINAL_INVOICE_ID = 1 - 1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_INVOICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUOTE_ID = 1;
    private static final Integer UPDATED_QUOTE_ID = 2;
    private static final Integer SMALLER_QUOTE_ID = 1 - 1;

    private static final Integer DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_ORDER_ID = 2;
    private static final Integer SMALLER_ORDER_ID = 1 - 1;

    private static final Instant DEFAULT_DELIVERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIVERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SALES_REP_ID = 1;
    private static final Integer UPDATED_SALES_REP_ID = 2;
    private static final Integer SMALLER_SALES_REP_ID = 1 - 1;

    private static final String DEFAULT_SALES_REP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SALES_REP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVER_FROM = "AAAAAAAAAA";
    private static final String UPDATED_DELIVER_FROM = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;
    private static final Integer SMALLER_CUSTOMER_ID = 1 - 1;

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ADDRESS = "BBBBBBBBBB";

    private static final Float DEFAULT_SUB_TOTAL = 1F;
    private static final Float UPDATED_SUB_TOTAL = 2F;
    private static final Float SMALLER_SUB_TOTAL = 1F - 1F;

    private static final Float DEFAULT_TOTAL_TAX = 1F;
    private static final Float UPDATED_TOTAL_TAX = 2F;
    private static final Float SMALLER_TOTAL_TAX = 1F - 1F;

    private static final Float DEFAULT_TOTAL_DISCOUNT = 1F;
    private static final Float UPDATED_TOTAL_DISCOUNT = 2F;
    private static final Float SMALLER_TOTAL_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_NET_TOTAL = 1F;
    private static final Float UPDATED_NET_TOTAL = 2F;
    private static final Float SMALLER_NET_TOTAL = 1F - 1F;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_PAID_AMOUNT = 1F;
    private static final Float UPDATED_PAID_AMOUNT = 2F;
    private static final Float SMALLER_PAID_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_AMOUNT_OWING = 1F;
    private static final Float UPDATED_AMOUNT_OWING = 2F;
    private static final Float SMALLER_AMOUNT_OWING = 1F - 1F;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Integer DEFAULT_LOCATION_ID = 1;
    private static final Integer UPDATED_LOCATION_ID = 2;
    private static final Integer SMALLER_LOCATION_ID = 1 - 1;

    private static final String DEFAULT_LOCATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATED_BY_ID = 1;
    private static final Integer UPDATED_CREATED_BY_ID = 2;
    private static final Integer SMALLER_CREATED_BY_ID = 1 - 1;

    private static final String DEFAULT_CREATED_BY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_AUTO_CARE_CHARGES = 1F;
    private static final Float UPDATED_AUTO_CARE_CHARGES = 2F;
    private static final Float SMALLER_AUTO_CARE_CHARGES = 1F - 1F;

    private static final Integer DEFAULT_AUTO_CARE_JOB_ID = 1;
    private static final Integer UPDATED_AUTO_CARE_JOB_ID = 2;
    private static final Integer SMALLER_AUTO_CARE_JOB_ID = 1 - 1;

    private static final String DEFAULT_VEHICLE_NO = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_NO = "BBBBBBBBBB";

    private static final Float DEFAULT_NBT_AMOUNT = 1F;
    private static final Float UPDATED_NBT_AMOUNT = 2F;
    private static final Float SMALLER_NBT_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_VAT_AMOUNT = 1F;
    private static final Float UPDATED_VAT_AMOUNT = 2F;
    private static final Float SMALLER_VAT_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_DUMMY_COMMISSION = 1F;
    private static final Float UPDATED_DUMMY_COMMISSION = 2F;
    private static final Float SMALLER_DUMMY_COMMISSION = 1F - 1F;

    private static final Instant DEFAULT_COMMISSION_PAID_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMMISSION_PAID_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_PAID_COMMISSION = 1F;
    private static final Float UPDATED_PAID_COMMISSION = 2F;
    private static final Float SMALLER_PAID_COMMISSION = 1F - 1F;

    private static final Integer DEFAULT_PAID_BY = 1;
    private static final Integer UPDATED_PAID_BY = 2;
    private static final Integer SMALLER_PAID_BY = 1 - 1;

    private static final String DEFAULT_ORIGINAL_INVOICE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_INVOICE_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sales-invoice-dummies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SalesInvoiceDummyRepository salesInvoiceDummyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesInvoiceDummyMockMvc;

    private SalesInvoiceDummy salesInvoiceDummy;

    private SalesInvoiceDummy insertedSalesInvoiceDummy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceDummy createEntity() {
        return new SalesInvoiceDummy()
            .originalInvoiceId(DEFAULT_ORIGINAL_INVOICE_ID)
            .code(DEFAULT_CODE)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .createdDate(DEFAULT_CREATED_DATE)
            .quoteID(DEFAULT_QUOTE_ID)
            .orderID(DEFAULT_ORDER_ID)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .salesRepID(DEFAULT_SALES_REP_ID)
            .salesRepName(DEFAULT_SALES_REP_NAME)
            .deliverFrom(DEFAULT_DELIVER_FROM)
            .customerID(DEFAULT_CUSTOMER_ID)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .customerAddress(DEFAULT_CUSTOMER_ADDRESS)
            .deliveryAddress(DEFAULT_DELIVERY_ADDRESS)
            .subTotal(DEFAULT_SUB_TOTAL)
            .totalTax(DEFAULT_TOTAL_TAX)
            .totalDiscount(DEFAULT_TOTAL_DISCOUNT)
            .netTotal(DEFAULT_NET_TOTAL)
            .message(DEFAULT_MESSAGE)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .paidAmount(DEFAULT_PAID_AMOUNT)
            .amountOwing(DEFAULT_AMOUNT_OWING)
            .isActive(DEFAULT_IS_ACTIVE)
            .locationID(DEFAULT_LOCATION_ID)
            .locationCode(DEFAULT_LOCATION_CODE)
            .referenceCode(DEFAULT_REFERENCE_CODE)
            .createdById(DEFAULT_CREATED_BY_ID)
            .createdByName(DEFAULT_CREATED_BY_NAME)
            .autoCareCharges(DEFAULT_AUTO_CARE_CHARGES)
            .autoCareJobId(DEFAULT_AUTO_CARE_JOB_ID)
            .vehicleNo(DEFAULT_VEHICLE_NO)
            .nbtAmount(DEFAULT_NBT_AMOUNT)
            .vatAmount(DEFAULT_VAT_AMOUNT)
            .dummyCommission(DEFAULT_DUMMY_COMMISSION)
            .commissionPaidDate(DEFAULT_COMMISSION_PAID_DATE)
            .paidCommission(DEFAULT_PAID_COMMISSION)
            .paidBy(DEFAULT_PAID_BY)
            .originalInvoiceCode(DEFAULT_ORIGINAL_INVOICE_CODE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceDummy createUpdatedEntity() {
        return new SalesInvoiceDummy()
            .originalInvoiceId(UPDATED_ORIGINAL_INVOICE_ID)
            .code(UPDATED_CODE)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .quoteID(UPDATED_QUOTE_ID)
            .orderID(UPDATED_ORDER_ID)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .salesRepID(UPDATED_SALES_REP_ID)
            .salesRepName(UPDATED_SALES_REP_NAME)
            .deliverFrom(UPDATED_DELIVER_FROM)
            .customerID(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .subTotal(UPDATED_SUB_TOTAL)
            .totalTax(UPDATED_TOTAL_TAX)
            .totalDiscount(UPDATED_TOTAL_DISCOUNT)
            .netTotal(UPDATED_NET_TOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .amountOwing(UPDATED_AMOUNT_OWING)
            .isActive(UPDATED_IS_ACTIVE)
            .locationID(UPDATED_LOCATION_ID)
            .locationCode(UPDATED_LOCATION_CODE)
            .referenceCode(UPDATED_REFERENCE_CODE)
            .createdById(UPDATED_CREATED_BY_ID)
            .createdByName(UPDATED_CREATED_BY_NAME)
            .autoCareCharges(UPDATED_AUTO_CARE_CHARGES)
            .autoCareJobId(UPDATED_AUTO_CARE_JOB_ID)
            .vehicleNo(UPDATED_VEHICLE_NO)
            .nbtAmount(UPDATED_NBT_AMOUNT)
            .vatAmount(UPDATED_VAT_AMOUNT)
            .dummyCommission(UPDATED_DUMMY_COMMISSION)
            .commissionPaidDate(UPDATED_COMMISSION_PAID_DATE)
            .paidCommission(UPDATED_PAID_COMMISSION)
            .paidBy(UPDATED_PAID_BY)
            .originalInvoiceCode(UPDATED_ORIGINAL_INVOICE_CODE);
    }

    @BeforeEach
    public void initTest() {
        salesInvoiceDummy = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSalesInvoiceDummy != null) {
            salesInvoiceDummyRepository.delete(insertedSalesInvoiceDummy);
            insertedSalesInvoiceDummy = null;
        }
    }

    @Test
    @Transactional
    void createSalesInvoiceDummy() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SalesInvoiceDummy
        var returnedSalesInvoiceDummy = om.readValue(
            restSalesInvoiceDummyMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceDummy)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SalesInvoiceDummy.class
        );

        // Validate the SalesInvoiceDummy in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSalesInvoiceDummyUpdatableFieldsEquals(returnedSalesInvoiceDummy, getPersistedSalesInvoiceDummy(returnedSalesInvoiceDummy));

        insertedSalesInvoiceDummy = returnedSalesInvoiceDummy;
    }

    @Test
    @Transactional
    void createSalesInvoiceDummyWithExistingId() throws Exception {
        // Create the SalesInvoiceDummy with an existing ID
        salesInvoiceDummy.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesInvoiceDummyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceDummy)))
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummies() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList
        restSalesInvoiceDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].originalInvoiceId").value(hasItem(DEFAULT_ORIGINAL_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].quoteID").value(hasItem(DEFAULT_QUOTE_ID)))
            .andExpect(jsonPath("$.[*].orderID").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].salesRepID").value(hasItem(DEFAULT_SALES_REP_ID)))
            .andExpect(jsonPath("$.[*].salesRepName").value(hasItem(DEFAULT_SALES_REP_NAME)))
            .andExpect(jsonPath("$.[*].deliverFrom").value(hasItem(DEFAULT_DELIVER_FROM)))
            .andExpect(jsonPath("$.[*].customerID").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerAddress").value(hasItem(DEFAULT_CUSTOMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].deliveryAddress").value(hasItem(DEFAULT_DELIVERY_ADDRESS)))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTax").value(hasItem(DEFAULT_TOTAL_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalDiscount").value(hasItem(DEFAULT_TOTAL_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].netTotal").value(hasItem(DEFAULT_NET_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountOwing").value(hasItem(DEFAULT_AMOUNT_OWING.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].locationID").value(hasItem(DEFAULT_LOCATION_ID)))
            .andExpect(jsonPath("$.[*].locationCode").value(hasItem(DEFAULT_LOCATION_CODE)))
            .andExpect(jsonPath("$.[*].referenceCode").value(hasItem(DEFAULT_REFERENCE_CODE)))
            .andExpect(jsonPath("$.[*].createdById").value(hasItem(DEFAULT_CREATED_BY_ID)))
            .andExpect(jsonPath("$.[*].createdByName").value(hasItem(DEFAULT_CREATED_BY_NAME)))
            .andExpect(jsonPath("$.[*].autoCareCharges").value(hasItem(DEFAULT_AUTO_CARE_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].autoCareJobId").value(hasItem(DEFAULT_AUTO_CARE_JOB_ID)))
            .andExpect(jsonPath("$.[*].vehicleNo").value(hasItem(DEFAULT_VEHICLE_NO)))
            .andExpect(jsonPath("$.[*].nbtAmount").value(hasItem(DEFAULT_NBT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vatAmount").value(hasItem(DEFAULT_VAT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dummyCommission").value(hasItem(DEFAULT_DUMMY_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].commissionPaidDate").value(hasItem(DEFAULT_COMMISSION_PAID_DATE.toString())))
            .andExpect(jsonPath("$.[*].paidCommission").value(hasItem(DEFAULT_PAID_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].paidBy").value(hasItem(DEFAULT_PAID_BY)))
            .andExpect(jsonPath("$.[*].originalInvoiceCode").value(hasItem(DEFAULT_ORIGINAL_INVOICE_CODE)));
    }

    @Test
    @Transactional
    void getSalesInvoiceDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get the salesInvoiceDummy
        restSalesInvoiceDummyMockMvc
            .perform(get(ENTITY_API_URL_ID, salesInvoiceDummy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesInvoiceDummy.getId().intValue()))
            .andExpect(jsonPath("$.originalInvoiceId").value(DEFAULT_ORIGINAL_INVOICE_ID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.quoteID").value(DEFAULT_QUOTE_ID))
            .andExpect(jsonPath("$.orderID").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.salesRepID").value(DEFAULT_SALES_REP_ID))
            .andExpect(jsonPath("$.salesRepName").value(DEFAULT_SALES_REP_NAME))
            .andExpect(jsonPath("$.deliverFrom").value(DEFAULT_DELIVER_FROM))
            .andExpect(jsonPath("$.customerID").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.customerAddress").value(DEFAULT_CUSTOMER_ADDRESS))
            .andExpect(jsonPath("$.deliveryAddress").value(DEFAULT_DELIVERY_ADDRESS))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.totalTax").value(DEFAULT_TOTAL_TAX.doubleValue()))
            .andExpect(jsonPath("$.totalDiscount").value(DEFAULT_TOTAL_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.netTotal").value(DEFAULT_NET_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.paidAmount").value(DEFAULT_PAID_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountOwing").value(DEFAULT_AMOUNT_OWING.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.locationID").value(DEFAULT_LOCATION_ID))
            .andExpect(jsonPath("$.locationCode").value(DEFAULT_LOCATION_CODE))
            .andExpect(jsonPath("$.referenceCode").value(DEFAULT_REFERENCE_CODE))
            .andExpect(jsonPath("$.createdById").value(DEFAULT_CREATED_BY_ID))
            .andExpect(jsonPath("$.createdByName").value(DEFAULT_CREATED_BY_NAME))
            .andExpect(jsonPath("$.autoCareCharges").value(DEFAULT_AUTO_CARE_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.autoCareJobId").value(DEFAULT_AUTO_CARE_JOB_ID))
            .andExpect(jsonPath("$.vehicleNo").value(DEFAULT_VEHICLE_NO))
            .andExpect(jsonPath("$.nbtAmount").value(DEFAULT_NBT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.vatAmount").value(DEFAULT_VAT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dummyCommission").value(DEFAULT_DUMMY_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.commissionPaidDate").value(DEFAULT_COMMISSION_PAID_DATE.toString()))
            .andExpect(jsonPath("$.paidCommission").value(DEFAULT_PAID_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.paidBy").value(DEFAULT_PAID_BY))
            .andExpect(jsonPath("$.originalInvoiceCode").value(DEFAULT_ORIGINAL_INVOICE_CODE));
    }

    @Test
    @Transactional
    void getSalesInvoiceDummiesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        Integer id = salesInvoiceDummy.getId();

        defaultSalesInvoiceDummyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSalesInvoiceDummyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSalesInvoiceDummyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceId equals to
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceId.equals=" + DEFAULT_ORIGINAL_INVOICE_ID,
            "originalInvoiceId.equals=" + UPDATED_ORIGINAL_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceId in
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceId.in=" + DEFAULT_ORIGINAL_INVOICE_ID + "," + UPDATED_ORIGINAL_INVOICE_ID,
            "originalInvoiceId.in=" + UPDATED_ORIGINAL_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceId is not null
        defaultSalesInvoiceDummyFiltering("originalInvoiceId.specified=true", "originalInvoiceId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceId is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceId.greaterThanOrEqual=" + DEFAULT_ORIGINAL_INVOICE_ID,
            "originalInvoiceId.greaterThanOrEqual=" + UPDATED_ORIGINAL_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceId is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceId.lessThanOrEqual=" + DEFAULT_ORIGINAL_INVOICE_ID,
            "originalInvoiceId.lessThanOrEqual=" + SMALLER_ORIGINAL_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceId is less than
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceId.lessThan=" + UPDATED_ORIGINAL_INVOICE_ID,
            "originalInvoiceId.lessThan=" + DEFAULT_ORIGINAL_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceId is greater than
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceId.greaterThan=" + SMALLER_ORIGINAL_INVOICE_ID,
            "originalInvoiceId.greaterThan=" + DEFAULT_ORIGINAL_INVOICE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where code equals to
        defaultSalesInvoiceDummyFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where code in
        defaultSalesInvoiceDummyFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where code is not null
        defaultSalesInvoiceDummyFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where code contains
        defaultSalesInvoiceDummyFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where code does not contain
        defaultSalesInvoiceDummyFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where invoiceDate equals to
        defaultSalesInvoiceDummyFiltering("invoiceDate.equals=" + DEFAULT_INVOICE_DATE, "invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where invoiceDate in
        defaultSalesInvoiceDummyFiltering(
            "invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE,
            "invoiceDate.in=" + UPDATED_INVOICE_DATE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where invoiceDate is not null
        defaultSalesInvoiceDummyFiltering("invoiceDate.specified=true", "invoiceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdDate equals to
        defaultSalesInvoiceDummyFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdDate in
        defaultSalesInvoiceDummyFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdDate is not null
        defaultSalesInvoiceDummyFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByQuoteIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where quoteID equals to
        defaultSalesInvoiceDummyFiltering("quoteID.equals=" + DEFAULT_QUOTE_ID, "quoteID.equals=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByQuoteIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where quoteID in
        defaultSalesInvoiceDummyFiltering("quoteID.in=" + DEFAULT_QUOTE_ID + "," + UPDATED_QUOTE_ID, "quoteID.in=" + UPDATED_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByQuoteIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where quoteID is not null
        defaultSalesInvoiceDummyFiltering("quoteID.specified=true", "quoteID.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByQuoteIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where quoteID is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "quoteID.greaterThanOrEqual=" + DEFAULT_QUOTE_ID,
            "quoteID.greaterThanOrEqual=" + UPDATED_QUOTE_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByQuoteIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where quoteID is less than or equal to
        defaultSalesInvoiceDummyFiltering("quoteID.lessThanOrEqual=" + DEFAULT_QUOTE_ID, "quoteID.lessThanOrEqual=" + SMALLER_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByQuoteIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where quoteID is less than
        defaultSalesInvoiceDummyFiltering("quoteID.lessThan=" + UPDATED_QUOTE_ID, "quoteID.lessThan=" + DEFAULT_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByQuoteIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where quoteID is greater than
        defaultSalesInvoiceDummyFiltering("quoteID.greaterThan=" + SMALLER_QUOTE_ID, "quoteID.greaterThan=" + DEFAULT_QUOTE_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOrderIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where orderID equals to
        defaultSalesInvoiceDummyFiltering("orderID.equals=" + DEFAULT_ORDER_ID, "orderID.equals=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOrderIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where orderID in
        defaultSalesInvoiceDummyFiltering("orderID.in=" + DEFAULT_ORDER_ID + "," + UPDATED_ORDER_ID, "orderID.in=" + UPDATED_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOrderIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where orderID is not null
        defaultSalesInvoiceDummyFiltering("orderID.specified=true", "orderID.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOrderIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where orderID is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "orderID.greaterThanOrEqual=" + DEFAULT_ORDER_ID,
            "orderID.greaterThanOrEqual=" + UPDATED_ORDER_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOrderIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where orderID is less than or equal to
        defaultSalesInvoiceDummyFiltering("orderID.lessThanOrEqual=" + DEFAULT_ORDER_ID, "orderID.lessThanOrEqual=" + SMALLER_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOrderIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where orderID is less than
        defaultSalesInvoiceDummyFiltering("orderID.lessThan=" + UPDATED_ORDER_ID, "orderID.lessThan=" + DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOrderIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where orderID is greater than
        defaultSalesInvoiceDummyFiltering("orderID.greaterThan=" + SMALLER_ORDER_ID, "orderID.greaterThan=" + DEFAULT_ORDER_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliveryDate equals to
        defaultSalesInvoiceDummyFiltering("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE, "deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliveryDate in
        defaultSalesInvoiceDummyFiltering(
            "deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE,
            "deliveryDate.in=" + UPDATED_DELIVERY_DATE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliveryDate is not null
        defaultSalesInvoiceDummyFiltering("deliveryDate.specified=true", "deliveryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepID equals to
        defaultSalesInvoiceDummyFiltering("salesRepID.equals=" + DEFAULT_SALES_REP_ID, "salesRepID.equals=" + UPDATED_SALES_REP_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepID in
        defaultSalesInvoiceDummyFiltering(
            "salesRepID.in=" + DEFAULT_SALES_REP_ID + "," + UPDATED_SALES_REP_ID,
            "salesRepID.in=" + UPDATED_SALES_REP_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepID is not null
        defaultSalesInvoiceDummyFiltering("salesRepID.specified=true", "salesRepID.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepID is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "salesRepID.greaterThanOrEqual=" + DEFAULT_SALES_REP_ID,
            "salesRepID.greaterThanOrEqual=" + UPDATED_SALES_REP_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepID is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "salesRepID.lessThanOrEqual=" + DEFAULT_SALES_REP_ID,
            "salesRepID.lessThanOrEqual=" + SMALLER_SALES_REP_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepID is less than
        defaultSalesInvoiceDummyFiltering("salesRepID.lessThan=" + UPDATED_SALES_REP_ID, "salesRepID.lessThan=" + DEFAULT_SALES_REP_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepID is greater than
        defaultSalesInvoiceDummyFiltering(
            "salesRepID.greaterThan=" + SMALLER_SALES_REP_ID,
            "salesRepID.greaterThan=" + DEFAULT_SALES_REP_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepName equals to
        defaultSalesInvoiceDummyFiltering("salesRepName.equals=" + DEFAULT_SALES_REP_NAME, "salesRepName.equals=" + UPDATED_SALES_REP_NAME);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepName in
        defaultSalesInvoiceDummyFiltering(
            "salesRepName.in=" + DEFAULT_SALES_REP_NAME + "," + UPDATED_SALES_REP_NAME,
            "salesRepName.in=" + UPDATED_SALES_REP_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepName is not null
        defaultSalesInvoiceDummyFiltering("salesRepName.specified=true", "salesRepName.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepName contains
        defaultSalesInvoiceDummyFiltering(
            "salesRepName.contains=" + DEFAULT_SALES_REP_NAME,
            "salesRepName.contains=" + UPDATED_SALES_REP_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySalesRepNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where salesRepName does not contain
        defaultSalesInvoiceDummyFiltering(
            "salesRepName.doesNotContain=" + UPDATED_SALES_REP_NAME,
            "salesRepName.doesNotContain=" + DEFAULT_SALES_REP_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliverFromIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliverFrom equals to
        defaultSalesInvoiceDummyFiltering("deliverFrom.equals=" + DEFAULT_DELIVER_FROM, "deliverFrom.equals=" + UPDATED_DELIVER_FROM);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliverFromIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliverFrom in
        defaultSalesInvoiceDummyFiltering(
            "deliverFrom.in=" + DEFAULT_DELIVER_FROM + "," + UPDATED_DELIVER_FROM,
            "deliverFrom.in=" + UPDATED_DELIVER_FROM
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliverFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliverFrom is not null
        defaultSalesInvoiceDummyFiltering("deliverFrom.specified=true", "deliverFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliverFromContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliverFrom contains
        defaultSalesInvoiceDummyFiltering("deliverFrom.contains=" + DEFAULT_DELIVER_FROM, "deliverFrom.contains=" + UPDATED_DELIVER_FROM);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliverFromNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliverFrom does not contain
        defaultSalesInvoiceDummyFiltering(
            "deliverFrom.doesNotContain=" + UPDATED_DELIVER_FROM,
            "deliverFrom.doesNotContain=" + DEFAULT_DELIVER_FROM
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerID equals to
        defaultSalesInvoiceDummyFiltering("customerID.equals=" + DEFAULT_CUSTOMER_ID, "customerID.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerID in
        defaultSalesInvoiceDummyFiltering(
            "customerID.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID,
            "customerID.in=" + UPDATED_CUSTOMER_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerID is not null
        defaultSalesInvoiceDummyFiltering("customerID.specified=true", "customerID.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerID is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "customerID.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID,
            "customerID.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerID is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "customerID.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID,
            "customerID.lessThanOrEqual=" + SMALLER_CUSTOMER_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerID is less than
        defaultSalesInvoiceDummyFiltering("customerID.lessThan=" + UPDATED_CUSTOMER_ID, "customerID.lessThan=" + DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerID is greater than
        defaultSalesInvoiceDummyFiltering("customerID.greaterThan=" + SMALLER_CUSTOMER_ID, "customerID.greaterThan=" + DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerName equals to
        defaultSalesInvoiceDummyFiltering("customerName.equals=" + DEFAULT_CUSTOMER_NAME, "customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerName in
        defaultSalesInvoiceDummyFiltering(
            "customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME,
            "customerName.in=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerName is not null
        defaultSalesInvoiceDummyFiltering("customerName.specified=true", "customerName.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerName contains
        defaultSalesInvoiceDummyFiltering(
            "customerName.contains=" + DEFAULT_CUSTOMER_NAME,
            "customerName.contains=" + UPDATED_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerName does not contain
        defaultSalesInvoiceDummyFiltering(
            "customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME,
            "customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerAddress equals to
        defaultSalesInvoiceDummyFiltering(
            "customerAddress.equals=" + DEFAULT_CUSTOMER_ADDRESS,
            "customerAddress.equals=" + UPDATED_CUSTOMER_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerAddress in
        defaultSalesInvoiceDummyFiltering(
            "customerAddress.in=" + DEFAULT_CUSTOMER_ADDRESS + "," + UPDATED_CUSTOMER_ADDRESS,
            "customerAddress.in=" + UPDATED_CUSTOMER_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerAddress is not null
        defaultSalesInvoiceDummyFiltering("customerAddress.specified=true", "customerAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerAddress contains
        defaultSalesInvoiceDummyFiltering(
            "customerAddress.contains=" + DEFAULT_CUSTOMER_ADDRESS,
            "customerAddress.contains=" + UPDATED_CUSTOMER_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCustomerAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where customerAddress does not contain
        defaultSalesInvoiceDummyFiltering(
            "customerAddress.doesNotContain=" + UPDATED_CUSTOMER_ADDRESS,
            "customerAddress.doesNotContain=" + DEFAULT_CUSTOMER_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliveryAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliveryAddress equals to
        defaultSalesInvoiceDummyFiltering(
            "deliveryAddress.equals=" + DEFAULT_DELIVERY_ADDRESS,
            "deliveryAddress.equals=" + UPDATED_DELIVERY_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliveryAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliveryAddress in
        defaultSalesInvoiceDummyFiltering(
            "deliveryAddress.in=" + DEFAULT_DELIVERY_ADDRESS + "," + UPDATED_DELIVERY_ADDRESS,
            "deliveryAddress.in=" + UPDATED_DELIVERY_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliveryAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliveryAddress is not null
        defaultSalesInvoiceDummyFiltering("deliveryAddress.specified=true", "deliveryAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliveryAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliveryAddress contains
        defaultSalesInvoiceDummyFiltering(
            "deliveryAddress.contains=" + DEFAULT_DELIVERY_ADDRESS,
            "deliveryAddress.contains=" + UPDATED_DELIVERY_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDeliveryAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where deliveryAddress does not contain
        defaultSalesInvoiceDummyFiltering(
            "deliveryAddress.doesNotContain=" + UPDATED_DELIVERY_ADDRESS,
            "deliveryAddress.doesNotContain=" + DEFAULT_DELIVERY_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySubTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where subTotal equals to
        defaultSalesInvoiceDummyFiltering("subTotal.equals=" + DEFAULT_SUB_TOTAL, "subTotal.equals=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySubTotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where subTotal in
        defaultSalesInvoiceDummyFiltering("subTotal.in=" + DEFAULT_SUB_TOTAL + "," + UPDATED_SUB_TOTAL, "subTotal.in=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySubTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where subTotal is not null
        defaultSalesInvoiceDummyFiltering("subTotal.specified=true", "subTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySubTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where subTotal is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "subTotal.greaterThanOrEqual=" + DEFAULT_SUB_TOTAL,
            "subTotal.greaterThanOrEqual=" + UPDATED_SUB_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySubTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where subTotal is less than or equal to
        defaultSalesInvoiceDummyFiltering("subTotal.lessThanOrEqual=" + DEFAULT_SUB_TOTAL, "subTotal.lessThanOrEqual=" + SMALLER_SUB_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySubTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where subTotal is less than
        defaultSalesInvoiceDummyFiltering("subTotal.lessThan=" + UPDATED_SUB_TOTAL, "subTotal.lessThan=" + DEFAULT_SUB_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesBySubTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where subTotal is greater than
        defaultSalesInvoiceDummyFiltering("subTotal.greaterThan=" + SMALLER_SUB_TOTAL, "subTotal.greaterThan=" + DEFAULT_SUB_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalTax equals to
        defaultSalesInvoiceDummyFiltering("totalTax.equals=" + DEFAULT_TOTAL_TAX, "totalTax.equals=" + UPDATED_TOTAL_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalTax in
        defaultSalesInvoiceDummyFiltering("totalTax.in=" + DEFAULT_TOTAL_TAX + "," + UPDATED_TOTAL_TAX, "totalTax.in=" + UPDATED_TOTAL_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalTax is not null
        defaultSalesInvoiceDummyFiltering("totalTax.specified=true", "totalTax.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalTax is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "totalTax.greaterThanOrEqual=" + DEFAULT_TOTAL_TAX,
            "totalTax.greaterThanOrEqual=" + UPDATED_TOTAL_TAX
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalTax is less than or equal to
        defaultSalesInvoiceDummyFiltering("totalTax.lessThanOrEqual=" + DEFAULT_TOTAL_TAX, "totalTax.lessThanOrEqual=" + SMALLER_TOTAL_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalTax is less than
        defaultSalesInvoiceDummyFiltering("totalTax.lessThan=" + UPDATED_TOTAL_TAX, "totalTax.lessThan=" + DEFAULT_TOTAL_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalTax is greater than
        defaultSalesInvoiceDummyFiltering("totalTax.greaterThan=" + SMALLER_TOTAL_TAX, "totalTax.greaterThan=" + DEFAULT_TOTAL_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalDiscount equals to
        defaultSalesInvoiceDummyFiltering(
            "totalDiscount.equals=" + DEFAULT_TOTAL_DISCOUNT,
            "totalDiscount.equals=" + UPDATED_TOTAL_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalDiscount in
        defaultSalesInvoiceDummyFiltering(
            "totalDiscount.in=" + DEFAULT_TOTAL_DISCOUNT + "," + UPDATED_TOTAL_DISCOUNT,
            "totalDiscount.in=" + UPDATED_TOTAL_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalDiscount is not null
        defaultSalesInvoiceDummyFiltering("totalDiscount.specified=true", "totalDiscount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalDiscount is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "totalDiscount.greaterThanOrEqual=" + DEFAULT_TOTAL_DISCOUNT,
            "totalDiscount.greaterThanOrEqual=" + UPDATED_TOTAL_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalDiscount is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "totalDiscount.lessThanOrEqual=" + DEFAULT_TOTAL_DISCOUNT,
            "totalDiscount.lessThanOrEqual=" + SMALLER_TOTAL_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalDiscount is less than
        defaultSalesInvoiceDummyFiltering(
            "totalDiscount.lessThan=" + UPDATED_TOTAL_DISCOUNT,
            "totalDiscount.lessThan=" + DEFAULT_TOTAL_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByTotalDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where totalDiscount is greater than
        defaultSalesInvoiceDummyFiltering(
            "totalDiscount.greaterThan=" + SMALLER_TOTAL_DISCOUNT,
            "totalDiscount.greaterThan=" + DEFAULT_TOTAL_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNetTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where netTotal equals to
        defaultSalesInvoiceDummyFiltering("netTotal.equals=" + DEFAULT_NET_TOTAL, "netTotal.equals=" + UPDATED_NET_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNetTotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where netTotal in
        defaultSalesInvoiceDummyFiltering("netTotal.in=" + DEFAULT_NET_TOTAL + "," + UPDATED_NET_TOTAL, "netTotal.in=" + UPDATED_NET_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNetTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where netTotal is not null
        defaultSalesInvoiceDummyFiltering("netTotal.specified=true", "netTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNetTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where netTotal is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "netTotal.greaterThanOrEqual=" + DEFAULT_NET_TOTAL,
            "netTotal.greaterThanOrEqual=" + UPDATED_NET_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNetTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where netTotal is less than or equal to
        defaultSalesInvoiceDummyFiltering("netTotal.lessThanOrEqual=" + DEFAULT_NET_TOTAL, "netTotal.lessThanOrEqual=" + SMALLER_NET_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNetTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where netTotal is less than
        defaultSalesInvoiceDummyFiltering("netTotal.lessThan=" + UPDATED_NET_TOTAL, "netTotal.lessThan=" + DEFAULT_NET_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNetTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where netTotal is greater than
        defaultSalesInvoiceDummyFiltering("netTotal.greaterThan=" + SMALLER_NET_TOTAL, "netTotal.greaterThan=" + DEFAULT_NET_TOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where message equals to
        defaultSalesInvoiceDummyFiltering("message.equals=" + DEFAULT_MESSAGE, "message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where message in
        defaultSalesInvoiceDummyFiltering("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE, "message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where message is not null
        defaultSalesInvoiceDummyFiltering("message.specified=true", "message.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByMessageContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where message contains
        defaultSalesInvoiceDummyFiltering("message.contains=" + DEFAULT_MESSAGE, "message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where message does not contain
        defaultSalesInvoiceDummyFiltering("message.doesNotContain=" + UPDATED_MESSAGE, "message.doesNotContain=" + DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmu equals to
        defaultSalesInvoiceDummyFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmu in
        defaultSalesInvoiceDummyFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmu is not null
        defaultSalesInvoiceDummyFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmu is greater than or equal to
        defaultSalesInvoiceDummyFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmu is less than or equal to
        defaultSalesInvoiceDummyFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmu is less than
        defaultSalesInvoiceDummyFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmu is greater than
        defaultSalesInvoiceDummyFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmd equals to
        defaultSalesInvoiceDummyFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmd in
        defaultSalesInvoiceDummyFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where lmd is not null
        defaultSalesInvoiceDummyFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidAmount equals to
        defaultSalesInvoiceDummyFiltering("paidAmount.equals=" + DEFAULT_PAID_AMOUNT, "paidAmount.equals=" + UPDATED_PAID_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidAmount in
        defaultSalesInvoiceDummyFiltering(
            "paidAmount.in=" + DEFAULT_PAID_AMOUNT + "," + UPDATED_PAID_AMOUNT,
            "paidAmount.in=" + UPDATED_PAID_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidAmount is not null
        defaultSalesInvoiceDummyFiltering("paidAmount.specified=true", "paidAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidAmount is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "paidAmount.greaterThanOrEqual=" + DEFAULT_PAID_AMOUNT,
            "paidAmount.greaterThanOrEqual=" + UPDATED_PAID_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidAmount is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "paidAmount.lessThanOrEqual=" + DEFAULT_PAID_AMOUNT,
            "paidAmount.lessThanOrEqual=" + SMALLER_PAID_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidAmount is less than
        defaultSalesInvoiceDummyFiltering("paidAmount.lessThan=" + UPDATED_PAID_AMOUNT, "paidAmount.lessThan=" + DEFAULT_PAID_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidAmount is greater than
        defaultSalesInvoiceDummyFiltering("paidAmount.greaterThan=" + SMALLER_PAID_AMOUNT, "paidAmount.greaterThan=" + DEFAULT_PAID_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAmountOwingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where amountOwing equals to
        defaultSalesInvoiceDummyFiltering("amountOwing.equals=" + DEFAULT_AMOUNT_OWING, "amountOwing.equals=" + UPDATED_AMOUNT_OWING);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAmountOwingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where amountOwing in
        defaultSalesInvoiceDummyFiltering(
            "amountOwing.in=" + DEFAULT_AMOUNT_OWING + "," + UPDATED_AMOUNT_OWING,
            "amountOwing.in=" + UPDATED_AMOUNT_OWING
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAmountOwingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where amountOwing is not null
        defaultSalesInvoiceDummyFiltering("amountOwing.specified=true", "amountOwing.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAmountOwingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where amountOwing is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "amountOwing.greaterThanOrEqual=" + DEFAULT_AMOUNT_OWING,
            "amountOwing.greaterThanOrEqual=" + UPDATED_AMOUNT_OWING
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAmountOwingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where amountOwing is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "amountOwing.lessThanOrEqual=" + DEFAULT_AMOUNT_OWING,
            "amountOwing.lessThanOrEqual=" + SMALLER_AMOUNT_OWING
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAmountOwingIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where amountOwing is less than
        defaultSalesInvoiceDummyFiltering("amountOwing.lessThan=" + UPDATED_AMOUNT_OWING, "amountOwing.lessThan=" + DEFAULT_AMOUNT_OWING);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAmountOwingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where amountOwing is greater than
        defaultSalesInvoiceDummyFiltering(
            "amountOwing.greaterThan=" + SMALLER_AMOUNT_OWING,
            "amountOwing.greaterThan=" + DEFAULT_AMOUNT_OWING
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where isActive equals to
        defaultSalesInvoiceDummyFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where isActive in
        defaultSalesInvoiceDummyFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where isActive is not null
        defaultSalesInvoiceDummyFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationID equals to
        defaultSalesInvoiceDummyFiltering("locationID.equals=" + DEFAULT_LOCATION_ID, "locationID.equals=" + UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationID in
        defaultSalesInvoiceDummyFiltering(
            "locationID.in=" + DEFAULT_LOCATION_ID + "," + UPDATED_LOCATION_ID,
            "locationID.in=" + UPDATED_LOCATION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationID is not null
        defaultSalesInvoiceDummyFiltering("locationID.specified=true", "locationID.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationID is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "locationID.greaterThanOrEqual=" + DEFAULT_LOCATION_ID,
            "locationID.greaterThanOrEqual=" + UPDATED_LOCATION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationID is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "locationID.lessThanOrEqual=" + DEFAULT_LOCATION_ID,
            "locationID.lessThanOrEqual=" + SMALLER_LOCATION_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationID is less than
        defaultSalesInvoiceDummyFiltering("locationID.lessThan=" + UPDATED_LOCATION_ID, "locationID.lessThan=" + DEFAULT_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationID is greater than
        defaultSalesInvoiceDummyFiltering("locationID.greaterThan=" + SMALLER_LOCATION_ID, "locationID.greaterThan=" + DEFAULT_LOCATION_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationCode equals to
        defaultSalesInvoiceDummyFiltering("locationCode.equals=" + DEFAULT_LOCATION_CODE, "locationCode.equals=" + UPDATED_LOCATION_CODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationCode in
        defaultSalesInvoiceDummyFiltering(
            "locationCode.in=" + DEFAULT_LOCATION_CODE + "," + UPDATED_LOCATION_CODE,
            "locationCode.in=" + UPDATED_LOCATION_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationCode is not null
        defaultSalesInvoiceDummyFiltering("locationCode.specified=true", "locationCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationCode contains
        defaultSalesInvoiceDummyFiltering(
            "locationCode.contains=" + DEFAULT_LOCATION_CODE,
            "locationCode.contains=" + UPDATED_LOCATION_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByLocationCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where locationCode does not contain
        defaultSalesInvoiceDummyFiltering(
            "locationCode.doesNotContain=" + UPDATED_LOCATION_CODE,
            "locationCode.doesNotContain=" + DEFAULT_LOCATION_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByReferenceCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where referenceCode equals to
        defaultSalesInvoiceDummyFiltering(
            "referenceCode.equals=" + DEFAULT_REFERENCE_CODE,
            "referenceCode.equals=" + UPDATED_REFERENCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByReferenceCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where referenceCode in
        defaultSalesInvoiceDummyFiltering(
            "referenceCode.in=" + DEFAULT_REFERENCE_CODE + "," + UPDATED_REFERENCE_CODE,
            "referenceCode.in=" + UPDATED_REFERENCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByReferenceCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where referenceCode is not null
        defaultSalesInvoiceDummyFiltering("referenceCode.specified=true", "referenceCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByReferenceCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where referenceCode contains
        defaultSalesInvoiceDummyFiltering(
            "referenceCode.contains=" + DEFAULT_REFERENCE_CODE,
            "referenceCode.contains=" + UPDATED_REFERENCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByReferenceCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where referenceCode does not contain
        defaultSalesInvoiceDummyFiltering(
            "referenceCode.doesNotContain=" + UPDATED_REFERENCE_CODE,
            "referenceCode.doesNotContain=" + DEFAULT_REFERENCE_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdById equals to
        defaultSalesInvoiceDummyFiltering("createdById.equals=" + DEFAULT_CREATED_BY_ID, "createdById.equals=" + UPDATED_CREATED_BY_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdById in
        defaultSalesInvoiceDummyFiltering(
            "createdById.in=" + DEFAULT_CREATED_BY_ID + "," + UPDATED_CREATED_BY_ID,
            "createdById.in=" + UPDATED_CREATED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdById is not null
        defaultSalesInvoiceDummyFiltering("createdById.specified=true", "createdById.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdById is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "createdById.greaterThanOrEqual=" + DEFAULT_CREATED_BY_ID,
            "createdById.greaterThanOrEqual=" + UPDATED_CREATED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdById is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "createdById.lessThanOrEqual=" + DEFAULT_CREATED_BY_ID,
            "createdById.lessThanOrEqual=" + SMALLER_CREATED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdById is less than
        defaultSalesInvoiceDummyFiltering("createdById.lessThan=" + UPDATED_CREATED_BY_ID, "createdById.lessThan=" + DEFAULT_CREATED_BY_ID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdById is greater than
        defaultSalesInvoiceDummyFiltering(
            "createdById.greaterThan=" + SMALLER_CREATED_BY_ID,
            "createdById.greaterThan=" + DEFAULT_CREATED_BY_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdByName equals to
        defaultSalesInvoiceDummyFiltering(
            "createdByName.equals=" + DEFAULT_CREATED_BY_NAME,
            "createdByName.equals=" + UPDATED_CREATED_BY_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdByName in
        defaultSalesInvoiceDummyFiltering(
            "createdByName.in=" + DEFAULT_CREATED_BY_NAME + "," + UPDATED_CREATED_BY_NAME,
            "createdByName.in=" + UPDATED_CREATED_BY_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdByName is not null
        defaultSalesInvoiceDummyFiltering("createdByName.specified=true", "createdByName.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdByName contains
        defaultSalesInvoiceDummyFiltering(
            "createdByName.contains=" + DEFAULT_CREATED_BY_NAME,
            "createdByName.contains=" + UPDATED_CREATED_BY_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCreatedByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where createdByName does not contain
        defaultSalesInvoiceDummyFiltering(
            "createdByName.doesNotContain=" + UPDATED_CREATED_BY_NAME,
            "createdByName.doesNotContain=" + DEFAULT_CREATED_BY_NAME
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareChargesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareCharges equals to
        defaultSalesInvoiceDummyFiltering(
            "autoCareCharges.equals=" + DEFAULT_AUTO_CARE_CHARGES,
            "autoCareCharges.equals=" + UPDATED_AUTO_CARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareChargesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareCharges in
        defaultSalesInvoiceDummyFiltering(
            "autoCareCharges.in=" + DEFAULT_AUTO_CARE_CHARGES + "," + UPDATED_AUTO_CARE_CHARGES,
            "autoCareCharges.in=" + UPDATED_AUTO_CARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareChargesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareCharges is not null
        defaultSalesInvoiceDummyFiltering("autoCareCharges.specified=true", "autoCareCharges.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareChargesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareCharges is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "autoCareCharges.greaterThanOrEqual=" + DEFAULT_AUTO_CARE_CHARGES,
            "autoCareCharges.greaterThanOrEqual=" + UPDATED_AUTO_CARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareChargesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareCharges is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "autoCareCharges.lessThanOrEqual=" + DEFAULT_AUTO_CARE_CHARGES,
            "autoCareCharges.lessThanOrEqual=" + SMALLER_AUTO_CARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareChargesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareCharges is less than
        defaultSalesInvoiceDummyFiltering(
            "autoCareCharges.lessThan=" + UPDATED_AUTO_CARE_CHARGES,
            "autoCareCharges.lessThan=" + DEFAULT_AUTO_CARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareChargesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareCharges is greater than
        defaultSalesInvoiceDummyFiltering(
            "autoCareCharges.greaterThan=" + SMALLER_AUTO_CARE_CHARGES,
            "autoCareCharges.greaterThan=" + DEFAULT_AUTO_CARE_CHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareJobIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareJobId equals to
        defaultSalesInvoiceDummyFiltering(
            "autoCareJobId.equals=" + DEFAULT_AUTO_CARE_JOB_ID,
            "autoCareJobId.equals=" + UPDATED_AUTO_CARE_JOB_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareJobIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareJobId in
        defaultSalesInvoiceDummyFiltering(
            "autoCareJobId.in=" + DEFAULT_AUTO_CARE_JOB_ID + "," + UPDATED_AUTO_CARE_JOB_ID,
            "autoCareJobId.in=" + UPDATED_AUTO_CARE_JOB_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareJobIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareJobId is not null
        defaultSalesInvoiceDummyFiltering("autoCareJobId.specified=true", "autoCareJobId.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareJobIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareJobId is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "autoCareJobId.greaterThanOrEqual=" + DEFAULT_AUTO_CARE_JOB_ID,
            "autoCareJobId.greaterThanOrEqual=" + UPDATED_AUTO_CARE_JOB_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareJobIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareJobId is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "autoCareJobId.lessThanOrEqual=" + DEFAULT_AUTO_CARE_JOB_ID,
            "autoCareJobId.lessThanOrEqual=" + SMALLER_AUTO_CARE_JOB_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareJobIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareJobId is less than
        defaultSalesInvoiceDummyFiltering(
            "autoCareJobId.lessThan=" + UPDATED_AUTO_CARE_JOB_ID,
            "autoCareJobId.lessThan=" + DEFAULT_AUTO_CARE_JOB_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByAutoCareJobIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where autoCareJobId is greater than
        defaultSalesInvoiceDummyFiltering(
            "autoCareJobId.greaterThan=" + SMALLER_AUTO_CARE_JOB_ID,
            "autoCareJobId.greaterThan=" + DEFAULT_AUTO_CARE_JOB_ID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVehicleNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vehicleNo equals to
        defaultSalesInvoiceDummyFiltering("vehicleNo.equals=" + DEFAULT_VEHICLE_NO, "vehicleNo.equals=" + UPDATED_VEHICLE_NO);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVehicleNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vehicleNo in
        defaultSalesInvoiceDummyFiltering(
            "vehicleNo.in=" + DEFAULT_VEHICLE_NO + "," + UPDATED_VEHICLE_NO,
            "vehicleNo.in=" + UPDATED_VEHICLE_NO
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVehicleNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vehicleNo is not null
        defaultSalesInvoiceDummyFiltering("vehicleNo.specified=true", "vehicleNo.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVehicleNoContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vehicleNo contains
        defaultSalesInvoiceDummyFiltering("vehicleNo.contains=" + DEFAULT_VEHICLE_NO, "vehicleNo.contains=" + UPDATED_VEHICLE_NO);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVehicleNoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vehicleNo does not contain
        defaultSalesInvoiceDummyFiltering(
            "vehicleNo.doesNotContain=" + UPDATED_VEHICLE_NO,
            "vehicleNo.doesNotContain=" + DEFAULT_VEHICLE_NO
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNbtAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where nbtAmount equals to
        defaultSalesInvoiceDummyFiltering("nbtAmount.equals=" + DEFAULT_NBT_AMOUNT, "nbtAmount.equals=" + UPDATED_NBT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNbtAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where nbtAmount in
        defaultSalesInvoiceDummyFiltering(
            "nbtAmount.in=" + DEFAULT_NBT_AMOUNT + "," + UPDATED_NBT_AMOUNT,
            "nbtAmount.in=" + UPDATED_NBT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNbtAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where nbtAmount is not null
        defaultSalesInvoiceDummyFiltering("nbtAmount.specified=true", "nbtAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNbtAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where nbtAmount is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "nbtAmount.greaterThanOrEqual=" + DEFAULT_NBT_AMOUNT,
            "nbtAmount.greaterThanOrEqual=" + UPDATED_NBT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNbtAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where nbtAmount is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "nbtAmount.lessThanOrEqual=" + DEFAULT_NBT_AMOUNT,
            "nbtAmount.lessThanOrEqual=" + SMALLER_NBT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNbtAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where nbtAmount is less than
        defaultSalesInvoiceDummyFiltering("nbtAmount.lessThan=" + UPDATED_NBT_AMOUNT, "nbtAmount.lessThan=" + DEFAULT_NBT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByNbtAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where nbtAmount is greater than
        defaultSalesInvoiceDummyFiltering("nbtAmount.greaterThan=" + SMALLER_NBT_AMOUNT, "nbtAmount.greaterThan=" + DEFAULT_NBT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVatAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vatAmount equals to
        defaultSalesInvoiceDummyFiltering("vatAmount.equals=" + DEFAULT_VAT_AMOUNT, "vatAmount.equals=" + UPDATED_VAT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVatAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vatAmount in
        defaultSalesInvoiceDummyFiltering(
            "vatAmount.in=" + DEFAULT_VAT_AMOUNT + "," + UPDATED_VAT_AMOUNT,
            "vatAmount.in=" + UPDATED_VAT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVatAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vatAmount is not null
        defaultSalesInvoiceDummyFiltering("vatAmount.specified=true", "vatAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVatAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vatAmount is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "vatAmount.greaterThanOrEqual=" + DEFAULT_VAT_AMOUNT,
            "vatAmount.greaterThanOrEqual=" + UPDATED_VAT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVatAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vatAmount is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "vatAmount.lessThanOrEqual=" + DEFAULT_VAT_AMOUNT,
            "vatAmount.lessThanOrEqual=" + SMALLER_VAT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVatAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vatAmount is less than
        defaultSalesInvoiceDummyFiltering("vatAmount.lessThan=" + UPDATED_VAT_AMOUNT, "vatAmount.lessThan=" + DEFAULT_VAT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByVatAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where vatAmount is greater than
        defaultSalesInvoiceDummyFiltering("vatAmount.greaterThan=" + SMALLER_VAT_AMOUNT, "vatAmount.greaterThan=" + DEFAULT_VAT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDummyCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where dummyCommission equals to
        defaultSalesInvoiceDummyFiltering(
            "dummyCommission.equals=" + DEFAULT_DUMMY_COMMISSION,
            "dummyCommission.equals=" + UPDATED_DUMMY_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDummyCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where dummyCommission in
        defaultSalesInvoiceDummyFiltering(
            "dummyCommission.in=" + DEFAULT_DUMMY_COMMISSION + "," + UPDATED_DUMMY_COMMISSION,
            "dummyCommission.in=" + UPDATED_DUMMY_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDummyCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where dummyCommission is not null
        defaultSalesInvoiceDummyFiltering("dummyCommission.specified=true", "dummyCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDummyCommissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where dummyCommission is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "dummyCommission.greaterThanOrEqual=" + DEFAULT_DUMMY_COMMISSION,
            "dummyCommission.greaterThanOrEqual=" + UPDATED_DUMMY_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDummyCommissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where dummyCommission is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "dummyCommission.lessThanOrEqual=" + DEFAULT_DUMMY_COMMISSION,
            "dummyCommission.lessThanOrEqual=" + SMALLER_DUMMY_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDummyCommissionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where dummyCommission is less than
        defaultSalesInvoiceDummyFiltering(
            "dummyCommission.lessThan=" + UPDATED_DUMMY_COMMISSION,
            "dummyCommission.lessThan=" + DEFAULT_DUMMY_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByDummyCommissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where dummyCommission is greater than
        defaultSalesInvoiceDummyFiltering(
            "dummyCommission.greaterThan=" + SMALLER_DUMMY_COMMISSION,
            "dummyCommission.greaterThan=" + DEFAULT_DUMMY_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCommissionPaidDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where commissionPaidDate equals to
        defaultSalesInvoiceDummyFiltering(
            "commissionPaidDate.equals=" + DEFAULT_COMMISSION_PAID_DATE,
            "commissionPaidDate.equals=" + UPDATED_COMMISSION_PAID_DATE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCommissionPaidDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where commissionPaidDate in
        defaultSalesInvoiceDummyFiltering(
            "commissionPaidDate.in=" + DEFAULT_COMMISSION_PAID_DATE + "," + UPDATED_COMMISSION_PAID_DATE,
            "commissionPaidDate.in=" + UPDATED_COMMISSION_PAID_DATE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByCommissionPaidDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where commissionPaidDate is not null
        defaultSalesInvoiceDummyFiltering("commissionPaidDate.specified=true", "commissionPaidDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidCommission equals to
        defaultSalesInvoiceDummyFiltering(
            "paidCommission.equals=" + DEFAULT_PAID_COMMISSION,
            "paidCommission.equals=" + UPDATED_PAID_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidCommission in
        defaultSalesInvoiceDummyFiltering(
            "paidCommission.in=" + DEFAULT_PAID_COMMISSION + "," + UPDATED_PAID_COMMISSION,
            "paidCommission.in=" + UPDATED_PAID_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidCommission is not null
        defaultSalesInvoiceDummyFiltering("paidCommission.specified=true", "paidCommission.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidCommissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidCommission is greater than or equal to
        defaultSalesInvoiceDummyFiltering(
            "paidCommission.greaterThanOrEqual=" + DEFAULT_PAID_COMMISSION,
            "paidCommission.greaterThanOrEqual=" + UPDATED_PAID_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidCommissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidCommission is less than or equal to
        defaultSalesInvoiceDummyFiltering(
            "paidCommission.lessThanOrEqual=" + DEFAULT_PAID_COMMISSION,
            "paidCommission.lessThanOrEqual=" + SMALLER_PAID_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidCommissionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidCommission is less than
        defaultSalesInvoiceDummyFiltering(
            "paidCommission.lessThan=" + UPDATED_PAID_COMMISSION,
            "paidCommission.lessThan=" + DEFAULT_PAID_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidCommissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidCommission is greater than
        defaultSalesInvoiceDummyFiltering(
            "paidCommission.greaterThan=" + SMALLER_PAID_COMMISSION,
            "paidCommission.greaterThan=" + DEFAULT_PAID_COMMISSION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidBy equals to
        defaultSalesInvoiceDummyFiltering("paidBy.equals=" + DEFAULT_PAID_BY, "paidBy.equals=" + UPDATED_PAID_BY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidBy in
        defaultSalesInvoiceDummyFiltering("paidBy.in=" + DEFAULT_PAID_BY + "," + UPDATED_PAID_BY, "paidBy.in=" + UPDATED_PAID_BY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidBy is not null
        defaultSalesInvoiceDummyFiltering("paidBy.specified=true", "paidBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidBy is greater than or equal to
        defaultSalesInvoiceDummyFiltering("paidBy.greaterThanOrEqual=" + DEFAULT_PAID_BY, "paidBy.greaterThanOrEqual=" + UPDATED_PAID_BY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidBy is less than or equal to
        defaultSalesInvoiceDummyFiltering("paidBy.lessThanOrEqual=" + DEFAULT_PAID_BY, "paidBy.lessThanOrEqual=" + SMALLER_PAID_BY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidByIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidBy is less than
        defaultSalesInvoiceDummyFiltering("paidBy.lessThan=" + UPDATED_PAID_BY, "paidBy.lessThan=" + DEFAULT_PAID_BY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByPaidByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where paidBy is greater than
        defaultSalesInvoiceDummyFiltering("paidBy.greaterThan=" + SMALLER_PAID_BY, "paidBy.greaterThan=" + DEFAULT_PAID_BY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceCode equals to
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceCode.equals=" + DEFAULT_ORIGINAL_INVOICE_CODE,
            "originalInvoiceCode.equals=" + UPDATED_ORIGINAL_INVOICE_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceCode in
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceCode.in=" + DEFAULT_ORIGINAL_INVOICE_CODE + "," + UPDATED_ORIGINAL_INVOICE_CODE,
            "originalInvoiceCode.in=" + UPDATED_ORIGINAL_INVOICE_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceCode is not null
        defaultSalesInvoiceDummyFiltering("originalInvoiceCode.specified=true", "originalInvoiceCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceCode contains
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceCode.contains=" + DEFAULT_ORIGINAL_INVOICE_CODE,
            "originalInvoiceCode.contains=" + UPDATED_ORIGINAL_INVOICE_CODE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceDummiesByOriginalInvoiceCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        // Get all the salesInvoiceDummyList where originalInvoiceCode does not contain
        defaultSalesInvoiceDummyFiltering(
            "originalInvoiceCode.doesNotContain=" + UPDATED_ORIGINAL_INVOICE_CODE,
            "originalInvoiceCode.doesNotContain=" + DEFAULT_ORIGINAL_INVOICE_CODE
        );
    }

    private void defaultSalesInvoiceDummyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSalesInvoiceDummyShouldBeFound(shouldBeFound);
        defaultSalesInvoiceDummyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalesInvoiceDummyShouldBeFound(String filter) throws Exception {
        restSalesInvoiceDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceDummy.getId())))
            .andExpect(jsonPath("$.[*].originalInvoiceId").value(hasItem(DEFAULT_ORIGINAL_INVOICE_ID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].quoteID").value(hasItem(DEFAULT_QUOTE_ID)))
            .andExpect(jsonPath("$.[*].orderID").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].salesRepID").value(hasItem(DEFAULT_SALES_REP_ID)))
            .andExpect(jsonPath("$.[*].salesRepName").value(hasItem(DEFAULT_SALES_REP_NAME)))
            .andExpect(jsonPath("$.[*].deliverFrom").value(hasItem(DEFAULT_DELIVER_FROM)))
            .andExpect(jsonPath("$.[*].customerID").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].customerAddress").value(hasItem(DEFAULT_CUSTOMER_ADDRESS)))
            .andExpect(jsonPath("$.[*].deliveryAddress").value(hasItem(DEFAULT_DELIVERY_ADDRESS)))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTax").value(hasItem(DEFAULT_TOTAL_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totalDiscount").value(hasItem(DEFAULT_TOTAL_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].netTotal").value(hasItem(DEFAULT_NET_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].paidAmount").value(hasItem(DEFAULT_PAID_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountOwing").value(hasItem(DEFAULT_AMOUNT_OWING.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].locationID").value(hasItem(DEFAULT_LOCATION_ID)))
            .andExpect(jsonPath("$.[*].locationCode").value(hasItem(DEFAULT_LOCATION_CODE)))
            .andExpect(jsonPath("$.[*].referenceCode").value(hasItem(DEFAULT_REFERENCE_CODE)))
            .andExpect(jsonPath("$.[*].createdById").value(hasItem(DEFAULT_CREATED_BY_ID)))
            .andExpect(jsonPath("$.[*].createdByName").value(hasItem(DEFAULT_CREATED_BY_NAME)))
            .andExpect(jsonPath("$.[*].autoCareCharges").value(hasItem(DEFAULT_AUTO_CARE_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].autoCareJobId").value(hasItem(DEFAULT_AUTO_CARE_JOB_ID)))
            .andExpect(jsonPath("$.[*].vehicleNo").value(hasItem(DEFAULT_VEHICLE_NO)))
            .andExpect(jsonPath("$.[*].nbtAmount").value(hasItem(DEFAULT_NBT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vatAmount").value(hasItem(DEFAULT_VAT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dummyCommission").value(hasItem(DEFAULT_DUMMY_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].commissionPaidDate").value(hasItem(DEFAULT_COMMISSION_PAID_DATE.toString())))
            .andExpect(jsonPath("$.[*].paidCommission").value(hasItem(DEFAULT_PAID_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].paidBy").value(hasItem(DEFAULT_PAID_BY)))
            .andExpect(jsonPath("$.[*].originalInvoiceCode").value(hasItem(DEFAULT_ORIGINAL_INVOICE_CODE)));

        // Check, that the count call also returns 1
        restSalesInvoiceDummyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalesInvoiceDummyShouldNotBeFound(String filter) throws Exception {
        restSalesInvoiceDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalesInvoiceDummyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalesInvoiceDummy() throws Exception {
        // Get the salesInvoiceDummy
        restSalesInvoiceDummyMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesInvoiceDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceDummy
        SalesInvoiceDummy updatedSalesInvoiceDummy = salesInvoiceDummyRepository.findById(salesInvoiceDummy.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSalesInvoiceDummy are not directly saved in db
        em.detach(updatedSalesInvoiceDummy);
        updatedSalesInvoiceDummy
            .originalInvoiceId(UPDATED_ORIGINAL_INVOICE_ID)
            .code(UPDATED_CODE)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .quoteID(UPDATED_QUOTE_ID)
            .orderID(UPDATED_ORDER_ID)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .salesRepID(UPDATED_SALES_REP_ID)
            .salesRepName(UPDATED_SALES_REP_NAME)
            .deliverFrom(UPDATED_DELIVER_FROM)
            .customerID(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .subTotal(UPDATED_SUB_TOTAL)
            .totalTax(UPDATED_TOTAL_TAX)
            .totalDiscount(UPDATED_TOTAL_DISCOUNT)
            .netTotal(UPDATED_NET_TOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .amountOwing(UPDATED_AMOUNT_OWING)
            .isActive(UPDATED_IS_ACTIVE)
            .locationID(UPDATED_LOCATION_ID)
            .locationCode(UPDATED_LOCATION_CODE)
            .referenceCode(UPDATED_REFERENCE_CODE)
            .createdById(UPDATED_CREATED_BY_ID)
            .createdByName(UPDATED_CREATED_BY_NAME)
            .autoCareCharges(UPDATED_AUTO_CARE_CHARGES)
            .autoCareJobId(UPDATED_AUTO_CARE_JOB_ID)
            .vehicleNo(UPDATED_VEHICLE_NO)
            .nbtAmount(UPDATED_NBT_AMOUNT)
            .vatAmount(UPDATED_VAT_AMOUNT)
            .dummyCommission(UPDATED_DUMMY_COMMISSION)
            .commissionPaidDate(UPDATED_COMMISSION_PAID_DATE)
            .paidCommission(UPDATED_PAID_COMMISSION)
            .paidBy(UPDATED_PAID_BY)
            .originalInvoiceCode(UPDATED_ORIGINAL_INVOICE_CODE);

        restSalesInvoiceDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesInvoiceDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSalesInvoiceDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSalesInvoiceDummyToMatchAllProperties(updatedSalesInvoiceDummy);
    }

    @Test
    @Transactional
    void putNonExistingSalesInvoiceDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceDummy.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesInvoiceDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesInvoiceDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceDummy.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesInvoiceDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceDummy.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceDummyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceDummy)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesInvoiceDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceDummy using partial update
        SalesInvoiceDummy partialUpdatedSalesInvoiceDummy = new SalesInvoiceDummy();
        partialUpdatedSalesInvoiceDummy.setId(salesInvoiceDummy.getId());

        partialUpdatedSalesInvoiceDummy
            .originalInvoiceId(UPDATED_ORIGINAL_INVOICE_ID)
            .code(UPDATED_CODE)
            .quoteID(UPDATED_QUOTE_ID)
            .orderID(UPDATED_ORDER_ID)
            .salesRepID(UPDATED_SALES_REP_ID)
            .customerID(UPDATED_CUSTOMER_ID)
            .totalTax(UPDATED_TOTAL_TAX)
            .totalDiscount(UPDATED_TOTAL_DISCOUNT)
            .netTotal(UPDATED_NET_TOTAL)
            .message(UPDATED_MESSAGE)
            .lmd(UPDATED_LMD)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .isActive(UPDATED_IS_ACTIVE)
            .locationID(UPDATED_LOCATION_ID)
            .createdById(UPDATED_CREATED_BY_ID)
            .createdByName(UPDATED_CREATED_BY_NAME)
            .autoCareCharges(UPDATED_AUTO_CARE_CHARGES)
            .autoCareJobId(UPDATED_AUTO_CARE_JOB_ID)
            .vehicleNo(UPDATED_VEHICLE_NO)
            .vatAmount(UPDATED_VAT_AMOUNT)
            .dummyCommission(UPDATED_DUMMY_COMMISSION)
            .paidCommission(UPDATED_PAID_COMMISSION);

        restSalesInvoiceDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceDummyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSalesInvoiceDummy, salesInvoiceDummy),
            getPersistedSalesInvoiceDummy(salesInvoiceDummy)
        );
    }

    @Test
    @Transactional
    void fullUpdateSalesInvoiceDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceDummy using partial update
        SalesInvoiceDummy partialUpdatedSalesInvoiceDummy = new SalesInvoiceDummy();
        partialUpdatedSalesInvoiceDummy.setId(salesInvoiceDummy.getId());

        partialUpdatedSalesInvoiceDummy
            .originalInvoiceId(UPDATED_ORIGINAL_INVOICE_ID)
            .code(UPDATED_CODE)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .createdDate(UPDATED_CREATED_DATE)
            .quoteID(UPDATED_QUOTE_ID)
            .orderID(UPDATED_ORDER_ID)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .salesRepID(UPDATED_SALES_REP_ID)
            .salesRepName(UPDATED_SALES_REP_NAME)
            .deliverFrom(UPDATED_DELIVER_FROM)
            .customerID(UPDATED_CUSTOMER_ID)
            .customerName(UPDATED_CUSTOMER_NAME)
            .customerAddress(UPDATED_CUSTOMER_ADDRESS)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .subTotal(UPDATED_SUB_TOTAL)
            .totalTax(UPDATED_TOTAL_TAX)
            .totalDiscount(UPDATED_TOTAL_DISCOUNT)
            .netTotal(UPDATED_NET_TOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidAmount(UPDATED_PAID_AMOUNT)
            .amountOwing(UPDATED_AMOUNT_OWING)
            .isActive(UPDATED_IS_ACTIVE)
            .locationID(UPDATED_LOCATION_ID)
            .locationCode(UPDATED_LOCATION_CODE)
            .referenceCode(UPDATED_REFERENCE_CODE)
            .createdById(UPDATED_CREATED_BY_ID)
            .createdByName(UPDATED_CREATED_BY_NAME)
            .autoCareCharges(UPDATED_AUTO_CARE_CHARGES)
            .autoCareJobId(UPDATED_AUTO_CARE_JOB_ID)
            .vehicleNo(UPDATED_VEHICLE_NO)
            .nbtAmount(UPDATED_NBT_AMOUNT)
            .vatAmount(UPDATED_VAT_AMOUNT)
            .dummyCommission(UPDATED_DUMMY_COMMISSION)
            .commissionPaidDate(UPDATED_COMMISSION_PAID_DATE)
            .paidCommission(UPDATED_PAID_COMMISSION)
            .paidBy(UPDATED_PAID_BY)
            .originalInvoiceCode(UPDATED_ORIGINAL_INVOICE_CODE);

        restSalesInvoiceDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceDummy))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceDummyUpdatableFieldsEquals(
            partialUpdatedSalesInvoiceDummy,
            getPersistedSalesInvoiceDummy(partialUpdatedSalesInvoiceDummy)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSalesInvoiceDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceDummy.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesInvoiceDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesInvoiceDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceDummy.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesInvoiceDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceDummy.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceDummyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(salesInvoiceDummy)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesInvoiceDummy() throws Exception {
        // Initialize the database
        insertedSalesInvoiceDummy = salesInvoiceDummyRepository.saveAndFlush(salesInvoiceDummy);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the salesInvoiceDummy
        restSalesInvoiceDummyMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesInvoiceDummy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return salesInvoiceDummyRepository.count();
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

    protected SalesInvoiceDummy getPersistedSalesInvoiceDummy(SalesInvoiceDummy salesInvoiceDummy) {
        return salesInvoiceDummyRepository.findById(salesInvoiceDummy.getId()).orElseThrow();
    }

    protected void assertPersistedSalesInvoiceDummyToMatchAllProperties(SalesInvoiceDummy expectedSalesInvoiceDummy) {
        assertSalesInvoiceDummyAllPropertiesEquals(expectedSalesInvoiceDummy, getPersistedSalesInvoiceDummy(expectedSalesInvoiceDummy));
    }

    protected void assertPersistedSalesInvoiceDummyToMatchUpdatableProperties(SalesInvoiceDummy expectedSalesInvoiceDummy) {
        assertSalesInvoiceDummyAllUpdatablePropertiesEquals(
            expectedSalesInvoiceDummy,
            getPersistedSalesInvoiceDummy(expectedSalesInvoiceDummy)
        );
    }
}
