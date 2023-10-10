package come.sudharaka.codechallenge.web.rest;

import come.sudharaka.codechallenge.domain.ProductOwner;
import come.sudharaka.codechallenge.repository.ProductOwnerRepository;
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
 * REST controller for managing {@link come.sudharaka.codechallenge.domain.ProductOwner}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductOwnerResource {

    private final Logger log = LoggerFactory.getLogger(ProductOwnerResource.class);

    private static final String ENTITY_NAME = "productOwner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductOwnerRepository productOwnerRepository;

    public ProductOwnerResource(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

    /**
     * {@code POST  /product-owners} : Create a new productOwner.
     *
     * @param productOwner the productOwner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productOwner, or with status {@code 400 (Bad Request)} if the productOwner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-owners")
    public ResponseEntity<ProductOwner> createProductOwner(@RequestBody ProductOwner productOwner) throws URISyntaxException {
        log.debug("REST request to save ProductOwner : {}", productOwner);
        if (productOwner.getId() != null) {
            throw new BadRequestAlertException("A new productOwner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOwner result = productOwnerRepository.save(productOwner);
        return ResponseEntity
            .created(new URI("/api/product-owners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-owners/:id} : Updates an existing productOwner.
     *
     * @param id the id of the productOwner to save.
     * @param productOwner the productOwner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOwner,
     * or with status {@code 400 (Bad Request)} if the productOwner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productOwner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-owners/{id}")
    public ResponseEntity<ProductOwner> updateProductOwner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductOwner productOwner
    ) throws URISyntaxException {
        log.debug("REST request to update ProductOwner : {}, {}", id, productOwner);
        if (productOwner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productOwner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productOwnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductOwner result = productOwnerRepository.save(productOwner);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productOwner.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-owners/:id} : Partial updates given fields of an existing productOwner, field will ignore if it is null
     *
     * @param id the id of the productOwner to save.
     * @param productOwner the productOwner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productOwner,
     * or with status {@code 400 (Bad Request)} if the productOwner is not valid,
     * or with status {@code 404 (Not Found)} if the productOwner is not found,
     * or with status {@code 500 (Internal Server Error)} if the productOwner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-owners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductOwner> partialUpdateProductOwner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductOwner productOwner
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductOwner partially : {}, {}", id, productOwner);
        if (productOwner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productOwner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productOwnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductOwner> result = productOwnerRepository
            .findById(productOwner.getId())
            .map(existingProductOwner -> {
                if (productOwner.getName() != null) {
                    existingProductOwner.setName(productOwner.getName());
                }

                return existingProductOwner;
            })
            .map(productOwnerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productOwner.getId().toString())
        );
    }

    /**
     * {@code GET  /product-owners} : get all the productOwners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productOwners in body.
     */
    @GetMapping("/product-owners")
    public ResponseEntity<List<ProductOwner>> getAllProductOwners(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProductOwners");
        Page<ProductOwner> page = productOwnerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-owners/:id} : get the "id" productOwner.
     *
     * @param id the id of the productOwner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productOwner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-owners/{id}")
    public ResponseEntity<ProductOwner> getProductOwner(@PathVariable Long id) {
        log.debug("REST request to get ProductOwner : {}", id);
        Optional<ProductOwner> productOwner = productOwnerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productOwner);
    }

    /**
     * {@code DELETE  /product-owners/:id} : delete the "id" productOwner.
     *
     * @param id the id of the productOwner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-owners/{id}")
    public ResponseEntity<Void> deleteProductOwner(@PathVariable Long id) {
        log.debug("REST request to delete ProductOwner : {}", id);
        productOwnerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
