package come.sudharaka.codechallenge.repository;

import come.sudharaka.codechallenge.domain.Product;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProductRepositoryWithBagRelationshipsImpl implements ProductRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Product> fetchBagRelationships(Optional<Product> product) {
        return product.map(this::fetchDevelopers);
    }

    @Override
    public Page<Product> fetchBagRelationships(Page<Product> products) {
        return new PageImpl<>(fetchBagRelationships(products.getContent()), products.getPageable(), products.getTotalElements());
    }

    @Override
    public List<Product> fetchBagRelationships(List<Product> products) {
        return Optional.of(products).map(this::fetchDevelopers).orElse(Collections.emptyList());
    }

    @Override
    public Page<Product> fetchProductsByScrumMasterId(long scrumMasterId) {
        return new PageImpl<>(fetchProductsByScrumMasterOrDeveloper(scrumMasterId, 0));
    }

    @Override
    public Page<Product> fetchProductsByDeveloperId(long developerId) {
        return new PageImpl<>(fetchProductsByScrumMasterOrDeveloper(0, developerId));
    }

    List<Product> fetchProductsByScrumMasterOrDeveloper(long scrumMasterId, long developerId) {
        if (scrumMasterId == 0 && developerId == 0) {
            return Collections.emptyList();
        } else if (scrumMasterId != 0) {
            return entityManager
                .createQuery(
                    "select distinct product from Product product left join fetch product.developers where scrum_master_id is :scrumMasterId",
                    Product.class
                )
                .setParameter("scrumMasterId", scrumMasterId)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
        } else {
            return entityManager
                .createQuery(
                    "select distinct product from Product product left join fetch product.developers where developer_id is :developerId",
                    Product.class
                )
                .setParameter("developerId", developerId)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
        }
    }

    Product fetchDevelopers(Product result) {
        return entityManager
            .createQuery("select product from Product product left join fetch product.developers where product is :product", Product.class)
            .setParameter("product", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Product> fetchDevelopers(List<Product> products) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, products.size()).forEach(index -> order.put(products.get(index).getId(), index));
        List<Product> result = entityManager
            .createQuery(
                "select distinct product from Product product left join fetch product.developers where product in :products",
                Product.class
            )
            .setParameter("products", products)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
