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
        "Game": [
            "/Testfield/resources/yunamagedon/src/Game"
        ],
        "gameUtil": [
            "/Testfield/resources/yunamagedon/src/util/gameUtil"
        ],
        "Component": [
            "/Testfield/resources/yunamagedon/src/components/Component"
        ]
    },
    "shim": {
        "yunamagedon": {
            deps: [ "jQuery", "pixi", "Game" ]
        },
        "Game": {
            deps: [
                "Player", "Unit", "gameUtil"
            ]
        },
        "Entity": {
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

