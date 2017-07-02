class Vector {
    constructor(x, y) {
        var vector = this;
        if (x instanceof Vector) {
            vector.x = x.x;
            vector.y = x.y;
        } else {
            vector.x = parseInt(x);
            vector.y = parseInt(y);
        }
    }
    
}