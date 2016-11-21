var app = angular.module("generalTestfield", []);

app.service("tfHttp", function ($http) {
    var showError = true;
    
    return {
        request: function (args) {
            args.data = typeof args.data === "undefined" ? {} : args.data;
            args = $.extend({
                data: {},
                method: "POST"
            } ,args);
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
        },
        
        showError: function () {
            showError = false;
        }
    };
});

app.service("UserService", function (tfHttp) {
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
            return tfHttp.request({
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

app.service("Testfield", function ($window) {
    return {
        goAfterLogout: function () {
            $window.location.href = "/Testfield/";
        }
    };
});

app.directive("linkable", function ($window) {
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

app.directive("tfModal", function ($http, $compile) {
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
