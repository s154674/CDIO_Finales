raavareBatchAdministration = $("#raavarebatch-administration");

raavarebatchNy = $("#raavarebatch-ny");
raavarebatchNyFormular = raavarebatchNy.find("#raavarebatch-ny-formular");
raavarebatchVis = $("#raavarebatch-vis");
raavarebatchVisTabel = raavarebatchVis.find("#raavarebatch-vis-tabel tbody").first();
raavarebatchRediger = $("#raavarebatch-rediger");
raavarebatchRedigerFormular = raavarebatchRediger.find("#raavarebatch-rediger-formular");

function opdaterRaavarebatcher(roller){
    if (roller.includes("Farmaceut", "Vaerkfoerer")) {
        $.ajax({
            type: "get",
            url: 'rest/raavarebatcher',
            success: function (data) {
                window.raavarebatcher = data;
            },
            error: function (xhr, ajaxOptions, thrownError) {
                notify("alert", "Kunne ikke hente råvarebatcher");
                window.raavarebatcher = [];
            },
            beforeSend: function (xhr, settings) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("jwt"));
            }
        }).done(function () {
            visRaavarebatcher();
        });
    } else {
        window.raavarebatcher = [];
        visRaavarebatcher();
    }
}
function visRaavarebatcher() {
    console.log("raavarebatcher:");
    console.log(window.raavarebatcher);

    // Vis i tabel
    raavarebatchVisTabel.empty();
    $.each(window.raavarebatcher, function(i, raavarebatch){
        row = "<tr><td>"+raavarebatch.rbId+"</td><td>"+raavarebatch.raavareId+"</td><td>"+raavarebatch.maengde+"</td><td><a href='#'><i class='delete-icon fi-x'></i></a><a href='#'></a></td><td><a href='#'><i class='edit-icon fi-pencil' data-open='raavarebatch-rediger'></i></a></td></tr>"
        raavarebatchVisTabel.append(row)
    });
}

//


// Opret en ny raavarebatch
raavarebatchNyFormular.submit(function(event ) {
    event.preventDefault();

    raavarebatch = raavarebatchNyFormular.serializeJSON();

    $.ajax({
        url: "rest/raavarebatcher",
        method: 'POST',
        data: JSON.stringify(raavarebatch),
        contentType: "application/json",
        success: function (data) {
            window.raavarebatcher.push(raavarebatch);
            visRaavarebatcher();
            raavarebatchNy.foundation('close');
            raavarebatchNyFormular.trigger('reset');
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// Tryk på redigér knappen
raavarebatchVisTabel.on('click', "tr td .edit-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    raavarebatch = {"rbId":row.children("td").eq(0).html(),
        "raavareId":row.children("td").eq(1).html(),
        "maengde":row.children("td").eq(2).html()};

    raavarebatchRedigerFormular.attr("action","rest/raavarebatcher/"+raavarebatch.rbId);
    raavarebatchRedigerFormular.find("[name='rbId']").val(raavarebatch.rbId);
    raavarebatchRedigerFormular.find("[name='raavareId']").val(raavarebatch.raavareId);
    raavarebatchRedigerFormular.find("[name='maengde']").val(raavarebatch.maengde);
});


// Send formular
raavarebatchRedigerFormular.submit(function(event ) {
    event.preventDefault();

    raavarebatch = raavarebatchRedigerFormular.serializeJSON();

    $.ajax({
        url: raavarebatchRedigerFormular.attr("action"),
        type:"PUT",
        data: JSON.stringify(raavarebatch),
        contentType: "application/json",
        success: function (data) {
            raavarebatchRediger.foundation('close');
            raavarebatchRediger.trigger('reset');
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// slet en receptkomponent
raavarebatchVisTabel.on('click', "tr td .delete-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    rbId = row.children("td").eq(0).html()

    $.ajax({
        url: "rest/raavarebatcher/"+rbId,
        method:"DELETE",
        contentType: "application/json",
        success: function (data) {
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});