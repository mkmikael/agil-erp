/**
 * Created by mkmik on 11/08/2016.
 */
(function($) {
    $.fn.validateState = function(options) {
        var defaults = {
            state: 'none'
        };
        var settings = $.extend({}, defaults, options);
        var self = this;

        if (settings.state != 'error'
            && settings.state != 'success'
            && settings.state != 'warning'
            && settings.state != 'none') {
            console.warn('Param state invalid! The state could be: error, success, warning, none');
            return;
        }

        var formGroup = self.parents('.form-group:first');
        var id = 'status_' + settings.state + '_' + self.prop('id');

        var _clean = function() {
            console.log(self.find('.form-control-feedback'));
            formGroup.find('.form-control-feedback').remove();
            self.find('#' + id).remove();
            formGroup.removeClass('has-error has-warning has-success has-feedback');
        };

        _clean();
        if (settings.state == 'none')
            return;

        formGroup.addClass('has-' + settings.state + ' has-feedback');
        self.attr('aria-describedby', id);
        self.after('<span id="' + id + '" class="sr-only">(' + settings.state + ')</span>');

        var glyphicon;
        switch (settings.state) {
            case 'success':
                glyphicon = 'ok';
                break;
            case 'error':
                glyphicon = 'remove';
                break;
            case 'warning':
                glyphicon = 'warning-sign';
                break;
        }

        self.after('<span class="glyphicon glyphicon-' + glyphicon + ' form-control-feedback" aria-hidden="true"></span>');
    };
})(jQuery)