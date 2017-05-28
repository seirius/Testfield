
define(function () {
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

    loginModule.controller("loginCtrl", function ($scope, $window, 
                            UserService, tfHttp, $location, FormService) {
        FormService.requestForm({
            form: "loginForm"
        }).then(function (response) {
            $scope.form = response.data.form;
        });

        $scope.submitLogin = function (data) {
            if (data.errorCode === 0) {
                $window.location.href = "/Testfield/choseApp";
            }
//            if ($scope.login.user.$ invalid) {
//                $scope.errorMessage = "User must be at least 3-30 characters";
//            } else if ($scope.login.password.$invalid) {
//                $scope.errorMessage = "Password must be at least 5-30 characters";
//            } else {
//                tfHttp.showError();
//                var login = UserService.login($scope.user, $scope.password);
//                login.then(function (data) {
//                    if (data.loginOk) {
//                        $window.location.href = "/Testfield/choseApp";
//                    }
//                }, function (data) {
//                    $scope.errorMessage = data.errorMsg;
//                });
//            }

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
