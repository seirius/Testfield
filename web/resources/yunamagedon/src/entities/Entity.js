/* global PIXI, stage, entities, game, Component, C_Body */

class Entity {
    
    constructor () {
        var entity = this;
        entity.components = [];
        entity.init();
    }
    
    addComponent (component) {
        if (! (component instanceof Component)) {
            console.log("addComponent", "component is not a Component");
        } else {
            var entity = this;
            entity.components.push(component);
            component.entity = entity;
            if (component instanceof C_Body) {
                component.body.entity = entity;
            }
        }
    }
    
    removeComponent (component) {
        if (! (component instanceof Component)) {
            console.log("addComponent", "component is not a Component");
        } else {
            var entity = this;
            var i = entity.components.length;
            for (i; i > 0; i--) {
                var comp = entity.components[i - 1];
                if (comp.id === component.id) {
                    entity.components.splice(i, 1);
                }
            }
        }
    }
    
    removeComponentByType (type) {
        var entity = this;
        var i = entity.components.length;
        for (i; i > 0; i--) {
            var comp = entity.components[i - 1];
            if (comp.type === type) {
                entity.components.splice(i, 1);
            }
        }
    }
    
    getComponent(type) {
        var entity = this;
        var i = 0;
        for (i; i < entity.components.length; i++) {
            if (entity.components[i].type === type) {
                return entity.components[i];
            }
        }
        return null;
    }
    
    init () {
        var entity = this;
        game.entities.push(entity);
    }
    
    update() {
        var entity = this;
        var i = 0;
        for (i; i < entity.components.length; i++) {
            entity.components[i].update();
        }
    }
    
    die () {
        var entity = this;
        entity.dead = true;
        var i = 0;
        for (i; i < entity.components.length; i++) {
            entity.components[i].die();
        }
    }
    
    remove() {
    }
    
    onDraw() {}
};

