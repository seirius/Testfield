/* global Unit, King */

class Soldier extends Unit {
    constructor(x, y) {
        super(x, y);
        var soldier = this;
        soldier.addComponent(new C_Graphic(function (graphics) {
            graphics.lineStyle(1, soldier.currentColor);
            graphics.drawCircle(soldier._position.x, soldier._position.y, soldier.radius);
            graphics.endFill();
        })).addComponent(new C_Sensor({
            radius: 34,
            frequency: 1
        })).addComponent(new C_MeleeSkill({
            sensor: soldier.C_Sensor,
            cd: 1,
            range: 10
        })).addComponent(new C_InteractiveClick(soldier.C_Graphic.graphics, 8, 8, function () {
            console.log(soldier);
        }));
        soldier.C_Sensor.filter = function (entity) {
            return (entity instanceof Unit || entity instanceof King)
                    && entity.side !== soldier.side;
        };
        soldier.C_Stats.velocity = 10;
        soldier.originalColor = 0xD2F95B;
        soldier.currentColor = soldier.originalColor;
        soldier.selectedColor = 0xA66FA6;
        soldier.selectableColor = 0x42C542;
    }
}

