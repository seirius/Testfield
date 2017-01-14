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