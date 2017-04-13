/* global generalTestfield */

generalTestfield.service("ManualService", function (tfHttp, $compile) {
    var manualService = this;
    
    var currentManual = {};
    var manualsList = [];
    
    var VIEW_STATE = {
        VIEW: 0,
        EDIT: 1
    };
    
    var MOVE_OPTIONS = {
        UP: 0,
        DOWN: 1,
        LEFT: 2,
        RIGHT: 3
    };
    
    var emitManualLoaded = function ($scope) {
        $scope.$emit("manual-loaded");
    };
    
    var setManualsViewState = function (state) {
        var manual = manualService.getCurrentManual();
        manual.viewState = state;
    };
    
    var setManualsStyle = function (manual) {
        var $manualContainer = $(".manualContainer");
        var fontColor = manual.manualConf.fontColor;
        var color = "rgb(" + fontColor.r + "," + fontColor.g + "," + fontColor.b +")";
        $manualContainer.css({
            "font-family": manual.manualConf.fontFamily.cssStyle,
            "color": color
        });
        if (manual.manualConf.manualBackground === "none") {
            $("body").removeClass("background-congruent");
        }
    };
    
    var setManualsPositions = function (manual) {
        manual.pages.forEach(function (page, indexPage) {
            page.isFirst = indexPage === 0;
            page.isLast = indexPage + 1 === manual.pages.length;
            
            page.rows.forEach(function (row, indexRow) {
                row.isFirst = indexRow === 0;
                row.isLast = indexRow + 1 === page.rows.length;
                
                row.blocks.forEach(function (block, indexBlock) {
                    block.isFirst = indexBlock === 0;
                    block.isLast = indexBlock + 1 === row.blocks.length;
                });
            });
        });
    };
    
    var introduceManual = function ($scope, container, manual) {
        manualService.setCurrentManual(manual);
        var manualPageDir = $compile($("<manual-page>"))($scope);
        container.empty().append(manualPageDir);
        setManualsPositions(manual);
        setManualsViewState(VIEW_STATE.EDIT);
        setManualsStyle(manual);
        emitManualLoaded($scope);
    };
    
    var visualizeManual = function ($scope, container, manual) {
        manualService.setCurrentManual(manual);
        var manualView = $compile($("<manual-view>"))($scope);
        container.empty().append(manualView);
        setManualsViewState(VIEW_STATE.VIEW);
        emitManualLoaded($scope);
    };
    
    manualService = {
        VIEW_STATE: VIEW_STATE,
        MOVE_OPTIONS: MOVE_OPTIONS,
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
        
        openManualsStyle: function ($scope) {
            var $modalList = $("<tf-modal>", {
                "url-content": "/Testfield/static/htmlParts/manuals/manualStyle.html"
            });

            $("body").append($modalList);
            $modalList = $compile($modalList)($scope);
        },
        
        openManual: function (idManual, $scope, container) {
            manualService.loadManual(idManual).then(function (data) {
                introduceManual($scope, container, data.manual);
            });
        },
        
        reloadManual: function ($scope) {
            manualService.loadManual(manualService.getCurrentManual().id).then(function (data) {
                introduceManual($scope, $(".manualContainer"), data.manual);
            });
        },
        
        visualizeManual: function ($scope) {
            manualService.loadManual(manualService.getCurrentManual().id).then(function (data) {
                visualizeManual($scope, $(".manualContainer"), data.manual);
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
                title: currentManual.title
            });
            var request = tfHttp.requestParam({
                url: "/Testfield/request/manual/setTitle",
                data: {
                    idManual: args.idManual,
                    title: args.title
                }
            });
            
            return request.then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    resolve(data);
                });
            });
        },
        
        saveBlock: function (idBlock, content) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/saveManualBlock",
                data: {
                    idBlock: idBlock,
                    content: content
                }
            }).then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    resolve(data);
                });
            });
        },
        
        /**
         * 
         * @param {JSON} args {
         *      idPage,
         *      rowOrder
         * }
         * @returns {unresolved}
         */
        addRow: function (args) {
            args = $.extend({
                idPage: "NOT SELECTED",
                rowOrder: 0
            }, args);
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/addRow",
                data: args
            }).then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    resolve(data);
                });
            });
        },
        
        /**
         * @param {JSON} args {
         *      idManual,
         *      pageOrder
         * }
         * @returns {unresolved}
         */
        addPage: function (args) {
            args = $.extend({
                idManual: manualService.getCurrentManual().id,
                pageOrder: 0
            }, args);
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/addPage",
                data: args
            }).then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    resolve(data);
                });
            });
        },
        
        /**
         * 
         * @param {JSON} args {
         *      idRow,
         *      blockOrder,
         *      content,
         *      widthTypes: [
         *          {
         *              widthTypes,
         *              amount
         *          }
         *      ]
         * }
         * @returns {unresolved}
         */
        addBlock: function (args) {
            args = $.extend({
                idRow: "NOT SELECTED",
                blockOrder: 0,
                content: "New Block",
                widthTypes: [
                    {
                        widthType: 3,
                        amount: 12
                    }
                ]
            }, args);
            
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/addBlock",
                data: args,
                argsStr: [
                    "widthTypes"
                ]
            }).then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    resolve(data);
                });
            });
        },
        
        /**
         * 
         * @param {JSON} args {
         *      idBlock,
         *      widthTypes: [
         *          {
         *              widthTypes,
         *              amount
         *          }
         *      ]
         * }
         * @returns {unresolved}
         */
        modifyBlockSize: function (args) {
            args = $.extend({
                idBlock: "",
                widthTypes: [
                    {
                        widthType: 3,
                        amount: 12
                    }
                ]
            }, args);
            
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/modifyBlockSize",
                data: args,
                argsStr: [
                    "widthTypes"
                ]
            }).then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    resolve(data);
                });
            });
        },
        
        getRow: function (pageId) {
            var manual = manualService.getCurrentManual();
            var rows = [];
            manual.pages.some(function (page) {
                if (page.id === pageId) {
                    rows = page.rows;
                    return true;
                } else {
                    return false;
                }
            });
            
            return rows;
        },
        
        getWidthTypes: function () {
            return tfHttp.request({
                url: "/Testfield/request/manual/getWidthTypes"
            });
        },
        
        deleteBlock: function (idBlock) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/deleteOptions",
                data: {
                    id: idBlock,
                    deleteType: "BLOCK"
                }
            });
        },
        
        deleteRow: function (idRow) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/deleteOptions",
                data: {
                    id: idRow,
                    deleteType: "ROW"
                }
            });
        },
        
        deletePage: function (idPage) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/deleteOptions",
                data: {
                    id: idPage,
                    deleteType: "PAGE"
                }
            });
        },
        
        moveBlockFoward: function (idBlock) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/moveBlock",
                data: {
                    idBlock: idBlock,
                    moveOption: MOVE_OPTIONS.RIGHT
                }
            });
        },
        
        moveBlockBackward: function (idBlock) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/moveBlock",
                data: {
                    idBlock: idBlock,
                    moveOption: MOVE_OPTIONS.LEFT
                }
            });
        },
        
        movePage: function (idPage, moveOption) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/movePage",
                data: {
                    idPage: idPage,
                    moveOption: moveOption
                }
            });
        },
        
        moveRow: function (idRow, moveOption) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/moveRow",
                data: {
                    idRow: idRow,
                    moveOption: moveOption
                }
            });
        },
        
        /**
         * 
         * @param {JSON} args {
         *      manualId, R, G, B, fontFamily
         * }
         * @returns {unresolved}
         */
        updateStyles: function (args) {
            var request = tfHttp.request({
                url: "/Testfield/request/manual/updateStyle",
                data: args
            });
            
            return request.then(function (data) {
                return new Promise(function (resolve) {
                    manualService.setCurrentManual(data.manual);
                    setManualsStyle(data.manual);
                    resolve(data);
                });
            });
        }
    };
    
    return manualService;
});