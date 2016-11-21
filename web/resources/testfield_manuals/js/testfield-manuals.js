var app = angular.module("manualsTestfield", ["generalTestfield"]);

app.controller("manualContainerCtrl", function ($scope, $compile, ManualService) {
    $scope.$on("load-manual", function (event, args) {
        ManualService.openManual(args.idManual, $scope, $(".manualContainer"));
    });
});

app.controller("manualPageCtrl", function($scope, ManualService) {
    var manual = ManualService.getCurrentManual();
    $scope.manual = manual;
    
    var keyups = 0;
    $scope.keyup = function () {
        keyups++;
        if (keyups > 10) {
            keyups = 0;
            $scope.save();
        }
    };
    
    $scope.save = function () {
        ManualService.saveTitle({
            newTitle: $scope.manual.title
        }).then(function (data) {
            console.log(data);
        });
    };
});

app.controller("manualListCtrl", function ($rootScope, $scope, ManualService) {
    $scope.manuals = ManualService.getManualsList();
    
    $scope.openManual = function (idManual) {
        $rootScope.$broadcast("close-manual-list");
        ManualService.openManual(idManual, $scope, $(".manualContainer"));
    };
});

app.service("ManualService", function (tfHttp, $compile) {
    var manualService = this;
    
    var currentManual = {};
    var manualsList = [];
    
    manualService = {
        createManual: function () {
            return tfHttp.request({
                url: "/Testfield/request/manual/createManual"
            });
        },
        
        loadManual: function (idManual) {
            return tfHttp.request({
                url: "/Testfield/request/manual/loadManual",
                data: {
                    id: idManual
                }
            });
        },
        
        loadManuals: function () {
            return tfHttp.request({
                url: "/Testfield/request/manual/loadManuals"
            });
        },
        
        openManualsList: function ($scope) {
            manualService.loadManuals().then(function (data) {
                manualService.setManualsList(data.manuals);
                var $modalList = $("<tf-modal>", {
                    "url-content": "/Testfield/static/htmlParts/manuals/manualList.html"
                });
                
                $modalList = $compile($modalList)($scope);
                $("body").append($modalList);
            });
        },
        
        openManual: function (idManual, $scope, container) {
            manualService.loadManual(idManual).then(function (data) {
                manualService.setCurrentManual(data.manual);
                var manualPageDir = $compile($("<manual-page>"))($scope);
                container.empty().append(manualPageDir);
            });
        },
        
        setCurrentManual: function (manual) {
            currentManual = manual;
        },
        
        getCurrentManual: function () {
            return currentManual;
        },
        
        setManualsList: function (manuals) {
            manualsList = manuals;
        },
        
        getManualsList: function () {
            return manualsList;
        },
        
        save: function (type, content) {
            console.log(content);
        },
        
        saveTitle: function (args) {
            args = $.extend(args, {
                idManual: currentManual.id,
                newTitle: currentManual.title
            });
            var request = tfHttp.request({
                url: "/Testfield/request/manual/setTitle",
                data: {
                    idManual: args.idManual,
                    newTitle: args.newTitle
                }
            });
            
            return request.then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    resolve(data);
                });
            });
        },
        
        saveBlock: function (manualBlock) {
            return tfHttp.request({
                url: "/Testfield/request/manual/saveManualBlock",
                data: {
                    manualBlock: manualBlock
                }
            }).then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    resolve(data);
                });
            });
        }
    };
    
    return manualService;
});

app.directive("manualPage", function () {
    return {
        restrict: "E",
        templateUrl: "static/htmlParts/manuals/manualPagePart.html"
    };
});

app.directive("tfEditableElement", function () {
    var tfEditable = {
        restrict: "A",
        link: function (scope, element, attrs) {
            var $textarea = element.closest("form").find("textarea");
            
            element.dblclick(function () {
                $textarea.removeClass("hidden");
                $textarea.focus();
                element.addClass("hidden");
            });
            
        }
    };
    
    return tfEditable;
});

app.directive("tfEditableTextarea", function () {
    var tfEditable = {
        restrict: "A",
        link: function (scope, element, attrs) {
            var $oldElement = element.closest("form").find("[tf-editable-element]");
            
            element.blur(function () {
                $oldElement.removeClass("hidden");
                $oldElement.focus();
                element.addClass("hidden");
            });
        }
    };
    
    return tfEditable;
});

app.controller("manualBlockCtrl", function ($scope, ManualService) {
    var keyups = 0;
    $scope.keyup = function () {
        keyups++;
        if (keyups > 10) {
            keyups = 0;
            $scope.save();
        }
    };
    
    $scope.save = function () {
        ManualService.saveBlock($scope.manualBlock).then(function (data) {
            console.log(data);
        });
    };
});