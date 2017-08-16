
//--------------------------------------------------------
//LOADING STATE
//--------------------------------------------------------
$.fn.loadingState = function (arg1) {
    var $element = this;
    if ($.type($element) === "undefined") {
        console.log("$element is undefined");
        return;
    }

    if ($.type(arg1) !== "string") {

        if ($.type($element.data("loading-state")) !== "undefined") {
            return;
        }
        
        var htmlLoading = ""
                + " <div class='loading'>"
                + "     <div class='bullet'>"
                + "     </div>"
                + "     <div class='bullet'>"
                + "     </div>"
                + "     <div class='bullet'>"
                + "     </div>"
                + "     <div class='bullet'>"
                + "     </div>"
                + " </div>";

        var settings = $.extend({
            "html": htmlLoading,
            "background-color": "gray",
            opacity: 0.5
        }, arg1);

        $element.css("position", "relative");

        var $wrapper = $("<div>").appendTo($element);
        var $background = $("<div>").appendTo($wrapper);
        var $container = $("<div>", {html: settings.html}).appendTo($wrapper);


        $wrapper.css({
            display: "flex",
            "justify-content": "center",
            "align-items": "center",
            position: "absolute",
            top: 0,
            left: 0,
            width: $element.width() + "px",
            height: $element.height() + "px",
            "border-radius": $element.css("border-radius")
        });

        $background.css({
            width: "100%",
            height: "100%",
            top: 0,
            left: 0,
            position: "absolute",
            "background-color": settings["background-color"],
            opacity: settings.opacity,
            "border-radius": $element.css("border-radius"),
            "z-index": "1"
        });

        $container.css({
            "align-center": "center",
            "z-index": "2"
        });

        var loadingState = {
            "wrapper": $wrapper,
            "container": $container
        };

        $element.bind("resize-loading", function () {
            $wrapper.width($(this).width());
            $wrapper.height($(this).height());
        });

        $element.data("loading-state", loadingState);
    } else {
        switch (arg1) {
            case "destroy":
                var loadingState = $element.data("loading-state");
                if (loadingState) {
                    loadingState.wrapper.remove();
                    $element.removeData("loading-state");
                    $element.unbind("resize-loading");
                }
                break;
        }
    }
};

$.fn.makeLinkable = function () {
    try {
        this.each(function () {
            var $element = $(this);
            $element.click(function () {
                var url = $element.data("url");
                if ($.type(url) === "string") {
                    window.location = url;
                }
            });
        });
    } catch(e) {
        treatException(e, undefined, "makeLinkable");
    }
};


function treatException(e, msg, scope) {
    var errorMsg = e;
    
    errorMsg = $.type(msg) === "string" ? errorMsg + "\n Message: " + msg : errorMsg;
    errorMsg = $.type(scope) === "string" ? errorMsg + "\n Scope: " + scope : errorMsg;
    
    console.log(errorMsg);
}

