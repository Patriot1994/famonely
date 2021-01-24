package com.famonely.app.web.rest;

import com.famonely.app.domain.UsersFamonely;
import com.famonely.app.repository.UsersFamonelyRepository;
import com.famonely.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.famonely.app.domain.UsersFamonely}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UsersFamonelyResource {

    private final Logger log = LoggerFactory.getLogger(UsersFamonelyResource.class);

    private static final String ENTITY_NAME = "usersFamonely";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsersFamonelyRepository usersFamonelyRepository;

    public UsersFamonelyResource(UsersFamonelyRepository usersFamonelyRepository) {
        this.usersFamonelyRepository = usersFamonelyRepository;
    }

    /**
     * {@code POST  /users-famonelies} : Create a new usersFamonely.
     *
     * @param usersFamonely the usersFamonely to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usersFamonely, or with status {@code 400 (Bad Request)} if the usersFamonely has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/users-famonelies")
    public ResponseEntity<UsersFamonely> createUsersFamonely(@RequestBody UsersFamonely usersFamonely) throws URISyntaxException {
        log.debug("REST request to save UsersFamonely : {}", usersFamonely);
        if (usersFamonely.getId() != null) {
            throw new BadRequestAlertException("A new usersFamonely cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsersFamonely result = usersFamonelyRepository.save(usersFamonely);
        return ResponseEntity.created(new URI("/api/users-famonelies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /users-famonelies} : Updates an existing usersFamonely.
     *
     * @param usersFamonely the usersFamonely to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usersFamonely,
     * or with status {@code 400 (Bad Request)} if the usersFamonely is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usersFamonely couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/users-famonelies")
    public ResponseEntity<UsersFamonely> updateUsersFamonely(@RequestBody UsersFamonely usersFamonely) throws URISyntaxException {
        log.debug("REST request to update UsersFamonely : {}", usersFamonely);
        if (usersFamonely.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsersFamonely result = usersFamonelyRepository.save(usersFamonely);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usersFamonely.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /users-famonelies} : get all the usersFamonelies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usersFamonelies in body.
     */
    @GetMapping("/users-famonelies")
    public List<UsersFamonely> getAllUsersFamonelies() {
        log.debug("REST request to get all UsersFamonelies");
        return usersFamonelyRepository.findAll();
    }

    /**
     * {@code GET  /users-famonelies/:id} : get the "id" usersFamonely.
     *
     * @param id the id of the usersFamonely to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usersFamonely, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users-famonelies/{id}")
    public ResponseEntity<UsersFamonely> getUsersFamonely(@PathVariable Long id) {
        log.debug("REST request to get UsersFamonely : {}", id);
        Optional<UsersFamonely> usersFamonely = usersFamonelyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(usersFamonely);
    }

    /**
     * {@code DELETE  /users-famonelies/:id} : delete the "id" usersFamonely.
     *
     * @param id the id of the usersFamonely to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users-famonelies/{id}")
    public ResponseEntity<Void> deleteUsersFamonely(@PathVariable Long id) {
        log.debug("REST request to delete UsersFamonely : {}", id);
        usersFamonelyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
