package dev.binz.Reference.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.FileOutputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class StreamRestControlleTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	WebTestClient webTestClient; 
	
	@Test
	void test() throws Exception {
		log.debug("1");
		MvcResult mvcResult = mockMvc.perform(get("/down"))
//				.andExpect(request().asyncStarted())
				.andReturn();
		
//		mvcResult.getAsyncResult();
		
		String filePath = "don.xlsx";
		log.debug("2");
		System.out.println("99");
		try(FileOutputStream fileOutputStream = new FileOutputStream(filePath)){
			byte[] content = mvcResult.getResponse().getContentAsByteArray();
			// 파일이 정상적으로 받아졌는지 크기를 출력하여 확인
			System.out.println("다운로드된 파일 크기: " + content.length + " bytes");
			fileOutputStream.write(content);
		}
		log.debug("end");
	}
	
	@Test
	void test1() {
		 webTestClient.get().uri("/down")
         .exchange() // 요청 실행
         .expectStatus().isOk() // 상태 코드 200 OK 확인
         .expectHeader().valueEquals("Content-Disposition", "attachment; filename=\"don.xlsx\"") // 헤더 확인
         .expectBody(byte[].class) // 응답 본문을 바이트 배열로 가져옴
         .consumeWith(response -> {
             byte[] content = response.getResponseBody();
//             assertThat(content).isNotNull();
//             assertThat(content.length).isGreaterThan(0);
             System.out.println("다운로드된 파일 크기 (WebTestClient): " + content.length + " bytes");
         });
	}
}
