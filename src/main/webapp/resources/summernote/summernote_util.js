/* global UTIL */

define(function () {
    var generalTestfield = angular.module("generalTestfield");

    generalTestfield.directive("editable", function () {
        return {
            restrict: "A",
            link: function (scope, element, attrs) {
                var $textarea = element.siblings("textarea");
                
                element.click(function () {
                    scope.editing = true;
                    scope.$apply();
                    UTIL.autoExpand($textarea);
                    $textarea.focus();
                });
                $textarea.blur(function () {
                    scope.editing = false;
                    scope.saveBlock();
                    scope.$apply();
                });

            }
        };
    });

    var summer_util = (function () {
        return {
            closeButton: function (click) {
                return function () {
                    var ui = $.summernote.ui;

                    var button = ui.button({
                        contents: '<i class="fa fa-child"/> Close',
                        tooltip: 'Close',
                        click: click
                    });

                    return button.render();
                };
            },

            getToolbarForBlock: [
                ['style', ['bold', 'italic', 'underline', 'clear', "fontname"]],
                ['font', ['strikethrough', 'superscript', 'subscript']],
                ['fontsize', ['fontsize']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['height', ['height']],
                ['insert', ['picture',/* 'link', 'video',*/ 'table', 'hr']],
                ['misc', ['fullscreen', 'codeview', 'undo', 'redo', 'help']],
                ["myButtons", ["close"]]
            ]
        };
    })();
});

