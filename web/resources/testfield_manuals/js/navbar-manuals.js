var app = angular.module("manualsTestfield");

app.controller("navbarManualsCtrl", function ($scope, $rootScope, ManualService) {
    $scope.createManual = function () {
        ManualService.createManual().then(function (data) {
            $rootScope.$broadcast("load-manual", {
                idManual: data.manual.id
            });
        });
    };
    
    $scope.manualsList = function () {
        ManualService.openManualsList($scope);
    };
});

app.directive("logout", function (UserService, Testfield) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.click(function () {
                UserService.logout().then(function (data) {
                    if (data.logoutOk) {
                        Testfield.goAfterLogout();
                    } else {
                        alert("Coldnt't logout.");
                    }
                });
            });
        }
    };
});

