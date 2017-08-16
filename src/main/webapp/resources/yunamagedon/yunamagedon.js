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

testScene();

function gridScene() {
    var cellWidth = 20;
    var cellHeight = 20;
    var rows = game.w / cellWidth;
    var cols = game.h / cellHeight;
    
    for (var i = 0; i < rows; i++) {
        game.grid[i] = [];
        for (var j = 0; j < cols; j++) {
            game.grid[i][j] = new Cell(i * cellWidth, j * cellHeight, cellWidth, cellHeight); 
        }
    }
}

function testScene() {
    game.player = new Player(100, 100);
    game.player.side = playerSide;
    game.player.C_Stats.at = 5;
    var sUnit = new Archer(120, 120);
    sUnit.side = playerSide;
    sUnit.C_Stats.velocity = 200;

    var king = new King(game.w / 2, 100);
    king.side = playerSide;

    for (var i = 0; i < 5; i++) {
        for (var j = 0; j < 5; j++) {
            var unit = new Archer(200 + i * 10, 200 + j * 10);
            unit.side = playerSide;
        }
    }

    for (var i = 0; i < 5; i++) {
        for (var j = 0; j < 5; j++) {
            var unit = new Archer(300 + i * 10, 180 + j * 10);
            unit.side = playerSide;
        }
    }
    
    setInterval(function () {
        var units = Math.floor(Math.random() * 10);
        var x = Math.random() * 1000;
        for (var i = 0; i < units; i++) {
            var unit = new Soldier(x, 1020);
            unit.originalColor = 0xFF0000;
            unit.currentColor = 0xFF0000;
            unit.side = enemySide;
            unit.C_Course.setGoal(king._position);
            x += 25;
        }
    }, 5000);
}
