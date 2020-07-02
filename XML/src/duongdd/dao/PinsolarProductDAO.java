package duongdd.dao;

import duongdd.dbutils.DBUtils;
import duongdd.entity.PinSolarProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;

public class PinsolarProductDAO implements Serializable {
    public boolean checkPinSolarExit(PinSolarProductEntity pinSolarProductEntity){
        EntityManager em = DBUtils.getEntityManager();
        String nameQuery = "PinSolarProductEntity.findByName";
        Query query = em.createNamedQuery(nameQuery);
        query.setParameter("namePinSolar", pinSolarProductEntity.getNamePinSolar());
        try {
            query.getSingleResult();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            if(em != null){
                em.close();
            }
        }

    }
    public void insertPinSolar(PinSolarProductEntity pinSolar){
        EntityManager em = DBUtils.getEntityManager();
        if(!checkPinSolarExit(pinSolar)){
            try{
                PinSolarProductEntity pinSolarProductEntity = new PinSolarProductEntity();
                pinSolarProductEntity.setNamePinSolar(pinSolar.getNamePinSolar());
                pinSolarProductEntity.setCapacityPinSolar(pinSolar.getCapacityPinSolar());
                pinSolarProductEntity.setPricePinSolar(pinSolar.getPricePinSolar());
                pinSolarProductEntity.setImagePinSolar(pinSolar.getImagePinSolar());
                em.getTransaction().begin();
                em.persist(pinSolarProductEntity);
                em.getTransaction().commit();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                if(em != null){
                    em.close();
                }
            }
        }
    }
}
