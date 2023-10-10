package come.sudharaka.codechallenge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import come.sudharaka.codechallenge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOwnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOwner.class);
        ProductOwner productOwner1 = new ProductOwner();
        productOwner1.setId(1L);
        ProductOwner productOwner2 = new ProductOwner();
        productOwner2.setId(productOwner1.getId());
        assertThat(productOwner1).isEqualTo(productOwner2);
        productOwner2.setId(2L);
        assertThat(productOwner1).isNotEqualTo(productOwner2);
        productOwner1.setId(null);
        assertThat(productOwner1).isNotEqualTo(productOwner2);
    }
}
