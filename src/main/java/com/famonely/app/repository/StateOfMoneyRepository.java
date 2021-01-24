package com.famonely.app.repository;

import com.famonely.app.domain.StateOfMoney;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StateOfMoney entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateOfMoneyRepository extends JpaRepository<StateOfMoney, Long>, SOMRepositoryCustom {
}
