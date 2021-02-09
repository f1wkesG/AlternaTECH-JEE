/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Commande;
import jpa.User;


@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "test3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public  User find_user(String username, String password) throws NoSuchAlgorithmException {
        User entity = new User();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
    	entity.setUsername(username);
    	entity.setPassword(password);
    	try{ 
            Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password ");
            query.setParameter("username",username);
            String st = new String(sb);
            query.setParameter("password", st);
            entity = (User) query.getSingleResult();
            return entity;
        }  catch(NoResultException e) {
            return null;
        }
    }
    
    public void add_user(User user) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(user.getPassword().getBytes());

        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            String st = new String(sb);
            user.setPassword(st);
        }
       super.create(user);
     }
    
    public void update_user(User user){
       super.edit(user);
    }
    public int nbrUser(){
        Query query = em.createQuery("SELECT COUNT(u) FROM User u");
        return ((Long) query.getSingleResult()).intValue();
    }
    
    public void modifier(int id, String email, String fullname, String address) {
     try{ 
         System.out.println("2");
         System.out.println(email);
         System.out.println(fullname);
         System.out.println(address);
         em.createNativeQuery("UPDATE User AS u SET u.email='"+email+
                "',u.address='"+address+
                "',u.fullname='"+fullname+ 
                "' WHERE u.id="+id).executeUpdate();
          } catch(NoResultException e) {
               System.out.print(e);
           }
    }
    
}
