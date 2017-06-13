// from Peheje in StackOverflow
function parseJwt(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
}

loginModal = $("#login-modal");
loginFormular = $("#login");
kontoModal = $("#konto-modal");
kontoOversigt = $("#konto-information");
logudKnap = $("#logud")

function reloadView() {
    $(".Administrator, .Farmaceut, .Vaerkfoerer, .Laborant, .non-loggedin").hide();
    if (localStorage.getItem("jwt") === null) {
        // Ikke logget ind
        $(".non-loggedin").show();
    } else {
        // Logget ind
        $.each(parseJwt(localStorage.getItem("jwt")).roller, function (i, rolle) {
            $("." + rolle).show();
        });
    }
}



// log ind
loginFormular.submit(function(event ) {
    event.preventDefault();

    $.ajax({
        url: "rest/login",
        data: JSON.stringify(loginFormular.serializeJSON()),
        contentType: "application/json",
        method: 'POST',
        success: function (data, textStatus, jqXhr) {
            localStorage.setItem("jwt",jqXhr.getResponseHeader('Authorization').substring(7));
            opdaterAlt();
            reloadView();
            loginFormular.trigger('reset');
            loginModal.foundation('close');
        }
    });
});


// log ud
logudKnap.click(function(event ) {
    localStorage.removeItem("jwt");
    opdaterAlt();
    reloadView();
    kontoModal.foundation('close');

});

// log ud
$("#user-icon").click(function(event ) {

    $.ajax( {
        type: "get",
        url: 'rest/brugere/'+parseJwt(localStorage.getItem("jwt")).oprId,
        success: function( bruger ) {
            kontoOversigt.find("#oversigt-oprId").html(bruger.oprId);
            kontoOversigt.find("#oversigt-oprNavn").html(bruger.oprNavn);
            kontoOversigt.find("#oversigt-ini").html(bruger.ini);
            kontoOversigt.find("#oversigt-cpr").html(bruger.cpr);
            kontoOversigt.find("#oversigt-password").html(bruger.password);
            kontoOversigt.find("#oversigt-roller").html(bruger.roller.join(', '));
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    } );


});