package com.famonely.app.web.rest;

import com.famonely.app.domain.PlannedOutcomes;
import com.famonely.app.repository.PlannedOutcomesRepository;
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
 * REST controller for managing {@link com.famonely.app.domain.PlannedOutcomes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlannedOutcomesResource {

    private final Logger log = LoggerFactory.getLogger(PlannedOutcomesResource.class);

    private static final String ENTITY_NAME = "plannedOutcomes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlannedOutcomesRepository plannedOutcomesRepository;

    public PlannedOutcomesResource(PlannedOutcomesRepository plannedOutcomesRepository) {
        this.plannedOutcomesRepository = plannedOutcomesRepository;
    }

    /**
     * {@code POST  /planned-outcomes} : Create a new plannedOutcomes.
     *
     * @param plannedOutcomes the plannedOutcomes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plannedOutcomes, or with status {@code 400 (Bad Request)} if the plannedOutcomes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/planned-outcomes")
    public ResponseEntity<PlannedOutcomes> createPlannedOutcomes(@RequestBody PlannedOutcomes plannedOutcomes) throws URISyntaxException {
        log.debug("REST request to save PlannedOutcomes : {}", plannedOutcomes);
        if (plannedOutcomes.getId() != null) {
            throw new BadRequestAlertException("A new plannedOutcomes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlannedOutcomes result = plannedOutcomesRepository.save(plannedOutcomes);
        return ResponseEntity.created(new URI("/api/planned-outcomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /planned-outcomes} : Updates an existing plannedOutcomes.
     *
     * @param plannedOutcomes the plannedOutcomes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plannedOutcomes,
     * or with status {@code 400 (Bad Request)} if the plannedOutcomes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plannedOutcomes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/planned-outcomes")
    public ResponseEntity<PlannedOutcomes> updatePlannedOutcomes(@RequestBody PlannedOutcomes plannedOutcomes) throws URISyntaxException {
        log.debug("REST request to update PlannedOutcomes : {}", plannedOutcomes);
        if (plannedOutcomes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlannedOutcomes result = plannedOutcomesRepository.save(plannedOutcomes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plannedOutcomes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /planned-outcomes} : get all the plannedOutcomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plannedOutcomes in body.
     */
    @GetMapping("/planned-outcomes")
    public List<PlannedOutcomes> getAllPlannedOutcomes() {
        log.debug("REST request to get all PlannedOutcomes");
        return plannedOutcomesRepository.findAll();
    }

    /**
     * {@code GET  /planned-outcomes/:id} : get the "id" plannedOutcomes.
     *
     * @param id the id of the plannedOutcomes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plannedOutcomes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planned-outcomes/{id}")
    public ResponseEntity<PlannedOutcomes> getPlannedOutcomes(@PathVariable Long id) {
        log.debug("REST request to get PlannedOutcomes : {}", id);
        Optional<PlannedOutcomes> plannedOutcomes = plannedOutcomesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(plannedOutcomes);
    }

    /**
     * {@code DELETE  /planned-outcomes/:id} : delete the "id" plannedOutcomes.
     *
     * @param id the id of the plannedOutcomes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/planned-outcomes/{id}")
    public ResponseEntity<Void> deletePlannedOutcomes(@PathVariable Long id) {
        log.debug("REST request to delete PlannedOutcomes : {}", id);
        plannedOutcomesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
