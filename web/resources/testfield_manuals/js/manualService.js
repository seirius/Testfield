var app = angular.module("manualsTestfield");

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
                
                $scope.$emit("manual-loaded");
            });
        },
        
        reloadManual: function ($scope) {
            manualService.loadManual(manualService.getCurrentManual().id).then(function (data) {
                manualService.setCurrentManual(data.manual);
                var manualPageDir = $compile($("<manual-page>"))($scope);
                $(".manualContainer").empty().append(manualPageDir);
                
                $scope.$emit("manual-loaded");
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
                    moveOption: 3
                }
            });
        },
        
        moveBlockBackward: function (idBlock) {
            return tfHttp.requestParam({
                url: "/Testfield/request/manual/moveBlock",
                data: {
                    idBlock: idBlock,
                    moveOption: 2
                }
            });
        }
    };
    
    return manualService;
});