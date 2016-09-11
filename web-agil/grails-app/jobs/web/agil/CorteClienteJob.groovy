package web.agil

class CorteClienteJob {

    CorteClienteService corteClienteService

    static triggers = {
        cron name: 'agil', cronExpression: "0 0 1 * * ?"
    }

    def execute() {
        corteClienteService.processar()
    }

}
