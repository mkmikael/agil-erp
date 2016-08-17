package web.agil.arquivo

/**
 * Created by mkmik on 13/08/2016.
 */
enum TipoExtensao {
    XLS('xls'), XLSX('xlsx'), TXT('txt'), CSV('csv'), PDF('pdf')

    String extensao

    TipoExtensao(String extensao) {
        this.extensao = extensao
    }
}