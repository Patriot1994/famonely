package com.famonely.app.web.rest;

import com.famonely.app.domain.Outcomes;
import com.famonely.app.repository.OutcomesRepository;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.famonely.app.domain.Outcomes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OutcomesResource {

    private final Logger log = LoggerFactory.getLogger(OutcomesResource.class);

    private static final String ENTITY_NAME = "outcomes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutcomesRepository outcomesRepository;

    private final StateOfMoneyRepository stateOfMoneyRepository;

    public OutcomesResource(OutcomesRepository outcomesRepository,StateOfMoneyRepository stateOfMoneyRepository) {
        this.outcomesRepository = outcomesRepository;
        this.stateOfMoneyRepository = stateOfMoneyRepository;
    }

    /**
     * {@code POST  /outcomes} : Create a new outcomes.
     *
     * @param outcomes the outcomes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outcomes, or with status {@code 400 (Bad Request)} if the outcomes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/outcomes")
    public ResponseEntity<Outcomes> createOutcomes(@RequestBody Outcomes outcomes) throws URISyntaxException {
        log.debug("REST request to save Outcomes : {}", outcomes);
        if (outcomes.getId() != null) {
            throw new BadRequestAlertException("A new outcomes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Outcomes result = outcomesRepository.save(outcomes);
        int lastSOMId = stateOfMoneyRepository.findAll().size();
        stateOfMoneyRepository.customSave(result,Long.valueOf(lastSOMId));
        return ResponseEntity.created(new URI("/api/outcomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /outcomes} : Updates an existing outcomes.
     *
     * @param outcomes the outcomes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outcomes,
     * or with status {@code 400 (Bad Request)} if the outcomes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outcomes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/outcomes")
    public ResponseEntity<Outcomes> updateOutcomes(@RequestBody Outcomes outcomes) throws URISyntaxException {
        log.debug("REST request to update Outcomes : {}", outcomes);
        if (outcomes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Outcomes result = outcomesRepository.save(outcomes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outcomes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /outcomes} : get all the outcomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outcomes in body.
     */
    @GetMapping("/outcomes")
    public List<Outcomes> getAllOutcomes() {
        log.debug("REST request to get all Outcomes");
        return outcomesRepository.findAll();
    }

    /**
     * {@code GET  /outcomes/:id} : get the "id" outcomes.
     *
     * @param id the id of the outcomes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outcomes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/outcomes/{id}")
    public ResponseEntity<Outcomes> getOutcomes(@PathVariable Long id) {
        log.debug("REST request to get Outcomes : {}", id);
        Optional<Outcomes> outcomes = outcomesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(outcomes);
    }

    /**
     * {@code DELETE  /outcomes/:id} : delete the "id" outcomes.
     *
     * @param id the id of the outcomes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/outcomes/{id}")
    public ResponseEntity<Void> deleteOutcomes(@PathVariable Long id) {
        log.debug("REST request to delete Outcomes : {}", id);
        outcomesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
