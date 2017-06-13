raavareAdministration = $("#raavare-administration");

raavareNy = $("#raavare-ny");
raavareNyFormular = raavareNy.find("#raavare-ny-formular");
raavareVis = $("#raavare-vis");
raavareVisTabel = raavareVis.find("#raavare-vis-tabel tbody").first();
raavareRediger = $("#raavare-rediger");
raavareRedigerFormular = raavareRediger.find("#raavare-rediger-formular");

function opdaterRaavarer(roller){
    if (roller.includes("Farmaceut")) {
        window.raavarer = [];
        visRaavarer();
        $.ajax({
            type: "get",
            url: 'rest/raavarer',
            success: function (data) {
                window.raavarer = data;
            }, error: function (xhr, ajaxOptions, thrownError) {
                notify("alert", "Kunne ikke hente raavarer");
                window.raavarer = [];
            },
            beforeSend: function (xhr, settings) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("jwt"));
            }
        }).done(function () {
            visRaavarer();
        });
    } else {
        window.raavarer = [];
        visRaavarer();
    }
}
function visRaavarer() {
    console.log("raavarer:");
    console.log(window.raavarer);

    // Vis i tabel
    raavareVisTabel.empty();
    $.each(window.raavarer, function(i, raavare){
        row = "<tr><td>"+raavare.raavareId+"</td><td>"+raavare.raavareNavn+"</td><td>"+raavare.leverandoer+"</td><td><a href='#'><i class='delete-icon fi-x'></i></a><a href='#'></a></td><td><a href='#'><i class='edit-icon fi-pencil' data-open='raavare-rediger'></i></a></td></tr>"
        raavareVisTabel.append(row)
    });

    // Vis i oprettelse og redigering af råvarebatches samt receptkomponenter
    raavarebatchNyFormular.find("[name=raavareId]").empty();
    raavarebatchRedigerFormular.find("[name=raavareId]").empty();
    receptkomponentNyFormular.find("[name=raavareId]").empty();
    receptkomponentRedigerFormular.find("[name=raavareId]").empty();
    $.each(window.raavarer, function(i, raavare) {
        row = "<option value='"+raavare.raavareId+"'>"+raavare.raavareNavn+"</option>";

        raavarebatchNyFormular.find("[name=raavareId]").append(row);
        raavarebatchRedigerFormular.find("[name=raavareId]").append(row);
        receptkomponentNyFormular.find("[name=raavareId]").append(row);
        receptkomponentRedigerFormular.find("[name=raavareId]").append(row);
    });
}


// Opret en ny raavare
raavareNyFormular.submit(function(event ) {
    event.preventDefault();

    raavare = raavareNyFormular.serializeJSON();

    $.ajax({
        url: "rest/raavarer",
        method: 'POST',
        data: JSON.stringify(raavare),
        contentType: "application/json",
        success: function (data) {
            window.raavarer.push(raavare);
            visRaavarer();
            raavareNy.foundation('close');
            raavareNyFormular.trigger('reset');
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// Tryk på redigér knappen
raavareVisTabel.on('click', "tr td .edit-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    raavare = {"raavareId":row.children("td").eq(0).html(),
        "raavareNavn":row.children("td").eq(1).html(),
        "leverandoer":row.children("td").eq(2).html()};

    raavareRedigerFormular.attr("action","rest/raavarer/"+raavare.raavareId);
    raavareRedigerFormular.find("[name='raavareId']").val(raavare.raavareId);
    raavareRedigerFormular.find("[name='raavareNavn']").val(raavare.raavareNavn);
    raavareRedigerFormular.find("[name='leverandoer']").val(raavare.leverandoer);
});


// Send formular
raavareRedigerFormular.submit(function(event ) {
    event.preventDefault();

    raavare = raavareRedigerFormular.serializeJSON();

    $.ajax({
        url: raavareRedigerFormular.attr("action"),
        type:"PUT",
        data: JSON.stringify(raavare),
        contentType: "application/json",
        success: function (data) {
            raavareRediger.foundation('close');
            raavareRediger.trigger('reset');
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// slet en receptkomponent
raavareVisTabel.on('click', "tr td .delete-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    raavareId = row.children("td").eq(0).html()

    $.ajax({
        url: "rest/raavarer/"+raavareId,
        method:"DELETE",
        contentType: "application/json",
        success: function (data) {
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});