/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootsshop;

/**
 *
 * @author user
 */
import entity.Client;
import entity.Shoe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class BaseDataManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("bootsshopPU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();
          
    public void saveProducts(List<Shoe> products){
        tx.begin();
        for (int i = 0; i < products.size(); i++) {
            Shoe product = products.get(i);
            if(product.getId() == null){
                em.persist(product);
            }else{
                em.merge(product);
            }
        }
        tx.commit();
    }
    
    public List<Shoe> loadProducts(){
        try {
            return em.createQuery("SELECT p FROM Shoe p", Shoe.class)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public void saveClients(List<Client> clients){
        tx.begin();
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if(client.getId() == null){
                em.persist(client);
            }else{
                em.merge(client);
            }
        }
        tx.commit();
    }

    public List<Client> loadClients() {
        try {
            return em.createQuery("SELECT c FROM Client c", Client.class)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public void closeEntityManager() {
        em.close();
        emf.close();
    }
}
