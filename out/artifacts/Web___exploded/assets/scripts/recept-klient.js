receptAdministration = $("#recept-administration");

receptNy = $("#recept-ny");
receptNyFormular = receptNy.find("#recept-ny-formular");
receptVis = $("#recept-vis");
receptVisTabel = receptVis.find("#recept-vis-tabel tbody").first();
receptRediger = $("#recept-rediger");
receptRedigerFormular = receptRediger.find("#recept-rediger-formular");

/**
 * Created by emilbonnekristiansen on 10/06/2017.
 */
function opdaterRecepter(roller){
    if (roller.includes("Farmaceut")) {
        $.ajax({
            type: "get",
            url: 'rest/recepter',
            success: function (data) {
                window.recepter = data;
            },
            error: function (xhr, ajaxOptions, thrownError) {
                notify("alert", "Kunne ikke hente recepter");
                window.recepter = [];
            },
            beforeSend: function (xhr, settings) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("jwt"));
            }
        }).done(function () {
            visRecepter();
        });
    } else {
        window.recepter = [];
        visRecepter();
    }
}

function visRecepter() {
    console.log("recepter:");
    console.log(window.recepter);


    // Vis i tabel
    receptVisTabel.empty();
    $.each(window.recepter, function(i, recept){
        row = "<tr><td>"+recept.receptId+"</td><td>"+recept.receptNavn+"</td><td><a href='#'><i class='delete-icon fi-x'></i></a><a href='#'></a></td><td><a href='#'><i class='edit-icon fi-pencil' data-open='recept-rediger'></i></a></td></tr>"
        receptVisTabel.append(row)
    });

    // Vis i oprettelse og redigering af produktbatches
    produktbatchNyFormular.find("[name=receptId]").empty();
    produktbatchRedigerFormular.find("[name=receptId]").empty();
    $.each(window.recepter, function(i, recept) {
        row = "<option value='"+recept.receptId+"'>"+recept.receptNavn+"</option>";

        produktbatchNyFormular.find("[name=receptId]").append(row);
        produktbatchRedigerFormular.find("[name=receptId]").append(row);
    });

}




//


// Opret en ny recept
receptNyFormular.submit(function(event ) {
    event.preventDefault();

    recept = receptNyFormular.serializeJSON();

    $.ajax({
        url: "rest/recepter",
        method: 'POST',
        data: JSON.stringify(recept),
        contentType: "application/json",
        success: function (data) {
            window.recepter.push(recept);
            visRecepter();
            receptNy.foundation('close');
            receptNyFormular.trigger('reset');
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// Tryk på redigér knappen
receptVisTabel.on('click', "tr td .edit-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    recept = {"receptId":row.children("td").eq(0).html(),
        "receptNavn":row.children("td").eq(1).html()};

    receptRedigerFormular.attr("action","rest/recepter/"+recept.receptId);
    receptRedigerFormular.find("[name='receptId']").val(recept.receptId);
    receptRedigerFormular.find("[name='receptNavn']").val(recept.receptNavn);
});


// Send formular
receptRedigerFormular.submit(function(event ) {
    event.preventDefault();

    recept = receptRedigerFormular.serializeJSON();

    $.ajax({
        url: receptRedigerFormular.attr("action"),
        type:"PUT",
        data: JSON.stringify(recept),
        contentType: "application/json",
        success: function (data) {
            receptRediger.foundation('close');
            receptRediger.trigger('reset');
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// slet en bruger
receptVisTabel.on('click', "tr td .delete-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    receptId = row.children("td").eq(0).html()

    $.ajax({
        url: "rest/recepter/"+receptId,
        method:"DELETE",
        contentType: "application/json",
        success: function (data) {
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});