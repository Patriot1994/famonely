package com.famonely.app.repository;

import com.famonely.app.domain.Incomes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Incomes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomesRepository extends JpaRepository<Incomes, Long> {
}
