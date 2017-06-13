/**
 * Created by emilbonnekristiansen on 09/06/2017.
 */
function notify(type, txt) {
    console.log(type+": "+txt);
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