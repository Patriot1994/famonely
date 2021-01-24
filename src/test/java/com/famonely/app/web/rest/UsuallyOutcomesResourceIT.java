package com.famonely.app.web.rest;

import com.famonely.app.FamonelyApp;
import com.famonely.app.domain.UsuallyOutcomes;
import com.famonely.app.repository.UsuallyOutcomesRepository;

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
 * Integration tests for the {@link UsuallyOutcomesResource} REST controller.
 */
@SpringBootTest(classes = FamonelyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsuallyOutcomesResourceIT {

    private static final LocalDate DEFAULT_PAY_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAY_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONEY = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_ALREADY_PAID = false;
    private static final Boolean UPDATED_IS_ALREADY_PAID = true;

    @Autowired
    private UsuallyOutcomesRepository usuallyOutcomesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuallyOutcomesMockMvc;

    private UsuallyOutcomes usuallyOutcomes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuallyOutcomes createEntity(EntityManager em) {
        UsuallyOutcomes usuallyOutcomes = new UsuallyOutcomes()
            .payDay(DEFAULT_PAY_DAY)
            .money(DEFAULT_MONEY)
            .isAlreadyPaid(DEFAULT_IS_ALREADY_PAID);
        return usuallyOutcomes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuallyOutcomes createUpdatedEntity(EntityManager em) {
        UsuallyOutcomes usuallyOutcomes = new UsuallyOutcomes()
            .payDay(UPDATED_PAY_DAY)
            .money(UPDATED_MONEY)
            .isAlreadyPaid(UPDATED_IS_ALREADY_PAID);
        return usuallyOutcomes;
    }

    @BeforeEach
    public void initTest() {
        usuallyOutcomes = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuallyOutcomes() throws Exception {
        int databaseSizeBeforeCreate = usuallyOutcomesRepository.findAll().size();
        // Create the UsuallyOutcomes
        restUsuallyOutcomesMockMvc.perform(post("/api/usually-outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuallyOutcomes)))
            .andExpect(status().isCreated());

        // Validate the UsuallyOutcomes in the database
        List<UsuallyOutcomes> usuallyOutcomesList = usuallyOutcomesRepository.findAll();
        assertThat(usuallyOutcomesList).hasSize(databaseSizeBeforeCreate + 1);
        UsuallyOutcomes testUsuallyOutcomes = usuallyOutcomesList.get(usuallyOutcomesList.size() - 1);
        assertThat(testUsuallyOutcomes.getPayDay()).isEqualTo(DEFAULT_PAY_DAY);
        assertThat(testUsuallyOutcomes.getMoney()).isEqualTo(DEFAULT_MONEY);
        assertThat(testUsuallyOutcomes.isIsAlreadyPaid()).isEqualTo(DEFAULT_IS_ALREADY_PAID);
    }

    @Test
    @Transactional
    public void createUsuallyOutcomesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuallyOutcomesRepository.findAll().size();

        // Create the UsuallyOutcomes with an existing ID
        usuallyOutcomes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuallyOutcomesMockMvc.perform(post("/api/usually-outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuallyOutcomes)))
            .andExpect(status().isBadRequest());

        // Validate the UsuallyOutcomes in the database
        List<UsuallyOutcomes> usuallyOutcomesList = usuallyOutcomesRepository.findAll();
        assertThat(usuallyOutcomesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsuallyOutcomes() throws Exception {
        // Initialize the database
        usuallyOutcomesRepository.saveAndFlush(usuallyOutcomes);

        // Get all the usuallyOutcomesList
        restUsuallyOutcomesMockMvc.perform(get("/api/usually-outcomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuallyOutcomes.getId().intValue())))
            .andExpect(jsonPath("$.[*].payDay").value(hasItem(DEFAULT_PAY_DAY.toString())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(DEFAULT_MONEY.intValue())))
            .andExpect(jsonPath("$.[*].isAlreadyPaid").value(hasItem(DEFAULT_IS_ALREADY_PAID.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getUsuallyOutcomes() throws Exception {
        // Initialize the database
        usuallyOutcomesRepository.saveAndFlush(usuallyOutcomes);

        // Get the usuallyOutcomes
        restUsuallyOutcomesMockMvc.perform(get("/api/usually-outcomes/{id}", usuallyOutcomes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuallyOutcomes.getId().intValue()))
            .andExpect(jsonPath("$.payDay").value(DEFAULT_PAY_DAY.toString()))
            .andExpect(jsonPath("$.money").value(DEFAULT_MONEY.intValue()))
            .andExpect(jsonPath("$.isAlreadyPaid").value(DEFAULT_IS_ALREADY_PAID.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUsuallyOutcomes() throws Exception {
        // Get the usuallyOutcomes
        restUsuallyOutcomesMockMvc.perform(get("/api/usually-outcomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuallyOutcomes() throws Exception {
        // Initialize the database
        usuallyOutcomesRepository.saveAndFlush(usuallyOutcomes);

        int databaseSizeBeforeUpdate = usuallyOutcomesRepository.findAll().size();

        // Update the usuallyOutcomes
        UsuallyOutcomes updatedUsuallyOutcomes = usuallyOutcomesRepository.findById(usuallyOutcomes.getId()).get();
        // Disconnect from session so that the updates on updatedUsuallyOutcomes are not directly saved in db
        em.detach(updatedUsuallyOutcomes);
        updatedUsuallyOutcomes
            .payDay(UPDATED_PAY_DAY)
            .money(UPDATED_MONEY)
            .isAlreadyPaid(UPDATED_IS_ALREADY_PAID);

        restUsuallyOutcomesMockMvc.perform(put("/api/usually-outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuallyOutcomes)))
            .andExpect(status().isOk());

        // Validate the UsuallyOutcomes in the database
        List<UsuallyOutcomes> usuallyOutcomesList = usuallyOutcomesRepository.findAll();
        assertThat(usuallyOutcomesList).hasSize(databaseSizeBeforeUpdate);
        UsuallyOutcomes testUsuallyOutcomes = usuallyOutcomesList.get(usuallyOutcomesList.size() - 1);
        assertThat(testUsuallyOutcomes.getPayDay()).isEqualTo(UPDATED_PAY_DAY);
        assertThat(testUsuallyOutcomes.getMoney()).isEqualTo(UPDATED_MONEY);
        assertThat(testUsuallyOutcomes.isIsAlreadyPaid()).isEqualTo(UPDATED_IS_ALREADY_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuallyOutcomes() throws Exception {
        int databaseSizeBeforeUpdate = usuallyOutcomesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuallyOutcomesMockMvc.perform(put("/api/usually-outcomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuallyOutcomes)))
            .andExpect(status().isBadRequest());

        // Validate the UsuallyOutcomes in the database
        List<UsuallyOutcomes> usuallyOutcomesList = usuallyOutcomesRepository.findAll();
        assertThat(usuallyOutcomesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuallyOutcomes() throws Exception {
        // Initialize the database
        usuallyOutcomesRepository.saveAndFlush(usuallyOutcomes);

        int databaseSizeBeforeDelete = usuallyOutcomesRepository.findAll().size();

        // Delete the usuallyOutcomes
        restUsuallyOutcomesMockMvc.perform(delete("/api/usually-outcomes/{id}", usuallyOutcomes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsuallyOutcomes> usuallyOutcomesList = usuallyOutcomesRepository.findAll();
        assertThat(usuallyOutcomesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
