/* global PIXI, p2 */

var type = "WebGL";
if (!PIXI.utils.isWebGLSupported()) {
    type = "canvas";
}

PIXI.utils.sayHello(type);

var game = new Game(1000, 1000);

var player = new Player(500, 500);
player.v = 10;