/* global manualsTestfield, URL */

manualsTestfield.controller("navbarManualsCtrl", function ($scope, $rootScope, 
        ManualService, $location, ModalService, FilesService) {
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
        $location.search("visualize", null);
        $location.search();
    };
    
    $scope.visualize = function () {
        $location.search("visualize", "true");
        $location.search();
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
    
    $scope.openImageList = function () {
        $location.search("visualize", null);
        $location.search("id", null);
        $location.search("files");
        $location.search();
        $scope.manualLoaded = false;
        $scope.editing = false;
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

