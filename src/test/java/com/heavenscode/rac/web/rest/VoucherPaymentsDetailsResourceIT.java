package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.VoucherPaymentsDetailsAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.VoucherPaymentsDetails;
import com.heavenscode.rac.repository.VoucherPaymentsDetailsRepository;
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
 * Integration tests for the {@link VoucherPaymentsDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherPaymentsDetailsResourceIT {

    private static final Integer DEFAULT_LINE_ID = 1;
    private static final Integer UPDATED_LINE_ID = 2;
    private static final Integer SMALLER_LINE_ID = 1 - 1;

    private static final Float DEFAULT_PAYMENT_AMOUNT = 1F;
    private static final Float UPDATED_PAYMENT_AMOUNT = 2F;
    private static final Float SMALLER_PAYMENT_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_TOTAL_VOUCHER_AMOUNT = 1F;
    private static final Float UPDATED_TOTAL_VOUCHER_AMOUNT = 2F;
    private static final Float SMALLER_TOTAL_VOUCHER_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_CHECKQUE_AMOUNT = 1F;
    private static final Float UPDATED_CHECKQUE_AMOUNT = 2F;
    private static final Float SMALLER_CHECKQUE_AMOUNT = 1F - 1F;

    private static final String DEFAULT_CHECKQUE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CHECKQUE_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CHECKQUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECKQUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CHECKQUE_EXPIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECKQUE_EXPIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BANK_ID = 1;
    private static final Integer UPDATED_BANK_ID = 2;
    private static final Integer SMALLER_BANK_ID = 1 - 1;

    private static final String DEFAULT_CREDIT_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD_NO = "BBBBBBBBBB";

    private static final Float DEFAULT_CREDIT_CARD_AMOUNT = 1F;
    private static final Float UPDATED_CREDIT_CARD_AMOUNT = 2F;
    private static final Float SMALLER_CREDIT_CARD_AMOUNT = 1F - 1F;

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_LMU = "AAAAAAAAAA";
    private static final String UPDATED_LMU = "BBBBBBBBBB";

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TERM_ID = 1;
    private static final Integer UPDATED_TERM_ID = 2;
    private static final Integer SMALLER_TERM_ID = 1 - 1;

    private static final String DEFAULT_TERM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TERM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCOUNT_NO = 1;
    private static final Integer UPDATED_ACCOUNT_NO = 2;
    private static final Integer SMALLER_ACCOUNT_NO = 1 - 1;

    private static final Long DEFAULT_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_ACCOUNT_NUMBER = 2L;
    private static final Long SMALLER_ACCOUNT_NUMBER = 1L - 1L;

    private static final Integer DEFAULT_ACCOUNT_ID = 1;
    private static final Integer UPDATED_ACCOUNT_ID = 2;
    private static final Integer SMALLER_ACCOUNT_ID = 1 - 1;

    private static final String DEFAULT_ACCOUNT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHEQUE_STATUS_ID = 1;
    private static final Integer UPDATED_CHEQUE_STATUS_ID = 2;
    private static final Integer SMALLER_CHEQUE_STATUS_ID = 1 - 1;

    private static final Boolean DEFAULT_IS_DEPOSIT = false;
    private static final Boolean UPDATED_IS_DEPOSIT = true;

    private static final Instant DEFAULT_DEPOSITED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPOSITED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_COMPANY_BANK_ID = 1;
    private static final Integer UPDATED_COMPANY_BANK_ID = 2;
    private static final Integer SMALLER_COMPANY_BANK_ID = 1 - 1;

    private static final Boolean DEFAULT_IS_BANK_RECONCILIATION = false;
    private static final Boolean UPDATED_IS_BANK_RECONCILIATION = true;

    private static final String ENTITY_API_URL = "/api/voucher-payments-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VoucherPaymentsDetailsRepository voucherPaymentsDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherPaymentsDetailsMockMvc;

    private VoucherPaymentsDetails voucherPaymentsDetails;

    private VoucherPaymentsDetails insertedVoucherPaymentsDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherPaymentsDetails createEntity() {
        return new VoucherPaymentsDetails()
            .lineID(DEFAULT_LINE_ID)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .totalVoucherAmount(DEFAULT_TOTAL_VOUCHER_AMOUNT)
            .checkqueAmount(DEFAULT_CHECKQUE_AMOUNT)
            .checkqueNo(DEFAULT_CHECKQUE_NO)
            .checkqueDate(DEFAULT_CHECKQUE_DATE)
            .checkqueExpireDate(DEFAULT_CHECKQUE_EXPIRE_DATE)
            .bankName(DEFAULT_BANK_NAME)
            .bankID(DEFAULT_BANK_ID)
            .creditCardNo(DEFAULT_CREDIT_CARD_NO)
            .creditCardAmount(DEFAULT_CREDIT_CARD_AMOUNT)
            .reference(DEFAULT_REFERENCE)
            .otherDetails(DEFAULT_OTHER_DETAILS)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .termID(DEFAULT_TERM_ID)
            .termName(DEFAULT_TERM_NAME)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountId(DEFAULT_ACCOUNT_ID)
            .accountCode(DEFAULT_ACCOUNT_CODE)
            .chequeStatusId(DEFAULT_CHEQUE_STATUS_ID)
            .isDeposit(DEFAULT_IS_DEPOSIT)
            .depositedDate(DEFAULT_DEPOSITED_DATE)
            .companyBankId(DEFAULT_COMPANY_BANK_ID)
            .isBankReconciliation(DEFAULT_IS_BANK_RECONCILIATION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherPaymentsDetails createUpdatedEntity() {
        return new VoucherPaymentsDetails()
            .lineID(UPDATED_LINE_ID)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .totalVoucherAmount(UPDATED_TOTAL_VOUCHER_AMOUNT)
            .checkqueAmount(UPDATED_CHECKQUE_AMOUNT)
            .checkqueNo(UPDATED_CHECKQUE_NO)
            .checkqueDate(UPDATED_CHECKQUE_DATE)
            .checkqueExpireDate(UPDATED_CHECKQUE_EXPIRE_DATE)
            .bankName(UPDATED_BANK_NAME)
            .bankID(UPDATED_BANK_ID)
            .creditCardNo(UPDATED_CREDIT_CARD_NO)
            .creditCardAmount(UPDATED_CREDIT_CARD_AMOUNT)
            .reference(UPDATED_REFERENCE)
            .otherDetails(UPDATED_OTHER_DETAILS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termID(UPDATED_TERM_ID)
            .termName(UPDATED_TERM_NAME)
            .accountNo(UPDATED_ACCOUNT_NO)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .chequeStatusId(UPDATED_CHEQUE_STATUS_ID)
            .isDeposit(UPDATED_IS_DEPOSIT)
            .depositedDate(UPDATED_DEPOSITED_DATE)
            .companyBankId(UPDATED_COMPANY_BANK_ID)
            .isBankReconciliation(UPDATED_IS_BANK_RECONCILIATION);
    }

    @BeforeEach
    public void initTest() {
        voucherPaymentsDetails = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedVoucherPaymentsDetails != null) {
            voucherPaymentsDetailsRepository.delete(insertedVoucherPaymentsDetails);
            insertedVoucherPaymentsDetails = null;
        }
    }

    @Test
    @Transactional
    void createVoucherPaymentsDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the VoucherPaymentsDetails
        var returnedVoucherPaymentsDetails = om.readValue(
            restVoucherPaymentsDetailsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucherPaymentsDetails)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VoucherPaymentsDetails.class
        );

        // Validate the VoucherPaymentsDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertVoucherPaymentsDetailsUpdatableFieldsEquals(
            returnedVoucherPaymentsDetails,
            getPersistedVoucherPaymentsDetails(returnedVoucherPaymentsDetails)
        );

        insertedVoucherPaymentsDetails = returnedVoucherPaymentsDetails;
    }

    @Test
    @Transactional
    void createVoucherPaymentsDetailsWithExistingId() throws Exception {
        // Create the VoucherPaymentsDetails with an existing ID
        voucherPaymentsDetails.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherPaymentsDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucherPaymentsDetails)))
            .andExpect(status().isBadRequest());

        // Validate the VoucherPaymentsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetails() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList
        restVoucherPaymentsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherPaymentsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineID").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalVoucherAmount").value(hasItem(DEFAULT_TOTAL_VOUCHER_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].checkqueAmount").value(hasItem(DEFAULT_CHECKQUE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].checkqueNo").value(hasItem(DEFAULT_CHECKQUE_NO)))
            .andExpect(jsonPath("$.[*].checkqueDate").value(hasItem(DEFAULT_CHECKQUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].checkqueExpireDate").value(hasItem(DEFAULT_CHECKQUE_EXPIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankID").value(hasItem(DEFAULT_BANK_ID)))
            .andExpect(jsonPath("$.[*].creditCardNo").value(hasItem(DEFAULT_CREDIT_CARD_NO)))
            .andExpect(jsonPath("$.[*].creditCardAmount").value(hasItem(DEFAULT_CREDIT_CARD_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].termID").value(hasItem(DEFAULT_TERM_ID)))
            .andExpect(jsonPath("$.[*].termName").value(hasItem(DEFAULT_TERM_NAME)))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].accountCode").value(hasItem(DEFAULT_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].chequeStatusId").value(hasItem(DEFAULT_CHEQUE_STATUS_ID)))
            .andExpect(jsonPath("$.[*].isDeposit").value(hasItem(DEFAULT_IS_DEPOSIT.booleanValue())))
            .andExpect(jsonPath("$.[*].depositedDate").value(hasItem(DEFAULT_DEPOSITED_DATE.toString())))
            .andExpect(jsonPath("$.[*].companyBankId").value(hasItem(DEFAULT_COMPANY_BANK_ID)))
            .andExpect(jsonPath("$.[*].isBankReconciliation").value(hasItem(DEFAULT_IS_BANK_RECONCILIATION.booleanValue())));
    }

    @Test
    @Transactional
    void getVoucherPaymentsDetails() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get the voucherPaymentsDetails
        restVoucherPaymentsDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, voucherPaymentsDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucherPaymentsDetails.getId().intValue()))
            .andExpect(jsonPath("$.lineID").value(DEFAULT_LINE_ID))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalVoucherAmount").value(DEFAULT_TOTAL_VOUCHER_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.checkqueAmount").value(DEFAULT_CHECKQUE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.checkqueNo").value(DEFAULT_CHECKQUE_NO))
            .andExpect(jsonPath("$.checkqueDate").value(DEFAULT_CHECKQUE_DATE.toString()))
            .andExpect(jsonPath("$.checkqueExpireDate").value(DEFAULT_CHECKQUE_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.bankID").value(DEFAULT_BANK_ID))
            .andExpect(jsonPath("$.creditCardNo").value(DEFAULT_CREDIT_CARD_NO))
            .andExpect(jsonPath("$.creditCardAmount").value(DEFAULT_CREDIT_CARD_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.otherDetails").value(DEFAULT_OTHER_DETAILS))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.termID").value(DEFAULT_TERM_ID))
            .andExpect(jsonPath("$.termName").value(DEFAULT_TERM_NAME))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.accountCode").value(DEFAULT_ACCOUNT_CODE))
            .andExpect(jsonPath("$.chequeStatusId").value(DEFAULT_CHEQUE_STATUS_ID))
            .andExpect(jsonPath("$.isDeposit").value(DEFAULT_IS_DEPOSIT.booleanValue()))
            .andExpect(jsonPath("$.depositedDate").value(DEFAULT_DEPOSITED_DATE.toString()))
            .andExpect(jsonPath("$.companyBankId").value(DEFAULT_COMPANY_BANK_ID))
            .andExpect(jsonPath("$.isBankReconciliation").value(DEFAULT_IS_BANK_RECONCILIATION.booleanValue()));
    }

    @Test
    @Transactional
    void getVoucherPaymentsDetailsByIdFiltering() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        Long id = voucherPaymentsDetails.getId();

        defaultVoucherPaymentsDetailsFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultVoucherPaymentsDetailsFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultVoucherPaymentsDetailsFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLineIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lineID equals to
        defaultVoucherPaymentsDetailsFiltering("lineID.equals=" + DEFAULT_LINE_ID, "lineID.equals=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLineIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lineID in
        defaultVoucherPaymentsDetailsFiltering("lineID.in=" + DEFAULT_LINE_ID + "," + UPDATED_LINE_ID, "lineID.in=" + UPDATED_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLineIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lineID is not null
        defaultVoucherPaymentsDetailsFiltering("lineID.specified=true", "lineID.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLineIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lineID is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "lineID.greaterThanOrEqual=" + DEFAULT_LINE_ID,
            "lineID.greaterThanOrEqual=" + UPDATED_LINE_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLineIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lineID is less than or equal to
        defaultVoucherPaymentsDetailsFiltering("lineID.lessThanOrEqual=" + DEFAULT_LINE_ID, "lineID.lessThanOrEqual=" + SMALLER_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLineIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lineID is less than
        defaultVoucherPaymentsDetailsFiltering("lineID.lessThan=" + UPDATED_LINE_ID, "lineID.lessThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLineIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lineID is greater than
        defaultVoucherPaymentsDetailsFiltering("lineID.greaterThan=" + SMALLER_LINE_ID, "lineID.greaterThan=" + DEFAULT_LINE_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByPaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where paymentAmount equals to
        defaultVoucherPaymentsDetailsFiltering(
            "paymentAmount.equals=" + DEFAULT_PAYMENT_AMOUNT,
            "paymentAmount.equals=" + UPDATED_PAYMENT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByPaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where paymentAmount in
        defaultVoucherPaymentsDetailsFiltering(
            "paymentAmount.in=" + DEFAULT_PAYMENT_AMOUNT + "," + UPDATED_PAYMENT_AMOUNT,
            "paymentAmount.in=" + UPDATED_PAYMENT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByPaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where paymentAmount is not null
        defaultVoucherPaymentsDetailsFiltering("paymentAmount.specified=true", "paymentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByPaymentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where paymentAmount is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "paymentAmount.greaterThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT,
            "paymentAmount.greaterThanOrEqual=" + UPDATED_PAYMENT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByPaymentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where paymentAmount is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "paymentAmount.lessThanOrEqual=" + DEFAULT_PAYMENT_AMOUNT,
            "paymentAmount.lessThanOrEqual=" + SMALLER_PAYMENT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByPaymentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where paymentAmount is less than
        defaultVoucherPaymentsDetailsFiltering(
            "paymentAmount.lessThan=" + UPDATED_PAYMENT_AMOUNT,
            "paymentAmount.lessThan=" + DEFAULT_PAYMENT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByPaymentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where paymentAmount is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "paymentAmount.greaterThan=" + SMALLER_PAYMENT_AMOUNT,
            "paymentAmount.greaterThan=" + DEFAULT_PAYMENT_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTotalVoucherAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where totalVoucherAmount equals to
        defaultVoucherPaymentsDetailsFiltering(
            "totalVoucherAmount.equals=" + DEFAULT_TOTAL_VOUCHER_AMOUNT,
            "totalVoucherAmount.equals=" + UPDATED_TOTAL_VOUCHER_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTotalVoucherAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where totalVoucherAmount in
        defaultVoucherPaymentsDetailsFiltering(
            "totalVoucherAmount.in=" + DEFAULT_TOTAL_VOUCHER_AMOUNT + "," + UPDATED_TOTAL_VOUCHER_AMOUNT,
            "totalVoucherAmount.in=" + UPDATED_TOTAL_VOUCHER_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTotalVoucherAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where totalVoucherAmount is not null
        defaultVoucherPaymentsDetailsFiltering("totalVoucherAmount.specified=true", "totalVoucherAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTotalVoucherAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where totalVoucherAmount is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "totalVoucherAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_VOUCHER_AMOUNT,
            "totalVoucherAmount.greaterThanOrEqual=" + UPDATED_TOTAL_VOUCHER_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTotalVoucherAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where totalVoucherAmount is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "totalVoucherAmount.lessThanOrEqual=" + DEFAULT_TOTAL_VOUCHER_AMOUNT,
            "totalVoucherAmount.lessThanOrEqual=" + SMALLER_TOTAL_VOUCHER_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTotalVoucherAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where totalVoucherAmount is less than
        defaultVoucherPaymentsDetailsFiltering(
            "totalVoucherAmount.lessThan=" + UPDATED_TOTAL_VOUCHER_AMOUNT,
            "totalVoucherAmount.lessThan=" + DEFAULT_TOTAL_VOUCHER_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTotalVoucherAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where totalVoucherAmount is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "totalVoucherAmount.greaterThan=" + SMALLER_TOTAL_VOUCHER_AMOUNT,
            "totalVoucherAmount.greaterThan=" + DEFAULT_TOTAL_VOUCHER_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueAmount equals to
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueAmount.equals=" + DEFAULT_CHECKQUE_AMOUNT,
            "checkqueAmount.equals=" + UPDATED_CHECKQUE_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueAmount in
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueAmount.in=" + DEFAULT_CHECKQUE_AMOUNT + "," + UPDATED_CHECKQUE_AMOUNT,
            "checkqueAmount.in=" + UPDATED_CHECKQUE_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueAmount is not null
        defaultVoucherPaymentsDetailsFiltering("checkqueAmount.specified=true", "checkqueAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueAmount is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueAmount.greaterThanOrEqual=" + DEFAULT_CHECKQUE_AMOUNT,
            "checkqueAmount.greaterThanOrEqual=" + UPDATED_CHECKQUE_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueAmount is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueAmount.lessThanOrEqual=" + DEFAULT_CHECKQUE_AMOUNT,
            "checkqueAmount.lessThanOrEqual=" + SMALLER_CHECKQUE_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueAmount is less than
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueAmount.lessThan=" + UPDATED_CHECKQUE_AMOUNT,
            "checkqueAmount.lessThan=" + DEFAULT_CHECKQUE_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueAmount is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueAmount.greaterThan=" + SMALLER_CHECKQUE_AMOUNT,
            "checkqueAmount.greaterThan=" + DEFAULT_CHECKQUE_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueNo equals to
        defaultVoucherPaymentsDetailsFiltering("checkqueNo.equals=" + DEFAULT_CHECKQUE_NO, "checkqueNo.equals=" + UPDATED_CHECKQUE_NO);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueNo in
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueNo.in=" + DEFAULT_CHECKQUE_NO + "," + UPDATED_CHECKQUE_NO,
            "checkqueNo.in=" + UPDATED_CHECKQUE_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueNo is not null
        defaultVoucherPaymentsDetailsFiltering("checkqueNo.specified=true", "checkqueNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueNoContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueNo contains
        defaultVoucherPaymentsDetailsFiltering("checkqueNo.contains=" + DEFAULT_CHECKQUE_NO, "checkqueNo.contains=" + UPDATED_CHECKQUE_NO);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueNoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueNo does not contain
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueNo.doesNotContain=" + UPDATED_CHECKQUE_NO,
            "checkqueNo.doesNotContain=" + DEFAULT_CHECKQUE_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueDate equals to
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueDate.equals=" + DEFAULT_CHECKQUE_DATE,
            "checkqueDate.equals=" + UPDATED_CHECKQUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueDate in
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueDate.in=" + DEFAULT_CHECKQUE_DATE + "," + UPDATED_CHECKQUE_DATE,
            "checkqueDate.in=" + UPDATED_CHECKQUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueDate is not null
        defaultVoucherPaymentsDetailsFiltering("checkqueDate.specified=true", "checkqueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueExpireDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueExpireDate equals to
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueExpireDate.equals=" + DEFAULT_CHECKQUE_EXPIRE_DATE,
            "checkqueExpireDate.equals=" + UPDATED_CHECKQUE_EXPIRE_DATE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueExpireDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueExpireDate in
        defaultVoucherPaymentsDetailsFiltering(
            "checkqueExpireDate.in=" + DEFAULT_CHECKQUE_EXPIRE_DATE + "," + UPDATED_CHECKQUE_EXPIRE_DATE,
            "checkqueExpireDate.in=" + UPDATED_CHECKQUE_EXPIRE_DATE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCheckqueExpireDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where checkqueExpireDate is not null
        defaultVoucherPaymentsDetailsFiltering("checkqueExpireDate.specified=true", "checkqueExpireDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankName equals to
        defaultVoucherPaymentsDetailsFiltering("bankName.equals=" + DEFAULT_BANK_NAME, "bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankName in
        defaultVoucherPaymentsDetailsFiltering(
            "bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME,
            "bankName.in=" + UPDATED_BANK_NAME
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankName is not null
        defaultVoucherPaymentsDetailsFiltering("bankName.specified=true", "bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankNameContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankName contains
        defaultVoucherPaymentsDetailsFiltering("bankName.contains=" + DEFAULT_BANK_NAME, "bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankName does not contain
        defaultVoucherPaymentsDetailsFiltering(
            "bankName.doesNotContain=" + UPDATED_BANK_NAME,
            "bankName.doesNotContain=" + DEFAULT_BANK_NAME
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankID equals to
        defaultVoucherPaymentsDetailsFiltering("bankID.equals=" + DEFAULT_BANK_ID, "bankID.equals=" + UPDATED_BANK_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankID in
        defaultVoucherPaymentsDetailsFiltering("bankID.in=" + DEFAULT_BANK_ID + "," + UPDATED_BANK_ID, "bankID.in=" + UPDATED_BANK_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankID is not null
        defaultVoucherPaymentsDetailsFiltering("bankID.specified=true", "bankID.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankID is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "bankID.greaterThanOrEqual=" + DEFAULT_BANK_ID,
            "bankID.greaterThanOrEqual=" + UPDATED_BANK_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankID is less than or equal to
        defaultVoucherPaymentsDetailsFiltering("bankID.lessThanOrEqual=" + DEFAULT_BANK_ID, "bankID.lessThanOrEqual=" + SMALLER_BANK_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankID is less than
        defaultVoucherPaymentsDetailsFiltering("bankID.lessThan=" + UPDATED_BANK_ID, "bankID.lessThan=" + DEFAULT_BANK_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByBankIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where bankID is greater than
        defaultVoucherPaymentsDetailsFiltering("bankID.greaterThan=" + SMALLER_BANK_ID, "bankID.greaterThan=" + DEFAULT_BANK_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardNo equals to
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardNo.equals=" + DEFAULT_CREDIT_CARD_NO,
            "creditCardNo.equals=" + UPDATED_CREDIT_CARD_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardNo in
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardNo.in=" + DEFAULT_CREDIT_CARD_NO + "," + UPDATED_CREDIT_CARD_NO,
            "creditCardNo.in=" + UPDATED_CREDIT_CARD_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardNo is not null
        defaultVoucherPaymentsDetailsFiltering("creditCardNo.specified=true", "creditCardNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardNoContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardNo contains
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardNo.contains=" + DEFAULT_CREDIT_CARD_NO,
            "creditCardNo.contains=" + UPDATED_CREDIT_CARD_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardNoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardNo does not contain
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardNo.doesNotContain=" + UPDATED_CREDIT_CARD_NO,
            "creditCardNo.doesNotContain=" + DEFAULT_CREDIT_CARD_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardAmount equals to
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardAmount.equals=" + DEFAULT_CREDIT_CARD_AMOUNT,
            "creditCardAmount.equals=" + UPDATED_CREDIT_CARD_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardAmount in
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardAmount.in=" + DEFAULT_CREDIT_CARD_AMOUNT + "," + UPDATED_CREDIT_CARD_AMOUNT,
            "creditCardAmount.in=" + UPDATED_CREDIT_CARD_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardAmount is not null
        defaultVoucherPaymentsDetailsFiltering("creditCardAmount.specified=true", "creditCardAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardAmount is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardAmount.greaterThanOrEqual=" + DEFAULT_CREDIT_CARD_AMOUNT,
            "creditCardAmount.greaterThanOrEqual=" + UPDATED_CREDIT_CARD_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardAmount is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardAmount.lessThanOrEqual=" + DEFAULT_CREDIT_CARD_AMOUNT,
            "creditCardAmount.lessThanOrEqual=" + SMALLER_CREDIT_CARD_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardAmount is less than
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardAmount.lessThan=" + UPDATED_CREDIT_CARD_AMOUNT,
            "creditCardAmount.lessThan=" + DEFAULT_CREDIT_CARD_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCreditCardAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where creditCardAmount is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "creditCardAmount.greaterThan=" + SMALLER_CREDIT_CARD_AMOUNT,
            "creditCardAmount.greaterThan=" + DEFAULT_CREDIT_CARD_AMOUNT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where reference equals to
        defaultVoucherPaymentsDetailsFiltering("reference.equals=" + DEFAULT_REFERENCE, "reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where reference in
        defaultVoucherPaymentsDetailsFiltering(
            "reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE,
            "reference.in=" + UPDATED_REFERENCE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where reference is not null
        defaultVoucherPaymentsDetailsFiltering("reference.specified=true", "reference.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByReferenceContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where reference contains
        defaultVoucherPaymentsDetailsFiltering("reference.contains=" + DEFAULT_REFERENCE, "reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where reference does not contain
        defaultVoucherPaymentsDetailsFiltering(
            "reference.doesNotContain=" + UPDATED_REFERENCE,
            "reference.doesNotContain=" + DEFAULT_REFERENCE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByOtherDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where otherDetails equals to
        defaultVoucherPaymentsDetailsFiltering(
            "otherDetails.equals=" + DEFAULT_OTHER_DETAILS,
            "otherDetails.equals=" + UPDATED_OTHER_DETAILS
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByOtherDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where otherDetails in
        defaultVoucherPaymentsDetailsFiltering(
            "otherDetails.in=" + DEFAULT_OTHER_DETAILS + "," + UPDATED_OTHER_DETAILS,
            "otherDetails.in=" + UPDATED_OTHER_DETAILS
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByOtherDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where otherDetails is not null
        defaultVoucherPaymentsDetailsFiltering("otherDetails.specified=true", "otherDetails.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByOtherDetailsContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where otherDetails contains
        defaultVoucherPaymentsDetailsFiltering(
            "otherDetails.contains=" + DEFAULT_OTHER_DETAILS,
            "otherDetails.contains=" + UPDATED_OTHER_DETAILS
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByOtherDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where otherDetails does not contain
        defaultVoucherPaymentsDetailsFiltering(
            "otherDetails.doesNotContain=" + UPDATED_OTHER_DETAILS,
            "otherDetails.doesNotContain=" + DEFAULT_OTHER_DETAILS
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lmu equals to
        defaultVoucherPaymentsDetailsFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lmu in
        defaultVoucherPaymentsDetailsFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lmu is not null
        defaultVoucherPaymentsDetailsFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLmuContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lmu contains
        defaultVoucherPaymentsDetailsFiltering("lmu.contains=" + DEFAULT_LMU, "lmu.contains=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLmuNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lmu does not contain
        defaultVoucherPaymentsDetailsFiltering("lmu.doesNotContain=" + UPDATED_LMU, "lmu.doesNotContain=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lmd equals to
        defaultVoucherPaymentsDetailsFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lmd in
        defaultVoucherPaymentsDetailsFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where lmd is not null
        defaultVoucherPaymentsDetailsFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermIDIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termID equals to
        defaultVoucherPaymentsDetailsFiltering("termID.equals=" + DEFAULT_TERM_ID, "termID.equals=" + UPDATED_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermIDIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termID in
        defaultVoucherPaymentsDetailsFiltering("termID.in=" + DEFAULT_TERM_ID + "," + UPDATED_TERM_ID, "termID.in=" + UPDATED_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termID is not null
        defaultVoucherPaymentsDetailsFiltering("termID.specified=true", "termID.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermIDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termID is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "termID.greaterThanOrEqual=" + DEFAULT_TERM_ID,
            "termID.greaterThanOrEqual=" + UPDATED_TERM_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermIDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termID is less than or equal to
        defaultVoucherPaymentsDetailsFiltering("termID.lessThanOrEqual=" + DEFAULT_TERM_ID, "termID.lessThanOrEqual=" + SMALLER_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermIDIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termID is less than
        defaultVoucherPaymentsDetailsFiltering("termID.lessThan=" + UPDATED_TERM_ID, "termID.lessThan=" + DEFAULT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermIDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termID is greater than
        defaultVoucherPaymentsDetailsFiltering("termID.greaterThan=" + SMALLER_TERM_ID, "termID.greaterThan=" + DEFAULT_TERM_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termName equals to
        defaultVoucherPaymentsDetailsFiltering("termName.equals=" + DEFAULT_TERM_NAME, "termName.equals=" + UPDATED_TERM_NAME);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termName in
        defaultVoucherPaymentsDetailsFiltering(
            "termName.in=" + DEFAULT_TERM_NAME + "," + UPDATED_TERM_NAME,
            "termName.in=" + UPDATED_TERM_NAME
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termName is not null
        defaultVoucherPaymentsDetailsFiltering("termName.specified=true", "termName.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermNameContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termName contains
        defaultVoucherPaymentsDetailsFiltering("termName.contains=" + DEFAULT_TERM_NAME, "termName.contains=" + UPDATED_TERM_NAME);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByTermNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where termName does not contain
        defaultVoucherPaymentsDetailsFiltering(
            "termName.doesNotContain=" + UPDATED_TERM_NAME,
            "termName.doesNotContain=" + DEFAULT_TERM_NAME
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNo equals to
        defaultVoucherPaymentsDetailsFiltering("accountNo.equals=" + DEFAULT_ACCOUNT_NO, "accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNo in
        defaultVoucherPaymentsDetailsFiltering(
            "accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO,
            "accountNo.in=" + UPDATED_ACCOUNT_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNo is not null
        defaultVoucherPaymentsDetailsFiltering("accountNo.specified=true", "accountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNo is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "accountNo.greaterThanOrEqual=" + DEFAULT_ACCOUNT_NO,
            "accountNo.greaterThanOrEqual=" + UPDATED_ACCOUNT_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNo is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "accountNo.lessThanOrEqual=" + DEFAULT_ACCOUNT_NO,
            "accountNo.lessThanOrEqual=" + SMALLER_ACCOUNT_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNo is less than
        defaultVoucherPaymentsDetailsFiltering("accountNo.lessThan=" + UPDATED_ACCOUNT_NO, "accountNo.lessThan=" + DEFAULT_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNo is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "accountNo.greaterThan=" + SMALLER_ACCOUNT_NO,
            "accountNo.greaterThan=" + DEFAULT_ACCOUNT_NO
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNumber equals to
        defaultVoucherPaymentsDetailsFiltering(
            "accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER,
            "accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNumber in
        defaultVoucherPaymentsDetailsFiltering(
            "accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER,
            "accountNumber.in=" + UPDATED_ACCOUNT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNumber is not null
        defaultVoucherPaymentsDetailsFiltering("accountNumber.specified=true", "accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNumber is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "accountNumber.greaterThanOrEqual=" + DEFAULT_ACCOUNT_NUMBER,
            "accountNumber.greaterThanOrEqual=" + UPDATED_ACCOUNT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNumber is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "accountNumber.lessThanOrEqual=" + DEFAULT_ACCOUNT_NUMBER,
            "accountNumber.lessThanOrEqual=" + SMALLER_ACCOUNT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNumber is less than
        defaultVoucherPaymentsDetailsFiltering(
            "accountNumber.lessThan=" + UPDATED_ACCOUNT_NUMBER,
            "accountNumber.lessThan=" + DEFAULT_ACCOUNT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountNumber is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "accountNumber.greaterThan=" + SMALLER_ACCOUNT_NUMBER,
            "accountNumber.greaterThan=" + DEFAULT_ACCOUNT_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountId equals to
        defaultVoucherPaymentsDetailsFiltering("accountId.equals=" + DEFAULT_ACCOUNT_ID, "accountId.equals=" + UPDATED_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountId in
        defaultVoucherPaymentsDetailsFiltering(
            "accountId.in=" + DEFAULT_ACCOUNT_ID + "," + UPDATED_ACCOUNT_ID,
            "accountId.in=" + UPDATED_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountId is not null
        defaultVoucherPaymentsDetailsFiltering("accountId.specified=true", "accountId.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountId is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "accountId.greaterThanOrEqual=" + DEFAULT_ACCOUNT_ID,
            "accountId.greaterThanOrEqual=" + UPDATED_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountId is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "accountId.lessThanOrEqual=" + DEFAULT_ACCOUNT_ID,
            "accountId.lessThanOrEqual=" + SMALLER_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountId is less than
        defaultVoucherPaymentsDetailsFiltering("accountId.lessThan=" + UPDATED_ACCOUNT_ID, "accountId.lessThan=" + DEFAULT_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountId is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "accountId.greaterThan=" + SMALLER_ACCOUNT_ID,
            "accountId.greaterThan=" + DEFAULT_ACCOUNT_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountCode equals to
        defaultVoucherPaymentsDetailsFiltering("accountCode.equals=" + DEFAULT_ACCOUNT_CODE, "accountCode.equals=" + UPDATED_ACCOUNT_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountCode in
        defaultVoucherPaymentsDetailsFiltering(
            "accountCode.in=" + DEFAULT_ACCOUNT_CODE + "," + UPDATED_ACCOUNT_CODE,
            "accountCode.in=" + UPDATED_ACCOUNT_CODE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountCode is not null
        defaultVoucherPaymentsDetailsFiltering("accountCode.specified=true", "accountCode.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountCode contains
        defaultVoucherPaymentsDetailsFiltering(
            "accountCode.contains=" + DEFAULT_ACCOUNT_CODE,
            "accountCode.contains=" + UPDATED_ACCOUNT_CODE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByAccountCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where accountCode does not contain
        defaultVoucherPaymentsDetailsFiltering(
            "accountCode.doesNotContain=" + UPDATED_ACCOUNT_CODE,
            "accountCode.doesNotContain=" + DEFAULT_ACCOUNT_CODE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByChequeStatusIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where chequeStatusId equals to
        defaultVoucherPaymentsDetailsFiltering(
            "chequeStatusId.equals=" + DEFAULT_CHEQUE_STATUS_ID,
            "chequeStatusId.equals=" + UPDATED_CHEQUE_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByChequeStatusIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where chequeStatusId in
        defaultVoucherPaymentsDetailsFiltering(
            "chequeStatusId.in=" + DEFAULT_CHEQUE_STATUS_ID + "," + UPDATED_CHEQUE_STATUS_ID,
            "chequeStatusId.in=" + UPDATED_CHEQUE_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByChequeStatusIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where chequeStatusId is not null
        defaultVoucherPaymentsDetailsFiltering("chequeStatusId.specified=true", "chequeStatusId.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByChequeStatusIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where chequeStatusId is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "chequeStatusId.greaterThanOrEqual=" + DEFAULT_CHEQUE_STATUS_ID,
            "chequeStatusId.greaterThanOrEqual=" + UPDATED_CHEQUE_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByChequeStatusIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where chequeStatusId is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "chequeStatusId.lessThanOrEqual=" + DEFAULT_CHEQUE_STATUS_ID,
            "chequeStatusId.lessThanOrEqual=" + SMALLER_CHEQUE_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByChequeStatusIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where chequeStatusId is less than
        defaultVoucherPaymentsDetailsFiltering(
            "chequeStatusId.lessThan=" + UPDATED_CHEQUE_STATUS_ID,
            "chequeStatusId.lessThan=" + DEFAULT_CHEQUE_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByChequeStatusIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where chequeStatusId is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "chequeStatusId.greaterThan=" + SMALLER_CHEQUE_STATUS_ID,
            "chequeStatusId.greaterThan=" + DEFAULT_CHEQUE_STATUS_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByIsDepositIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where isDeposit equals to
        defaultVoucherPaymentsDetailsFiltering("isDeposit.equals=" + DEFAULT_IS_DEPOSIT, "isDeposit.equals=" + UPDATED_IS_DEPOSIT);
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByIsDepositIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where isDeposit in
        defaultVoucherPaymentsDetailsFiltering(
            "isDeposit.in=" + DEFAULT_IS_DEPOSIT + "," + UPDATED_IS_DEPOSIT,
            "isDeposit.in=" + UPDATED_IS_DEPOSIT
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByIsDepositIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where isDeposit is not null
        defaultVoucherPaymentsDetailsFiltering("isDeposit.specified=true", "isDeposit.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByDepositedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where depositedDate equals to
        defaultVoucherPaymentsDetailsFiltering(
            "depositedDate.equals=" + DEFAULT_DEPOSITED_DATE,
            "depositedDate.equals=" + UPDATED_DEPOSITED_DATE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByDepositedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where depositedDate in
        defaultVoucherPaymentsDetailsFiltering(
            "depositedDate.in=" + DEFAULT_DEPOSITED_DATE + "," + UPDATED_DEPOSITED_DATE,
            "depositedDate.in=" + UPDATED_DEPOSITED_DATE
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByDepositedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where depositedDate is not null
        defaultVoucherPaymentsDetailsFiltering("depositedDate.specified=true", "depositedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCompanyBankIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where companyBankId equals to
        defaultVoucherPaymentsDetailsFiltering(
            "companyBankId.equals=" + DEFAULT_COMPANY_BANK_ID,
            "companyBankId.equals=" + UPDATED_COMPANY_BANK_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCompanyBankIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where companyBankId in
        defaultVoucherPaymentsDetailsFiltering(
            "companyBankId.in=" + DEFAULT_COMPANY_BANK_ID + "," + UPDATED_COMPANY_BANK_ID,
            "companyBankId.in=" + UPDATED_COMPANY_BANK_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCompanyBankIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where companyBankId is not null
        defaultVoucherPaymentsDetailsFiltering("companyBankId.specified=true", "companyBankId.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCompanyBankIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where companyBankId is greater than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "companyBankId.greaterThanOrEqual=" + DEFAULT_COMPANY_BANK_ID,
            "companyBankId.greaterThanOrEqual=" + UPDATED_COMPANY_BANK_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCompanyBankIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where companyBankId is less than or equal to
        defaultVoucherPaymentsDetailsFiltering(
            "companyBankId.lessThanOrEqual=" + DEFAULT_COMPANY_BANK_ID,
            "companyBankId.lessThanOrEqual=" + SMALLER_COMPANY_BANK_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCompanyBankIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where companyBankId is less than
        defaultVoucherPaymentsDetailsFiltering(
            "companyBankId.lessThan=" + UPDATED_COMPANY_BANK_ID,
            "companyBankId.lessThan=" + DEFAULT_COMPANY_BANK_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByCompanyBankIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where companyBankId is greater than
        defaultVoucherPaymentsDetailsFiltering(
            "companyBankId.greaterThan=" + SMALLER_COMPANY_BANK_ID,
            "companyBankId.greaterThan=" + DEFAULT_COMPANY_BANK_ID
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByIsBankReconciliationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where isBankReconciliation equals to
        defaultVoucherPaymentsDetailsFiltering(
            "isBankReconciliation.equals=" + DEFAULT_IS_BANK_RECONCILIATION,
            "isBankReconciliation.equals=" + UPDATED_IS_BANK_RECONCILIATION
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByIsBankReconciliationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where isBankReconciliation in
        defaultVoucherPaymentsDetailsFiltering(
            "isBankReconciliation.in=" + DEFAULT_IS_BANK_RECONCILIATION + "," + UPDATED_IS_BANK_RECONCILIATION,
            "isBankReconciliation.in=" + UPDATED_IS_BANK_RECONCILIATION
        );
    }

    @Test
    @Transactional
    void getAllVoucherPaymentsDetailsByIsBankReconciliationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        // Get all the voucherPaymentsDetailsList where isBankReconciliation is not null
        defaultVoucherPaymentsDetailsFiltering("isBankReconciliation.specified=true", "isBankReconciliation.specified=false");
    }

    private void defaultVoucherPaymentsDetailsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultVoucherPaymentsDetailsShouldBeFound(shouldBeFound);
        defaultVoucherPaymentsDetailsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVoucherPaymentsDetailsShouldBeFound(String filter) throws Exception {
        restVoucherPaymentsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherPaymentsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineID").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalVoucherAmount").value(hasItem(DEFAULT_TOTAL_VOUCHER_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].checkqueAmount").value(hasItem(DEFAULT_CHECKQUE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].checkqueNo").value(hasItem(DEFAULT_CHECKQUE_NO)))
            .andExpect(jsonPath("$.[*].checkqueDate").value(hasItem(DEFAULT_CHECKQUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].checkqueExpireDate").value(hasItem(DEFAULT_CHECKQUE_EXPIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].bankID").value(hasItem(DEFAULT_BANK_ID)))
            .andExpect(jsonPath("$.[*].creditCardNo").value(hasItem(DEFAULT_CREDIT_CARD_NO)))
            .andExpect(jsonPath("$.[*].creditCardAmount").value(hasItem(DEFAULT_CREDIT_CARD_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].otherDetails").value(hasItem(DEFAULT_OTHER_DETAILS)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].termID").value(hasItem(DEFAULT_TERM_ID)))
            .andExpect(jsonPath("$.[*].termName").value(hasItem(DEFAULT_TERM_NAME)))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].accountCode").value(hasItem(DEFAULT_ACCOUNT_CODE)))
            .andExpect(jsonPath("$.[*].chequeStatusId").value(hasItem(DEFAULT_CHEQUE_STATUS_ID)))
            .andExpect(jsonPath("$.[*].isDeposit").value(hasItem(DEFAULT_IS_DEPOSIT.booleanValue())))
            .andExpect(jsonPath("$.[*].depositedDate").value(hasItem(DEFAULT_DEPOSITED_DATE.toString())))
            .andExpect(jsonPath("$.[*].companyBankId").value(hasItem(DEFAULT_COMPANY_BANK_ID)))
            .andExpect(jsonPath("$.[*].isBankReconciliation").value(hasItem(DEFAULT_IS_BANK_RECONCILIATION.booleanValue())));

        // Check, that the count call also returns 1
        restVoucherPaymentsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVoucherPaymentsDetailsShouldNotBeFound(String filter) throws Exception {
        restVoucherPaymentsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoucherPaymentsDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVoucherPaymentsDetails() throws Exception {
        // Get the voucherPaymentsDetails
        restVoucherPaymentsDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoucherPaymentsDetails() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucherPaymentsDetails
        VoucherPaymentsDetails updatedVoucherPaymentsDetails = voucherPaymentsDetailsRepository
            .findById(voucherPaymentsDetails.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedVoucherPaymentsDetails are not directly saved in db
        em.detach(updatedVoucherPaymentsDetails);
        updatedVoucherPaymentsDetails
            .lineID(UPDATED_LINE_ID)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .totalVoucherAmount(UPDATED_TOTAL_VOUCHER_AMOUNT)
            .checkqueAmount(UPDATED_CHECKQUE_AMOUNT)
            .checkqueNo(UPDATED_CHECKQUE_NO)
            .checkqueDate(UPDATED_CHECKQUE_DATE)
            .checkqueExpireDate(UPDATED_CHECKQUE_EXPIRE_DATE)
            .bankName(UPDATED_BANK_NAME)
            .bankID(UPDATED_BANK_ID)
            .creditCardNo(UPDATED_CREDIT_CARD_NO)
            .creditCardAmount(UPDATED_CREDIT_CARD_AMOUNT)
            .reference(UPDATED_REFERENCE)
            .otherDetails(UPDATED_OTHER_DETAILS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termID(UPDATED_TERM_ID)
            .termName(UPDATED_TERM_NAME)
            .accountNo(UPDATED_ACCOUNT_NO)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .chequeStatusId(UPDATED_CHEQUE_STATUS_ID)
            .isDeposit(UPDATED_IS_DEPOSIT)
            .depositedDate(UPDATED_DEPOSITED_DATE)
            .companyBankId(UPDATED_COMPANY_BANK_ID)
            .isBankReconciliation(UPDATED_IS_BANK_RECONCILIATION);

        restVoucherPaymentsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVoucherPaymentsDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedVoucherPaymentsDetails))
            )
            .andExpect(status().isOk());

        // Validate the VoucherPaymentsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVoucherPaymentsDetailsToMatchAllProperties(updatedVoucherPaymentsDetails);
    }

    @Test
    @Transactional
    void putNonExistingVoucherPaymentsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherPaymentsDetails.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherPaymentsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherPaymentsDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voucherPaymentsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherPaymentsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoucherPaymentsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherPaymentsDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherPaymentsDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voucherPaymentsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherPaymentsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoucherPaymentsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherPaymentsDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherPaymentsDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voucherPaymentsDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherPaymentsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoucherPaymentsDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucherPaymentsDetails using partial update
        VoucherPaymentsDetails partialUpdatedVoucherPaymentsDetails = new VoucherPaymentsDetails();
        partialUpdatedVoucherPaymentsDetails.setId(voucherPaymentsDetails.getId());

        partialUpdatedVoucherPaymentsDetails
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .totalVoucherAmount(UPDATED_TOTAL_VOUCHER_AMOUNT)
            .checkqueNo(UPDATED_CHECKQUE_NO)
            .bankName(UPDATED_BANK_NAME)
            .bankID(UPDATED_BANK_ID)
            .otherDetails(UPDATED_OTHER_DETAILS)
            .termID(UPDATED_TERM_ID)
            .termName(UPDATED_TERM_NAME)
            .accountNo(UPDATED_ACCOUNT_NO)
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .companyBankId(UPDATED_COMPANY_BANK_ID);

        restVoucherPaymentsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherPaymentsDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoucherPaymentsDetails))
            )
            .andExpect(status().isOk());

        // Validate the VoucherPaymentsDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoucherPaymentsDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVoucherPaymentsDetails, voucherPaymentsDetails),
            getPersistedVoucherPaymentsDetails(voucherPaymentsDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdateVoucherPaymentsDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voucherPaymentsDetails using partial update
        VoucherPaymentsDetails partialUpdatedVoucherPaymentsDetails = new VoucherPaymentsDetails();
        partialUpdatedVoucherPaymentsDetails.setId(voucherPaymentsDetails.getId());

        partialUpdatedVoucherPaymentsDetails
            .lineID(UPDATED_LINE_ID)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .totalVoucherAmount(UPDATED_TOTAL_VOUCHER_AMOUNT)
            .checkqueAmount(UPDATED_CHECKQUE_AMOUNT)
            .checkqueNo(UPDATED_CHECKQUE_NO)
            .checkqueDate(UPDATED_CHECKQUE_DATE)
            .checkqueExpireDate(UPDATED_CHECKQUE_EXPIRE_DATE)
            .bankName(UPDATED_BANK_NAME)
            .bankID(UPDATED_BANK_ID)
            .creditCardNo(UPDATED_CREDIT_CARD_NO)
            .creditCardAmount(UPDATED_CREDIT_CARD_AMOUNT)
            .reference(UPDATED_REFERENCE)
            .otherDetails(UPDATED_OTHER_DETAILS)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .termID(UPDATED_TERM_ID)
            .termName(UPDATED_TERM_NAME)
            .accountNo(UPDATED_ACCOUNT_NO)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountId(UPDATED_ACCOUNT_ID)
            .accountCode(UPDATED_ACCOUNT_CODE)
            .chequeStatusId(UPDATED_CHEQUE_STATUS_ID)
            .isDeposit(UPDATED_IS_DEPOSIT)
            .depositedDate(UPDATED_DEPOSITED_DATE)
            .companyBankId(UPDATED_COMPANY_BANK_ID)
            .isBankReconciliation(UPDATED_IS_BANK_RECONCILIATION);

        restVoucherPaymentsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherPaymentsDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoucherPaymentsDetails))
            )
            .andExpect(status().isOk());

        // Validate the VoucherPaymentsDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoucherPaymentsDetailsUpdatableFieldsEquals(
            partialUpdatedVoucherPaymentsDetails,
            getPersistedVoucherPaymentsDetails(partialUpdatedVoucherPaymentsDetails)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVoucherPaymentsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherPaymentsDetails.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherPaymentsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voucherPaymentsDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voucherPaymentsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherPaymentsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoucherPaymentsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherPaymentsDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherPaymentsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voucherPaymentsDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherPaymentsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoucherPaymentsDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voucherPaymentsDetails.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherPaymentsDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(voucherPaymentsDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherPaymentsDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoucherPaymentsDetails() throws Exception {
        // Initialize the database
        insertedVoucherPaymentsDetails = voucherPaymentsDetailsRepository.saveAndFlush(voucherPaymentsDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the voucherPaymentsDetails
        restVoucherPaymentsDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, voucherPaymentsDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return voucherPaymentsDetailsRepository.count();
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

    protected VoucherPaymentsDetails getPersistedVoucherPaymentsDetails(VoucherPaymentsDetails voucherPaymentsDetails) {
        return voucherPaymentsDetailsRepository.findById(voucherPaymentsDetails.getId()).orElseThrow();
    }

    protected void assertPersistedVoucherPaymentsDetailsToMatchAllProperties(VoucherPaymentsDetails expectedVoucherPaymentsDetails) {
        assertVoucherPaymentsDetailsAllPropertiesEquals(
            expectedVoucherPaymentsDetails,
            getPersistedVoucherPaymentsDetails(expectedVoucherPaymentsDetails)
        );
    }

    protected void assertPersistedVoucherPaymentsDetailsToMatchUpdatableProperties(VoucherPaymentsDetails expectedVoucherPaymentsDetails) {
        assertVoucherPaymentsDetailsAllUpdatablePropertiesEquals(
            expectedVoucherPaymentsDetails,
            getPersistedVoucherPaymentsDetails(expectedVoucherPaymentsDetails)
        );
    }
}
