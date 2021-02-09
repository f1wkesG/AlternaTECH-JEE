/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.UserFacade;
import java.security.NoSuchAlgorithmException;
import javax.inject.Named;
import javax.enterprise.context.Dependent;


@Named(value = "userInfoBean")
@Dependent
public class UserInfoBean {
    private UserFacade UserEJB;
    private String fullname;
    private String email;
    private String oldpass;
    private String newpass;
    
    public UserInfoBean() {
    }
    
    /*public String changeInfos(int id){
        return UserEJB.updateInfos(id, fullname, email);
    }*/

    public UserFacade getUserEJB() {
        return UserEJB;
    }

    public void setUserEJB(UserFacade UserEJB) {
        this.UserEJB = UserEJB;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
    
    
}
