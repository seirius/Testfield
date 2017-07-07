class Unit extends Entity {
    constructor (x, y) {
        super();
        var unit = this;
        unit.radius = 4;
        unit.c_pos = new C_Position(x, y, unit.radius);
        unit.c_pos.v = 10;
        unit.addComponent(unit.c_pos);
        unit.graphics = new C_Graphic();
        unit.addComponent(unit.graphics);
        unit.surroundingArea = new C_Sensor(10, unit.c_pos.body);
        unit.surroundingUnits = [];
        unit.surroundingArea.beginContact = function (shape) {
            unit.addSurroundingUnit(shape);
        };
        unit.surroundingArea.endContact = function (shape) {
            unit.removeSurroundingUnit(shape);
        };
        unit.addComponent(unit.surroundingArea);
        
        unit.upd = 0;
        
        
        unit.originalColor = 0xD2F95B;
        unit.currentColor = unit.originalColor;
        unit.selectedColor = 0xFF3B3B;
        unit.selectableColor = 0x42C542;
        unit.isSelectable = false;
        unit.isSelected = false;
        unit.isInSelectableArea = false;
    }
    
    onDraw (graphics) {
        var unit = this;
        graphics.lineStyle(1, unit.currentColor);
        graphics.drawCircle(unit.c_pos.position.x, unit.c_pos.position.y, unit.radius);
        graphics.endFill();
    }
    
    addSurroundingUnit(unitShape) {
        var unit = this;
        if (unitShape.component.entity instanceof Unit 
                && unitShape.component.entity.id !== unit.id) {
            var i = 0;
            var isAlreadyIn = false;
            for (i; i < unit.surroundingUnits.length && !isAlreadyIn; i++) {
                var surUnit = unit.surroundingUnits[i];
                isAlreadyIn = surUnit.id === unitShape.component.entity.id;
            }
            if (!isAlreadyIn) {
                unit.surroundingUnits.push(unitShape.component.entity);
            }
        }
    }
    
    removeSurroundingUnit(unitShape) {
        var unit = this;
        if (unitShape.component.entity instanceof Unit) {
            var i = unit.surroundingUnits.length;
            for (i; i > 0; i--) {
                var surUnit = unit.surroundingUnits[i - 1];
                if (surUnit.id === unitShape.component.entity.id) {
                    unit.surroundingUnits.splice(i - 1, 1);
                    i = 0;
                }
            }
        }
    }
    
    bodyUpdate (body) {
        var unit = this;
    }
    
    update() {
        super.update();
        var unit = this;
        unit.upd += 1;
        if (!unit.isSelectable) {
            unit.isSelected = false;
        }
        if (unit.upd % 30 === 0) {
            var i = 0;
            var selectable = false;
            for (i; i < unit.surroundingUnits.length; i++) {
                var surUnit = unit.surroundingUnits[i];
                if (surUnit.isSelectable) {
                    unit.selectable();
                    selectable = true;
                }
            }
            unit.upd = 0;
            if (!selectable && unit.isSelectable
                    && !unit.isInSelectableArea) {
                unit.unselectable();
            } 
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
};

