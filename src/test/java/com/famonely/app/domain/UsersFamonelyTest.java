package com.famonely.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famonely.app.web.rest.TestUtil;

public class UsersFamonelyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsersFamonely.class);
        UsersFamonely usersFamonely1 = new UsersFamonely();
        usersFamonely1.setId(1L);
        UsersFamonely usersFamonely2 = new UsersFamonely();
        usersFamonely2.setId(usersFamonely1.getId());
        assertThat(usersFamonely1).isEqualTo(usersFamonely2);
        usersFamonely2.setId(2L);
        assertThat(usersFamonely1).isNotEqualTo(usersFamonely2);
        usersFamonely1.setId(null);
        assertThat(usersFamonely1).isNotEqualTo(usersFamonely2);
    }
}
