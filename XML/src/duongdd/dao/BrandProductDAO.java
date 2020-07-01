package duongdd.dao;

import duongdd.dbutils.DBUtils;
import duongdd.entity.BrandProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class BrandProductDAO {
    public boolean checkBrandExist(String nameBrand) {
        EntityManager em = DBUtils.getEntityManager();
        String jpql = "BrandProductEntity.findByName";
        Query query = em.createNamedQuery(jpql);
        query.setParameter("nameBrand", nameBrand.toUpperCase());
        try {
            query.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
    public void insertBrand(String nameBrand){
        EntityManager em = DBUtils.getEntityManager();
        try {
            if (!checkBrandExist(nameBrand)) {
                BrandProductEntity brand = new BrandProductEntity();
                brand.setNameBrand(nameBrand.toUpperCase());
                em.getTransaction().begin();
                em.persist(brand);
                em.getTransaction().commit();
            }
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
}
