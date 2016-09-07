/**
 * Created by mkmik on 02/08/2016.
 */

(function($) {
    $.ajaxBS = function(options) {
        $.blockUI({ message: '<h1>Por favor, aguarde...</h1>' });
        if (!options.data) {
            options.data = {}
        }
        options.data._ie_ = Math.round(Math.random() * 10000);
        var ajax = $.ajax(options)
            .always(function () {
                $.unblockUI();
            });
        return {
            done: function(funcDone) {
                ajax.done(function(data, textStatus, jqXHR) {
                    funcDone(data, textStatus, jqXHR);
                    if (data.alert) {
                        swal(data.alert);
                    }
                });
                return this;
            },
            fail: function(funcFail) {
                ajax.fail(funcFail);
                swal('Erro inesperado.', 'Ocorreu um erro inesperado. Por favor, contate o suporte.', 'error');
                return this;
            },
            always: function(funcFail) {
                ajax.always(funcFail);
                return this;
            }
        }; // return
    }; // ajaxBS
})(jQuery);