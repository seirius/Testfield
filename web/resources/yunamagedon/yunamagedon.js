/* global PIXI, p2 */

const type = "WebGL";
if (!PIXI.utils.isWebGLSupported()) {
    type = "canvas";
}

PIXI.utils.sayHello(type);

const playerSide = 1;
const enemySide = 2;

var l = console.log;

const game = new Game(1000, 1000);

game.player = new Player(100, 100);
game.player.side = playerSide;
game.player.C_Stats.at = 5;
var sUnit = new Archer(120, 120);
sUnit.side = playerSide;
sUnit.C_Stats.velocity = 200;

for (var i = 0; i < 5; i++) {
    for (var j = 0; j < 5; j++) {
        var unit = new Archer(200 + i * 10, 200 + j * 10);
        unit.side = playerSide;
    }
}

for (var i = 0; i < 5; i++) {
    for (var j = 0; j < 1; j++) {
        var unit = new Archer(200 + i * 10, 180 + j * 10);
        unit.side = playerSide;
    }
}

setTimeout(function () {
    for (var i = 0; i < 5; i++) {
        for (var j = 0; j < 5; j++) {
            var unit = new Archer(200 + i * 10, 350 + j * 10);
            unit.originalColor = 0xFF0000;
            unit.currentColor = 0xFF0000;
            unit.side = enemySide;
        }
    }
}, 200);