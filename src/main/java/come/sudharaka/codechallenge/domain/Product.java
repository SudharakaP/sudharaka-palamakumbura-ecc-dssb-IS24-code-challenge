package come.sudharaka.codechallenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import come.sudharaka.codechallenge.domain.enumeration.Methodology;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "methodology")
    private Methodology methodology;

    @Column(name = "location")
    private String location;

    @ManyToMany
    @JoinTable(
        name = "rel_product__developer",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Set<Developer> developers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private ScrumMaster scrumMaster;

    @ManyToOne
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private ProductOwner productOwner;

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Product startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Methodology getMethodology() {
        return this.methodology;
    }

    public Product methodology(Methodology methodology) {
        this.setMethodology(methodology);
        return this;
    }

    public void setMethodology(Methodology methodology) {
        this.methodology = methodology;
    }

    public String getLocation() {
        return this.location;
    }

    public Product location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Developer> getDevelopers() {
        return this.developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public Product developers(Set<Developer> developers) {
        this.setDevelopers(developers);
        return this;
    }

    public Product addDeveloper(Developer developer) {
        this.developers.add(developer);
        developer.getProducts().add(this);
        return this;
    }

    public Product removeDeveloper(Developer developer) {
        this.developers.remove(developer);
        developer.getProducts().remove(this);
        return this;
    }

    public ScrumMaster getScrumMaster() {
        return this.scrumMaster;
    }

    public void setScrumMaster(ScrumMaster scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public Product scrumMaster(ScrumMaster scrumMaster) {
        this.setScrumMaster(scrumMaster);
        return this;
    }

    public ProductOwner getProductOwner() {
        return this.productOwner;
    }

    public void setProductOwner(ProductOwner productOwner) {
        this.productOwner = productOwner;
    }

    public Product productOwner(ProductOwner productOwner) {
        this.setProductOwner(productOwner);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", methodology='" + getMethodology() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
