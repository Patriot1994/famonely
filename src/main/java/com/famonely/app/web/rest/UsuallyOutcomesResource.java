package com.famonely.app.web.rest;

import com.famonely.app.domain.UsuallyOutcomes;
import com.famonely.app.repository.UsuallyOutcomesRepository;
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
 * REST controller for managing {@link com.famonely.app.domain.UsuallyOutcomes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UsuallyOutcomesResource {

    private final Logger log = LoggerFactory.getLogger(UsuallyOutcomesResource.class);

    private static final String ENTITY_NAME = "usuallyOutcomes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuallyOutcomesRepository usuallyOutcomesRepository;

    public UsuallyOutcomesResource(UsuallyOutcomesRepository usuallyOutcomesRepository) {
        this.usuallyOutcomesRepository = usuallyOutcomesRepository;
    }

    /**
     * {@code POST  /usually-outcomes} : Create a new usuallyOutcomes.
     *
     * @param usuallyOutcomes the usuallyOutcomes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuallyOutcomes, or with status {@code 400 (Bad Request)} if the usuallyOutcomes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usually-outcomes")
    public ResponseEntity<UsuallyOutcomes> createUsuallyOutcomes(@RequestBody UsuallyOutcomes usuallyOutcomes) throws URISyntaxException {
        log.debug("REST request to save UsuallyOutcomes : {}", usuallyOutcomes);
        if (usuallyOutcomes.getId() != null) {
            throw new BadRequestAlertException("A new usuallyOutcomes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuallyOutcomes result = usuallyOutcomesRepository.save(usuallyOutcomes);
        return ResponseEntity.created(new URI("/api/usually-outcomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usually-outcomes} : Updates an existing usuallyOutcomes.
     *
     * @param usuallyOutcomes the usuallyOutcomes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuallyOutcomes,
     * or with status {@code 400 (Bad Request)} if the usuallyOutcomes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuallyOutcomes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usually-outcomes")
    public ResponseEntity<UsuallyOutcomes> updateUsuallyOutcomes(@RequestBody UsuallyOutcomes usuallyOutcomes) throws URISyntaxException {
        log.debug("REST request to update UsuallyOutcomes : {}", usuallyOutcomes);
        if (usuallyOutcomes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuallyOutcomes result = usuallyOutcomesRepository.save(usuallyOutcomes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuallyOutcomes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usually-outcomes} : get all the usuallyOutcomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuallyOutcomes in body.
     */
    @GetMapping("/usually-outcomes")
    public List<UsuallyOutcomes> getAllUsuallyOutcomes() {
        log.debug("REST request to get all UsuallyOutcomes");
        return usuallyOutcomesRepository.findAll();
    }

    /**
     * {@code GET  /usually-outcomes/:id} : get the "id" usuallyOutcomes.
     *
     * @param id the id of the usuallyOutcomes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuallyOutcomes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usually-outcomes/{id}")
    public ResponseEntity<UsuallyOutcomes> getUsuallyOutcomes(@PathVariable Long id) {
        log.debug("REST request to get UsuallyOutcomes : {}", id);
        Optional<UsuallyOutcomes> usuallyOutcomes = usuallyOutcomesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(usuallyOutcomes);
    }

    /**
     * {@code DELETE  /usually-outcomes/:id} : delete the "id" usuallyOutcomes.
     *
     * @param id the id of the usuallyOutcomes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usually-outcomes/{id}")
    public ResponseEntity<Void> deleteUsuallyOutcomes(@PathVariable Long id) {
        log.debug("REST request to delete UsuallyOutcomes : {}", id);
        usuallyOutcomesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
