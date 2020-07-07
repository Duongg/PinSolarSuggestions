package duongdd.dao;

import duongdd.dbutils.DBUtils;
import duongdd.entity.BrandProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
    public List<BrandProductEntity> getAllBrand(){
        EntityManager em = DBUtils.getEntityManager();
        try{
            String  nameQueryString = "BrandProductEntity.findAll";
            Query query = em.createNamedQuery(nameQueryString);
            List<BrandProductEntity> result = query.getResultList();
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
    public List<BrandProductEntity> getAllNameBrand(){
        EntityManager em = DBUtils.getEntityManager();
        try{
            String  nameQueryString = "BrandProductEntity.findAllName";
            Query query = em.createNamedQuery(nameQueryString);
            List<BrandProductEntity> result = query.getResultList();
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
}
