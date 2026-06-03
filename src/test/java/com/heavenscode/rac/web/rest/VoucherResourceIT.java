package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.VoucherAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Voucher;
import com.heavenscode.rac.repository.VoucherRepository;
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
 * Integration tests for the {@link VoucherResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_VOUCHER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VOUCHER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SUPPLIER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_ADDRESS = "BBBBBBBBBB";

    private static final Float DEFAULT_TOTAL_AMOUNT = 1F;
    private static final Float UPDATED_TOTAL_AMOUNT = 2F;
    private static final Float SMALLER_TOTAL_AMOUNT = 1F - 1F;

    private static final String DEFAULT_TOTAL_AMOUNT_IN_WORD = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_AMOUNT_IN_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TERM_ID = 1;
    private static final Integer UPDATED_TERM_ID = 2;
    private static final Integer SMALLER_TERM_ID = 1 - 1;

    private static final String DEFAULT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_TERM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_AMOUNT_PAID = 1F;
    private static final Float UPDATED_AMOUNT_PAID = 2F;
    private static final Float SMALLER_AMOUNT_PAID = 1F - 1F;

    private static final Integer DEFAULT_SUPPLIER_ID = 1;
    private static final Integer UPDATED_SUPPLIER_ID = 2;
    private static final Integer SMALLER_SUPPLIER_ID = 1 - 1;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;
    private static final Integer SMALLER_CREATED_BY = 1 - 1;

    private static final String ENTITY_API_URL = "/api/vouchers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherMockMvc;

    private Voucher voucher;

    private Voucher insertedVoucher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createEntity() {
        return new Voucher()
            .code(DEFAULT_CODE)
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .supplierName(DEFAULT_SUPPLIER_NAME)
            .supplierAddress(DEFAULT_SUPPLIER_ADDRESS)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .totalAmountInWord(DEFAULT_TOTAL_AMOUNT_IN_WORD)
            .comments(DEFAULT_COMMENTS)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .termId(DEFAULT_TERM_ID)
            .term(DEFAULT_TERM)
            .date(DEFAULT_DATE)
            .amountPaid(DEFAULT_AMOUNT_PAID)
            .supplierID(DEFAULT_SUPPLIER_ID)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createUpdatedEntity() {
        return new Voucher()
            .code(UPDATED_CODE)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountInWord(UPDATED_TOTAL_AMOUNT_IN_WORD)
            .comments(UPDATED_COMMENTS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termId(UPDATED_TERM_ID)
            .term(UPDATED_TERM)
            .date(UPDATED_DATE)
            .amountPaid(UPDATED_AMOUNT_PAID)
            .supplierID(UPDATED_SUPPLIER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY);
    }

    @BeforeEach
    public void initTest() {
        voucher = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedVoucher != null) {
            voucherRepository.delete(insertedVoucher);
            insertedVoucher = null;
        }
    }

    @Test
    @Transactional
    void createVoucher() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Voucher
        var returnedVoucher = om.readValue(
            restVoucherMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucher)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Voucher.class
        );

        // Validate the Voucher in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVoucherUpdatableFieldsEquals(returnedVoucher, getPersistedVoucher(returnedVoucher));

        insertedVoucher = returnedVoucher;
    }

    @Test
    @Transactional
    void createVoucherWithExistingId() throws Exception {
        // Create the Voucher with an existing ID
        voucher.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucher)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVouchers() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME)))
            .andExpect(jsonPath("$.[*].supplierAddress").value(hasItem(DEFAULT_SUPPLIER_ADDRESS)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmountInWord").value(hasItem(DEFAULT_TOTAL_AMOUNT_IN_WORD)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].termId").value(hasItem(DEFAULT_TERM_ID)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].supplierID").value(hasItem(DEFAULT_SUPPLIER_ID)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    @Transactional
    void getVoucher() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get the voucher
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL_ID, voucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucher.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.voucherDate").value(DEFAULT_VOUCHER_DATE.toString()))
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME))
            .andExpect(jsonPath("$.supplierAddress").value(DEFAULT_SUPPLIER_ADDRESS))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalAmountInWord").value(DEFAULT_TOTAL_AMOUNT_IN_WORD))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.termId").value(DEFAULT_TERM_ID))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amountPaid").value(DEFAULT_AMOUNT_PAID.doubleValue()))
            .andExpect(jsonPath("$.supplierID").value(DEFAULT_SUPPLIER_ID))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    void getVouchersByIdFiltering() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        Long id = voucher.getId();

        defaultVoucherFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultVoucherFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultVoucherFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code equals to
        defaultVoucherFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code in
        defaultVoucherFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code is not null
        defaultVoucherFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code contains
        defaultVoucherFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where code does not contain
        defaultVoucherFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherDate equals to
        defaultVoucherFiltering("voucherDate.equals=" + DEFAULT_VOUCHER_DATE, "voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherDate in
        defaultVoucherFiltering(
            "voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE,
            "voucherDate.in=" + UPDATED_VOUCHER_DATE
        );
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where voucherDate is not null
        defaultVoucherFiltering("voucherDate.specified=true", "voucherDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierName equals to
        defaultVoucherFiltering("supplierName.equals=" + DEFAULT_SUPPLIER_NAME, "supplierName.equals=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierName in
        defaultVoucherFiltering(
            "supplierName.in=" + DEFAULT_SUPPLIER_NAME + "," + UPDATED_SUPPLIER_NAME,
            "supplierName.in=" + UPDATED_SUPPLIER_NAME
        );
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierName is not null
        defaultVoucherFiltering("supplierName.specified=true", "supplierName.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierNameContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierName contains
        defaultVoucherFiltering("supplierName.contains=" + DEFAULT_SUPPLIER_NAME, "supplierName.contains=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierName does not contain
        defaultVoucherFiltering(
            "supplierName.doesNotContain=" + UPDATED_SUPPLIER_NAME,
            "supplierName.doesNotContain=" + DEFAULT_SUPPLIER_NAME
        );
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierAddress equals to
        defaultVoucherFiltering("supplierAddress.equals=" + DEFAULT_SUPPLIER_ADDRESS, "supplierAddress.equals=" + UPDATED_SUPPLIER_ADDRESS);
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierAddress in
        defaultVoucherFiltering(
            "supplierAddress.in=" + DEFAULT_SUPPLIER_ADDRESS + "," + UPDATED_SUPPLIER_ADDRESS,
            "supplierAddress.in=" + UPDATED_SUPPLIER_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierAddress is not null
        defaultVoucherFiltering("supplierAddress.specified=true", "supplierAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierAddress contains
        defaultVoucherFiltering(
            "supplierAddress.contains=" + DEFAULT_SUPPLIER_ADDRESS,
            "supplierAddress.contains=" + UPDATED_SUPPLIER_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierAddress does not contain
        defaultVoucherFiltering(
            "supplierAddress.doesNotContain=" + UPDATED_SUPPLIER_ADDRESS,
            "supplierAddress.doesNotContain=" + DEFAULT_SUPPLIER_ADDRESS
        );
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmount equals to
        defaultVoucherFiltering("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT, "totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmount in
        defaultVoucherFiltering(
            "totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT,
            "totalAmount.in=" + UPDATED_TOTAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmount is not null
        defaultVoucherFiltering("totalAmount.specified=true", "totalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmount is greater than or equal to
        defaultVoucherFiltering(
            "totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT,
            "totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmount is less than or equal to
        defaultVoucherFiltering(
            "totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT,
            "totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmount is less than
        defaultVoucherFiltering("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT, "totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmount is greater than
        defaultVoucherFiltering("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT, "totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountInWordIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmountInWord equals to
        defaultVoucherFiltering(
            "totalAmountInWord.equals=" + DEFAULT_TOTAL_AMOUNT_IN_WORD,
            "totalAmountInWord.equals=" + UPDATED_TOTAL_AMOUNT_IN_WORD
        );
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountInWordIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmountInWord in
        defaultVoucherFiltering(
            "totalAmountInWord.in=" + DEFAULT_TOTAL_AMOUNT_IN_WORD + "," + UPDATED_TOTAL_AMOUNT_IN_WORD,
            "totalAmountInWord.in=" + UPDATED_TOTAL_AMOUNT_IN_WORD
        );
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountInWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmountInWord is not null
        defaultVoucherFiltering("totalAmountInWord.specified=true", "totalAmountInWord.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountInWordContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmountInWord contains
        defaultVoucherFiltering(
            "totalAmountInWord.contains=" + DEFAULT_TOTAL_AMOUNT_IN_WORD,
            "totalAmountInWord.contains=" + UPDATED_TOTAL_AMOUNT_IN_WORD
        );
    }

    @Test
    @Transactional
    void getAllVouchersByTotalAmountInWordNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where totalAmountInWord does not contain
        defaultVoucherFiltering(
            "totalAmountInWord.doesNotContain=" + UPDATED_TOTAL_AMOUNT_IN_WORD,
            "totalAmountInWord.doesNotContain=" + DEFAULT_TOTAL_AMOUNT_IN_WORD
        );
    }

    @Test
    @Transactional
    void getAllVouchersByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where comments equals to
        defaultVoucherFiltering("comments.equals=" + DEFAULT_COMMENTS, "comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllVouchersByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where comments in
        defaultVoucherFiltering("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS, "comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllVouchersByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where comments is not null
        defaultVoucherFiltering("comments.specified=true", "comments.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByCommentsContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where comments contains
        defaultVoucherFiltering("comments.contains=" + DEFAULT_COMMENTS, "comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void getAllVouchersByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where comments does not contain
        defaultVoucherFiltering("comments.doesNotContain=" + UPDATED_COMMENTS, "comments.doesNotContain=" + DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void getAllVouchersByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmu equals to
        defaultVoucherFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVouchersByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmu in
        defaultVoucherFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVouchersByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmu is not null
        defaultVoucherFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmu is greater than or equal to
        defaultVoucherFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVouchersByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmu is less than or equal to
        defaultVoucherFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllVouchersByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmu is less than
        defaultVoucherFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllVouchersByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmu is greater than
        defaultVoucherFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllVouchersByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmd equals to
        defaultVoucherFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllVouchersByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmd in
        defaultVoucherFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllVouchersByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lmd is not null
        defaultVoucherFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByTermIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where termId equals to
        defaultVoucherFiltering("termId.equals=" + DEFAULT_TERM_ID, "termId.equals=" + UPDATED_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVouchersByTermIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where termId in
        defaultVoucherFiltering("termId.in=" + DEFAULT_TERM_ID + "," + UPDATED_TERM_ID, "termId.in=" + UPDATED_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVouchersByTermIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where termId is not null
        defaultVoucherFiltering("termId.specified=true", "termId.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByTermIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where termId is greater than or equal to
        defaultVoucherFiltering("termId.greaterThanOrEqual=" + DEFAULT_TERM_ID, "termId.greaterThanOrEqual=" + UPDATED_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVouchersByTermIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where termId is less than or equal to
        defaultVoucherFiltering("termId.lessThanOrEqual=" + DEFAULT_TERM_ID, "termId.lessThanOrEqual=" + SMALLER_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVouchersByTermIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where termId is less than
        defaultVoucherFiltering("termId.lessThan=" + UPDATED_TERM_ID, "termId.lessThan=" + DEFAULT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVouchersByTermIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where termId is greater than
        defaultVoucherFiltering("termId.greaterThan=" + SMALLER_TERM_ID, "termId.greaterThan=" + DEFAULT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVouchersByTermIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where term equals to
        defaultVoucherFiltering("term.equals=" + DEFAULT_TERM, "term.equals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllVouchersByTermIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where term in
        defaultVoucherFiltering("term.in=" + DEFAULT_TERM + "," + UPDATED_TERM, "term.in=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllVouchersByTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where term is not null
        defaultVoucherFiltering("term.specified=true", "term.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByTermContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where term contains
        defaultVoucherFiltering("term.contains=" + DEFAULT_TERM, "term.contains=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllVouchersByTermNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where term does not contain
        defaultVoucherFiltering("term.doesNotContain=" + UPDATED_TERM, "term.doesNotContain=" + DEFAULT_TERM);
    }

    @Test
    @Transactional
    void getAllVouchersByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where date equals to
        defaultVoucherFiltering("date.equals=" + DEFAULT_DATE, "date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where date in
        defaultVoucherFiltering("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE, "date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where date is not null
        defaultVoucherFiltering("date.specified=true", "date.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByAmountPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amountPaid equals to
        defaultVoucherFiltering("amountPaid.equals=" + DEFAULT_AMOUNT_PAID, "amountPaid.equals=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountPaidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amountPaid in
        defaultVoucherFiltering("amountPaid.in=" + DEFAULT_AMOUNT_PAID + "," + UPDATED_AMOUNT_PAID, "amountPaid.in=" + UPDATED_AMOUNT_PAID);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amountPaid is not null
        defaultVoucherFiltering("amountPaid.specified=true", "amountPaid.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByAmountPaidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amountPaid is greater than or equal to
        defaultVoucherFiltering(
            "amountPaid.greaterThanOrEqual=" + DEFAULT_AMOUNT_PAID,
            "amountPaid.greaterThanOrEqual=" + UPDATED_AMOUNT_PAID
        );
    }

    @Test
    @Transactional
    void getAllVouchersByAmountPaidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amountPaid is less than or equal to
        defaultVoucherFiltering("amountPaid.lessThanOrEqual=" + DEFAULT_AMOUNT_PAID, "amountPaid.lessThanOrEqual=" + SMALLER_AMOUNT_PAID);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountPaidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amountPaid is less than
        defaultVoucherFiltering("amountPaid.lessThan=" + UPDATED_AMOUNT_PAID, "amountPaid.lessThan=" + DEFAULT_AMOUNT_PAID);
    }

    @Test
    @Transactional
    void getAllVouchersByAmountPaidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where amountPaid is greater than
        defaultVoucherFiltering("amountPaid.greaterThan=" + SMALLER_AMOUNT_PAID, "amountPaid.greaterThan=" + DEFAULT_AMOUNT_PAID);
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierID equals to
        defaultVoucherFiltering("supplierID.equals=" + DEFAULT_SUPPLIER_ID, "supplierID.equals=" + UPDATED_SUPPLIER_ID);
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierID in
        defaultVoucherFiltering("supplierID.in=" + DEFAULT_SUPPLIER_ID + "," + UPDATED_SUPPLIER_ID, "supplierID.in=" + UPDATED_SUPPLIER_ID);
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierID is not null
        defaultVoucherFiltering("supplierID.specified=true", "supplierID.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierID is greater than or equal to
        defaultVoucherFiltering(
            "supplierID.greaterThanOrEqual=" + DEFAULT_SUPPLIER_ID,
            "supplierID.greaterThanOrEqual=" + UPDATED_SUPPLIER_ID
        );
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierID is less than or equal to
        defaultVoucherFiltering("supplierID.lessThanOrEqual=" + DEFAULT_SUPPLIER_ID, "supplierID.lessThanOrEqual=" + SMALLER_SUPPLIER_ID);
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierID is less than
        defaultVoucherFiltering("supplierID.lessThan=" + UPDATED_SUPPLIER_ID, "supplierID.lessThan=" + DEFAULT_SUPPLIER_ID);
    }

    @Test
    @Transactional
    void getAllVouchersBySupplierIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where supplierID is greater than
        defaultVoucherFiltering("supplierID.greaterThan=" + SMALLER_SUPPLIER_ID, "supplierID.greaterThan=" + DEFAULT_SUPPLIER_ID);
    }

    @Test
    @Transactional
    void getAllVouchersByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where isActive equals to
        defaultVoucherFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllVouchersByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where isActive in
        defaultVoucherFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllVouchersByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where isActive is not null
        defaultVoucherFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where createdBy equals to
        defaultVoucherFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where createdBy in
        defaultVoucherFiltering("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY, "createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where createdBy is not null
        defaultVoucherFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByCreatedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where createdBy is greater than or equal to
        defaultVoucherFiltering("createdBy.greaterThanOrEqual=" + DEFAULT_CREATED_BY, "createdBy.greaterThanOrEqual=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByCreatedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where createdBy is less than or equal to
        defaultVoucherFiltering("createdBy.lessThanOrEqual=" + DEFAULT_CREATED_BY, "createdBy.lessThanOrEqual=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByCreatedByIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where createdBy is less than
        defaultVoucherFiltering("createdBy.lessThan=" + UPDATED_CREATED_BY, "createdBy.lessThan=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByCreatedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where createdBy is greater than
        defaultVoucherFiltering("createdBy.greaterThan=" + SMALLER_CREATED_BY, "createdBy.greaterThan=" + DEFAULT_CREATED_BY);
    }

    private void defaultVoucherFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultVoucherShouldBeFound(shouldBeFound);
        defaultVoucherShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVoucherShouldBeFound(String filter) throws Exception {
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME)))
            .andExpect(jsonPath("$.[*].supplierAddress").value(hasItem(DEFAULT_SUPPLIER_ADDRESS)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmountInWord").value(hasItem(DEFAULT_TOTAL_AMOUNT_IN_WORD)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].termId").value(hasItem(DEFAULT_TERM_ID)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountPaid").value(hasItem(DEFAULT_AMOUNT_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].supplierID").value(hasItem(DEFAULT_SUPPLIER_ID)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));

        // Check, that the count call also returns 1
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVoucherShouldNotBeFound(String filter) throws Exception {
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoucherMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVoucher() throws Exception {
        // Get the voucher
        restVoucherMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoucher() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucher
        Voucher updatedVoucher = voucherRepository.findById(voucher.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVoucher are not directly saved in db
        em.detach(updatedVoucher);
        updatedVoucher
            .code(UPDATED_CODE)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountInWord(UPDATED_TOTAL_AMOUNT_IN_WORD)
            .comments(UPDATED_COMMENTS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termId(UPDATED_TERM_ID)
            .term(UPDATED_TERM)
            .date(UPDATED_DATE)
            .amountPaid(UPDATED_AMOUNT_PAID)
            .supplierID(UPDATED_SUPPLIER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY);

        restVoucherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoucher.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVoucher))
            )
            .andExpect(status().isOk());

        // Validate the Voucher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVoucherToMatchAllProperties(updatedVoucher);
    }

    @Test
    @Transactional
    void putNonExistingVoucher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucher.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(put(ENTITY_API_URL_ID, voucher.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucher)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoucher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucher.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voucher))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoucher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucher.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucher)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voucher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoucherWithPatch() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucher using partial update
        Voucher partialUpdatedVoucher = new Voucher();
        partialUpdatedVoucher.setId(voucher.getId());

        partialUpdatedVoucher
            .voucherDate(UPDATED_VOUCHER_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountInWord(UPDATED_TOTAL_AMOUNT_IN_WORD)
            .comments(UPDATED_COMMENTS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termId(UPDATED_TERM_ID)
            .date(UPDATED_DATE)
            .amountPaid(UPDATED_AMOUNT_PAID)
            .supplierID(UPDATED_SUPPLIER_ID)
            .isActive(UPDATED_IS_ACTIVE);

        restVoucherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucher.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoucher))
            )
            .andExpect(status().isOk());

        // Validate the Voucher in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoucherUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVoucher, voucher), getPersistedVoucher(voucher));
    }

    @Test
    @Transactional
    void fullUpdateVoucherWithPatch() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucher using partial update
        Voucher partialUpdatedVoucher = new Voucher();
        partialUpdatedVoucher.setId(voucher.getId());

        partialUpdatedVoucher
            .code(UPDATED_CODE)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalAmountInWord(UPDATED_TOTAL_AMOUNT_IN_WORD)
            .comments(UPDATED_COMMENTS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termId(UPDATED_TERM_ID)
            .term(UPDATED_TERM)
            .date(UPDATED_DATE)
            .amountPaid(UPDATED_AMOUNT_PAID)
            .supplierID(UPDATED_SUPPLIER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY);

        restVoucherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucher.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoucher))
            )
            .andExpect(status().isOk());

        // Validate the Voucher in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoucherUpdatableFieldsEquals(partialUpdatedVoucher, getPersistedVoucher(partialUpdatedVoucher));
    }

    @Test
    @Transactional
    void patchNonExistingVoucher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucher.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voucher.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(voucher))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoucher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucher.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voucher))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoucher() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucher.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(voucher)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voucher in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoucher() throws Exception {
        // Initialize the database
        insertedVoucher = voucherRepository.saveAndFlush(voucher);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the voucher
        restVoucherMockMvc
            .perform(delete(ENTITY_API_URL_ID, voucher.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return voucherRepository.count();
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

    protected Voucher getPersistedVoucher(Voucher voucher) {
        return voucherRepository.findById(voucher.getId()).orElseThrow();
    }

    protected void assertPersistedVoucherToMatchAllProperties(Voucher expectedVoucher) {
        assertVoucherAllPropertiesEquals(expectedVoucher, getPersistedVoucher(expectedVoucher));
    }

    protected void assertPersistedVoucherToMatchUpdatableProperties(Voucher expectedVoucher) {
        assertVoucherAllUpdatablePropertiesEquals(expectedVoucher, getPersistedVoucher(expectedVoucher));
    }
}
