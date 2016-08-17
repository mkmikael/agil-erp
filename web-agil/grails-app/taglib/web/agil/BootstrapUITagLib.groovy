package web.agil

class BootstrapUITagLib {

    static namespace = "bs"
    static defaultEncodeAs = [taglib:'raw']
//    static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def fieldGroup = { attrs, body ->
        def inline = attrs.inline ?: false
        def classGroup = attrs.classGroup ?: ''
        if (attrs.type == 'select') {
            if (attrs.class)
                attrs.class += ' form-control'
            else
                attrs.class = 'form-control'

            out << """
            <div class="form-group $classGroup">
                ${label(attrs)}
                ${inline ? '<div class="form-inline">' : ''}
                ${g.select(attrs)}
                ${inline ? '</div>' : ''}
            </div>
        """
        } else {
            out << """
            <div class="form-group $classGroup">
                ${label(attrs)}
                ${inline ? '<div class="form-inline">' : ''}
                ${field(attrs)}
                ${inline ? '</div>' : ''}
            </div>
        """
        }
    }

    def label = { attrs, body ->
        def label = attrs.label ?: ''
        def required = attrs.required ?: false
        out << """<label class="control-label">${label} ${required ? '<span style="color: red">*</span>': ''}</label>"""
    }

    def field = { attrs, body ->
        def type = attrs.type ?: 'text'
        def name = attrs.name
        def id = attrs.id ?: name
        def clazz = attrs.class ?: ''
        def value = attrs.value
        def required = attrs.required ?: false
        out << """<input type="$type" class="form-control $clazz" ${attr('id', id)} ${attr('name', name)} ${attr('value', value)} ${required ? 'required' : ''}>"""
    }


    def modal = { attrs, body ->
        def id = attrs.id
        def size = attrs.size
        out << """
<div ${id ? "id=\"${id}\"" : ''} class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content ${size != 'lg' ?: 'modal-lg'} ${size != 'sm' ?: 'modal-sm'}">
      ${body()}
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->"""
    }

    def modalBody = { attrs, body ->
        out << """
<div class="modal-body">
    ${body()}
</div>"""
    }

    def modalHeader = { attrs, body ->
        out << """
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    ${body()}
</div>"""
    }

    def modalFooter = { attrs, body ->
        out << """
<div class="modal-footer">
    ${body()}
</div>"""
    }


    protected attr(attr, value) {
        if (value) {
            "${attr}=\"$value\""
        } else {
            ""
        }
    }

}
