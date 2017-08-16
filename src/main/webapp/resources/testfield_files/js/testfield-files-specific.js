define(function () {
    var filesTestfield = angular.module("filesTestfield");
    filesTestfield.config(function ($routeProvider) {
        $routeProvider.when("/browse", {
            templateUrl: "static/htmlParts/files/imagesList.html",
            controller: "imageListController",
            css: [ "resources/testfield_files/style/file-list.css" ]
        }).otherwise("/browse");
    });
    
    filesTestfield.controller("navbarFilesController",
    ["$scope", "$window",
    function ($scope, $window) {
        $scope.goToManuals = function () {
            $window.location.href = "/Testfield/manuals";
        };
        $scope.goToForum = function () {
            alert("Not implemented yet");
        };
        $scope.goToFiles = function () {
            $window.location.href = "/Testfield/files";
        };
    }]);
});
