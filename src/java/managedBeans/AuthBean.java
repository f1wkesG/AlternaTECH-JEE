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
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import jpa.User;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import ls.weblistener.Listener;


@ManagedBean
@SessionScoped
public class AuthBean implements Serializable  {
    @EJB
    private UserFacade UserEJB;
    private User UserEntity;
    private User selectedUserEntity;
    private Boolean islogged=false;
    private static final long serialVersionUID = 7765876811740798583L;
    @PostConstruct
    public void init() {
        UserEntity = new User();
        selectedUserEntity = new User();
        islogged=false;
    }

    public Boolean getIslogged() {
        return islogged;
    }

    public void setIslogged(Boolean islogged) {
        this.islogged = islogged;
    }

    public User getUserEntity() {
        return UserEntity;
    }

    public void setUserEntity(User UserEntity) {
        this.UserEntity = UserEntity;
    }

    public User getSelectedUserEntity() {
        return selectedUserEntity;
    }

    public void setSelectedUserEntity(User selectedUserEntity) {
        this.selectedUserEntity = selectedUserEntity;
    }
    
    private void putUserInSession(User user) {
        Map<String, Object> session = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap();
        session.put("LOGGEDIN_USER", user);
    }
    
    
    public boolean verifier() throws NoSuchAlgorithmException{
        User user=UserEJB.find_user(UserEntity.getUsername(), UserEntity.getPassword());
        if(user==null){
            this.islogged=false;
            return islogged;
        }
        if(user!=null) {
            this.islogged=true;
            UserEntity = user;
            putUserInSession(user);
            return islogged;}
        this.islogged=false;
        return islogged;
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
    
    public String login() throws NoSuchAlgorithmException{
        if(verifier()==true)
            return "index.xhtml?faces-redirect=true"; 
        return "login";
    }
    
    public String update(){
        UserEJB.update_user(UserEntity);
        return "account";
    }
    public String register(){
        
        try{
            Date date = new Date();
            long time = date.getTime();
            UserEntity.setTime(time);
            UserEJB.add_user(UserEntity);
            return "login";
        }catch(Exception e){
        
        }
        return "register";
    }
    public String deconnexion(){
        this.islogged=false;
        UserEntity = new User();
        return "index.xhtml?faces-redirect = true";
    }
    
    
    public String modifier(){
        
        return "acceuil.xhtml?faces-redirect = true";
    }
    public int nbrUser(){
        return UserEJB.nbrUser();
    }
    /**
     * Creates a new instance of AuthBean
     */
    public AuthBean() {
    }
    
    
    public void logout(){
        System.out.println("logout action invoked");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
    
    public String sessionCount(){
        System.out.println("session count invoked");
        return Integer.toString(Listener.getCount());
    }
    
}
