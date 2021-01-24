package com.famonely.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famonely.app.web.rest.TestUtil;

public class StateOfMoneyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateOfMoney.class);
        StateOfMoney stateOfMoney1 = new StateOfMoney();
        stateOfMoney1.setId(1L);
        StateOfMoney stateOfMoney2 = new StateOfMoney();
        stateOfMoney2.setId(stateOfMoney1.getId());
        assertThat(stateOfMoney1).isEqualTo(stateOfMoney2);
        stateOfMoney2.setId(2L);
        assertThat(stateOfMoney1).isNotEqualTo(stateOfMoney2);
        stateOfMoney1.setId(null);
        assertThat(stateOfMoney1).isNotEqualTo(stateOfMoney2);
    }
}
