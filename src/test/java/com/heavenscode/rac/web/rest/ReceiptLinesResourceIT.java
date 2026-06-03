package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.ReceiptLinesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.ReceiptLines;
import com.heavenscode.rac.repository.ReceiptLinesRepository;
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
 * Integration tests for the {@link ReceiptLinesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReceiptLinesResourceIT {

    private static final Long DEFAULT_LINEID = 1L;
    private static final Long UPDATED_LINEID = 2L;
    private static final Long SMALLER_LINEID = 1L - 1L;

    private static final String DEFAULT_INVOICECODE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICECODE = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICETYPE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICETYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_ORIGINALAMOUNT = 1D;
    private static final Double UPDATED_ORIGINALAMOUNT = 2D;
    private static final Double SMALLER_ORIGINALAMOUNT = 1D - 1D;

    private static final Double DEFAULT_AMOUNTOWING = 1D;
    private static final Double UPDATED_AMOUNTOWING = 2D;
    private static final Double SMALLER_AMOUNTOWING = 1D - 1D;

    private static final Double DEFAULT_DISCOUNTAVAILABLE = 1D;
    private static final Double UPDATED_DISCOUNTAVAILABLE = 2D;
    private static final Double SMALLER_DISCOUNTAVAILABLE = 1D - 1D;

    private static final Double DEFAULT_DISCOUNTTAKEN = 1D;
    private static final Double UPDATED_DISCOUNTTAKEN = 2D;
    private static final Double SMALLER_DISCOUNTTAKEN = 1D - 1D;

    private static final Double DEFAULT_AMOUNTRECEIVED = 1D;
    private static final Double UPDATED_AMOUNTRECEIVED = 2D;
    private static final Double SMALLER_AMOUNTRECEIVED = 1D - 1D;

    private static final Long DEFAULT_LMU = 1L;
    private static final Long UPDATED_LMU = 2L;
    private static final Long SMALLER_LMU = 1L - 1L;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_ACCOUNTID = 1L;
    private static final Long UPDATED_ACCOUNTID = 2L;
    private static final Long SMALLER_ACCOUNTID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/receipt-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReceiptLinesRepository receiptLinesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceiptLinesMockMvc;

    private ReceiptLines receiptLines;

    private ReceiptLines insertedReceiptLines;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceiptLines createEntity() {
        return new ReceiptLines()
            .lineid(DEFAULT_LINEID)
            .invoicecode(DEFAULT_INVOICECODE)
            .invoicetype(DEFAULT_INVOICETYPE)
            .originalamount(DEFAULT_ORIGINALAMOUNT)
            .amountowing(DEFAULT_AMOUNTOWING)
            .discountavailable(DEFAULT_DISCOUNTAVAILABLE)
            .discounttaken(DEFAULT_DISCOUNTTAKEN)
            .amountreceived(DEFAULT_AMOUNTRECEIVED)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .accountid(DEFAULT_ACCOUNTID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceiptLines createUpdatedEntity() {
        return new ReceiptLines()
            .lineid(UPDATED_LINEID)
            .invoicecode(UPDATED_INVOICECODE)
            .invoicetype(UPDATED_INVOICETYPE)
            .originalamount(UPDATED_ORIGINALAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .discountavailable(UPDATED_DISCOUNTAVAILABLE)
            .discounttaken(UPDATED_DISCOUNTTAKEN)
            .amountreceived(UPDATED_AMOUNTRECEIVED)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .accountid(UPDATED_ACCOUNTID);
    }

    @BeforeEach
    public void initTest() {
        receiptLines = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedReceiptLines != null) {
            receiptLinesRepository.delete(insertedReceiptLines);
            insertedReceiptLines = null;
        }
    }

    @Test
    @Transactional
    void createReceiptLines() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ReceiptLines
        var returnedReceiptLines = om.readValue(
            restReceiptLinesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(receiptLines)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ReceiptLines.class
        );

        // Validate the ReceiptLines in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertReceiptLinesUpdatableFieldsEquals(returnedReceiptLines, getPersistedReceiptLines(returnedReceiptLines));

        insertedReceiptLines = returnedReceiptLines;
    }

    @Test
    @Transactional
    void createReceiptLinesWithExistingId() throws Exception {
        // Create the ReceiptLines with an existing ID
        receiptLines.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptLinesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(receiptLines)))
            .andExpect(status().isBadRequest());

        // Validate the ReceiptLines in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReceiptLines() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList
        restReceiptLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID.intValue())))
            .andExpect(jsonPath("$.[*].invoicecode").value(hasItem(DEFAULT_INVOICECODE)))
            .andExpect(jsonPath("$.[*].invoicetype").value(hasItem(DEFAULT_INVOICETYPE)))
            .andExpect(jsonPath("$.[*].originalamount").value(hasItem(DEFAULT_ORIGINALAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountowing").value(hasItem(DEFAULT_AMOUNTOWING.doubleValue())))
            .andExpect(jsonPath("$.[*].discountavailable").value(hasItem(DEFAULT_DISCOUNTAVAILABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].discounttaken").value(hasItem(DEFAULT_DISCOUNTTAKEN.doubleValue())))
            .andExpect(jsonPath("$.[*].amountreceived").value(hasItem(DEFAULT_AMOUNTRECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU.intValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].accountid").value(hasItem(DEFAULT_ACCOUNTID.intValue())));
    }

    @Test
    @Transactional
    void getReceiptLines() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get the receiptLines
        restReceiptLinesMockMvc
            .perform(get(ENTITY_API_URL_ID, receiptLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receiptLines.getId().intValue()))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID.intValue()))
            .andExpect(jsonPath("$.invoicecode").value(DEFAULT_INVOICECODE))
            .andExpect(jsonPath("$.invoicetype").value(DEFAULT_INVOICETYPE))
            .andExpect(jsonPath("$.originalamount").value(DEFAULT_ORIGINALAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountowing").value(DEFAULT_AMOUNTOWING.doubleValue()))
            .andExpect(jsonPath("$.discountavailable").value(DEFAULT_DISCOUNTAVAILABLE.doubleValue()))
            .andExpect(jsonPath("$.discounttaken").value(DEFAULT_DISCOUNTTAKEN.doubleValue()))
            .andExpect(jsonPath("$.amountreceived").value(DEFAULT_AMOUNTRECEIVED.doubleValue()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU.intValue()))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.accountid").value(DEFAULT_ACCOUNTID.intValue()));
    }

    @Test
    @Transactional
    void getReceiptLinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        Long id = receiptLines.getId();

        defaultReceiptLinesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultReceiptLinesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultReceiptLinesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lineid equals to
        defaultReceiptLinesFiltering("lineid.equals=" + DEFAULT_LINEID, "lineid.equals=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lineid in
        defaultReceiptLinesFiltering("lineid.in=" + DEFAULT_LINEID + "," + UPDATED_LINEID, "lineid.in=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lineid is not null
        defaultReceiptLinesFiltering("lineid.specified=true", "lineid.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lineid is greater than or equal to
        defaultReceiptLinesFiltering("lineid.greaterThanOrEqual=" + DEFAULT_LINEID, "lineid.greaterThanOrEqual=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lineid is less than or equal to
        defaultReceiptLinesFiltering("lineid.lessThanOrEqual=" + DEFAULT_LINEID, "lineid.lessThanOrEqual=" + SMALLER_LINEID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lineid is less than
        defaultReceiptLinesFiltering("lineid.lessThan=" + UPDATED_LINEID, "lineid.lessThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lineid is greater than
        defaultReceiptLinesFiltering("lineid.greaterThan=" + SMALLER_LINEID, "lineid.greaterThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicecodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicecode equals to
        defaultReceiptLinesFiltering("invoicecode.equals=" + DEFAULT_INVOICECODE, "invoicecode.equals=" + UPDATED_INVOICECODE);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicecodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicecode in
        defaultReceiptLinesFiltering(
            "invoicecode.in=" + DEFAULT_INVOICECODE + "," + UPDATED_INVOICECODE,
            "invoicecode.in=" + UPDATED_INVOICECODE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicecodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicecode is not null
        defaultReceiptLinesFiltering("invoicecode.specified=true", "invoicecode.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicecodeContainsSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicecode contains
        defaultReceiptLinesFiltering("invoicecode.contains=" + DEFAULT_INVOICECODE, "invoicecode.contains=" + UPDATED_INVOICECODE);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicecodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicecode does not contain
        defaultReceiptLinesFiltering(
            "invoicecode.doesNotContain=" + UPDATED_INVOICECODE,
            "invoicecode.doesNotContain=" + DEFAULT_INVOICECODE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicetypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicetype equals to
        defaultReceiptLinesFiltering("invoicetype.equals=" + DEFAULT_INVOICETYPE, "invoicetype.equals=" + UPDATED_INVOICETYPE);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicetypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicetype in
        defaultReceiptLinesFiltering(
            "invoicetype.in=" + DEFAULT_INVOICETYPE + "," + UPDATED_INVOICETYPE,
            "invoicetype.in=" + UPDATED_INVOICETYPE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicetypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicetype is not null
        defaultReceiptLinesFiltering("invoicetype.specified=true", "invoicetype.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicetypeContainsSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicetype contains
        defaultReceiptLinesFiltering("invoicetype.contains=" + DEFAULT_INVOICETYPE, "invoicetype.contains=" + UPDATED_INVOICETYPE);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByInvoicetypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where invoicetype does not contain
        defaultReceiptLinesFiltering(
            "invoicetype.doesNotContain=" + UPDATED_INVOICETYPE,
            "invoicetype.doesNotContain=" + DEFAULT_INVOICETYPE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByOriginalamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where originalamount equals to
        defaultReceiptLinesFiltering("originalamount.equals=" + DEFAULT_ORIGINALAMOUNT, "originalamount.equals=" + UPDATED_ORIGINALAMOUNT);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByOriginalamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where originalamount in
        defaultReceiptLinesFiltering(
            "originalamount.in=" + DEFAULT_ORIGINALAMOUNT + "," + UPDATED_ORIGINALAMOUNT,
            "originalamount.in=" + UPDATED_ORIGINALAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByOriginalamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where originalamount is not null
        defaultReceiptLinesFiltering("originalamount.specified=true", "originalamount.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByOriginalamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where originalamount is greater than or equal to
        defaultReceiptLinesFiltering(
            "originalamount.greaterThanOrEqual=" + DEFAULT_ORIGINALAMOUNT,
            "originalamount.greaterThanOrEqual=" + UPDATED_ORIGINALAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByOriginalamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where originalamount is less than or equal to
        defaultReceiptLinesFiltering(
            "originalamount.lessThanOrEqual=" + DEFAULT_ORIGINALAMOUNT,
            "originalamount.lessThanOrEqual=" + SMALLER_ORIGINALAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByOriginalamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where originalamount is less than
        defaultReceiptLinesFiltering(
            "originalamount.lessThan=" + UPDATED_ORIGINALAMOUNT,
            "originalamount.lessThan=" + DEFAULT_ORIGINALAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByOriginalamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where originalamount is greater than
        defaultReceiptLinesFiltering(
            "originalamount.greaterThan=" + SMALLER_ORIGINALAMOUNT,
            "originalamount.greaterThan=" + DEFAULT_ORIGINALAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountowingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountowing equals to
        defaultReceiptLinesFiltering("amountowing.equals=" + DEFAULT_AMOUNTOWING, "amountowing.equals=" + UPDATED_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountowingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountowing in
        defaultReceiptLinesFiltering(
            "amountowing.in=" + DEFAULT_AMOUNTOWING + "," + UPDATED_AMOUNTOWING,
            "amountowing.in=" + UPDATED_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountowingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountowing is not null
        defaultReceiptLinesFiltering("amountowing.specified=true", "amountowing.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountowingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountowing is greater than or equal to
        defaultReceiptLinesFiltering(
            "amountowing.greaterThanOrEqual=" + DEFAULT_AMOUNTOWING,
            "amountowing.greaterThanOrEqual=" + UPDATED_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountowingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountowing is less than or equal to
        defaultReceiptLinesFiltering(
            "amountowing.lessThanOrEqual=" + DEFAULT_AMOUNTOWING,
            "amountowing.lessThanOrEqual=" + SMALLER_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountowingIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountowing is less than
        defaultReceiptLinesFiltering("amountowing.lessThan=" + UPDATED_AMOUNTOWING, "amountowing.lessThan=" + DEFAULT_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountowingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountowing is greater than
        defaultReceiptLinesFiltering("amountowing.greaterThan=" + SMALLER_AMOUNTOWING, "amountowing.greaterThan=" + DEFAULT_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscountavailableIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discountavailable equals to
        defaultReceiptLinesFiltering(
            "discountavailable.equals=" + DEFAULT_DISCOUNTAVAILABLE,
            "discountavailable.equals=" + UPDATED_DISCOUNTAVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscountavailableIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discountavailable in
        defaultReceiptLinesFiltering(
            "discountavailable.in=" + DEFAULT_DISCOUNTAVAILABLE + "," + UPDATED_DISCOUNTAVAILABLE,
            "discountavailable.in=" + UPDATED_DISCOUNTAVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscountavailableIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discountavailable is not null
        defaultReceiptLinesFiltering("discountavailable.specified=true", "discountavailable.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscountavailableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discountavailable is greater than or equal to
        defaultReceiptLinesFiltering(
            "discountavailable.greaterThanOrEqual=" + DEFAULT_DISCOUNTAVAILABLE,
            "discountavailable.greaterThanOrEqual=" + UPDATED_DISCOUNTAVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscountavailableIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discountavailable is less than or equal to
        defaultReceiptLinesFiltering(
            "discountavailable.lessThanOrEqual=" + DEFAULT_DISCOUNTAVAILABLE,
            "discountavailable.lessThanOrEqual=" + SMALLER_DISCOUNTAVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscountavailableIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discountavailable is less than
        defaultReceiptLinesFiltering(
            "discountavailable.lessThan=" + UPDATED_DISCOUNTAVAILABLE,
            "discountavailable.lessThan=" + DEFAULT_DISCOUNTAVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscountavailableIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discountavailable is greater than
        defaultReceiptLinesFiltering(
            "discountavailable.greaterThan=" + SMALLER_DISCOUNTAVAILABLE,
            "discountavailable.greaterThan=" + DEFAULT_DISCOUNTAVAILABLE
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscounttakenIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discounttaken equals to
        defaultReceiptLinesFiltering("discounttaken.equals=" + DEFAULT_DISCOUNTTAKEN, "discounttaken.equals=" + UPDATED_DISCOUNTTAKEN);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscounttakenIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discounttaken in
        defaultReceiptLinesFiltering(
            "discounttaken.in=" + DEFAULT_DISCOUNTTAKEN + "," + UPDATED_DISCOUNTTAKEN,
            "discounttaken.in=" + UPDATED_DISCOUNTTAKEN
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscounttakenIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discounttaken is not null
        defaultReceiptLinesFiltering("discounttaken.specified=true", "discounttaken.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscounttakenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discounttaken is greater than or equal to
        defaultReceiptLinesFiltering(
            "discounttaken.greaterThanOrEqual=" + DEFAULT_DISCOUNTTAKEN,
            "discounttaken.greaterThanOrEqual=" + UPDATED_DISCOUNTTAKEN
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscounttakenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discounttaken is less than or equal to
        defaultReceiptLinesFiltering(
            "discounttaken.lessThanOrEqual=" + DEFAULT_DISCOUNTTAKEN,
            "discounttaken.lessThanOrEqual=" + SMALLER_DISCOUNTTAKEN
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscounttakenIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discounttaken is less than
        defaultReceiptLinesFiltering("discounttaken.lessThan=" + UPDATED_DISCOUNTTAKEN, "discounttaken.lessThan=" + DEFAULT_DISCOUNTTAKEN);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByDiscounttakenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where discounttaken is greater than
        defaultReceiptLinesFiltering(
            "discounttaken.greaterThan=" + SMALLER_DISCOUNTTAKEN,
            "discounttaken.greaterThan=" + DEFAULT_DISCOUNTTAKEN
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountreceivedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountreceived equals to
        defaultReceiptLinesFiltering("amountreceived.equals=" + DEFAULT_AMOUNTRECEIVED, "amountreceived.equals=" + UPDATED_AMOUNTRECEIVED);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountreceivedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountreceived in
        defaultReceiptLinesFiltering(
            "amountreceived.in=" + DEFAULT_AMOUNTRECEIVED + "," + UPDATED_AMOUNTRECEIVED,
            "amountreceived.in=" + UPDATED_AMOUNTRECEIVED
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountreceivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountreceived is not null
        defaultReceiptLinesFiltering("amountreceived.specified=true", "amountreceived.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountreceivedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountreceived is greater than or equal to
        defaultReceiptLinesFiltering(
            "amountreceived.greaterThanOrEqual=" + DEFAULT_AMOUNTRECEIVED,
            "amountreceived.greaterThanOrEqual=" + UPDATED_AMOUNTRECEIVED
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountreceivedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountreceived is less than or equal to
        defaultReceiptLinesFiltering(
            "amountreceived.lessThanOrEqual=" + DEFAULT_AMOUNTRECEIVED,
            "amountreceived.lessThanOrEqual=" + SMALLER_AMOUNTRECEIVED
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountreceivedIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountreceived is less than
        defaultReceiptLinesFiltering(
            "amountreceived.lessThan=" + UPDATED_AMOUNTRECEIVED,
            "amountreceived.lessThan=" + DEFAULT_AMOUNTRECEIVED
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAmountreceivedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where amountreceived is greater than
        defaultReceiptLinesFiltering(
            "amountreceived.greaterThan=" + SMALLER_AMOUNTRECEIVED,
            "amountreceived.greaterThan=" + DEFAULT_AMOUNTRECEIVED
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmu equals to
        defaultReceiptLinesFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmu in
        defaultReceiptLinesFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmu is not null
        defaultReceiptLinesFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmu is greater than or equal to
        defaultReceiptLinesFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmu is less than or equal to
        defaultReceiptLinesFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmu is less than
        defaultReceiptLinesFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmu is greater than
        defaultReceiptLinesFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmd equals to
        defaultReceiptLinesFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmd in
        defaultReceiptLinesFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where lmd is not null
        defaultReceiptLinesFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAccountidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where accountid equals to
        defaultReceiptLinesFiltering("accountid.equals=" + DEFAULT_ACCOUNTID, "accountid.equals=" + UPDATED_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAccountidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where accountid in
        defaultReceiptLinesFiltering("accountid.in=" + DEFAULT_ACCOUNTID + "," + UPDATED_ACCOUNTID, "accountid.in=" + UPDATED_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAccountidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where accountid is not null
        defaultReceiptLinesFiltering("accountid.specified=true", "accountid.specified=false");
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAccountidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where accountid is greater than or equal to
        defaultReceiptLinesFiltering(
            "accountid.greaterThanOrEqual=" + DEFAULT_ACCOUNTID,
            "accountid.greaterThanOrEqual=" + UPDATED_ACCOUNTID
        );
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAccountidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where accountid is less than or equal to
        defaultReceiptLinesFiltering("accountid.lessThanOrEqual=" + DEFAULT_ACCOUNTID, "accountid.lessThanOrEqual=" + SMALLER_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAccountidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where accountid is less than
        defaultReceiptLinesFiltering("accountid.lessThan=" + UPDATED_ACCOUNTID, "accountid.lessThan=" + DEFAULT_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllReceiptLinesByAccountidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        // Get all the receiptLinesList where accountid is greater than
        defaultReceiptLinesFiltering("accountid.greaterThan=" + SMALLER_ACCOUNTID, "accountid.greaterThan=" + DEFAULT_ACCOUNTID);
    }

    private void defaultReceiptLinesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultReceiptLinesShouldBeFound(shouldBeFound);
        defaultReceiptLinesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReceiptLinesShouldBeFound(String filter) throws Exception {
        restReceiptLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID.intValue())))
            .andExpect(jsonPath("$.[*].invoicecode").value(hasItem(DEFAULT_INVOICECODE)))
            .andExpect(jsonPath("$.[*].invoicetype").value(hasItem(DEFAULT_INVOICETYPE)))
            .andExpect(jsonPath("$.[*].originalamount").value(hasItem(DEFAULT_ORIGINALAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountowing").value(hasItem(DEFAULT_AMOUNTOWING.doubleValue())))
            .andExpect(jsonPath("$.[*].discountavailable").value(hasItem(DEFAULT_DISCOUNTAVAILABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].discounttaken").value(hasItem(DEFAULT_DISCOUNTTAKEN.doubleValue())))
            .andExpect(jsonPath("$.[*].amountreceived").value(hasItem(DEFAULT_AMOUNTRECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU.intValue())))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].accountid").value(hasItem(DEFAULT_ACCOUNTID.intValue())));

        // Check, that the count call also returns 1
        restReceiptLinesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReceiptLinesShouldNotBeFound(String filter) throws Exception {
        restReceiptLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReceiptLinesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingReceiptLines() throws Exception {
        // Get the receiptLines
        restReceiptLinesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReceiptLines() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the receiptLines
        ReceiptLines updatedReceiptLines = receiptLinesRepository.findById(receiptLines.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReceiptLines are not directly saved in db
        em.detach(updatedReceiptLines);
        updatedReceiptLines
            .lineid(UPDATED_LINEID)
            .invoicecode(UPDATED_INVOICECODE)
            .invoicetype(UPDATED_INVOICETYPE)
            .originalamount(UPDATED_ORIGINALAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .discountavailable(UPDATED_DISCOUNTAVAILABLE)
            .discounttaken(UPDATED_DISCOUNTTAKEN)
            .amountreceived(UPDATED_AMOUNTRECEIVED)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .accountid(UPDATED_ACCOUNTID);

        restReceiptLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReceiptLines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedReceiptLines))
            )
            .andExpect(status().isOk());

        // Validate the ReceiptLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReceiptLinesToMatchAllProperties(updatedReceiptLines);
    }

    @Test
    @Transactional
    void putNonExistingReceiptLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptLines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, receiptLines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(receiptLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiptLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReceiptLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiptLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(receiptLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiptLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReceiptLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiptLinesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(receiptLines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReceiptLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReceiptLinesWithPatch() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the receiptLines using partial update
        ReceiptLines partialUpdatedReceiptLines = new ReceiptLines();
        partialUpdatedReceiptLines.setId(receiptLines.getId());

        partialUpdatedReceiptLines
            .invoicecode(UPDATED_INVOICECODE)
            .originalamount(UPDATED_ORIGINALAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .discountavailable(UPDATED_DISCOUNTAVAILABLE)
            .discounttaken(UPDATED_DISCOUNTTAKEN);

        restReceiptLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceiptLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReceiptLines))
            )
            .andExpect(status().isOk());

        // Validate the ReceiptLines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReceiptLinesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedReceiptLines, receiptLines),
            getPersistedReceiptLines(receiptLines)
        );
    }

    @Test
    @Transactional
    void fullUpdateReceiptLinesWithPatch() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the receiptLines using partial update
        ReceiptLines partialUpdatedReceiptLines = new ReceiptLines();
        partialUpdatedReceiptLines.setId(receiptLines.getId());

        partialUpdatedReceiptLines
            .lineid(UPDATED_LINEID)
            .invoicecode(UPDATED_INVOICECODE)
            .invoicetype(UPDATED_INVOICETYPE)
            .originalamount(UPDATED_ORIGINALAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .discountavailable(UPDATED_DISCOUNTAVAILABLE)
            .discounttaken(UPDATED_DISCOUNTTAKEN)
            .amountreceived(UPDATED_AMOUNTRECEIVED)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .accountid(UPDATED_ACCOUNTID);

        restReceiptLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReceiptLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReceiptLines))
            )
            .andExpect(status().isOk());

        // Validate the ReceiptLines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReceiptLinesUpdatableFieldsEquals(partialUpdatedReceiptLines, getPersistedReceiptLines(partialUpdatedReceiptLines));
    }

    @Test
    @Transactional
    void patchNonExistingReceiptLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptLines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, receiptLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(receiptLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiptLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReceiptLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiptLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(receiptLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReceiptLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReceiptLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        receiptLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReceiptLinesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(receiptLines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReceiptLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReceiptLines() throws Exception {
        // Initialize the database
        insertedReceiptLines = receiptLinesRepository.saveAndFlush(receiptLines);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the receiptLines
        restReceiptLinesMockMvc
            .perform(delete(ENTITY_API_URL_ID, receiptLines.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return receiptLinesRepository.count();
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

    protected ReceiptLines getPersistedReceiptLines(ReceiptLines receiptLines) {
        return receiptLinesRepository.findById(receiptLines.getId()).orElseThrow();
    }

    protected void assertPersistedReceiptLinesToMatchAllProperties(ReceiptLines expectedReceiptLines) {
        assertReceiptLinesAllPropertiesEquals(expectedReceiptLines, getPersistedReceiptLines(expectedReceiptLines));
    }

    protected void assertPersistedReceiptLinesToMatchUpdatableProperties(ReceiptLines expectedReceiptLines) {
        assertReceiptLinesAllUpdatablePropertiesEquals(expectedReceiptLines, getPersistedReceiptLines(expectedReceiptLines));
    }
}
