package duongdd.dtos;

import duongdd.dao.ElectrictProductDAO;
import duongdd.entity.ElectricProductEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCart {
    private int quantity;
    private int time;

    public ProductCart() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private Map<Integer, ElectricProductEntity> items;

    public Map<Integer, ElectricProductEntity> getItems() {
        return items;
    }

    public void setItems(Map<Integer, ElectricProductEntity> items) {
        this.items = items;
    }

    public void addItemsToCaculate(int idProduct) {
        ElectrictProductDAO dao = new ElectrictProductDAO();
        if (items == null) {
            this.items = new HashMap<>();
        }
        ElectricProductEntity dto = dao.getElectricProductById(idProduct);
        this.items.put(idProduct, dto);
    }

    public void removeItem(int idProduct) {
        if (this.items.containsKey(idProduct)) {
            this.items.remove(idProduct);
        }
    }

    public float caculateElectric(int idProduct, float capacity) {

        float totalMoney = 0;
        int f1 = 50, f2 = 100, f3 = 200, f4 = 300, f5 = 400;
        int m1 = 1678, m2 = 1734, m3 = 2014, m4 = 2536, m5 = 2834, m6 = 2927;
        float usedCapacity = capacity / 1000;
        if (usedCapacity <= f1) {
            totalMoney = m1 * usedCapacity;
        } else{
            if (usedCapacity > f1 && usedCapacity <= f2) {
                totalMoney = (m1 * f1) + (m2 * (usedCapacity - f1));
            } else if (usedCapacity > f2 && usedCapacity <= f3) {
                totalMoney = (f1 * (m1+m2) + (usedCapacity-f2) * m3);
            } else if (usedCapacity > f3 && usedCapacity <= f4) {
                totalMoney = (f1 * (m1 + m2 + m3) + (usedCapacity - f3)*m4);
            } else if (usedCapacity > f4 && usedCapacity <= f5) {
                totalMoney = (f1*(m1 + m2 + m3 + m4) + (usedCapacity - f4) * m5);
            }else if(usedCapacity > f5){
                totalMoney = (f1 * (m1 + m2 + m3 + m4 + m5) + (usedCapacity - f5) * m6);
            }
        }

        return totalMoney;
    }
}
