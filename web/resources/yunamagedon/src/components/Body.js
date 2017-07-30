/* global VECTOR, U_STATIC, game */
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
        body.layer = LAYERS.A;
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
                || !entity.C_Body.isPhysic || !LAYERS.contact(body.layer, entity.C_Body.layer)) {
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

const LAYERS = {
    "A": { 
        name: "A",
        collide: [ "A" ]
    },
    "B": { 
        name: "B",
        collide: [ "A" ]
    },
    contact: function (layerIn, layer) {
        return layerIn.collide.some(function (l) {
            return l === layer.name;
        });
    }
};