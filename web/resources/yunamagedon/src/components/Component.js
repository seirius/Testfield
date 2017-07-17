/* global Vector, game, PIXI, VECTOR, p2 */

const C_STATIC = {
    id: 0,
    type: {
        POSITION: 1,
        GRAPHIC: 2,
        BODY: 3
    }
};

class Component {
    constructor() {
        var component = this;
        component.id = ++C_STATIC.id;
        component.entity = null;
    }
    
    added(entity) {
        var component = this;
        component.entity = entity;
    }
    
    update () {
        
    }
    
    die () {
        
    }
    
    beginContact(shape) {}
    
    endContact(shape) {}
}

class C_Position extends Component {
    constructor(x, y, radius) {
        super();
        var pos = this;
        if (x instanceof Vector) {
            pos.position = new Vector(x);
        } else {
            pos.position = new Vector(x, y);
        }
        pos.type = C_STATIC.type.POSITION;
        pos.course = null;
        pos.destination = null;
        pos.moving = false;
        pos.v = 1;
        pos.body = new p2.Body({
            mass: 1,
            position: [game.renderToWorld(x), game.renderToWorld(y)]
        });
        pos.shape = new p2.Circle({
            radius: game.renderToWorld(radius)
        });
        pos.shape.component = pos;
        pos.body.addShape(pos.shape);
        pos.updateCourse = 0;
        game.world.addBody(pos.body);
    }
    
    added(entity) {
        super.added(entity);
        var pos = this;
        pos.body.entity = entity;
    }
    
    update () {
        var pos = this;
        pos.updateCourse += 1;
        if (pos.course) {
            if (pos.updateCourse % 10 === 0) {
                pos.course = VECTOR.directionVector(pos.position, pos.destination, pos.v);
                pos.updateCourse = 0;
            }
            var distance = VECTOR.distance(pos.position, pos.destination);
            if (distance < pos.v) {
                pos.stop();
            } else {
                pos.move(pos.course);
            }
        } else {
            pos.stop();
        }
        pos.position = new Vector(game.worldToRender(pos.body.position[0]), 
            game.worldToRender(pos.body.position[1]));
        pos.entity._position = new Vector(pos.position);
    }
    
    setCourse(course) {
        this.course = course;
        this.moving = true;
    }
    
    stop () {
        var pos = this;
        pos.course = null;
        pos.moving = false;
        pos.body.velocity = [0, 0];
    }
    
    move(vector) {
        var pos = this;
        if (pos.entity) {
            pos.body.velocity = [game.renderToWorld(vector.x), game.renderToWorld(vector.y)];
        }
    }
    
    die () {
        var pos = this;
        game.world.removeBody(pos.body);
    }
}

class C_Graphic extends Component {
    constructor() {
        super();
        var g = this;
        g.type = C_STATIC.type.GRAPHIC;
        g.graphics = new PIXI.Graphics();
        game.stage.addChild(g.graphics);
    }
    
    added(entity) {
        super.added(entity);
    }
    
    update () {
        super.update();
        var g = this;
        g.graphics.clear();
        if (g.entity.onDraw) {
            g.entity.onDraw(g.graphics);
        }
    }
    
    die () {
        game.stage.removeChild(this.graphics);
    }
}

class C_Sensor extends Component {
    constructor (radius, body) {
        super();
        var sensor = this;
        body.c_sensor = sensor;
        var wRadius = game.renderToWorld(radius);
        sensor.shape = new p2.Circle({
            radius: wRadius,
            sensor: true
        });
        sensor.shape.component = sensor;
        body.addShape(sensor.shape);
    }
    
    added(entity) {
        super.added(entity);
    }
    
    update () {
        super.update();
    }
    
    sensorDetected() {}
}

class C_Stats extends Component {
    constructor () {
        super();
        var stats = this;
        stats.hp = 10;
        stats.at = 1;
    }
    
    added(entity) {
        super.added(entity);
        var stats = this;
        entity._stats = stats;
    }
    
    update() {
        super.update();
        var stats = this;
        if (stats.hp <= 0) {
            stats.entity.die();
        }
    }
    
    takeDmg (dmgDealer, dmg) {
        var stats = this;
        if (stats.onTakeDmg) {
            stats.onTakeDmg(dmgDealer, dmg);
        }
        stats.hp -= dmg;
    }
}

class C_InteractiveClick extends Component {
    constructor (graphic, w, h, click) {
        super();
        var int = this;
        int.graphic = graphic;
        int.hw = w / 2;
        int.hh = h / 2;
        int.w = w;
        int.h = h;
        int.graphic.interactive = true;
        if (click) {
            int.graphic.on("pointerdown", click);
        }
    }
    
    added(entity) {
        super.added(entity);
        var int = this;
    }
    
    update() {
        super.update();
        var int = this;
        int.graphic.hitArea = 
                new PIXI.Rectangle(int.entity._position.x - int.hw, 
                                   int.entity._position.y - int.hh, int.w, int.h);
    }
}