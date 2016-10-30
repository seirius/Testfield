var app = angular.module("generalTestfield", []);

app.service("tfHttp", function ($http) {
    this.request = function (args) {
        args = $.extend(args, {
            data: $.param(args.data),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
        return $http(args);
    };
});

$(document).ready(function () {
   
    $(".linkable").makeLinkable();
    
});

