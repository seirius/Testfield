var generalTestfield = angular.module("generalTestfield");

generalTestfield.service("FormService", function (tfHttp) {
    
    return {
        FORM_B_URL: "static/htmlParts/forms/formBuilder.html",
        requestForm: function (args) {
            return tfHttp.requestParam({
                url: "/Testfield/request/form",
                data: {
                    formName: args.formName
                },
                method: "GET"
            });
        },
        requestFormPost: function (args) {
            return tfHttp.request({
                url: "/Testfield/request/formPost",
                data: args,
                method: "POST"
            });
        },
        sendForm: function (form) {
            return tfHttp.request({
                url: "/Testfield/request/sendForm",
                data: {
                    form: form
                },
                method: "POST"
            });
        },
        serverValidation: function (input) {
            return tfHttp.request({
                url: "/Testfield/request/serverValidation",
                data: input,
                method: "POST"
            });
        },
        getSubmitBtnData: function (form) {
            var btn;
            if (form.buttons && form.buttons.buttons) {
                form.buttons.buttons.some(function (button) {
                    if (button.type === "submit" && button.click) {
                        btn = button;
                        return true;
                    }
                });
            }
            return btn;
        }
    };
});

generalTestfield.controller("formController", 
["$scope", "FormService",
function ($scope, FormService) {
    $scope.getInputHtml = function (input) {
        switch(input["@type"]) {
            case "InputText":
                return "static/htmlParts/forms/inputs/inputText.html";
                
            case "InputSelect":
                return "static/htmlParts/forms/inputs/inputSelect.html";
                
            case "InputTextarea":
                return "static/htmlParts/forms/inputs/inputTextarea.html";
            
            case "InputRadio": 
                return "static/htmlParts/forms/inputs/inputRadio.html";
            
            case "InputCheckbox": 
                return "static/htmlParts/forms/inputs/inputCheckbox.html";
        }
    };
    $scope.submit = function () {
        if ($scope[$scope.form.name].$valid) {
            FormService.sendForm($scope.form)
            .then(function (data) {
                var submit = FormService.getSubmitBtnData($scope.form);
                if (submit) {
                    $scope[submit.click](data);
                }
            });
        }
    };
    
    $scope.buttonClick = function (buttonType, buttonFunction) {
        if (buttonType !== "submit") {
            buttonFunction();
        }
    };
}]);  

generalTestfield.directive("inputVerify", function () {
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attrs, ngModel) {
            if (!ngModel || attrs.inputVerify.length === 0) return;
            
            var modelInput = scope[scope.form.name][attrs.inputVerify];
            
            ngModel.$validators.inputVerify = function (modelValue, viewValue) {
                return modelInput.$viewValue === viewValue;
            };
            
            modelInput.$validators.modelVerify = function () {
                ngModel.$validate();
                return true;
            };
        }
    };
});

generalTestfield.directive("server", function ($q, FormService) {
    return {
        restrict: "A",
        require: "ngModel",
        link: function (scope, element, attrs, ngModel) {
            if (attrs.server.length === 0) return;
            
            ngModel.$asyncValidators.server = function (modelValue, viewValue) {
                var defer = $q.defer();
                
                if (ngModel.$isEmpty(viewValue)) {
                    return $q.resolve();
                }
                
                var input;
                scope.form.inputs.some(function (auxInput) {
                    if (auxInput.name === ngModel.$name) {
                        input = auxInput;
                        return true;
                    }
                });
                
                FormService.serverValidation(input).then(function (response) {
                    if (response.data.ok) {
                        defer.resolve();
                    } else {
                        defer.reject();
                    }
                });
                
                return defer.pormise;
            };
        }
    };
});