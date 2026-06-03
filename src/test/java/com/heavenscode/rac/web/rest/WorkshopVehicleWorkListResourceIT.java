package com.heavenscode.rac.web.rest;

import static com.heavenscode.rac.domain.WorkshopVehicleWorkListAsserts.*;
import static com.heavenscode.rac.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heavenscode.rac.IntegrationTest;
import com.heavenscode.rac.domain.WorkshopVehicleWorkList;
import com.heavenscode.rac.repository.WorkshopVehicleWorkListRepository;
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
 * Integration tests for the {@link WorkshopVehicleWorkListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkshopVehicleWorkListResourceIT {

    private static final Integer DEFAULT_VEHICLEWORKID = 1;
    private static final Integer UPDATED_VEHICLEWORKID = 2;

    private static final Integer DEFAULT_LINEID = 1;
    private static final Integer UPDATED_LINEID = 2;

    private static final Integer DEFAULT_WORKID = 1;
    private static final Integer UPDATED_WORKID = 2;

    private static final String DEFAULT_WORKSHOPWORK = "AAAAAAAAAA";
    private static final String UPDATED_WORKSHOPWORK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISJOBDONE = false;
    private static final Boolean UPDATED_ISJOBDONE = true;

    private static final Instant DEFAULT_JOBDONEDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOBDONEDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_JOBNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_JOBNUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_JOBVALUE = 1F;
    private static final Float UPDATED_JOBVALUE = 2F;

    private static final Float DEFAULT_ESTIMATEVALUE = 1F;
    private static final Float UPDATED_ESTIMATEVALUE = 2F;

    private static final String ENTITY_API_URL = "/api/workshop-vehicle-work-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WorkshopVehicleWorkListRepository workshopVehicleWorkListRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkshopVehicleWorkListMockMvc;

    private WorkshopVehicleWorkList workshopVehicleWorkList;

    private WorkshopVehicleWorkList insertedWorkshopVehicleWorkList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkshopVehicleWorkList createEntity() {
        return new WorkshopVehicleWorkList()
            .vehicleworkid(DEFAULT_VEHICLEWORKID)
            .lineid(DEFAULT_LINEID)
            .workid(DEFAULT_WORKID)
            .workshopwork(DEFAULT_WORKSHOPWORK)
            .isjobdone(DEFAULT_ISJOBDONE)
            .jobdonedate(DEFAULT_JOBDONEDATE)
            .jobnumber(DEFAULT_JOBNUMBER)
            .jobvalue(DEFAULT_JOBVALUE)
            .estimatevalue(DEFAULT_ESTIMATEVALUE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkshopVehicleWorkList createUpdatedEntity() {
        return new WorkshopVehicleWorkList()
            .vehicleworkid(UPDATED_VEHICLEWORKID)
            .lineid(UPDATED_LINEID)
            .workid(UPDATED_WORKID)
            .workshopwork(UPDATED_WORKSHOPWORK)
            .isjobdone(UPDATED_ISJOBDONE)
            .jobdonedate(UPDATED_JOBDONEDATE)
            .jobnumber(UPDATED_JOBNUMBER)
            .jobvalue(UPDATED_JOBVALUE)
            .estimatevalue(UPDATED_ESTIMATEVALUE);
    }

    @BeforeEach
    public void initTest() {
        workshopVehicleWorkList = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedWorkshopVehicleWorkList != null) {
            workshopVehicleWorkListRepository.delete(insertedWorkshopVehicleWorkList);
            insertedWorkshopVehicleWorkList = null;
        }
    }

    @Test
    @Transactional
    void createWorkshopVehicleWorkList() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WorkshopVehicleWorkList
        var returnedWorkshopVehicleWorkList = om.readValue(
            restWorkshopVehicleWorkListMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workshopVehicleWorkList))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WorkshopVehicleWorkList.class
        );

        // Validate the WorkshopVehicleWorkList in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWorkshopVehicleWorkListUpdatableFieldsEquals(
            returnedWorkshopVehicleWorkList,
            getPersistedWorkshopVehicleWorkList(returnedWorkshopVehicleWorkList)
        );

        insertedWorkshopVehicleWorkList = returnedWorkshopVehicleWorkList;
    }

    @Test
    @Transactional
    void createWorkshopVehicleWorkListWithExistingId() throws Exception {
        // Create the WorkshopVehicleWorkList with an existing ID
        workshopVehicleWorkList.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkshopVehicleWorkListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workshopVehicleWorkList)))
            .andExpect(status().isBadRequest());

        // Validate the WorkshopVehicleWorkList in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkshopVehicleWorkLists() throws Exception {
        // Initialize the database
        insertedWorkshopVehicleWorkList = workshopVehicleWorkListRepository.saveAndFlush(workshopVehicleWorkList);

        // Get all the workshopVehicleWorkListList
        restWorkshopVehicleWorkListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workshopVehicleWorkList.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleworkid").value(hasItem(DEFAULT_VEHICLEWORKID)))
            .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
            .andExpect(jsonPath("$.[*].workid").value(hasItem(DEFAULT_WORKID)))
            .andExpect(jsonPath("$.[*].workshopwork").value(hasItem(DEFAULT_WORKSHOPWORK)))
            .andExpect(jsonPath("$.[*].isjobdone").value(hasItem(DEFAULT_ISJOBDONE.booleanValue())))
            .andExpect(jsonPath("$.[*].jobdonedate").value(hasItem(DEFAULT_JOBDONEDATE.toString())))
            .andExpect(jsonPath("$.[*].jobnumber").value(hasItem(DEFAULT_JOBNUMBER)))
            .andExpect(jsonPath("$.[*].jobvalue").value(hasItem(DEFAULT_JOBVALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].estimatevalue").value(hasItem(DEFAULT_ESTIMATEVALUE.doubleValue())));
    }

    @Test
    @Transactional
    void getWorkshopVehicleWorkList() throws Exception {
        // Initialize the database
        insertedWorkshopVehicleWorkList = workshopVehicleWorkListRepository.saveAndFlush(workshopVehicleWorkList);

        // Get the workshopVehicleWorkList
        restWorkshopVehicleWorkListMockMvc
            .perform(get(ENTITY_API_URL_ID, workshopVehicleWorkList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workshopVehicleWorkList.getId().intValue()))
            .andExpect(jsonPath("$.vehicleworkid").value(DEFAULT_VEHICLEWORKID))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.workid").value(DEFAULT_WORKID))
            .andExpect(jsonPath("$.workshopwork").value(DEFAULT_WORKSHOPWORK))
            .andExpect(jsonPath("$.isjobdone").value(DEFAULT_ISJOBDONE.booleanValue()))
            .andExpect(jsonPath("$.jobdonedate").value(DEFAULT_JOBDONEDATE.toString()))
            .andExpect(jsonPath("$.jobnumber").value(DEFAULT_JOBNUMBER))
            .andExpect(jsonPath("$.jobvalue").value(DEFAULT_JOBVALUE.doubleValue()))
            .andExpect(jsonPath("$.estimatevalue").value(DEFAULT_ESTIMATEVALUE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingWorkshopVehicleWorkList() throws Exception {
        // Get the workshopVehicleWorkList
        restWorkshopVehicleWorkListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkshopVehicleWorkList() throws Exception {
        // Initialize the database
        insertedWorkshopVehicleWorkList = workshopVehicleWorkListRepository.saveAndFlush(workshopVehicleWorkList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workshopVehicleWorkList
        WorkshopVehicleWorkList updatedWorkshopVehicleWorkList = workshopVehicleWorkListRepository
            .findById(workshopVehicleWorkList.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedWorkshopVehicleWorkList are not directly saved in db
        em.detach(updatedWorkshopVehicleWorkList);
        updatedWorkshopVehicleWorkList
            .vehicleworkid(UPDATED_VEHICLEWORKID)
            .lineid(UPDATED_LINEID)
            .workid(UPDATED_WORKID)
            .workshopwork(UPDATED_WORKSHOPWORK)
            .isjobdone(UPDATED_ISJOBDONE)
            .jobdonedate(UPDATED_JOBDONEDATE)
            .jobnumber(UPDATED_JOBNUMBER)
            .jobvalue(UPDATED_JOBVALUE)
            .estimatevalue(UPDATED_ESTIMATEVALUE);

        restWorkshopVehicleWorkListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkshopVehicleWorkList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWorkshopVehicleWorkList))
            )
            .andExpect(status().isOk());

        // Validate the WorkshopVehicleWorkList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWorkshopVehicleWorkListToMatchAllProperties(updatedWorkshopVehicleWorkList);
    }

    @Test
    @Transactional
    void putNonExistingWorkshopVehicleWorkList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshopVehicleWorkList.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkshopVehicleWorkListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workshopVehicleWorkList.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(workshopVehicleWorkList))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkshopVehicleWorkList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkshopVehicleWorkList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshopVehicleWorkList.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkshopVehicleWorkListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(workshopVehicleWorkList))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkshopVehicleWorkList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkshopVehicleWorkList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshopVehicleWorkList.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkshopVehicleWorkListMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workshopVehicleWorkList)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkshopVehicleWorkList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkshopVehicleWorkListWithPatch() throws Exception {
        // Initialize the database
        insertedWorkshopVehicleWorkList = workshopVehicleWorkListRepository.saveAndFlush(workshopVehicleWorkList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workshopVehicleWorkList using partial update
        WorkshopVehicleWorkList partialUpdatedWorkshopVehicleWorkList = new WorkshopVehicleWorkList();
        partialUpdatedWorkshopVehicleWorkList.setId(workshopVehicleWorkList.getId());

        partialUpdatedWorkshopVehicleWorkList
            .vehicleworkid(UPDATED_VEHICLEWORKID)
            .workid(UPDATED_WORKID)
            .workshopwork(UPDATED_WORKSHOPWORK)
            .jobdonedate(UPDATED_JOBDONEDATE)
            .jobnumber(UPDATED_JOBNUMBER);

        restWorkshopVehicleWorkListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkshopVehicleWorkList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWorkshopVehicleWorkList))
            )
            .andExpect(status().isOk());

        // Validate the WorkshopVehicleWorkList in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkshopVehicleWorkListUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedWorkshopVehicleWorkList, workshopVehicleWorkList),
            getPersistedWorkshopVehicleWorkList(workshopVehicleWorkList)
        );
    }

    @Test
    @Transactional
    void fullUpdateWorkshopVehicleWorkListWithPatch() throws Exception {
        // Initialize the database
        insertedWorkshopVehicleWorkList = workshopVehicleWorkListRepository.saveAndFlush(workshopVehicleWorkList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workshopVehicleWorkList using partial update
        WorkshopVehicleWorkList partialUpdatedWorkshopVehicleWorkList = new WorkshopVehicleWorkList();
        partialUpdatedWorkshopVehicleWorkList.setId(workshopVehicleWorkList.getId());

        partialUpdatedWorkshopVehicleWorkList
            .vehicleworkid(UPDATED_VEHICLEWORKID)
            .lineid(UPDATED_LINEID)
            .workid(UPDATED_WORKID)
            .workshopwork(UPDATED_WORKSHOPWORK)
            .isjobdone(UPDATED_ISJOBDONE)
            .jobdonedate(UPDATED_JOBDONEDATE)
            .jobnumber(UPDATED_JOBNUMBER)
            .jobvalue(UPDATED_JOBVALUE)
            .estimatevalue(UPDATED_ESTIMATEVALUE);

        restWorkshopVehicleWorkListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkshopVehicleWorkList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWorkshopVehicleWorkList))
            )
            .andExpect(status().isOk());

        // Validate the WorkshopVehicleWorkList in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkshopVehicleWorkListUpdatableFieldsEquals(
            partialUpdatedWorkshopVehicleWorkList,
            getPersistedWorkshopVehicleWorkList(partialUpdatedWorkshopVehicleWorkList)
        );
    }

    @Test
    @Transactional
    void patchNonExistingWorkshopVehicleWorkList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshopVehicleWorkList.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkshopVehicleWorkListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workshopVehicleWorkList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(workshopVehicleWorkList))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkshopVehicleWorkList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkshopVehicleWorkList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshopVehicleWorkList.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkshopVehicleWorkListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(workshopVehicleWorkList))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkshopVehicleWorkList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkshopVehicleWorkList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshopVehicleWorkList.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkshopVehicleWorkListMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(workshopVehicleWorkList))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkshopVehicleWorkList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkshopVehicleWorkList() throws Exception {
        // Initialize the database
        insertedWorkshopVehicleWorkList = workshopVehicleWorkListRepository.saveAndFlush(workshopVehicleWorkList);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the workshopVehicleWorkList
        restWorkshopVehicleWorkListMockMvc
            .perform(delete(ENTITY_API_URL_ID, workshopVehicleWorkList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return workshopVehicleWorkListRepository.count();
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

    protected WorkshopVehicleWorkList getPersistedWorkshopVehicleWorkList(WorkshopVehicleWorkList workshopVehicleWorkList) {
        return workshopVehicleWorkListRepository.findById(workshopVehicleWorkList.getId()).orElseThrow();
    }

    protected void assertPersistedWorkshopVehicleWorkListToMatchAllProperties(WorkshopVehicleWorkList expectedWorkshopVehicleWorkList) {
        assertWorkshopVehicleWorkListAllPropertiesEquals(
            expectedWorkshopVehicleWorkList,
            getPersistedWorkshopVehicleWorkList(expectedWorkshopVehicleWorkList)
        );
    }

    protected void assertPersistedWorkshopVehicleWorkListToMatchUpdatableProperties(
        WorkshopVehicleWorkList expectedWorkshopVehicleWorkList
    ) {
        assertWorkshopVehicleWorkListAllUpdatablePropertiesEquals(
            expectedWorkshopVehicleWorkList,
            getPersistedWorkshopVehicleWorkList(expectedWorkshopVehicleWorkList)
        );
    }
}
