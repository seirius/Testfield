/* global manualsTestfield, URL */

manualsTestfield.controller("navbarManualsCtrl", function ($scope, $rootScope, 
        ManualService, $location, $window) {
    $scope.manualLoaded = false;
    
    $scope.createManual = function () {
        ManualService.createManual().then(function (data) {
            $location.search("id", data.manual.id);
        });
    };
    
    $scope.editing = true;
    $rootScope.$on("manual-loaded", function () {
        var viewState = ManualService.getCurrentManual().viewState;
        $scope.editing = viewState === ManualService.VIEW_STATE.EDIT;
        $scope.manualLoaded = $scope.editing;
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
    
    $scope.addPage = function () {
        ManualService.addPage(ManualService.getCurrentManual().id).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.manualsStyle = function () {
        ManualService.openManualsStyle($scope);
    };
    
    $scope.getJsonManual = function () {
        ManualService.getJsonManual(ManualService.getCurrentManual().id)
        .then(function (data) {
            var jsonManual = new Blob([data.jsonManual], {
                type: "application/json"
            });
            var jsonUrl = URL.createObjectURL(jsonManual);
            window.open(jsonUrl);
        });
    };
    
    $scope.getHtmlManual = function () {
        ManualService.getHtmlManual(ManualService.getCurrentManual().id);
    };
});

manualsTestfield.directive("logout", function (UserService, Testfield) {
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

