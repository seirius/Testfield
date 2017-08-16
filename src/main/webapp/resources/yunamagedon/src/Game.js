/* global PIXI, C_STATIC, U_STATIC, VECTOR, p2, Unit, STATIC_UNIT */

class Game {
    constructor(w, h) {
        var game = this;
        game.w = w;
        game.h = h;
        game.renderer = PIXI.autoDetectRenderer(game.w, game.h);
        game.backgroundColor = 0xf0f0f0;
        game.stage = new PIXI.Container(true);
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
        game.selectedEntities = [];
        game.fixedTimeStep = 1 / 60;
        game.maxSubSteps = 10;
        game.deltaTime = 0;
        game.commands = {
            isGeneral: true,
            isUnits: false
        };
        game.player = null;
        game.stopError = false;
        game.stop = false;
        game.grid = [];
        game.init();
    }
    
    init () {
        var game = this;
        game.keyboardCB();
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
        game.fpsDisplay = new PIXI.Text(game.fpss.toString().substring(0, 4), {
            font: "20px Arial", 
            fill: "white"
        });
        setInterval(function () {
            game.fpsDisplay.text = game.fpss.toString().substring(0, 4);
        }, 500);
        game.stage.addChild(game.fpsDisplay);
    }
    
    keyboardCB () {
        var game = this;
        game.keys = {
            one: game.keyboard(49),
            two: game.keyboard(50),
            q: game.keyboard(81),
            w: game.keyboard(87)
        };
        
        game.keys.q.press = function () {
            if (game.player) {
                var prj = new Arrow({
                    parent: game.player,
                    velocity: 200,
                    target: new Vector(game.mouse.position),
                    onHit: function () {}
                });
            }
        };
        
        game.keys.w.press = function () {
            game.stop = !game.stop;
        };
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
    
    modeGeneral() {
        var game = this;
        game.commands.isGeneral = true;
        game.commands.isUnits = false;
    }
    
    modeUnits() {
        var game = this;
        game.commands.isGeneral = false;
        game.commands.isUnits = true;
    }

    
    updateHitArea() {
        var game = this;
        game.stage.hitArea = new PIXI.Rectangle(0, 0, 
            window.outerWidth * game.ratio, 
            window.outerHeight * game.ratio);
    }
    
    gameLoop() {
        var game = this;
        if (game.stopError) {
            return;
        }
        requestAnimationFrame(function () {
            game.gameLoop();
        });
        
        try {
            
            if (!game.stop) {
                var i = game.entities.length;
                for (i; i > 0; i--) {
                    var entity = game.entities[i - 1];
                    entity.update();

                    if (entity.dead) {
                        entity.remove();
                        game.entities.splice(i - 1, 1);
                    }
                }

                game.rubberBand();
                game.command();
            }
            

            game.renderer.render(game.stage);

            if (game.keys.one.isDown) {
                game.modeGeneral();
            }

            if (game.keys.two.isDown) {
                game.modeUnits();
            }
            
            game.mouse.isRightDown = false;

            var thisLoop = new Date();
            game.fpss = 1000 / (thisLoop - game.lastLoop);
            game.deltaTime = (thisLoop - game.lastLoop) / 1000;
            game.lastLoop = thisLoop;
        } catch(e) {
            game.stopError = true;
            console.log(e);
        }

    }
    
    rubberBand() {
        var game = this;
        game.rubber.clear();
        if (game.player) {
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
            if (entity instanceof Unit && game.player.side === entity.side) {
                var position = entity._position;
                if (position.x > oX 
                        && position.x < eX
                        && position.y > oY 
                        && position.y < eY
                        && entity.isSelectable
                        && entity.onSelect) {
                    entity.onSelect();
                    game.selectedEntities.push(entity);
                }
            }
        }
        if (game.selectedEntities.length > 0) {
            game.modeUnits();
        } else {
            game.modeGeneral();
        }
    }
    
    unselectUnits() {
        var game = this;
        game.selectedEntities = [];
        var i = 0;
        for (i; i < game.entities.length; i++) {
            var entity = game.entities[i];
            if (entity.unSelect && entity.isSelected && game.player.side === entity.side) {
                entity.unSelect();
            }
        }
    }
    
    command () {
        var game = this;
        if (game.mouse.isRightDown 
                && game.selectedEntities.length > 0
                && game.commands.isUnits) {
            var avgVector = U_STATIC.getAvgPosition(game.selectedEntities);
            var dif = VECTOR.minus(avgVector, game.mouse.position);
            var i = 0;
            for (i; i < game.selectedEntities.length; i++) {
                var entity = game.selectedEntities[i];
                if (entity.C_Stats && entity.isSelected) {
                    var dest = VECTOR.minus(entity._position, dif);
                    entity.orderMove(dest, true);
                }
            }
        }
    }
    
    worldToRender(size) {
        var game = this;
        return size * game.wrScale;
    }
    
    renderToWorld(size) {
        var game = this;
        return size * game.iwrScale;
    }
    
    gameover() {
        var game = this;
        var gameover = new PIXI.Text("GAME OVER", {
            font: "20px Arial",
            fill: "red"
        });
        gameover.x = game.w / 2 - gameover.width / 2;
        gameover.y = game.h / 2 - gameover.height / 2;
        game.stage.addChild(gameover);
        game.stop = true;
    }
    
    
}
