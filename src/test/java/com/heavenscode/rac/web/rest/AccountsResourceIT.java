package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.AccountsAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.Accounts;
import com.heavenscode.rac.repository.AccountsRepository;
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
 * Integration tests for the {@link AccountsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;
    private static final Integer SMALLER_TYPE = 1 - 1;

    private static final Integer DEFAULT_PARENT = 1;
    private static final Integer UPDATED_PARENT = 2;
    private static final Integer SMALLER_PARENT = 1 - 1;

    private static final Float DEFAULT_BALANCE = 1F;
    private static final Float UPDATED_BALANCE = 2F;
    private static final Float SMALLER_BALANCE = 1F - 1F;

    private static final Integer DEFAULT_LMU = 1;
    private static final Integer UPDATED_LMU = 2;
    private static final Integer SMALLER_LMU = 1 - 1;

    private static final Instant DEFAULT_LMD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LMD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_HASBATCHES = false;
    private static final Boolean UPDATED_HASBATCHES = true;

    private static final Float DEFAULT_ACCOUNTVALUE = 1F;
    private static final Float UPDATED_ACCOUNTVALUE = 2F;
    private static final Float SMALLER_ACCOUNTVALUE = 1F - 1F;

    private static final Integer DEFAULT_ACCOUNTLEVEL = 1;
    private static final Integer UPDATED_ACCOUNTLEVEL = 2;
    private static final Integer SMALLER_ACCOUNTLEVEL = 1 - 1;

    private static final Long DEFAULT_ACCOUNTSNUMBERINGSYSTEM = 1L;
    private static final Long UPDATED_ACCOUNTSNUMBERINGSYSTEM = 2L;
    private static final Long SMALLER_ACCOUNTSNUMBERINGSYSTEM = 1L - 1L;

    private static final Integer DEFAULT_SUBPARENTID = 1;
    private static final Integer UPDATED_SUBPARENTID = 2;
    private static final Integer SMALLER_SUBPARENTID = 1 - 1;

    private static final Boolean DEFAULT_CANEDIT = false;
    private static final Boolean UPDATED_CANEDIT = true;

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;
    private static final Float SMALLER_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_CREDITAMOUNT = 1F;
    private static final Float UPDATED_CREDITAMOUNT = 2F;
    private static final Float SMALLER_CREDITAMOUNT = 1F - 1F;

    private static final Float DEFAULT_DEBITAMOUNT = 1F;
    private static final Float UPDATED_DEBITAMOUNT = 2F;
    private static final Float SMALLER_DEBITAMOUNT = 1F - 1F;

    private static final String DEFAULT_DEBITORCREDIT = "AAAAAAAAAA";
    private static final String UPDATED_DEBITORCREDIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_REPORTTYPE = 1;
    private static final Integer UPDATED_REPORTTYPE = 2;
    private static final Integer SMALLER_REPORTTYPE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsMockMvc;

    private Accounts accounts;

    private Accounts insertedAccounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createEntity() {
        return new Accounts()
            .code(DEFAULT_CODE)
            .date(DEFAULT_DATE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .parent(DEFAULT_PARENT)
            .balance(DEFAULT_BALANCE)
            .lmu(DEFAULT_LMU)
            .lmd(DEFAULT_LMD)
            .hasbatches(DEFAULT_HASBATCHES)
            .accountvalue(DEFAULT_ACCOUNTVALUE)
            .accountlevel(DEFAULT_ACCOUNTLEVEL)
            .accountsnumberingsystem(DEFAULT_ACCOUNTSNUMBERINGSYSTEM)
            .subparentid(DEFAULT_SUBPARENTID)
            .canedit(DEFAULT_CANEDIT)
            .amount(DEFAULT_AMOUNT)
            .creditamount(DEFAULT_CREDITAMOUNT)
            .debitamount(DEFAULT_DEBITAMOUNT)
            .debitorcredit(DEFAULT_DEBITORCREDIT)
            .reporttype(DEFAULT_REPORTTYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createUpdatedEntity() {
        return new Accounts()
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .parent(UPDATED_PARENT)
            .balance(UPDATED_BALANCE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .hasbatches(UPDATED_HASBATCHES)
            .accountvalue(UPDATED_ACCOUNTVALUE)
            .accountlevel(UPDATED_ACCOUNTLEVEL)
            .accountsnumberingsystem(UPDATED_ACCOUNTSNUMBERINGSYSTEM)
            .subparentid(UPDATED_SUBPARENTID)
            .canedit(UPDATED_CANEDIT)
            .amount(UPDATED_AMOUNT)
            .creditamount(UPDATED_CREDITAMOUNT)
            .debitamount(UPDATED_DEBITAMOUNT)
            .debitorcredit(UPDATED_DEBITORCREDIT)
            .reporttype(UPDATED_REPORTTYPE);
    }

    @BeforeEach
    public void initTest() {
        accounts = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAccounts != null) {
            accountsRepository.delete(insertedAccounts);
            insertedAccounts = null;
        }
    }

    @Test
    @Transactional
    void createAccounts() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Accounts
        var returnedAccounts = om.readValue(
            restAccountsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accounts)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Accounts.class
        );

        // Validate the Accounts in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAccountsUpdatableFieldsEquals(returnedAccounts, getPersistedAccounts(returnedAccounts));

        insertedAccounts = returnedAccounts;
    }

    @Test
    @Transactional
    void createAccountsWithExistingId() throws Exception {
        // Create the Accounts with an existing ID
        accounts.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accounts)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccounts() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].hasbatches").value(hasItem(DEFAULT_HASBATCHES.booleanValue())))
            .andExpect(jsonPath("$.[*].accountvalue").value(hasItem(DEFAULT_ACCOUNTVALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].accountlevel").value(hasItem(DEFAULT_ACCOUNTLEVEL)))
            .andExpect(jsonPath("$.[*].accountsnumberingsystem").value(hasItem(DEFAULT_ACCOUNTSNUMBERINGSYSTEM.intValue())))
            .andExpect(jsonPath("$.[*].subparentid").value(hasItem(DEFAULT_SUBPARENTID)))
            .andExpect(jsonPath("$.[*].canedit").value(hasItem(DEFAULT_CANEDIT.booleanValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].creditamount").value(hasItem(DEFAULT_CREDITAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].debitamount").value(hasItem(DEFAULT_DEBITAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].debitorcredit").value(hasItem(DEFAULT_DEBITORCREDIT)))
            .andExpect(jsonPath("$.[*].reporttype").value(hasItem(DEFAULT_REPORTTYPE)));
    }

    @Test
    @Transactional
    void getAccounts() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get the accounts
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL_ID, accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accounts.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.lmu").value(DEFAULT_LMU))
            .andExpect(jsonPath("$.lmd").value(DEFAULT_LMD.toString()))
            .andExpect(jsonPath("$.hasbatches").value(DEFAULT_HASBATCHES.booleanValue()))
            .andExpect(jsonPath("$.accountvalue").value(DEFAULT_ACCOUNTVALUE.doubleValue()))
            .andExpect(jsonPath("$.accountlevel").value(DEFAULT_ACCOUNTLEVEL))
            .andExpect(jsonPath("$.accountsnumberingsystem").value(DEFAULT_ACCOUNTSNUMBERINGSYSTEM.intValue()))
            .andExpect(jsonPath("$.subparentid").value(DEFAULT_SUBPARENTID))
            .andExpect(jsonPath("$.canedit").value(DEFAULT_CANEDIT.booleanValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.creditamount").value(DEFAULT_CREDITAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.debitamount").value(DEFAULT_DEBITAMOUNT.doubleValue()))
            .andExpect(jsonPath("$.debitorcredit").value(DEFAULT_DEBITORCREDIT))
            .andExpect(jsonPath("$.reporttype").value(DEFAULT_REPORTTYPE));
    }

    @Test
    @Transactional
    void getAccountsByIdFiltering() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        Long id = accounts.getId();

        defaultAccountsFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAccountsFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAccountsFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccountsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where code equals to
        defaultAccountsFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAccountsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where code in
        defaultAccountsFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAccountsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where code is not null
        defaultAccountsFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where code contains
        defaultAccountsFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllAccountsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where code does not contain
        defaultAccountsFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllAccountsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where date equals to
        defaultAccountsFiltering("date.equals=" + DEFAULT_DATE, "date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAccountsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where date in
        defaultAccountsFiltering("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE, "date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAccountsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where date is not null
        defaultAccountsFiltering("date.specified=true", "date.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where name equals to
        defaultAccountsFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAccountsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where name in
        defaultAccountsFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAccountsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where name is not null
        defaultAccountsFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where name contains
        defaultAccountsFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAccountsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where name does not contain
        defaultAccountsFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllAccountsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where description equals to
        defaultAccountsFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccountsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where description in
        defaultAccountsFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllAccountsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where description is not null
        defaultAccountsFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where description contains
        defaultAccountsFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccountsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where description does not contain
        defaultAccountsFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllAccountsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where type equals to
        defaultAccountsFiltering("type.equals=" + DEFAULT_TYPE, "type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where type in
        defaultAccountsFiltering("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE, "type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where type is not null
        defaultAccountsFiltering("type.specified=true", "type.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where type is greater than or equal to
        defaultAccountsFiltering("type.greaterThanOrEqual=" + DEFAULT_TYPE, "type.greaterThanOrEqual=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where type is less than or equal to
        defaultAccountsFiltering("type.lessThanOrEqual=" + DEFAULT_TYPE, "type.lessThanOrEqual=" + SMALLER_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where type is less than
        defaultAccountsFiltering("type.lessThan=" + UPDATED_TYPE, "type.lessThan=" + DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where type is greater than
        defaultAccountsFiltering("type.greaterThan=" + SMALLER_TYPE, "type.greaterThan=" + DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where parent equals to
        defaultAccountsFiltering("parent.equals=" + DEFAULT_PARENT, "parent.equals=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllAccountsByParentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where parent in
        defaultAccountsFiltering("parent.in=" + DEFAULT_PARENT + "," + UPDATED_PARENT, "parent.in=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllAccountsByParentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where parent is not null
        defaultAccountsFiltering("parent.specified=true", "parent.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByParentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where parent is greater than or equal to
        defaultAccountsFiltering("parent.greaterThanOrEqual=" + DEFAULT_PARENT, "parent.greaterThanOrEqual=" + UPDATED_PARENT);
    }

    @Test
    @Transactional
    void getAllAccountsByParentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where parent is less than or equal to
        defaultAccountsFiltering("parent.lessThanOrEqual=" + DEFAULT_PARENT, "parent.lessThanOrEqual=" + SMALLER_PARENT);
    }

    @Test
    @Transactional
    void getAllAccountsByParentIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where parent is less than
        defaultAccountsFiltering("parent.lessThan=" + UPDATED_PARENT, "parent.lessThan=" + DEFAULT_PARENT);
    }

    @Test
    @Transactional
    void getAllAccountsByParentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where parent is greater than
        defaultAccountsFiltering("parent.greaterThan=" + SMALLER_PARENT, "parent.greaterThan=" + DEFAULT_PARENT);
    }

    @Test
    @Transactional
    void getAllAccountsByBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where balance equals to
        defaultAccountsFiltering("balance.equals=" + DEFAULT_BALANCE, "balance.equals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void getAllAccountsByBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where balance in
        defaultAccountsFiltering("balance.in=" + DEFAULT_BALANCE + "," + UPDATED_BALANCE, "balance.in=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void getAllAccountsByBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where balance is not null
        defaultAccountsFiltering("balance.specified=true", "balance.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where balance is greater than or equal to
        defaultAccountsFiltering("balance.greaterThanOrEqual=" + DEFAULT_BALANCE, "balance.greaterThanOrEqual=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void getAllAccountsByBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where balance is less than or equal to
        defaultAccountsFiltering("balance.lessThanOrEqual=" + DEFAULT_BALANCE, "balance.lessThanOrEqual=" + SMALLER_BALANCE);
    }

    @Test
    @Transactional
    void getAllAccountsByBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where balance is less than
        defaultAccountsFiltering("balance.lessThan=" + UPDATED_BALANCE, "balance.lessThan=" + DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void getAllAccountsByBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where balance is greater than
        defaultAccountsFiltering("balance.greaterThan=" + SMALLER_BALANCE, "balance.greaterThan=" + DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void getAllAccountsByLmuIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmu equals to
        defaultAccountsFiltering("lmu.equals=" + DEFAULT_LMU, "lmu.equals=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAccountsByLmuIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmu in
        defaultAccountsFiltering("lmu.in=" + DEFAULT_LMU + "," + UPDATED_LMU, "lmu.in=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAccountsByLmuIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmu is not null
        defaultAccountsFiltering("lmu.specified=true", "lmu.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByLmuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmu is greater than or equal to
        defaultAccountsFiltering("lmu.greaterThanOrEqual=" + DEFAULT_LMU, "lmu.greaterThanOrEqual=" + UPDATED_LMU);
    }

    @Test
    @Transactional
    void getAllAccountsByLmuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmu is less than or equal to
        defaultAccountsFiltering("lmu.lessThanOrEqual=" + DEFAULT_LMU, "lmu.lessThanOrEqual=" + SMALLER_LMU);
    }

    @Test
    @Transactional
    void getAllAccountsByLmuIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmu is less than
        defaultAccountsFiltering("lmu.lessThan=" + UPDATED_LMU, "lmu.lessThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAccountsByLmuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmu is greater than
        defaultAccountsFiltering("lmu.greaterThan=" + SMALLER_LMU, "lmu.greaterThan=" + DEFAULT_LMU);
    }

    @Test
    @Transactional
    void getAllAccountsByLmdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmd equals to
        defaultAccountsFiltering("lmd.equals=" + DEFAULT_LMD, "lmd.equals=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAccountsByLmdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmd in
        defaultAccountsFiltering("lmd.in=" + DEFAULT_LMD + "," + UPDATED_LMD, "lmd.in=" + UPDATED_LMD);
    }

    @Test
    @Transactional
    void getAllAccountsByLmdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lmd is not null
        defaultAccountsFiltering("lmd.specified=true", "lmd.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByHasbatchesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hasbatches equals to
        defaultAccountsFiltering("hasbatches.equals=" + DEFAULT_HASBATCHES, "hasbatches.equals=" + UPDATED_HASBATCHES);
    }

    @Test
    @Transactional
    void getAllAccountsByHasbatchesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hasbatches in
        defaultAccountsFiltering("hasbatches.in=" + DEFAULT_HASBATCHES + "," + UPDATED_HASBATCHES, "hasbatches.in=" + UPDATED_HASBATCHES);
    }

    @Test
    @Transactional
    void getAllAccountsByHasbatchesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hasbatches is not null
        defaultAccountsFiltering("hasbatches.specified=true", "hasbatches.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAccountvalueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountvalue equals to
        defaultAccountsFiltering("accountvalue.equals=" + DEFAULT_ACCOUNTVALUE, "accountvalue.equals=" + UPDATED_ACCOUNTVALUE);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountvalueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountvalue in
        defaultAccountsFiltering(
            "accountvalue.in=" + DEFAULT_ACCOUNTVALUE + "," + UPDATED_ACCOUNTVALUE,
            "accountvalue.in=" + UPDATED_ACCOUNTVALUE
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountvalueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountvalue is not null
        defaultAccountsFiltering("accountvalue.specified=true", "accountvalue.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAccountvalueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountvalue is greater than or equal to
        defaultAccountsFiltering(
            "accountvalue.greaterThanOrEqual=" + DEFAULT_ACCOUNTVALUE,
            "accountvalue.greaterThanOrEqual=" + UPDATED_ACCOUNTVALUE
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountvalueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountvalue is less than or equal to
        defaultAccountsFiltering(
            "accountvalue.lessThanOrEqual=" + DEFAULT_ACCOUNTVALUE,
            "accountvalue.lessThanOrEqual=" + SMALLER_ACCOUNTVALUE
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountvalueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountvalue is less than
        defaultAccountsFiltering("accountvalue.lessThan=" + UPDATED_ACCOUNTVALUE, "accountvalue.lessThan=" + DEFAULT_ACCOUNTVALUE);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountvalueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountvalue is greater than
        defaultAccountsFiltering("accountvalue.greaterThan=" + SMALLER_ACCOUNTVALUE, "accountvalue.greaterThan=" + DEFAULT_ACCOUNTVALUE);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountlevelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountlevel equals to
        defaultAccountsFiltering("accountlevel.equals=" + DEFAULT_ACCOUNTLEVEL, "accountlevel.equals=" + UPDATED_ACCOUNTLEVEL);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountlevelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountlevel in
        defaultAccountsFiltering(
            "accountlevel.in=" + DEFAULT_ACCOUNTLEVEL + "," + UPDATED_ACCOUNTLEVEL,
            "accountlevel.in=" + UPDATED_ACCOUNTLEVEL
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountlevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountlevel is not null
        defaultAccountsFiltering("accountlevel.specified=true", "accountlevel.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAccountlevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountlevel is greater than or equal to
        defaultAccountsFiltering(
            "accountlevel.greaterThanOrEqual=" + DEFAULT_ACCOUNTLEVEL,
            "accountlevel.greaterThanOrEqual=" + UPDATED_ACCOUNTLEVEL
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountlevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountlevel is less than or equal to
        defaultAccountsFiltering(
            "accountlevel.lessThanOrEqual=" + DEFAULT_ACCOUNTLEVEL,
            "accountlevel.lessThanOrEqual=" + SMALLER_ACCOUNTLEVEL
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountlevelIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountlevel is less than
        defaultAccountsFiltering("accountlevel.lessThan=" + UPDATED_ACCOUNTLEVEL, "accountlevel.lessThan=" + DEFAULT_ACCOUNTLEVEL);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountlevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountlevel is greater than
        defaultAccountsFiltering("accountlevel.greaterThan=" + SMALLER_ACCOUNTLEVEL, "accountlevel.greaterThan=" + DEFAULT_ACCOUNTLEVEL);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountsnumberingsystemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountsnumberingsystem equals to
        defaultAccountsFiltering(
            "accountsnumberingsystem.equals=" + DEFAULT_ACCOUNTSNUMBERINGSYSTEM,
            "accountsnumberingsystem.equals=" + UPDATED_ACCOUNTSNUMBERINGSYSTEM
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountsnumberingsystemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountsnumberingsystem in
        defaultAccountsFiltering(
            "accountsnumberingsystem.in=" + DEFAULT_ACCOUNTSNUMBERINGSYSTEM + "," + UPDATED_ACCOUNTSNUMBERINGSYSTEM,
            "accountsnumberingsystem.in=" + UPDATED_ACCOUNTSNUMBERINGSYSTEM
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountsnumberingsystemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountsnumberingsystem is not null
        defaultAccountsFiltering("accountsnumberingsystem.specified=true", "accountsnumberingsystem.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAccountsnumberingsystemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountsnumberingsystem is greater than or equal to
        defaultAccountsFiltering(
            "accountsnumberingsystem.greaterThanOrEqual=" + DEFAULT_ACCOUNTSNUMBERINGSYSTEM,
            "accountsnumberingsystem.greaterThanOrEqual=" + UPDATED_ACCOUNTSNUMBERINGSYSTEM
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountsnumberingsystemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountsnumberingsystem is less than or equal to
        defaultAccountsFiltering(
            "accountsnumberingsystem.lessThanOrEqual=" + DEFAULT_ACCOUNTSNUMBERINGSYSTEM,
            "accountsnumberingsystem.lessThanOrEqual=" + SMALLER_ACCOUNTSNUMBERINGSYSTEM
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountsnumberingsystemIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountsnumberingsystem is less than
        defaultAccountsFiltering(
            "accountsnumberingsystem.lessThan=" + UPDATED_ACCOUNTSNUMBERINGSYSTEM,
            "accountsnumberingsystem.lessThan=" + DEFAULT_ACCOUNTSNUMBERINGSYSTEM
        );
    }

    @Test
    @Transactional
    void getAllAccountsByAccountsnumberingsystemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where accountsnumberingsystem is greater than
        defaultAccountsFiltering(
            "accountsnumberingsystem.greaterThan=" + SMALLER_ACCOUNTSNUMBERINGSYSTEM,
            "accountsnumberingsystem.greaterThan=" + DEFAULT_ACCOUNTSNUMBERINGSYSTEM
        );
    }

    @Test
    @Transactional
    void getAllAccountsBySubparentidIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where subparentid equals to
        defaultAccountsFiltering("subparentid.equals=" + DEFAULT_SUBPARENTID, "subparentid.equals=" + UPDATED_SUBPARENTID);
    }

    @Test
    @Transactional
    void getAllAccountsBySubparentidIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where subparentid in
        defaultAccountsFiltering(
            "subparentid.in=" + DEFAULT_SUBPARENTID + "," + UPDATED_SUBPARENTID,
            "subparentid.in=" + UPDATED_SUBPARENTID
        );
    }

    @Test
    @Transactional
    void getAllAccountsBySubparentidIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where subparentid is not null
        defaultAccountsFiltering("subparentid.specified=true", "subparentid.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsBySubparentidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where subparentid is greater than or equal to
        defaultAccountsFiltering(
            "subparentid.greaterThanOrEqual=" + DEFAULT_SUBPARENTID,
            "subparentid.greaterThanOrEqual=" + UPDATED_SUBPARENTID
        );
    }

    @Test
    @Transactional
    void getAllAccountsBySubparentidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where subparentid is less than or equal to
        defaultAccountsFiltering(
            "subparentid.lessThanOrEqual=" + DEFAULT_SUBPARENTID,
            "subparentid.lessThanOrEqual=" + SMALLER_SUBPARENTID
        );
    }

    @Test
    @Transactional
    void getAllAccountsBySubparentidIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where subparentid is less than
        defaultAccountsFiltering("subparentid.lessThan=" + UPDATED_SUBPARENTID, "subparentid.lessThan=" + DEFAULT_SUBPARENTID);
    }

    @Test
    @Transactional
    void getAllAccountsBySubparentidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where subparentid is greater than
        defaultAccountsFiltering("subparentid.greaterThan=" + SMALLER_SUBPARENTID, "subparentid.greaterThan=" + DEFAULT_SUBPARENTID);
    }

    @Test
    @Transactional
    void getAllAccountsByCaneditIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where canedit equals to
        defaultAccountsFiltering("canedit.equals=" + DEFAULT_CANEDIT, "canedit.equals=" + UPDATED_CANEDIT);
    }

    @Test
    @Transactional
    void getAllAccountsByCaneditIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where canedit in
        defaultAccountsFiltering("canedit.in=" + DEFAULT_CANEDIT + "," + UPDATED_CANEDIT, "canedit.in=" + UPDATED_CANEDIT);
    }

    @Test
    @Transactional
    void getAllAccountsByCaneditIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where canedit is not null
        defaultAccountsFiltering("canedit.specified=true", "canedit.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where amount equals to
        defaultAccountsFiltering("amount.equals=" + DEFAULT_AMOUNT, "amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where amount in
        defaultAccountsFiltering("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT, "amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where amount is not null
        defaultAccountsFiltering("amount.specified=true", "amount.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where amount is greater than or equal to
        defaultAccountsFiltering("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT, "amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where amount is less than or equal to
        defaultAccountsFiltering("amount.lessThanOrEqual=" + DEFAULT_AMOUNT, "amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where amount is less than
        defaultAccountsFiltering("amount.lessThan=" + UPDATED_AMOUNT, "amount.lessThan=" + DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where amount is greater than
        defaultAccountsFiltering("amount.greaterThan=" + SMALLER_AMOUNT, "amount.greaterThan=" + DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByCreditamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where creditamount equals to
        defaultAccountsFiltering("creditamount.equals=" + DEFAULT_CREDITAMOUNT, "creditamount.equals=" + UPDATED_CREDITAMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByCreditamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where creditamount in
        defaultAccountsFiltering(
            "creditamount.in=" + DEFAULT_CREDITAMOUNT + "," + UPDATED_CREDITAMOUNT,
            "creditamount.in=" + UPDATED_CREDITAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAccountsByCreditamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where creditamount is not null
        defaultAccountsFiltering("creditamount.specified=true", "creditamount.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByCreditamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where creditamount is greater than or equal to
        defaultAccountsFiltering(
            "creditamount.greaterThanOrEqual=" + DEFAULT_CREDITAMOUNT,
            "creditamount.greaterThanOrEqual=" + UPDATED_CREDITAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAccountsByCreditamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where creditamount is less than or equal to
        defaultAccountsFiltering(
            "creditamount.lessThanOrEqual=" + DEFAULT_CREDITAMOUNT,
            "creditamount.lessThanOrEqual=" + SMALLER_CREDITAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAccountsByCreditamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where creditamount is less than
        defaultAccountsFiltering("creditamount.lessThan=" + UPDATED_CREDITAMOUNT, "creditamount.lessThan=" + DEFAULT_CREDITAMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByCreditamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where creditamount is greater than
        defaultAccountsFiltering("creditamount.greaterThan=" + SMALLER_CREDITAMOUNT, "creditamount.greaterThan=" + DEFAULT_CREDITAMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByDebitamountIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitamount equals to
        defaultAccountsFiltering("debitamount.equals=" + DEFAULT_DEBITAMOUNT, "debitamount.equals=" + UPDATED_DEBITAMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByDebitamountIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitamount in
        defaultAccountsFiltering(
            "debitamount.in=" + DEFAULT_DEBITAMOUNT + "," + UPDATED_DEBITAMOUNT,
            "debitamount.in=" + UPDATED_DEBITAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAccountsByDebitamountIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitamount is not null
        defaultAccountsFiltering("debitamount.specified=true", "debitamount.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByDebitamountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitamount is greater than or equal to
        defaultAccountsFiltering(
            "debitamount.greaterThanOrEqual=" + DEFAULT_DEBITAMOUNT,
            "debitamount.greaterThanOrEqual=" + UPDATED_DEBITAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAccountsByDebitamountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitamount is less than or equal to
        defaultAccountsFiltering(
            "debitamount.lessThanOrEqual=" + DEFAULT_DEBITAMOUNT,
            "debitamount.lessThanOrEqual=" + SMALLER_DEBITAMOUNT
        );
    }

    @Test
    @Transactional
    void getAllAccountsByDebitamountIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitamount is less than
        defaultAccountsFiltering("debitamount.lessThan=" + UPDATED_DEBITAMOUNT, "debitamount.lessThan=" + DEFAULT_DEBITAMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByDebitamountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitamount is greater than
        defaultAccountsFiltering("debitamount.greaterThan=" + SMALLER_DEBITAMOUNT, "debitamount.greaterThan=" + DEFAULT_DEBITAMOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByDebitorcreditIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitorcredit equals to
        defaultAccountsFiltering("debitorcredit.equals=" + DEFAULT_DEBITORCREDIT, "debitorcredit.equals=" + UPDATED_DEBITORCREDIT);
    }

    @Test
    @Transactional
    void getAllAccountsByDebitorcreditIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitorcredit in
        defaultAccountsFiltering(
            "debitorcredit.in=" + DEFAULT_DEBITORCREDIT + "," + UPDATED_DEBITORCREDIT,
            "debitorcredit.in=" + UPDATED_DEBITORCREDIT
        );
    }

    @Test
    @Transactional
    void getAllAccountsByDebitorcreditIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitorcredit is not null
        defaultAccountsFiltering("debitorcredit.specified=true", "debitorcredit.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByDebitorcreditContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitorcredit contains
        defaultAccountsFiltering("debitorcredit.contains=" + DEFAULT_DEBITORCREDIT, "debitorcredit.contains=" + UPDATED_DEBITORCREDIT);
    }

    @Test
    @Transactional
    void getAllAccountsByDebitorcreditNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where debitorcredit does not contain
        defaultAccountsFiltering(
            "debitorcredit.doesNotContain=" + UPDATED_DEBITORCREDIT,
            "debitorcredit.doesNotContain=" + DEFAULT_DEBITORCREDIT
        );
    }

    @Test
    @Transactional
    void getAllAccountsByReporttypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where reporttype equals to
        defaultAccountsFiltering("reporttype.equals=" + DEFAULT_REPORTTYPE, "reporttype.equals=" + UPDATED_REPORTTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByReporttypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where reporttype in
        defaultAccountsFiltering("reporttype.in=" + DEFAULT_REPORTTYPE + "," + UPDATED_REPORTTYPE, "reporttype.in=" + UPDATED_REPORTTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByReporttypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where reporttype is not null
        defaultAccountsFiltering("reporttype.specified=true", "reporttype.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByReporttypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where reporttype is greater than or equal to
        defaultAccountsFiltering(
            "reporttype.greaterThanOrEqual=" + DEFAULT_REPORTTYPE,
            "reporttype.greaterThanOrEqual=" + UPDATED_REPORTTYPE
        );
    }

    @Test
    @Transactional
    void getAllAccountsByReporttypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where reporttype is less than or equal to
        defaultAccountsFiltering("reporttype.lessThanOrEqual=" + DEFAULT_REPORTTYPE, "reporttype.lessThanOrEqual=" + SMALLER_REPORTTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByReporttypeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where reporttype is less than
        defaultAccountsFiltering("reporttype.lessThan=" + UPDATED_REPORTTYPE, "reporttype.lessThan=" + DEFAULT_REPORTTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByReporttypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where reporttype is greater than
        defaultAccountsFiltering("reporttype.greaterThan=" + SMALLER_REPORTTYPE, "reporttype.greaterThan=" + DEFAULT_REPORTTYPE);
    }

    private void defaultAccountsFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAccountsShouldBeFound(shouldBeFound);
        defaultAccountsShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccountsShouldBeFound(String filter) throws Exception {
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].lmu").value(hasItem(DEFAULT_LMU)))
            .andExpect(jsonPath("$.[*].lmd").value(hasItem(DEFAULT_LMD.toString())))
            .andExpect(jsonPath("$.[*].hasbatches").value(hasItem(DEFAULT_HASBATCHES.booleanValue())))
            .andExpect(jsonPath("$.[*].accountvalue").value(hasItem(DEFAULT_ACCOUNTVALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].accountlevel").value(hasItem(DEFAULT_ACCOUNTLEVEL)))
            .andExpect(jsonPath("$.[*].accountsnumberingsystem").value(hasItem(DEFAULT_ACCOUNTSNUMBERINGSYSTEM.intValue())))
            .andExpect(jsonPath("$.[*].subparentid").value(hasItem(DEFAULT_SUBPARENTID)))
            .andExpect(jsonPath("$.[*].canedit").value(hasItem(DEFAULT_CANEDIT.booleanValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].creditamount").value(hasItem(DEFAULT_CREDITAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].debitamount").value(hasItem(DEFAULT_DEBITAMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].debitorcredit").value(hasItem(DEFAULT_DEBITORCREDIT)))
            .andExpect(jsonPath("$.[*].reporttype").value(hasItem(DEFAULT_REPORTTYPE)));

        // Check, that the count call also returns 1
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccountsShouldNotBeFound(String filter) throws Exception {
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccounts() throws Exception {
        // Get the accounts
        restAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccounts() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accounts
        Accounts updatedAccounts = accountsRepository.findById(accounts.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAccounts are not directly saved in db
        em.detach(updatedAccounts);
        updatedAccounts
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .parent(UPDATED_PARENT)
            .balance(UPDATED_BALANCE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .hasbatches(UPDATED_HASBATCHES)
            .accountvalue(UPDATED_ACCOUNTVALUE)
            .accountlevel(UPDATED_ACCOUNTLEVEL)
            .accountsnumberingsystem(UPDATED_ACCOUNTSNUMBERINGSYSTEM)
            .subparentid(UPDATED_SUBPARENTID)
            .canedit(UPDATED_CANEDIT)
            .amount(UPDATED_AMOUNT)
            .creditamount(UPDATED_CREDITAMOUNT)
            .debitamount(UPDATED_DEBITAMOUNT)
            .debitorcredit(UPDATED_DEBITORCREDIT)
            .reporttype(UPDATED_REPORTTYPE);

        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccounts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAccountsToMatchAllProperties(updatedAccounts);
    }

    @Test
    @Transactional
    void putNonExistingAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accounts.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accounts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accounts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accounts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountsWithPatch() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accounts using partial update
        Accounts partialUpdatedAccounts = new Accounts();
        partialUpdatedAccounts.setId(accounts.getId());

        partialUpdatedAccounts
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .balance(UPDATED_BALANCE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .hasbatches(UPDATED_HASBATCHES)
            .accountlevel(UPDATED_ACCOUNTLEVEL)
            .amount(UPDATED_AMOUNT)
            .debitorcredit(UPDATED_DEBITORCREDIT)
            .reporttype(UPDATED_REPORTTYPE);

        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAccountsUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAccounts, accounts), getPersistedAccounts(accounts));
    }

    @Test
    @Transactional
    void fullUpdateAccountsWithPatch() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accounts using partial update
        Accounts partialUpdatedAccounts = new Accounts();
        partialUpdatedAccounts.setId(accounts.getId());

        partialUpdatedAccounts
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .parent(UPDATED_PARENT)
            .balance(UPDATED_BALANCE)
            .lmu(UPDATED_LMU)
            .lmd(UPDATED_LMD)
            .hasbatches(UPDATED_HASBATCHES)
            .accountvalue(UPDATED_ACCOUNTVALUE)
            .accountlevel(UPDATED_ACCOUNTLEVEL)
            .accountsnumberingsystem(UPDATED_ACCOUNTSNUMBERINGSYSTEM)
            .subparentid(UPDATED_SUBPARENTID)
            .canedit(UPDATED_CANEDIT)
            .amount(UPDATED_AMOUNT)
            .creditamount(UPDATED_CREDITAMOUNT)
            .debitamount(UPDATED_DEBITAMOUNT)
            .debitorcredit(UPDATED_DEBITORCREDIT)
            .reporttype(UPDATED_REPORTTYPE);

        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAccountsUpdatableFieldsEquals(partialUpdatedAccounts, getPersistedAccounts(partialUpdatedAccounts));
    }

    @Test
    @Transactional
    void patchNonExistingAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(accounts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(accounts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccounts() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accounts.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(accounts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accounts in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccounts() throws Exception {
        // Initialize the database
        insertedAccounts = accountsRepository.saveAndFlush(accounts);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the accounts
        restAccountsMockMvc
            .perform(delete(ENTITY_API_URL_ID, accounts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return accountsRepository.count();
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

    protected Accounts getPersistedAccounts(Accounts accounts) {
        return accountsRepository.findById(accounts.getId()).orElseThrow();
    }

    protected void assertPersistedAccountsToMatchAllProperties(Accounts expectedAccounts) {
        assertAccountsAllPropertiesEquals(expectedAccounts, getPersistedAccounts(expectedAccounts));
    }

    protected void assertPersistedAccountsToMatchUpdatableProperties(Accounts expectedAccounts) {
        assertAccountsAllUpdatablePropertiesEquals(expectedAccounts, getPersistedAccounts(expectedAccounts));
    }
}
