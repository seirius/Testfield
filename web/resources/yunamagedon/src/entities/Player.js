/* global game, PIXI, VECTOR, Unit */

class Player extends Entity {
    constructor(x, y) {
        super();
        var player = this;
        player.radius = 4;
        player.c_pos = new C_Position(x, y, player.radius);
        player.c_pos.v = 40;
        player.addComponent(player.c_pos);
        player.graphics = new C_Graphic();
        player.addComponent(player.graphics);
        player.generalSensor = new C_Sensor(50, player.c_pos.body);
        player.generalSensor.beginContact = function (shape) {
            if (shape.component.entity instanceof Unit) {
                shape.component.entity.selectableInArea();
            }
        };
        player.generalSensor.endContact = function (shape) {
            if (shape.component.entity instanceof Unit) {
                shape.component.entity.unSelectableInArea();
            }
        };
        player.addComponent(player.generalSensor);
        
        player.startRubber = null;
        player.rubber = null;
        player.lastDirection = null;
    }
    
    init () {
        super.init();
    }
    
    onDraw (graphics) {
        var player = this;
        graphics.lineStyle(1, 0xFFFFFF);
        graphics.drawCircle(player.c_pos.position.x, player.c_pos.position.y, player.radius);
        graphics.endFill();
    }
    
    bodyUpdate (body) {
        var player = this;
    }
    
    update () {
        var player = this;
        super.update();
        
        if (game.mouse.isRightDown 
                && game.commands.isGeneral) {
            player.lastDirection = new Vector(game.mouse.position);
            player.c_pos.destination = new Vector(game.mouse.position);
            player.c_pos
                    .setCourse(VECTOR.directionVector(player.c_pos.position, 
                        game.mouse.position, player.c_pos.v));
        }
    }
}

