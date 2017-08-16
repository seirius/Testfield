angular.module("loginApp", ["ui.bootstrap"]);
var loginApp = angular.module("loginApp");

loginApp.controller("loginController", function ($scope, $http) {
    
    $scope.userNick = "";
    $scope.password = "";
    
    $scope.submitLogin = function () {
//        var data = {
//            "userNick": this.userNick,
//            "password": this.password
//        };
//        
//        var request = $http.post("/Testfield/request/login", data);
//                
//        request.success(function (data, status, headers, config) {
//            console.log(data);
//        });
//        request.error(function (data, status, headers, config) {
//            console.log(data);
//        });
        $http({
            method: "POST",
            url: "/Testfield/request/login",
            data: $.param({
                userNick: $scope.userNick,
                password: $scope.password
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(function (response) {
            console.log(response);
        }, function (response) {
            console.log(response);
        });
    };
    
    
});

loginApp.directive("lengthVerify", function () {
    return {
        require: "ngModel",
        scope: {
            minLengthV: "="
        },
        link: function (scope, element, attrs, ctrl) {
            function myValidation(value) {
                if (value.length > scope.minLengthV) {
                    ctrl.$setValidity("lengthOk", true);
                } else {
                    ctrl.$setValidity("lengthOk", false);
                }
                return value;
            }
            ctrl.$parsers.push(myValidation);
        }
    };
});