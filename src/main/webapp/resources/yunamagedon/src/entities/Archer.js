/* global Unit, King */

class Archer extends Unit {
    constructor (x, y) {
        super(x, y);
        var archer = this;
        archer.addComponent(new C_Graphic(function (graphics) {
            graphics.lineStyle(1, archer.currentColor);
            graphics.drawCircle(archer._position.x, archer._position.y, archer.radius);
            graphics.endFill();
        })).addComponent(new C_Sensor({
            radius: 240,
            frequency: 1
        })).addComponent(new C_BowSkill({
            sensor: archer.C_Sensor,
            cd: 5,
            range: 200
        })).addComponent(new C_InteractiveClick(archer.C_Graphic.graphics, 8, 8, function () {
            console.log(archer);
        }));
        archer.C_Sensor.filter = function (entity) {
            return ( entity instanceof Unit || entity instanceof King )
                    && entity.side !== archer.side;
        };
        archer.C_Stats.velocity = 10;
        archer.originalColor = 0x372C6B;
        archer.currentColor = archer.originalColor;
        archer.selectedColor = 0xA66FA6;
        archer.selectableColor = 0x42C542;
    }
}
