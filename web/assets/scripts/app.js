/**
 * Created by emilbonnekristiansen on 09/06/2017.
 */
notificationPane = $("#notification-pane");
function notify(type, txt) {
    notification = $("<div style='display: none;' class='notification callout "+type+"' data-closable><p>"+txt+"</p><button class='close-button' aria-label='Dismiss alert' type='button' data-close> <span aria-hidden='true'>&times;</span> </button></div>");
    notification.prependTo(notificationPane).fadeIn().delay(5000).fadeOut();
    //notification.remove();
}


function opdaterAlt(){


    roller = [];

    if (localStorage.getItem("jwt")!==null){
        roller = parseJwt(localStorage.getItem("jwt")).roller;
    }

    opdaterBrugere(roller);

    opdaterRaavarer(roller);
    opdaterRecepter(roller);
    opdaterReceptkomponenter(roller);

    opdaterRaavarebatcher(roller);
    opdaterProduktbatcher(roller);
    opdaterProduktbatchkomponenter(roller);
}