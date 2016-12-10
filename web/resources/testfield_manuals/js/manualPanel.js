var app = angular.module("manualsTestfield");

app.controller("manualPanelCtrl", function ($rootScope, $scope, ManualService) {
    $scope.manualLoaded = false;
    $scope.pageOrderSelected = 0;
    $scope.rowOrderSelected = 0;
    $scope.blockOrderSelected = 0;
    $scope.rowsForBlock = [];
    
    var selectPage = function (scope, page) {
    };
    
    $rootScope.$on("page-selected", selectPage);
    
    var selectRow = function (scope, row) {
    };
    
    $rootScope.$on("row-selected", selectRow);
    
    var selectBlock = function (scope, block) {
    };
      
    $rootScope.$on("block-selected", selectBlock);
    
    $rootScope.$on("manual-loaded", function () {
        $scope.manualLoaded = true;
        $scope.manual = ManualService.getCurrentManual();
        
        if ($scope.manual.pages.length > 0) {
            $scope.selectPageForRow = $scope.manual.pages[0].id;
            $scope.selectPageForBlock = $scope.selectPageForRow;
        }
        
        $scope.loadRows();
        
        ManualService.getWidthTypes().then(function (data) {
            $scope.widthTypes = data.widthTypes;
        });
    });
    
    $scope.addPage = function () {
        ManualService.addPage({
            pageOrder: $scope.pageOrderSelected
        }).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.addRow = function () {
        ManualService.addRow({
            idPage: $scope.selectPageForRow,
            rowOrder: $scope.rowOrderSelected
        }).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.addBlock = function () {
        ManualService.addBlock({
            idRow: $scope.selectRowForBlock,
            widthTypes: [
                {
                    widthTypeId: $scope.selectWidthType,
                    amount: $scope.amountWidthType
                }
            ],
            blockOrder: $scope.blockOrderSelected
        }).then(function () {
            ManualService.reloadManual($scope);
        });
    };
    
    $scope.loadRows = function () {
        $scope.rowsForBlock = ManualService.getRow($scope.selectPageForBlock);
        if ($scope.rowsForBlock.length > 0) {
            $scope.selectRowForBlock = $scope.rowsForBlock[0].id;
        }
    };
});


