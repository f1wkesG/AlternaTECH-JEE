/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Commande;
import jpa.Product;
import jpa.User;
import managedBeans.CartItem;


public class CommandeFacade extends AbstractFacade<Commande> {

    @PersistenceContext(unitName = "test3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommandeFacade() {
        super(Commande.class);
    }
    
    public int nbrProduct(){
        Query query = em.createQuery("SELECT COUNT(c) FROM Commande c");
        return ((Long) query.getSingleResult()).intValue();
    }
    
    public void payer(int id, int userid, String details, double totalprice){
        Date date = new Date();
        long time = date.getTime();
        
        try{
            System.out.println("2");
            System.out.println(id);
            System.out.println(userid);
            System.out.println(details);
            System.out.println(totalprice);
            em.createNativeQuery("INSERT INTO Commande VALUES("+id+", "+userid+", '"+details+"',"+totalprice+","+time+")").executeUpdate();
        } catch(NoResultException e) {
             System.out.print(e);
         }
    }    
    
    public String getCommandeDetails(int id){
        Query query = em.createQuery("SELECT c.details FROM Commande c WHERE id=:id");
        query.setParameter("id", id);
        return (query.getSingleResult()).toString();
    }
    
    public List<Commande> getMyCommandes(int userid){
        try{ 
            Query query = em.createQuery("SELECT c FROM Commande c WHERE c.userid = :userid");
            query.setParameter("userid",userid);
	    List<Commande> entity = query.getResultList();
            System.out.println(entity.toArray().toString());
            return entity;
        }  catch(NoResultException e) {
            return null;
        }
    }
    
    public  String getAllProductDetails(){
        String r = "";
        List<Integer> l = new ArrayList<Integer>();
        try{ 
            Query query = em.createQuery("SELECT c FROM Commande AS c");
	    List<Commande> c = query.getResultList();
            for(int i = 0;i<c.size();i++){
                if(r.equals("")){
                    r = c.get(i).getDetail();
                }else{
                    r = r + ";" + c.get(i).getDetail();
                }
                
            }
            return r;
        }  catch(NoResultException e) {
            return r;
        }
    }
    
    
}
