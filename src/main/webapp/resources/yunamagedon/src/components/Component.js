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
                .distance(entity._position, course.goal) < course.entity.C_Stats.velocity * game.deltaTime;
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