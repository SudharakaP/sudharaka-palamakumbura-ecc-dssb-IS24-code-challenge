package come.sudharaka.codechallenge.web.rest;

import come.sudharaka.codechallenge.domain.Developer;
import come.sudharaka.codechallenge.repository.DeveloperRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link come.sudharaka.codechallenge.domain.Developer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeveloperResource {

    private final Logger log = LoggerFactory.getLogger(DeveloperResource.class);

    private static final String ENTITY_NAME = "developer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeveloperRepository developerRepository;

    public DeveloperResource(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    /**
     * {@code POST  /developers} : Create a new developer.
     *
     * @param developer the developer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new developer, or with status {@code 400 (Bad Request)} if the developer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/developers")
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer) throws URISyntaxException {
        log.debug("REST request to save Developer : {}", developer);
        if (developer.getId() != null) {
            throw new BadRequestAlertException("A new developer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Developer result = developerRepository.save(developer);
        return ResponseEntity
            .created(new URI("/api/developers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /developers/:id} : Updates an existing developer.
     *
     * @param id the id of the developer to save.
     * @param developer the developer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated developer,
     * or with status {@code 400 (Bad Request)} if the developer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the developer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/developers/{id}")
    public ResponseEntity<Developer> updateDeveloper(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Developer developer
    ) throws URISyntaxException {
        log.debug("REST request to update Developer : {}, {}", id, developer);
        if (developer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, developer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!developerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Developer result = developerRepository.save(developer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, developer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /developers/:id} : Partial updates given fields of an existing developer, field will ignore if it is null
     *
     * @param id the id of the developer to save.
     * @param developer the developer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated developer,
     * or with status {@code 400 (Bad Request)} if the developer is not valid,
     * or with status {@code 404 (Not Found)} if the developer is not found,
     * or with status {@code 500 (Internal Server Error)} if the developer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/developers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Developer> partialUpdateDeveloper(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Developer developer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Developer partially : {}, {}", id, developer);
        if (developer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, developer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!developerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Developer> result = developerRepository
            .findById(developer.getId())
            .map(existingDeveloper -> {
                if (developer.getName() != null) {
                    existingDeveloper.setName(developer.getName());
                }

                return existingDeveloper;
            })
            .map(developerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, developer.getId().toString())
        );
    }

    /**
     * {@code GET  /developers} : get all the developers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of developers in body.
     */
    @GetMapping("/developers")
    public ResponseEntity<List<Developer>> getAllDevelopers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Developers");
        Page<Developer> page = developerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /developers/:id} : get the "id" developer.
     *
     * @param id the id of the developer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the developer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/developers/{id}")
    public ResponseEntity<Developer> getDeveloper(@PathVariable Long id) {
        log.debug("REST request to get Developer : {}", id);
        Optional<Developer> developer = developerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(developer);
    }

    /**
     * {@code DELETE  /developers/:id} : delete the "id" developer.
     *
     * @param id the id of the developer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        log.debug("REST request to delete Developer : {}", id);
        developerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
