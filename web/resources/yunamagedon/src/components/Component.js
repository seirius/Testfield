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
        course.storedGoal = null;
        course.pause = false;
        course.lastVelocity = 0;
    }
    
    update() {
        super.update();
        var course = this;
        if (!course.goal || !course.entity.C_Body || !course.entity.C_Stats
                || course.pause) {
            return;
        }
        if (course.reachedGoal()) {
            if (course.storedGoal && course.storedGoal.equals(course.goal)) {
                course.storedGoal = null;
                if (course.onReach) {
                    course.onReach();
                }
            } else if (course.onReach) {
                course.onReach();
            }
            course.goal = null;
            return;
        }
        
        course.count += game.deltaTime;
        if (course.count >= course.updateGoal
                || course.lastVelocity !== course.entity.C_Stats.velocity) {
            course.lastVelocity = course.entity.C_Stats.velocity;
            course.count = 0;
            course.calcMoveForce();
        }
        course.entity.C_Body.applyForce(course.moveForce);
    }
    
    reachedGoal() {
        var course = this;
        var entity = course.entity;
        return VECTOR
                .distance(entity._position, course.goal) < 1;
    }
    
    calcMoveForce() {
        var course = this;
        course.moveForce = VECTOR.directionVector(course.entity._position, 
                course.goal, course.entity.C_Stats.velocity * game.deltaTime);
    }
    
    setGoal(goal, store) {
        var course = this;
        course.goal = new Vector(goal);
        if (store) {
            course.storedGoal = new Vector(goal);
        }
        course.calcMoveForce();
    }
    
    setGoalFromStored() {
        var course = this;
        if (course.storedGoal) {
            course.goal = new Vector(course.storedGoal);
            course.calcMoveForce();
        }
    }
}

class C_Graphic extends Component {
    constructor(onDraw) {
        super();
        var g = this;
        if (!onDraw) {
            throw "onDraw not defined";
        }
        g.type = C_STATIC.type.GRAPHIC;
        g.graphics = new PIXI.Graphics();
        g.onDraw = onDraw;
        game.stage.addChild(g.graphics);
    }
    
    added(entity) {
        super.added(entity);
    }
    
    update () {
        super.update();
        var g = this;
        g.graphics.clear();
        g.onDraw(g.graphics);
    }
    
    die () {
        game.stage.removeChild(this.graphics);
    }
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
    constructor(position, radius, onCollision) {
        super();
        var body = this;
        body.position = position;
        body.radius = radius;
        body.sRadius = radius * radius;
        body.force = new Vector(0, 0);
        body.isMain = true;
        body.onCollision = onCollision;
        body.isPhysic = true;
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
        if (entity.id === body.entity.id || !entity.C_Body 
                || !entity.C_Body.isPhysic) {
            return false;
        }
        
        var dirAngle = body.force.angle();
        var pntLeft = VECTOR.toVector(dirAngle - 1.5708, body.radius)
                .plus(body.position).plus(body.force);
        var pntHLeft = VECTOR.toVector(dirAngle - 0.7853, body.radius)
                .plus(body.position).plus(body.force);
        var pntRight = VECTOR.toVector(dirAngle + 1.5708, body.radius)
                .plus(body.position).plus(body.force);
        var pntHRight = VECTOR.toVector(dirAngle + 0.7853, body.radius)
                .plus(body.position).plus(body.force);
        var pntForward = new Vector(VECTOR.toVector(dirAngle, body.radius))
                .plus(body.position).plus(body.force);
        
        var collision = U_STATIC.pntCircle(pntForward, entity.C_Body.position, 
            entity.C_Body.sRadius)
                || U_STATIC.pntCircle(pntLeft, entity.C_Body.position, 
            entity.C_Body.sRadius)
                || U_STATIC.pntCircle(pntHLeft, entity.C_Body.position, 
            entity.C_Body.sRadius)
                || U_STATIC.pntCircle(pntRight, entity.C_Body.position, 
            entity.C_Body.sRadius)
                || U_STATIC.pntCircle(pntHRight, entity.C_Body.position, 
            entity.C_Body.sRadius);
        
        if (collision) {
            if (body.isPhysic) {
                var angleForce = VECTOR.angleR(body.position, entity.C_Body.position);
                var outsideForce = VECTOR.toVector(angleForce, 
                    body.entity.C_Stats.velocity * game.deltaTime / 2);
                entity.C_Body.applyForce(outsideForce);
            }
            if (body.onCollision) {
                body.onCollision(entity);
            }
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
        if (!collision || !body.isPhysic) {
            body.position.plus(body.force);
            if (body.isMain) {
                body.entity._position = new Vector(body.position);
            }
        }
        
        body.force.reset();
    }
}

class C_Sensor extends Component {
    constructor(args) {
        super();
        var sensor = this;
        if (!args.radius) {
            throw "Radius not defined";
        }
        sensor.radius = args.radius;
        sensor.sRadius = args.radius * args.radius;
        sensor.xOffset = args.xOffset ? args.xOffset : 0;
        sensor.yOffset = args.yOffset ? args.yOffset : 0;
        sensor.frequency = args.frequency ? args.frequency : 0.5;
        sensor.contacts = [];
        sensor.addContact(args.onContact);
        sensor.endContacts = [];
        sensor.addEndContact(args.onEndContact);
        sensor.counter = 0;
        sensor.filter = args.filter;
        sensor.entitiesInside = {};
        sensor.position = new Vector(0, 0);
    }
    
