package com.famonely.app.web.rest;

import com.famonely.app.FamonelyApp;
import com.famonely.app.domain.StateOfMoney;
import com.famonely.app.repository.StateOfMoneyRepository;

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
 * Integration tests for the {@link StateOfMoneyResource} REST controller.
 */
@SpringBootTest(classes = FamonelyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StateOfMoneyResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONEY = new BigDecimal(2);

    @Autowired
    private StateOfMoneyRepository stateOfMoneyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStateOfMoneyMockMvc;

    private StateOfMoney stateOfMoney;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateOfMoney createEntity(EntityManager em) {
        StateOfMoney stateOfMoney = new StateOfMoney()
            .date(DEFAULT_DATE)
            .money(DEFAULT_MONEY);
        return stateOfMoney;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateOfMoney createUpdatedEntity(EntityManager em) {
        StateOfMoney stateOfMoney = new StateOfMoney()
            .date(UPDATED_DATE)
            .money(UPDATED_MONEY);
        return stateOfMoney;
    }

    @BeforeEach
    public void initTest() {
        stateOfMoney = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateOfMoney() throws Exception {
        int databaseSizeBeforeCreate = stateOfMoneyRepository.findAll().size();
        // Create the StateOfMoney
        restStateOfMoneyMockMvc.perform(post("/api/state-of-monies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateOfMoney)))
            .andExpect(status().isCreated());

        // Validate the StateOfMoney in the database
        List<StateOfMoney> stateOfMoneyList = stateOfMoneyRepository.findAll();
        assertThat(stateOfMoneyList).hasSize(databaseSizeBeforeCreate + 1);
        StateOfMoney testStateOfMoney = stateOfMoneyList.get(stateOfMoneyList.size() - 1);
        assertThat(testStateOfMoney.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testStateOfMoney.getMoney()).isEqualTo(DEFAULT_MONEY);
    }

    @Test
    @Transactional
    public void createStateOfMoneyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateOfMoneyRepository.findAll().size();

        // Create the StateOfMoney with an existing ID
        stateOfMoney.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateOfMoneyMockMvc.perform(post("/api/state-of-monies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateOfMoney)))
            .andExpect(status().isBadRequest());

        // Validate the StateOfMoney in the database
        List<StateOfMoney> stateOfMoneyList = stateOfMoneyRepository.findAll();
        assertThat(stateOfMoneyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStateOfMonies() throws Exception {
        // Initialize the database
        stateOfMoneyRepository.saveAndFlush(stateOfMoney);

        // Get all the stateOfMoneyList
        restStateOfMoneyMockMvc.perform(get("/api/state-of-monies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateOfMoney.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(DEFAULT_MONEY.intValue())));
    }
    
    @Test
    @Transactional
    public void getStateOfMoney() throws Exception {
        // Initialize the database
        stateOfMoneyRepository.saveAndFlush(stateOfMoney);

        // Get the stateOfMoney
        restStateOfMoneyMockMvc.perform(get("/api/state-of-monies/{id}", stateOfMoney.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stateOfMoney.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.money").value(DEFAULT_MONEY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStateOfMoney() throws Exception {
        // Get the stateOfMoney
        restStateOfMoneyMockMvc.perform(get("/api/state-of-monies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateOfMoney() throws Exception {
        // Initialize the database
        stateOfMoneyRepository.saveAndFlush(stateOfMoney);

        int databaseSizeBeforeUpdate = stateOfMoneyRepository.findAll().size();

        // Update the stateOfMoney
        StateOfMoney updatedStateOfMoney = stateOfMoneyRepository.findById(stateOfMoney.getId()).get();
        // Disconnect from session so that the updates on updatedStateOfMoney are not directly saved in db
        em.detach(updatedStateOfMoney);
        updatedStateOfMoney
            .date(UPDATED_DATE)
            .money(UPDATED_MONEY);

        restStateOfMoneyMockMvc.perform(put("/api/state-of-monies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStateOfMoney)))
            .andExpect(status().isOk());

        // Validate the StateOfMoney in the database
        List<StateOfMoney> stateOfMoneyList = stateOfMoneyRepository.findAll();
        assertThat(stateOfMoneyList).hasSize(databaseSizeBeforeUpdate);
        StateOfMoney testStateOfMoney = stateOfMoneyList.get(stateOfMoneyList.size() - 1);
        assertThat(testStateOfMoney.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStateOfMoney.getMoney()).isEqualTo(UPDATED_MONEY);
    }

    @Test
    @Transactional
    public void updateNonExistingStateOfMoney() throws Exception {
        int databaseSizeBeforeUpdate = stateOfMoneyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateOfMoneyMockMvc.perform(put("/api/state-of-monies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stateOfMoney)))
            .andExpect(status().isBadRequest());

        // Validate the StateOfMoney in the database
        List<StateOfMoney> stateOfMoneyList = stateOfMoneyRepository.findAll();
        assertThat(stateOfMoneyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStateOfMoney() throws Exception {
        // Initialize the database
        stateOfMoneyRepository.saveAndFlush(stateOfMoney);

        int databaseSizeBeforeDelete = stateOfMoneyRepository.findAll().size();

        // Delete the stateOfMoney
        restStateOfMoneyMockMvc.perform(delete("/api/state-of-monies/{id}", stateOfMoney.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StateOfMoney> stateOfMoneyList = stateOfMoneyRepository.findAll();
        assertThat(stateOfMoneyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
