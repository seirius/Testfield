/* global game, PIXI, VECTOR, Unit, playerSide */

class Player extends Entity {
    constructor(x, y) {
        super();
        var player = this;
        player._position = new Vector(x, y);
        player.unitsUnderInfluence = [];
        player.radius = 4;
        player.sRadius = player.radius * player.radius;
        var stats = new C_Stats();
        stats.velocity = 40;
        player.addComponent(stats)
                .addComponent(new C_Graphic(function (graphics) {
                    graphics.lineStyle(1, 0xFFFFFF);
                    graphics.drawCircle(player._position.x, player._position.y, player.radius);
                    graphics.endFill();
                }))
                .addComponent(new C_Course())
                .addComponent(new C_Body(player._position, 4))
                .addComponent(new C_Sensor({
                    radius: 150,
                    frequency: 0.25
                }));
        player.C_Sensor.filter = function (entity) {
            return entity instanceof Unit
                    && entity.side === playerSide;
        };
        player.C_Sensor.addContact(function (unit) {
            unit.selectableInArea();
        });
        player.C_Sensor.addEndContact(function (unit) {
            unit.unSelectableInArea();
        });
        player.startRubber = null;
        player.rubber = null;
        player.lastDirection = null;
    }
    
    init () {
        super.init();
    }
    
    update () {
        var player = this;
        super.update();
        
        if (game.mouse.isRightDown 
                && game.commands.isGeneral) {
            player.lastDirection = new Vector(game.mouse.position);
            player.C_Course.setGoal(new Vector(game.mouse.position));
        }
    }
}

