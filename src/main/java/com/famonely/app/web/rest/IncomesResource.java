package com.famonely.app.web.rest;

import com.famonely.app.domain.Incomes;
import com.famonely.app.domain.StateOfMoney;
import com.famonely.app.repository.IncomesRepository;
import com.famonely.app.repository.SOMRepositoryCustom;
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
 * REST controller for managing {@link com.famonely.app.domain.Incomes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IncomesResource {

    private final Logger log = LoggerFactory.getLogger(IncomesResource.class);

    private static final String ENTITY_NAME = "incomes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomesRepository incomesRepository;
    private final StateOfMoneyRepository stateOfMoneyRepository;

    public IncomesResource(IncomesRepository incomesRepository,StateOfMoneyRepository stateOfMoneyRepository) {
        this.incomesRepository = incomesRepository;
        this.stateOfMoneyRepository = stateOfMoneyRepository;
    }

    /**
     * {@code POST  /incomes} : Create a new incomes.
     *
     * @param incomes the incomes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomes, or with status {@code 400 (Bad Request)} if the incomes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incomes")
    public ResponseEntity<Incomes> createIncomes(@RequestBody Incomes incomes) throws URISyntaxException {
        log.debug("REST request to save Incomes : {}", incomes);
        if (incomes.getId() != null) {
            throw new BadRequestAlertException("A new incomes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Incomes result = incomesRepository.save(incomes);
        int lastSOMId = stateOfMoneyRepository.findAll().size();
        stateOfMoneyRepository.customSave(result,Long.valueOf(lastSOMId));

        return ResponseEntity.created(new URI("/api/incomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incomes} : Updates an existing incomes.
     *
     * @param incomes the incomes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomes,
     * or with status {@code 400 (Bad Request)} if the incomes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incomes")
    public ResponseEntity<Incomes> updateIncomes(@RequestBody Incomes incomes) throws URISyntaxException {
        log.debug("REST request to update Incomes : {}", incomes);
        if (incomes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Incomes result = incomesRepository.save(incomes);
        // update StateOfMoney


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incomes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incomes} : get all the incomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomes in body.
     */
    @GetMapping("/incomes")
    public List<Incomes> getAllIncomes() {
        log.debug("REST request to get all Incomes");
        return incomesRepository.findAll();
    }

    /**
     * {@code GET  /incomes/:id} : get the "id" incomes.
     *
     * @param id the id of the incomes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incomes/{id}")
    public ResponseEntity<Incomes> getIncomes(@PathVariable Long id) {
        log.debug("REST request to get Incomes : {}", id);
        Optional<Incomes> incomes = incomesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(incomes);
    }

    /**
     * {@code DELETE  /incomes/:id} : delete the "id" incomes.
     *
     * @param id the id of the incomes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<Void> deleteIncomes(@PathVariable Long id) {
        log.debug("REST request to delete Incomes : {}", id);
        incomesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
