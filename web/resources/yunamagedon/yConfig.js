require.config({
    paths: {
        "jQuery": [
            "https://code.jquery.com/jquery-1.12.0.min", 
            "/Testfield/resources/js/jquery/jquery"
        ],
        "yunamagedon": [
            "/Testfield/resources/yunamagedon/yunamagedon"
        ],
        "pixi": [
            "/Testfield/resources/js/pixi/pixi"
        ],
        "p2": [
            "/Testfield/resources/js/p2/p2"
        ],
        "Entity": [
            "/Testfield/resources/yunamagedon/src/entities/Entity"
        ],
        "Player": [
            "/Testfield/resources/yunamagedon/src/entities/Player"
        ],
        "Unit": [
            "/Testfield/resources/yunamagedon/src/entities/Unit"
        ],
        "Soldier": [
            "/Testfield/resources/yunamagedon/src/entities/Soldier"
        ],
        "Archer": [
            "/Testfield/resources/yunamagedon/src/entities/Archer"
        ],
        "King": [
            "/Testfield/resources/yunamagedon/src/entities/King"
        ],
        "Projectile": [
            "/Testfield/resources/yunamagedon/src/entities/projectiles/Projectile"
        ],
        "Game": [
            "/Testfield/resources/yunamagedon/src/Game"
        ],
        "gameUtil": [
            "/Testfield/resources/yunamagedon/src/util/gameUtil"
        ],
        "Component": [
            "/Testfield/resources/yunamagedon/src/components/Component"
        ],
        "Body": [
            "/Testfield/resources/yunamagedon/src/components/Body"
        ],
        "Sensor": [
            "/Testfield/resources/yunamagedon/src/components/Sensor"
        ],
        "Skill": [
            "/Testfield/resources/yunamagedon/src/components/Skill"
        ],
        "grid": [
            "/Testfield/resources/yunamagedon/src/grid/grid"
        ]
    },
    "shim": {
        "yunamagedon": {
            deps: [ "jQuery", "pixi", "Game", "grid" ]
        },
        "Game": {
            deps: [
                "Player", "Soldier", "Archer", "gameUtil", "Projectile", "King"
            ]
        },
        "Soldier": {
            deps: [
                "Unit"
            ]
        },
        "Archer": {
            deps: [
                "Unit"
            ]
        },
        "King": {
            deps: [
                "Entity"
            ]
        },
        "Projectile": {
            deps: ["Entity"]
        },
        "Entity": {
            deps: [ "Component", "Body", "Sensor", "Skill" ]
        },
        "Body": {
            deps: [ "Component" ]
        },
        "Sensor": {
            deps: [ "Component" ]
        },
        "Skill": {
            deps: [ "Component" ]
        },
        "Player": {
            deps: [ "Entity" ]
        },
        "Unit": {
            deps: [ "Entity" ]
        }
    }
});

require(["yunamagedon"]);

