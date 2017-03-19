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
@Table(name = "user_conf", catalog = "testfield")
public class UserConf implements Serializable {
    
    @Id
    @Column(name = "user_nick", unique = true, nullable = false, length = 30)
    private String userNick;
    
    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) throws BeanException {
        BeanValidator.validateEmpty(userNick, "User");
        BeanValidator.validateLength(userNick, 30, "User");
        this.userNick = userNick;
    }

}
