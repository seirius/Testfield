/* global generalTestfield */

generalTestfield.service("StyleService", function (tfHttp, $compile) {
    var styleService = this;
    
    styleService =  {
        getFontFamilies: function () {
            return tfHttp.requestParam({
                url: "/Testfield/request/style/fontFamilies"
            });
        }
    };
    
    return styleService;
});
