package duongdd.dao;

import duongdd.dbutils.DBUtils;
import duongdd.entity.BrandProductEntity;
import duongdd.entity.ElectricProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class ElectrictProductDAO implements Serializable {
    public boolean checkProductExist(ElectricProductEntity productEntity){
        EntityManager em = DBUtils.getEntityManager();
        String jpql = "ElectricProductEntity.findByName";
        Query query = em.createNamedQuery(jpql);
        query.setParameter("productName", productEntity.getProductName());
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

    public void insertProduct(ElectricProductEntity productEntity){
        EntityManager em = DBUtils.getEntityManager();
        if(!checkProductExist(productEntity)){
            try {
                ElectricProductEntity electricProductEntity = new ElectricProductEntity();
                electricProductEntity.setProductName(productEntity.getProductName());
                electricProductEntity.setProductCapacity(productEntity.getProductCapacity());
                electricProductEntity.setIdBrand(productEntity.getIdBrand());
                electricProductEntity.setIdCategory(productEntity.getIdCategory());
                em.getTransaction().begin();
                em.persist(electricProductEntity);
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
