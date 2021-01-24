package com.famonely.app.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface SOMRepositoryCustom {
    <T> void customSave(T money,Long idOfLastSOM);
}
