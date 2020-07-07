package duongdd.dao;

import duongdd.dbutils.DBUtils;
import duongdd.entity.CategoryProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;


public class CategoryProductDAO implements Serializable {

    public boolean checkCategoryExist(String nameCategory){
        EntityManager em = DBUtils.getEntityManager();
        String jpql = "CategoryProductEntity.findByName";
        Query query = em.createNamedQuery(jpql);
        query.setParameter("nameCategory", nameCategory.toUpperCase());
        try{
            query.getSingleResult();
            return true;
        }catch (Exception e){
            return false;
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
    public void insertCategory(String nameCategory){
        EntityManager em = DBUtils.getEntityManager();
        try{
            if(!checkCategoryExist(nameCategory)){
                CategoryProductEntity category = new CategoryProductEntity();
                category.setNameCategory(nameCategory.toUpperCase());
                em.getTransaction().begin();
                em.persist(category);
                em.getTransaction().commit();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
    public List<CategoryProductEntity> getAllCategory(){
        EntityManager em = DBUtils.getEntityManager();
        try{
            String nameQueryString = "CategoryProductEntity.findAll";
            Query query = em.createNamedQuery(nameQueryString);
            List<CategoryProductEntity> result = query.getResultList();
            return result;
        }catch (Exception e){
            return null;
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
    public List<CategoryProductEntity> getAllNameCategory(){
        EntityManager em = DBUtils.getEntityManager();
        try{
            String nameQueryString = "CategoryProductEntity.findAllCategory";
            Query query = em.createNamedQuery(nameQueryString);
            List<CategoryProductEntity> result = query.getResultList();
            return result;
        }catch (Exception e){
            return null;
        }finally {
            if(em != null){
                em.close();
            }
        }
    }
}
