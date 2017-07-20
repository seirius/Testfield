/* global C_STATIC */

class Vector {
    constructor(x, y) {
        var vector = this;
        if (x instanceof Vector) {
            vector.x = x.x;
            vector.y = x.y;
        } else {
            vector.x = x;
            vector.y = y;
        }
    }
    
    plus(vector) {
        var vec = this;
        if (vector instanceof Vector) {
            vec.x += vector.x;
            vec.y += vector.y;
        }
        return vec;
    }
    
    minus(vector) {
        var vec = this;
        vec.plus(new Vector(-vector.x, -vector.y));
        return vec;
    }
    
    addMagnitude (magnitude) {
        var vec = this;
        var magX = vec.x > 0 ? magnitude : magnitude * -1;
        var magY = vec.y > 0 ? magnitude : magnitude * -1;
        vec.plus(new Vector(magX, magY));
        return vec;
    }
    
    angle() {
        var vec = this;
        return Math.atan2(vec.y, vec.x);
    }
    
    angleD() {
        var vec = this;
        return vec.angle() * 180 / Math.PI;
    }
    
    isZero() {
        var vec = this;
        return vec.x === 0 && vec.y === 0;
    }
    
    reset() {
        var vec = this;
        vec.x = 0;
        vec.y = 0;
        return vec;
    }
    
}

const VECTOR = {
    angleR: function (vector1, vector2) {
        var p1 = vector2.y - vector1.y;
        var p2 = vector2.x - vector1.x;
        return Math.atan2(p1, p2);
    },
    
    angleD: function (vector1, vector2) {
        return VECTOR.angleR(vector1, vector2) * 180 / Math.PI;
    },
    
    toVector: function (angleR, magnitude) {
        return new Vector(Math.cos(angleR) * magnitude, Math.sin(angleR) * magnitude);
    },
    
    directionVector: function (vector1, vector2, magnitude) {
        var angleR = VECTOR.angleR(vector1, vector2);
        return VECTOR.toVector(angleR, magnitude);
    },
    
    distance: function (vector1, vector2) {
        var x = vector1.x - vector2.x;
        var y = vector1.y - vector2.y;
        return x * x + y * y;
    },
    
    realDistance: function (vector1, vector2) {
        return Math.sqrt(VECTOR.distance(vector1, vector2));
    },
    
    plus: function (vector1, vector2) {
        return new Vector(vector1.x + vector2.x, vector1.y + vector2.y);
    },
    
    minus: function (vector1, vector2) {
        return VECTOR.plus(vector1, new Vector(-vector2.x, -vector2.y));
    }
};

var U_STATIC = {
    getAvgPosition: function (entities) {
        var i = 0;
        var tX = 0;
        var tY = 0;
        for (i; i < entities.length; i++) {
            var entity = entities[i];
            tX += entity._position.x;
            tY += entity._position.y;
        }
        return new Vector(tX / entities.length, tY / entities.length);
    },
    
    pntCircle: function (point, circlePosition, sRadius) {
        var dist = VECTOR.distance(point, circlePosition);
        return dist < sRadius;
    }
};

Array.prototype.move = function (old_index, new_index) {
    if (new_index >= this.length) {
        var k = new_index - this.length;
        while ((k--) + 1) {
            this.push(undefined);
        }
    }
    this.splice(new_index, 0, this.splice(old_index, 1)[0]);
    return this; // for testing purposes
};