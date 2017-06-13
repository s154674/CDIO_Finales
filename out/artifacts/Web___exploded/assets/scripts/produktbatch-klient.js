produktbatchAdministration = $("#produktbatch-administration");

produktbatchNy = $("#produktbatch-ny");
produktbatchNyFormular = produktbatchNy.find("#produktbatch-ny-formular");
produktbatchVis = $("#produktbatch-vis");
produktbatchVisTabel = produktbatchVis.find("#produktbatch-vis-tabel tbody").first();
produktbatchRediger = $("#produktbatch-rediger");
produktbatchRedigerFormular = produktbatchRediger.find("#produktbatch-rediger-formular");

function opdaterProduktbatcher(roller){
    if (roller.includes("Farmaceut", "Vaerkfoerer")) {

        $.ajax({
            type: "get",
            url: 'rest/produktbatcher',
            success: function (data) {
                window.produktbatcher = data;
            },
            error: function (xhr, ajaxOptions, thrownError) {
                notify("alert", "Kunne ikke hente produktbatcher");
                window.produktbatcher = [];
            },
            beforeSend: function (xhr, settings) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("jwt"));
            }
        }).done(function () {
            visProduktbatcher();
        });
    } else {
        window.produktbatcher = [];
        visProduktbatcher();
    }
}
function visProduktbatcher() {
    console.log("produktbatcher:");
    console.log(window.produktbatcher);

    // Vis i tabel
    produktbatchVisTabel.empty();
    $.each(window.produktbatcher, function(i, produktbatch){
        row = "<tr><td>"+produktbatch.pbId+"</td><td>"+produktbatch.status+"</td><td>"+produktbatch.receptId+"</td><td><a href='#'><i class='delete-icon fi-x'></i></a><a href='#'></a></td><td><a href='#'><i class='edit-icon fi-pencil' data-open='produktbatch-rediger'></i></a></td></tr>"
        produktbatchVisTabel.append(row)
    });
}






// Opret en ny raavare
produktbatchNyFormular.submit(function(event ) {
    event.preventDefault();

    produktbatch = produktbatchNyFormular.serializeJSON();

    $.ajax({
        url: "rest/produktbatcher",
        method: 'POST',
        data: JSON.stringify(produktbatch),
        contentType: "application/json",
        success: function (data) {
            window.produktbatcher.push(produktbatch);
            visRaavarer();
            produktbatchNy.foundation('close');
            produktbatchNyFormular.trigger('reset');
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// Tryk på redigér knappen
produktbatchVisTabel.on('click', "tr td .edit-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    produktbatch = {"pbId":row.children("td").eq(0).html(),
        "status":row.children("td").eq(1).html(),
        "receptId":row.children("td").eq(2).html()};

    produktbatchRedigerFormular.attr("action","rest/produktbatcher/"+produktbatch.pbId);
    produktbatchRedigerFormular.find("[name='pbId']").val(produktbatch.pbId);
    produktbatchRedigerFormular.find("[name='status']").val(produktbatch.status);
    produktbatchRedigerFormular.find("[name='receptId']").val(produktbatch.receptId);
});


// Send formular
produktbatchRedigerFormular.submit(function(event ) {
    event.preventDefault();

    produktbatch = produktbatchRedigerFormular.serializeJSON();

    $.ajax({
        url: produktbatchRedigerFormular.attr("action"),
        type:"PUT",
        data: JSON.stringify(produktbatch),
        contentType: "application/json",
        success: function (data) {
            raavareRediger.foundation('close');
            raavareRediger.trigger('reset');
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// slet en bruger
produktbatchVisTabel.on('click', "tr td .delete-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    pbId = row.children("td").eq(0).html()

    $.ajax({
        url: "rest/produktbatcher/"+pbId,
        method:"DELETE",
        contentType: "application/json",
        success: function (data) {
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});