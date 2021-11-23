package SingletoneTest;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelData {
	public String[][]read_sheet() throws InvalidFormatException, IOException{
		File myFile = new File (".\\test_data\\inputs.xlsx");
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(myFile);
		XSSFSheet mySheet = wb.getSheet("Sheet1");
		int numberOfRows = mySheet.getPhysicalNumberOfRows();
		int numberOfColumns=mySheet.getRow(0).getLastCellNum();
		String [][] myArray= new String [numberOfRows-1][numberOfColumns];

	
	
		for(int i=1;i<numberOfRows;i++) {
			for(int j =0;j<numberOfColumns;j++) {
				XSSFRow row=mySheet.getRow(i);
				myArray[i-1][j]=row.getCell(j).getStringCellValue();
			}
		}
		
		return myArray;
		
	}

}
