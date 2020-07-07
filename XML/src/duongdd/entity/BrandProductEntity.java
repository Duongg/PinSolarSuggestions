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
@Table(name = "BrandProduct", schema = "dbo", catalog = "PinSolarSuggestions")
@NamedQueries({
        @NamedQuery(name = "BrandProductEntity.findByName", query = "SELECT B FROM BrandProductEntity B WHERE B.nameBrand = :nameBrand"),
        @NamedQuery(name ="BrandProductEntity.findAll", query = "SELECT B FROM BrandProductEntity B"),
        @NamedQuery(name ="BrandProductEntity.findAllName", query = "SELECT B.nameBrand FROM BrandProductEntity B")
})
public class BrandProductEntity {
    private int idBrandProduct;
    private String nameBrand;

    private Collection<ElectricProductEntity> electricProductsByIdBrandProduct;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBrandProduct", nullable = false)
    public int getIdBrandProduct() {
        return idBrandProduct;
    }

    public void setIdBrandProduct(int idBrandProduct) {
        this.idBrandProduct = idBrandProduct;
    }

    @Basic
    @Column(name = "nameBrand", nullable = false, length = 50)
    public String getNameBrand() {
        return nameBrand;
    }

    public void setNameBrand(String nameBrand) {
        this.nameBrand = nameBrand;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandProductEntity that = (BrandProductEntity) o;
        return idBrandProduct == that.idBrandProduct &&

                Objects.equals(nameBrand, that.nameBrand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBrandProduct, nameBrand);
    }


    @OneToMany(mappedBy = "brandProductByIdBrand")
    public Collection<ElectricProductEntity> getElectricProductsByIdBrandProduct() {
        return electricProductsByIdBrandProduct;
    }

    public void setElectricProductsByIdBrandProduct(Collection<ElectricProductEntity> electricProductsByIdBrandProduct) {
        this.electricProductsByIdBrandProduct = electricProductsByIdBrandProduct;
    }
}
