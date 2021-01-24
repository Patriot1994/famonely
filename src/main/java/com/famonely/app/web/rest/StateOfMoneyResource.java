package com.famonely.app.web.rest;

import com.famonely.app.domain.StateOfMoney;
import com.famonely.app.repository.StateOfMoneyRepository;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.famonely.app.domain.StateOfMoney}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StateOfMoneyResource {

    private final Logger log = LoggerFactory.getLogger(StateOfMoneyResource.class);

    private static final String ENTITY_NAME = "stateOfMoney";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateOfMoneyRepository stateOfMoneyRepository;

    public StateOfMoneyResource(StateOfMoneyRepository stateOfMoneyRepository) {
        this.stateOfMoneyRepository = stateOfMoneyRepository;
    }

    /**
     * {@code POST  /state-of-monies} : Create a new stateOfMoney.
     *
     * @param stateOfMoney the stateOfMoney to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateOfMoney, or with status {@code 400 (Bad Request)} if the stateOfMoney has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-of-monies")
    public ResponseEntity<StateOfMoney> createStateOfMoney(@RequestBody StateOfMoney stateOfMoney) throws URISyntaxException {
        log.debug("REST request to save StateOfMoney : {}", stateOfMoney);
        if (stateOfMoney.getId() != null) {
            throw new BadRequestAlertException("A new stateOfMoney cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateOfMoney result = stateOfMoneyRepository.save(stateOfMoney);
        return ResponseEntity.created(new URI("/api/state-of-monies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-of-monies} : Updates an existing stateOfMoney.
     *
     * @param stateOfMoney the stateOfMoney to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateOfMoney,
     * or with status {@code 400 (Bad Request)} if the stateOfMoney is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateOfMoney couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-of-monies")
    public ResponseEntity<StateOfMoney> updateStateOfMoney(@RequestBody StateOfMoney stateOfMoney) throws URISyntaxException {
        log.debug("REST request to update StateOfMoney : {}", stateOfMoney);
        if (stateOfMoney.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateOfMoney result = stateOfMoneyRepository.save(stateOfMoney);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateOfMoney.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /state-of-monies} : get all the stateOfMonies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateOfMonies in body.
     */
    @GetMapping("/state-of-monies")
    public List<StateOfMoney> getAllStateOfMonies() {
        log.debug("REST request to get all StateOfMonies");
        return stateOfMoneyRepository.findAll();
    }

    /**
     * {@code GET  /state-of-monies/:id} : get the "id" stateOfMoney.
     *
     * @param id the id of the stateOfMoney to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateOfMoney, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-of-monies/{id}")
    public ResponseEntity<StateOfMoney> getStateOfMoney(@PathVariable Long id) {
        log.debug("REST request to get StateOfMoney : {}", id);
        Optional<StateOfMoney> stateOfMoney = stateOfMoneyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stateOfMoney);
    }

    /**
     * {@code DELETE  /state-of-monies/:id} : delete the "id" stateOfMoney.
     *
     * @param id the id of the stateOfMoney to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-of-monies/{id}")
    public ResponseEntity<Void> deleteStateOfMoney(@PathVariable Long id) {
        log.debug("REST request to delete StateOfMoney : {}", id);
        stateOfMoneyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
