angular.module("angularTest", ["ui.bootstrap"]);

angular
.module("angularTest")
.controller("angularController", function ($scope, cribsFactory) {

    $scope.cribs;

    cribsFactory.getCribs().success(function (data) {
        $scope.cribs = data;
    }).error(function (error) {
        console.log(error);
    });

    $scope.sayHello = function () {
        console.log("Hello");
    };

});
        
angular
.module("angularTest")
.factory("cribsFactory", function ($http) {
    function getCribs() {
//                return $http.get("/Testfield/angularArray");
        return $http.get("/Testfield/resources/tests/data/data.json");
    }

    return {
        getCribs: getCribs
    };
});
        