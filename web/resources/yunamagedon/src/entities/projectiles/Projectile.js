/* global Entity, VECTOR, Unit */

class Projectile extends Entity {
    constructor(args) {
        super();
        if (!args.parent || ! (args.parent instanceof Entity) ) {
            throw "Parent not defined";
        }
        if (!args.velocity) {
            throw "Velocity not defined";
        }
        if (!args.parent.C_Stats) {
            throw "Parent has no Stats";
        }
        if (!args.target) {
            throw "Target not defined";
        }
        var proj = this;
        proj.parent = args.parent;
        proj.offset = args.offset;
        proj.velocity = args.velocity;
        proj.target = new Vector(args.target);
        proj._position = new Vector(proj.parent._position);
        proj.radius = 1;
        proj.sRadius = 1;
        
        proj.addComponent(new C_Course())
                .addComponent(new C_Body(proj._position, 1));
        proj.addComponent(new C_Graphic(function (graphics) {
            graphics.lineStyle(1, 0xD2F95B);
            graphics.drawCircle(proj._position.x, proj._position.y, proj.radius);
            graphics.endFill();
        })).addComponent(new C_Stats());
        proj.C_Stats.velocity = args.velocity;
        
        proj.C_Course.setGoal(proj.target);
        proj.color = 0xD2F95B;
    }
}

class Arrow extends Projectile {
    constructor(args) {
        super(args);
        var arrow = this;
        arrow.C_Body.isPhysic = false;
        arrow.startPosition = new Vector(arrow._position);
        arrow.lastPosition = new Vector(arrow._position);
        
        arrow.distance = VECTOR.realDistance(arrow._position, arrow.target);
        arrow.hDistance = arrow.distance / 2;
        arrow.perTick = arrow.velocity / 2 / arrow.hDistance;
        arrow.dPerTick = arrow.perTick * 2;
        arrow.landDistance = arrow.distance / 10;
        var acc = arrow.distance / 7;
        var rnd = Math.random();
        var random = rnd < 0.5 ? -1 * rnd : 1 * rnd;
        var accX = Math.floor((random * acc) + 1);
        var accY = Math.floor((random * acc) + 1);
        arrow.target.plus(new Vector(accX, accY));
        arrow.C_Course.setGoal(arrow.target);
        arrow.count = 0;
        
        
        arrow.C_Course.onReach = function () {
            arrow.die();
        };
        
        arrow.C_Body.onCollision = function (entity) {
            if (arrow.C_Body.isPhysic) {
                if (entity instanceof Unit) {
                    entity.C_Stats.takeDmg(arrow.parent, arrow.parent.C_Stats.at);
                }
                arrow.die();
            }
        };
    }
    
    update() {
        super.update();
        var arrow = this;
        
        var auxDistance = VECTOR.realDistance(arrow._position, arrow.target);
        
        if (arrow.distance > 30) {
            var distanceDone = VECTOR.realDistance(arrow.lastPosition, arrow._position);
            arrow.lastPosition = new Vector(arrow._position);
            if (auxDistance >= arrow.hDistance) {
                arrow.count += distanceDone;
                arrow.C_Stats.velocity -= distanceDone * arrow.perTick;
            } else {
                arrow.C_Stats.velocity += distanceDone * arrow.dPerTick;
            }
        }
        
        if (!arrow.C_Body.isPhysic) {
            if (auxDistance <= arrow.landDistance) {
                arrow.C_Body.isPhysic = true;
            }
        }
        
        if (arrow.C_Stats.velocity <= 0) {
            arrow.die();
        }
    }
}

