package web.agil

import grails.transaction.Transactional

@Transactional
class BatchService {

    def sessionFactory

    def gormClean() {
        log.debug "GORM Clearing..."
        sessionFactory.currentSession.flush()
        sessionFactory.currentSession.clear()
    }

    def gormClean(int i, int max) {
        if ((i + 1) % max == 0) {
            gormClean()
        }
    }

}
