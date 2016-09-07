/**
 * Created by mkmik on 20/08/2016.
 */

(function ($) {
    $.grailsAlert = function (options) {
        var defaults = {};
        var settings = $.extend({}, defaults, options);

        $.ajaxSetup({
            global: true
        });

        $( document ).ajaxSuccess(function( event, request, settings ) {
            console.log('URL:' + settings.url);
            console.log('DATA:' + settings.data);
            if (!settings.data) return;
            var viewState = settings.data['grails_view_state_id'];
            console.log('VIEW_STATE:' + viewState);
            var alertName = 'alert_' + viewState;
            if (settings.data[alertName]) {
                swal(settings.data[alertName]);
            }
        });
    };
})(jQuery);