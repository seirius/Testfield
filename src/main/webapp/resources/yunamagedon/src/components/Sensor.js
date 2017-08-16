/* global VECTOR, game, U_STATIC */

class C_Sensor extends Component {
    constructor(args) {
        super();
        var sensor = this;
        if (!args.radius) {
            throw "Radius not defined";
        }
        sensor.radius = args.radius;
        sensor.sRadius = args.radius * args.radius;
        sensor.xOffset = args.xOffset ? args.xOffset : 0;
        sensor.yOffset = args.yOffset ? args.yOffset : 0;
        sensor.frequency = args.frequency ? args.frequency : 0.5;
        sensor.contacts = [];
        sensor.addContact(args.onContact);
        sensor.endContacts = [];
        sensor.addEndContact(args.onEndContact);
        sensor.counter = 0;
        sensor.filter = args.filter;
        sensor.entitiesInside = {};
        sensor.position = new Vector(0, 0);
    }
    
    added(entity) {
        super.added(entity);
        var sensor = this;
        sensor.updatePosition();
    }
    
    updatePosition() {
        var sensor = this;
        sensor.position = VECTOR.plus(sensor.entity._position, 
            new Vector(sensor.xOffset, sensor.yOffset));
    }
    
    addContact(contact) {
        var sensor = this;
        if (typeof contact === "function") {
            sensor.contacts.push(contact);
        }
    }
    
    addEndContact(endContact) {
        var sensor = this;
        if (typeof endContact === "function") {
            sensor.endContacts.push(endContact);
        }
    }
    
    contact(entity) {
        var sensor = this;
        sensor.contacts.forEach(function (contact) {
            contact(entity);
        });
    }
    
    endContact(entity) {
        var sensor = this;
        sensor.endContacts.forEach(function (endContact) {
            endContact(entity);
        });
    }
    
    addEntity(entity) {
        var sensor = this;
        if (!sensor.entitiesInside["entity_" + entity.id]) {
            sensor.entitiesInside["entity_" + entity.id] = entity;
            sensor.contact(entity);
        }
    }
    
    removeEntity(entity) {
        var sensor = this;
        if (sensor.entitiesInside["entity_" + entity.id]) {
            sensor.entitiesInside["entity_" + entity.id] = null;
            delete sensor.entitiesInside["entity_" + entity.id];
            sensor.endContact(entity);
        }
    }
    
    removeEntities() {
        var sensor = this;
        Object.keys(sensor.entitiesInside).forEach(function (entityKey) {
            var entity = sensor.entitiesInside[entityKey];
            if (entity.dead || !sensor.isInside(entity)) {
                sensor.removeEntity(entity);
            }
        });
    }
    
    checkFilter(entity) {
        var sensor = this;
        if (typeof sensor.filter === "function") {
            return sensor.filter(entity);
        }
        
        return true;
    }
    
    addEntities() {
        var sensor = this;
        game.entities.forEach(function (entity) {
            if (sensor.entity.id !== entity.id 
                    && !sensor.entitiesInside["entity_" + entity.id]
                    && sensor.isInside(entity)
                    && sensor.checkFilter(entity)) {
                sensor.addEntity(entity);
            }
        });
    }
    
    isInside(entity) {
        var sensor = this;
        return U_STATIC.pntCircle(entity._position, sensor.position, sensor.sRadius);
    }
    
    isDetected(entity) {
        var sensor = this;
        return typeof sensor.entitiesInside["entity_" + entity.id] === "object";
    }
    
    gotEntities() {
        var sensor = this;
        return Object.keys(sensor.entitiesInside).length > 0;
    }
    
    getFirstEntity() {
        var sensor = this;
        var entity = null;
        Object.keys(sensor.entitiesInside).some(function (entityId) {
            entity = sensor.entitiesInside[entityId];
            return true;
        });
        return entity;
    }
    
    getClosestEntity() {
        var sensor = this;
        var entity = null;
        var minDist = 999999999;
        Object.keys(sensor.entitiesInside).forEach(function (entityId) {
            var auxEntity = sensor.entitiesInside[entityId];
            var dist = VECTOR.distance(sensor.position, auxEntity._position);
            if (dist < minDist) {
                minDist = dist;
                entity = auxEntity;
            }
        });
        return entity;
    }
    
    update() {
        super.update();
        
        var sensor = this;
        sensor.updatePosition();
        sensor.counter += game.deltaTime;
        if (sensor.counter >= sensor.frequency) {
            sensor.counter = 0;
            sensor.removeEntities();
            sensor.addEntities();
        }
    }
}

