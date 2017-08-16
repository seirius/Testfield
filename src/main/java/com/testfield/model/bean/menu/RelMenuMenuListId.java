package com.testfield.model.bean.menu;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Andriy
 */
@Embeddable
public class RelMenuMenuListId implements Serializable {
    
    @Column (name = "menu", nullable = false, length = 60)
    private String menu;
    
    @Column (name = "menu_list", nullable = false, length = 60)
    private String menuList;

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getMenuList() {
        return menuList;
    }

    public void setMenuList(String menuList) {
        this.menuList = menuList;
    }

}
