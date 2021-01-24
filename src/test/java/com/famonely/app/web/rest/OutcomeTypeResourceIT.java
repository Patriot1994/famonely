package com.famonely.app.web.rest;

import com.famonely.app.FamonelyApp;
import com.famonely.app.domain.OutcomeType;
import com.famonely.app.repository.OutcomeTypeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OutcomeTypeResource} REST controller.
 */
@SpringBootTest(classes = FamonelyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OutcomeTypeResourceIT {

    private static final String DEFAULT_NAME_OF_OUTCOME = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_OUTCOME = "BBBBBBBBBB";

    @Autowired
    private OutcomeTypeRepository outcomeTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOutcomeTypeMockMvc;

    private OutcomeType outcomeType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OutcomeType createEntity(EntityManager em) {
        OutcomeType outcomeType = new OutcomeType()
            .nameOfOutcome(DEFAULT_NAME_OF_OUTCOME);
        return outcomeType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OutcomeType createUpdatedEntity(EntityManager em) {
        OutcomeType outcomeType = new OutcomeType()
            .nameOfOutcome(UPDATED_NAME_OF_OUTCOME);
        return outcomeType;
    }

    @BeforeEach
    public void initTest() {
        outcomeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutcomeType() throws Exception {
        int databaseSizeBeforeCreate = outcomeTypeRepository.findAll().size();
        // Create the OutcomeType
        restOutcomeTypeMockMvc.perform(post("/api/outcome-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeType)))
            .andExpect(status().isCreated());

        // Validate the OutcomeType in the database
        List<OutcomeType> outcomeTypeList = outcomeTypeRepository.findAll();
        assertThat(outcomeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OutcomeType testOutcomeType = outcomeTypeList.get(outcomeTypeList.size() - 1);
        assertThat(testOutcomeType.getNameOfOutcome()).isEqualTo(DEFAULT_NAME_OF_OUTCOME);
    }

    @Test
    @Transactional
    public void createOutcomeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outcomeTypeRepository.findAll().size();

        // Create the OutcomeType with an existing ID
        outcomeType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutcomeTypeMockMvc.perform(post("/api/outcome-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeType)))
            .andExpect(status().isBadRequest());

        // Validate the OutcomeType in the database
        List<OutcomeType> outcomeTypeList = outcomeTypeRepository.findAll();
        assertThat(outcomeTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOutcomeTypes() throws Exception {
        // Initialize the database
        outcomeTypeRepository.saveAndFlush(outcomeType);

        // Get all the outcomeTypeList
        restOutcomeTypeMockMvc.perform(get("/api/outcome-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outcomeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOfOutcome").value(hasItem(DEFAULT_NAME_OF_OUTCOME)));
    }
    
    @Test
    @Transactional
    public void getOutcomeType() throws Exception {
        // Initialize the database
        outcomeTypeRepository.saveAndFlush(outcomeType);

        // Get the outcomeType
        restOutcomeTypeMockMvc.perform(get("/api/outcome-types/{id}", outcomeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(outcomeType.getId().intValue()))
            .andExpect(jsonPath("$.nameOfOutcome").value(DEFAULT_NAME_OF_OUTCOME));
    }
    @Test
    @Transactional
    public void getNonExistingOutcomeType() throws Exception {
        // Get the outcomeType
        restOutcomeTypeMockMvc.perform(get("/api/outcome-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutcomeType() throws Exception {
        // Initialize the database
        outcomeTypeRepository.saveAndFlush(outcomeType);

        int databaseSizeBeforeUpdate = outcomeTypeRepository.findAll().size();

        // Update the outcomeType
        OutcomeType updatedOutcomeType = outcomeTypeRepository.findById(outcomeType.getId()).get();
        // Disconnect from session so that the updates on updatedOutcomeType are not directly saved in db
        em.detach(updatedOutcomeType);
        updatedOutcomeType
            .nameOfOutcome(UPDATED_NAME_OF_OUTCOME);

        restOutcomeTypeMockMvc.perform(put("/api/outcome-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOutcomeType)))
            .andExpect(status().isOk());

        // Validate the OutcomeType in the database
        List<OutcomeType> outcomeTypeList = outcomeTypeRepository.findAll();
        assertThat(outcomeTypeList).hasSize(databaseSizeBeforeUpdate);
        OutcomeType testOutcomeType = outcomeTypeList.get(outcomeTypeList.size() - 1);
        assertThat(testOutcomeType.getNameOfOutcome()).isEqualTo(UPDATED_NAME_OF_OUTCOME);
    }

    @Test
    @Transactional
    public void updateNonExistingOutcomeType() throws Exception {
        int databaseSizeBeforeUpdate = outcomeTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutcomeTypeMockMvc.perform(put("/api/outcome-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomeType)))
            .andExpect(status().isBadRequest());

        // Validate the OutcomeType in the database
        List<OutcomeType> outcomeTypeList = outcomeTypeRepository.findAll();
        assertThat(outcomeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOutcomeType() throws Exception {
        // Initialize the database
        outcomeTypeRepository.saveAndFlush(outcomeType);

        int databaseSizeBeforeDelete = outcomeTypeRepository.findAll().size();

        // Delete the outcomeType
        restOutcomeTypeMockMvc.perform(delete("/api/outcome-types/{id}", outcomeType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OutcomeType> outcomeTypeList = outcomeTypeRepository.findAll();
        assertThat(outcomeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
