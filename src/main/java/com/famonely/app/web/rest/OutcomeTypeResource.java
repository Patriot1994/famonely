package com.famonely.app.web.rest;

import com.famonely.app.domain.OutcomeType;
import com.famonely.app.repository.OutcomeTypeRepository;
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
 * REST controller for managing {@link com.famonely.app.domain.OutcomeType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OutcomeTypeResource {

    private final Logger log = LoggerFactory.getLogger(OutcomeTypeResource.class);

    private static final String ENTITY_NAME = "outcomeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutcomeTypeRepository outcomeTypeRepository;

    public OutcomeTypeResource(OutcomeTypeRepository outcomeTypeRepository) {
        this.outcomeTypeRepository = outcomeTypeRepository;
    }

    /**
     * {@code POST  /outcome-types} : Create a new outcomeType.
     *
     * @param outcomeType the outcomeType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outcomeType, or with status {@code 400 (Bad Request)} if the outcomeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/outcome-types")
    public ResponseEntity<OutcomeType> createOutcomeType(@RequestBody OutcomeType outcomeType) throws URISyntaxException {
        log.debug("REST request to save OutcomeType : {}", outcomeType);
        if (outcomeType.getId() != null) {
            throw new BadRequestAlertException("A new outcomeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OutcomeType result = outcomeTypeRepository.save(outcomeType);
        return ResponseEntity.created(new URI("/api/outcome-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /outcome-types} : Updates an existing outcomeType.
     *
     * @param outcomeType the outcomeType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outcomeType,
     * or with status {@code 400 (Bad Request)} if the outcomeType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outcomeType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/outcome-types")
    public ResponseEntity<OutcomeType> updateOutcomeType(@RequestBody OutcomeType outcomeType) throws URISyntaxException {
        log.debug("REST request to update OutcomeType : {}", outcomeType);
        if (outcomeType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OutcomeType result = outcomeTypeRepository.save(outcomeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outcomeType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /outcome-types} : get all the outcomeTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outcomeTypes in body.
     */
    @GetMapping("/outcome-types")
    public List<OutcomeType> getAllOutcomeTypes() {
        log.debug("REST request to get all OutcomeTypes");
        return outcomeTypeRepository.findAll();
    }

    /**
     * {@code GET  /outcome-types/:id} : get the "id" outcomeType.
     *
     * @param id the id of the outcomeType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outcomeType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/outcome-types/{id}")
    public ResponseEntity<OutcomeType> getOutcomeType(@PathVariable Long id) {
        log.debug("REST request to get OutcomeType : {}", id);
        Optional<OutcomeType> outcomeType = outcomeTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(outcomeType);
    }

    /**
     * {@code DELETE  /outcome-types/:id} : delete the "id" outcomeType.
     *
     * @param id the id of the outcomeType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/outcome-types/{id}")
    public ResponseEntity<Void> deleteOutcomeType(@PathVariable Long id) {
        log.debug("REST request to delete OutcomeType : {}", id);
        outcomeTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
