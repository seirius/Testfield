/* global VECTOR */

class Unit extends Entity {
    constructor (x, y) {
        super();
        var unit = this;
        unit._position = new Vector(x, y);
        unit.radius = 4;
        unit.sRadius = unit.radius * unit.radius;
        unit.surroundingUnits = [];
        unit.surroundingEnemies = [];

        
        unit.enemyTarget = null;
        var stats = new C_Stats();
        stats.velocity = 1;
        
        unit.addComponent(new C_Graphic())
                .addComponent(new C_Course())
                .addComponent(stats)
                .addComponent(new C_InteractiveClick(unit.C_Graphic.graphics, 8, 8, function () {
                    console.log(unit);
                }))
                .addComponent(new C_Body(unit._position, 4));
        
        unit.atCd = 0;
        unit.upd = 0;
        unit.originalColor = 0xD2F95B;
        unit.currentColor = unit.originalColor;
        unit.selectedColor = 0xA66FA6;
        unit.selectableColor = 0x42C542;
        unit.isSelectable = false;
        unit.isSelected = false;
        unit.isInSelectableArea = false;
        unit.aiStatus = null;
        unit.command = {
            course: null,
            destination: null
        };
    }
    
    onDraw (graphics) {
        var unit = this;
        graphics.lineStyle(1, unit.currentColor);
        graphics.drawCircle(unit._position.x, unit._position.y, unit.radius);
        graphics.endFill();
    }
    
    update() {
        super.update();
        var unit = this;
        unit.upd += 1;
//        if (!unit.isSelectable) {
//            unit.isSelected = false;
//        }
        unit.atCd += 1;
        if (unit.enemyTarget && !unit.enemyTarget.dead && unit.atCd % 60 === 0) {
            unit.enemyTarget._stats.takeDmg(unit, unit.C_Stats.at);
            unit.atCd = 0;
        } else if ( (!unit.enemyTarget || unit.enemyTarget.dead)) {
            if (unit.surroundingEnemies.length > 0) {
                unit.enemyTarget = unit.surroundingEnemies[0];
                unit.C_Course.setGoal(unit.enemyTarget._position);
            } else if (unit.enemyTarget) {
                unit.enemyTarget = null;
                unit.C_Course.setGoal(unit.command.destination);
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
    
    orderMove(course, destination) {
        var unit = this;
        unit.command = {
            course: course,
            destination: destination
        };
        unit.C_Course.setGoal(destination);
    }
};

