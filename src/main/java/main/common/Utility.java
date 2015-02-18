package main.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;



public class Utility {
	private static BufferedReader bufRdr;

	/**
	 * Method Name: readFile Description: Reading a file and returning an object
	 * of BufferedReader
	 * 

	 * @param filePath
	 * @return BufferedReader object
	 */
	public static BufferedReader readFile(String filePath)
			throws FileNotFoundException {
		File file = new File(filePath);
		bufRdr = new BufferedReader(new FileReader(file.getAbsolutePath()));
		return bufRdr;
	}

	/**
	 * Method Name: getStringTokenized Description: Tokenizes the string and
	 * returns a string array with the tokenized elements
	 * Author: Jagan Date: 22-08-2013 Description:
	 * @param conditions
	 * @return String[]
	 */
	public static String[] getStringTokenized(String stringToTokenize,
			String delimiter) {

		StringTokenizer st = new StringTokenizer(stringToTokenize, delimiter);
		int numberOfTokens = st.countTokens();
		int position = 0;
		String[] tokenArray = new String[numberOfTokens];
		while (st.hasMoreTokens()) {
			String content = st.nextToken();
			tokenArray[position] = content;
			position++;
		}
		return tokenArray;

	}

	/**
	 * Method Name: getSourceFiles Description: Returns the list of files from a
	 * particular directory
	 * 
	 * @param sourceFilePath
	 * @return listFiles
	 */

	public static File[] getSourceFiles(String sourceFilePath) {
		File sourceDir = new File(sourceFilePath);
		sourceFilePath = sourceDir.getAbsolutePath();
		File[] listFiles = new File(sourceFilePath).listFiles();
		return listFiles;

	}

	/**
	 * Author: Jagan Date: 22-08-2013 Description: For a particular date format,
	 * the year or month or date or hour or minute or second can be incremented
	 * or decremented and the same could be returned
	 * 
	 * @param textPattern
	 * @param message
	 * @return
	 */
	public static String parseDateToAFormat(String format, String dateToParse,
			int year, int month, int date, int hourOfDay, int minute, int second) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(dateToParse));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, date);
		cal.add(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.add(Calendar.MINUTE, minute);
		cal.add(Calendar.SECOND, second);
		cal.add(Calendar.YEAR, year);
		return sdf.format(cal.getTime());
	}

	/**
	 * Author: Jagan Date: 22-08-2013 Description:
	 * 
	 * @param textPattern
	 * @param message
	 * @return
	 */
	public static String getDateAndMinuteIncremented(String dateFormat,
			int dateCount, int minuteCount) {
		Calendar currentTime = Calendar.getInstance();
		SimpleDateFormat timeFormat = new SimpleDateFormat(dateFormat);
		currentTime.add(Calendar.DATE, dateCount);
		currentTime.add(Calendar.MINUTE, minuteCount);
		return timeFormat.format(currentTime.getTime());

	}

	public static StringBuilder convertStringToBinary(String stringToConvert) {
		byte[] bytes = stringToConvert.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
			binary.append(' ');
		}
		System.out.println("'" + stringToConvert + "' to binary: " + binary);
		return binary;
	}

}
