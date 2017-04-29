require.config({
    paths: {
        "jQuery": [
            "https://code.jquery.com/jquery-1.12.0.min", 
            "../jquery/jquery"
        ],
        "testfield-files": "../../testfield_general/js/testfield-files"
    },
    shim: {
        "jQuery": {
            exports: "$"
        }
    }
});
require(["testfield-files"], function () {
});

