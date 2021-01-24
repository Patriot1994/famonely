package com.famonely.app.web.rest;

import com.famonely.app.domain.IncomeType;
import com.famonely.app.repository.IncomeTypeRepository;
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
 * REST controller for managing {@link com.famonely.app.domain.IncomeType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IncomeTypeResource {

    private final Logger log = LoggerFactory.getLogger(IncomeTypeResource.class);

    private static final String ENTITY_NAME = "incomeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomeTypeRepository incomeTypeRepository;

    public IncomeTypeResource(IncomeTypeRepository incomeTypeRepository) {
        this.incomeTypeRepository = incomeTypeRepository;
    }

    /**
     * {@code POST  /income-types} : Create a new incomeType.
     *
     * @param incomeType the incomeType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomeType, or with status {@code 400 (Bad Request)} if the incomeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/income-types")
    public ResponseEntity<IncomeType> createIncomeType(@RequestBody IncomeType incomeType) throws URISyntaxException {
        log.debug("REST request to save IncomeType : {}", incomeType);
        if (incomeType.getId() != null) {
            throw new BadRequestAlertException("A new incomeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomeType result = incomeTypeRepository.save(incomeType);
        return ResponseEntity.created(new URI("/api/income-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /income-types} : Updates an existing incomeType.
     *
     * @param incomeType the incomeType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomeType,
     * or with status {@code 400 (Bad Request)} if the incomeType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomeType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/income-types")
    public ResponseEntity<IncomeType> updateIncomeType(@RequestBody IncomeType incomeType) throws URISyntaxException {
        log.debug("REST request to update IncomeType : {}", incomeType);
        if (incomeType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncomeType result = incomeTypeRepository.save(incomeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, incomeType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /income-types} : get all the incomeTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomeTypes in body.
     */
    @GetMapping("/income-types")
    public List<IncomeType> getAllIncomeTypes() {
        log.debug("REST request to get all IncomeTypes");
        return incomeTypeRepository.findAll();
    }

    /**
     * {@code GET  /income-types/:id} : get the "id" incomeType.
     *
     * @param id the id of the incomeType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomeType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/income-types/{id}")
    public ResponseEntity<IncomeType> getIncomeType(@PathVariable Long id) {
        log.debug("REST request to get IncomeType : {}", id);
        Optional<IncomeType> incomeType = incomeTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(incomeType);
    }

    /**
     * {@code DELETE  /income-types/:id} : delete the "id" incomeType.
     *
     * @param id the id of the incomeType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/income-types/{id}")
    public ResponseEntity<Void> deleteIncomeType(@PathVariable Long id) {
        log.debug("REST request to delete IncomeType : {}", id);
        incomeTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
