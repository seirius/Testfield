var generalTestfield = angular.module("generalTestfield", []);

generalTestfield.service("tfHttp", function ($http) {
    var showError = true;
    
    var httpReturn = function (args) {
        return $http(args).then(function (response) {
            return new Promise(function (resolve, reject) {
                if (typeof response !== "undefined" && typeof response.data !== "undefined") {
                    var data = response.data;
                    if (data.errorCode === 0) {
                        resolve(data.data);
                    } else {
                        if (showError) {
                            alert(data.errorMsg);
                        }
                        reject(data);
                    }

                }

                showError = true;
            });
        }, function (response) {
            alert(response.responseText);
        });
    };
    
    var checkStringify = function (args) {
        var argsToStr = args.argsStr;
        if ($.type(argsToStr) === "array") {
            argsToStr.forEach(function (arg) {
                var dataArg = args.data[arg];
                if (typeof dataArg !== "undefined") {
                    args.data[arg] = JSON.stringify(dataArg);
                }
            });
        }
    };
    
    return {
        request: function (args) {
            args.data = typeof args.data === "undefined" ? {} : args.data;
            checkStringify(args);
            args = $.extend({
                data: {},
                method: "POST"
            }, args);
            
            return httpReturn(args);
        },
        
        requestParam: function (args) {
            args.params = typeof args.data === "undefined" ? {} : args.data;
            checkStringify(args);
            args = $.extend({
                params: {},
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }, args);
            
            return httpReturn(args);
        },
        
        showError: function () {
            showError = false;
        }
    };
});

function httpReturn($http, args) {
    return $http(args).then(function (response) {
        return new Promise(function (resolve, reject) {
            if (typeof response !== "undefined" && typeof response.data !== "undefined") {
                var data = response.data;
                if (data.errorCode === 0) {
                    resolve(data.data);
                } else {
                    if (showError) {
                        alert(data.errorMsg);
                    }
                    reject(data);
                }

            }

            showError = true;
        });
    }, function (response) {
        alert(response.responseText);
    });
}

generalTestfield.service("UserService", function (tfHttp) {
    return {
        login: function (user, password) {
            return tfHttp.request({
                url: "/Testfield/request/login",
                data: {
                    userNick: user,
                    password: password
                }
            });
        },
        
        createUser: function (user, password, email) {
            return tfHttp.requestParam({
                url: "/Testfield/request/createUser",
                data: {
                    userNick: user,
                    password: password,
                    email: email
                }
            });
        },
        
        logout: function () {
            return tfHttp.request({
                url: "/Testfield/request/logout"
            });
        }
    };
});

generalTestfield.service("Testfield", function ($window) {
    return {
        goAfterLogout: function () {
            $window.location.href = "/Testfield/";
        }
    };
});

generalTestfield.directive("linkable", function ($window) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            var dir = attrs.linkable;
            
            element.click(function () {
                $window.location.href = dir;
            });
        }
    };
});

generalTestfield.directive("tfModal", function ($http, $compile) {
    return {
        restrict: "E",
        templateUrl: "/Testfield/static/htmlParts/util/modalPart.html",
        link: function (scope, element, attrs) {
            var $modal = element.find(".modal");
            $modal.on("hidden.bs.modal", function () {
                element.remove();
            });

            $http.get(attrs.urlContent).then(function (content) {
                var $content = $compile(content.data)(scope);
                $modal.find(".modal-body").append($content);
                $modal.modal("show");
            });
            
            scope.$on("close-manual-list", function () {
                $modal.modal("hide");
            });
        }
    };
});
