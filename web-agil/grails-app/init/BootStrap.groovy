class BootStrap {

    def init = { servletContext ->
        Locale.default = new Locale('pt', 'BR')
    }
    def destroy = {
    }
}
