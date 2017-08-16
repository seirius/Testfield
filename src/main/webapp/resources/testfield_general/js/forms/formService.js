var generalTestfield = angular.module("generalTestfield");

generalTestfield.service("FormService", function (tfHttp) {
    
    var controlValidation = function (response) {
        if (response.errorCode !== 0) {
            return;
        }
        
        response.data.form.inputs.forEach(function (input) {
            if (typeof input.validation === "undefined") {
                input.validation = {
                    ngModelOptions: {}
                };
            } else if (typeof input.validation.ngModelOptions === "undefined"
                    || input.validation.ngModelOptions === null) {
                input.validation.ngModelOptions = {};
            }
        });
    };
    
    return {
        FORM_B_URL: "static/htmlParts/forms/formBuilder.html",
        requestForm: function (args) {
            var promise = tfHttp.requestParam({
                url: "/Testfield/request/form",
                data: {
                    formName: args.formName
                },
                method: "GET"
            });
            promise.then(controlValidation);
            return promise;
        },
        requestFormPost: function (args) {
            var promise = tfHttp.request({
                url: "/Testfield/request/formPost",
                data: args,
                method: "POST"
            });
            promise.then(controlValidation);
            return promise;
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
    $scope.getInputHtmlM = function (input) {
        switch(input["@type"]) {
            case "InputText":
                return "static/htmlParts/forms/inputs/inputTextM.html";
                
            case "InputSelect":
                return "static/htmlParts/forms/inputs/inputSelectM.html";
                
            case "InputTextarea":
                return "static/htmlParts/forms/inputs/inputTextareaM.html";
            
            case "InputRadio": 
                return "static/htmlParts/forms/inputs/inputRadioM.html";
            
            case "InputCheckbox": 
                return "static/htmlParts/forms/inputs/inputCheckboxM.html";
        }
    };
    $scope.submit = function () {
        if ($scope[$scope.form.name].$valid) {
            FormService.sendForm($scope.form)
            .then(function (data) {
                var submit = FormService.getSubmitBtnData($scope.form);
                if (submit && submit.click && $scope[submit.click]) {
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
    
    $scope.inputsLoaded = 0;
    $scope.inputLoaded = function (input) {
        $scope.inputsLoaded++;
        if ($scope.inputsLoaded === $scope.form.inputs.length
                && $scope.formRendered) {
            $scope.formRendered();
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
                
                input.value = modelValue;
                FormService.serverValidation(input).then(function (response) {
                    if (response.data.ok) {
                        defer.resolve(modelValue);
                    } else {
                        defer.reject(modelValue);
                    }
                });
                
                return defer.promise;
            };
        }
    };
});

generalTestfield.directive('onRepeatEnd', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    if (scope[attr.onRepeatEnd]) {
                        scope[attr.onRepeatEnd]();
                    }
                });
            }
        }
    };
});