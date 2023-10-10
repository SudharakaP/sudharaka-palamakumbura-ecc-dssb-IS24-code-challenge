package come.sudharaka.codechallenge.repository;

import come.sudharaka.codechallenge.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Product entity.
 *
 * When extending this class, extend ProductRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ProductRepository extends ProductRepositoryWithBagRelationships, JpaRepository<Product, Long> {
    default Optional<Product> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Product> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Product> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    default Page<Product> findByScrumMasterId(long scrumMasterId, Pageable pageable) {
        return this.fetchProductsByScrumMasterId(scrumMasterId, this.findAll(pageable));
    }

    default Page<Product> findByDeveloperId(long developerId, Pageable pageable) {
        return this.fetchProductsByDeveloperId(developerId, this.findAll(pageable));
    }
}
