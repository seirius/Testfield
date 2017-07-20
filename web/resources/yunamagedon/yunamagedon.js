/* global PIXI, p2 */

const type = "WebGL";
if (!PIXI.utils.isWebGLSupported()) {
    type = "canvas";
}

PIXI.utils.sayHello(type);

const playerSide = 1;
const enemySide = 2;

const game = new Game(1000, 1000);

const player = new Player(100, 100);
player.side = playerSide;
game.player = player;
var sUnit = new Unit(120, 120);
sUnit.side = playerSide;
sUnit.C_Course.setGoal(new Vector(600, 120));

for (var i = 0; i < 5; i++) {
    for (var j = 0; j < 5; j++) {
        var unit = new Unit(200 + i * 10, 200 + j * 10);
        unit.side = playerSide;
    }
}

for (var i = 0; i < 5; i++) {
    for (var j = 0; j < 5; j++) {
        var unit = new Unit(260 + i * 10, 200 + j * 10);
        unit.side = playerSide;
    }
}

for (var i = 0; i < 5; i++) {
    for (var j = 0; j < 5; j++) {
        var unit = new Unit(200 + i * 10, 350 + j * 10);
        unit.originalColor = 0xFF0000;
        unit.currentColor = 0xFF0000;
        unit.side = enemySide;
    }
}

for (var i = 0; i < 5; i++) {
    for (var j = 0; j < 5; j++) {
        var unit = new Unit(260 + i * 10, 350 + j * 10);
        unit.originalColor = 0xFF0000;
        unit.currentColor = 0xFF0000;
        unit.side = enemySide;
    }
}

for (var i = 0; i < 5; i++) {
    for (var j = 0; j < 5; j++) {
        var unit = new Unit(320 + i * 10, 350 + j * 10);
        unit.originalColor = 0xFF0000;
        unit.currentColor = 0xFF0000;
        unit.side = enemySide;
    }
}