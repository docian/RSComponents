package com.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UtilsDataProvider {

	private UtilsDataProvider() {

	}

	public static String[][] issueData(String file, String dataSheet) {
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Sheet dataSheetParsed = wb.getSheet(dataSheet);
		XSSFRow line;
		Iterator<?> rowIt = dataSheetParsed.rowIterator();

		List<List<String>> dl = new ArrayList<List<String>>();
		rowIt.next();
		while (rowIt.hasNext()) {
			line = (XSSFRow) rowIt.next();
			Iterator<?> cellIt = line.iterator();
			List<String> rl = new ArrayList<String>();
			while (cellIt.hasNext()) {
				rl.add(cellIt.next().toString());
			}
			dl.add(rl);
		}

		String[][] bb = new String[dl.size()][2];
		int i = 0;
		for (List<String> it : dl) {
			bb[i++] = it.toArray(new String[it.size()]);
		}
		
		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bb;

	}

	public static Map<String, String> stringToHeader(String data) {
		Map<String, String> hd = null;
		if (!data.isEmpty()) {
			hd = new HashedMap<String, String>();
			String[] lst = data.split(",");
			for (String elem : lst) {
				hd.put(elem.split("=")[0], elem.split("=")[1]);
			}
		}
		return hd;
	}

	public static boolean isNullOrBlank(String s) {
		return (s == null || s.trim().equals(""));

	}

	public static String returnPort(String data) {
		Double d = Double.parseDouble(data);
		String port = d.toString();
		port = port.replace(".0", "");
		return port;
	}

}
