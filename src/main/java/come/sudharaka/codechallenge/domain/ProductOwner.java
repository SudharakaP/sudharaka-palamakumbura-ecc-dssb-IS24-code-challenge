package come.sudharaka.codechallenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ProductOwner.
 */
@Entity
@Table(name = "product_owner")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "productOwner")
    @JsonIgnoreProperties(value = { "developers", "scrumMaster", "productOwner" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductOwner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ProductOwner name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setProductOwner(null));
        }
        if (products != null) {
            products.forEach(i -> i.setProductOwner(this));
        }
        this.products = products;
    }

    public ProductOwner products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public ProductOwner addProduct(Product product) {
        this.products.add(product);
        product.setProductOwner(this);
        return this;
    }

    public ProductOwner removeProduct(Product product) {
        this.products.remove(product);
        product.setProductOwner(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOwner)) {
            return false;
        }
        return id != null && id.equals(((ProductOwner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOwner{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
