/* global UTIL, summer_util, URL */

var manualsTestfield = angular.module("manualsTestfield", ["generalTestfield", 
            "ngSanitize", "filesTestfield", "ngRoute"]);
manualsTestfield.config(function ($routeProvider) {
    $routeProvider
    .when("/browse", {
        templateUrl: "static/htmlParts/manuals/browseManuals.html",
        controller: "manualsBrowserController"
    })
    .when("/manual/:param", {
        templateUrl: "static/htmlParts/manuals/manualView.html",
        controller: "manualViewController"
    })
    .when("/manual/:param/edit", {
        templateUrl: "static/htmlParts/manuals/manualPagePart.html",
        controller: "manualPageController"
    })
    .otherwise("/browse");
});

manualsTestfield.controller("navbarManualsCtrl", 
["$scope", "$rootScope", "ManualService", "$location", "$routeParams", "$window",
"FormService", "ModalService",
function ($scope, $rootScope, ManualService, $location, $routeParams, $window,
    FormService, ModalService) {
    $scope.manualLoaded = false;
    
    var loadEdit = function (id) {
        $location.path("/manual/" + id + "/edit");
    };
    
    var loadVisualize = function (id) {
        $location.path("/manual/" + id);
    };
    
    $scope.createManual = function () {
        ManualService.createManual().then(function (response) {
            loadEdit(response.data.manual.id);
        });
    };
    
    $scope.editing = true;
    $rootScope.$on("manual-loaded", function () {
        var manual = ManualService.getCurrentManual();
        var viewState = manual.viewState;
        $scope.editing = viewState === ManualService.VIEW_STATE.EDIT;
        $scope.manualLoaded = true;
        $scope.manual = manual;
    });
    
    $scope.manualsList = function () {
        ManualService.openManualsList($scope);
    };
    
    $scope.edit = function () {
        var id = parseInt($routeParams.param);
        loadEdit(id);
    };
    
    $scope.visualize = function () {
        var id = parseInt($routeParams.param);
        loadVisualize(id);
    };
    
    $scope.addPage = function () {
        ManualService.addPage(ManualService.getCurrentManual().id).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.manualsStyle = function () {
        FormService.requestFormPost({
            formName: "manualOptionsForm",
            data: {
                data: {
                    manualId: ManualService.getCurrentManual().id
                }
            }
        }).then(function (response) {
            $scope.form = response.data.form;
            $scope.form.formSendData = {
                data: {
                    manualId: ManualService.getCurrentManual().id
                }
            };
            ModalService.openModal({
                urlContent: FormService.FORM_B_URL,
                scope: $scope,
                callbacks: {
                    close: function () {
                        $scope.form = {};
                    }
                }
            }).then(function ($modal) {
                $scope.submitStyles = function (response) {
                    ManualService.setCurrentManual(response.data.manual);
                    $modal.modal("hide");
                };
            });
        });
    };
    
    $scope.openOptions = function () {
        console.log($scope.manual);
        ManualService.openManualOptions($scope);
    };
    
    $scope.getJsonManual = function () {
        ManualService.getJsonManual(ManualService.getCurrentManual().id)
        .then(function (response) {
            var jsonManual = new Blob([response.data.jsonManual], {
                type: "application/json"
            });
            var jsonUrl = URL.createObjectURL(jsonManual);
            window.open(jsonUrl);
        });
    };
    
    $scope.getHtmlManual = function () {
        ManualService.getHtmlManual(ManualService.getCurrentManual().id);
    };
    
    $scope.goToManuals = function () {
        $window.location.href = "/Testfield/manuals";
    };
    $scope.goToForum = function () {
        alert("Not implemented yet");
    };
    $scope.goToFiles = function () {
        $window.location.href = "/Testfield/files";
    };
    
    $scope.requestForm = function () {
        FormService.requestFormPost({
            formName: "blockOptions",
            data: {
                data: {
                    idBlock: "05f830d5-8f18-45f6-b23d-40a88ee42e62"
                }
            }
        }).then(function (response) {
            $scope.form = response.data.form;
            $scope.form.formSendData = {
                data: {
                    idBlock: "05f830d5-8f18-45f6-b23d-40a88ee42e62"
                }
            };
            ModalService.openModal({
                urlContent: "/Testfield/static/htmlParts/forms/formBuilder.html",
                scope: $scope,
                callbacks: {
                    close: function () {
                        $scope.form = {};
                        $scope.formSubmitCallback = undefined;
                    }
                }
            }).then(function ($modal) {
                $scope.formSubmitCallback = function () {
                    console.log("submitted");
                    $modal.modal("hide");
                };
                $scope.cancelForm = function () {
                    console.log("cancelled");
                    $modal.modal("hide");
                };
            });
        });
    };
    
}]);

manualsTestfield.controller("manualsBrowserController", 
["$scope",
function ($scope) {
}]);

manualsTestfield.controller("manualPageController", 
function ($scope, ManualService, ModalService, $routeParams, $location) {
    var id = parseInt($routeParams.param);
    ManualService.openManual(id, $scope)
    .then(function (response) {
        $scope.manual = response.data.manual;
    });
    
    var keyups = 0;
    var titleSaved;
    $scope.keyup = function () {
        keyups++;
        if (keyups > 10) {
            keyups = 0;
            $scope.save();
        }
    };

    $scope.saveTitle = function () {
        ManualService.saveTitle({
            title: $scope.manual.title
        }).then(function (response) {
            console.log(response.data);
        });
        
        titleSaved = true;
        $scope.$broadcast("close-tf-modal");
    };
    
    $scope.editTitle = function () {
        var oldTitle = $scope.manual.title;
        titleSaved = false;
        ModalService.openModal({
            urlContent: "static/htmlParts/manuals/editTitle.html", 
            scope: $scope,
            callbacks: {
                close: function () {
                    if (!titleSaved) {
                        $scope.manual.title = oldTitle;
                        $scope.$apply();
                        oldTitle = "";
                    }
                }
            }
        });
    };
    
    $scope.$on("manual-loaded", function () {
        $scope.manual = ManualService.getCurrentManual();
    });

    $scope.pageSelected = function () {

    };
});

manualsTestfield.controller("manualListCtrl", 
[ "$rootScope", "$scope", "ManualService", "$location",
function ($rootScope, $scope, ManualService, $location) {
    $scope.manuals = ManualService.getManualsList();

    $scope.openManual = function (idManual) {
        $rootScope.$broadcast("close-tf-modal");
        $location.path("/manual/" + idManual + "/edit");
    };
}]);

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

manualsTestfield.controller("manualBlockCtrl", 
function ($scope, $rootScope, ManualService, $sce, FormService, ModalService) {
    $scope.showModButton = true;
    $scope.options = {
        showModify: false
    };
    $scope.$sce = $sce;
    $scope.manualBlock.manualBlockTrustedHtml = $sce.trustAsHtml($scope.manualBlock.content);
    
    var keyups = 0;
    $scope.keyup = function () {
        keyups++;
        if (keyups > 10) {
            keyups = 0;
            $scope.save();
        }
    };
    
    $scope.saveBlock = function (content) {
        ManualService.saveBlock($scope.block.id, content);
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
    
    $scope.modifyElement = function () {
        FormService.requestFormPost({
            formName: "blockOptions",
            data: {
                data: {
                    idBlock: $scope.block.id
                }
            }
        }).then(function (response) {
            $scope.form = response.data.form;
            $scope.form.formSendData = {
                data: {
                    idBlock: $scope.block.id
                }
            };
            ModalService.openModal({
                urlContent: "/Testfield/static/htmlParts/forms/formBuilder.html",
                scope: $scope,
                callbacks: {
                    close: function () {
                        $scope.form = {};
                        $scope.formSubmitCallback = undefined;
                    }
                }
            }).then(function ($modal) {
                $scope.formSubmitCallback = function () {
                    console.log("submitted");
                    $modal.modal("hide");
                };
                $scope.cancelForm = function () {
                    console.log("cancelled");
                    $modal.modal("hide");
                };
            });
        });
    };
    
    $scope.submitOptions = function (response) {
        $scope.block = response.data.manualBlock;
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

manualsTestfield.controller("manualViewController", 
function ($scope, ManualService, $sce, $routeParams) {
    var id = parseInt($routeParams.param);
    ManualService.visualizeManual(id, $scope)
    .then(function (response) {
        $scope.manual = response.data.manual;
    });
    
    $scope.getTrustedHtml = function (content) {
        return $sce.trustAsHtml(content);
    };
});

manualsTestfield.controller("manualOptionsController", 
["$scope", "ManualService",
function ($scope, ManualService) {

    $scope.isPublic = ManualService.isPublic;
    $scope.isPrivate = ManualService.isPrivate;
    
    $scope.changeVisibility = function () {
        var visibility;
        if (ManualService.isPublic($scope.manual)) {
            visibility = ManualService.VISIBILITY.PRIVATE; 
        } else {
            visibility = ManualService.VISIBILITY.PUBLIC; 
        }
        ManualService.setCurrentsVisibility(visibility);
    };
    
}]);

manualsTestfield.directive("blockClasses", function () {
    var getClassByWidthType = function (widthType) {
        var clazz;
        switch(widthType) {
            case 1:
                clazz = "col-xs-";
                break;
            case 2:
                clazz = "col-sm-";
                break;
            case 3:
                clazz = "col-md-";
                break;
            case 4:
                clazz = "col-lg-";
                break;
        }
        return clazz;
    };
    
    var setClasses = function (scope, element) {
        scope.block.relBlockWidthTypes.forEach(function (relBlock) {
            var preClass = getClassByWidthType(relBlock.id.widthType);
            element.removeClass (function (index, className) {
                return (className.match (/(^|\s)col-\S+/g) || []).join(' ');
            });
            element.addClass(preClass + relBlock.amount);
        });
    };

    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            setClasses(scope, element);
            scope.$watch("block.relBlockWidthTypes", function () {
                setClasses(scope, element);
            });
        }
    };
});

manualsTestfield.directive("blockOptions", 
["FormService", "ModalService", 
function (FormService, ModalService) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.hover(function () {
                scope.options.showModify = true;
                scope.$apply();
            }, function () {
                scope.options.showModify = false;
                scope.$apply();
            });
        }
    };
}]);