package duongdd.dao;

import duongdd.dbutils.DBUtils;
import duongdd.entity.PinSolarProductEntity;
import org.eclipse.persistence.internal.jpa.config.converters.EnumeratedImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
                pinSolarProductEntity.setIdCategoryPinSolar(pinSolar.getIdCategoryPinSolar());

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
    public List<PinSolarProductEntity> getAllPinSolar(){
        EntityManager em = DBUtils.getEntityManager();
        List<PinSolarProductEntity> listPin = new ArrayList<>();
        try{
            String sql = "SELECT p.idPinSolar, p.namePinSolar, p.pricePinSolar, p.capacityPinSolar, p.imagePinSolar, p.idCategoryPinSolar\n" +
                    "FROM (PinSolarProduct p INNER JOIN CategoryPinSolar C ON P.idCategoryPinSolar = C.idCategoryPinSolar)\n" +
                    "WHERE C.nameCategoryPinSolar LIKE N'NĂNG LƯỢNG MẶT TRỜI'";
            List<Object[]> result = em.createNativeQuery(sql).getResultList();
            for(Object[] rs : result){
                PinSolarProductEntity dto = new PinSolarProductEntity();
                dto.setIdPinSolar((Integer) rs[0]);
                dto.setNamePinSolar((String) rs[1]);
                dto.setPricePinSolar((String) rs[2]);
                dto.setCapacityPinSolar((int)Math.round((Double) rs[3]));
                dto.setImagePinSolar((String) rs[4]);
                dto.setIdCategoryPinSolar((Integer) rs[5]);
                listPin.add(dto);
            }
            return listPin;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
    public List<PinSolarProductEntity> searchInverter(int totalCapacity){
        EntityManager em = DBUtils.getEntityManager();
        List<PinSolarProductEntity> listPin = new ArrayList<>();
        try {
            String sql = "SELECT p.idPinSolar, p.namePinSolar, p.pricePinSolar, p.capacityPinSolar, p.imagePinSolar \n" +
                    "FROM (PinSolarProduct p INNER JOIN CategoryPinSolar C \n" +
                    "ON p.namePinSolar LIKE '%Inverter%' and P.idCategoryPinSolar = C.idCategoryPinSolar)\n" +
                    "WHERE p.capacityPinSolar ='" + totalCapacity + "'";
            List<Object[]> result = em.createNativeQuery(sql).getResultList();
            for(Object[] rs : result){
                PinSolarProductEntity dto = new PinSolarProductEntity();
                dto.setIdPinSolar((Integer) rs[0]);
                dto.setNamePinSolar((String) rs[1]);
                dto.setPricePinSolar((String) rs[2]);
                dto.setCapacityPinSolar((Double) rs[3]);
                dto.setImagePinSolar((String) rs[4]);
                listPin.add(dto);
            }
            return listPin;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(em != null){
                em.close();
            }
        }
        return null;
    }
    public PinSolarProductEntity getPinDetail(int idPin){
        EntityManager em = DBUtils.getEntityManager();
        PinSolarProductEntity pinSolarProductEntity = new PinSolarProductEntity();
        try{
            String sql ="SELECT p.idPinSolar, p.namePinSolar, p.pricePinSolar, p.capacityPinSolar, p.imagePinSolar \n" +
                    "FROM (PinSolarProduct p INNER JOIN CategoryPinSolar C ON P.idCategoryPinSolar = C.idCategoryPinSolar) \n" +
                    "WHERE p.idPinSolar  ='" + idPin + "'";
            Object[] ob = (Object[]) em.createNativeQuery(sql).getSingleResult();
            pinSolarProductEntity.setIdPinSolar((Integer) ob[0]);
            pinSolarProductEntity.setNamePinSolar((String) ob[1]);
            pinSolarProductEntity.setPricePinSolar((String) ob[2]);
            pinSolarProductEntity.setCapacityPinSolar((int)Math.round((Double) ob[3]));
            pinSolarProductEntity.setImagePinSolar((String) ob[4]);
            return pinSolarProductEntity;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(em != null){
                em.close();
            }
        }
        return null;
    }
}
