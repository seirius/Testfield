class King extends Entity {
    constructor(x, y) {
        super();
        var king = this;
        king._position = new Vector(x, y);
        king.radius = 4;
        king.sRadius = king.radius * king.radius;
        king.originalColor = 0x64D400;
        king.currentColor = king.originalColor;
        king.selectedColor = king.originalColor;
        king.selectableColor = king.originalColor;
        
        king.addComponent(new C_Course())
            .addComponent(new C_Stats())
            .addComponent(new C_Body(king._position, king.radius));
        king.addComponent(new C_Graphic(function (graphics) {
            graphics.lineStyle(1.5, king.currentColor);
            graphics.drawCircle(king._position.x, king._position.y, king.radius);
            graphics.endFill();
        }));
        
        king.C_Stats.hp = 1;
    }
}

