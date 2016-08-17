package web.agil

import grails.transaction.Transactional
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import web.agil.cadastro.*
import web.agil.cadastro.enums.StatusPapel
import web.agil.util.Util

@Transactional
class ImportClienteExcelService {

    def grailsApplication
    def sessionFactory
    ImportClienteService importClienteService

    final ID                    = 1
    final IS_CNPJ_OR_CPF        = 2
    final CNPJ_OR_CPF           = 3
    final CLIENTE               = 4
    final INSCRICAO_ESTADUAL    = 5
    final ENDERECO              = 6
    final NUMERO                = 7
    final COMPLEMENTO           = 8
    final BAIRRO                = 9
    final CIDADE                = 10
    final UF                    = 11
    final CEP                   = 12
    final PAIS                  = 13
    final TELEFONE              = 14
    final VENDEDOR              = 15
    final CANAL                 = 16

    def execute() {
        log.debug('Init processamento planilha com clientes ativos e inativos')
        def pathClientes = grailsApplication.config.arquivo?.clientesExcel
        log.debug("File path: $pathClientes")
        FileInputStream fileInputStream = new FileInputStream(pathClientes)
        execute(fileInputStream)
    }

    def execute(InputStream inputStream) {
        Workbook workbook = new XSSFWorkbook(inputStream)
        Sheet clientesInativos = workbook.getSheetAt(0)
        Sheet clientesAtivos = workbook.getSheetAt(1)
        readSheet(clientesInativos, StatusPapel.INATIVO)
        readSheet(clientesAtivos, StatusPapel.ATIVO)
        inputStream.close();
    }

    def readSheet(Sheet sheet, StatusPapel status) {
        Row row
        for ( int i in 2 .. sheet.physicalNumberOfRows ) {
            row = sheet.getRow(i)
            try {
                if (row != null) {
                    def cliente = createCliente(row)
                    if (cliente) {
                        cliente.statusPapel = status
                        cliente.save(flush: true)
                    }
                    if (i % 30 == 0)
                        gormClean()
                }
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

    Cliente createCliente(Row row) {
        def map = [:]
        if (get(row, IS_CNPJ_OR_CPF) == "CNPJ") {
            map.inscricaoEstadual = get(row, INSCRICAO_ESTADUAL)
        }
        map.doc = get(row, CNPJ_OR_CPF)
        def params = get(row, CLIENTE)?.replace('–', '-')?.split('-')
        if (params?.length == 2) {
            map.nomeFantasia = params[1].trim()
            map.nome = params[0].trim()
        } else {
            map.nome = get(row, CLIENTE)
            map.nomeFantasia = map.nome
        }

        map.vendedor = get(row, VENDEDOR)

        map.telefone = [numero: get(row, TELEFONE)]

        def cep = get(row, CEP)
        def logradouro = get(row, ENDERECO)
        def complemento = get(row, COMPLEMENTO)
        def numero = get(row, NUMERO)
        def bairro = get(row, BAIRRO)
        def cidade = get(row, CIDADE)?.toUpperCase()
        map.endereco = [cep: cep, numero: numero, logradouro: logradouro, complemento: complemento, bairro: bairro, cidade: cidade, uf: 'PA']

        def cliente = importClienteService.createClienteFull(map)
        if (cliente)
            log.debug "Save $cliente"
        cliente
    }

    protected String get(Row row, int field) {
        row.getCell( field )?.toString()
    }

    protected gormClean() {
        log.debug('<<<<<<<<<<<GORM CLEAN>>>>>>>>>>>')
        sessionFactory.currentSession.flush()
        sessionFactory.currentSession.clear()
    }
}