    added(entity) {
        super.added(entity);
        var sensor = this;
        sensor.updatePosition();
    }
    
    updatePosition() {
        var sensor = this;
        sensor.position = VECTOR.plus(sensor.entity._position, 
            new Vector(sensor.xOffset, sensor.yOffset));
    }
    
    addContact(contact) {
        var sensor = this;
        if (typeof contact === "function") {
            sensor.contacts.push(contact);
        }
    }
    
    addEndContact(endContact) {
        var sensor = this;
        if (typeof endContact === "function") {
            sensor.endContacts.push(endContact);
        }
    }
    
    contact(entity) {
        var sensor = this;
        sensor.contacts.forEach(function (contact) {
            contact(entity);
        });
    }
    
    endContact(entity) {
        var sensor = this;
        sensor.endContacts.forEach(function (endContact) {
            endContact(entity);
        });
    }
    
    addEntity(entity) {
        var sensor = this;
        if (!sensor.entitiesInside["entity_" + entity.id]) {
            sensor.entitiesInside["entity_" + entity.id] = entity;
            sensor.contact(entity);
        }
    }
    
    removeEntity(entity) {
        var sensor = this;
        if (sensor.entitiesInside["entity_" + entity.id]) {
            sensor.entitiesInside["entity_" + entity.id] = null;
            delete sensor.entitiesInside["entity_" + entity.id];
            sensor.endContact(entity);
        }
    }
    
    removeEntities() {
        var sensor = this;
        Object.keys(sensor.entitiesInside).forEach(function (entityKey) {
            var entity = sensor.entitiesInside[entityKey];
            if (entity.dead || !sensor.isInside(entity)) {
                sensor.removeEntity(entity);
            }
        });
    }
    
    checkFilter(entity) {
        var sensor = this;
        if (typeof sensor.filter === "function") {
            return sensor.filter(entity);
        }
        
        return true;
    }
    
    addEntities() {
        var sensor = this;
        game.entities.forEach(function (entity) {
            if (sensor.entity.id !== entity.id 
                    && !sensor.entitiesInside["entity_" + entity.id]
                    && sensor.isInside(entity)
                    && sensor.checkFilter(entity)) {
                sensor.addEntity(entity);
            }
        });
    }
    
    isInside(entity) {
        var sensor = this;
        return U_STATIC.pntCircle(entity._position, sensor.position, sensor.sRadius);
    }
    
