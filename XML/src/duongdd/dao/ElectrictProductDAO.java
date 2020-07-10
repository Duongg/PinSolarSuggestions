package duongdd.dao;

import duongdd.dbutils.DBUtils;
import duongdd.dtos.ProductDTO;
import duongdd.entity.BrandProductEntity;
import duongdd.entity.ElectricProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
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
                electricProductEntity.setIdCategory(productEntity.getIdCategory());
                electricProductEntity.setIdBrand(productEntity.getIdBrand());

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
    public List<ElectricProductEntity> getAllElectricProduct(){
        EntityManager em = DBUtils.getEntityManager();
        try{
            String nameQuery = "ElectricProductEntity.findAll";
            Query query = em.createNamedQuery(nameQuery);
            List<ElectricProductEntity> result = query.getResultList();
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
    public ElectricProductEntity getElectricProductById(int idProduct){
        EntityManager em = DBUtils.getEntityManager();
        try{
            String nameQuery = "ElectricProductEntity.findById";
            Query query = em.createNamedQuery(nameQuery);
           query.setParameter("idProduct", idProduct);
           List<ElectricProductEntity> result = query.getResultList();
           if(result != null && !result.isEmpty()){
               return result.get(0);
           }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }finally {
            if(em != null){
                em.close();
            }
        }
        return null;
    }
    public List<ElectricProductEntity> searchProductByBrand(String categoryName){
        EntityManager em = DBUtils.getEntityManager();
        try{
            String sql = "SELECT E.idProduct, E.productName, E.productCapacity, B.idBrandProduct, C.idCategory " +
                    "FROM ((ElectricProduct E INNER JOIN BrandProduct B ON E.idBrand = B.idBrandProduct) " +
                    "INNER JOIN CategoryProduct C ON E.idCategory = C.idCategory) " +
                    "WHERE C.nameCategory = N'"+ categoryName + "'";
            List<Object[]> result = em.createNativeQuery(sql).getResultList();

            List<ElectricProductEntity> productDTOList = new ArrayList<>();
            for(Object[] object : result){
                ElectricProductEntity dto = new ElectricProductEntity();
                dto.setIdProduct((Integer) object[0]);
                dto.setProductName((String) object[1]);
                dto.setProductCapacity((Double) object[2]);
                dto.setIdCategory((Integer) object[3]);
                dto.setIdBrand((Integer) object[4]);
                productDTOList.add(dto);
            }
            return productDTOList;
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
