package com.famonely.app.repository;

import com.famonely.app.domain.UsersFamonely;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UsersFamonely entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersFamonelyRepository extends JpaRepository<UsersFamonely, Long> {
}
