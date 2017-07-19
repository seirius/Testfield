/* global Vector, game, PIXI, VECTOR, p2, U_STATIC */

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
        pos.cantMove = false;
        pos.v = 1;
        pos.updateCourse = 0;
        pos.outsideForce = new Vector(0, 0);
    }
    
    added(entity) {
        super.added(entity);
        var pos = this;
        entity.c_pos = pos;
    }
    
    update () {
        var pos = this;
        pos.updateCourse += 1;
        if (pos.course && !pos.cantMove) {
            if (pos.updateCourse % 10 === 0) {
                pos.course = VECTOR.directionVector(pos.position, pos.destination, pos.v);
                pos.updateCourse = 0;
            }
            var distance = VECTOR.distance(pos.position, pos.destination);
            if (distance < pos.v) {
                pos.stop();
            } else {
                var movement = new Vector(pos.course);
                if (!pos.outsideForce.isZero()) {
                    movement.plus(pos.outsideForce);
                    pos.outsideForce = new Vector(0, 0);
                }
                pos.move(VECTOR.plus(pos.course, pos.outsideForce));
            }
        } else if (!pos.outsideForce.isZero()) {
            pos.move(pos.outsideForce);
            pos.outsideForce = new Vector(0, 0);
        } else if (!pos.cantMove) {
            pos.stop();
        }
        pos.entity._position = new Vector(pos.position);
    }
    
    setCourse(course) {
        this.course = course;
        this.moving = true;
    }
    
    applyForce(force) {
        var pos = this;
        pos.outsideForce = force;
    }
    
    pause() {
        var pos = this;
        pos.cantMove = true;
    }
    
    resume() {
        var pos = this;
        pos.cantMove = false;
    }
    
    stop () {
        var pos = this;
        pos.course = null;
        pos.moving = false;
    }
    
    move(vector) {
        var pos = this;
        if (pos.entity) {
            pos.position.plus(vector);
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

class C_Body extends Component {
    constructor(position, radius) {
        super();
        var body = this;
        body.position = position;
        body.radius = radius;
        body.sRadius = radius * radius;
    }
    
    added(entity) {
        super.added(entity);
        var body = this;
        entity.body = body;
        if (entity.c_pos) {
            var indexPos = 0;
            var indexBody = 0;
            entity.components.forEach(function (component, index) {
                if (component instanceof C_Position) {
                    indexPos = index;
                } else if (component instanceof C_Body) {
                    indexBody = index;
                }
            });
            
            entity.components.move(indexPos, indexBody);
        }
    }
    
    collide(entity) {
        var body = this;
        if (entity.id === body.entity.id || !entity.body 
                || !body.entity.c_pos
                || !entity.c_pos ) {
            return false;
        }
        
        var force;
        if (body.entity.c_pos.course) {
            force = VECTOR.plus(body.entity.c_pos.course, body.entity.c_pos.outsideForce);
        } else if (body.entity.c_pos.outsideForce) {
            force = new Vector(body.entity.c_pos.outsideForce);
        } else {
            return false;
        }
        
        var dirAngle = force.angle();
        var pntLeft = VECTOR.toVector(dirAngle - 1.5708, body.radius)
                .plus(body.position).plus(force);
        var pntRight = VECTOR.toVector(dirAngle + 1.5708, body.radius)
                .plus(body.position).plus(force);
        var pntForward = new Vector(VECTOR.toVector(dirAngle, body.radius))
                .plus(body.position).plus(force);
        
        var collision = U_STATIC.pntCircle(pntForward, entity.body.position, 
            entity.body.sRadius)
                || U_STATIC.pntCircle(pntLeft, entity.body.position, 
            entity.body.sRadius)
                || U_STATIC.pntCircle(pntRight, entity.body.position, 
            entity.body.sRadius);
        
        if (collision) {
            var angleForce = VECTOR.angleR(body.position, entity.body.position);
            var outsideForce = VECTOR.toVector(angleForce, body.entity.c_pos.v / 2);
            entity.c_pos.applyForce(outsideForce);
        }
        
        return collision;
    }
    
    update() {
        super.update();
        var body = this;
        body.position = new Vector(body.entity._position);
        
        if (!body.entity.c_pos) {
            return;
        }
        
        var collision = false;
        game.entities.forEach(function (entity) {
            var collAux = body.collide(entity);
            if (collAux) {
                collision = true;
            }
        });
        
        if (body.entity instanceof Player) {
            if (collision) {
                body.entity.c_pos.pause();
            } else {
                body.entity.c_pos.resume();
            }
        } else {
            if (collision) {
                body.entity.c_pos.pause();
            } else if (body.entity.c_pos.cantMove) {
                body.entity.c_pos.resume();
            }
        }
        
    }
}