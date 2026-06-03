package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummyAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SaleInvoiceCommonServiceChargeDummy;
import com.heavenscode.rac.repository.SaleInvoiceCommonServiceChargeDummyRepository;
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
 * Integration tests for the {@link SaleInvoiceCommonServiceChargeDummyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleInvoiceCommonServiceChargeDummyResourceIT {

    private static final Integer DEFAULT_INVOICEID = 1;
    private static final Integer UPDATED_INVOICEID = 2;
    private static final Integer SMALLER_INVOICEID = 1 - 1;

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;
    private static final Integer SMALLER_LINEID = 1 - 1;

    private static final Integer DEFAULT_OPTIONID = 1;
    private static final Integer UPDATED_OPTIONID = 2;
    private static final Integer SMALLER_OPTIONID = 1 - 1;

    private static final Integer DEFAULT_MAINID = 1;
    private static final Integer UPDATED_MAINID = 2;
    private static final Integer SMALLER_MAINID = 1 - 1;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;
    private static final Float SMALLER_VALUE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/sale-invoice-common-service-charge-dummies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SaleInvoiceCommonServiceChargeDummyRepository saleInvoiceCommonServiceChargeDummyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleInvoiceCommonServiceChargeDummyMockMvc;

    private SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy;

    private SaleInvoiceCommonServiceChargeDummy insertedSaleInvoiceCommonServiceChargeDummy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleInvoiceCommonServiceChargeDummy createEntity() {
        return new SaleInvoiceCommonServiceChargeDummy()
            .invoiceid(DEFAULT_INVOICEID)
            .lineid(DEFAULT_LINEID)
            .optionid(DEFAULT_OPTIONID)
            .mainid(DEFAULT_MAINID)
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaleInvoiceCommonServiceChargeDummy createUpdatedEntity() {
        return new SaleInvoiceCommonServiceChargeDummy()
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);
    }

    @BeforeEach
    public void initTest() {
        saleInvoiceCommonServiceChargeDummy = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSaleInvoiceCommonServiceChargeDummy != null) {
            saleInvoiceCommonServiceChargeDummyRepository.delete(insertedSaleInvoiceCommonServiceChargeDummy);
            insertedSaleInvoiceCommonServiceChargeDummy = null;
        }
    }

    @Test
    @Transactional
    void createSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SaleInvoiceCommonServiceChargeDummy
        var returnedSaleInvoiceCommonServiceChargeDummy = om.readValue(
            restSaleInvoiceCommonServiceChargeDummyMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SaleInvoiceCommonServiceChargeDummy.class
        );

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSaleInvoiceCommonServiceChargeDummyUpdatableFieldsEquals(
            returnedSaleInvoiceCommonServiceChargeDummy,
            getPersistedSaleInvoiceCommonServiceChargeDummy(returnedSaleInvoiceCommonServiceChargeDummy)
        );

        insertedSaleInvoiceCommonServiceChargeDummy = returnedSaleInvoiceCommonServiceChargeDummy;
    }

    @Test
    @Transactional
    void createSaleInvoiceCommonServiceChargeDummyWithExistingId() throws Exception {
        // Create the SaleInvoiceCommonServiceChargeDummy with an existing ID
        saleInvoiceCommonServiceChargeDummy.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummies() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleInvoiceCommonServiceChargeDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    void getSaleInvoiceCommonServiceChargeDummy() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get the saleInvoiceCommonServiceChargeDummy
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(get(ENTITY_API_URL_ID, saleInvoiceCommonServiceChargeDummy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saleInvoiceCommonServiceChargeDummy.getId().intValue()))
            .andExpect(jsonPath("$.invoiceid").value(DEFAULT_INVOICEID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.optionid").value(DEFAULT_OPTIONID))
            .andExpect(jsonPath("$.mainid").value(DEFAULT_MAINID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    void getSaleInvoiceCommonServiceChargeDummiesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        Long id = saleInvoiceCommonServiceChargeDummy.getId();

        defaultSaleInvoiceCommonServiceChargeDummyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSaleInvoiceCommonServiceChargeDummyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSaleInvoiceCommonServiceChargeDummyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByInvoiceidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where invoiceid equals to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "invoiceid.equals=" + DEFAULT_INVOICEID,
            "invoiceid.equals=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByInvoiceidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where invoiceid in
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "invoiceid.in=" + DEFAULT_INVOICEID + "," + UPDATED_INVOICEID,
            "invoiceid.in=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByInvoiceidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where invoiceid is not null
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("invoiceid.specified=true", "invoiceid.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByInvoiceidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where invoiceid is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "invoiceid.greaterThanOrEqual=" + DEFAULT_INVOICEID,
            "invoiceid.greaterThanOrEqual=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByInvoiceidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where invoiceid is less than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "invoiceid.lessThanOrEqual=" + DEFAULT_INVOICEID,
            "invoiceid.lessThanOrEqual=" + SMALLER_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByInvoiceidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where invoiceid is less than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "invoiceid.lessThan=" + UPDATED_INVOICEID,
            "invoiceid.lessThan=" + DEFAULT_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByInvoiceidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where invoiceid is greater than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "invoiceid.greaterThan=" + SMALLER_INVOICEID,
            "invoiceid.greaterThan=" + DEFAULT_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByLineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where lineid equals to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("lineid.equals=" + DEFAULT_LINEID, "lineid.equals=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByLineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where lineid in
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "lineid.in=" + DEFAULT_LINEID + "," + UPDATED_LINEID,
            "lineid.in=" + UPDATED_LINEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByLineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where lineid is not null
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("lineid.specified=true", "lineid.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByLineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where lineid is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "lineid.greaterThanOrEqual=" + DEFAULT_LINEID,
            "lineid.greaterThanOrEqual=" + UPDATED_LINEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByLineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where lineid is less than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "lineid.lessThanOrEqual=" + DEFAULT_LINEID,
            "lineid.lessThanOrEqual=" + SMALLER_LINEID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByLineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where lineid is less than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("lineid.lessThan=" + UPDATED_LINEID, "lineid.lessThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByLineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where lineid is greater than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("lineid.greaterThan=" + SMALLER_LINEID, "lineid.greaterThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByOptionidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where optionid equals to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("optionid.equals=" + DEFAULT_OPTIONID, "optionid.equals=" + UPDATED_OPTIONID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByOptionidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where optionid in
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "optionid.in=" + DEFAULT_OPTIONID + "," + UPDATED_OPTIONID,
            "optionid.in=" + UPDATED_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByOptionidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where optionid is not null
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("optionid.specified=true", "optionid.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByOptionidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where optionid is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "optionid.greaterThanOrEqual=" + DEFAULT_OPTIONID,
            "optionid.greaterThanOrEqual=" + UPDATED_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByOptionidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where optionid is less than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "optionid.lessThanOrEqual=" + DEFAULT_OPTIONID,
            "optionid.lessThanOrEqual=" + SMALLER_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByOptionidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where optionid is less than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "optionid.lessThan=" + UPDATED_OPTIONID,
            "optionid.lessThan=" + DEFAULT_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByOptionidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where optionid is greater than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "optionid.greaterThan=" + SMALLER_OPTIONID,
            "optionid.greaterThan=" + DEFAULT_OPTIONID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByMainidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where mainid equals to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("mainid.equals=" + DEFAULT_MAINID, "mainid.equals=" + UPDATED_MAINID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByMainidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where mainid in
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "mainid.in=" + DEFAULT_MAINID + "," + UPDATED_MAINID,
            "mainid.in=" + UPDATED_MAINID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByMainidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where mainid is not null
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("mainid.specified=true", "mainid.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByMainidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where mainid is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "mainid.greaterThanOrEqual=" + DEFAULT_MAINID,
            "mainid.greaterThanOrEqual=" + UPDATED_MAINID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByMainidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where mainid is less than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "mainid.lessThanOrEqual=" + DEFAULT_MAINID,
            "mainid.lessThanOrEqual=" + SMALLER_MAINID
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByMainidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where mainid is less than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("mainid.lessThan=" + UPDATED_MAINID, "mainid.lessThan=" + DEFAULT_MAINID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByMainidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where mainid is greater than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("mainid.greaterThan=" + SMALLER_MAINID, "mainid.greaterThan=" + DEFAULT_MAINID);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where code equals to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where code in
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where code is not null
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where code contains
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where code does not contain
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where name equals to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where name in
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where name is not null
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where name contains
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where name does not contain
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where description equals to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "description.equals=" + DEFAULT_DESCRIPTION,
            "description.equals=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where description in
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where description is not null
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where description contains
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "description.contains=" + DEFAULT_DESCRIPTION,
            "description.contains=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where description does not contain
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where value equals to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("value.equals=" + DEFAULT_VALUE, "value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where value in
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE, "value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where value is not null
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("value.specified=true", "value.specified=false");
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where value is greater than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "value.greaterThanOrEqual=" + DEFAULT_VALUE,
            "value.greaterThanOrEqual=" + UPDATED_VALUE
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where value is less than or equal to
        defaultSaleInvoiceCommonServiceChargeDummyFiltering(
            "value.lessThanOrEqual=" + DEFAULT_VALUE,
            "value.lessThanOrEqual=" + SMALLER_VALUE
        );
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where value is less than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("value.lessThan=" + UPDATED_VALUE, "value.lessThan=" + DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void getAllSaleInvoiceCommonServiceChargeDummiesByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        // Get all the saleInvoiceCommonServiceChargeDummyList where value is greater than
        defaultSaleInvoiceCommonServiceChargeDummyFiltering("value.greaterThan=" + SMALLER_VALUE, "value.greaterThan=" + DEFAULT_VALUE);
    }

    private void defaultSaleInvoiceCommonServiceChargeDummyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSaleInvoiceCommonServiceChargeDummyShouldBeFound(shouldBeFound);
        defaultSaleInvoiceCommonServiceChargeDummyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSaleInvoiceCommonServiceChargeDummyShouldBeFound(String filter) throws Exception {
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saleInvoiceCommonServiceChargeDummy.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].optionid").value(hasItem(DEFAULT_OPTIONID)))
            .andExpect(jsonPath("$.[*].mainid").value(hasItem(DEFAULT_MAINID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));

        // Check, that the count call also returns 1
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSaleInvoiceCommonServiceChargeDummyShouldNotBeFound(String filter) throws Exception {
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSaleInvoiceCommonServiceChargeDummy() throws Exception {
        // Get the saleInvoiceCommonServiceChargeDummy
        restSaleInvoiceCommonServiceChargeDummyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSaleInvoiceCommonServiceChargeDummy() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceChargeDummy
        SaleInvoiceCommonServiceChargeDummy updatedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository
            .findById(saleInvoiceCommonServiceChargeDummy.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSaleInvoiceCommonServiceChargeDummy are not directly saved in db
        em.detach(updatedSaleInvoiceCommonServiceChargeDummy);
        updatedSaleInvoiceCommonServiceChargeDummy
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);

        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSaleInvoiceCommonServiceChargeDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSaleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSaleInvoiceCommonServiceChargeDummyToMatchAllProperties(updatedSaleInvoiceCommonServiceChargeDummy);
    }

    @Test
    @Transactional
    void putNonExistingSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleInvoiceCommonServiceChargeDummy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleInvoiceCommonServiceChargeDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceChargeDummy using partial update
        SaleInvoiceCommonServiceChargeDummy partialUpdatedSaleInvoiceCommonServiceChargeDummy = new SaleInvoiceCommonServiceChargeDummy();
        partialUpdatedSaleInvoiceCommonServiceChargeDummy.setId(saleInvoiceCommonServiceChargeDummy.getId());

        partialUpdatedSaleInvoiceCommonServiceChargeDummy
            .invoiceid(UPDATED_INVOICEID)
            .optionid(UPDATED_OPTIONID)
            .code(UPDATED_CODE)
            .value(UPDATED_VALUE);

        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleInvoiceCommonServiceChargeDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaleInvoiceCommonServiceChargeDummyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSaleInvoiceCommonServiceChargeDummy, saleInvoiceCommonServiceChargeDummy),
            getPersistedSaleInvoiceCommonServiceChargeDummy(saleInvoiceCommonServiceChargeDummy)
        );
    }

    @Test
    @Transactional
    void fullUpdateSaleInvoiceCommonServiceChargeDummyWithPatch() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the saleInvoiceCommonServiceChargeDummy using partial update
        SaleInvoiceCommonServiceChargeDummy partialUpdatedSaleInvoiceCommonServiceChargeDummy = new SaleInvoiceCommonServiceChargeDummy();
        partialUpdatedSaleInvoiceCommonServiceChargeDummy.setId(saleInvoiceCommonServiceChargeDummy.getId());

        partialUpdatedSaleInvoiceCommonServiceChargeDummy
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .optionid(UPDATED_OPTIONID)
            .mainid(UPDATED_MAINID)
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);

        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaleInvoiceCommonServiceChargeDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSaleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isOk());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSaleInvoiceCommonServiceChargeDummyUpdatableFieldsEquals(
            partialUpdatedSaleInvoiceCommonServiceChargeDummy,
            getPersistedSaleInvoiceCommonServiceChargeDummy(partialUpdatedSaleInvoiceCommonServiceChargeDummy)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saleInvoiceCommonServiceChargeDummy.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaleInvoiceCommonServiceChargeDummy() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        saleInvoiceCommonServiceChargeDummy.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(saleInvoiceCommonServiceChargeDummy))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaleInvoiceCommonServiceChargeDummy in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaleInvoiceCommonServiceChargeDummy() throws Exception {
        // Initialize the database
        insertedSaleInvoiceCommonServiceChargeDummy = saleInvoiceCommonServiceChargeDummyRepository.saveAndFlush(
            saleInvoiceCommonServiceChargeDummy
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the saleInvoiceCommonServiceChargeDummy
        restSaleInvoiceCommonServiceChargeDummyMockMvc
            .perform(delete(ENTITY_API_URL_ID, saleInvoiceCommonServiceChargeDummy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return saleInvoiceCommonServiceChargeDummyRepository.count();
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

    protected SaleInvoiceCommonServiceChargeDummy getPersistedSaleInvoiceCommonServiceChargeDummy(
        SaleInvoiceCommonServiceChargeDummy saleInvoiceCommonServiceChargeDummy
    ) {
        return saleInvoiceCommonServiceChargeDummyRepository.findById(saleInvoiceCommonServiceChargeDummy.getId()).orElseThrow();
    }

    protected void assertPersistedSaleInvoiceCommonServiceChargeDummyToMatchAllProperties(
        SaleInvoiceCommonServiceChargeDummy expectedSaleInvoiceCommonServiceChargeDummy
    ) {
        assertSaleInvoiceCommonServiceChargeDummyAllPropertiesEquals(
            expectedSaleInvoiceCommonServiceChargeDummy,
            getPersistedSaleInvoiceCommonServiceChargeDummy(expectedSaleInvoiceCommonServiceChargeDummy)
        );
    }

    protected void assertPersistedSaleInvoiceCommonServiceChargeDummyToMatchUpdatableProperties(
        SaleInvoiceCommonServiceChargeDummy expectedSaleInvoiceCommonServiceChargeDummy
    ) {
        assertSaleInvoiceCommonServiceChargeDummyAllUpdatablePropertiesEquals(
            expectedSaleInvoiceCommonServiceChargeDummy,
            getPersistedSaleInvoiceCommonServiceChargeDummy(expectedSaleInvoiceCommonServiceChargeDummy)
        );
    }
}
