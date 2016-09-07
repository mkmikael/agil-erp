/**
 * Created by mkmik on 21/08/2016.
 */

$(function() {
    $('#pf').show();
    $('#pj').hide();
    $('#tipoPessoa').change(function() {
        if (this.value =="PF") {
            $('#pf').show();
            $('#pj').hide();
        } else if (this.value == "PJ") {
            $('#pj').show();
            $('#pf').hide();
        }
    });
});