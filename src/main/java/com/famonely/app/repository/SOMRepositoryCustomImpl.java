package com.famonely.app.repository;

import com.famonely.app.domain.Incomes;
import com.famonely.app.domain.Outcomes;
import com.famonely.app.domain.StateOfMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@Repository
public class SOMRepositoryCustomImpl implements SOMRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    /*
    TODO: update to JavaDoc
    Desc: Get Last of money
          add ( or sub ) income/outcome,
          create new State Of Money
     */
    @Override
    public <T> void customSave(T money,Long idOfLastSOM) {
        StateOfMoney stateOfMoney = new StateOfMoney();
        StateOfMoney lastStateOfMoney = entityManager.find(StateOfMoney.class, idOfLastSOM);
        if(money instanceof Incomes){
            stateOfMoney.setMoney(((Incomes) money).getSpentMoney().add(lastStateOfMoney.getMoney()));
        }
        if(money instanceof Outcomes){
            stateOfMoney.setMoney(((Incomes) money).getSpentMoney().subtract(lastStateOfMoney.getMoney()));
        }
        stateOfMoney.setDate(LocalDate.now());
        if(stateOfMoney.getMoney().compareTo(lastStateOfMoney.getMoney())!=0){
            entityManager.persist(stateOfMoney);
        }
    }
}
