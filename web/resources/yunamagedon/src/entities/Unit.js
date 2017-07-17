/* global VECTOR */

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
        unit.surroundingEnemies = [];
        unit.surroundingArea.beginContact = function (shape) {
            unit.addSurroundingUnit(shape);
        };
        unit.surroundingArea.endContact = function (shape) {
            unit.removeSurroundingUnit(shape);
        };
        unit.addComponent(unit.surroundingArea);

        unit.enemyRadar = new C_Sensor(20, unit.c_pos.body);
        unit.enemyRadar.beginContact = function (shape) {
            unit.addSurroundingEnemy(shape);
        };
        unit.enemyRadar.endContact = function (shape) {
            unit.removeSurroundingEnemy(shape);
        };
        unit.addComponent(unit.enemyRadar);
        
        unit.int = new C_InteractiveClick(unit.graphics.graphics, 8, 8, function () {
            console.log(unit);
        });
        unit.addComponent(unit.int);
        
        unit.enemyTarget = null;
        
        unit.addComponent(new C_Stats());
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
        graphics.drawCircle(unit.c_pos.position.x, unit.c_pos.position.y, unit.radius);
        graphics.endFill();
    }
    
    addSurroundingUnit(unitShape) {
        var unit = this;
        if (unitShape.component.entity instanceof Unit 
                && unitShape.component.entity.id !== unit.id
                && unitShape.component.entity.side !== unit.side) {
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
    
    addSurroundingEnemy(unitShape) {
        var unit = this;
        if (unitShape.component.entity instanceof Unit 
                && unit.side !== unitShape.component.entity.side
                && unitShape.component.entity.id !== unit.id) {
            var i = 0;
            var isAlreadyIn = false;
            for (i; i < unit.surroundingEnemies.length && !isAlreadyIn; i++) {
                var surUnit = unit.surroundingEnemies[i];
                isAlreadyIn = surUnit.id === unitShape.component.entity.id;
            }
            if (!isAlreadyIn) {
                unit.surroundingEnemies.push(unitShape.component.entity);
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
    
    removeSurroundingEnemy(unitShape) {
        var unit = this;
        if (unitShape.component.entity instanceof Unit 
                && unitShape.component.entity.side !== unit.side) {
            var i = unit.surroundingEnemies.length;
            for (i; i > 0; i--) {
                var surUnit = unit.surroundingEnemies[i - 1];
                if (surUnit.id === unitShape.component.entity.id) {
                    unit.surroundingEnemies.splice(i - 1, 1);
                    i = 0;
                }
            }
        }
    }
    
    update() {
        super.update();
        var unit = this;
        unit.upd += 1;
        if (!unit.isSelectable) {
            unit.isSelected = false;
        }
        unit.atCd += 1;
        if (unit.enemyTarget && !unit.enemyTarget.dead && unit.atCd % 60 === 0) {
            unit.enemyTarget._stats.takeDmg(unit, unit._stats.at);
            unit.atCd = 0;
        } else if ( (!unit.enemyTarget || unit.enemyTarget.dead)) {
            if (unit.surroundingEnemies.length > 0) {
                unit.enemyTarget = unit.surroundingEnemies[0];
                unit.c_pos.destination = unit.enemyTarget._position;
                unit.c_pos.setCourse(VECTOR.directionVector(unit.c_pos.position, 
                    unit.enemyTarget._position, unit.c_pos.v));
            } else if (unit.enemyTarget) {
                unit.enemyTarget = null;
                unit.c_pos.destination = unit.command.destination;
                unit.c_pos.setCourse(unit.command.course);
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
        unit.c_pos.destination = destination;
        unit.c_pos.setCourse(course);
    }
};

