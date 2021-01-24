package com.famonely.app.repository;

import com.famonely.app.domain.IncomeType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IncomeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {
}
