/**
 * Created by emilbonnekristiansen on 10/06/2017.
 */
function opdaterProduktbatchkomponenter(roller){
    if (roller.includes("Farmaceut", "Vaerkfoerer")) {
        $.ajax({
            type: "get",
            url: 'rest/produktbatchkomponenter',
            success: function (data) {
                window.produktbatchkomponenter = data;
            },
            error: function (xhr, ajaxOptions, thrownError) {
                notify("alert", "Kunne ikke hente produktbatchkomponenter");
                window.produktbatchkomponenter = [];
            },
            beforeSend: function (xhr, settings) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("jwt"));
            }
        }).done(function () {
            visProduktbatchkomponenter();
        });
    } else {
        window.produktbatchkomponenter = [];
        visProduktbatchkomponenter();
    }
}
function visProduktbatchkomponenter() {
    console.log("produktbatchkomponenter:");
    console.log(window.produktbatchkomponenter);
}