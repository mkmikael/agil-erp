package web.anp

import grails.transaction.Transactional

@Transactional(readOnly = true)
class CalendarioService {

    def proximoDiaUtil(Date data) {
        def d=data
        while (naoEhDiaUtil(d)) d=d+1
        d
    }
    def nDiasUteis(n,Date data=new Date()) {
        data.hours   = 0
        data.minutes = 0
        data.seconds = 0
        def d= data
        if (n>=0) {
            def i=n
            while(i--) {
                d=proximoDiaUtil(d+1)
            }
            d
        } else {
            d=proximoDiaUtil(d)
        }
        d
    }
    def naoEhDiaUtil(Date data) {
        (ehSabado(data) || ehDomingo(data))
    }
    def ehDiaUtil(Date data) {
        !naoEhDiaUtil(data)
    }
    def ehSabado(Date data) {
        def cal=new GregorianCalendar()
        cal.time=data
        cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY
    }

    def ehDomingo(Date data) {
        def cal=new GregorianCalendar()
        cal.time=data
        cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
    }

    def eh(Date data, int dayOfWeek) {
        def cal=new GregorianCalendar()
        cal.time=data
        cal.get(Calendar.DAY_OF_WEEK)==dayOfWeek
    }

    def proximoDiaDaSemana(Date date, int dayOfWeek) {
        while(!eh(date, dayOfWeek)) date = date + 1
        date
    }
}

