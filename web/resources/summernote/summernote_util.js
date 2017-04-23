/* global generalTestfield */

generalTestfield.directive("editable", function ($templateRequest, FileService, 
                        ManualService) {
    var validElements = [
        "div"
    ];
    
    var isEditing = false;
    var $editorPanel;
    
    var onEditingChange = function (element) {
        isEditing = !isEditing;
        if (isEditing) {
            element.addClass("editing manual-block-editing");
            element.removeClass("editable-standby");
        } else {
            element.removeClass("editing manual-block-editing");
            element.addClass("editable-standby");
        }
    };
    
    var closeSummernote = function (element, callbacks) {
        if (isEditing) {
            callbacks.close(element.summernote("code"));
            element.summernote("destroy");
            onEditingChange(element);
        }
    };
    
    var enableSummernote = function (args, callbacks) {
        callbacks = $.extend({
            keyup: function () {},
            close: function () {}
        }, callbacks);
        
        var keyups = 0;
        
        args.element.summernote({
            toolbar: summer_util.getToolbarForBlock,
            buttons: {
                close: summer_util.closeButton(function () {
                    closeSummernote(args.element, callbacks);
                })
            },
            callbacks: {
                onInit: function () {
                    var $noteEditor = args.element.siblings(".note-editor");
                    $noteEditor.find("button").tooltip("destroy");
                    $templateRequest("static/htmlParts/manuals/summernoteEditPanel.html")
                    .then(function (data) {
                        $editorPanel = $(data);
                        $(".principal-container").append($editorPanel);
                        var $noteToolbar = $noteEditor.find(".note-toolbar");
                        $editorPanel.find(".summernote-edit-panel").append($noteToolbar);
                        $noteToolbar
                                .find(".dropdown-menu")
                                .closest(".note-btn-group")
                                .addClass("dropup");
                        $noteEditor.find(".note-editable").focus();
                    });
                },
                onBlur: function (event) {
                    if (event.relatedTarget === null) {
                        closeSummernote(args.element, callbacks);
                    }
                },
                onKeyup: function () {
                    keyups++;
                    if (keyups > args.attrs.snTrigger) {
                        callbacks.keyup(args.element.summernote("code"));
                        keyups = 0;
                    }
                },
                onPaste: function (e) {
                    var bufferText = ((e.originalEvent || e).clipboardData || window.clipboardData).getData('Text');
                    e.preventDefault();
                    document.execCommand('insertText', false, bufferText);
                },
                onImageUpload: function (files, editor, welEditable) {
                    FileService.uploadManualFile({
                        files: files,
                        manualId: ManualService.getCurrentManual().id
                    }).then(function (data) {
                        console.log(data);
                    }, function (data) {
                        console.log(data);
                    });
                }
            }
        });
        
        onEditingChange(args.element);
    };
    
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            var isValidElement = validElements.some(function (type) {
                return element.is(type);
            });
            if (!isValidElement) {
                throw "Non valid element " + element.prop("tagName");
            }
            
            element.dblclick(function () {
                if (!isEditing) {
                    enableSummernote({
                        element: element,
                        attrs: attrs
                    }, {
                        keyup: scope[attrs.snKeyup],
                        close: scope[attrs.snClose]
                    });
                }
            }).addClass("editable-standby");
            
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

