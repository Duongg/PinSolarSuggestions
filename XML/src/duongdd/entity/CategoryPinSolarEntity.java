package duongdd.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
@Table(name = "CategoryPinSolar", schema = "dbo", catalog = "PinSolarSuggestions")
@NamedQueries({
        @NamedQuery(name="CategoryPinSolarEntity.findByName",query = "SELECT C FROM CategoryPinSolarEntity C WHERE C.nameCategoryPinSolar = :nameCategoryPinSolar"),
        @NamedQuery(name="CategoryPinSolarEntity.findAll", query = "SELECT C FROM CategoryPinSolarEntity C")
})
public class CategoryPinSolarEntity {
    private int idCategoryPinSolar;
    private String nameCategoryPinSolar;
    private Collection<PinSolarProductEntity> pinSolarProductsByIdCategoryPinSolar;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoryPinSolar", nullable = false)
    public int getIdCategoryPinSolar() {
        return idCategoryPinSolar;
    }

    public void setIdCategoryPinSolar(int idCategoryPinSolar) {
        this.idCategoryPinSolar = idCategoryPinSolar;
    }

    @Basic
    @Column(name = "nameCategoryPinSolar", nullable = false, length = 200)
    public String getNameCategoryPinSolar() {
        return nameCategoryPinSolar;
    }

    public void setNameCategoryPinSolar(String nameCategoryPinSolar) {
        this.nameCategoryPinSolar = nameCategoryPinSolar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryPinSolarEntity that = (CategoryPinSolarEntity) o;
        return idCategoryPinSolar == that.idCategoryPinSolar &&
                Objects.equals(nameCategoryPinSolar, that.nameCategoryPinSolar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoryPinSolar, nameCategoryPinSolar);
    }

    @OneToMany(mappedBy = "categoryPinSolarByIdCategoryPinSolar")
    public Collection<PinSolarProductEntity> getPinSolarProductsByIdCategoryPinSolar() {
        return pinSolarProductsByIdCategoryPinSolar;
    }

    public void setPinSolarProductsByIdCategoryPinSolar(Collection<PinSolarProductEntity> pinSolarProductsByIdCategoryPinSolar) {
        this.pinSolarProductsByIdCategoryPinSolar = pinSolarProductsByIdCategoryPinSolar;
    }
}
