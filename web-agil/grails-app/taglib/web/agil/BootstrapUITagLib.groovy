package web.agil

import com.gfaces.components.StateType
import org.grails.web.util.WebUtils

class BootstrapUITagLib {

    static namespace = "bs"
    static defaultEncodeAs = [taglib:'raw']
//    static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def panelFilter = { attrs, body ->
        def id = attrs.id
        def hiddenButton = attrs.hiddenButton
        def random = Math.round(Math.random() * 1000)
        out << "<div ${attr('id', id)} class=\"panel panel-default\">"
        out << "<div class=\"panel-heading\">"
        out << """
<a data-toggle="collapse" data-target="#filtros_$random" class="collapsed">
    <span style="font-size: 1.2em;">
        <span class="glyphicon glyphicon-filter"></span> Filtros
    </span>
</a>
"""
        out << "</div> <!-- panel-heading -->"

        out << "<div id=\"filtros_$random\" class=\"panel-body collapse\">"
        out << body()
        if (hiddenButton == null)
            out << """<button type="submit" onclick="\$('#filtros_$random form').submit()" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Procurar</button>"""
        out << "</div> <!-- panel-body -->"
        out << "</div>"
    }

    def fieldGroup = { attrs, body ->
        def inline = attrs.inline ?: false
        def classGroup = attrs.classGroup ?: ''
        if (attrs.type == 'select' || attrs.type == 'multiple') {
            if (attrs.class)
                attrs.class += ' form-control'
            else
                attrs.class = 'form-control'
            if (attrs.type == 'multiple') {
                attrs.type = 'select'
                attrs.multiple = ''
            }
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

    def alert = { attrs, body ->
        def request = WebUtils.retrieveGrailsWebRequest()
        def params = request.getFlashScope()
        def uuid = params.grails_view_state_id
        if (!uuid) return
        def alertConfig = params["alert_$uuid"]
        StateType type = alertConfig.type
        out << """
<div class="alert alert-${type.label}" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <p>
        <span class="${type.icon}"></span>
        ${alertConfig.title ? "<b>${alertConfig.title}</b> " : ''}${alertConfig.text ?: ''}
    </p>
</div>
"""
    }

    def listButton = { attrs, body ->
        def entityName = attrs.entityName
        if (!entityName)
            throw new IllegalArgumentException("O atributo 'entityName' é obrigatório.")
        attrs.class = "btn btn-default"
        out << g.link(attrs) {
            "<span class=\"glyphicon glyphicon-list\"></span> ${message(code: 'default.list.label', args: [entityName])}"
        }
    }

    def createButton = { attrs, body ->
        attrs.action = 'create'
        def entityName = attrs.remove('entityName')
        if (!entityName)
            throw new IllegalArgumentException("O atributo 'entityName' é obrigatório.")
        attrs.class = "btn btn-default"
        out << g.link(attrs) {
            "<span class=\"glyphicon glyphicon-plus\"></span> ${message(code: 'default.new.label', args: [entityName])}"
        }
    }

    def editButton = { attrs, body ->
        def resource = attrs.resource
        if (!resource)
            throw new IllegalArgumentException("O atributo 'resource' é obrigatório.")
        attrs.class = "btn btn-default"
        attrs.action = 'edit'
        out << g.link(attrs) {
            "<span class=\"glyphicon glyphicon-edit\"></span> ${message(code: 'default.button.update.label', default: 'Update')}"
        }
    }

    def saveButton = { attrs, body ->
        out << """
<button type="submit" class="btn btn-default">
    <span class="glyphicon glyphicon-floppy-disk"></span> ${message(code: 'default.button.create.label', default: 'Create')}
</button>
"""
    }

    def updateButton = { attrs, body ->
        out << """
<button type="submit" class="btn btn-default">
    <span class="glyphicon glyphicon-floppy-disk"></span> ${message(code: 'default.button.update.label', default: 'Update')}
</button>
"""
    }

    def deleteButton = { attrs, body ->
        def resource = attrs.resource
        if (!resource)
            throw new IllegalArgumentException("O atributo 'resource' é obrigatório.")
        attrs.style = "display: inline-block"
        attrs.method = "DELETE"
        out << g.form(attrs) {
            """
<button class="btn btn-default" type="submit" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" >
    <span class="glyphicon glyphicon-trash"></span> ${message(code: 'default.button.delete.label', default: 'Delete')}
</button>
"""
        }
    }

    protected attr(attr, value) {
        if (value) {
            "${attr}=\"$value\""
        } else {
            ""
        }
    }

}
