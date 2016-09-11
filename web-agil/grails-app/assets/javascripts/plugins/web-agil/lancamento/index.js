/**
 * Created by mikael on 11/09/16.
 */

$(function () {
    $('*[data-url]').click(function () {
        var self = $(this);
        var url = self.data('url');
        $.ajax(url, { method: 'POST' }).done(function (data) {
            $('#dados').html(data);
            var status = self.parent().parent().find('.status');
            status.html(self.data('status'));
        });
    });
});
