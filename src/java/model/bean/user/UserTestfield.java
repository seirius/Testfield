/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import model.bean.manual.Manual;
import util.BeanValidator;
import util.exceptions.BeanException;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "user_testfield", catalog = "testfield")
public class UserTestfield implements Serializable {
    
    @Id
    @Column(name = "USER_NICK", unique = true, nullable = false, length = 30)
    private String userNick;
    
    @Column(name = "PASSWORD", nullable = false, length = 30)
    private String password;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATION", nullable = false, length = 19)
    private Date dateCreation = new Date();
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NICK")
    private UserInfo userInfo;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NICK")
    private List<Manual> manuals;

    public List<Manual> getManuals() {
        return manuals;
    }

    public void setManuals(List<Manual> manuals) {
        this.manuals = manuals;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) throws BeanException {
        BeanValidator.validateEmpty(userNick, "Nick");
        BeanValidator.validateLength(userNick, 30, "Nick");
        this.userNick = userNick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws BeanException {
        BeanValidator.validateEmpty(userNick, "Nick");
        BeanValidator.validateLength(password, 30, "Password");
        this.password = password;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) throws BeanException {
        BeanValidator.validateNull(dateCreation, "Creation's date");
        this.dateCreation = dateCreation;
    }
    
    @Override
    public String toString() {
        return ""
                + "USER_TESTFIELD ("
                + "\nUSER_NICK: " + userNick
                + "\nPASSWORD: " + password
                + "\nDATE_CREATION: " + dateCreation 
                + "\nUSER_INFO: " + userInfo.toString()
                + "\n);";
    }
    
}
