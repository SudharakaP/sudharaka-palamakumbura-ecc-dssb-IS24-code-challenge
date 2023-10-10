package come.sudharaka.codechallenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ScrumMaster.
 */
@Entity
@Table(name = "scrum_master")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScrumMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "scrumMaster")
    @JsonIgnoreProperties(value = { "developers", "scrumMaster", "productOwner" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ScrumMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ScrumMaster name(String name) {
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
            this.products.forEach(i -> i.setScrumMaster(null));
        }
        if (products != null) {
            products.forEach(i -> i.setScrumMaster(this));
        }
        this.products = products;
    }

    public ScrumMaster products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public ScrumMaster addProduct(Product product) {
        this.products.add(product);
        product.setScrumMaster(this);
        return this;
    }

    public ScrumMaster removeProduct(Product product) {
        this.products.remove(product);
        product.setScrumMaster(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScrumMaster)) {
            return false;
        }
        return id != null && id.equals(((ScrumMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScrumMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
