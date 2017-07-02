/* global PIXI, stage, entities, game */

class Entity {
    
    constructor (x, y, width, height) {
        var entity = this;
        entity.position = new Vector(x, y);
        entity.v = 1;
        entity.dead = false;
        if (width && height) {
            entity.size = {
                width: width,
                height: height
            };
        }
        entity.init();
    }
    
    init () {
        var entity = this;
        game.entities.push(entity);
        entity.sprite = new PIXI.Graphics();
        if (entity.size) {
            entity.sprite.lineStyle(4, 0xFF3300, 1);
            entity.sprite.drawRect(
                0,
                0,
                entity.size.width,
                entity.size.height
            );
        } else {
            entity.sprite.beginFill(0xFFFFFF);
            entity.sprite.drawCircle(0, 0, 32);
        }
        entity.sprite.endFill();
        entity.sprite.x = entity.position.x;
        entity.sprite.y = entity.position.y;
        game.stage.addChild(entity.sprite);
    }
    
    update() {
        var entity = this;
        entity.sprite.x = entity.position.x;
        entity.sprite.y = entity.position.y;
        if (entity.size) {
            entity.sprite.width = entity.size.width;
            entity.sprite.height = entity.size.height;
        }
    }
    
    die () {
        this.dead = true;
    }
    
    remove() {
        var entity = this;
        game.stage.removeChild(entity.sprite);
    }
};

