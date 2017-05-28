var generalTestfield = angular.module("generalTestfield", []);

generalTestfield.service("tfHttp", function ($http) {
    var showError = true;

    var httpReturn = function (args) {
        return $http(args).then(function (response) {
            return new Promise(function (resolve, reject) {
                if (typeof response !== "undefined" && typeof response.data !== "undefined") {
                    var data = response.data;
                    if (data.errorCode === 0) {
                        resolve(data);
                    } else if (typeof data.errorCode !== "undefined" 
                            && data.errorCode !== 0) {
                        if (showError) {
                            alert(data.errorMsg);
                        }
                        reject(data);
                    } else {
                        resolve(response);
                    }

                }

                showError = true;
            });
        }, function (response) {
            alert(response);
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

        requestFile: function (args) {
            var formData = new FormData();

            if (typeof args.data === "object") {
                for (var key in args.data) {
                    formData.append(key, args.data[key]);
                }
            }

            if (typeof args.files === "object") {
                var i = 0;
                for (i; i < args.files.length; i++) {
                    formData.append("files", args.files[i]);
                }
            }

            var auxArgs = {
                url: args.url,
                method: "POST",
                headers: {
                    "Content-Type": undefined
                },
                transformRequest: angular.identity,
                data: formData
            };

            return httpReturn(auxArgs);
        },

        showError: function () {
            showError = false;
        }
    };
});

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
    var DOMAIN_URL = "http://79.108.123.27:8090/Testfield";

    return {
        goAfterLogout: function () {
            $window.location.href = "/Testfield/";
        },

        getDomainUrl() {
            return DOMAIN_URL;
        },

        CANVAS: {
            MANUAL: "static/htmlParts/manuals/manualPagePart.html",
            MANUAL_V: "static/htmlParts/manuals/manualView.html",
            IMAGE_LIST: "static/htmlParts/files/imagesList.html"
        },

        resolveController: function (names) {
            return {
                load: ['$q', '$rootScope', function ($q, $rootScope) {
                        var defer = $q.defer();
                        require(names, function () {
                            defer.resolve();
                            $rootScope.$apply();
                        });
                        return defer.promise;
                    }]
            };
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

generalTestfield.service("ModalService", function ($templateRequest, $compile) {
    return {
        openModal: function (args) {
            return new Promise(function (resolve, reject) {
                var callbacks = args.callbacks;
                callbacks = $.extend({
                    open: function () {},
                    close: function () {},
                    buttonClose: function () {}
                }, callbacks);

                $templateRequest("/Testfield/static/htmlParts/util/modalPart.html")
                .then(function (modalHtml) {
                    var $modal = $(modalHtml);
                    $("body").append($modal);

                    $modal.on("hidden.bs.modal", function () {
                        callbacks.close();
                        $modal.remove();
                    });

                    args.scope.$on("close-tf-modal", function () {
                        $modal.modal("hide");
                    });

                    $modal.find("button[data-dismiss='modal']")
                            .click(callbacks.buttonClose);

                    if (typeof args.urlContent === "string") {
                        $templateRequest(args.urlContent).then(function (content) {
                            var $content = $compile(content)(args.scope);
                            $modal.find(".modal-body").append($content);
                            $modal.modal("show");
                            resolve($modal);
                        });
                    } else {
                        resolve($modal);
                    }
                });
            });
        }
    };
});

generalTestfield.directive("tfModal", ["ModalService", function (ModalService) {
    return {
        restrict: "E",
        link: function (scope, element, attrs) {
            ModalService.openModal({
                urlContent: attrs.urlContent, 
                scope: scope
            });
        }
    };
}]);

generalTestfield.directive("showErrors", function () {
    return {
        link: function (scope, element) {
            var input = element.find("[name]");
            var inputName = input.attr("name");

            input.blur(function () {
                var formInput = scope.register[inputName];
                var gotError = formInput.$invalid && formInput.$touched && formInput.$dirty;
                element.toggleClass("has-error", gotError);
            });

            scope.$on("show-errors-event", function () {
                var formInput = scope.register[inputName];
                var gotError = formInput.$invalid;
                element.toggleClass("has-error", gotError);
            });
        }
    };
});

generalTestfield.directive("checkPasswords", function () {
    return {
        require: "ngModel",
        link: function (scope, element, attrs, ctrl) {
            var firstPassword = element.closest("form").find("[name='" + attrs.checkPasswords + "']");

            element.keyup(function () {
                var valid = element.val() === firstPassword.val();
                ctrl.$setValidity("matchpw", valid);
            });
        }
    };
});

generalTestfield.directive('head', ['$rootScope', '$compile',
    function ($rootScope, $compile) {
        return {
            restrict: 'E',
            link: function (scope, elem) {
                var html = '<link rel="stylesheet" ng-repeat="(routeCtrl, cssUrl) in routeStyles" ng-href="{{cssUrl}}" />';
                elem.append($compile(html)(scope));
                scope.routeStyles = {};
                $rootScope.$on('$routeChangeStart', function (e, next, current) {
                    if (current && current.$$route && current.$$route.css) {
                        if (!angular.isArray(current.$$route.css)) {
                            current.$$route.css = [current.$$route.css];
                        }
                        angular.forEach(current.$$route.css, function (sheet) {
                            delete scope.routeStyles[sheet];
                        });
                    }
                    if (next && next.$$route && next.$$route.css) {
                        if (!angular.isArray(next.$$route.css)) {
                            next.$$route.css = [next.$$route.css];
                        }
                        angular.forEach(next.$$route.css, function (sheet) {
                            scope.routeStyles[sheet] = sheet;
                        });
                    }
                });
            }
        };
    }
]);


generalTestfield.directive("logout", function (UserService, Testfield) {
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            element.click(function () {
                UserService.logout().then(function (response) {
                    if (response.data.logoutOk) {
                        Testfield.goAfterLogout();
                    } else {
                        alert("Coldnt't logout.");
                    }
                });
            });
        }
    };
});