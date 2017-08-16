package model.bean.menu;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Andriy
 */
@Entity
@Table(name = "rel_menu_menu_list", catalog = "testfield")
public class RelMenuMenuList implements Serializable {
    
    @EmbeddedId
    private RelMenuMenuListId id;

    public RelMenuMenuListId getId() {
        return id;
    }

    public void setId(RelMenuMenuListId id) {
        this.id = id;
    }
    
    
    
}
