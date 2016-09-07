package web.agil

import org.hibernate.Session


class LogInterceptor {


    LogInterceptor() {
        matchAll()
    }

    boolean before() {
        def p = new HashMap(params)
        def ip = request.remoteAddr
        def user = 'ANON'
//        User.withNewSession { Session session ->
//            def userId = springSecurityService.currentUserId
//            if (userId) {
//                user = session.load(User, userId) ?: 'ANON'
//                if (!user.isAttached())
//                    user.attach()
//            }
//        }
        def controller = p.remove('controller') ?: ''
        def action = p.remove('action') ?: ''
        def format = p.remove('format') ?: ''
        log.debug("[$ip@$user][C: $controller, A: $action, F: $format]${p}")
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
