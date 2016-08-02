<%@ page import="web.agil.financeiro.enums.TipoNotaFiscal" %>

<script type="application/javascript">
    $(function () {
        $('#mes').change(function () {
            var baseUrl = $('#baseUrl').val();
            var url = baseUrl + "/estatistica/${viewName}";
            var data = { mes: this.value };
            $.blockUI({ message: '<h1>Por favor, aguarde...</h1>' });
            $.get(url, data)
            .done(function (data) {
                $('#views').html(data);
                applyBS_Table();
            }).fail(function () {
                swal('Erro inesperado.', 'Ocorreu um erro inesperado. Por favor, contate o suporte.', 'error');
            }).always(function () {
                $.unblockUI();
            });
        });

    });
</script>

<div>
    <label for="mes">Mês</label>
    <g:select name="mes" optionKey="valor" optionValue="label" value="${mes}"
              from="[[valor: 1, label: 'Janeiro'],
                     [valor: 2, label: 'Fevereiro'],
                     [valor: 3, label: 'Março'],
                     [valor: 4, label: 'Abril'],
                     [valor: 5, label: 'Maio'],
                     [valor: 6, label: 'Junho'],
                     [valor: 7, label: 'Julho'],
                     [valor: 8, label: 'Agosto'],
                     [valor: 9, label: 'Setembro'],
                     [valor: 10, label: 'Outubro'],
                     [valor: 11, label: 'Novembro'],
                     [valor: 12, label: 'Dezembro'],
              ]" />
</div>
