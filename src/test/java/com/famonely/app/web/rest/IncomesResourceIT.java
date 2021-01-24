package com.famonely.app.web.rest;

import com.famonely.app.FamonelyApp;
import com.famonely.app.domain.Incomes;
import com.famonely.app.repository.IncomesRepository;

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
 * Integration tests for the {@link IncomesResource} REST controller.
 */
@SpringBootTest(classes = FamonelyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IncomesResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_SPENT_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_SPENT_MONEY = new BigDecimal(2);

    @Autowired
    private IncomesRepository incomesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomesMockMvc;

    private Incomes incomes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incomes createEntity(EntityManager em) {
        Incomes incomes = new Incomes()
            .date(DEFAULT_DATE)
            .spentMoney(DEFAULT_SPENT_MONEY);
        return incomes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incomes createUpdatedEntity(EntityManager em) {
        Incomes incomes = new Incomes()
            .date(UPDATED_DATE)
            .spentMoney(UPDATED_SPENT_MONEY);
        return incomes;
    }

    @BeforeEach
    public void initTest() {
        incomes = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncomes() throws Exception {
        int databaseSizeBeforeCreate = incomesRepository.findAll().size();
        // Create the Incomes
        restIncomesMockMvc.perform(post("/api/incomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomes)))
            .andExpect(status().isCreated());

        // Validate the Incomes in the database
        List<Incomes> incomesList = incomesRepository.findAll();
        assertThat(incomesList).hasSize(databaseSizeBeforeCreate + 1);
        Incomes testIncomes = incomesList.get(incomesList.size() - 1);
        assertThat(testIncomes.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testIncomes.getSpentMoney()).isEqualTo(DEFAULT_SPENT_MONEY);
    }

    @Test
    @Transactional
    public void createIncomesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incomesRepository.findAll().size();

        // Create the Incomes with an existing ID
        incomes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomesMockMvc.perform(post("/api/incomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomes)))
            .andExpect(status().isBadRequest());

        // Validate the Incomes in the database
        List<Incomes> incomesList = incomesRepository.findAll();
        assertThat(incomesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIncomes() throws Exception {
        // Initialize the database
        incomesRepository.saveAndFlush(incomes);

        // Get all the incomesList
        restIncomesMockMvc.perform(get("/api/incomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomes.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].spentMoney").value(hasItem(DEFAULT_SPENT_MONEY.intValue())));
    }
    
    @Test
    @Transactional
    public void getIncomes() throws Exception {
        // Initialize the database
        incomesRepository.saveAndFlush(incomes);

        // Get the incomes
        restIncomesMockMvc.perform(get("/api/incomes/{id}", incomes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomes.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.spentMoney").value(DEFAULT_SPENT_MONEY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingIncomes() throws Exception {
        // Get the incomes
        restIncomesMockMvc.perform(get("/api/incomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncomes() throws Exception {
        // Initialize the database
        incomesRepository.saveAndFlush(incomes);

        int databaseSizeBeforeUpdate = incomesRepository.findAll().size();

        // Update the incomes
        Incomes updatedIncomes = incomesRepository.findById(incomes.getId()).get();
        // Disconnect from session so that the updates on updatedIncomes are not directly saved in db
        em.detach(updatedIncomes);
        updatedIncomes
            .date(UPDATED_DATE)
            .spentMoney(UPDATED_SPENT_MONEY);

        restIncomesMockMvc.perform(put("/api/incomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncomes)))
            .andExpect(status().isOk());

        // Validate the Incomes in the database
        List<Incomes> incomesList = incomesRepository.findAll();
        assertThat(incomesList).hasSize(databaseSizeBeforeUpdate);
        Incomes testIncomes = incomesList.get(incomesList.size() - 1);
        assertThat(testIncomes.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testIncomes.getSpentMoney()).isEqualTo(UPDATED_SPENT_MONEY);
    }

    @Test
    @Transactional
    public void updateNonExistingIncomes() throws Exception {
        int databaseSizeBeforeUpdate = incomesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomesMockMvc.perform(put("/api/incomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(incomes)))
            .andExpect(status().isBadRequest());

        // Validate the Incomes in the database
        List<Incomes> incomesList = incomesRepository.findAll();
        assertThat(incomesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncomes() throws Exception {
        // Initialize the database
        incomesRepository.saveAndFlush(incomes);

        int databaseSizeBeforeDelete = incomesRepository.findAll().size();

        // Delete the incomes
        restIncomesMockMvc.perform(delete("/api/incomes/{id}", incomes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Incomes> incomesList = incomesRepository.findAll();
        assertThat(incomesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
