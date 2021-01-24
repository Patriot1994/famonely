package com.famonely.app.web.rest;

import com.famonely.app.FamonelyApp;
import com.famonely.app.domain.IncomeType;
import com.famonely.app.repository.IncomeTypeRepository;

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
 * Integration tests for the {@link IncomeTypeResource} REST controller.
 */
@SpringBootTest(classes = FamonelyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IncomeTypeResourceIT {

    private static final String DEFAULT_NAME_OF_OUTCOME = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_OUTCOME = "BBBBBBBBBB";

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomeTypeMockMvc;

    private IncomeType incomeType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomeType createEntity(EntityManager em) {
        IncomeType incomeType = new IncomeType()
            .nameOfOutcome(DEFAULT_NAME_OF_OUTCOME);
        return incomeType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomeType createUpdatedEntity(EntityManager em) {
        IncomeType incomeType = new IncomeType()
            .nameOfOutcome(UPDATED_NAME_OF_OUTCOME);
        return incomeType;
    }

    @BeforeEach
    public void initTest() {
        incomeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomeType() throws Exception {
        int databaseSizeBeforeCreate = incomeTypeRepository.findAll().size();
        // Create the IncomeType
        restIncomeTypeMockMvc.perform(post("/api/income-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomeType)))
            .andExpect(status().isCreated());

        // Validate the IncomeType in the database
        List<IncomeType> incomeTypeList = incomeTypeRepository.findAll();
        assertThat(incomeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IncomeType testIncomeType = incomeTypeList.get(incomeTypeList.size() - 1);
        assertThat(testIncomeType.getNameOfOutcome()).isEqualTo(DEFAULT_NAME_OF_OUTCOME);
    }

    @Test
    @Transactional
    public void createIncomeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomeTypeRepository.findAll().size();

        // Create the IncomeType with an existing ID
        incomeType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomeTypeMockMvc.perform(post("/api/income-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomeType)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeType in the database
        List<IncomeType> incomeTypeList = incomeTypeRepository.findAll();
        assertThat(incomeTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIncomeTypes() throws Exception {
        // Initialize the database
        incomeTypeRepository.saveAndFlush(incomeType);

        // Get all the incomeTypeList
        restIncomeTypeMockMvc.perform(get("/api/income-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOfOutcome").value(hasItem(DEFAULT_NAME_OF_OUTCOME)));
    }
    
    @Test
    @Transactional
    public void getIncomeType() throws Exception {
        // Initialize the database
        incomeTypeRepository.saveAndFlush(incomeType);

        // Get the incomeType
        restIncomeTypeMockMvc.perform(get("/api/income-types/{id}", incomeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomeType.getId().intValue()))
            .andExpect(jsonPath("$.nameOfOutcome").value(DEFAULT_NAME_OF_OUTCOME));
    }
    @Test
    @Transactional
    public void getNonExistingIncomeType() throws Exception {
        // Get the incomeType
        restIncomeTypeMockMvc.perform(get("/api/income-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomeType() throws Exception {
        // Initialize the database
        incomeTypeRepository.saveAndFlush(incomeType);

        int databaseSizeBeforeUpdate = incomeTypeRepository.findAll().size();

        // Update the incomeType
        IncomeType updatedIncomeType = incomeTypeRepository.findById(incomeType.getId()).get();
        // Disconnect from session so that the updates on updatedIncomeType are not directly saved in db
        em.detach(updatedIncomeType);
        updatedIncomeType
            .nameOfOutcome(UPDATED_NAME_OF_OUTCOME);

        restIncomeTypeMockMvc.perform(put("/api/income-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncomeType)))
            .andExpect(status().isOk());

        // Validate the IncomeType in the database
        List<IncomeType> incomeTypeList = incomeTypeRepository.findAll();
        assertThat(incomeTypeList).hasSize(databaseSizeBeforeUpdate);
        IncomeType testIncomeType = incomeTypeList.get(incomeTypeList.size() - 1);
        assertThat(testIncomeType.getNameOfOutcome()).isEqualTo(UPDATED_NAME_OF_OUTCOME);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomeType() throws Exception {
        int databaseSizeBeforeUpdate = incomeTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeTypeMockMvc.perform(put("/api/income-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomeType)))
            .andExpect(status().isBadRequest());

        // Validate the IncomeType in the database
        List<IncomeType> incomeTypeList = incomeTypeRepository.findAll();
        assertThat(incomeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomeType() throws Exception {
        // Initialize the database
        incomeTypeRepository.saveAndFlush(incomeType);

        int databaseSizeBeforeDelete = incomeTypeRepository.findAll().size();

        // Delete the incomeType
        restIncomeTypeMockMvc.perform(delete("/api/income-types/{id}", incomeType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IncomeType> incomeTypeList = incomeTypeRepository.findAll();
        assertThat(incomeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
