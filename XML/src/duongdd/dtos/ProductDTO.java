package duongdd.dtos;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private String productName;
    private double productCapacity;
    private String productBrand;
    private String productCategory;
    public ProductDTO() {
    }

    public ProductDTO(String productName, double productCapacity, String productBrand, String productCategory) {
        this.productName = productName;
        this.productCapacity = productCapacity;
        this.productBrand = productBrand;
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductCapacity() {
        return productCapacity;
    }

    public void setProductCapacity(double productCapacity) {
        this.productCapacity = productCapacity;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
