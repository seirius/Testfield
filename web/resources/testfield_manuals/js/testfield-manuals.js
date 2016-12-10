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
    
    $scope.pageSelected = function () {
        
    };
});

app.controller("manualListCtrl", function ($rootScope, $scope, ManualService) {
    $scope.manuals = ManualService.getManualsList();
    
    $scope.openManual = function (idManual) {
        $rootScope.$broadcast("close-manual-list");
        ManualService.openManual(idManual, $scope, $(".manualContainer"));
    };
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

app.controller("manualBlockCtrl", function ($scope, $rootScope, ManualService) {
    var keyups = 0;
    $scope.keyup = function () {
        keyups++;
        if (keyups > 10) {
            keyups = 0;
            $scope.save();
        }
    };
    
    $scope.save = function () {
        ManualService.saveBlock($scope.block.id, $scope.block.content).then(function (data) {
            console.log(data);
        });
    };
    
    $scope.blockSelected = function () {
        $rootScope.$broadcast("block-selected", $scope.block);
    };
    
    $scope.initBlock = function (block) {
        $scope.block = block;
    };
    
    $scope.delete = function () {
        ManualService.deleteBlock($scope.block.id).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.moveLeft = function () {
        ManualService.moveBlockBackward($scope.block.id).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.moveRight = function () {
        ManualService.moveBlockFoward($scope.block.id).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
});

app.controller("pageCtrl", function ($scope, $rootScope, ManualService) {
    $scope.pageSelected = function () {
        $rootScope.$broadcast("page-selected", $scope.page);
    };
    
    $scope.initPage = function (page) {
        $scope.page = page;
    };
    
    $scope.delete = function () {
        ManualService.deletePage($scope.page.id).then(function () {
            ManualService.reloadManual($scope);
        });
    };
});

app.controller("rowCtrl", function ($scope, $rootScope, ManualService) {
    $scope.rowSelected = function () {
        $rootScope.$broadcast("row-selected", $scope.row);
    };
    
    $scope.initRow = function (row) {
        $scope.row = row;
    };
    
    $scope.delete = function () {
        ManualService.deleteRow($scope.row.id).then(function () {
            ManualService.reloadManual($scope);
        });
    };
});

app.directive("blockOptions", function ($templateRequest, $compile) {
    var blockOptions = {
        restrict: "A",
        link: function (scope, element, attrs) {
            var direction = attrs.blockOptions;
            var $element = $(element);
            $templateRequest("static/htmlParts/manuals/blockOptions.html").then(function (html) {
                $element.prepend($compile(html)(scope));
                var $deleteIcon = $element.find(".delete");
                var $arrowLeft = $element.find(".arrows-left");
                var $arrowRight = $element.find(".arrows-right");
                var $arrowUp = $element.find(".arrows-up");
                var $arrowDown = $element.find(".arrows-down");
                $element.hover(
                    function () {
                        $deleteIcon.removeClass("hidden");
                        if (direction.indexOf("vertical") > -1) {
                            $arrowLeft.removeClass("hidden");
                            $arrowRight.removeClass("hidden");
                        }
                        
                        if (direction.indexOf("horizontal") > -1) {
                            $arrowUp.removeClass("hidden");
                            $arrowDown.removeClass("hidden");
                        }
                    },
                    function () {
                        $deleteIcon.addClass("hidden");
                        if (direction.indexOf("vertical") > -1) {
                            $arrowLeft.addClass("hidden");
                            $arrowRight.addClass("hidden");
                        }
                        if (direction.indexOf("horizontal") > -1) {
                            $arrowUp.addClass("hidden");
                            $arrowDown.addClass("hidden");
                        }
                    }
                );
            });
        }
    };
    
    return blockOptions;
});

app.directive("blockClass", function () {
    var blockClass = {
        restrict: "A",
        link: function (scope, element, attrs) {
            var $element = $(element);
            var classes = getClassesByWidthTypes(scope.block.widthTypes, scope.block.relBlockWidthTypes);
            classes.forEach(function (value) {
                $element.addClass(value);
            });
        }
    };
    
    return blockClass;
});

function getClassesByWidthTypes(widthTypes, relWidthTypes) {
    var classes = [];
    
    widthTypes.forEach(function (widthType) {
        
        relWidthTypes.some(function (relWidthType) {
            
            if (widthType.widthType === relWidthType.id.widthType) {
                classes.push(getClassByWidthType(widthType, relWidthType));
            }
            
        });
        
    });
    
    return classes;
}

function getClassByWidthType(widthType, relWidthType) {
    var classAux = "";
    switch (widthType.description) {
        
        case "Phones":
            classAux = "col-xs-";
            break;
        
        case "Tablets":
            classAux = "col-sm-";
            break;
            
        case "Default":
            classAux = "col-md-";
            break;
            
        case "Large display":
            classAux = "col-lg-";
            break;
        
        default:
            throw "Width Type not supported.";
    }
    
    return classAux + relWidthType.amount;
}