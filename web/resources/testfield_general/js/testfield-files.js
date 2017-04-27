var filesTestfield = angular.module("filesTestfield", ["generalTestfield"]);

filesTestfield.controller("imageList", function ($scope, FilesService, Testfield) {
    $scope.domainUrl = Testfield.getDomainUrl();
    $scope.files = [{path: ""}];
    
    FilesService.getUsersImages()
    .then(function (data) {
        $scope.files = data.files;
    });
});

filesTestfield.service("FilesService", function (tfHttp) {
    return {
        HTML_USR_IMG: "static/htmlParts/files/imagesList.html",
        getUsersImages: function () {
            return tfHttp.requestParam({
                url: "/Testfield/request/file/getUsersImages"
            });
        }
    };
});

filesTestfield.directive("displayImagesList", function (FilesService) {
    return {
        restrict: "E",
        templateUrl: FilesService.HTML_USR_IMG
    };
});



