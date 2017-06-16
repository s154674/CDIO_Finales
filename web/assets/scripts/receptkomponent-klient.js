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

    // Vis i tabel
    receptkomponentVisTabel.empty();
    $.each(window.receptkomponenter, function(i, receptkomponent){
        row = "<tr><td>"+receptkomponent.receptId+"</td><td>"+receptkomponent.raavareId+"</td><td>"+receptkomponent.nomNetto+"</td><td>"+receptkomponent.tolerance+"</td><td><a href='#'><i class='delete-icon fi-x'></i></a><a href='#'></a></td><td><a href='#'><i class='edit-icon fi-pencil' data-open='receptkomponent-rediger'></i></a></td></tr>"
        receptkomponentVisTabel.append(row)
    });
}










$("#recept-receptkomponent-ny").on('click',function(event){
   event.preventDefault();
   receptId = $(this).parents(".reveal").data("receptId");
   grid = $(this).parents(".reveal").find("#recept-receptkomponent-vis-grid");
   raavareSelector = $("<select style='margin-top:17px;' name='raavareId'></select>");
   raavareIderiercept = $("#recept-receptkomponent form.rediger [name='raavareId']").map(function(){return parseInt($(this).val())}).toArray();
   relraavarer = window.raavarer.filter(function(rv){return !raavareIderiercept.includes(rv.raavareId)});
   $.each(relraavarer, function(i, raavare) {
       row = "<option value='" + raavare.raavareId + "'>" + raavare.raavareNavn + "</option>";
       raavareSelector.append(row);
   });
   block = "<div class='column column-block'><div class='callout secondary'><form class='opret' action='rest/receptkomponenter' method='post'><input name='receptId' type='hidden' value='"+receptId+"'>"+raavareSelector.prop('outerHTML')+"<label>Vægt: <div class='input-group'><input name='nomNetto' class='input-group-field' type='number' value='100' step='0.01'><span class='input-group-label'>gram</span></div> </label> <label>Tolerance: <div class='input-group'><input name='tolerance' class='input-group-field' type='number' value='2' step='0.01'><span class='input-group-label'>%</span></div> </label><input type='submit' class='button expanded success' value='Opret'></form></div></div>";
   grid.append(block);
});


