package com.famonely.app.repository;

import com.famonely.app.domain.OutcomeType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OutcomeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutcomeTypeRepository extends JpaRepository<OutcomeType, Long> {
}
