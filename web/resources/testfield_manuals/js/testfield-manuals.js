/* global UTIL, summer_util */

var manualsTestfield = angular.module("manualsTestfield", ["generalTestfield", "ngSanitize"]);
manualsTestfield.config(function ($locationProvider) {
    $locationProvider.html5Mode(true);
});

manualsTestfield.controller("manualContainerCtrl", function ($scope, $location, ManualService) {
    var loadManualByGet = function () {
        var id = parseInt($location.search().id);
        if (!isNaN(id)) {
            ManualService.openManual(id, $scope, $(".manualContainer"));
        }
    };
    
    $scope.$watch(function () {
        return $location.search();
    }, function () {
        loadManualByGet();
    }, true);
    $location.search();
});

manualsTestfield.controller("manualPageCtrl", function ($scope, ManualService) {
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

manualsTestfield.controller("manualListCtrl", function ($rootScope, $scope, ManualService, $location) {
    $scope.manuals = ManualService.getManualsList();

    $scope.openManual = function (idManual) {
        $rootScope.$broadcast("close-tf-modal");
        $location.search("id", idManual);
    };
});

manualsTestfield.directive("manualPage", function () {
    return {
        restrict: "E",
        templateUrl: "static/htmlParts/manuals/manualPagePart.html"
    };
});

manualsTestfield.directive("manualView", function () {
    return {
        restrict: "E",
        templateUrl: "static/htmlParts/manuals/manualView.html"
    };
});

manualsTestfield.directive("editableBlock", function ($compile) {
    var editableBlock = {
        restrict: "A",
        scope: false,
        link: function (scope, element, attrs) {
            var $textSpan = element.find("span");
            var $textarea = element.find(".editable-textarea");
            var $editorPanel;
            scope.isEditing = false;
            var idCounter = 0;
            scope.startEditing = function () {
                if (!scope.isEditing) {
                    scope.isEditing = true;
                    var ownId = idCounter;
                    var onClose = function () {
                        scope.stopEditing();
                        scope.save();
                    };
                    element.addClass("manual-block-editing");
                    $textarea.summernote({
                        toolbar: summer_util.getToolbarForBlock,
                        buttons: {
                            close: summer_util.closeButton(onClose)
                        },
                        callbacks: {
                            onInit: function () {
                                var $noteEditor = $textarea.siblings(".note-editor");
                                $noteEditor.addClass("remove-panel-margin");
                                $noteEditor.find("button").tooltip('destroy'); 
                                var $aux = $("<summernote-edit-panel>", {
                                    attr: {
                                        onsuccess: "onLoadEditPanel"
                                    }
                                });
                                $editorPanel = $($compile($aux)(scope));
                                $(".principal-container").append($editorPanel);
                            },
                            onBlur: function (event) {
                                if (event.relatedTarget === null 
                                        || $(event.relatedTarget).closest(".note-toolbar").data("id-counter") !== ownId) {
                                    onClose();
                                }
                            }
                        }
                    });
                }
            };
            scope.onLoadEditPanel = function (data) {
                var $noteEditor = $textarea.siblings(".note-editor");
                var $noteToolbar = $noteEditor.find(".note-toolbar");
                $noteToolbar.data("id-counter", idCounter);
                $editorPanel.find(".summernote-edit-panel").append($noteToolbar);
                $noteToolbar.find(".dropdown-menu").closest(".note-btn-group").addClass("dropup");
                $noteEditor.find(".note-editable").focus();
            };
            scope.stopEditing = function () {
                if (scope.isEditing) {
                    scope.isEditing = false;
                    var originalValue = $textarea.summernote("code");
                    scope.manualBlock.manualBlockTrustedHtml = scope.$parent.$sce.trustAsHtml(originalValue);
                    scope.manualBlock.content = originalValue;
                    $textarea.summernote("destroy");
                    $editorPanel.remove();
                    element.removeClass("manual-block-editing");
                }
            };
        }
    };
    
    return editableBlock;
});

manualsTestfield.directive("summernoteEditPanel", function ($templateRequest) {
    return {
        restrict: "E",
        link: function (scope, element, attrs) {
            $templateRequest("static/htmlParts/manuals/summernoteEditPanel.html").then(function (data) {
                element.append(data);
                element.addClass("summernote-edit-panel-container");
                scope[attrs.onsuccess](data);
            });
        }
    };
});

manualsTestfield.directive("tfEditableElement", function () {
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

manualsTestfield.directive("tfEditableTextarea", function () {
    var tfEditable = {
        restrict: "A",
        link: function (scope, element, attrs) {
            var $oldElement = element.closest("form").find("[tf-editable-element]");
            
            element.resizeTextarea();
            element.blur(function () {
                $oldElement.removeClass("hidden");
                $oldElement.focus();
                element.addClass("hidden");
            });
        }
    };

    return tfEditable;
});

manualsTestfield.controller("manualBlockCtrl", function ($scope, $rootScope, ManualService, $sce) {
    $scope.showModButton = true;
    $scope.$sce = $sce;
    $scope.manualBlock.manualBlockTrustedHtml = $sce.trustAsHtml($scope.manualBlock.content);
    
    var loadBlockSize = function () {
        $scope.blockSize = MANSC.getBlockSizes($scope.block);
    };
    
    var keyups = 0;
    $scope.keyup = function () {
        keyups++;
        if (keyups > 10) {
            keyups = 0;
            $scope.save();
        }
    };

    $scope.save = function () {
        ManualService.saveBlock($scope.block.id, $scope.block.content);
    };

    $scope.blockSelected = function () {
        $rootScope.$broadcast("block-selected", $scope.block);
    };

    $scope.initBlock = function (block) {
        $scope.block = block;
        loadBlockSize();
    };
    
    $scope.popoverClosed = loadBlockSize;

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
    
    $scope.modifyElement = function () {
        ManualService.modifyBlockSize({
            idBlock: $scope.block.id,
            widthTypes: MANSC.getWidthTypes($scope.blockSize)
        }).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
});

manualsTestfield.controller("pageCtrl", function ($scope, $rootScope, ManualService) {
    $scope.clickEnabled = true;
    
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
    
    $scope.moveUp = function () {
        ManualService.movePage($scope.page.id, ManualService.MOVE_OPTIONS.UP).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.moveDown = function () {
        ManualService.movePage($scope.page.id, ManualService.MOVE_OPTIONS.DOWN).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.addElement = function () {
        ManualService.addRow({
            idPage: $scope.page.id
        }).then(function () {
            ManualService.reloadManual($scope);
        });
    };
});

manualsTestfield.controller("rowCtrl", function ($scope, $rootScope, ManualService) {
    $scope.showModButton = false;
    
    var resetBlockSize = function () {
        $scope.blockSize = {
            xs: 0,
            sm: 0,
            md: 0,
            lg: 0
        };
    };
    
    $scope.rowSelected = function () {
        $rootScope.$broadcast("row-selected", $scope.row);
    };

    $scope.initRow = function (row) {
        $scope.row = row;
        resetBlockSize();
    };

    $scope.delete = function () {
        ManualService.deleteRow($scope.row.id).then(function () {
            ManualService.reloadManual($scope);
        });
    };

    $scope.addElement = function () {
        ManualService.addBlock({
            idRow: $scope.row.id,
            widthTypes: MANSC.getWidthTypes($scope.blockSize),
            blockOrder: 0
        }).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.popoverClosed = resetBlockSize;
    
    $scope.moveUp = function () {
        ManualService.moveRow($scope.row.id, ManualService.MOVE_OPTIONS.UP)
        .then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.moveDown = function () {
        ManualService.moveRow($scope.row.id, ManualService.MOVE_OPTIONS.DOWN)
        .then(function () {
            ManualService.reloadManual($scope);
        });
    };

});

manualsTestfield.directive("blockOptions", function ($templateRequest, $compile) {
    var blockOptions = {
        restrict: "A",
        link: function (scope, element, attrs) {
            var options = attrs.blockOptions;
            var $element = $(element);
            
            $templateRequest("static/htmlParts/manuals/blockOptions.html").then(function (html) {
                MANSC.functionalityByType({
                    $element: $element,
                    $templateRequest: $templateRequest,
                    $scope: scope,
                    $compile: $compile,
                    html: html
                });
                
                MANSC.elementHover($element, options);
            });
        }
    };

    return blockOptions;
});

manualsTestfield.directive("blockClass", function () {
    var blockClass = {
        restrict: "A",
        link: function (scope, element, attrs) {
            var $element = $(element);
            var classes = MANSC.getClassesByWidthTypes(scope.block.widthTypes, 
                scope.block.relBlockWidthTypes);
            classes.forEach(function (value) {
                $element.addClass(value);
            });
        }
    };

    return blockClass;
});

manualsTestfield
        .controller(
            "manualStyleCtrl", 
            function ($scope, $rootScope, StyleService, ManualService) {
    
    var manual = ManualService.getCurrentManual();
    
    $scope.submitStyles = function () {
        if ($scope.manualStyleForm.$pristine ||
                $scope.manualStyleForm.$invalid) {
            alert("nop");
        } else {
            $rootScope.$broadcast("close-tf-modal");
            var args = $.extend({
                manualId: ManualService.getCurrentManual().id,
                R: 0,
                G: 0,
                B: 0,
                fontFamily: 0
            }, $scope.style);
            ManualService.updateStyles(args);
        }
    };
    $scope.style = {
        R: 0,
        G: 0,
        B: 0,
        fontFamily: ""
    };
    
    StyleService.getFontFamilies()
    .then(function (data) {
        $scope.fontFamilies = data.fontFamilies;
    });
});

manualsTestfield.controller("manualView", function ($scope, ManualService) {
    $scope.manual = ManualService.getCurrentManual();
});

var MANSC = (function () {
    
    function findElementByOption($element, elementOption) {
        var optionClass = "";
        var options = [
            "delete", 
            "vertical",
            "horizontal",
            "add",
            "modify"
        ];
        
        var addClassToOptions = function (clas) {
            optionClass += clas + ",";
        };
        
        options.forEach(function (option) {
            if (elementOption.indexOf(option) > -1) {
                switch(option) {
                    case "delete":
                        addClassToOptions(".delete");
                        break;

                    case "vertical":
                        addClassToOptions(".arrows-left");
                        addClassToOptions(".arrows-right");
                        break;

                    case "horizontal":
                        addClassToOptions(".arrows-up");
                        addClassToOptions(".arrows-down");
                        break;

                    case "add":
                        addClassToOptions(".add-element");
                        break;

                    case "modify":
                        addClassToOptions(".modify-element");
                        break;
                }
            }
        });
        
        if (optionClass.length > 0) {
            optionClass = optionClass.substring(0, optionClass.length - 1);
        }
        
        return $element.children(optionClass);
    }
    
    function elementPopover(args) {
        args.$templateRequest("static/htmlParts/manuals/popoverBlocks.html")
                .then(function (html) {
                    var $funElement = args.$element.find(args.classToFind);
                    var popoverContent = args.$compile(html)(args.$scope);
                    $funElement.popover({
                        content: popoverContent,
                        placement: "left",
                        html: true
                    });
                    
                    var getBlockSizes = function () {
                        return popoverContent.find("[name='blockSize']");
                    };

                    var clickSet = false;
                    $funElement.on("shown.bs.popover", function () {
                        var $button = popoverContent.find(args.nameToFind);
                        

                        if (!clickSet) {
                            getBlockSizes().each(function () {
                                var $thisElement = $(this);
                                var elementVal = $thisElement.val().length === 0 ? 0 : parseInt($thisElement.val());
                                $thisElement.slider({
                                    min: 0,
                                    max: 12,
                                    step: 2,
                                    value: elementVal
                                });
                                $thisElement.on("slide", function (ui) {
                                    $thisElement.val(ui.value).trigger("input");
                                });
                            });
                            $button.click(function () {
                                $funElement.click();
                            });
                            clickSet = true;
                        }
                    });
                    //Reset the values of the slider when is again opened.
                    $funElement.on("show.bs.popover", function () {
                        if (clickSet) {
                            getBlockSizes().each(function () {
                                var $thisElement = $(this);
                                var elementVal = $thisElement.val().length === 0 ? 0 : parseInt($thisElement.val());
                                $thisElement.slider("setValue", elementVal);
                            });
                        }
                    });
                    $funElement.on("hidden.bs.popover", function () {
                        args.$scope.popoverClosed();
                    });
                });
    }
    
    function pageFunctionality(args) {
        var $html = $("<div>").append(args.html);
        $html.find(".add-element").attr("ng-click", "addElement()");
        $html.find(".delete").attr("ng-click", "delete()");
        $html.find(".arrows-up").attr("ng-click", "moveUp()");
        $html.find(".arrows-down").attr("ng-click", "moveDown()");
        
        var htmlP = $html.html();
        args.$element.prepend(args.$compile(htmlP)(args.$scope));
    }
    
    function rowsFunctionality(args) {
        var $html = $("<div>").append(args.html);
        $html.find(".delete").attr("ng-click", "delete()");
        $html.find(".arrows-up").attr("ng-click", "moveUp()");
        $html.find(".arrows-down").attr("ng-click", "moveDown()");
        
        var htmlP = $html.html();
        args.$element.prepend(args.$compile(htmlP)(args.$scope));
        
        args = $.extend(args, {
            classToFind: ".add-element",
            nameToFind: "[name='addElement']"
        });
        elementPopover(args);
    }
    
    function blocksFunctionality(args) {
        var $html = $("<div>").append(args.html);
        $html.find(".delete").attr("ng-click", "delete()");
        $html.find(".arrows-left").attr("ng-click", "moveLeft()");
        $html.find(".arrows-right").attr("ng-click", "moveRight()");
        $html.find(".modify-element").attr("ng-click", "modify()");
        
        var htmlP = $html.html();
        args.$element.prepend(args.$compile(htmlP)(args.$scope));
        
        args = $.extend(args, {
            classToFind: ".modify-element",
            nameToFind: "[name='modifyElement']"
        });
        elementPopover(args);
    }
    
    var functions = {
        
        blockOptions: function ($element, options, showHide) {
            var $elementOptions = findElementByOption($element, options);
            $elementOptions.each(function (index, option) {
                var $option = $(option);
                var isLast = $element.hasClass("last");
                var isFirst = $element.hasClass("first");
                var isArrowFoward = $option.hasClass("arrows-right") || $option.hasClass("arrows-down");
                var isArrowBackward = $option.hasClass("arrows-left") || $option.hasClass("arrows-up");
                
                if (showHide && !(isLast && isArrowFoward) && !(isFirst && isArrowBackward)) {
                    $option.removeClass("hidden");
                } else {
                    $option.addClass("hidden");
                }
            });
        },
         
        blockOptionsHover: function (args, inside, $parentElement) {
            args.types.forEach(function (type) {
                if (args.options.indexOf(type.option) > -1) {
                    type.elements.forEach(function (element) {
                        var isLast = $parentElement.hasClass("last");
                        var isFirst = $parentElement.hasClass("first");
                        var isArrowFoward = element.hasClass("arrows-right") || element.hasClass("arrows-down");
                        var isArrowBackward = element.hasClass("arrows-left") || element.hasClass("arrows-up");

                        if (inside && !(isLast && isArrowFoward) && !(isFirst && isArrowBackward)) {
                            element.removeClass("hidden");
                        } else {
                            element.addClass("hidden");
                        }

                    });
                }
            });
        },
        
        getClassesByWidthTypes: function (widthTypes, relWidthTypes) {
            var classes = [];

            widthTypes.forEach(function (widthType) {

                relWidthTypes.some(function (relWidthType) {

                    if (widthType.widthType === relWidthType.id.widthType) {
                        classes.push(MANSC.getClassByWidthType(widthType, relWidthType));
                    }

                });

            });

            return classes;
        },
        
        getClassByWidthType: function (widthType, relWidthType) {
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
        },
        
        functionalityByType: function (args) {
            if (args.$element.hasClass("manual-row")) {
                rowsFunctionality(args);
            } else if (args.$element.hasClass("manual-block")) {
                blocksFunctionality(args);
            } else if (args.$element.hasClass("manual-page")) {
                pageFunctionality(args);
            }
        },
        
        getWidthTypes: function (blockSize) {
            var widthTypes = [];
            if (blockSize.xs > 0) {
                widthTypes.push({
                    widthTypeId: 1,
                    amount: blockSize.xs
                });
            }
            if (blockSize.sm > 0) {
                widthTypes.push({
                    widthTypeId: 2,
                    amount: blockSize.sm
                });
            }
            if (blockSize.md > 0) {
                widthTypes.push({
                    widthTypeId: 3,
                    amount: blockSize.md
                });
            }
            if (blockSize.lg > 0) {
                widthTypes.push({
                    widthTypeId: 4,
                    amount: blockSize.lg
                });
            }
            
            return widthTypes;
        },
        
        getBlockSizes: function (block) {
            var blockSizes = {
                xs: 0,
                sm: 0,
                md: 0,
                lg: 0
            };
            block.relBlockWidthTypes.forEach(function (widthType) {
                switch(widthType.id.widthType) {
                    case UTIL.WIDTH_TYPES.XS:
                        blockSizes.xs = widthType.amount;
                        break;
                        
                    case UTIL.WIDTH_TYPES.SM:
                        blockSizes.sm = widthType.amount;
                        break;
                        
                    case UTIL.WIDTH_TYPES.MD:
                        blockSizes.md = widthType.amount;
                        break;
                        
                    case UTIL.WIDTH_TYPES.LG:
                        blockSizes.lg = widthType.amount;
                        break;
                }
            });
            return blockSizes;
        },
        
        elementHover: function ($element, options) {
            $element.hover(
                function () {
                    MANSC.blockOptions($element, options, true);
                },
                function () {
                    MANSC.blockOptions($element, options, false);
                }
            );
        }
    }; 
    
    return functions;
})();