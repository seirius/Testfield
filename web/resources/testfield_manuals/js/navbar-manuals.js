var app = angular.module("manualsTestfield");

app.controller("navbarManualsCtrl", function ($scope, $rootScope, ManualService) {
    $scope.createManual = function () {
        ManualService.createManual().then(function (data) {
            $rootScope.$broadcast("load-manual", {
                idManual: data.manual.id
            });
        });
    };
    
    $scope.editing = true;
    $scope.$on("manual-loaded", function () {
        var viewState = ManualService.getCurrentManual().viewState;
        $scope.editing = viewState === ManualService.VIEW_STATE.EDIT;
    });
    
    $scope.manualsList = function () {
        ManualService.openManualsList($scope);
    };
    
    $scope.edit = function () {
        ManualService.reloadManual($scope);
    };
    
    $scope.visualize = function () {
        ManualService.visualizeManual($scope);
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

