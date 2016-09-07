package web.agil

import com.gfaces.components.StateType
import grails.transaction.Transactional
import org.grails.web.util.WebUtils

@Transactional
class AlertService {

    def success(Map map) {
        message(StateType.SUCCESS, map)
    }

    def info(Map map) {
        message(StateType.INFO, map)
    }

    def danger(Map map) {
        message(StateType.DANGER, map)
    }

    def warning(Map map) {
        message(StateType.WARNING, map)
    }

    protected void message(StateType type, Map map) {
        def request = WebUtils.retrieveGrailsWebRequest()
        def params = request.flashScope
        def uuid = UUID.randomUUID().toString()
        params.grails_view_state_id = uuid
        def alert = [type: type] + map
        params["alert_$uuid"] = alert

    }

}
