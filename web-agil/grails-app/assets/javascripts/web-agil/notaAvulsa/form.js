/**
 * Created by mkmik on 10/07/2016.
 */

function apply() {
    $('*[data-remover]').click(function () {
        if (confirm('Você tem certeza?')) {
            var id = $(this).data('id');
            if (id) {
                var url = $('#baseUrl').val() + "notaAvulsa/removerItem/" + id;
                $.post(url);
            }
            var row = $(this).parents('tr:first');
            row.remove();
        }
    });

    $('input[data-field]').keyup(function () {
        var row = $(this).parents('tr:first');
        var tdQuant = row.find('*[data-field="quantidade"]').val();
        var quant = parseFloat(tdQuant.replace(',', '.'));
        var tdVlUni = row.find('*[data-field="vlUnitario"]').val();
        var vlUni = parseFloat(tdVlUni.replace(',', '.'));
        var totalStr = (quant * vlUni).toFixed(2).toString().replace('.', ',');
        if (isNaN(quant) || isNaN(vlUni)) {
            totalStr = "0,00";
        }
        row.find('*[data-field="total"]').html(totalStr);
    });
}

function criarCliente() {
    $('#notaAvulsa\\.cliente\\.id').change(function () {
        var self = $(this);
        var text = self.find('option:selected').html();
        if (text == 'CRIAR CLIENTE') {
            console.log('LOL');
        }
    });
}

var count = 0;

$(function () {
    apply();
    criarCliente();
    count = $('#tableItens tbody tr').length;
    $('#add').click(function () {
        var url = $('#baseUrl').val() + "notaAvulsa/itemLinha";
        $.get(url, {index: count, __ie: Math.random()}).done(function (data) {
            $('#tableItens tbody').append(data);
            apply();
            applyMask();
            applySelect2();
            count++;
        });
    });
    $('input:submit').click(function () {
        var rows = $('#tableItens tbody tr').length;
        if (rows == 0) {
            swal('Erro de validação', 'Você deve adicionar ao menos um item.', 'error');
            return false;
        }
    });
});
