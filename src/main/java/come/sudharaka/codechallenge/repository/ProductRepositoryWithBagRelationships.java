package come.sudharaka.codechallenge.repository;

import come.sudharaka.codechallenge.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProductRepositoryWithBagRelationships {
    Optional<Product> fetchBagRelationships(Optional<Product> product);

    List<Product> fetchBagRelationships(List<Product> products);

    Page<Product> fetchBagRelationships(Page<Product> products);

    Page<Product> fetchProductsByScrumMasterId(long scrumMasterId, Page<Product> products);

    Page<Product> fetchProductsByDeveloperId(long developerId, Page<Product> products);
}
