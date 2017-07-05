class Unit extends Entity {
    constructor (x, y) {
        super();
        var unit = this;
        unit.c_pos = new C_Position(x, y);
        unit.addComponent(unit.c_pos);
        unit.graphics = new C_Graphic();
        unit.addComponent(unit.graphics);
        unit.body = new C_Body(new Vector(x, y), 2);
        unit.addComponent(unit.body);
        unit.originalColor = 0xD2F95B;
        unit.currentColor = unit.originalColor;
        unit.selectedColor = 0xFF3B3B;
    }
    
    onDraw (graphics) {
        var unit = this;
        graphics.lineStyle(1, unit.currentColor);
        graphics.drawCircle(unit.c_pos.position.x, unit.c_pos.position.y, 4);
        graphics.endFill();
    }
    
    bodyUpdate (body) {
        var unit = this;
        body.body.position = [unit.c_pos.position.x, unit.c_pos.position.y];
    }
    
    onSelect () {
        var unit = this;
        unit.currentColor = unit.selectedColor;
    }
    
    unSelect () {
        var unit = this;
        unit.currentColor = unit.originalColor;
    }
};

