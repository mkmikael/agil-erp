package web.agil.financeiro.planoPagamento

class ConjuntoIntervaloPgto extends PlanoPagamento {

    static hasMany = [intervalos: IntervaloPagamento]
    static constraints = {
    }

    List<Date> getDatasPrevistas(Date dataReferencia) {
        if (!intervalos) {
            throw new RuntimeException('O conjunto esta sem intervalos.')
        }
        def list = []
        intervalos.each { i ->
            list << i.getDatasPrevistas(dataReferencia)?.get(0)
        }
        list
    }

    String getDisplay() {
        def dias = intervalos?.dias?.sort()
        dias ? dias.join(' - ') : 'Sem intervalo definido'
    }

    String toString() { "ConjuntoIntervaloPgto #$id intervalos: ${intervalos?.dias}" }

}
