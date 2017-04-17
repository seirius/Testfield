$.fn.resizeTextarea = function () {
    var $textarea = $(this);
    if (!$textarea.is("textarea")) {
        return;
    }
    
    var resize = function () {
        $textarea.height(1);
        $textarea.height(25 + $textarea[0].scrollHeight);
    };
    
    $textarea.focus(function () {
        resize();
    });
    $textarea.keyup(function () {
        resize();
    });
};

var UTIL = (function () {
    var WIDTH_TYPES = {
        XS: 1,
        SM: 2,
        MD: 3,
        LG: 4
    };
    
    return {
        WIDTH_TYPES: WIDTH_TYPES
    };
})();
