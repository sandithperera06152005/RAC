package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AutojobsinvoicelinesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Autojobsinvoicelines;
import com.heavenscode.rac.repository.AutojobsinvoicelinesRepository;
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
 * Integration tests for the {@link AutojobsinvoicelinesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutojobsinvoicelinesResourceIT {

    private static final Integer DEFAULT_INVOCIEID = 1;
    private static final Integer UPDATED_INVOCIEID = 2;
    private static final Integer SMALLER_INVOCIEID = 1 - 1;

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;
    private static final Integer SMALLER_LINEID = 1 - 1;

    private static final Integer DEFAULT_ITEMID = 1;
    private static final Integer UPDATED_ITEMID = 2;
    private static final Integer SMALLER_ITEMID = 1 - 1;

    private static final String DEFAULT_ITEMCODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEMCODE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEMNAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEMNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_UNITOFMEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_UNITOFMEASUREMENT = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTITY = 1F;
    private static final Float UPDATED_QUANTITY = 2F;
    private static final Float SMALLER_QUANTITY = 1F - 1F;

    private static final Float DEFAULT_ITEMCOST = 1F;
    private static final Float UPDATED_ITEMCOST = 2F;
    private static final Float SMALLER_ITEMCOST = 1F - 1F;

    private static final Float DEFAULT_ITEMPRICE = 1F;
    private static final Float UPDATED_ITEMPRICE = 2F;
    private static final Float SMALLER_ITEMPRICE = 1F - 1F;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;
    private static final Float SMALLER_DISCOUNT = 1F - 1F;

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;
    private static final Float SMALLER_TAX = 1F - 1F;

    private static final Float DEFAULT_SELLINGPRICE = 1F;
    private static final Float UPDATED_SELLINGPRICE = 2F;
    private static final Float SMALLER_SELLINGPRICE = 1F - 1F;

    private static final Float DEFAULT_LINETOTAL = 1F;
    private static final Float UPDATED_LINETOTAL = 2F;
    private static final Float SMALLER_LINETOTAL = 1F - 1F;

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_NBT = false;
    private static final Boolean UPDATED_NBT = true;

    private static final Boolean DEFAULT_VAT = false;
    private static final Boolean UPDATED_VAT = true;

    private static final String ENTITY_API_URL = "/api/autojobsinvoicelines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AutojobsinvoicelinesRepository autojobsinvoicelinesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutojobsinvoicelinesMockMvc;

    private Autojobsinvoicelines autojobsinvoicelines;

    private Autojobsinvoicelines insertedAutojobsinvoicelines;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autojobsinvoicelines createEntity() {
        return new Autojobsinvoicelines()
            .invocieid(DEFAULT_INVOCIEID)
            .lineid(DEFAULT_LINEID)
            .itemid(DEFAULT_ITEMID)
            .itemcode(DEFAULT_ITEMCODE)
            .itemname(DEFAULT_ITEMNAME)
            .description(DEFAULT_DESCRIPTION)
            .unitofmeasurement(DEFAULT_UNITOFMEASUREMENT)
            .quantity(DEFAULT_QUANTITY)
            .itemcost(DEFAULT_ITEMCOST)
            .itemprice(DEFAULT_ITEMPRICE)
            .discount(DEFAULT_DISCOUNT)
            .tax(DEFAULT_TAX)
            .sellingprice(DEFAULT_SELLINGPRICE)
            .linetotal(DEFAULT_LINETOTAL)
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
    public static Autojobsinvoicelines createUpdatedEntity() {
        return new Autojobsinvoicelines()
            .invocieid(UPDATED_INVOCIEID)
            .lineid(UPDATED_LINEID)
            .itemid(UPDATED_ITEMID)
            .itemcode(UPDATED_ITEMCODE)
            .itemname(UPDATED_ITEMNAME)
            .description(UPDATED_DESCRIPTION)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemcost(UPDATED_ITEMCOST)
            .itemprice(UPDATED_ITEMPRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingprice(UPDATED_SELLINGPRICE)
            .linetotal(UPDATED_LINETOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);
    }

    @BeforeEach
    public void initTest() {
        autojobsinvoicelines = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAutojobsinvoicelines != null) {
            autojobsinvoicelinesRepository.delete(insertedAutojobsinvoicelines);
            insertedAutojobsinvoicelines = null;
        }
    }

    @Test
    @Transactional
    void createAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Autojobsinvoicelines
        var returnedAutojobsinvoicelines = om.readValue(
            restAutojobsinvoicelinesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelines)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Autojobsinvoicelines.class
        );

        // Validate the Autojobsinvoicelines in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAutojobsinvoicelinesUpdatableFieldsEquals(
            returnedAutojobsinvoicelines,
            getPersistedAutojobsinvoicelines(returnedAutojobsinvoicelines)
        );

        insertedAutojobsinvoicelines = returnedAutojobsinvoicelines;
    }

    @Test
    @Transactional
    void createAutojobsinvoicelinesWithExistingId() throws Exception {
        // Create the Autojobsinvoicelines with an existing ID
        autojobsinvoicelines.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutojobsinvoicelinesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelines)))
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelines() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList
        restAutojobsinvoicelinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsinvoicelines.getId().intValue())))
            .andExpect(jsonPath("$.[*].invocieid").value(hasItem(DEFAULT_INVOCIEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].itemid").value(hasItem(DEFAULT_ITEMID)))
            .andExpect(jsonPath("$.[*].itemcode").value(hasItem(DEFAULT_ITEMCODE)))
            .andExpect(jsonPath("$.[*].itemname").value(hasItem(DEFAULT_ITEMNAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].unitofmeasurement").value(hasItem(DEFAULT_UNITOFMEASUREMENT)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].itemcost").value(hasItem(DEFAULT_ITEMCOST.doubleValue())))
            .andExpect(jsonPath("$.[*].itemprice").value(hasItem(DEFAULT_ITEMPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingprice").value(hasItem(DEFAULT_SELLINGPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].linetotal").value(hasItem(DEFAULT_LINETOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())));
    }

    @Test
    @Transactional
    void getAutojobsinvoicelines() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get the autojobsinvoicelines
        restAutojobsinvoicelinesMockMvc
            .perform(get(ENTITY_API_URL_ID, autojobsinvoicelines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autojobsinvoicelines.getId().intValue()))
            .andExpect(jsonPath("$.invocieid").value(DEFAULT_INVOCIEID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.itemid").value(DEFAULT_ITEMID))
            .andExpect(jsonPath("$.itemcode").value(DEFAULT_ITEMCODE))
            .andExpect(jsonPath("$.itemname").value(DEFAULT_ITEMNAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.unitofmeasurement").value(DEFAULT_UNITOFMEASUREMENT))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.itemcost").value(DEFAULT_ITEMCOST.doubleValue()))
            .andExpect(jsonPath("$.itemprice").value(DEFAULT_ITEMPRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.sellingprice").value(DEFAULT_SELLINGPRICE.doubleValue()))
            .andExpect(jsonPath("$.linetotal").value(DEFAULT_LINETOTAL.doubleValue()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.nbt").value(DEFAULT_NBT.booleanValue()))
            .andExpect(jsonPath("$.vat").value(DEFAULT_VAT.booleanValue()));
    }

    @Test
    @Transactional
    void getAutojobsinvoicelinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        Long id = autojobsinvoicelines.getId();

        defaultAutojobsinvoicelinesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAutojobsinvoicelinesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAutojobsinvoicelinesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByInvocieidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where invocieid equals to
        defaultAutojobsinvoicelinesFiltering("invocieid.equals=" + DEFAULT_INVOCIEID, "invocieid.equals=" + UPDATED_INVOCIEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByInvocieidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where invocieid in
        defaultAutojobsinvoicelinesFiltering(
            "invocieid.in=" + DEFAULT_INVOCIEID + "," + UPDATED_INVOCIEID,
            "invocieid.in=" + UPDATED_INVOCIEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByInvocieidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where invocieid is not null
        defaultAutojobsinvoicelinesFiltering("invocieid.specified=true", "invocieid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByInvocieidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where invocieid is greater than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "invocieid.greaterThanOrEqual=" + DEFAULT_INVOCIEID,
            "invocieid.greaterThanOrEqual=" + UPDATED_INVOCIEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByInvocieidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where invocieid is less than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "invocieid.lessThanOrEqual=" + DEFAULT_INVOCIEID,
            "invocieid.lessThanOrEqual=" + SMALLER_INVOCIEID
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByInvocieidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where invocieid is less than
        defaultAutojobsinvoicelinesFiltering("invocieid.lessThan=" + UPDATED_INVOCIEID, "invocieid.lessThan=" + DEFAULT_INVOCIEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByInvocieidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where invocieid is greater than
        defaultAutojobsinvoicelinesFiltering("invocieid.greaterThan=" + SMALLER_INVOCIEID, "invocieid.greaterThan=" + DEFAULT_INVOCIEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lineid equals to
        defaultAutojobsinvoicelinesFiltering("lineid.equals=" + DEFAULT_LINEID, "lineid.equals=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lineid in
        defaultAutojobsinvoicelinesFiltering("lineid.in=" + DEFAULT_LINEID + "," + UPDATED_LINEID, "lineid.in=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lineid is not null
        defaultAutojobsinvoicelinesFiltering("lineid.specified=true", "lineid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lineid is greater than or equal to
        defaultAutojobsinvoicelinesFiltering("lineid.greaterThanOrEqual=" + DEFAULT_LINEID, "lineid.greaterThanOrEqual=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lineid is less than or equal to
        defaultAutojobsinvoicelinesFiltering("lineid.lessThanOrEqual=" + DEFAULT_LINEID, "lineid.lessThanOrEqual=" + SMALLER_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lineid is less than
        defaultAutojobsinvoicelinesFiltering("lineid.lessThan=" + UPDATED_LINEID, "lineid.lessThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lineid is greater than
        defaultAutojobsinvoicelinesFiltering("lineid.greaterThan=" + SMALLER_LINEID, "lineid.greaterThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemid equals to
        defaultAutojobsinvoicelinesFiltering("itemid.equals=" + DEFAULT_ITEMID, "itemid.equals=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemid in
        defaultAutojobsinvoicelinesFiltering("itemid.in=" + DEFAULT_ITEMID + "," + UPDATED_ITEMID, "itemid.in=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemid is not null
        defaultAutojobsinvoicelinesFiltering("itemid.specified=true", "itemid.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemid is greater than or equal to
        defaultAutojobsinvoicelinesFiltering("itemid.greaterThanOrEqual=" + DEFAULT_ITEMID, "itemid.greaterThanOrEqual=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemid is less than or equal to
        defaultAutojobsinvoicelinesFiltering("itemid.lessThanOrEqual=" + DEFAULT_ITEMID, "itemid.lessThanOrEqual=" + SMALLER_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemid is less than
        defaultAutojobsinvoicelinesFiltering("itemid.lessThan=" + UPDATED_ITEMID, "itemid.lessThan=" + DEFAULT_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemid is greater than
        defaultAutojobsinvoicelinesFiltering("itemid.greaterThan=" + SMALLER_ITEMID, "itemid.greaterThan=" + DEFAULT_ITEMID);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcode equals to
        defaultAutojobsinvoicelinesFiltering("itemcode.equals=" + DEFAULT_ITEMCODE, "itemcode.equals=" + UPDATED_ITEMCODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcode in
        defaultAutojobsinvoicelinesFiltering("itemcode.in=" + DEFAULT_ITEMCODE + "," + UPDATED_ITEMCODE, "itemcode.in=" + UPDATED_ITEMCODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcode is not null
        defaultAutojobsinvoicelinesFiltering("itemcode.specified=true", "itemcode.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcode contains
        defaultAutojobsinvoicelinesFiltering("itemcode.contains=" + DEFAULT_ITEMCODE, "itemcode.contains=" + UPDATED_ITEMCODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcode does not contain
        defaultAutojobsinvoicelinesFiltering("itemcode.doesNotContain=" + UPDATED_ITEMCODE, "itemcode.doesNotContain=" + DEFAULT_ITEMCODE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemnameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemname equals to
        defaultAutojobsinvoicelinesFiltering("itemname.equals=" + DEFAULT_ITEMNAME, "itemname.equals=" + UPDATED_ITEMNAME);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemnameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemname in
        defaultAutojobsinvoicelinesFiltering("itemname.in=" + DEFAULT_ITEMNAME + "," + UPDATED_ITEMNAME, "itemname.in=" + UPDATED_ITEMNAME);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemname is not null
        defaultAutojobsinvoicelinesFiltering("itemname.specified=true", "itemname.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemnameContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemname contains
        defaultAutojobsinvoicelinesFiltering("itemname.contains=" + DEFAULT_ITEMNAME, "itemname.contains=" + UPDATED_ITEMNAME);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemnameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemname does not contain
        defaultAutojobsinvoicelinesFiltering("itemname.doesNotContain=" + UPDATED_ITEMNAME, "itemname.doesNotContain=" + DEFAULT_ITEMNAME);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where description equals to
        defaultAutojobsinvoicelinesFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where description in
        defaultAutojobsinvoicelinesFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where description is not null
        defaultAutojobsinvoicelinesFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where description contains
        defaultAutojobsinvoicelinesFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where description does not contain
        defaultAutojobsinvoicelinesFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByUnitofmeasurementIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where unitofmeasurement equals to
        defaultAutojobsinvoicelinesFiltering(
            "unitofmeasurement.equals=" + DEFAULT_UNITOFMEASUREMENT,
            "unitofmeasurement.equals=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByUnitofmeasurementIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where unitofmeasurement in
        defaultAutojobsinvoicelinesFiltering(
            "unitofmeasurement.in=" + DEFAULT_UNITOFMEASUREMENT + "," + UPDATED_UNITOFMEASUREMENT,
            "unitofmeasurement.in=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByUnitofmeasurementIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where unitofmeasurement is not null
        defaultAutojobsinvoicelinesFiltering("unitofmeasurement.specified=true", "unitofmeasurement.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByUnitofmeasurementContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where unitofmeasurement contains
        defaultAutojobsinvoicelinesFiltering(
            "unitofmeasurement.contains=" + DEFAULT_UNITOFMEASUREMENT,
            "unitofmeasurement.contains=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByUnitofmeasurementNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where unitofmeasurement does not contain
        defaultAutojobsinvoicelinesFiltering(
            "unitofmeasurement.doesNotContain=" + UPDATED_UNITOFMEASUREMENT,
            "unitofmeasurement.doesNotContain=" + DEFAULT_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where quantity equals to
        defaultAutojobsinvoicelinesFiltering("quantity.equals=" + DEFAULT_QUANTITY, "quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where quantity in
        defaultAutojobsinvoicelinesFiltering("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY, "quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where quantity is not null
        defaultAutojobsinvoicelinesFiltering("quantity.specified=true", "quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where quantity is greater than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.greaterThanOrEqual=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where quantity is less than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "quantity.lessThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.lessThanOrEqual=" + SMALLER_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where quantity is less than
        defaultAutojobsinvoicelinesFiltering("quantity.lessThan=" + UPDATED_QUANTITY, "quantity.lessThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where quantity is greater than
        defaultAutojobsinvoicelinesFiltering("quantity.greaterThan=" + SMALLER_QUANTITY, "quantity.greaterThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcost equals to
        defaultAutojobsinvoicelinesFiltering("itemcost.equals=" + DEFAULT_ITEMCOST, "itemcost.equals=" + UPDATED_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcost in
        defaultAutojobsinvoicelinesFiltering("itemcost.in=" + DEFAULT_ITEMCOST + "," + UPDATED_ITEMCOST, "itemcost.in=" + UPDATED_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcost is not null
        defaultAutojobsinvoicelinesFiltering("itemcost.specified=true", "itemcost.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcost is greater than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "itemcost.greaterThanOrEqual=" + DEFAULT_ITEMCOST,
            "itemcost.greaterThanOrEqual=" + UPDATED_ITEMCOST
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcost is less than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "itemcost.lessThanOrEqual=" + DEFAULT_ITEMCOST,
            "itemcost.lessThanOrEqual=" + SMALLER_ITEMCOST
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcost is less than
        defaultAutojobsinvoicelinesFiltering("itemcost.lessThan=" + UPDATED_ITEMCOST, "itemcost.lessThan=" + DEFAULT_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItemcostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemcost is greater than
        defaultAutojobsinvoicelinesFiltering("itemcost.greaterThan=" + SMALLER_ITEMCOST, "itemcost.greaterThan=" + DEFAULT_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItempriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemprice equals to
        defaultAutojobsinvoicelinesFiltering("itemprice.equals=" + DEFAULT_ITEMPRICE, "itemprice.equals=" + UPDATED_ITEMPRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItempriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemprice in
        defaultAutojobsinvoicelinesFiltering(
            "itemprice.in=" + DEFAULT_ITEMPRICE + "," + UPDATED_ITEMPRICE,
            "itemprice.in=" + UPDATED_ITEMPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItempriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemprice is not null
        defaultAutojobsinvoicelinesFiltering("itemprice.specified=true", "itemprice.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItempriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemprice is greater than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "itemprice.greaterThanOrEqual=" + DEFAULT_ITEMPRICE,
            "itemprice.greaterThanOrEqual=" + UPDATED_ITEMPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItempriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemprice is less than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "itemprice.lessThanOrEqual=" + DEFAULT_ITEMPRICE,
            "itemprice.lessThanOrEqual=" + SMALLER_ITEMPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItempriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemprice is less than
        defaultAutojobsinvoicelinesFiltering("itemprice.lessThan=" + UPDATED_ITEMPRICE, "itemprice.lessThan=" + DEFAULT_ITEMPRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByItempriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where itemprice is greater than
        defaultAutojobsinvoicelinesFiltering("itemprice.greaterThan=" + SMALLER_ITEMPRICE, "itemprice.greaterThan=" + DEFAULT_ITEMPRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where discount equals to
        defaultAutojobsinvoicelinesFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where discount in
        defaultAutojobsinvoicelinesFiltering("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT, "discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where discount is not null
        defaultAutojobsinvoicelinesFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where discount is greater than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where discount is less than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "discount.lessThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.lessThanOrEqual=" + SMALLER_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where discount is less than
        defaultAutojobsinvoicelinesFiltering("discount.lessThan=" + UPDATED_DISCOUNT, "discount.lessThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where discount is greater than
        defaultAutojobsinvoicelinesFiltering("discount.greaterThan=" + SMALLER_DISCOUNT, "discount.greaterThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where tax equals to
        defaultAutojobsinvoicelinesFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where tax in
        defaultAutojobsinvoicelinesFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where tax is not null
        defaultAutojobsinvoicelinesFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where tax is greater than or equal to
        defaultAutojobsinvoicelinesFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where tax is less than or equal to
        defaultAutojobsinvoicelinesFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where tax is less than
        defaultAutojobsinvoicelinesFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where tax is greater than
        defaultAutojobsinvoicelinesFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesBySellingpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where sellingprice equals to
        defaultAutojobsinvoicelinesFiltering("sellingprice.equals=" + DEFAULT_SELLINGPRICE, "sellingprice.equals=" + UPDATED_SELLINGPRICE);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesBySellingpriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where sellingprice in
        defaultAutojobsinvoicelinesFiltering(
            "sellingprice.in=" + DEFAULT_SELLINGPRICE + "," + UPDATED_SELLINGPRICE,
            "sellingprice.in=" + UPDATED_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesBySellingpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where sellingprice is not null
        defaultAutojobsinvoicelinesFiltering("sellingprice.specified=true", "sellingprice.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesBySellingpriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where sellingprice is greater than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "sellingprice.greaterThanOrEqual=" + DEFAULT_SELLINGPRICE,
            "sellingprice.greaterThanOrEqual=" + UPDATED_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesBySellingpriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where sellingprice is less than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "sellingprice.lessThanOrEqual=" + DEFAULT_SELLINGPRICE,
            "sellingprice.lessThanOrEqual=" + SMALLER_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesBySellingpriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where sellingprice is less than
        defaultAutojobsinvoicelinesFiltering(
            "sellingprice.lessThan=" + UPDATED_SELLINGPRICE,
            "sellingprice.lessThan=" + DEFAULT_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesBySellingpriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where sellingprice is greater than
        defaultAutojobsinvoicelinesFiltering(
            "sellingprice.greaterThan=" + SMALLER_SELLINGPRICE,
            "sellingprice.greaterThan=" + DEFAULT_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLinetotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where linetotal equals to
        defaultAutojobsinvoicelinesFiltering("linetotal.equals=" + DEFAULT_LINETOTAL, "linetotal.equals=" + UPDATED_LINETOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLinetotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where linetotal in
        defaultAutojobsinvoicelinesFiltering(
            "linetotal.in=" + DEFAULT_LINETOTAL + "," + UPDATED_LINETOTAL,
            "linetotal.in=" + UPDATED_LINETOTAL
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLinetotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where linetotal is not null
        defaultAutojobsinvoicelinesFiltering("linetotal.specified=true", "linetotal.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLinetotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where linetotal is greater than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "linetotal.greaterThanOrEqual=" + DEFAULT_LINETOTAL,
            "linetotal.greaterThanOrEqual=" + UPDATED_LINETOTAL
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLinetotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where linetotal is less than or equal to
        defaultAutojobsinvoicelinesFiltering(
            "linetotal.lessThanOrEqual=" + DEFAULT_LINETOTAL,
            "linetotal.lessThanOrEqual=" + SMALLER_LINETOTAL
        );
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLinetotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where linetotal is less than
        defaultAutojobsinvoicelinesFiltering("linetotal.lessThan=" + UPDATED_LINETOTAL, "linetotal.lessThan=" + DEFAULT_LINETOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLinetotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where linetotal is greater than
        defaultAutojobsinvoicelinesFiltering("linetotal.greaterThan=" + SMALLER_LINETOTAL, "linetotal.greaterThan=" + DEFAULT_LINETOTAL);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmu equals to
        defaultAutojobsinvoicelinesFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmu in
        defaultAutojobsinvoicelinesFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmu is not null
        defaultAutojobsinvoicelinesFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmu is greater than or equal to
        defaultAutojobsinvoicelinesFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmu is less than or equal to
        defaultAutojobsinvoicelinesFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmu is less than
        defaultAutojobsinvoicelinesFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmu is greater than
        defaultAutojobsinvoicelinesFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmd equals to
        defaultAutojobsinvoicelinesFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmd in
        defaultAutojobsinvoicelinesFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where lmd is not null
        defaultAutojobsinvoicelinesFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByNbtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where nbt equals to
        defaultAutojobsinvoicelinesFiltering("nbt.equals=" + DEFAULT_NBT, "nbt.equals=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByNbtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where nbt in
        defaultAutojobsinvoicelinesFiltering("nbt.in=" + DEFAULT_NBT + "," + UPDATED_NBT, "nbt.in=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByNbtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where nbt is not null
        defaultAutojobsinvoicelinesFiltering("nbt.specified=true", "nbt.specified=false");
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByVatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where vat equals to
        defaultAutojobsinvoicelinesFiltering("vat.equals=" + DEFAULT_VAT, "vat.equals=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByVatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where vat in
        defaultAutojobsinvoicelinesFiltering("vat.in=" + DEFAULT_VAT + "," + UPDATED_VAT, "vat.in=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllAutojobsinvoicelinesByVatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        // Get all the autojobsinvoicelinesList where vat is not null
        defaultAutojobsinvoicelinesFiltering("vat.specified=true", "vat.specified=false");
    }

    private void defaultAutojobsinvoicelinesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAutojobsinvoicelinesShouldBeFound(shouldBeFound);
        defaultAutojobsinvoicelinesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutojobsinvoicelinesShouldBeFound(String filter) throws Exception {
        restAutojobsinvoicelinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autojobsinvoicelines.getId().intValue())))
            .andExpect(jsonPath("$.[*].invocieid").value(hasItem(DEFAULT_INVOCIEID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].itemid").value(hasItem(DEFAULT_ITEMID)))
            .andExpect(jsonPath("$.[*].itemcode").value(hasItem(DEFAULT_ITEMCODE)))
            .andExpect(jsonPath("$.[*].itemname").value(hasItem(DEFAULT_ITEMNAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].unitofmeasurement").value(hasItem(DEFAULT_UNITOFMEASUREMENT)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].itemcost").value(hasItem(DEFAULT_ITEMCOST.doubleValue())))
            .andExpect(jsonPath("$.[*].itemprice").value(hasItem(DEFAULT_ITEMPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingprice").value(hasItem(DEFAULT_SELLINGPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].linetotal").value(hasItem(DEFAULT_LINETOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].nbt").value(hasItem(DEFAULT_NBT.booleanValue())))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(DEFAULT_VAT.booleanValue())));

        // Check, that the count call also returns 1
        restAutojobsinvoicelinesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutojobsinvoicelinesShouldNotBeFound(String filter) throws Exception {
        restAutojobsinvoicelinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutojobsinvoicelinesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutojobsinvoicelines() throws Exception {
        // Get the autojobsinvoicelines
        restAutojobsinvoicelinesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutojobsinvoicelines() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelines
        Autojobsinvoicelines updatedAutojobsinvoicelines = autojobsinvoicelinesRepository
            .findById(autojobsinvoicelines.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAutojobsinvoicelines are not directly saved in db
        em.detach(updatedAutojobsinvoicelines);
        updatedAutojobsinvoicelines
            .invocieid(UPDATED_INVOCIEID)
            .lineid(UPDATED_LINEID)
            .itemid(UPDATED_ITEMID)
            .itemcode(UPDATED_ITEMCODE)
            .itemname(UPDATED_ITEMNAME)
            .description(UPDATED_DESCRIPTION)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemcost(UPDATED_ITEMCOST)
            .itemprice(UPDATED_ITEMPRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingprice(UPDATED_SELLINGPRICE)
            .linetotal(UPDATED_LINETOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);

        restAutojobsinvoicelinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAutojobsinvoicelines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAutojobsinvoicelines))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAutojobsinvoicelinesToMatchAllProperties(updatedAutojobsinvoicelines);
    }

    @Test
    @Transactional
    void putNonExistingAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autojobsinvoicelines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsinvoicelines))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(autojobsinvoicelines))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(autojobsinvoicelines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutojobsinvoicelinesWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelines using partial update
        Autojobsinvoicelines partialUpdatedAutojobsinvoicelines = new Autojobsinvoicelines();
        partialUpdatedAutojobsinvoicelines.setId(autojobsinvoicelines.getId());

        partialUpdatedAutojobsinvoicelines
            .lineid(UPDATED_LINEID)
            .itemname(UPDATED_ITEMNAME)
            .description(UPDATED_DESCRIPTION)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX);

        restAutojobsinvoicelinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsinvoicelines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsinvoicelines))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsinvoicelinesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAutojobsinvoicelines, autojobsinvoicelines),
            getPersistedAutojobsinvoicelines(autojobsinvoicelines)
        );
    }

    @Test
    @Transactional
    void fullUpdateAutojobsinvoicelinesWithPatch() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the autojobsinvoicelines using partial update
        Autojobsinvoicelines partialUpdatedAutojobsinvoicelines = new Autojobsinvoicelines();
        partialUpdatedAutojobsinvoicelines.setId(autojobsinvoicelines.getId());

        partialUpdatedAutojobsinvoicelines
            .invocieid(UPDATED_INVOCIEID)
            .lineid(UPDATED_LINEID)
            .itemid(UPDATED_ITEMID)
            .itemcode(UPDATED_ITEMCODE)
            .itemname(UPDATED_ITEMNAME)
            .description(UPDATED_DESCRIPTION)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .quantity(UPDATED_QUANTITY)
            .itemcost(UPDATED_ITEMCOST)
            .itemprice(UPDATED_ITEMPRICE)
            .discount(UPDATED_DISCOUNT)
            .tax(UPDATED_TAX)
            .sellingprice(UPDATED_SELLINGPRICE)
            .linetotal(UPDATED_LINETOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .nbt(UPDATED_NBT)
            .vat(UPDATED_VAT);

        restAutojobsinvoicelinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutojobsinvoicelines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAutojobsinvoicelines))
            )
            .andExpect(status().isOk());

        // Validate the Autojobsinvoicelines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAutojobsinvoicelinesUpdatableFieldsEquals(
            partialUpdatedAutojobsinvoicelines,
            getPersistedAutojobsinvoicelines(partialUpdatedAutojobsinvoicelines)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autojobsinvoicelines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsinvoicelines))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(autojobsinvoicelines))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutojobsinvoicelines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        autojobsinvoicelines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutojobsinvoicelinesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(autojobsinvoicelines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autojobsinvoicelines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutojobsinvoicelines() throws Exception {
        // Initialize the database
        insertedAutojobsinvoicelines = autojobsinvoicelinesRepository.saveAndFlush(autojobsinvoicelines);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the autojobsinvoicelines
        restAutojobsinvoicelinesMockMvc
            .perform(delete(ENTITY_API_URL_ID, autojobsinvoicelines.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return autojobsinvoicelinesRepository.count();
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

    protected Autojobsinvoicelines getPersistedAutojobsinvoicelines(Autojobsinvoicelines autojobsinvoicelines) {
        return autojobsinvoicelinesRepository.findById(autojobsinvoicelines.getId()).orElseThrow();
    }

    protected void assertPersistedAutojobsinvoicelinesToMatchAllProperties(Autojobsinvoicelines expectedAutojobsinvoicelines) {
        assertAutojobsinvoicelinesAllPropertiesEquals(
            expectedAutojobsinvoicelines,
            getPersistedAutojobsinvoicelines(expectedAutojobsinvoicelines)
        );
    }

    protected void assertPersistedAutojobsinvoicelinesToMatchUpdatableProperties(Autojobsinvoicelines expectedAutojobsinvoicelines) {
        assertAutojobsinvoicelinesAllUpdatablePropertiesEquals(
            expectedAutojobsinvoicelines,
            getPersistedAutojobsinvoicelines(expectedAutojobsinvoicelines)
        );
    }
}
