/* global PIXI, p2 */

PIXI.utils.sayHello();

var game = {
    renderer: null,
    stage: null,
    container: null,
    world: null,
    zoom: 100
};

var renderer, stage, container, graphics;

init();
animate();

function init() {

    // Init p2.js
    game.world = new p2.World();

    // Add a box
    var boxShape = new p2.Box({width: 2, height: 1});
    var boxBody = new p2.Body({
        mass: 1,
        position: [0, 2],
        angularVelocity: 1
    });
    boxBody.addShape(boxShape);
    game.world.addBody(boxBody);

    // Add a plane
    var planeShape = new p2.Plane();
    var planeBody = new p2.Body({position: [0, -1]});
    planeBody.addShape(planeShape);
    game.world.addBody(planeBody);

    // Pixi.js zoom level
    // Initialize the stage
    renderer = PIXI.autoDetectRenderer(800, 600),
    stage = new PIXI.Stage(0xFFFFFF);

    // We use a container inside the stage for all our content
    // This enables us to zoom and translate the content
    container = new PIXI.DisplayObjectContainer(),
            stage.addChild(container);

    // Add the canvas to the DOM
    document.body.appendChild(renderer.view);

    // Add transform to the container
    container.position.x = renderer.width / 2; // center at origin
    container.position.y = renderer.height / 2;
    container.scale.x = game.zoom;  // zoom in
    container.scale.y = -game.zoom; // Note: we flip the y axis to make "up" the physics "up"

    // Draw the box.
    graphics = new PIXI.Graphics();
    graphics.beginFill(0xff0000);
    graphics.drawRect(-boxShape.width / 2, -boxShape.height / 2, boxShape.width, boxShape.height);

    // Add the box to our container
    container.addChild(graphics);
}

// Animation loop
function animate(t) {
    t = t || 0;
    requestAnimationFrame(animate);

    // Move physics bodies forward in time
    game.world.step(1 / 60);

    // Transfer positions of the physics objects to Pixi.js
//    graphics.position.x = boxBody.position[0];
//    graphics.position.y = boxBody.position[1];
//    graphics.rotation += 0.1;

    // Render scene
    renderer.render(stage);
}

/*

var renderer = PIXI.autoDetectRenderer(400, 300, {
    transparent: true,
    resolution: 2
});
document.getElementById("display").appendChild(renderer.view);

var stage = new PIXI.Container();
var entities = [];

PIXI.loader
        .add("spritesheet", "/Testfield/resources/yunamagedon/src/sprites/spriteSheet.png")
        .add("aang", "/Testfield/resources/yunamagedon/src/sprites/spriteSheet.png")
        .load(setup);

function setup() {
    var texture = PIXI.loader.resources["spritesheet"].texture;
    texture.frame = new PIXI.Rectangle(0, 0, 16, 16);

    var x, y;
    for (x = 0; x < 32; x++) {
        for (y = 0; y < 32; y++) {
            var sprite = new PIXI.Sprite(texture);
            sprite.x = x * 16;
            sprite.y = y * 16;
            stage.addChild(sprite);
        }
    }

    require(["Aang"], () => {
        var aang = new Aang(200, 30);
    });

    animationLoop();
}

function animationLoop() {
    requestAnimationFrame(animationLoop);

    for (let i = 0; i < entities.length; i++) {
        var entity = entities[i];
        entity.update();
    }

    renderer.render(stage);
}

function keyboard(keyCode) {
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

    window.addEventListener(
            "keydown", key.downHandler.bind(key), false
            );
    window.addEventListener(
            "keyup", key.upHandler.bind(key), false
            );
    return key;
}
 

*/