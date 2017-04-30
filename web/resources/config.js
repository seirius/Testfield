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
        "testfield-general": "./testfield_general/js/testfield-general",
        "testfield-files": "./testfield_files/js/testfield-files",
        "file-service": "./testfield_files/js/fileService",
        "testfield-files-specific": "./testfield_files/js/testfield-files-specific",
        "chose-app": "./testfield_general/js/chose-app",
        "summernote": "./summernote/summernote",
        "summernote-util": "./summernote/summernote_util",
        "angular-sanitize": [
            "https://code.angularjs.org/1.2.20/angular-sanitize.min",
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
            deps: [ "angular-route" ]
        },
        "testfield-general": {
            deps: [ "angular" ]
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
        "testfield-files": {
            deps: [ "testfield-general", "angular-route" ]
        },
        "file-service": {
            deps: [ "testfield-files" ]
        },
        "testfield-files-specific": {
            deps: [ "file-service", "bootstrap" ]
        },
        "util": {
            deps: [ "jQuery" ]
        },
        "testfield-manuals": {
            deps: [ "angular-sanitize", "angular-route", "summernote-util",
                "util", "bootstrap-slider", "style-service"
            ]
        },
        "bootstrap": {
            deps: [ "jQuery" ]
        },
        "bootstrap-slider": {
            deps: [ "bootstrap" ]
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