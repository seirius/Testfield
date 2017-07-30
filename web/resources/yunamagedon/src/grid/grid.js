/* global PIXI, game */

class Cell {
    constructor(x, y, w, h) {
        var cell = this;
        cell.hw = w / 2;
        cell.hh = h / 2;
        cell.x = x + cell.hw;
        cell.y = y + cell.hh;
        cell.w = w;
        cell.h = h;
        cell.f = 0;
        cell.g = 0;
        cell.parent = null;
        
        var graphics = new PIXI.Graphics();
        graphics.lineStyle(1, 0xFFFFFF);
        graphics.drawRect(cell.x - cell.hw, cell.y - cell.hh, cell.w, cell.h);
        game.stage.addChild(graphics);
    }
}

