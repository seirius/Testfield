var app = angular.module("mainApp", ["generalTestfield"]);

app.controller("loginCtrl", function ($scope, $window, UserService) {
    
    $scope.submitLogin = function () {
        var login = UserService.login($scope.user, $scope.password);
        login.then(function (response) {
            if (response.data.data.loginOk) {
                $window.location.href = "/Testfield/choseApp";
            } else {
                $scope.errorMessage = response.data.errorMsg;
                $scope.showError = true;
            }
        });
    };

    $scope.closeAlert = function () {
        $scope.showError = false;
    };
    
});

app.service("UserService", function (tfHttp) {
    return {
        login: function (user, password) {
            return tfHttp.request({
                method: "POST",
                url: "/Testfield/request/login",
                data: {
                    userNick: user,
                    password: password
                }
            });
        }
    };
});



//$(document).ready(function () {
//    
//    var form = $("#form-login");
//    form.validate({
//        submitHandler: function () {
//            form.ajaxSubmit({
//                dataType: "json",
//                beforeSend: function () {
//                    $("body").loadingState();
//                },
//                success: function (response) {
//                    $("body").loadingState("destroy");
//                    if (response.data.loginOk) {
//                        window.location = "/Testfield/choseApp";
//                    } else {
//                        console.log(response.errorMsg);
//                    }
//                },
//                error: function (error) {
//                    treatException(error);
//                }
//            });
//        },
//        rules: {
//            user: {
//                required: true,
//                minlength: 2,
//                maxlength: 20
//            },
//            password: {
//                required: true,
//                minlength: 2,
//                maxlength: 30
//            }
//        }
//    });
//});

