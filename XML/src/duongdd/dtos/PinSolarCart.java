package duongdd.dtos;

import duongdd.dao.PinsolarProductDAO;
import duongdd.entity.PinSolarProductEntity;

import java.util.HashMap;
import java.util.Map;

public class PinSolarCart {
    public PinSolarCart() {
    }
    private Map<Integer, PinSolarProductEntity> items;

    public Map<Integer, PinSolarProductEntity> getItems() {
        return items;
    }

    public void setItems(Map<Integer, PinSolarProductEntity> items) {
        this.items = items;
    }
    public void addItemsToCart(int idProduct) {
        PinsolarProductDAO dao = new PinsolarProductDAO();
        if (items == null) {
            this.items = new HashMap<>();
        }
        PinSolarProductEntity dto = dao.getPinDetail(idProduct);
        this.items.put(idProduct, dto);
    }

    public void removeItem(int idProduct) {
        if (this.items.containsKey(idProduct)) {
            this.items.remove(idProduct);
        }
    }
}
