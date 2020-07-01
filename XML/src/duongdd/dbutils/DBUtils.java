package duongdd.dbutils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtils {
    private static final Object LOCK =new Object();
    private static EntityManagerFactory managerFactory;
    public static EntityManager getEntityManager(){
        synchronized (LOCK) {
            if (managerFactory == null) {
                try {
                    managerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return managerFactory.createEntityManager();
    }
}
