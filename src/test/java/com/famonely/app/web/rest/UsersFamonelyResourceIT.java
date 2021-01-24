package com.famonely.app.web.rest;

import com.famonely.app.FamonelyApp;
import com.famonely.app.domain.UsersFamonely;
import com.famonely.app.repository.UsersFamonelyRepository;

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
 * Integration tests for the {@link UsersFamonelyResource} REST controller.
 */
@SpringBootTest(classes = FamonelyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsersFamonelyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SWEET_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SWEET_SHORTNAME = "BBBBBBBBBB";

    @Autowired
    private UsersFamonelyRepository usersFamonelyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsersFamonelyMockMvc;

    private UsersFamonely usersFamonely;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsersFamonely createEntity(EntityManager em) {
        UsersFamonely usersFamonely = new UsersFamonely()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .sweetShortname(DEFAULT_SWEET_SHORTNAME);
        return usersFamonely;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsersFamonely createUpdatedEntity(EntityManager em) {
        UsersFamonely usersFamonely = new UsersFamonely()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .sweetShortname(UPDATED_SWEET_SHORTNAME);
        return usersFamonely;
    }

    @BeforeEach
    public void initTest() {
        usersFamonely = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsersFamonely() throws Exception {
        int databaseSizeBeforeCreate = usersFamonelyRepository.findAll().size();
        // Create the UsersFamonely
        restUsersFamonelyMockMvc.perform(post("/api/users-famonelies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersFamonely)))
            .andExpect(status().isCreated());

        // Validate the UsersFamonely in the database
        List<UsersFamonely> usersFamonelyList = usersFamonelyRepository.findAll();
        assertThat(usersFamonelyList).hasSize(databaseSizeBeforeCreate + 1);
        UsersFamonely testUsersFamonely = usersFamonelyList.get(usersFamonelyList.size() - 1);
        assertThat(testUsersFamonely.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUsersFamonely.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testUsersFamonely.getSweetShortname()).isEqualTo(DEFAULT_SWEET_SHORTNAME);
    }

    @Test
    @Transactional
    public void createUsersFamonelyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersFamonelyRepository.findAll().size();

        // Create the UsersFamonely with an existing ID
        usersFamonely.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersFamonelyMockMvc.perform(post("/api/users-famonelies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersFamonely)))
            .andExpect(status().isBadRequest());

        // Validate the UsersFamonely in the database
        List<UsersFamonely> usersFamonelyList = usersFamonelyRepository.findAll();
        assertThat(usersFamonelyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsersFamonelies() throws Exception {
        // Initialize the database
        usersFamonelyRepository.saveAndFlush(usersFamonely);

        // Get all the usersFamonelyList
        restUsersFamonelyMockMvc.perform(get("/api/users-famonelies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usersFamonely.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].sweetShortname").value(hasItem(DEFAULT_SWEET_SHORTNAME)));
    }
    
    @Test
    @Transactional
    public void getUsersFamonely() throws Exception {
        // Initialize the database
        usersFamonelyRepository.saveAndFlush(usersFamonely);

        // Get the usersFamonely
        restUsersFamonelyMockMvc.perform(get("/api/users-famonelies/{id}", usersFamonely.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usersFamonely.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.sweetShortname").value(DEFAULT_SWEET_SHORTNAME));
    }
    @Test
    @Transactional
    public void getNonExistingUsersFamonely() throws Exception {
        // Get the usersFamonely
        restUsersFamonelyMockMvc.perform(get("/api/users-famonelies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsersFamonely() throws Exception {
        // Initialize the database
        usersFamonelyRepository.saveAndFlush(usersFamonely);

        int databaseSizeBeforeUpdate = usersFamonelyRepository.findAll().size();

        // Update the usersFamonely
        UsersFamonely updatedUsersFamonely = usersFamonelyRepository.findById(usersFamonely.getId()).get();
        // Disconnect from session so that the updates on updatedUsersFamonely are not directly saved in db
        em.detach(updatedUsersFamonely);
        updatedUsersFamonely
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .sweetShortname(UPDATED_SWEET_SHORTNAME);

        restUsersFamonelyMockMvc.perform(put("/api/users-famonelies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsersFamonely)))
            .andExpect(status().isOk());

        // Validate the UsersFamonely in the database
        List<UsersFamonely> usersFamonelyList = usersFamonelyRepository.findAll();
        assertThat(usersFamonelyList).hasSize(databaseSizeBeforeUpdate);
        UsersFamonely testUsersFamonely = usersFamonelyList.get(usersFamonelyList.size() - 1);
        assertThat(testUsersFamonely.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsersFamonely.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testUsersFamonely.getSweetShortname()).isEqualTo(UPDATED_SWEET_SHORTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUsersFamonely() throws Exception {
        int databaseSizeBeforeUpdate = usersFamonelyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersFamonelyMockMvc.perform(put("/api/users-famonelies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersFamonely)))
            .andExpect(status().isBadRequest());

        // Validate the UsersFamonely in the database
        List<UsersFamonely> usersFamonelyList = usersFamonelyRepository.findAll();
        assertThat(usersFamonelyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsersFamonely() throws Exception {
        // Initialize the database
        usersFamonelyRepository.saveAndFlush(usersFamonely);

        int databaseSizeBeforeDelete = usersFamonelyRepository.findAll().size();

        // Delete the usersFamonely
        restUsersFamonelyMockMvc.perform(delete("/api/users-famonelies/{id}", usersFamonely.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsersFamonely> usersFamonelyList = usersFamonelyRepository.findAll();
        assertThat(usersFamonelyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
