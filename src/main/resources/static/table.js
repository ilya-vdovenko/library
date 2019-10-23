$(document).ready(function(){

    $("#t_body").load("/books_list #tr_table");

    var by = "author";
    var order = "asc";
    var author_order = "desc";
    var title_order = "asc";
    var from = 5;

    $("#author_th").css({'border-style':'solid', 'border-color':'#4db8ff'});

    $("#showMore").click(function(){
        $.get("/books_list?from="+from+"&by="+by+"&order="+order, function(data) {
            if($.trim($(data).find("#isn").html())=='') {
                alert("Все данные загружены");
            }
            else {
                $("#t_body").append($(data).find("#tr_table"));
                from+=5;
            }

        });
    });

    $("#author_th").click(function() {

        title_order="asc";

        $("#t_body").load("/books_list?from=0&by=author&order="+author_order+" #tr_table");

        $("#title_th").css({'border-style':'solid', 'border-color' : ''});
        $(this).css({'border-style':'solid', 'border-color':'#4db8ff'});

        from = 5;
        by = "author";
        order = author_order;

        if(author_order=="desc") author_order = "asc";
        else author_order = "desc";
    });

    $("#title_th").click(function () {

        author_order ="asc";

        $("#t_body").load("/books_list?from=0&by=title&order="+title_order+" #tr_table");

        $("#author_th").css({'border-style':'solid', 'border-color' : ''});
        $(this).css({'border-style':'solid', 'border-color':'#4db8ff'});

        from = 5;
        by = "title";
        order = title_order;

        if(title_order=="desc") title_order = "asc";
        else title_order = "desc";
    });
});
