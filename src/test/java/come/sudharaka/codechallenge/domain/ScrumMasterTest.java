package come.sudharaka.codechallenge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import come.sudharaka.codechallenge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScrumMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScrumMaster.class);
        ScrumMaster scrumMaster1 = new ScrumMaster();
        scrumMaster1.setId(1L);
        ScrumMaster scrumMaster2 = new ScrumMaster();
        scrumMaster2.setId(scrumMaster1.getId());
        assertThat(scrumMaster1).isEqualTo(scrumMaster2);
        scrumMaster2.setId(2L);
        assertThat(scrumMaster1).isNotEqualTo(scrumMaster2);
        scrumMaster1.setId(null);
        assertThat(scrumMaster1).isNotEqualTo(scrumMaster2);
    }
}
