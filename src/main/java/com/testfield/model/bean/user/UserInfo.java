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
        if (email != null) {
            BeanValidator.validateLength(email, 80, "Email");
        }
        this.email = email;
    }
    
    @Override
    public String toString() {
        return String.format(
                "USER_INFO (\nuserNick: %s, \nEmail: %s\n)",
                userNick, email);
    }
    
}
