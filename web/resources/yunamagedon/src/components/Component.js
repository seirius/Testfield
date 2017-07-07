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
            position: [x, y]
        });
        pos.shape = new p2.Circle({
            radius: radius
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
        if (pos.entity.bodyUpdate) {
            pos.entity.bodyUpdate(pos);
        } 
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
        pos.position = new Vector(pos.body.position[0], pos.body.position[1]);
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
            pos.body.velocity = [vector.x, vector.y];
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
        sensor.shape = new p2.Circle({
            radius: radius,
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