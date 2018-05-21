package com.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.models.Response;

/**
 * @author: HiepLe
 * @version: May 14, 2018
 */
public class ExcelReportView extends AbstractXlsView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment;filename=\"student.xls\"");
		@SuppressWarnings("unchecked")
		List<Response> responses = (List<Response>) model.get("saleList");
		Sheet sheet = workbook.createSheet("Sale Data");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("id");
		header.createCell(1).setCellValue("agriID");
		header.createCell(2).setCellValue("area");
		header.createCell(3).setCellValue("quanlity");
		header.createCell(4).setCellValue("price");
		header.createCell(5).setCellValue("communeID");
		
		int rowNum = 1;
		for (Response myResponse : responses) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(myResponse.getSaleID());
			row.createCell(1).setCellValue(myResponse.getAgriID());
			row.createCell(2).setCellValue(myResponse.getArea());
			row.createCell(3).setCellValue(myResponse.getQuanlity());
			row.createCell(4).setCellValue(myResponse.getPrice());
			row.createCell(5).setCellValue(myResponse.getCommuneID());
		}
	}
}
