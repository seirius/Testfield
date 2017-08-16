package com.testfield.model.bean.user;

import com.testfield.util.BeanValidator;
import com.testfield.util.exceptions.BeanException;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
