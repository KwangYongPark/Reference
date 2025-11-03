package dev.binz.reference.user.service;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class StreamService {

	public void createLargeExcelStream(OutputStream out) throws IOException, InterruptedException {
		// SXSSFWorkbook을 사용하여 메모리 사용을 최소화
		try (SXSSFWorkbook workbook = new SXSSFWorkbook(100)) {
			Sheet sheet = workbook.createSheet("User Data");

			// 헤더 로우 생성
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Name");
			headerRow.createCell(1).setCellValue("Age");
			
			Thread.sleep(5000);
			
			// OutputStream에 직접 데이터 쓰기
			workbook.write(out);
		}
	}
}
