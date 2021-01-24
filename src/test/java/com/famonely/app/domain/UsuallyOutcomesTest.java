package com.famonely.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famonely.app.web.rest.TestUtil;

public class UsuallyOutcomesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuallyOutcomes.class);
        UsuallyOutcomes usuallyOutcomes1 = new UsuallyOutcomes();
        usuallyOutcomes1.setId(1L);
        UsuallyOutcomes usuallyOutcomes2 = new UsuallyOutcomes();
        usuallyOutcomes2.setId(usuallyOutcomes1.getId());
        assertThat(usuallyOutcomes1).isEqualTo(usuallyOutcomes2);
        usuallyOutcomes2.setId(2L);
        assertThat(usuallyOutcomes1).isNotEqualTo(usuallyOutcomes2);
        usuallyOutcomes1.setId(null);
        assertThat(usuallyOutcomes1).isNotEqualTo(usuallyOutcomes2);
    }
}