    isDetected(entity) {
        var sensor = this;
        return typeof sensor.entitiesInside["entity_" + entity.id] === "object";
    }
    
    gotEntities() {
        var sensor = this;
        return Object.keys(sensor.entitiesInside).length > 0;
    }
    
    getFirstEntity() {
        var sensor = this;
        var entity = null;
        Object.keys(sensor.entitiesInside).some(function (entityId) {
            entity = sensor.entitiesInside[entityId];
            return true;
        });
        return entity;
    }
    
    getClosestEntity() {
        var sensor = this;
        var entity = null;
        var minDist = 999999999;
        Object.keys(sensor.entitiesInside).forEach(function (entityId) {
            var auxEntity = sensor.entitiesInside[entityId];
            var dist = VECTOR.distance(sensor.position, auxEntity._position);
            if (dist < minDist) {
                minDist = dist;
                entity = auxEntity;
            }
        });
        return entity;
    }
    
    update() {
        super.update();
        
        var sensor = this;
        sensor.updatePosition();
        sensor.counter += game.deltaTime;
        if (sensor.counter >= sensor.frequency) {
            sensor.counter = 0;
            sensor.removeEntities();
            sensor.addEntities();
        }
    }
}

class C_Skill extends Component {
    constructor(args) {
        super();
        var skill = this;
        if (!args.cd) {
            throw "Cooldown no defined";
        }
        if (!args.range) {
            throw "Range no defined";
        }
        skill.cd = args.cd;
        skill.count = 0;
        skill.target = null;
        skill.range = args.range * args.range;
    }
    
    isInRange() {
        var skill = this;
        if (skill.target) {
            return VECTOR.distance(skill.entity._position, 
                skill.target._position) < skill.range;
        }
        
        return false;
    }
}

class C_MeleeSkill extends C_Skill {
    constructor(args) {
        super(args);
        var skill = this;
        if (!args.sensor) {
            throw "Sensor no defined";
        }
        skill.sensor = args.sensor;
    }
    
    update () {
        super.update();
        var skill = this;
        if (skill.count < skill.cd) {
            skill.count += game.deltaTime;
        }
        
        if (skill.target && !skill.sensor.isDetected(skill.target)) {
            skill.target = null;
        }
        
        if (!skill.target && skill.sensor.gotEntities()) {
            skill.target = skill.sensor.getClosestEntity();
        }
        
        if (skill.target) {
            var inRange = skill.isInRange();
            if (inRange && skill.count >= skill.cd) {
                skill.count = 0;
                skill.target.C_Stats.takeDmg(skill.entity, skill.entity.C_Stats.at);
            } else if (!inRange) {
                skill.entity.orderMove(skill.target._position);
            }
        } else {
            skill.entity.C_Course.setGoalFromStored();
        }
    }
}

class C_BowSkill extends C_Skill {
    constructor(args) {
        super(args);
        var skill = this;
        if (!args.sensor) {
            throw "Sensor no defined";
        }
        skill.sensor = args.sensor;
    }
    
    update () {
        super.update();
        var skill = this;
        if (skill.count < skill.cd) {
            skill.count += game.deltaTime;
        }
        
        if (skill.target && !skill.sensor.isDetected(skill.target)) {
            skill.target = null;
        }
        
        if (!skill.target && skill.sensor.gotEntities()) {
            skill.target = skill.sensor.getClosestEntity();
        }
        
        if (skill.target) {
            var inRange = skill.isInRange();
            if (inRange) {
                skill.entity.C_Course.pause = true;
                if (skill.count >= skill.cd) {
                    skill.count = 0;
                    new Arrow({
                        parent: skill.entity,
                        velocity: 100,
                        target: new Vector(skill.target._position)
                    });
                    skill.target.C_Stats.takeDmg(skill.entity, skill.entity.C_Stats.at);
                }
            } else {
                skill.entity.C_Course.pause = false;
                skill.entity.orderMove(skill.target._position);
            }
        } else {
            skill.entity.C_Course.setGoalFromStored();
        }
    }
}