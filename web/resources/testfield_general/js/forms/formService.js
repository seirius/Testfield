var generalTestfield = angular.module("generalTestfield");

generalTestfield.service("FormService", function (tfHttp) {
    
    return {
        FORM_B_URL: "static/htmlParts/forms/formBuilder.html",
        requestForm: function (args) {
            return tfHttp.requestParam({
                url: "/Testfield/request/form",
                data: {
                    formName: args.form
                },
                method: "GET"
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
        switch(input.type) {
            case "text":
                return "static/htmlParts/forms/inputs/inputText.html";
                
            case "select":
                return "static/htmlParts/forms/inputs/inputSelect.html";
                
            case "textarea":
                return "static/htmlParts/forms/inputs/inputTextarea.html";
            
            case "radio": 
                return "static/htmlParts/forms/inputs/inputRadio.html";
            
            case "checkbox": 
                return "static/htmlParts/forms/inputs/inputCheckbox.html";
            
            case "checkboxGroup": 
                return "static/htmlParts/forms/inputs/inputCheckboxGroup.html";
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
}]);  