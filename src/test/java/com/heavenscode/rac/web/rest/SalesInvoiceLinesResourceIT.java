package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SalesInvoiceLinesAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.SalesInvoiceLines;
import com.heavenscode.rac.repository.SalesInvoiceLinesRepository;
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
 * Integration tests for the {@link SalesInvoiceLinesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesInvoiceLinesResourceIT {

    private static final Integer DEFAULT_INVOICEID = 1;
    private static final Integer UPDATED_INVOICEID = 2;
    private static final Integer SMALLER_INVOICEID = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/sales-invoice-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SalesInvoiceLinesRepository salesInvoiceLinesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesInvoiceLinesMockMvc;

    private SalesInvoiceLines salesInvoiceLines;

    private SalesInvoiceLines insertedSalesInvoiceLines;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesInvoiceLines createEntity() {
        return new SalesInvoiceLines()
            .invoiceid(DEFAULT_INVOICEID)
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
    public static SalesInvoiceLines createUpdatedEntity() {
        return new SalesInvoiceLines()
            .invoiceid(UPDATED_INVOICEID)
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
        salesInvoiceLines = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSalesInvoiceLines != null) {
            salesInvoiceLinesRepository.delete(insertedSalesInvoiceLines);
            insertedSalesInvoiceLines = null;
        }
    }

    @Test
    @Transactional
    void createSalesInvoiceLines() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SalesInvoiceLines
        var returnedSalesInvoiceLines = om.readValue(
            restSalesInvoiceLinesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLines)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SalesInvoiceLines.class
        );

        // Validate the SalesInvoiceLines in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSalesInvoiceLinesUpdatableFieldsEquals(returnedSalesInvoiceLines, getPersistedSalesInvoiceLines(returnedSalesInvoiceLines));

        insertedSalesInvoiceLines = returnedSalesInvoiceLines;
    }

    @Test
    @Transactional
    void createSalesInvoiceLinesWithExistingId() throws Exception {
        // Create the SalesInvoiceLines with an existing ID
        salesInvoiceLines.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesInvoiceLinesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLines)))
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLines in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLines() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList
        restSalesInvoiceLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
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
    void getSalesInvoiceLines() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get the salesInvoiceLines
        restSalesInvoiceLinesMockMvc
            .perform(get(ENTITY_API_URL_ID, salesInvoiceLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesInvoiceLines.getId().intValue()))
            .andExpect(jsonPath("$.invoiceid").value(DEFAULT_INVOICEID))
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
    void getSalesInvoiceLinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        Long id = salesInvoiceLines.getId();

        defaultSalesInvoiceLinesFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSalesInvoiceLinesFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSalesInvoiceLinesFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByInvoiceidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where invoiceid equals to
        defaultSalesInvoiceLinesFiltering("invoiceid.equals=" + DEFAULT_INVOICEID, "invoiceid.equals=" + UPDATED_INVOICEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByInvoiceidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where invoiceid in
        defaultSalesInvoiceLinesFiltering(
            "invoiceid.in=" + DEFAULT_INVOICEID + "," + UPDATED_INVOICEID,
            "invoiceid.in=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByInvoiceidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where invoiceid is not null
        defaultSalesInvoiceLinesFiltering("invoiceid.specified=true", "invoiceid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByInvoiceidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where invoiceid is greater than or equal to
        defaultSalesInvoiceLinesFiltering(
            "invoiceid.greaterThanOrEqual=" + DEFAULT_INVOICEID,
            "invoiceid.greaterThanOrEqual=" + UPDATED_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByInvoiceidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where invoiceid is less than or equal to
        defaultSalesInvoiceLinesFiltering(
            "invoiceid.lessThanOrEqual=" + DEFAULT_INVOICEID,
            "invoiceid.lessThanOrEqual=" + SMALLER_INVOICEID
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByInvoiceidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where invoiceid is less than
        defaultSalesInvoiceLinesFiltering("invoiceid.lessThan=" + UPDATED_INVOICEID, "invoiceid.lessThan=" + DEFAULT_INVOICEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByInvoiceidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where invoiceid is greater than
        defaultSalesInvoiceLinesFiltering("invoiceid.greaterThan=" + SMALLER_INVOICEID, "invoiceid.greaterThan=" + DEFAULT_INVOICEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLineidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lineid equals to
        defaultSalesInvoiceLinesFiltering("lineid.equals=" + DEFAULT_LINEID, "lineid.equals=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLineidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lineid in
        defaultSalesInvoiceLinesFiltering("lineid.in=" + DEFAULT_LINEID + "," + UPDATED_LINEID, "lineid.in=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLineidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lineid is not null
        defaultSalesInvoiceLinesFiltering("lineid.specified=true", "lineid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLineidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lineid is greater than or equal to
        defaultSalesInvoiceLinesFiltering("lineid.greaterThanOrEqual=" + DEFAULT_LINEID, "lineid.greaterThanOrEqual=" + UPDATED_LINEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLineidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lineid is less than or equal to
        defaultSalesInvoiceLinesFiltering("lineid.lessThanOrEqual=" + DEFAULT_LINEID, "lineid.lessThanOrEqual=" + SMALLER_LINEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLineidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lineid is less than
        defaultSalesInvoiceLinesFiltering("lineid.lessThan=" + UPDATED_LINEID, "lineid.lessThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLineidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lineid is greater than
        defaultSalesInvoiceLinesFiltering("lineid.greaterThan=" + SMALLER_LINEID, "lineid.greaterThan=" + DEFAULT_LINEID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemid equals to
        defaultSalesInvoiceLinesFiltering("itemid.equals=" + DEFAULT_ITEMID, "itemid.equals=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemid in
        defaultSalesInvoiceLinesFiltering("itemid.in=" + DEFAULT_ITEMID + "," + UPDATED_ITEMID, "itemid.in=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemid is not null
        defaultSalesInvoiceLinesFiltering("itemid.specified=true", "itemid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemid is greater than or equal to
        defaultSalesInvoiceLinesFiltering("itemid.greaterThanOrEqual=" + DEFAULT_ITEMID, "itemid.greaterThanOrEqual=" + UPDATED_ITEMID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemid is less than or equal to
        defaultSalesInvoiceLinesFiltering("itemid.lessThanOrEqual=" + DEFAULT_ITEMID, "itemid.lessThanOrEqual=" + SMALLER_ITEMID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemid is less than
        defaultSalesInvoiceLinesFiltering("itemid.lessThan=" + UPDATED_ITEMID, "itemid.lessThan=" + DEFAULT_ITEMID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemid is greater than
        defaultSalesInvoiceLinesFiltering("itemid.greaterThan=" + SMALLER_ITEMID, "itemid.greaterThan=" + DEFAULT_ITEMID);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcode equals to
        defaultSalesInvoiceLinesFiltering("itemcode.equals=" + DEFAULT_ITEMCODE, "itemcode.equals=" + UPDATED_ITEMCODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcode in
        defaultSalesInvoiceLinesFiltering("itemcode.in=" + DEFAULT_ITEMCODE + "," + UPDATED_ITEMCODE, "itemcode.in=" + UPDATED_ITEMCODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcode is not null
        defaultSalesInvoiceLinesFiltering("itemcode.specified=true", "itemcode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcode contains
        defaultSalesInvoiceLinesFiltering("itemcode.contains=" + DEFAULT_ITEMCODE, "itemcode.contains=" + UPDATED_ITEMCODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcode does not contain
        defaultSalesInvoiceLinesFiltering("itemcode.doesNotContain=" + UPDATED_ITEMCODE, "itemcode.doesNotContain=" + DEFAULT_ITEMCODE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemnameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemname equals to
        defaultSalesInvoiceLinesFiltering("itemname.equals=" + DEFAULT_ITEMNAME, "itemname.equals=" + UPDATED_ITEMNAME);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemnameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemname in
        defaultSalesInvoiceLinesFiltering("itemname.in=" + DEFAULT_ITEMNAME + "," + UPDATED_ITEMNAME, "itemname.in=" + UPDATED_ITEMNAME);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemname is not null
        defaultSalesInvoiceLinesFiltering("itemname.specified=true", "itemname.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemnameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemname contains
        defaultSalesInvoiceLinesFiltering("itemname.contains=" + DEFAULT_ITEMNAME, "itemname.contains=" + UPDATED_ITEMNAME);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemnameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemname does not contain
        defaultSalesInvoiceLinesFiltering("itemname.doesNotContain=" + UPDATED_ITEMNAME, "itemname.doesNotContain=" + DEFAULT_ITEMNAME);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where description equals to
        defaultSalesInvoiceLinesFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where description in
        defaultSalesInvoiceLinesFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where description is not null
        defaultSalesInvoiceLinesFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where description contains
        defaultSalesInvoiceLinesFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where description does not contain
        defaultSalesInvoiceLinesFiltering(
            "description.doesNotContain=" + UPDATED_DESCRIPTION,
            "description.doesNotContain=" + DEFAULT_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByUnitofmeasurementIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where unitofmeasurement equals to
        defaultSalesInvoiceLinesFiltering(
            "unitofmeasurement.equals=" + DEFAULT_UNITOFMEASUREMENT,
            "unitofmeasurement.equals=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByUnitofmeasurementIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where unitofmeasurement in
        defaultSalesInvoiceLinesFiltering(
            "unitofmeasurement.in=" + DEFAULT_UNITOFMEASUREMENT + "," + UPDATED_UNITOFMEASUREMENT,
            "unitofmeasurement.in=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByUnitofmeasurementIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where unitofmeasurement is not null
        defaultSalesInvoiceLinesFiltering("unitofmeasurement.specified=true", "unitofmeasurement.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByUnitofmeasurementContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where unitofmeasurement contains
        defaultSalesInvoiceLinesFiltering(
            "unitofmeasurement.contains=" + DEFAULT_UNITOFMEASUREMENT,
            "unitofmeasurement.contains=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByUnitofmeasurementNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where unitofmeasurement does not contain
        defaultSalesInvoiceLinesFiltering(
            "unitofmeasurement.doesNotContain=" + UPDATED_UNITOFMEASUREMENT,
            "unitofmeasurement.doesNotContain=" + DEFAULT_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where quantity equals to
        defaultSalesInvoiceLinesFiltering("quantity.equals=" + DEFAULT_QUANTITY, "quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where quantity in
        defaultSalesInvoiceLinesFiltering("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY, "quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where quantity is not null
        defaultSalesInvoiceLinesFiltering("quantity.specified=true", "quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where quantity is greater than or equal to
        defaultSalesInvoiceLinesFiltering(
            "quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.greaterThanOrEqual=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where quantity is less than or equal to
        defaultSalesInvoiceLinesFiltering("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY, "quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where quantity is less than
        defaultSalesInvoiceLinesFiltering("quantity.lessThan=" + UPDATED_QUANTITY, "quantity.lessThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where quantity is greater than
        defaultSalesInvoiceLinesFiltering("quantity.greaterThan=" + SMALLER_QUANTITY, "quantity.greaterThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcost equals to
        defaultSalesInvoiceLinesFiltering("itemcost.equals=" + DEFAULT_ITEMCOST, "itemcost.equals=" + UPDATED_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcost in
        defaultSalesInvoiceLinesFiltering("itemcost.in=" + DEFAULT_ITEMCOST + "," + UPDATED_ITEMCOST, "itemcost.in=" + UPDATED_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcost is not null
        defaultSalesInvoiceLinesFiltering("itemcost.specified=true", "itemcost.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcost is greater than or equal to
        defaultSalesInvoiceLinesFiltering(
            "itemcost.greaterThanOrEqual=" + DEFAULT_ITEMCOST,
            "itemcost.greaterThanOrEqual=" + UPDATED_ITEMCOST
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcost is less than or equal to
        defaultSalesInvoiceLinesFiltering("itemcost.lessThanOrEqual=" + DEFAULT_ITEMCOST, "itemcost.lessThanOrEqual=" + SMALLER_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcost is less than
        defaultSalesInvoiceLinesFiltering("itemcost.lessThan=" + UPDATED_ITEMCOST, "itemcost.lessThan=" + DEFAULT_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItemcostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemcost is greater than
        defaultSalesInvoiceLinesFiltering("itemcost.greaterThan=" + SMALLER_ITEMCOST, "itemcost.greaterThan=" + DEFAULT_ITEMCOST);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItempriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemprice equals to
        defaultSalesInvoiceLinesFiltering("itemprice.equals=" + DEFAULT_ITEMPRICE, "itemprice.equals=" + UPDATED_ITEMPRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItempriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemprice in
        defaultSalesInvoiceLinesFiltering(
            "itemprice.in=" + DEFAULT_ITEMPRICE + "," + UPDATED_ITEMPRICE,
            "itemprice.in=" + UPDATED_ITEMPRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItempriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemprice is not null
        defaultSalesInvoiceLinesFiltering("itemprice.specified=true", "itemprice.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItempriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemprice is greater than or equal to
        defaultSalesInvoiceLinesFiltering(
            "itemprice.greaterThanOrEqual=" + DEFAULT_ITEMPRICE,
            "itemprice.greaterThanOrEqual=" + UPDATED_ITEMPRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItempriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemprice is less than or equal to
        defaultSalesInvoiceLinesFiltering(
            "itemprice.lessThanOrEqual=" + DEFAULT_ITEMPRICE,
            "itemprice.lessThanOrEqual=" + SMALLER_ITEMPRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItempriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemprice is less than
        defaultSalesInvoiceLinesFiltering("itemprice.lessThan=" + UPDATED_ITEMPRICE, "itemprice.lessThan=" + DEFAULT_ITEMPRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByItempriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where itemprice is greater than
        defaultSalesInvoiceLinesFiltering("itemprice.greaterThan=" + SMALLER_ITEMPRICE, "itemprice.greaterThan=" + DEFAULT_ITEMPRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where discount equals to
        defaultSalesInvoiceLinesFiltering("discount.equals=" + DEFAULT_DISCOUNT, "discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where discount in
        defaultSalesInvoiceLinesFiltering("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT, "discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where discount is not null
        defaultSalesInvoiceLinesFiltering("discount.specified=true", "discount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where discount is greater than or equal to
        defaultSalesInvoiceLinesFiltering(
            "discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT,
            "discount.greaterThanOrEqual=" + UPDATED_DISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where discount is less than or equal to
        defaultSalesInvoiceLinesFiltering("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT, "discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where discount is less than
        defaultSalesInvoiceLinesFiltering("discount.lessThan=" + UPDATED_DISCOUNT, "discount.lessThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where discount is greater than
        defaultSalesInvoiceLinesFiltering("discount.greaterThan=" + SMALLER_DISCOUNT, "discount.greaterThan=" + DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where tax equals to
        defaultSalesInvoiceLinesFiltering("tax.equals=" + DEFAULT_TAX, "tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where tax in
        defaultSalesInvoiceLinesFiltering("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX, "tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where tax is not null
        defaultSalesInvoiceLinesFiltering("tax.specified=true", "tax.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where tax is greater than or equal to
        defaultSalesInvoiceLinesFiltering("tax.greaterThanOrEqual=" + DEFAULT_TAX, "tax.greaterThanOrEqual=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where tax is less than or equal to
        defaultSalesInvoiceLinesFiltering("tax.lessThanOrEqual=" + DEFAULT_TAX, "tax.lessThanOrEqual=" + SMALLER_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where tax is less than
        defaultSalesInvoiceLinesFiltering("tax.lessThan=" + UPDATED_TAX, "tax.lessThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where tax is greater than
        defaultSalesInvoiceLinesFiltering("tax.greaterThan=" + SMALLER_TAX, "tax.greaterThan=" + DEFAULT_TAX);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesBySellingpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where sellingprice equals to
        defaultSalesInvoiceLinesFiltering("sellingprice.equals=" + DEFAULT_SELLINGPRICE, "sellingprice.equals=" + UPDATED_SELLINGPRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesBySellingpriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where sellingprice in
        defaultSalesInvoiceLinesFiltering(
            "sellingprice.in=" + DEFAULT_SELLINGPRICE + "," + UPDATED_SELLINGPRICE,
            "sellingprice.in=" + UPDATED_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesBySellingpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where sellingprice is not null
        defaultSalesInvoiceLinesFiltering("sellingprice.specified=true", "sellingprice.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesBySellingpriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where sellingprice is greater than or equal to
        defaultSalesInvoiceLinesFiltering(
            "sellingprice.greaterThanOrEqual=" + DEFAULT_SELLINGPRICE,
            "sellingprice.greaterThanOrEqual=" + UPDATED_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesBySellingpriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where sellingprice is less than or equal to
        defaultSalesInvoiceLinesFiltering(
            "sellingprice.lessThanOrEqual=" + DEFAULT_SELLINGPRICE,
            "sellingprice.lessThanOrEqual=" + SMALLER_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesBySellingpriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where sellingprice is less than
        defaultSalesInvoiceLinesFiltering("sellingprice.lessThan=" + UPDATED_SELLINGPRICE, "sellingprice.lessThan=" + DEFAULT_SELLINGPRICE);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesBySellingpriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where sellingprice is greater than
        defaultSalesInvoiceLinesFiltering(
            "sellingprice.greaterThan=" + SMALLER_SELLINGPRICE,
            "sellingprice.greaterThan=" + DEFAULT_SELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLinetotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where linetotal equals to
        defaultSalesInvoiceLinesFiltering("linetotal.equals=" + DEFAULT_LINETOTAL, "linetotal.equals=" + UPDATED_LINETOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLinetotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where linetotal in
        defaultSalesInvoiceLinesFiltering(
            "linetotal.in=" + DEFAULT_LINETOTAL + "," + UPDATED_LINETOTAL,
            "linetotal.in=" + UPDATED_LINETOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLinetotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where linetotal is not null
        defaultSalesInvoiceLinesFiltering("linetotal.specified=true", "linetotal.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLinetotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where linetotal is greater than or equal to
        defaultSalesInvoiceLinesFiltering(
            "linetotal.greaterThanOrEqual=" + DEFAULT_LINETOTAL,
            "linetotal.greaterThanOrEqual=" + UPDATED_LINETOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLinetotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where linetotal is less than or equal to
        defaultSalesInvoiceLinesFiltering(
            "linetotal.lessThanOrEqual=" + DEFAULT_LINETOTAL,
            "linetotal.lessThanOrEqual=" + SMALLER_LINETOTAL
        );
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLinetotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where linetotal is less than
        defaultSalesInvoiceLinesFiltering("linetotal.lessThan=" + UPDATED_LINETOTAL, "linetotal.lessThan=" + DEFAULT_LINETOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLinetotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where linetotal is greater than
        defaultSalesInvoiceLinesFiltering("linetotal.greaterThan=" + SMALLER_LINETOTAL, "linetotal.greaterThan=" + DEFAULT_LINETOTAL);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmu equals to
        defaultSalesInvoiceLinesFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmu in
        defaultSalesInvoiceLinesFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmu is not null
        defaultSalesInvoiceLinesFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmu is greater than or equal to
        defaultSalesInvoiceLinesFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmu is less than or equal to
        defaultSalesInvoiceLinesFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmu is less than
        defaultSalesInvoiceLinesFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmu is greater than
        defaultSalesInvoiceLinesFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmd equals to
        defaultSalesInvoiceLinesFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmd in
        defaultSalesInvoiceLinesFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where lmd is not null
        defaultSalesInvoiceLinesFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByNbtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where nbt equals to
        defaultSalesInvoiceLinesFiltering("nbt.equals=" + DEFAULT_NBT, "nbt.equals=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByNbtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where nbt in
        defaultSalesInvoiceLinesFiltering("nbt.in=" + DEFAULT_NBT + "," + UPDATED_NBT, "nbt.in=" + UPDATED_NBT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByNbtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where nbt is not null
        defaultSalesInvoiceLinesFiltering("nbt.specified=true", "nbt.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByVatIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where vat equals to
        defaultSalesInvoiceLinesFiltering("vat.equals=" + DEFAULT_VAT, "vat.equals=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByVatIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where vat in
        defaultSalesInvoiceLinesFiltering("vat.in=" + DEFAULT_VAT + "," + UPDATED_VAT, "vat.in=" + UPDATED_VAT);
    }

    @Test
    @Transactional
    void getAllSalesInvoiceLinesByVatIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        // Get all the salesInvoiceLinesList where vat is not null
        defaultSalesInvoiceLinesFiltering("vat.specified=true", "vat.specified=false");
    }

    private void defaultSalesInvoiceLinesFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSalesInvoiceLinesShouldBeFound(shouldBeFound);
        defaultSalesInvoiceLinesShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalesInvoiceLinesShouldBeFound(String filter) throws Exception {
        restSalesInvoiceLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesInvoiceLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceid").value(hasItem(DEFAULT_INVOICEID)))
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
        restSalesInvoiceLinesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalesInvoiceLinesShouldNotBeFound(String filter) throws Exception {
        restSalesInvoiceLinesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalesInvoiceLinesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalesInvoiceLines() throws Exception {
        // Get the salesInvoiceLines
        restSalesInvoiceLinesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesInvoiceLines() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLines
        SalesInvoiceLines updatedSalesInvoiceLines = salesInvoiceLinesRepository.findById(salesInvoiceLines.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSalesInvoiceLines are not directly saved in db
        em.detach(updatedSalesInvoiceLines);
        updatedSalesInvoiceLines
            .invoiceid(UPDATED_INVOICEID)
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

        restSalesInvoiceLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesInvoiceLines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSalesInvoiceLines))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSalesInvoiceLinesToMatchAllProperties(updatedSalesInvoiceLines);
    }

    @Test
    @Transactional
    void putNonExistingSalesInvoiceLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesInvoiceLines.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesInvoiceLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesInvoiceLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesInvoiceLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesInvoiceLines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesInvoiceLinesWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLines using partial update
        SalesInvoiceLines partialUpdatedSalesInvoiceLines = new SalesInvoiceLines();
        partialUpdatedSalesInvoiceLines.setId(salesInvoiceLines.getId());

        partialUpdatedSalesInvoiceLines
            .invoiceid(UPDATED_INVOICEID)
            .lineid(UPDATED_LINEID)
            .itemid(UPDATED_ITEMID)
            .itemprice(UPDATED_ITEMPRICE)
            .discount(UPDATED_DISCOUNT)
            .linetotal(UPDATED_LINETOTAL)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .vat(UPDATED_VAT);

        restSalesInvoiceLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceLines))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceLinesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSalesInvoiceLines, salesInvoiceLines),
            getPersistedSalesInvoiceLines(salesInvoiceLines)
        );
    }

    @Test
    @Transactional
    void fullUpdateSalesInvoiceLinesWithPatch() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesInvoiceLines using partial update
        SalesInvoiceLines partialUpdatedSalesInvoiceLines = new SalesInvoiceLines();
        partialUpdatedSalesInvoiceLines.setId(salesInvoiceLines.getId());

        partialUpdatedSalesInvoiceLines
            .invoiceid(UPDATED_INVOICEID)
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

        restSalesInvoiceLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesInvoiceLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesInvoiceLines))
            )
            .andExpect(status().isOk());

        // Validate the SalesInvoiceLines in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesInvoiceLinesUpdatableFieldsEquals(
            partialUpdatedSalesInvoiceLines,
            getPersistedSalesInvoiceLines(partialUpdatedSalesInvoiceLines)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSalesInvoiceLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLines.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesInvoiceLines.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesInvoiceLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesInvoiceLines))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalesInvoiceLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesInvoiceLines() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesInvoiceLines.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesInvoiceLinesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(salesInvoiceLines)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalesInvoiceLines in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesInvoiceLines() throws Exception {
        // Initialize the database
        insertedSalesInvoiceLines = salesInvoiceLinesRepository.saveAndFlush(salesInvoiceLines);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the salesInvoiceLines
        restSalesInvoiceLinesMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesInvoiceLines.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return salesInvoiceLinesRepository.count();
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

    protected SalesInvoiceLines getPersistedSalesInvoiceLines(SalesInvoiceLines salesInvoiceLines) {
        return salesInvoiceLinesRepository.findById(salesInvoiceLines.getId()).orElseThrow();
    }

    protected void assertPersistedSalesInvoiceLinesToMatchAllProperties(SalesInvoiceLines expectedSalesInvoiceLines) {
        assertSalesInvoiceLinesAllPropertiesEquals(expectedSalesInvoiceLines, getPersistedSalesInvoiceLines(expectedSalesInvoiceLines));
    }

    protected void assertPersistedSalesInvoiceLinesToMatchUpdatableProperties(SalesInvoiceLines expectedSalesInvoiceLines) {
        assertSalesInvoiceLinesAllUpdatablePropertiesEquals(
            expectedSalesInvoiceLines,
            getPersistedSalesInvoiceLines(expectedSalesInvoiceLines)
        );
    }
}
