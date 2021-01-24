package com.famonely.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famonely.app.web.rest.TestUtil;

public class OutcomesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Outcomes.class);
        Outcomes outcomes1 = new Outcomes();
        outcomes1.setId(1L);
        Outcomes outcomes2 = new Outcomes();
        outcomes2.setId(outcomes1.getId());
        assertThat(outcomes1).isEqualTo(outcomes2);
        outcomes2.setId(2L);
        assertThat(outcomes1).isNotEqualTo(outcomes2);
        outcomes1.setId(null);
        assertThat(outcomes1).isNotEqualTo(outcomes2);
    }
}
