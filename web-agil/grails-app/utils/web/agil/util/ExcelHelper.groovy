package web.agil.util

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

/**
 * Created by mkmik on 14/08/2016.
 */
class ExcelHelper {

    Workbook workbook

    private ExcelHelper(Workbook workbook, Closure closure) {
        this.workbook = workbook
        closure.delegate = this
        closure(workbook)
    }

    static ExcelHelper openHSSFWorkbook(InputStream inputStream, Closure c) {
        Workbook workbook = new HSSFWorkbook(inputStream)
        def helper = new ExcelHelper(workbook, c)
        inputStream.close()
        helper
    }

    static void openXSSFWorkbook(String path, Closure c) {
        Workbook workbook = new XSSFWorkbook(path)
        new ExcelHelper(workbook, c)
    }

    static void openXSSFWorkbook(InputStream inputStream, Closure c) {
        Workbook workbook = new XSSFWorkbook(inputStream)
        def helper = new ExcelHelper(workbook, c)
        inputStream.close()
        helper
    }

    SheetHelper openSheet(Object indexOrName, Closure _closure) {
        Sheet sheet
        if (indexOrName instanceof String)
            sheet = workbook.getSheet(indexOrName)
        else if (indexOrName instanceof Integer)
            sheet = workbook.getSheetAt(indexOrName)
        else
            throw new IllegalArgumentException('O param indexOrName é inválido')
        if (!sheet)
            throw new RuntimeException('Sheet not found')
        new SheetHelper(sheet, _closure)
    }

    private class SheetHelper {
        Sheet sheet

        public SheetHelper(Sheet sheet, Closure closure) {
            this.sheet = sheet
            closure.delegate = this
            closure(sheet)
        }

        String get(int row, int column) {
            getCell(row, column)?.toString()
        }

        Cell getCell(int row, int column) {
            getRow(row)?.getCell(column)
        }

        Row getRow(int row) {
            sheet.getRow(row)
        }

    }

}
