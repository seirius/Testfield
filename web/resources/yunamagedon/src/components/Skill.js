/* global game, VECTOR */

class C_Skill extends Component {
    constructor(args) {
        super();
        var skill = this;
        if (!args.cd) {
            throw "Cooldown no defined";
        }
        if (!args.range) {
            throw "Range no defined";
        }
        skill.cd = args.cd;
        skill.count = 0;
        skill.target = null;
        skill.range = args.range * args.range;
    }
    
    isInRange() {
        var skill = this;
        if (skill.target) {
            return VECTOR.distance(skill.entity._position, 
                skill.target._position) < skill.range;
        }
        
        return false;
    }
}

class C_MeleeSkill extends C_Skill {
    constructor(args) {
        super(args);
        var skill = this;
        if (!args.sensor) {
            throw "Sensor no defined";
        }
        skill.sensor = args.sensor;
    }
    
    update () {
        super.update();
        var skill = this;
        if (skill.count < skill.cd) {
            skill.count += game.deltaTime;
        }
        
        if (skill.target && !skill.sensor.isDetected(skill.target)) {
            skill.target = null;
        }
        
        if (!skill.target && skill.sensor.gotEntities()) {
            skill.target = skill.sensor.getClosestEntity();
        }
        
        if (skill.target) {
            var inRange = skill.isInRange();
            if (inRange && skill.count >= skill.cd) {
                skill.count = 0;
                skill.target.C_Stats.takeDmg(skill.entity, skill.entity.C_Stats.at);
            } else if (!inRange) {
                skill.entity.orderMove(skill.target._position);
            }
        } else {
            skill.entity.C_Course.setGoalFromStored();
        }
    }
}

class C_BowSkill extends C_Skill {
    constructor(args) {
        super(args);
        var skill = this;
        if (!args.sensor) {
            throw "Sensor no defined";
        }
        skill.sensor = args.sensor;
    }
    
    update () {
        super.update();
        var skill = this;
        if (skill.count < skill.cd) {
            skill.count += game.deltaTime;
        }
        
        if (skill.target && !skill.sensor.isDetected(skill.target)) {
            skill.target = null;
        }
        
        if (!skill.target && skill.sensor.gotEntities()) {
            skill.target = skill.sensor.getClosestEntity();
        }
        
        if (skill.target) {
            var inRange = skill.isInRange();
            if (inRange) {
                skill.entity.C_Course.pause = true;
                if (skill.count >= skill.cd) {
                    skill.count = 0;
                    new Arrow({
                        parent: skill.entity,
                        velocity: 200,
                        target: new Vector(skill.target._position),
                        onHit: function (enemy) {
                            enemy.C_Stats.takeDmg(skill.entity, skill.entity.C_Stats.at);
                        }
                    });
                }
            } else {
                skill.entity.C_Course.pause = false;
                skill.entity.orderMove(skill.target._position);
            }
        } else {
            skill.entity.C_Course.setGoalFromStored();
        }
    }
}

