package smoke;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import jxl.Sheet;
import jxl.Workbook;


public class GraphTest {
	
	
	private DBConnection dbconnection;
	public static void main(String[] args) throws Exception {

		FileInputStream fi = new FileInputStream("E:/Input.xls");
		Workbook w = Workbook.getWorkbook(fi);
		Sheet sheet = w.getSheet(0);
		// System.out.println(wb.getNumberOfSheets()); // Total Sheet numbers
		String line = ""; // available

		
		Properties prop = new Properties();
		for (int sheetNo = 0; sheetNo < w.getNumberOfSheets(); sheetNo++) {

			System.out.println("Sheet Name is:\t" + sheet.getName());
			ArrayList<String> Data = new ArrayList<String>();
			if (sheet.getName().trim().equals("Input")) {
				int columns = sheet.getColumns();
				int rows = sheet.getRows();

				System.out.println(columns + "-" + rows);

				for (int col = 0; col < columns; col++) {

					if (sheet.getCell(col, 0).getContents()
							.equalsIgnoreCase("DataForGraph")) {

						for (int row = 0; row < rows; row++) {
							if (!sheet.getCell(col, row).getContents()
									.isEmpty()) {

								System.out
										.println(col
												+ ","
												+ row
												+ "-"
												+ sheet.getCell(col, row)
														.getContents());

								Data.add(sheet.getCell(col, row).getContents());
							}

						}

					}
				}

			}
			for (int j = 1; j < Data.size(); j++) {
				String totalprice = Data.get(j);

				prop.setProperty("database" + j, totalprice);

				System.out.println("**************" + "Text" + j + "****"
						+ totalprice);

			}

		}

		prop.store(new FileOutputStream("E:/config.properties"), null);
	}
}
