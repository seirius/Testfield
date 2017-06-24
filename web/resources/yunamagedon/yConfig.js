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
        "Aang": [
            "/Testfield/resources/yunamagedon/src/entities/Aang"
        ]
    },
    "shim": {
        "yunamagedon": {
            deps: [ "jQuery", "pixi", "p2" ]
        },
        "Aang": {
            deps: [ "Entity" ]
        }
    }
});

require(["yunamagedon"]);

