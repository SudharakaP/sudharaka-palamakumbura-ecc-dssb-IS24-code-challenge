package come.sudharaka.codechallenge.repository;

import come.sudharaka.codechallenge.domain.ProductOwner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductOwner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOwnerRepository extends JpaRepository<ProductOwner, Long> {}
