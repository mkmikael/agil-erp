// https://mvnrepository.com/artifact/org.postgresql/postgresql
@Grapes([
    @Grab('org.slf4j:slf4j-simple:1.5.11'),
    @Grab(group='postgresql', module='postgresql', version='9.1-901-1.jdbc4'),
    @GrabConfig(systemClassLoader = true),
])
import org.slf4j.*
import groovy.sql.*

def newSqlInstance(Map ds) {
    Sql.newInstance(ds.url, ds.user, ds.password, ds.driver)
}

def rowToList(row) {
    def metaData = row.getMetaData()
    def list = []
    for (i in 1..metaData.columnCount) {
        list << row[i - 1]
    }
    list
}

def wrapElements(List list, String gstring = '"') {
    list.eachWithIndex { item, i ->
        list.set(i, "${gstring}${item}${gstring}")
    }
}

def logger = LoggerFactory.getLogger('sql')
logger.info 'Initialize SQL'

def defaultDS = [
    driver: 'org.postgresql.Driver',
    user: 'postgres',
    password: 'postgres',
]
def agil_production_11092016 = [ url: 'jdbc:postgresql://localhost/agil_production_11092016'] + defaultDS
def agil_production = [url: 'jdbc:postgresql://localhost/agil_production'] + defaultDS

def sqlInstance = newSqlInstance(agil_production_11092016)
def sql = "select id, data_emissao, total, tipo from nota_comercial order by data_emissao"
def fileContent = new StringBuilder()
sqlInstance.eachRow(sql) { nota ->
    sql = """select p.nome as nome, p.nome_fantasia as nomeFantasia, coalesce(p.cnpj, p.cpf) as doc from participante p
                inner join papel pp on p.id = pp.participante_id
                inner join nota_comercial n on n.cliente_id = pp.id where n.id = ?"""
    def cliente = sqlInstance.firstRow(sql, [nota.id])
    def list = [nota.tipo, nota.data_emissao] + cliente.values()
    list << nota.total
    // def list = rowToList(nota)
    wrapElements(list)
    println list.join(';')
    fileContent << list.join(';') + '\n'
    sql = "select * from item_nota_comercial where nota_comercial_id = ?"
    sqlInstance.eachRow(sql, [nota.id]) { item ->
        sql = """select p.nome as nome from produto p where id = ?"""
        def produto = sqlInstance.firstRow(sql, [item.produto_id])
        sql = """select p.nome as nome from unidade_medida p where id = ?"""
        def unidade = sqlInstance.firstRow(sql, [item.unidade_medida_id])
        list = [item.total, unidade.nome, produto.nome]
        wrapElements(list)
        println list.join(';')
        fileContent << list.join(';') + '\n'
        // println "\t" + item.values().join(',')
    }
}
def file = new File('/home/mikael/Documentos/agil_production_11092016.csv')
file.write fileContent.toString()
println "Size Records: " + sqlInstance.firstRow('select count(*) from nota_comercial')
logger.info "Got myself a SQL connection: $sql"
sqlInstance.close()
