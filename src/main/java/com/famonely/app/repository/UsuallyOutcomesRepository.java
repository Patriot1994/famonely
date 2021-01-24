package com.famonely.app.repository;

import com.famonely.app.domain.UsuallyOutcomes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UsuallyOutcomes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuallyOutcomesRepository extends JpaRepository<UsuallyOutcomes, Long> {
}
