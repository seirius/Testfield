$(document).ready(function () {
    $("#btnLogout").click(function () {
        $.ajax({
            url: "/Testfield/logout",
            dataType: "json",
            type: "POST",
            beforeSend: function () {
                $("body").loadingState();
            },
            success: function (response) {
                $("body").loadingState("destroy");
                if (response.data.logoutOk) {
                    window.location = "/Testfield";
                } else {
                    console.log(response.errorMsg);
                }
            },
            error: function (error) {
                treatException(error);
            }
        });
    });
});


