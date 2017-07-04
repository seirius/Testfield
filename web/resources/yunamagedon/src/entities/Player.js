/* global game, PIXI, VECTOR */

class Player extends Entity {
    constructor(x, y) {
        super();
        var player = this;
        player.c_pos = new C_Position(x, y);
        player.addComponent(player.c_pos);
        player.graphics = new C_Graphic();
        player.addComponent(player.graphics);
        
        player.startRubber = null;
        player.rubber = null;
        player.lastDirection = null;
    }
    
    init () {
        super.init();
//        player.left = game.keyboard(37);
//        player.up = game.keyboard(38);
//        player.right = game.keyboard(39);
//        player.down = game.keyboard(40);
    }
    
    onDraw (graphics) {
        var player = this;
        graphics.lineStyle(1, 0xFFFFFF);
        graphics.drawCircle(player.c_pos.position.x, player.c_pos.position.y, 4);
        graphics.endFill();
    }
    
    update () {
        var player = this;
        super.update();
        
        if (game.mouse.isRightDown) {
            player.lastDirection = new Vector(game.mouse.position);
            player.c_pos.setCourse(VECTOR.directionVector(player.c_pos.position, game.mouse.position, 2));
        }
        
        if (player.c_pos.moving) {
            var distance = VECTOR.distance(player.c_pos.position, player.lastDirection);
            if (distance < player.c_pos.v * player.c_pos.v) {
                player.c_pos.stop();
            }
        }
    }
}

