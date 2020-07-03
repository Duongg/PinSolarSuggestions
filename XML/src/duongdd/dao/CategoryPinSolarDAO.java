package duongdd.dao;

import duongdd.dbutils.DBUtils;
import duongdd.entity.CategoryPinSolarEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CategoryPinSolarDAO {
    public boolean checkCategoryPinExist(String nameCategory){
        EntityManager em = DBUtils.getEntityManager();
        String nameQuery = "CategoryPinSolarEntity.findByName";
        Query query = em.createNamedQuery(nameQuery);
        query.setParameter("nameCategoryPinSolar",nameCategory.toUpperCase());
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
            if(!checkCategoryPinExist(nameCategory)){
                CategoryPinSolarEntity category = new CategoryPinSolarEntity();
                category.setNameCategoryPinSolar(nameCategory.toUpperCase());
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
    public List<CategoryPinSolarEntity> getAllCategory(){
        EntityManager em = DBUtils.getEntityManager();
        try{
            String nameQuery ="CategoryPinSolarEntity.findAll";
            Query query = em.createNamedQuery(nameQuery);
            List<CategoryPinSolarEntity> result = query.getResultList();
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
