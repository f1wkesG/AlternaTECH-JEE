/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.UserFacade;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.User;


@Named(value = "changeInfoBean")
@ManagedBean
@RequestScoped
public class ChangeInfoBean implements Serializable   {
    @EJB
    private UserFacade UserEJB;
    private User UserEntity;
    private static final long serialVersionUID = 7765876811740798583L;
    
    
    
    
    private String email;
    private String fullname;
    private String address;
    
    
    @PostConstruct
    public void init() {
        UserEntity = new User();
    }
    public ChangeInfoBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    
    public UserFacade getUserEJB() {
        return UserEJB;
    }

    public void setUserEJB(UserFacade UserEJB) {
        this.UserEJB = UserEJB;
    }

    public User getUserEntity() {
        return UserEntity;
    }

    public void setUserEntity(User UserEntity) {
        this.UserEntity = UserEntity;
    }

    
    public boolean verifier_update() throws NoSuchAlgorithmException{
        User user=UserEJB.find_user(UserEntity.getUsername(), UserEntity.getPassword());
        if(user==null){
            return false;
        }else if(user!=null) {
            return true;
        }
        return false;
    }

    /*public String update(){
        try{
            UserEntity.setAddress("Adresse modifé \n oui oui c'est modifié");
            UserEJB.modifier(UserEntity);
            return "account";
        }catch(Exception e){
        
        }
        return "account";
    }*/
    private Object getObjectFromSession(String key) {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(key);
    }

    public String modifier(){
        User u = (User) getObjectFromSession("LOGGEDIN_USER");
        User user = new User();
        user.setId(u.getId());
        user.setTime(u.getTime());
        user.setType(u.getType());
        user.setUsername(u.getUsername());
        user.setAddress(UserEntity.getAddress());
        user.setFullname(UserEntity.getFullname());
        user.setEmail(UserEntity.getEmail());
        int id = u.getId();
        System.out.println("1");
        System.out.println(email);
        System.out.println(fullname);
        System.out.println(address);
        UserEJB.modifier(id, email, fullname, address);
        return "account.xhtml?faces-redirect = true";
    }
    public int nbrUser(){
        return UserEJB.nbrUser();
    }
    //public String getCommandeItems(){
        
    //}
    
    
    
}
