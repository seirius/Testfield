var app = angular.module("manualsTestfield", ["generalTestfield"]);

app.controller("manualContainerCtrl", function ($scope, $compile, ManualService) {
    $scope.$on("load-manual", function (event, args) {
        ManualService.openManual(args.idManual, $scope, $(".manualContainer"));
    });
});

app.controller("manualPageCtrl", function ($scope, ManualService) {
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

    $scope.addElement = function () {
        var widthTypes = [];
        
        if ($scope.blockSize.xs > 0) {
            widthTypes.push({
                widthTypeId: 1,
                amount: $scope.blockSize.xs
            });
        }
        if ($scope.blockSize.sm > 0) {
            widthTypes.push({
                widthTypeId: 2,
                amount: $scope.blockSize.sm
            });
        }
        if ($scope.blockSize.md > 0) {
            widthTypes.push({
                widthTypeId: 3,
                amount: $scope.blockSize.md
            });
        }
        if ($scope.blockSize.lg > 0) {
            widthTypes.push({
                widthTypeId: 4,
                amount: $scope.blockSize.lg
            });
        }
        
        ManualService.addBlock({
            idRow: $scope.row.id,
            widthTypes: widthTypes,
            blockOrder: 0
        }).then(function () {
            ManualService.reloadManual($scope);
        });
    };
});

app.directive("blockOptions", function ($templateRequest, $compile) {
    var blockOptions = {
        restrict: "A",
        link: function (scope, element, attrs) {
            var options = attrs.blockOptions;
            var $element = $(element);
            $templateRequest("static/htmlParts/manuals/blockOptions.html").then(function (html) {
                $element.prepend($compile(html)(scope));
                var $deleteIcon = $element.find(".delete");
                var $arrowLeft = $element.find(".arrows-left");
                var $arrowRight = $element.find(".arrows-right");
                var $arrowUp = $element.find(".arrows-up");
                var $arrowDown = $element.find(".arrows-down");
                var $addElement = $element.find(".add-element");
                
                var hoverOptions = function (inside) {
                    blockOptionsHover({
                        types: [
                            {
                                option: "delete",
                                elements: [$deleteIcon]
                            },
                            {
                                option: "vertical",
                                elements: [$arrowLeft, $arrowRight] 
                            },
                            {
                                option: "horizontal",
                                elements: [$arrowUp, $arrowDown]
                            },
                            {
                                option: "add",
                                elements: [$addElement]
                            }
                        ],
                        options: options
                    }, inside);
                };

                $element.hover(
                        function () {
                            hoverOptions(true);
                        },
                        function () {
                            hoverOptions(false);
                        }
                );

                $templateRequest("static/htmlParts/manuals/popoverBlocks.html").then(
                    function (html) {
                        var popoverContent = $compile(html)(scope);
                        $addElement.popover({
                            content: popoverContent,
                            placement: "left",
                            html: true
                        });
                        
                        var clickSetted = false;
                        $addElement.on("shown.bs.popover", function () {
                            var $addButton = popoverContent.find("[name='addElement']");

                            if (!clickSetted) {
                                popoverContent.find("[name='blockSize']").each(function () {
                                    var $thisElement = $(this);
                                    $thisElement.slider({
                                        min: 0,
                                        max: 12,
                                        step: 2,
                                        value: 0
                                    });
                                    $thisElement.on("slide", function (ui) {
                                        $thisElement.val(ui.value).trigger("input");
                                    });
                                });
                                $addButton.click(function () {
                                    $addElement.click();
                                });
                                clickSetted = true;
                            }
                        });
                    }
                );

            });
        }
    };

    return blockOptions;
});

function blockOptionsHover(args, inside) {
    args.types.forEach(function (type) {
        if (args.options.indexOf(type.option) > -1) {
            type.elements.forEach(function (element) {
                if (inside) {
                    element.removeClass("hidden");
                } else {
                    element.addClass("hidden");
                }
            });
        }
    });
}

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