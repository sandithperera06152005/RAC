package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.SalesinvoiceAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Salesinvoice;
import com.heavenscode.rac.repository.SalesinvoiceRepository;
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
 * Integration tests for the {@link SalesinvoiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalesinvoiceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_INVOICEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUOTEID = 1;
    private static final Integer UPDATED_QUOTEID = 2;
    private static final Integer SMALLER_QUOTEID = 1 - 1;

    private static final Integer DEFAULT_ORDERID = 1;
    private static final Integer UPDATED_ORDERID = 2;
    private static final Integer SMALLER_ORDERID = 1 - 1;

    private static final Instant DEFAULT_DELIEVERYDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIEVERYDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SALESREPID = 1;
    private static final Integer UPDATED_SALESREPID = 2;
    private static final Integer SMALLER_SALESREPID = 1 - 1;

    private static final String DEFAULT_SALESREPNAME = "AAAAAAAAAA";
    private static final String UPDATED_SALESREPNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DELIEVERFROM = "AAAAAAAAAA";
    private static final String UPDATED_DELIEVERFROM = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUSTOMERID = 1;
    private static final Integer UPDATED_CUSTOMERID = 2;
    private static final Integer SMALLER_CUSTOMERID = 1 - 1;

    private static final String DEFAULT_CUSTOMERNAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMERADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERYADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERYADDRESS = "BBBBBBBBBB";

    private static final Float DEFAULT_SUBTOTAL = 1F;
    private static final Float UPDATED_SUBTOTAL = 2F;
    private static final Float SMALLER_SUBTOTAL = 1F - 1F;

    private static final Float DEFAULT_TOTALTAX = 1F;
    private static final Float UPDATED_TOTALTAX = 2F;
    private static final Float SMALLER_TOTALTAX = 1F - 1F;

    private static final Float DEFAULT_TOTALDISCOUNT = 1F;
    private static final Float UPDATED_TOTALDISCOUNT = 2F;
    private static final Float SMALLER_TOTALDISCOUNT = 1F - 1F;

    private static final Float DEFAULT_NETTOTAL = 1F;
    private static final Float UPDATED_NETTOTAL = 2F;
    private static final Float SMALLER_NETTOTAL = 1F - 1F;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_PAIDAMOUNT = 1F;
    private static final Float UPDATED_PAIDAMOUNT = 2F;
    private static final Float SMALLER_PAIDAMOUNT = 1F - 1F;

    private static final Float DEFAULT_AMOUNTOWING = 1F;
    private static final Float UPDATED_AMOUNTOWING = 2F;
    private static final Float SMALLER_AMOUNTOWING = 1F - 1F;

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final Integer DEFAULT_LOCATIONID = 1;
    private static final Integer UPDATED_LOCATIONID = 2;
    private static final Integer SMALLER_LOCATIONID = 1 - 1;

    private static final String DEFAULT_LOCATIONCODE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATIONCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCECODE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCECODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATEDBYID = 1;
    private static final Integer UPDATED_CREATEDBYID = 2;
    private static final Integer SMALLER_CREATEDBYID = 1 - 1;

    private static final String DEFAULT_CREATEDBYNAME = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBYNAME = "BBBBBBBBBB";

    private static final Float DEFAULT_AUTOCARECHARGES = 1F;
    private static final Float UPDATED_AUTOCARECHARGES = 2F;
    private static final Float SMALLER_AUTOCARECHARGES = 1F - 1F;

    private static final Integer DEFAULT_AUTOCAREJOBID = 1;
    private static final Integer UPDATED_AUTOCAREJOBID = 2;
    private static final Integer SMALLER_AUTOCAREJOBID = 1 - 1;

    private static final String DEFAULT_VEHICLENO = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLENO = "BBBBBBBBBB";

    private static final String DEFAULT_NEXTMETER = "AAAAAAAAAA";
    private static final String UPDATED_NEXTMETER = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENTMETER = "AAAAAAAAAA";
    private static final String UPDATED_CURRENTMETER = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HASDUMMYBILL = false;
    private static final Boolean UPDATED_HASDUMMYBILL = true;

    private static final Integer DEFAULT_DUMMYBILLID = 1;
    private static final Integer UPDATED_DUMMYBILLID = 2;
    private static final Integer SMALLER_DUMMYBILLID = 1 - 1;

    private static final Float DEFAULT_DUMMYBILLAMOUNT = 1F;
    private static final Float UPDATED_DUMMYBILLAMOUNT = 2F;
    private static final Float SMALLER_DUMMYBILLAMOUNT = 1F - 1F;

    private static final Float DEFAULT_DUMMYCOMMISION = 1F;
    private static final Float UPDATED_DUMMYCOMMISION = 2F;
    private static final Float SMALLER_DUMMYCOMMISION = 1F - 1F;

    private static final Boolean DEFAULT_ISSERVICEINVOICE = false;
    private static final Boolean UPDATED_ISSERVICEINVOICE = true;

    private static final Float DEFAULT_NBTAMOUNT = 1F;
    private static final Float UPDATED_NBTAMOUNT = 2F;
    private static final Float SMALLER_NBTAMOUNT = 1F - 1F;

    private static final Float DEFAULT_VATAMOUNT = 1F;
    private static final Float UPDATED_VATAMOUNT = 2F;
    private static final Float SMALLER_VATAMOUNT = 1F - 1F;

    private static final Integer DEFAULT_AUTOCARECOMPANYID = 1;
    private static final Integer UPDATED_AUTOCARECOMPANYID = 2;
    private static final Integer SMALLER_AUTOCARECOMPANYID = 1 - 1;

    private static final Boolean DEFAULT_ISCOMPANYINVOICE = false;
    private static final Boolean UPDATED_ISCOMPANYINVOICE = true;

    private static final Instant DEFAULT_INVCANCELDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVCANCELDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_INVCANCELBY = 1;
    private static final Integer UPDATED_INVCANCELBY = 2;
    private static final Integer SMALLER_INVCANCELBY = 1 - 1;

    private static final Boolean DEFAULT_ISVATINVOICE = false;
    private static final Boolean UPDATED_ISVATINVOICE = true;

    private static final String DEFAULT_PAYMENTTYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENTTYPE = "BBBBBBBBBB";

    private static final Float DEFAULT_PENDINGAMOUNT = 1F;
    private static final Float UPDATED_PENDINGAMOUNT = 2F;
    private static final Float SMALLER_PENDINGAMOUNT = 1F - 1F;

    private static final Float DEFAULT_ADVANCEPAYMENT = 1F;
    private static final Float UPDATED_ADVANCEPAYMENT = 2F;
    private static final Float SMALLER_ADVANCEPAYMENT = 1F - 1F;

    private static final String DEFAULT_DISCOUNTCODE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNTCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/salesinvoices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SalesinvoiceRepository salesinvoiceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesinvoiceMockMvc;

    private Salesinvoice salesinvoice;

    private Salesinvoice insertedSalesinvoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salesinvoice createEntity() {
        return new Salesinvoice()
            .code(DEFAULT_CODE)
            .invoicedate(DEFAULT_INVOICEDATE)
            .createddate(DEFAULT_CREATEDDATE)
            .quoteid(DEFAULT_QUOTEID)
            .orderid(DEFAULT_ORDERID)
            .delieverydate(DEFAULT_DELIEVERYDATE)
            .salesrepid(DEFAULT_SALESREPID)
            .salesrepname(DEFAULT_SALESREPNAME)
            .delieverfrom(DEFAULT_DELIEVERFROM)
            .customerid(DEFAULT_CUSTOMERID)
            .customername(DEFAULT_CUSTOMERNAME)
            .customeraddress(DEFAULT_CUSTOMERADDRESS)
            .deliveryaddress(DEFAULT_DELIVERYADDRESS)
            .subtotal(DEFAULT_SUBTOTAL)
            .totaltax(DEFAULT_TOTALTAX)
            .totaldiscount(DEFAULT_TOTALDISCOUNT)
            .nettotal(DEFAULT_NETTOTAL)
            .message(DEFAULT_MESSAGE)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .paidamount(DEFAULT_PAIDAMOUNT)
            .amountowing(DEFAULT_AMOUNTOWING)
            .isactive(DEFAULT_ISACTIVE)
            .locationid(DEFAULT_LOCATIONID)
            .locationcode(DEFAULT_LOCATIONCODE)
            .referencecode(DEFAULT_REFERENCECODE)
            .createdbyid(DEFAULT_CREATEDBYID)
            .createdbyname(DEFAULT_CREATEDBYNAME)
            .autocarecharges(DEFAULT_AUTOCARECHARGES)
            .autocarejobid(DEFAULT_AUTOCAREJOBID)
            .vehicleno(DEFAULT_VEHICLENO)
            .nextmeter(DEFAULT_NEXTMETER)
            .currentmeter(DEFAULT_CURRENTMETER)
            .remarks(DEFAULT_REMARKS)
            .hasdummybill(DEFAULT_HASDUMMYBILL)
            .dummybillid(DEFAULT_DUMMYBILLID)
            .dummybillamount(DEFAULT_DUMMYBILLAMOUNT)
            .dummycommision(DEFAULT_DUMMYCOMMISION)
            .isserviceinvoice(DEFAULT_ISSERVICEINVOICE)
            .nbtamount(DEFAULT_NBTAMOUNT)
            .vatamount(DEFAULT_VATAMOUNT)
            .autocarecompanyid(DEFAULT_AUTOCARECOMPANYID)
            .iscompanyinvoice(DEFAULT_ISCOMPANYINVOICE)
            .invcanceldate(DEFAULT_INVCANCELDATE)
            .invcancelby(DEFAULT_INVCANCELBY)
            .isvatinvoice(DEFAULT_ISVATINVOICE)
            .paymenttype(DEFAULT_PAYMENTTYPE)
            .pendingamount(DEFAULT_PENDINGAMOUNT)
            .advancepayment(DEFAULT_ADVANCEPAYMENT)
            .discountcode(DEFAULT_DISCOUNTCODE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Salesinvoice createUpdatedEntity() {
        return new Salesinvoice()
            .code(UPDATED_CODE)
            .invoicedate(UPDATED_INVOICEDATE)
            .createddate(UPDATED_CREATEDDATE)
            .quoteid(UPDATED_QUOTEID)
            .orderid(UPDATED_ORDERID)
            .delieverydate(UPDATED_DELIEVERYDATE)
            .salesrepid(UPDATED_SALESREPID)
            .salesrepname(UPDATED_SALESREPNAME)
            .delieverfrom(UPDATED_DELIEVERFROM)
            .customerid(UPDATED_CUSTOMERID)
            .customername(UPDATED_CUSTOMERNAME)
            .customeraddress(UPDATED_CUSTOMERADDRESS)
            .deliveryaddress(UPDATED_DELIVERYADDRESS)
            .subtotal(UPDATED_SUBTOTAL)
            .totaltax(UPDATED_TOTALTAX)
            .totaldiscount(UPDATED_TOTALDISCOUNT)
            .nettotal(UPDATED_NETTOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidamount(UPDATED_PAIDAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .isactive(UPDATED_ISACTIVE)
            .locationid(UPDATED_LOCATIONID)
            .locationcode(UPDATED_LOCATIONCODE)
            .referencecode(UPDATED_REFERENCECODE)
            .createdbyid(UPDATED_CREATEDBYID)
            .createdbyname(UPDATED_CREATEDBYNAME)
            .autocarecharges(UPDATED_AUTOCARECHARGES)
            .autocarejobid(UPDATED_AUTOCAREJOBID)
            .vehicleno(UPDATED_VEHICLENO)
            .nextmeter(UPDATED_NEXTMETER)
            .currentmeter(UPDATED_CURRENTMETER)
            .remarks(UPDATED_REMARKS)
            .hasdummybill(UPDATED_HASDUMMYBILL)
            .dummybillid(UPDATED_DUMMYBILLID)
            .dummybillamount(UPDATED_DUMMYBILLAMOUNT)
            .dummycommision(UPDATED_DUMMYCOMMISION)
            .isserviceinvoice(UPDATED_ISSERVICEINVOICE)
            .nbtamount(UPDATED_NBTAMOUNT)
            .vatamount(UPDATED_VATAMOUNT)
            .autocarecompanyid(UPDATED_AUTOCARECOMPANYID)
            .iscompanyinvoice(UPDATED_ISCOMPANYINVOICE)
            .invcanceldate(UPDATED_INVCANCELDATE)
            .invcancelby(UPDATED_INVCANCELBY)
            .isvatinvoice(UPDATED_ISVATINVOICE)
            .paymenttype(UPDATED_PAYMENTTYPE)
            .pendingamount(UPDATED_PENDINGAMOUNT)
            .advancepayment(UPDATED_ADVANCEPAYMENT)
            .discountcode(UPDATED_DISCOUNTCODE);
    }

    @BeforeEach
    public void initTest() {
        salesinvoice = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSalesinvoice != null) {
            salesinvoiceRepository.delete(insertedSalesinvoice);
            insertedSalesinvoice = null;
        }
    }

    @Test
    @Transactional
    void createSalesinvoice() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Salesinvoice
        var returnedSalesinvoice = om.readValue(
            restSalesinvoiceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesinvoice)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Salesinvoice.class
        );

        // Validate the Salesinvoice in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSalesinvoiceUpdatableFieldsEquals(returnedSalesinvoice, getPersistedSalesinvoice(returnedSalesinvoice));

        insertedSalesinvoice = returnedSalesinvoice;
    }

    @Test
    @Transactional
    void createSalesinvoiceWithExistingId() throws Exception {
        // Create the Salesinvoice with an existing ID
        salesinvoice.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesinvoiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesinvoice)))
            .andExpect(status().isBadRequest());

        // Validate the Salesinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSalesinvoices() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList
        restSalesinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesinvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].invoicedate").value(hasItem(DEFAULT_INVOICEDATE.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].quoteid").value(hasItem(DEFAULT_QUOTEID)))
            .andExpect(jsonPath("$.[*].orderid").value(hasItem(DEFAULT_ORDERID)))
            .andExpect(jsonPath("$.[*].delieverydate").value(hasItem(DEFAULT_DELIEVERYDATE.toString())))
            .andExpect(jsonPath("$.[*].salesrepid").value(hasItem(DEFAULT_SALESREPID)))
            .andExpect(jsonPath("$.[*].salesrepname").value(hasItem(DEFAULT_SALESREPNAME)))
            .andExpect(jsonPath("$.[*].delieverfrom").value(hasItem(DEFAULT_DELIEVERFROM)))
            .andExpect(jsonPath("$.[*].customerid").value(hasItem(DEFAULT_CUSTOMERID)))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME)))
            .andExpect(jsonPath("$.[*].customeraddress").value(hasItem(DEFAULT_CUSTOMERADDRESS)))
            .andExpect(jsonPath("$.[*].deliveryaddress").value(hasItem(DEFAULT_DELIVERYADDRESS)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].totaltax").value(hasItem(DEFAULT_TOTALTAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totaldiscount").value(hasItem(DEFAULT_TOTALDISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].nettotal").value(hasItem(DEFAULT_NETTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].paidamount").value(hasItem(DEFAULT_PAIDAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountowing").value(hasItem(DEFAULT_AMOUNTOWING.doubleValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].locationid").value(hasItem(DEFAULT_LOCATIONID)))
            .andExpect(jsonPath("$.[*].locationcode").value(hasItem(DEFAULT_LOCATIONCODE)))
            .andExpect(jsonPath("$.[*].referencecode").value(hasItem(DEFAULT_REFERENCECODE)))
            .andExpect(jsonPath("$.[*].createdbyid").value(hasItem(DEFAULT_CREATEDBYID)))
            .andExpect(jsonPath("$.[*].createdbyname").value(hasItem(DEFAULT_CREATEDBYNAME)))
            .andExpect(jsonPath("$.[*].autocarecharges").value(hasItem(DEFAULT_AUTOCARECHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].autocarejobid").value(hasItem(DEFAULT_AUTOCAREJOBID)))
            .andExpect(jsonPath("$.[*].vehicleno").value(hasItem(DEFAULT_VEHICLENO)))
            .andExpect(jsonPath("$.[*].nextmeter").value(hasItem(DEFAULT_NEXTMETER)))
            .andExpect(jsonPath("$.[*].currentmeter").value(hasItem(DEFAULT_CURRENTMETER)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].hasdummybill").value(hasItem(DEFAULT_HASDUMMYBILL.booleanValue())))
            .andExpect(jsonPath("$.[*].dummybillid").value(hasItem(DEFAULT_DUMMYBILLID)))
            .andExpect(jsonPath("$.[*].dummybillamount").value(hasItem(DEFAULT_DUMMYBILLAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dummycommision").value(hasItem(DEFAULT_DUMMYCOMMISION.doubleValue())))
            .andExpect(jsonPath("$.[*].isserviceinvoice").value(hasItem(DEFAULT_ISSERVICEINVOICE.booleanValue())))
            .andExpect(jsonPath("$.[*].nbtamount").value(hasItem(DEFAULT_NBTAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vatamount").value(hasItem(DEFAULT_VATAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].autocarecompanyid").value(hasItem(DEFAULT_AUTOCARECOMPANYID)))
            .andExpect(jsonPath("$.[*].iscompanyinvoice").value(hasItem(DEFAULT_ISCOMPANYINVOICE.booleanValue())))
            .andExpect(jsonPath("$.[*].invcanceldate").value(hasItem(DEFAULT_INVCANCELDATE.toString())))
            .andExpect(jsonPath("$.[*].invcancelby").value(hasItem(DEFAULT_INVCANCELBY)))
            .andExpect(jsonPath("$.[*].isvatinvoice").value(hasItem(DEFAULT_ISVATINVOICE.booleanValue())))
            .andExpect(jsonPath("$.[*].paymenttype").value(hasItem(DEFAULT_PAYMENTTYPE)))
            .andExpect(jsonPath("$.[*].pendingamount").value(hasItem(DEFAULT_PENDINGAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].advancepayment").value(hasItem(DEFAULT_ADVANCEPAYMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].discountcode").value(hasItem(DEFAULT_DISCOUNTCODE)));
    }

    @Test
    @Transactional
    void getSalesinvoice() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get the salesinvoice
        restSalesinvoiceMockMvc
            .perform(get(ENTITY_API_URL_ID, salesinvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesinvoice.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.invoicedate").value(DEFAULT_INVOICEDATE.toString()))
            .andExpect(jsonPath("$.createddate").value(DEFAULT_CREATEDDATE.toString()))
            .andExpect(jsonPath("$.quoteid").value(DEFAULT_QUOTEID))
            .andExpect(jsonPath("$.orderid").value(DEFAULT_ORDERID))
            .andExpect(jsonPath("$.delieverydate").value(DEFAULT_DELIEVERYDATE.toString()))
            .andExpect(jsonPath("$.salesrepid").value(DEFAULT_SALESREPID))
            .andExpect(jsonPath("$.salesrepname").value(DEFAULT_SALESREPNAME))
            .andExpect(jsonPath("$.delieverfrom").value(DEFAULT_DELIEVERFROM))
            .andExpect(jsonPath("$.customerid").value(DEFAULT_CUSTOMERID))
            .andExpect(jsonPath("$.customername").value(DEFAULT_CUSTOMERNAME))
            .andExpect(jsonPath("$.customeraddress").value(DEFAULT_CUSTOMERADDRESS))
            .andExpect(jsonPath("$.deliveryaddress").value(DEFAULT_DELIVERYADDRESS))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.doubleValue()))
            .andExpect(jsonPath("$.totaltax").value(DEFAULT_TOTALTAX.doubleValue()))
            .andExpect(jsonPath("$.totaldiscount").value(DEFAULT_TOTALDISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.nettotal").value(DEFAULT_NETTOTAL.doubleValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.paidamount").value(DEFAULT_PAIDAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountowing").value(DEFAULT_AMOUNTOWING.doubleValue()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.locationid").value(DEFAULT_LOCATIONID))
            .andExpect(jsonPath("$.locationcode").value(DEFAULT_LOCATIONCODE))
            .andExpect(jsonPath("$.referencecode").value(DEFAULT_REFERENCECODE))
            .andExpect(jsonPath("$.createdbyid").value(DEFAULT_CREATEDBYID))
            .andExpect(jsonPath("$.createdbyname").value(DEFAULT_CREATEDBYNAME))
            .andExpect(jsonPath("$.autocarecharges").value(DEFAULT_AUTOCARECHARGES.doubleValue()))
            .andExpect(jsonPath("$.autocarejobid").value(DEFAULT_AUTOCAREJOBID))
            .andExpect(jsonPath("$.vehicleno").value(DEFAULT_VEHICLENO))
            .andExpect(jsonPath("$.nextmeter").value(DEFAULT_NEXTMETER))
            .andExpect(jsonPath("$.currentmeter").value(DEFAULT_CURRENTMETER))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.hasdummybill").value(DEFAULT_HASDUMMYBILL.booleanValue()))
            .andExpect(jsonPath("$.dummybillid").value(DEFAULT_DUMMYBILLID))
            .andExpect(jsonPath("$.dummybillamount").value(DEFAULT_DUMMYBILLAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dummycommision").value(DEFAULT_DUMMYCOMMISION.doubleValue()))
            .andExpect(jsonPath("$.isserviceinvoice").value(DEFAULT_ISSERVICEINVOICE.booleanValue()))
            .andExpect(jsonPath("$.nbtamount").value(DEFAULT_NBTAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.vatamount").value(DEFAULT_VATAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.autocarecompanyid").value(DEFAULT_AUTOCARECOMPANYID))
            .andExpect(jsonPath("$.iscompanyinvoice").value(DEFAULT_ISCOMPANYINVOICE.booleanValue()))
            .andExpect(jsonPath("$.invcanceldate").value(DEFAULT_INVCANCELDATE.toString()))
            .andExpect(jsonPath("$.invcancelby").value(DEFAULT_INVCANCELBY))
            .andExpect(jsonPath("$.isvatinvoice").value(DEFAULT_ISVATINVOICE.booleanValue()))
            .andExpect(jsonPath("$.paymenttype").value(DEFAULT_PAYMENTTYPE))
            .andExpect(jsonPath("$.pendingamount").value(DEFAULT_PENDINGAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.advancepayment").value(DEFAULT_ADVANCEPAYMENT.doubleValue()))
            .andExpect(jsonPath("$.discountcode").value(DEFAULT_DISCOUNTCODE));
    }

    @Test
    @Transactional
    void getSalesinvoicesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        Long id = salesinvoice.getId();

        defaultSalesinvoiceFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSalesinvoiceFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSalesinvoiceFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where code equals to
        defaultSalesinvoiceFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where code in
        defaultSalesinvoiceFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where code is not null
        defaultSalesinvoiceFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where code contains
        defaultSalesinvoiceFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where code does not contain
        defaultSalesinvoiceFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvoicedateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invoicedate equals to
        defaultSalesinvoiceFiltering("invoicedate.equals=" + DEFAULT_INVOICEDATE, "invoicedate.equals=" + UPDATED_INVOICEDATE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvoicedateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invoicedate in
        defaultSalesinvoiceFiltering(
            "invoicedate.in=" + DEFAULT_INVOICEDATE + "," + UPDATED_INVOICEDATE,
            "invoicedate.in=" + UPDATED_INVOICEDATE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvoicedateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invoicedate is not null
        defaultSalesinvoiceFiltering("invoicedate.specified=true", "invoicedate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreateddateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createddate equals to
        defaultSalesinvoiceFiltering("createddate.equals=" + DEFAULT_CREATEDDATE, "createddate.equals=" + UPDATED_CREATEDDATE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreateddateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createddate in
        defaultSalesinvoiceFiltering(
            "createddate.in=" + DEFAULT_CREATEDDATE + "," + UPDATED_CREATEDDATE,
            "createddate.in=" + UPDATED_CREATEDDATE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreateddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createddate is not null
        defaultSalesinvoiceFiltering("createddate.specified=true", "createddate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByQuoteidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where quoteid equals to
        defaultSalesinvoiceFiltering("quoteid.equals=" + DEFAULT_QUOTEID, "quoteid.equals=" + UPDATED_QUOTEID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByQuoteidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where quoteid in
        defaultSalesinvoiceFiltering("quoteid.in=" + DEFAULT_QUOTEID + "," + UPDATED_QUOTEID, "quoteid.in=" + UPDATED_QUOTEID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByQuoteidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where quoteid is not null
        defaultSalesinvoiceFiltering("quoteid.specified=true", "quoteid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByQuoteidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where quoteid is greater than or equal to
        defaultSalesinvoiceFiltering("quoteid.greaterThanOrEqual=" + DEFAULT_QUOTEID, "quoteid.greaterThanOrEqual=" + UPDATED_QUOTEID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByQuoteidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where quoteid is less than or equal to
        defaultSalesinvoiceFiltering("quoteid.lessThanOrEqual=" + DEFAULT_QUOTEID, "quoteid.lessThanOrEqual=" + SMALLER_QUOTEID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByQuoteidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where quoteid is less than
        defaultSalesinvoiceFiltering("quoteid.lessThan=" + UPDATED_QUOTEID, "quoteid.lessThan=" + DEFAULT_QUOTEID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByQuoteidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where quoteid is greater than
        defaultSalesinvoiceFiltering("quoteid.greaterThan=" + SMALLER_QUOTEID, "quoteid.greaterThan=" + DEFAULT_QUOTEID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByOrderidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where orderid equals to
        defaultSalesinvoiceFiltering("orderid.equals=" + DEFAULT_ORDERID, "orderid.equals=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByOrderidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where orderid in
        defaultSalesinvoiceFiltering("orderid.in=" + DEFAULT_ORDERID + "," + UPDATED_ORDERID, "orderid.in=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByOrderidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where orderid is not null
        defaultSalesinvoiceFiltering("orderid.specified=true", "orderid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByOrderidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where orderid is greater than or equal to
        defaultSalesinvoiceFiltering("orderid.greaterThanOrEqual=" + DEFAULT_ORDERID, "orderid.greaterThanOrEqual=" + UPDATED_ORDERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByOrderidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where orderid is less than or equal to
        defaultSalesinvoiceFiltering("orderid.lessThanOrEqual=" + DEFAULT_ORDERID, "orderid.lessThanOrEqual=" + SMALLER_ORDERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByOrderidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where orderid is less than
        defaultSalesinvoiceFiltering("orderid.lessThan=" + UPDATED_ORDERID, "orderid.lessThan=" + DEFAULT_ORDERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByOrderidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where orderid is greater than
        defaultSalesinvoiceFiltering("orderid.greaterThan=" + SMALLER_ORDERID, "orderid.greaterThan=" + DEFAULT_ORDERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDelieverydateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where delieverydate equals to
        defaultSalesinvoiceFiltering("delieverydate.equals=" + DEFAULT_DELIEVERYDATE, "delieverydate.equals=" + UPDATED_DELIEVERYDATE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDelieverydateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where delieverydate in
        defaultSalesinvoiceFiltering(
            "delieverydate.in=" + DEFAULT_DELIEVERYDATE + "," + UPDATED_DELIEVERYDATE,
            "delieverydate.in=" + UPDATED_DELIEVERYDATE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDelieverydateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where delieverydate is not null
        defaultSalesinvoiceFiltering("delieverydate.specified=true", "delieverydate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepid equals to
        defaultSalesinvoiceFiltering("salesrepid.equals=" + DEFAULT_SALESREPID, "salesrepid.equals=" + UPDATED_SALESREPID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepid in
        defaultSalesinvoiceFiltering(
            "salesrepid.in=" + DEFAULT_SALESREPID + "," + UPDATED_SALESREPID,
            "salesrepid.in=" + UPDATED_SALESREPID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepid is not null
        defaultSalesinvoiceFiltering("salesrepid.specified=true", "salesrepid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepid is greater than or equal to
        defaultSalesinvoiceFiltering(
            "salesrepid.greaterThanOrEqual=" + DEFAULT_SALESREPID,
            "salesrepid.greaterThanOrEqual=" + UPDATED_SALESREPID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepid is less than or equal to
        defaultSalesinvoiceFiltering(
            "salesrepid.lessThanOrEqual=" + DEFAULT_SALESREPID,
            "salesrepid.lessThanOrEqual=" + SMALLER_SALESREPID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepid is less than
        defaultSalesinvoiceFiltering("salesrepid.lessThan=" + UPDATED_SALESREPID, "salesrepid.lessThan=" + DEFAULT_SALESREPID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepid is greater than
        defaultSalesinvoiceFiltering("salesrepid.greaterThan=" + SMALLER_SALESREPID, "salesrepid.greaterThan=" + DEFAULT_SALESREPID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepnameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepname equals to
        defaultSalesinvoiceFiltering("salesrepname.equals=" + DEFAULT_SALESREPNAME, "salesrepname.equals=" + UPDATED_SALESREPNAME);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepnameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepname in
        defaultSalesinvoiceFiltering(
            "salesrepname.in=" + DEFAULT_SALESREPNAME + "," + UPDATED_SALESREPNAME,
            "salesrepname.in=" + UPDATED_SALESREPNAME
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepname is not null
        defaultSalesinvoiceFiltering("salesrepname.specified=true", "salesrepname.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepnameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepname contains
        defaultSalesinvoiceFiltering("salesrepname.contains=" + DEFAULT_SALESREPNAME, "salesrepname.contains=" + UPDATED_SALESREPNAME);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySalesrepnameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where salesrepname does not contain
        defaultSalesinvoiceFiltering(
            "salesrepname.doesNotContain=" + UPDATED_SALESREPNAME,
            "salesrepname.doesNotContain=" + DEFAULT_SALESREPNAME
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDelieverfromIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where delieverfrom equals to
        defaultSalesinvoiceFiltering("delieverfrom.equals=" + DEFAULT_DELIEVERFROM, "delieverfrom.equals=" + UPDATED_DELIEVERFROM);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDelieverfromIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where delieverfrom in
        defaultSalesinvoiceFiltering(
            "delieverfrom.in=" + DEFAULT_DELIEVERFROM + "," + UPDATED_DELIEVERFROM,
            "delieverfrom.in=" + UPDATED_DELIEVERFROM
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDelieverfromIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where delieverfrom is not null
        defaultSalesinvoiceFiltering("delieverfrom.specified=true", "delieverfrom.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDelieverfromContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where delieverfrom contains
        defaultSalesinvoiceFiltering("delieverfrom.contains=" + DEFAULT_DELIEVERFROM, "delieverfrom.contains=" + UPDATED_DELIEVERFROM);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDelieverfromNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where delieverfrom does not contain
        defaultSalesinvoiceFiltering(
            "delieverfrom.doesNotContain=" + UPDATED_DELIEVERFROM,
            "delieverfrom.doesNotContain=" + DEFAULT_DELIEVERFROM
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeridIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customerid equals to
        defaultSalesinvoiceFiltering("customerid.equals=" + DEFAULT_CUSTOMERID, "customerid.equals=" + UPDATED_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeridIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customerid in
        defaultSalesinvoiceFiltering(
            "customerid.in=" + DEFAULT_CUSTOMERID + "," + UPDATED_CUSTOMERID,
            "customerid.in=" + UPDATED_CUSTOMERID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeridIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customerid is not null
        defaultSalesinvoiceFiltering("customerid.specified=true", "customerid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeridIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customerid is greater than or equal to
        defaultSalesinvoiceFiltering(
            "customerid.greaterThanOrEqual=" + DEFAULT_CUSTOMERID,
            "customerid.greaterThanOrEqual=" + UPDATED_CUSTOMERID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeridIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customerid is less than or equal to
        defaultSalesinvoiceFiltering(
            "customerid.lessThanOrEqual=" + DEFAULT_CUSTOMERID,
            "customerid.lessThanOrEqual=" + SMALLER_CUSTOMERID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeridIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customerid is less than
        defaultSalesinvoiceFiltering("customerid.lessThan=" + UPDATED_CUSTOMERID, "customerid.lessThan=" + DEFAULT_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeridIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customerid is greater than
        defaultSalesinvoiceFiltering("customerid.greaterThan=" + SMALLER_CUSTOMERID, "customerid.greaterThan=" + DEFAULT_CUSTOMERID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomernameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customername equals to
        defaultSalesinvoiceFiltering("customername.equals=" + DEFAULT_CUSTOMERNAME, "customername.equals=" + UPDATED_CUSTOMERNAME);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomernameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customername in
        defaultSalesinvoiceFiltering(
            "customername.in=" + DEFAULT_CUSTOMERNAME + "," + UPDATED_CUSTOMERNAME,
            "customername.in=" + UPDATED_CUSTOMERNAME
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customername is not null
        defaultSalesinvoiceFiltering("customername.specified=true", "customername.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomernameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customername contains
        defaultSalesinvoiceFiltering("customername.contains=" + DEFAULT_CUSTOMERNAME, "customername.contains=" + UPDATED_CUSTOMERNAME);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomernameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customername does not contain
        defaultSalesinvoiceFiltering(
            "customername.doesNotContain=" + UPDATED_CUSTOMERNAME,
            "customername.doesNotContain=" + DEFAULT_CUSTOMERNAME
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeraddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customeraddress equals to
        defaultSalesinvoiceFiltering(
            "customeraddress.equals=" + DEFAULT_CUSTOMERADDRESS,
            "customeraddress.equals=" + UPDATED_CUSTOMERADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeraddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customeraddress in
        defaultSalesinvoiceFiltering(
            "customeraddress.in=" + DEFAULT_CUSTOMERADDRESS + "," + UPDATED_CUSTOMERADDRESS,
            "customeraddress.in=" + UPDATED_CUSTOMERADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeraddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customeraddress is not null
        defaultSalesinvoiceFiltering("customeraddress.specified=true", "customeraddress.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeraddressContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customeraddress contains
        defaultSalesinvoiceFiltering(
            "customeraddress.contains=" + DEFAULT_CUSTOMERADDRESS,
            "customeraddress.contains=" + UPDATED_CUSTOMERADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCustomeraddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where customeraddress does not contain
        defaultSalesinvoiceFiltering(
            "customeraddress.doesNotContain=" + UPDATED_CUSTOMERADDRESS,
            "customeraddress.doesNotContain=" + DEFAULT_CUSTOMERADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDeliveryaddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where deliveryaddress equals to
        defaultSalesinvoiceFiltering(
            "deliveryaddress.equals=" + DEFAULT_DELIVERYADDRESS,
            "deliveryaddress.equals=" + UPDATED_DELIVERYADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDeliveryaddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where deliveryaddress in
        defaultSalesinvoiceFiltering(
            "deliveryaddress.in=" + DEFAULT_DELIVERYADDRESS + "," + UPDATED_DELIVERYADDRESS,
            "deliveryaddress.in=" + UPDATED_DELIVERYADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDeliveryaddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where deliveryaddress is not null
        defaultSalesinvoiceFiltering("deliveryaddress.specified=true", "deliveryaddress.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDeliveryaddressContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where deliveryaddress contains
        defaultSalesinvoiceFiltering(
            "deliveryaddress.contains=" + DEFAULT_DELIVERYADDRESS,
            "deliveryaddress.contains=" + UPDATED_DELIVERYADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDeliveryaddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where deliveryaddress does not contain
        defaultSalesinvoiceFiltering(
            "deliveryaddress.doesNotContain=" + UPDATED_DELIVERYADDRESS,
            "deliveryaddress.doesNotContain=" + DEFAULT_DELIVERYADDRESS
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySubtotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where subtotal equals to
        defaultSalesinvoiceFiltering("subtotal.equals=" + DEFAULT_SUBTOTAL, "subtotal.equals=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySubtotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where subtotal in
        defaultSalesinvoiceFiltering("subtotal.in=" + DEFAULT_SUBTOTAL + "," + UPDATED_SUBTOTAL, "subtotal.in=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySubtotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where subtotal is not null
        defaultSalesinvoiceFiltering("subtotal.specified=true", "subtotal.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySubtotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where subtotal is greater than or equal to
        defaultSalesinvoiceFiltering("subtotal.greaterThanOrEqual=" + DEFAULT_SUBTOTAL, "subtotal.greaterThanOrEqual=" + UPDATED_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySubtotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where subtotal is less than or equal to
        defaultSalesinvoiceFiltering("subtotal.lessThanOrEqual=" + DEFAULT_SUBTOTAL, "subtotal.lessThanOrEqual=" + SMALLER_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySubtotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where subtotal is less than
        defaultSalesinvoiceFiltering("subtotal.lessThan=" + UPDATED_SUBTOTAL, "subtotal.lessThan=" + DEFAULT_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesBySubtotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where subtotal is greater than
        defaultSalesinvoiceFiltering("subtotal.greaterThan=" + SMALLER_SUBTOTAL, "subtotal.greaterThan=" + DEFAULT_SUBTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaltaxIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaltax equals to
        defaultSalesinvoiceFiltering("totaltax.equals=" + DEFAULT_TOTALTAX, "totaltax.equals=" + UPDATED_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaltaxIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaltax in
        defaultSalesinvoiceFiltering("totaltax.in=" + DEFAULT_TOTALTAX + "," + UPDATED_TOTALTAX, "totaltax.in=" + UPDATED_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaltaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaltax is not null
        defaultSalesinvoiceFiltering("totaltax.specified=true", "totaltax.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaltaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaltax is greater than or equal to
        defaultSalesinvoiceFiltering("totaltax.greaterThanOrEqual=" + DEFAULT_TOTALTAX, "totaltax.greaterThanOrEqual=" + UPDATED_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaltaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaltax is less than or equal to
        defaultSalesinvoiceFiltering("totaltax.lessThanOrEqual=" + DEFAULT_TOTALTAX, "totaltax.lessThanOrEqual=" + SMALLER_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaltaxIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaltax is less than
        defaultSalesinvoiceFiltering("totaltax.lessThan=" + UPDATED_TOTALTAX, "totaltax.lessThan=" + DEFAULT_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaltaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaltax is greater than
        defaultSalesinvoiceFiltering("totaltax.greaterThan=" + SMALLER_TOTALTAX, "totaltax.greaterThan=" + DEFAULT_TOTALTAX);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaldiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaldiscount equals to
        defaultSalesinvoiceFiltering("totaldiscount.equals=" + DEFAULT_TOTALDISCOUNT, "totaldiscount.equals=" + UPDATED_TOTALDISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaldiscountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaldiscount in
        defaultSalesinvoiceFiltering(
            "totaldiscount.in=" + DEFAULT_TOTALDISCOUNT + "," + UPDATED_TOTALDISCOUNT,
            "totaldiscount.in=" + UPDATED_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaldiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaldiscount is not null
        defaultSalesinvoiceFiltering("totaldiscount.specified=true", "totaldiscount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaldiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaldiscount is greater than or equal to
        defaultSalesinvoiceFiltering(
            "totaldiscount.greaterThanOrEqual=" + DEFAULT_TOTALDISCOUNT,
            "totaldiscount.greaterThanOrEqual=" + UPDATED_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaldiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaldiscount is less than or equal to
        defaultSalesinvoiceFiltering(
            "totaldiscount.lessThanOrEqual=" + DEFAULT_TOTALDISCOUNT,
            "totaldiscount.lessThanOrEqual=" + SMALLER_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaldiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaldiscount is less than
        defaultSalesinvoiceFiltering("totaldiscount.lessThan=" + UPDATED_TOTALDISCOUNT, "totaldiscount.lessThan=" + DEFAULT_TOTALDISCOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByTotaldiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where totaldiscount is greater than
        defaultSalesinvoiceFiltering(
            "totaldiscount.greaterThan=" + SMALLER_TOTALDISCOUNT,
            "totaldiscount.greaterThan=" + DEFAULT_TOTALDISCOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNettotalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nettotal equals to
        defaultSalesinvoiceFiltering("nettotal.equals=" + DEFAULT_NETTOTAL, "nettotal.equals=" + UPDATED_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNettotalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nettotal in
        defaultSalesinvoiceFiltering("nettotal.in=" + DEFAULT_NETTOTAL + "," + UPDATED_NETTOTAL, "nettotal.in=" + UPDATED_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNettotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nettotal is not null
        defaultSalesinvoiceFiltering("nettotal.specified=true", "nettotal.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNettotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nettotal is greater than or equal to
        defaultSalesinvoiceFiltering("nettotal.greaterThanOrEqual=" + DEFAULT_NETTOTAL, "nettotal.greaterThanOrEqual=" + UPDATED_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNettotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nettotal is less than or equal to
        defaultSalesinvoiceFiltering("nettotal.lessThanOrEqual=" + DEFAULT_NETTOTAL, "nettotal.lessThanOrEqual=" + SMALLER_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNettotalIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nettotal is less than
        defaultSalesinvoiceFiltering("nettotal.lessThan=" + UPDATED_NETTOTAL, "nettotal.lessThan=" + DEFAULT_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNettotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nettotal is greater than
        defaultSalesinvoiceFiltering("nettotal.greaterThan=" + SMALLER_NETTOTAL, "nettotal.greaterThan=" + DEFAULT_NETTOTAL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where message equals to
        defaultSalesinvoiceFiltering("message.equals=" + DEFAULT_MESSAGE, "message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where message in
        defaultSalesinvoiceFiltering("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE, "message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where message is not null
        defaultSalesinvoiceFiltering("message.specified=true", "message.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByMessageContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where message contains
        defaultSalesinvoiceFiltering("message.contains=" + DEFAULT_MESSAGE, "message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where message does not contain
        defaultSalesinvoiceFiltering("message.doesNotContain=" + UPDATED_MESSAGE, "message.doesNotContain=" + DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmu equals to
        defaultSalesinvoiceFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmu in
        defaultSalesinvoiceFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmu is not null
        defaultSalesinvoiceFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmu is greater than or equal to
        defaultSalesinvoiceFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmu is less than or equal to
        defaultSalesinvoiceFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmu is less than
        defaultSalesinvoiceFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmu is greater than
        defaultSalesinvoiceFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmd equals to
        defaultSalesinvoiceFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmd in
        defaultSalesinvoiceFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where lmd is not null
        defaultSalesinvoiceFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaidamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paidamount equals to
        defaultSalesinvoiceFiltering("paidamount.equals=" + DEFAULT_PAIDAMOUNT, "paidamount.equals=" + UPDATED_PAIDAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaidamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paidamount in
        defaultSalesinvoiceFiltering(
            "paidamount.in=" + DEFAULT_PAIDAMOUNT + "," + UPDATED_PAIDAMOUNT,
            "paidamount.in=" + UPDATED_PAIDAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaidamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paidamount is not null
        defaultSalesinvoiceFiltering("paidamount.specified=true", "paidamount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaidamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paidamount is greater than or equal to
        defaultSalesinvoiceFiltering(
            "paidamount.greaterThanOrEqual=" + DEFAULT_PAIDAMOUNT,
            "paidamount.greaterThanOrEqual=" + UPDATED_PAIDAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaidamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paidamount is less than or equal to
        defaultSalesinvoiceFiltering(
            "paidamount.lessThanOrEqual=" + DEFAULT_PAIDAMOUNT,
            "paidamount.lessThanOrEqual=" + SMALLER_PAIDAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaidamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paidamount is less than
        defaultSalesinvoiceFiltering("paidamount.lessThan=" + UPDATED_PAIDAMOUNT, "paidamount.lessThan=" + DEFAULT_PAIDAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaidamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paidamount is greater than
        defaultSalesinvoiceFiltering("paidamount.greaterThan=" + SMALLER_PAIDAMOUNT, "paidamount.greaterThan=" + DEFAULT_PAIDAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAmountowingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where amountowing equals to
        defaultSalesinvoiceFiltering("amountowing.equals=" + DEFAULT_AMOUNTOWING, "amountowing.equals=" + UPDATED_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAmountowingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where amountowing in
        defaultSalesinvoiceFiltering(
            "amountowing.in=" + DEFAULT_AMOUNTOWING + "," + UPDATED_AMOUNTOWING,
            "amountowing.in=" + UPDATED_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAmountowingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where amountowing is not null
        defaultSalesinvoiceFiltering("amountowing.specified=true", "amountowing.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAmountowingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where amountowing is greater than or equal to
        defaultSalesinvoiceFiltering(
            "amountowing.greaterThanOrEqual=" + DEFAULT_AMOUNTOWING,
            "amountowing.greaterThanOrEqual=" + UPDATED_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAmountowingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where amountowing is less than or equal to
        defaultSalesinvoiceFiltering(
            "amountowing.lessThanOrEqual=" + DEFAULT_AMOUNTOWING,
            "amountowing.lessThanOrEqual=" + SMALLER_AMOUNTOWING
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAmountowingIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where amountowing is less than
        defaultSalesinvoiceFiltering("amountowing.lessThan=" + UPDATED_AMOUNTOWING, "amountowing.lessThan=" + DEFAULT_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAmountowingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where amountowing is greater than
        defaultSalesinvoiceFiltering("amountowing.greaterThan=" + SMALLER_AMOUNTOWING, "amountowing.greaterThan=" + DEFAULT_AMOUNTOWING);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsactiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isactive equals to
        defaultSalesinvoiceFiltering("isactive.equals=" + DEFAULT_ISACTIVE, "isactive.equals=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsactiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isactive in
        defaultSalesinvoiceFiltering("isactive.in=" + DEFAULT_ISACTIVE + "," + UPDATED_ISACTIVE, "isactive.in=" + UPDATED_ISACTIVE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsactiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isactive is not null
        defaultSalesinvoiceFiltering("isactive.specified=true", "isactive.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationid equals to
        defaultSalesinvoiceFiltering("locationid.equals=" + DEFAULT_LOCATIONID, "locationid.equals=" + UPDATED_LOCATIONID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationid in
        defaultSalesinvoiceFiltering(
            "locationid.in=" + DEFAULT_LOCATIONID + "," + UPDATED_LOCATIONID,
            "locationid.in=" + UPDATED_LOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationid is not null
        defaultSalesinvoiceFiltering("locationid.specified=true", "locationid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationid is greater than or equal to
        defaultSalesinvoiceFiltering(
            "locationid.greaterThanOrEqual=" + DEFAULT_LOCATIONID,
            "locationid.greaterThanOrEqual=" + UPDATED_LOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationid is less than or equal to
        defaultSalesinvoiceFiltering(
            "locationid.lessThanOrEqual=" + DEFAULT_LOCATIONID,
            "locationid.lessThanOrEqual=" + SMALLER_LOCATIONID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationid is less than
        defaultSalesinvoiceFiltering("locationid.lessThan=" + UPDATED_LOCATIONID, "locationid.lessThan=" + DEFAULT_LOCATIONID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationid is greater than
        defaultSalesinvoiceFiltering("locationid.greaterThan=" + SMALLER_LOCATIONID, "locationid.greaterThan=" + DEFAULT_LOCATIONID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationcode equals to
        defaultSalesinvoiceFiltering("locationcode.equals=" + DEFAULT_LOCATIONCODE, "locationcode.equals=" + UPDATED_LOCATIONCODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationcodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationcode in
        defaultSalesinvoiceFiltering(
            "locationcode.in=" + DEFAULT_LOCATIONCODE + "," + UPDATED_LOCATIONCODE,
            "locationcode.in=" + UPDATED_LOCATIONCODE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationcode is not null
        defaultSalesinvoiceFiltering("locationcode.specified=true", "locationcode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationcodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationcode contains
        defaultSalesinvoiceFiltering("locationcode.contains=" + DEFAULT_LOCATIONCODE, "locationcode.contains=" + UPDATED_LOCATIONCODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByLocationcodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where locationcode does not contain
        defaultSalesinvoiceFiltering(
            "locationcode.doesNotContain=" + UPDATED_LOCATIONCODE,
            "locationcode.doesNotContain=" + DEFAULT_LOCATIONCODE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByReferencecodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where referencecode equals to
        defaultSalesinvoiceFiltering("referencecode.equals=" + DEFAULT_REFERENCECODE, "referencecode.equals=" + UPDATED_REFERENCECODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByReferencecodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where referencecode in
        defaultSalesinvoiceFiltering(
            "referencecode.in=" + DEFAULT_REFERENCECODE + "," + UPDATED_REFERENCECODE,
            "referencecode.in=" + UPDATED_REFERENCECODE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByReferencecodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where referencecode is not null
        defaultSalesinvoiceFiltering("referencecode.specified=true", "referencecode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByReferencecodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where referencecode contains
        defaultSalesinvoiceFiltering("referencecode.contains=" + DEFAULT_REFERENCECODE, "referencecode.contains=" + UPDATED_REFERENCECODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByReferencecodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where referencecode does not contain
        defaultSalesinvoiceFiltering(
            "referencecode.doesNotContain=" + UPDATED_REFERENCECODE,
            "referencecode.doesNotContain=" + DEFAULT_REFERENCECODE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbyidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyid equals to
        defaultSalesinvoiceFiltering("createdbyid.equals=" + DEFAULT_CREATEDBYID, "createdbyid.equals=" + UPDATED_CREATEDBYID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbyidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyid in
        defaultSalesinvoiceFiltering(
            "createdbyid.in=" + DEFAULT_CREATEDBYID + "," + UPDATED_CREATEDBYID,
            "createdbyid.in=" + UPDATED_CREATEDBYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyid is not null
        defaultSalesinvoiceFiltering("createdbyid.specified=true", "createdbyid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyid is greater than or equal to
        defaultSalesinvoiceFiltering(
            "createdbyid.greaterThanOrEqual=" + DEFAULT_CREATEDBYID,
            "createdbyid.greaterThanOrEqual=" + UPDATED_CREATEDBYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyid is less than or equal to
        defaultSalesinvoiceFiltering(
            "createdbyid.lessThanOrEqual=" + DEFAULT_CREATEDBYID,
            "createdbyid.lessThanOrEqual=" + SMALLER_CREATEDBYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbyidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyid is less than
        defaultSalesinvoiceFiltering("createdbyid.lessThan=" + UPDATED_CREATEDBYID, "createdbyid.lessThan=" + DEFAULT_CREATEDBYID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyid is greater than
        defaultSalesinvoiceFiltering("createdbyid.greaterThan=" + SMALLER_CREATEDBYID, "createdbyid.greaterThan=" + DEFAULT_CREATEDBYID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbynameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyname equals to
        defaultSalesinvoiceFiltering("createdbyname.equals=" + DEFAULT_CREATEDBYNAME, "createdbyname.equals=" + UPDATED_CREATEDBYNAME);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbynameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyname in
        defaultSalesinvoiceFiltering(
            "createdbyname.in=" + DEFAULT_CREATEDBYNAME + "," + UPDATED_CREATEDBYNAME,
            "createdbyname.in=" + UPDATED_CREATEDBYNAME
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbynameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyname is not null
        defaultSalesinvoiceFiltering("createdbyname.specified=true", "createdbyname.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbynameContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyname contains
        defaultSalesinvoiceFiltering("createdbyname.contains=" + DEFAULT_CREATEDBYNAME, "createdbyname.contains=" + UPDATED_CREATEDBYNAME);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCreatedbynameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where createdbyname does not contain
        defaultSalesinvoiceFiltering(
            "createdbyname.doesNotContain=" + UPDATED_CREATEDBYNAME,
            "createdbyname.doesNotContain=" + DEFAULT_CREATEDBYNAME
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarechargesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecharges equals to
        defaultSalesinvoiceFiltering(
            "autocarecharges.equals=" + DEFAULT_AUTOCARECHARGES,
            "autocarecharges.equals=" + UPDATED_AUTOCARECHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarechargesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecharges in
        defaultSalesinvoiceFiltering(
            "autocarecharges.in=" + DEFAULT_AUTOCARECHARGES + "," + UPDATED_AUTOCARECHARGES,
            "autocarecharges.in=" + UPDATED_AUTOCARECHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarechargesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecharges is not null
        defaultSalesinvoiceFiltering("autocarecharges.specified=true", "autocarecharges.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarechargesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecharges is greater than or equal to
        defaultSalesinvoiceFiltering(
            "autocarecharges.greaterThanOrEqual=" + DEFAULT_AUTOCARECHARGES,
            "autocarecharges.greaterThanOrEqual=" + UPDATED_AUTOCARECHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarechargesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecharges is less than or equal to
        defaultSalesinvoiceFiltering(
            "autocarecharges.lessThanOrEqual=" + DEFAULT_AUTOCARECHARGES,
            "autocarecharges.lessThanOrEqual=" + SMALLER_AUTOCARECHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarechargesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecharges is less than
        defaultSalesinvoiceFiltering(
            "autocarecharges.lessThan=" + UPDATED_AUTOCARECHARGES,
            "autocarecharges.lessThan=" + DEFAULT_AUTOCARECHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarechargesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecharges is greater than
        defaultSalesinvoiceFiltering(
            "autocarecharges.greaterThan=" + SMALLER_AUTOCARECHARGES,
            "autocarecharges.greaterThan=" + DEFAULT_AUTOCARECHARGES
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarejobidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarejobid equals to
        defaultSalesinvoiceFiltering("autocarejobid.equals=" + DEFAULT_AUTOCAREJOBID, "autocarejobid.equals=" + UPDATED_AUTOCAREJOBID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarejobidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarejobid in
        defaultSalesinvoiceFiltering(
            "autocarejobid.in=" + DEFAULT_AUTOCAREJOBID + "," + UPDATED_AUTOCAREJOBID,
            "autocarejobid.in=" + UPDATED_AUTOCAREJOBID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarejobidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarejobid is not null
        defaultSalesinvoiceFiltering("autocarejobid.specified=true", "autocarejobid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarejobidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarejobid is greater than or equal to
        defaultSalesinvoiceFiltering(
            "autocarejobid.greaterThanOrEqual=" + DEFAULT_AUTOCAREJOBID,
            "autocarejobid.greaterThanOrEqual=" + UPDATED_AUTOCAREJOBID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarejobidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarejobid is less than or equal to
        defaultSalesinvoiceFiltering(
            "autocarejobid.lessThanOrEqual=" + DEFAULT_AUTOCAREJOBID,
            "autocarejobid.lessThanOrEqual=" + SMALLER_AUTOCAREJOBID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarejobidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarejobid is less than
        defaultSalesinvoiceFiltering("autocarejobid.lessThan=" + UPDATED_AUTOCAREJOBID, "autocarejobid.lessThan=" + DEFAULT_AUTOCAREJOBID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarejobidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarejobid is greater than
        defaultSalesinvoiceFiltering(
            "autocarejobid.greaterThan=" + SMALLER_AUTOCAREJOBID,
            "autocarejobid.greaterThan=" + DEFAULT_AUTOCAREJOBID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVehiclenoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vehicleno equals to
        defaultSalesinvoiceFiltering("vehicleno.equals=" + DEFAULT_VEHICLENO, "vehicleno.equals=" + UPDATED_VEHICLENO);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVehiclenoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vehicleno in
        defaultSalesinvoiceFiltering("vehicleno.in=" + DEFAULT_VEHICLENO + "," + UPDATED_VEHICLENO, "vehicleno.in=" + UPDATED_VEHICLENO);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVehiclenoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vehicleno is not null
        defaultSalesinvoiceFiltering("vehicleno.specified=true", "vehicleno.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVehiclenoContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vehicleno contains
        defaultSalesinvoiceFiltering("vehicleno.contains=" + DEFAULT_VEHICLENO, "vehicleno.contains=" + UPDATED_VEHICLENO);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVehiclenoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vehicleno does not contain
        defaultSalesinvoiceFiltering("vehicleno.doesNotContain=" + UPDATED_VEHICLENO, "vehicleno.doesNotContain=" + DEFAULT_VEHICLENO);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNextmeterIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nextmeter equals to
        defaultSalesinvoiceFiltering("nextmeter.equals=" + DEFAULT_NEXTMETER, "nextmeter.equals=" + UPDATED_NEXTMETER);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNextmeterIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nextmeter in
        defaultSalesinvoiceFiltering("nextmeter.in=" + DEFAULT_NEXTMETER + "," + UPDATED_NEXTMETER, "nextmeter.in=" + UPDATED_NEXTMETER);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNextmeterIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nextmeter is not null
        defaultSalesinvoiceFiltering("nextmeter.specified=true", "nextmeter.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNextmeterContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nextmeter contains
        defaultSalesinvoiceFiltering("nextmeter.contains=" + DEFAULT_NEXTMETER, "nextmeter.contains=" + UPDATED_NEXTMETER);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNextmeterNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nextmeter does not contain
        defaultSalesinvoiceFiltering("nextmeter.doesNotContain=" + UPDATED_NEXTMETER, "nextmeter.doesNotContain=" + DEFAULT_NEXTMETER);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCurrentmeterIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where currentmeter equals to
        defaultSalesinvoiceFiltering("currentmeter.equals=" + DEFAULT_CURRENTMETER, "currentmeter.equals=" + UPDATED_CURRENTMETER);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCurrentmeterIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where currentmeter in
        defaultSalesinvoiceFiltering(
            "currentmeter.in=" + DEFAULT_CURRENTMETER + "," + UPDATED_CURRENTMETER,
            "currentmeter.in=" + UPDATED_CURRENTMETER
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCurrentmeterIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where currentmeter is not null
        defaultSalesinvoiceFiltering("currentmeter.specified=true", "currentmeter.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCurrentmeterContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where currentmeter contains
        defaultSalesinvoiceFiltering("currentmeter.contains=" + DEFAULT_CURRENTMETER, "currentmeter.contains=" + UPDATED_CURRENTMETER);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByCurrentmeterNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where currentmeter does not contain
        defaultSalesinvoiceFiltering(
            "currentmeter.doesNotContain=" + UPDATED_CURRENTMETER,
            "currentmeter.doesNotContain=" + DEFAULT_CURRENTMETER
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where remarks equals to
        defaultSalesinvoiceFiltering("remarks.equals=" + DEFAULT_REMARKS, "remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where remarks in
        defaultSalesinvoiceFiltering("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS, "remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where remarks is not null
        defaultSalesinvoiceFiltering("remarks.specified=true", "remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByRemarksContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where remarks contains
        defaultSalesinvoiceFiltering("remarks.contains=" + DEFAULT_REMARKS, "remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where remarks does not contain
        defaultSalesinvoiceFiltering("remarks.doesNotContain=" + UPDATED_REMARKS, "remarks.doesNotContain=" + DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByHasdummybillIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where hasdummybill equals to
        defaultSalesinvoiceFiltering("hasdummybill.equals=" + DEFAULT_HASDUMMYBILL, "hasdummybill.equals=" + UPDATED_HASDUMMYBILL);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByHasdummybillIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where hasdummybill in
        defaultSalesinvoiceFiltering(
            "hasdummybill.in=" + DEFAULT_HASDUMMYBILL + "," + UPDATED_HASDUMMYBILL,
            "hasdummybill.in=" + UPDATED_HASDUMMYBILL
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByHasdummybillIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where hasdummybill is not null
        defaultSalesinvoiceFiltering("hasdummybill.specified=true", "hasdummybill.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillid equals to
        defaultSalesinvoiceFiltering("dummybillid.equals=" + DEFAULT_DUMMYBILLID, "dummybillid.equals=" + UPDATED_DUMMYBILLID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillid in
        defaultSalesinvoiceFiltering(
            "dummybillid.in=" + DEFAULT_DUMMYBILLID + "," + UPDATED_DUMMYBILLID,
            "dummybillid.in=" + UPDATED_DUMMYBILLID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillid is not null
        defaultSalesinvoiceFiltering("dummybillid.specified=true", "dummybillid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillid is greater than or equal to
        defaultSalesinvoiceFiltering(
            "dummybillid.greaterThanOrEqual=" + DEFAULT_DUMMYBILLID,
            "dummybillid.greaterThanOrEqual=" + UPDATED_DUMMYBILLID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillid is less than or equal to
        defaultSalesinvoiceFiltering(
            "dummybillid.lessThanOrEqual=" + DEFAULT_DUMMYBILLID,
            "dummybillid.lessThanOrEqual=" + SMALLER_DUMMYBILLID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillid is less than
        defaultSalesinvoiceFiltering("dummybillid.lessThan=" + UPDATED_DUMMYBILLID, "dummybillid.lessThan=" + DEFAULT_DUMMYBILLID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillid is greater than
        defaultSalesinvoiceFiltering("dummybillid.greaterThan=" + SMALLER_DUMMYBILLID, "dummybillid.greaterThan=" + DEFAULT_DUMMYBILLID);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillamount equals to
        defaultSalesinvoiceFiltering(
            "dummybillamount.equals=" + DEFAULT_DUMMYBILLAMOUNT,
            "dummybillamount.equals=" + UPDATED_DUMMYBILLAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillamount in
        defaultSalesinvoiceFiltering(
            "dummybillamount.in=" + DEFAULT_DUMMYBILLAMOUNT + "," + UPDATED_DUMMYBILLAMOUNT,
            "dummybillamount.in=" + UPDATED_DUMMYBILLAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillamount is not null
        defaultSalesinvoiceFiltering("dummybillamount.specified=true", "dummybillamount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillamount is greater than or equal to
        defaultSalesinvoiceFiltering(
            "dummybillamount.greaterThanOrEqual=" + DEFAULT_DUMMYBILLAMOUNT,
            "dummybillamount.greaterThanOrEqual=" + UPDATED_DUMMYBILLAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillamount is less than or equal to
        defaultSalesinvoiceFiltering(
            "dummybillamount.lessThanOrEqual=" + DEFAULT_DUMMYBILLAMOUNT,
            "dummybillamount.lessThanOrEqual=" + SMALLER_DUMMYBILLAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillamount is less than
        defaultSalesinvoiceFiltering(
            "dummybillamount.lessThan=" + UPDATED_DUMMYBILLAMOUNT,
            "dummybillamount.lessThan=" + DEFAULT_DUMMYBILLAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummybillamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummybillamount is greater than
        defaultSalesinvoiceFiltering(
            "dummybillamount.greaterThan=" + SMALLER_DUMMYBILLAMOUNT,
            "dummybillamount.greaterThan=" + DEFAULT_DUMMYBILLAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummycommisionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummycommision equals to
        defaultSalesinvoiceFiltering("dummycommision.equals=" + DEFAULT_DUMMYCOMMISION, "dummycommision.equals=" + UPDATED_DUMMYCOMMISION);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummycommisionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummycommision in
        defaultSalesinvoiceFiltering(
            "dummycommision.in=" + DEFAULT_DUMMYCOMMISION + "," + UPDATED_DUMMYCOMMISION,
            "dummycommision.in=" + UPDATED_DUMMYCOMMISION
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummycommisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummycommision is not null
        defaultSalesinvoiceFiltering("dummycommision.specified=true", "dummycommision.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummycommisionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummycommision is greater than or equal to
        defaultSalesinvoiceFiltering(
            "dummycommision.greaterThanOrEqual=" + DEFAULT_DUMMYCOMMISION,
            "dummycommision.greaterThanOrEqual=" + UPDATED_DUMMYCOMMISION
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummycommisionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummycommision is less than or equal to
        defaultSalesinvoiceFiltering(
            "dummycommision.lessThanOrEqual=" + DEFAULT_DUMMYCOMMISION,
            "dummycommision.lessThanOrEqual=" + SMALLER_DUMMYCOMMISION
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummycommisionIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummycommision is less than
        defaultSalesinvoiceFiltering(
            "dummycommision.lessThan=" + UPDATED_DUMMYCOMMISION,
            "dummycommision.lessThan=" + DEFAULT_DUMMYCOMMISION
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDummycommisionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where dummycommision is greater than
        defaultSalesinvoiceFiltering(
            "dummycommision.greaterThan=" + SMALLER_DUMMYCOMMISION,
            "dummycommision.greaterThan=" + DEFAULT_DUMMYCOMMISION
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsserviceinvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isserviceinvoice equals to
        defaultSalesinvoiceFiltering(
            "isserviceinvoice.equals=" + DEFAULT_ISSERVICEINVOICE,
            "isserviceinvoice.equals=" + UPDATED_ISSERVICEINVOICE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsserviceinvoiceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isserviceinvoice in
        defaultSalesinvoiceFiltering(
            "isserviceinvoice.in=" + DEFAULT_ISSERVICEINVOICE + "," + UPDATED_ISSERVICEINVOICE,
            "isserviceinvoice.in=" + UPDATED_ISSERVICEINVOICE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsserviceinvoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isserviceinvoice is not null
        defaultSalesinvoiceFiltering("isserviceinvoice.specified=true", "isserviceinvoice.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNbtamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nbtamount equals to
        defaultSalesinvoiceFiltering("nbtamount.equals=" + DEFAULT_NBTAMOUNT, "nbtamount.equals=" + UPDATED_NBTAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNbtamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nbtamount in
        defaultSalesinvoiceFiltering("nbtamount.in=" + DEFAULT_NBTAMOUNT + "," + UPDATED_NBTAMOUNT, "nbtamount.in=" + UPDATED_NBTAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNbtamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nbtamount is not null
        defaultSalesinvoiceFiltering("nbtamount.specified=true", "nbtamount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNbtamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nbtamount is greater than or equal to
        defaultSalesinvoiceFiltering(
            "nbtamount.greaterThanOrEqual=" + DEFAULT_NBTAMOUNT,
            "nbtamount.greaterThanOrEqual=" + UPDATED_NBTAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNbtamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nbtamount is less than or equal to
        defaultSalesinvoiceFiltering("nbtamount.lessThanOrEqual=" + DEFAULT_NBTAMOUNT, "nbtamount.lessThanOrEqual=" + SMALLER_NBTAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNbtamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nbtamount is less than
        defaultSalesinvoiceFiltering("nbtamount.lessThan=" + UPDATED_NBTAMOUNT, "nbtamount.lessThan=" + DEFAULT_NBTAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByNbtamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where nbtamount is greater than
        defaultSalesinvoiceFiltering("nbtamount.greaterThan=" + SMALLER_NBTAMOUNT, "nbtamount.greaterThan=" + DEFAULT_NBTAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVatamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vatamount equals to
        defaultSalesinvoiceFiltering("vatamount.equals=" + DEFAULT_VATAMOUNT, "vatamount.equals=" + UPDATED_VATAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVatamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vatamount in
        defaultSalesinvoiceFiltering("vatamount.in=" + DEFAULT_VATAMOUNT + "," + UPDATED_VATAMOUNT, "vatamount.in=" + UPDATED_VATAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVatamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vatamount is not null
        defaultSalesinvoiceFiltering("vatamount.specified=true", "vatamount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVatamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vatamount is greater than or equal to
        defaultSalesinvoiceFiltering(
            "vatamount.greaterThanOrEqual=" + DEFAULT_VATAMOUNT,
            "vatamount.greaterThanOrEqual=" + UPDATED_VATAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVatamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vatamount is less than or equal to
        defaultSalesinvoiceFiltering("vatamount.lessThanOrEqual=" + DEFAULT_VATAMOUNT, "vatamount.lessThanOrEqual=" + SMALLER_VATAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVatamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vatamount is less than
        defaultSalesinvoiceFiltering("vatamount.lessThan=" + UPDATED_VATAMOUNT, "vatamount.lessThan=" + DEFAULT_VATAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByVatamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where vatamount is greater than
        defaultSalesinvoiceFiltering("vatamount.greaterThan=" + SMALLER_VATAMOUNT, "vatamount.greaterThan=" + DEFAULT_VATAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarecompanyidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecompanyid equals to
        defaultSalesinvoiceFiltering(
            "autocarecompanyid.equals=" + DEFAULT_AUTOCARECOMPANYID,
            "autocarecompanyid.equals=" + UPDATED_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarecompanyidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecompanyid in
        defaultSalesinvoiceFiltering(
            "autocarecompanyid.in=" + DEFAULT_AUTOCARECOMPANYID + "," + UPDATED_AUTOCARECOMPANYID,
            "autocarecompanyid.in=" + UPDATED_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarecompanyidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecompanyid is not null
        defaultSalesinvoiceFiltering("autocarecompanyid.specified=true", "autocarecompanyid.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarecompanyidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecompanyid is greater than or equal to
        defaultSalesinvoiceFiltering(
            "autocarecompanyid.greaterThanOrEqual=" + DEFAULT_AUTOCARECOMPANYID,
            "autocarecompanyid.greaterThanOrEqual=" + UPDATED_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarecompanyidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecompanyid is less than or equal to
        defaultSalesinvoiceFiltering(
            "autocarecompanyid.lessThanOrEqual=" + DEFAULT_AUTOCARECOMPANYID,
            "autocarecompanyid.lessThanOrEqual=" + SMALLER_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarecompanyidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecompanyid is less than
        defaultSalesinvoiceFiltering(
            "autocarecompanyid.lessThan=" + UPDATED_AUTOCARECOMPANYID,
            "autocarecompanyid.lessThan=" + DEFAULT_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAutocarecompanyidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where autocarecompanyid is greater than
        defaultSalesinvoiceFiltering(
            "autocarecompanyid.greaterThan=" + SMALLER_AUTOCARECOMPANYID,
            "autocarecompanyid.greaterThan=" + DEFAULT_AUTOCARECOMPANYID
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIscompanyinvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where iscompanyinvoice equals to
        defaultSalesinvoiceFiltering(
            "iscompanyinvoice.equals=" + DEFAULT_ISCOMPANYINVOICE,
            "iscompanyinvoice.equals=" + UPDATED_ISCOMPANYINVOICE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIscompanyinvoiceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where iscompanyinvoice in
        defaultSalesinvoiceFiltering(
            "iscompanyinvoice.in=" + DEFAULT_ISCOMPANYINVOICE + "," + UPDATED_ISCOMPANYINVOICE,
            "iscompanyinvoice.in=" + UPDATED_ISCOMPANYINVOICE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIscompanyinvoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where iscompanyinvoice is not null
        defaultSalesinvoiceFiltering("iscompanyinvoice.specified=true", "iscompanyinvoice.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcanceldateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcanceldate equals to
        defaultSalesinvoiceFiltering("invcanceldate.equals=" + DEFAULT_INVCANCELDATE, "invcanceldate.equals=" + UPDATED_INVCANCELDATE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcanceldateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcanceldate in
        defaultSalesinvoiceFiltering(
            "invcanceldate.in=" + DEFAULT_INVCANCELDATE + "," + UPDATED_INVCANCELDATE,
            "invcanceldate.in=" + UPDATED_INVCANCELDATE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcanceldateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcanceldate is not null
        defaultSalesinvoiceFiltering("invcanceldate.specified=true", "invcanceldate.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcancelbyIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcancelby equals to
        defaultSalesinvoiceFiltering("invcancelby.equals=" + DEFAULT_INVCANCELBY, "invcancelby.equals=" + UPDATED_INVCANCELBY);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcancelbyIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcancelby in
        defaultSalesinvoiceFiltering(
            "invcancelby.in=" + DEFAULT_INVCANCELBY + "," + UPDATED_INVCANCELBY,
            "invcancelby.in=" + UPDATED_INVCANCELBY
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcancelbyIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcancelby is not null
        defaultSalesinvoiceFiltering("invcancelby.specified=true", "invcancelby.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcancelbyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcancelby is greater than or equal to
        defaultSalesinvoiceFiltering(
            "invcancelby.greaterThanOrEqual=" + DEFAULT_INVCANCELBY,
            "invcancelby.greaterThanOrEqual=" + UPDATED_INVCANCELBY
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcancelbyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcancelby is less than or equal to
        defaultSalesinvoiceFiltering(
            "invcancelby.lessThanOrEqual=" + DEFAULT_INVCANCELBY,
            "invcancelby.lessThanOrEqual=" + SMALLER_INVCANCELBY
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcancelbyIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcancelby is less than
        defaultSalesinvoiceFiltering("invcancelby.lessThan=" + UPDATED_INVCANCELBY, "invcancelby.lessThan=" + DEFAULT_INVCANCELBY);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByInvcancelbyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where invcancelby is greater than
        defaultSalesinvoiceFiltering("invcancelby.greaterThan=" + SMALLER_INVCANCELBY, "invcancelby.greaterThan=" + DEFAULT_INVCANCELBY);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsvatinvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isvatinvoice equals to
        defaultSalesinvoiceFiltering("isvatinvoice.equals=" + DEFAULT_ISVATINVOICE, "isvatinvoice.equals=" + UPDATED_ISVATINVOICE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsvatinvoiceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isvatinvoice in
        defaultSalesinvoiceFiltering(
            "isvatinvoice.in=" + DEFAULT_ISVATINVOICE + "," + UPDATED_ISVATINVOICE,
            "isvatinvoice.in=" + UPDATED_ISVATINVOICE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByIsvatinvoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where isvatinvoice is not null
        defaultSalesinvoiceFiltering("isvatinvoice.specified=true", "isvatinvoice.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaymenttypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paymenttype equals to
        defaultSalesinvoiceFiltering("paymenttype.equals=" + DEFAULT_PAYMENTTYPE, "paymenttype.equals=" + UPDATED_PAYMENTTYPE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaymenttypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paymenttype in
        defaultSalesinvoiceFiltering(
            "paymenttype.in=" + DEFAULT_PAYMENTTYPE + "," + UPDATED_PAYMENTTYPE,
            "paymenttype.in=" + UPDATED_PAYMENTTYPE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaymenttypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paymenttype is not null
        defaultSalesinvoiceFiltering("paymenttype.specified=true", "paymenttype.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaymenttypeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paymenttype contains
        defaultSalesinvoiceFiltering("paymenttype.contains=" + DEFAULT_PAYMENTTYPE, "paymenttype.contains=" + UPDATED_PAYMENTTYPE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPaymenttypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where paymenttype does not contain
        defaultSalesinvoiceFiltering(
            "paymenttype.doesNotContain=" + UPDATED_PAYMENTTYPE,
            "paymenttype.doesNotContain=" + DEFAULT_PAYMENTTYPE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPendingamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where pendingamount equals to
        defaultSalesinvoiceFiltering("pendingamount.equals=" + DEFAULT_PENDINGAMOUNT, "pendingamount.equals=" + UPDATED_PENDINGAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPendingamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where pendingamount in
        defaultSalesinvoiceFiltering(
            "pendingamount.in=" + DEFAULT_PENDINGAMOUNT + "," + UPDATED_PENDINGAMOUNT,
            "pendingamount.in=" + UPDATED_PENDINGAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPendingamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where pendingamount is not null
        defaultSalesinvoiceFiltering("pendingamount.specified=true", "pendingamount.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPendingamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where pendingamount is greater than or equal to
        defaultSalesinvoiceFiltering(
            "pendingamount.greaterThanOrEqual=" + DEFAULT_PENDINGAMOUNT,
            "pendingamount.greaterThanOrEqual=" + UPDATED_PENDINGAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPendingamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where pendingamount is less than or equal to
        defaultSalesinvoiceFiltering(
            "pendingamount.lessThanOrEqual=" + DEFAULT_PENDINGAMOUNT,
            "pendingamount.lessThanOrEqual=" + SMALLER_PENDINGAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPendingamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where pendingamount is less than
        defaultSalesinvoiceFiltering("pendingamount.lessThan=" + UPDATED_PENDINGAMOUNT, "pendingamount.lessThan=" + DEFAULT_PENDINGAMOUNT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByPendingamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where pendingamount is greater than
        defaultSalesinvoiceFiltering(
            "pendingamount.greaterThan=" + SMALLER_PENDINGAMOUNT,
            "pendingamount.greaterThan=" + DEFAULT_PENDINGAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAdvancepaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where advancepayment equals to
        defaultSalesinvoiceFiltering("advancepayment.equals=" + DEFAULT_ADVANCEPAYMENT, "advancepayment.equals=" + UPDATED_ADVANCEPAYMENT);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAdvancepaymentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where advancepayment in
        defaultSalesinvoiceFiltering(
            "advancepayment.in=" + DEFAULT_ADVANCEPAYMENT + "," + UPDATED_ADVANCEPAYMENT,
            "advancepayment.in=" + UPDATED_ADVANCEPAYMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAdvancepaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where advancepayment is not null
        defaultSalesinvoiceFiltering("advancepayment.specified=true", "advancepayment.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAdvancepaymentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where advancepayment is greater than or equal to
        defaultSalesinvoiceFiltering(
            "advancepayment.greaterThanOrEqual=" + DEFAULT_ADVANCEPAYMENT,
            "advancepayment.greaterThanOrEqual=" + UPDATED_ADVANCEPAYMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAdvancepaymentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where advancepayment is less than or equal to
        defaultSalesinvoiceFiltering(
            "advancepayment.lessThanOrEqual=" + DEFAULT_ADVANCEPAYMENT,
            "advancepayment.lessThanOrEqual=" + SMALLER_ADVANCEPAYMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAdvancepaymentIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where advancepayment is less than
        defaultSalesinvoiceFiltering(
            "advancepayment.lessThan=" + UPDATED_ADVANCEPAYMENT,
            "advancepayment.lessThan=" + DEFAULT_ADVANCEPAYMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByAdvancepaymentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where advancepayment is greater than
        defaultSalesinvoiceFiltering(
            "advancepayment.greaterThan=" + SMALLER_ADVANCEPAYMENT,
            "advancepayment.greaterThan=" + DEFAULT_ADVANCEPAYMENT
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDiscountcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where discountcode equals to
        defaultSalesinvoiceFiltering("discountcode.equals=" + DEFAULT_DISCOUNTCODE, "discountcode.equals=" + UPDATED_DISCOUNTCODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDiscountcodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where discountcode in
        defaultSalesinvoiceFiltering(
            "discountcode.in=" + DEFAULT_DISCOUNTCODE + "," + UPDATED_DISCOUNTCODE,
            "discountcode.in=" + UPDATED_DISCOUNTCODE
        );
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDiscountcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where discountcode is not null
        defaultSalesinvoiceFiltering("discountcode.specified=true", "discountcode.specified=false");
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDiscountcodeContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where discountcode contains
        defaultSalesinvoiceFiltering("discountcode.contains=" + DEFAULT_DISCOUNTCODE, "discountcode.contains=" + UPDATED_DISCOUNTCODE);
    }

    @Test
    @Transactional
    void getAllSalesinvoicesByDiscountcodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        // Get all the salesinvoiceList where discountcode does not contain
        defaultSalesinvoiceFiltering(
            "discountcode.doesNotContain=" + UPDATED_DISCOUNTCODE,
            "discountcode.doesNotContain=" + DEFAULT_DISCOUNTCODE
        );
    }

    private void defaultSalesinvoiceFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSalesinvoiceShouldBeFound(shouldBeFound);
        defaultSalesinvoiceShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSalesinvoiceShouldBeFound(String filter) throws Exception {
        restSalesinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesinvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].invoicedate").value(hasItem(DEFAULT_INVOICEDATE.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())))
            .andExpect(jsonPath("$.[*].quoteid").value(hasItem(DEFAULT_QUOTEID)))
            .andExpect(jsonPath("$.[*].orderid").value(hasItem(DEFAULT_ORDERID)))
            .andExpect(jsonPath("$.[*].delieverydate").value(hasItem(DEFAULT_DELIEVERYDATE.toString())))
            .andExpect(jsonPath("$.[*].salesrepid").value(hasItem(DEFAULT_SALESREPID)))
            .andExpect(jsonPath("$.[*].salesrepname").value(hasItem(DEFAULT_SALESREPNAME)))
            .andExpect(jsonPath("$.[*].delieverfrom").value(hasItem(DEFAULT_DELIEVERFROM)))
            .andExpect(jsonPath("$.[*].customerid").value(hasItem(DEFAULT_CUSTOMERID)))
            .andExpect(jsonPath("$.[*].customername").value(hasItem(DEFAULT_CUSTOMERNAME)))
            .andExpect(jsonPath("$.[*].customeraddress").value(hasItem(DEFAULT_CUSTOMERADDRESS)))
            .andExpect(jsonPath("$.[*].deliveryaddress").value(hasItem(DEFAULT_DELIVERYADDRESS)))
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].totaltax").value(hasItem(DEFAULT_TOTALTAX.doubleValue())))
            .andExpect(jsonPath("$.[*].totaldiscount").value(hasItem(DEFAULT_TOTALDISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].nettotal").value(hasItem(DEFAULT_NETTOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].paidamount").value(hasItem(DEFAULT_PAIDAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountowing").value(hasItem(DEFAULT_AMOUNTOWING.doubleValue())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].locationid").value(hasItem(DEFAULT_LOCATIONID)))
            .andExpect(jsonPath("$.[*].locationcode").value(hasItem(DEFAULT_LOCATIONCODE)))
            .andExpect(jsonPath("$.[*].referencecode").value(hasItem(DEFAULT_REFERENCECODE)))
            .andExpect(jsonPath("$.[*].createdbyid").value(hasItem(DEFAULT_CREATEDBYID)))
            .andExpect(jsonPath("$.[*].createdbyname").value(hasItem(DEFAULT_CREATEDBYNAME)))
            .andExpect(jsonPath("$.[*].autocarecharges").value(hasItem(DEFAULT_AUTOCARECHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].autocarejobid").value(hasItem(DEFAULT_AUTOCAREJOBID)))
            .andExpect(jsonPath("$.[*].vehicleno").value(hasItem(DEFAULT_VEHICLENO)))
            .andExpect(jsonPath("$.[*].nextmeter").value(hasItem(DEFAULT_NEXTMETER)))
            .andExpect(jsonPath("$.[*].currentmeter").value(hasItem(DEFAULT_CURRENTMETER)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].hasdummybill").value(hasItem(DEFAULT_HASDUMMYBILL.booleanValue())))
            .andExpect(jsonPath("$.[*].dummybillid").value(hasItem(DEFAULT_DUMMYBILLID)))
            .andExpect(jsonPath("$.[*].dummybillamount").value(hasItem(DEFAULT_DUMMYBILLAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dummycommision").value(hasItem(DEFAULT_DUMMYCOMMISION.doubleValue())))
            .andExpect(jsonPath("$.[*].isserviceinvoice").value(hasItem(DEFAULT_ISSERVICEINVOICE.booleanValue())))
            .andExpect(jsonPath("$.[*].nbtamount").value(hasItem(DEFAULT_NBTAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vatamount").value(hasItem(DEFAULT_VATAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].autocarecompanyid").value(hasItem(DEFAULT_AUTOCARECOMPANYID)))
            .andExpect(jsonPath("$.[*].iscompanyinvoice").value(hasItem(DEFAULT_ISCOMPANYINVOICE.booleanValue())))
            .andExpect(jsonPath("$.[*].invcanceldate").value(hasItem(DEFAULT_INVCANCELDATE.toString())))
            .andExpect(jsonPath("$.[*].invcancelby").value(hasItem(DEFAULT_INVCANCELBY)))
            .andExpect(jsonPath("$.[*].isvatinvoice").value(hasItem(DEFAULT_ISVATINVOICE.booleanValue())))
            .andExpect(jsonPath("$.[*].paymenttype").value(hasItem(DEFAULT_PAYMENTTYPE)))
            .andExpect(jsonPath("$.[*].pendingamount").value(hasItem(DEFAULT_PENDINGAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].advancepayment").value(hasItem(DEFAULT_ADVANCEPAYMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].discountcode").value(hasItem(DEFAULT_DISCOUNTCODE)));

        // Check, that the count call also returns 1
        restSalesinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSalesinvoiceShouldNotBeFound(String filter) throws Exception {
        restSalesinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSalesinvoiceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSalesinvoice() throws Exception {
        // Get the salesinvoice
        restSalesinvoiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSalesinvoice() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesinvoice
        Salesinvoice updatedSalesinvoice = salesinvoiceRepository.findById(salesinvoice.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSalesinvoice are not directly saved in db
        em.detach(updatedSalesinvoice);
        updatedSalesinvoice
            .code(UPDATED_CODE)
            .invoicedate(UPDATED_INVOICEDATE)
            .createddate(UPDATED_CREATEDDATE)
            .quoteid(UPDATED_QUOTEID)
            .orderid(UPDATED_ORDERID)
            .delieverydate(UPDATED_DELIEVERYDATE)
            .salesrepid(UPDATED_SALESREPID)
            .salesrepname(UPDATED_SALESREPNAME)
            .delieverfrom(UPDATED_DELIEVERFROM)
            .customerid(UPDATED_CUSTOMERID)
            .customername(UPDATED_CUSTOMERNAME)
            .customeraddress(UPDATED_CUSTOMERADDRESS)
            .deliveryaddress(UPDATED_DELIVERYADDRESS)
            .subtotal(UPDATED_SUBTOTAL)
            .totaltax(UPDATED_TOTALTAX)
            .totaldiscount(UPDATED_TOTALDISCOUNT)
            .nettotal(UPDATED_NETTOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidamount(UPDATED_PAIDAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .isactive(UPDATED_ISACTIVE)
            .locationid(UPDATED_LOCATIONID)
            .locationcode(UPDATED_LOCATIONCODE)
            .referencecode(UPDATED_REFERENCECODE)
            .createdbyid(UPDATED_CREATEDBYID)
            .createdbyname(UPDATED_CREATEDBYNAME)
            .autocarecharges(UPDATED_AUTOCARECHARGES)
            .autocarejobid(UPDATED_AUTOCAREJOBID)
            .vehicleno(UPDATED_VEHICLENO)
            .nextmeter(UPDATED_NEXTMETER)
            .currentmeter(UPDATED_CURRENTMETER)
            .remarks(UPDATED_REMARKS)
            .hasdummybill(UPDATED_HASDUMMYBILL)
            .dummybillid(UPDATED_DUMMYBILLID)
            .dummybillamount(UPDATED_DUMMYBILLAMOUNT)
            .dummycommision(UPDATED_DUMMYCOMMISION)
            .isserviceinvoice(UPDATED_ISSERVICEINVOICE)
            .nbtamount(UPDATED_NBTAMOUNT)
            .vatamount(UPDATED_VATAMOUNT)
            .autocarecompanyid(UPDATED_AUTOCARECOMPANYID)
            .iscompanyinvoice(UPDATED_ISCOMPANYINVOICE)
            .invcanceldate(UPDATED_INVCANCELDATE)
            .invcancelby(UPDATED_INVCANCELBY)
            .isvatinvoice(UPDATED_ISVATINVOICE)
            .paymenttype(UPDATED_PAYMENTTYPE)
            .pendingamount(UPDATED_PENDINGAMOUNT)
            .advancepayment(UPDATED_ADVANCEPAYMENT)
            .discountcode(UPDATED_DISCOUNTCODE);

        restSalesinvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSalesinvoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSalesinvoice))
            )
            .andExpect(status().isOk());

        // Validate the Salesinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSalesinvoiceToMatchAllProperties(updatedSalesinvoice);
    }

    @Test
    @Transactional
    void putNonExistingSalesinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesinvoice.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesinvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salesinvoice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesinvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salesinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalesinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesinvoice.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesinvoiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(salesinvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salesinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalesinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesinvoice.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesinvoiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(salesinvoice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Salesinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalesinvoiceWithPatch() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesinvoice using partial update
        Salesinvoice partialUpdatedSalesinvoice = new Salesinvoice();
        partialUpdatedSalesinvoice.setId(salesinvoice.getId());

        partialUpdatedSalesinvoice
            .code(UPDATED_CODE)
            .createddate(UPDATED_CREATEDDATE)
            .quoteid(UPDATED_QUOTEID)
            .salesrepid(UPDATED_SALESREPID)
            .salesrepname(UPDATED_SALESREPNAME)
            .delieverfrom(UPDATED_DELIEVERFROM)
            .customerid(UPDATED_CUSTOMERID)
            .customername(UPDATED_CUSTOMERNAME)
            .customeraddress(UPDATED_CUSTOMERADDRESS)
            .deliveryaddress(UPDATED_DELIVERYADDRESS)
            .totaltax(UPDATED_TOTALTAX)
            .message(UPDATED_MESSAGE)
            .lmd(UPDATED_LMD)
            .paidamount(UPDATED_PAIDAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .isactive(UPDATED_ISACTIVE)
            .locationid(UPDATED_LOCATIONID)
            .locationcode(UPDATED_LOCATIONCODE)
            .referencecode(UPDATED_REFERENCECODE)
            .createdbyid(UPDATED_CREATEDBYID)
            .createdbyname(UPDATED_CREATEDBYNAME)
            .autocarecharges(UPDATED_AUTOCARECHARGES)
            .autocarejobid(UPDATED_AUTOCAREJOBID)
            .nextmeter(UPDATED_NEXTMETER)
            .hasdummybill(UPDATED_HASDUMMYBILL)
            .dummybillid(UPDATED_DUMMYBILLID)
            .dummybillamount(UPDATED_DUMMYBILLAMOUNT)
            .dummycommision(UPDATED_DUMMYCOMMISION)
            .isserviceinvoice(UPDATED_ISSERVICEINVOICE)
            .nbtamount(UPDATED_NBTAMOUNT)
            .autocarecompanyid(UPDATED_AUTOCARECOMPANYID)
            .invcanceldate(UPDATED_INVCANCELDATE)
            .isvatinvoice(UPDATED_ISVATINVOICE)
            .paymenttype(UPDATED_PAYMENTTYPE)
            .pendingamount(UPDATED_PENDINGAMOUNT)
            .advancepayment(UPDATED_ADVANCEPAYMENT);

        restSalesinvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesinvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesinvoice))
            )
            .andExpect(status().isOk());

        // Validate the Salesinvoice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesinvoiceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSalesinvoice, salesinvoice),
            getPersistedSalesinvoice(salesinvoice)
        );
    }

    @Test
    @Transactional
    void fullUpdateSalesinvoiceWithPatch() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the salesinvoice using partial update
        Salesinvoice partialUpdatedSalesinvoice = new Salesinvoice();
        partialUpdatedSalesinvoice.setId(salesinvoice.getId());

        partialUpdatedSalesinvoice
            .code(UPDATED_CODE)
            .invoicedate(UPDATED_INVOICEDATE)
            .createddate(UPDATED_CREATEDDATE)
            .quoteid(UPDATED_QUOTEID)
            .orderid(UPDATED_ORDERID)
            .delieverydate(UPDATED_DELIEVERYDATE)
            .salesrepid(UPDATED_SALESREPID)
            .salesrepname(UPDATED_SALESREPNAME)
            .delieverfrom(UPDATED_DELIEVERFROM)
            .customerid(UPDATED_CUSTOMERID)
            .customername(UPDATED_CUSTOMERNAME)
            .customeraddress(UPDATED_CUSTOMERADDRESS)
            .deliveryaddress(UPDATED_DELIVERYADDRESS)
            .subtotal(UPDATED_SUBTOTAL)
            .totaltax(UPDATED_TOTALTAX)
            .totaldiscount(UPDATED_TOTALDISCOUNT)
            .nettotal(UPDATED_NETTOTAL)
            .message(UPDATED_MESSAGE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .paidamount(UPDATED_PAIDAMOUNT)
            .amountowing(UPDATED_AMOUNTOWING)
            .isactive(UPDATED_ISACTIVE)
            .locationid(UPDATED_LOCATIONID)
            .locationcode(UPDATED_LOCATIONCODE)
            .referencecode(UPDATED_REFERENCECODE)
            .createdbyid(UPDATED_CREATEDBYID)
            .createdbyname(UPDATED_CREATEDBYNAME)
            .autocarecharges(UPDATED_AUTOCARECHARGES)
            .autocarejobid(UPDATED_AUTOCAREJOBID)
            .vehicleno(UPDATED_VEHICLENO)
            .nextmeter(UPDATED_NEXTMETER)
            .currentmeter(UPDATED_CURRENTMETER)
            .remarks(UPDATED_REMARKS)
            .hasdummybill(UPDATED_HASDUMMYBILL)
            .dummybillid(UPDATED_DUMMYBILLID)
            .dummybillamount(UPDATED_DUMMYBILLAMOUNT)
            .dummycommision(UPDATED_DUMMYCOMMISION)
            .isserviceinvoice(UPDATED_ISSERVICEINVOICE)
            .nbtamount(UPDATED_NBTAMOUNT)
            .vatamount(UPDATED_VATAMOUNT)
            .autocarecompanyid(UPDATED_AUTOCARECOMPANYID)
            .iscompanyinvoice(UPDATED_ISCOMPANYINVOICE)
            .invcanceldate(UPDATED_INVCANCELDATE)
            .invcancelby(UPDATED_INVCANCELBY)
            .isvatinvoice(UPDATED_ISVATINVOICE)
            .paymenttype(UPDATED_PAYMENTTYPE)
            .pendingamount(UPDATED_PENDINGAMOUNT)
            .advancepayment(UPDATED_ADVANCEPAYMENT)
            .discountcode(UPDATED_DISCOUNTCODE);

        restSalesinvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalesinvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSalesinvoice))
            )
            .andExpect(status().isOk());

        // Validate the Salesinvoice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSalesinvoiceUpdatableFieldsEquals(partialUpdatedSalesinvoice, getPersistedSalesinvoice(partialUpdatedSalesinvoice));
    }

    @Test
    @Transactional
    void patchNonExistingSalesinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesinvoice.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesinvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salesinvoice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesinvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salesinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalesinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesinvoice.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesinvoiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(salesinvoice))
            )
            .andExpect(status().isBadRequest());

        // Validate the Salesinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalesinvoice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        salesinvoice.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalesinvoiceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(salesinvoice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Salesinvoice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalesinvoice() throws Exception {
        // Initialize the database
        insertedSalesinvoice = salesinvoiceRepository.saveAndFlush(salesinvoice);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the salesinvoice
        restSalesinvoiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, salesinvoice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return salesinvoiceRepository.count();
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

    protected Salesinvoice getPersistedSalesinvoice(Salesinvoice salesinvoice) {
        return salesinvoiceRepository.findById(salesinvoice.getId()).orElseThrow();
    }

    protected void assertPersistedSalesinvoiceToMatchAllProperties(Salesinvoice expectedSalesinvoice) {
        assertSalesinvoiceAllPropertiesEquals(expectedSalesinvoice, getPersistedSalesinvoice(expectedSalesinvoice));
    }

    protected void assertPersistedSalesinvoiceToMatchUpdatableProperties(Salesinvoice expectedSalesinvoice) {
        assertSalesinvoiceAllUpdatablePropertiesEquals(expectedSalesinvoice, getPersistedSalesinvoice(expectedSalesinvoice));
    }
}
