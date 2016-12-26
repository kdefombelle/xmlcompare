/**
 *  This software program has been written by Karim de Fombelle.
 */
package fr.kdefombelle.xmlcompare.core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlunit.diff.Difference;


public class ExcelDifferenceWriter {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    final Logger logger = LoggerFactory.getLogger(ExcelDifferenceWriter.class);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public void write(Iterable<Difference> differences, OutputStream os) throws IOException {
        write("control", "test", differences, os);
    }

    public void write(String controlIdentifier, String testIdentifier, Iterable<Difference> differences, OutputStream os) throws IOException {
        Workbook wb = new SXSSFWorkbook();

        Sheet sheet1 = wb.createSheet("Xmlcompare");
        int rowIndex = -1;

        CellStyle titleStyle = getTitleCellStyle(wb);

        Row row0 = sheet1.createRow(++rowIndex);
        row0.createCell(0).setCellValue(controlIdentifier);
        createStyledCell(row0, 1, titleStyle).setCellValue("control");
        createStyledCell(row0, 2, titleStyle).setCellValue("test");
        row0.createCell(3).setCellValue(testIdentifier);

        Row row1 = sheet1.createRow(++rowIndex);

        Row row2 = sheet1.createRow(++rowIndex);
        if (!differences.iterator().hasNext()) {
            row2.createCell(0).setCellValue("No difference found");
        } else {
            createStyledCell(row2, 0, titleStyle).setCellValue("xpath");
            createStyledCell(row2, 1, titleStyle).setCellValue("value");
            createStyledCell(row2, 2, titleStyle).setCellValue("value");
            createStyledCell(row2, 3, titleStyle).setCellValue("xpath");

            for (Difference difference : differences) {
                Row row3 = sheet1.createRow(++rowIndex);
                row3.createCell(0).setCellValue(difference.getComparison().getControlDetails().getXPath());
                row3.createCell(1).setCellValue(String.valueOf(difference.getComparison().getControlDetails().getValue()));
                row3.createCell(2).setCellValue(String.valueOf(difference.getComparison().getTestDetails().getValue()));
                row3.createCell(3).setCellValue(difference.getComparison().getTestDetails().getXPath());
            }
        }
        wb.write(os);
    }

    public void write(File xmlControl, File xmlTest, Iterable<Difference> differences, OutputStream os) throws IOException {
        String controlFileName = xmlControl.getAbsolutePath();
        String testFileAbsolutePath = xmlTest.getAbsolutePath();
        write(controlFileName, testFileAbsolutePath, differences, os);
    }

    private CellStyle getTitleCellStyle(Workbook wb) {
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        titleStyle.setFillPattern(CellStyle.FINE_DOTS);
        titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
        titleStyle.setBorderTop(CellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
        titleStyle.setBorderRight(CellStyle.BORDER_THIN);
        return titleStyle;
    }

    private Cell createStyledCell(Row row, int index, CellStyle cellStyle) {
        Cell cell = row.createCell(index);
        cell.setCellStyle(cellStyle);
        return cell;
    }
}
