package com.famonely.app.web.rest;

import com.famonely.app.FamonelyApp;
import com.famonely.app.domain.Outcomes;
import com.famonely.app.repository.OutcomesRepository;

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
 * Integration tests for the {@link OutcomesResource} REST controller.
 */
@SpringBootTest(classes = FamonelyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OutcomesResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_SPENT_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_SPENT_MONEY = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_MONTHLY_OUT_COME = false;
    private static final Boolean UPDATED_IS_MONTHLY_OUT_COME = true;

    private static final Boolean DEFAULT_IS_PLANNED_OUT_COME = false;
    private static final Boolean UPDATED_IS_PLANNED_OUT_COME = true;

    @Autowired
    private OutcomesRepository outcomesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOutcomesMockMvc;

    private Outcomes outcomes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outcomes createEntity(EntityManager em) {
        Outcomes outcomes = new Outcomes()
            .date(DEFAULT_DATE)
            .spentMoney(DEFAULT_SPENT_MONEY)
            .isMonthlyOutCome(DEFAULT_IS_MONTHLY_OUT_COME)
            .isPlannedOutCome(DEFAULT_IS_PLANNED_OUT_COME);
        return outcomes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outcomes createUpdatedEntity(EntityManager em) {
        Outcomes outcomes = new Outcomes()
            .date(UPDATED_DATE)
            .spentMoney(UPDATED_SPENT_MONEY)
            .isMonthlyOutCome(UPDATED_IS_MONTHLY_OUT_COME)
            .isPlannedOutCome(UPDATED_IS_PLANNED_OUT_COME);
        return outcomes;
    }

    @BeforeEach
    public void initTest() {
        outcomes = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutcomes() throws Exception {
        int databaseSizeBeforeCreate = outcomesRepository.findAll().size();
        // Create the Outcomes
        restOutcomesMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomes)))
            .andExpect(status().isCreated());

        // Validate the Outcomes in the database
        List<Outcomes> outcomesList = outcomesRepository.findAll();
        assertThat(outcomesList).hasSize(databaseSizeBeforeCreate + 1);
        Outcomes testOutcomes = outcomesList.get(outcomesList.size() - 1);
        assertThat(testOutcomes.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOutcomes.getSpentMoney()).isEqualTo(DEFAULT_SPENT_MONEY);
        assertThat(testOutcomes.isIsMonthlyOutCome()).isEqualTo(DEFAULT_IS_MONTHLY_OUT_COME);
        assertThat(testOutcomes.isIsPlannedOutCome()).isEqualTo(DEFAULT_IS_PLANNED_OUT_COME);
    }

    @Test
    @Transactional
    public void createOutcomesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outcomesRepository.findAll().size();

        // Create the Outcomes with an existing ID
        outcomes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutcomesMockMvc.perform(post("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomes)))
            .andExpect(status().isBadRequest());

        // Validate the Outcomes in the database
        List<Outcomes> outcomesList = outcomesRepository.findAll();
        assertThat(outcomesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOutcomes() throws Exception {
        // Initialize the database
        outcomesRepository.saveAndFlush(outcomes);

        // Get all the outcomesList
        restOutcomesMockMvc.perform(get("/api/outcomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outcomes.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].spentMoney").value(hasItem(DEFAULT_SPENT_MONEY.intValue())))
            .andExpect(jsonPath("$.[*].isMonthlyOutCome").value(hasItem(DEFAULT_IS_MONTHLY_OUT_COME.booleanValue())))
            .andExpect(jsonPath("$.[*].isPlannedOutCome").value(hasItem(DEFAULT_IS_PLANNED_OUT_COME.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOutcomes() throws Exception {
        // Initialize the database
        outcomesRepository.saveAndFlush(outcomes);

        // Get the outcomes
        restOutcomesMockMvc.perform(get("/api/outcomes/{id}", outcomes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(outcomes.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.spentMoney").value(DEFAULT_SPENT_MONEY.intValue()))
            .andExpect(jsonPath("$.isMonthlyOutCome").value(DEFAULT_IS_MONTHLY_OUT_COME.booleanValue()))
            .andExpect(jsonPath("$.isPlannedOutCome").value(DEFAULT_IS_PLANNED_OUT_COME.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOutcomes() throws Exception {
        // Get the outcomes
        restOutcomesMockMvc.perform(get("/api/outcomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutcomes() throws Exception {
        // Initialize the database
        outcomesRepository.saveAndFlush(outcomes);

        int databaseSizeBeforeUpdate = outcomesRepository.findAll().size();

        // Update the outcomes
        Outcomes updatedOutcomes = outcomesRepository.findById(outcomes.getId()).get();
        // Disconnect from session so that the updates on updatedOutcomes are not directly saved in db
        em.detach(updatedOutcomes);
        updatedOutcomes
            .date(UPDATED_DATE)
            .spentMoney(UPDATED_SPENT_MONEY)
            .isMonthlyOutCome(UPDATED_IS_MONTHLY_OUT_COME)
            .isPlannedOutCome(UPDATED_IS_PLANNED_OUT_COME);

        restOutcomesMockMvc.perform(put("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOutcomes)))
            .andExpect(status().isOk());

        // Validate the Outcomes in the database
        List<Outcomes> outcomesList = outcomesRepository.findAll();
        assertThat(outcomesList).hasSize(databaseSizeBeforeUpdate);
        Outcomes testOutcomes = outcomesList.get(outcomesList.size() - 1);
        assertThat(testOutcomes.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOutcomes.getSpentMoney()).isEqualTo(UPDATED_SPENT_MONEY);
        assertThat(testOutcomes.isIsMonthlyOutCome()).isEqualTo(UPDATED_IS_MONTHLY_OUT_COME);
        assertThat(testOutcomes.isIsPlannedOutCome()).isEqualTo(UPDATED_IS_PLANNED_OUT_COME);
    }

    @Test
    @Transactional
    public void updateNonExistingOutcomes() throws Exception {
        int databaseSizeBeforeUpdate = outcomesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutcomesMockMvc.perform(put("/api/outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outcomes)))
            .andExpect(status().isBadRequest());

        // Validate the Outcomes in the database
        List<Outcomes> outcomesList = outcomesRepository.findAll();
        assertThat(outcomesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOutcomes() throws Exception {
        // Initialize the database
        outcomesRepository.saveAndFlush(outcomes);

        int databaseSizeBeforeDelete = outcomesRepository.findAll().size();

        // Delete the outcomes
        restOutcomesMockMvc.perform(delete("/api/outcomes/{id}", outcomes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Outcomes> outcomesList = outcomesRepository.findAll();
        assertThat(outcomesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
