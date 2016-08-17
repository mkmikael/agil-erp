// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-2.2.0.min
//= require bootstrap
//= require_tree plugins
//= require_self

if (typeof jQuery !== 'undefined') {
    $.ajaxSetup({ cache: false });
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}

function applyBS_Table() {
    var bsTable = $('table[data-bs-table]');
    bsTable.addClass('table table-bordered table-responsive table-striped scroll-v');
    bsTable.find('thead th.desc a').prepend('<span class="glyphicon glyphicon-sort-by-attributes-alt"></span> ');
    bsTable.find('thead th.asc a').prepend('<span class="glyphicon glyphicon-sort-by-attributes"></span> ');
    var trTable = bsTable.find('tbody tr');
    if (trTable.length == 0) {
        bsTable.wrap('<div></div>');
        var divScroll = bsTable.parent();
        divScroll.html('<div class="well">Sem registros.</div>');
    } else {
        bsTable.wrap('<div class="scroll-vertical"></div>');
    }
}

function applyBS_Pagination() {
    $('.pagination *').wrap('<li></li>');
    $('.pagination .currentStep').parents('li:first').addClass('active');
    $('.pagination .prevLink').html('&laquo');
    $('.pagination .nextLink').html('&raquo');
}

function applyBS_UI() {
    applyBS_Pagination();
    applyBS_Table();
}

function applyMask() {
    $('.cpf').mask('000.000.000-00', { reverse: true, placeholder: '___.___.___-__'});
    $('.cnpj').mask('00.000.000/0000-00', { reverse: true, placeholder: '__.___.___/____-__'});
    var inputs = $('[data-mask]');
    inputs.each(function (i, input) {
        var inputMask = $(input);
        var mask = inputMask.data('mask');
        if (mask == 'cpf') {
            $(inputMask).mask('000.000.000-00', { reverse: true, placeholder: '___.___.___-__'});
        } else if (mask == 'cnpj') {
            $(inputMask).mask('00.000.000/0000-00', { reverse: true, placeholder: '__.___.___/____-__'});
        } else if (mask == 'cep') {
            $(inputMask).mask('00000-000', { reverse: true, placeholder: '_____-___'});
        } else if (mask == 'data') {
            $(inputMask).mask('00/00/0000', { reverse: true, placeholder: '__/__/____' });
        } else if (mask == 'money') {
            $(inputMask).maskMoney({ thousands: '.', decimal: ',' });
        }
    });
}

function applySelect2() {
    $(".select2").select2({language: "pt-BR"});
}

$(function () {
    $('.datepicker').datepicker({
        format: "dd/mm/yyyy",
        language: 'pt-BR'
    });
    applyBS_UI();
    applyMask();
    applySelect2();
    $('form[data-js-validate]').validate();
    $('input:submit').click(function () {
        var self = $(this);
        var form = self.parents('form');
        if (form) {
            if (form.valid())
                $.blockUI({ message: '<h1>Por favor, aguarde...</h1>' });
        }
        return true;
    });
});
