var app = angular.module("mainApp", ["generalTestfield"]);

app.controller("mainLogin", function ($scope) {
});

app.controller("loginCtrl", function ($scope, $window, UserService, tfHttp) {
    
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
        $scope.$parent.$parent.loginView = 'part/register';
    };
    
});

app.controller("registerCtrl", function ($scope, UserService) {
    
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
        $scope.$parent.$parent.loginView = 'part/login';
    };
    
});

app.directive("showErrors", function () {
    return {
        link: function (scope, element) {
            var input = element.find("[name]");
            var inputName = input.attr("name");
            
            input.blur(function () {
                var formInput = scope.register[inputName];
                var gotError = formInput.$invalid && formInput.$touched && formInput.$dirty;
                element.toggleClass("has-error", gotError);
            });
            
            scope.$on("show-errors-event", function () {
                var formInput = scope.register[inputName];
                var gotError = formInput.$invalid;
                element.toggleClass("has-error", gotError);
            });
        }
    };
});

app.directive("checkPasswords", function () {
    return {
        require: "ngModel",
        link: function (scope, element, attrs, ctrl) {
            var firstPassword = element.closest("form").find("[name='" + attrs.checkPasswords + "']");
            
            element.keyup(function () {
                var valid = element.val() === firstPassword.val();
                ctrl.$setValidity("matchpw", valid);
            });
        }
    };
});
