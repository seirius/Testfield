package model.bean;
// Generated 03-ago-2016 18:40:01 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.exceptions.BeanException;

/**
 * UserTestfield generated by hbm2java
 */
@Entity
@Table(name="user_testfield"
    ,catalog="testfield"
)
public class UserTestfield  implements java.io.Serializable {


     private String userNick;
     private String password;
     private Date dateCreation = new Date();
     private Set<Connections> connectionses = new HashSet<>(0);

    public UserTestfield() {
    }
    
    public UserTestfield(String userNick) {
        this.userNick = userNick;
    }
    
    public UserTestfield(String userNick, String password) {
        this.userNick = userNick;
        this.password = password;
    }
	
    public UserTestfield(String userNick, String password, Date dateCreation) {
        this.userNick = userNick;
        this.password = password;
        this.dateCreation = dateCreation;
    }
    public UserTestfield(String userNick, String password, Date dateCreation, Set<Connections> connectionses) {
       this.userNick = userNick;
       this.password = password;
       this.dateCreation = dateCreation;
       this.connectionses = connectionses;
    }
   
     @Id 

    
    @Column(name="USER_NICK", unique=true, nullable=false, length=20)
    public String getUserNick() {
        return this.userNick;
    }
    
    public void setUserNick(String userNick) throws BeanException {
        if (userNick == null) {
            throw new BeanException("User can't be null.");
        } else if (userNick.length() > 20) {
            throw new BeanException("User can't have more than 20 characters.");
        }
        
        this.userNick = userNick;
    }

    
    @Column(name="PASSWORD", nullable=false, length=30)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE_CREATION", nullable=false, length=19)
    public Date getDateCreation() {
        return this.dateCreation;
    }
    
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="userTestfield")
    public Set<Connections> getConnectionses() {
        return this.connectionses;
    }
    
    public void setConnectionses(Set<Connections> connectionses) {
        this.connectionses = connectionses;
    }




}


