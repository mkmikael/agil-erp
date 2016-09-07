<g:if test="${flash.message}">
    <g:if test="${flash.message instanceof String}">
        <div class="alert alert-info" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            ${flash.message}
        </div>
    </g:if>
    <g:else>
        <div class="alert alert-${flash.message.type}" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            ${flash.message.text}
        </div>
    </g:else>
</g:if>