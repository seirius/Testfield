/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean.manual;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import model.bean.tag.Tag;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import util.BeanValidator;
import util.enums.ManualState;
import util.enums.ManualVisibility;
import util.exceptions.BeanException;
import util.interfaces.WebBean;

/**
 *
 * @author Andriy
 */

@Entity
@Table(name = "Manual", catalog = "testfield")
public class Manual implements Serializable, WebBean {
    
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Column(name = "USER_NICK", nullable = false, length = 30)
    private String userNick;
    
    @Column(name = "TITLE", nullable = false, length = 255)
    private String title;
    
    @Column(name = "VISIBILITY", nullable = false, length = 2)
    private int visibility;
    
    @Column(name = "DATE_CREATION", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    
    @Column(name = "DATE_LAST_MOD", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastMod;
    
    @Column(name = "CURRENT_STATE", nullable = false, length = 2)
    private int currentState;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANUAL")
    @OrderBy("pageOrder")
    private List<ManualPage> pages;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private ManualConf manualConf;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_tag_manual", catalog = "testfield", 
        joinColumns = {
            @JoinColumn(name = "MANUAL", nullable = false, updatable = false)
        },
        inverseJoinColumns = {
            @JoinColumn(name = "TAG", nullable = false, updatable = false)
        }
    )
    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<ManualPage> getPages() {
        return pages;
    }

    public void setPages(List<ManualPage> pages) {
        this.pages = pages;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) throws BeanException {
        BeanValidator.validateLength(userNick, 30, "User's nick");
        this.userNick = userNick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws BeanException {
        BeanValidator.validateLength(title, 255, "Title");
        this.title = title;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
    
    @Transient
    public void setManualsVisibility(ManualVisibility visibility) {
        this.visibility = visibility.getValue();
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateLastMod() {
        return dateLastMod;
    }

    public void setDateLastMod(Date dateLastMod) {
        this.dateLastMod = dateLastMod;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public ManualConf getManualConf() {
        return manualConf;
    }

    public void setManualConf(ManualConf manualConf) {
        this.manualConf = manualConf;
    }
    
    @Transient
    public boolean isVisible() {
        return getManualVisibility() == ManualVisibility.VISIBLE;
    }
    
    @Transient 
    public ManualVisibility getManualVisibility() {
        return ManualVisibility.toEnum(visibility);
    }
    
    @Transient
    public void setCurrentState(ManualState currentState) {
        this.currentState = currentState.getValue();
    }
    
    @Transient
    @Override
    public Manual prepareForWeb() throws BeanException {
        try {
            Hibernate.initialize(pages);
            Hibernate.initialize(tags);
            Hibernate.initialize(manualConf);
            if (pages == null) {
                return this;
            }
            for (ManualPage page: pages) {
                List<ManualRow> rows = page.getRows();
                Hibernate.initialize(rows);
                if (rows == null) {
                    return this;
                }
                for (ManualRow row: rows) {
                    List<ManualBlock> blocks = row.getBlocks();
                    Hibernate.initialize(blocks);
                    if (blocks == null) {
                        return this;
                    }
                    for (ManualBlock block: blocks) {
                        block.prepareForWeb();
                    }
                }
            }
        } catch(HibernateException e) {
            throw new BeanException("Error preparing the manual for web.", e);
        }
        
        return this;
    }
    
 
}
