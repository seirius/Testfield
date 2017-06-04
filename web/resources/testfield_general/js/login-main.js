
var loginModule = angular.module("loginModule", ["ngRoute", "generalTestfield"]);

loginModule.config(function ($routeProvider) {
    $routeProvider
            .when("/", {
                templateUrl: "static/htmlParts/login/loginPart.html",
                controller: "loginCtrl"
            })
            .when("/register", {
                templateUrl: "static/htmlParts/login/registerPart.html",
                controller: "registerCtrl"
            });
});

loginModule.controller("loginCtrl", function ($scope, $window, $location, FormService) {
    FormService.requestForm({
        formName: "loginForm"
    }).then(function (response) {
        $scope.form = response.data.form;
    });

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
        FormService.requestForm({
            formName: "registerForm"
        }).then(function (response) {
            $scope.form = response.data.form;
        });

        $scope.submitRegister = function () {

//            $scope.$broadcast("show-errors-event");
//
//            if ($scope.register.$invalid) {
//                return;
//            }
//
//            var userCreation = UserService.createUser($scope.user, $scope.password, $scope.email);
//            userCreation.then(function (response) {
//                console.log(response);
//            });

        };

        $scope.goLogin = function () {
            $location.path("/");
        };

    }]);
