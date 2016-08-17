package web.agil.util

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

/**
 * Created by mkmik on 06/08/2016.
 */
class Util {
    static removeSpecialCaracter(String str) {
        if (str)
            return str
                    .replace('Á', 'A')
                    .replace('Ã', 'A')
                    .replace('Â', 'A')
                    .replace('Ê', 'E')
                    .replace('É', 'E')
                    .replace('Í', 'I')
                    .replace('Ó', 'O')
                    .replace('Ô', 'O')
                    .replace('Ú', 'U')
                    .replace("?", "")
                    .replace("Ç", "C")
    }

    static onlyNumber(String str) {
        if (str) {
            str.replaceAll("[^\\d]", '')
        }
    }

    static void openWorkbook(InputStream inputStream, Closure closure) {
        Workbook workbook = new XSSFWorkbook(inputStream)

        closure.delegate.openSheet = { Object indexOrName, Closure closure2 ->
            def sheet
            if (indexOrName instanceof String)
                sheet = workbook.getSheet(indexOrName)
            else if (indexOrName instanceof Integer)
                sheet = workbook.getSheetAt(indexOrName)

        }
        closure.delegate.get = { Row row, int field ->
            row.getCell( field )?.toString()
        }
        closure(workbook)
        inputStream.close();
    }

    static openSheet = { Object indexOrName, Closure closure ->
        def sheet
        if (indexOrName instanceof String)
            sheet = workbook.getSheet(indexOrName)
        else if (indexOrName instanceof Integer)
            sheet = workbook.getSheetAt(indexOrName)

    }

}
