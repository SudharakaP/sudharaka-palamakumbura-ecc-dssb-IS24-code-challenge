package come.sudharaka.codechallenge.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import come.sudharaka.codechallenge.IntegrationTest;
import come.sudharaka.codechallenge.domain.ScrumMaster;
import come.sudharaka.codechallenge.repository.ScrumMasterRepository;
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
 * Integration tests for the {@link ScrumMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScrumMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/scrum-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScrumMasterRepository scrumMasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScrumMasterMockMvc;

    private ScrumMaster scrumMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScrumMaster createEntity(EntityManager em) {
        ScrumMaster scrumMaster = new ScrumMaster().name(DEFAULT_NAME);
        return scrumMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScrumMaster createUpdatedEntity(EntityManager em) {
        ScrumMaster scrumMaster = new ScrumMaster().name(UPDATED_NAME);
        return scrumMaster;
    }

    @BeforeEach
    public void initTest() {
        scrumMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createScrumMaster() throws Exception {
        int databaseSizeBeforeCreate = scrumMasterRepository.findAll().size();
        // Create the ScrumMaster
        restScrumMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scrumMaster)))
            .andExpect(status().isCreated());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeCreate + 1);
        ScrumMaster testScrumMaster = scrumMasterList.get(scrumMasterList.size() - 1);
        assertThat(testScrumMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createScrumMasterWithExistingId() throws Exception {
        // Create the ScrumMaster with an existing ID
        scrumMaster.setId(1L);

        int databaseSizeBeforeCreate = scrumMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScrumMasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scrumMaster)))
            .andExpect(status().isBadRequest());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScrumMasters() throws Exception {
        // Initialize the database
        scrumMasterRepository.saveAndFlush(scrumMaster);

        // Get all the scrumMasterList
        restScrumMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scrumMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getScrumMaster() throws Exception {
        // Initialize the database
        scrumMasterRepository.saveAndFlush(scrumMaster);

        // Get the scrumMaster
        restScrumMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, scrumMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scrumMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingScrumMaster() throws Exception {
        // Get the scrumMaster
        restScrumMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScrumMaster() throws Exception {
        // Initialize the database
        scrumMasterRepository.saveAndFlush(scrumMaster);

        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();

        // Update the scrumMaster
        ScrumMaster updatedScrumMaster = scrumMasterRepository.findById(scrumMaster.getId()).get();
        // Disconnect from session so that the updates on updatedScrumMaster are not directly saved in db
        em.detach(updatedScrumMaster);
        updatedScrumMaster.name(UPDATED_NAME);

        restScrumMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScrumMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScrumMaster))
            )
            .andExpect(status().isOk());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
        ScrumMaster testScrumMaster = scrumMasterList.get(scrumMasterList.size() - 1);
        assertThat(testScrumMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingScrumMaster() throws Exception {
        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();
        scrumMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScrumMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scrumMaster.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scrumMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScrumMaster() throws Exception {
        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();
        scrumMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScrumMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scrumMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScrumMaster() throws Exception {
        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();
        scrumMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScrumMasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scrumMaster)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScrumMasterWithPatch() throws Exception {
        // Initialize the database
        scrumMasterRepository.saveAndFlush(scrumMaster);

        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();

        // Update the scrumMaster using partial update
        ScrumMaster partialUpdatedScrumMaster = new ScrumMaster();
        partialUpdatedScrumMaster.setId(scrumMaster.getId());

        restScrumMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScrumMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScrumMaster))
            )
            .andExpect(status().isOk());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
        ScrumMaster testScrumMaster = scrumMasterList.get(scrumMasterList.size() - 1);
        assertThat(testScrumMaster.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateScrumMasterWithPatch() throws Exception {
        // Initialize the database
        scrumMasterRepository.saveAndFlush(scrumMaster);

        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();

        // Update the scrumMaster using partial update
        ScrumMaster partialUpdatedScrumMaster = new ScrumMaster();
        partialUpdatedScrumMaster.setId(scrumMaster.getId());

        partialUpdatedScrumMaster.name(UPDATED_NAME);

        restScrumMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScrumMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScrumMaster))
            )
            .andExpect(status().isOk());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
        ScrumMaster testScrumMaster = scrumMasterList.get(scrumMasterList.size() - 1);
        assertThat(testScrumMaster.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingScrumMaster() throws Exception {
        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();
        scrumMaster.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScrumMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scrumMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scrumMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScrumMaster() throws Exception {
        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();
        scrumMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScrumMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scrumMaster))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScrumMaster() throws Exception {
        int databaseSizeBeforeUpdate = scrumMasterRepository.findAll().size();
        scrumMaster.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScrumMasterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(scrumMaster))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScrumMaster in the database
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScrumMaster() throws Exception {
        // Initialize the database
        scrumMasterRepository.saveAndFlush(scrumMaster);

        int databaseSizeBeforeDelete = scrumMasterRepository.findAll().size();

        // Delete the scrumMaster
        restScrumMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, scrumMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScrumMaster> scrumMasterList = scrumMasterRepository.findAll();
        assertThat(scrumMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
