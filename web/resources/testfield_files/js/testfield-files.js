define(["js/masonry/masonry"], function (Masonry) {
    var filesTestfield = angular.module("filesTestfield", 
    ["generalTestfield", "ngRoute"]);

    filesTestfield.controller("imageListController", 
    ["$scope", "FileService", "Testfield",
    function ($scope, FileService, Testfield) {
        $scope.domainUrl = Testfield.getDomainUrl();
        $scope.files = [];

        FileService.getUsersImages()
        .then(function (data) {
            $scope.files = data.files;
        });
        
        $scope.nons = [
            {a: "a"},
            {a: "b"},
            {a: "a"},
            {a: "a"},
            {a: "b"},
            {a: "a"}
        ];
    }]);

    filesTestfield.directive("masonrySet", 
    function () {
        return {
            restrict: "A",
            link: function (scope, element, attrs) {
//                setTimeout(function () {
//                    new Masonry(element.parent()[0], {
//                        itemSelector: ".file-item"
//                    });
//                }, 1000);
            }
        };
    });
});



