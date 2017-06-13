opretBrugerFormular = $("#bruger-opret");
opretBrugerModal = $("#opret-bruger-modal");
brugerSkema = $("#bruger-vis tbody");
redigerBrugerFormular = $('#bruger-rediger');
redigerBrugerModal = $("#rediger-bruger-modal");

function opdaterBrugere(roller){
    if (roller.includes("Administrator")){
        $.ajax( {
            type: "get",
            url: 'rest/brugere',
            success: function( data ) {
                window.brugere = data;
            },
            error: function(xhr, ajaxOptions, thrownError) {
                window.brugere = [];
                notify("alert","Kunne ikke hente brugere");
            },
            beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
        }).done(function(){
            visBrugere();
        });
    } else {
        window.brugere = [];
        visBrugere();
    }
}

function visBrugere(){
    console.log("brugere:");
    console.log(window.brugere);

    // brugerskema
    brugerSkema.empty();
    $.each(window.brugere, function(i, bruger){
        row = "<tr><td>"+bruger.oprId+"</td><td>"+bruger.oprNavn+"</td><td>"+bruger.ini+"</td><td>"+bruger.cpr+"</td><td>"+bruger.password+"</td><td>"+bruger.roller.join(", ")+"</td><td><a href='#'><i class='delete-icon fi-x'></i></a><a href='#'></a></td><td><a href='#'><i class='edit-icon fi-pencil' data-open='rediger-bruger-modal'></i></a></td></tr>"
        brugerSkema.append(row)
    });
}


// Opret en ny bruger
opretBrugerFormular.submit(function(event ) {
    event.preventDefault();

    bruger = opretBrugerFormular.serializeJSON();
    bruger.roller = opretBrugerFormular.find("[name='roller']").first().val();

    $.ajax({
        url: "rest/brugere",
        method: 'POST',
        data: JSON.stringify(bruger),
        contentType: "application/json",
        success: function (data) {
            window.brugere.push(bruger);
            visBrugere();
            opretBrugerModal.foundation('close');
            opretBrugerFormular.trigger('reset');
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});

// rediger en bruger

    // Tryk på redigér knappen
    brugerSkema.on('click', "tr td .edit-icon", function (event) {
        event.preventDefault();
        row =  $(this).parents("tr");
        bruger = {"oprId":row.children("td").eq(0).html(),
                  "oprNavn":row.children("td").eq(1).html(),
                  "ini":row.children("td").eq(2).html(),
                  "cpr":row.children("td").eq(3).html(),
                  "password":row.children("td").eq(4).html(),
                  "roller":row.children("td").eq(5).html()}

        redigerBrugerFormular.attr("action","rest/brugere/"+bruger.oprId);
        redigerBrugerFormular.find("[name='oprId']").val(bruger.oprId);
        redigerBrugerFormular.find("[name='oprNavn']").val(bruger.oprNavn);
        redigerBrugerFormular.find("[name='ini']").val(bruger.ini);
        redigerBrugerFormular.find("[name='cpr']").val(bruger.cpr);
        redigerBrugerFormular.find("[name='password']").val(bruger.password);
        $.each(bruger.roller.split(", "), function(i,e){
            redigerBrugerFormular.find("[name='roller'] option[value='" + e + "']").prop("selected", true);
        });
    })

    // Send formular
    redigerBrugerFormular.submit(function(event ) {
        event.preventDefault();

        bruger = redigerBrugerFormular.serializeJSON();
        bruger.roller = redigerBrugerFormular.find("[name='roller']").first().val()

        $.ajax({
            url: redigerBrugerFormular.attr("action"),
            type:"PUT",
            data: JSON.stringify(bruger),
            contentType: "application/json",
            success: function (data) {
                redigerBrugerModal.foundation('close');
                redigerBrugerFormular.trigger('reset');
                opdaterBrugere();
            },
            beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
        });
    });


// slet en bruger
brugerSkema.on('click', "tr td .delete-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    oprId = row.children("td").eq(0).html()

    $.ajax({
        url: "rest/brugere/"+oprId,
        method:"DELETE",
        data: bruger,
        contentType: "application/json",
        success: function (data) {
            opdaterBrugere();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
})