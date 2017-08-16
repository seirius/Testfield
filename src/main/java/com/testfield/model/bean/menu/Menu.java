package com.testfield.model.bean.menu;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "menu", catalog = "testfield")
public class Menu implements Serializable {
    
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 60)
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @GeneratedValue(generator = "generator")
    private String id;
    
    @Column(name = "name", nullable = false, length = 60)
    private String name;
    
    @Column(name = "app", nullable = false, length = 40)
    private String app;
    
    @Column(name = "action", nullable = false, length = 60)
    private String action;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
}
