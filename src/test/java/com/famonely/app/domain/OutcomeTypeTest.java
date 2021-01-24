package com.famonely.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famonely.app.web.rest.TestUtil;

public class OutcomeTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OutcomeType.class);
        OutcomeType outcomeType1 = new OutcomeType();
        outcomeType1.setId(1L);
        OutcomeType outcomeType2 = new OutcomeType();
        outcomeType2.setId(outcomeType1.getId());
        assertThat(outcomeType1).isEqualTo(outcomeType2);
        outcomeType2.setId(2L);
        assertThat(outcomeType1).isNotEqualTo(outcomeType2);
        outcomeType1.setId(null);
        assertThat(outcomeType1).isNotEqualTo(outcomeType2);
    }
}
