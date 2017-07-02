/* global game */

class Player extends Entity {
    constructor(x, y) {
        super(x, y);
        var player = this;
        player.rubber = null;
        player.rubberFP = new Vector(0, 0);
    }
    
    init () {
        var player = this;
        super.init();
        player.left = game.keyboard(37);
        player.up = game.keyboard(38);
        player.right = game.keyboard(39);
        player.down = game.keyboard(40);
        
    }
    
    update () {
        var player = this;
        super.update();
        
        if (player.left.isDown) {
            player.position.x -= player.v;
        }
        if (player.right.isDown) {
            player.position.x += player.v;
        }
        if (player.up.isDown) {
            player.position.y -= player.v;
        }
        if (player.down.isDown) {
            player.position.y += player.v;
        }
        
        if (game.mouse.isDown) {
            var w = game.mouse.position.x - player.rubberFP.x;
            var h = game.mouse.position.y - player.rubberFP.y;
            if (!player.rubber) {
                player.rubberFP = new Vector(game.mouse.position);
                player.rubber = new Entity(player.rubberFP.x, player.rubberFP.y, 10, 10);
            }
            player.rubber.sprite.width = parseInt(w);
            player.rubber.sprite.height = parseInt(h);
            console.log(player.rubber.sprite.width);
        } else if (player.rubber) {
            player.rubber.die();
            player.rubber = null;
        }
    }
}

