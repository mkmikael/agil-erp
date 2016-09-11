package web.agil.financeiro.planoPagamento

class IntervaloPagamento extends PlanoPagamento {

    def calendarioService

    Integer dias

    static constraints = {
    }

    List<Date> getDatasPrevistas(Date data) {
        data += dias
        [calendarioService.proximoDiaUtil(data)]
    }

    String toString() { "Intervalo #$id dias: $dias" }
}
