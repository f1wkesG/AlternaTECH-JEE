/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Product;


@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "test3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }
    
    
    public  List<Product> findbyName(String name) {
    	try{ 
            Query query = em.createQuery("SELECT u FROM Product u WHERE u.name like :name");
            query.setParameter("name","%"+name+"%");
	    List<Product> entity = query.getResultList();
            return entity;
        }  catch(NoResultException e) {
            return null;
        }
    }
    
    public  List<Product> findbyName(String name, double maxp, double minp) {
    	try{ 
            Query query = em.createQuery("SELECT u FROM Product u WHERE u.name like :name WHERE u.price < :maxp AND u.price > :minp");
            query.setParameter("name","%"+name+"%");
            query.setParameter("maxp",maxp);
            query.setParameter("minp",minp);
	    List<Product> entity = query.getResultList();
            return entity;
        }  catch(NoResultException e) {
            return null;
        }
    }
    
    public  double maxPrice() {
    	try{ 
            Query query = em.createQuery("SELECT u FROM Product u");
	    Product p =  (Product)query.getSingleResult();
            return p.getPrice();
        }  catch(NoResultException e) {
            return 1000;
        }
    }
    
    public  Product findbyId(int id) {
    	try{ 
            Query query = em.createQuery("SELECT u FROM Product u WHERE u.id = :id");
            query.setParameter("id",id);
	    Product p = (Product)query.getSingleResult();
            return p;
        }  catch(NoResultException e) {
            return null;
        }
    }
    
    public int nbrProduct(){
        Query query = em.createQuery("SELECT COUNT(p) FROM Product p");
        return ((Long) query.getSingleResult()).intValue();
    }
    
    public List<Product> findbyCat(int cat){
        try{ 
            Query query = em.createQuery("SELECT u FROM Product u WHERE u.catId= :cat");
            query.setParameter("cat", cat);
	    List<Product> entity = query.getResultList();
            return entity;
        }  catch(NoResultException e) {
            return null;
        }
    }
    
    
}
