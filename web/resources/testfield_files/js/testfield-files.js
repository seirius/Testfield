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
}]);



