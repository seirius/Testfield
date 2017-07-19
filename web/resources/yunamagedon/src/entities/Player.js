/* global game, PIXI, VECTOR, Unit */

class Player extends Entity {
    constructor(x, y) {
        super();
        var player = this;
        player.unitsUnderInfluence = [];
        player.radius = 4;
        player.sRadius = player.radius * player.radius;
        var pos = new C_Position(x, y, player.radius);
        pos.v = 3;
        player.addComponent(pos);
        player.graphics = new C_Graphic();
        player.addComponent(player.graphics);
        player.addComponent(new C_Body(player._position, 4));
        
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

