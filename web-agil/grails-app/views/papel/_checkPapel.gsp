<script>
    $(function () {
        $('#checkPapel').modal();
        $('#check').click(function () {
            var url = $('#baseUrl').val() + 'papel/checkPapel';
            var data = { doc: $('#doc').val(), docType: $('#checkPapel input:radio:checked').val() };
            $.ajaxBS({
                url: url,
                data: data,
                method: 'POST'
            }).done(function (_data) {
                if (_data.alert)
                    if (_data.alert.type)
                        return
                $('#checkPapel').modal('hide');
                $('#doc\\.label').val(data.doc);
                $('#participante\\.doc').val(data.doc);
            });
        });
    });
</script>
<bs:modal id="checkPapel">
    <bs:modalBody>
        <input type="radio" name="mask" data-mask-target="#doc" value="cpf" checked> CPF
        <input type="radio" name="mask" data-mask-target="#doc" value="cnpj"> CNPJ
        <input id="doc" class="form-control">
    </bs:modalBody>
    <bs:modalFooter>
        <button id="check" type="button" class="btn btn-default">Verificar</button>
    </bs:modalFooter>
</bs:modal>