/* global game, PIXI, VECTOR, Unit */

class Player extends Entity {
    constructor(x, y) {
        super();
        var player = this;
        player._position = new Vector(x, y);
        player.unitsUnderInfluence = [];
        player.radius = 4;
        player.sRadius = player.radius * player.radius;
        var stats = new C_Stats();
        stats.velocity = 3;
        player.addComponent(stats)
                .addComponent(new C_Graphic())
                .addComponent(new C_Course())
                .addComponent(new C_Body(player._position, 4));
        
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
        graphics.drawCircle(player._position.x, player._position.y, player.radius);
        graphics.endFill();
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

