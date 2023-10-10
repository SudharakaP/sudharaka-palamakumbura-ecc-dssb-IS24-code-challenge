package come.sudharaka.codechallenge.web.rest;

import come.sudharaka.codechallenge.domain.ScrumMaster;
import come.sudharaka.codechallenge.repository.ScrumMasterRepository;
import come.sudharaka.codechallenge.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link come.sudharaka.codechallenge.domain.ScrumMaster}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScrumMasterResource {

    private final Logger log = LoggerFactory.getLogger(ScrumMasterResource.class);

    private static final String ENTITY_NAME = "scrumMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScrumMasterRepository scrumMasterRepository;

    public ScrumMasterResource(ScrumMasterRepository scrumMasterRepository) {
        this.scrumMasterRepository = scrumMasterRepository;
    }

    /**
     * {@code POST  /scrum-masters} : Create a new scrumMaster.
     *
     * @param scrumMaster the scrumMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scrumMaster, or with status {@code 400 (Bad Request)} if the scrumMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scrum-masters")
    public ResponseEntity<ScrumMaster> createScrumMaster(@RequestBody ScrumMaster scrumMaster) throws URISyntaxException {
        log.debug("REST request to save ScrumMaster : {}", scrumMaster);
        if (scrumMaster.getId() != null) {
            throw new BadRequestAlertException("A new scrumMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScrumMaster result = scrumMasterRepository.save(scrumMaster);
        return ResponseEntity
            .created(new URI("/api/scrum-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scrum-masters/:id} : Updates an existing scrumMaster.
     *
     * @param id the id of the scrumMaster to save.
     * @param scrumMaster the scrumMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scrumMaster,
     * or with status {@code 400 (Bad Request)} if the scrumMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scrumMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scrum-masters/{id}")
    public ResponseEntity<ScrumMaster> updateScrumMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScrumMaster scrumMaster
    ) throws URISyntaxException {
        log.debug("REST request to update ScrumMaster : {}, {}", id, scrumMaster);
        if (scrumMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scrumMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scrumMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScrumMaster result = scrumMasterRepository.save(scrumMaster);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scrumMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scrum-masters/:id} : Partial updates given fields of an existing scrumMaster, field will ignore if it is null
     *
     * @param id the id of the scrumMaster to save.
     * @param scrumMaster the scrumMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scrumMaster,
     * or with status {@code 400 (Bad Request)} if the scrumMaster is not valid,
     * or with status {@code 404 (Not Found)} if the scrumMaster is not found,
     * or with status {@code 500 (Internal Server Error)} if the scrumMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scrum-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScrumMaster> partialUpdateScrumMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ScrumMaster scrumMaster
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScrumMaster partially : {}, {}", id, scrumMaster);
        if (scrumMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scrumMaster.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scrumMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScrumMaster> result = scrumMasterRepository
            .findById(scrumMaster.getId())
            .map(existingScrumMaster -> {
                if (scrumMaster.getName() != null) {
                    existingScrumMaster.setName(scrumMaster.getName());
                }

                return existingScrumMaster;
            })
            .map(scrumMasterRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scrumMaster.getId().toString())
        );
    }

    /**
     * {@code GET  /scrum-masters} : get all the scrumMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scrumMasters in body.
     */
    @GetMapping("/scrum-masters")
    public ResponseEntity<List<ScrumMaster>> getAllScrumMasters(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ScrumMasters");
        Page<ScrumMaster> page = scrumMasterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scrum-masters/:id} : get the "id" scrumMaster.
     *
     * @param id the id of the scrumMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scrumMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scrum-masters/{id}")
    public ResponseEntity<ScrumMaster> getScrumMaster(@PathVariable Long id) {
        log.debug("REST request to get ScrumMaster : {}", id);
        Optional<ScrumMaster> scrumMaster = scrumMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scrumMaster);
    }

    /**
     * {@code DELETE  /scrum-masters/:id} : delete the "id" scrumMaster.
     *
     * @param id the id of the scrumMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scrum-masters/{id}")
    public ResponseEntity<Void> deleteScrumMaster(@PathVariable Long id) {
        log.debug("REST request to delete ScrumMaster : {}", id);
        scrumMasterRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
