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
    }
    
    minus(vector) {
        var vec = this;
        vec.plus(new Vector(-vector.x, -vector.y));
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
    }
};