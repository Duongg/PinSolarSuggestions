package duongdd.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "electricproductentity")
@Entity
@Table(name = "ElectricProduct", schema = "dbo", catalog = "PinSolarSuggestions")
@NamedQueries({
        @NamedQuery(name="ElectricProductEntity.findByName", query = "SELECT P FROM ElectricProductEntity P WHERE P.productName = :productName")
})
public class ElectricProductEntity {
    private int idProduct;
    private String productName;
    private double productCapacity;
    private int idCategory;
    private Collection<BrandProductEntity> brandProductsByIdProduct;
    private CategoryProductEntity categoryProductByIdCategory;
    private BrandProductEntity brandProductByIdBrand;
    private int idBrand;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduct", nullable = false)
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Basic
    @Column(name = "productName", nullable = false, length = 200)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "productCapacity", nullable = false, precision = 0)
    public double getProductCapacity() {
        return productCapacity;
    }

    public void setProductCapacity(double productCapacity) {
        this.productCapacity = productCapacity;
    }

    @Basic
    @Column(name = "idCategory", nullable = false)
    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectricProductEntity that = (ElectricProductEntity) o;
        return idProduct == that.idProduct &&
                Double.compare(that.productCapacity, productCapacity) == 0 &&
                idCategory == that.idCategory &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, productName, productCapacity, idCategory);
    }

    public void setBrandProductsByIdProduct(Collection<BrandProductEntity> brandProductsByIdProduct) {
        this.brandProductsByIdProduct = brandProductsByIdProduct;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "idCategory", referencedColumnName = "idCategory", nullable = false, insertable = false, updatable = false)})
    public CategoryProductEntity getCategoryProductByIdCategory() {
        return categoryProductByIdCategory;
    }

    public void setCategoryProductByIdCategory(CategoryProductEntity categoryProductByIdCategory) {
        this.categoryProductByIdCategory = categoryProductByIdCategory;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "idBrand", referencedColumnName = "idBrandProduct", nullable = false, insertable = false, updatable = false)})
    public BrandProductEntity getBrandProductByIdBrand() {
        return brandProductByIdBrand;
    }

    public void setBrandProductByIdBrand(BrandProductEntity brandProductByIdBrand) {
        this.brandProductByIdBrand = brandProductByIdBrand;
    }

    @Basic
    @Column(name = "idBrand", nullable = false)
    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }
}
