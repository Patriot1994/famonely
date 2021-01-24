package com.famonely.app.repository;

import com.famonely.app.domain.PlannedOutcomes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PlannedOutcomes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlannedOutcomesRepository extends JpaRepository<PlannedOutcomes, Long> {
}
