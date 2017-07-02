/* global PIXI */

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
            isUp: true
        };
        window.onresize = function () {
            game.resize();
        };
        game.init();
    }
    
    init () {
        var game = this;
        game.renderer.render(game.stage);
        document.body.appendChild(game.renderer.view);
        game.resize();
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

    resize() {
        var game = this;
        if (window.innerWidth / window.innerHeight >= game.ratio) {
            var w = window.innerWidth * game.ratio;
            var h = window.innerHeight;
        } else {
            var w = window.innerWidth;
            var h = window.innerWidth / game.ratio;
        }
        game.renderer.view.style.width = w + 'px';
        game.renderer.view.style.height = h + 'px';
        game.updateHitArea();
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
        
        game.renderer.render(game.stage);
    }
}

