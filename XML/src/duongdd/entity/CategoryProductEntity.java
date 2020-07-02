package duongdd.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "CategoryProduct", schema = "dbo", catalog = "PinSolarSuggestions")
@NamedQueries({
        @NamedQuery(name="CategoryProductEntity.findByName",query = "SELECT C FROM CategoryProductEntity C WHERE C.nameCategory = :nameCategory"),
        @NamedQuery(name="CategoryProductEntity.findAll", query = "SELECT C FROM CategoryProductEntity C")
})
public class CategoryProductEntity {
    private int idCategory;
    private String nameCategory;
    private Collection<ElectricProductEntity> electricProductsByIdCategory;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategory", nullable = false)
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Basic
    @Column(name = "nameCategory", nullable = false, length = 50)
    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryProductEntity that = (CategoryProductEntity) o;
        return idCategory == that.idCategory &&
                Objects.equals(nameCategory, that.nameCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategory, nameCategory);
    }

    @OneToMany(mappedBy = "categoryProductByIdCategory")
    public Collection<ElectricProductEntity> getElectricProductsByIdCategory() {
        return electricProductsByIdCategory;
    }

    public void setElectricProductsByIdCategory(Collection<ElectricProductEntity> electricProductsByIdCategory) {
        this.electricProductsByIdCategory = electricProductsByIdCategory;
    }
}
