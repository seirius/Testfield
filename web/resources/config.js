require.config({
    paths: {
        "jQuery": [
            "https://code.jquery.com/jquery-1.12.0.min", 
            "js/jquery/jquery"
        ],
        "angular": [
            "https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min",
            "js/angularjs/angular"
        ],
        "angular-route": [
            "https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.1/angular-route.min",
            "js/angularjs/angular-route"
        ],
        "login-main": "testfield_general/js/login-main",
        "login-main-m": "testfield_general/js/login-main",
        "testfield-general": "./testfield_general/js/testfield-general",
        "testfield-files": "./testfield_files/js/testfield-files",
        "file-service": "./testfield_files/js/fileService",
        "testfield-files-specific": "./testfield_files/js/testfield-files-specific",
        "chose-app": "./testfield_general/js/chose-app",
        "summernote": "./summernote/summernote",
        "summernote-util": "./summernote/summernote_util",
        "angular-sanitize": [
//            "https://code.angularjs.org/1.2.20/angular-sanitize.min",
            "./js/angularjs/angular-sanitize"
        ],
        "testfield-manuals": "./testfield_manuals/js/testfield-manuals",
        "manuals-service": "./testfield_manuals/js/manualService",
        "style-service": "./testfield_general/js/styleService",
        "ui-bootstrap": [
            "https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.1.3/ui-bootstrap.min",
            "./js/angularjs/uiBootstrap"
        ],
        "util": "./testfield_general/js/util",
        "bootstrap": [
            "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min",
            "./bootstrap/js/bootstrap"
        ],
        "bootstrap-slider": [
            "./bootstrap/slider/bootstrap-slider"
        ],
        "masonry": [
            "./js/masonry/masonry"
        ],
        "form-service": [
            "./testfield_general/js/forms/formService"
        ],
        "materialize": [
            "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.2/js/materialize.min",
            "resources/materialize/js/materialize.min"
        ],
        "angular-materialize": [
            "https://cdnjs.cloudflare.com/ajax/libs/angular-materialize/0.2.2/angular-materialize.min"
        ],
        "hammerjs": [
            "./hammerjs/hammer"
        ]
    },
    shim: {
        jQuery: {
            exports: "$"
        },
        angular: {
            deps: [ "jQuery" ]
        },
        "angular-route": {
            deps: [ "angular", "testfield-general" ]
        },
        "login-main": {
            deps: [ "angular-route", "form-service" ]
        },
        "login-main-m": {
            deps: [ "angular-route", "form-service" ]
        },
        "hammerjs": {
            deps: ["angular"]
        },
        "materialize": {
            deps: ["hammerjs", "angular-materialize"]
        },
        "angular-materialize": {
            deps: ["angular"]
        },
        "testfield-general": {
            deps: [ "angular", "materialize" ]
        },
        "chose-app": {
            deps: [ "angular-route" ]
        },
        "summernote": {
            deps: [ "jQuery" ]
        },
        "summernote-util": {
            deps: [ "summernote", "manuals-service", "file-service" ]
        },
        "angular-sanitize": {
            deps: [ "angular" ]
        },
        "manuals-service": {
            deps: [ "testfield-general" ]
        },
        "style-service": {
            deps: [ "testfield-general" ]
        },
        "ui-bootstrap": {
            deps: [ "angular" ]
        },
        "file-service": {
            deps: [ "testfield-files", "masonry" ]
        },
        "testfield-files-specific": {
            deps: [ "file-service", "bootstrap" ]
        },
        "util": {
            deps: [ "jQuery" ]
        },
        "form-service": {
            deps: [
                "angular", "testfield-general"
            ]
        },
        "testfield-manuals": {
            deps: [ "angular-sanitize", "angular-route", "summernote-util",
                "util", "bootstrap-slider", "style-service", "form-service"
            ]
        },
        "bootstrap": {
            deps: [ "jQuery" ]
        },
        "bootstrap-slider": {
            deps: [ "bootstrap" ]
        },
        "testfield-files": {
            deps: [ "testfield-general", "angular-route" ]
        }
    }
});

require(["jQuery"], function () {
    var $script = $("script[data-main]");
    var module = $script.data("module");
    var dependency = $script.data("dependency");
    require([dependency], function () {
        angular.bootstrap(document, [module]);
    });
});