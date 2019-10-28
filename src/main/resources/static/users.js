$(document).ready(function() {

    /*Удаление записи (пользователя)*/
    $("table #t_body").on('click', '.del', function () {
        var name = $(this).val();
        if (window.confirm( "Удалить пользователя: " + name +"?")) {
            $.get("/users/delete?name="+name);
            $(this).closest('tr').remove();
        }
    });

    /*Открытие модального окна для добавления записи (книги)*/
    $('#addBtn').click(function(){
        $('#UserModal').load("/users/new .modal",function(){
            $('.modal').modal();
        });
    });

    /*Открытие модального окна для редактирования записи (книги)*/
    $("table #t_body").on('click', '#name', function () {
            var tr = $(this).closest('tr');
            var name = tr.find('#name').text();
        $('#UserModal').load("/users/edit?name="+name+" .modal",function(){
            $('.modal').modal();
        });
    });

    /*Отправка формы*/
    $("#UserModal").on('click', '.send', function (){
        var url = $(this).val();
        $.post(url, $('#user_form').serialize(), function(data) {
            var user_form = $(data).find('#user_form');
            if(user_form.length>0) {
                $('#user_form').remove();
                $('.modal-body').prepend(user_form);
            }
            else {
                $('.modal').modal('hide');
                $('#user_form').remove();
                $("#t_body").load("/users #tr_table");
            }
        });
    });
});