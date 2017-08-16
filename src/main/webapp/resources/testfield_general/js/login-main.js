
var loginModule = angular.module("loginModule", ["ngRoute", "generalTestfield"]);

loginModule.config(function ($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl: "static/htmlParts/login/loginPartM.html",
        controller: "loginCtrl"
    })
    .when("/register", {
        templateUrl: "static/htmlParts/login/registerPartM.html",
        controller: "registerCtrl"
    });
});

loginModule.controller("loginCtrl", function ($scope, $window, $location, FormService) {
    $scope.showLogin = false;
    FormService.requestForm({
        formName: "loginForm"
    }).then(function (response) {
        $scope.form = response.data.form;
    });
    $scope.formRendered = function () {
        $scope.showLogin = true;
    };
    
    $scope.submitLogin = function (data) {
        if (data.errorCode === 0) {
            $window.location.href = "/Testfield/choseApp";
        }
    };
    $scope.goRegister = function () {
        $location.path("/register");
    };

});

loginModule.controller("registerCtrl", ["$scope", "UserService", "$location", 
    "FormService", function ($scope, UserService, $location, FormService) {
        $scope.showRegister = false;
        $scope.showSuccess = false;
        FormService.requestForm({
            formName: "registerForm"
        }).then(function (response) {
            $scope.form = response.data.form;
        });
        
        $scope.formRendered = function () {
            $scope.showRegister = true;
        };

        $scope.submitRegister = function (data) {
            if (data.errorCode === 0) {
                $scope.showSuccess = true;
            }
        };

        $scope.goLogin = function () {
            $location.path("/");
        };

    }]);
