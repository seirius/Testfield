/* global PIXI, p2 */

var type = "WebGL";
if (!PIXI.utils.isWebGLSupported()) {
    type = "canvas";
}

PIXI.utils.sayHello(type);

var game = new Game(1000, 1000);

var player = new Player(100, 100);
for (var i = 0; i < 10; i++) {
    for (var j = 0; j < 10; j++) {
        new Unit(200 + i * 20, 200 + j * 20);
    }
}