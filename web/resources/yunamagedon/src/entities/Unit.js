/* global VECTOR, enemySide */

class Unit extends Entity {
    constructor (x, y) {
        super();
        var unit = this;
        unit._position = new Vector(x, y);
        unit.radius = 4;
        unit.sRadius = unit.radius * unit.radius;
        
        unit.addComponent(new C_Course())
        .addComponent(new C_Stats())
        .addComponent(new C_Body(unit._position, unit.radius));
                
        unit.isSelectable = false;
        unit.isSelected = false;
        unit.isInSelectableArea = false;
    }
    
    update() {
        super.update();
        var unit = this;
        if (!unit.isSelectable) {
            unit.isSelected = false;
        }
    }
    
    onSelect () {
        var unit = this;
        unit.currentColor = unit.selectedColor;
        unit.isSelected = true;
    }
    
    unSelect () {
        var unit = this;
        if (unit.isSelectable) {
            unit.currentColor = unit.selectableColor;
        } else {
            unit.currentColor = unit.originalColor;
        }
        unit.isSelected = false;
    }
    
    selectable() {
        var unit = this;
        if (!unit.isSelected) {
            unit.currentColor = unit.selectableColor;
            unit.isSelectable = true;
        }
    }
    
    selectableInArea() {
        var unit = this;
        unit.selectable();
        unit.isInSelectableArea = true;
    }
    
    unSelectableInArea() {
        var unit = this;
        unit.unselectable();
        unit.isInSelectableArea = false;
    }
    
    unselectable() {
        var unit = this;
        unit.currentColor = unit.originalColor;
        unit.isSelectable = false;
    }
    
    orderMove(goal, store) {
        var unit = this;
        unit.C_Course.setGoal(goal, store);
    }
};

