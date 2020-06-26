package duongdd.dtos;

import java.io.Serializable;

public class PinSolarDTO implements Serializable {
    private String pinName;
    private String pinPrice;
    private String pinImage;
    private float pinCapacity;
    public PinSolarDTO() {
    }

    public PinSolarDTO(String pinName, String pinPrice, String pinImage, float pinCapacity) {
        this.pinName = pinName;
        this.pinPrice = pinPrice;
        this.pinImage = pinImage;
        this.pinCapacity = pinCapacity;
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

    public float getPinCapacity() {
        return pinCapacity;
    }

    public void setPinCapacity(float pinCapacity) {
        this.pinCapacity = pinCapacity;
    }
}
