package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.VoucherLinesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.VoucherLines;
import com.heavenscode.rac.repository.VoucherLinesRepository;
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
 * Integration tests for the {@link VoucherLinesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherLinesResourceIT {

    private static final Integer DEFAULT_LINE_ID = 1;
    private static final Integer UPDATED_LINE_ID = 2;
    private static final Integer SMALLER_LINE_ID = 1 - 1;

    private static final String DEFAULT_GRN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GRN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GRN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GRN_TYPE = "BBBBBBBBBB";

    private static final Float DEFAULT_ORIGINAL_AMOUNT = 1F;
    private static final Float UPDATED_ORIGINAL_AMOUNT = 2F;
    private static final Float SMALLER_ORIGINAL_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_AMOUNT_OWING = 1F;
    private static final Float UPDATED_AMOUNT_OWING = 2F;
    private static final Float SMALLER_AMOUNT_OWING = 1F - 1F;

    private static final Float DEFAULT_DISCOUNT_AVAILABLE = 1F;
    private static final Float UPDATED_DISCOUNT_AVAILABLE = 2F;
    private static final Float SMALLER_DISCOUNT_AVAILABLE = 1F - 1F;

    private static final Float DEFAULT_DISCOUNT_TAKEN = 1F;
    private static final Float UPDATED_DISCOUNT_TAKEN = 2F;
    private static final Float SMALLER_DISCOUNT_TAKEN = 1F - 1F;

    private static final Float DEFAULT_AMOUNT_RECEIVED = 1F;
    private static final Float UPDATED_AMOUNT_RECEIVED = 2F;
    private static final Float SMALLER_AMOUNT_RECEIVED = 1F - 1F;

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ACCOUNT_ID = 1;
    private static final Integer UPDATED_ACCOUNT_ID = 2;
    private static final Integer SMALLER_ACCOUNT_ID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/voucher-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VoucherLinesRepository voucherLinesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherLinesMockMvc;

    private VoucherLines voucherLines;

    private VoucherLines insertedVoucherLines;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherLines createEntity() {
        return new VoucherLines()
            .lineID(DEFAULT_LINE_ID)
            .grnCode(DEFAULT_GRN_CODE)
            .grnType(DEFAULT_GRN_TYPE)
            .originalAmount(DEFAULT_ORIGINAL_AMOUNT)
            .amountOwing(DEFAULT_AMOUNT_OWING)
            .discountAvailable(DEFAULT_DISCOUNT_AVAILABLE)
            .discountTaken(DEFAULT_DISCOUNT_TAKEN)
            .amountReceived(DEFAULT_AMOUNT_RECEIVED)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .accountId(DEFAULT_ACCOUNT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherLines createUpdatedEntity() {
        return new VoucherLines()
            .lineID(UPDATED_LINE_ID)
            .grnCode(UPDATED_GRN_CODE)
            .grnType(UPDATED_GRN_TYPE)
            .originalAmount(UPDATED_ORIGINAL_AMOUNT)
            .amountOwing(UPDATED_AMOUNT_OWING)
            .discountAvailable(UPDATED_DISCOUNT_AVAILABLE)
            .discountTaken(UPDATED_DISCOUNT_TAKEN)
            .amountReceived(UPDATED_AMOUNT_RECEIVED)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .accountId(UPDATED_ACCOUNT_ID);
    }

    @BeforeEach
    public void initTest() {
        voucherLines = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedVoucherLines != null) {
            voucherLinesRepository.delete(insertedVoucherLines);
            insertedVoucherLines = null;
        }
    }

    @Test
    @Transactional
    void createVoucherLines() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the VoucherLines
        var returnedVoucherLines = om.readValue(
            restVoucherLinesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucherLines)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VoucherLines.class
        );

        // Validate the VoucherLines in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVoucherLinesUpdatableFieldsEquals(returnedVoucherLines, getPersistedVoucherLines(returnedVoucherLines));

        insertedVoucherLines = returnedVoucherLines;
    }

    @Test
    @Transactional
    void createVoucherLinesWithExistingId() throws Exception {
        // Create the VoucherLines with an existing ID
        voucherLines.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherLinesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucherLines)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherLines in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoucherLines() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList
        restVoucherLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineID").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].grnCode").value(hasItem(DEFAULT_GRN_CODE)))
            .andExpect(jsonPath("$.[*].grnType").value(hasItem(DEFAULT_GRN_TYPE)))
            .andExpect(jsonPath("$.[*].originalAmount").value(hasItem(DEFAULT_ORIGINAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountOwing").value(hasItem(DEFAULT_AMOUNT_OWING.doubleValue())))
            .andExpect(jsonPath("$.[*].discountAvailable").value(hasItem(DEFAULT_DISCOUNT_AVAILABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountTaken").value(hasItem(DEFAULT_DISCOUNT_TAKEN.doubleValue())))
            .andExpect(jsonPath("$.[*].amountReceived").value(hasItem(DEFAULT_AMOUNT_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)));
    }

    @Test
    @Transactional
    void getVoucherLines() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get the voucherLines
        restVoucherLinesMockMvc
            .perform(get(ENTITY_API_URL_ID, voucherLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucherLines.getId().intValue()))
            .andExpect(jsonPath("$.lineID").value(DEFAULT_LINE_ID))
            .andExpect(jsonPath("$.grnCode").value(DEFAULT_GRN_CODE))
            .andExpect(jsonPath("$.grnType").value(DEFAULT_GRN_TYPE))
            .andExpect(jsonPath("$.originalAmount").value(DEFAULT_ORIGINAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountOwing").value(DEFAULT_AMOUNT_OWING.doubleValue()))
            .andExpect(jsonPath("$.discountAvailable").value(DEFAULT_DISCOUNT_AVAILABLE.doubleValue()))
            .andExpect(jsonPath("$.discountTaken").value(DEFAULT_DISCOUNT_TAKEN.doubleValue()))
            .andExpect(jsonPath("$.amountReceived").value(DEFAULT_AMOUNT_RECEIVED.doubleValue()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID));
    }

    @Test
    @Transactional
    void getVoucherLinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        Long id = voucherLines.getId();

        defaultVoucherLinesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultVoucherLinesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultVoucherLinesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLineIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lineID equals to
        defaultVoucherLinesFiltering("lineID.equals=" + DEFAULT_LINE_ID, "lineID.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLineIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lineID in
        defaultVoucherLinesFiltering("lineID.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID, "lineID.in=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLineIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lineID is not null
        defaultVoucherLinesFiltering("lineID.specified=true", "lineID.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLineIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lineID is greater than or equal to
        defaultVoucherLinesFiltering("lineID.greaterThanOrEqual=" + DEFAULT_LINE_ID, "lineID.greaterThanOrEqual=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLineIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lineID is less than or equal to
        defaultVoucherLinesFiltering("lineID.lessThanOrEqual=" + DEFAULT_LINE_ID, "lineID.lessThanOrEqual=" + SMALLER_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLineIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lineID is less than
        defaultVoucherLinesFiltering("lineID.lessThan=" + UPDATED_LINE_ID, "lineID.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLineIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lineID is greater than
        defaultVoucherLinesFiltering("lineID.greaterThan=" + SMALLER_LINE_ID, "lineID.greaterThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnCode equals to
        defaultVoucherLinesFiltering("grnCode.equals=" + DEFAULT_GRN_CODE, "grnCode.equals=" + UPDATED_GRN_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnCode in
        defaultVoucherLinesFiltering("grnCode.in=" + DEFAULT_GRN_CODE + "," + UPDATED_GRN_CODE, "grnCode.in=" + UPDATED_GRN_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnCode is not null
        defaultVoucherLinesFiltering("grnCode.specified=true", "grnCode.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnCode contains
        defaultVoucherLinesFiltering("grnCode.contains=" + DEFAULT_GRN_CODE, "grnCode.contains=" + UPDATED_GRN_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnCode does not contain
        defaultVoucherLinesFiltering("grnCode.doesNotContain=" + UPDATED_GRN_CODE, "grnCode.doesNotContain=" + DEFAULT_GRN_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnType equals to
        defaultVoucherLinesFiltering("grnType.equals=" + DEFAULT_GRN_TYPE, "grnType.equals=" + UPDATED_GRN_TYPE);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnType in
        defaultVoucherLinesFiltering("grnType.in=" + DEFAULT_GRN_TYPE + "," + UPDATED_GRN_TYPE, "grnType.in=" + UPDATED_GRN_TYPE);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnType is not null
        defaultVoucherLinesFiltering("grnType.specified=true", "grnType.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnType contains
        defaultVoucherLinesFiltering("grnType.contains=" + DEFAULT_GRN_TYPE, "grnType.contains=" + UPDATED_GRN_TYPE);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByGrnTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where grnType does not contain
        defaultVoucherLinesFiltering("grnType.doesNotContain=" + UPDATED_GRN_TYPE, "grnType.doesNotContain=" + DEFAULT_GRN_TYPE);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByOriginalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where originalAmount equals to
        defaultVoucherLinesFiltering(
            "originalAmount.equals=" + DEFAULT_ORIGINAL_AMOUNT,
            "originalAmount.equals=" + UPDATED_ORIGINAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByOriginalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where originalAmount in
        defaultVoucherLinesFiltering(
            "originalAmount.in=" + DEFAULT_ORIGINAL_AMOUNT + "," + UPDATED_ORIGINAL_AMOUNT,
            "originalAmount.in=" + UPDATED_ORIGINAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByOriginalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where originalAmount is not null
        defaultVoucherLinesFiltering("originalAmount.specified=true", "originalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByOriginalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where originalAmount is greater than or equal to
        defaultVoucherLinesFiltering(
            "originalAmount.greaterThanOrEqual=" + DEFAULT_ORIGINAL_AMOUNT,
            "originalAmount.greaterThanOrEqual=" + UPDATED_ORIGINAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByOriginalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where originalAmount is less than or equal to
        defaultVoucherLinesFiltering(
            "originalAmount.lessThanOrEqual=" + DEFAULT_ORIGINAL_AMOUNT,
            "originalAmount.lessThanOrEqual=" + SMALLER_ORIGINAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByOriginalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where originalAmount is less than
        defaultVoucherLinesFiltering(
            "originalAmount.lessThan=" + UPDATED_ORIGINAL_AMOUNT,
            "originalAmount.lessThan=" + DEFAULT_ORIGINAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByOriginalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where originalAmount is greater than
        defaultVoucherLinesFiltering(
            "originalAmount.greaterThan=" + SMALLER_ORIGINAL_AMOUNT,
            "originalAmount.greaterThan=" + DEFAULT_ORIGINAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountOwingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountOwing equals to
        defaultVoucherLinesFiltering("amountOwing.equals=" + DEFAULT_AMOUNT_OWING, "amountOwing.equals=" + UPDATED_AMOUNT_OWING);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountOwingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountOwing in
        defaultVoucherLinesFiltering(
            "amountOwing.in=" + DEFAULT_AMOUNT_OWING + "," + UPDATED_AMOUNT_OWING,
            "amountOwing.in=" + UPDATED_AMOUNT_OWING
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountOwingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountOwing is not null
        defaultVoucherLinesFiltering("amountOwing.specified=true", "amountOwing.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountOwingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountOwing is greater than or equal to
        defaultVoucherLinesFiltering(
            "amountOwing.greaterThanOrEqual=" + DEFAULT_AMOUNT_OWING,
            "amountOwing.greaterThanOrEqual=" + UPDATED_AMOUNT_OWING
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountOwingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountOwing is less than or equal to
        defaultVoucherLinesFiltering(
            "amountOwing.lessThanOrEqual=" + DEFAULT_AMOUNT_OWING,
            "amountOwing.lessThanOrEqual=" + SMALLER_AMOUNT_OWING
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountOwingIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountOwing is less than
        defaultVoucherLinesFiltering("amountOwing.lessThan=" + UPDATED_AMOUNT_OWING, "amountOwing.lessThan=" + DEFAULT_AMOUNT_OWING);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountOwingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountOwing is greater than
        defaultVoucherLinesFiltering("amountOwing.greaterThan=" + SMALLER_AMOUNT_OWING, "amountOwing.greaterThan=" + DEFAULT_AMOUNT_OWING);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountAvailableIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountAvailable equals to
        defaultVoucherLinesFiltering(
            "discountAvailable.equals=" + DEFAULT_DISCOUNT_AVAILABLE,
            "discountAvailable.equals=" + UPDATED_DISCOUNT_AVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountAvailableIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountAvailable in
        defaultVoucherLinesFiltering(
            "discountAvailable.in=" + DEFAULT_DISCOUNT_AVAILABLE + "," + UPDATED_DISCOUNT_AVAILABLE,
            "discountAvailable.in=" + UPDATED_DISCOUNT_AVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountAvailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountAvailable is not null
        defaultVoucherLinesFiltering("discountAvailable.specified=true", "discountAvailable.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountAvailableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountAvailable is greater than or equal to
        defaultVoucherLinesFiltering(
            "discountAvailable.greaterThanOrEqual=" + DEFAULT_DISCOUNT_AVAILABLE,
            "discountAvailable.greaterThanOrEqual=" + UPDATED_DISCOUNT_AVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountAvailableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountAvailable is less than or equal to
        defaultVoucherLinesFiltering(
            "discountAvailable.lessThanOrEqual=" + DEFAULT_DISCOUNT_AVAILABLE,
            "discountAvailable.lessThanOrEqual=" + SMALLER_DISCOUNT_AVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountAvailableIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountAvailable is less than
        defaultVoucherLinesFiltering(
            "discountAvailable.lessThan=" + UPDATED_DISCOUNT_AVAILABLE,
            "discountAvailable.lessThan=" + DEFAULT_DISCOUNT_AVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountAvailableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountAvailable is greater than
        defaultVoucherLinesFiltering(
            "discountAvailable.greaterThan=" + SMALLER_DISCOUNT_AVAILABLE,
            "discountAvailable.greaterThan=" + DEFAULT_DISCOUNT_AVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountTakenIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountTaken equals to
        defaultVoucherLinesFiltering("discountTaken.equals=" + DEFAULT_DISCOUNT_TAKEN, "discountTaken.equals=" + UPDATED_DISCOUNT_TAKEN);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountTakenIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountTaken in
        defaultVoucherLinesFiltering(
            "discountTaken.in=" + DEFAULT_DISCOUNT_TAKEN + "," + UPDATED_DISCOUNT_TAKEN,
            "discountTaken.in=" + UPDATED_DISCOUNT_TAKEN
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountTakenIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountTaken is not null
        defaultVoucherLinesFiltering("discountTaken.specified=true", "discountTaken.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountTakenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountTaken is greater than or equal to
        defaultVoucherLinesFiltering(
            "discountTaken.greaterThanOrEqual=" + DEFAULT_DISCOUNT_TAKEN,
            "discountTaken.greaterThanOrEqual=" + UPDATED_DISCOUNT_TAKEN
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountTakenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountTaken is less than or equal to
        defaultVoucherLinesFiltering(
            "discountTaken.lessThanOrEqual=" + DEFAULT_DISCOUNT_TAKEN,
            "discountTaken.lessThanOrEqual=" + SMALLER_DISCOUNT_TAKEN
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountTakenIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountTaken is less than
        defaultVoucherLinesFiltering(
            "discountTaken.lessThan=" + UPDATED_DISCOUNT_TAKEN,
            "discountTaken.lessThan=" + DEFAULT_DISCOUNT_TAKEN
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByDiscountTakenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where discountTaken is greater than
        defaultVoucherLinesFiltering(
            "discountTaken.greaterThan=" + SMALLER_DISCOUNT_TAKEN,
            "discountTaken.greaterThan=" + DEFAULT_DISCOUNT_TAKEN
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountReceivedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountReceived equals to
        defaultVoucherLinesFiltering(
            "amountReceived.equals=" + DEFAULT_AMOUNT_RECEIVED,
            "amountReceived.equals=" + UPDATED_AMOUNT_RECEIVED
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountReceivedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountReceived in
        defaultVoucherLinesFiltering(
            "amountReceived.in=" + DEFAULT_AMOUNT_RECEIVED + "," + UPDATED_AMOUNT_RECEIVED,
            "amountReceived.in=" + UPDATED_AMOUNT_RECEIVED
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountReceivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountReceived is not null
        defaultVoucherLinesFiltering("amountReceived.specified=true", "amountReceived.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountReceivedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountReceived is greater than or equal to
        defaultVoucherLinesFiltering(
            "amountReceived.greaterThanOrEqual=" + DEFAULT_AMOUNT_RECEIVED,
            "amountReceived.greaterThanOrEqual=" + UPDATED_AMOUNT_RECEIVED
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountReceivedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountReceived is less than or equal to
        defaultVoucherLinesFiltering(
            "amountReceived.lessThanOrEqual=" + DEFAULT_AMOUNT_RECEIVED,
            "amountReceived.lessThanOrEqual=" + SMALLER_AMOUNT_RECEIVED
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountReceivedIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountReceived is less than
        defaultVoucherLinesFiltering(
            "amountReceived.lessThan=" + UPDATED_AMOUNT_RECEIVED,
            "amountReceived.lessThan=" + DEFAULT_AMOUNT_RECEIVED
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAmountReceivedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where amountReceived is greater than
        defaultVoucherLinesFiltering(
            "amountReceived.greaterThan=" + SMALLER_AMOUNT_RECEIVED,
            "amountReceived.greaterThan=" + DEFAULT_AMOUNT_RECEIVED
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmu equals to
        defaultVoucherLinesFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmu in
        defaultVoucherLinesFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmu is not null
        defaultVoucherLinesFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmu is greater than or equal to
        defaultVoucherLinesFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmu is less than or equal to
        defaultVoucherLinesFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmu is less than
        defaultVoucherLinesFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmu is greater than
        defaultVoucherLinesFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmd equals to
        defaultVoucherLinesFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmd in
        defaultVoucherLinesFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where lmd is not null
        defaultVoucherLinesFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where accountId equals to
        defaultVoucherLinesFiltering("accountId.equals=" + DEFAULT_ACCOUNT_ID, "accountId.equals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where accountId in
        defaultVoucherLinesFiltering("accountId.in=" + DEFAULT_ACCOUNT_ID + "," + UPDATED_ACCOUNT_ID, "accountId.in=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where accountId is not null
        defaultVoucherLinesFiltering("accountId.specified=true", "accountId.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where accountId is greater than or equal to
        defaultVoucherLinesFiltering(
            "accountId.greaterThanOrEqual=" + DEFAULT_ACCOUNT_ID,
            "accountId.greaterThanOrEqual=" + UPDATED_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAccountIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where accountId is less than or equal to
        defaultVoucherLinesFiltering("accountId.lessThanOrEqual=" + DEFAULT_ACCOUNT_ID, "accountId.lessThanOrEqual=" + SMALLER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where accountId is less than
        defaultVoucherLinesFiltering("accountId.lessThan=" + UPDATED_ACCOUNT_ID, "accountId.lessThan=" + DEFAULT_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllVoucherLinesByAccountIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        // Get all the voucherLinesList where accountId is greater than
        defaultVoucherLinesFiltering("accountId.greaterThan=" + SMALLER_ACCOUNT_ID, "accountId.greaterThan=" + DEFAULT_ACCOUNT_ID);
    }

    private void defaultVoucherLinesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultVoucherLinesShouldBeFound(shouldBeFound);
        defaultVoucherLinesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVoucherLinesShouldBeFound(String filter) throws Exception {
        restVoucherLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineID").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].grnCode").value(hasItem(DEFAULT_GRN_CODE)))
            .andExpect(jsonPath("$.[*].grnType").value(hasItem(DEFAULT_GRN_TYPE)))
            .andExpect(jsonPath("$.[*].originalAmount").value(hasItem(DEFAULT_ORIGINAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountOwing").value(hasItem(DEFAULT_AMOUNT_OWING.doubleValue())))
            .andExpect(jsonPath("$.[*].discountAvailable").value(hasItem(DEFAULT_DISCOUNT_AVAILABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountTaken").value(hasItem(DEFAULT_DISCOUNT_TAKEN.doubleValue())))
            .andExpect(jsonPath("$.[*].amountReceived").value(hasItem(DEFAULT_AMOUNT_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)));

        // Check, that the count call also returns 1
        restVoucherLinesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVoucherLinesShouldNotBeFound(String filter) throws Exception {
        restVoucherLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoucherLinesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVoucherLines() throws Exception {
        // Get the voucherLines
        restVoucherLinesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoucherLines() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucherLines
        VoucherLines updatedVoucherLines = voucherLinesRepository.findById(voucherLines.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVoucherLines are not directly saved in db
        em.detach(updatedVoucherLines);
        updatedVoucherLines
            .lineID(UPDATED_LINE_ID)
            .grnCode(UPDATED_GRN_CODE)
            .grnType(UPDATED_GRN_TYPE)
            .originalAmount(UPDATED_ORIGINAL_AMOUNT)
            .amountOwing(UPDATED_AMOUNT_OWING)
            .discountAvailable(UPDATED_DISCOUNT_AVAILABLE)
            .discountTaken(UPDATED_DISCOUNT_TAKEN)
            .amountReceived(UPDATED_AMOUNT_RECEIVED)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .accountId(UPDATED_ACCOUNT_ID);

        restVoucherLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoucherLines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVoucherLines))
            )
            .andExpect(status().isOk());

        // Validate the VoucherLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVoucherLinesToMatchAllProperties(updatedVoucherLines);
    }

    @Test
    @Transactional
    void putNonExistingVoucherLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherLines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherLines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voucherLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoucherLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voucherLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoucherLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherLinesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucherLines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoucherLinesWithPatch() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucherLines using partial update
        VoucherLines partialUpdatedVoucherLines = new VoucherLines();
        partialUpdatedVoucherLines.setId(voucherLines.getId());

        partialUpdatedVoucherLines
            .amountOwing(UPDATED_AMOUNT_OWING)
            .discountAvailable(UPDATED_DISCOUNT_AVAILABLE)
            .amountReceived(UPDATED_AMOUNT_RECEIVED)
            .accountId(UPDATED_ACCOUNT_ID);

        restVoucherLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoucherLines))
            )
            .andExpect(status().isOk());

        // Validate the VoucherLines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoucherLinesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVoucherLines, voucherLines),
            getPersistedVoucherLines(voucherLines)
        );
    }

    @Test
    @Transactional
    void fullUpdateVoucherLinesWithPatch() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucherLines using partial update
        VoucherLines partialUpdatedVoucherLines = new VoucherLines();
        partialUpdatedVoucherLines.setId(voucherLines.getId());

        partialUpdatedVoucherLines
            .lineID(UPDATED_LINE_ID)
            .grnCode(UPDATED_GRN_CODE)
            .grnType(UPDATED_GRN_TYPE)
            .originalAmount(UPDATED_ORIGINAL_AMOUNT)
            .amountOwing(UPDATED_AMOUNT_OWING)
            .discountAvailable(UPDATED_DISCOUNT_AVAILABLE)
            .discountTaken(UPDATED_DISCOUNT_TAKEN)
            .amountReceived(UPDATED_AMOUNT_RECEIVED)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .accountId(UPDATED_ACCOUNT_ID);

        restVoucherLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoucherLines))
            )
            .andExpect(status().isOk());

        // Validate the VoucherLines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoucherLinesUpdatableFieldsEquals(partialUpdatedVoucherLines, getPersistedVoucherLines(partialUpdatedVoucherLines));
    }

    @Test
    @Transactional
    void patchNonExistingVoucherLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherLines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voucherLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voucherLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoucherLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voucherLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoucherLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherLinesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(voucherLines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoucherLines() throws Exception {
        // Initialize the database
        insertedVoucherLines = voucherLinesRepository.saveAndFlush(voucherLines);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the voucherLines
        restVoucherLinesMockMvc
            .perform(delete(ENTITY_API_URL_ID, voucherLines.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return voucherLinesRepository.count();
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

    protected VoucherLines getPersistedVoucherLines(VoucherLines voucherLines) {
        return voucherLinesRepository.findById(voucherLines.getId()).orElseThrow();
    }

    protected void assertPersistedVoucherLinesToMatchAllProperties(VoucherLines expectedVoucherLines) {
        assertVoucherLinesAllPropertiesEquals(expectedVoucherLines, getPersistedVoucherLines(expectedVoucherLines));
    }

    protected void assertPersistedVoucherLinesToMatchUpdatableProperties(VoucherLines expectedVoucherLines) {
        assertVoucherLinesAllUpdatablePropertiesEquals(expectedVoucherLines, getPersistedVoucherLines(expectedVoucherLines));
    }
}
