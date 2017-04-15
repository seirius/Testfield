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

generalTestfield.service("ModalService", function ($templateRequest, $compile) {
    return {
        openModal: function (args) {
            var callbacks = args.callbacks;
            callbacks = $.extend({
                open: function () {},
                close: function () {},
                buttonClose: function () {}
            }, callbacks);
            
            $templateRequest("/Testfield/static/htmlParts/util/modalPart.html")
            .then(function(modalHtml) {
                var $modal = $(modalHtml);
                $("body").append($modal);
                
                $modal.on("hidden.bs.modal", function () {
                    callbacks.close();
                    $modal.remove();
                });

                $templateRequest(args.urlContent).then(function (content) {
                    var $content = $compile(content)(args.scope);
                    $modal.find(".modal-body").append($content);
                    $modal.modal("show");
                });

                args.scope.$on("close-tf-modal", function () {
                    $modal.modal("hide");
                });
                
                $modal.find("button[data-dismiss='modal']")
                        .click(callbacks.buttonClose);
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