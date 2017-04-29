
define(function () {
    var loginModule = angular.module("loginModule", ["ngRoute", "generalTestfield"]);
    
    loginModule.config(function ($routeProvider) {
        $routeProvider
        .when("/", {
            templateUrl: "static/htmlParts/login/loginPart.html",
//            css: loginCss,
            controller: "loginCtrl"
        })
        .when("/register", {
            templateUrl: "static/htmlParts/login/registerPart.html",
            controller: "registerCtrl"
        });
    });

    loginModule.controller("loginCtrl", function ($scope, $window, 
                            UserService, tfHttp, $location) {

        $scope.submitLogin = function () {
            if ($scope.login.user.$invalid) {
                $scope.errorMessage = "User must be at least 3-30 characters";
            } else if ($scope.login.password.$invalid) {
                $scope.errorMessage = "Password must be at least 5-30 characters";
            } else {
                tfHttp.showError();
                var login = UserService.login($scope.user, $scope.password);
                login.then(function (data) {
                    if (data.loginOk) {
                        $window.location.href = "/Testfield/choseApp";
                    }
                }, function (data) {
                    $scope.errorMessage = data.errorMsg;
                });
            }

        };
        $scope.goRegister = function () {
            $location.path("/register");
        };

    });

    loginModule.controller("registerCtrl", function ($scope, UserService, $location) {

        $scope.submitRegister = function () {

            $scope.$broadcast("show-errors-event");

            if ($scope.register.$invalid) {
                return;
            }

            var userCreation = UserService.createUser($scope.user, $scope.password, $scope.email);
            userCreation.then(function (response) {
                console.log(response);
            });

        };

        $scope.goLogin = function () {
            $location.path("/");
        };

    });
});
