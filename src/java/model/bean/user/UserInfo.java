/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import util.BeanValidator;
import util.exceptions.BeanException;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "user_info", catalog = "testfield")
public class UserInfo implements Serializable {
   
    @Id
    @Column(name = "USER_NICK", unique = true, nullable = false, length = 30)
    private String userNick;
    
    @Column(name = "EMAIL", nullable = true, length = 80)
    private String email;

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) throws BeanException {
        BeanValidator.validateEmpty(userNick, "User");
        BeanValidator.validateLength(userNick, 30, "User");
        this.userNick = userNick;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws BeanException {
        BeanValidator.validateLength(email, 80, "Email");
        this.email = email;
    }
    
    @Override
    public String toString() {
        return ""
                + "USER_INFO ("
                + "\nUSER_NICK: " + userNick
                + "\nEMAIL: " + email
                + "\n)"
                + "";
    }
    
}
