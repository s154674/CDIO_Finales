receptkomponentAdministration = $("#receptkomponent-administration");

receptkomponentNy = $("#receptkomponent-ny");
receptkomponentNyFormular = receptkomponentNy.find("#receptkomponent-ny-formular");
receptkomponentVis = $("#receptkomponent-vis");
receptkomponentVisTabel = receptkomponentVis.find("#receptkomponent-vis-tabel tbody").first();
receptkomponentRediger = $("#receptkomponent-rediger");
receptkomponentRedigerFormular = receptkomponentRediger.find("#receptkomponent-rediger-formular");

function opdaterReceptkomponenter(roller){
    if (roller.includes("Farmaceut")) {
        window.receptkomponenter = [];
        visReceptkomponenter();
        $.ajax({
            type: "get",
            url: 'rest/receptkomponenter',
            success: function (data) {
                window.receptkomponenter = data;
            },
            error: function (xhr, ajaxOptions, thrownError) {
                notify("alert", "Kunne ikke hente receptkomponenter");
                window.receptkomponenter = [];
            },
            beforeSend: function (xhr, settings) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("jwt"));
            }
        }).done(function () {
            visReceptkomponenter();
        });
    } else {
        window.receptkomponenter = [];
        visReceptkomponenter();
    }
}
function visReceptkomponenter() {
    console.log("receptkomponenter:");
    console.log(window.receptkomponenter);

    // Vis i tabel
    receptkomponentVisTabel.empty();
    $.each(window.receptkomponenter, function(i, receptkomponent){
        row = "<tr><td>"+receptkomponent.receptId+"</td><td>"+receptkomponent.raavareId+"</td><td>"+receptkomponent.nomNetto+"</td><td>"+receptkomponent.tolerance+"</td><td><a href='#'><i class='delete-icon fi-x'></i></a><a href='#'></a></td><td><a href='#'><i class='edit-icon fi-pencil' data-open='receptkomponent-rediger'></i></a></td></tr>"
        receptkomponentVisTabel.append(row)
    });
}

//






// Opret en ny receptkomponent
receptkomponentNyFormular.submit(function(event ) {
    event.preventDefault();

    receptkomponent = receptkomponentNyFormular.serializeJSON();

    $.ajax({
        url: "rest/receptkomponenter",
        method: 'POST',
        data: JSON.stringify(receptkomponent),
        contentType: "application/json",
        success: function (data) {
            window.receptkomponenter.push(receptkomponent);
            visReceptkomponenter();
            receptkomponentNy.foundation('close');
            receptkomponentNyFormular.trigger('reset');
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// Tryk på redigér knappen
receptkomponentVisTabel.on('click', "tr td .edit-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    receptkomponent = {"receptId":row.children("td").eq(0).html(),
        "raavareId":row.children("td").eq(1).html(),
        "nomNetto":row.children("td").eq(2).html(),
        "tolerance":row.children("td").eq(3).html()};

    receptkomponentRedigerFormular.attr("action","rest/receptkomponenter/"+receptkomponent.receptId+"+"+receptkomponent.raavareId);
    receptkomponentRedigerFormular.find("[name='receptId']").val(receptkomponent.receptId);
    receptkomponentRedigerFormular.find("[name='raavareId']").val(receptkomponent.raavareId);
    receptkomponentRedigerFormular.find("[name='nomNetto']").val(receptkomponent.nomNetto);
    receptkomponentRedigerFormular.find("[name='tolerance']").val(receptkomponent.tolerance);
});


// Send formular
receptkomponentRedigerFormular.submit(function(event ) {
    event.preventDefault();

    receptkomponent = receptkomponentRedigerFormular.serializeJSON();

    $.ajax({
        url: receptkomponentRedigerFormular.attr("action"),
        type:"PUT",
        data: JSON.stringify(receptkomponent),
        contentType: "application/json",
        success: function (data) {
            receptkomponentRediger.foundation('close');
            receptkomponentRediger.trigger('reset');
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});


// slet en receptkomponent
receptkomponentVisTabel.on('click', "tr td .delete-icon", function (event) {
    event.preventDefault();
    row =  $(this).parents("tr");
    receptId = row.children("td").eq(0).html();
    raavareId = row.children("td").eq(1).html()

    $.ajax({
        url: "rest/receptkomponenter/"+receptId+"+"+raavareId,
        method:"DELETE",
        contentType: "application/json",
        success: function (data) {
            opdaterAlt();
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});