$("#recept-receptkomponent-vis-grid").on('submit', '.callout form.opret', function(event){
    event.preventDefault();
    raavareNavn = $(this).find("[name='raavareId'] option:selected").html();
    container = $(this).parents(".column");
    receptkomponent = $(this).serializeJSON();
    receptkomponent.receptId = parseInt(receptkomponent.receptId);
    receptkomponent.raavareId = parseInt(receptkomponent.raavareId);
    receptkomponent.nomNetto = parseFloat(receptkomponent.nomNetto);
    receptkomponent.tolerance = parseFloat(receptkomponent.tolerance);
    $.ajax({
        url: "rest/receptkomponenter",
        type:"POST",
        data: JSON.stringify(receptkomponent),
        contentType: "application/json",
        success: function (data) {
            window.receptkomponenter.push(receptkomponent);
            container.remove();
            block = "<div class='column column-block'><div class='callout secondary'><div style='height: 24px;'> <a href='#' class='float-right delete-icon'><i class='fi-x'></i></a><a href='#' class='float-right edit-icon'><i class='fi-pencil'></i></a></div><h5>"+raavareNavn+"</h5><form class='rediger' action='rest/receptkomponenter/{receptId}+{raavareId}'><input name='receptId' type='hidden' value='"+receptkomponent.receptId+"'> <input name='raavareId' type='hidden' value='"+receptkomponent.raavareId+"'> <label>Vægt: <div class='input-group'><input name='nomNetto' class='input-group-field' type='number' value='"+receptkomponent.nomNetto+"' step='0.01' readonly /><span class='input-group-label'>gram</span></div> </label> <label>Tolerance: <div class='input-group'><input name='tolerance' class='input-group-field' type='number' value='"+receptkomponent.tolerance+"' step='0.01' readonly /><span class='input-group-label'>%</span></div> </label><input type='submit' class='button expanded success' value='Opdater'></form></div></div>";
            $("#recept-receptkomponent-vis-grid").append(block);
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});



$("#recept-receptkomponent-vis-grid").on('click', '.callout .edit-icon', function(event){
    event.preventDefault();
    $(this).toggleClass("active");

    container = $(this).parents(".callout");
    container.find("[name='nomNetto']").prop("readonly", !$(this).hasClass( "active" ));
    container.find("[name='tolerance']").prop("readonly", !$(this).hasClass( "active" ));
    container.find("[type='submit']").slideToggle();
});

$("#recept-receptkomponent-vis-grid").on('submit', '.callout form.rediger', function(event){
    event.preventDefault();

    container = $(this).parents(".callout");
    receptkomponent = $(this).serializeJSON();
    receptkomponent.receptId = parseInt(receptkomponent.receptId);
    receptkomponent.raavareId = parseInt(receptkomponent.raavareId);
    receptkomponent.nomNetto = parseFloat(receptkomponent.nomNetto);
    receptkomponent.tolerance = parseFloat(receptkomponent.tolerance);
    $.ajax({
        url: "rest/receptkomponenter/"+receptkomponent.receptId+"+"+receptkomponent.raavareId,
        type:"PUT",
        data: JSON.stringify(receptkomponent),
        contentType: "application/json",
        success: function (data) {
            for (i = 0; i < window.receptkomponenter.length; i++) {
                if (window.receptkomponenter[i].receptId==receptkomponent.receptId && window.receptkomponenter[i].raavareId==receptkomponent.raavareId){
                    window.receptkomponenter[i] = receptkomponent;
                }
            }
            container.find(".edit-icon").trigger('click');
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});




$("#recept-receptkomponent-vis-grid").on('click', '.callout .delete-icon', function(event){
    event.preventDefault();
    container = $(this).parents(".column");
    receptId = parseInt(container.find("form [name='receptId']").val());
    raavareId = parseInt(container.find("form [name='raavareId']").val());

    $.ajax({
        url: "rest/receptkomponenter/"+receptId+"+"+raavareId,
        method:"DELETE",
        contentType: "application/json",
        success: function (data) {
            container.remove();
            window.receptkomponenter = window.receptkomponenter.filter(function(rk){return rk.receptId!==receptId || rk.raavareId!==raavareId})
        },
        beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
    });
});























// // Opret en ny receptkomponent
// receptkomponentNyFormular.submit(function(event ) {
//     event.preventDefault();
//
//     receptkomponent = receptkomponentNyFormular.serializeJSON();
//
//     $.ajax({
//         url: "rest/receptkomponenter",
//         method: 'POST',
//         data: JSON.stringify(receptkomponent),
//         contentType: "application/json",
//         success: function (data) {
//             window.receptkomponenter.push(receptkomponent);
//             visReceptkomponenter();
//             receptkomponentNy.foundation('close');
//             receptkomponentNyFormular.trigger('reset');
//         },
//         beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
//     });
// });
//
//
// // Tryk på redigér knappen
// receptkomponentVisTabel.on('click', "tr td .edit-icon", function (event) {
//     event.preventDefault();
//     row =  $(this).parents("tr");
//     receptkomponent = {"receptId":row.children("td").eq(0).html(),
//         "raavareId":row.children("td").eq(1).html(),
//         "nomNetto":row.children("td").eq(2).html(),
//         "tolerance":row.children("td").eq(3).html()};
//
//     receptkomponentRedigerFormular.attr("action","rest/receptkomponenter/"+receptkomponent.receptId+"+"+receptkomponent.raavareId);
//     receptkomponentRedigerFormular.find("[name='receptId']").val(receptkomponent.receptId);
//     receptkomponentRedigerFormular.find("[name='raavareId']").val(receptkomponent.raavareId);
//     receptkomponentRedigerFormular.find("[name='nomNetto']").val(receptkomponent.nomNetto);
//     receptkomponentRedigerFormular.find("[name='tolerance']").val(receptkomponent.tolerance);
// });
//
//
// // Send formular
// receptkomponentRedigerFormular.submit(function(event ) {
//     event.preventDefault();
//
//     receptkomponent = receptkomponentRedigerFormular.serializeJSON();
//
//     $.ajax({
//         url: receptkomponentRedigerFormular.attr("action"),
//         type:"PUT",
//         data: JSON.stringify(receptkomponent),
//         contentType: "application/json",
//         success: function (data) {
//             receptkomponentRediger.foundation('close');
//             receptkomponentRediger.trigger('reset');
//             opdaterAlt();
//         },
//         beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
//     });
// });
//
//
// // slet en receptkomponent
// receptkomponentVisTabel.on('click', "tr td .delete-icon", function (event) {
//     event.preventDefault();
//     row =  $(this).parents("tr");
//     receptId = row.children("td").eq(0).html();
//     raavareId = row.children("td").eq(1).html()
//
//     $.ajax({
//         url: "rest/receptkomponenter/"+receptId+"+"+raavareId,
//         method:"DELETE",
//         contentType: "application/json",
//         success: function (data) {
//             opdaterAlt();
//         },
//         beforeSend: function(xhr, settings) { xhr.setRequestHeader('Authorization','Bearer ' + localStorage.getItem("jwt")); }
//     });
// });