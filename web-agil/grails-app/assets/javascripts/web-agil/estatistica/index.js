/**
 * Created by mkmik on 04/07/2016.
 */
function go(url) {
    $.blockUI({ message: '<h1>Por favor, aguarde...</h1>' });
    return $.get(url)
        .done(function (data) {
            $('#views').html(data);
            applyBS_Table();
        }).fail(function () {
            swal('Erro inesperado.', 'Ocorreu um erro inesperado. Por favor, contate o suporte.', 'error');
        }).always(function () {
            $.unblockUI();
        });
}
$(function () {
    var baseUrl = $('#baseUrl').val();
    $('#produtoCmpMensal').click(function () {
        var url = baseUrl + "/estatistica/produtoComparativoMensal";
        go(url);
    });
    $('#fornecedorBI').click(function () {
        var url = baseUrl + "/estatistica/biFornecedor";
        go(url);
    });
    $('#produtoBI').click(function () {
        var url = baseUrl + "/estatistica/biProduto";
        go(url);
    });
});
