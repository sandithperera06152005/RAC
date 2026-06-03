package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutojobsinvoicelinebatchesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autojobsinvoicelinebatches;
import com.heavenscode.rac.repository.AutojobsinvoicelinebatchesRepository;
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
 * Integration tests for the {@link AutojobsinvoicelinebatchesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutojobsinvoicelinebatchesResourceIT {

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;
    private static final Integer SMALLER_LINEID = 1 - 1;

    private static final Integer DEFAULT_BATCHLINEID = 1;
    private static final Integer UPDATED_BATCHLINEID = 2;
    private static final Integer SMALLER_BATCHLINEID = 1 - 1;

    private static final Integer DEFAULT_ITEMID = 1;
    private static final Integer UPDATED_ITEMID = 2;
    private static final Integer SMALLER_ITEMID = 1 - 1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_BATCHID = 1;
    private static final Integer UPDATED_BATCHID = 2;
    private static final Integer SMALLER_BATCHID = 1 - 1;

    private static final String DEFAULT_BATCHCODE = "AAAAAAAAAA";
    private static final String UPDATED_BATCHCODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TXDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TXDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MANUFACTUREDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MANUFACTUREDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIREDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIREDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_QTY = 1F;
    private static final Float UPDATED_QTY = 2F;
    private static final Float SMALLER_QTY = 1F - 1F;

    private static final Float DEFAULT_COST = 1F;
    private static final Float UPDATED_COST = 2F;
    private static final Float SMALLER_COST = 1F - 1F;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_NBT = false;
    private static final Boolean UPDATED_NBT = true;

    private static final Boolean DEFAULT_VAT = false;
    private static final Boolean UPDATED_VAT = true;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;
    private static final Float SMALLER_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_TOTAL = 1F;
    private static final Float UPDATED_TOTAL = 2F;
    private static final Float SMALLER_TOTAL = 1F - 1F;

    private static final Boolean DEFAULT_ISSUED = false;
    private static final Boolean UPDATED_ISSUED = true;

    private static final Integer DEFAULT_ISSUEDBY = 1;
    private static final Integer UPDATED_ISSUEDBY = 2;
    private static final Integer SMALLER_ISSUEDBY = 1 - 1;

    private static final Instant DEFAULT_ISSUEDDATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ISSUEDDATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ADDEDBYID = 1;
    private static final Integer UPDATED_ADDEDBYID = 2;
    private static final Integer SMALLER_ADDEDBYID = 1 - 1;

    private static final Integer DEFAULT_CANCELOPTID = 1;
    private static final Integer UPDATED_CANCELOPTID = 2;
    private static final Integer SMALLER_CANCELOPTID = 1 - 1;

    private static final String DEFAULT_CANCELOPT = "AAAAAAAAAA";
    private static final String UPDATED_CANCELOPT = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANCELBY = 1;
    private static final Integer UPDATED_CANCELBY = 2;
    private static final Integer SMALLER_CANCELBY = 1 - 1;

    private static final String ENTITY_API_URL = "/api/autojobsinvoicelinebatches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutojobsinvoicelinebatchesRepository autojobsinvoicelinebatchesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutojobsinvoicelinebatchesMockMvc;

    private Autojobsinvoicelinebatches autojobsinvoicelinebatches;

    private Autojobsinvoicelinebatches insertedAutojobsinvoicelinebatches;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsinvoicelinebatches createEntity() {
        return new Autojobsinvoicelinebatches()
            .lineid(DEFAULT_LINEID)
            .batchlineid(DEFAULT_BATCHLINEID)
            .itemid(DEFAULT_ITEMID)
            .code(DEFAULT_CODE)
            .batchid(DEFAULT_BATCHID)
            .batchcode(DEFAULT_BATCHCODE)
            .txdate(DEFAULT_TXDATE)
            .manufacturedate(DEFAULT_MANUFACTUREDATE)
            .expireddate(DEFAULT_EXPIREDDATE)
            .qty(DEFAULT_QTY)
            .cost(DEFAULT_COST)
            .price(DEFAULT_PRICE)
            .notes(DEFAULT_NOTES)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .nbt(DEFAULT_NBT)
            .vat(DEFAULT_VAT)
            .discount(DEFAULT_DISCOUNT)
            .total(DEFAULT_TOTAL)
            .issued(DEFAULT_ISSUED)
            .issuedby(DEFAULT_ISSUEDBY)
            .issueddatetime(DEFAULT_ISSUEDDATETIME)
            .addedbyid(DEFAULT_ADDEDBYID)
            .canceloptid(DEFAULT_CANCELOPTID)
            .cancelopt(DEFAULT_CANCELOPT)
            .cancelby(DEFAULT_CANCELBY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsinvoicelinebatches createUpdatedEntity() {
        return new Autojobsinvoicelinebatches()
            .lineid(UPDATED_LINEID)
            .batchlineid(UPDATED_BATCHLINEID)
            .itemid(UPDATED_ITEMID)
            .code(UPDATED_CODE)
            .batchid(UPDATED_BATCHID)
            .batchcode(UPDATED_BATCHCODE)
            .txdate(UPDATED_TXDATE)
            .manufacturedate(UPDATED_MANUFACTUREDATE)
            .expireddate(UPDATED_EXPIREDDATE)
            .qty(UPDATED_QTY)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT)
            .discount(UPDATED_DISCOUNT)
            .total(UPDATED_TOTAL)
            .issued(UPDATED_ISSUED)
            .issuedby(UPDATED_ISSUEDBY)
            .issueddatetime(UPDATED_ISSUEDDATETIME)
            .addedbyid(UPDATED_ADDEDBYID)
            .canceloptid(UPDATED_CANCELOPTID)
            .cancelopt(UPDATED_CANCELOPT)
            .cancelby(UPDATED_CANCELBY);
    }

    @BeforeEach
    public void initTest() {
        autojobsinvoicelinebatches = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutojobsinvoicelinebatches != null) {
            autojobsinvoicelinebatchesRepository.delete(insertedAutojobsinvoicelinebatches);
            insertedAutojobsinvoicelinebatches = null;
        }
    }

    @Test
    @Transactional
    void createAutojobsinvoicelinebatches() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autojobsinvoicelinebatches
        var returnedAutojobsinvoicelinebatches = om.readValue(
            restAutojobsinvoicelinebatchesMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelinebatches))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autojobsinvoicelinebatches.class
        );

        // Validate the Autojobsinvoicelinebatches in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutojobsinvoicelinebatchesUpdatableFieldsEquals(
            returnedAutojobsinvoicelinebatches,
            getPersistedAutojobsinvoicelinebatches(returnedAutojobsinvoicelinebatches)
        );

        insertedAutojobsinvoicelinebatches = returnedAutojobsinvoicelinebatches;
    }

    @Test
    @Transactional
    void createAutojobsinvoicelinebatchesWithExistingId() throws Exception {
        // Create the Autojobsinvoicelinebatches with an existing ID
        autojobsinvoicelinebatches.setId(1);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutojobsinvoicelinebatchesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelinebatches)))
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelinebatches in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatches() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList
        restAutojobsinvoicelinebatchesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsinvoicelinebatches.getId().intValue())))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].batchlineid").value(hasItem(DEFAULT_BATCHLINEID)))
            .andExpect(jsonPath("$.[*].itemid").value(hasItem(DEFAULT_ITEMID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].batchid").value(hasItem(DEFAULT_BATCHID)))
            .andExpect(jsonPath("$.[*].batchcode").value(hasItem(DEFAULT_BATCHCODE)))
            .andExpect(jsonPath("$.[*].txdate").value(hasItem(DEFAULT_TXDATE.toString())))
            .andExpect(jsonPath("$.[*].manufacturedate").value(hasItem(DEFAULT_MANUFACTUREDATE.toString())))
            .andExpect(jsonPath("$.[*].expireddate").value(hasItem(DEFAULT_EXPIREDDATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].issued").value(hasItem(DEFAULT_ISSUED.booleanValue())))
            .andExpect(jsonPath("$.[*].issuedby").value(hasItem(DEFAULT_ISSUEDBY)))
            .andExpect(jsonPath("$.[*].issueddatetime").value(hasItem(DEFAULT_ISSUEDDATETIME.toString())))
            .andExpect(jsonPath("$.[*].addedbyid").value(hasItem(DEFAULT_ADDEDBYID)))
            .andExpect(jsonPath("$.[*].canceloptid").value(hasItem(DEFAULT_CANCELOPTID)))
            .andExpect(jsonPath("$.[*].cancelopt").value(hasItem(DEFAULT_CANCELOPT)))
            .andExpect(jsonPath("$.[*].cancelby").value(hasItem(DEFAULT_CANCELBY)));
    }

    @Test
    @Transactional
    void getAutojobsinvoicelinebatches() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get the autojobsinvoicelinebatches
        restAutojobsinvoicelinebatchesMockMvc
            .perform(get(ENTITY_API_URL_ID, autojobsinvoicelinebatches.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autojobsinvoicelinebatches.getId().intValue()))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.batchlineid").value(DEFAULT_BATCHLINEID))
            .andExpect(jsonPath("$.itemid").value(DEFAULT_ITEMID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.batchid").value(DEFAULT_BATCHID))
            .andExpect(jsonPath("$.batchcode").value(DEFAULT_BATCHCODE))
            .andExpect(jsonPath("$.txdate").value(DEFAULT_TXDATE.toString()))
            .andExpect(jsonPath("$.manufacturedate").value(DEFAULT_MANUFACTUREDATE.toString()))
            .andExpect(jsonPath("$.expireddate").value(DEFAULT_EXPIREDDATE.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY.doubleValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.nbt").value(DEFAULT_NBT.booleanValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.booleanValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.issued").value(DEFAULT_ISSUED.booleanValue()))
            .andExpect(jsonPath("$.issuedby").value(DEFAULT_ISSUEDBY))
            .andExpect(jsonPath("$.issueddatetime").value(DEFAULT_ISSUEDDATETIME.toString()))
            .andExpect(jsonPath("$.addedbyid").value(DEFAULT_ADDEDBYID))
            .andExpect(jsonPath("$.canceloptid").value(DEFAULT_CANCELOPTID))
            .andExpect(jsonPath("$.cancelopt").value(DEFAULT_CANCELOPT))
            .andExpect(jsonPath("$.cancelby").value(DEFAULT_CANCELBY));
    }

    @Test
    @Transactional
    void getAutojobsinvoicelinebatchesByIdFiltering() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        Integer id = autojobsinvoicelinebatches.getId();

        defaultAutojobsinvoicelinebatchesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAutojobsinvoicelinebatchesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAutojobsinvoicelinebatchesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lineid equals to
        defaultAutojobsinvoicelinebatchesFiltering("lineid.equals=" + DEFAULT_LINEID, "lineid.equals=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lineid in
        defaultAutojobsinvoicelinebatchesFiltering("lineid.in=" + DEFAULT_LINEID + "," + UPDATED_LINEID, "lineid.in=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lineid is not null
        defaultAutojobsinvoicelinebatchesFiltering("lineid.specified=true", "lineid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lineid is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "lineid.greaterThanOrEqual=" + DEFAULT_LINEID,
            "lineid.greaterThanOrEqual=" + UPDATED_LINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lineid is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("lineid.lessThanOrEqual=" + DEFAULT_LINEID, "lineid.lessThanOrEqual=" + SMALLER_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lineid is less than
        defaultAutojobsinvoicelinebatchesFiltering("lineid.lessThan=" + UPDATED_LINEID, "lineid.lessThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lineid is greater than
        defaultAutojobsinvoicelinebatchesFiltering("lineid.greaterThan=" + SMALLER_LINEID, "lineid.greaterThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchlineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchlineid equals to
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchlineid.equals=" + DEFAULT_BATCHLINEID,
            "batchlineid.equals=" + UPDATED_BATCHLINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchlineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchlineid in
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchlineid.in=" + DEFAULT_BATCHLINEID + "," + UPDATED_BATCHLINEID,
            "batchlineid.in=" + UPDATED_BATCHLINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchlineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchlineid is not null
        defaultAutojobsinvoicelinebatchesFiltering("batchlineid.specified=true", "batchlineid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchlineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchlineid is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchlineid.greaterThanOrEqual=" + DEFAULT_BATCHLINEID,
            "batchlineid.greaterThanOrEqual=" + UPDATED_BATCHLINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchlineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchlineid is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchlineid.lessThanOrEqual=" + DEFAULT_BATCHLINEID,
            "batchlineid.lessThanOrEqual=" + SMALLER_BATCHLINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchlineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchlineid is less than
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchlineid.lessThan=" + UPDATED_BATCHLINEID,
            "batchlineid.lessThan=" + DEFAULT_BATCHLINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchlineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchlineid is greater than
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchlineid.greaterThan=" + SMALLER_BATCHLINEID,
            "batchlineid.greaterThan=" + DEFAULT_BATCHLINEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByItemidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where itemid equals to
        defaultAutojobsinvoicelinebatchesFiltering("itemid.equals=" + DEFAULT_ITEMID, "itemid.equals=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByItemidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where itemid in
        defaultAutojobsinvoicelinebatchesFiltering("itemid.in=" + DEFAULT_ITEMID + "," + UPDATED_ITEMID, "itemid.in=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByItemidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where itemid is not null
        defaultAutojobsinvoicelinebatchesFiltering("itemid.specified=true", "itemid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByItemidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where itemid is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "itemid.greaterThanOrEqual=" + DEFAULT_ITEMID,
            "itemid.greaterThanOrEqual=" + UPDATED_ITEMID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByItemidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where itemid is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("itemid.lessThanOrEqual=" + DEFAULT_ITEMID, "itemid.lessThanOrEqual=" + SMALLER_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByItemidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where itemid is less than
        defaultAutojobsinvoicelinebatchesFiltering("itemid.lessThan=" + UPDATED_ITEMID, "itemid.lessThan=" + DEFAULT_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByItemidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where itemid is greater than
        defaultAutojobsinvoicelinebatchesFiltering("itemid.greaterThan=" + SMALLER_ITEMID, "itemid.greaterThan=" + DEFAULT_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where code equals to
        defaultAutojobsinvoicelinebatchesFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where code in
        defaultAutojobsinvoicelinebatchesFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where code is not null
        defaultAutojobsinvoicelinebatchesFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where code contains
        defaultAutojobsinvoicelinebatchesFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where code does not contain
        defaultAutojobsinvoicelinebatchesFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchid equals to
        defaultAutojobsinvoicelinebatchesFiltering("batchid.equals=" + DEFAULT_BATCHID, "batchid.equals=" + UPDATED_BATCHID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchid in
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchid.in=" + DEFAULT_BATCHID + "," + UPDATED_BATCHID,
            "batchid.in=" + UPDATED_BATCHID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchid is not null
        defaultAutojobsinvoicelinebatchesFiltering("batchid.specified=true", "batchid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchid is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchid.greaterThanOrEqual=" + DEFAULT_BATCHID,
            "batchid.greaterThanOrEqual=" + UPDATED_BATCHID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchid is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchid.lessThanOrEqual=" + DEFAULT_BATCHID,
            "batchid.lessThanOrEqual=" + SMALLER_BATCHID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchid is less than
        defaultAutojobsinvoicelinebatchesFiltering("batchid.lessThan=" + UPDATED_BATCHID, "batchid.lessThan=" + DEFAULT_BATCHID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchid is greater than
        defaultAutojobsinvoicelinebatchesFiltering("batchid.greaterThan=" + SMALLER_BATCHID, "batchid.greaterThan=" + DEFAULT_BATCHID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchcode equals to
        defaultAutojobsinvoicelinebatchesFiltering("batchcode.equals=" + DEFAULT_BATCHCODE, "batchcode.equals=" + UPDATED_BATCHCODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchcodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchcode in
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchcode.in=" + DEFAULT_BATCHCODE + "," + UPDATED_BATCHCODE,
            "batchcode.in=" + UPDATED_BATCHCODE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchcode is not null
        defaultAutojobsinvoicelinebatchesFiltering("batchcode.specified=true", "batchcode.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchcodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchcode contains
        defaultAutojobsinvoicelinebatchesFiltering("batchcode.contains=" + DEFAULT_BATCHCODE, "batchcode.contains=" + UPDATED_BATCHCODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByBatchcodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where batchcode does not contain
        defaultAutojobsinvoicelinebatchesFiltering(
            "batchcode.doesNotContain=" + UPDATED_BATCHCODE,
            "batchcode.doesNotContain=" + DEFAULT_BATCHCODE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTxdateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where txdate equals to
        defaultAutojobsinvoicelinebatchesFiltering("txdate.equals=" + DEFAULT_TXDATE, "txdate.equals=" + UPDATED_TXDATE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTxdateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where txdate in
        defaultAutojobsinvoicelinebatchesFiltering("txdate.in=" + DEFAULT_TXDATE + "," + UPDATED_TXDATE, "txdate.in=" + UPDATED_TXDATE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTxdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where txdate is not null
        defaultAutojobsinvoicelinebatchesFiltering("txdate.specified=true", "txdate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByManufacturedateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where manufacturedate equals to
        defaultAutojobsinvoicelinebatchesFiltering(
            "manufacturedate.equals=" + DEFAULT_MANUFACTUREDATE,
            "manufacturedate.equals=" + UPDATED_MANUFACTUREDATE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByManufacturedateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where manufacturedate in
        defaultAutojobsinvoicelinebatchesFiltering(
            "manufacturedate.in=" + DEFAULT_MANUFACTUREDATE + "," + UPDATED_MANUFACTUREDATE,
            "manufacturedate.in=" + UPDATED_MANUFACTUREDATE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByManufacturedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where manufacturedate is not null
        defaultAutojobsinvoicelinebatchesFiltering("manufacturedate.specified=true", "manufacturedate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByExpireddateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where expireddate equals to
        defaultAutojobsinvoicelinebatchesFiltering(
            "expireddate.equals=" + DEFAULT_EXPIREDDATE,
            "expireddate.equals=" + UPDATED_EXPIREDDATE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByExpireddateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where expireddate in
        defaultAutojobsinvoicelinebatchesFiltering(
            "expireddate.in=" + DEFAULT_EXPIREDDATE + "," + UPDATED_EXPIREDDATE,
            "expireddate.in=" + UPDATED_EXPIREDDATE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByExpireddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where expireddate is not null
        defaultAutojobsinvoicelinebatchesFiltering("expireddate.specified=true", "expireddate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where qty equals to
        defaultAutojobsinvoicelinebatchesFiltering("qty.equals=" + DEFAULT_QTY, "qty.equals=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByQtyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where qty in
        defaultAutojobsinvoicelinebatchesFiltering("qty.in=" + DEFAULT_QTY + "," + UPDATED_QTY, "qty.in=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where qty is not null
        defaultAutojobsinvoicelinebatchesFiltering("qty.specified=true", "qty.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where qty is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("qty.greaterThanOrEqual=" + DEFAULT_QTY, "qty.greaterThanOrEqual=" + UPDATED_QTY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where qty is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("qty.lessThanOrEqual=" + DEFAULT_QTY, "qty.lessThanOrEqual=" + SMALLER_QTY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where qty is less than
        defaultAutojobsinvoicelinebatchesFiltering("qty.lessThan=" + UPDATED_QTY, "qty.lessThan=" + DEFAULT_QTY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where qty is greater than
        defaultAutojobsinvoicelinebatchesFiltering("qty.greaterThan=" + SMALLER_QTY, "qty.greaterThan=" + DEFAULT_QTY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cost equals to
        defaultAutojobsinvoicelinebatchesFiltering("cost.equals=" + DEFAULT_COST, "cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cost in
        defaultAutojobsinvoicelinebatchesFiltering("cost.in=" + DEFAULT_COST + "," + UPDATED_COST, "cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cost is not null
        defaultAutojobsinvoicelinebatchesFiltering("cost.specified=true", "cost.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cost is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("cost.greaterThanOrEqual=" + DEFAULT_COST, "cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cost is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("cost.lessThanOrEqual=" + DEFAULT_COST, "cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cost is less than
        defaultAutojobsinvoicelinebatchesFiltering("cost.lessThan=" + UPDATED_COST, "cost.lessThan=" + DEFAULT_COST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cost is greater than
        defaultAutojobsinvoicelinebatchesFiltering("cost.greaterThan=" + SMALLER_COST, "cost.greaterThan=" + DEFAULT_COST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where price equals to
        defaultAutojobsinvoicelinebatchesFiltering("price.equals=" + DEFAULT_PRICE, "price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where price in
        defaultAutojobsinvoicelinebatchesFiltering("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE, "price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where price is not null
        defaultAutojobsinvoicelinebatchesFiltering("price.specified=true", "price.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where price is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "price.greaterThanOrEqual=" + DEFAULT_PRICE,
            "price.greaterThanOrEqual=" + UPDATED_PRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where price is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("price.lessThanOrEqual=" + DEFAULT_PRICE, "price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where price is less than
        defaultAutojobsinvoicelinebatchesFiltering("price.lessThan=" + UPDATED_PRICE, "price.lessThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where price is greater than
        defaultAutojobsinvoicelinebatchesFiltering("price.greaterThan=" + SMALLER_PRICE, "price.greaterThan=" + DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where notes equals to
        defaultAutojobsinvoicelinebatchesFiltering("notes.equals=" + DEFAULT_NOTES, "notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where notes in
        defaultAutojobsinvoicelinebatchesFiltering("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES, "notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where notes is not null
        defaultAutojobsinvoicelinebatchesFiltering("notes.specified=true", "notes.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByNotesContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where notes contains
        defaultAutojobsinvoicelinebatchesFiltering("notes.contains=" + DEFAULT_NOTES, "notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where notes does not contain
        defaultAutojobsinvoicelinebatchesFiltering("notes.doesNotContain=" + UPDATED_NOTES, "notes.doesNotContain=" + DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmu equals to
        defaultAutojobsinvoicelinebatchesFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmu in
        defaultAutojobsinvoicelinebatchesFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmu is not null
        defaultAutojobsinvoicelinebatchesFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmu is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmu is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmu is less than
        defaultAutojobsinvoicelinebatchesFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmu is greater than
        defaultAutojobsinvoicelinebatchesFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmd equals to
        defaultAutojobsinvoicelinebatchesFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmd in
        defaultAutojobsinvoicelinebatchesFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where lmd is not null
        defaultAutojobsinvoicelinebatchesFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByNbtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where nbt equals to
        defaultAutojobsinvoicelinebatchesFiltering("nbt.equals=" + DEFAULT_NBT, "nbt.equals=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByNbtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where nbt in
        defaultAutojobsinvoicelinebatchesFiltering("nbt.in=" + DEFAULT_NBT + "," + UPDATED_NBT, "nbt.in=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByNbtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where nbt is not null
        defaultAutojobsinvoicelinebatchesFiltering("nbt.specified=true", "nbt.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByVatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where vat equals to
        defaultAutojobsinvoicelinebatchesFiltering("vat.equals=" + DEFAULT_VAT, "vat.equals=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByVatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where vat in
        defaultAutojobsinvoicelinebatchesFiltering("vat.in=" + DEFAULT_VAT + "," + UPDATED_VAT, "vat.in=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByVatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where vat is not null
        defaultAutojobsinvoicelinebatchesFiltering("vat.specified=true", "vat.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where discount equals to
        defaultAutojobsinvoicelinebatchesFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where discount in
        defaultAutojobsinvoicelinebatchesFiltering(
            "discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT,
            "discount.in=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where discount is not null
        defaultAutojobsinvoicelinebatchesFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where discount is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where discount is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where discount is less than
        defaultAutojobsinvoicelinebatchesFiltering("discount.lessThan=" + UPDATED_DISCOUNT, "discount.lessThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where discount is greater than
        defaultAutojobsinvoicelinebatchesFiltering("discount.greaterThan=" + SMALLER_DISCOUNT, "discount.greaterThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where total equals to
        defaultAutojobsinvoicelinebatchesFiltering("total.equals=" + DEFAULT_TOTAL, "total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where total in
        defaultAutojobsinvoicelinebatchesFiltering("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL, "total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where total is not null
        defaultAutojobsinvoicelinebatchesFiltering("total.specified=true", "total.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where total is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "total.greaterThanOrEqual=" + DEFAULT_TOTAL,
            "total.greaterThanOrEqual=" + UPDATED_TOTAL
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where total is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering("total.lessThanOrEqual=" + DEFAULT_TOTAL, "total.lessThanOrEqual=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where total is less than
        defaultAutojobsinvoicelinebatchesFiltering("total.lessThan=" + UPDATED_TOTAL, "total.lessThan=" + DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where total is greater than
        defaultAutojobsinvoicelinebatchesFiltering("total.greaterThan=" + SMALLER_TOTAL, "total.greaterThan=" + DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issued equals to
        defaultAutojobsinvoicelinebatchesFiltering("issued.equals=" + DEFAULT_ISSUED, "issued.equals=" + UPDATED_ISSUED);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issued in
        defaultAutojobsinvoicelinebatchesFiltering("issued.in=" + DEFAULT_ISSUED + "," + UPDATED_ISSUED, "issued.in=" + UPDATED_ISSUED);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issued is not null
        defaultAutojobsinvoicelinebatchesFiltering("issued.specified=true", "issued.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedbyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issuedby equals to
        defaultAutojobsinvoicelinebatchesFiltering("issuedby.equals=" + DEFAULT_ISSUEDBY, "issuedby.equals=" + UPDATED_ISSUEDBY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedbyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issuedby in
        defaultAutojobsinvoicelinebatchesFiltering(
            "issuedby.in=" + DEFAULT_ISSUEDBY + "," + UPDATED_ISSUEDBY,
            "issuedby.in=" + UPDATED_ISSUEDBY
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedbyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issuedby is not null
        defaultAutojobsinvoicelinebatchesFiltering("issuedby.specified=true", "issuedby.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedbyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issuedby is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "issuedby.greaterThanOrEqual=" + DEFAULT_ISSUEDBY,
            "issuedby.greaterThanOrEqual=" + UPDATED_ISSUEDBY
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedbyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issuedby is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "issuedby.lessThanOrEqual=" + DEFAULT_ISSUEDBY,
            "issuedby.lessThanOrEqual=" + SMALLER_ISSUEDBY
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedbyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issuedby is less than
        defaultAutojobsinvoicelinebatchesFiltering("issuedby.lessThan=" + UPDATED_ISSUEDBY, "issuedby.lessThan=" + DEFAULT_ISSUEDBY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssuedbyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issuedby is greater than
        defaultAutojobsinvoicelinebatchesFiltering("issuedby.greaterThan=" + SMALLER_ISSUEDBY, "issuedby.greaterThan=" + DEFAULT_ISSUEDBY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssueddatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issueddatetime equals to
        defaultAutojobsinvoicelinebatchesFiltering(
            "issueddatetime.equals=" + DEFAULT_ISSUEDDATETIME,
            "issueddatetime.equals=" + UPDATED_ISSUEDDATETIME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssueddatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issueddatetime in
        defaultAutojobsinvoicelinebatchesFiltering(
            "issueddatetime.in=" + DEFAULT_ISSUEDDATETIME + "," + UPDATED_ISSUEDDATETIME,
            "issueddatetime.in=" + UPDATED_ISSUEDDATETIME
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByIssueddatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where issueddatetime is not null
        defaultAutojobsinvoicelinebatchesFiltering("issueddatetime.specified=true", "issueddatetime.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByAddedbyidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where addedbyid equals to
        defaultAutojobsinvoicelinebatchesFiltering("addedbyid.equals=" + DEFAULT_ADDEDBYID, "addedbyid.equals=" + UPDATED_ADDEDBYID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByAddedbyidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where addedbyid in
        defaultAutojobsinvoicelinebatchesFiltering(
            "addedbyid.in=" + DEFAULT_ADDEDBYID + "," + UPDATED_ADDEDBYID,
            "addedbyid.in=" + UPDATED_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByAddedbyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where addedbyid is not null
        defaultAutojobsinvoicelinebatchesFiltering("addedbyid.specified=true", "addedbyid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByAddedbyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where addedbyid is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "addedbyid.greaterThanOrEqual=" + DEFAULT_ADDEDBYID,
            "addedbyid.greaterThanOrEqual=" + UPDATED_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByAddedbyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where addedbyid is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "addedbyid.lessThanOrEqual=" + DEFAULT_ADDEDBYID,
            "addedbyid.lessThanOrEqual=" + SMALLER_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByAddedbyidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where addedbyid is less than
        defaultAutojobsinvoicelinebatchesFiltering("addedbyid.lessThan=" + UPDATED_ADDEDBYID, "addedbyid.lessThan=" + DEFAULT_ADDEDBYID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByAddedbyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where addedbyid is greater than
        defaultAutojobsinvoicelinebatchesFiltering(
            "addedbyid.greaterThan=" + SMALLER_ADDEDBYID,
            "addedbyid.greaterThan=" + DEFAULT_ADDEDBYID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where canceloptid equals to
        defaultAutojobsinvoicelinebatchesFiltering(
            "canceloptid.equals=" + DEFAULT_CANCELOPTID,
            "canceloptid.equals=" + UPDATED_CANCELOPTID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where canceloptid in
        defaultAutojobsinvoicelinebatchesFiltering(
            "canceloptid.in=" + DEFAULT_CANCELOPTID + "," + UPDATED_CANCELOPTID,
            "canceloptid.in=" + UPDATED_CANCELOPTID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where canceloptid is not null
        defaultAutojobsinvoicelinebatchesFiltering("canceloptid.specified=true", "canceloptid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where canceloptid is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "canceloptid.greaterThanOrEqual=" + DEFAULT_CANCELOPTID,
            "canceloptid.greaterThanOrEqual=" + UPDATED_CANCELOPTID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where canceloptid is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "canceloptid.lessThanOrEqual=" + DEFAULT_CANCELOPTID,
            "canceloptid.lessThanOrEqual=" + SMALLER_CANCELOPTID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where canceloptid is less than
        defaultAutojobsinvoicelinebatchesFiltering(
            "canceloptid.lessThan=" + UPDATED_CANCELOPTID,
            "canceloptid.lessThan=" + DEFAULT_CANCELOPTID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where canceloptid is greater than
        defaultAutojobsinvoicelinebatchesFiltering(
            "canceloptid.greaterThan=" + SMALLER_CANCELOPTID,
            "canceloptid.greaterThan=" + DEFAULT_CANCELOPTID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelopt equals to
        defaultAutojobsinvoicelinebatchesFiltering("cancelopt.equals=" + DEFAULT_CANCELOPT, "cancelopt.equals=" + UPDATED_CANCELOPT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelopt in
        defaultAutojobsinvoicelinebatchesFiltering(
            "cancelopt.in=" + DEFAULT_CANCELOPT + "," + UPDATED_CANCELOPT,
            "cancelopt.in=" + UPDATED_CANCELOPT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelopt is not null
        defaultAutojobsinvoicelinebatchesFiltering("cancelopt.specified=true", "cancelopt.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelopt contains
        defaultAutojobsinvoicelinebatchesFiltering("cancelopt.contains=" + DEFAULT_CANCELOPT, "cancelopt.contains=" + UPDATED_CANCELOPT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCanceloptNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelopt does not contain
        defaultAutojobsinvoicelinebatchesFiltering(
            "cancelopt.doesNotContain=" + UPDATED_CANCELOPT,
            "cancelopt.doesNotContain=" + DEFAULT_CANCELOPT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCancelbyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelby equals to
        defaultAutojobsinvoicelinebatchesFiltering("cancelby.equals=" + DEFAULT_CANCELBY, "cancelby.equals=" + UPDATED_CANCELBY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCancelbyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelby in
        defaultAutojobsinvoicelinebatchesFiltering(
            "cancelby.in=" + DEFAULT_CANCELBY + "," + UPDATED_CANCELBY,
            "cancelby.in=" + UPDATED_CANCELBY
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCancelbyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelby is not null
        defaultAutojobsinvoicelinebatchesFiltering("cancelby.specified=true", "cancelby.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCancelbyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelby is greater than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "cancelby.greaterThanOrEqual=" + DEFAULT_CANCELBY,
            "cancelby.greaterThanOrEqual=" + UPDATED_CANCELBY
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCancelbyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelby is less than or equal to
        defaultAutojobsinvoicelinebatchesFiltering(
            "cancelby.lessThanOrEqual=" + DEFAULT_CANCELBY,
            "cancelby.lessThanOrEqual=" + SMALLER_CANCELBY
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCancelbyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelby is less than
        defaultAutojobsinvoicelinebatchesFiltering("cancelby.lessThan=" + UPDATED_CANCELBY, "cancelby.lessThan=" + DEFAULT_CANCELBY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinebatchesByCancelbyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        // Get all the autojobsinvoicelinebatchesList where cancelby is greater than
        defaultAutojobsinvoicelinebatchesFiltering("cancelby.greaterThan=" + SMALLER_CANCELBY, "cancelby.greaterThan=" + DEFAULT_CANCELBY);
    }

    private void defaultAutojobsinvoicelinebatchesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAutojobsinvoicelinebatchesShouldBeFound(shouldBeFound);
        defaultAutojobsinvoicelinebatchesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutojobsinvoicelinebatchesShouldBeFound(String filter) throws Exception {
        restAutojobsinvoicelinebatchesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsinvoicelinebatches.getId())))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].batchlineid").value(hasItem(DEFAULT_BATCHLINEID)))
            .andExpect(jsonPath("$.[*].itemid").value(hasItem(DEFAULT_ITEMID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].batchid").value(hasItem(DEFAULT_BATCHID)))
            .andExpect(jsonPath("$.[*].batchcode").value(hasItem(DEFAULT_BATCHCODE)))
            .andExpect(jsonPath("$.[*].txdate").value(hasItem(DEFAULT_TXDATE.toString())))
            .andExpect(jsonPath("$.[*].manufacturedate").value(hasItem(DEFAULT_MANUFACTUREDATE.toString())))
            .andExpect(jsonPath("$.[*].expireddate").value(hasItem(DEFAULT_EXPIREDDATE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].issued").value(hasItem(DEFAULT_ISSUED.booleanValue())))
            .andExpect(jsonPath("$.[*].issuedby").value(hasItem(DEFAULT_ISSUEDBY)))
            .andExpect(jsonPath("$.[*].issueddatetime").value(hasItem(DEFAULT_ISSUEDDATETIME.toString())))
            .andExpect(jsonPath("$.[*].addedbyid").value(hasItem(DEFAULT_ADDEDBYID)))
            .andExpect(jsonPath("$.[*].canceloptid").value(hasItem(DEFAULT_CANCELOPTID)))
            .andExpect(jsonPath("$.[*].cancelopt").value(hasItem(DEFAULT_CANCELOPT)))
            .andExpect(jsonPath("$.[*].cancelby").value(hasItem(DEFAULT_CANCELBY)));

        // Check, that the count call also returns 1
        restAutojobsinvoicelinebatchesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutojobsinvoicelinebatchesShouldNotBeFound(String filter) throws Exception {
        restAutojobsinvoicelinebatchesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutojobsinvoicelinebatchesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutojobsinvoicelinebatches() throws Exception {
        // Get the autojobsinvoicelinebatches
        restAutojobsinvoicelinebatchesMockMvc.perform(get(ENTITY_API_URL_ID, Integer.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutojobsinvoicelinebatches() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelinebatches
        Autojobsinvoicelinebatches updatedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository
            .findById(autojobsinvoicelinebatches.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAutojobsinvoicelinebatches are not directly saved in db
        em.detach(updatedAutojobsinvoicelinebatches);
        updatedAutojobsinvoicelinebatches
            .lineid(UPDATED_LINEID)
            .batchlineid(UPDATED_BATCHLINEID)
            .itemid(UPDATED_ITEMID)
            .code(UPDATED_CODE)
            .batchid(UPDATED_BATCHID)
            .batchcode(UPDATED_BATCHCODE)
            .txdate(UPDATED_TXDATE)
            .manufacturedate(UPDATED_MANUFACTUREDATE)
            .expireddate(UPDATED_EXPIREDDATE)
            .qty(UPDATED_QTY)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT)
            .discount(UPDATED_DISCOUNT)
            .total(UPDATED_TOTAL)
            .issued(UPDATED_ISSUED)
            .issuedby(UPDATED_ISSUEDBY)
            .issueddatetime(UPDATED_ISSUEDDATETIME)
            .addedbyid(UPDATED_ADDEDBYID)
            .canceloptid(UPDATED_CANCELOPTID)
            .cancelopt(UPDATED_CANCELOPT)
            .cancelby(UPDATED_CANCELBY);

        restAutojobsinvoicelinebatchesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutojobsinvoicelinebatches.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutojobsinvoicelinebatches))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelinebatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutojobsinvoicelinebatchesToMatchAllProperties(updatedAutojobsinvoicelinebatches);
    }

    @Test
    @Transactional
    void putNonExistingAutojobsinvoicelinebatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelinebatches.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinebatchesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autojobsinvoicelinebatches.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsinvoicelinebatches))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelinebatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutojobsinvoicelinebatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelinebatches.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinebatchesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsinvoicelinebatches))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelinebatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutojobsinvoicelinebatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelinebatches.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinebatchesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelinebatches)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsinvoicelinebatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutojobsinvoicelinebatchesWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelinebatches using partial update
        Autojobsinvoicelinebatches partialUpdatedAutojobsinvoicelinebatches = new Autojobsinvoicelinebatches();
        partialUpdatedAutojobsinvoicelinebatches.setId(autojobsinvoicelinebatches.getId());

        partialUpdatedAutojobsinvoicelinebatches
            .batchlineid(UPDATED_BATCHLINEID)
            .batchcode(UPDATED_BATCHCODE)
            .manufacturedate(UPDATED_MANUFACTUREDATE)
            .expireddate(UPDATED_EXPIREDDATE)
            .price(UPDATED_PRICE)
            .vat(UPDATED_VAT)
            .discount(UPDATED_DISCOUNT)
            .total(UPDATED_TOTAL)
            .issued(UPDATED_ISSUED)
            .addedbyid(UPDATED_ADDEDBYID)
            .cancelopt(UPDATED_CANCELOPT);

        restAutojobsinvoicelinebatchesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsinvoicelinebatches.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsinvoicelinebatches))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelinebatches in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsinvoicelinebatchesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutojobsinvoicelinebatches, autojobsinvoicelinebatches),
            getPersistedAutojobsinvoicelinebatches(autojobsinvoicelinebatches)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutojobsinvoicelinebatchesWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelinebatches using partial update
        Autojobsinvoicelinebatches partialUpdatedAutojobsinvoicelinebatches = new Autojobsinvoicelinebatches();
        partialUpdatedAutojobsinvoicelinebatches.setId(autojobsinvoicelinebatches.getId());

        partialUpdatedAutojobsinvoicelinebatches
            .lineid(UPDATED_LINEID)
            .batchlineid(UPDATED_BATCHLINEID)
            .itemid(UPDATED_ITEMID)
            .code(UPDATED_CODE)
            .batchid(UPDATED_BATCHID)
            .batchcode(UPDATED_BATCHCODE)
            .txdate(UPDATED_TXDATE)
            .manufacturedate(UPDATED_MANUFACTUREDATE)
            .expireddate(UPDATED_EXPIREDDATE)
            .qty(UPDATED_QTY)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .notes(UPDATED_NOTES)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT)
            .discount(UPDATED_DISCOUNT)
            .total(UPDATED_TOTAL)
            .issued(UPDATED_ISSUED)
            .issuedby(UPDATED_ISSUEDBY)
            .issueddatetime(UPDATED_ISSUEDDATETIME)
            .addedbyid(UPDATED_ADDEDBYID)
            .canceloptid(UPDATED_CANCELOPTID)
            .cancelopt(UPDATED_CANCELOPT)
            .cancelby(UPDATED_CANCELBY);

        restAutojobsinvoicelinebatchesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsinvoicelinebatches.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsinvoicelinebatches))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelinebatches in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsinvoicelinebatchesUpdatableFieldsEquals(
            partialUpdatedAutojobsinvoicelinebatches,
            getPersistedAutojobsinvoicelinebatches(partialUpdatedAutojobsinvoicelinebatches)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutojobsinvoicelinebatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelinebatches.setId(intCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinebatchesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autojobsinvoicelinebatches.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsinvoicelinebatches))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelinebatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutojobsinvoicelinebatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelinebatches.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinebatchesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsinvoicelinebatches))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelinebatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutojobsinvoicelinebatches() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelinebatches.setId(intCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinebatchesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(autojobsinvoicelinebatches))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsinvoicelinebatches in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutojobsinvoicelinebatches() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelinebatches = autojobsinvoicelinebatchesRepository.saveAndFlush(autojobsinvoicelinebatches);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autojobsinvoicelinebatches
        restAutojobsinvoicelinebatchesMockMvc
            .perform(delete(ENTITY_API_URL_ID, autojobsinvoicelinebatches.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autojobsinvoicelinebatchesRepository.count();
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

    protected Autojobsinvoicelinebatches getPersistedAutojobsinvoicelinebatches(Autojobsinvoicelinebatches autojobsinvoicelinebatches) {
        return autojobsinvoicelinebatchesRepository.findById(autojobsinvoicelinebatches.getId()).orElseThrow();
    }

    protected void assertPersistedAutojobsinvoicelinebatchesToMatchAllProperties(
        Autojobsinvoicelinebatches expectedAutojobsinvoicelinebatches
    ) {
        assertAutojobsinvoicelinebatchesAllPropertiesEquals(
            expectedAutojobsinvoicelinebatches,
            getPersistedAutojobsinvoicelinebatches(expectedAutojobsinvoicelinebatches)
        );
    }

    protected void assertPersistedAutojobsinvoicelinebatchesToMatchUpdatableProperties(
        Autojobsinvoicelinebatches expectedAutojobsinvoicelinebatches
    ) {
        assertAutojobsinvoicelinebatchesAllUpdatablePropertiesEquals(
            expectedAutojobsinvoicelinebatches,
            getPersistedAutojobsinvoicelinebatches(expectedAutojobsinvoicelinebatches)
        );
    }
}
