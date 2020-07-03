package duongdd.dtos;

import java.io.Serializable;

public class PinSolarDTO implements Serializable {
    private String pinName;
    private String pinPrice;
    private String pinImage;
    private double pinCapacity;
    private String pinCategory;
    public PinSolarDTO() {
    }

    public PinSolarDTO(String pinName, String pinPrice, String pinImage, double pinCapacity, String pinCategory) {
        this.pinName = pinName;
        this.pinPrice = pinPrice;
        this.pinImage = pinImage;
        this.pinCapacity = pinCapacity;
        this.pinCategory = pinCategory;
    }

    public String getPinName() {
        return pinName;
    }

    public void setPinName(String pinName) {
        this.pinName = pinName;
    }

    public String getPinPrice() {
        return pinPrice;
    }

    public void setPinPrice(String pinPrice) {
        this.pinPrice = pinPrice;
    }

    public String getPinImage() {
        return pinImage;
    }

    public void setPinImage(String pinImage) {
        this.pinImage = pinImage;
    }

    public double getPinCapacity() {
        return pinCapacity;
    }

    public void setPinCapacity(double pinCapacity) {
        this.pinCapacity = pinCapacity;
    }

    public String getPinCategory() {
        return pinCategory;
    }

    public void setPinCategory(String pinCategory) {
        this.pinCategory = pinCategory;
    }
}
