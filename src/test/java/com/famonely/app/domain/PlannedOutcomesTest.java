package com.famonely.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famonely.app.web.rest.TestUtil;

public class PlannedOutcomesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlannedOutcomes.class);
        PlannedOutcomes plannedOutcomes1 = new PlannedOutcomes();
        plannedOutcomes1.setId(1L);
        PlannedOutcomes plannedOutcomes2 = new PlannedOutcomes();
        plannedOutcomes2.setId(plannedOutcomes1.getId());
        assertThat(plannedOutcomes1).isEqualTo(plannedOutcomes2);
        plannedOutcomes2.setId(2L);
        assertThat(plannedOutcomes1).isNotEqualTo(plannedOutcomes2);
        plannedOutcomes1.setId(null);
        assertThat(plannedOutcomes1).isNotEqualTo(plannedOutcomes2);
    }
}
