package org.example.helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelHelpers {

    private Workbook workbook;
    private Sheet sheet;

    // Hàm mở file Excel
    public void setExcelFile(String excelFilePath, String sheetName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        workbook = new XSSFWorkbook(fileInputStream); // Đọc file .xlsx
        sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IOException("Error: Sheet named '\" + sheetName + \"' could not be found in the Excel file. " +
                    "Please check the Sheet name again (case sensitive).");
        }
    }

    // Hàm lấy dữ liệu từ ô (Cell) bất kỳ
    public String getCellData(int rowNum, int colNum) {
        try {
            Cell cell = sheet.getRow(rowNum).getCell(colNum);

            // Xử lý các định dạng dữ liệu (Số, Chuỗi, Công thức...)
            // Ở đây mình ép kiểu về String hết cho dễ xử lý trong Test
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        } catch (Exception e) {
            return "";
        }
    }

    // Hàm lấy tổng số dòng có dữ liệu
    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    // Hàm lấy tổng số cột
    public int getColCount() {
        return sheet.getRow(0).getLastCellNum();
    }

    // --- HÀM QUAN TRỌNG NHẤT: Đọc toàn bộ Sheet trả về mảng 2 chiều ---
    // Hàm này sẽ được @DataProvider gọi
    public Object[][] getDataHashTable(String excelPath, String sheetName, int startRow, int startCol) throws IOException {
        System.out.println("Đang đọc dữ liệu từ file: " + excelPath);

        setExcelFile(excelPath, sheetName);

        int rows = getRowCount();
        int cols = getColCount();

        // Tạo mảng 2 chiều để chứa dữ liệu
        // rows -> Số dòng dữ liệu thực tế (bỏ dòng tiêu đề nếu startRow = 1)
        Object[][] data = new Object[rows][cols];

        for (int i = 0; i < rows; i++) { // Duyệt từng dòng
            for (int j = 0; j < cols; j++) { // Duyệt từng cột
                // i + startRow: Để bỏ qua dòng tiêu đề
                data[i][j] = getCellData(i + startRow, j + startCol);
            }
        }
        return data;
    }
}