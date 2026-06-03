package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.InventoryAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Inventory;
import com.heavenscode.rac.repository.InventoryRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
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
 * Integration tests for the {@link InventoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventoryResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARTNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;
    private static final Integer SMALLER_TYPE = 1 - 1;

    private static final String DEFAULT_CLASSIFICATION_1 = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION_2 = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION_3 = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION_4 = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION_5 = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION_5 = "BBBBBBBBBB";

    private static final String DEFAULT_UNITOFMEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_UNITOFMEASUREMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_DECIMALPLACES = 1;
    private static final Integer UPDATED_DECIMALPLACES = 2;
    private static final Integer SMALLER_DECIMALPLACES = 1 - 1;

    private static final Boolean DEFAULT_ISASSEMBLYUNIT = false;
    private static final Boolean UPDATED_ISASSEMBLYUNIT = true;

    private static final Integer DEFAULT_ASSEMBLYUNITOF = 1;
    private static final Integer UPDATED_ASSEMBLYUNITOF = 2;
    private static final Integer SMALLER_ASSEMBLYUNITOF = 1 - 1;

    private static final Float DEFAULT_REORDERLEVEL = 1F;
    private static final Float UPDATED_REORDERLEVEL = 2F;
    private static final Float SMALLER_REORDERLEVEL = 1F - 1F;

    private static final Float DEFAULT_LASTCOST = 1F;
    private static final Float UPDATED_LASTCOST = 2F;
    private static final Float SMALLER_LASTCOST = 1F - 1F;

    private static final Float DEFAULT_LASTSELLINGPRICE = 1F;
    private static final Float UPDATED_LASTSELLINGPRICE = 2F;
    private static final Float SMALLER_LASTSELLINGPRICE = 1F - 1F;

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_AVAILABLEQUANTITY = 1F;
    private static final Float UPDATED_AVAILABLEQUANTITY = 2F;
    private static final Float SMALLER_AVAILABLEQUANTITY = 1F - 1F;

    private static final Boolean DEFAULT_HASBATCHES = false;
    private static final Boolean UPDATED_HASBATCHES = true;

    private static final String DEFAULT_ITEMSPECFILEPATH = "AAAAAAAAAA";
    private static final String UPDATED_ITEMSPECFILEPATH = "BBBBBBBBBB";

    private static final String DEFAULT_ITEMIMAGEPATH = "AAAAAAAAAA";
    private static final String UPDATED_ITEMIMAGEPATH = "BBBBBBBBBB";

    private static final Float DEFAULT_RETURNPRICE = 1F;
    private static final Float UPDATED_RETURNPRICE = 2F;
    private static final Float SMALLER_RETURNPRICE = 1F - 1F;

    private static final Boolean DEFAULT_ACTIVEITEM = false;
    private static final Boolean UPDATED_ACTIVEITEM = true;

    private static final Float DEFAULT_MINSTOCK = 1F;
    private static final Float UPDATED_MINSTOCK = 2F;
    private static final Float SMALLER_MINSTOCK = 1F - 1F;

    private static final Float DEFAULT_MAXSTOCK = 1F;
    private static final Float UPDATED_MAXSTOCK = 2F;
    private static final Float SMALLER_MAXSTOCK = 1F - 1F;

    private static final Float DEFAULT_DAILYAVERAGE = 1F;
    private static final Float UPDATED_DAILYAVERAGE = 2F;
    private static final Float SMALLER_DAILYAVERAGE = 1F - 1F;

    private static final Float DEFAULT_BUFFERLEVEL = 1F;
    private static final Float UPDATED_BUFFERLEVEL = 2F;
    private static final Float SMALLER_BUFFERLEVEL = 1F - 1F;

    private static final Float DEFAULT_LEADTIME = 1F;
    private static final Float UPDATED_LEADTIME = 2F;
    private static final Float SMALLER_LEADTIME = 1F - 1F;

    private static final Float DEFAULT_BUFFERTIME = 1F;
    private static final Float UPDATED_BUFFERTIME = 2F;
    private static final Float SMALLER_BUFFERTIME = 1F - 1F;

    private static final Float DEFAULT_SAFTYDAYS = 1F;
    private static final Float UPDATED_SAFTYDAYS = 2F;
    private static final Float SMALLER_SAFTYDAYS = 1F - 1F;

    private static final String DEFAULT_ACCOUNTCODE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNTCODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCOUNTID = 1;
    private static final Integer UPDATED_ACCOUNTID = 2;
    private static final Integer SMALLER_ACCOUNTID = 1 - 1;

    private static final Integer DEFAULT_CASEPACKQTY = 1;
    private static final Integer UPDATED_CASEPACKQTY = 2;
    private static final Integer SMALLER_CASEPACKQTY = 1 - 1;

    private static final Boolean DEFAULT_ISREGISTERED = false;
    private static final Boolean UPDATED_ISREGISTERED = true;

    private static final Integer DEFAULT_DEFAULTSTOCKLOCATIONID = 1;
    private static final Integer UPDATED_DEFAULTSTOCKLOCATIONID = 2;
    private static final Integer SMALLER_DEFAULTSTOCKLOCATIONID = 1 - 1;

    private static final String DEFAULT_RACKNO = "AAAAAAAAAA";
    private static final String UPDATED_RACKNO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BARCODEIMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BARCODEIMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BARCODEIMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BARCODEIMAGE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_COMMISSIONEMPID = 1;
    private static final Integer UPDATED_COMMISSIONEMPID = 2;
    private static final Integer SMALLER_COMMISSIONEMPID = 1 - 1;

    private static final Integer DEFAULT_CHECKTYPEID = 1;
    private static final Integer UPDATED_CHECKTYPEID = 2;
    private static final Integer SMALLER_CHECKTYPEID = 1 - 1;

    private static final String DEFAULT_CHECKTYPE = "AAAAAAAAAA";
    private static final String UPDATED_CHECKTYPE = "BBBBBBBBBB";

    private static final Float DEFAULT_REORDERQTY = 1F;
    private static final Float UPDATED_REORDERQTY = 2F;
    private static final Float SMALLER_REORDERQTY = 1F - 1F;

    private static final Boolean DEFAULT_NOTININVOICE = false;
    private static final Boolean UPDATED_NOTININVOICE = true;

    private static final String ENTITY_API_URL = "/api/inventories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    private Inventory insertedInventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createEntity() {
        return new Inventory()
            .code(DEFAULT_CODE)
            .partnumber(DEFAULT_PARTNUMBER)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .classification1(DEFAULT_CLASSIFICATION_1)
            .classification2(DEFAULT_CLASSIFICATION_2)
            .classification3(DEFAULT_CLASSIFICATION_3)
            .classification4(DEFAULT_CLASSIFICATION_4)
            .classification5(DEFAULT_CLASSIFICATION_5)
            .unitofmeasurement(DEFAULT_UNITOFMEASUREMENT)
            .decimalplaces(DEFAULT_DECIMALPLACES)
            .isassemblyunit(DEFAULT_ISASSEMBLYUNIT)
            .assemblyunitof(DEFAULT_ASSEMBLYUNITOF)
            .reorderlevel(DEFAULT_REORDERLEVEL)
            .lastcost(DEFAULT_LASTCOST)
            .lastsellingprice(DEFAULT_LASTSELLINGPRICE)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .availablequantity(DEFAULT_AVAILABLEQUANTITY)
            .hasbatches(DEFAULT_HASBATCHES)
            .itemspecfilepath(DEFAULT_ITEMSPECFILEPATH)
            .itemimagepath(DEFAULT_ITEMIMAGEPATH)
            .returnprice(DEFAULT_RETURNPRICE)
            .activeitem(DEFAULT_ACTIVEITEM)
            .minstock(DEFAULT_MINSTOCK)
            .maxstock(DEFAULT_MAXSTOCK)
            .dailyaverage(DEFAULT_DAILYAVERAGE)
            .bufferlevel(DEFAULT_BUFFERLEVEL)
            .leadtime(DEFAULT_LEADTIME)
            .buffertime(DEFAULT_BUFFERTIME)
            .saftydays(DEFAULT_SAFTYDAYS)
            .accountcode(DEFAULT_ACCOUNTCODE)
            .accountid(DEFAULT_ACCOUNTID)
            .casepackqty(DEFAULT_CASEPACKQTY)
            .isregistered(DEFAULT_ISREGISTERED)
            .defaultstocklocationid(DEFAULT_DEFAULTSTOCKLOCATIONID)
            .rackno(DEFAULT_RACKNO)
            .barcodeimage(DEFAULT_BARCODEIMAGE)
            .barcodeimageContentType(DEFAULT_BARCODEIMAGE_CONTENT_TYPE)
            .commissionempid(DEFAULT_COMMISSIONEMPID)
            .checktypeid(DEFAULT_CHECKTYPEID)
            .checktype(DEFAULT_CHECKTYPE)
            .reorderqty(DEFAULT_REORDERQTY)
            .notininvoice(DEFAULT_NOTININVOICE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createUpdatedEntity() {
        return new Inventory()
            .code(UPDATED_CODE)
            .partnumber(UPDATED_PARTNUMBER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .classification1(UPDATED_CLASSIFICATION_1)
            .classification2(UPDATED_CLASSIFICATION_2)
            .classification3(UPDATED_CLASSIFICATION_3)
            .classification4(UPDATED_CLASSIFICATION_4)
            .classification5(UPDATED_CLASSIFICATION_5)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .decimalplaces(UPDATED_DECIMALPLACES)
            .isassemblyunit(UPDATED_ISASSEMBLYUNIT)
            .assemblyunitof(UPDATED_ASSEMBLYUNITOF)
            .reorderlevel(UPDATED_REORDERLEVEL)
            .lastcost(UPDATED_LASTCOST)
            .lastsellingprice(UPDATED_LASTSELLINGPRICE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .availablequantity(UPDATED_AVAILABLEQUANTITY)
            .hasbatches(UPDATED_HASBATCHES)
            .itemspecfilepath(UPDATED_ITEMSPECFILEPATH)
            .itemimagepath(UPDATED_ITEMIMAGEPATH)
            .returnprice(UPDATED_RETURNPRICE)
            .activeitem(UPDATED_ACTIVEITEM)
            .minstock(UPDATED_MINSTOCK)
            .maxstock(UPDATED_MAXSTOCK)
            .dailyaverage(UPDATED_DAILYAVERAGE)
            .bufferlevel(UPDATED_BUFFERLEVEL)
            .leadtime(UPDATED_LEADTIME)
            .buffertime(UPDATED_BUFFERTIME)
            .saftydays(UPDATED_SAFTYDAYS)
            .accountcode(UPDATED_ACCOUNTCODE)
            .accountid(UPDATED_ACCOUNTID)
            .casepackqty(UPDATED_CASEPACKQTY)
            .isregistered(UPDATED_ISREGISTERED)
            .defaultstocklocationid(UPDATED_DEFAULTSTOCKLOCATIONID)
            .rackno(UPDATED_RACKNO)
            .barcodeimage(UPDATED_BARCODEIMAGE)
            .barcodeimageContentType(UPDATED_BARCODEIMAGE_CONTENT_TYPE)
            .commissionempid(UPDATED_COMMISSIONEMPID)
            .checktypeid(UPDATED_CHECKTYPEID)
            .checktype(UPDATED_CHECKTYPE)
            .reorderqty(UPDATED_REORDERQTY)
            .notininvoice(UPDATED_NOTININVOICE);
    }

    @BeforeEach
    public void initTest() {
        inventory = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedInventory != null) {
            inventoryRepository.delete(insertedInventory);
            insertedInventory = null;
        }
    }

    @Test
    @Transactional
    void createInventory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inventory
        var returnedInventory = om.readValue(
            restInventoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventory)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Inventory.class
        );

        // Validate the Inventory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertInventoryUpdatableFieldsEquals(returnedInventory, getPersistedInventory(returnedInventory));

        insertedInventory = returnedInventory;
    }

    @Test
    @Transactional
    void createInventoryWithExistingId() throws Exception {
        // Create the Inventory with an existing ID
        inventory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventory)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInventories() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].partnumber").value(hasItem(DEFAULT_PARTNUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].classification1").value(hasItem(DEFAULT_CLASSIFICATION_1)))
            .andExpect(jsonPath("$.[*].classification2").value(hasItem(DEFAULT_CLASSIFICATION_2)))
            .andExpect(jsonPath("$.[*].classification3").value(hasItem(DEFAULT_CLASSIFICATION_3)))
            .andExpect(jsonPath("$.[*].classification4").value(hasItem(DEFAULT_CLASSIFICATION_4)))
            .andExpect(jsonPath("$.[*].classification5").value(hasItem(DEFAULT_CLASSIFICATION_5)))
            .andExpect(jsonPath("$.[*].unitofmeasurement").value(hasItem(DEFAULT_UNITOFMEASUREMENT)))
            .andExpect(jsonPath("$.[*].decimalplaces").value(hasItem(DEFAULT_DECIMALPLACES)))
            .andExpect(jsonPath("$.[*].isassemblyunit").value(hasItem(DEFAULT_ISASSEMBLYUNIT.booleanValue())))
            .andExpect(jsonPath("$.[*].assemblyunitof").value(hasItem(DEFAULT_ASSEMBLYUNITOF)))
            .andExpect(jsonPath("$.[*].reorderlevel").value(hasItem(DEFAULT_REORDERLEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].lastcost").value(hasItem(DEFAULT_LASTCOST.doubleValue())))
            .andExpect(jsonPath("$.[*].lastsellingprice").value(hasItem(DEFAULT_LASTSELLINGPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].availablequantity").value(hasItem(DEFAULT_AVAILABLEQUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].hasbatches").value(hasItem(DEFAULT_HASBATCHES.booleanValue())))
            .andExpect(jsonPath("$.[*].itemspecfilepath").value(hasItem(DEFAULT_ITEMSPECFILEPATH)))
            .andExpect(jsonPath("$.[*].itemimagepath").value(hasItem(DEFAULT_ITEMIMAGEPATH)))
            .andExpect(jsonPath("$.[*].returnprice").value(hasItem(DEFAULT_RETURNPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].activeitem").value(hasItem(DEFAULT_ACTIVEITEM.booleanValue())))
            .andExpect(jsonPath("$.[*].minstock").value(hasItem(DEFAULT_MINSTOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].maxstock").value(hasItem(DEFAULT_MAXSTOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].dailyaverage").value(hasItem(DEFAULT_DAILYAVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].bufferlevel").value(hasItem(DEFAULT_BUFFERLEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].leadtime").value(hasItem(DEFAULT_LEADTIME.doubleValue())))
            .andExpect(jsonPath("$.[*].buffertime").value(hasItem(DEFAULT_BUFFERTIME.doubleValue())))
            .andExpect(jsonPath("$.[*].saftydays").value(hasItem(DEFAULT_SAFTYDAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].accountcode").value(hasItem(DEFAULT_ACCOUNTCODE)))
            .andExpect(jsonPath("$.[*].accountid").value(hasItem(DEFAULT_ACCOUNTID)))
            .andExpect(jsonPath("$.[*].casepackqty").value(hasItem(DEFAULT_CASEPACKQTY)))
            .andExpect(jsonPath("$.[*].isregistered").value(hasItem(DEFAULT_ISREGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultstocklocationid").value(hasItem(DEFAULT_DEFAULTSTOCKLOCATIONID)))
            .andExpect(jsonPath("$.[*].rackno").value(hasItem(DEFAULT_RACKNO)))
            .andExpect(jsonPath("$.[*].barcodeimageContentType").value(hasItem(DEFAULT_BARCODEIMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].barcodeimage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_BARCODEIMAGE))))
            .andExpect(jsonPath("$.[*].commissionempid").value(hasItem(DEFAULT_COMMISSIONEMPID)))
            .andExpect(jsonPath("$.[*].checktypeid").value(hasItem(DEFAULT_CHECKTYPEID)))
            .andExpect(jsonPath("$.[*].checktype").value(hasItem(DEFAULT_CHECKTYPE)))
            .andExpect(jsonPath("$.[*].reorderqty").value(hasItem(DEFAULT_REORDERQTY.doubleValue())))
            .andExpect(jsonPath("$.[*].notininvoice").value(hasItem(DEFAULT_NOTININVOICE.booleanValue())));
    }

    @Test
    @Transactional
    void getInventory() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL_ID, inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.partnumber").value(DEFAULT_PARTNUMBER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.classification1").value(DEFAULT_CLASSIFICATION_1))
            .andExpect(jsonPath("$.classification2").value(DEFAULT_CLASSIFICATION_2))
            .andExpect(jsonPath("$.classification3").value(DEFAULT_CLASSIFICATION_3))
            .andExpect(jsonPath("$.classification4").value(DEFAULT_CLASSIFICATION_4))
            .andExpect(jsonPath("$.classification5").value(DEFAULT_CLASSIFICATION_5))
            .andExpect(jsonPath("$.unitofmeasurement").value(DEFAULT_UNITOFMEASUREMENT))
            .andExpect(jsonPath("$.decimalplaces").value(DEFAULT_DECIMALPLACES))
            .andExpect(jsonPath("$.isassemblyunit").value(DEFAULT_ISASSEMBLYUNIT.booleanValue()))
            .andExpect(jsonPath("$.assemblyunitof").value(DEFAULT_ASSEMBLYUNITOF))
            .andExpect(jsonPath("$.reorderlevel").value(DEFAULT_REORDERLEVEL.doubleValue()))
            .andExpect(jsonPath("$.lastcost").value(DEFAULT_LASTCOST.doubleValue()))
            .andExpect(jsonPath("$.lastsellingprice").value(DEFAULT_LASTSELLINGPRICE.doubleValue()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.availablequantity").value(DEFAULT_AVAILABLEQUANTITY.doubleValue()))
            .andExpect(jsonPath("$.hasbatches").value(DEFAULT_HASBATCHES.booleanValue()))
            .andExpect(jsonPath("$.itemspecfilepath").value(DEFAULT_ITEMSPECFILEPATH))
            .andExpect(jsonPath("$.itemimagepath").value(DEFAULT_ITEMIMAGEPATH))
            .andExpect(jsonPath("$.returnprice").value(DEFAULT_RETURNPRICE.doubleValue()))
            .andExpect(jsonPath("$.activeitem").value(DEFAULT_ACTIVEITEM.booleanValue()))
            .andExpect(jsonPath("$.minstock").value(DEFAULT_MINSTOCK.doubleValue()))
            .andExpect(jsonPath("$.maxstock").value(DEFAULT_MAXSTOCK.doubleValue()))
            .andExpect(jsonPath("$.dailyaverage").value(DEFAULT_DAILYAVERAGE.doubleValue()))
            .andExpect(jsonPath("$.bufferlevel").value(DEFAULT_BUFFERLEVEL.doubleValue()))
            .andExpect(jsonPath("$.leadtime").value(DEFAULT_LEADTIME.doubleValue()))
            .andExpect(jsonPath("$.buffertime").value(DEFAULT_BUFFERTIME.doubleValue()))
            .andExpect(jsonPath("$.saftydays").value(DEFAULT_SAFTYDAYS.doubleValue()))
            .andExpect(jsonPath("$.accountcode").value(DEFAULT_ACCOUNTCODE))
            .andExpect(jsonPath("$.accountid").value(DEFAULT_ACCOUNTID))
            .andExpect(jsonPath("$.casepackqty").value(DEFAULT_CASEPACKQTY))
            .andExpect(jsonPath("$.isregistered").value(DEFAULT_ISREGISTERED.booleanValue()))
            .andExpect(jsonPath("$.defaultstocklocationid").value(DEFAULT_DEFAULTSTOCKLOCATIONID))
            .andExpect(jsonPath("$.rackno").value(DEFAULT_RACKNO))
            .andExpect(jsonPath("$.barcodeimageContentType").value(DEFAULT_BARCODEIMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.barcodeimage").value(Base64.getEncoder().encodeToString(DEFAULT_BARCODEIMAGE)))
            .andExpect(jsonPath("$.commissionempid").value(DEFAULT_COMMISSIONEMPID))
            .andExpect(jsonPath("$.checktypeid").value(DEFAULT_CHECKTYPEID))
            .andExpect(jsonPath("$.checktype").value(DEFAULT_CHECKTYPE))
            .andExpect(jsonPath("$.reorderqty").value(DEFAULT_REORDERQTY.doubleValue()))
            .andExpect(jsonPath("$.notininvoice").value(DEFAULT_NOTININVOICE.booleanValue()));
    }

    @Test
    @Transactional
    void getInventoriesByIdFiltering() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        Long id = inventory.getId();

        defaultInventoryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultInventoryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultInventoryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInventoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where code equals to
        defaultInventoryFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInventoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where code in
        defaultInventoryFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInventoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where code is not null
        defaultInventoryFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where code contains
        defaultInventoryFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllInventoriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where code does not contain
        defaultInventoryFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllInventoriesByPartnumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where partnumber equals to
        defaultInventoryFiltering("partnumber.equals=" + DEFAULT_PARTNUMBER, "partnumber.equals=" + UPDATED_PARTNUMBER);
    }

    @Test
    @Transactional
    void getAllInventoriesByPartnumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where partnumber in
        defaultInventoryFiltering("partnumber.in=" + DEFAULT_PARTNUMBER + "," + UPDATED_PARTNUMBER, "partnumber.in=" + UPDATED_PARTNUMBER);
    }

    @Test
    @Transactional
    void getAllInventoriesByPartnumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where partnumber is not null
        defaultInventoryFiltering("partnumber.specified=true", "partnumber.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByPartnumberContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where partnumber contains
        defaultInventoryFiltering("partnumber.contains=" + DEFAULT_PARTNUMBER, "partnumber.contains=" + UPDATED_PARTNUMBER);
    }

    @Test
    @Transactional
    void getAllInventoriesByPartnumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where partnumber does not contain
        defaultInventoryFiltering("partnumber.doesNotContain=" + UPDATED_PARTNUMBER, "partnumber.doesNotContain=" + DEFAULT_PARTNUMBER);
    }

    @Test
    @Transactional
    void getAllInventoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where name equals to
        defaultInventoryFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where name in
        defaultInventoryFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where name is not null
        defaultInventoryFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where name contains
        defaultInventoryFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where name does not contain
        defaultInventoryFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllInventoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description equals to
        defaultInventoryFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInventoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description in
        defaultInventoryFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description is not null
        defaultInventoryFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description contains
        defaultInventoryFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInventoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description does not contain
        defaultInventoryFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInventoriesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where type equals to
        defaultInventoryFiltering("type.equals=" + DEFAULT_TYPE, "type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where type in
        defaultInventoryFiltering("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE, "type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where type is not null
        defaultInventoryFiltering("type.specified=true", "type.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where type is greater than or equal to
        defaultInventoryFiltering("type.greaterThanOrEqual=" + DEFAULT_TYPE, "type.greaterThanOrEqual=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where type is less than or equal to
        defaultInventoryFiltering("type.lessThanOrEqual=" + DEFAULT_TYPE, "type.lessThanOrEqual=" + SMALLER_TYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where type is less than
        defaultInventoryFiltering("type.lessThan=" + UPDATED_TYPE, "type.lessThan=" + DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where type is greater than
        defaultInventoryFiltering("type.greaterThan=" + SMALLER_TYPE, "type.greaterThan=" + DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification1IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification1 equals to
        defaultInventoryFiltering(
            "classification1.equals=" + DEFAULT_CLASSIFICATION_1,
            "classification1.equals=" + UPDATED_CLASSIFICATION_1
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification1IsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification1 in
        defaultInventoryFiltering(
            "classification1.in=" + DEFAULT_CLASSIFICATION_1 + "," + UPDATED_CLASSIFICATION_1,
            "classification1.in=" + UPDATED_CLASSIFICATION_1
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification1IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification1 is not null
        defaultInventoryFiltering("classification1.specified=true", "classification1.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification1ContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification1 contains
        defaultInventoryFiltering(
            "classification1.contains=" + DEFAULT_CLASSIFICATION_1,
            "classification1.contains=" + UPDATED_CLASSIFICATION_1
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification1NotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification1 does not contain
        defaultInventoryFiltering(
            "classification1.doesNotContain=" + UPDATED_CLASSIFICATION_1,
            "classification1.doesNotContain=" + DEFAULT_CLASSIFICATION_1
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification2IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification2 equals to
        defaultInventoryFiltering(
            "classification2.equals=" + DEFAULT_CLASSIFICATION_2,
            "classification2.equals=" + UPDATED_CLASSIFICATION_2
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification2IsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification2 in
        defaultInventoryFiltering(
            "classification2.in=" + DEFAULT_CLASSIFICATION_2 + "," + UPDATED_CLASSIFICATION_2,
            "classification2.in=" + UPDATED_CLASSIFICATION_2
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification2IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification2 is not null
        defaultInventoryFiltering("classification2.specified=true", "classification2.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification2ContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification2 contains
        defaultInventoryFiltering(
            "classification2.contains=" + DEFAULT_CLASSIFICATION_2,
            "classification2.contains=" + UPDATED_CLASSIFICATION_2
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification2NotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification2 does not contain
        defaultInventoryFiltering(
            "classification2.doesNotContain=" + UPDATED_CLASSIFICATION_2,
            "classification2.doesNotContain=" + DEFAULT_CLASSIFICATION_2
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification3IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification3 equals to
        defaultInventoryFiltering(
            "classification3.equals=" + DEFAULT_CLASSIFICATION_3,
            "classification3.equals=" + UPDATED_CLASSIFICATION_3
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification3IsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification3 in
        defaultInventoryFiltering(
            "classification3.in=" + DEFAULT_CLASSIFICATION_3 + "," + UPDATED_CLASSIFICATION_3,
            "classification3.in=" + UPDATED_CLASSIFICATION_3
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification3IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification3 is not null
        defaultInventoryFiltering("classification3.specified=true", "classification3.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification3ContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification3 contains
        defaultInventoryFiltering(
            "classification3.contains=" + DEFAULT_CLASSIFICATION_3,
            "classification3.contains=" + UPDATED_CLASSIFICATION_3
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification3NotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification3 does not contain
        defaultInventoryFiltering(
            "classification3.doesNotContain=" + UPDATED_CLASSIFICATION_3,
            "classification3.doesNotContain=" + DEFAULT_CLASSIFICATION_3
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification4IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification4 equals to
        defaultInventoryFiltering(
            "classification4.equals=" + DEFAULT_CLASSIFICATION_4,
            "classification4.equals=" + UPDATED_CLASSIFICATION_4
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification4IsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification4 in
        defaultInventoryFiltering(
            "classification4.in=" + DEFAULT_CLASSIFICATION_4 + "," + UPDATED_CLASSIFICATION_4,
            "classification4.in=" + UPDATED_CLASSIFICATION_4
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification4IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification4 is not null
        defaultInventoryFiltering("classification4.specified=true", "classification4.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification4ContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification4 contains
        defaultInventoryFiltering(
            "classification4.contains=" + DEFAULT_CLASSIFICATION_4,
            "classification4.contains=" + UPDATED_CLASSIFICATION_4
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification4NotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification4 does not contain
        defaultInventoryFiltering(
            "classification4.doesNotContain=" + UPDATED_CLASSIFICATION_4,
            "classification4.doesNotContain=" + DEFAULT_CLASSIFICATION_4
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification5IsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification5 equals to
        defaultInventoryFiltering(
            "classification5.equals=" + DEFAULT_CLASSIFICATION_5,
            "classification5.equals=" + UPDATED_CLASSIFICATION_5
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification5IsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification5 in
        defaultInventoryFiltering(
            "classification5.in=" + DEFAULT_CLASSIFICATION_5 + "," + UPDATED_CLASSIFICATION_5,
            "classification5.in=" + UPDATED_CLASSIFICATION_5
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification5IsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification5 is not null
        defaultInventoryFiltering("classification5.specified=true", "classification5.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification5ContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification5 contains
        defaultInventoryFiltering(
            "classification5.contains=" + DEFAULT_CLASSIFICATION_5,
            "classification5.contains=" + UPDATED_CLASSIFICATION_5
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByClassification5NotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where classification5 does not contain
        defaultInventoryFiltering(
            "classification5.doesNotContain=" + UPDATED_CLASSIFICATION_5,
            "classification5.doesNotContain=" + DEFAULT_CLASSIFICATION_5
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByUnitofmeasurementIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where unitofmeasurement equals to
        defaultInventoryFiltering(
            "unitofmeasurement.equals=" + DEFAULT_UNITOFMEASUREMENT,
            "unitofmeasurement.equals=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByUnitofmeasurementIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where unitofmeasurement in
        defaultInventoryFiltering(
            "unitofmeasurement.in=" + DEFAULT_UNITOFMEASUREMENT + "," + UPDATED_UNITOFMEASUREMENT,
            "unitofmeasurement.in=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByUnitofmeasurementIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where unitofmeasurement is not null
        defaultInventoryFiltering("unitofmeasurement.specified=true", "unitofmeasurement.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByUnitofmeasurementContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where unitofmeasurement contains
        defaultInventoryFiltering(
            "unitofmeasurement.contains=" + DEFAULT_UNITOFMEASUREMENT,
            "unitofmeasurement.contains=" + UPDATED_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByUnitofmeasurementNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where unitofmeasurement does not contain
        defaultInventoryFiltering(
            "unitofmeasurement.doesNotContain=" + UPDATED_UNITOFMEASUREMENT,
            "unitofmeasurement.doesNotContain=" + DEFAULT_UNITOFMEASUREMENT
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDecimalplacesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where decimalplaces equals to
        defaultInventoryFiltering("decimalplaces.equals=" + DEFAULT_DECIMALPLACES, "decimalplaces.equals=" + UPDATED_DECIMALPLACES);
    }

    @Test
    @Transactional
    void getAllInventoriesByDecimalplacesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where decimalplaces in
        defaultInventoryFiltering(
            "decimalplaces.in=" + DEFAULT_DECIMALPLACES + "," + UPDATED_DECIMALPLACES,
            "decimalplaces.in=" + UPDATED_DECIMALPLACES
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDecimalplacesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where decimalplaces is not null
        defaultInventoryFiltering("decimalplaces.specified=true", "decimalplaces.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByDecimalplacesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where decimalplaces is greater than or equal to
        defaultInventoryFiltering(
            "decimalplaces.greaterThanOrEqual=" + DEFAULT_DECIMALPLACES,
            "decimalplaces.greaterThanOrEqual=" + UPDATED_DECIMALPLACES
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDecimalplacesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where decimalplaces is less than or equal to
        defaultInventoryFiltering(
            "decimalplaces.lessThanOrEqual=" + DEFAULT_DECIMALPLACES,
            "decimalplaces.lessThanOrEqual=" + SMALLER_DECIMALPLACES
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDecimalplacesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where decimalplaces is less than
        defaultInventoryFiltering("decimalplaces.lessThan=" + UPDATED_DECIMALPLACES, "decimalplaces.lessThan=" + DEFAULT_DECIMALPLACES);
    }

    @Test
    @Transactional
    void getAllInventoriesByDecimalplacesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where decimalplaces is greater than
        defaultInventoryFiltering(
            "decimalplaces.greaterThan=" + SMALLER_DECIMALPLACES,
            "decimalplaces.greaterThan=" + DEFAULT_DECIMALPLACES
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByIsassemblyunitIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where isassemblyunit equals to
        defaultInventoryFiltering("isassemblyunit.equals=" + DEFAULT_ISASSEMBLYUNIT, "isassemblyunit.equals=" + UPDATED_ISASSEMBLYUNIT);
    }

    @Test
    @Transactional
    void getAllInventoriesByIsassemblyunitIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where isassemblyunit in
        defaultInventoryFiltering(
            "isassemblyunit.in=" + DEFAULT_ISASSEMBLYUNIT + "," + UPDATED_ISASSEMBLYUNIT,
            "isassemblyunit.in=" + UPDATED_ISASSEMBLYUNIT
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByIsassemblyunitIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where isassemblyunit is not null
        defaultInventoryFiltering("isassemblyunit.specified=true", "isassemblyunit.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByAssemblyunitofIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where assemblyunitof equals to
        defaultInventoryFiltering("assemblyunitof.equals=" + DEFAULT_ASSEMBLYUNITOF, "assemblyunitof.equals=" + UPDATED_ASSEMBLYUNITOF);
    }

    @Test
    @Transactional
    void getAllInventoriesByAssemblyunitofIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where assemblyunitof in
        defaultInventoryFiltering(
            "assemblyunitof.in=" + DEFAULT_ASSEMBLYUNITOF + "," + UPDATED_ASSEMBLYUNITOF,
            "assemblyunitof.in=" + UPDATED_ASSEMBLYUNITOF
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAssemblyunitofIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where assemblyunitof is not null
        defaultInventoryFiltering("assemblyunitof.specified=true", "assemblyunitof.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByAssemblyunitofIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where assemblyunitof is greater than or equal to
        defaultInventoryFiltering(
            "assemblyunitof.greaterThanOrEqual=" + DEFAULT_ASSEMBLYUNITOF,
            "assemblyunitof.greaterThanOrEqual=" + UPDATED_ASSEMBLYUNITOF
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAssemblyunitofIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where assemblyunitof is less than or equal to
        defaultInventoryFiltering(
            "assemblyunitof.lessThanOrEqual=" + DEFAULT_ASSEMBLYUNITOF,
            "assemblyunitof.lessThanOrEqual=" + SMALLER_ASSEMBLYUNITOF
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAssemblyunitofIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where assemblyunitof is less than
        defaultInventoryFiltering("assemblyunitof.lessThan=" + UPDATED_ASSEMBLYUNITOF, "assemblyunitof.lessThan=" + DEFAULT_ASSEMBLYUNITOF);
    }

    @Test
    @Transactional
    void getAllInventoriesByAssemblyunitofIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where assemblyunitof is greater than
        defaultInventoryFiltering(
            "assemblyunitof.greaterThan=" + SMALLER_ASSEMBLYUNITOF,
            "assemblyunitof.greaterThan=" + DEFAULT_ASSEMBLYUNITOF
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderlevelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderlevel equals to
        defaultInventoryFiltering("reorderlevel.equals=" + DEFAULT_REORDERLEVEL, "reorderlevel.equals=" + UPDATED_REORDERLEVEL);
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderlevelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderlevel in
        defaultInventoryFiltering(
            "reorderlevel.in=" + DEFAULT_REORDERLEVEL + "," + UPDATED_REORDERLEVEL,
            "reorderlevel.in=" + UPDATED_REORDERLEVEL
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderlevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderlevel is not null
        defaultInventoryFiltering("reorderlevel.specified=true", "reorderlevel.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderlevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderlevel is greater than or equal to
        defaultInventoryFiltering(
            "reorderlevel.greaterThanOrEqual=" + DEFAULT_REORDERLEVEL,
            "reorderlevel.greaterThanOrEqual=" + UPDATED_REORDERLEVEL
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderlevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderlevel is less than or equal to
        defaultInventoryFiltering(
            "reorderlevel.lessThanOrEqual=" + DEFAULT_REORDERLEVEL,
            "reorderlevel.lessThanOrEqual=" + SMALLER_REORDERLEVEL
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderlevelIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderlevel is less than
        defaultInventoryFiltering("reorderlevel.lessThan=" + UPDATED_REORDERLEVEL, "reorderlevel.lessThan=" + DEFAULT_REORDERLEVEL);
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderlevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderlevel is greater than
        defaultInventoryFiltering("reorderlevel.greaterThan=" + SMALLER_REORDERLEVEL, "reorderlevel.greaterThan=" + DEFAULT_REORDERLEVEL);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastcostIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastcost equals to
        defaultInventoryFiltering("lastcost.equals=" + DEFAULT_LASTCOST, "lastcost.equals=" + UPDATED_LASTCOST);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastcostIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastcost in
        defaultInventoryFiltering("lastcost.in=" + DEFAULT_LASTCOST + "," + UPDATED_LASTCOST, "lastcost.in=" + UPDATED_LASTCOST);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastcostIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastcost is not null
        defaultInventoryFiltering("lastcost.specified=true", "lastcost.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByLastcostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastcost is greater than or equal to
        defaultInventoryFiltering("lastcost.greaterThanOrEqual=" + DEFAULT_LASTCOST, "lastcost.greaterThanOrEqual=" + UPDATED_LASTCOST);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastcostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastcost is less than or equal to
        defaultInventoryFiltering("lastcost.lessThanOrEqual=" + DEFAULT_LASTCOST, "lastcost.lessThanOrEqual=" + SMALLER_LASTCOST);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastcostIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastcost is less than
        defaultInventoryFiltering("lastcost.lessThan=" + UPDATED_LASTCOST, "lastcost.lessThan=" + DEFAULT_LASTCOST);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastcostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastcost is greater than
        defaultInventoryFiltering("lastcost.greaterThan=" + SMALLER_LASTCOST, "lastcost.greaterThan=" + DEFAULT_LASTCOST);
    }

    @Test
    @Transactional
    void getAllInventoriesByLastsellingpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastsellingprice equals to
        defaultInventoryFiltering(
            "lastsellingprice.equals=" + DEFAULT_LASTSELLINGPRICE,
            "lastsellingprice.equals=" + UPDATED_LASTSELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByLastsellingpriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastsellingprice in
        defaultInventoryFiltering(
            "lastsellingprice.in=" + DEFAULT_LASTSELLINGPRICE + "," + UPDATED_LASTSELLINGPRICE,
            "lastsellingprice.in=" + UPDATED_LASTSELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByLastsellingpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastsellingprice is not null
        defaultInventoryFiltering("lastsellingprice.specified=true", "lastsellingprice.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByLastsellingpriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastsellingprice is greater than or equal to
        defaultInventoryFiltering(
            "lastsellingprice.greaterThanOrEqual=" + DEFAULT_LASTSELLINGPRICE,
            "lastsellingprice.greaterThanOrEqual=" + UPDATED_LASTSELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByLastsellingpriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastsellingprice is less than or equal to
        defaultInventoryFiltering(
            "lastsellingprice.lessThanOrEqual=" + DEFAULT_LASTSELLINGPRICE,
            "lastsellingprice.lessThanOrEqual=" + SMALLER_LASTSELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByLastsellingpriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastsellingprice is less than
        defaultInventoryFiltering(
            "lastsellingprice.lessThan=" + UPDATED_LASTSELLINGPRICE,
            "lastsellingprice.lessThan=" + DEFAULT_LASTSELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByLastsellingpriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastsellingprice is greater than
        defaultInventoryFiltering(
            "lastsellingprice.greaterThan=" + SMALLER_LASTSELLINGPRICE,
            "lastsellingprice.greaterThan=" + DEFAULT_LASTSELLINGPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmu equals to
        defaultInventoryFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllInventoriesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmu in
        defaultInventoryFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllInventoriesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmu is not null
        defaultInventoryFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmu is greater than or equal to
        defaultInventoryFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllInventoriesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmu is less than or equal to
        defaultInventoryFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllInventoriesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmu is less than
        defaultInventoryFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllInventoriesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmu is greater than
        defaultInventoryFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllInventoriesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmd equals to
        defaultInventoryFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllInventoriesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmd in
        defaultInventoryFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllInventoriesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lmd is not null
        defaultInventoryFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByAvailablequantityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where availablequantity equals to
        defaultInventoryFiltering(
            "availablequantity.equals=" + DEFAULT_AVAILABLEQUANTITY,
            "availablequantity.equals=" + UPDATED_AVAILABLEQUANTITY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAvailablequantityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where availablequantity in
        defaultInventoryFiltering(
            "availablequantity.in=" + DEFAULT_AVAILABLEQUANTITY + "," + UPDATED_AVAILABLEQUANTITY,
            "availablequantity.in=" + UPDATED_AVAILABLEQUANTITY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAvailablequantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where availablequantity is not null
        defaultInventoryFiltering("availablequantity.specified=true", "availablequantity.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByAvailablequantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where availablequantity is greater than or equal to
        defaultInventoryFiltering(
            "availablequantity.greaterThanOrEqual=" + DEFAULT_AVAILABLEQUANTITY,
            "availablequantity.greaterThanOrEqual=" + UPDATED_AVAILABLEQUANTITY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAvailablequantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where availablequantity is less than or equal to
        defaultInventoryFiltering(
            "availablequantity.lessThanOrEqual=" + DEFAULT_AVAILABLEQUANTITY,
            "availablequantity.lessThanOrEqual=" + SMALLER_AVAILABLEQUANTITY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAvailablequantityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where availablequantity is less than
        defaultInventoryFiltering(
            "availablequantity.lessThan=" + UPDATED_AVAILABLEQUANTITY,
            "availablequantity.lessThan=" + DEFAULT_AVAILABLEQUANTITY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAvailablequantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where availablequantity is greater than
        defaultInventoryFiltering(
            "availablequantity.greaterThan=" + SMALLER_AVAILABLEQUANTITY,
            "availablequantity.greaterThan=" + DEFAULT_AVAILABLEQUANTITY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByHasbatchesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where hasbatches equals to
        defaultInventoryFiltering("hasbatches.equals=" + DEFAULT_HASBATCHES, "hasbatches.equals=" + UPDATED_HASBATCHES);
    }

    @Test
    @Transactional
    void getAllInventoriesByHasbatchesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where hasbatches in
        defaultInventoryFiltering("hasbatches.in=" + DEFAULT_HASBATCHES + "," + UPDATED_HASBATCHES, "hasbatches.in=" + UPDATED_HASBATCHES);
    }

    @Test
    @Transactional
    void getAllInventoriesByHasbatchesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where hasbatches is not null
        defaultInventoryFiltering("hasbatches.specified=true", "hasbatches.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByItemspecfilepathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemspecfilepath equals to
        defaultInventoryFiltering(
            "itemspecfilepath.equals=" + DEFAULT_ITEMSPECFILEPATH,
            "itemspecfilepath.equals=" + UPDATED_ITEMSPECFILEPATH
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByItemspecfilepathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemspecfilepath in
        defaultInventoryFiltering(
            "itemspecfilepath.in=" + DEFAULT_ITEMSPECFILEPATH + "," + UPDATED_ITEMSPECFILEPATH,
            "itemspecfilepath.in=" + UPDATED_ITEMSPECFILEPATH
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByItemspecfilepathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemspecfilepath is not null
        defaultInventoryFiltering("itemspecfilepath.specified=true", "itemspecfilepath.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByItemspecfilepathContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemspecfilepath contains
        defaultInventoryFiltering(
            "itemspecfilepath.contains=" + DEFAULT_ITEMSPECFILEPATH,
            "itemspecfilepath.contains=" + UPDATED_ITEMSPECFILEPATH
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByItemspecfilepathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemspecfilepath does not contain
        defaultInventoryFiltering(
            "itemspecfilepath.doesNotContain=" + UPDATED_ITEMSPECFILEPATH,
            "itemspecfilepath.doesNotContain=" + DEFAULT_ITEMSPECFILEPATH
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByItemimagepathIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemimagepath equals to
        defaultInventoryFiltering("itemimagepath.equals=" + DEFAULT_ITEMIMAGEPATH, "itemimagepath.equals=" + UPDATED_ITEMIMAGEPATH);
    }

    @Test
    @Transactional
    void getAllInventoriesByItemimagepathIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemimagepath in
        defaultInventoryFiltering(
            "itemimagepath.in=" + DEFAULT_ITEMIMAGEPATH + "," + UPDATED_ITEMIMAGEPATH,
            "itemimagepath.in=" + UPDATED_ITEMIMAGEPATH
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByItemimagepathIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemimagepath is not null
        defaultInventoryFiltering("itemimagepath.specified=true", "itemimagepath.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByItemimagepathContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemimagepath contains
        defaultInventoryFiltering("itemimagepath.contains=" + DEFAULT_ITEMIMAGEPATH, "itemimagepath.contains=" + UPDATED_ITEMIMAGEPATH);
    }

    @Test
    @Transactional
    void getAllInventoriesByItemimagepathNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemimagepath does not contain
        defaultInventoryFiltering(
            "itemimagepath.doesNotContain=" + UPDATED_ITEMIMAGEPATH,
            "itemimagepath.doesNotContain=" + DEFAULT_ITEMIMAGEPATH
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReturnpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where returnprice equals to
        defaultInventoryFiltering("returnprice.equals=" + DEFAULT_RETURNPRICE, "returnprice.equals=" + UPDATED_RETURNPRICE);
    }

    @Test
    @Transactional
    void getAllInventoriesByReturnpriceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where returnprice in
        defaultInventoryFiltering(
            "returnprice.in=" + DEFAULT_RETURNPRICE + "," + UPDATED_RETURNPRICE,
            "returnprice.in=" + UPDATED_RETURNPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReturnpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where returnprice is not null
        defaultInventoryFiltering("returnprice.specified=true", "returnprice.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByReturnpriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where returnprice is greater than or equal to
        defaultInventoryFiltering(
            "returnprice.greaterThanOrEqual=" + DEFAULT_RETURNPRICE,
            "returnprice.greaterThanOrEqual=" + UPDATED_RETURNPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReturnpriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where returnprice is less than or equal to
        defaultInventoryFiltering(
            "returnprice.lessThanOrEqual=" + DEFAULT_RETURNPRICE,
            "returnprice.lessThanOrEqual=" + SMALLER_RETURNPRICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReturnpriceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where returnprice is less than
        defaultInventoryFiltering("returnprice.lessThan=" + UPDATED_RETURNPRICE, "returnprice.lessThan=" + DEFAULT_RETURNPRICE);
    }

    @Test
    @Transactional
    void getAllInventoriesByReturnpriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where returnprice is greater than
        defaultInventoryFiltering("returnprice.greaterThan=" + SMALLER_RETURNPRICE, "returnprice.greaterThan=" + DEFAULT_RETURNPRICE);
    }

    @Test
    @Transactional
    void getAllInventoriesByActiveitemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where activeitem equals to
        defaultInventoryFiltering("activeitem.equals=" + DEFAULT_ACTIVEITEM, "activeitem.equals=" + UPDATED_ACTIVEITEM);
    }

    @Test
    @Transactional
    void getAllInventoriesByActiveitemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where activeitem in
        defaultInventoryFiltering("activeitem.in=" + DEFAULT_ACTIVEITEM + "," + UPDATED_ACTIVEITEM, "activeitem.in=" + UPDATED_ACTIVEITEM);
    }

    @Test
    @Transactional
    void getAllInventoriesByActiveitemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where activeitem is not null
        defaultInventoryFiltering("activeitem.specified=true", "activeitem.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByMinstockIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where minstock equals to
        defaultInventoryFiltering("minstock.equals=" + DEFAULT_MINSTOCK, "minstock.equals=" + UPDATED_MINSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMinstockIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where minstock in
        defaultInventoryFiltering("minstock.in=" + DEFAULT_MINSTOCK + "," + UPDATED_MINSTOCK, "minstock.in=" + UPDATED_MINSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMinstockIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where minstock is not null
        defaultInventoryFiltering("minstock.specified=true", "minstock.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByMinstockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where minstock is greater than or equal to
        defaultInventoryFiltering("minstock.greaterThanOrEqual=" + DEFAULT_MINSTOCK, "minstock.greaterThanOrEqual=" + UPDATED_MINSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMinstockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where minstock is less than or equal to
        defaultInventoryFiltering("minstock.lessThanOrEqual=" + DEFAULT_MINSTOCK, "minstock.lessThanOrEqual=" + SMALLER_MINSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMinstockIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where minstock is less than
        defaultInventoryFiltering("minstock.lessThan=" + UPDATED_MINSTOCK, "minstock.lessThan=" + DEFAULT_MINSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMinstockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where minstock is greater than
        defaultInventoryFiltering("minstock.greaterThan=" + SMALLER_MINSTOCK, "minstock.greaterThan=" + DEFAULT_MINSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMaxstockIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where maxstock equals to
        defaultInventoryFiltering("maxstock.equals=" + DEFAULT_MAXSTOCK, "maxstock.equals=" + UPDATED_MAXSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMaxstockIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where maxstock in
        defaultInventoryFiltering("maxstock.in=" + DEFAULT_MAXSTOCK + "," + UPDATED_MAXSTOCK, "maxstock.in=" + UPDATED_MAXSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMaxstockIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where maxstock is not null
        defaultInventoryFiltering("maxstock.specified=true", "maxstock.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByMaxstockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where maxstock is greater than or equal to
        defaultInventoryFiltering("maxstock.greaterThanOrEqual=" + DEFAULT_MAXSTOCK, "maxstock.greaterThanOrEqual=" + UPDATED_MAXSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMaxstockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where maxstock is less than or equal to
        defaultInventoryFiltering("maxstock.lessThanOrEqual=" + DEFAULT_MAXSTOCK, "maxstock.lessThanOrEqual=" + SMALLER_MAXSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMaxstockIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where maxstock is less than
        defaultInventoryFiltering("maxstock.lessThan=" + UPDATED_MAXSTOCK, "maxstock.lessThan=" + DEFAULT_MAXSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByMaxstockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where maxstock is greater than
        defaultInventoryFiltering("maxstock.greaterThan=" + SMALLER_MAXSTOCK, "maxstock.greaterThan=" + DEFAULT_MAXSTOCK);
    }

    @Test
    @Transactional
    void getAllInventoriesByDailyaverageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where dailyaverage equals to
        defaultInventoryFiltering("dailyaverage.equals=" + DEFAULT_DAILYAVERAGE, "dailyaverage.equals=" + UPDATED_DAILYAVERAGE);
    }

    @Test
    @Transactional
    void getAllInventoriesByDailyaverageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where dailyaverage in
        defaultInventoryFiltering(
            "dailyaverage.in=" + DEFAULT_DAILYAVERAGE + "," + UPDATED_DAILYAVERAGE,
            "dailyaverage.in=" + UPDATED_DAILYAVERAGE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDailyaverageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where dailyaverage is not null
        defaultInventoryFiltering("dailyaverage.specified=true", "dailyaverage.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByDailyaverageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where dailyaverage is greater than or equal to
        defaultInventoryFiltering(
            "dailyaverage.greaterThanOrEqual=" + DEFAULT_DAILYAVERAGE,
            "dailyaverage.greaterThanOrEqual=" + UPDATED_DAILYAVERAGE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDailyaverageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where dailyaverage is less than or equal to
        defaultInventoryFiltering(
            "dailyaverage.lessThanOrEqual=" + DEFAULT_DAILYAVERAGE,
            "dailyaverage.lessThanOrEqual=" + SMALLER_DAILYAVERAGE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDailyaverageIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where dailyaverage is less than
        defaultInventoryFiltering("dailyaverage.lessThan=" + UPDATED_DAILYAVERAGE, "dailyaverage.lessThan=" + DEFAULT_DAILYAVERAGE);
    }

    @Test
    @Transactional
    void getAllInventoriesByDailyaverageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where dailyaverage is greater than
        defaultInventoryFiltering("dailyaverage.greaterThan=" + SMALLER_DAILYAVERAGE, "dailyaverage.greaterThan=" + DEFAULT_DAILYAVERAGE);
    }

    @Test
    @Transactional
    void getAllInventoriesByBufferlevelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where bufferlevel equals to
        defaultInventoryFiltering("bufferlevel.equals=" + DEFAULT_BUFFERLEVEL, "bufferlevel.equals=" + UPDATED_BUFFERLEVEL);
    }

    @Test
    @Transactional
    void getAllInventoriesByBufferlevelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where bufferlevel in
        defaultInventoryFiltering(
            "bufferlevel.in=" + DEFAULT_BUFFERLEVEL + "," + UPDATED_BUFFERLEVEL,
            "bufferlevel.in=" + UPDATED_BUFFERLEVEL
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByBufferlevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where bufferlevel is not null
        defaultInventoryFiltering("bufferlevel.specified=true", "bufferlevel.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByBufferlevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where bufferlevel is greater than or equal to
        defaultInventoryFiltering(
            "bufferlevel.greaterThanOrEqual=" + DEFAULT_BUFFERLEVEL,
            "bufferlevel.greaterThanOrEqual=" + UPDATED_BUFFERLEVEL
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByBufferlevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where bufferlevel is less than or equal to
        defaultInventoryFiltering(
            "bufferlevel.lessThanOrEqual=" + DEFAULT_BUFFERLEVEL,
            "bufferlevel.lessThanOrEqual=" + SMALLER_BUFFERLEVEL
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByBufferlevelIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where bufferlevel is less than
        defaultInventoryFiltering("bufferlevel.lessThan=" + UPDATED_BUFFERLEVEL, "bufferlevel.lessThan=" + DEFAULT_BUFFERLEVEL);
    }

    @Test
    @Transactional
    void getAllInventoriesByBufferlevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where bufferlevel is greater than
        defaultInventoryFiltering("bufferlevel.greaterThan=" + SMALLER_BUFFERLEVEL, "bufferlevel.greaterThan=" + DEFAULT_BUFFERLEVEL);
    }

    @Test
    @Transactional
    void getAllInventoriesByLeadtimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where leadtime equals to
        defaultInventoryFiltering("leadtime.equals=" + DEFAULT_LEADTIME, "leadtime.equals=" + UPDATED_LEADTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByLeadtimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where leadtime in
        defaultInventoryFiltering("leadtime.in=" + DEFAULT_LEADTIME + "," + UPDATED_LEADTIME, "leadtime.in=" + UPDATED_LEADTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByLeadtimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where leadtime is not null
        defaultInventoryFiltering("leadtime.specified=true", "leadtime.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByLeadtimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where leadtime is greater than or equal to
        defaultInventoryFiltering("leadtime.greaterThanOrEqual=" + DEFAULT_LEADTIME, "leadtime.greaterThanOrEqual=" + UPDATED_LEADTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByLeadtimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where leadtime is less than or equal to
        defaultInventoryFiltering("leadtime.lessThanOrEqual=" + DEFAULT_LEADTIME, "leadtime.lessThanOrEqual=" + SMALLER_LEADTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByLeadtimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where leadtime is less than
        defaultInventoryFiltering("leadtime.lessThan=" + UPDATED_LEADTIME, "leadtime.lessThan=" + DEFAULT_LEADTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByLeadtimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where leadtime is greater than
        defaultInventoryFiltering("leadtime.greaterThan=" + SMALLER_LEADTIME, "leadtime.greaterThan=" + DEFAULT_LEADTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByBuffertimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where buffertime equals to
        defaultInventoryFiltering("buffertime.equals=" + DEFAULT_BUFFERTIME, "buffertime.equals=" + UPDATED_BUFFERTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByBuffertimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where buffertime in
        defaultInventoryFiltering("buffertime.in=" + DEFAULT_BUFFERTIME + "," + UPDATED_BUFFERTIME, "buffertime.in=" + UPDATED_BUFFERTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByBuffertimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where buffertime is not null
        defaultInventoryFiltering("buffertime.specified=true", "buffertime.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByBuffertimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where buffertime is greater than or equal to
        defaultInventoryFiltering(
            "buffertime.greaterThanOrEqual=" + DEFAULT_BUFFERTIME,
            "buffertime.greaterThanOrEqual=" + UPDATED_BUFFERTIME
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByBuffertimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where buffertime is less than or equal to
        defaultInventoryFiltering("buffertime.lessThanOrEqual=" + DEFAULT_BUFFERTIME, "buffertime.lessThanOrEqual=" + SMALLER_BUFFERTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByBuffertimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where buffertime is less than
        defaultInventoryFiltering("buffertime.lessThan=" + UPDATED_BUFFERTIME, "buffertime.lessThan=" + DEFAULT_BUFFERTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesByBuffertimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where buffertime is greater than
        defaultInventoryFiltering("buffertime.greaterThan=" + SMALLER_BUFFERTIME, "buffertime.greaterThan=" + DEFAULT_BUFFERTIME);
    }

    @Test
    @Transactional
    void getAllInventoriesBySaftydaysIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where saftydays equals to
        defaultInventoryFiltering("saftydays.equals=" + DEFAULT_SAFTYDAYS, "saftydays.equals=" + UPDATED_SAFTYDAYS);
    }

    @Test
    @Transactional
    void getAllInventoriesBySaftydaysIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where saftydays in
        defaultInventoryFiltering("saftydays.in=" + DEFAULT_SAFTYDAYS + "," + UPDATED_SAFTYDAYS, "saftydays.in=" + UPDATED_SAFTYDAYS);
    }

    @Test
    @Transactional
    void getAllInventoriesBySaftydaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where saftydays is not null
        defaultInventoryFiltering("saftydays.specified=true", "saftydays.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesBySaftydaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where saftydays is greater than or equal to
        defaultInventoryFiltering("saftydays.greaterThanOrEqual=" + DEFAULT_SAFTYDAYS, "saftydays.greaterThanOrEqual=" + UPDATED_SAFTYDAYS);
    }

    @Test
    @Transactional
    void getAllInventoriesBySaftydaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where saftydays is less than or equal to
        defaultInventoryFiltering("saftydays.lessThanOrEqual=" + DEFAULT_SAFTYDAYS, "saftydays.lessThanOrEqual=" + SMALLER_SAFTYDAYS);
    }

    @Test
    @Transactional
    void getAllInventoriesBySaftydaysIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where saftydays is less than
        defaultInventoryFiltering("saftydays.lessThan=" + UPDATED_SAFTYDAYS, "saftydays.lessThan=" + DEFAULT_SAFTYDAYS);
    }

    @Test
    @Transactional
    void getAllInventoriesBySaftydaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where saftydays is greater than
        defaultInventoryFiltering("saftydays.greaterThan=" + SMALLER_SAFTYDAYS, "saftydays.greaterThan=" + DEFAULT_SAFTYDAYS);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountcode equals to
        defaultInventoryFiltering("accountcode.equals=" + DEFAULT_ACCOUNTCODE, "accountcode.equals=" + UPDATED_ACCOUNTCODE);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountcodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountcode in
        defaultInventoryFiltering(
            "accountcode.in=" + DEFAULT_ACCOUNTCODE + "," + UPDATED_ACCOUNTCODE,
            "accountcode.in=" + UPDATED_ACCOUNTCODE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountcode is not null
        defaultInventoryFiltering("accountcode.specified=true", "accountcode.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountcodeContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountcode contains
        defaultInventoryFiltering("accountcode.contains=" + DEFAULT_ACCOUNTCODE, "accountcode.contains=" + UPDATED_ACCOUNTCODE);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountcodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountcode does not contain
        defaultInventoryFiltering("accountcode.doesNotContain=" + UPDATED_ACCOUNTCODE, "accountcode.doesNotContain=" + DEFAULT_ACCOUNTCODE);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountid equals to
        defaultInventoryFiltering("accountid.equals=" + DEFAULT_ACCOUNTID, "accountid.equals=" + UPDATED_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountid in
        defaultInventoryFiltering("accountid.in=" + DEFAULT_ACCOUNTID + "," + UPDATED_ACCOUNTID, "accountid.in=" + UPDATED_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountid is not null
        defaultInventoryFiltering("accountid.specified=true", "accountid.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountid is greater than or equal to
        defaultInventoryFiltering("accountid.greaterThanOrEqual=" + DEFAULT_ACCOUNTID, "accountid.greaterThanOrEqual=" + UPDATED_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountid is less than or equal to
        defaultInventoryFiltering("accountid.lessThanOrEqual=" + DEFAULT_ACCOUNTID, "accountid.lessThanOrEqual=" + SMALLER_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountid is less than
        defaultInventoryFiltering("accountid.lessThan=" + UPDATED_ACCOUNTID, "accountid.lessThan=" + DEFAULT_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllInventoriesByAccountidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where accountid is greater than
        defaultInventoryFiltering("accountid.greaterThan=" + SMALLER_ACCOUNTID, "accountid.greaterThan=" + DEFAULT_ACCOUNTID);
    }

    @Test
    @Transactional
    void getAllInventoriesByCasepackqtyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where casepackqty equals to
        defaultInventoryFiltering("casepackqty.equals=" + DEFAULT_CASEPACKQTY, "casepackqty.equals=" + UPDATED_CASEPACKQTY);
    }

    @Test
    @Transactional
    void getAllInventoriesByCasepackqtyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where casepackqty in
        defaultInventoryFiltering(
            "casepackqty.in=" + DEFAULT_CASEPACKQTY + "," + UPDATED_CASEPACKQTY,
            "casepackqty.in=" + UPDATED_CASEPACKQTY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByCasepackqtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where casepackqty is not null
        defaultInventoryFiltering("casepackqty.specified=true", "casepackqty.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByCasepackqtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where casepackqty is greater than or equal to
        defaultInventoryFiltering(
            "casepackqty.greaterThanOrEqual=" + DEFAULT_CASEPACKQTY,
            "casepackqty.greaterThanOrEqual=" + UPDATED_CASEPACKQTY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByCasepackqtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where casepackqty is less than or equal to
        defaultInventoryFiltering(
            "casepackqty.lessThanOrEqual=" + DEFAULT_CASEPACKQTY,
            "casepackqty.lessThanOrEqual=" + SMALLER_CASEPACKQTY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByCasepackqtyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where casepackqty is less than
        defaultInventoryFiltering("casepackqty.lessThan=" + UPDATED_CASEPACKQTY, "casepackqty.lessThan=" + DEFAULT_CASEPACKQTY);
    }

    @Test
    @Transactional
    void getAllInventoriesByCasepackqtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where casepackqty is greater than
        defaultInventoryFiltering("casepackqty.greaterThan=" + SMALLER_CASEPACKQTY, "casepackqty.greaterThan=" + DEFAULT_CASEPACKQTY);
    }

    @Test
    @Transactional
    void getAllInventoriesByIsregisteredIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where isregistered equals to
        defaultInventoryFiltering("isregistered.equals=" + DEFAULT_ISREGISTERED, "isregistered.equals=" + UPDATED_ISREGISTERED);
    }

    @Test
    @Transactional
    void getAllInventoriesByIsregisteredIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where isregistered in
        defaultInventoryFiltering(
            "isregistered.in=" + DEFAULT_ISREGISTERED + "," + UPDATED_ISREGISTERED,
            "isregistered.in=" + UPDATED_ISREGISTERED
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByIsregisteredIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where isregistered is not null
        defaultInventoryFiltering("isregistered.specified=true", "isregistered.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByDefaultstocklocationidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where defaultstocklocationid equals to
        defaultInventoryFiltering(
            "defaultstocklocationid.equals=" + DEFAULT_DEFAULTSTOCKLOCATIONID,
            "defaultstocklocationid.equals=" + UPDATED_DEFAULTSTOCKLOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDefaultstocklocationidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where defaultstocklocationid in
        defaultInventoryFiltering(
            "defaultstocklocationid.in=" + DEFAULT_DEFAULTSTOCKLOCATIONID + "," + UPDATED_DEFAULTSTOCKLOCATIONID,
            "defaultstocklocationid.in=" + UPDATED_DEFAULTSTOCKLOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDefaultstocklocationidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where defaultstocklocationid is not null
        defaultInventoryFiltering("defaultstocklocationid.specified=true", "defaultstocklocationid.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByDefaultstocklocationidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where defaultstocklocationid is greater than or equal to
        defaultInventoryFiltering(
            "defaultstocklocationid.greaterThanOrEqual=" + DEFAULT_DEFAULTSTOCKLOCATIONID,
            "defaultstocklocationid.greaterThanOrEqual=" + UPDATED_DEFAULTSTOCKLOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDefaultstocklocationidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where defaultstocklocationid is less than or equal to
        defaultInventoryFiltering(
            "defaultstocklocationid.lessThanOrEqual=" + DEFAULT_DEFAULTSTOCKLOCATIONID,
            "defaultstocklocationid.lessThanOrEqual=" + SMALLER_DEFAULTSTOCKLOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDefaultstocklocationidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where defaultstocklocationid is less than
        defaultInventoryFiltering(
            "defaultstocklocationid.lessThan=" + UPDATED_DEFAULTSTOCKLOCATIONID,
            "defaultstocklocationid.lessThan=" + DEFAULT_DEFAULTSTOCKLOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByDefaultstocklocationidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where defaultstocklocationid is greater than
        defaultInventoryFiltering(
            "defaultstocklocationid.greaterThan=" + SMALLER_DEFAULTSTOCKLOCATIONID,
            "defaultstocklocationid.greaterThan=" + DEFAULT_DEFAULTSTOCKLOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByRacknoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where rackno equals to
        defaultInventoryFiltering("rackno.equals=" + DEFAULT_RACKNO, "rackno.equals=" + UPDATED_RACKNO);
    }

    @Test
    @Transactional
    void getAllInventoriesByRacknoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where rackno in
        defaultInventoryFiltering("rackno.in=" + DEFAULT_RACKNO + "," + UPDATED_RACKNO, "rackno.in=" + UPDATED_RACKNO);
    }

    @Test
    @Transactional
    void getAllInventoriesByRacknoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where rackno is not null
        defaultInventoryFiltering("rackno.specified=true", "rackno.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByRacknoContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where rackno contains
        defaultInventoryFiltering("rackno.contains=" + DEFAULT_RACKNO, "rackno.contains=" + UPDATED_RACKNO);
    }

    @Test
    @Transactional
    void getAllInventoriesByRacknoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where rackno does not contain
        defaultInventoryFiltering("rackno.doesNotContain=" + UPDATED_RACKNO, "rackno.doesNotContain=" + DEFAULT_RACKNO);
    }

    @Test
    @Transactional
    void getAllInventoriesByCommissionempidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where commissionempid equals to
        defaultInventoryFiltering("commissionempid.equals=" + DEFAULT_COMMISSIONEMPID, "commissionempid.equals=" + UPDATED_COMMISSIONEMPID);
    }

    @Test
    @Transactional
    void getAllInventoriesByCommissionempidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where commissionempid in
        defaultInventoryFiltering(
            "commissionempid.in=" + DEFAULT_COMMISSIONEMPID + "," + UPDATED_COMMISSIONEMPID,
            "commissionempid.in=" + UPDATED_COMMISSIONEMPID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByCommissionempidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where commissionempid is not null
        defaultInventoryFiltering("commissionempid.specified=true", "commissionempid.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByCommissionempidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where commissionempid is greater than or equal to
        defaultInventoryFiltering(
            "commissionempid.greaterThanOrEqual=" + DEFAULT_COMMISSIONEMPID,
            "commissionempid.greaterThanOrEqual=" + UPDATED_COMMISSIONEMPID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByCommissionempidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where commissionempid is less than or equal to
        defaultInventoryFiltering(
            "commissionempid.lessThanOrEqual=" + DEFAULT_COMMISSIONEMPID,
            "commissionempid.lessThanOrEqual=" + SMALLER_COMMISSIONEMPID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByCommissionempidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where commissionempid is less than
        defaultInventoryFiltering(
            "commissionempid.lessThan=" + UPDATED_COMMISSIONEMPID,
            "commissionempid.lessThan=" + DEFAULT_COMMISSIONEMPID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByCommissionempidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where commissionempid is greater than
        defaultInventoryFiltering(
            "commissionempid.greaterThan=" + SMALLER_COMMISSIONEMPID,
            "commissionempid.greaterThan=" + DEFAULT_COMMISSIONEMPID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktypeid equals to
        defaultInventoryFiltering("checktypeid.equals=" + DEFAULT_CHECKTYPEID, "checktypeid.equals=" + UPDATED_CHECKTYPEID);
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktypeid in
        defaultInventoryFiltering(
            "checktypeid.in=" + DEFAULT_CHECKTYPEID + "," + UPDATED_CHECKTYPEID,
            "checktypeid.in=" + UPDATED_CHECKTYPEID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktypeid is not null
        defaultInventoryFiltering("checktypeid.specified=true", "checktypeid.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktypeid is greater than or equal to
        defaultInventoryFiltering(
            "checktypeid.greaterThanOrEqual=" + DEFAULT_CHECKTYPEID,
            "checktypeid.greaterThanOrEqual=" + UPDATED_CHECKTYPEID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktypeid is less than or equal to
        defaultInventoryFiltering(
            "checktypeid.lessThanOrEqual=" + DEFAULT_CHECKTYPEID,
            "checktypeid.lessThanOrEqual=" + SMALLER_CHECKTYPEID
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktypeid is less than
        defaultInventoryFiltering("checktypeid.lessThan=" + UPDATED_CHECKTYPEID, "checktypeid.lessThan=" + DEFAULT_CHECKTYPEID);
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktypeid is greater than
        defaultInventoryFiltering("checktypeid.greaterThan=" + SMALLER_CHECKTYPEID, "checktypeid.greaterThan=" + DEFAULT_CHECKTYPEID);
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktype equals to
        defaultInventoryFiltering("checktype.equals=" + DEFAULT_CHECKTYPE, "checktype.equals=" + UPDATED_CHECKTYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktype in
        defaultInventoryFiltering("checktype.in=" + DEFAULT_CHECKTYPE + "," + UPDATED_CHECKTYPE, "checktype.in=" + UPDATED_CHECKTYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktype is not null
        defaultInventoryFiltering("checktype.specified=true", "checktype.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktype contains
        defaultInventoryFiltering("checktype.contains=" + DEFAULT_CHECKTYPE, "checktype.contains=" + UPDATED_CHECKTYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByChecktypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where checktype does not contain
        defaultInventoryFiltering("checktype.doesNotContain=" + UPDATED_CHECKTYPE, "checktype.doesNotContain=" + DEFAULT_CHECKTYPE);
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderqtyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderqty equals to
        defaultInventoryFiltering("reorderqty.equals=" + DEFAULT_REORDERQTY, "reorderqty.equals=" + UPDATED_REORDERQTY);
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderqtyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderqty in
        defaultInventoryFiltering("reorderqty.in=" + DEFAULT_REORDERQTY + "," + UPDATED_REORDERQTY, "reorderqty.in=" + UPDATED_REORDERQTY);
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderqtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderqty is not null
        defaultInventoryFiltering("reorderqty.specified=true", "reorderqty.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderqtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderqty is greater than or equal to
        defaultInventoryFiltering(
            "reorderqty.greaterThanOrEqual=" + DEFAULT_REORDERQTY,
            "reorderqty.greaterThanOrEqual=" + UPDATED_REORDERQTY
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderqtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderqty is less than or equal to
        defaultInventoryFiltering("reorderqty.lessThanOrEqual=" + DEFAULT_REORDERQTY, "reorderqty.lessThanOrEqual=" + SMALLER_REORDERQTY);
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderqtyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderqty is less than
        defaultInventoryFiltering("reorderqty.lessThan=" + UPDATED_REORDERQTY, "reorderqty.lessThan=" + DEFAULT_REORDERQTY);
    }

    @Test
    @Transactional
    void getAllInventoriesByReorderqtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reorderqty is greater than
        defaultInventoryFiltering("reorderqty.greaterThan=" + SMALLER_REORDERQTY, "reorderqty.greaterThan=" + DEFAULT_REORDERQTY);
    }

    @Test
    @Transactional
    void getAllInventoriesByNotininvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where notininvoice equals to
        defaultInventoryFiltering("notininvoice.equals=" + DEFAULT_NOTININVOICE, "notininvoice.equals=" + UPDATED_NOTININVOICE);
    }

    @Test
    @Transactional
    void getAllInventoriesByNotininvoiceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where notininvoice in
        defaultInventoryFiltering(
            "notininvoice.in=" + DEFAULT_NOTININVOICE + "," + UPDATED_NOTININVOICE,
            "notininvoice.in=" + UPDATED_NOTININVOICE
        );
    }

    @Test
    @Transactional
    void getAllInventoriesByNotininvoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where notininvoice is not null
        defaultInventoryFiltering("notininvoice.specified=true", "notininvoice.specified=false");
    }

    private void defaultInventoryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultInventoryShouldBeFound(shouldBeFound);
        defaultInventoryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryShouldBeFound(String filter) throws Exception {
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].partnumber").value(hasItem(DEFAULT_PARTNUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].classification1").value(hasItem(DEFAULT_CLASSIFICATION_1)))
            .andExpect(jsonPath("$.[*].classification2").value(hasItem(DEFAULT_CLASSIFICATION_2)))
            .andExpect(jsonPath("$.[*].classification3").value(hasItem(DEFAULT_CLASSIFICATION_3)))
            .andExpect(jsonPath("$.[*].classification4").value(hasItem(DEFAULT_CLASSIFICATION_4)))
            .andExpect(jsonPath("$.[*].classification5").value(hasItem(DEFAULT_CLASSIFICATION_5)))
            .andExpect(jsonPath("$.[*].unitofmeasurement").value(hasItem(DEFAULT_UNITOFMEASUREMENT)))
            .andExpect(jsonPath("$.[*].decimalplaces").value(hasItem(DEFAULT_DECIMALPLACES)))
            .andExpect(jsonPath("$.[*].isassemblyunit").value(hasItem(DEFAULT_ISASSEMBLYUNIT.booleanValue())))
            .andExpect(jsonPath("$.[*].assemblyunitof").value(hasItem(DEFAULT_ASSEMBLYUNITOF)))
            .andExpect(jsonPath("$.[*].reorderlevel").value(hasItem(DEFAULT_REORDERLEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].lastcost").value(hasItem(DEFAULT_LASTCOST.doubleValue())))
            .andExpect(jsonPath("$.[*].lastsellingprice").value(hasItem(DEFAULT_LASTSELLINGPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].availablequantity").value(hasItem(DEFAULT_AVAILABLEQUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].hasbatches").value(hasItem(DEFAULT_HASBATCHES.booleanValue())))
            .andExpect(jsonPath("$.[*].itemspecfilepath").value(hasItem(DEFAULT_ITEMSPECFILEPATH)))
            .andExpect(jsonPath("$.[*].itemimagepath").value(hasItem(DEFAULT_ITEMIMAGEPATH)))
            .andExpect(jsonPath("$.[*].returnprice").value(hasItem(DEFAULT_RETURNPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].activeitem").value(hasItem(DEFAULT_ACTIVEITEM.booleanValue())))
            .andExpect(jsonPath("$.[*].minstock").value(hasItem(DEFAULT_MINSTOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].maxstock").value(hasItem(DEFAULT_MAXSTOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].dailyaverage").value(hasItem(DEFAULT_DAILYAVERAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].bufferlevel").value(hasItem(DEFAULT_BUFFERLEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].leadtime").value(hasItem(DEFAULT_LEADTIME.doubleValue())))
            .andExpect(jsonPath("$.[*].buffertime").value(hasItem(DEFAULT_BUFFERTIME.doubleValue())))
            .andExpect(jsonPath("$.[*].saftydays").value(hasItem(DEFAULT_SAFTYDAYS.doubleValue())))
            .andExpect(jsonPath("$.[*].accountcode").value(hasItem(DEFAULT_ACCOUNTCODE)))
            .andExpect(jsonPath("$.[*].accountid").value(hasItem(DEFAULT_ACCOUNTID)))
            .andExpect(jsonPath("$.[*].casepackqty").value(hasItem(DEFAULT_CASEPACKQTY)))
            .andExpect(jsonPath("$.[*].isregistered").value(hasItem(DEFAULT_ISREGISTERED.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultstocklocationid").value(hasItem(DEFAULT_DEFAULTSTOCKLOCATIONID)))
            .andExpect(jsonPath("$.[*].rackno").value(hasItem(DEFAULT_RACKNO)))
            .andExpect(jsonPath("$.[*].barcodeimageContentType").value(hasItem(DEFAULT_BARCODEIMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].barcodeimage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_BARCODEIMAGE))))
            .andExpect(jsonPath("$.[*].commissionempid").value(hasItem(DEFAULT_COMMISSIONEMPID)))
            .andExpect(jsonPath("$.[*].checktypeid").value(hasItem(DEFAULT_CHECKTYPEID)))
            .andExpect(jsonPath("$.[*].checktype").value(hasItem(DEFAULT_CHECKTYPE)))
            .andExpect(jsonPath("$.[*].reorderqty").value(hasItem(DEFAULT_REORDERQTY.doubleValue())))
            .andExpect(jsonPath("$.[*].notininvoice").value(hasItem(DEFAULT_NOTININVOICE.booleanValue())));

        // Check, that the count call also returns 1
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryShouldNotBeFound(String filter) throws Exception {
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInventory() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findById(inventory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInventory are not directly saved in db
        em.detach(updatedInventory);
        updatedInventory
            .code(UPDATED_CODE)
            .partnumber(UPDATED_PARTNUMBER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .classification1(UPDATED_CLASSIFICATION_1)
            .classification2(UPDATED_CLASSIFICATION_2)
            .classification3(UPDATED_CLASSIFICATION_3)
            .classification4(UPDATED_CLASSIFICATION_4)
            .classification5(UPDATED_CLASSIFICATION_5)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .decimalplaces(UPDATED_DECIMALPLACES)
            .isassemblyunit(UPDATED_ISASSEMBLYUNIT)
            .assemblyunitof(UPDATED_ASSEMBLYUNITOF)
            .reorderlevel(UPDATED_REORDERLEVEL)
            .lastcost(UPDATED_LASTCOST)
            .lastsellingprice(UPDATED_LASTSELLINGPRICE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .availablequantity(UPDATED_AVAILABLEQUANTITY)
            .hasbatches(UPDATED_HASBATCHES)
            .itemspecfilepath(UPDATED_ITEMSPECFILEPATH)
            .itemimagepath(UPDATED_ITEMIMAGEPATH)
            .returnprice(UPDATED_RETURNPRICE)
            .activeitem(UPDATED_ACTIVEITEM)
            .minstock(UPDATED_MINSTOCK)
            .maxstock(UPDATED_MAXSTOCK)
            .dailyaverage(UPDATED_DAILYAVERAGE)
            .bufferlevel(UPDATED_BUFFERLEVEL)
            .leadtime(UPDATED_LEADTIME)
            .buffertime(UPDATED_BUFFERTIME)
            .saftydays(UPDATED_SAFTYDAYS)
            .accountcode(UPDATED_ACCOUNTCODE)
            .accountid(UPDATED_ACCOUNTID)
            .casepackqty(UPDATED_CASEPACKQTY)
            .isregistered(UPDATED_ISREGISTERED)
            .defaultstocklocationid(UPDATED_DEFAULTSTOCKLOCATIONID)
            .rackno(UPDATED_RACKNO)
            .barcodeimage(UPDATED_BARCODEIMAGE)
            .barcodeimageContentType(UPDATED_BARCODEIMAGE_CONTENT_TYPE)
            .commissionempid(UPDATED_COMMISSIONEMPID)
            .checktypeid(UPDATED_CHECKTYPEID)
            .checktype(UPDATED_CHECKTYPE)
            .reorderqty(UPDATED_REORDERQTY)
            .notininvoice(UPDATED_NOTININVOICE);

        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInventory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedInventory))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInventoryToMatchAllProperties(updatedInventory);
    }

    @Test
    @Transactional
    void putNonExistingInventory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventory.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventoryWithPatch() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventory using partial update
        Inventory partialUpdatedInventory = new Inventory();
        partialUpdatedInventory.setId(inventory.getId());

        partialUpdatedInventory
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .classification2(UPDATED_CLASSIFICATION_2)
            .classification4(UPDATED_CLASSIFICATION_4)
            .decimalplaces(UPDATED_DECIMALPLACES)
            .isassemblyunit(UPDATED_ISASSEMBLYUNIT)
            .reorderlevel(UPDATED_REORDERLEVEL)
            .lastcost(UPDATED_LASTCOST)
            .lmu(UPDATED_LMU)
            .availablequantity(UPDATED_AVAILABLEQUANTITY)
            .hasbatches(UPDATED_HASBATCHES)
            .itemspecfilepath(UPDATED_ITEMSPECFILEPATH)
            .itemimagepath(UPDATED_ITEMIMAGEPATH)
            .activeitem(UPDATED_ACTIVEITEM)
            .minstock(UPDATED_MINSTOCK)
            .maxstock(UPDATED_MAXSTOCK)
            .dailyaverage(UPDATED_DAILYAVERAGE)
            .bufferlevel(UPDATED_BUFFERLEVEL)
            .leadtime(UPDATED_LEADTIME)
            .buffertime(UPDATED_BUFFERTIME)
            .saftydays(UPDATED_SAFTYDAYS)
            .isregistered(UPDATED_ISREGISTERED)
            .defaultstocklocationid(UPDATED_DEFAULTSTOCKLOCATIONID)
            .barcodeimage(UPDATED_BARCODEIMAGE)
            .barcodeimageContentType(UPDATED_BARCODEIMAGE_CONTENT_TYPE)
            .notininvoice(UPDATED_NOTININVOICE);

        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventory))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInventory, inventory),
            getPersistedInventory(inventory)
        );
    }

    @Test
    @Transactional
    void fullUpdateInventoryWithPatch() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventory using partial update
        Inventory partialUpdatedInventory = new Inventory();
        partialUpdatedInventory.setId(inventory.getId());

        partialUpdatedInventory
            .code(UPDATED_CODE)
            .partnumber(UPDATED_PARTNUMBER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .classification1(UPDATED_CLASSIFICATION_1)
            .classification2(UPDATED_CLASSIFICATION_2)
            .classification3(UPDATED_CLASSIFICATION_3)
            .classification4(UPDATED_CLASSIFICATION_4)
            .classification5(UPDATED_CLASSIFICATION_5)
            .unitofmeasurement(UPDATED_UNITOFMEASUREMENT)
            .decimalplaces(UPDATED_DECIMALPLACES)
            .isassemblyunit(UPDATED_ISASSEMBLYUNIT)
            .assemblyunitof(UPDATED_ASSEMBLYUNITOF)
            .reorderlevel(UPDATED_REORDERLEVEL)
            .lastcost(UPDATED_LASTCOST)
            .lastsellingprice(UPDATED_LASTSELLINGPRICE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .availablequantity(UPDATED_AVAILABLEQUANTITY)
            .hasbatches(UPDATED_HASBATCHES)
            .itemspecfilepath(UPDATED_ITEMSPECFILEPATH)
            .itemimagepath(UPDATED_ITEMIMAGEPATH)
            .returnprice(UPDATED_RETURNPRICE)
            .activeitem(UPDATED_ACTIVEITEM)
            .minstock(UPDATED_MINSTOCK)
            .maxstock(UPDATED_MAXSTOCK)
            .dailyaverage(UPDATED_DAILYAVERAGE)
            .bufferlevel(UPDATED_BUFFERLEVEL)
            .leadtime(UPDATED_LEADTIME)
            .buffertime(UPDATED_BUFFERTIME)
            .saftydays(UPDATED_SAFTYDAYS)
            .accountcode(UPDATED_ACCOUNTCODE)
            .accountid(UPDATED_ACCOUNTID)
            .casepackqty(UPDATED_CASEPACKQTY)
            .isregistered(UPDATED_ISREGISTERED)
            .defaultstocklocationid(UPDATED_DEFAULTSTOCKLOCATIONID)
            .rackno(UPDATED_RACKNO)
            .barcodeimage(UPDATED_BARCODEIMAGE)
            .barcodeimageContentType(UPDATED_BARCODEIMAGE_CONTENT_TYPE)
            .commissionempid(UPDATED_COMMISSIONEMPID)
            .checktypeid(UPDATED_CHECKTYPEID)
            .checktype(UPDATED_CHECKTYPE)
            .reorderqty(UPDATED_REORDERQTY)
            .notininvoice(UPDATED_NOTININVOICE);

        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventory))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventoryUpdatableFieldsEquals(partialUpdatedInventory, getPersistedInventory(partialUpdatedInventory));
    }

    @Test
    @Transactional
    void patchNonExistingInventory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventory))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inventory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventory() throws Exception {
        // Initialize the database
        insertedInventory = inventoryRepository.saveAndFlush(inventory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inventory
        restInventoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inventoryRepository.count();
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

    protected Inventory getPersistedInventory(Inventory inventory) {
        return inventoryRepository.findById(inventory.getId()).orElseThrow();
    }

    protected void assertPersistedInventoryToMatchAllProperties(Inventory expectedInventory) {
        assertInventoryAllPropertiesEquals(expectedInventory, getPersistedInventory(expectedInventory));
    }

    protected void assertPersistedInventoryToMatchUpdatableProperties(Inventory expectedInventory) {
        assertInventoryAllUpdatablePropertiesEquals(expectedInventory, getPersistedInventory(expectedInventory));
    }
}
