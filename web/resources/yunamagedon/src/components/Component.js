/* global Vector, game, PIXI */

const C_STATIC = {
    id: 0,
    type: {
        POSITION: 1,
        GRAPHIC: 2
    }
};

class Component {
    constructor() {
        var component = this;
        component.id = ++C_STATIC.id;
        component.entity = null;
    }
    
    update () {
        
    }
    
    die () {
        
    }
}

class C_Position extends Component {
    constructor(x, y) {
        super();
        var pos = this;
        if (x instanceof Vector) {
            pos.position = new Vector(x);
        } else {
            pos.position = new Vector(x, y);
        }
        pos.type = C_STATIC.type.POSITION;
        pos.course = null;
        pos.moving = false;
        pos.v = 1;
    }
    
    update () {
        var pos = this;
        if (pos.course) {
            pos.move(pos.course);
        }
    }
    
    setCourse(course) {
        this.course = course;
        this.moving = true;
    }
    
    stop () {
        this.course = null;
        this.moving = false;
    }
    
    move(vector) {
        var pos = this;
        pos.position.plus(vector);
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




