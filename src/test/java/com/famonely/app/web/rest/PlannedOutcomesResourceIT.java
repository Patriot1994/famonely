package com.famonely.app.web.rest;

import com.famonely.app.FamonelyApp;
import com.famonely.app.domain.PlannedOutcomes;
import com.famonely.app.repository.PlannedOutcomesRepository;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlannedOutcomesResource} REST controller.
 */
@SpringBootTest(classes = FamonelyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlannedOutcomesResourceIT {

    private static final LocalDate DEFAULT_PLANNED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLANNED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PLANNED_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_PLANNED_MONEY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONEY_TO_GOAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONEY_TO_GOAL = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_ALREADY_PAID = false;
    private static final Boolean UPDATED_IS_ALREADY_PAID = true;

    @Autowired
    private PlannedOutcomesRepository plannedOutcomesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlannedOutcomesMockMvc;

    private PlannedOutcomes plannedOutcomes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlannedOutcomes createEntity(EntityManager em) {
        PlannedOutcomes plannedOutcomes = new PlannedOutcomes()
            .plannedDate(DEFAULT_PLANNED_DATE)
            .plannedMoney(DEFAULT_PLANNED_MONEY)
            .moneyToGoal(DEFAULT_MONEY_TO_GOAL)
            .isAlreadyPaid(DEFAULT_IS_ALREADY_PAID);
        return plannedOutcomes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlannedOutcomes createUpdatedEntity(EntityManager em) {
        PlannedOutcomes plannedOutcomes = new PlannedOutcomes()
            .plannedDate(UPDATED_PLANNED_DATE)
            .plannedMoney(UPDATED_PLANNED_MONEY)
            .moneyToGoal(UPDATED_MONEY_TO_GOAL)
            .isAlreadyPaid(UPDATED_IS_ALREADY_PAID);
        return plannedOutcomes;
    }

    @BeforeEach
    public void initTest() {
        plannedOutcomes = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlannedOutcomes() throws Exception {
        int databaseSizeBeforeCreate = plannedOutcomesRepository.findAll().size();
        // Create the PlannedOutcomes
        restPlannedOutcomesMockMvc.perform(post("/api/planned-outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plannedOutcomes)))
            .andExpect(status().isCreated());

        // Validate the PlannedOutcomes in the database
        List<PlannedOutcomes> plannedOutcomesList = plannedOutcomesRepository.findAll();
        assertThat(plannedOutcomesList).hasSize(databaseSizeBeforeCreate + 1);
        PlannedOutcomes testPlannedOutcomes = plannedOutcomesList.get(plannedOutcomesList.size() - 1);
        assertThat(testPlannedOutcomes.getPlannedDate()).isEqualTo(DEFAULT_PLANNED_DATE);
        assertThat(testPlannedOutcomes.getPlannedMoney()).isEqualTo(DEFAULT_PLANNED_MONEY);
        assertThat(testPlannedOutcomes.getMoneyToGoal()).isEqualTo(DEFAULT_MONEY_TO_GOAL);
        assertThat(testPlannedOutcomes.isIsAlreadyPaid()).isEqualTo(DEFAULT_IS_ALREADY_PAID);
    }

    @Test
    @Transactional
    public void createPlannedOutcomesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plannedOutcomesRepository.findAll().size();

        // Create the PlannedOutcomes with an existing ID
        plannedOutcomes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlannedOutcomesMockMvc.perform(post("/api/planned-outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plannedOutcomes)))
            .andExpect(status().isBadRequest());

        // Validate the PlannedOutcomes in the database
        List<PlannedOutcomes> plannedOutcomesList = plannedOutcomesRepository.findAll();
        assertThat(plannedOutcomesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlannedOutcomes() throws Exception {
        // Initialize the database
        plannedOutcomesRepository.saveAndFlush(plannedOutcomes);

        // Get all the plannedOutcomesList
        restPlannedOutcomesMockMvc.perform(get("/api/planned-outcomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plannedOutcomes.getId().intValue())))
            .andExpect(jsonPath("$.[*].plannedDate").value(hasItem(DEFAULT_PLANNED_DATE.toString())))
            .andExpect(jsonPath("$.[*].plannedMoney").value(hasItem(DEFAULT_PLANNED_MONEY.intValue())))
            .andExpect(jsonPath("$.[*].moneyToGoal").value(hasItem(DEFAULT_MONEY_TO_GOAL.intValue())))
            .andExpect(jsonPath("$.[*].isAlreadyPaid").value(hasItem(DEFAULT_IS_ALREADY_PAID.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPlannedOutcomes() throws Exception {
        // Initialize the database
        plannedOutcomesRepository.saveAndFlush(plannedOutcomes);

        // Get the plannedOutcomes
        restPlannedOutcomesMockMvc.perform(get("/api/planned-outcomes/{id}", plannedOutcomes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plannedOutcomes.getId().intValue()))
            .andExpect(jsonPath("$.plannedDate").value(DEFAULT_PLANNED_DATE.toString()))
            .andExpect(jsonPath("$.plannedMoney").value(DEFAULT_PLANNED_MONEY.intValue()))
            .andExpect(jsonPath("$.moneyToGoal").value(DEFAULT_MONEY_TO_GOAL.intValue()))
            .andExpect(jsonPath("$.isAlreadyPaid").value(DEFAULT_IS_ALREADY_PAID.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPlannedOutcomes() throws Exception {
        // Get the plannedOutcomes
        restPlannedOutcomesMockMvc.perform(get("/api/planned-outcomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlannedOutcomes() throws Exception {
        // Initialize the database
        plannedOutcomesRepository.saveAndFlush(plannedOutcomes);

        int databaseSizeBeforeUpdate = plannedOutcomesRepository.findAll().size();

        // Update the plannedOutcomes
        PlannedOutcomes updatedPlannedOutcomes = plannedOutcomesRepository.findById(plannedOutcomes.getId()).get();
        // Disconnect from session so that the updates on updatedPlannedOutcomes are not directly saved in db
        em.detach(updatedPlannedOutcomes);
        updatedPlannedOutcomes
            .plannedDate(UPDATED_PLANNED_DATE)
            .plannedMoney(UPDATED_PLANNED_MONEY)
            .moneyToGoal(UPDATED_MONEY_TO_GOAL)
            .isAlreadyPaid(UPDATED_IS_ALREADY_PAID);

        restPlannedOutcomesMockMvc.perform(put("/api/planned-outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlannedOutcomes)))
            .andExpect(status().isOk());

        // Validate the PlannedOutcomes in the database
        List<PlannedOutcomes> plannedOutcomesList = plannedOutcomesRepository.findAll();
        assertThat(plannedOutcomesList).hasSize(databaseSizeBeforeUpdate);
        PlannedOutcomes testPlannedOutcomes = plannedOutcomesList.get(plannedOutcomesList.size() - 1);
        assertThat(testPlannedOutcomes.getPlannedDate()).isEqualTo(UPDATED_PLANNED_DATE);
        assertThat(testPlannedOutcomes.getPlannedMoney()).isEqualTo(UPDATED_PLANNED_MONEY);
        assertThat(testPlannedOutcomes.getMoneyToGoal()).isEqualTo(UPDATED_MONEY_TO_GOAL);
        assertThat(testPlannedOutcomes.isIsAlreadyPaid()).isEqualTo(UPDATED_IS_ALREADY_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingPlannedOutcomes() throws Exception {
        int databaseSizeBeforeUpdate = plannedOutcomesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlannedOutcomesMockMvc.perform(put("/api/planned-outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plannedOutcomes)))
            .andExpect(status().isBadRequest());

        // Validate the PlannedOutcomes in the database
        List<PlannedOutcomes> plannedOutcomesList = plannedOutcomesRepository.findAll();
        assertThat(plannedOutcomesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlannedOutcomes() throws Exception {
        // Initialize the database
        plannedOutcomesRepository.saveAndFlush(plannedOutcomes);

        int databaseSizeBeforeDelete = plannedOutcomesRepository.findAll().size();

        // Delete the plannedOutcomes
        restPlannedOutcomesMockMvc.perform(delete("/api/planned-outcomes/{id}", plannedOutcomes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlannedOutcomes> plannedOutcomesList = plannedOutcomesRepository.findAll();
        assertThat(plannedOutcomesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
