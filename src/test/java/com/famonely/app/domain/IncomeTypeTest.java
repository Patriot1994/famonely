package com.famonely.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famonely.app.web.rest.TestUtil;

public class IncomeTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeType.class);
        IncomeType incomeType1 = new IncomeType();
        incomeType1.setId(1L);
        IncomeType incomeType2 = new IncomeType();
        incomeType2.setId(incomeType1.getId());
        assertThat(incomeType1).isEqualTo(incomeType2);
        incomeType2.setId(2L);
        assertThat(incomeType1).isNotEqualTo(incomeType2);
        incomeType1.setId(null);
        assertThat(incomeType1).isNotEqualTo(incomeType2);
    }
}
