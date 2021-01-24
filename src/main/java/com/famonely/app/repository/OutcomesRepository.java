package com.famonely.app.repository;

import com.famonely.app.domain.Outcomes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Outcomes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutcomesRepository extends JpaRepository<Outcomes, Long> {
}
