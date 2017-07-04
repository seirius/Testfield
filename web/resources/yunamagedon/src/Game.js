/* global PIXI, C_STATIC */

class Game {
    constructor(w, h) {
        var game = this;
        game.w = w;
        game.h = h;
        game.renderer = PIXI.autoDetectRenderer(game.w, game.h);
        game.backgroundColor = 0xf0f0f0;
        game.stage = new PIXI.Container();
        game.entities = [];
        game.ratio = game.w / game.h;
        game.mouse = {
            position: new Vector(0, 0),
            isDown: false,
            isUp: true,
            isRightDown: false
        };
        game.rubber = new PIXI.Graphics();
        game.stage.addChild(game.rubber);
        game.startRubber = null;
        game.lastLoop = new Date();
        game.fpss = 0;
        game.init();
    }
    
    init () {
        var game = this;
        game.renderer.render(game.stage);
        document.body.appendChild(game.renderer.view);
        game.gameLoop();
        game.stage.interactive = true;
        game.stage.on("mousedown", function (e) {
            game.mouse.isDown = true;
            game.mouse.isUp = false;
        });
        game.stage.on("mouseup", function (e) {
            game.mouse.isDown = false;
            game.mouse.isUp = true;
        });
        game.stage.on("mousemove", function (e) {
            game.mouse.position = new Vector(e.data.global.x, e.data.global.y);
        });
        game.stage.hitArea = new PIXI.Rectangle(0, 0, game.w, game.h);
        game.renderer.view.addEventListener("contextmenu", function (e) {
            e.preventDefault();
            game.mouse.isRightDown = true;
            return false;
        });
        setInterval(function () {
            console.log("fps", game.fpss);
        }, 5000);
    }
    
    keyboard (keyCode) {
        var key = {};
        key.code = keyCode;
        key.isDown = false;
        key.isUp = true;
        key.press = undefined;
        key.release = undefined;
        key.downHandler = function (event) {
            if (event.keyCode === key.code) {
                if (key.isUp && key.press)
                    key.press();
                key.isDown = true;
                key.isUp = false;
            }
            event.preventDefault();
        };

        key.upHandler = function (event) {
            if (event.keyCode === key.code) {
                if (key.isDown && key.release)
                    key.release();
                key.isDown = false;
                key.isUp = true;
            }
            event.preventDefault();
        };

        window.addEventListener("keydown", key.downHandler.bind(key), false);
        window.addEventListener("keyup", key.upHandler.bind(key), false);
        return key;
    }

    
    updateHitArea() {
        var game = this;
        game.stage.hitArea = new PIXI.Rectangle(0, 0, 
            window.outerWidth * game.ratio, 
            window.outerHeight * game.ratio);
    }
    
    gameLoop() {
        var game = this;
        requestAnimationFrame(function () {
            game.gameLoop();
        });

        var i = game.entities.length;
        for (i; i > 0; i--) {
            var entity = game.entities[i - 1];
            entity.update();
            
            if (entity.dead) {
                entity.remove();
                game.entities.splice(i - 1, 1);
            }
        }
        
        if (game.mouse.isRightDown) {
            console.log("move!");
        }
        
        game.rubberBand();
        
        game.renderer.render(game.stage);
        
        game.mouse.isRightDown = false;
        
        var thisLoop = new Date();
        game.fpss = 1000 / (thisLoop - game.lastLoop);
        game.lastLoop = thisLoop;
    }
    
    rubberBand() {
        var game = this;
        game.rubber.clear();
        if (game.mouse.isDown) {
            if (!game.startRubber) {
                game.startRubber = new Vector(game.mouse.position);
                game.unselectUnits();
            }
            game.rubber.lineStyle(1, 0xFF3300);
            var width = game.mouse.position.x - game.startRubber.x;
            var height = game.mouse.position.y - game.startRubber.y;
            game.rubber.drawRect(game.startRubber.x, game.startRubber.y, width, height);
        } else if (game.startRubber) {
            game.selectUnits();
            game.startRubber = null;
        }
    }
    
    selectUnits() {
        var game = this;
        var w = game.mouse.position.x - game.startRubber.x;
        var h = game.mouse.position.y - game.startRubber.y;
        var oX = w < 0 ? game.startRubber.x + w : game.startRubber.x;
        var oY = h < 0 ? game.startRubber.y + h : game.startRubber.y;
        var eX = oX + Math.abs(w);
        var eY = oY + Math.abs(h);
        var i = 0;
        for (i; i < game.entities.length; i++) {
            var entity = game.entities[i];
            var pos = entity.getComponent(C_STATIC.type.POSITION);
            if (pos 
                    && pos.position.x > oX 
                    && pos.position.x < eX
                    && pos.position.y > oY 
                    && pos.position.y < eY
                    && entity.onSelect) {
                entity.onSelect();
            }
        }
    }
    
    unselectUnits() {
        var game = this;
        var i = 0;
        for (i; i < game.entities.length; i++) {
            var entity = game.entities[i];
            if (entity.unSelect) {
                entity.unSelect();
            }
        }
    }
    
    
}

