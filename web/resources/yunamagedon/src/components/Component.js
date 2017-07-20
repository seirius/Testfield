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
        entity[component.constructor.name] = component;
    }
    
    update () {}
    
    die () {}
}

class C_Course extends Component {
    constructor() {
        super();
        var course = this;
        course.goal = null;
        course.moveForce = new Vector(0, 0);
        course.updateGoal = .5;
        course.count = 0;
    }
    
    update() {
        super.update();
        var course = this;
        if (!course.goal || !course.entity.C_Body || !course.entity.C_Stats) {
            return;
        }
        if (course.reachedGoal()) {
            course.goal = null;
            return;
        }
        
        course.count += game.deltaTime;
        if (course.count >= course.updateGoal) {
            course.count = 0;
            course.calcMoveForce();
        }
        course.entity.C_Body.applyForce(course.moveForce);
    }
    
    reachedGoal() {
        var course = this;
        var entity = course.entity;
        return VECTOR
                .distance(entity._position, course.goal) < entity.C_Stats.velocity;
    }
    
    calcMoveForce() {
        var course = this;
        course.moveForce = VECTOR.directionVector(course.entity._position, 
                course.goal, course.entity.C_Stats.velocity);
    }
    
    setGoal(goal) {
        var course = this;
        course.goal = goal;
        course.calcMoveForce();
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
        stats.velocity = 1;
    }
    
    added(entity) {
        super.added(entity);
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
        body.force = new Vector(0, 0);
        body.isMain = true;
    }
    
    added(entity) {
        super.added(entity);
        entity.components.move(entity.components.length - 1, 0);
    }
    
    applyForce(extra) {
        var body = this;
        body.force.plus(extra);
    }
    
    moveLast() {
        var body = this;
        var components = body.entity.components;
        if (body.isMain && components[components.length - 1].id !== body.id) {
            var bodyIndex = 0;
            components.some(function (comp, index) {
                if (comp.id === body.id) {
                    bodyIndex = index;
                    return true;
                }
                
                return false;
            });
            components.move(components.length - 1, bodyIndex);
        }
    }
    
    collide(entity) {
        var body = this;
        if (entity.id === body.entity.id || !entity.C_Body) {
            return false;
        }
        
        var dirAngle = body.force.angle();
        var pntLeft = VECTOR.toVector(dirAngle - 1.5708, body.radius)
                .plus(body.position).plus(body.force);
        var pntRight = VECTOR.toVector(dirAngle + 1.5708, body.radius)
                .plus(body.position).plus(body.force);
        var pntForward = new Vector(VECTOR.toVector(dirAngle, body.radius))
                .plus(body.position).plus(body.force);
        
        var collision = U_STATIC.pntCircle(pntForward, entity.C_Body.position, 
            entity.C_Body.sRadius)
                || U_STATIC.pntCircle(pntLeft, entity.C_Body.position, 
            entity.C_Body.sRadius)
                || U_STATIC.pntCircle(pntRight, entity.C_Body.position, 
            entity.C_Body.sRadius);
        
        if (collision) {
            var angleForce = VECTOR.angleR(body.position, entity.C_Body.position);
            var outsideForce = VECTOR.toVector(angleForce, body.entity.C_Stats.velocity / 2);
            entity.C_Body.applyForce(outsideForce);
        }
        
        return collision;
    }
    
    update() {
        super.update();
        var body = this;
        if (body.force.isZero()) {
            return;
        }
        
        body.moveLast();
        
        var collision = false;
        game.entities.forEach(function (entity) {
            var collAux = body.collide(entity);
            if (collAux) {
                collision = true;
            }
        });
        if (!collision) {
            body.position.plus(body.force);
            if (body.isMain) {
                body.entity._position = new Vector(body.position);
            }
        }
        
        body.force.reset();
    }
}