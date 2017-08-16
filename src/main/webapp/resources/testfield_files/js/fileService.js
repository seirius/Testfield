define(function () {
    var filesTestfield = angular.module("filesTestfield");

    filesTestfield.service("FileService", function (tfHttp) {
        return {
            /**
             * 
             * @param {JSON} args {manualId, file}
             * @returns {Promise}
             */
            uploadManualFile: function (args) {
                return tfHttp.requestFile({
                    url: "/Testfield/request/upload/uploadManualFiles",
                    data: {
                        manualId: args.manualId
                    },
                    files: args.files
                });
            },
            HTML_USR_IMG: "static/htmlParts/files/imagesList.html",
            getUsersImages: function () {
                return tfHttp.requestParam({
                    url: "/Testfield/request/file/getUsersImages"
                });
            }
        };
    });
});

