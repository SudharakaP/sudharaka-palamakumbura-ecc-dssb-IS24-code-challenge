package come.sudharaka.codechallenge.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import come.sudharaka.codechallenge.IntegrationTest;
import come.sudharaka.codechallenge.domain.ProductOwner;
import come.sudharaka.codechallenge.repository.ProductOwnerRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductOwnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductOwnerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-owners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductOwnerRepository productOwnerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOwnerMockMvc;

    private ProductOwner productOwner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOwner createEntity(EntityManager em) {
        ProductOwner productOwner = new ProductOwner().name(DEFAULT_NAME);
        return productOwner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOwner createUpdatedEntity(EntityManager em) {
        ProductOwner productOwner = new ProductOwner().name(UPDATED_NAME);
        return productOwner;
    }

    @BeforeEach
    public void initTest() {
        productOwner = createEntity(em);
    }

    @Test
    @Transactional
    void createProductOwner() throws Exception {
        int databaseSizeBeforeCreate = productOwnerRepository.findAll().size();
        // Create the ProductOwner
        restProductOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOwner)))
            .andExpect(status().isCreated());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOwner testProductOwner = productOwnerList.get(productOwnerList.size() - 1);
        assertThat(testProductOwner.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createProductOwnerWithExistingId() throws Exception {
        // Create the ProductOwner with an existing ID
        productOwner.setId(1L);

        int databaseSizeBeforeCreate = productOwnerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOwnerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOwner)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductOwners() throws Exception {
        // Initialize the database
        productOwnerRepository.saveAndFlush(productOwner);

        // Get all the productOwnerList
        restProductOwnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOwner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getProductOwner() throws Exception {
        // Initialize the database
        productOwnerRepository.saveAndFlush(productOwner);

        // Get the productOwner
        restProductOwnerMockMvc
            .perform(get(ENTITY_API_URL_ID, productOwner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOwner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingProductOwner() throws Exception {
        // Get the productOwner
        restProductOwnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductOwner() throws Exception {
        // Initialize the database
        productOwnerRepository.saveAndFlush(productOwner);

        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();

        // Update the productOwner
        ProductOwner updatedProductOwner = productOwnerRepository.findById(productOwner.getId()).get();
        // Disconnect from session so that the updates on updatedProductOwner are not directly saved in db
        em.detach(updatedProductOwner);
        updatedProductOwner.name(UPDATED_NAME);

        restProductOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductOwner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductOwner))
            )
            .andExpect(status().isOk());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
        ProductOwner testProductOwner = productOwnerList.get(productOwnerList.size() - 1);
        assertThat(testProductOwner.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingProductOwner() throws Exception {
        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();
        productOwner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOwner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOwner))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductOwner() throws Exception {
        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();
        productOwner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOwnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOwner))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductOwner() throws Exception {
        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();
        productOwner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOwnerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOwner)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductOwnerWithPatch() throws Exception {
        // Initialize the database
        productOwnerRepository.saveAndFlush(productOwner);

        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();

        // Update the productOwner using partial update
        ProductOwner partialUpdatedProductOwner = new ProductOwner();
        partialUpdatedProductOwner.setId(productOwner.getId());

        partialUpdatedProductOwner.name(UPDATED_NAME);

        restProductOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOwner))
            )
            .andExpect(status().isOk());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
        ProductOwner testProductOwner = productOwnerList.get(productOwnerList.size() - 1);
        assertThat(testProductOwner.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateProductOwnerWithPatch() throws Exception {
        // Initialize the database
        productOwnerRepository.saveAndFlush(productOwner);

        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();

        // Update the productOwner using partial update
        ProductOwner partialUpdatedProductOwner = new ProductOwner();
        partialUpdatedProductOwner.setId(productOwner.getId());

        partialUpdatedProductOwner.name(UPDATED_NAME);

        restProductOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOwner))
            )
            .andExpect(status().isOk());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
        ProductOwner testProductOwner = productOwnerList.get(productOwnerList.size() - 1);
        assertThat(testProductOwner.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingProductOwner() throws Exception {
        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();
        productOwner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productOwner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOwner))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductOwner() throws Exception {
        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();
        productOwner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOwner))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductOwner() throws Exception {
        int databaseSizeBeforeUpdate = productOwnerRepository.findAll().size();
        productOwner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOwnerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productOwner))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOwner in the database
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductOwner() throws Exception {
        // Initialize the database
        productOwnerRepository.saveAndFlush(productOwner);

        int databaseSizeBeforeDelete = productOwnerRepository.findAll().size();

        // Delete the productOwner
        restProductOwnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, productOwner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOwner> productOwnerList = productOwnerRepository.findAll();
        assertThat(productOwnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
